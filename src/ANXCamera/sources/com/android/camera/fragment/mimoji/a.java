package com.android.camera.fragment.mimoji;

import android.view.View;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;
import com.arcsoft.avatar.AvatarConfig;

/* compiled from: lambda */
public final /* synthetic */ class a implements OnRecyclerItemClickListener {
    private final /* synthetic */ EditLevelListAdapter tg;
    private final /* synthetic */ MimojiThumbnailRecyclerAdapter ug;
    private final /* synthetic */ int vg;

    public /* synthetic */ a(EditLevelListAdapter editLevelListAdapter, MimojiThumbnailRecyclerAdapter mimojiThumbnailRecyclerAdapter, int i) {
        this.tg = editLevelListAdapter;
        this.ug = mimojiThumbnailRecyclerAdapter;
        this.vg = i;
    }

    @Override // com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener
    public final void OnRecyclerItemClickListener(Object obj, int i, View view) {
        this.tg.a(this.ug, this.vg, (AvatarConfig.ASAvatarConfigInfo) obj, i, view);
    }
}
