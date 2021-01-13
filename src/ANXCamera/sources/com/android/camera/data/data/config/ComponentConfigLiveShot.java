package com.android.camera.data.data.config;

import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import java.util.List;

public class ComponentConfigLiveShot extends ComponentData {
    private static final String TAG = "ComponentConfigLiveShot";
    private boolean mIsClosed;
    private boolean mSupported;

    public ComponentConfigLiveShot(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        throw new UnsupportedOperationException(TAG + "#getDefaultValue() not supported");
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        throw new UnsupportedOperationException(TAG + "#getDisplayTitleString() not supported");
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        throw new UnsupportedOperationException(TAG + "#getItems() not supported");
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        throw new UnsupportedOperationException(TAG + "#getKey() not supported");
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        if (this.mSupported && !isClosed()) {
            return this.mParentDataItem.getBoolean("pref_live_shot_enabled", false);
        }
        return false;
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        this.mSupported = false;
        if (DataRepository.dataItemFeature().Lj()) {
            if (i == 163 || i == 165) {
                this.mSupported = true;
            } else {
                this.mSupported = false;
            }
        }
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    public void setLiveShotOn(boolean z) {
        setClosed(false);
        this.mParentDataItem.editor().putBoolean("pref_live_shot_enabled", z).apply();
    }
}
