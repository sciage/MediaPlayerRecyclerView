package active.since93.recyclerview.audio;

public interface ActivityToFragment {
     void onAudioCompleteFragment();
    void onAudioUpdateFragment(int currentPosition);
    void onPause();
    void onResume();
    void onDestroy();

}
