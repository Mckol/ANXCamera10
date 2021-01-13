package com.android.camera.module;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class ia implements Consumer {
    private final /* synthetic */ Camera2Module tg;

    public /* synthetic */ ia(Camera2Module camera2Module) {
        this.tg = camera2Module;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.resetToIdleNoKeep(((Boolean) obj).booleanValue());
    }
}
