package com.android.camera.module.loader.camera2;

import com.android.camera.data.cloud.DataCloudItemFeature;
import com.android.camera.log.Log;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements Consumer {
    public static final /* synthetic */ d INSTANCE = new d();

    private /* synthetic */ d() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        DataCloudItemFeature dataCloudItemFeature = (DataCloudItemFeature) obj;
        Log.d("loadSettings:", dataCloudItemFeature.getVersion() + "");
    }
}
