package com.android.camera.fragment.fullscreen;

import com.android.camera.fragment.fullscreen.FragmentFullScreen;
import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class c implements FragmentFullScreen.OnFrameUpdatedCallback {
    private final /* synthetic */ FragmentFullScreen tg;
    private final /* synthetic */ ModeProtocol.LiveVideoEditor ug;

    public /* synthetic */ c(FragmentFullScreen fragmentFullScreen, ModeProtocol.LiveVideoEditor liveVideoEditor) {
        this.tg = fragmentFullScreen;
        this.ug = liveVideoEditor;
    }

    @Override // com.android.camera.fragment.fullscreen.FragmentFullScreen.OnFrameUpdatedCallback
    public final void onUpdate() {
        this.tg.a(this.ug);
    }
}
