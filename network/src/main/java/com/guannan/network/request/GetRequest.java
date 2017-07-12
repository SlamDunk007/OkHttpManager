package com.guannan.network.request;

import android.support.annotation.Nullable;

import java.util.HashMap;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by guannan on 2017/7/6.
 */

public class GetRequest extends OkHttpRequest {

    public GetRequest(String url, Object tag, HashMap<String, String> headersMap) {
        super(url, tag, headersMap);
    }

    @Override
    protected Request buildRequest(@Nullable RequestBody requestBody) {
        return builder.get().build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }
}
