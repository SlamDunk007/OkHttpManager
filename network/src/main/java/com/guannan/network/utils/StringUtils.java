package com.guannan.network.utils;

import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * Created by guannan on 2017/7/21.
 */

public class StringUtils {

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
