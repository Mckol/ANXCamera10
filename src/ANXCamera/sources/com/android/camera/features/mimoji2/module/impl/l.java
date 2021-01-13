package com.android.camera.features.mimoji2.module.impl;

import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    private final /* synthetic */ MimojiModeProtocol.MimojiBottomList tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ l(MimojiModeProtocol.MimojiBottomList mimojiBottomList, boolean z) {
        this.tg = mimojiBottomList;
        this.ug = z;
    }

    public final void run() {
        MimojiAvatarEngine2Impl.a(this.tg, this.ug);
    }
}
