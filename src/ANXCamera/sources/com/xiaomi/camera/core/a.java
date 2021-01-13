package com.xiaomi.camera.core;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ ParallelDataZipper tg;
    private final /* synthetic */ long ug;

    public /* synthetic */ a(ParallelDataZipper parallelDataZipper, long j) {
        this.tg = parallelDataZipper;
        this.ug = j;
    }

    public final void run() {
        this.tg.f(this.ug);
    }
}
