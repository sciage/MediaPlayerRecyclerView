package com.example.record.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.VideosViewHolder> {
    private Context mContext;

    public PlaylistAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class VideosViewHolder extends RecyclerView.ViewHolder {
        public VideosViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
