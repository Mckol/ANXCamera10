package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* renamed from: com.android.camera.dualvideo.render.g  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0055g implements Predicate {
    public static final /* synthetic */ C0055g INSTANCE = new C0055g();

    private /* synthetic */ C0055g() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((IRenderable) obj).isVisible();
    }
}
