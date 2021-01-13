package com.android.camera.features.mimoji2.module.impl;

import java.nio.ByteBuffer;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    private final /* synthetic */ MimojiAvatarEngine2Impl tg;
    private final /* synthetic */ ByteBuffer ug;
    private final /* synthetic */ int vg;
    private final /* synthetic */ int wg;

    public /* synthetic */ g(MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl, ByteBuffer byteBuffer, int i, int i2) {
        this.tg = mimojiAvatarEngine2Impl;
        this.ug = byteBuffer;
        this.vg = i;
        this.wg = i2;
    }

    public final void run() {
        this.tg.a(this.ug, this.vg, this.wg);
    }
}
