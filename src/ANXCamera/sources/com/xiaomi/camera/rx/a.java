package com.xiaomi.camera.rx;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    public final void run() {
        CameraSchedulers.sIsCameraSetupThread.set(Boolean.TRUE);
    }
}
