package com.guannan.network;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by guannan on 2017/7/7.
 */

public class MainExecutor implements Executor {

    private MainExecutor(){}

    private static class Holder{

        private static final MainExecutor mainExecutor = new MainExecutor();
    }

    public static MainExecutor getInstance(){
        return Holder.mainExecutor;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {

        mHandler.post(command);
    }
}
