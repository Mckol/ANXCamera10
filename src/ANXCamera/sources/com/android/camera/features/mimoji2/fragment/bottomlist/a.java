package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.view.View;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;

/* compiled from: lambda */
public final /* synthetic */ class a implements OnRecyclerItemClickListener {
    private final /* synthetic */ FragmentMimojiBottomList tg;

    public /* synthetic */ a(FragmentMimojiBottomList fragmentMimojiBottomList) {
        this.tg = fragmentMimojiBottomList;
    }

    @Override // com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener
    public final void OnRecyclerItemClickListener(Object obj, int i, View view) {
        this.tg.a((MimojiBgInfo) obj, i, view);
    }
}
