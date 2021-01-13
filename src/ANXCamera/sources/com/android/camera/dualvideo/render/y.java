package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class y implements Consumer {
    public static final /* synthetic */ y INSTANCE = new y();

    private /* synthetic */ y() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IRenderable) obj).alphaIn6PatchTag(true);
    }
}
