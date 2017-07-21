package com.guannan.network.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by guannan on 2017/7/21.
 */

public class ProgressRequestBody extends RequestBody {

    //实际的待包装请求体
    private RequestBody mRequestBody;
    //进度回调接口
    private ProgressRequestListener mRequestListener;
    //包装完成的CountingSink
    private CountingSink mCountingSink;

    public ProgressRequestBody(RequestBody requestBody, ProgressRequestListener requestListener) {

        this.mRequestBody = requestBody;
        this.mRequestListener = requestListener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return mRequestBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        mCountingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(mCountingSink);
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        //当前写入字节数
        long bytesWritten = 0L;
        //总字节长度，避免多次调用contentLength()方法
        long contentLength = 0L;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (contentLength == 0) {
                //获得contentLength的值，后续不再调用
                contentLength = contentLength();
            }
            bytesWritten += byteCount;
            mRequestListener.onRequestListener(bytesWritten, contentLength(), bytesWritten == contentLength);
        }

    }

    public interface ProgressRequestListener {

        public void onRequestListener(long currentLength, long contentLength, boolean done);
    }
}
