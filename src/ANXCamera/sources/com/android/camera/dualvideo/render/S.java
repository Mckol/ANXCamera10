package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class S implements Consumer {
    public static final /* synthetic */ S INSTANCE = new S();

    private /* synthetic */ S() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IMiscRenderable) obj).getBasicTexture().recycle();
    }
}
