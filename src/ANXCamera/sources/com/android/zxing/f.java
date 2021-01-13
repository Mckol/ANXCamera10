package com.android.zxing;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class f implements Consumer {
    private final /* synthetic */ QrDecoder tg;

    public /* synthetic */ f(QrDecoder qrDecoder) {
        this.tg = qrDecoder;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.U((String) obj);
    }
}
