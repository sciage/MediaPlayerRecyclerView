package com.example.record.audio;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivityDialog extends DialogFragment {

    private MusicChooseViewCustom musicChooseViewCustom;
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

    public void onAudioCompleteFragment() {
        musicChooseViewCustom.onAudioCompleteFragment();
    }

    public void onAudioUpdateFragment(int currentPosition) {
        musicChooseViewCustom.onAudioUpdateFragment(currentPosition);
    }
}