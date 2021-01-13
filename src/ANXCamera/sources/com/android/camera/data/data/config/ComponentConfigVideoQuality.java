package com.android.camera.data.data.config;

import android.graphics.SurfaceTexture;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.MiCustomFpsRange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ComponentConfigVideoQuality extends ComponentData {
    public static final String FPS_24 = "24";
    private static List<ComponentDataItem> ITEMS_FROM_WIDE_FAST_MOTION = new ArrayList();
    private static List<ComponentDataItem> ITEMS_FROM_WIDE_RECORD_VIDEO = new ArrayList();
    public static final String QUALITY_1080P = "6";
    public static final String QUALITY_1080P_60FPS = "6,60";
    public static final String QUALITY_4K = "8";
    public static final String QUALITY_4K_60FPS = "8,60";
    public static final String QUALITY_720P = "5";
    public static final String QUALITY_8K = "3001";
    public static final String QUALITY_8K_24FPS = "3001,24";
    private static final List<String> QUALITY_ALL = new ArrayList();
    private static final String TAG = "ComponentConfigVideoQuality";
    private String mDefaultValue = "6";
    private boolean mIsOnly8k24;

    static {
        QUALITY_ALL.clear();
        QUALITY_ALL.add(QUALITY_4K_60FPS);
        QUALITY_ALL.add("8");
        QUALITY_ALL.add(QUALITY_1080P_60FPS);
        QUALITY_ALL.add("6");
        QUALITY_ALL.add("5");
    }

    public ComponentConfigVideoQuality(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private void copyList(List<ComponentDataItem> list, List<ComponentDataItem> list2) {
        if (list2 == null) {
            list2 = new ArrayList<>();
        }
        list2.clear();
        for (ComponentDataItem componentDataItem : list) {
            list2.add(componentDataItem);
        }
    }

    private void filterAllQualities(List<ComponentDataItem> list, int i, CameraCapabilities cameraCapabilities, int i2, List<String> list2, List<ComponentDataItem> list3) {
        boolean z = cameraCapabilities.getCameraId() == Camera2DataContainer.getInstance().getMainBackCameraId() || Camera2DataContainer.getInstance().getVideoSATCameraId() == cameraCapabilities.getCameraId();
        if (i2 == 0 && ((list3 == null || list3.size() == 0) && z)) {
            copyList(list, list3);
        }
        if (list2.size() > 0 && i == 0 && list3 != null && list3.size() > 0) {
            filterSupprotedItems(list3, QUALITY_ALL);
            copyList(list3, list);
            if (!z && cameraCapabilities.getCameraId() != Camera2DataContainer.getInstance().getAuxCameraId() && cameraCapabilities.getCameraId() != Camera2DataContainer.getInstance().getUltraTeleCameraId()) {
                filterSupprotedItems(list, list2);
            }
        }
    }

    private List<String> filterSupprotedItems(List<ComponentDataItem> list, List<String> list2) {
        ArrayList arrayList = new ArrayList();
        if (list == null || list.size() == 0) {
            return null;
        }
        for (ComponentDataItem componentDataItem : list) {
            componentDataItem.mIsDisabled = true;
            Iterator<String> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                if (componentDataItem.mValue == next) {
                    arrayList.add(next);
                    componentDataItem.mIsDisabled = false;
                    break;
                }
            }
        }
        return arrayList;
    }

    private List<String> filterSupprotedItems(List<ComponentDataItem> list, String... strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            arrayList.add(str);
        }
        return filterSupprotedItems(list, arrayList);
    }

    private String getSupportMaxValue(String str) {
        if (!(this.mItems == null || this.mItems.size() == 0)) {
            for (int size = this.mItems.size() - 1; size >= 0; size--) {
                if (!this.mItems.get(size).mIsDisabled) {
                    return this.mItems.get(size).mValue;
                }
            }
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        if (r12 != 210) goto L_0x007f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x008d  */
    private List<String> initItem(List<ComponentDataItem> list, int i, int i2, CameraCapabilities cameraCapabilities, int i3) {
        ArrayList arrayList = new ArrayList();
        if (i != 161) {
            if (i == 162 || i == 169 || i == 180) {
                initVideoMode(list, i, i2, cameraCapabilities, i3, arrayList);
                if (i != 162) {
                    filterAllQualities(list, i2, cameraCapabilities, i3, arrayList, ITEMS_FROM_WIDE_RECORD_VIDEO);
                } else if (i == 169) {
                    filterAllQualities(list, i2, cameraCapabilities, i3, arrayList, ITEMS_FROM_WIDE_FAST_MOTION);
                }
                return arrayList;
            } else if (i != 183) {
                if (i != 209) {
                }
            }
        }
        List<CameraSize> supportedOutputSizeWithAssignedMode = cameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        if (supportedOutputSizeWithAssignedMode.contains(new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH))) {
            list.add(new ComponentDataItem((int) R.drawable.ic_config_720p_30, (int) R.drawable.ic_config_720p_30, (int) R.string.pref_video_quality_entry_720p, "5"));
            arrayList.add("5");
        }
        if (!c.rh && !c.sh && supportedOutputSizeWithAssignedMode.contains(new CameraSize(1920, 1080))) {
            list.add(new ComponentDataItem((int) R.drawable.ic_config_1080p_30, (int) R.drawable.ic_config_1080p_30, (int) R.string.pref_video_quality_entry_1080p, "6"));
            arrayList.add("6");
        }
        if (i != 162) {
        }
        return arrayList;
    }

    private void initVideoMode(List<ComponentDataItem> list, int i, int i2, CameraCapabilities cameraCapabilities, int i3, List<String> list2) {
        List<CameraSize> supportedOutputSizeWithTargetMode = cameraCapabilities.getSupportedOutputSizeWithTargetMode(MediaRecorder.class, 32772);
        if (supportedOutputSizeWithTargetMode.contains(new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH)) && CamcorderProfile.hasProfile(i2, 5)) {
            list.add(new ComponentDataItem((int) R.drawable.ic_config_720p_30, (int) R.drawable.ic_config_720p_30, (int) R.string.pref_video_quality_entry_720p, "5"));
            list2.add("5");
        }
        if (supportedOutputSizeWithTargetMode.contains(new CameraSize(1920, 1080)) && CamcorderProfile.hasProfile(i2, 6)) {
            list.add(new ComponentDataItem((int) R.drawable.ic_config_1080p_30, (int) R.drawable.ic_config_1080p_30, (int) R.string.pref_video_quality_entry_1080p, "6"));
            list2.add("6");
            if (i != 169 && isSupportFpsRange(1920, 1080, i3, cameraCapabilities)) {
                list.add(new ComponentDataItem((int) R.drawable.ic_config_1080p_60, (int) R.drawable.ic_config_1080p_60, (int) R.string.pref_video_quality_entry_1080p_60fps, QUALITY_1080P_60FPS));
                list2.add(QUALITY_1080P_60FPS);
            }
        }
        int i4 = CameraSettings.get4kProfile();
        if (c.ao() && supportedOutputSizeWithTargetMode.contains(new CameraSize(3840, 2160)) && CamcorderProfile.hasProfile(i2, i4)) {
            list.add(new ComponentDataItem((int) R.drawable.ic_config_4k_30, (int) R.drawable.ic_config_4k_30, (int) R.string.pref_video_quality_entry_4kuhd, "8"));
            list2.add("8");
            if (i != 169 && isSupportFpsRange(3840, 2160, i3, cameraCapabilities)) {
                list.add(new ComponentDataItem((int) R.drawable.ic_config_4k_60, (int) R.drawable.ic_config_4k_60, (int) R.string.pref_video_quality_entry_4kuhd_60fps, QUALITY_4K_60FPS));
                list2.add(QUALITY_4K_60FPS);
            }
        }
    }

    public static boolean is8KCamcorderSupported(int i) {
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i);
        if (capabilities == null) {
            return false;
        }
        return c.ao() && capabilities.getSupportedOutputSizeWithTargetMode(MediaRecorder.class, 32772).contains(new CameraSize(7680, 4320)) && CamcorderProfile.hasProfile(i, CameraSettings.get8kProfile());
    }

    private boolean isContain(String str, List<ComponentDataItem> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (ComponentDataItem componentDataItem : list) {
            if (TextUtils.equals(str, componentDataItem.mValue) && !componentDataItem.mIsDisabled) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupportFpsRange(int i, int i2, int i3, CameraCapabilities cameraCapabilities) {
        List<MiCustomFpsRange> supportedCustomFpsRange;
        if (i3 == 0 && (supportedCustomFpsRange = cameraCapabilities.getSupportedCustomFpsRange()) != null && !supportedCustomFpsRange.isEmpty()) {
            for (MiCustomFpsRange miCustomFpsRange : supportedCustomFpsRange) {
                if (miCustomFpsRange.getWidth() == i && miCustomFpsRange.getHeight() == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    private int supprotedItemsSize(List<ComponentDataItem> list) {
        int i = 0;
        if (!(list == null || list.size() == 0)) {
            for (ComponentDataItem componentDataItem : list) {
                if (!componentDataItem.mIsDisabled) {
                    i++;
                }
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        if (isContain(str, this.mItems)) {
            return true;
        }
        String str2 = TAG;
        Log.d(str2, "checkValueValid: invalid value: " + str);
        return false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return this.mItems == null || this.mItems.size() == 1 || supprotedItemsSize(this.mItems) <= 1;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return getComponentValue(i, "");
    }

    public String getComponentValue(int i, String str) {
        if (CameraSettings.isVideoQuality8KOpen(i)) {
            return DataRepository.dataItemRunning().getComponentRunning8KVideoQuality().getQuality(i);
        }
        if (!TextUtils.isEmpty(str) && checkValueValid(i, str)) {
            return str;
        }
        String defaultValue = getDefaultValue(i);
        String string = this.mParentDataItem.getString(getKey(i), defaultValue);
        if (string == null || string.equals(defaultValue) || checkValueValid(i, string)) {
            return string;
        }
        String simpleName = ComponentConfigVideoQuality.class.getSimpleName();
        Log.e(simpleName, "reset invalid value " + string);
        return getSupportMaxValue(defaultValue);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return this.mDefaultValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDisplayComponentValue(int i) {
        if (!this.mIsOnly8k24 || !CameraSettings.isVideoQuality8KOpen(i)) {
            return getComponentValue(i);
        }
        String defaultValue = getDefaultValue(i);
        String string = this.mParentDataItem.getString(getKey(i), defaultValue);
        if (string == null || string.equals(defaultValue)) {
            return string;
        }
        for (ComponentDataItem componentDataItem : this.mItems) {
            if (TextUtils.equals(string, componentDataItem.mValue)) {
                return string;
            }
        }
        String simpleName = ComponentConfigVideoQuality.class.getSimpleName();
        Log.e(simpleName, "reset invalid value " + string);
        return getSupportMaxValue(defaultValue);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_video_quality_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            Log.e(TAG, "List is empty!");
        }
        return this.mItems == null ? Collections.emptyList() : this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 161 ? i != 180 ? i != 183 ? "pref_video_quality_key" : CameraSettings.KEY_MI_LIVE_QUALITY : CameraSettings.KEY_CAMERA_PRO_VIDEO_QUALITY : CameraSettings.KEY_CAMERA_FUN_VIDEO_QUALITY;
    }

    public boolean isSupportVideoQuality(int i, int i2, boolean z) {
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i2);
        if (capabilities == null) {
            return false;
        }
        if (CameraSettings.isVideoQuality8KOpen(i)) {
            return is8KCamcorderSupported(i2);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        initVideoMode(arrayList, i, 0, capabilities, 0, arrayList2);
        return arrayList2.contains(z ? this.mParentDataItem.getString(getKey(i), getDefaultValue(i)) : getComponentValue(i));
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities, int i3) {
        String str;
        int size;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        new ArrayList();
        List<String> initItem = initItem(arrayList2, i, i2, cameraCapabilities, i3);
        this.mDefaultValue = null;
        this.mIsOnly8k24 = false;
        if (!(i == 161 || i == 183)) {
            if (CameraSettings.isAutoZoomEnabled(i)) {
                filterSupprotedItems(arrayList2, "6");
                this.mDefaultValue = "6";
            }
            if (i == 204) {
                filterSupprotedItems(arrayList2, "6");
                this.mDefaultValue = "6";
            }
            if (CameraSettings.isSuperEISEnabled(i)) {
                filterSupprotedItems(arrayList2, "6");
                this.mDefaultValue = "6";
            }
            if (CameraSettings.isFaceBeautyOn(i, null)) {
                filterSupprotedItems(arrayList2, "5");
                this.mDefaultValue = "5";
            }
            if (CameraSettings.isVideoBokehOn()) {
                filterSupprotedItems(arrayList2, "5");
                this.mDefaultValue = "5";
            }
            if (CameraSettings.isProVideoLogOpen(i)) {
                filterSupprotedItems(arrayList2, "8");
                this.mDefaultValue = "8";
            }
            if (CameraSettings.isVideoQuality8KOpen(i)) {
                if (cameraCapabilities.get8KMaxFpsSupported() > 24) {
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_8k_24, (int) R.drawable.ic_config_8k_24, (int) R.string.pref_video_quality_entry_8k_24fps_uhd, QUALITY_8K_24FPS));
                    arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_8k_30, (int) R.drawable.ic_config_8k_30, (int) R.string.pref_video_quality_entry_8kuhd, QUALITY_8K));
                } else {
                    this.mIsOnly8k24 = true;
                    filterSupprotedItems(arrayList2, QUALITY_8K);
                }
            }
        }
        if (this.mDefaultValue == null) {
            if (161 == i) {
                this.mDefaultValue = "6";
            } else if (183 == i) {
                this.mDefaultValue = "5";
            } else if (i2 == 1) {
                this.mDefaultValue = "6";
            } else if (i2 == 0) {
                this.mDefaultValue = CameraSettings.getDefaultValueByKey("pref_video_quality_key");
            }
        }
        if (initItem != null && initItem.size() > 0 && (str = this.mDefaultValue) != null && !initItem.contains(str) && (size = initItem.size()) > 0) {
            this.mDefaultValue = initItem.get(size - 1);
        }
        if (!CameraSettings.isVideoQuality8KOpen(i) || this.mIsOnly8k24) {
            this.mItems = Collections.unmodifiableList(arrayList2);
            return;
        }
        this.mDefaultValue = QUALITY_8K;
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    public void reset() {
        List<ComponentDataItem> list = ITEMS_FROM_WIDE_RECORD_VIDEO;
        if (list != null) {
            list.clear();
        }
        List<ComponentDataItem> list2 = ITEMS_FROM_WIDE_FAST_MOTION;
        if (list2 != null) {
            list2.clear();
        }
        setForceValueOverlay(null);
    }

    public void setForceValueOverlay(@Nullable String str) {
        if (str == null) {
            filterSupprotedItems(this.mItems, QUALITY_ALL);
            return;
        }
        filterSupprotedItems(this.mItems, str);
    }

    public boolean supportVideoSATForVideoQuality(int i) {
        if (!DataRepository.dataItemGlobal().isNormalIntent() || !DataRepository.dataItemFeature().qm() || !CameraSettings.isSettingsVideoSATEnable() || i != 162 || CameraSettings.isVideoQuality8KOpen(i)) {
            return false;
        }
        String componentValue = getComponentValue(i);
        return !"8".equals(componentValue) && !QUALITY_4K_60FPS.equals(componentValue) && !QUALITY_1080P_60FPS.equals(componentValue);
    }
}
