package com.example.record.audio;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.record.R;
import com.example.record.secondBridge.SendListAudio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> implements SendListAudio {

    private Context context;
    private List<String> contactList;
    private View view;
    private MediaPlayerUtils.Listener listener;

    private List<AudioStatus> audioStatuses;

    public AudioListAdapter(Context context, MediaPlayerUtils.Listener listener) {
        this.context = context;
        this.contactList = new ArrayList<>();
        this.audioStatuses = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String songPath = contactList.get(position);
        String songName = songPath.substring(songPath.lastIndexOf("/") + 1);
        holder.txtSongName.setText(songName);

        if(audioStatuses.get(position).getAudioState() != AudioStatus.AUDIO_STATE.IDLE.ordinal()) {
            holder.seekBarAudio.setMax(audioStatuses.get(position).getTotalDuration());
            holder.seekBarAudio.setProgress(audioStatuses.get(position).getCurrentValue());
            holder.seekBarAudio.setEnabled(true);
        } else {
            holder.seekBarAudio.setProgress(0);
            holder.seekBarAudio.setEnabled(false);
        }

        if(audioStatuses.get(position).getAudioState() == AudioStatus.AUDIO_STATE.IDLE.ordinal()
                || audioStatuses.get(position).getAudioState() == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
            holder.btnPlay.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_play, null));
        } else {
            holder.btnPlay.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_pause, null));
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void sendAudioList(List<AudioStatus> audio, List<String> contactList) {
        this.contactList = contactList;
        this.audioStatuses = audio;
         notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView btnPlay;
        SeekBar seekBarAudio;
        TextView txtSongName;

        ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            txtSongName = itemView.findViewById(R.id.txtSongName);
            seekBarAudio = itemView.findViewById(R.id.seekBarAudio);
            btnPlay = itemView.findViewById(R.id.btnPlay);

            seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser) MediaPlayerUtils.applySeekBarValue(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    // Check if any other audio is playing
                    if(audioStatuses.get(position).getAudioState()
                            == AudioStatus.AUDIO_STATE.IDLE.ordinal()) {
                        Log.d("MUSIC", "AUDIO Already playing Mediaplayer");

                        listener.onAudioComplete();
                    }

                    String audioPath = contactList.get(position);
                    AudioStatus audioStatus = audioStatuses.get(position);
                    int currentAudioState = audioStatus.getAudioState();

                    if(currentAudioState == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                        // If mediaPlayer is playing, pause mediaPlayer
                        Log.d("MUSIC", "AUDIO playing, pause Mediaplayer");

                        btnPlay.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_play, null));
                        MediaPlayerUtils.pauseMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PAUSED.ordinal());
                        audioStatuses.set(position, audioStatus);
                    } else if(currentAudioState == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
                        // If mediaPlayer is paused, play mediaPlayer
                        Log.d("MUSIC", "AUDIO  pause, play Mediaplayer");

                        btnPlay.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_pause, null));

                        MediaPlayerUtils.playMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        audioStatuses.set(position, audioStatus);
                    } else {
                        // If mediaPlayer is in idle state, start and play mediaPlayer
                        btnPlay.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_pause, null));
                        Log.d("MUSIC", "AUDIO  is idle, play Mediaplayer");

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        audioStatuses.set(position, audioStatus);

                        try {
                            MediaPlayerUtils.startAndPlayMediaPlayer(audioPath, listener);

                            audioStatus.setTotalDuration(MediaPlayerUtils.getTotalDuration());
                            audioStatuses.set(position, audioStatus);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
