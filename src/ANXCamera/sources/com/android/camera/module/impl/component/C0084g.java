package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.g  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0084g implements Consumer {
    public static final /* synthetic */ C0084g INSTANCE = new C0084g();

    private /* synthetic */ C0084g() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(10);
    }
}
