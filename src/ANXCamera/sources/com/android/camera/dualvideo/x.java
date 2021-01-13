package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class x implements Function {
    public static final /* synthetic */ x INSTANCE = new x();

    private /* synthetic */ x() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((IRenderable) obj).getRenderComposeType());
    }
}
