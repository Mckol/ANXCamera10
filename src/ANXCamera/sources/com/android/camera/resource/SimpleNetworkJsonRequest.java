package com.android.camera.resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class SimpleNetworkJsonRequest<T> extends SimpleNetworkBaseRequest<T> {
    public SimpleNetworkJsonRequest(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public abstract T parseJson(JSONObject jSONObject, @NonNull T t) throws BaseRequestException, JSONException;

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.SimpleNetworkBaseRequest
    public T process(String str, @Nullable T t) throws BaseRequestException {
        try {
            return parseJson(new JSONObject(str), t);
        } catch (Exception e2) {
            e2.printStackTrace();
            throw new BaseRequestException(2, e2.getMessage(), e2);
        }
    }
}
