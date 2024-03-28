package com.mj.musicyun.model.data.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.mj.musicyun.model.data.dao.SongDao;
import com.mj.musicyun.model.data.dao.UserDao;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.model.data.entity.User;

@Database(entities = {Song.class, User.class}
        , version = 1,
        exportSchema = false
//        autoMigrations = {
//                @AutoMigration(from = 1, to = 2)
//        }
)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract SongDao songDao();
    public abstract UserDao userDao();
    private static final String database_name="database-song";
    private static volatile MyDatabase myDatabase;

    public static MyDatabase getInstance(Context context){
        if (myDatabase==null){
            synchronized (MyDatabase.class){
                if (myDatabase==null){
                    myDatabase= Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, database_name).build();
                }
            }
        }
        return myDatabase;
    }

}
