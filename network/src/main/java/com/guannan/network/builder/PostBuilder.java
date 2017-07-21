package com.guannan.network.builder;

import android.text.TextUtils;

import com.guannan.network.request.PostRequest;
import com.guannan.network.request.RequestDelegate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by guannan on 2017/7/18.
 * post提交字符串
 */

public class PostBuilder extends OkHttpRequestBuilder<PostBuilder> {

    private String mContent;
    private Map<String, String> mParams;
    private MediaType mMediaType;

    @Override
    public RequestDelegate build() {
        getBodyContent();
        return new PostRequest(url, tag, headerMap, mContent, mMediaType).build();
    }

    public PostBuilder content(String content) {

        this.mContent = content;
        return this;
    }

    /**
     * 添加post请求参数集合
     *
     * @param params
     * @return
     */
    public PostBuilder params(HashMap<String, String> params) {

        this.mParams = params;
        return this;
    }

    /**
     * 添加post请求参数，以键值对的形式
     *
     * @param key
     * @param val
     * @return
     */
    public PostBuilder addParam(String key, String val) {
        if (this.mParams == null) {
            this.mParams = new LinkedHashMap<>();
        }
        this.mParams.put(key, val);
        return this;
    }

    /**
     * post上传的内容的类型
     *
     * @param mediaType
     * @return
     */
    public PostBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    /**
     * 拼接post请求的参数
     */
    private void getBodyContent() {
        try {
            if (mParams != null && mParams.size() > 0) {

                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : mParams.entrySet()) {

                    if (TextUtils.isEmpty(entry.getKey()) || TextUtils.isEmpty(entry.getValue())) {
                        continue;
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), PARAMS_ENCODING))
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), PARAMS_ENCODING))
                            .append("$");
                }
                String content = sb.toString().substring(0, sb.length() - 1);
                content(content);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
