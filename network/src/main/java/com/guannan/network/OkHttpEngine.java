package com.guannan.network;

import com.guannan.network.bean.ErrorResponse;
import com.guannan.network.builder.GetBuilder;
import com.guannan.network.builder.PostBuilder;
import com.guannan.network.builder.PostFormBuilder;
import com.guannan.network.callback.ResultCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by guannan on 2017/7/3.
 */

public class OkHttpEngine {

    private OkHttpClient mOkHttpClient;

    private OkHttpEngine(){}

    private static class Holder{

        private static final OkHttpEngine sOkHttpEngine = new OkHttpEngine();
    }

    public static final OkHttpEngine getInstance(){
        return Holder.sOkHttpEngine;
    }

    public void init(){

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)  //设置连接超时时间
                .readTimeout(10000L, TimeUnit.MILLISECONDS)     //设置读取超时时间
                .writeTimeout(10000L,TimeUnit.MICROSECONDS)     //设置写超时时间
                .build();
    }

    public OkHttpClient getOkHttpClient(){

        return mOkHttpClient;
    }

    public void execute(Call call, final ResultCallback resultCallback) {

        if(call!=null){

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    if(resultCallback == null){
                        return;
                    }
                    if(call.isCanceled()){

                        handleErrorResult(new ErrorResponse(NetConfig.StatusCode.NET_CALLED,"result is cancelled",call),resultCallback);
                    }else{
                        handleErrorResult(new ErrorResponse(NetConfig.StatusCode.NET_ERROR,e.getMessage(),call),resultCallback);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if(resultCallback == null){
                        return;
                    }
                    if(call.isCanceled()){
                        handleErrorResult(new ErrorResponse(NetConfig.StatusCode.NET_CALLED,"result is cancelled",call),resultCallback);
                    }

                    if(response.isSuccessful()){

                        try {
                            Object o = resultCallback.parseNetworkResponse(response);
                            if(o == null){
                                throw new RuntimeException("parse error");
                            }else{
                                handleSuccessResult(o, resultCallback);
                            }
                        } catch (Exception e) {
                            handleErrorResult(new ErrorResponse(NetConfig.StatusCode.PARSE_ERROR,e.getMessage(),call),resultCallback);
                        } finally {
                            if(response!=null){
                                response.body().close();
                            }
                        }

                    }
                }
            });
        }

    }

    /**
     * 处理成功返回的数据处理
     * @param response
     * @param resultCallback
     */
    private void handleSuccessResult(final Object response, final ResultCallback resultCallback) {

        if(resultCallback.mRunOnMainOnThread){
            MainExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    resultCallback.onSuccess(response);
                }
            });
        }
    }

    /**
     * 请求异常情况处理
     * @param errorResponse
     * @param resultCallback
     */
    private void handleErrorResult(final ErrorResponse errorResponse, final ResultCallback resultCallback) {

        if(resultCallback.mRunOnMainOnThread){
            MainExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    resultCallback.onError(errorResponse);
                }
            });
        }
    }

    public GetBuilder get(){

        return new GetBuilder();
    }

    public PostBuilder post(){
        return new PostBuilder();
    }

    public PostFormBuilder postForm(){
        return new PostFormBuilder();
    }

}
