package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class i implements Predicate {
    private final /* synthetic */ float tg;
    private final /* synthetic */ float ug;

    public /* synthetic */ i(float f, float f2) {
        this.tg = f;
        this.ug = f2;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoControler.b(this.tg, this.ug, (IRenderable) obj);
    }
}