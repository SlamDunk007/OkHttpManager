package com.guannan.network.builder;

import com.guannan.network.request.PostFormRequest;
import com.guannan.network.request.RequestDelegate;

import java.io.File;
import java.util.Map;

/**
 * Created by guannan on 2017/7/20.
 * 以表单的形式上传文件，和表单内容
 * 支持多文件上传
 */

public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> {

    private Map<String, String> mContentParams;
    private Map<String, File> mFileParams;

    @Override
    public RequestDelegate build() {
        return new PostFormRequest(url, tag, headerMap, mContentParams, mFileParams).build();
    }

    /**
     * 表单内容
     *
     * @param contentParams
     * @return
     */
    public PostFormBuilder contentParams(Map<String, String> contentParams) {

        this.mContentParams = contentParams;
        return this;
    }

    /**
     * 上传的文件参数
     *
     * @param fileParams
     * @return
     */
    public PostFormBuilder fileParams(Map<String, File> fileParams) {

        this.mFileParams = fileParams;
        return this;
    }

}
