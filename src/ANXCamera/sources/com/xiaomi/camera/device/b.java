package com.xiaomi.camera.device;

import com.xiaomi.camera.device.CameraHandlerThread;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class b implements Function {
    public static final /* synthetic */ b INSTANCE = new b();

    private /* synthetic */ b() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return CameraHandlerThread.CookieStore.Z((String) obj);
    }
}
