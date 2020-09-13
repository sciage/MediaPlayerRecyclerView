package com.example.record.audio;

public interface AudioInterface {
     void onAudioCompleteFragment();
    void onAudioUpdateFragment(int currentPosition);
    void onPause();
    void onResume();
    void onDestroy();

}
