package com.android.camera.module;

import com.android.camera.fragment.GoogleLensFragment;

/* renamed from: com.android.camera.module.l  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0088l implements GoogleLensFragment.OnClickListener {
    private final /* synthetic */ Camera2Module tg;
    private final /* synthetic */ float ug;
    private final /* synthetic */ float vg;
    private final /* synthetic */ int wg;
    private final /* synthetic */ int xg;

    public /* synthetic */ C0088l(Camera2Module camera2Module, float f, float f2, int i, int i2) {
        this.tg = camera2Module;
        this.ug = f;
        this.vg = f2;
        this.wg = i;
        this.xg = i2;
    }

    @Override // com.android.camera.fragment.GoogleLensFragment.OnClickListener
    public final void onOptionClick(int i) {
        this.tg.a(this.ug, this.vg, this.wg, this.xg, i);
    }
}
