package com.android.camera.data.data.config;

import android.util.ArrayMap;
import androidx.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ComponentRunning8KVideoQuality extends ComponentData {
    private Map<Integer, Integer> mCacheFpsValues = new ArrayMap();
    private Map<String, Boolean> mCacheValues = new ArrayMap();
    private int mCameraId = 0;
    private int mDefaultValue = 30;
    private Boolean mIsSupportEis;

    public ComponentRunning8KVideoQuality(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public void clearArrayMap() {
        this.mCacheValues.clear();
        this.mCacheFpsValues.clear();
    }

    public boolean get8KEisIsSupported(CameraCapabilities cameraCapabilities) {
        Boolean bool = this.mIsSupportEis;
        if (bool != null) {
            return bool.booleanValue();
        }
        this.mIsSupportEis = Boolean.valueOf(CameraSettings.isCurrentQualitySupportEis(Integer.parseInt(ComponentConfigVideoQuality.QUALITY_8K), 24, cameraCapabilities));
        return this.mIsSupportEis.booleanValue();
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.video_ultra_clear_tip_8k;
    }

    public int getFps(int i) {
        return this.mCacheFpsValues.getOrDefault(Integer.valueOf(i), Integer.valueOf(this.mDefaultValue)).intValue();
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 162 || i == 180) {
            return "pref_camera_video_8k_" + i + "_" + this.mCameraId;
        }
        return "pref_camera_video_8k_unsupported_" + this.mCameraId;
    }

    public String getQuality(int i) {
        return getFps(i) == 24 ? ComponentConfigVideoQuality.QUALITY_8K_24FPS : ComponentConfigVideoQuality.QUALITY_8K;
    }

    public boolean isEnabled(int i) {
        Boolean bool = this.mCacheValues.get(getKey(i));
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        this.mCameraId = i2;
        ArrayList arrayList = new ArrayList();
        if (i == 162 || i == 180) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_video_8k_highlight, (int) R.drawable.ic_config_video_8k_normal, (int) R.string.video_ultra_clear_tip_8k, "on"));
            this.mDefaultValue = cameraCapabilities.get8KMaxFpsSupported();
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    public void setEnabled(int i, boolean z) {
        if (!z) {
            this.mCacheFpsValues.put(Integer.valueOf(i), 30);
        } else if (this.mDefaultValue == 24) {
            this.mCacheFpsValues.put(Integer.valueOf(i), Integer.valueOf(this.mDefaultValue));
        }
        this.mCacheValues.put(getKey(i), Boolean.valueOf(z));
    }

    public void setFps(int i, int i2) {
        this.mCacheFpsValues.put(Integer.valueOf(i), Integer.valueOf(i2));
    }
}
