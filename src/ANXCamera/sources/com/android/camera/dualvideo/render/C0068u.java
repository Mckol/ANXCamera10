package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.u  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0068u implements Consumer {
    public static final /* synthetic */ C0068u INSTANCE = new C0068u();

    private /* synthetic */ C0068u() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IRenderable) obj).setSelectedtypeWithAnim(1, true);
    }
}
