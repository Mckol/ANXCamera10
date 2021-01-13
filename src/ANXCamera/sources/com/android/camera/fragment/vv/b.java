package com.android.camera.fragment.vv;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    private final /* synthetic */ FragmentVVGallery tg;

    public /* synthetic */ b(FragmentVVGallery fragmentVVGallery) {
        this.tg = fragmentVVGallery;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.a((VVList) obj);
    }
}
