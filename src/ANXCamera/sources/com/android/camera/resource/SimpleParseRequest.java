package com.android.camera.resource;

import com.android.camera.resource.BaseResourceCacheable;
import org.json.JSONException;

public abstract class SimpleParseRequest<T extends BaseResourceCacheable> extends BaseObservableRequest<T> {
    /* access modifiers changed from: protected */
    public abstract void processParse(T t) throws JSONException;

    /* access modifiers changed from: protected */
    public void scheduleRequest(ResponseListener<T> responseListener, T t) {
        try {
            processParse(t);
            responseListener.onResponse(t, false);
        } catch (Exception e2) {
            e2.printStackTrace();
            responseListener.onResponse(null, true);
        }
    }
}
