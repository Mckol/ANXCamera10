package com.android.camera.resource;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class a implements ObservableOnSubscribe {
    private final /* synthetic */ BaseObservableRequest tg;
    private final /* synthetic */ Object ug;

    public /* synthetic */ a(BaseObservableRequest baseObservableRequest, Object obj) {
        this.tg = baseObservableRequest;
        this.ug = obj;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public final void subscribe(ObservableEmitter observableEmitter) {
        this.tg.a(this.ug, observableEmitter);
    }
}
