package com.example.record.audio;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.record.R;
import com.example.record.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivityDialog extends DialogFragment {

    public MainActivityDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sound_main, container, false);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(),  getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


//        musicChooseViewCustom = new MusicChooseViewCustom(getContext());
//        return musicChooseViewCustom;
        return view;
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        musicChooseViewCustom.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        musicChooseViewCustom.onResume();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        musicChooseViewCustom.onDestroy();
//    }
//
//    public void onAudioCompleteFragment() {
//        musicChooseViewCustom.onAudioCompleteFragment();
//    }
//
//    public void onAudioUpdateFragment(int currentPosition) {
//        musicChooseViewCustom.onAudioUpdateFragment(currentPosition);
//    }
}