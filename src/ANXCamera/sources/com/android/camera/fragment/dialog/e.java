package com.android.camera.fragment.dialog;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    private final /* synthetic */ FragmentLiveReview tg;

    public /* synthetic */ e(FragmentLiveReview fragmentLiveReview) {
        this.tg = fragmentLiveReview;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.a((Integer) obj);
    }
}
