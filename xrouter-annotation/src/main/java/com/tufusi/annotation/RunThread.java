package com.tufusi.annotation;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description 运行线程枚举类 包含：主线程、发布中线程、后台运行线程、异步线程
 */
public enum RunThread {

    /**
     * 主线程
     */
    MAIN,

    /**
     * 发布中线程
     */
    POSTING,

    /**
     * 后台运行线程
     */
    BACKGROUND,

    /**
     * 异步线程
     */
    ASYNC
}
