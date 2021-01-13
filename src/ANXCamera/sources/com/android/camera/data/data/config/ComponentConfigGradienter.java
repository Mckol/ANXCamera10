package com.android.camera.data.data.config;

import androidx.annotation.NonNull;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.provider.DataProvider;
import java.util.List;

public class ComponentConfigGradienter extends ComponentData {
    private static final String SCOPE_NS_PHOTO = "photo";
    private static final String SCOPE_NS_PHOTO_PRO = "photo_pro";
    private static final String SCOPE_NS_UNSUPPORTED = "unsupported";
    private static final String SCOPE_NS_VIDEO = "video";
    private static final String SCOPE_NS_VIDEO_PRO = "video_pro";
    private static final String TAG = "CCGradienter";
    public static final String VALUE_GRADIENTER_OFF = "off";
    public static final String VALUE_GRADIENTER_ON = "on";
    private int mCameraId;
    private int mCapturingMode;

    public ComponentConfigGradienter(DataItemConfig dataItemConfig, int i) {
        super(dataItemConfig);
        this.mCameraId = i;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return (this.mCameraId != 1 && !getKey(i).endsWith(SCOPE_NS_UNSUPPORTED)) ? super.getComponentValue(i) : "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        throw new UnsupportedOperationException("CCGradienter#getItems() not supported");
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 162) {
            return "pref_camera_gradienter_key_video";
        }
        if (i == 163 || i == 165 || i == 175 || i == 177) {
            return "pref_camera_gradienter_key_photo";
        }
        if (i == 180) {
            return "pref_camera_gradienter_key_video_pro";
        }
        if (i == 184 || i == 205) {
            return "pref_camera_gradienter_key_photo";
        }
        switch (i) {
            case 167:
                return "pref_camera_gradienter_key_photo_pro";
            case 168:
            case 169:
                return "pref_camera_gradienter_key_video";
            default:
                switch (i) {
                    case 171:
                    case 173:
                        return "pref_camera_gradienter_key_photo";
                    case 172:
                        return "pref_camera_gradienter_key_video";
                    default:
                        switch (i) {
                            case 186:
                            case 188:
                                return "pref_camera_gradienter_key_photo";
                            case 187:
                                return "pref_camera_gradienter_key_photo_pro";
                            default:
                                return "pref_camera_gradienter_key_unsupported";
                        }
                }
        }
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        return "on".equals(getComponentValue(i));
    }

    public void reInit(int i, int i2) {
        this.mCapturingMode = i;
        this.mCameraId = i2;
    }

    public void resetToDefault(DataProvider.ProviderEditor providerEditor) {
        providerEditor.remove(getKey(163));
        providerEditor.remove(getKey(167));
        providerEditor.remove(getKey(162));
        providerEditor.remove(getKey(180));
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        if (this.mCameraId != 1 && !getKey(i).endsWith(SCOPE_NS_UNSUPPORTED)) {
            super.setComponentValue(i, str);
        }
    }

    @Override // com.android.camera.data.data.ComponentData
    public void toSwitch(int i, boolean z) {
        setComponentValue(i, z ? "on" : "off");
    }
}
