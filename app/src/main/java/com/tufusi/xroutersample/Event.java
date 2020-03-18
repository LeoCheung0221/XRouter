package com.tufusi.xroutersample;

/**
 * Created by 鼠夏目 on 2020/3/17.
 *
 * @See
 * @Description 事件总线接口
 */
public interface Event {

    interface MainView {

        void textPostThread(String posting);

        void textMainThread(String main);

        void textBackgroundThread(String background);

        void textAsyncThread(String async);
    }

    interface TestMultiReceivers {
        void testMulti(String msg);
    }

}
