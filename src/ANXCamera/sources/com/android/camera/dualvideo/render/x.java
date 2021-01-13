package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class x implements Predicate {
    public static final /* synthetic */ x INSTANCE = new x();

    private /* synthetic */ x() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((IRenderable) obj).isVisible();
    }
}
