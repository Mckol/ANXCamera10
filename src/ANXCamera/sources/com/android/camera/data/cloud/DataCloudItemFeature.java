package com.android.camera.data.cloud;

import androidx.collection.SimpleArrayMap;
import com.android.camera.resource.BaseResourceCacheable;
import java.util.Iterator;
import org.json.JSONObject;

public class DataCloudItemFeature extends DataCloudItemBase implements BaseResourceCacheable {
    public static final String CACHE_INFO = "cache_info";
    private static final String SUPPORT_TM_MUSIC = "tm";
    private static final String VERSION = "v";

    public DataCloudItemFeature() {
        setReady(false);
    }

    @Override // com.android.camera.resource.BaseResourceCacheable
    public long getCacheExpireTime() {
        return 5400000;
    }

    public String getVersion() {
        return getCloudString(VERSION, "");
    }

    public void parseJson(JSONObject jSONObject) {
        Iterator<String> keys = jSONObject.keys();
        SimpleArrayMap<String, Object> values = getValues();
        while (keys.hasNext()) {
            String next = keys.next();
            values.put(next, jSONObject.opt(next));
        }
        setReady(true);
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public String provideKey() {
        return null;
    }

    public boolean supportTMMusic() {
        return getCloudBoolean(SUPPORT_TM_MUSIC, true);
    }
}
