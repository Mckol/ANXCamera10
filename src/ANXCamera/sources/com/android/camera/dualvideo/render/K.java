package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class K implements Consumer {
    private final /* synthetic */ DualVideoRenderManager tg;

    public /* synthetic */ K(DualVideoRenderManager dualVideoRenderManager) {
        this.tg = dualVideoRenderManager;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.y((IRenderable) obj);
    }
}
