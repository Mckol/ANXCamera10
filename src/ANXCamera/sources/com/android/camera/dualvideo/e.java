package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class e implements Predicate {
    public static final /* synthetic */ e INSTANCE = new e();

    private /* synthetic */ e() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.h((IRenderable) obj);
    }
}
