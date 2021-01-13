package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class J implements Runnable {
    private final /* synthetic */ ModeProtocol.CloneProcess tg;

    public /* synthetic */ J(ModeProtocol.CloneProcess cloneProcess) {
        this.tg = cloneProcess;
    }

    public final void run() {
        this.tg.showSaveAndGiveUp();
    }
}
