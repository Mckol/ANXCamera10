package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.view.View;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;

/* compiled from: lambda */
public final /* synthetic */ class b implements OnRecyclerItemClickListener {
    private final /* synthetic */ FragmentMimojiBottomList tg;

    public /* synthetic */ b(FragmentMimojiBottomList fragmentMimojiBottomList) {
        this.tg = fragmentMimojiBottomList;
    }

    @Override // com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener
    public final void OnRecyclerItemClickListener(Object obj, int i, View view) {
        this.tg.a((MimojiInfo2) obj, i, view);
    }
}
