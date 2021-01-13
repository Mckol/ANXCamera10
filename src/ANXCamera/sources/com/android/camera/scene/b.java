package com.android.camera.scene;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ ASDResultParse tg;
    private final /* synthetic */ int ug;
    private final /* synthetic */ int vg;
    private final /* synthetic */ int wg;

    public /* synthetic */ b(ASDResultParse aSDResultParse, int i, int i2, int i3) {
        this.tg = aSDResultParse;
        this.ug = i;
        this.vg = i2;
        this.wg = i3;
    }

    public final void run() {
        this.tg.a(this.ug, this.vg, this.wg);
    }
}
