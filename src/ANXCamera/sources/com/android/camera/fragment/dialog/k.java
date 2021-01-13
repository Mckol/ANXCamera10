package com.android.camera.fragment.dialog;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class k implements View.OnClickListener {
    private final /* synthetic */ LongPressLiveFragment tg;
    private final /* synthetic */ View ug;

    public /* synthetic */ k(LongPressLiveFragment longPressLiveFragment, View view) {
        this.tg = longPressLiveFragment;
        this.ug = view;
    }

    public final void onClick(View view) {
        this.tg.a(this.ug, view);
    }
}
