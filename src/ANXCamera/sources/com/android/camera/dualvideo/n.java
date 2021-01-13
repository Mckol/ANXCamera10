package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class n implements Consumer {
    private final /* synthetic */ StringBuilder tg;

    public /* synthetic */ n(StringBuilder sb) {
        this.tg = sb;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DualVideoControler.a(this.tg, (IRenderable) obj);
    }
}
