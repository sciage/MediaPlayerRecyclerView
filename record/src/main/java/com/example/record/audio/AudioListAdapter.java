package com.example.record.audio;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
//    private MainActivity mainActivity;
    private View view;
    private MediaPlayerUtils.Listener listener;

    private List<AudioStatus> audioStatuses;


    public AudioListAdapter(Context context, MediaPlayerUtils.Listener listener) {
        this.context = context;
        this.contactList = new ArrayList<>();
        this.audioStatuses = new ArrayList<>();
        this.listener = listener;
//        if (context instanceof MainActivity){
//        this.mainActivity = (MainActivity) context;
//          }
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
            holder.btnPlay.setText(view.getContext().getString(R.string.play));
        } else {
            holder.btnPlay.setText(view.getContext().getString(R.string.pause));
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

        Button btnPlay;
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
//                    boolean ifRequest = mainActivity.requestPermissionIfNeeded();
//                    if(ifRequest) return;

                    int position = getAdapterPosition();

                    // Check if any other audio is playing
                    if(audioStatuses.get(position).getAudioState()
                            == AudioStatus.AUDIO_STATE.IDLE.ordinal()) {

                        // Reset media player
//                        MediaPlayerUtils.Listener listener = (MediaPlayerUtils.Listener) context;
                        listener.onAudioComplete();
                    }

                    String audioPath = contactList.get(position);
                    AudioStatus audioStatus = audioStatuses.get(position);
                    int currentAudioState = audioStatus.getAudioState();

                    if(currentAudioState == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                        // If mediaPlayer is playing, pause mediaPlayer
                        btnPlay.setText(view.getContext().getString(R.string.play));
                        MediaPlayerUtils.pauseMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PAUSED.ordinal());
                        audioStatuses.set(position, audioStatus);
                    } else if(currentAudioState == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
                        // If mediaPlayer is paused, play mediaPlayer
                        btnPlay.setText(view.getContext().getString(R.string.pause));
                        MediaPlayerUtils.playMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        audioStatuses.set(position, audioStatus);
                    } else {
                        // If mediaPlayer is in idle state, start and play mediaPlayer
                        btnPlay.setText(view.getContext().getString(R.string.pause));

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
