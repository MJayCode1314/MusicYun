package com.mj.musicyun.model.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mj.musicyun.R;
import com.mj.musicyun.model.data.entity.SongList;

import java.util.ArrayList;
import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongHolder> {

    View view;

    private List<SongList> list=new ArrayList<>();

    public SongListAdapter(List<SongList> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.songlist_list, parent, false);
        SongHolder songHolder=new SongHolder(view);
        return songHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        SongList songList=list.get(position);
        holder.getTextView().setText(songList.getsList_name());
//        Glide.with(view).load(songList.getsList_img_url())
//                .into(holder.getImageView());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("list",list.get(position).toString());
                holder.layout.setBackgroundColor(R.color.teal_200);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class SongHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        private LinearLayout layout;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.album_img);
            textView=itemView.findViewById(R.id.list_name);
            layout=itemView.findViewById(R.id.linear);
        }

        public ImageView getImageView() {
            return imageView;
        }


        public TextView getTextView() {
            return textView;
        }


    }
}
