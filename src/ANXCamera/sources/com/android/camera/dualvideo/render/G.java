package com.android.camera.dualvideo.render;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class G implements Consumer {
    public static final /* synthetic */ G INSTANCE = new G();

    private /* synthetic */ G() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IRenderable) obj).alphaIn6PatchTag(true);
    }
}
