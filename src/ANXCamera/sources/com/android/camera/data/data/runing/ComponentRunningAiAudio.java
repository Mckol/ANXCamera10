package com.android.camera.data.data.runing;

import androidx.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningAiAudio extends ComponentData {
    public static final String REC_TYPE_3D_RECORD = "3d record";
    public static final String REC_TYPE_AUDIO_ZOOM = "audio zoom";
    public static final String REC_TYPE_NORMAL = "normal";

    public ComponentRunningAiAudio(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
        this.mItems = new ArrayList();
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_rec_type_normal, (int) R.drawable.ic_config_rec_type_normal, (int) R.string.pref_camera_rec_type_normal, "normal"));
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_rec_type_audio_zoom, (int) R.drawable.ic_config_rec_type_audio_zoom, (int) R.string.pref_camera_rec_type_audio_zoom, REC_TYPE_AUDIO_ZOOM));
        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_config_rec_type_3d_record, (int) R.drawable.ic_config_rec_type_3d_record, (int) R.string.pref_camera_rec_type_3d_record, REC_TYPE_3D_RECORD));
    }

    public String getCurrentParameters(int i, boolean z, int i2) {
        int i3;
        int i4;
        int i5;
        if (!z) {
            return "SetAudioCustomScene=";
        }
        int i6 = 0;
        if (!CameraSettings.isFrontCamera()) {
            i4 = getCurrentRecType(i);
            i3 = 1;
        } else if (CameraSettings.isFrontDenoiseOn()) {
            i4 = 0;
            i3 = 0;
        } else {
            i3 = 0;
            i4 = 1;
        }
        if (i2 != 0) {
            if (i2 != 90) {
                if (i2 == 180) {
                    i5 = 3;
                } else if (i2 == 270) {
                    i5 = 2;
                }
            }
            i5 = 1;
        } else {
            i5 = 4;
        }
        boolean isWindDenoiseOn = CameraSettings.isWindDenoiseOn();
        if (Util.isWiredHeadsetOn() || CameraSettings.isMacroLensOn(i)) {
            i4 = 1;
        } else {
            i6 = isWindDenoiseOn ? 1 : 0;
        }
        return "SetAudioCustomScene=audioZoom@shot@" + i3 + "/scene@" + i5 + "/recType@" + i4 + "/wnd_ns@" + i6 + "/";
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003b  */
    public int getCurrentRecType(int i) {
        char c2;
        String componentValue = getComponentValue(i);
        int hashCode = componentValue.hashCode();
        if (hashCode != -2103084864) {
            if (hashCode != -1039745817) {
                if (hashCode == 1491365341 && componentValue.equals(REC_TYPE_AUDIO_ZOOM)) {
                    c2 = 1;
                    if (c2 != 0) {
                        if (c2 == 1) {
                            return 2;
                        }
                        if (c2 == 2) {
                            return 3;
                        }
                    }
                    return 1;
                }
            } else if (componentValue.equals("normal")) {
                c2 = 0;
                if (c2 != 0) {
                }
                return 1;
            }
        } else if (componentValue.equals(REC_TYPE_3D_RECORD)) {
            c2 = 2;
            if (c2 != 0) {
            }
            return 1;
        }
        c2 = 65535;
        if (c2 != 0) {
        }
        return 1;
    }

    public int getCurrentStringRes(int i) {
        int currentRecType = getCurrentRecType(i);
        if (currentRecType == 2) {
            return R.string.pref_camera_rec_type_audio_zoom;
        }
        if (currentRecType != 3) {
            return -1;
        }
        return R.string.pref_camera_rec_type_3d_record;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "normal";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_ai_audio;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_ai_audio";
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("normal".equals(componentValue)) {
            return R.drawable.ic_config_rec_type_normal;
        }
        if (REC_TYPE_AUDIO_ZOOM.equals(componentValue)) {
            return R.drawable.ic_config_rec_type_audio_zoom;
        }
        if (REC_TYPE_3D_RECORD.equals(componentValue)) {
            return R.drawable.ic_config_rec_type_3d_record;
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("normal".equals(componentValue)) {
            return R.string.pref_camera_rec_type_normal;
        }
        if (REC_TYPE_AUDIO_ZOOM.equals(componentValue)) {
            return R.string.pref_camera_rec_type_audio_zoom;
        }
        if (REC_TYPE_3D_RECORD.equals(componentValue)) {
            return R.string.pref_camera_rec_type_3d_record;
        }
        return -1;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isEmpty() {
        return this.mItems == null || this.mItems.isEmpty();
    }
}
