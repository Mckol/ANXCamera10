package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class s implements Consumer {
    public static final /* synthetic */ s INSTANCE = new s();

    private /* synthetic */ s() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).resetAiSceneInDocumentModeOn();
    }
}
