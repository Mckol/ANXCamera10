package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.render.RegionHelper;

/* renamed from: com.android.camera.dualvideo.render.k  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0059k implements RegionHelper.UpdatedListener {
    private final /* synthetic */ DualVideoRenderManager tg;

    public /* synthetic */ C0059k(DualVideoRenderManager dualVideoRenderManager) {
        this.tg = dualVideoRenderManager;
    }

    @Override // com.android.camera.dualvideo.render.RegionHelper.UpdatedListener
    public final void onUpdated() {
        this.tg.Ec();
    }
}
