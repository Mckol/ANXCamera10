package com.android.camera;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    private final /* synthetic */ ActivityBase tg;

    public /* synthetic */ i(ActivityBase activityBase) {
        this.tg = activityBase;
    }

    public final void run() {
        this.tg.finish();
    }
}
