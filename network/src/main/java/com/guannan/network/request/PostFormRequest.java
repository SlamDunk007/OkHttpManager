package com.guannan.network.request;

import android.support.annotation.Nullable;

import com.guannan.network.MainExecutor;
import com.guannan.network.bean.ProgressModel;
import com.guannan.network.callback.ResultCallback;
import com.guannan.network.utils.ProgressRequestBody;
import com.guannan.network.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by guannan on 2017/7/20.
 */

public class PostFormRequest extends OkHttpRequest {

    private Map<String, String> mContentParams;
    private Map<String, File> mFileParams;

    public PostFormRequest(String url, Object tag, HashMap<String, String> headersMap, Map<String, String> contentParams, Map<String, File> fileParams) {
        super(url, tag, headersMap);
        this.mContentParams = contentParams;
        this.mFileParams = fileParams;

    }

    @Override
    protected Request buildRequest(@Nullable RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (mContentParams != null && mContentParams.size() > 0) {

            for (Map.Entry<String, String> entry : mContentParams.entrySet()) {
                builder.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"" + entry.getKey() + "\""),
                        RequestBody.create(DEFAULT_MEDIATYPE, entry.getValue()));
            }
        }

        if (mFileParams != null && mFileParams.size() > 0) {

            for (Map.Entry<String, File> entry : mFileParams.entrySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse(StringUtils.getMimeType(entry.getValue())), entry.getValue());
                builder.addFormDataPart(entry.getKey(), entry.getValue().getName(), requestBody);
            }
        }
        return builder.build();
    }

    @Override
    protected RequestBody wrapBody(RequestBody requestBody, final ResultCallback resultCallback) {

        if(resultCallback == null){
            return requestBody;
        }
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, new ProgressRequestBody.ProgressRequestListener() {
            @Override
            public void onRequestListener(final long currentLength, final long contentLength, final boolean done) {

                MainExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        //上传文件的进度是在UI线程
                        resultCallback.onResponseProgress(new ProgressModel(currentLength,contentLength,done));
                    }
                });

            }
        });
        return progressRequestBody;
    }
}
