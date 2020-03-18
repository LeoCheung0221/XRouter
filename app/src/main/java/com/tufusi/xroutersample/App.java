package com.tufusi.xroutersample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by 鼠夏目 on 2020/3/16.
 *
 * @See
 * @Description
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
