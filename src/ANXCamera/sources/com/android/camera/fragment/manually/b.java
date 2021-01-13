package com.android.camera.fragment.manually;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    public static final /* synthetic */ b INSTANCE = new b();

    private /* synthetic */ b() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((FragmentManuallyExtra) obj).toUpdateAutoButton();
    }
}
