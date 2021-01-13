package com.android.camera.features.mimoji2.widget.baseview;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    private final /* synthetic */ OnRecyclerItemClickListener tg;
    private final /* synthetic */ Object ug;
    private final /* synthetic */ int vg;
    private final /* synthetic */ View wg;

    public /* synthetic */ a(OnRecyclerItemClickListener onRecyclerItemClickListener, Object obj, int i, View view) {
        this.tg = onRecyclerItemClickListener;
        this.ug = obj;
        this.vg = i;
        this.wg = view;
    }

    public final void onClick(View view) {
        this.tg.OnRecyclerItemClickListener(this.ug, this.vg, this.wg);
    }
}
