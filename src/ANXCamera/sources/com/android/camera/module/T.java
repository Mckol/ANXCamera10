package com.android.camera.module;

import com.android.camera.dualvideo.UserSelectData;
import java.util.Comparator;

/* compiled from: lambda */
public final /* synthetic */ class T implements Comparator {
    public static final /* synthetic */ T INSTANCE = new T();

    private /* synthetic */ T() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return DualVideoModule.a((UserSelectData) obj, (UserSelectData) obj2);
    }
}
