package com.android.camera2;

import java.util.Comparator;
import java.util.HashMap;

/* compiled from: lambda */
public final /* synthetic */ class e implements Comparator {
    private final /* synthetic */ HashMap tg;

    public /* synthetic */ e(HashMap hashMap) {
        this.tg = hashMap;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return MiCamera2.a(this.tg, (Integer) obj, (Integer) obj2);
    }
}
