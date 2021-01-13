package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class u implements Consumer {
    public static final /* synthetic */ u INSTANCE = new u();

    private /* synthetic */ u() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.i((BaseModule) obj);
    }
}
