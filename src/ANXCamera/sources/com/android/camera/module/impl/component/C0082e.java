package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.e  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0082e implements Consumer {
    private final /* synthetic */ boolean tg;

    public /* synthetic */ C0082e(boolean z) {
        this.tg = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.b(this.tg, (BaseModule) obj);
    }
}
