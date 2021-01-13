package com.android.camera.provider;

import android.content.Context;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ SplashProvider tg;
    private final /* synthetic */ Context ug;
    private final /* synthetic */ File vg;

    public /* synthetic */ a(SplashProvider splashProvider, Context context, File file) {
        this.tg = splashProvider;
        this.ug = context;
        this.vg = file;
    }

    public final void run() {
        this.tg.a(this.ug, this.vg);
    }
}
