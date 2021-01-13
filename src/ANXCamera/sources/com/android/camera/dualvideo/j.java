package com.android.camera.dualvideo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class j implements Predicate {
    private final /* synthetic */ ConcurrentHashMap tg;

    public /* synthetic */ j(ConcurrentHashMap concurrentHashMap) {
        this.tg = concurrentHashMap;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.a(this.tg, (UserSelectData) obj);
    }
}
