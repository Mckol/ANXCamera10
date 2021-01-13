package com.android.camera.fragment.mimoji;

import com.android.camera.fragment.mimoji.EditLevelListAdapter;

/* compiled from: lambda */
public final /* synthetic */ class e implements EditLevelListAdapter.ItfGvOnItemClickListener {
    private final /* synthetic */ FragmentMimojiEdit tg;

    public /* synthetic */ e(FragmentMimojiEdit fragmentMimojiEdit) {
        this.tg = fragmentMimojiEdit;
    }

    @Override // com.android.camera.fragment.mimoji.EditLevelListAdapter.ItfGvOnItemClickListener
    public final void notifyUIChanged() {
        this.tg.Sa();
    }
}
