package com.android.camera.fragment;

import com.android.camera.fragment.EffectItemAdapter;
import com.android.camera.ui.GLTextureView;
import javax.microedition.khronos.egl.EGLContext;

/* compiled from: lambda */
public final /* synthetic */ class d implements GLTextureView.EGLShareContextGetter {
    private final /* synthetic */ EffectItemAdapter.EffectRealtimeItemHolder tg;

    public /* synthetic */ d(EffectItemAdapter.EffectRealtimeItemHolder effectRealtimeItemHolder) {
        this.tg = effectRealtimeItemHolder;
    }

    @Override // com.android.camera.ui.GLTextureView.EGLShareContextGetter
    public final EGLContext getShareContext() {
        return this.tg.ab();
    }
}
