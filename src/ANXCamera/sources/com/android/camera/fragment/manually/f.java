package com.android.camera.fragment.manually;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class f implements Consumer {
    public static final /* synthetic */ f INSTANCE = new f();

    private /* synthetic */ f() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((FragmentManuallyExtra) obj).toUpdateAutoButton();
    }
}
