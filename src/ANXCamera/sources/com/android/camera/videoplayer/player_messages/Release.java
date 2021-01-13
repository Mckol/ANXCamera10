package com.android.camera.videoplayer.player_messages;

import com.android.camera.videoplayer.PlayerMessageState;
import com.android.camera.videoplayer.manager.VideoPlayerManagerCallback;
import com.android.camera.videoplayer.ui.VideoPlayerView;

public class Release extends PlayerMessage {
    public Release(VideoPlayerView videoPlayerView, VideoPlayerManagerCallback videoPlayerManagerCallback) {
        super(videoPlayerView, videoPlayerManagerCallback);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.videoplayer.player_messages.PlayerMessage
    public void performAction(VideoPlayerView videoPlayerView) {
        videoPlayerView.release();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.videoplayer.player_messages.PlayerMessage
    public PlayerMessageState stateAfter() {
        return PlayerMessageState.RELEASED;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.videoplayer.player_messages.PlayerMessage
    public PlayerMessageState stateBefore() {
        return PlayerMessageState.RELEASING;
    }
}
