package com.android.camera.dualvideo.render;

import java.util.List;
import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.q  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0065q implements Consumer {
    private final /* synthetic */ DualVideoRenderManager tg;
    private final /* synthetic */ List ug;

    public /* synthetic */ C0065q(DualVideoRenderManager dualVideoRenderManager, List list) {
        this.tg = dualVideoRenderManager;
        this.ug = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.a(this.ug, (IRenderable) obj);
    }
}
