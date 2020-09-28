package com.example.record.audio;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;

import java.util.ArrayList;
import java.util.List;

public class MusicChooseViewCustom02 extends LinearLayout implements AudioInterface, MediaPlayerUtils.Listener  {
    private List<String> audioList = new ArrayList<>();
    public List<AudioStatus> audioStatusList = new ArrayList<>();
    private Parcelable state;
    RecyclerView recyclerView;

    public MusicChooseViewCustom02(Context context) {
        super(context);
        init();
    }

    public MusicChooseViewCustom02(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MusicChooseViewCustom02(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.music_layout, this, true);

        recyclerView = findViewById(R.id.recyclerViewContactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        audioList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        audioList.add("http://34.66.8.61:8000/media/uploads/sounds/Punto_g_reggaeton.mp3");
        audioList.add("http://34.66.8.61:8000/media/uploads/sounds/Ocean_reggae.mp3");
        audioList.add("http://34.66.8.61:8000/media/uploads/sounds/Delincuente_reggae.mp3");
        audioList.add("http://34.66.8.61:8000/media/uploads/sounds/Nada_reggae.mp3");

        for (int i = 0; i < audioList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }

        setRecyclerViewAdapter(audioList);

    }

    private void setRecyclerViewAdapter(List<String> audioList) {
        AudioListAdapter adapter = new AudioListAdapter(getContext(), audioList, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onAudioCompleteFragment() {
        state = recyclerView.getLayoutManager().onSaveInstanceState();

        audioStatusList.clear();
        for (int i = 0; i < audioList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }

        setRecyclerViewAdapter(audioList);

        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

    @Override
    public void onAudioUpdateFragment(int currentPosition) {
        int playingAudioPosition = -1;
        for (int i = 0; i < audioStatusList.size(); i++) {
            AudioStatus audioStatus = audioStatusList.get(i);
            if (audioStatus.getAudioState() == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                playingAudioPosition = i;
                break;
            }
        }
    }

    @Override
    public void onPause() {
        state = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onResume() {
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

    @Override
    public void onDestroy() {
        MediaPlayerUtils.releaseMediaPlayer();
    }

    @Override
    public void onAudioComplete() {
        onAudioCompleteFragment();
    }

    @Override
    public void onAudioUpdate(int currentPosition) {
        onAudioUpdateFragment(currentPosition);
    }

    @Override
    public List<AudioStatus> updateList() {
        return audioStatusList;
    }
}
