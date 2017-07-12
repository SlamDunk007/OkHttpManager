package com.guannan.network.builder;

import com.guannan.network.request.GetRequest;
import com.guannan.network.request.RequestDelegate;

/**
 * Created by guannan on 2017/7/6.
 */

public class GetBuilder extends OkHttpRequestBuilder <GetBuilder> {
    @Override
    protected RequestDelegate build() {

        return new GetRequest(url, tag,headerMap).build();
    }

}
