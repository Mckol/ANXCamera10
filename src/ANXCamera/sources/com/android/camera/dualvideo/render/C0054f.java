package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.f  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0054f implements Consumer {
    private final /* synthetic */ DualVideoRenderManager tg;

    public /* synthetic */ C0054f(DualVideoRenderManager dualVideoRenderManager) {
        this.tg = dualVideoRenderManager;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.F((IRenderable) obj);
    }
}
