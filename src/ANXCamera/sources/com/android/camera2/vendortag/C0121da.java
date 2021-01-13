package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* renamed from: com.android.camera2.vendortag.da  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0121da implements Supplier {
    public static final /* synthetic */ C0121da INSTANCE = new C0121da();

    private /* synthetic */ C0121da() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CameraCharacteristicsVendorTags.Me();
    }
}
