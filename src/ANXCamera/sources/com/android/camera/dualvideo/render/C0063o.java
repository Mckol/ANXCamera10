package com.android.camera.dualvideo.render;

import android.util.SparseArray;
import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.o  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0063o implements Consumer {
    private final /* synthetic */ DualVideoRenderManager tg;
    private final /* synthetic */ SparseArray ug;

    public /* synthetic */ C0063o(DualVideoRenderManager dualVideoRenderManager, SparseArray sparseArray) {
        this.tg = dualVideoRenderManager;
        this.ug = sparseArray;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.a(this.ug, (IRenderable) obj);
    }
}
