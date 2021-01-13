package com.ss.android.vesdk.runtime.cloudconfig;

import androidx.annotation.NonNull;
import java.util.Map;
import org.json.JSONObject;

public interface IInjector {
    void inject(Map<String, String> map, @NonNull VECloudConfig vECloudConfig);

    Map<String, String> parse(@NonNull JSONObject jSONObject);
}
