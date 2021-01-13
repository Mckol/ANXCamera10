package com.android.camera.fragment.dialog;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class h implements ObservableOnSubscribe {
    private final /* synthetic */ FragmentLiveReview tg;

    public /* synthetic */ h(FragmentLiveReview fragmentLiveReview) {
        this.tg = fragmentLiveReview;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public final void subscribe(ObservableEmitter observableEmitter) {
        this.tg.b(observableEmitter);
    }
}
