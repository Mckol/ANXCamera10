package com.android.camera.resource;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class SimpleNetworkArchiveRequest<T> extends SimpleNetworkBaseRequest<T> {
    public SimpleNetworkArchiveRequest(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public abstract T parseJson(JSONObject jSONObject, T t) throws BaseRequestException, JSONException;

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.SimpleNetworkBaseRequest
    public T process(String str, T t) throws BaseRequestException {
        try {
            return parseJson(new JSONObject(str), t);
        } catch (JSONException e2) {
            throw new BaseRequestException(2, e2.getMessage(), e2);
        }
    }
}
