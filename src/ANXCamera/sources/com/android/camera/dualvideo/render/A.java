package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class A implements Function {
    public static final /* synthetic */ A INSTANCE = new A();

    private /* synthetic */ A() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((UserSelectData) obj).mPreviewType);
    }
}
