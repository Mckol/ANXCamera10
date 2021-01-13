package com.android.camera.dualvideo.render;

import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class P implements Predicate {
    private final /* synthetic */ int tg;

    public /* synthetic */ P(int i) {
        this.tg = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return MiscTextureManager.b(this.tg, (IMiscRenderable) obj);
    }
}
