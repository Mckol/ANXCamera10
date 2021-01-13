package com.android.camera.fragment.manually;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements Consumer {
    public static final /* synthetic */ d INSTANCE = new d();

    private /* synthetic */ d() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((FragmentManuallyExtra) obj).toUpdateAutoButton();
    }
}
