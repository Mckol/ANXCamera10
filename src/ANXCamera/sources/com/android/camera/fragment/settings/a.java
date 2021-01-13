package com.android.camera.fragment.settings;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ CameraPreferenceFragment tg;

    public /* synthetic */ a(CameraPreferenceFragment cameraPreferenceFragment) {
        this.tg = cameraPreferenceFragment;
    }

    public final void run() {
        this.tg.restorePreferences();
    }
}
