package com.android.camera.fragment.mimoji;

import android.view.View;
import com.android.camera.fragment.mimoji.MimojiCreateItemAdapter;

/* compiled from: lambda */
public final /* synthetic */ class c implements MimojiCreateItemAdapter.OnItemClickListener {
    private final /* synthetic */ FragmentMimoji tg;

    public /* synthetic */ c(FragmentMimoji fragmentMimoji) {
        this.tg = fragmentMimoji;
    }

    @Override // com.android.camera.fragment.mimoji.MimojiCreateItemAdapter.OnItemClickListener
    public final void onItemClick(MimojiInfo mimojiInfo, int i, View view) {
        this.tg.a(mimojiInfo, i, view);
    }
}
