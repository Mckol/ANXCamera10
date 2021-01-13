package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class g implements Predicate {
    public static final /* synthetic */ g INSTANCE = new g();

    private /* synthetic */ g() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.k((IRenderable) obj);
    }
}
