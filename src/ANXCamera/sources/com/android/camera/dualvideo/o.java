package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class o implements Consumer {
    private final /* synthetic */ IRenderable tg;

    public /* synthetic */ o(IRenderable iRenderable) {
        this.tg = iRenderable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DualVideoControler.a(this.tg, (UserSelectData) obj);
    }
}
