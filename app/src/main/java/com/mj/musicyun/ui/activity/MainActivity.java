package com.mj.musicyun.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.mj.musicyun.R;
import com.mj.musicyun.databinding.ActivityMainBinding;
import com.mj.musicyun.model.service.MediaCntrollerUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListenableFuture<MediaController> controllerFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView =binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_online, R.id.navigation_share, R.id.navigation_mymusic)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        init();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
// 发布通知
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }

    public void init(){
        hideFrag();
        binding.player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });
        binding.playerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        controllerFuture=MediaCntrollerUtil.getInstance(this);
        controllerFuture.addListener(()->{
            MediaController controller=MediaCntrollerUtil.getController();
            assert controller!=null;
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
                   if (controller.getCurrentMediaItem()!=null){
                       if (controller.isPlaying()){
                           binding.songName.setText(controller.getCurrentMediaItem().mediaMetadata.title);
                       }
                   }
                   updateSeekBar(binding.playProcess,controller);
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

    void hideFrag(){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        Fragment fragment=manager.findFragmentById(R.id.frag_list);
        assert fragment != null;
        transaction.hide(fragment).commit();
    }
    void showFrag(){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        Fragment fragment=manager.findFragmentById(R.id.frag_list);
        assert fragment != null;
        transaction.show(fragment).commit();
    }

}