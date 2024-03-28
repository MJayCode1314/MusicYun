package com.mj.musicyun.model.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mj.musicyun.model.data.entity.Song;

import java.util.List;

@Dao
public interface SongDao {

    @Query("select * from song")
    List<Song> getAll();
    @Query("select * from song where song_id=:id")
    Song getById(int id);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Song song);
    @Update
    void updateSong(Song song);


}
