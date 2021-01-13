package com.android.camera.module;

import com.android.camera.data.observeable.RxData;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class ba implements Consumer {
    private final /* synthetic */ LiveModuleSubVV tg;

    public /* synthetic */ ba(LiveModuleSubVV liveModuleSubVV) {
        this.tg = liveModuleSubVV;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.tg.b((RxData.DataWrap) obj);
    }
}
