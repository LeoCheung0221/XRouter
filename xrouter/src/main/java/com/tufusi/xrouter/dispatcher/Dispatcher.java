package com.tufusi.xrouter.dispatcher;

/**
 * Created by 鼠夏目 on 2020/3/16.
 *
 * @See
 * @Description 分发器实现接口
 */
public interface Dispatcher {
    /**
     * 分发
     *
     * @param runnable 线程
     */
    void dispatch(Runnable runnable);

    /**
     * 是否停止分发
     */
    boolean stop();
}
