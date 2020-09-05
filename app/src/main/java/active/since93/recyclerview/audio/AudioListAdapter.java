package active.since93.recyclerview.audio;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {

    private Context context;
    private List<String> contactList = new ArrayList<>();
    private MainActivity mainActivity;
    private View view;

    public AudioListAdapter(Context context, List<String> contactList) {
        this.context = context;
        this.contactList = contactList;
        if (context instanceof MainActivity){
        this.mainActivity = (MainActivity) context;
          }
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

        if(mainActivity.getAudioList().get(position).getAudioState() != AudioStatus.AUDIO_STATE.IDLE.ordinal()) {
            holder.seekBarAudio.setMax(mainActivity.getAudioList().get(position).getTotalDuration());
            holder.seekBarAudio.setProgress(mainActivity.getAudioList().get(position).getCurrentValue());
            holder.seekBarAudio.setEnabled(true);
        } else {
            holder.seekBarAudio.setProgress(0);
            holder.seekBarAudio.setEnabled(false);
        }

        if(mainActivity.getAudioList().get(position).getAudioState() == AudioStatus.AUDIO_STATE.IDLE.ordinal()
                || mainActivity.getAudioList().get(position).getAudioState() == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
            holder.btnPlay.setText(view.getContext().getString(R.string.play));
        } else {
            holder.btnPlay.setText(view.getContext().getString(R.string.pause));
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btnPlay)
        Button btnPlay;

        @BindView(R.id.seekBarAudio)
        SeekBar seekBarAudio;

        @BindView(R.id.txtSongName)
        TextView txtSongName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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
                    boolean ifRequest = mainActivity.requestPermissionIfNeeded();
                    if(ifRequest) return;

                    int position = getAdapterPosition();

                    // Check if any other audio is playing
                    if(mainActivity.getAudioList().get(position).getAudioState()
                            == AudioStatus.AUDIO_STATE.IDLE.ordinal()) {

                        // Reset media player
                        MediaPlayerUtils.Listener listener = (MediaPlayerUtils.Listener) context;
                        listener.onAudioComplete();
                    }

                    String audioPath = contactList.get(position);
                    AudioStatus audioStatus = mainActivity.getAudioList().get(position);
                    int currentAudioState = audioStatus.getAudioState();

                    if(currentAudioState == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                        // If mediaPlayer is playing, pause mediaPlayer
                        btnPlay.setText(view.getContext().getString(R.string.play));
                        MediaPlayerUtils.pauseMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PAUSED.ordinal());
                        mainActivity.getAudioList().set(position, audioStatus);
                    } else if(currentAudioState == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
                        // If mediaPlayer is paused, play mediaPlayer
                        btnPlay.setText(view.getContext().getString(R.string.pause));
                        MediaPlayerUtils.playMediaPlayer();

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        mainActivity.getAudioList().set(position, audioStatus);
                    } else {
                        // If mediaPlayer is in idle state, start and play mediaPlayer
                        btnPlay.setText(view.getContext().getString(R.string.pause));

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        mainActivity.getAudioList().set(position, audioStatus);

                        try {
                            MediaPlayerUtils.startAndPlayMediaPlayer(audioPath, (MediaPlayerUtils.Listener) context);

                            audioStatus.setTotalDuration(MediaPlayerUtils.getTotalDuration());
                            mainActivity.getAudioList().set(position, audioStatus);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
