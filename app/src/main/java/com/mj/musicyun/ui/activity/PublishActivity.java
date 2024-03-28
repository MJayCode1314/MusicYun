package com.mj.musicyun.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mj.musicyun.R;
import com.mj.musicyun.databinding.ActivityPublishBinding;
import com.mj.musicyun.model.data.entity.ShareContent;
import com.mj.musicyun.model.net.MyRetrofit;
import com.mj.musicyun.model.net.impl.RetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PublishActivity extends AppCompatActivity {

    private ActivityPublishBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPublishBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);
        init();
    }

    void init(){
        binding.publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publish();
            }
        });
    }

    void publish(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               Retrofit retrofit= MyRetrofit.getInstance();
               RetrofitApi api=retrofit.create(RetrofitApi.class);
               String name="kawhi";
               ShareContent shareContent=
                       new ShareContent(name,binding.shareContent.getText().toString(),"浮夸-陈奕迅");
               Call<Integer> call= api.shareAdd(shareContent);
               Log.d("share",shareContent.toString());
               call.enqueue(new Callback() {
                   @Override
                   public void onResponse(Call call, Response response) {
                       Log.d("retrofit","发布中");
                       if (response.isSuccessful()){
                           Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                           onBackPressed();
                       }
                   }

                   @Override
                   public void onFailure(Call call, Throwable t) {

                   }
               });
           }
       }).start();
    }
}