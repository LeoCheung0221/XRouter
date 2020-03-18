package com.tufusi.xrouter.dispatcher;

/**
 * Created by 鼠夏目 on 2020/3/16.
 *
 * @See
 * @Description
 */
public class PostingThreadDispatcher implements Dispatcher {

    @Override
    public void dispatch(Runnable runnable) {
        runnable.run();
    }

    @Override
    public boolean stop() {
        return true;
    }
}
