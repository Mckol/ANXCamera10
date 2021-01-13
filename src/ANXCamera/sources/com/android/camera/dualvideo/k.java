package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class k implements Function {
    public static final /* synthetic */ k INSTANCE = new k();

    private /* synthetic */ k() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return DualVideoControler.g((IRenderable) obj);
    }
}
