package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Function;

/* renamed from: com.android.camera.dualvideo.render.b  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0050b implements Function {
    public static final /* synthetic */ C0050b INSTANCE = new C0050b();

    private /* synthetic */ C0050b() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((UserSelectData) obj).mPreviewType);
    }
}
