package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.b  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0079b implements Consumer {
    public static final /* synthetic */ C0079b INSTANCE = new C0079b();

    private /* synthetic */ C0079b() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(11, 10, 37);
    }
}
