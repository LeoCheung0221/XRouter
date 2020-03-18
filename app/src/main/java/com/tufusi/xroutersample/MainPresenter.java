package com.tufusi.xroutersample;

import com.tufusi.xrouter.XRouter;

/**
 * Created by 鼠夏目 on 2020/3/17.
 *
 * @See
 * @Description
 */
public class MainPresenter {

    void testRunThread() {
        new Thread() {
            @Override
            public void run() {
                XRouter.instance().getReceiver(Event.MainView.class).textMainThread("main");
                XRouter.instance().getReceiver(Event.MainView.class).textPostThread("post");
                XRouter.instance().getReceiver(Event.MainView.class).textBackgroundThread("background");
                XRouter.instance().getReceiver(Event.MainView.class).textAsyncThread("async");
            }
        }.start();
    }

    void testMultiReceivers() {
        XRouter.instance().getReceiver(Event.TestMultiReceivers.class).testMulti("TestMultiReceiver");
    }

}
