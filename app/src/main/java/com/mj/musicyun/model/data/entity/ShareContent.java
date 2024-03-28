package com.mj.musicyun.model.data.entity;
public class ShareContent {
    private int share_id;
    private String share_content;
    private String share_song;
    private String share_user;
    public ShareContent( String share_user,String share_content, String share_song) {
        this.share_user=share_user;
        this.share_content = share_content;
        this.share_song = share_song;
    }

    public int getShare_id() {
        return share_id;
    }

    public String getShare_user() {
        return share_user;
    }

    public void setShare_user(String share_user) {
        this.share_user = share_user;
    }

    public void setShare_id(int share_id) {
        this.share_id = share_id;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getShare_song() {
        return share_song;
    }

    public void setShare_song(String share_song) {
        this.share_song = share_song;
    }
}
