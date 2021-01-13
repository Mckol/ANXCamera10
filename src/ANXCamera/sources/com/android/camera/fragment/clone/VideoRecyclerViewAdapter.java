package com.android.camera.fragment.clone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.videoplayer.manager.VideoPlayerManager;
import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private final Context mContext;
    private final List<BaseVideoItem> mList;
    private final VideoPlayerManager mVideoPlayerManager;

    public VideoRecyclerViewAdapter(VideoPlayerManager videoPlayerManager, Context context, List<BaseVideoItem> list) {
        this.mVideoPlayerManager = videoPlayerManager;
        this.mContext = context;
        this.mList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    public void onBindViewHolder(VideoViewHolder videoViewHolder, int i) {
        this.mList.get(i).update(i, videoViewHolder, this.mVideoPlayerManager);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new VideoViewHolder(this.mList.get(i).createView(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_clone_use_guide_item, viewGroup, false), this.mContext.getResources().getDisplayMetrics().widthPixels));
    }
}
