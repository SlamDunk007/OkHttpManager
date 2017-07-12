package com.guannan.network.callback;

import okhttp3.Response;

/**
 * Created by guannan on 2017/7/6.
 */

public abstract class StringCallback extends ResultCallback<String> {
    @Override
    public String parseNetworkResponse(Response response) throws Exception {

        byte[] bytes = response.body().bytes();
        String result = new String(bytes, "UTF-8");
        return result;
    }
}
