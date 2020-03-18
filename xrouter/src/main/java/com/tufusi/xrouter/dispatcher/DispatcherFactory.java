package com.tufusi.xrouter.dispatcher;

import android.os.Looper;

import com.tufusi.annotation.RunThread;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description 分发工厂类
 */
public class DispatcherFactory {

    private static final Dispatcher MAIN_THREAD_DISPATCHER = new MainThreadDispatcher();
    private static final Dispatcher POSTING_THREAD_DISPATCHER = new PostingThreadDispatcher();
    private static final Dispatcher BACKGROUND_THREAD_DISPATCHER = new BackgroundDispatcher();
    private volatile static Dispatcher sAsyncThreadDispatcher;

    /**
     * 获取事件分发器
     *
     * @param runThread 线程器
     * @return 返回分发器
     */
    public static Dispatcher getEventDispatch(RunThread runThread) {
        switch (runThread) {
            case MAIN:
                return isMainThread() ? POSTING_THREAD_DISPATCHER : MAIN_THREAD_DISPATCHER;
            case POSTING:
                return POSTING_THREAD_DISPATCHER;
            case BACKGROUND:
                return !isMainThread() ? POSTING_THREAD_DISPATCHER : BACKGROUND_THREAD_DISPATCHER;
            case ASYNC:
                //懒加载，仅在需要时创建线程池
                if (sAsyncThreadDispatcher == null) {
                    synchronized (DispatcherFactory.class) {
                        if (sAsyncThreadDispatcher == null) {
                            sAsyncThreadDispatcher = new AsyncThreadDispatcher();
                        }
                    }
                }
                return sAsyncThreadDispatcher;
            default:
                return MAIN_THREAD_DISPATCHER;
        }
    }

    private static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
