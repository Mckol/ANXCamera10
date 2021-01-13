package com.android.camera.fragment.manually;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class c implements Consumer {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((FragmentManuallyExtra) obj).toUpdateAutoButton();
    }
}
