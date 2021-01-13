package com.android.camera.module.loader;

import android.text.TextUtils;
import b.c.a.c;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigBokeh;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigGradienter;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentConfigUltraWide;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentRunning8KVideoQuality;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningColorEnhance;
import com.android.camera.data.data.runing.ComponentRunningLighting;
import com.android.camera.data.data.runing.ComponentRunningSubtitle;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.permission.PermissionManager;
import io.reactivex.annotations.NonNull;
import java.util.Iterator;

public class FunctionCameraPrepare extends Func1Base<Camera, BaseModule> {
    private static final String TAG = "FunctionCameraPrepare";
    private BaseModule baseModule;
    private int mLastMode;
    private boolean mNeedReConfigureData;
    private int mResetType;

    public FunctionCameraPrepare(int i, int i2, int i3, boolean z, BaseModule baseModule2) {
        super(i);
        this.mLastMode = i2;
        this.mResetType = i3;
        this.mNeedReConfigureData = z;
        this.baseModule = baseModule2;
    }

    private void reconfigureData() {
        boolean z;
        DataItemConfig dataItemConfig;
        int i;
        boolean z2;
        boolean z3;
        if (!this.mNeedReConfigureData) {
            DataRepository.dataItemConfig().editor().remove(CameraSettings.KEY_ZOOM).apply();
            return;
        }
        CameraSettings.upgradeGlobalPreferences();
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        DataItemConfig dataItemConfig2 = DataRepository.dataItemConfig();
        int lastCameraId = dataItemGlobal.getLastCameraId();
        ComponentConfigFlash componentFlash = dataItemConfig2.getComponentFlash();
        DataProvider.ProviderEditor editor = dataItemConfig2.editor();
        DataProvider.ProviderEditor editor2 = dataItemGlobal.editor();
        DataProvider.ProviderEditor editor3 = DataRepository.dataItemLive().editor();
        editor.remove(CameraSettings.KEY_ZOOM).remove(CameraSettings.KEY_EXPOSURE);
        String persistValue = componentFlash.getPersistValue(this.mLastMode);
        String persistValue2 = componentFlash.getPersistValue(this.mTargetMode);
        if (!componentFlash.isValidFlashValue(persistValue2)) {
            editor.remove(componentFlash.getKey(this.mTargetMode));
        }
        if (this.mResetType != 7) {
            if (persistValue2.equals("2") || persistValue2.equals("5")) {
                editor.remove(componentFlash.getKey(this.mTargetMode));
            }
            if (persistValue.equals("2") || persistValue.equals("5")) {
                editor.remove(componentFlash.getKey(this.mLastMode));
            }
        }
        String defaultWatermarkStr = CameraSettings.getDefaultWatermarkStr();
        if (TextUtils.equals(defaultWatermarkStr, dataItemGlobal.getString(CameraSettings.KEY_CUSTOM_WATERMARK, defaultWatermarkStr))) {
            editor2.remove(CameraSettings.KEY_CUSTOM_WATERMARK);
        }
        ComponentConfigRatio componentConfigRatio = dataItemConfig2.getComponentConfigRatio();
        int i2 = this.mTargetMode;
        if (i2 == 163 || i2 == 165 || i2 == 167 || i2 == 173 || i2 == 175 || i2 == 171) {
            String[] fullSupportRatioValues = componentConfigRatio.getFullSupportRatioValues();
            String persistValue3 = componentConfigRatio.getPersistValue(this.mTargetMode);
            int length = fullSupportRatioValues.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    z3 = false;
                    break;
                } else if (TextUtils.equals(fullSupportRatioValues[i3], persistValue3)) {
                    z3 = true;
                    break;
                } else {
                    i3++;
                }
            }
            if (!z3 && (this.mTargetMode == 165 || !persistValue3.equals(ComponentConfigRatio.RATIO_1X1))) {
                Log.d(TAG, "reconfigureData: clear DATA_CONFIG_RATIO");
                editor.remove("pref_camera_picturesize_key");
            }
        }
        if (this.mTargetMode == 172) {
            ComponentConfigSlowMotion componentConfigSlowMotion = dataItemConfig2.getComponentConfigSlowMotion();
            String componentValue = componentConfigSlowMotion.getComponentValue(172);
            Iterator<ComponentDataItem> it = componentConfigSlowMotion.getItems().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (TextUtils.equals(it.next().mValue, componentValue)) {
                        z2 = true;
                        break;
                    }
                } else {
                    z2 = false;
                    break;
                }
            }
            if (!z2) {
                Log.d(TAG, "reconfigureData: clear DATA_CONFIG_NEW_SLOW_MOTION_KEY");
                editor.remove(DataItemConfig.DATA_CONFIG_NEW_SLOW_MOTION_KEY);
            }
        }
        if (this.mTargetMode == 167) {
            if (!Util.isStringValueContained(dataItemConfig2.getString(CameraSettings.KEY_QC_ISO, CameraAppImpl.getAndroidContext().getString(R.string.pref_camera_iso_default)), (DataRepository.dataItemFeature().Yl() || DataRepository.dataItemFeature().Ql()) ? R.array.pref_camera_iso_entryvalues_new : R.array.pref_camera_iso_entryvalues)) {
                editor.remove(CameraSettings.KEY_QC_ISO);
            }
        }
        if (!c.Mn()) {
            editor.remove(CameraSettings.KEY_QC_FOCUS_POSITION);
            editor.remove(CameraSettings.KEY_QC_EXPOSURETIME);
        }
        if (!Util.isLabOptionsVisible()) {
            editor2.remove(CameraSettings.KEY_FACE_DETECTION).remove(CameraSettings.KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY).remove(CameraSettings.KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN).remove(CameraSettings.KEY_VIDEO_SHOW_FACE_VIEW).remove(CameraSettings.KEY_CAMERA_DUAL_ENABLE).remove(CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE).remove(CameraSettings.KEY_CAMERA_MFNR_SAT_ENABLE).remove(CameraSettings.KEY_CAMERA_SR_ENABLE);
        }
        if (!Util.isValidValue(dataItemGlobal.getString(CameraSettings.KEY_ANTIBANDING, "1"))) {
            editor2.remove(CameraSettings.KEY_ANTIBANDING);
        }
        if (!c.Jm()) {
            editor2.remove(CameraSettings.KEY_FINGERPRINT_CAPTURE);
        }
        int i4 = this.mResetType;
        if ((i4 == 4 || i4 == 2) && DataRepository.dataItemFeature().ak()) {
            editor.remove(CameraSettings.KEY_CAMERA_PIXEL_LENS);
        }
        switch (this.mResetType) {
            case 2:
            case 7:
                z = false;
                ComponentRunningLighting componentRunningLighting = dataItemRunning.getComponentRunningLighting();
                int i5 = this.mTargetMode;
                if (!componentRunningLighting.checkValueValid(i5, componentRunningLighting.getPersistValue(i5))) {
                    int i6 = this.mTargetMode;
                    componentRunningLighting.setComponentValue(i6, componentRunningLighting.getDefaultValue(i6));
                    break;
                }
                break;
            case 3:
            case 6:
                resetFlash(componentFlash, editor);
                resetHdr(dataItemConfig2.getComponentHdr(), editor);
                resetBeautyTransientAndVideoPersist(dataItemConfig2.getComponentConfigBeauty(), editor);
                resetUltraWide(dataItemConfig2.getComponentConfigUltraWide(), editor);
                resetLensType(dataItemConfig2.getComponentConfigUltraWide(), dataItemConfig2.getManuallyDualLens(), editor);
                resetGradienter(dataItemConfig2.getComponentConfigGradienter(), editor);
                resetAIWatermark(dataItemRunning.getComponentRunningAIWatermark(), false);
                resetColorEnhance(dataItemRunning.getComponentRunningColorEnhance(), false);
                editor.remove(dataItemConfig2.getComponentConfigSlowMotion().getKey(this.mTargetMode));
                resetVideoQualityPartially(dataItemConfig2.getComponentConfigVideoQuality());
                editor2.remove(DataItemConfig.DATA_CONFIG_GIF);
                dataItemRunning.getComponentRunningShine().clearArrayMap();
                if (dataItemGlobal.getCurrentCameraId() == 0) {
                    resetBeautyBackLevel(editor);
                    resetBeautyCaptureFigure(editor);
                    dataItemConfig = (DataItemConfig) DataRepository.provider().dataConfig(1);
                    z = false;
                } else {
                    z = false;
                    dataItemConfig = (DataItemConfig) DataRepository.provider().dataConfig(0);
                }
                DataProvider.ProviderEditor editor4 = dataItemConfig.editor();
                resetFlash(dataItemConfig.getComponentFlash(), editor4);
                resetHdr(dataItemConfig.getComponentHdr(), editor4);
                resetFrontBokenh(dataItemConfig.getComponentBokeh(), editor4);
                resetBeautyTransientAndVideoPersist(dataItemConfig.getComponentConfigBeauty(), editor4);
                resetVideoQualityPartially(dataItemConfig.getComponentConfigVideoQuality());
                resetBeautyVideoFront(dataItemConfig);
                editor4.apply();
                DataRepository.dataItemLive().clearAll();
                reset8KVideoQuality(dataItemRunning.getComponentRunning8KVideoQuality());
                resetSubtitle(dataItemRunning.getComponentRunningSubtitle());
                if (DataRepository.dataItemFeature().Kj() || DataRepository.dataItemFeature().Rj()) {
                    editor3.remove(CameraSettings.KEY_LIVE_MUSIC_PATH).remove(CameraSettings.KEY_LIVE_MUSIC_HINT).remove(CameraSettings.KEY_LIVE_STICKER).remove(CameraSettings.KEY_LIVE_STICKER_NAME).remove(CameraSettings.KEY_LIVE_STICKER_HINT).remove(CameraSettings.KEY_LIVE_SPEED).remove(CameraSettings.KEY_LIVE_FILTER).remove("key_live_shrink_face_ratio").remove("key_live_enlarge_eye_ratio").remove("key_live_smooth_strength").remove(CameraSettings.KEY_LIVE_BEAUTY_STATUS);
                }
                if (DataRepository.dataItemFeature().Ej()) {
                    editor3.remove(CameraSettings.KEY_MIMOJI_INDEX).remove(CameraSettings.KEY_MIMOJI_PANNEL_STATE);
                }
                if (DataRepository.dataItemFeature().Yl()) {
                    editor2.remove(CameraSettings.KEY_CAMERA_PRO_VIDEO_LOG_FROMAT).remove(CameraSettings.KEY_CAMERA_PRO_VIDEO_HISTOGRAM);
                    break;
                }
                break;
            case 4:
            case 5:
                int i7 = this.mTargetMode;
                if (i7 != 161) {
                    if (i7 != 162) {
                        if (!(i7 == 166 || i7 == 167)) {
                            if (i7 == 169) {
                                i = dataItemGlobal.getCurrentCameraId();
                            } else if (i7 != 171) {
                                if (!(i7 == 174 || i7 == 183)) {
                                    i = dataItemGlobal.getCurrentCameraId();
                                }
                            } else if (DataRepository.dataItemFeature().Bk()) {
                                i = dataItemGlobal.getCurrentCameraId();
                            }
                        }
                        i = 0;
                    } else {
                        i = dataItemGlobal.getCurrentCameraId();
                    }
                    dataItemGlobal.setCameraIdTransient(i);
                }
                i = dataItemGlobal.getCurrentCameraId();
                dataItemGlobal.setCameraIdTransient(i);
            default:
                z = false;
                break;
        }
        boolean Ij = DataRepository.dataItemFeature().Ij();
        if (!(this.mResetType == 4 && lastCameraId == dataItemGlobal.getCurrentCameraId())) {
            z = Ij;
        }
        if (z) {
            editor.putBoolean(CameraSettings.KEY_LENS_DIRTY_DETECT_ENABLED, true);
        }
        editor.apply();
        editor2.apply();
        editor3.apply();
    }

    private void reset8KVideoQuality(ComponentRunning8KVideoQuality componentRunning8KVideoQuality) {
        componentRunning8KVideoQuality.clearArrayMap();
    }

    private void resetAIWatermark(ComponentRunningAIWatermark componentRunningAIWatermark, boolean z) {
        if (componentRunningAIWatermark != null) {
            componentRunningAIWatermark.resetAIWatermark(z);
        }
    }

    private void resetBeautyBackLevel(DataProvider.ProviderEditor providerEditor) {
        String[] strArr = BeautyConstant.BEAUTY_CATEGORY_LEVEL;
        for (String str : strArr) {
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForCapture(str));
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForVideo(str));
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForPortrait(str));
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForFun(str));
        }
    }

    private void resetBeautyCaptureFigure(DataProvider.ProviderEditor providerEditor) {
        for (String str : BeautyConstant.BEAUTY_CATEGORY_BACK_FIGURE) {
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForCapture(str));
        }
    }

    private void resetBeautyTransientAndVideoPersist(ComponentConfigBeauty componentConfigBeauty, DataProvider.ProviderEditor providerEditor) {
        componentConfigBeauty.clearClosed();
        String persistValue = componentConfigBeauty.getPersistValue(162);
        String defaultValue = componentConfigBeauty.getDefaultValue(162);
        if (!TextUtils.equals(persistValue, defaultValue)) {
            providerEditor.putString(componentConfigBeauty.getKey(162), defaultValue);
        }
    }

    private void resetBeautyVideoFront(DataProvider.ProviderEditor providerEditor) {
        for (String str : BeautyConstant.BEAUTY_CATEGORY_LEVEL) {
            providerEditor.remove(BeautyConstant.wrappedSettingKeyForVideo(str));
        }
    }

    private void resetColorEnhance(ComponentRunningColorEnhance componentRunningColorEnhance, boolean z) {
        if (componentRunningColorEnhance != null) {
            componentRunningColorEnhance.reset(z);
        }
    }

    private void resetFlash(ComponentConfigFlash componentConfigFlash, DataProvider.ProviderEditor providerEditor) {
        componentConfigFlash.resetToDefault(providerEditor);
    }

    private void resetFrontBokenh(ComponentConfigBokeh componentConfigBokeh, DataProvider.ProviderEditor providerEditor) {
        if ("on".equals(componentConfigBokeh.getPersistValue(this.mTargetMode))) {
            componentConfigBokeh.setComponentValue(this.mTargetMode, "off");
        }
    }

    private void resetGradienter(ComponentConfigGradienter componentConfigGradienter, DataProvider.ProviderEditor providerEditor) {
        if (componentConfigGradienter != null) {
            componentConfigGradienter.resetToDefault(providerEditor);
        }
    }

    private void resetHdr(ComponentConfigHdr componentConfigHdr, DataProvider.ProviderEditor providerEditor) {
        String persistValue = componentConfigHdr.getPersistValue(this.mTargetMode);
        if (!persistValue.equals("auto") && !persistValue.equals("off")) {
            providerEditor.putString(componentConfigHdr.getKey(this.mTargetMode), componentConfigHdr.getDefaultValue(this.mTargetMode));
        }
    }

    private void resetLensType(ComponentConfigUltraWide componentConfigUltraWide, ComponentManuallyDualLens componentManuallyDualLens, DataProvider.ProviderEditor providerEditor) {
        if (componentConfigUltraWide != null && componentManuallyDualLens != null) {
            componentManuallyDualLens.resetLensType(componentConfigUltraWide, providerEditor);
        }
    }

    private void resetSubtitle(ComponentRunningSubtitle componentRunningSubtitle) {
        componentRunningSubtitle.clearArrayMap();
    }

    private void resetUltraWide(ComponentConfigUltraWide componentConfigUltraWide, DataProvider.ProviderEditor providerEditor) {
        if (componentConfigUltraWide != null) {
            componentConfigUltraWide.resetUltraWide(providerEditor);
        }
    }

    private void resetVideoQualityPartially(ComponentConfigVideoQuality componentConfigVideoQuality) {
        componentConfigVideoQuality.reset();
    }

    public NullHolder<BaseModule> apply(@NonNull NullHolder<Camera> nullHolder) throws Exception {
        if (!nullHolder.isPresent()) {
            return NullHolder.ofNullable(null, 234);
        }
        if (!PermissionManager.checkCameraLaunchPermissions()) {
            return NullHolder.ofNullable(null, 229);
        }
        Camera camera = nullHolder.get();
        if (camera.isFinishing()) {
            Log.d(TAG, "activity is finishing, the content of BaseModuleHolder is set to null");
            return NullHolder.ofNullable(null, 235);
        }
        camera.changeRequestOrientation();
        if (this.baseModule.isDeparted()) {
            return NullHolder.ofNullable(this.baseModule, 225);
        }
        reconfigureData();
        return NullHolder.ofNullable(this.baseModule);
    }
}
