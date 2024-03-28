package com.mj.musicyun.model.service;


import android.content.ComponentName;
import android.content.Context;
import android.view.View;

import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

//mediacontroller连接媒体会话
public class MediaCntrollerUtil {
//    private volatile static MediaController controller;
    private volatile static ListenableFuture<MediaController> controllerFuture;

    private MediaCntrollerUtil() {
    }

    public static MediaController getController() {
        try {
            MediaController mediaController=controllerFuture.isDone() ? controllerFuture.get() : null;
            assert mediaController != null;
            mediaController.prepare();
            return mediaController;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ListenableFuture<MediaController> getInstance(Context context){
        if (controllerFuture==null){
            synchronized (MediaCntrollerUtil.class){
                if (controllerFuture==null){
                    SessionToken sessionToken =
                            new SessionToken(context, new ComponentName(context, PlaybackService.class));
                    controllerFuture =
                            new MediaController.Builder(context, sessionToken).buildAsync();
                }
            }
        }
        return controllerFuture;
    }
}
