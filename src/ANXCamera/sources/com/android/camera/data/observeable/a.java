package com.android.camera.data.observeable;

import com.android.camera.fragment.vv.VVList;
import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class a implements Function {
    private final /* synthetic */ VlogViewModel tg;

    public /* synthetic */ a(VlogViewModel vlogViewModel) {
        this.tg = vlogViewModel;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.tg.b((VVList) obj);
    }
}
