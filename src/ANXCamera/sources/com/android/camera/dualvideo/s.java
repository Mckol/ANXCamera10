package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.DualVideoRenderManager;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class s implements Consumer {
    private final /* synthetic */ DualVideoControler tg;

    public /* synthetic */ s(DualVideoControler dualVideoControler) {
        this.tg = dualVideoControler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.a((DualVideoRenderManager) obj);
    }
}
