package com.android.camera.zoommap;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ ZoomMapController tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ a(ZoomMapController zoomMapController, boolean z) {
        this.tg = zoomMapController;
        this.ug = z;
    }

    public final void run() {
        this.tg.J(this.ug);
    }
}
