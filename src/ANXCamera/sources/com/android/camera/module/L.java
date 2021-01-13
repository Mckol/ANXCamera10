package com.android.camera.module;

import com.android.camera.protocol.ModeProtocol;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class L implements Function {
    public static final /* synthetic */ L INSTANCE = new L();

    private /* synthetic */ L() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return DualVideoModule.d((ModeProtocol.DualVideoRenderProtocol) obj);
    }
}
