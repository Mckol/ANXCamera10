package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class p implements Function {
    public static final /* synthetic */ p INSTANCE = new p();

    private /* synthetic */ p() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return DualVideoControler.i((IRenderable) obj);
    }
}
