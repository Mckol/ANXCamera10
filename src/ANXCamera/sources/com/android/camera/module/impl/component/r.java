package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class r implements Consumer {
    private final /* synthetic */ ConfigChangeImpl tg;

    public /* synthetic */ r(ConfigChangeImpl configChangeImpl) {
        this.tg = configChangeImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.a((BaseModule) obj);
    }
}
