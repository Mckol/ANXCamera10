package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class oa implements Function {
    public static final /* synthetic */ oa INSTANCE = new oa();

    private /* synthetic */ oa() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return VideoModule.b((ModeProtocol.DualVideoRenderProtocol) obj);
    }
}
