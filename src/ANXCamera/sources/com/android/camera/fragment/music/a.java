package com.android.camera.fragment.music;

import io.reactivex.functions.Consumer;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    private final /* synthetic */ FragmentLiveMusicPager tg;

    public /* synthetic */ a(FragmentLiveMusicPager fragmentLiveMusicPager) {
        this.tg = fragmentLiveMusicPager;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.c((List) obj);
    }
}
