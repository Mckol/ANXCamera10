package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* renamed from: com.android.camera.module.impl.component.c  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0080c implements Consumer {
    private final /* synthetic */ int[] tg;

    public /* synthetic */ C0080c(int[] iArr) {
        this.tg = iArr;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(this.tg);
    }
}
