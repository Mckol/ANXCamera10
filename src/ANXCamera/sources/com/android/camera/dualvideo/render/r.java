package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class r implements Predicate {
    public static final /* synthetic */ r INSTANCE = new r();

    private /* synthetic */ r() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((IRenderable) obj).isAnimating();
    }
}
