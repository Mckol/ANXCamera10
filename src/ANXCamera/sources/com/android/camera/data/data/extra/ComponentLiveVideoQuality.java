package com.android.camera.data.data.extra;

import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentLiveVideoQuality extends ComponentData {
    public static final String QUALITY_1080P = "6";
    public static final String QUALITY_720P = "5";
    public static final int SUPPORT_1080P_ONLY = 2;
    public static final int SUPPORT_720P_AND_1080P = 0;
    public static final int SUPPORT_720P_ONLY = 1;
    private static final String TAG = "ComponentLiveVideoQuality";
    private String mDefaultValue = "5";

    public ComponentLiveVideoQuality(DataItemLive dataItemLive) {
        super(dataItemLive);
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return this.mItems == null || this.mItems.size() == 1;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return this.mDefaultValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_video_quality_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            Log.e(TAG, "List is empty!");
        }
        return this.mItems == null ? Collections.emptyList() : this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return CameraSettings.KEY_MI_LIVE_QUALITY;
    }

    public String getNextValue() {
        String componentValue = getComponentValue(160);
        if (this.mItems != null) {
            int size = this.mItems.size();
            for (int i = 0; i < size; i++) {
                if (TextUtils.equals(this.mItems.get(i).mValue, componentValue)) {
                    return this.mItems.get((i + 1) % size).mValue;
                }
            }
        }
        return getDefaultValue(160);
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        List<CameraSize> supportedOutputSizeWithAssignedMode = cameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        int Ih = DataRepository.dataItemFeature().Ih();
        boolean z = false;
        boolean z2 = Ih != 2 && supportedOutputSizeWithAssignedMode.contains(new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH));
        if (Ih != 1 && supportedOutputSizeWithAssignedMode.contains(new CameraSize(1920, 1080))) {
            z = true;
        }
        if (z2 && z) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_720p_30, (int) R.drawable.ic_config_720p_30, (int) R.string.pref_video_quality_entry_720p, "5"));
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_1080p_30, (int) R.drawable.ic_config_1080p_30, (int) R.string.pref_video_quality_entry_1080p, "6"));
            this.mDefaultValue = "5";
        } else if (z2) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_720p_30, (int) R.drawable.ic_config_720p_30, (int) R.string.pref_video_quality_entry_720p, "5"));
            this.mDefaultValue = "5";
        } else if (z) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_1080p_30, (int) R.drawable.ic_config_1080p_30, (int) R.string.pref_video_quality_entry_1080p, "6"));
            this.mDefaultValue = "6";
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }
}
