package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class c implements Function {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return DualVideoControler.e((IRenderable) obj);
    }
}
