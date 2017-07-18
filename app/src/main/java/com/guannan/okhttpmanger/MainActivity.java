package com.guannan.okhttpmanger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.guannan.network.OkHttpEngine;
import com.guannan.network.bean.ErrorResponse;
import com.guannan.network.callback.StringCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getDataFromNet(View v){

        OkHttpEngine.getInstance()
                .get()
                .url("http://www.baidu.com")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(ErrorResponse errorResponse) {

                        String s = errorResponse.toString();
                    }

                    @Override
                    public void onSuccess(String response) {

                        String s = response.toString();
                    }
                });
    }
}
