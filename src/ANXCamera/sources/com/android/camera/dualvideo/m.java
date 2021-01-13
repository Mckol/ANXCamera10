package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class m implements Function {
    private final /* synthetic */ DualVideoControler tg;

    public /* synthetic */ m(DualVideoControler dualVideoControler) {
        this.tg = dualVideoControler;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.tg.b((IRenderable) obj);
    }
}
