package com.android.camera.module.loader.camera2;

import io.reactivex.functions.Function;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: lambda */
public final /* synthetic */ class b implements Function {
    private final /* synthetic */ ConcurrentHashMap tg;

    public /* synthetic */ b(ConcurrentHashMap concurrentHashMap) {
        this.tg = concurrentHashMap;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return Camera2OpenManager.a(this.tg, (Camera2Result) obj);
    }
}
