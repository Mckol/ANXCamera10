package com.android.camera.fragment.music;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    private final /* synthetic */ FragmentLiveMusicPager tg;

    public /* synthetic */ b(FragmentLiveMusicPager fragmentLiveMusicPager) {
        this.tg = fragmentLiveMusicPager;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.b((Throwable) obj);
    }
}