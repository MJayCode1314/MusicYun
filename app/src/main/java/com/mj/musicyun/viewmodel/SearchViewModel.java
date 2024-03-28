package com.mj.musicyun.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mj.musicyun.model.adapter.SongAdapter;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.net.MyRetrofit;
import com.mj.musicyun.model.net.impl.RetrofitApi;
import com.mj.musicyun.ui.activity.SearchResultActvity;
import com.mj.musicyun.ui.online.OnlineFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchViewModel extends ViewModel {
    private static final String TAG = "Rxjava_search";

    private Retrofit retrofit= MyRetrofit.getInstance();
    private RetrofitApi api=retrofit.create(RetrofitApi.class);



    public void  searchByKey(RecyclerView recyclerView, String key){
//        List<Song> list=new ArrayList<>();
//        Date date = new Date();
//        list.add(new Song(1, "sda", "sdd", "sdsdd", "sdda", date));
        Observable<List<Song>> observable=api.searchByKey(key);
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
                        Log.d(TAG,songs.toString());
                        if (songs != null) {
                            SongAdapter adapter=new SongAdapter(songs);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
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
