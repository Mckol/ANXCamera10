package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* renamed from: com.android.camera.dualvideo.render.d  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0052d implements Predicate {
    public static final /* synthetic */ C0052d INSTANCE = new C0052d();

    private /* synthetic */ C0052d() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((IRenderable) obj).isPressedIn6Patch();
    }
}
