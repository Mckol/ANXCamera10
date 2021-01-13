package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class M implements Predicate {
    public static final /* synthetic */ M INSTANCE = new M();

    private /* synthetic */ M() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.D((IRenderable) obj);
    }
}
