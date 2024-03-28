package com.mj.musicyun.model.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int user_id;
    @ColumnInfo
    private String user;
    @ColumnInfo
    private String password;

    public User() {
    }

    @Ignore
    public User( String user, String password) {
        this.user = user;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
