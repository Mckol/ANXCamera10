package com.android.camera.fragment.fastmotion;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ FragmentFastMotion tg;
    private final /* synthetic */ int ug;

    public /* synthetic */ a(FragmentFastMotion fragmentFastMotion, int i) {
        this.tg = fragmentFastMotion;
        this.ug = i;
    }

    public final void run() {
        this.tg.o(this.ug);
    }
}
