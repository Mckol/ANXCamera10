package com.android.camera.dualvideo.render;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class B implements Consumer {
    private final /* synthetic */ ArrayList tg;

    public /* synthetic */ B(ArrayList arrayList) {
        this.tg = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DualVideoRenderManager.a(this.tg, (IRenderable) obj);
    }
}
