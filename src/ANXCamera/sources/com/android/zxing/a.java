package com.android.zxing;

import android.util.Pair;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    private final /* synthetic */ DocumentDecoder tg;

    public /* synthetic */ a(DocumentDecoder documentDecoder) {
        this.tg = documentDecoder;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.a((Pair) obj);
    }
}
