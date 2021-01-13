package com.android.camera.module;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class V implements Predicate {
    public static final /* synthetic */ V INSTANCE = new V();

    private /* synthetic */ V() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return DualVideoModule.e((UserSelectData) obj);
    }
}
