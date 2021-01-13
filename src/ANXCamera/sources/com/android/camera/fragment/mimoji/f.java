package com.android.camera.fragment.mimoji;

import com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectAdapter;

/* compiled from: lambda */
public final /* synthetic */ class f implements AutoSelectAdapter.OnSelectListener {
    private final /* synthetic */ FragmentMimojiEdit tg;

    public /* synthetic */ f(FragmentMimojiEdit fragmentMimojiEdit) {
        this.tg = fragmentMimojiEdit;
    }

    @Override // com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectAdapter.OnSelectListener
    public final void onSelectListener(Object obj, int i) {
        this.tg.a((MimojiTypeBean) obj, i);
    }
}
