package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class o implements Consumer {
    public static final /* synthetic */ o INSTANCE = new o();

    private /* synthetic */ o() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.g((BaseModule) obj);
    }
}
