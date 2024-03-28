package com.mj.musicyun.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.mj.musicyun.R;
import com.mj.musicyun.databinding.ActivityMessageBinding;
import com.mj.musicyun.model.adapter.MessageAdapter;
import com.mj.musicyun.model.data.entity.MessageContent;

import java.util.List;

public class MessageActivity extends AppCompatActivity {


    private ActivityMessageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMessageBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        init();
    }

    void init(){
        RecyclerView recyclerView=binding.messageList;
        List<MessageContent> list = null;
        MessageAdapter adapter=new MessageAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}