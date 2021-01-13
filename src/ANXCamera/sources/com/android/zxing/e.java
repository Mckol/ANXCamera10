package com.android.zxing;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class e implements Function {
    private final /* synthetic */ QrDecoder tg;

    public /* synthetic */ e(QrDecoder qrDecoder) {
        this.tg = qrDecoder;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.tg.c((PreviewImage) obj);
    }
}
