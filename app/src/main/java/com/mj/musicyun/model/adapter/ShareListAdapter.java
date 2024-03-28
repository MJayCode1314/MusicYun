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

import com.mj.musicyun.R;
import com.mj.musicyun.model.data.entity.ShareContent;

import java.util.ArrayList;
import java.util.List;

public class ShareListAdapter extends RecyclerView.Adapter<ShareListAdapter.ShareHolder>{

    private List<ShareContent> list=new ArrayList<>();

    public ShareListAdapter(List<ShareContent> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.share_list,parent,false);
        ShareHolder holder=new ShareHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareHolder holder, int position) {
        Log.d("adapterList",list.toString());
        holder.getTitle().setText(list.get(position).getShare_content());
        holder.getSong_name().setText(list.get(position).getShare_song());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShareHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView img;
        private TextView song_name;
        private LinearLayout layout;
        public ShareHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.share_title);
            img=itemView.findViewById(R.id.share_album_img);
            song_name=itemView.findViewById(R.id.share_song_name);
            layout=itemView.findViewById(R.id.song_linear);
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }

        public TextView getSong_name() {
            return song_name;
        }

        public void setSong_name(TextView song_name) {
            this.song_name = song_name;
        }

        public LinearLayout getLayout() {
            return layout;
        }

        public void setLayout(LinearLayout layout) {
            this.layout = layout;
        }
    }
}
