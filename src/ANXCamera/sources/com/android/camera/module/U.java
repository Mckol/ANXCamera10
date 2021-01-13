package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class U implements Runnable {
    private final /* synthetic */ ModeProtocol.ActionProcessing tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ U(ModeProtocol.ActionProcessing actionProcessing, boolean z) {
        this.tg = actionProcessing;
        this.ug = z;
    }

    public final void run() {
        DualVideoModule.a(this.tg, this.ug);
    }
}
