package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.CameraIDManager;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.i  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0057i implements Consumer {
    private final /* synthetic */ ArrayList tg;
    private final /* synthetic */ ConcurrentHashMap ug;
    private final /* synthetic */ CameraIDManager vg;

    public /* synthetic */ C0057i(ArrayList arrayList, ConcurrentHashMap concurrentHashMap, CameraIDManager cameraIDManager) {
        this.tg = arrayList;
        this.ug = concurrentHashMap;
        this.vg = cameraIDManager;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        IRenderable iRenderable;
        this.tg.stream().filter(new w(iRenderable)).findFirst().ifPresent(new C0060l((IRenderable) obj, this.ug, this.vg));
    }
}
