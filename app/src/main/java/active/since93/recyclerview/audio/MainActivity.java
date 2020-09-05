package active.since93.recyclerview.audio;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MediaPlayerUtils.Listener {

//    private Context context;
    private List<String> contactList = new ArrayList<>();
//    public List<AudioStatus> audioStatusList = new ArrayList<>();
    private Parcelable state;
    private MainActivityDialog dialogFragment;

    private static final int RC_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dialogFragment = MainActivityDialog.newInstance("Some Title", "");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");

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
        // Main position of RecyclerView when loaded again
        if (state != null) {
            dialogFragment.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        MediaPlayerUtils.releaseMediaPlayer();
    }

    public List<AudioStatus> getAudioList(){
        return dialogFragment.getAudioStatusList();
    }

    @Override
    public void onAudioUpdate(int currentPosition) {
        dialogFragment.onAudioUpdateFragment(currentPosition);
    }

    @Override
    public void onAudioComplete() {
        dialogFragment.onAudioCompleteFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
