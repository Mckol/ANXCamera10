package com.android.camera.module;

import android.view.MotionEvent;
import com.android.camera.dualvideo.TouchEventView;

/* compiled from: lambda */
public final /* synthetic */ class O implements TouchEventView.TouchEventListener {
    private final /* synthetic */ DualVideoModule tg;

    public /* synthetic */ O(DualVideoModule dualVideoModule) {
        this.tg = dualVideoModule;
    }

    @Override // com.android.camera.dualvideo.TouchEventView.TouchEventListener
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return this.tg.a(motionEvent);
    }
}
