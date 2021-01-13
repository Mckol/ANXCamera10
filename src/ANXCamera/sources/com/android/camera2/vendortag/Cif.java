package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* renamed from: com.android.camera2.vendortag.if  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class Cif implements Supplier {
    public static final /* synthetic */ Cif INSTANCE = new Cif();

    private /* synthetic */ Cif() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.me();
    }
}
