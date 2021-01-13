package com.android.camera.fragment.music;

import com.android.camera.resource.tmmusic.TMMusicCatrgoryRequest;
import com.android.camera.resource.tmmusic.TMMusicList;
import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class c implements Function {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        TMMusicList tMMusicList;
        return new TMMusicCatrgoryRequest(tMMusicList.getCategoryID()).startObservable((TMMusicList) obj);
    }
}
