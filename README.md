# OkHttpManager
对OkHttp进行的封装，现在支持以下请求：.

  - 支持get请求
  - 支持post请求
  - 支持post上传多文件和单文件以及进度回调
  - 支持文件下载以及进度回调

# 使用方法

### get请求
```sh
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
```

### post请求
```sh
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
```
### post上传多文件
```sh
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
```
