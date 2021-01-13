package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class L implements Consumer {
    public static final /* synthetic */ L INSTANCE = new L();

    private /* synthetic */ L() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DualVideoRenderManager.t((IRenderable) obj);
    }
}
