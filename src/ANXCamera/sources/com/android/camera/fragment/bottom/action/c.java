package com.android.camera.fragment.bottom.action;

import android.view.View;
import com.android.camera.ui.AdjustAnimationView;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    private final /* synthetic */ AdjustAnimationView tg;
    private final /* synthetic */ View ug;

    public /* synthetic */ c(AdjustAnimationView adjustAnimationView, View view) {
        this.tg = adjustAnimationView;
        this.ug = view;
    }

    public final void run() {
        FragmentBottomAction.a(this.tg, this.ug);
    }
}
