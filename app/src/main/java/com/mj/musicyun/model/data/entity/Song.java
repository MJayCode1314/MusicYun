package com.mj.musicyun.model.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "song")
public class Song {
    @PrimaryKey
    @ColumnInfo
    private int song_id;
    @ColumnInfo
    private String song_name;
    @ColumnInfo
    private String artist;
    @ColumnInfo
    private String song_url;
    @ColumnInfo
    private String album;
    @ColumnInfo
    private Date release_date;

    @Ignore
    public Song(int song_id, String song_name, String artist, String song_url, String album, Date release_date) {
        this.song_id = song_id;
        this.song_name = song_name;
        this.artist = artist;
        this.song_url = song_url;
        this.album = album;
        this.release_date = release_date;
    }

    public Song() {
    }


    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Song{" +
                "song_id=" + song_id +
                ", song_name='" + song_name + '\'' +
                ", artist='" + artist + '\'' +
                ", song_url='" + song_url + '\'' +
                ", album='" + album + '\'' +
                ", release_date=" + release_date +
                '}';
    }
}

