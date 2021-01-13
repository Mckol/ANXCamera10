package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class ra implements Function {
    public static final /* synthetic */ ra INSTANCE = new ra();

    private /* synthetic */ ra() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return VideoModule.c((ModeProtocol.DualVideoRenderProtocol) obj);
    }
}
