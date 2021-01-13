package com.android.camera.module;

import com.android.camera.module.AmbilightModule;

/* renamed from: com.android.camera.module.c  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0071c implements AmbilightModule.SaveStateCallback {
    private final /* synthetic */ AmbilightModule tg;

    public /* synthetic */ C0071c(AmbilightModule ambilightModule) {
        this.tg = ambilightModule;
    }

    @Override // com.android.camera.module.AmbilightModule.SaveStateCallback
    public final void onSaveCompleted() {
        this.tg.fd();
    }
}
