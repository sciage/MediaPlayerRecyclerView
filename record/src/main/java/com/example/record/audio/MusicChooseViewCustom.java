package com.example.record.audio;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;
import com.example.record.audio.MediaPlayerUtils;
import com.example.record.secondBridge.BaseSoundFile;
import com.example.record.secondBridge.SendListAudio;
import com.example.record.secondBridge.SoundFile;

import java.util.ArrayList;
import java.util.List;

public class MusicChooseViewCustom extends LinearLayout implements AudioInterface {
    private List<String> contactList = new ArrayList<>();
    public List<AudioStatus> audioStatusList = new ArrayList<>();
    private Parcelable state;
    RecyclerView recyclerView;
    private BaseSoundFile soundFile;

    public MusicChooseViewCustom(Context context) {
        super(context);
        init();
    }

    public MusicChooseViewCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MusicChooseViewCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_main, this, true);

        recyclerView = findViewById(R.id.recyclerViewContactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");

        for (int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }

        setRecyclerViewAdapter();
    }

    private void setRecyclerViewAdapter() {
        AudioListAdapter adapter = new AudioListAdapter(getContext());
        recyclerView.setAdapter(adapter);

        soundFile = new SoundFile(adapter, audioStatusList, contactList);
        soundFile.sendAudioList();
    }

    @Override
    public void onAudioCompleteFragment() {
        state = recyclerView.getLayoutManager().onSaveInstanceState();

        audioStatusList.clear();
        for (int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter();

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

        if (playingAudioPosition != -1) {
            AudioListAdapter.ViewHolder holder
                    = (AudioListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(playingAudioPosition);
            if (holder != null) {
                holder.seekBarAudio.setProgress(currentPosition);
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

}
