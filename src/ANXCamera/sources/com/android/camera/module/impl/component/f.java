package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class f implements Consumer {
    public static final /* synthetic */ f INSTANCE = new f();

    private /* synthetic */ f() {
    }

    public final void accept(Object obj) {
        ConfigChangeImpl.j((BaseModule) obj);
    }
}
