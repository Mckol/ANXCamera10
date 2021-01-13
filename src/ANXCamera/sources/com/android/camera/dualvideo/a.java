package com.android.camera.dualvideo;

import com.android.camera.dualvideo.CameraIDManager;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class a implements Function {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Float.valueOf(((CameraIDManager.IdZoomMap) obj).mZoom);
    }
}
