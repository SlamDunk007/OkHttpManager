package com.guannan.network.request;

import android.support.annotation.Nullable;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by guannan on 2017/7/20.
 */

public class PostFormRequest extends OkHttpRequest {

    private Map<String, String> mContentParams;
    private Map<String, File> mFileParams;

    public PostFormRequest(String url, Object tag, HashMap<String, String> headersMap, Map<String, String> contentParams, Map<String, File> fileParams) {
        super(url, tag, headersMap);
        this.mContentParams = contentParams;
        this.mFileParams = fileParams;

    }

    @Override
    protected Request buildRequest(@Nullable RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (mContentParams != null && mContentParams.size() > 0) {

            for (Map.Entry<String, String> entry : mContentParams.entrySet()) {
                builder.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"" + entry.getKey() + "\""),
                        RequestBody.create(DEFAULT_MEDIATYPE, entry.getValue()));
            }
        }

        if (mFileParams != null && mFileParams.size() > 0) {

            for (Map.Entry<String, File> entry : mFileParams.entrySet()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse(getMimeType(entry.getValue())), entry.getValue());
                builder.addFormDataPart(entry.getKey(), entry.getValue().getName(), requestBody);
            }
        }
        return builder.build();
    }


    /**
     * 获取MimeType类型
     * @param file
     * @return
     */
    public static String getMimeType(File file) {
        if (file == null) {
            return null;
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtension(file));
    }

    /**
     * 获取文件的扩展名
     *
     * @param file
     * @return
     */
    private static String getExtension(final File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }
}
