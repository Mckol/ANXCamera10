package com.android.camera.fragment.settings;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ CameraPreferenceFragment tg;

    public /* synthetic */ b(CameraPreferenceFragment cameraPreferenceFragment) {
        this.tg = cameraPreferenceFragment;
    }

    public final void run() {
        this.tg.installQRCodeReceiver();
    }
}
