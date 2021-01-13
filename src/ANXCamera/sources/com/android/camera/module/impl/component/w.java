package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class w implements Consumer {
    private final /* synthetic */ ConfigChangeImpl tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ w(ConfigChangeImpl configChangeImpl, boolean z) {
        this.tg = configChangeImpl;
        this.ug = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.a(this.ug, (BaseModule) obj);
    }
}
