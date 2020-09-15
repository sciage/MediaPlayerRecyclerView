package com.example.record.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;
import com.example.record.audio.MediaPlayerUtils;
import com.example.record.audio.MusicChooseViewCustom;
import com.example.record.secondBridge.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoverFragment extends DialogFragment implements MediaPlayerUtils.Listener {
    private RecyclerView mRvVideos;
    private TextView seeall;
    private TextView title_page;
    private String pageName;

    private MusicChooseViewCustom musicChooseViewCustom;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static DiscoverFragment newInstance(String page) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SECTION_NUMBER, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            pageName = getArguments().getString(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.discover_fragment, container, false);
//        initView(root);
//        setRecyclerView();

        musicChooseViewCustom = new MusicChooseViewCustom(getContext(), this);
        return musicChooseViewCustom;
//        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        musicChooseViewCustom.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        musicChooseViewCustom.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicChooseViewCustom.onDestroy();
    }

    @Override
    public void onAudioUpdate(int currentPosition) {
        musicChooseViewCustom.onAudioUpdateFragment(currentPosition);
    }

    @Override
    public void onAudioComplete() {
        musicChooseViewCustom.onAudioCompleteFragment();
    }

    @Override
    public void onDetach() {
        MediaPlayerUtils.releaseMediaPlayer();

        super.onDetach();
    }

    //    private void initView(View view) {
//        mRvVideos = view.findViewById(R.id.rv_videos);
//        title_page = view.findViewById(R.id.title_page);
//
//        switch (pageName){
//            case Constants.DISCOVER:
//                title_page.setText("For you");
//                break;
//            case Constants.FAVOURITE:
//                title_page.setText("Favorites");
//                break;
//
//        }
//        seeall = view.findViewById(R.id.seeall);
//
//        seeall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//    }

//    private void setRecyclerView() {
//        DiscoverAudioAdapter adapter = new DiscoverAudioAdapter(getContext());
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//
//        mRvVideos.setLayoutManager(layoutManager);
//        mRvVideos.setAdapter(adapter);
//    }


}