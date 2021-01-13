package com.android.camera.fragment;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    private final /* synthetic */ FragmentMainContent tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ g(FragmentMainContent fragmentMainContent, boolean z) {
        this.tg = fragmentMainContent;
        this.ug = z;
    }

    public final void run() {
        this.tg.w(this.ug);
    }
}
