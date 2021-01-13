package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;

/* renamed from: com.android.camera.module.g  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class RunnableC0075g implements Runnable {
    private final /* synthetic */ ModeProtocol.TopAlert tg;

    public /* synthetic */ RunnableC0075g(ModeProtocol.TopAlert topAlert) {
        this.tg = topAlert;
    }

    public final void run() {
        this.tg.reInitAlert(true);
    }
}
