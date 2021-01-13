package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class C implements Predicate {
    public static final /* synthetic */ C INSTANCE = new C();

    private /* synthetic */ C() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.G((IRenderable) obj);
    }
}
