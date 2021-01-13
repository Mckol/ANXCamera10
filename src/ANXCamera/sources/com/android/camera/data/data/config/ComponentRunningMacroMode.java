package com.android.camera.data.data.config;

import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import java.util.List;

public class ComponentRunningMacroMode extends ComponentData {
    public static final String VALUE_OFF = "OFF";
    public static final String VALUE_ON = "ON";
    public List<ComponentDataItem> mItems;

    public ComponentRunningMacroMode(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private String getMode(int i, int i2) {
        if (165 == i || 186 == i) {
            i = 163;
        }
        if (169 == i) {
            i = 162;
        }
        return String.valueOf(i) + "_" + i2;
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
        return "pref_camera_macro_scene_mode_key";
    }

    public int getResText() {
        return DataRepository.dataItemFeature().Wi() ? R.string.super_macro_mode : R.string.macro_mode;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        return "ON".equals(getComponentValue(i));
    }

    public void reInit(int i, boolean z) {
    }

    public void setSwitchOff(int i) {
        setComponentValue(i, "OFF");
    }

    public void setSwitchOn(int i) {
        setComponentValue(i, "ON");
    }
}
