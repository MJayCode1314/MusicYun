package com.mj.musicyun.model.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mj.musicyun.model.data.entity.MessageContent;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>{
    private List<MessageContent> list;

    public MessageAdapter(List<MessageContent> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MessageHolder extends RecyclerView.ViewHolder{
       public MessageHolder(@NonNull View itemView) {
           super(itemView);
       }

       @Override
       public String toString() {
           return super.toString();
       }
   }
}
