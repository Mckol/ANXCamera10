package com.android.camera.module;

import com.android.camera.dualvideo.DualVideoControler;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class P implements Consumer {
    private final /* synthetic */ DualVideoModule tg;

    public /* synthetic */ P(DualVideoModule dualVideoModule) {
        this.tg = dualVideoModule;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.c((DualVideoControler) obj);
    }
}