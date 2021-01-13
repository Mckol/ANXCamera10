package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class t implements Consumer {
    private final /* synthetic */ IRenderable tg;

    public /* synthetic */ t(IRenderable iRenderable) {
        this.tg = iRenderable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((UserSelectData) obj).mPreviewType = this.tg.getRenderComposeType();
    }
}
