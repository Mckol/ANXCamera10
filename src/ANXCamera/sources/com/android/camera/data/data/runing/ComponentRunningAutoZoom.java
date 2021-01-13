package com.android.camera.data.data.runing;

import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;

public class ComponentRunningAutoZoom extends ComponentData {
    public static final String VALUE_OFF = "OFF";
    public static final String VALUE_ON = "ON";
    private int mCameraId;

    public ComponentRunningAutoZoom(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "OFF";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_camera_auto_zoom";
    }

    public int getResText() {
        return R.string.autozoom_hint;
    }

    public boolean isNormalIntent() {
        return DataRepository.dataItemGlobal().isNormalIntent();
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        if (DataRepository.dataItemFeature().fj() && this.mCameraId == 0 && i == 162 && isNormalIntent()) {
            return "ON".equals(getComponentValue(i));
        }
        return false;
    }

    public void reInit(int i) {
        this.mCameraId = i;
    }

    public void setEnabled(int i, boolean z) {
        setComponentValue(i, z ? "ON" : "OFF");
    }
}
