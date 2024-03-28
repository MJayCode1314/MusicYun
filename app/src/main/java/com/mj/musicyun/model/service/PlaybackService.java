package com.mj.musicyun.model.service;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaSessionService;

public class PlaybackService extends MediaSessionService {
    private MediaSession mediaSession = null;

    // Create your Player and MediaSession in the onCreate lifecycle event
    @Override
    public void onCreate() {
        super.onCreate();

        ExoPlayer player = new ExoPlayer.Builder(this).build();
//        MediaItem mediaItem = new MediaItem.Builder().setMediaId("music1").setMimeType(MimeTypes.AUDIO_MPEG)
//                .setUri(Uri.parse("http:192.168.1.200:8080/music/美人鱼-林俊杰.mp3")).build();
//// Set the media item to be played.
//        player.setMediaItem(mediaItem);
//        MediaItem mediaItem =
//                new MediaItem.Builder()
//                        .setMediaId("media-1")
//                        .setUri("http:192.168.1.200:8080/music/美人鱼-林俊杰.mp3")
//                        .setMediaMetadata(
//                                new MediaMetadata.Builder()
//                                        .setArtist("林俊杰")
//                                        .setTitle("美人鱼")
//                                        .build())
//                        .build();
//        player.setMediaItem(mediaItem);
//        MediaItem mediaItem1 =
//                new MediaItem.Builder()
//                        .setMediaId("media-1")
//                        .setUri("http:192.168.1.200:8080/music/浮夸-陈奕迅.flac")
//                        .setMediaMetadata(
//                                new MediaMetadata.Builder()
//                                        .setArtist("陈奕迅")
//                                        .setTitle("浮夸")
//                                        .build())
//                        .build();
//        player.addMediaItem(mediaItem1);
        mediaSession = new MediaSession.Builder(this, player).build();

    }

    @Nullable
    @Override
    public MediaSession onGetSession(MediaSession.ControllerInfo controllerInfo) {
        return mediaSession;
    }

    // The user dismissed the app from the recent tasks
    @Override
    public void onTaskRemoved(@Nullable Intent rootIntent) {
        Player player = mediaSession.getPlayer();
        if (!player.getPlayWhenReady() || player.getMediaItemCount() == 0) {
            // Stop the service if not playing, continue playing in the background
            // otherwise.
            stopSelf();
        }
    }

    // Remember to release the player and media session in onDestroy
    @Override
    public void onDestroy() {
        mediaSession.getPlayer().release();
        mediaSession.release();
        mediaSession = null;
        super.onDestroy();
    }
}
