package com.android.camera.features.mimoji2.module.impl;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    private final /* synthetic */ MimojiAvatarEngine2Impl tg;
    private final /* synthetic */ int ug;

    public /* synthetic */ i(MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl, int i) {
        this.tg = mimojiAvatarEngine2Impl;
        this.ug = i;
    }

    public final void run() {
        this.tg.u(this.ug);
    }
}
