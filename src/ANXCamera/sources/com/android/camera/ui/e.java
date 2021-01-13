package com.android.camera.ui;

import android.view.View;
import com.android.camera.ui.ModeSelectView;

/* compiled from: lambda */
public final /* synthetic */ class e implements View.OnClickListener {
    private final /* synthetic */ ModeSelectView.ModeSelectAdapter tg;

    public /* synthetic */ e(ModeSelectView.ModeSelectAdapter modeSelectAdapter) {
        this.tg = modeSelectAdapter;
    }

    public final void onClick(View view) {
        this.tg.j(view);
    }
}
