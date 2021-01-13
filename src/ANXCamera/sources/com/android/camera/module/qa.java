package com.android.camera.module;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class qa implements Consumer {
    private final /* synthetic */ VideoModule tg;

    public /* synthetic */ qa(VideoModule videoModule) {
        this.tg = videoModule;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.c((Boolean) obj);
    }
}
