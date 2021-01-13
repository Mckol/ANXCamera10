package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.h  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0056h implements Consumer {
    public static final /* synthetic */ C0056h INSTANCE = new C0056h();

    private /* synthetic */ C0056h() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IRenderable) obj).onKeyUp();
    }
}
