package com.mj.musicyun.model.service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mj.musicyun.R;
import com.mj.musicyun.model.net.impl.RetrofitApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadService extends Service {
    private String base_uri = "http:192.168.1.198:8080";

    public DownloadService() {
    }


    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "download";
            String description = "this is download service";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // 注册通知渠道
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String name = intent.getStringExtra("song_name");
        String url = intent.getStringExtra("song_url");
        download(url, this, name);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void download(String url, Context context, String name) {
        Toast.makeText(context, "正在下载" + name, Toast.LENGTH_LONG).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle("Download" + name)
                .setContentText("Downloading...")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true) // 设置为正在进行的通知，这样通知就不会被用户滑动删除
                .setProgress(100, 0, false); // 假设最大进度为100，初始进度为0，不使用模糊进度条

// 获取NotificationManager实例
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// 通知ID
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_uri)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitApi api = retrofit.create(RetrofitApi.class);
                Call<ResponseBody> call = api.download(url);
                try {
                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        if (response.isSuccessful()) {
                            Log.d("download", "开始下载");
                            assert response.body() != null;
                            InputStream inputStream = response.body().byteStream();
                            FileOutputStream fos = null;
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), name + ".flac");
//                           File file = new File(context.getExternalFilesDir(null).getAbsolutePath(),name+".flac");
                            if (file.exists()) {
                                Toast.makeText(getApplicationContext(), "此歌曲已下载", Toast.LENGTH_LONG).show();
                            } else {
                                Log.d("fileDir", context.getExternalFilesDir(null).toString());
                                fos = new FileOutputStream(file);
                                byte[] buffer = new byte[1024];
                                int len = 0;
                                long sum = 0;
                                while ((len = inputStream.read(buffer)) != -1) {
                                    fos.write(buffer, 0, len);
                                    builder.setProgress(100, (int) (sum /1000), false);
                                    notificationManager.notify(notificationId, builder.build());
                                    sum+=len;
                                }

                                fos.close();
                                inputStream.close();
                                Log.d("download", "下载完成");
                                //下载完成，关闭服务
                                builder.setContentText("Download complete")
                                        .setProgress(0,0,false) // 移除进度条
                                        .setOngoing(false); // 允许用户移除通知
                                notificationManager.notify(notificationId, builder.build());
                            }
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Toast.makeText(context,"下载结束"+name,Toast.LENGTH_LONG).show();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}