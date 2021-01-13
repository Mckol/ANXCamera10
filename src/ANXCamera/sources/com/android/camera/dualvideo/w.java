package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class w implements Predicate {
    public static final /* synthetic */ w INSTANCE = new w();

    private /* synthetic */ w() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.f((IRenderable) obj);
    }
}
