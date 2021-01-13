package com.android.camera.fragment.ambilight;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ FragmentAmbilight tg;
    private final /* synthetic */ int ug;

    public /* synthetic */ b(FragmentAmbilight fragmentAmbilight, int i) {
        this.tg = fragmentAmbilight;
        this.ug = i;
    }

    public final void run() {
        this.tg.n(this.ug);
    }
}
