package com.android.camera.module.interceptor;

import android.hardware.camera2.CaptureResult;

public abstract class BaseASDInterceptor extends BaseModuleInterceptor {
    /* access modifiers changed from: protected */
    public abstract void conditionDect();

    @Override // com.android.camera.module.interceptor.BaseModuleInterceptor
    public int getPriority() {
        return 0;
    }

    @Override // com.android.camera.module.interceptor.BaseModuleInterceptor
    public int getScope() {
        return 6;
    }

    /* access modifiers changed from: protected */
    public abstract int getType();

    /* access modifiers changed from: protected */
    public abstract void onCaptureResultNext(CaptureResult captureResult);
}
