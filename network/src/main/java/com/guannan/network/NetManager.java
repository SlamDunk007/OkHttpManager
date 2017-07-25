package com.guannan.network;

import com.guannan.network.bean.RequestBean;
import com.guannan.network.callback.ResultCallback;

/**
 * Created by guannan on 2017/7/25.
 */

public class NetManager {

    private static volatile NetManager mNetManager = null;
    public static NetManager getInstance(){
        if(mNetManager == null){
            synchronized(NetManager.class){
                if(mNetManager == null){
                    mNetManager = new NetManager();
                }
            }
        }
        return mNetManager;
    }

    /**
     * get请求
     * @param requestBean
     * @param resultCallback
     */
    public void performGetRequest(RequestBean requestBean, ResultCallback resultCallback){

        OkHttpEngine.getInstance()
                .get()
                .url(requestBean.getCommonUrl())
                .build()
                .execute(resultCallback);
    }

    /**
     * post请求
     * @param requestBean
     * @param resultCallback
     */
    public void performPostRequest(RequestBean requestBean,ResultCallback resultCallback){

        OkHttpEngine.getInstance()
                .post()
                .url(requestBean.getCommonUrl())
                .params(requestBean.requestBody)
                .build()
                .execute(resultCallback);
    }

    /**
     * post表单上传参数和多文件
     * @param requestBean
     * @param resultCallback
     */
    public void performMultiFileRequest(RequestBean requestBean,ResultCallback resultCallback){

        OkHttpEngine.getInstance()
                .postForm()
                .url(requestBean.getCommonUrl())
                .contentParams(requestBean.requestBody)
                .fileParams(requestBean.mContentFiles)
                .build()
                .execute(resultCallback);
    }

    /**
     * post表单上传单文件
     */
    public void performFileRequest(RequestBean requestBean,ResultCallback resultCallback){

        OkHttpEngine.getInstance()
                .postFile()
                .url(requestBean.getCommonUrl())
                .fileParams(requestBean.mFile)
                .build()
                .execute(resultCallback);
    }

}
