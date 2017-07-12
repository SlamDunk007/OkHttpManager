package com.guannan.network.callback;

import com.guannan.network.bean.ErrorResponse;

import okhttp3.Response;

/**
 * Created by guannan on 2017/7/6.
 */

public abstract class ResultCallback<T> {

    public boolean mRunOnMainOnThread = true;

    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(ErrorResponse errorResponse);

    public abstract void onSuccess(T response);
}
