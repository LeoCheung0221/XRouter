package com.tufusi.xrouter;

import android.text.TextUtils;
import android.util.Log;

import com.tufusi.annotation.RunThread;
import com.tufusi.annotation.Subscribe;
import com.tufusi.xrouter.dispatcher.Dispatcher;
import com.tufusi.xrouter.dispatcher.DispatcherFactory;

import java.lang.reflect.Method;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description 接收类
 */
public class Acception {

    private Object mReceiver;
    private Method mInvokedMethod;
    private Object[] mArgs;
    private Runnable mRunnable;
    Dispatcher mDispatcher;

    Acception(Object receiver, Method invokedMethod, Object[] args) {
        this.mReceiver = receiver;
        this.mInvokedMethod = invokedMethod;
        this.mArgs = args;
        initReception();
    }

    private void initReception() {
        mInvokedMethod.setAccessible(true);
        mRunnable = produceEvent();
        RunThread runThread = RunThread.MAIN;
        ThreadFinder threadFinderFinder = null;
        try {
            Class<?> methodThreadCls = Class.forName("com.tufusi.xrouter.MethodThreadFinder");
            threadFinderFinder = (ThreadFinder) methodThreadCls.newInstance();
            String fullMethodName = mReceiver.getClass().getName() + "." + mInvokedMethod.getName();
            String methodThreadName = threadFinderFinder.getMethodThread(fullMethodName);
            if (!TextUtils.isEmpty(methodThreadName)) {
                runThread = RunThread.valueOf(methodThreadName);
            }
            Log.d("Acception", "Use Annotation ahead of runtime");
        } catch (ClassNotFoundException e) {
            Log.e("Acception", "Use Annotation ahead of runtime ClassNotFoundException", e);
        } catch (InstantiationException e) {
            Log.e("Acception", "Use Annotation ahead of runtime InstantiationException", e);
        } catch (IllegalAccessException e) {
            Log.e("Acception", "Use Annotation ahead of runtime IllegalAccessException", e);
        } catch (NullPointerException e) {
            Log.e("Acception", "Use Annotation ahead of runtime NullPointerException", e);
        }
        if (threadFinderFinder == null) {
            Subscribe subscribeAnnotation = mInvokedMethod.getAnnotation(Subscribe.class);
            if (subscribeAnnotation != null) {
                runThread = subscribeAnnotation.runThread();
            }
            Log.d("Acception", "Use Annotation Runtime");
        }
        mDispatcher = DispatcherFactory.getEventDispatch(runThread);
    }

    void dispatchEvent() {
        mDispatcher.dispatch(mRunnable);
    }

    private Runnable produceEvent() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    if (mInvokedMethod != null && mReceiver != null) {
                        mInvokedMethod.invoke(mReceiver, mArgs);
                    }
                } catch (Exception e) {
                    Log.d("Acception", "UnHandler Exception when method "
                            + mInvokedMethod.getName() + " of " + mReceiver.getClass().getCanonicalName() + "," + e);
                }
            }
        };
    }
}
