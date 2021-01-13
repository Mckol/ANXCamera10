package com.android.camera.scene;

import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ ModeProtocol.TopAlert tg;

    public /* synthetic */ d(ModeProtocol.TopAlert topAlert) {
        this.tg = topAlert;
    }

    public final void run() {
        this.tg.alertAiSceneSelector(8);
    }
}
