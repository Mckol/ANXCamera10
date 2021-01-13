package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.n  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0062n implements Consumer {
    public static final /* synthetic */ C0062n INSTANCE = new C0062n();

    private /* synthetic */ C0062n() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IRenderable) obj).setVisibilityWithAnim(true, true);
    }
}
