package com.android.camera.module.loader.camera2;

import com.android.camera.log.Log;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    public static final /* synthetic */ e INSTANCE = new e();

    private /* synthetic */ e() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        Throwable th = (Throwable) obj;
        Log.d("loadSettings:", "error");
    }
}
