package com.android.camera.videoplayer.player_messages;

import com.android.camera.videoplayer.PlayerMessageState;
import com.android.camera.videoplayer.manager.VideoPlayerManagerCallback;
import com.android.camera.videoplayer.ui.VideoPlayerView;

public abstract class PlayerMessage implements Message {
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = "PlayerMessage";
    private final VideoPlayerManagerCallback mCallback;
    private final VideoPlayerView mCurrentPlayer;

    public PlayerMessage(VideoPlayerView videoPlayerView, VideoPlayerManagerCallback videoPlayerManagerCallback) {
        this.mCurrentPlayer = videoPlayerView;
        this.mCallback = videoPlayerManagerCallback;
    }

    /* access modifiers changed from: protected */
    public final PlayerMessageState getCurrentState() {
        return this.mCallback.getCurrentPlayerState();
    }

    @Override // com.android.camera.videoplayer.player_messages.Message
    public final void messageFinished() {
        this.mCallback.setVideoPlayerState(this.mCurrentPlayer, stateAfter());
    }

    /* access modifiers changed from: protected */
    public abstract void performAction(VideoPlayerView videoPlayerView);

    @Override // com.android.camera.videoplayer.player_messages.Message
    public final void polledFromQueue() {
        this.mCallback.setVideoPlayerState(this.mCurrentPlayer, stateBefore());
    }

    @Override // com.android.camera.videoplayer.player_messages.Message
    public final void runMessage() {
        performAction(this.mCurrentPlayer);
    }

    /* access modifiers changed from: protected */
    public abstract PlayerMessageState stateAfter();

    /* access modifiers changed from: protected */
    public abstract PlayerMessageState stateBefore();

    public String toString() {
        return getClass().getSimpleName();
    }
}
