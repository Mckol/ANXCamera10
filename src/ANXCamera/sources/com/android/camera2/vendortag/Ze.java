package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Ze implements Supplier {
    public static final /* synthetic */ Ze INSTANCE = new Ze();

    private /* synthetic */ Ze() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.ae();
    }
}
