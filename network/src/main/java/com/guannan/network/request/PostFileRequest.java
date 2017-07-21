package com.guannan.network.request;

import android.support.annotation.Nullable;

import com.guannan.network.MainExecutor;
import com.guannan.network.bean.ProgressModel;
import com.guannan.network.callback.ResultCallback;
import com.guannan.network.utils.ProgressRequestBody;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by guannan on 2017/7/21.
 */

public class PostFileRequest extends OkHttpRequest {

    private File mFile;
    private MediaType mMediaType;

    public PostFileRequest(String url, Object tag, HashMap<String, String> headersMap, File file, MediaType mediaType) {
        super(url, tag, headersMap);
        this.mFile = file;
        this.mMediaType = mediaType;
    }

    @Override
    protected Request buildRequest(@Nullable RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {

        return RequestBody.create(mMediaType, mFile);
    }

    @Override
    protected RequestBody wrapBody(RequestBody requestBody, final ResultCallback resultCallback) {

        if (resultCallback == null) {
            return requestBody;
        }

        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, new ProgressRequestBody.ProgressRequestListener() {
            @Override
            public void onRequestListener(final long currentLength, final long contentLength, final boolean done) {

                MainExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        //上传加载的进度是在UI线程
                        resultCallback.onResponseProgress(new ProgressModel(currentLength, contentLength, done));
                    }
                });
            }
        });
        return progressRequestBody;
    }
}
