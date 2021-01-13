package com.android.camera.videoplayer.player_messages;

import com.android.camera.videoplayer.PlayerMessageState;
import com.android.camera.videoplayer.manager.VideoPlayerManagerCallback;
import com.android.camera.videoplayer.ui.VideoPlayerView;

public class Reset extends PlayerMessage {
    public Reset(VideoPlayerView videoPlayerView, VideoPlayerManagerCallback videoPlayerManagerCallback) {
        super(videoPlayerView, videoPlayerManagerCallback);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.videoplayer.player_messages.PlayerMessage
    public void performAction(VideoPlayerView videoPlayerView) {
        videoPlayerView.reset();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.videoplayer.player_messages.PlayerMessage
    public PlayerMessageState stateAfter() {
        return PlayerMessageState.RESET;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.videoplayer.player_messages.PlayerMessage
    public PlayerMessageState stateBefore() {
        return PlayerMessageState.RESETTING;
    }
}
