package com.mj.musicyun.ui.share;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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

import com.mj.musicyun.R;
import com.mj.musicyun.databinding.FragmentShareBinding;
import com.mj.musicyun.model.adapter.ShareListAdapter;
import com.mj.musicyun.model.data.entity.ShareContent;
import com.mj.musicyun.ui.activity.MessageActivity;
import com.mj.musicyun.ui.activity.PublishActivity;
import com.mj.musicyun.viewmodel.ShareViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment {

    private ShareViewModel mViewModel;

    private FragmentShareBinding binding;

    public static ShareFragment newInstance() {
        return new ShareFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.message){
            Intent intent=new Intent(getContext(), MessageActivity.class);
            startActivity(intent);
        }else {
            if (item.getItemId()==R.id.add){
            Intent intent=new Intent(getContext(), PublishActivity.class);
            startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.message_menu, menu);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=FragmentShareBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(ShareViewModel.class);
        init();
        return view;
    }
    public void init(){
//        List<ShareContent> list=new ArrayList<>();
//        mViewModel.getDataList().observe(getViewLifecycleOwner(),newData->{
//
//        });
        RecyclerView recyclerView=binding.shareList;
        Log.d("fragment","share");
       mViewModel.getSongList(recyclerView);
       binding.dxydRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
       binding.dxydRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
           @Override
           public void onRefresh(RefreshLayout refreshLayout) {
                mViewModel.getSongList(recyclerView);
                refreshLayout.finishLoadMore(1000);
                binding.dxydRefreshLayout.finishRefresh();
           }
       });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}