package com.android.camera.fragment.music;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    private final /* synthetic */ FragmentLiveMusicPager tg;

    public /* synthetic */ e(FragmentLiveMusicPager fragmentLiveMusicPager) {
        this.tg = fragmentLiveMusicPager;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.c((Throwable) obj);
    }
}
