package com.android.camera.fragment.clone;

import android.content.res.AssetFileDescriptor;
import com.android.camera.videoplayer.manager.VideoPlayerManager;
import com.android.camera.videoplayer.meta.MetaData;
import com.android.camera.videoplayer.ui.VideoPlayerView;

public class AssetVideoItem extends BaseVideoItem {
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = "AssetVideoItem";
    private final AssetFileDescriptor mAssetFileDescriptor;
    protected final int mCoverResource;

    public AssetVideoItem(AssetFileDescriptor assetFileDescriptor, VideoPlayerManager<MetaData> videoPlayerManager, int i) {
        super(videoPlayerManager);
        this.mAssetFileDescriptor = assetFileDescriptor;
        this.mCoverResource = i;
    }

    @Override // com.android.camera.videoplayer.manager.VideoItem
    public void playNewVideo(MetaData metaData, VideoPlayerView videoPlayerView, VideoPlayerManager<MetaData> videoPlayerManager) {
        AssetFileDescriptor assetFileDescriptor = this.mAssetFileDescriptor;
        if (assetFileDescriptor != null) {
            videoPlayerManager.playNewVideo(metaData, videoPlayerView, assetFileDescriptor);
        } else {
            videoPlayerManager.stopAnyPlayback();
        }
    }

    @Override // com.android.camera.videoplayer.manager.VideoItem
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }

    @Override // com.android.camera.fragment.clone.BaseVideoItem
    public void update(int i, VideoViewHolder videoViewHolder, VideoPlayerManager videoPlayerManager) {
        videoViewHolder.mCover.setVisibility(0);
    }
}
