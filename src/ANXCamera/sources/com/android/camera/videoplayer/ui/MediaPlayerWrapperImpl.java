package com.android.camera.videoplayer.ui;

import android.media.MediaPlayer;

public class MediaPlayerWrapperImpl extends MediaPlayerWrapper {
    public MediaPlayerWrapperImpl() {
        super(new MediaPlayer());
    }
}
