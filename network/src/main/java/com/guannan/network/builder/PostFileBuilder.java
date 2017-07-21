package com.guannan.network.builder;

import com.guannan.network.request.PostFileRequest;
import com.guannan.network.request.RequestDelegate;
import com.guannan.network.utils.StringUtils;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by guannan on 2017/7/21.
 * 上传单文件
 */

public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {

    private File mFile;
    private MediaType mMediaType;
    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    @Override
    public RequestDelegate build() {
        if (mMediaType == null) {
            mMediaType = MediaType.parse(StringUtils.getMimeType(mFile));
            if (mMediaType == null) {
                mMediaType = MEDIA_TYPE_STREAM;
            }
        }
        return new PostFileRequest(url,tag,headerMap,mFile,mMediaType).build();
    }

    /**
     * 要上传的文件
     * @param file
     * @return
     */
    public PostFileBuilder fileParams(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file can't be null");
        }
        this.mFile = file;
        return this;
    }

    /**
     * 要上传的文件的MIME类型
     * @param mediaType
     * @return
     */
    public PostFileBuilder mediaType(MediaType mediaType) {

        if (mediaType != null) {
            this.mMediaType = mediaType;
        }
        return this;
    }


}
