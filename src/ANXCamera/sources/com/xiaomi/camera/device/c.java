package com.xiaomi.camera.device;

import android.os.Handler;
import android.os.Message;

/* compiled from: lambda */
public final /* synthetic */ class c implements Handler.Callback {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    public final boolean handleMessage(Message message) {
        return CameraService.b(message);
    }
}
