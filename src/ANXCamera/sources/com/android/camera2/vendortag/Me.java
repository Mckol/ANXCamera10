package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Me implements Supplier {
    public static final /* synthetic */ Me INSTANCE = new Me();

    private /* synthetic */ Me() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.mf();
    }
}
