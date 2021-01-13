package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class ma implements Runnable {
    private final /* synthetic */ ModeProtocol.RecordState tg;

    public /* synthetic */ ma(ModeProtocol.RecordState recordState) {
        this.tg = recordState;
    }

    public final void run() {
        this.tg.onFinish();
    }
}
