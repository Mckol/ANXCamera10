package com.android.camera.module;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class ga implements FlowableOnSubscribe {
    private final /* synthetic */ MiLiveModule tg;

    public /* synthetic */ ga(MiLiveModule miLiveModule) {
        this.tg = miLiveModule;
    }

    @Override // io.reactivex.FlowableOnSubscribe
    public final void subscribe(FlowableEmitter flowableEmitter) {
        this.tg.a(flowableEmitter);
    }
}
