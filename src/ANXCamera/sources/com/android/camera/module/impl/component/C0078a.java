package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.a  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0078a implements Consumer {
    public static final /* synthetic */ C0078a INSTANCE = new C0078a();

    private /* synthetic */ C0078a() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(29);
    }
}
