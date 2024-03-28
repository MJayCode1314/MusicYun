package com.mj.musicyun.model.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.mj.musicyun.R;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.net.MyRetrofit;
import com.mj.musicyun.model.net.impl.RetrofitApi;
import com.mj.musicyun.model.service.DownloadService;
import com.mj.musicyun.model.service.MediaCntrollerUtil;

import androidx.media3.session.MediaController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {

    public View view;

    private List<Song> list=new ArrayList<>();
    private String base_uri="http:192.168.1.198:8080";
    ListenableFuture<MediaController> controller= null;
    MediaController mediaController=null;

    public SongAdapter(List<Song> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list,parent,false);
        SongHolder holder=new SongHolder(view);
        controller=MediaCntrollerUtil.getInstance(view.getContext());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(),DownloadService.class);
                intent.putExtra("song_name",list.get(position).getSong_name());
                intent.putExtra("song_url",list.get(position).getSong_url());
                view.getContext().startService(intent);
            }
        });
        holder.getSinger().setText(list.get(position).getArtist());
        holder.getSong_name().setText(list.get(position).getSong_name());
        controller.addListener(() -> {
            mediaController = MediaCntrollerUtil.getController();
            assert mediaController != null;
            if (controller == null) return;
            holder.getImageButton().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    playItem(mediaController,position);
                    return true;
                }
            });
        }, MoreExecutors.directExecutor());
    }

    public void playItem(MediaController mediaController,int position){
        mediaController.setMediaItems(addMedia(list));
        if (mediaController.getCurrentMediaItemIndex()==position){
            if (mediaController.isPlaying()){

            }else {
                mediaController.play();
            }
        }else {
            mediaController.seekToDefaultPosition(position);
            mediaController.prepare();
            mediaController.play();
        }
        Log.d("mediacontroller", "准备播放");
    }

    public List<MediaItem> addMedia(List<Song> list){
        List<MediaItem> songs=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Uri uri= Uri.parse(base_uri+list.get(i).getSong_url());
            MediaItem mediaItem=new MediaItem.Builder()
                    .setUri(uri)
                    .setMediaMetadata(
                            new MediaMetadata.Builder()
                                    .setArtist(list.get(i).getArtist())
                                    .setTitle(list.get(i).getSong_name())
                                    .build()
                    ).build();
            songs.add(mediaItem);
        }
        return songs;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder{
        private TextView song_name;

        private TextView singer;

        private ImageButton imageButton;
        private Button down;
        public SongHolder(@NonNull View itemView) {
            super(itemView);
            song_name=itemView.findViewById(R.id.song_name);
            singer=itemView.findViewById(R.id.singer);
            imageButton=itemView.findViewById(R.id.btn_play);
            down=itemView.findViewById(R.id.btn_down);
        }

        public Button getDown() {
            return down;
        }

        public void setDown(Button down) {
            this.down = down;
        }

        @Override
        public String toString() {
            return super.toString();
        }

        public TextView getSong_name() {
            return song_name;
        }

        public void setSong_name(TextView song_name) {
            this.song_name = song_name;
        }

        public TextView getSinger() {
            return singer;
        }

        public void setSinger(TextView singer) {
            this.singer = singer;
        }

        public ImageButton getImageButton() {
            return imageButton;
        }

        public void setImageButton(ImageButton imageButton) {
            this.imageButton = imageButton;
        }
    }
}
