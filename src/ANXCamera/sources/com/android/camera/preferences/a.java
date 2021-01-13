package com.android.camera.preferences;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.VideoView;

/* compiled from: lambda */
public final /* synthetic */ class a implements MediaPlayer.OnInfoListener {
    private final /* synthetic */ VideoView tg;
    private final /* synthetic */ View ug;

    public /* synthetic */ a(VideoView videoView, View view) {
        this.tg = videoView;
        this.ug = view;
    }

    public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        return MoreModeStylePreference.a(this.tg, this.ug, mediaPlayer, i, i2);
    }
}
