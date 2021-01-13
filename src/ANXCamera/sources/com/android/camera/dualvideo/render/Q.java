package com.android.camera.dualvideo.render;

import com.android.gallery3d.ui.GLCanvas;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class Q implements Consumer {
    private final /* synthetic */ GLCanvas tg;

    public /* synthetic */ Q(GLCanvas gLCanvas) {
        this.tg = gLCanvas;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((IMiscRenderable) obj).getBasicTexture().onBind(this.tg);
    }
}
