package active.since93.recyclerview.audio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import active.since93.recyclerview.audio.firstBridge.MusicFile;
import active.since93.recyclerview.audio.firstBridge.MusicFileBean;

import com.example.record.audio.MainActivityDialog;
import com.example.record.audio.MediaPlayerUtils;
import com.example.record.fragment.DiscoverFragment;
import com.example.record.fragment.ExampleDialog;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MusicFile {

    private MainActivityDialog dialogFragment;

    private static final int RC_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.music_layout);
        ButterKnife.bind(this);


        dialogFragment = MainActivityDialog.display(getSupportFragmentManager());
        requestPermissionIfNeeded();
    }

    public boolean requestPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_PERMISSION);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Store its state
        dialogFragment.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
         dialogFragment.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        MediaPlayerUtils.releaseMediaPlayer();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void sendMusicPojo(MusicFileBean musicFileBean, long duration) {
        Toast.makeText(this, musicFileBean.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
