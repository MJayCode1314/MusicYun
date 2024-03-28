package com.mj.musicyun.ui.online;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.mj.musicyun.R;
import com.mj.musicyun.databinding.FragmentOnlineBinding;
import com.mj.musicyun.model.adapter.SongAdapter;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.net.MyRetrofit;
import com.mj.musicyun.model.net.impl.RetrofitApi;
import com.mj.musicyun.ui.activity.SearchResultActvity;
import com.mj.musicyun.viewmodel.OnlineViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OnlineFragment extends Fragment {

    private OnlineViewModel mViewModel;
    private FragmentOnlineBinding binding;

    private static final String TAG = "Rxjava";

    public static OnlineFragment newInstance() {
        return new OnlineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(OnlineViewModel.class);
        binding=FragmentOnlineBinding.inflate(inflater,container,false);
        init();
        return binding.getRoot();
    }

    private void init(){
        binding.listNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getParentFragmentManager();
//                FragmentTransaction transaction=manager.beginTransaction()
//                        .replace(R.id.container, NewestListFragment.class,"newestList")
//                        .commit()

            }
        });
        binding.listHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        RecyclerView recyclerView=binding.listRecom;
        mViewModel.getSongList(recyclerView);
//        getSongList(recyclerView);

    }
//    public void  getSongList(RecyclerView recyclerView){
////        List<Song> list=new ArrayList<>();
////        Date date = new Date();
////        list.add(new Song(1, "sda", "sdd", "sdsdd", "sdda", date));
//        Retrofit retrofit= MyRetrofit.getInstance();
//        RetrofitApi api=retrofit.create(RetrofitApi.class);
//        Observable<List<Song>> observable=api.getAllSong();
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Song>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//
//                    @Override
//                    public void onNext(List<Song> songs) {
//                        // 步骤8：对返回的数据进行处理
//                        Log.d(TAG,songs.toString());
//                        if (songs != null) {
//                            SongAdapter adapter=new SongAdapter(songs);
//                            recyclerView.setAdapter(adapter);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "请求失败");
//                        Log.d("error",e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "请求成功");
//                    }
//                });
//
//
//    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        assert searchView != null;
        searchView.setQueryHint("请输入歌曲名");
        searchView.setSubmitButtonEnabled(true);
        SearchManager searchManager=(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName=new ComponentName(getContext(),SearchResultActvity.class);
        SearchableInfo searchableInfo=searchManager.getSearchableInfo(componentName);
        searchView.setSearchableInfo(searchableInfo);

        // 设置SearchView的监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Intent intent=new Intent(getActivity(), SearchResultActvity.class);
//                intent.putExtra("search",query);
//                // 处理搜索提交事件
//                startActivity(intent);
                Toast.makeText(getContext(),"搜索中",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 处理搜索内容改变事件
                return false;
            }
        });

    }




}