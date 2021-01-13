package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class d implements Predicate {
    public static final /* synthetic */ d INSTANCE = new d();

    private /* synthetic */ d() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.j((IRenderable) obj);
    }
}
