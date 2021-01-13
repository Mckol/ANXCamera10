package com.android.camera.data.data.config;

import android.text.TextUtils;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import java.util.ArrayList;
import java.util.List;

public class ComponentManuallyDualLens extends ComponentData {
    public static final String LENS_MACRO = "macro";
    public static final String LENS_TELE = "tele";
    public static final String LENS_ULTRA = "ultra";
    public static final String LENS_ULTRA_TELE = "Standalone";
    public static final String LENS_WIDE = "wide";
    private static final String TAG = "DualLens";

    public ComponentManuallyDualLens(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private int indexOf(String str) {
        List<ComponentDataItem> items = getItems();
        if (items != null && !TextUtils.isEmpty(str)) {
            for (int i = 0; i < items.size(); i++) {
                if (TextUtils.equals(items.get(i).mValue, str)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private List<ComponentDataItem> initItems(int i) {
        ArrayList arrayList = new ArrayList();
        if (i != 175) {
            if (DataRepository.dataItemFeature().fm() && DataRepository.dataItemFeature().gm()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_macro, (int) R.drawable.ic_camera_zoom_mode_entry_macro_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_macro_abbr, "macro"));
            }
            if (DataRepository.dataItemFeature().isSupportUltraWide()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_ultra, (int) R.drawable.ic_camera_zoom_mode_entry_ultra_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_ultra_abbr, LENS_ULTRA));
            }
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_wide, (int) R.drawable.ic_camera_zoom_mode_entry_wide_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_wide_abbr, LENS_WIDE));
            if (CameraSettings.isSupportedOpticalZoom()) {
                if (DataRepository.dataItemFeature().pk()) {
                    if (!DataRepository.dataItemFeature().uk() || i != 180) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_tele2x, (int) R.drawable.ic_camera_zoom_mode_entry_tele2x_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_tele_2X_abbr, LENS_TELE));
                    }
                } else if (Camera2DataContainer.getInstance().getAuxCameraId() >= 0) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_tele2x, (int) R.drawable.ic_camera_zoom_mode_entry_tele2x_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_tele_abbr, LENS_TELE));
                }
            }
            if (DataRepository.dataItemFeature().pk() && (i != 180 || DataRepository.dataItemFeature().uk())) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_tele5x, (int) R.drawable.ic_camera_zoom_mode_entry_tele5x_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_tele_5X_abbr, LENS_ULTRA_TELE));
            }
        } else {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_wide, (int) R.drawable.ic_camera_zoom_mode_entry_wide_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_wide_abbr, LENS_WIDE));
            if (DataRepository.dataItemFeature().pk()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_camera_zoom_mode_entry_tele5x, (int) R.drawable.ic_camera_zoom_mode_entry_tele5x_shadow, -1, (int) R.string.pref_camera_zoom_mode_entry_tele_5X_abbr, LENS_ULTRA_TELE));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        Log.d(TAG, "checkValueValid: invalid value: " + str);
        return false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return LENS_WIDE;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_zoom_mode_title_abbr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            this.mItems = initItems(DataRepository.dataItemGlobal().getCurrentMode());
        }
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 167 ? i != 175 ? i != 180 ? CameraSettings.KEY_CAMERA_ZOOM_MODE : CameraSettings.KEY_CAMERA_PRO_VIDEO_LENS : CameraSettings.KEY_CAMERA_PIXEL_LENS : CameraSettings.KEY_CAMERA_MANUALLY_LENS;
    }

    public String next(String str, int i) {
        int indexOf = indexOf(str);
        List<ComponentDataItem> items = getItems();
        if (items == null) {
            return LENS_WIDE;
        }
        return items.get(indexOf == items.size() + -1 ? 0 : indexOf + 1).mValue;
    }

    public List<ComponentDataItem> reInit(int i) {
        this.mItems = initItems(i);
        return this.mItems;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public void resetComponentValue(int i) {
        this.mItems = initItems(i);
        setComponentValue(i, getDefaultValue(i));
    }

    public void resetLensType(ComponentConfigUltraWide componentConfigUltraWide, DataProvider.ProviderEditor providerEditor) {
        String componentValue = getComponentValue(167);
        if (!LENS_WIDE.equals(componentValue)) {
            providerEditor.putString(getKey(167), LENS_WIDE);
            if (LENS_ULTRA.equals(componentValue)) {
                providerEditor.putString(componentConfigUltraWide.getKey(167), "OFF");
            }
        }
    }
}
