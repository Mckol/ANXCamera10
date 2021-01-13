package com.android.camera.module;

import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera2.Camera2Proxy;

/* compiled from: lambda */
public final /* synthetic */ class K implements Camera2Proxy.FocusCallback {
    private final /* synthetic */ DualVideoModule tg;

    public /* synthetic */ K(DualVideoModule dualVideoModule) {
        this.tg = dualVideoModule;
    }

    @Override // com.android.camera2.Camera2Proxy.FocusCallback
    public final void onFocusStateChanged(FocusTask focusTask) {
        this.tg.a(focusTask);
    }
}
