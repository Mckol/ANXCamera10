package com.android.camera.features.mimoji2.fragment.edit;

import com.android.camera.features.mimoji2.fragment.edit.EditLevelListAdapter2;

/* compiled from: lambda */
public final /* synthetic */ class e implements EditLevelListAdapter2.ItfGvOnItemClickListener {
    private final /* synthetic */ FragmentMimojiEdit2 tg;

    public /* synthetic */ e(FragmentMimojiEdit2 fragmentMimojiEdit2) {
        this.tg = fragmentMimojiEdit2;
    }

    @Override // com.android.camera.features.mimoji2.fragment.edit.EditLevelListAdapter2.ItfGvOnItemClickListener
    public final void notifyUIChanged() {
        this.tg.wa();
    }
}
