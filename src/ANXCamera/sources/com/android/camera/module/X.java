package com.android.camera.module;

import com.android.camera.dualvideo.DualVideoControler;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class X implements Consumer {
    private final /* synthetic */ DualVideoModule tg;

    public /* synthetic */ X(DualVideoModule dualVideoModule) {
        this.tg = dualVideoModule;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.b((DualVideoControler) obj);
    }
}
