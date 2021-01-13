package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class w implements Predicate {
    private final /* synthetic */ IRenderable tg;

    public /* synthetic */ w(IRenderable iRenderable) {
        this.tg = iRenderable;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.e(this.tg, (UserSelectData) obj);
    }
}
