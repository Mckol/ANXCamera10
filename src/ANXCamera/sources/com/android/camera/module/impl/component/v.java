package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class v implements Consumer {
    public static final /* synthetic */ v INSTANCE = new v();

    private /* synthetic */ v() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.k((BaseModule) obj);
    }
}
