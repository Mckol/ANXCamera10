package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ia implements Supplier {
    public static final /* synthetic */ Ia INSTANCE = new Ia();

    private /* synthetic */ Ia() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.ae();
    }
}
