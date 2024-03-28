package com.mj.musicyun.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.mj.musicyun.databinding.ActivitySearchResultActvityBinding;
import com.mj.musicyun.model.data.dao.SongDao;
import com.mj.musicyun.model.data.database.MyDatabase;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.service.MediaCntrollerUtil;
import com.mj.musicyun.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResultActvity extends AppCompatActivity {

    private ActivitySearchResultActvityBinding binding;
    private SearchViewModel viewModel;
    private ListenableFuture<MediaController> controllerFuture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchResultActvityBinding.inflate(getLayoutInflater());
        viewModel=new  ViewModelProvider(this).get(SearchViewModel.class);
        setContentView(binding.getRoot());
        init();
    }

    void init(){
        Intent intent=getIntent();
        intent.getAction();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("search", query);
            RecyclerView recyclerView = binding.searchList;
            viewModel.searchByKey(recyclerView, query);
        }

        binding.player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchResultActvity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });
        binding.playerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        controllerFuture= MediaCntrollerUtil.getInstance(this);
        controllerFuture.addListener(()->{
            MediaController controller=MediaCntrollerUtil.getController();
            assert controller!=null;
            binding.songName.setText(controller.getCurrentMediaItem().mediaMetadata.title);
            SeekBar seekBar= binding.playProcess;
            // 监听进度条手动更改
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        controller.seekTo(progress * controller.getDuration() / 100);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // 可以在这里暂停播放
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // 可以在这里恢复播放
                }
            });
            controller.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    Player.Listener.super.onPlaybackStateChanged(playbackState);
                    binding.songName.setText(controller.getCurrentMediaItem().mediaMetadata.title);
                    updateSeekBar(seekBar,controller);
                }

                @Override
                public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
                    Player.Listener.super.onPositionDiscontinuity(oldPosition, newPosition, reason);
                    updateSeekBar(binding.playProcess,controller);
                }
            });

        }, MoreExecutors.directExecutor());

    }

    private void updateSeekBar(SeekBar seekBar,MediaController player) {
        seekBar.setProgress((int) (player.getCurrentPosition() * 100 / player.getDuration()));
        if (player.isPlaying()) {
            seekBar.postDelayed(() -> updateSeekBar(seekBar, player), 100);
        }
    }

    public void setRoom(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDatabase db = MyDatabase.getInstance(SearchResultActvity.this);
                SongDao songDao = db.songDao();
                Date date=new Date();
                Song song=new Song(2,"ahdhd","成 ","skdj","sdk",date);
                if (songDao.getById(song.getSong_id())==null){
                    songDao.insert(song);
                }else {
                    songDao.updateSong(song);
                }
                List<Song> list = new ArrayList<>();
                list=songDao.getAll();
                Log.d("Room",list.get(0).toString());
//                if (list!=null){
//                    List<Song> finalList = list;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("Room",finalList.get(0).toString());
//                            binding.tvSearchResult.setText(finalList.get(0).toString());
//                        }
//                    });
//                }else
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            binding.tvSearchResult.setText("Room无法使用");
//                        }
//                    });

            }
        }).start();
    }
}