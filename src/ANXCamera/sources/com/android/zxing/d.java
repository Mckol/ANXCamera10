package com.android.zxing;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements Consumer {
    private final /* synthetic */ HandGestureDecoder tg;

    public /* synthetic */ d(HandGestureDecoder handGestureDecoder) {
        this.tg = handGestureDecoder;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.e((Integer) obj);
    }
}
