package com.android.camera.dualvideo;

import com.android.camera.dualvideo.CameraIDManager;
import java.util.function.Predicate;

/* compiled from: lambda */
public final /* synthetic */ class b implements Predicate {
    private final /* synthetic */ int tg;

    public /* synthetic */ b(int i) {
        this.tg = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return CameraIDManager.a(this.tg, (CameraIDManager.IdZoomMap) obj);
    }
}
