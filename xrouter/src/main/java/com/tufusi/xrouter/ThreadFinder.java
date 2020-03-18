package com.tufusi.xrouter;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description
 */
public interface ThreadFinder {

    /**
     * 获取对应方法名的线程名
     */
    String getMethodThread(String methodName);
}
