package com.android.camera.fragment.bottom.action;

import android.view.View;
import com.android.camera.ui.AdjustAnimationView;
import com.android.camera.ui.AnimationView;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ AdjustAnimationView tg;
    private final /* synthetic */ AnimationView ug;
    private final /* synthetic */ View vg;
    private final /* synthetic */ float wg;
    private final /* synthetic */ float xg;

    public /* synthetic */ b(AdjustAnimationView adjustAnimationView, AnimationView animationView, View view, float f, float f2) {
        this.tg = adjustAnimationView;
        this.ug = animationView;
        this.vg = view;
        this.wg = f;
        this.xg = f2;
    }

    public final void run() {
        FragmentBottomAction.a(this.tg, this.ug, this.vg, this.wg, this.xg);
    }
}
