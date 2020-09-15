package com.example.record.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record.R;
import com.example.record.secondBridge.CircleTransFrom;
import com.squareup.picasso.Picasso;

public class DiscoverAudioAdapter extends RecyclerView.Adapter<DiscoverAudioAdapter.VideosViewHolder> {
    private Context mContext;
    private ImageView avatar_image;
    private View view;

    public DiscoverAudioAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);

        avatar_image = view.findViewById(R.id.avatar_image);
        Picasso.get().load(R.mipmap.ic_launcher).resize(40, 40).transform(new CircleTransFrom()).into(avatar_image);

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
