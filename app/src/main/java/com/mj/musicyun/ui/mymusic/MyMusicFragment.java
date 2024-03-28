package com.mj.musicyun.ui.mymusic;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mj.musicyun.R;
import com.mj.musicyun.databinding.FragmentMyMusicBinding;
import com.mj.musicyun.model.adapter.SongListAdapter;
import com.mj.musicyun.model.data.entity.SongList;
import com.mj.musicyun.ui.activity.LocalMusicActivity;
import com.mj.musicyun.ui.activity.MySettingActivity;
import com.mj.musicyun.ui.user.LoginActivity;
import com.mj.musicyun.viewmodel.MyMusicViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyMusicFragment extends Fragment {

    @Inject
    MyMusicViewModel mViewModel;
    private FragmentMyMusicBinding binding;
    private RecyclerView recyclerView;

    public static MyMusicFragment newInstance() {
        return new MyMusicFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.setting_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=new Intent(getContext(),MySettingActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=FragmentMyMusicBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        init();
        return view;
    }
     private void init(){
         recyclerView=binding.songList;
         List<SongList> list=new ArrayList<>();
         SongList songList=new SongList("歌单1","sdad");
         list.add(songList);
         SongList songList1=new SongList("歌单2","sdadadsd");
         list.add(songList1);
         SongList songList2=new SongList("歌单3","sdadadsd");
         list.add(songList2);
         SongListAdapter adapter=new SongListAdapter(list);
         recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         binding.local.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(getContext(), LocalMusicActivity.class);
                 startActivity(intent);
             }
         });
         binding.userHead.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(getContext(), LoginActivity.class);
                 startActivity(intent);
             }
         });
     }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyMusicViewModel.class);
        // TODO: Use the ViewModel
    }

}