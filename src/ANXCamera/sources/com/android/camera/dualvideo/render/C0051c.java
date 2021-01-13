package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.c  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0051c implements Consumer {
    public static final /* synthetic */ C0051c INSTANCE = new C0051c();

    private /* synthetic */ C0051c() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IRenderable) obj).alphaInSelectedFrame(true);
    }
}
