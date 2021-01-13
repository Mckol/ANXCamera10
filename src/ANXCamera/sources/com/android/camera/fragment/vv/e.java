package com.android.camera.fragment.vv;

import com.android.camera.module.loader.NullHolder;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    private final /* synthetic */ FragmentVVProcess tg;

    public /* synthetic */ e(FragmentVVProcess fragmentVVProcess) {
        this.tg = fragmentVVProcess;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.b((NullHolder) obj);
    }
}
