package com.android.camera2;

import android.hardware.camera2.params.StreamConfiguration;
import com.android.camera.CameraSize;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    private final /* synthetic */ List tg;

    public /* synthetic */ a(List list) {
        this.tg = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.tg.add(new CameraSize(((StreamConfiguration) obj).getSize()));
    }
}
