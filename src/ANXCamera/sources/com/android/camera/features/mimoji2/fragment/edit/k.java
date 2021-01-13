package com.android.camera.features.mimoji2.fragment.edit;

import android.view.View;
import com.android.camera.features.mimoji2.bean.MimojiEmoticonInfo;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;

/* compiled from: lambda */
public final /* synthetic */ class k implements OnRecyclerItemClickListener {
    private final /* synthetic */ FragmentMimojiEmoticon tg;

    public /* synthetic */ k(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.tg = fragmentMimojiEmoticon;
    }

    @Override // com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener
    public final void OnRecyclerItemClickListener(Object obj, int i, View view) {
        this.tg.a((MimojiEmoticonInfo) obj, i, view);
    }
}
