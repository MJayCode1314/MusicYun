package com.mj.musicyun.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mj.musicyun.model.adapter.ShareListAdapter;
import com.mj.musicyun.model.adapter.SongAdapter;
import com.mj.musicyun.model.data.entity.ShareContent;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.net.MyRetrofit;
import com.mj.musicyun.model.net.impl.RetrofitApi;
import com.mj.musicyun.ui.online.OnlineFragment;
import com.mj.musicyun.ui.share.ShareFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ShareViewModel extends ViewModel {
    private Retrofit retrofit=MyRetrofit.getInstance();
    private RetrofitApi api=retrofit.create(RetrofitApi.class);
    private MutableLiveData<List<ShareContent>> listMutableLiveData=new MutableLiveData<>();
    String TAG="ShareViewModel";
    // TODO: Implement the ViewModel
    public LiveData<List<ShareContent>> getDataList() {
        return listMutableLiveData;
    }
    public void  getSongList(RecyclerView recyclerView){
//        List<Song> list=new ArrayList<>();
//        Date date = new Date();
//        list.add(new Song(1, "sda", "sdd", "sdsdd", "sdda", date));
        Observable<List<ShareContent>> observable=api.getShareList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ShareContent>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(List<ShareContent> shareContents) {
                        ShareListAdapter adapter=new ShareListAdapter(shareContents);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ShareFragment.newInstance().getContext()));
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