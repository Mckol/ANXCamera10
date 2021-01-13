package com.android.camera.fragment.manually;

import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentManuallyWB;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    private final /* synthetic */ ComponentData tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ e(ComponentData componentData, boolean z) {
        this.tg = componentData;
        this.ug = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        FragmentManuallyExtra fragmentManuallyExtra = (FragmentManuallyExtra) obj;
        fragmentManuallyExtra.showCustomWB((ComponentManuallyWB) this.tg, this.ug);
    }
}
