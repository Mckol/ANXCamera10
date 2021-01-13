package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class E implements Predicate {
    private final /* synthetic */ ConcurrentHashMap tg;

    public /* synthetic */ E(ConcurrentHashMap concurrentHashMap) {
        this.tg = concurrentHashMap;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.c(this.tg, (UserSelectData) obj);
    }
}
