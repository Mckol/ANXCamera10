package com.android.camera.videoplayer.manager;

import android.content.res.AssetFileDescriptor;
import com.android.camera.videoplayer.meta.MetaData;
import com.android.camera.videoplayer.ui.VideoPlayerView;

public interface VideoPlayerManager<T extends MetaData> {
    void playNewVideo(T t, VideoPlayerView videoPlayerView, AssetFileDescriptor assetFileDescriptor);

    void playNewVideo(T t, VideoPlayerView videoPlayerView, String str);

    void resetMediaPlayer();

    void resumeMediaPlayer();

    void stopAnyPlayback();
}
