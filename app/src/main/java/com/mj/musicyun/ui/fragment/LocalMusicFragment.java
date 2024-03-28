package com.mj.musicyun.ui.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.mj.musicyun.R;
import com.mj.musicyun.databinding.FragmentLocalMusicBinding;
import com.mj.musicyun.model.adapter.LocalListAdapter;
import com.mj.musicyun.model.data.entity.Song;
import com.mj.musicyun.ui.view.VerticalSeekBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalMusicFragment extends Fragment {
    private FragmentLocalMusicBinding binding;

    public LocalMusicFragment() {
        // Required empty public constructor
    }

    private List<MediaItem> list;

    // TODO: Rename and change types and number of parameters
    public static LocalMusicFragment newInstance() {
        LocalMusicFragment fragment = new LocalMusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=getMusicFiles(getContext());

    }
    public List<MediaItem> getMusicFiles(Context context) {
//        MediaScannerConnection.scanFile(context, new String[] { Environment.getExternalStorageDirectory().toString() }, null, new MediaScannerConnection.OnScanCompletedListener() {
//            public void onScanCompleted(String path, Uri uri) {
//                Log.d("ExternalStorage", "Scanned " + path + ":");
//                Log.d("ExternalStorage", "-> uri=" + uri);
//            }
//        });
        Log.d("local","检索本地文件");
        List<MediaItem> songs = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String title = cursor.getString(nameColumn);
                String artist = cursor.getString(artistColumn);
                Uri contentUri =  ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                Log.d("music_name",title);
                MediaItem mediaItem = new MediaItem.Builder()
                        .setUri(contentUri)
                        .setMediaId(String.valueOf(id))
                        .setMediaMetadata(new MediaMetadata.Builder()
                                .setArtist(artist)
                                .setTitle(title)
                                .build()
                        )
                        .build();
                songs.add(mediaItem);
                Log.d("Local", mediaItem.toString());
            }
            cursor.close();
        }
        Log.d("localmusic",songs.toString());
        return songs;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentLocalMusicBinding.inflate(getLayoutInflater(),container,false);
        View view=binding.getRoot();
        init();
        // Inflate the layout for this fragment
        return view;
    }

    public void  init(){
        RecyclerView recyclerView=binding.musicLocal;
//        recyclerView.setItemViewCacheSize(50);
        LocalListAdapter adapter=new LocalListAdapter(list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        binding.nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoveToPosition(layoutManager,recyclerView,layoutManager.findFirstVisibleItemPosition()+8);
            }
        });
        binding.prePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutManager.findFirstVisibleItemPosition()>=8){
                    MoveToPosition(layoutManager,recyclerView,layoutManager.findFirstVisibleItemPosition()-8);
                }

            }
        });
//        SeekBar seekBar=binding.seekbarList;
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (fromUser) {
//                    MoveToPosition(layoutManager,recyclerView,progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // 可以在这里暂停播放
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // 可以在这里恢复播放
//            }
//        });
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {


        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
//        int first=manager.findFirstVisibleItemPosition();
//        int end=manager.findLastVisibleItemPosition();
//        int current=(first+end)/2;
//        seekBar.setProgress(current);

    }

}