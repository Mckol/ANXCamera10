package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class h implements Predicate {
    public static final /* synthetic */ h INSTANCE = new h();

    private /* synthetic */ h() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.a((IRenderable) obj);
    }
}
