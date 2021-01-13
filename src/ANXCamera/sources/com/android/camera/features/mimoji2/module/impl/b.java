package com.android.camera.features.mimoji2.module.impl;

import com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl;
import java.nio.ByteBuffer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ MimojiAvatarEngine2Impl.AnonymousClass3 tg;
    private final /* synthetic */ ByteBuffer ug;

    public /* synthetic */ b(MimojiAvatarEngine2Impl.AnonymousClass3 r1, ByteBuffer byteBuffer) {
        this.tg = r1;
        this.ug = byteBuffer;
    }

    public final void run() {
        this.tg.a(this.ug);
    }
}
