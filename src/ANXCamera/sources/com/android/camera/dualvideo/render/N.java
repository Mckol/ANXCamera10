package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class N implements Predicate {
    private final /* synthetic */ int tg;

    public /* synthetic */ N(int i) {
        this.tg = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoUtil.b(this.tg, (UserSelectData) obj);
    }
}
