package com.android.camera.fragment.dialog;

import com.android.camera.fragment.fullscreen.ShareInfo;
import java.util.Comparator;

/* compiled from: lambda */
public final /* synthetic */ class c implements Comparator {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return FragmentLiveReview.a((ShareInfo) obj, (ShareInfo) obj2);
    }
}
