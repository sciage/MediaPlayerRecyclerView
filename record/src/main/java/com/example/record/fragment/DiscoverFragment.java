package com.example.record.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;
import com.example.record.audio.MediaPlayerUtils;
import com.example.record.audio.MusicChooseViewCustom;
import com.example.record.secondBridge.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoverFragment extends DialogFragment{
    private String pageName;

    private MusicChooseViewCustom musicChooseViewCustom;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static DiscoverFragment newInstance(String page) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SECTION_NUMBER, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            pageName = getArguments().getString(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        musicChooseViewCustom = new MusicChooseViewCustom(getContext());
        return musicChooseViewCustom;
    }

    @Override
    public void onPause() {
        super.onPause();
        musicChooseViewCustom.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        musicChooseViewCustom.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicChooseViewCustom.onDestroy();
    }

    @Override
    public void onDetach() {
        MediaPlayerUtils.releaseMediaPlayer();
        super.onDetach();
    }

}