package com.android.camera.preferences;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.VideoView;

/* compiled from: lambda */
public final /* synthetic */ class b implements MediaPlayer.OnPreparedListener {
    private final /* synthetic */ VideoView tg;
    private final /* synthetic */ View ug;

    public /* synthetic */ b(VideoView videoView, View view) {
        this.tg = videoView;
        this.ug = view;
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        MoreModeStylePreference.a(this.tg, this.ug, mediaPlayer);
    }
}
