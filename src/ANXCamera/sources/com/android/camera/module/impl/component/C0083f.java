package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.f  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0083f implements Consumer {
    private final /* synthetic */ int[] tg;

    public /* synthetic */ C0083f(int[] iArr) {
        this.tg = iArr;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.a(this.tg, (BaseModule) obj);
    }
}
