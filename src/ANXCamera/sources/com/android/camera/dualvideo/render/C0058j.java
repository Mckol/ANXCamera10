package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.j  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0058j implements Consumer {
    public static final /* synthetic */ C0058j INSTANCE = new C0058j();

    private /* synthetic */ C0058j() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DualVideoRenderManager.u((IRenderable) obj);
    }
}
