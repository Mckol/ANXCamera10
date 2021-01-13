package com.android.camera.dualvideo;

import com.android.camera.dualvideo.render.IRenderable;
import java.util.Comparator;

/* compiled from: lambda */
public final /* synthetic */ class y implements Comparator {
    public static final /* synthetic */ y INSTANCE = new y();

    private /* synthetic */ y() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return IRenderable.compare((IRenderable) obj, (IRenderable) obj2);
    }
}
