package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ae implements Supplier {
    public static final /* synthetic */ Ae INSTANCE = new Ae();

    private /* synthetic */ Ae() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.df();
    }
}
