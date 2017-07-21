package com.guannan.network.request;

import android.support.annotation.Nullable;

import com.guannan.network.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by guannan on 2017/7/6.
 */

public abstract class OkHttpRequest {

    private String url;
    private Object tag;
    private HashMap<String, String> headersMap;
    protected static final MediaType DEFAULT_MEDIATYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");

    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag, HashMap<String, String> headersMap) {

        this.url = url;
        this.tag = tag;
        this.headersMap = headersMap;
        if (url == null) {
            throw new IllegalArgumentException("url can't be null.");
        }
        initBuilder();
    }

    /**
     * 构造请求参数
     */
    private void initBuilder() {

        builder.url(url).tag(tag);
        appendHeaders();
    }

    /**
     * 添加请求头信息
     */
    private void appendHeaders() {

        if (headersMap != null && !headersMap.isEmpty()) {  //请求集合不为空，且大小不等于零

            Headers.Builder headerBuilder = new Headers.Builder();

            for (Map.Entry<String, String> entry : headersMap.entrySet()) {

                headerBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.headers(headerBuilder.build());
        }
    }

    protected abstract Request buildRequest(@Nullable RequestBody requestBody);

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapBody(RequestBody requestBody, ResultCallback resultCallback) {
        return requestBody;
    }

    /**
     * 获取包装好的request对象
     *
     * @param resultCallback
     * @return
     */
    public Request generateRequest(ResultCallback resultCallback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrapBody = wrapBody(requestBody, resultCallback);
        return buildRequest(wrapBody);
    }

    public RequestDelegate build() {
        return new RequestDelegate(this);
    }

}
