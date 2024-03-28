package com.mj.musicyun.model.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.session.MediaController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.mj.musicyun.R;
import com.mj.musicyun.model.service.MediaCntrollerUtil;

import java.util.ArrayList;
import java.util.List;

public class LocalListAdapter extends RecyclerView.Adapter<LocalListAdapter.LocalMusicHolder> {

    private List<MediaItem> list=new ArrayList<>();

    public LocalListAdapter(List<MediaItem> list) {
        this.list = list;
    }

    ListenableFuture<MediaController> controller= null;
    @NonNull
    @Override
    public LocalMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.local_music_list,parent,false);
        LocalMusicHolder holder=new LocalMusicHolder(view);
        controller=MediaCntrollerUtil.getInstance(view.getContext());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalMusicHolder holder, int position) {
        holder.getArtist().setText(list.get(position).mediaMetadata.artist);
        holder.getSong_name().setText(list.get(position).mediaMetadata.title);
        controller.addListener(() -> {
            MediaController mediaController = MediaCntrollerUtil.getController();
            assert mediaController != null;
            holder.getLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playItem(mediaController,position);

                }
            });
        }, MoreExecutors.directExecutor());

    }

    public void playItem(MediaController mediaController,int position){
        mediaController.setMediaItems(list);
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LocalMusicHolder extends RecyclerView.ViewHolder{

        private TextView song_name;
        private TextView artist;
        private LinearLayout layout;

        public TextView getSong_name() {
            return song_name;
        }

        public void setSong_name(TextView song_name) {
            this.song_name = song_name;
        }

        public TextView getArtist() {
            return artist;
        }

        public void setArtist(TextView artist) {
            this.artist = artist;
        }

        public LinearLayout getLayout() {
            return layout;
        }

        public void setLayout(LinearLayout layout) {
            this.layout = layout;
        }

        public LocalMusicHolder(@NonNull View itemView) {
            super(itemView);
            song_name=itemView.findViewById(R.id.song_name);
            artist=itemView.findViewById(R.id.artist);
            layout=itemView.findViewById(R.id.local_linear);
        }
    }
}
