package com.example.record.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;


public class PlaylistFragment extends Fragment {
    private RecyclerView mRvVideos;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PlaylistFragment newInstance(int index) {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playlist_fragment, container, false);
       initView(root);
       setRecyclerView();
        return root;
    }

    private void initView(View view) {
        mRvVideos = view.findViewById(R.id.rv_videos);
    }

    private void setRecyclerView() {
        PlaylistAdapter adapter = new PlaylistAdapter(getContext());
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        mRvVideos.setLayoutManager(manager);
        mRvVideos.setAdapter(adapter);
    }
}