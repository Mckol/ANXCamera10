package com.android.camera.fragment.beauty;

import android.view.View;
import android.widget.AdapterView;

/* compiled from: lambda */
public final /* synthetic */ class a implements AdapterView.OnItemClickListener {
    private final /* synthetic */ RemodelingParamsFragment tg;

    public /* synthetic */ a(RemodelingParamsFragment remodelingParamsFragment) {
        this.tg = remodelingParamsFragment;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.tg.a(adapterView, view, i, j);
    }
}
