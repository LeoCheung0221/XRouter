package com.tufusi.xrouter;

import com.tufusi.xrouter.dispatcher.Dispatcher;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description 路由分发类
 */
public class XRouter {

    private Set<WeakReference<Object>> mReceivers = new CopyOnWriteArraySet<>();
    private Set<Dispatcher> mDispatchers = new HashSet<>();
    private Map<Class<?>, ReceiverHandler> mReceiverHandlerByInterface = new ConcurrentHashMap<>();

    boolean mAnnotateMethodOnInterface;

    private XRouter() {
    }

    private static class InstanceHolder {
        private static XRouter sInstance = new XRouter();
    }

    public static XRouter instance() {
        return InstanceHolder.sInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getReceiver(Class<T> interfaceType) {
        ReceiverHandler receiverHandler = mReceiverHandlerByInterface.get(interfaceType);

        if (!interfaceType.isInterface()) {
            throw new XRouterException(String.format("receiverType must be a interface , " + "%s is not a interface", interfaceType.getName()));
        }

        if (receiverHandler == null) {
            receiverHandler = new ReceiverHandler(this, interfaceType, mReceivers);
            mReceiverHandlerByInterface.put(interfaceType, receiverHandler);
        }

        return (T) mReceiverHandlerByInterface.get(interfaceType).mReceiverProxy;
    }

    public void setmAnnotateMethodOnInterface(boolean mAnnotateMethodOnInterface) {
        this.mAnnotateMethodOnInterface = mAnnotateMethodOnInterface;
    }

    /**
     * 添加分发器
     */
    void addDispatch(Dispatcher dispatcher) {
        mDispatchers.add(dispatcher);
    }

    public synchronized void register(Object receiver) {
        if (receiver == null) {
            return;
        }
        mReceivers.add(new WeakReference<>(receiver));
    }

    public synchronized void unregister(Object receiver) {

        if (receiver == null) {
            return;
        }

        for (Object mReceiver : mReceivers) {
            WeakReference weakReference = (WeakReference) mReceiver;
            Object o = weakReference.get();
            if (receiver.equals(o) || o == null) {
                mReceivers.remove(mReceiver);
            }
        }

        Iterator iterator = mReceiverHandlerByInterface.keySet().iterator();
        while (iterator.hasNext()) {
            Class type = (Class) iterator.next();
            if (type.isInstance(receiver) && mReceiverHandlerByInterface.get(type).getSameTypeReceivesCount() == 0) {
                iterator.remove();
            }
        }

        if (mReceivers.size() == 0) {
            stopRouter();
        }
    }

    private void stopRouter() {
        for (Dispatcher dispatcher : mDispatchers) {
            dispatcher.stop();
        }
    }
}
