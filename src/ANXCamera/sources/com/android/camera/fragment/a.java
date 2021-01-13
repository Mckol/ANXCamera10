package com.android.camera.fragment;

import com.android.camera.fragment.lifeCircle.BaseLifecycleListener;
import io.reactivex.functions.Action;

/* compiled from: lambda */
public final /* synthetic */ class a implements Action {
    private final /* synthetic */ BaseLifecycleListener tg;

    public /* synthetic */ a(BaseLifecycleListener baseLifecycleListener) {
        this.tg = baseLifecycleListener;
    }

    @Override // io.reactivex.functions.Action
    public final void run() {
        this.tg.onLifeAlive();
    }
}
