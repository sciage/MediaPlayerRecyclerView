package com.example.record.newcode;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;
import com.example.record.audio.AudioListAdapter;
import com.example.record.audio.AudioStatus;
import com.example.record.audio.MediaPlayerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class AudioListFragment extends Fragment implements MediaPlayerUtils.Listener {

    private static final String TAG = "AudioListFragment";
    private List<String> audioList = new ArrayList<>();
    public List<AudioStatus> audioStatusList = new ArrayList<>();
    private Parcelable state;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_list, container, false);
        recyclerView = view.findViewById(R.id.rvAudio);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        audioList.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
        audioList.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3");
        audioList.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3");
        audioList.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3");

        for (int i = 0; i < audioList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter(audioList);

    }


    private void setRecyclerViewAdapter(List<String> audioList) {
        AudioListAdapter adapter = new AudioListAdapter(getActivity(), audioList, this);
        recyclerView.setAdapter(adapter);
    }

    public void onAudioCompleteFragment() {
        // Store its state
        state = recyclerView.getLayoutManager().onSaveInstanceState();

        audioStatusList.clear();
        for (int i = 0; i < audioList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter(audioList);

        // Main position of RecyclerView when loaded again
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

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
        super.onPause();
        // Store its state
        state = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Main position of RecyclerView when loaded again
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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