package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.d  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0081d implements Consumer {
    private final /* synthetic */ ConfigChangeImpl tg;

    public /* synthetic */ C0081d(ConfigChangeImpl configChangeImpl) {
        this.tg = configChangeImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.h((BaseModule) obj);
    }
}
