package com.android.camera.module.loader.camera2;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    private final /* synthetic */ Camera2OpenManager tg;

    public /* synthetic */ a(Camera2OpenManager camera2OpenManager) {
        this.tg = camera2OpenManager;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.fire((Camera2Result) obj);
    }
}
