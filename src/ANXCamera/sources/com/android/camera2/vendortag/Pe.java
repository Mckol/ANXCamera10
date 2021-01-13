package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Pe implements Supplier {
    public static final /* synthetic */ Pe INSTANCE = new Pe();

    private /* synthetic */ Pe() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.ff();
    }
}
