package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class v implements Predicate {
    private final /* synthetic */ IRenderable tg;

    public /* synthetic */ v(IRenderable iRenderable) {
        this.tg = iRenderable;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.c(this.tg, (UserSelectData) obj);
    }
}
