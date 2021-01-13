package com.android.camera.fragment.dialog;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class f implements Consumer {
    private final /* synthetic */ FragmentLiveReview tg;

    public /* synthetic */ f(FragmentLiveReview fragmentLiveReview) {
        this.tg = fragmentLiveReview;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.c((Integer) obj);
    }
}
