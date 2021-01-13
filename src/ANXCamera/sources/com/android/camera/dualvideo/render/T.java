package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class T implements Predicate {
    private final /* synthetic */ int tg;

    public /* synthetic */ T(int i) {
        this.tg = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return MiscTextureManager.a(this.tg, (IMiscRenderable) obj);
    }
}
