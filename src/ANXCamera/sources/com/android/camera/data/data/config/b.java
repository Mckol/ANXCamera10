package com.android.camera.data.data.config;

import com.android.camera.data.data.ComponentDataItem;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class b implements Predicate {
    private final /* synthetic */ String tg;

    public /* synthetic */ b(String str) {
        this.tg = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ComponentManuallyISO.b(this.tg, (ComponentDataItem) obj);
    }
}
