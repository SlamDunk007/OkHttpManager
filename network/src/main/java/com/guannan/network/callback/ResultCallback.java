package com.guannan.network.callback;

import com.guannan.network.bean.ErrorResponse;
import com.guannan.network.bean.ProgressModel;

import okhttp3.Response;

/**
 * Created by guannan on 2017/7/6.
 */

public abstract class ResultCallback<T> {

    public String DEFAULT_CHARSET = "UTF-8";

    public boolean mRunOnMainOnThread = true;

    /**
     * 当下载的文件的大小未知不确定的时候，contentLengt()会返回-1,所以我们在做进度显示的时候，要判断一下
     * 文件的总长度是否为-1，然后才可以进行进度的回显
     * @param progressModel
     */
    public void onResponseProgress(ProgressModel progressModel){

    }

    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(ErrorResponse errorResponse);

    public abstract void onSuccess(T response);
}
