package com.guannan.network.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by guannan on 2017/7/18.
 * 将json串通过gson封装到具体的实体类中
 */

public abstract class GenericCallback<T> extends ResultCallback<T> {

    private Type mType = null;

    public GenericCallback() {

        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {

            ParameterizedType type = (ParameterizedType) superclass;
            Type[] actualTypeArguments = type.getActualTypeArguments();
            mType = actualTypeArguments[0];
        }
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {

        Gson gson = new Gson();
        byte[] bytes = response.body().bytes();
        String result = new String(bytes, DEFAULT_CHARSET);
        Object o = gson.fromJson(result, mType);
        return (T) o;
    }
}
