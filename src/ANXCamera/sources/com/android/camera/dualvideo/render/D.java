package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class D implements Predicate {
    public static final /* synthetic */ D INSTANCE = new D();

    private /* synthetic */ D() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.p((IRenderable) obj);
    }
}
