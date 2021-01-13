package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class ua implements Function {
    public static final /* synthetic */ ua INSTANCE = new ua();

    private /* synthetic */ ua() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return VideoModule.a((ModeProtocol.DualVideoRenderProtocol) obj);
    }
}
