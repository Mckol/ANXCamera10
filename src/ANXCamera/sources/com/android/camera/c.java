package com.android.camera;

import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.fragment.dialog.BaseDialogFragment;

/* compiled from: lambda */
public final /* synthetic */ class c implements BaseDialogFragment.DismissCallback {
    private final /* synthetic */ Camera tg;
    private final /* synthetic */ DataItemGlobal ug;

    public /* synthetic */ c(Camera camera, DataItemGlobal dataItemGlobal) {
        this.tg = camera;
        this.ug = dataItemGlobal;
    }

    @Override // com.android.camera.fragment.dialog.BaseDialogFragment.DismissCallback
    public final void onDismiss() {
        this.tg.a(this.ug);
    }
}
