package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class z implements Predicate {
    public static final /* synthetic */ z INSTANCE = new z();

    private /* synthetic */ z() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.B((IRenderable) obj);
    }
}
