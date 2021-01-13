package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class F implements Predicate {
    private final /* synthetic */ int tg;

    public /* synthetic */ F(int i) {
        this.tg = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoRenderManager.a(this.tg, (UserSelectData) obj);
    }
}
