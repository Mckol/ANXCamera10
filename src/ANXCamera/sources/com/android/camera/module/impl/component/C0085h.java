package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.h  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0085h implements Consumer {
    public static final /* synthetic */ C0085h INSTANCE = new C0085h();

    private /* synthetic */ C0085h() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(11, 37);
    }
}