package com.guannan.network.request;

import com.guannan.network.NetConfig;
import com.guannan.network.OkHttpEngine;
import com.guannan.network.callback.FileCallback;
import com.guannan.network.callback.ResultCallback;
import com.guannan.network.utils.ProgressResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guannan on 2017/7/6.
 */

public class RequestDelegate {

    private OkHttpRequest okHttpRequest;

    //读取时间
    private long mReadTimeOut;

    //写入时间
    private long mWriteTimeOut;

    //连接时间
    private long mConnectTimeOut;

    public RequestDelegate(OkHttpRequest okHttpRequest) {

        this.okHttpRequest = okHttpRequest;
    }

    public RequestDelegate readTimeOut(long readTimeOut) {
        this.mReadTimeOut = readTimeOut;
        return this;
    }

    public RequestDelegate writeTimeOut(long writeTimeOut) {
        this.mWriteTimeOut = writeTimeOut;
        return this;
    }

    public RequestDelegate connectTimeOut(long connectTimeOut) {
        this.mConnectTimeOut = connectTimeOut;
        return this;
    }

    /**
     * 获取client和call进行okhttp的执行和回调
     *
     * @param resultCallback
     */
    public void execute(ResultCallback resultCallback) {

        Call call = buildCallback(resultCallback);
        OkHttpEngine.getInstance().execute(call, resultCallback);

    }

    private Call buildCallback(final ResultCallback resultCallback) {

        Request request = okHttpRequest.generateRequest(resultCallback);
        OkHttpClient tempClient;
        if (mReadTimeOut > 0 || mWriteTimeOut > 0 || mConnectTimeOut > 0) {

            mReadTimeOut = mReadTimeOut > 0 ? mReadTimeOut : NetConfig.mReadTimeOut;
            mWriteTimeOut = mWriteTimeOut > 0 ? mWriteTimeOut : NetConfig.mWriteTimeOut;
            mConnectTimeOut = mConnectTimeOut > 0 ? mConnectTimeOut : NetConfig.mConnectTimeOut;
            tempClient = OkHttpEngine.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(mReadTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(mWriteTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(mConnectTimeOut, TimeUnit.MILLISECONDS)
                    .build();
        } else {
            tempClient = OkHttpEngine.getInstance().getOkHttpClient();
        }

        /**
         * 包装下载文件的响应体
         */
        if (resultCallback != null && resultCallback instanceof FileCallback) {

            tempClient = tempClient.newBuilder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Response response = chain.proceed(chain.request());
                    return response.newBuilder()
                            .body(new ProgressResponseBody(response.body(), resultCallback))    //此处的回调子线程当中
                            .build();
                }
            }).build();
        }
        return tempClient.newCall(request);
    }
}
