package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Gc implements Supplier {
    public static final /* synthetic */ Gc INSTANCE = new Gc();

    private /* synthetic */ Gc() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.de();
    }
}
