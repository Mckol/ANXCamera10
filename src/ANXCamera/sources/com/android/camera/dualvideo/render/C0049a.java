package com.android.camera.dualvideo.render;

import com.android.camera.dualvideo.UserSelectData;
import java.util.function.Consumer;

/* renamed from: com.android.camera.dualvideo.render.a  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0049a implements Consumer {
    private final /* synthetic */ IRenderable tg;

    public /* synthetic */ C0049a(IRenderable iRenderable) {
        this.tg = iRenderable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.setSelectedtypeWithAnim(((UserSelectData) obj).getIndex(), true);
    }
}
