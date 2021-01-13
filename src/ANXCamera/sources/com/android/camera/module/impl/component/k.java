package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class k implements Consumer {
    private final /* synthetic */ boolean tg;

    public /* synthetic */ k(boolean z) {
        this.tg = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.d(this.tg, (BaseModule) obj);
    }
}
