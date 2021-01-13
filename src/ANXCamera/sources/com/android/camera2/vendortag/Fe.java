package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Fe implements Supplier {
    public static final /* synthetic */ Fe INSTANCE = new Fe();

    private /* synthetic */ Fe() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.we();
    }
}
