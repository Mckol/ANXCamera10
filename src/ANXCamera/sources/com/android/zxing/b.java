package com.android.zxing;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class b implements Function {
    private final /* synthetic */ DocumentDecoder tg;

    public /* synthetic */ b(DocumentDecoder documentDecoder) {
        this.tg = documentDecoder;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.tg.a((PreviewImage) obj);
    }
}
