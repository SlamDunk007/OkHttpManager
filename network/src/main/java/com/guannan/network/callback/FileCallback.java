package com.guannan.network.callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by guannan on 2017/7/21.
 */

public abstract class FileCallback extends ResultCallback<File> {

    /**
     * 目标文件存储的文件夹路径
     */
    public String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    public String destFileName;

    @Override
    public File parseNetworkResponse(Response response) throws Exception {
        return saveFile(response);
    }

    public File saveFile(Response response) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[1024];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return file;
        } finally {
            try {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e) {}
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {}
        }
    }
}
