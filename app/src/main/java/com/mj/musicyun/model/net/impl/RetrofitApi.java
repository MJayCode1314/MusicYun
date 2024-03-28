package com.mj.musicyun.model.net.impl;


import com.mj.musicyun.model.data.entity.ShareContent;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.data.entity.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET("song/{song_id}")
    Call<Song> getSong(@Path("song_id") int id);

    @GET("/song/param")
    Call<Song> getSong1(@Query("song_id") int id);

    @GET("/song/search/{keyword}")
    Observable<List<Song>> searchByKey(@Path("keyword") String keyword);

    @GET("/song/all")
    Observable<List<Song>> getAllSong();

    /**
     * 查询分享
     * @return
     */
    @GET("/share/list")
    Observable<List<ShareContent>> getShareList();
    /**
     * 文件下载
     *
     * @param url
     * @return
     */
    @Streaming  //文件特别大的时候可以防止内存溢出
    @GET
    Call<ResponseBody> download(@Url String url);

    /**
     * RxJava方式进行文件下载
     * @param url
     * @return
     */
    @Streaming  //文件特别大的时候可以防止内存溢出
    @GET()
    Observable<ResponseBody> downloadRxJava(@Url String url);

    @GET
    Call<Integer> login(User user);

    /**
     * 发布
     * @return
     */
    @POST("/share/add")
    Call<Integer> shareAdd(@Body ShareContent shareContent);

}
