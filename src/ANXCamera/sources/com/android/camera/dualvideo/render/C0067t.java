package com.android.camera.dualvideo.render;

import android.util.SparseArray;
import java.util.function.Predicate;

/* renamed from: com.android.camera.dualvideo.render.t  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0067t implements Predicate {
    private final /* synthetic */ SparseArray tg;
    private final /* synthetic */ int ug;

    public /* synthetic */ C0067t(SparseArray sparseArray, int i) {
        this.tg = sparseArray;
        this.ug = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.a(this.tg, this.ug, (IRenderable) obj);
    }
}
