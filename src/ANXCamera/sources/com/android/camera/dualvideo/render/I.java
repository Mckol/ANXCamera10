package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class I implements Predicate {
    public static final /* synthetic */ I INSTANCE = new I();

    private /* synthetic */ I() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.n((IRenderable) obj);
    }
}
