package com.guannan.network.builder;

import com.guannan.network.request.RequestDelegate;

import java.util.HashMap;

/**
 * Created by guannan on 2017/7/6.
 */

public abstract class OkHttpRequestBuilder <T extends OkHttpRequestBuilder>{

    protected String url;
    protected Object tag;
    protected HashMap<String,String> headerMap;

    public T url(String url){
        this.url = url;
        return (T) this;
    }

    public T tag(Object tag){

        this.tag = tag;
        return (T) this;
    }

    public T headers(HashMap<String,String> map){

        this.headerMap = map;
        return (T) this;
    }

    //拼接请求的参数等
    protected abstract RequestDelegate build();


}
