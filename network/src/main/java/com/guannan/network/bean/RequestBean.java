package com.guannan.network.bean;

import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guannan on 2017/7/25.
 */

public class RequestBean {

    public HashMap<String,String> headers;

    /**
     * post或get时的url上的参数，一般用作get请求
     */
    public HashMap<String,String> urlParams;

    /**
     * post时的请求参数
     */
    public HashMap<String,String> requestBody;

    /**
     * 上传单个文件
     */
    public File mFile;

    /**
     * 表单上传多文件
     */
    public Map<String,File> mContentFiles;

    /**
     * 请求地址
     */
    public String url;

    public String tag;

    /**
     * 文件下载路径
     */
    public String mDesFileDir;

    /**
     * 文件下载的名称
     */
    public String mDesFileName;


    /**
     * 拼接get请求url的参数
     * @return
     */
    public String getCommonUrl(){

        if(urlParams!=null && urlParams.size()>0){

            Map<String, String> tempMap = new HashMap<>();
            tempMap = urlParams;    //避免concurrentMixedException
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if(!url.endsWith("?")){
                sb.append("?");
            }
            try {
                for (Map.Entry<String, String> entry : tempMap.entrySet()) {
                    if(entry.getKey()==null || entry.getValue()==null){
                        continue;
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), "utf-8")).append("=").append(URLEncoder.encode(entry.getValue(),"utf-8")).append("&");
                }
                return sb.toString().substring(0,sb.length()-1);
            } catch (UnsupportedEncodingException e) {
                Log.e("RequestBean",
                        "RequestBean(RequestBean.java:74)"+e.toString());
            }
        }else{
            return url;
        }
        return null;
    }

}
