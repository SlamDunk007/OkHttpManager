package com.guannan.okhttpmanger;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.guannan.network.OkHttpEngine;
import com.guannan.network.bean.ErrorResponse;
import com.guannan.network.bean.ProgressModel;
import com.guannan.network.callback.FileCallback;
import com.guannan.network.callback.GenericCallback;
import com.guannan.network.callback.StringCallback;
import com.guannan.okhttpmanger.bean.Result;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String path = Environment.getExternalStorageDirectory().getPath()+"/james.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * get请求
     * @param view
     */
    public void get(View view){

        OkHttpEngine.getInstance()
                .get()
                .url("http://www.baidu.com")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(ErrorResponse errorResponse) {

                        Toast.makeText(MainActivity.this,"获取失败"+errorResponse.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String response) {

                        Toast.makeText(MainActivity.this,"获取成功"+response,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void post(View view){

        postData(new GenericCallback<Result>() {
            @Override
            public void onError(ErrorResponse errorResponse) {

                Toast.makeText(MainActivity.this,"获取失败"+errorResponse.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Result response) {

                Toast.makeText(MainActivity.this, response.getStatus()+response.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * post请求，上传字符串
     */
    public void postData(GenericCallback<Result> listener){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("content", "测试数据");
        OkHttpEngine.getInstance()
                .post()
                .url("http://dev.ggt.sina.com.cn/Suggest/add?ggtToken=1a2s3d4f5g6h")
                .params(paramsMap)
                .build()
                .execute(listener);
    }

    public void postFormFile(View view){

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("content", "测试数据");
        HashMap<String, File> fileMap = new HashMap<>();
        fileMap.put("file[]", new File(path));
        OkHttpEngine.getInstance()
                .postForm()
                .url("http://dev.ggt.sina.com.cn/Suggest/add?ggtToken=1a2s3d4f5g6h")
                .contentParams(paramsMap)
                .fileParams(fileMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(ErrorResponse errorResponse) {
                        String s = errorResponse.toString();
                    }

                    @Override
                    public void onSuccess(String response) {

                        Toast.makeText(MainActivity.this,"上传成功"+response.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseProgress(ProgressModel progressModel) {

                        Log.e("MainActivity",
                                "MainActivity(MainActivity.java:115)"+progressModel.getCurrentBytes()+Thread.currentThread().getName());
                    }
                });
    }

    /**
     * 上传单文件
     * @param view
     */
    public void postSingleFile(View view){

        OkHttpEngine.getInstance()
                .postFile()
                .url("https://api.ggt.sina.com.cn/Acount/uploadFaceImg?ggtToken=1a2b3c4d5e6f7g8h9i0j")
                .fileParams(new File(path))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(ErrorResponse errorResponse) {
                        Log.e("MainActivity",
                                "MainActivity(MainActivity.java:133)"+errorResponse.getErrorMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(MainActivity.this,"上传成功"+response.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseProgress(ProgressModel progressModel) {

                        Log.e("MainActivity",
                                "MainActivity(MainActivity.java:145)"+progressModel.getCurrentBytes()+"总共："+progressModel.getContentLength());
                    }
                });
    }

    /**
     * 文件下载
     * @param view
     */
    public void downLoad(View view){

        downLoadFile(new FileCallback() {
            @Override
            public void onError(ErrorResponse errorResponse) {

                Log.e("MainActivity",
                        "MainActivity(MainActivity.java:161)"+errorResponse.getErrorMessage()+" "+errorResponse.getErrorCode());
            }

            @Override
            public void onSuccess(File response) {
                Toast.makeText(MainActivity.this,"下载成功"+response.getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseProgress(ProgressModel progressModel) {

                Log.e("MainActivity",
                        "MainActivity(MainActivity.java:173)"+progressModel.toString());
            }
        });
    }

    public void downLoadFile(FileCallback fileCallback){

        fileCallback.destFileName = "jamesdownload.jpg";
        fileCallback.destFileDir = Environment.getExternalStorageDirectory().getPath();
        OkHttpEngine.getInstance()
                .get()
                .url("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E8%A9%B9%E5%A7%86%E6%96%AF&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=3666499916,2526913278&os=483387471,3051780284&pn=1&rn=1&di=129581410451&ln=1997&fr=&fmq=1500621930246_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=11&pi=0&gsm=0&objurl=http%3A%2F%2F01.imgmini.eastday.com%2Fmobile%2F20170707%2F20170707124011_7d795d891beaad905721828828e936cf_1.jpeg&rpstart=0&rpnum=0&adpicid=0&ctd=1500621938398^3_1903X947%1")
                .build()
                .execute(fileCallback);
    }
}
