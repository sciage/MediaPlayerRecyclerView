package active.since93.recyclerview.audio;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MediaPlayerUtils.Listener {

    private Context context;
    private List<String> contactList = new ArrayList<>();
    public List<AudioStatus> audioStatusList = new ArrayList<>();
    private Parcelable state;

    @BindView(R.id.recyclerViewContactsList)
    RecyclerView recyclerView;

    private static final int RC_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = MainActivity.this;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO replace below audio paths with respective SD Card location
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");
        contactList.add("http://34.66.8.61:8000/media/uploads/sounds/The_Christmas_Song_Sentimental.mp3");

        for(int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter(contactList);

        requestPermissionIfNeeded();
    }

    private void setRecyclerViewAdapter(List<String> contactList) {
        AudioListAdapter adapter = new AudioListAdapter(context, contactList);
        recyclerView.setAdapter(adapter);
    }

    public boolean requestPermissionIfNeeded() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_PERMISSION);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Store its state
        state = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Main position of RecyclerView when loaded again
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MediaPlayerUtils.releaseMediaPlayer();
    }

    @Override
    public void onAudioUpdate(int currentPosition) {
        int playingAudioPosition = -1;
        for(int i = 0; i < audioStatusList.size(); i++) {
            AudioStatus audioStatus = audioStatusList.get(i);
            if(audioStatus.getAudioState() == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                playingAudioPosition = i;
                break;
            }
        }

        if(playingAudioPosition != -1) {
            AudioListAdapter.ViewHolder holder
                    = (AudioListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(playingAudioPosition);
            if (holder != null) {
                holder.seekBarAudio.setProgress(currentPosition);
            }
        }
    }

    @Override
    public void onAudioComplete() {
        // Store its state
        state = recyclerView.getLayoutManager().onSaveInstanceState();

        audioStatusList.clear();
        for(int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter(contactList);

        // Main position of RecyclerView when loaded again
        if (state != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(state);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
