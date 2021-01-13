package com.android.camera.dualvideo.render;

import java.util.Comparator;

/* compiled from: lambda */
public final /* synthetic */ class U implements Comparator {
    public static final /* synthetic */ U INSTANCE = new U();

    private /* synthetic */ U() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return IRenderable.compare((IRenderable) obj, (IRenderable) obj2);
    }
}
