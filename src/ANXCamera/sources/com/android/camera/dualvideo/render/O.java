package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class O implements Function {
    public static final /* synthetic */ O INSTANCE = new O();

    private /* synthetic */ O() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((UserSelectData) obj).getIndex());
    }
}
