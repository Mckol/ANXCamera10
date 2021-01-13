package com.android.camera.data.data.global;

import java.util.function.ToIntFunction;

/* compiled from: lambda */
public final /* synthetic */ class b implements ToIntFunction {
    public static final /* synthetic */ b INSTANCE = new b();

    private /* synthetic */ b() {
    }

    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((Integer) obj).intValue();
    }
}
