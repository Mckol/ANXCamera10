package com.android.camera.features.mimoji2.fragment.edit;

import android.view.View;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;
import com.arcsoft.avatar2.AvatarConfig;

/* compiled from: lambda */
public final /* synthetic */ class a implements OnRecyclerItemClickListener {
    private final /* synthetic */ EditLevelListAdapter2 tg;
    private final /* synthetic */ MimojiThumbnailRecyclerAdapter2 ug;
    private final /* synthetic */ int vg;

    public /* synthetic */ a(EditLevelListAdapter2 editLevelListAdapter2, MimojiThumbnailRecyclerAdapter2 mimojiThumbnailRecyclerAdapter2, int i) {
        this.tg = editLevelListAdapter2;
        this.ug = mimojiThumbnailRecyclerAdapter2;
        this.vg = i;
    }

    @Override // com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener
    public final void OnRecyclerItemClickListener(Object obj, int i, View view) {
        this.tg.a(this.ug, this.vg, (AvatarConfig.ASAvatarConfigInfo) obj, i, view);
    }
}
