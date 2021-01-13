package com.android.camera.data.data.global;

import java.util.function.ToIntFunction;

/* compiled from: lambda */
public final /* synthetic */ class a implements ToIntFunction {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return Integer.parseInt(((String) obj).trim());
    }
}
