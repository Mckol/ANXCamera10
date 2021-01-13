package com.android.camera.data.data.config;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import b.c.a.b;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentConfigRatio extends ComponentData {
    public static final String RATIO_16X9 = "16x9";
    public static final String RATIO_1X1 = "1x1";
    public static final String RATIO_4X3 = "4x3";
    public static final String RATIO_FULL_18X9 = "18x9";
    public static final String RATIO_FULL_18_7_5X9 = "18.75x9";
    public static final String RATIO_FULL_195X9 = "19.5x9";
    public static final String RATIO_FULL_19X9 = "19x9";
    public static final String RATIO_FULL_20X9 = "20x9";
    public final float mCurrentScreenRatio = (((float) Util.sWindowHeight) / ((float) Util.sWindowWidth));
    private String mForceValue;
    @CameraRatio
    private Map<Integer, String> mSupportDefaultValues = new HashMap();
    private ArrayList<String> sEntryValues = new ArrayList<>();
    private boolean sSupport18_7_5x9;
    private boolean sSupport18x9;
    private boolean sSupport195x9;
    private boolean sSupport19x9;
    private boolean sSupport20x9;

    public @interface CameraRatio {
    }

    public ComponentConfigRatio(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        String str = this.mForceValue;
        if (str == null || i == 165) {
            str = super.getComponentValue(i);
        }
        if (165 == i && str.equals(RATIO_1X1)) {
            return RATIO_1X1;
        }
        if (i == 184 && CameraSettings.isGifOn()) {
            return RATIO_1X1;
        }
        if (CameraSettings.isCinematicAspectRatioEnabled(i)) {
            return RATIO_16X9;
        }
        for (ComponentDataItem componentDataItem : getItems()) {
            if (componentDataItem != null && TextUtils.equals(str, componentDataItem.mValue)) {
                return str;
            }
        }
        return getDefaultValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        if (DataRepository.dataItemGlobal().getDisplayMode() == 2) {
            return RATIO_16X9;
        }
        String str = this.mSupportDefaultValues.get(Integer.valueOf(i));
        return str != null ? str : DataRepository.dataItemFeature().M(CameraSettings.isFrontCamera());
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_picturesize_title_simple_mode;
    }

    public String[] getFullSupportRatioValues() {
        this.sEntryValues.clear();
        this.sEntryValues.add(RATIO_4X3);
        this.sEntryValues.add(RATIO_16X9);
        if (c.Im()) {
            this.sEntryValues.add(RATIO_FULL_18X9);
        }
        if (DataRepository.dataItemFeature().el()) {
            this.sEntryValues.add(RATIO_FULL_19X9);
        }
        if (DataRepository.dataItemFeature().dl()) {
            this.sEntryValues.add(RATIO_FULL_195X9);
        }
        if (DataRepository.dataItemFeature().cl()) {
            this.sEntryValues.add(RATIO_FULL_18_7_5X9);
        }
        if (DataRepository.dataItemFeature().fl()) {
            this.sEntryValues.add(RATIO_FULL_20X9);
        }
        String[] strArr = new String[this.sEntryValues.size()];
        this.sEntryValues.toArray(strArr);
        return strArr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            reInit(DataRepository.dataItemGlobal().getCurrentMode(), DataRepository.dataItemGlobal().getCurrentCameraId());
        }
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 165 ? "pref_camera_picturesize_key" : "is_square";
    }

    public int getMappingModeByRatio(int i) {
        if (i != 163 && i != 165) {
            return i;
        }
        if (isSquareModule() && !CameraSettings.isCinematicAspectRatioEnabled(i)) {
            return 165;
        }
        String componentValue = getComponentValue(i);
        char c2 = 65535;
        if (componentValue.hashCode() == 50858 && componentValue.equals(RATIO_1X1)) {
            c2 = 0;
        }
        return c2 != 0 ? 163 : 165;
    }

    public String getNextValue(int i) {
        String persistValue = getPersistValue(i);
        if (this.mItems != null) {
            int size = this.mItems.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (TextUtils.equals(this.mItems.get(i2).mValue, persistValue)) {
                    return this.mItems.get((i2 + 1) % size).mValue;
                }
            }
        }
        return getDefaultValue(i);
    }

    public String getPictureSizeRatioString(int i) {
        String str = this.mForceValue;
        return str != null ? str : getComponentValue(i);
    }

    public void initSensorRatio(Map<Float, CameraSize> map, int i, int i2) {
        b dataItemFeature = DataRepository.dataItemFeature();
        boolean z = true;
        if (1 != i2) {
            z = false;
        }
        String M = dataItemFeature.M(z);
        Map<Integer, String> map2 = this.mSupportDefaultValues;
        Integer valueOf = Integer.valueOf(i);
        if (map.get(Float.valueOf(Util.getRatio(M))) == null) {
            M = RATIO_16X9;
        }
        map2.put(valueOf, M);
        reInit(i, i2);
    }

    public boolean isSquareModule() {
        String componentValue = getComponentValue(165);
        return componentValue != null && componentValue.equals(RATIO_1X1);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0235  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0270  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0298  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0226  */
    public void reInit(int i, int i2) {
        int i3;
        if (c.Im()) {
            this.sSupport18x9 = true;
        } else {
            this.sSupport18x9 = false;
        }
        if (DataRepository.dataItemFeature().dl()) {
            this.sSupport195x9 = true;
        } else {
            this.sSupport195x9 = false;
        }
        if (DataRepository.dataItemFeature().fl()) {
            this.sSupport20x9 = true;
        } else {
            this.sSupport20x9 = false;
        }
        if (DataRepository.dataItemFeature().el()) {
            this.sSupport19x9 = true;
        } else {
            this.sSupport19x9 = false;
        }
        if (DataRepository.dataItemFeature().cl()) {
            this.sSupport18_7_5x9 = true;
        } else {
            this.sSupport18_7_5x9 = false;
        }
        ArrayList arrayList = new ArrayList();
        this.mForceValue = null;
        if (i != 163) {
            if (i != 171) {
                if (i != 173) {
                    if (i != 205) {
                        switch (i) {
                            case 165:
                                this.mForceValue = RATIO_4X3;
                            case 166:
                                this.mForceValue = RATIO_16X9;
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                                break;
                            case 167:
                                if (CameraSettings.isUltraPixelOn()) {
                                    this.mForceValue = RATIO_4X3;
                                }
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                                if (!this.sSupport18_7_5x9) {
                                    if (!this.sSupport18x9) {
                                        if (!this.sSupport195x9) {
                                            if (!this.sSupport19x9) {
                                                if (this.sSupport20x9) {
                                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
                                                    break;
                                                }
                                            } else {
                                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
                                                break;
                                            }
                                        } else {
                                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                                            break;
                                        }
                                    } else {
                                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
                                        break;
                                    }
                                } else if (DataRepository.dataItemGlobal().getDisplayMode() != 2) {
                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18_7_5X9));
                                    break;
                                } else {
                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                                    break;
                                }
                                break;
                            default:
                                switch (i) {
                                    case 175:
                                        break;
                                    case 176:
                                        break;
                                    case 177:
                                        if (!CameraSettings.isGifOn()) {
                                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                                            if (!this.sSupport18x9) {
                                                if (!this.sSupport195x9) {
                                                    if (!this.sSupport19x9) {
                                                        if (this.sSupport20x9) {
                                                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
                                                            break;
                                                        }
                                                    } else {
                                                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
                                                        break;
                                                    }
                                                } else {
                                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                                                    break;
                                                }
                                            } else {
                                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
                                                break;
                                            }
                                        } else {
                                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_1_1, (int) R.drawable.ic_config_1_1, (int) R.string.pref_camera_picturesize_entry_1_1, RATIO_1X1));
                                            break;
                                        }
                                        break;
                                    default:
                                        switch (i) {
                                            case 186:
                                                if (i2 != 0) {
                                                    if (CameraSettings.isUltraPixelOn() || CameraSettings.isAIWatermarkOn()) {
                                                        this.mForceValue = RATIO_4X3;
                                                    }
                                                } else if (CameraSettings.isUltraPixelOn() || CameraSettings.isAIWatermarkOn()) {
                                                    this.mForceValue = RATIO_4X3;
                                                }
                                                if (i != 165 || i == 163) {
                                                    i3 = 1;
                                                    if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                                                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_1_1, (int) R.drawable.ic_config_1_1, (int) R.string.pref_camera_picturesize_entry_1_1, RATIO_1X1));
                                                    }
                                                } else {
                                                    i3 = 1;
                                                }
                                                if (DataRepository.dataItemGlobal().getDisplayMode() == i3) {
                                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                                                }
                                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                                                if (this.sSupport18_7_5x9) {
                                                    if (!this.sSupport18x9) {
                                                        if (!this.sSupport195x9) {
                                                            if (!this.sSupport19x9) {
                                                                if (this.sSupport20x9) {
                                                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
                                                                    break;
                                                                }
                                                            } else {
                                                                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
                                                                break;
                                                            }
                                                        } else {
                                                            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                                                            break;
                                                        }
                                                    } else {
                                                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
                                                        break;
                                                    }
                                                } else {
                                                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18_7_5X9));
                                                    break;
                                                }
                                                break;
                                        }
                                }
                        }
                    }
                    this.mForceValue = RATIO_4X3;
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                }
            } else if (i2 != 0 || !DataRepository.dataItemFeature().Fi()) {
                if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
                }
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
                if (this.sSupport18_7_5x9) {
                    if (DataRepository.dataItemGlobal().getDisplayMode() == 2) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                    } else {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18_7_5X9));
                    }
                } else if (this.sSupport18x9) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_18X9));
                } else if (this.sSupport195x9) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_195X9));
                } else if (this.sSupport19x9) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_19X9));
                } else if (this.sSupport20x9) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_fullscreen, (int) R.drawable.ic_config_fullscreen, (int) R.string.pref_camera_picturesize_entry_fullscreen, RATIO_FULL_20X9));
                }
            } else {
                this.mForceValue = RATIO_4X3;
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_4_3, (int) R.drawable.ic_config_4_3, (int) R.string.pref_camera_picturesize_entry_3_4, RATIO_4X3));
            }
            this.mItems = Collections.unmodifiableList(arrayList);
        }
        if (i2 != 0) {
        }
        if (i != 165) {
        }
        i3 = 1;
        if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
        }
        if (DataRepository.dataItemGlobal().getDisplayMode() == i3) {
        }
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_16_9, (int) R.drawable.ic_config_16_9, (int) R.string.pref_camera_picturesize_entry_9_16, RATIO_16X9));
        if (this.sSupport18_7_5x9) {
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        if (i == 165 && !str.equals(RATIO_1X1)) {
            i = 163;
        }
        if (str.equals(RATIO_1X1)) {
            super.setComponentValue(165, str);
            return;
        }
        super.setComponentValue(165, null);
        super.setComponentValue(i, str);
    }

    public boolean supportRatioSwitch() {
        return this.mItems != null && this.mItems.size() > 1;
    }
}
