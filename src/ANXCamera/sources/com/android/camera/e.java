package com.android.camera;

import com.google.lens.sdk.LensApi;

/* compiled from: lambda */
public final /* synthetic */ class e implements LensApi.LensAvailabilityCallback {
    private final /* synthetic */ Camera tg;

    public /* synthetic */ e(Camera camera) {
        this.tg = camera;
    }

    @Override // com.google.lens.sdk.LensApi.LensAvailabilityCallback
    public final void onAvailabilityStatusFetched(int i) {
        this.tg.i(i);
    }
}
