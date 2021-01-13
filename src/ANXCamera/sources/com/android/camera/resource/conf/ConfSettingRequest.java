package com.android.camera.resource.conf;

import android.content.Context;
import android.provider.MiuiSettings;
import android.text.TextUtils;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.cloud.DataCloudItemFeature;
import com.android.camera.network.resource.RequestContracts;
import com.android.camera.resource.SimpleParseRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfSettingRequest extends SimpleParseRequest<DataCloudItemFeature> {
    private static final String CLOUD_DATA_MODULE_NAME = "camera_v4";

    private static final String getCloudDataString(String str, String str2, String str3) {
        return MiuiSettings.SettingsCloudData.getCloudDataString(CameraAppImpl.getAndroidContext().getContentResolver(), str, str2, str3);
    }

    /* access modifiers changed from: protected */
    public void processParse(DataCloudItemFeature dataCloudItemFeature) throws JSONException {
        Context androidContext = CameraAppImpl.getAndroidContext();
        String cloudDataString = getCloudDataString(CLOUD_DATA_MODULE_NAME, RequestContracts.Info.JSON_KEY_EXTRA, null);
        if (!TextUtils.isEmpty(cloudDataString)) {
            writeToCache(DataCloudItemFeature.CACHE_INFO, androidContext, cloudDataString);
            dataCloudItemFeature.parseJson(new JSONObject(cloudDataString));
        }
    }
}
