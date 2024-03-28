package com.mj.musicyun.ui.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.mj.musicyun.R;

public class MySettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
