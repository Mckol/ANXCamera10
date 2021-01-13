package com.android.camera.resource.conf;

import android.content.Context;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.cloud.DataCloudItemFeature;
import com.android.camera.network.resource.RequestContracts;
import com.android.camera.resource.SimpleNetworkJsonRequest;
import com.google.android.apps.photos.api.PhotosOemApi;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfLoadRequest extends SimpleNetworkJsonRequest<DataCloudItemFeature> {
    private static final String PARENT_ID = "13641940998946848";

    public ConfLoadRequest() {
        super(RequestContracts.Info.URL);
        addParam("id", PARENT_ID);
    }

    /* access modifiers changed from: protected */
    public DataCloudItemFeature parseJson(JSONObject jSONObject, DataCloudItemFeature dataCloudItemFeature) throws JSONException {
        Context androidContext = CameraAppImpl.getAndroidContext();
        String optString = jSONObject.optJSONObject(PhotosOemApi.PATH_SPECIAL_TYPE_DATA).optJSONArray(RequestContracts.Info.JSON_KEY_ITEMS).optJSONObject(0).optString(RequestContracts.Info.JSON_KEY_EXTRA);
        writeToCache(DataCloudItemFeature.CACHE_INFO, androidContext, optString);
        dataCloudItemFeature.parseJson(new JSONObject(optString));
        return dataCloudItemFeature;
    }
}
