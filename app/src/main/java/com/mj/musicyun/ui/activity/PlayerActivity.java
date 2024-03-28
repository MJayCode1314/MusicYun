package com.mj.musicyun.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.mj.musicyun.R;
import com.mj.musicyun.databinding.ActivityPlayerBinding;
import com.mj.musicyun.model.service.MediaCntrollerUtil;
import com.mj.musicyun.viewmodel.PlayerViewModel;


import java.util.Locale;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private PlayerViewModel viewModel;
    private ListenableFuture<MediaController> controller;

    private ImageButton pre_btn;
    private ImageButton play_btn;
    private ImageButton next_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPlayerBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        viewModel= new ViewModelProvider(this).get(PlayerViewModel.class);
        // 隐藏Toolbar
        getSupportActionBar().hide();
        // 显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 设置返回按钮图标
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_community);
        init();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // 返回上一个界面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        next_btn=binding.nextPlay;
        pre_btn=binding.prePlay;
        play_btn=binding.pausePlay;
        controller= MediaCntrollerUtil.getInstance(this);
        controller.addListener(() -> {
            MediaController mediaController = MediaCntrollerUtil.getController();
            assert controller!=null;
            SeekBar seekBar=binding.songSeekbar;
            binding.playingName.setText(mediaController.getCurrentMediaItem().mediaMetadata.title);
            binding.repeatModel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaController.getRepeatMode()==Player.REPEAT_MODE_OFF||mediaController.getRepeatMode()==Player.REPEAT_MODE_ALL){
                        //随机

                        if (mediaController.getShuffleModeEnabled()) {
                            mediaController.setShuffleModeEnabled(false);
                            //重复
                            binding.repeatModel.setImageResource(R.drawable.ic_repeat_mode_one);
                            mediaController.setRepeatMode(Player.REPEAT_MODE_ONE);
                        }else {
                            mediaController.setShuffleModeEnabled(true);
                            binding.repeatModel.setImageResource(R.drawable.ic_repeat_mode_shuffle);
                        }
                    }else{
                        binding.repeatModel.setImageResource(R.drawable.ic_repeat_sequential);
                        mediaController.setRepeatMode(Player.REPEAT_MODE_ALL);
                    }
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b){
                        mediaController.seekTo(i * mediaController.getDuration() / 100);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mediaController.pause();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaController.play();
                }
            });
            //播放
            play_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaController.isPlaying()){
                        mediaController.pause();
                        play_btn.setImageResource(R.drawable.ic_play_play);
                    }else {
                        if (mediaController.getCurrentMediaItem()!=null){
                            mediaController.play();
                            play_btn.setImageResource(R.drawable.ic_puase_play);
                        }

                    }
                }
            });
            //上一曲
            pre_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaController.previous();
                }
            });
            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaController.next();
                }
            });
            //下一曲
            updateSeekBar(seekBar,mediaController);
            mediaController.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int playbackState) {
                        Player.Listener.super.onPlaybackStateChanged(playbackState);
                        binding.playingName.setText(mediaController.getCurrentMediaItem().mediaMetadata.title);
                        updateSeekBar(binding.songSeekbar,mediaController);
                    }

                    @Override
                    public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                        Player.Listener.super.onMediaItemTransition(mediaItem, reason);
                        if (!mediaController.hasNextMediaItem()){
                            mediaController.setMediaItem(mediaController.getMediaItemAt(0));
                        }
                    }

                    @Override
                    public void onIsPlayingChanged(boolean isPlaying) {
                        Player.Listener.super.onIsPlayingChanged(isPlaying);
                        binding.playingName.setText(mediaController.getCurrentMediaItem().mediaMetadata.title);
                            if (mediaController.isPlaying()){
                                play_btn.setImageResource(R.drawable.ic_puase_play);
                            }else {
                                    play_btn.setImageResource(R.drawable.ic_play_play);
                            }
                    }

                    @Override
                    public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
                        Player.Listener.super.onPositionDiscontinuity(oldPosition, newPosition, reason);
                        updateSeekBar(binding.songSeekbar,mediaController);
                    }
                });


        }, MoreExecutors.directExecutor());
    }
    //格式化时间
    private String formatTime(long millis) {
        long seconds = millis / 1000; // 将毫秒转换为秒
        long minutes = seconds / 60; // 计算总分钟数
        seconds = seconds % 60; // 计算剩余秒数
        return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
    }
    private void updateSeekBar(SeekBar seekBar,MediaController player) {
        long currentDuration=player.getCurrentPosition();
        String current=formatTime(currentDuration);
        binding.currentTime.setText(current);
        long maxDuration=player.getDuration();
        String max=formatTime(maxDuration);
        binding.maxTime.setText(max);
        seekBar.setProgress((int) (player.getCurrentPosition() * 100 / player.getDuration()));
        if (player.isPlaying()) {
            seekBar.postDelayed(() -> updateSeekBar(seekBar, player), 100);
        }
    }
}