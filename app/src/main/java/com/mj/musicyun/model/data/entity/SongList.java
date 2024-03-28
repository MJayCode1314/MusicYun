package com.mj.musicyun.model.data.entity;

public class SongList {
    private String sList_name;
    private String sList_img_url;

    public SongList(String sList_name, String sList_img_url) {
        this.sList_name = sList_name;
        this.sList_img_url = sList_img_url;
    }

    public String getsList_name() {
        return sList_name;
    }

    public void setsList_name(String sList_name) {
        this.sList_name = sList_name;
    }

    public String getsList_img_url() {
        return sList_img_url;
    }

    public void setsList_img_url(String sList_img_url) {
        this.sList_img_url = sList_img_url;
    }
}
