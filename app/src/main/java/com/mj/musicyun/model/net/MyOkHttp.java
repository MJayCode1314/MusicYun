package com.mj.musicyun.model.net;

import okhttp3.OkHttpClient;

public class MyOkHttp {
    //双重校验单例
    private static volatile OkHttpClient client;

    public MyOkHttp() {
    }

    public OkHttpClient getSingleClient(){
        if (client==null){
            synchronized (MyOkHttp.class){
                if (client==null){
                    client=new OkHttpClient();
                }
            }
        }
        return client;
    }
}
