package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.CameraIDManager;
import com.android.camera.dualvideo.UserSelectData;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.l  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0060l implements Consumer {
    private final /* synthetic */ IRenderable tg;
    private final /* synthetic */ ConcurrentHashMap ug;
    private final /* synthetic */ CameraIDManager vg;

    public /* synthetic */ C0060l(IRenderable iRenderable, ConcurrentHashMap concurrentHashMap, CameraIDManager cameraIDManager) {
        this.tg = iRenderable;
        this.ug = concurrentHashMap;
        this.vg = cameraIDManager;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DualVideoRenderManager.a(this.tg, this.ug, this.vg, (UserSelectData) obj);
    }
}
