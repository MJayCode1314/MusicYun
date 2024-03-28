package com.mj.musicyun.model.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private volatile static Retrofit retroft;
    private static String base_url="http:192.168.1.198:8090/";

    public static Retrofit getInstance(){
        if (retroft==null){
            synchronized (MyRetrofit.class){
                if (retroft==null){
                    retroft= new Retrofit.Builder()
                            .baseUrl(base_url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();

                }
            }
        }
        return retroft;
    }
}
