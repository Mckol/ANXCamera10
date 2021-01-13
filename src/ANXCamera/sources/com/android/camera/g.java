package com.android.camera;

import com.android.camera.module.loader.NullHolder;
import com.android.camera.module.loader.camera2.Camera2Result;
import io.reactivex.functions.BiFunction;

/* compiled from: lambda */
public final /* synthetic */ class g implements BiFunction {
    private final /* synthetic */ Camera tg;

    public /* synthetic */ g(Camera camera) {
        this.tg = camera;
    }

    @Override // io.reactivex.functions.BiFunction
    public final Object apply(Object obj, Object obj2) {
        return this.tg.a((NullHolder) obj, (Camera2Result) obj2);
    }
}
