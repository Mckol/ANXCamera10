package com.android.camera;

import com.android.camera.module.loader.NullHolder;
import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class f implements Function {
    private final /* synthetic */ Camera tg;

    public /* synthetic */ f(Camera camera) {
        this.tg = camera;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.tg.a((NullHolder) obj);
    }
}
