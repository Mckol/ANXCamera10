package com.android.zxing;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class c implements Function {
    private final /* synthetic */ HandGestureDecoder tg;

    public /* synthetic */ c(HandGestureDecoder handGestureDecoder) {
        this.tg = handGestureDecoder;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.tg.b((PreviewImage) obj);
    }
}
