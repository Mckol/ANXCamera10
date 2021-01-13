package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Be implements Supplier {
    public static final /* synthetic */ Be INSTANCE = new Be();

    private /* synthetic */ Be() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.re();
    }
}
