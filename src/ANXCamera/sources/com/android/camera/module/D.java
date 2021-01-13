package com.android.camera.module;

import android.net.Uri;

/* compiled from: lambda */
public final /* synthetic */ class D implements Runnable {
    private final /* synthetic */ CloneModule tg;
    private final /* synthetic */ Uri ug;

    public /* synthetic */ D(CloneModule cloneModule, Uri uri) {
        this.tg = cloneModule;
        this.ug = uri;
    }

    public final void run() {
        this.tg.a(this.ug);
    }
}
