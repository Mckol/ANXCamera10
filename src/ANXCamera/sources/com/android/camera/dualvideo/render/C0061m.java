package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/* renamed from: com.android.camera.dualvideo.render.m  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0061m implements Predicate {
    private final /* synthetic */ ConcurrentHashMap tg;

    public /* synthetic */ C0061m(ConcurrentHashMap concurrentHashMap) {
        this.tg = concurrentHashMap;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.d(this.tg, (UserSelectData) obj);
    }
}
