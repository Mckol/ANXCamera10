package com.android.camera.data.data.config;

import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ComponentConfigSlowMotionQuality extends ComponentData {
    public static final String QUALITY_1080P = "6";
    public static final String QUALITY_720P = "5";
    public static final String SIZE_FPS_1080_120 = "1920x1080:120";
    public static final String SIZE_FPS_1080_240 = "1920x1080:240";
    public static final String SIZE_FPS_720_120 = "1280x720:120";
    private static final String TAG = "ComponentConfigSlowMotionQuality";
    public static String mCurrentFps = "slow_motion_120";

    public ComponentConfigSlowMotionQuality(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public static ArrayList<String> getSupportedHfrSettings(CameraCapabilities cameraCapabilities) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (cameraCapabilities == null) {
            Log.w(TAG, "getSupportedHfrSettings: CameraCapabilities is null!!!");
            return arrayList;
        }
        Size[] supportedHighSpeedVideoSize = cameraCapabilities.getSupportedHighSpeedVideoSize();
        for (Size size : supportedHighSpeedVideoSize) {
            if (size.getWidth() == 1920 || size.getWidth() == 1280) {
                for (Range<Integer> range : cameraCapabilities.getSupportedHighSpeedVideoFPSRange(size)) {
                    String format = String.format(Locale.ENGLISH, "%dx%d:%d", Integer.valueOf(size.getWidth()), Integer.valueOf(size.getHeight()), range.getUpper());
                    if (!arrayList.contains(format)) {
                        arrayList.add(format);
                    }
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue) && !componentDataItem.mIsDisabled) {
                return true;
            }
        }
        Log.d(TAG, "checkValueValid: invalid value: " + str);
        return false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return this.mItems == null || this.mItems.size() == 1;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        String defaultValue = getDefaultValue(i);
        String string = this.mParentDataItem.getString(getKey(i), defaultValue);
        if (string == null || string.equals(defaultValue) || checkValueValid(i, string)) {
            return string;
        }
        String simpleName = ComponentConfigSlowMotionQuality.class.getSimpleName();
        Log.e(simpleName, "Items do not have this " + string + ",so return defaultValue = " + defaultValue);
        return defaultValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "5";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_video_quality_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_video_new_slow_motion_key";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009e, code lost:
        if (r11.equals(com.android.camera.data.data.config.ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960) == false) goto L_0x00b5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c6  */
    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities, ComponentConfigSlowMotion componentConfigSlowMotion) {
        ArrayList arrayList = new ArrayList();
        if (i == 172) {
            ArrayList<String> supportedHfrSettings = getSupportedHfrSettings(cameraCapabilities);
            boolean contains = supportedHfrSettings.contains("1920x1080:240");
            boolean contains2 = supportedHfrSettings.contains(SIZE_FPS_1080_120);
            mCurrentFps = componentConfigSlowMotion.getComponentValue(i);
            boolean z = false;
            if (!(componentConfigSlowMotion.getItems().size() == 1)) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p, (int) R.drawable.ic_config_slow_720p, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, "5"));
                ComponentDataItem componentDataItem = new ComponentDataItem((int) R.drawable.ic_config_slow_1080p, (int) R.drawable.ic_config_slow_1080p, (int) R.string.pref_new_slow_motion_video_quality_entry_1080p, "6");
                String str = mCurrentFps;
                int hashCode = str.hashCode();
                if (hashCode != -1150307548) {
                    if (hashCode != -1150306525) {
                        if (hashCode == -1150299736) {
                        }
                    } else if (str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240)) {
                        z = true;
                        if (!z) {
                            componentDataItem.mIsDisabled = !contains;
                        } else if (z) {
                            componentDataItem.mIsDisabled = !contains;
                        } else if (z) {
                            componentDataItem.mIsDisabled = !contains2;
                        }
                        arrayList.add(componentDataItem);
                    }
                } else if (str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120)) {
                    z = true;
                    if (!z) {
                    }
                    arrayList.add(componentDataItem);
                }
                z = true;
                if (!z) {
                }
                arrayList.add(componentDataItem);
            } else if (contains2) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_slow_1080p_120fps, (int) R.drawable.ic_config_slow_1080p_120fps, (int) R.string.pref_new_slow_motion_video_quality_entry_1080p, "6"));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_120fps, (int) R.drawable.ic_config_slow_720p_120fps, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, "5"));
            } else {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_slow_720p_120fps, (int) R.drawable.ic_config_slow_720p_120fps, (int) R.string.pref_new_slow_motion_video_quality_entry_720p, "5"));
                if (DataRepository.dataItemFeature().Ck()) {
                    setComponentValue(i, "5");
                }
            }
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }
}
