package com.android.camera.data.data.runing;

import androidx.annotation.NonNull;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;

public class ComponentRunningSuperEIS extends ComponentData {
    public static final String VALUE_OFF = "OFF";
    public static final String VALUE_ON = "ON";
    private boolean mIsNormalIntent;

    public ComponentRunningSuperEIS(DataItemRunning dataItemRunning) {
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
        if (i == 162 || i == 169 || i == 204) {
            return "pref_camera_super_eis";
        }
        return "pref_camera_super_eis_" + Integer.toHexString(i);
    }

    public boolean isEnabled(int i) {
        if (DataRepository.dataItemFeature().kk() && this.mIsNormalIntent) {
            return "ON".equals(getComponentValue(i));
        }
        return false;
    }

    public void reInit(int i, boolean z) {
        this.mIsNormalIntent = z;
    }

    public void setEnabled(int i, boolean z) {
        setComponentValue(i, z ? "ON" : "OFF");
    }
}
