package com.android.camera.fragment.top;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    private final /* synthetic */ LabelItemView tg;
    private final /* synthetic */ String ug;
    private final /* synthetic */ String vg;

    public /* synthetic */ o(LabelItemView labelItemView, String str, String str2) {
        this.tg = labelItemView;
        this.ug = str;
        this.vg = str2;
    }

    public final void run() {
        TopExpandAdapter.a(this.tg, this.ug, this.vg);
    }
}
