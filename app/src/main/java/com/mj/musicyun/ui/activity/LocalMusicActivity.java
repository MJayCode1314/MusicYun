package com.mj.musicyun.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mj.musicyun.R;
import com.mj.musicyun.databinding.ActivityLocalMusicBinding;
import com.mj.musicyun.ui.fragment.LocalMusicFragment;

public class LocalMusicActivity extends AppCompatActivity {

    ActivityLocalMusicBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLocalMusicBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();

        setContentView(view);
        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_controller_view, LocalMusicFragment.class,null)
                    .commit();
        }
        init();
    }


    private void init(){
//        Intent intent=getIntent();
//        switch (intent.getStringExtra("fragment")){
//            case "fragment":
//            default:
//        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.music_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.navigation_recent:
//                // User chooses the "Settings" item. Show the app settings UI.
//                return true;
//
//            case R.id.navigation_favour:
//                // User chooses the "Favorite" action. Mark the current item as a
//                // favorite.
//                return true;
//            case R.id.navigation_local:
//
//            default:
//                // The user's action isn't recognized.
//                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

    }


}