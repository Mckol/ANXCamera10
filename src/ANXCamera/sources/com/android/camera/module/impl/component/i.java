package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class i implements Consumer {
    public static final /* synthetic */ i INSTANCE = new i();

    private /* synthetic */ i() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(45);
    }
}
