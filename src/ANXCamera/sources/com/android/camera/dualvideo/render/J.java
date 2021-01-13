package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class J implements Predicate {
    public static final /* synthetic */ J INSTANCE = new J();

    private /* synthetic */ J() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.r((IRenderable) obj);
    }
}
