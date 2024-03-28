package com.mj.musicyun.viewmodel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mj.musicyun.model.adapter.SongAdapter;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.net.MyRetrofit;
import com.mj.musicyun.model.net.impl.RetrofitApi;
import com.mj.musicyun.ui.activity.MainActivity;
import com.mj.musicyun.ui.online.OnlineFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnlineViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private static final String TAG = "Rxjava";

    private Retrofit retrofit= MyRetrofit.getInstance();
    private RetrofitApi api=retrofit.create(RetrofitApi.class);

    public void getSongById(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit= MyRetrofit.getInstance();
                RetrofitApi api=retrofit.create(RetrofitApi.class);
                Call<Song> songCall=api.getSong1(1);
                //异步请求
                songCall.enqueue(new Callback<Song>() {
                    @Override
                    public void onResponse(Call<Song> call, Response<Song> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            Log.d("response", response.body().toString());
                            Song song = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<Song> call, Throwable t) {

                    }
                });
            }
        }).start();
    }

    public void  getSongList(RecyclerView recyclerView){
//        List<Song> list=new ArrayList<>();
//        Date date = new Date();
//        list.add(new Song(1, "sda", "sdd", "sdsdd", "sdda", date));
        Observable<List<Song>> observable=api.getAllSong();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Song>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(List<Song> songs) {
                        // 步骤8：对返回的数据进行处理
                        if (songs != null) {
                            SongAdapter adapter=new SongAdapter(songs);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(OnlineFragment.newInstance().getContext()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");
                    }
                });


    }



}