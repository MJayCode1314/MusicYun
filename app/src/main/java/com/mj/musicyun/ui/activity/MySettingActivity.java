package com.mj.musicyun.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mj.musicyun.R;
import com.mj.musicyun.ui.fragment.MySettingsFragment;

public class MySettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container_view, new MySettingsFragment())
                .commit();
    }
}