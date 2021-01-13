package com.android.camera.fragment.top;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    private final /* synthetic */ FragmentTopConfig tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ m(FragmentTopConfig fragmentTopConfig, boolean z) {
        this.tg = fragmentTopConfig;
        this.ug = z;
    }

    public final void run() {
        this.tg.x(this.ug);
    }
}
