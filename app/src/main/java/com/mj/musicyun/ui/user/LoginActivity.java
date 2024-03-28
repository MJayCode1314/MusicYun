package com.mj.musicyun.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mj.musicyun.databinding.ActivityLoginBinding;
import com.mj.musicyun.model.data.entity.User;
import com.mj.musicyun.model.net.MyRetrofit;
import com.mj.musicyun.model.net.impl.RetrofitApi;
import com.mj.musicyun.ui.activity.MainActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        init();
    }

    public void init(){
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=binding.user.getText().toString();
                String pass=binding.password.getText().toString();
                login(new User(user,pass));

            }
        });
    }

    boolean login(User user){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit=MyRetrofit.getInstance();
                RetrofitApi api=retrofit.create(RetrofitApi.class);
                Call<Integer> call=api.login(user);
                try {
                    Response<Integer> response= call.execute();
                    if (response.isSuccessful()){
                        int code= (int) response.body();
                        Log.d("login",String.valueOf(code));
                        if (code==0){
                            saveUser(user);
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{

                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
        return true;
    }
    void saveUser(User user){
//        MyDatabase database=MyDatabase.getInstance(this);
//        UserDao dao=database.userDao();
//        dao.add(user);
    }
}