package com.guannan.okhttpmanger;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.guannan.network.OkHttpEngine;
import com.guannan.network.bean.ErrorResponse;
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

                        Toast.makeText(MainActivity.this,"上传成功"+response,Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
