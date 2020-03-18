package com.tufusi.xrouter;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description 接收器处理类
 */
public class ReceiverHandler implements InvocationHandler {

    private XRouter xRouter;
    private Class mReceiverType;
    private Set<WeakReference<Object>> mReceivers;
    private AtomicInteger sameTypeReceivesCount = new AtomicInteger(0);
    Object mReceiverProxy;

    ReceiverHandler(XRouter xRouter, Class receiverType, Set<WeakReference<Object>> mReceivers) {
        this.xRouter = xRouter;
        this.mReceiverType = receiverType;
        this.mReceivers = mReceivers;
        this.mReceiverProxy = Proxy.newProxyInstance(mReceiverType.getClassLoader(), new Class[]{mReceiverType}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        for (WeakReference weakReference : mReceivers) {
            Object receiver = weakReference.get();
            if (mReceiverType.isInstance(receiver)) {
                if (!xRouter.mAnnotateMethodOnInterface) {
                    try {
                        method = receiver.getClass().getMethod(method.getName(), method.getParameterTypes());
                    } catch (NoSuchMethodException e) {
                        throw new XRouterException(String.format("%s no method %s", receiver.getClass().getName(), method.getName()), e);
                    }
                }
                Acception acception = new Acception(receiver, method, args);
                acception.dispatchEvent();
                xRouter.addDispatch(acception.mDispatcher);
            }
        }
        return null;
    }

    int getSameTypeReceivesCount() {
        sameTypeReceivesCount.set(0);

        for (WeakReference weakReference : mReceivers) {
            Object receiver = weakReference.get();
            if (mReceiverType.isInstance(receiver)) {
                sameTypeReceivesCount.incrementAndGet();
            }
        }
        return sameTypeReceivesCount.get();
    }
}
