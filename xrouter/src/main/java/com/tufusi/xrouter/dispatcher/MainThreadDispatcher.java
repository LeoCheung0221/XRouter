package com.tufusi.xrouter.dispatcher;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by 鼠夏目 on 2020/3/16.
 *
 * @See
 * @Description 主线程分发器
 */
public class MainThreadDispatcher implements Dispatcher {

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void dispatch(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    @Override
    public boolean stop() {
        mMainHandler.removeCallbacksAndMessages(null);
        return true;
    }
}
