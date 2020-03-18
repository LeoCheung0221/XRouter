package com.tufusi.xrouter.dispatcher;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by 鼠夏目 on 2020/3/16.
 *
 * @See
 * @Description 后台线程分发器
 */
public class BackgroundDispatcher implements Dispatcher {

    private HandlerThread mBackgroundThread = new HandlerThread("Background");
    private Handler mBackgroundHandler;

    BackgroundDispatcher() {
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    @Override
    public void dispatch(Runnable runnable) {
        if (!mBackgroundThread.isAlive()) {
            return;
        }
        mBackgroundHandler.post(runnable);
    }

    @Override
    public boolean stop() {
        if (!mBackgroundThread.isAlive()) {
            return true;
        }
        mBackgroundHandler.removeCallbacksAndMessages(null);
        return true;
    }
}
