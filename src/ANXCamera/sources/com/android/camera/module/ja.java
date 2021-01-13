package com.android.camera.module;

import com.android.camera.module.SuperMoonModule;
import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class ja implements Runnable {
    private final /* synthetic */ ModeProtocol.TopAlert tg;

    public /* synthetic */ ja(ModeProtocol.TopAlert topAlert) {
        this.tg = topAlert;
    }

    public final void run() {
        SuperMoonModule.AnonymousClass3.b(this.tg);
    }
}
