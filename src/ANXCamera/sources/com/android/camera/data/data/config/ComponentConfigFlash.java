package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.SparseArray;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ThermalDetector;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TargetApi(21)
public class ComponentConfigFlash extends ComponentData {
    public static final String FLASH_VALUE_AUTO = "3";
    public static final String FLASH_VALUE_BACK_SOFT_LIGHT = "5";
    public static final String FLASH_VALUE_MANUAL_OFF = "200";
    public static final String FLASH_VALUE_OFF = "0";
    public static final String FLASH_VALUE_ON = "1";
    public static final String FLASH_VALUE_SCREEN_LIGHT_AUTO = "103";
    public static final String FLASH_VALUE_SCREEN_LIGHT_ON = "101";
    public static final String FLASH_VALUE_TORCH = "2";
    private SparseArray<String> mFlashValuesForSceneMode = new SparseArray<>();
    private boolean mIsBackSoftLightSupported;
    private boolean mIsClosed;
    private boolean mIsHardwareSupported;

    public ComponentConfigFlash(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        this.mItems = new ArrayList();
        this.mItems.add(new ComponentDataItem(getFlashOffRes()[0], getFlashOffRes()[1], (int) R.string.pref_camera_flashmode_entry_off, "0"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0057, code lost:
        if (r29 != 185) goto L_0x00c0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c7 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01de  */
    private List<ComponentDataItem> createItems(int i, int i2, CameraCapabilities cameraCapabilities, ComponentConfigUltraWide componentConfigUltraWide) {
        ArrayList arrayList = new ArrayList();
        this.mIsHardwareSupported = cameraCapabilities.isFlashSupported() && DataRepository.dataItemGlobal().getDisplayMode() == 1;
        this.mIsBackSoftLightSupported = DataRepository.dataItemFeature().wj() && i2 == 0 && cameraCapabilities.isBackSoftLightSupported();
        if (i != 166) {
            if (i != 171) {
                if (!(i == 173 || i == 187)) {
                    if (i != 204) {
                        if (i != 210) {
                            if (i == 184) {
                                if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 1 && CameraSettings.isFrontCamera()) {
                                    return arrayList;
                                }
                            }
                        }
                    } else if (DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch()) {
                        return arrayList;
                    }
                    if (this.mIsHardwareSupported) {
                        if (i2 == 1 && c.En()) {
                            boolean z = i == 173 && DataRepository.dataItemFeature().im();
                            if (i == 163 || i == 165 || i == 171 || i == 205 || z) {
                                arrayList.add(new ComponentDataItem(getFlashOffRes()[0], (int) R.drawable.ic_new_config_flash_off_shadow, getFlashOffRes()[1], (int) R.string.pref_camera_flashmode_entry_off, "0"));
                                arrayList.add(new ComponentDataItem(getFlashAutoRes()[0], R.drawable.ic_new_config_flash_auto_shadow, getFlashAutoRes()[1], getFlashAutoRes()[0], getFlashAutoRes()[2], R.drawable.ic_new_config_fash_auto_label_shadow, R.string.pref_camera_flashmode_entry_auto, FLASH_VALUE_SCREEN_LIGHT_AUTO));
                                arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, FLASH_VALUE_SCREEN_LIGHT_ON));
                            }
                            if (i == 177) {
                                arrayList.add(new ComponentDataItem(getFlashOffRes()[0], (int) R.drawable.ic_new_config_flash_off_shadow, getFlashOffRes()[1], (int) R.string.pref_camera_flashmode_entry_off, "0"));
                                arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, FLASH_VALUE_SCREEN_LIGHT_ON));
                            } else if (i == 184 && DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 0) {
                                arrayList.add(new ComponentDataItem(getFlashOffRes()[0], (int) R.drawable.ic_new_config_flash_off_shadow, getFlashOffRes()[1], (int) R.string.pref_camera_flashmode_entry_off, "0"));
                                arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, FLASH_VALUE_SCREEN_LIGHT_ON));
                            }
                        }
                        return arrayList;
                    }
                    arrayList.add(new ComponentDataItem(getFlashOffRes()[0], (int) R.drawable.ic_new_config_flash_off_shadow, getFlashOffRes()[1], (int) R.string.pref_camera_flashmode_entry_off, "0"));
                    if (!(i == 161 || i == 162 || i == 169 || i == 172 || i == 174)) {
                        if (i == 177) {
                            if (CameraSettings.isBackCamera()) {
                                arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, "1"));
                            }
                            if (CameraSettings.isFrontCamera() && c.yn()) {
                                arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, "2"));
                            } else if (c.Zn()) {
                                arrayList.add(new ComponentDataItem(getFlashTorchRes()[0], (int) R.drawable.ic_new_config_flash_torch_shadow, getFlashTorchRes()[1], (int) R.string.pref_camera_flashmode_entry_torch, "2"));
                            }
                        } else if (!(i == 204 || i == 179 || i == 180 || i == 183)) {
                            if (i != 184) {
                                arrayList.add(new ComponentDataItem(getFlashAutoRes()[0], R.drawable.ic_new_config_flash_auto_shadow, getFlashAutoRes()[1], getFlashAutoRes()[0], getFlashAutoRes()[2], R.drawable.ic_new_config_fash_auto_label_shadow, R.string.pref_camera_flashmode_entry_auto, "3"));
                                if (CameraSettings.isBackCamera()) {
                                    arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, "1"));
                                }
                                if (CameraSettings.isFrontCamera() && c.yn()) {
                                    arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, "2"));
                                } else if (c.Zn()) {
                                    arrayList.add(new ComponentDataItem(getFlashTorchRes()[0], (int) R.drawable.ic_new_config_flash_torch_shadow, getFlashTorchRes()[1], (int) R.string.pref_camera_flashmode_entry_torch, "2"));
                                }
                                if (this.mIsBackSoftLightSupported) {
                                    arrayList.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), (int) R.drawable.ic_new_config_flash_back_soft_light_shadow, getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, "5"));
                                }
                            } else {
                                if (CameraSettings.isBackCamera() && DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 0) {
                                    arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, "1"));
                                }
                                if (CameraSettings.isFrontCamera() && c.yn()) {
                                    arrayList.add(new ComponentDataItem(getFlashOnRes()[0], (int) R.drawable.ic_new_config_flash_on_shadow, getFlashOnRes()[1], (int) R.string.pref_camera_flashmode_entry_on, "2"));
                                } else if (c.Zn()) {
                                    arrayList.add(new ComponentDataItem(getFlashTorchRes()[0], (int) R.drawable.ic_new_config_flash_torch_shadow, getFlashTorchRes()[1], (int) R.string.pref_camera_flashmode_entry_torch, "2"));
                                }
                            }
                        }
                        return arrayList;
                    }
                    arrayList.add(new ComponentDataItem(getFlashTorchRes()[0], (int) R.drawable.ic_new_config_flash_torch_shadow, getFlashTorchRes()[1], (int) R.string.pref_camera_flashmode_entry_torch, "2"));
                    if (this.mIsBackSoftLightSupported) {
                        arrayList.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), (int) R.drawable.ic_new_config_flash_back_soft_light_shadow, getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, "5"));
                    }
                    return arrayList;
                }
            } else if (this.mIsHardwareSupported && this.mIsBackSoftLightSupported) {
                arrayList.add(new ComponentDataItem(getFlashBackSoftLightRes()[0], (int) R.drawable.ic_new_config_flash_back_soft_light_shadow, getFlashBackSoftLightRes()[1], (int) R.string.pref_camera_flashmode_entry_off, "0"));
                arrayList.add(new ComponentDataItem(getFlashBackSoftLightSelectedRes(), (int) R.drawable.ic_new_config_flash_back_soft_light_shadow, getFlashBackSoftLightSelectedRes(), (int) R.string.pref_camera_flashmode_entry_back_soft_light, "5"));
                return arrayList;
            }
        }
        if (i2 == 0) {
            return arrayList;
        }
        if (this.mIsHardwareSupported) {
        }
    }

    private String getComponentValueInternal(int i) {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        if (dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key")) {
            String flashModeByScene = CameraSettings.getFlashModeByScene(dataItemRunning.getComponentRunningSceneValue().getComponentValue(i));
            if (!TextUtils.isEmpty(flashModeByScene)) {
                return flashModeByScene;
            }
        }
        return super.getComponentValue(i);
    }

    private int[] getFlashAutoRes() {
        return new int[]{R.drawable.ic_new_config_flash_auto, R.drawable.ic_new_config_flash_auto, R.drawable.ic_new_config_fash_auto_label};
    }

    private int[] getFlashBackSoftLightRes() {
        return new int[]{R.drawable.ic_new_config_flash_back_soft_light, R.drawable.ic_new_config_flash_back_soft_light};
    }

    private int getFlashBackSoftLightSelectedRes() {
        return R.drawable.ic_new_config_flash_back_soft_light;
    }

    private int[] getFlashOffRes() {
        return new int[]{R.drawable.ic_new_config_flash_off, R.drawable.ic_new_config_flash_off};
    }

    private int[] getFlashOnRes() {
        return new int[]{R.drawable.ic_new_config_flash_on, R.drawable.ic_new_config_flash_on};
    }

    private int[] getFlashTorchRes() {
        return new int[]{R.drawable.ic_new_config_flash_torch, R.drawable.ic_new_config_flash_torch};
    }

    public void clearClosed() {
        this.mIsClosed = false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return ThermalDetector.getInstance().thermalCloseFlash() && isHardwareSupported();
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return (!isClosed() && !isDisabled(i) && !isEmpty()) ? getComponentValueInternal(i) : "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisableReasonString() {
        return CameraSettings.isFrontCamera() ? R.string.close_front_flash_toast : R.string.close_back_flash_toast;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_flashmode_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 169 || i == 174) {
            return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
        }
        if (i == 177) {
            return CameraSettings.KEY_FUN_AR_FLASH_MODE;
        }
        if (i == 204) {
            return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
        }
        if (i == 171) {
            return CameraSettings.KEY_PORTRAIT_FLASH_MODE;
        }
        if (i == 172 || i == 179 || i == 180 || i == 183) {
            return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
        }
        if (i == 184) {
            return DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 0 ? CameraSettings.KEY_FUN_AR2_PHOTO_FLASH_MODE : CameraSettings.KEY_FUN_AR2_VIDEO_FLASH_MODE;
        }
        switch (i) {
            case 160:
                throw new RuntimeException("unspecified flash");
            case 161:
            case 162:
                return CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE;
            default:
                return CameraSettings.KEY_FLASH_MODE;
        }
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("1".equals(componentValue)) {
            return getFlashOnRes()[0];
        }
        if ("3".equals(componentValue)) {
            return getFlashAutoRes()[0];
        }
        if ("0".equals(componentValue)) {
            return (i != 171 || !this.mIsBackSoftLightSupported) ? getFlashOffRes()[0] : getFlashBackSoftLightRes()[0];
        }
        if ("2".equals(componentValue)) {
            return CameraSettings.isFrontCamera() ? getFlashOnRes()[0] : getFlashTorchRes()[0];
        }
        if (FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
            return getFlashAutoRes()[0];
        }
        if (FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
            return getFlashOnRes()[0];
        }
        if ("5".equals(componentValue)) {
            return getFlashBackSoftLightSelectedRes();
        }
        return -1;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getValueSelectedShadowDrawable(int i) {
        String componentValue = getComponentValue(i);
        if ("1".equals(componentValue)) {
            return R.drawable.ic_new_config_flash_on_shadow;
        }
        if ("3".equals(componentValue)) {
            return R.drawable.ic_new_config_flash_auto_shadow;
        }
        if ("0".equals(componentValue)) {
            return (i != 171 || !this.mIsBackSoftLightSupported) ? R.drawable.ic_new_config_flash_off_shadow : R.drawable.ic_new_config_flash_back_soft_light_shadow;
        }
        if ("2".equals(componentValue)) {
            return CameraSettings.isFrontCamera() ? R.drawable.ic_new_config_flash_on_shadow : R.drawable.ic_new_config_flash_torch_shadow;
        }
        if (FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
            return R.drawable.ic_new_config_flash_auto_shadow;
        }
        if (FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
            return R.drawable.ic_new_config_flash_on_shadow;
        }
        if ("5".equals(componentValue)) {
            return R.drawable.ic_new_config_flash_back_soft_light_shadow;
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("1".equals(componentValue)) {
            return R.string.accessibility_flash_on;
        }
        if ("3".equals(componentValue)) {
            return R.string.accessibility_flash_auto;
        }
        if ("0".equals(componentValue)) {
            return R.string.accessibility_flash_off;
        }
        if ("2".equals(componentValue)) {
            return CameraSettings.isFrontCamera() ? R.string.accessibility_flash_on : R.string.accessibility_flash_torch;
        }
        if (FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
            return R.string.accessibility_flash_auto;
        }
        if (FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
            return R.string.accessibility_flash_on;
        }
        if ("5".equals(componentValue)) {
            return R.string.accessibility_flash_back_soft_light;
        }
        return -1;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isDisabled(int i) {
        if (i != 167 || CameraSettings.isFlashSupportedInManualMode()) {
            return i == 184 && DataRepository.dataItemLive().getMimojiStatusManager2().getMode() == 4;
        }
        return true;
    }

    public boolean isHardwareSupported() {
        return this.mIsHardwareSupported;
    }

    public boolean isValidFlashValue(String str) {
        return str.matches("^[0-9]+$");
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities, ComponentConfigUltraWide componentConfigUltraWide) {
        this.mItems = Collections.unmodifiableList(createItems(i, i2, cameraCapabilities, componentConfigUltraWide));
    }

    public void resetIfNeed(String str, DataProvider.ProviderEditor providerEditor) {
        String string = this.mParentDataItem.getString(str, "0");
        if (!string.equals("0") && !string.equals("3")) {
            providerEditor.remove(str);
        }
    }

    public void resetToDefault(DataProvider.ProviderEditor providerEditor) {
        resetIfNeed(getKey(163), providerEditor);
        resetIfNeed(getKey(162), providerEditor);
        resetIfNeed(getKey(171), providerEditor);
        resetIfNeed(getKey(177), providerEditor);
        resetIfNeed(CameraSettings.KEY_FUN_AR2_PHOTO_FLASH_MODE, providerEditor);
        resetIfNeed(CameraSettings.KEY_FUN_AR2_VIDEO_FLASH_MODE, providerEditor);
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        setClosed(false);
        super.setComponentValue(i, str);
    }

    public void setSceneModeFlashValue(int i, String str) {
        this.mFlashValuesForSceneMode.put(i, str);
    }
}
