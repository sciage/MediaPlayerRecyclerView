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


public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {

    private Context context;
    private List<String> audioList = new ArrayList<>();
    private View view;
    private MediaPlayerUtils.Listener listener;

    public AudioListAdapter(Context context, List<String> contactList, MediaPlayerUtils.Listener listener) {
        this.context = context;
        this.audioList = contactList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String songPath = audioList.get(position);
        String songName = songPath.substring(songPath.lastIndexOf("/") + 1);
        holder.txtSongName.setText(songName);

        if (listener.updateList().get(position).getAudioState() == AudioStatus.AUDIO_STATE.IDLE.ordinal()
                || listener.updateList().get(position).getAudioState() == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {

            holder.btnPlay.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_play));
        } else {
            holder.btnPlay.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pause));
        }
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView btnPlay;
        TextView txtSongName;

        ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            txtSongName = itemView.findViewById(R.id.txtSongName);
            btnPlay = itemView.findViewById(R.id.btnPlay);

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    // Check if any other audio is playing
                    if (listener.updateList().get(position).getAudioState()
                            == AudioStatus.AUDIO_STATE.IDLE.ordinal()) {
                        listener.onAudioComplete();
                    }

                    String audioPath = audioList.get(position);
                    AudioStatus audioStatus = listener.updateList().get(position);
                    int currentAudioState = audioStatus.getAudioState();

                    if (currentAudioState == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                        // If mediaPlayer is playing, pause mediaPlayer
                        btnPlay.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_play));
                        MediaPlayerUtils.pauseMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PAUSED.ordinal());
                        listener.updateList().set(position, audioStatus);
                    } else if (currentAudioState == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
                        // If mediaPlayer is paused, play mediaPlayer
                        btnPlay.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pause));
                        MediaPlayerUtils.playMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        listener.updateList().set(position, audioStatus);
                    } else {

                        // If mediaPlayer is in idle state, start and play mediaPlayer
                        btnPlay.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pause));
                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        listener.updateList().set(position, audioStatus);

                        try {
                            MediaPlayerUtils.startAndPlayMediaPlayer(audioPath, listener, context);
                            audioStatus.setTotalDuration(MediaPlayerUtils.getTotalDuration());
                            listener.updateList().set(position, audioStatus);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }
}
