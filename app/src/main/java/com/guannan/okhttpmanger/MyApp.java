package com.guannan.okhttpmanger;

import android.app.Application;

import com.guannan.network.OkHttpEngine;

/**
 * Created by guannan on 2017/7/14.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpEngine.getInstance().init();
    }
}
