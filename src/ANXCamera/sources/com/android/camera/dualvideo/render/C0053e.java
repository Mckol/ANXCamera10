package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Function;

/* renamed from: com.android.camera.dualvideo.render.e  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0053e implements Function {
    public static final /* synthetic */ C0053e INSTANCE = new C0053e();

    private /* synthetic */ C0053e() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((UserSelectData) obj).mPreviewType);
    }
}
