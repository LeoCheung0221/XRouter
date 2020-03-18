package com.tufusi.xrouter.dispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 鼠夏目 on 2020/3/16.
 *
 * @See
 * @Description 异步线程分发器
 */
public class AsyncThreadDispatcher implements Dispatcher {

    private ExecutorService mAsyncExecutor;

    AsyncThreadDispatcher() {
        mAsyncExecutor = Executors.newCachedThreadPool();
    }

    @Override
    public void dispatch(Runnable runnable) {
        if (mAsyncExecutor.isShutdown()) {
            return;
        }
        mAsyncExecutor.execute(runnable);
    }

    @Override
    public boolean stop() {
        return true;
    }
}
