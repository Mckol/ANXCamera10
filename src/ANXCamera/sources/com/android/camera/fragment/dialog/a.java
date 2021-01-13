package com.android.camera.fragment.dialog;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnTouchListener {
    private final /* synthetic */ BaseDialogFragment tg;

    public /* synthetic */ a(BaseDialogFragment baseDialogFragment) {
        this.tg = baseDialogFragment;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return this.tg.a(view, motionEvent);
    }
}
