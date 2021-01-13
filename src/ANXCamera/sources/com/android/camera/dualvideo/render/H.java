package com.android.camera.dualvideo.render;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class H implements Consumer {
    private final /* synthetic */ ArrayList tg;

    public /* synthetic */ H(ArrayList arrayList) {
        this.tg = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        IRenderable iRenderable;
        this.tg.stream().filter(new v(iRenderable)).findFirst().ifPresent(new C0049a((IRenderable) obj));
    }
}
