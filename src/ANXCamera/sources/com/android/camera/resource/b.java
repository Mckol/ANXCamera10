package com.android.camera.resource;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class b implements ObservableOnSubscribe {
    private final /* synthetic */ BaseObservableRequest tg;
    private final /* synthetic */ Class ug;

    public /* synthetic */ b(BaseObservableRequest baseObservableRequest, Class cls) {
        this.tg = baseObservableRequest;
        this.ug = cls;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public final void subscribe(ObservableEmitter observableEmitter) {
        this.tg.a(this.ug, observableEmitter);
    }
}
