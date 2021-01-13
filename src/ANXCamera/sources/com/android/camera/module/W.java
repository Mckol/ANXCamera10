package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class W implements Runnable {
    private final /* synthetic */ ModeProtocol.ActionProcessing tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ W(ModeProtocol.ActionProcessing actionProcessing, boolean z) {
        this.tg = actionProcessing;
        this.ug = z;
    }

    public final void run() {
        this.tg.switchThumbnailFunction(this.ug);
    }
}
