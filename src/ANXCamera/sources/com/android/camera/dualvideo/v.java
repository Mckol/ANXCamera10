package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class v implements Predicate {
    public static final /* synthetic */ v INSTANCE = new v();

    private /* synthetic */ v() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.c((IRenderable) obj);
    }
}
