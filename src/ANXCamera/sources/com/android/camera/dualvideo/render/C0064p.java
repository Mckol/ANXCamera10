package com.android.camera.dualvideo.render;

import com.android.camera.log.Log;
import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.p  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0064p implements Consumer {
    public static final /* synthetic */ C0064p INSTANCE = new C0064p();

    private /* synthetic */ C0064p() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        IRenderable iRenderable = (IRenderable) obj;
        Log.d(DualVideoRenderManager.TAG, "printRenderList: " + iRenderable.toString());
    }
}
