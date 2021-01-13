package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Od implements Supplier {
    public static final /* synthetic */ Od INSTANCE = new Od();

    private /* synthetic */ Od() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.xe();
    }
}
