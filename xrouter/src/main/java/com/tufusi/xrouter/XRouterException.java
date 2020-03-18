package com.tufusi.xrouter;

/**
 * Created by 鼠夏目 on 2020/3/13.
 *
 * @See
 * @Description
 */
public class XRouterException extends RuntimeException {

    public XRouterException(String detailMessage) {
        super(detailMessage);
    }

    public XRouterException(Throwable throwable) {
        super(throwable);
    }

    public XRouterException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
