package com.guannan.network.request;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by guannan on 2017/7/18.
 */

public class PostRequest extends OkHttpRequest {

    private static final MediaType DEFAULT_MEDIATYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    private String mContent;
    private MediaType mMediaType;

    public PostRequest(String url, Object tag, HashMap<String, String> headersMap, String content, MediaType mediaType) {
        super(url, tag, headersMap);
        if(TextUtils.isEmpty(content)){
            this.mContent = "";
        }else{
            this.mContent = content;
        }
        if(mediaType == null){
            mMediaType = DEFAULT_MEDIATYPE;
        }else{
            mMediaType = mediaType;
        }
    }

    @Override
    protected Request buildRequest(@Nullable RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {

        Log.e("PostRequest",
                "PostRequest(PostRequest.java:45)"+mContent);
        return RequestBody.create(mMediaType,mContent);
    }
}
