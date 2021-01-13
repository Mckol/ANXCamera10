package com.android.camera.module;

import android.net.Uri;

/* compiled from: lambda */
public final /* synthetic */ class ha implements Runnable {
    private final /* synthetic */ MiLiveModule tg;
    private final /* synthetic */ String ug;
    private final /* synthetic */ Uri vg;

    public /* synthetic */ ha(MiLiveModule miLiveModule, String str, Uri uri) {
        this.tg = miLiveModule;
        this.ug = str;
        this.vg = uri;
    }

    public final void run() {
        this.tg.a(this.ug, this.vg);
    }
}
