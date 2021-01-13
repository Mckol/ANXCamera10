package com.android.camera.module.impl.component;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.StringRes;
import com.android.camera.ActivityBase;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.ModeEditorActivity;
import com.android.camera.MutexModeManager;
import com.android.camera.R;
import com.android.camera.ThermalDetector;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.aiwatermark.chain.AbstractPriorityChain;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.EyeLightConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigAi;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigLiveShot;
import com.android.camera.data.data.config.ComponentConfigMeter;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentConfigRaw;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentConfigSlowMotionQuality;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.extra.ComponentLiveVideoQuality;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningColorEnhance;
import com.android.camera.data.data.runing.ComponentRunningEisPro;
import com.android.camera.data.data.runing.ComponentRunningEyeLight;
import com.android.camera.data.data.runing.ComponentRunningFastMotionDuration;
import com.android.camera.data.data.runing.ComponentRunningFastMotionSpeed;
import com.android.camera.data.data.runing.ComponentRunningMoon;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.fragment.beauty.ShineHelper;
import com.android.camera.fragment.clone.Config;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.settings.CameraPreferenceFragment;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.VideoBase;
import com.android.camera.module.VideoModule;
import com.android.camera.module.interceptor.ConfigChangeInterceptor;
import com.android.camera.module.loader.StartControl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.timerburst.TimerBurstController;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.xiaomi.fenshen.FenShenCam;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ConfigChangeImpl implements ModeProtocol.ConfigChanges {
    private static final String TAG = "ConfigChangeImpl";
    private ActivityBase mActivity;
    private AbstractPriorityChain mChain = null;
    private ConfigChangeInterceptor mChangeInterceptor;
    private int[] mRecordingClosedElements;

    public ConfigChangeImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    static /* synthetic */ void a(int[] iArr, BaseModule baseModule) {
        baseModule.updatePreferenceTrampoline(iArr);
        Camera2Proxy cameraDevice = baseModule.getCameraDevice();
        if (cameraDevice != null) {
            cameraDevice.resumePreview();
        }
    }

    private void applyConfig(int i, int i2) {
        switch (i) {
            case 161:
                showOrHideMimoji();
                return;
            case 162:
                configGif(i2);
                return;
            case 163:
            case 169:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 197:
            case 198:
            case 200:
            case 202:
            case 204:
            case 208:
            case 210:
            case 211:
            case 213:
            case 214:
            case 222:
            case 224:
            case 226:
            case 232:
            case 242:
            case 244:
            case 245:
            case 250:
            case 254:
            case 257:
            case 259:
            default:
                return;
            case 164:
                configCloneUseGuide();
                return;
            case 165:
                configSuperEISPro();
                return;
            case 166:
                configIDCard();
                return;
            case 167:
                configNearRangeMode();
                return;
            case 168:
                configAiAudio();
                return;
            case 170:
                configTimerBurst(i2);
                return;
            case 171:
                configAi108();
                return;
            case 195:
                configPortraitSwitch(i2);
                return;
            case 196:
                showOrHideShine();
                return;
            case 199:
                configFocusPeakSwitch(i2);
                return;
            case 201:
                configAiSceneSwitch(i2);
                return;
            case 203:
                showOrHideLighting(true);
                return;
            case 205:
                configSwitchUltraWide();
                return;
            case 206:
                configLiveShotSwitch(i2);
                return;
            case 207:
                configSwitchUltraWideBokeh();
                return;
            case 209:
                configSwitchUltraPixel(i2);
                return;
            case 212:
                showOrHideShine();
                return;
            case 215:
                configUltraPixelPortrait(i2);
                return;
            case 216:
                configVV();
                return;
            case 217:
                configBack();
                return;
            case 218:
                configSuperEIS();
                return;
            case 219:
                configReferenceLineSwitch(i2);
                return;
            case 220:
                configVideoSubtitle();
                return;
            case 221:
                configDocumentMode(i2);
                return;
            case 223:
                configAIWatermark(i2);
                return;
            case 225:
                showSetting();
                return;
            case 227:
                configColorEnhance(i2);
                return;
            case 228:
                configTiltSwitch(i2);
                return;
            case 229:
                configGradienterSwitch(i2);
                return;
            case 230:
                configHHTSwitch(i2);
                return;
            case 231:
                configMagicFocusSwitch();
                return;
            case 233:
                configVideoFast();
                return;
            case 234:
                beautyMutexHandle();
                configScene(i2);
                return;
            case 235:
                configGroupSwitch(i2);
                return;
            case 236:
                configMagicMirrorSwitch(i2);
                return;
            case 237:
                configRawSwitch(i2);
                return;
            case 238:
                configGenderAgeSwitch(i2);
                return;
            case 239:
                showOrHideShine();
                return;
            case 240:
                configDualWaterMarkSwitch();
                return;
            case 241:
                configSuperResolutionSwitch(i2);
                return;
            case 243:
                configVideoBokehSwitch(i2);
                return;
            case 246:
                configMoon(true);
                return;
            case 247:
                configMoonNight();
                return;
            case 248:
                configSilhouette();
                return;
            case 249:
                configMoonBacklight();
                return;
            case 251:
                configCinematicAspectRatio(i2);
                return;
            case 252:
                configSwitchHandGesture();
                return;
            case 253:
                configAutoZoom();
                return;
            case 255:
                configMacroMode();
                return;
            case 256:
                configVideo8K();
                return;
            case 258:
                configExposureFeedbackSwitch(i2);
                return;
            case 260:
                configVideoLogSwitch(i2);
                return;
            case 261:
                configRGBHistogramSwitch(i2);
                return;
            case 262:
                configSpeechShutter();
                return;
        }
    }

    private void applyConfigValue(int i, String str) {
        if (i == 187) {
            configLiveVideoQuality(str);
        } else if (i == 204) {
            configFPS960(str);
        } else if (i == 208) {
            configVideoQuality(str);
        } else if (i == 210) {
            configRatio(false, str);
        } else if (i == 213) {
            configSlowQuality(str);
        } else if (i == 221) {
            configDocumentModeValue(str);
        } else if (i == 226) {
            configTimerSwitch(str);
        } else if (i == 228) {
            configTilt(str);
        } else if (i == 246) {
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 3357441) {
                if (hashCode == 104817688 && str.equals(ComponentRunningMoon.NIGHT)) {
                    c2 = 1;
                }
            } else if (str.equals(ComponentRunningMoon.MOON)) {
                c2 = 0;
            }
            if (c2 == 0) {
                configMoon(true);
            } else if (c2 == 1) {
                configMoonNight();
            }
        }
    }

    static /* synthetic */ void b(boolean z, BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            String str = TAG;
            Log.d(str, "(moon_mode) config moon:" + z);
            ((Camera2Module) baseModule).updateMoon(z);
        }
    }

    private void beautyMutexHandle() {
    }

    static /* synthetic */ void c(boolean z, BaseModule baseModule) {
        ModeProtocol.TopAlert topAlert;
        int moduleIndex = baseModule.getModuleIndex();
        if (moduleIndex == 172 && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && !topAlert.isExtraMenuShowing()) {
            int valueSelectedStringIdIgnoreClose = DataRepository.dataItemConfig().getComponentConfigSlowMotion().getValueSelectedStringIdIgnoreClose(moduleIndex);
            topAlert.alertVideoUltraClear(valueSelectedStringIdIgnoreClose != -1 ? 0 : 8, valueSelectedStringIdIgnoreClose);
            if (z && CameraSettings.isFPS960(baseModule.getModuleIndex())) {
                topAlert.alertAiDetectTipHint(0, R.string.fps960_toast, 3000);
            }
        }
    }

    private void changeMode(int i) {
        DataRepository.dataItemGlobal().setCurrentMode(i);
        ActivityBase activityBase = this.mActivity;
        if (activityBase != null) {
            activityBase.onModeSelected(StartControl.create(i).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
            return;
        }
        String str = TAG;
        Log.d(str, "ignore changeMode " + i);
    }

    private void changeModeWithoutConfigureData(int i, boolean z) {
        ActivityBase activityBase = this.mActivity;
        int i2 = 2;
        StartControl viewConfigType = StartControl.create(i).setViewConfigType(2);
        if (!z) {
            i2 = 7;
        }
        activityBase.onModeSelected(viewConfigType.setResetType(i2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
    }

    private void clearToast() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
            bottomPopupTips.setPortraitHintVisible(8);
            bottomPopupTips.hideLeftTipImage();
            bottomPopupTips.hideRightTipImage();
            bottomPopupTips.hideSpeedTipImage();
            bottomPopupTips.hideCenterTipImage();
            bottomPopupTips.directHideLyingDirectHint();
            bottomPopupTips.reConfigQrCodeTip();
        }
    }

    private boolean closeVideoFast() {
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        if (dataItemGlobal.getCurrentMode() != 169) {
            return false;
        }
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        dataItemGlobal.setCurrentMode(162);
        dataItemRunning.switchOff("pref_video_speed_fast_key");
        return true;
    }

    private void configAIWatermark(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            int i2 = 163;
            if (moduleIndex == 163 || moduleIndex == 165 || moduleIndex == 188) {
                ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
                boolean aIWatermarkEnable = componentRunningAIWatermark.getAIWatermarkEnable();
                if (i != 3 || aIWatermarkEnable) {
                    setTipsState("ai_watermark", true);
                    boolean z = !aIWatermarkEnable;
                    if (z) {
                        configDocumentMode(3);
                        ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                        if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                            componentRunningMacroMode.setSwitchOff(moduleIndex);
                        }
                        watermarkToast();
                        configTimerBurst(3);
                    }
                    componentRunningAIWatermark.setAIWatermarkEnable(z);
                    componentRunningAIWatermark.resetAIWatermark(z);
                    setWatermarkEnable(z);
                    if (1 == i) {
                        CameraStatUtils.trackAIWatermarkClick(z ? MistatsConstants.AIWatermark.AI_WATERMARK_OPEN : MistatsConstants.AIWatermark.AI_WATERMARK_CLOSE);
                    }
                    updateASDForWatermark();
                    componentRunningAIWatermark.setEnable(CameraSettings.getBogusCameraId(), z);
                    if (!z && TextUtils.equals(DataRepository.dataItemConfig().getComponentConfigRatio().getComponentValue(165), ComponentConfigRatio.RATIO_1X1)) {
                        i2 = 165;
                    }
                    Log.d(TAG, "newMode=" + i2);
                    DataRepository.dataItemGlobal().setCurrentMode(i2);
                    this.mActivity.onModeSelected(StartControl.create(i2).setViewConfigType(2).setResetType(7).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
                }
            }
        }
    }

    private void configAiAudio() {
        boolean z;
        ComponentManuallyDualLens manuallyDualLens;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            int i = 0;
            if (CameraSettings.isMacroModeEnabled(moduleIndex)) {
                DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
                z = true;
            } else {
                z = false;
            }
            if (CameraSettings.isAutoZoomEnabled(moduleIndex)) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                z = true;
            }
            if (moduleIndex == 180 && (manuallyDualLens = DataRepository.dataItemConfig().getManuallyDualLens()) != null && TextUtils.equals(manuallyDualLens.getComponentValue(moduleIndex), "macro")) {
                manuallyDualLens.setComponentValue(moduleIndex, ComponentManuallyDualLens.LENS_WIDE);
                z = true;
            }
            if (z) {
                setTipsState(FragmentTopAlert.TIP_AI_AUDIO, true);
                changeMode(moduleIndex);
                return;
            }
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                int currentStringRes = DataRepository.dataItemRunning().getComponentRunningAiAudio().getCurrentStringRes(moduleIndex);
                if (currentStringRes == -1) {
                    i = 8;
                }
                topAlert.alertAiAudioBGHint(i, currentStringRes);
            }
        }
    }

    private void configAiSceneSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean aiSceneOpen = CameraSettings.getAiSceneOpen(moduleIndex);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("configAiSceneSwitch: ");
            sb.append(!aiSceneOpen);
            Log.d(str, sb.toString());
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (i == 1) {
                if (!aiSceneOpen) {
                    topAlert.alertSwitchTip("ai", 0, R.string.pref_camera_front_ai_scene_entry_on);
                    CameraSettings.setAiSceneOpen(moduleIndex, true);
                    if (EffectController.getInstance().getAiColorCorrectionVersion() >= 1) {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_CC, true, null);
                    } else {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_SCENE, true, null);
                    }
                } else {
                    topAlert.alertSwitchTip("ai", 8, R.string.pref_camera_front_ai_scene_entry_off);
                    CameraSettings.setAiSceneOpen(moduleIndex, false);
                    if (EffectController.getInstance().getAiColorCorrectionVersion() >= 1) {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_CC, false, null);
                    } else {
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_SCENE, false, null);
                    }
                    BaseModule baseModule2 = baseModule.get();
                    if (baseModule2 != null && (baseModule2 instanceof Camera2Module)) {
                        ((Camera2Module) baseModule2).closeMoonMode(0, 8);
                    }
                }
                topAlert.updateConfigItem(201);
                if (CameraSettings.isGroupShotOn()) {
                    ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configGroupSwitch(4);
                    topAlert.refreshExtraMenu();
                }
            } else if (i != 2 && i == 3) {
                CameraSettings.setAiSceneOpen(moduleIndex, false);
                topAlert.updateConfigItem(201);
            }
            baseModule.get().updatePreferenceTrampoline(36);
            Camera2Proxy cameraDevice = baseModule.get().getCameraDevice();
            if (cameraDevice != null) {
                cameraDevice.resumePreview();
            }
            if (i == 1 && CameraSettings.isUltraPixelOn()) {
                configSwitchUltraPixel(3);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
            if (i == 1 && CameraSettings.isUltraPixelPortraitFrontOn()) {
                configUltraPixelPortrait(3);
            }
        }
    }

    private void configAutoZoom() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            setTipsState(FragmentTopAlert.TIP_AUTO_ZOOM, true);
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(moduleIndex);
            HybridZoomingSystem.clearZoomRatioHistory();
            if (isAutoZoomEnabled) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                topAlert.updateConfigItem(253);
            } else {
                CameraSettings.setAutoZoomEnabled(moduleIndex, true);
                topAlert.updateConfigItem(253);
                switchOffElementsSilent(216);
                closeVideoFast();
                singeSwitchVideoBeauty(false);
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                CameraSettings.setGradienterOn(false);
                CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, false);
                CameraSettings.setVideoQuality8K(moduleIndex, false);
                DataRepository.dataItemRunning().getComponentRunningAiAudio().setComponentValue(moduleIndex, "normal");
            }
            ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
            if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                componentRunningMacroMode.setSwitchOff(moduleIndex);
            }
            if (moduleIndex == 204) {
                DataRepository.dataItemGlobal().setCurrentMode(162);
            }
            changeModeWithoutConfigureData(162, false);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
        }
    }

    private void configBack() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 179) {
                configVVBack();
            } else if (moduleIndex == 182) {
                configIDCardBack();
            } else if (moduleIndex == 185) {
                configCloneModeBack();
            }
        }
    }

    private void configCloneModeBack() {
        Log.d(TAG, "configCloneModeBack");
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess != null) {
            CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_BACK_CLICK);
            cloneProcess.showExitConfirm(true);
        }
    }

    private void configCloneUseGuide() {
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(23);
        if (Config.getCloneMode() == FenShenCam.Mode.PHOTO) {
            CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_PHOTO_GUIDE_CLICK);
        } else {
            CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_VIDEO_GUIDE_CLICK);
        }
    }

    private void configColorEnhance(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            ComponentRunningColorEnhance componentRunningColorEnhance = DataRepository.dataItemRunning().getComponentRunningColorEnhance();
            boolean isEnabled = componentRunningColorEnhance.isEnabled(moduleIndex);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (i == 1) {
                if (isEnabled) {
                    componentRunningColorEnhance.setEnabled(false, 1);
                    topAlert.alertProColourHint(8, R.string.pro_color_mode);
                    CameraStatUtils.trackProColorClick(MistatsConstants.ProColor.VALUE_CLOSE);
                } else {
                    componentRunningColorEnhance.setEnabled(true, 1);
                    topAlert.alertProColourHint(0, R.string.pro_color_mode);
                    CameraStatUtils.trackProColorClick(MistatsConstants.ProColor.VALUE_OPEN);
                }
            }
            topAlert.updateConfigItem(227);
            baseModule.get().updatePreferenceInWorkThread(74);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0094  */
    private void configDocumentMode(int i) {
        ModeProtocol.TopAlert topAlert;
        if (DataRepository.dataItemFeature().qj() || DataRepository.dataItemFeature().rj()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                int moduleIndex = baseModule.get().getModuleIndex();
                boolean isDocumentModeOn = CameraSettings.isDocumentModeOn(moduleIndex);
                if (i != 1) {
                    if (i != 2 && i == 3) {
                        if (isDocumentModeOn) {
                            CameraSettings.setDocumentModeOn(false);
                        } else {
                            return;
                        }
                    }
                    ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                    if (isDocumentModeOn) {
                        topAlert.alertSlideSwitchLayout(true, 221);
                        getBaseModule().ifPresent(s.INSTANCE);
                    } else {
                        topAlert.alertSlideSwitchLayout(false, 221);
                    }
                    CameraStatUtils.trackDocumentModeChanged(CameraStatUtils.getDocumentModeValue(moduleIndex));
                } else if (isDocumentModeOn) {
                    CameraSettings.setDocumentModeOn(false);
                    restoreAllMutexElement("p");
                } else {
                    CameraSettings.setDocumentModeOn(true);
                    closeMutexElement("p", 196);
                    configLiveShotSwitch(3);
                    configTiltSwitch(3);
                    if (CameraSettings.isUltraPixelOn()) {
                        configSwitchUltraPixel(3);
                    }
                    isDocumentModeOn = true;
                    ModeProtocol.ActionProcessing actionProcessing2 = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                    if (isDocumentModeOn) {
                    }
                    CameraStatUtils.trackDocumentModeChanged(CameraStatUtils.getDocumentModeValue(moduleIndex));
                }
                isDocumentModeOn = false;
                ModeProtocol.ActionProcessing actionProcessing22 = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (isDocumentModeOn) {
                }
                CameraStatUtils.trackDocumentModeChanged(CameraStatUtils.getDocumentModeValue(moduleIndex));
            }
        }
    }

    private void configDocumentModeValue(String str) {
        DataRepository.dataItemRunning().getComponentRunningDocument().setComponentValue(186, str);
    }

    private void configGif(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean z = !CameraSettings.isGifOn();
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.changeToGif(z);
            }
            CameraSettings.setGifSwitch(z);
            if (z) {
                DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiRecordState(1);
            }
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.updateConfigItem(162);
            }
            changeModeWithoutConfigureData(baseModule.get().getModuleIndex(), false);
        }
    }

    private void configIDCardBack() {
        ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).callBackEvent();
    }

    private void configLiveVideoQuality(String str) {
        ComponentLiveVideoQuality componentLiveVideoQuality = DataRepository.dataItemLive().getComponentLiveVideoQuality();
        int currentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        CameraStatUtils.trackVideoQuality(CameraSettings.KEY_MI_LIVE_QUALITY, CameraSettings.isFrontCamera(), str);
        componentLiveVideoQuality.setComponentValue(160, str);
        changeModeWithoutConfigureData(currentMode, false);
    }

    private void configMacroMode() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = false;
            boolean z2 = 169 == moduleIndex;
            boolean z3 = !CameraSettings.isMacroModeEnabled(moduleIndex);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (z3 && z2) {
                DataRepository.dataItemGlobal().setCurrentMode(162);
            }
            switchOffElementsSilent(216);
            CameraSettings.setAutoZoomEnabled(moduleIndex, false);
            CameraSettings.setSuperEISEnabled(moduleIndex, false);
            if (z3 && moduleIndex == 162) {
                singeSwitchVideoBeauty(false);
            }
            CameraSettings.setVideoQuality8K(moduleIndex, false);
            DataRepository.dataItemRunning().getComponentRunningAiAudio().setComponentValue(moduleIndex, "normal");
            HybridZoomingSystem.clearZoomRatioHistory();
            setTipsState("macro", true);
            ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
            if (z3) {
                componentRunningMacroMode.setSwitchOn(moduleIndex);
                if (baseModule.get().getCameraCapabilities().isMacroHdrMutex()) {
                    closeMutexElement("m", 194);
                    DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                }
            } else {
                if (baseModule.get().getCameraCapabilities().isMacroHdrMutex()) {
                    this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                    restoreAllMutexElement("m");
                }
                componentRunningMacroMode.setSwitchOff(moduleIndex);
            }
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.MacroAttr.PARAM_SWITCH_MACRO, z3 ? "on" : "off");
            MistatsWrapper.mistatEvent(MistatsConstants.MacroAttr.FUCNAME_MACRO_MODE, hashMap);
            changeModeWithoutConfigureData(moduleIndex, false);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (z3) {
                if (bottomPopupTips != null) {
                    bottomPopupTips.directHideTipImage();
                    bottomPopupTips.directShowOrHideLeftTipImage(false);
                }
                if (dualController != null) {
                    dualController.hideZoomButton();
                    return;
                }
                return;
            }
            if (miBeautyProtocol != null) {
                z = miBeautyProtocol.isBeautyPanelShow();
            }
            if (bottomPopupTips != null && !z) {
                bottomPopupTips.reInitTipImage();
            }
            if (dualController != null && !z) {
                if (!CameraSettings.isUltraWideConfigOpen(moduleIndex) && (moduleIndex != 172 || !DataRepository.dataItemFeature().Qj())) {
                    dualController.showZoomButton();
                }
                if (topAlert != null) {
                    topAlert.clearAlertStatus();
                }
            }
        }
    }

    private void configMoon(boolean z) {
        getBaseModule().ifPresent(new C0082e(z));
    }

    private void configMoonBacklight() {
        getBaseModule().ifPresent(o.INSTANCE);
    }

    private void configMoonNight() {
        getBaseModule().ifPresent(new C0081d(this));
    }

    private void configReferenceLineSwitch(int i) {
        ModeProtocol.MainContentProtocol mainContentProtocol;
        boolean z = false;
        if (!DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_REFERENCE_LINE, false)) {
            z = true;
        }
        DataRepository.dataItemGlobal().putBoolean(CameraSettings.KEY_REFERENCE_LINE, z).apply();
        if (1 == i) {
            trackReferenceLineChanged(z);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent() || (mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)) == null) {
            return;
        }
        if (DataRepository.dataItemGlobal().isNormalIntent() || !baseModule.get().getCameraCapabilities().isSupportLightTripartite()) {
            mainContentProtocol.updateReferenceGradienterSwitched();
        } else {
            mainContentProtocol.hideReferenceGradienter();
        }
    }

    private void configSilhouette() {
        getBaseModule().ifPresent(v.INSTANCE);
    }

    private void configSlowQuality(String str) {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        ComponentConfigSlowMotionQuality componentConfigSlowMotionQuality = dataItemConfig.getComponentConfigSlowMotionQuality();
        int currentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        CameraStatUtils.trackSlowMotionQuality(dataItemConfig.getComponentConfigSlowMotion().getComponentValue(currentMode), str);
        componentConfigSlowMotionQuality.setComponentValue(currentMode, str);
        changeModeWithoutConfigureData(currentMode, false);
    }

    private void configSpeechShutter() {
        if (DataRepository.dataItemRunning().supportSpeechShutter()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                boolean z = !CameraSettings.isSpeechShutterOpen();
                CameraSettings.setSpeechShutterStatus(z);
                CameraStatUtils.trackSpeechShutterStatus(z);
                if (z) {
                    setTipsState(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC, true);
                }
                int moduleIndex = baseModule.get().getModuleIndex();
                if (moduleIndex == 254 || moduleIndex == 209 || moduleIndex == 210) {
                    z = false;
                }
                ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (actionProcessing != null) {
                    actionProcessing.processingSpeechShutter(z);
                }
                ModeProtocol.SpeechShutterDetect speechShutterDetect = (ModeProtocol.SpeechShutterDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(255);
                if (speechShutterDetect != null) {
                    speechShutterDetect.processingSpeechShutter(z);
                }
            }
        }
    }

    private void configSuperEIS() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule != null && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            setTipsState(FragmentTopAlert.TIP_SUPER_EIS, true);
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isSuperEISEnabled = CameraSettings.isSuperEISEnabled(moduleIndex);
            HybridZoomingSystem.clearZoomRatioHistory();
            if (isSuperEISEnabled) {
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                topAlert.updateConfigItem(218);
            } else {
                CameraSettings.setSuperEISEnabled(moduleIndex, true);
                topAlert.updateConfigItem(218);
                switchOffElementsSilent(216);
                closeVideoFast();
                singeSwitchVideoBeauty(false);
                resetVideoBokehLevel();
                resetVideoFilter();
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                    componentRunningMacroMode.setSwitchOff(moduleIndex);
                }
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, false);
                CameraSettings.setVideoQuality8K(moduleIndex, false);
            }
            trackSuperEISChanged(!isSuperEISEnabled);
            if (moduleIndex == 204) {
                DataRepository.dataItemGlobal().setCurrentMode(162);
            }
            changeModeWithoutConfigureData(162, false);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (isSuperEISEnabled) {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_SUPER_EIS, 8, R.string.super_eis_disabled_hint);
            }
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
        }
    }

    private void configSuperEISPro() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule != null && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            setTipsState("super_eis_pro", true);
            int moduleIndex = baseModule.get().getModuleIndex();
            String componentValue = DataRepository.dataItemRunning().getComponentRunningEisPro().getComponentValue(moduleIndex);
            HybridZoomingSystem.clearZoomRatioHistory();
            topAlert.updateConfigItem(165);
            if (!componentValue.equals("off")) {
                switchOffElementsSilent(216);
                closeVideoFast();
                singeSwitchVideoBeauty(false);
                resetVideoBokehLevel();
                resetVideoFilter();
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                    componentRunningMacroMode.setSwitchOff(moduleIndex);
                }
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, false);
                CameraSettings.setVideoQuality8K(moduleIndex, false);
            }
            if (moduleIndex == 204) {
                DataRepository.dataItemGlobal().setCurrentMode(162);
            }
            changeModeWithoutConfigureData(162, true);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
        }
    }

    private void configSwitchHandGesture() {
        BaseModule baseModule;
        if (DataRepository.dataItemRunning().supportHandGesture()) {
            Optional<BaseModule> baseModule2 = getBaseModule();
            if (baseModule2.isPresent() && (baseModule = baseModule2.get()) != null) {
                boolean z = !CameraSettings.isHandGestureOpen();
                if (z) {
                    setTipsState(FragmentTopAlert.TIP_HAND_GESTURE_DESC, true);
                }
                CameraSettings.setHandGestureStatus(z);
                ((Camera2Module) baseModule).onHanGestureSwitched(z);
            }
        }
    }

    private void configSwitchUltraWide() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = !CameraSettings.isUltraWideConfigOpen(moduleIndex);
            if (CameraSettings.isUltraPixelOn()) {
                CameraSettings.switchOffUltraPixel();
            }
            HybridZoomingSystem.clearZoomRatioHistory();
            CameraSettings.setUltraWideConfig(moduleIndex, z);
            ModeProtocol.LensProtocol lensProtocol = (ModeProtocol.LensProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(200);
            if (lensProtocol != null) {
                lensProtocol.onSwitchLens(true, false);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                if (z) {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_open_tip, 7, 500);
                } else {
                    bottomPopupTips.showTips(13, R.string.ultra_wide_close_tip, 6);
                }
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    private void configSwitchUltraWideBokeh() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                boolean isSwitchOn = DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
                MistatsWrapper.moduleUIClickEvent("M_portrait_", MistatsConstants.PortraitAttr.PARAM_ULTRA_WIDE_BOKEH, Boolean.valueOf(!isSwitchOn));
                if (isSwitchOn) {
                    DataRepository.dataItemRunning().switchOff("pref_ultra_wide_bokeh_enabled");
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_WIDE_BOKEH, 0, R.string.ultra_wide_bokeh_close_tip);
                } else {
                    setTipsState(FragmentTopAlert.TIP_ULTRA_WIDE_BOKEH, true);
                    DataRepository.dataItemRunning().switchOn("pref_ultra_wide_bokeh_enabled");
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_WIDE_BOKEH, 8, R.string.ultra_wide_bokeh_open_tip);
                }
                changeModeWithoutConfigureData(baseModule.get().getModuleIndex(), false);
            }
        }
    }

    private void configTilt(String str) {
        Optional<BaseModule> baseModule = getBaseModule();
        BaseModule baseModule2 = baseModule.get();
        if (baseModule.isPresent() && (baseModule2 instanceof Camera2Module)) {
            DataRepository.dataItemRunning().getComponentRunningTiltValue().setComponentValue(160, str);
            ((Camera2Module) baseModule.get()).showOrHideChip(false);
            ((Camera2Module) baseModule.get()).onTiltShiftSwitched(true);
            EffectController.getInstance().setDrawTilt(true);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    private void configTimerBurst(int i) {
        ModeProtocol.TopAlert topAlert;
        boolean isTimerBurstEnable = CameraSettings.isTimerBurstEnable();
        if (i != 3) {
            boolean z = !isTimerBurstEnable;
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                CameraSettings.setTimerBurstEnable(z);
                BaseModule baseModule2 = baseModule.get();
                MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.CaptureSence.PARAM_TIMER_BURST, Boolean.valueOf(z), null);
                if (z && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                    configTiltSwitch(3);
                    configLiveShotSwitch(3);
                    if (CameraSettings.isUltraPixelOn()) {
                        CameraSettings.switchOffUltraPixel();
                        topAlert.updateConfigItem(209);
                        changeModeWithoutConfigureData(baseModule2.getModuleIndex(), false);
                    }
                    topAlert.alertTimerBurstHint(0, R.string.timer_burst);
                }
            }
        } else if (isTimerBurstEnable) {
            DataRepository.dataItemLive().getTimerBurstController().muteTimerConfig();
            ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert2 != null) {
                topAlert2.alertTimerBurstHint(8, R.string.timer_burst);
            }
        }
    }

    private void configVV() {
        boolean z;
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate.getActiveFragment(R.id.bottom_action) != 65530) {
            int moduleIndex = getBaseModule().get().getModuleIndex();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null && DataRepository.dataItemFeature().Nj()) {
                topAlert.clearVideoUltraClear();
            }
            if (moduleIndex == 169) {
                closeVideoFast();
                z = true;
            } else {
                z = false;
            }
            if (CameraSettings.isFaceBeautyOn(moduleIndex, null)) {
                singeSwitchVideoBeauty(false);
                z = true;
            }
            if (CameraSettings.isSuperEISEnabled(moduleIndex)) {
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                z = true;
            }
            if (CameraSettings.isSubtitleEnabled(moduleIndex)) {
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                z = true;
            }
            if (CameraSettings.isAutoZoomEnabled(moduleIndex)) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                z = true;
            }
            if (CameraSettings.isMacroModeEnabled(moduleIndex)) {
                DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
                z = true;
            }
            if (CameraSettings.isVideoQuality8KOpen(162)) {
                CameraSettings.setVideoQuality8K(162, false);
                z = true;
            }
            if (z) {
                CameraSettings.writeZoom(1.0f);
                HybridZoomingSystem.setZoomRatioHistory(moduleIndex, String.valueOf(1.0f));
            }
            DataRepository.dataItemConfig().getComponentConfigVideoQuality().setForceValueOverlay("6");
            CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, false);
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).updateCinematicAspectRatioSwitched(false);
            baseDelegate.delegateEvent(15);
        } else {
            DataRepository.dataItemConfig().getComponentConfigVideoQuality().setForceValueOverlay(null);
            ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert2 != null) {
                topAlert2.alertTopHint(8, R.string.vv_use_hint_text_title);
            }
            baseDelegate.delegateEvent(15);
            reCheckVideoUltraClearTip();
            z = false;
        }
        ModeProtocol.TopAlert topAlert3 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert3 != null) {
            topAlert3.updateConfigItem(216);
            topAlert3.updateConfigItem(256);
            topAlert3.refreshExtraMenu();
            if (z) {
                changeMode(162);
            }
        }
    }

    private void configVVBack() {
        ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
        if (liveVVProcess != null) {
            liveVVProcess.showExitConfirm();
        }
    }

    private void configVideo8K() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
            int currentMode = dataItemGlobal.getCurrentMode();
            if (CameraSettings.isVideoQuality8KOpen(currentMode)) {
                CameraStatUtils.track8KVideo(currentMode, "off");
                CameraSettings.setVideoQuality8K(currentMode, false);
                topAlert.updateConfigItem(256);
                topAlert.alertVideoUltraClear(8, R.string.pref_camera_video_8k_tips);
            } else {
                CameraStatUtils.track8KVideo(currentMode, String.valueOf(30));
                if (closeVideoFast()) {
                    currentMode = dataItemGlobal.getCurrentMode();
                }
                CameraSettings.setVideoQuality8K(currentMode, true);
                switchOffElementsSilent(216);
                if (!DataRepository.dataItemFeature().gl()) {
                    CameraSettings.setCinematicAspectRatioEnabled(currentMode, false);
                }
                if (!ComponentConfigVideoQuality.is8KCamcorderSupported(baseModule.get().getActualCameraId())) {
                    HybridZoomingSystem.clearZoomRatioHistory();
                    if (currentMode == 180) {
                        DataRepository.dataItemConfig().getManuallyDualLens().setComponentValue(currentMode, ComponentManuallyDualLens.LENS_WIDE);
                    }
                }
                singeSwitchVideoBeauty(false);
                resetVideoBokehLevel();
                resetVideoFilter();
                CameraSettings.setAutoZoomEnabled(currentMode, false);
                CameraSettings.setSuperEISEnabled(currentMode, false);
                DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(currentMode);
                CameraSettings.setProVideoLog(false);
                CameraSettings.setSubtitleEnabled(currentMode, false);
                topAlert.updateConfigItem(256);
                topAlert.alertVideoUltraClear(0, R.string.pref_camera_video_8k_tips);
            }
            changeMode(currentMode);
        }
    }

    private void configVideoBokehSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
            boolean isSwitchOn = dataItemConfig.isSwitchOn("pref_video_bokeh_key");
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("configVideoBokehSwitch: switchOn = ");
            sb.append(!isSwitchOn);
            Log.d(str, sb.toString());
            HashMap hashMap = new HashMap();
            if (!isSwitchOn) {
                dataItemConfig.switchOn("pref_video_bokeh_key");
                hashMap.put(MistatsConstants.AlgoAttr.PARAM_BOKEN, "on");
            } else {
                dataItemConfig.switchOff("pref_video_bokeh_key");
                hashMap.put(MistatsConstants.AlgoAttr.PARAM_BOKEN, "off");
            }
            MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
            topAlert.updateConfigItem(243);
            changeModeWithoutConfigureData(baseModule.get().getModuleIndex(), true);
            if (!isSwitchOn) {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_VIDEO_BOKEH, 0, R.string.pref_camera_video_bokeh_on);
            } else {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_VIDEO_BOKEH, 8, R.string.pref_camera_video_bokeh_off);
            }
        }
    }

    private boolean configVideoHdrIfNeed() {
        int moduleIndex;
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent() || (moduleIndex = baseModule.get().getModuleIndex()) != 162 || !CameraSettings.isFrontCamera()) {
            return false;
        }
        changeMode(moduleIndex);
        return true;
    }

    private void configVideoQuality(String str) {
        switchOffElementsSilent(216);
        ComponentConfigVideoQuality componentConfigVideoQuality = DataRepository.dataItemConfig().getComponentConfigVideoQuality();
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        int currentMode = dataItemGlobal.getCurrentMode();
        boolean supportVideoSATForVideoQuality = DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(currentMode);
        if (ComponentConfigVideoQuality.QUALITY_8K.equals(str) || ComponentConfigVideoQuality.QUALITY_8K_24FPS.equals(str)) {
            if (closeVideoFast()) {
                currentMode = dataItemGlobal.getCurrentMode();
            }
            int i = ComponentConfigVideoQuality.QUALITY_8K_24FPS.equals(str) ? 24 : 30;
            DataRepository.dataItemRunning().getComponentRunning8KVideoQuality().setFps(currentMode, i);
            CameraStatUtils.track8KVideo(currentMode, String.valueOf(i));
            CameraSettings.setVideoQuality8K(currentMode, true);
        } else {
            CameraSettings.setVideoQuality8K(currentMode == 169 ? 162 : currentMode, false);
            CameraStatUtils.track8KVideo(currentMode, "off");
            componentConfigVideoQuality.setComponentValue(currentMode, str);
        }
        CameraStatUtils.trackVideoQuality("pref_video_quality_key", CameraSettings.isFrontCamera(), str);
        if (supportVideoSATForVideoQuality && !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(currentMode)) {
            HybridZoomingSystem.clearZoomRatioHistory();
        }
        changeModeWithoutConfigureData(currentMode, false);
    }

    private void configVideoSubtitle() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isSubtitleEnabled = CameraSettings.isSubtitleEnabled(moduleIndex);
            CameraStatUtils.trackSubtitle(!isSubtitleEnabled);
            if (isSubtitleEnabled) {
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                topAlert.updateConfigItem(220);
            } else {
                CameraSettings.setSubtitleEnabled(162, true);
                topAlert.updateConfigItem(220);
                CameraSettings.setVideoQuality8K(162, false);
                switchOffElementsSilent(216);
                closeVideoFast();
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.writeZoom(1.0f);
                HybridZoomingSystem.setZoomRatioHistory(moduleIndex, String.valueOf(1.0f));
            }
            if (moduleIndex == 204) {
                DataRepository.dataItemGlobal().setCurrentMode(162);
            }
            changeModeWithoutConfigureData(162, false);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (CameraSettings.isSubtitleEnabled(162)) {
                ModeProtocol.SubtitleRecording subtitleRecording = (ModeProtocol.SubtitleRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(231);
                subtitleRecording.initPermission();
                subtitleRecording.checkNetWorkStatus();
            }
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
        }
    }

    private void conflictWithFlashAndHdr() {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        dataItemRunning.switchOff("pref_camera_hand_night_key");
        dataItemRunning.switchOff("pref_camera_groupshot_mode_key");
        dataItemRunning.switchOff("pref_camera_super_resolution_key");
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips == null) {
            Log.d(TAG, "conflictWithFlashAndHdr return, BottomPopupTips is null");
        } else if (!CameraSettings.isMacroModeEnabled(ModuleManager.getActiveModuleIndex()) || bottomPopupTips.getCurrentBottomTipType() != 18) {
            bottomPopupTips.directlyHideTips();
        }
    }

    public static ConfigChangeImpl create(ActivityBase activityBase) {
        return new ConfigChangeImpl(activityBase);
    }

    static /* synthetic */ void d(boolean z, BaseModule baseModule) {
        if ((baseModule instanceof Camera2Module) && z) {
            ((Camera2Module) baseModule).closeMoonMode(0, 8);
        }
    }

    static /* synthetic */ void g(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            ((Camera2Module) baseModule).updateBacklight();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Optional<BaseModule> getBaseModule() {
        ActivityBase activityBase = this.mActivity;
        return activityBase == null ? Optional.empty() : Optional.ofNullable((BaseModule) activityBase.getCurrentModule());
    }

    private boolean getState(int i, String str) {
        if (i == 2) {
            return DataRepository.dataItemRunning().isSwitchOn(str);
        }
        if (i != 4) {
            return DataRepository.dataItemRunning().triggerSwitchAndGet(str);
        }
        DataRepository.dataItemRunning().switchOff(str);
        return false;
    }

    private int getUpDistance() {
        int distanceForWM;
        ModeProtocol.WatermarkProtocol watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253);
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (watermarkProtocol != null) {
            if (!watermarkProtocol.isWatermarkPanelShow()) {
                return 0;
            }
            distanceForWM = watermarkProtocol.getDistanceForWM();
        } else if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
            return 0;
        } else {
            distanceForWM = miBeautyProtocol.getDistanceForWM();
        }
        return distanceForWM;
    }

    private void hideTipMessage(@StringRes int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (i <= 0 || bottomPopupTips.containTips(i)) {
            bottomPopupTips.directlyHideTips();
        }
    }

    static /* synthetic */ void i(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            ((Camera2Module) baseModule).updateNearRangeMode(false, true);
        }
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.CaptureAttr.PARAM_NEAR_RANGE_MODE, MistatsConstants.CaptureAttr.VALUE_CLOSE_NEAR_RANGE_MODE);
        MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
    }

    private boolean is4KQuality(int i, int i2) {
        return i >= 3840 && i2 >= 2160;
    }

    private boolean is8KQuality(int i, int i2) {
        return i >= 7680 && i2 >= 4320;
    }

    private boolean isAlive() {
        return this.mActivity != null;
    }

    private boolean isBeautyPanelShow() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            return miBeautyProtocol.isBeautyPanelShow();
        }
        return false;
    }

    private boolean isChangeManuallyParameters() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent()) {
            return false;
        }
        int moduleIndex = baseModule.get().getModuleIndex();
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        return dataItemConfig.getmComponentManuallyWB().isModified(moduleIndex) || dataItemConfig.getmComponentManuallyET().isModified(moduleIndex) || dataItemConfig.getmComponentManuallyISO().isModified(moduleIndex) || dataItemConfig.getManuallyFocus().isModified(moduleIndex) || dataItemConfig.getComponentConfigMeter().isModified(moduleIndex) || dataItemConfig.getComponentManuallyEV().isModified(moduleIndex);
    }

    private boolean isVideoRecoding(BaseModule baseModule) {
        if (baseModule == null || !(baseModule instanceof VideoBase)) {
            return false;
        }
        return baseModule.isRecording();
    }

    static /* synthetic */ void k(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            ((Camera2Module) baseModule).updateSilhouette();
        }
    }

    private void mutexBeautyBusiness(int i) {
        DataRepository.dataItemConfig().getComponentConfigBeauty().setClosed(false, i);
        switchOffElementsSilent(216);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (CameraSettings.isAutoZoomEnabled(i)) {
            HybridZoomingSystem.clearZoomRatioHistory();
            CameraSettings.setAutoZoomEnabled(i, false);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            bottomPopupTips.updateLeftTipImage();
            bottomPopupTips.updateTipImage();
            if (topAlert != null) {
                topAlert.hideSwitchTip();
            }
        }
        if (CameraSettings.isSuperEISEnabled(i)) {
            HybridZoomingSystem.clearZoomRatioHistory();
            CameraSettings.setSuperEISEnabled(i, false);
            ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            bottomPopupTips2.updateLeftTipImage();
            bottomPopupTips2.updateTipImage();
            if (topAlert != null) {
                topAlert.hideSwitchTip();
            }
        }
        ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
        if (componentRunningMacroMode.isSwitchOn(i)) {
            HybridZoomingSystem.clearZoomRatioHistory();
            componentRunningMacroMode.setSwitchOff(i);
        }
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        if (dataItemConfig.isSwitchOn("pref_video_bokeh_key")) {
            dataItemConfig.switchOff("pref_video_bokeh_key");
        }
        CameraSettings.setVideoQuality8K(i, false);
        if (topAlert != null) {
            topAlert.updateConfigItem(256);
        }
    }

    private void persistFilter(int i) {
        String str = TAG;
        Log.d(str, "persistFilter: filterId = " + i);
        CameraSettings.setShaderEffect(i);
    }

    public static void preload() {
        Log.i(TAG, "preload");
    }

    private void reConfigTipOfHdr(int i) {
        ModeProtocol.TopAlert topAlert;
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            String componentValue = componentHdr.getComponentValue(i);
            if ("on".equals(componentValue) || "normal".equals(componentValue)) {
                topAlert.alertHDR(0, false, false);
            } else if ("live".equals(componentValue)) {
                topAlert.alertHDR(0, true, false);
            } else if (topAlert.isHDRShowing()) {
                topAlert.alertHDR(8, false, false);
            }
        }
    }

    private void resetVideoBokehLevel() {
        CameraSettings.setVideoBokehRatio(0.0f);
    }

    private void resetVideoFilter() {
        CameraSettings.setShaderEffect(0);
    }

    private void setTipsState(String str, boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.setTipsState(str, z);
        }
    }

    private void showDualController(boolean z) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            boolean isBackCamera = CameraSettings.isBackCamera();
            if (z || !isBackCamera) {
                dualController.hideZoomButton();
            } else {
                dualController.showZoomButton();
            }
        }
    }

    private void showOrHideTipImage(boolean z) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        if (!z && CameraSettings.isFrontCamera() && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
            bottomPopupTips.updateTipImage();
        }
    }

    private void singeSwitchVideoBeauty(boolean z) {
        ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        int i = 0;
        if (componentRunningShine.supportBeautyLevel()) {
            if (z) {
                i = 3;
            }
            CameraSettings.setFaceBeautyLevel(i);
        } else if (componentRunningShine.supportSmoothLevel()) {
            CameraSettings.setFaceBeautySmoothLevel(z ? 40 : 0);
            if (!z) {
                DataRepository.dataItemRunning().getComponentRunningShine().setVideoShineForceOn(DataRepository.dataItemGlobal().getCurrentMode(), false);
                if (DataRepository.dataItemRunning().getComponentRunningShine().supportVideoFilter()) {
                    CameraSettings.setShaderEffect(0);
                    CameraSettings.setVideoBokehRatio(0.0f);
                }
                ShineHelper.onBeautyChanged();
                ShineHelper.onVideoBokehRatioChanged();
                ShineHelper.onVideoFilterChanged();
            }
        }
    }

    private void trackFocusPeakChanged(boolean z) {
        MistatsWrapper.moduleUIClickEvent("M_manual_", MistatsConstants.Manual.MANUAL_FOCUS_PEAK, String.valueOf(z));
    }

    private void trackGenderAgeChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_GENDER_AGE, Boolean.valueOf(z), null);
    }

    private void trackGotoSettings() {
        BaseModule baseModule = (BaseModule) this.mActivity.getCurrentModule();
        if (baseModule != null) {
            CameraStatUtils.trackGotoSettings(baseModule.getModuleIndex());
        }
    }

    private void trackGradienterChanged(boolean z) {
        MistatsWrapper.moduleUIClickEvent(DataRepository.dataItemGlobal().getCurrentMode(), MistatsConstants.Manual.GRADIENT, Boolean.valueOf(z));
    }

    private void trackHHTChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_HHT, Boolean.valueOf(z), null);
    }

    private void trackMagicMirrorChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_MAGIC_MIRROR, Boolean.valueOf(z), null);
    }

    private void trackReferenceLineChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent("attr_reference_line", Boolean.valueOf(z), null);
    }

    private void trackSuperEISChanged(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, MistatsWrapper.getStatsModuleKey(DataRepository.dataItemGlobal().getCurrentMode()));
        hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? "front" : "back");
        hashMap.put(MistatsConstants.VideoAttr.PARAM_SUPER_EIS, String.valueOf(z));
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    private void trackSuperResolutionChanged(boolean z) {
        MistatsWrapper.moduleUIClickEvent("M_manual_", MistatsConstants.Manual.SUPER_RESOLUTION, String.valueOf(z));
    }

    private void trackUltraPixelPortraitChanged(boolean z) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.PortraitAttr.ULTRAPIXEL_PORTRAIT, String.valueOf(z), null);
    }

    private void updateAiScene(boolean z) {
        DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigAi componentConfigAi = DataRepository.dataItemConfig().getComponentConfigAi();
        if (!componentConfigAi.isEmpty() && componentConfigAi.isClosed() != z) {
            componentConfigAi.setClosed(z);
            getBaseModule().ifPresent(new k(z));
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(201);
        }
    }

    private void updateAutoZoom(boolean z) {
    }

    private void updateComponentBeauty(boolean z) {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol;
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigBeauty componentConfigBeauty = DataRepository.dataItemConfig().getComponentConfigBeauty();
        if (!componentConfigBeauty.isEmpty() && componentConfigBeauty.isClosed(currentMode) != z) {
            componentConfigBeauty.setClosed(z, currentMode);
            if (z && (miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) != null && miBeautyProtocol.isBeautyPanelShow()) {
                miBeautyProtocol.dismiss(2);
            }
            ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
            if (onShineChangedProtocol != null) {
                onShineChangedProtocol.onShineChanged(true, 239);
            }
        }
    }

    private void updateComponentFilter(boolean z) {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol;
        String str = TAG;
        Log.d(str, "updateComponentFilter: close = " + z);
        ComponentConfigFilter componentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentConfigFilter.isEmpty() && componentConfigFilter.isClosed(currentMode) != z) {
            componentConfigFilter.setClosed(z, currentMode);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(212);
            if (z && (miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) != null && miBeautyProtocol.isBeautyPanelShow()) {
                miBeautyProtocol.dismiss(2);
            }
        }
    }

    private void updateComponentFlash(String str, boolean z) {
        ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentFlash.isEmpty() && componentFlash.isClosed() != z) {
            if (!z || !componentFlash.getComponentValue(currentMode).equals("2") || !SupportedConfigFactory.CLOSE_BY_BURST_SHOOT.equals(str)) {
                componentFlash.setClosed(z);
                ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(193);
            }
        }
    }

    private void updateComponentHdr(boolean z) {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        DataRepository.dataItemGlobal().getCurrentMode();
        if (!componentHdr.isEmpty() && componentHdr.isClosed() != z) {
            componentHdr.setClosed(z);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(194);
        }
    }

    private void updateComponentShine(boolean z) {
        ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        if (!componentRunningShine.isEmpty() && componentRunningShine.isClosed() != z) {
            componentRunningShine.setClosed(z);
        }
    }

    private void updateEyeLight(boolean z) {
        ComponentRunningEyeLight componentRunningEyeLight = DataRepository.dataItemRunning().getComponentRunningEyeLight();
        if (componentRunningEyeLight.isClosed() != z) {
            componentRunningEyeLight.setClosed(z);
        }
    }

    private void updateFlashModeAndRefreshUI(BaseModule baseModule, String str) {
        int moduleIndex = baseModule.getModuleIndex();
        String str2 = TAG;
        Log.d(str2, "updateFlashModeAndRefreshUI flashMode = " + str);
        if (!TextUtils.isEmpty(str)) {
            CameraSettings.setFlashMode(moduleIndex, str);
        }
        if ("0".equals(str)) {
            if (CameraSettings.isFrontCamera()) {
                ToastUtils.showToast(this.mActivity, (int) R.string.close_front_flash_toast);
            } else {
                ToastUtils.showToast(this.mActivity, (int) R.string.close_back_flash_toast);
            }
        }
        if (!baseModule.isDoingAction() || "0".equals(str)) {
            baseModule.updatePreferenceInWorkThread(10);
        } else {
            baseModule.updatePreferenceTrampoline(10);
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.updateConfigItem(193);
        }
    }

    private void updateLiveShot(boolean z) {
        if (DataRepository.dataItemFeature().Lj()) {
            int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
            if (currentMode == 163 || currentMode == 165) {
                ComponentConfigLiveShot componentConfigLiveShot = DataRepository.dataItemConfig().getComponentConfigLiveShot();
                if (componentConfigLiveShot.isClosed() != z) {
                    componentConfigLiveShot.setClosed(z);
                    ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(206);
                }
            }
        }
    }

    private void updateRaw(boolean z) {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigRaw componentConfigRaw = DataRepository.dataItemConfig().getComponentConfigRaw();
        if (!componentConfigRaw.isEmpty() && componentConfigRaw.isClosed(currentMode) != z) {
            componentConfigRaw.setClosed(z, currentMode);
        }
    }

    private void updateTipMessage(int i, @StringRes int i2, int i3) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).showTips(i, i2, i3);
    }

    private void updateUltraPixel(boolean z) {
        ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
        if (!componentUltraPixel.isEmpty() && componentUltraPixel.isClosed() != z) {
            componentUltraPixel.setClosed(z);
        }
    }

    private void watermarkToast() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertSwitchTip("ai_watermark", 0, R.string.ai_watermark_title);
        }
    }

    public /* synthetic */ void a(BaseModule baseModule) {
        if (172 != baseModule.getModuleIndex()) {
            conflictWithFlashAndHdr();
        }
        baseModule.updatePreferenceInWorkThread(11, 58);
    }

    public /* synthetic */ void a(boolean z, BaseModule baseModule) {
        if ("auto" != DataRepository.dataItemConfig().getComponentHdr().getComponentValue(DataRepository.dataItemGlobal().getCurrentMode()) && z && (getBaseModule().get() instanceof Camera2Module)) {
            ((Camera2Module) getBaseModule().get()).resetMagneticInfo();
        }
        if (z) {
            baseModule.updatePreferenceInWorkThread(11);
        }
        baseModule.updatePreferenceInWorkThread(10);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void closeMutexElement(String str, int... iArr) {
        int[] iArr2 = new int[iArr.length];
        this.mRecordingClosedElements = iArr;
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            if (i2 == 193) {
                updateComponentFlash(str, true);
                iArr2[i] = 10;
            } else if (i2 == 194) {
                updateComponentHdr(true);
                iArr2[i] = 11;
            } else if (i2 == 196) {
                updateComponentFilter(true);
                iArr2[i] = 2;
            } else if (i2 == 201) {
                updateAiScene(true);
                iArr2[i] = 36;
            } else if (i2 == 206) {
                updateLiveShot(true);
                iArr2[i] = 49;
            } else if (i2 == 209) {
                updateUltraPixel(true);
                iArr2[i] = 50;
            } else if (i2 == 212) {
                updateComponentShine(true);
                iArr2[i] = 2;
            } else if (i2 == 227) {
                iArr2[i] = 74;
            } else if (i2 == 237) {
                updateRaw(true);
                iArr2[i] = 44;
            } else if (i2 == 239) {
                updateComponentBeauty(true);
                iArr2[i] = 13;
            } else if (i2 == 253) {
                updateAutoZoom(true);
                iArr2[i] = 51;
            } else if (i2 == 254) {
                updateEyeLight(true);
                iArr2[i] = 45;
            } else {
                throw new RuntimeException("unknown mutex element");
            }
        }
        getBaseModule().ifPresent(new C0083f(iArr2));
    }

    public void configAi108() {
        if (getBaseModule().isPresent()) {
            boolean ai108Running = DataRepository.dataItemRunning().getAi108Running();
            if (ai108Running) {
                DataRepository.dataItemConfig().getManuallyDualLens().reset(175);
            }
            DataRepository.dataItemRunning().setAi108Running(!ai108Running);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.setTipsState(FragmentTopAlert.TIP_ULTRA_PIXEL, true);
                topAlert.updateConfigItem(171);
            }
            changeModeWithoutConfigureData(175, true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configBackSoftLightSwitch(String str) {
        getBaseModule().ifPresent(new r(this));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configBeautySwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean z = moduleIndex == 162 || moduleIndex == 169;
            if (moduleIndex == 163 || moduleIndex == 165 || moduleIndex == 171 || moduleIndex == 161 || z) {
                ComponentConfigBeauty componentConfigBeauty = DataRepository.dataItemConfig().getComponentConfigBeauty();
                String nextValue = componentConfigBeauty.getNextValue(moduleIndex);
                boolean z2 = (!BeautyConstant.LEVEL_CLOSE.equals(componentConfigBeauty.getComponentValue(moduleIndex))) ^ (!BeautyConstant.LEVEL_CLOSE.equals(nextValue));
                componentConfigBeauty.setComponentValue(moduleIndex, nextValue);
                CameraStatUtils.trackBeautySwitchChanged(nextValue);
                if (z2 && z) {
                    if (moduleIndex != 162) {
                        DataRepository.dataItemRunning().switchOff("pref_video_speed_fast_key");
                        CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                        ((DataItemGlobal) DataRepository.provider().dataGlobal()).setCurrentMode(162);
                        CameraStatUtils.trackVideoModeChanged("normal");
                    }
                    changeModeWithoutConfigureData(162, true);
                } else if (!z2 || moduleIndex != 161) {
                    baseModule.get().updatePreferenceInWorkThread(13);
                } else {
                    changeModeWithoutConfigureData(161, true);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configBokeh(String str) {
        if (str.equals("on")) {
            updateTipMessage(4, R.string.bokeh_use_hint, 2);
        } else {
            hideTipMessage(R.string.bokeh_use_hint);
        }
        getBaseModule().ifPresent(C0085h.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configCinematicAspectRatio(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    int moduleIndex = baseModule2.getModuleIndex();
                    boolean z = !CameraSettings.isCinematicAspectRatioEnabled(moduleIndex);
                    if (moduleIndex == 165) {
                        moduleIndex = 163;
                    }
                    CameraSettings.setCinematicAspectRatioEnabled(moduleIndex, z);
                    String str = "on";
                    if (moduleIndex == 171 || moduleIndex == 163 || moduleIndex == 173) {
                        if (!z) {
                            str = "off";
                        }
                        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.PARAM_PHOTO_RATIO_MOVIE, str, null);
                        configRatio(true, null);
                        return;
                    }
                    if (!z) {
                        str = "off";
                    }
                    MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.PARAM_VIDEO_RATIO_MOVIE, str, null);
                    switchOffElementsSilent(216);
                    CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                    CameraSettings.setSuperEISEnabled(moduleIndex, false);
                    if (!DataRepository.dataItemFeature().gl()) {
                        CameraSettings.setVideoQuality8K(moduleIndex, false);
                    }
                    changeModeWithoutConfigureData(moduleIndex, false);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configClone(FenShenCam.Mode mode, boolean z) {
        if (z) {
            ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
            if (cloneProcess != null) {
                ModeProtocol.CloneChooser cloneChooser = (ModeProtocol.CloneChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(416);
                if (cloneChooser != null) {
                    cloneChooser.hide();
                }
                cloneProcess.prepare(mode, false);
                changeMode(185);
                return;
            }
            return;
        }
        Config.setCloneMode(null);
        ModeProtocol.CloneProcess cloneProcess2 = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess2 != null) {
            cloneProcess2.quit();
        }
        changeMode(210);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configDualWaterMarkSwitch() {
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        CameraStatUtils.trackDualWaterMarkChanged(!isDualCameraWaterMarkOpen);
        if (isDualCameraWaterMarkOpen) {
            hideTipMessage(R.string.hunt_dual_water_mark);
            CameraSettings.setDualCameraWaterMarkOpen(false);
        } else {
            updateTipMessage(4, R.string.hunt_dual_water_mark, 2);
            CameraSettings.setDualCameraWaterMarkOpen(true);
        }
        getBaseModule().ifPresent(m.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configExposureFeedbackSwitch(int i) {
        boolean isSwitchOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_exposure_feedback");
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            if (1 == i) {
                isSwitchOn = !isSwitchOn;
                int moduleIndex = baseModule.get().getModuleIndex();
                if (1 == i) {
                    MistatsWrapper.moduleUIClickEvent(moduleIndex == 167 ? "M_manual_" : "M_proVideo_", MistatsConstants.Manual.MANUAL_FOCUS_PEAK, isSwitchOn ? "on" : "off");
                }
            }
            if (isSwitchOn) {
                DataRepository.dataItemRunning().switchOn("pref_camera_exposure_feedback");
                DataRepository.dataItemRunning().switchOff("pref_camera_peak_key");
                EffectController.getInstance().setDrawPeaking(false);
            } else {
                DataRepository.dataItemRunning().switchOff("pref_camera_exposure_feedback");
            }
            EffectController.getInstance().setDrawExposure(isSwitchOn);
        }
    }

    public void configFPS960(String str) {
        ComponentConfigSlowMotion componentConfigSlowMotion = DataRepository.dataItemConfig().getComponentConfigSlowMotion();
        if (componentConfigSlowMotion.getItems().size() > 1) {
            componentConfigSlowMotion.setComponentValue(172, str);
            changeModeWithoutConfigureData(172, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configFlash(String str, boolean z) {
        if (!ModuleManager.isVideoNewSlowMotion()) {
            conflictWithFlashAndHdr();
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && z) {
            topAlert.alertHDR(8, false, false);
        }
        getBaseModule().ifPresent(new w(this, z));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configFocusPeakSwitch(int i) {
        boolean isSwitchOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key");
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (1 == i) {
                isSwitchOn = !isSwitchOn;
                MistatsWrapper.moduleUIClickEvent(moduleIndex == 167 ? "M_manual_" : "M_proVideo_", MistatsConstants.Manual.MANUAL_FOCUS_PEAK, isSwitchOn ? "on" : "off");
            }
            if (isSwitchOn) {
                DataRepository.dataItemRunning().switchOn("pref_camera_peak_key");
                DataRepository.dataItemRunning().switchOff("pref_camera_exposure_feedback");
                EffectController.getInstance().setDrawExposure(false);
            } else {
                DataRepository.dataItemRunning().switchOff("pref_camera_peak_key");
            }
            EffectController.getInstance().setDrawPeaking(isSwitchOn);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configGenderAgeSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_show_gender_age_key");
            if (1 == i) {
                trackGenderAgeChanged(state);
            }
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).setShowGenderAndAge(state);
            baseModule.get().updatePreferenceInWorkThread(38);
            if (state) {
                Camera2Proxy cameraDevice = baseModule.get().getCameraDevice();
                if (cameraDevice != null) {
                    String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.face_age_info);
                    cameraDevice.setFaceWaterMarkEnable(true);
                    cameraDevice.setFaceWaterMarkFormat(string);
                    return;
                }
                return;
            }
            Camera2Proxy cameraDevice2 = baseModule.get().getCameraDevice();
            if (cameraDevice2 != null) {
                cameraDevice2.setFaceWaterMarkEnable(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configGradienterSwitch(int i) {
        boolean z;
        boolean z2 = true;
        if (i == 2) {
            z = CameraSettings.isGradienterOn();
        } else if (i != 4) {
            boolean isGradienterOn = CameraSettings.isGradienterOn();
            CameraSettings.setGradienterOn(!isGradienterOn);
            z = !isGradienterOn;
        } else {
            CameraSettings.setGradienterOn(false);
            z = false;
        }
        if (1 == i) {
            trackGradienterChanged(z);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            baseModule.get().onGradienterSwitched(z);
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                if (DataRepository.dataItemGlobal().isNormalIntent() || !baseModule.get().getCameraCapabilities().isSupportLightTripartite()) {
                    mainContentProtocol.updateReferenceGradienterSwitched();
                } else {
                    mainContentProtocol.hideReferenceGradienter();
                }
            }
            BaseModule baseModule2 = baseModule.get();
            if (z) {
                z2 = false;
            }
            baseModule2.showOrHideChip(z2);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
            if (z && CameraSettings.isAutoZoomEnabled(moduleIndex)) {
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.writeZoom(1.0f);
                HybridZoomingSystem.setZoomRatioHistory(moduleIndex, String.valueOf(1.0f));
                changeModeWithoutConfigureData(162, false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configGroupSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_groupshot_mode_key");
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            Camera2Module camera2Module = (Camera2Module) baseModule.get();
            camera2Module.showOrHideChip(!state);
            boolean isBeautyPanelShow = isBeautyPanelShow();
            if (state) {
                if (!isBeautyPanelShow) {
                    updateTipMessage(17, R.string.hint_groupshot, 2);
                }
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_GROUP, 193, 194, 196, 201, 254);
            } else {
                restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_GROUP);
                hideTipMessage(R.string.hint_groupshot);
            }
            camera2Module.onSharedPreferenceChanged();
            baseModule.get().updatePreferenceInWorkThread(42, 34, 30);
            bottomPopupTips.reConfigQrCodeTip();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configHHTSwitch(int i) {
        boolean state = getState(i, "pref_camera_hand_night_key");
        if (1 == i) {
            trackHHTChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            MutexModeManager mutexModePicker = baseModule.get().getMutexModePicker();
            if (state) {
                updateTipMessage(4, R.string.hine_hht, 2);
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_HHT, 193, 194);
                mutexModePicker.setMutexModeMandatory(3);
                return;
            }
            hideTipMessage(R.string.hine_hht);
            mutexModePicker.clearMandatoryFlag();
            baseModule.get().resetMutexModeManually();
            restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_HHT);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configHdr(String str) {
        if (!configVideoHdrIfNeed()) {
            conflictWithFlashAndHdr();
            ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
            int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
            if (!(getBaseModule().get() == null || "auto" == componentHdr.getComponentValue(currentMode) || !(getBaseModule().get() instanceof Camera2Module))) {
                ((Camera2Module) getBaseModule().get()).resetMagneticInfo();
            }
            getBaseModule().ifPresent(C0079b.INSTANCE);
            if ("off" != str && CameraSettings.isUltraPixelRearOn()) {
                configSwitchUltraPixel(3);
            }
            if ("off" != str && CameraSettings.isUltraPixelPortraitFrontOn()) {
                configUltraPixelPortrait(3);
            }
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getCameraCapabilities().isMacroHdrMutex()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if ("off" != str && CameraSettings.isMacroModeEnabled(moduleIndex)) {
                    DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
                    HybridZoomingSystem.clearZoomRatioHistory();
                    changeMode(currentMode);
                }
            }
            reConfigTipOfHdr(currentMode);
        }
    }

    public void configIDCard() {
        ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).prepare(getBaseModule().get().getModuleIndex());
        ((ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179)).changeModeByNewMode(182, 0);
        CameraStatUtils.trackGotoIDCard();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configLiveReview() {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(11);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configLiveShotSwitch(int i) {
        ModeProtocol.TopAlert topAlert;
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 165) && DataRepository.dataItemFeature().Lj() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                        Camera2Module camera2Module = (Camera2Module) baseModule2;
                        if (i == 1) {
                            boolean isLiveShotOn = CameraSettings.isLiveShotOn();
                            CameraSettings.setLiveShotOn(!isLiveShotOn);
                            if (isLiveShotOn) {
                                camera2Module.stopLiveShot(false);
                                topAlert.alertLiveShotHint(8, R.string.camera_liveshot_off_tip);
                            } else {
                                configDocumentMode(3);
                                configTimerBurst(3);
                                if (!CameraSettings.isUltraPixelOn()) {
                                    if (camera2Module.getModuleIndex() == 165) {
                                        configRatio(false, DataRepository.dataItemConfig().getComponentConfigRatio().getDefaultValue(163));
                                    } else {
                                        camera2Module.startLiveShot();
                                        topAlert.alertLiveShotHint(0, R.string.camera_liveshot_on_tip);
                                    }
                                    setTipsState(FragmentTopAlert.TIP_LIVE_SHOT, true);
                                } else {
                                    Log.d(TAG, "Ignore #startLiveShot in ultra pixel photography mode");
                                }
                            }
                        } else if ((i == 3 || i == 4) && CameraSettings.isLiveShotOn()) {
                            CameraSettings.setLiveShotOn(false);
                            camera2Module.stopLiveShot(false);
                        }
                        topAlert.updateConfigItem(206);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configLiveVV(VVItem vVItem, boolean z, boolean z2) {
        ((VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class)).reset();
        String str = TAG;
        Log.d(str, "configLiveVV " + z);
        if (z) {
            ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
            if (liveVVProcess != null) {
                ((ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229)).hide();
                liveVVProcess.prepare(vVItem);
                DataRepository.dataItemLive().setCurrentVVItem(vVItem);
                changeMode(179);
                return;
            }
            return;
        }
        if (z2) {
            configVV();
        } else {
            VVItem currentVVItem = DataRepository.dataItemLive().getCurrentVVItem();
            if (currentVVItem != null) {
                int i = currentVVItem.index;
            }
            ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).quit();
        }
        changeMode(209);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configMagicFocusSwitch() {
        trackMagicMirrorChanged(DataRepository.dataItemRunning().triggerSwitchAndGet("pref_camera_ubifocus_key"));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configMagicMirrorSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_magic_mirror_key");
            if (1 == i) {
                trackMagicMirrorChanged(state);
            }
            ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).setShowMagicMirror(state);
            baseModule.get().updatePreferenceInWorkThread(39);
            if (state) {
                Camera2Proxy cameraDevice = baseModule.get().getCameraDevice();
                if (cameraDevice != null) {
                    String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.face_score_info);
                    cameraDevice.setFaceWaterMarkEnable(true);
                    cameraDevice.setFaceWaterMarkFormat(string);
                    return;
                }
                return;
            }
            Camera2Proxy cameraDevice2 = baseModule.get().getCameraDevice();
            if (cameraDevice2 != null) {
                cameraDevice2.setFaceWaterMarkEnable(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configMeter(String str) {
        reCheckParameterResetTip(false);
        getBaseModule().ifPresent(new Consumer<BaseModule>() {
            /* class com.android.camera.module.impl.component.ConfigChangeImpl.AnonymousClass1 */

            public void accept(BaseModule baseModule) {
                int valueSelectedStringIdIgnoreClose = DataRepository.dataItemConfig().getComponentConfigMeter().getValueSelectedStringIdIgnoreClose(((BaseModule) ConfigChangeImpl.this.getBaseModule().get()).getModuleIndex());
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_METER, 0, valueSelectedStringIdIgnoreClose);
                }
            }
        });
        getBaseModule().ifPresent(C0078a.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configModeEdit() {
        Log.d(TAG, "configModeEdit");
        Intent intent = new Intent(this.mActivity, ModeEditorActivity.class);
        intent.putExtra("from_where", ModuleManager.getActiveModuleIndex());
        if (this.mActivity.startFromKeyguard()) {
            intent.putExtra("StartActivityWhenLocked", true);
        }
        this.mActivity.startActivity(intent);
        this.mActivity.setJumpFlag(7);
        MistatsWrapper.customizeCameraSettingClick(MistatsConstants.CUSTOMIZE_CAMERA.PREF_KEY_CUSTOM_MODE_ICON);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configModeMore(boolean z) {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            Log.d(TAG, "configModeMore");
            baseDelegate.delegateEvent(z ? 31 : 30);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configNearRangeMode() {
        getBaseModule().ifPresent(u.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configPortraitSwitch(int i) {
        getBaseModule().ifPresent(l.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configRGBHistogramSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            BaseModule baseModule2 = baseModule.get();
            if (baseModule2.isFrameAvailable()) {
                int moduleIndex = baseModule2.getModuleIndex();
                boolean isProVideoHistogramOpen = CameraSettings.isProVideoHistogramOpen(moduleIndex);
                CameraSettings.setProVideoHistogramOpen(!isProVideoHistogramOpen);
                MistatsWrapper.moduleUIClickEvent("M_proVideo_", MistatsConstants.Manual.HISTOGRAM, isProVideoHistogramOpen ? "off" : "on");
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.updateRGBHistogramSwitched(!isProVideoHistogramOpen);
                }
                changeModeWithoutConfigureData(moduleIndex, false);
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public void configRatio(boolean z, String str) {
        boolean z2;
        boolean z3;
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (baseModule2.isFrameAvailable()) {
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    int moduleIndex = baseModule2.getModuleIndex();
                    ComponentConfigRatio componentConfigRatio = DataRepository.dataItemConfig().getComponentConfigRatio();
                    if (z) {
                        str = componentConfigRatio.getDefaultValue(moduleIndex);
                    } else {
                        CameraStatUtils.trackPictureSize(moduleIndex, str);
                        if (DataRepository.dataItemRunning().reConfigCinematicAspectRatioIfRatioChanged(moduleIndex, str) && topAlert != null) {
                            topAlert.updateConfigItem(251);
                        }
                    }
                    if (CameraSettings.isCinematicAspectRatioEnabled(moduleIndex)) {
                        z = true;
                        str = ComponentConfigRatio.RATIO_16X9;
                    }
                    char c2 = 65535;
                    switch (str.hashCode()) {
                        case 50858:
                            if (str.equals(ComponentConfigRatio.RATIO_1X1)) {
                                c2 = 6;
                                break;
                            }
                            break;
                        case 53743:
                            if (str.equals(ComponentConfigRatio.RATIO_4X3)) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case 1515430:
                            if (str.equals(ComponentConfigRatio.RATIO_16X9)) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 1517352:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_18X9)) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 1518313:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_19X9)) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 1539455:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_20X9)) {
                                c2 = 5;
                                break;
                            }
                            break;
                        case 1456894192:
                            if (str.equals(ComponentConfigRatio.RATIO_FULL_195X9)) {
                                c2 = 4;
                                break;
                            }
                            break;
                    }
                    int i = 165;
                    switch (c2) {
                        case 0:
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                z3 = false;
                                z2 = false;
                                i = 163;
                                break;
                            }
                            i = moduleIndex;
                            z3 = false;
                            z2 = z3;
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                moduleIndex = 163;
                            }
                            i = moduleIndex;
                            z3 = true;
                            z2 = z3;
                            break;
                        case 6:
                            componentConfigRatio.setComponentValue(moduleIndex, componentConfigRatio.getDefaultValue(moduleIndex));
                            if (moduleIndex == 165 || moduleIndex == 163) {
                                configLiveShotSwitch(3);
                            }
                            z3 = true;
                            z2 = z3;
                            break;
                        default:
                            i = moduleIndex;
                            z3 = false;
                            z2 = z3;
                            break;
                    }
                    if (z3) {
                        DataRepository.dataItemRunning().getComponentRunningAIWatermark().setAIWatermarkEnable(false);
                    }
                    if (z2 && CameraSettings.isUltraPixelOn()) {
                        switchOffElementsSilent(209);
                    }
                    if (!z) {
                        componentConfigRatio.setComponentValue(i, str);
                    }
                    DataRepository.dataItemGlobal().setCurrentMode(i);
                    changeModeWithoutConfigureData(i, false);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configRawSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            ComponentConfigRaw componentConfigRaw = DataRepository.dataItemConfig().getComponentConfigRaw();
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isSwitchOn = componentConfigRaw.isSwitchOn(moduleIndex);
            CameraCapabilities cameraCapabilities = baseModule.get().getCameraCapabilities();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("configRawSwitch: ");
            sb.append(!isSwitchOn);
            Log.d(str, sb.toString());
            if (i == 1) {
                if (isSwitchOn) {
                    componentConfigRaw.setRaw(moduleIndex, false);
                    MistatsWrapper.moduleUIClickEvent("M_manual_", "raw", "off");
                } else {
                    componentConfigRaw.setRaw(moduleIndex, true);
                    if (DataRepository.dataItemFeature().a(cameraCapabilities)) {
                        closeMutexElement("n", 209);
                    }
                    MistatsWrapper.moduleUIClickEvent("M_manual_", "raw", "on");
                }
                changeModeWithoutConfigureData(moduleIndex, false);
                reCheckRaw();
            } else if (i == 2) {
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configRotationChange(int i, int i2) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        boolean z = true;
        if (!(i2 == 0 || i2 == 90)) {
            if (i2 == 180) {
                if (topAlert != null) {
                    topAlert.updateLyingDirectHint(false, false);
                }
                if (bottomPopupTips != null) {
                    if (i != 1) {
                        z = false;
                    }
                    bottomPopupTips.updateLyingDirectHint(z, false);
                    return;
                }
                return;
            } else if (i2 != 270) {
                return;
            }
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateLyingDirectHint(false, false);
        }
        if (topAlert != null) {
            if (i != 1) {
                z = false;
            }
            topAlert.updateLyingDirectHint(z, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configScene(int i) {
        final Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            boolean state = getState(i, "pref_camera_scenemode_setting_key");
            ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (state) {
                bottomPopupTips.hideTipImage();
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    miBeautyProtocol.dismiss(2);
                }
                manuallyAdjust.setManuallyVisible(2, 1, new ManuallyListener() {
                    /* class com.android.camera.module.impl.component.ConfigChangeImpl.AnonymousClass2 */

                    @Override // com.android.camera.fragment.manually.ManuallyListener
                    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
                        ((BaseModule) baseModule.get()).onSharedPreferenceChanged();
                        ((BaseModule) baseModule.get()).updatePreferenceInWorkThread(4);
                    }
                });
            } else {
                bottomPopupTips.reInitTipImage();
                manuallyAdjust.setManuallyVisible(2, i == 1 ? 4 : 3, null);
            }
            baseModule.get().onSharedPreferenceChanged();
            baseModule.get().updatePreferenceInWorkThread(4);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configSuperResolutionSwitch(int i) {
        boolean state = getState(i, "pref_camera_super_resolution_key");
        if (1 == i) {
            trackSuperResolutionChanged(state);
        }
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            MutexModeManager mutexModePicker = baseModule.get().getMutexModePicker();
            if (state) {
                closeMutexElement(SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, 193, 194);
                mutexModePicker.setMutexModeMandatory(9);
                return;
            }
            mutexModePicker.clearMandatoryFlag();
            baseModule.get().resetMutexModeManually();
            restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION);
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010a  */
    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configSwitchUltraPixel(int i) {
        char c2;
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                int moduleIndex = baseModule2.getModuleIndex();
                boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
                boolean z = !isUltraPixelOn;
                ComponentRunningUltraPixel componentUltraPixel = DataRepository.dataItemRunning().getComponentUltraPixel();
                String currentSupportUltraPixel = componentUltraPixel.getCurrentSupportUltraPixel();
                CameraCapabilities cameraCapabilities = baseModule.get().getCameraCapabilities();
                if (i == 1) {
                    if (CameraSettings.isUltraWideConfigOpen(moduleIndex)) {
                        CameraSettings.setUltraWideConfig(moduleIndex, false);
                        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                        bottomPopupTips.updateLeftTipImage();
                        bottomPopupTips.directlyHideTips();
                    }
                    configDocumentMode(3);
                    if (z) {
                        int hashCode = currentSupportUltraPixel.hashCode();
                        if (hashCode != -1379357773) {
                            switch (hashCode) {
                                case -70725170:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_64M)) {
                                        c2 = 2;
                                        break;
                                    }
                                    break;
                                case -70725169:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_48M)) {
                                        c2 = 0;
                                        break;
                                    }
                                    break;
                                case -70725168:
                                    if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_108M)) {
                                        c2 = 3;
                                        break;
                                    }
                                    break;
                            }
                            if (c2 == 0) {
                                int[] iArr = {194, 239, 201, 206};
                                if (DataRepository.dataItemFeature().a(cameraCapabilities)) {
                                    iArr = Arrays.copyOf(iArr, iArr.length + 1);
                                    iArr[iArr.length - 1] = 237;
                                }
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, iArr);
                            } else if (c2 == 1) {
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, 196, 201, 206);
                            } else if ((c2 == 2 || c2 == 3) && DataRepository.dataItemFeature().a(cameraCapabilities)) {
                                closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, 237);
                            }
                            DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                            CameraSettings.switchOnUltraPixel(currentSupportUltraPixel);
                            configTimerBurst(3);
                        } else if (currentSupportUltraPixel.equals(ComponentRunningUltraPixel.ULTRA_PIXEL_ON_FRONT_32M)) {
                            c2 = 1;
                            if (c2 == 0) {
                            }
                            DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                            CameraSettings.switchOnUltraPixel(currentSupportUltraPixel);
                            configTimerBurst(3);
                        }
                        c2 = 65535;
                        if (c2 == 0) {
                        }
                        DataRepository.dataItemRunning().setRecordingClosedElements(this.mRecordingClosedElements);
                        CameraSettings.switchOnUltraPixel(currentSupportUltraPixel);
                        configTimerBurst(3);
                    } else {
                        this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                        restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                        CameraSettings.switchOffUltraPixel();
                    }
                    ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                    if (miBeautyProtocol != null ? miBeautyProtocol.isBeautyPanelShow() : false) {
                        miBeautyProtocol.dismiss(2);
                    }
                    if (baseModule2.getModuleIndex() == 165) {
                        changeMode(163);
                    } else {
                        changeModeWithoutConfigureData(baseModule2.getModuleIndex(), false);
                    }
                    if (z) {
                        setTipsState(FragmentTopAlert.TIP_ULTRA_PIXEL, true);
                    } else {
                        topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_PIXEL, 8, componentUltraPixel.getUltraPixelCloseTip());
                    }
                    if (baseModule2.getModuleIndex() == 167) {
                        StringBuilder sb = new StringBuilder(16);
                        sb.append(String.valueOf(z));
                        sb.append(currentSupportUltraPixel);
                        MistatsWrapper.moduleUIClickEvent("M_manual_", MistatsConstants.Manual.SUPERME_PIXEL, sb.toString());
                    }
                } else if (i == 3 && isUltraPixelOn) {
                    this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                    if (this.mRecordingClosedElements != null) {
                        restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                    }
                    CameraSettings.switchOffUltraPixel();
                    if (DataRepository.dataItemRunning().getLastUiStyle() == 3) {
                        changeMode(baseModule2.getModuleIndex());
                    } else {
                        baseModule2.restartModule();
                    }
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_PIXEL, 8, componentUltraPixel.getUltraPixelCloseTip());
                }
                ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                ModeProtocol.MiBeautyProtocol miBeautyProtocol2 = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (z) {
                    if (ComponentRunningUltraPixel.ULTRA_PIXEL_ON_REAR_48M.equals(currentSupportUltraPixel) && bottomPopupTips2 != null) {
                        bottomPopupTips2.directHideTipImage();
                        bottomPopupTips2.directShowOrHideLeftTipImage(false);
                        bottomPopupTips2.hideQrCodeTip();
                    }
                    if (dualController != null) {
                        dualController.hideZoomButton();
                        return;
                    }
                    return;
                }
                boolean z2 = false;
                if (miBeautyProtocol2 != null) {
                    z2 = miBeautyProtocol2.isBeautyPanelShow();
                }
                if (bottomPopupTips2 != null && !z2) {
                    bottomPopupTips2.reInitTipImage();
                }
                if (dualController != null && !z2) {
                    if (moduleIndex != 167) {
                        dualController.showZoomButton();
                    }
                    if (topAlert != null) {
                        topAlert.clearAlertStatus();
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configTiltSwitch(int i) {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        BaseModule baseModule2 = baseModule.get();
        if (baseModule.isPresent() && (baseModule2 instanceof Camera2Module) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            ComponentRunningTiltValue componentRunningTiltValue = DataRepository.dataItemRunning().getComponentRunningTiltValue();
            boolean isSwitchOn = componentRunningTiltValue.isSwitchOn(160);
            if (i == 1) {
                if (!isSwitchOn) {
                    CameraStatUtils.trackTiltShiftChanged(ComponentRunningTiltValue.TILT_CIRCLE);
                    componentRunningTiltValue.toSwitch(160, true);
                    configDocumentMode(3);
                    configTimerBurst(3);
                    isSwitchOn = true;
                } else {
                    CameraStatUtils.trackTiltShiftChanged("off");
                    componentRunningTiltValue.toSwitch(160, false);
                    isSwitchOn = false;
                }
                ((Camera2Module) baseModule.get()).showOrHideChip(!isSwitchOn);
            } else if (i != 2 && i == 3) {
                componentRunningTiltValue.toSwitch(160, false);
                isSwitchOn = false;
            }
            topAlert.alertSlideSwitchLayout(isSwitchOn, 228);
            ((Camera2Module) baseModule.get()).onTiltShiftSwitched(isSwitchOn);
            EffectController.getInstance().setDrawTilt(isSwitchOn);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configTimerSwitch(String str) {
        ComponentRunningTimer componentRunningTimer = DataRepository.dataItemRunning().getComponentRunningTimer();
        CameraStatUtils.trackTimerChanged(str);
        componentRunningTimer.setComponentValue(160, str);
    }

    public void configUltraPixelPortrait(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            boolean isSwitchOn = dataItemRunning.isSwitchOn("pref_camera_ultra_pixel_portrait_mode_key");
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (i == 1) {
                if (isSwitchOn) {
                    dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_PIXEL_PORTRAIT, 8, R.string.ultra_pixel_portrait_hint);
                    this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
                } else {
                    dataItemRunning.switchOn("pref_camera_ultra_pixel_portrait_mode_key");
                    closeMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, 194, 196, 201, 239, 254);
                    dataItemRunning.setRecordingClosedElements(this.mRecordingClosedElements);
                    ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                    if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                        miBeautyProtocol.dismiss(2);
                    }
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_PIXEL_PORTRAIT, 0, R.string.ultra_pixel_portrait_hint);
                }
                trackUltraPixelPortraitChanged(!isSwitchOn);
            } else if (i == 3 && isSwitchOn) {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_PIXEL_PORTRAIT, 8, R.string.ultra_pixel_portrait_hint);
                this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                if (this.mRecordingClosedElements != null) {
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
                }
                dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
            }
            baseModule.get().updatePreferenceInWorkThread(57);
            ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).updateTipImage();
            topAlert.updateConfigItem(215);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configVideoFast() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex != 169) {
                CameraStatUtils.trackVideoModeChanged(CameraSettings.VIDEO_SPEED_FAST);
                switchOffElementsSilent(216);
                CameraSettings.setAutoZoomEnabled(moduleIndex, false);
                CameraSettings.setSuperEISEnabled(moduleIndex, false);
                singeSwitchVideoBeauty(false);
                if (CameraSettings.isMacroModeEnabled(moduleIndex)) {
                    DataRepository.dataItemRunning().getComponentRunningMacroMode().setSwitchOff(moduleIndex);
                }
                CameraSettings.setSubtitleEnabled(moduleIndex, false);
                CameraSettings.setVideoQuality8K(moduleIndex, false);
                changeMode(169);
                updateTipMessage(4, R.string.hint_fast_motion, 2);
                return;
            }
            hideTipMessage(R.string.hint_fast_motion);
            dataItemRunning.switchOff("pref_video_speed_fast_key");
            CameraStatUtils.trackVideoModeChanged("normal");
            DataRepository.dataItemGlobal().setCurrentMode(162);
            changeMode(162);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void configVideoLogSwitch(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            boolean isProVideoLogOpen = CameraSettings.isProVideoLogOpen(moduleIndex);
            CameraSettings.setProVideoLog(!isProVideoLogOpen);
            MistatsWrapper.moduleUIClickEvent("M_proVideo_", MistatsConstants.Manual.LOG, isProVideoLogOpen ? "off" : "on");
            if (!isProVideoLogOpen) {
                HybridZoomingSystem.clearZoomRatioHistory();
                resetVideoFilter();
                CameraSettings.setVideoQuality8K(moduleIndex, false);
                DataRepository.dataItemConfig().getManuallyDualLens().setComponentValue(moduleIndex, ComponentManuallyDualLens.LENS_WIDE);
            }
            changeModeWithoutConfigureData(moduleIndex, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void findBestWatermarkItem(int i) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            baseModule.get().updatePreferenceInWorkThread(i);
        }
    }

    public /* synthetic */ void h(BaseModule baseModule) {
        if (baseModule instanceof Camera2Module) {
            configMoon(false);
            Log.d(TAG, "(moon_mode) config moon night");
            ((Camera2Module) baseModule).updateMoonNight();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
        if (r14 == 206) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0075, code lost:
        if (r14 == 229) goto L_0x0073;
     */
    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void onConfigChanged(int i) {
        if (isAlive()) {
            ConfigChangeInterceptor configChangeInterceptor = this.mChangeInterceptor;
            if (configChangeInterceptor != null && configChangeInterceptor.consumeConfigChanged(i) && this.mChangeInterceptor.asBlocker()) {
                return;
            }
            if (SupportedConfigFactory.isMutexConfig(i)) {
                DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
                int[] iArr = SupportedConfigFactory.MUTEX_MENU_CONFIGS;
                boolean z = false;
                int i2 = 176;
                for (int i3 : iArr) {
                    if (!(i3 == i || ((i == 209 && i3 == 229) || (i == 229 && i3 == 209)))) {
                        if (i3 != 203) {
                            if (i3 != 206) {
                                if (i3 != 209) {
                                    if (i3 != 229) {
                                        if (!dataItemRunning.isSwitchOn(SupportedConfigFactory.getConfigKey(i3))) {
                                        }
                                        i2 = i3;
                                    } else if (!CameraSettings.isGradienterOn()) {
                                    }
                                } else if (CameraSettings.isUltraPixelOn()) {
                                    z = true;
                                    i2 = i3;
                                }
                            } else if (CameraSettings.isLiveShotOn()) {
                                if (i != 209) {
                                }
                            }
                            i2 = 176;
                        } else if (((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).isShowLightingView()) {
                            showOrHideLighting(false);
                        }
                    }
                }
                if (!z) {
                    if (i2 != 176) {
                        applyConfig(i2, 3);
                    }
                    applyConfig(i, 1);
                    return;
                }
                applyConfig(i, 1);
                if (i2 != 176) {
                    applyConfig(i2, 3);
                    return;
                }
                return;
            }
            applyConfig(i, 1);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void onConfigValueChanged(int i, String str) {
        if (isAlive()) {
            applyConfigValue(i, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void onShineDismiss(int i) {
        ModeProtocol.AmbilightSelector ambilightSelector;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 167 || moduleIndex == 180) {
                ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                if (manuallyAdjust != null) {
                    manuallyAdjust.setManuallyLayoutVisible(true);
                }
            } else if (moduleIndex == 187 && (ambilightSelector = (ModeProtocol.AmbilightSelector) ModeCoordinatorImpl.getInstance().getAttachProtocol(421)) != null) {
                ambilightSelector.setSelectorLayoutVisible(true);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void onThermalNotification(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (!baseModule.isPresent()) {
                Log.w(TAG, "onThermalNotification current module is null");
                return;
            }
            BaseModule baseModule2 = baseModule.get();
            if (!baseModule2.isFrameAvailable() || baseModule2.isSelectingCapturedResult()) {
                Log.w(TAG, "onThermalNotification current module has not ready");
                return;
            }
            baseModule2.setThermalLevel(i);
            if (ThermalDetector.thermalConstrained(i)) {
                Log.w(TAG, "thermalConstrained");
                baseModule2.thermalConstrained();
            }
            ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
            if (componentFlash.isEmpty() || !componentFlash.isHardwareSupported()) {
                Log.w(TAG, "onThermalNotification don't support hardware flash");
                return;
            }
            baseModule2.updatePreferenceInWorkThread(66);
            String str = "0";
            if (ThermalDetector.thermalCloseFlash(i)) {
                Log.w(TAG, "thermalCloseFlash");
            } else if (!baseModule2.isThermalThreshold() || ((i != 2 || !CameraSettings.isFrontCamera()) && i != 3)) {
                str = "";
            } else {
                Log.w(TAG, "recording time is up to thermal threshold");
            }
            updateFlashModeAndRefreshUI(baseModule2, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAIWatermark(boolean z) {
        ModeProtocol.TopAlert topAlert;
        if (DataRepository.dataItemFeature().il()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
                if (moduleIndex == 205 || moduleIndex == 188) {
                    componentRunningAIWatermark.setAIWatermarkEnable(true);
                    WatermarkItem majorWatermarkItem = componentRunningAIWatermark.getMajorWatermarkItem();
                    boolean z2 = moduleIndex == 205;
                    int i = 2;
                    if (majorWatermarkItem != null) {
                        int type = majorWatermarkItem.getType();
                        z2 = (type == 0 || type == 1 || type == 2 || type == 3 || type == 4 || type == 11 || type == 12) ? false : true;
                    }
                    if (z2) {
                        ModeProtocol.AIWatermarkDetect aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254);
                        if (aIWatermarkDetect != null) {
                            aIWatermarkDetect.resetResult();
                        }
                        setWatermarkEnable(true);
                        updateASDForWatermark();
                    } else {
                        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                        if (mainContentProtocol != null) {
                            mainContentProtocol.updateWatermarkSample(majorWatermarkItem);
                            int upDistance = getUpDistance();
                            if (upDistance > 0) {
                                i = 1;
                            }
                            mainContentProtocol.moveWatermarkLayout(i, upDistance);
                        }
                    }
                    configTimerBurst(3);
                    return;
                }
                boolean aIWatermarkEnable = componentRunningAIWatermark.getAIWatermarkEnable(moduleIndex);
                boolean needForceDisable = componentRunningAIWatermark.needForceDisable(moduleIndex);
                if (!aIWatermarkEnable || !needForceDisable) {
                    if (aIWatermarkEnable && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                        topAlert.alertSwitchTip("ai_watermark", 0, R.string.ai_watermark_title);
                    }
                    if (!z) {
                        boolean iWatermarkEnable = componentRunningAIWatermark.getIWatermarkEnable();
                        if (aIWatermarkEnable && iWatermarkEnable) {
                            ModeProtocol.AIWatermarkDetect aIWatermarkDetect2 = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254);
                            if (aIWatermarkDetect2 != null) {
                                aIWatermarkDetect2.resetResult();
                            }
                            setWatermarkEnable(true);
                        }
                        if (moduleIndex == 163 || moduleIndex == 165 || moduleIndex == 188) {
                            showDualController(aIWatermarkEnable);
                            return;
                        }
                        return;
                    }
                    return;
                }
                showWatermarkSample(false);
                componentRunningAIWatermark.setAIWatermarkEnable(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAiAudio() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                int currentStringRes = DataRepository.dataItemRunning().getComponentRunningAiAudio().getCurrentStringRes(moduleIndex);
                topAlert.alertAiAudioBGHint(currentStringRes != -1 ? 0 : 8, currentStringRes);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAiScene() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && CameraSettings.getAiSceneOpen(baseModule.get().getModuleIndex()) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            topAlert.alertSwitchTip("ai", 0, R.string.pref_camera_front_ai_scene_entry_on);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckAmbilight() {
        ModeProtocol.AmbilightSelector ambilightSelector;
        if (DataRepository.dataItemFeature().sm()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getModuleIndex() == 187 && (ambilightSelector = (ModeProtocol.AmbilightSelector) ModeCoordinatorImpl.getInstance().getAttachProtocol(421)) != null) {
                ambilightSelector.updateTips();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckColorEnhance() {
        ModeProtocol.TopAlert topAlert;
        if (DataRepository.dataItemFeature().supportColorEnhance()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                if (DataRepository.dataItemRunning().getComponentRunningColorEnhance().isEnabled(baseModule.get().getModuleIndex()) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                    topAlert.alertProColourHint(0, R.string.pro_color_mode);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckDocumentMode() {
        if (DataRepository.dataItemFeature().qj() || DataRepository.dataItemFeature().rj()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (topAlert != null) {
                Optional<BaseModule> baseModule = getBaseModule();
                if (baseModule.isPresent() && CameraSettings.isDocumentModeOn(baseModule.get().getModuleIndex())) {
                    topAlert.alertSlideSwitchLayout(true, 221);
                    getBaseModule().ifPresent(j.INSTANCE);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckExposureFeedbackConfig() {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if ((moduleIndex == 180 || moduleIndex == 167) && DataRepository.dataItemRunning().isSwitchOn("pref_camera_exposure_feedback")) {
                    Log.d(TAG, "reCheckExposureFeedbackConfig: configExposureFeedbackSwitch");
                    configExposureFeedbackSwitch(2);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckEyeLight() {
        String eyeLightType = CameraSettings.getEyeLightType();
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (topAlert != null && bottomPopupTips != null && !"-1".equals(eyeLightType)) {
            topAlert.alertTopHint(0, R.string.eye_light);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckFastMotion(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getModuleIndex() == 169 && DataRepository.dataItemFeature().uj() && !topAlert.isExtraMenuShowing()) {
                ComponentRunningFastMotionSpeed componentRunningFastMotionSpeed = DataRepository.dataItemRunning().getComponentRunningFastMotionSpeed();
                ComponentRunningFastMotionDuration componentRunningFastMotionDuration = DataRepository.dataItemRunning().getComponentRunningFastMotionDuration();
                boolean z2 = componentRunningFastMotionSpeed.isModified() || componentRunningFastMotionDuration.isModified();
                ModeProtocol.FastMotionProtocol fastMotionProtocol = (ModeProtocol.FastMotionProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(674);
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (!z2 || ((fastMotionProtocol != null && fastMotionProtocol.isShowing()) || cameraAction == null || cameraAction.isRecording())) {
                    topAlert.alertFastmotionIndicator(8, "", "", "", z);
                } else {
                    topAlert.alertFastmotionIndicator(0, componentRunningFastMotionSpeed.getValueDisplayStringNotFromResource(160), componentRunningFastMotionDuration.isModified() ? componentRunningFastMotionDuration.getComponentValue(160) : "", componentRunningFastMotionDuration.isModified() ? this.mActivity.getResources().getQuantityString(R.plurals.pref_camera_fastmotion_duration_unit, 10, "") : this.mActivity.getString(R.string.pref_camera_fastmotion_duration_infinity), z);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckFocusPeakConfig() {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if ((moduleIndex == 180 || moduleIndex == 167) && DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key")) {
                    Log.d(TAG, "reCheckFocusPeakConfig: configFocusPeakSwitch");
                    configFocusPeakSwitch(2);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckFrontBokenTip() {
        if (DataRepository.dataItemFeature().yj() && ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && "on".equals(DataRepository.dataItemConfig().getComponentBokeh().getComponentValue(baseModule.get().getModuleIndex()))) {
                updateTipMessage(4, R.string.bokeh_use_hint, 2);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckGradienter() {
        if (CameraSettings.isGradienterOn()) {
            configGradienterSwitch(2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckHanGestureDescTip() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.getTipsState(FragmentTopAlert.TIP_HAND_GESTURE_DESC)) {
            setTipsState(FragmentTopAlert.TIP_HAND_GESTURE_DESC, false);
            if (CameraSettings.isHandGestureOpen()) {
                topAlert.alertRecommendDescTip(FragmentTopAlert.TIP_HAND_GESTURE_DESC, 0, R.string.hand_gesture_open_tip);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckHandGesture() {
        ModeProtocol.TopAlert topAlert;
        if (getBaseModule().isPresent() && CameraSettings.isHandGestureOpen() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            topAlert.alertHandGestureHint(R.string.hand_gesture_tip);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckLighting() {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            if (!DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171).equals("0") && !actionProcessing.isShowLightingView()) {
                actionProcessing.showOrHideLightingView();
            }
            if (actionProcessing.isShowLightingView()) {
                actionProcessing.showOrHideLightingView();
                showOrHideLighting(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckLiveShot() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            BaseModule baseModule2 = baseModule.get();
            if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 165) && DataRepository.dataItemFeature().Lj() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && CameraSettings.isLiveShotOn()) {
                topAlert.alertLiveShotHint(0, R.string.camera_liveshot_on_tip);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckLiveVV() {
        ModeProtocol.TopAlert topAlert;
        ModeProtocol.BaseDelegate baseDelegate;
        if (DataRepository.dataItemFeature().Mj()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getModuleIndex() == 162 && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && this.mActivity != null && (baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 65530) {
                topAlert.alertTopHint(0, R.string.vv_use_hint_text_title);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckMacroMode() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if ((baseModule2.getModuleIndex() == 163 || baseModule2.getModuleIndex() == 162 || baseModule2.getModuleIndex() == 165 || baseModule2.getModuleIndex() == 172 || baseModule2.getModuleIndex() == 186 || baseModule2.getModuleIndex() == 205) && !topAlert.isExtraMenuShowing() && CameraSettings.isMacroModeEnabled(baseModule2.getModuleIndex())) {
                    topAlert.alertMacroModeHint(0, DataRepository.dataItemRunning().getComponentRunningMacroMode().getResText());
                    CameraCapabilities cameraCapabilities = baseModule2.getCameraCapabilities();
                    if (cameraCapabilities != null && cameraCapabilities.isMacroHdrMutex()) {
                        updateComponentHdr(true);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckModuleUltraPixel() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && !DataRepository.dataItemRunning().getBoolean("pref_module_ultra_pixel_tip", false) && CameraSettings.isUltraPixelOn()) {
            DataRepository.dataItemRunning().putBoolean("pref_module_ultra_pixel_tip", true);
            topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_PIXEL, 0, DataRepository.dataItemRunning().getComponentUltraPixel().getUltraPixelOpenTip());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckMutexConfigs(int i) {
        if (isAlive()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated()) {
                DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
                int[] iArr = SupportedConfigFactory.MUTEX_MENU_CONFIGS;
                for (int i2 : iArr) {
                    if (i2 == 203) {
                        int portraitLightVersion = DataRepository.dataItemRunning().getComponentRunningLighting().getPortraitLightVersion();
                        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                        if (portraitLightVersion <= 1) {
                            reCheckLighting();
                        } else {
                            ModeProtocol.ActionProcessing actionProcessing2 = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                            if (actionProcessing2 != null) {
                                actionProcessing2.setLightingViewStatus(false);
                            }
                        }
                    } else if (i2 != 209 && dataItemRunning.isSwitchOn(SupportedConfigFactory.getConfigKey(i2))) {
                        applyConfig(i2, 2);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckMutexEarly(int i) {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        boolean isUltraPixelPortraitFrontOn = CameraSettings.isUltraPixelPortraitFrontOn();
        boolean aiSceneOpen = CameraSettings.getAiSceneOpen(i);
        if (isUltraPixelPortraitFrontOn && aiSceneOpen) {
            this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
            if (this.mRecordingClosedElements != null) {
                restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);
            }
            dataItemRunning.switchOff("pref_camera_ultra_pixel_portrait_mode_key");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckParameterDescriptionTip() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex != 167 && moduleIndex != 180 && moduleIndex != 169 && moduleIndex != 187) {
                return;
            }
            if (moduleIndex != 169 || DataRepository.dataItemFeature().uj()) {
                boolean isParameterDescriptionEnable = (!isVideoRecoding(baseModule.get())) & CameraSettings.isParameterDescriptionEnable();
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.alertParameterDescriptionTip(isParameterDescriptionEnable & (topAlert.isExtraMenuShowing() ^ true) ? 0 : 8);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckParameterResetTip(boolean z) {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if ((moduleIndex != 167 && moduleIndex != 180) || (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) == null || topAlert.isExtraMenuShowing()) {
                return;
            }
            if (!isChangeManuallyParameters()) {
                topAlert.alertParameterResetTip(z, 8, R.string.reset_manually_parameter_hint);
            } else {
                topAlert.alertParameterResetTip(z, 0, R.string.reset_manually_parameter_hint);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckRaw() {
        int moduleIndex;
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (!baseModule.isPresent() || (moduleIndex = baseModule.get().getModuleIndex()) != 167 || topAlert.isExtraMenuShowing()) {
                return;
            }
            if (DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(moduleIndex)) {
                topAlert.alertVideoUltraClear(0, R.string.manually_raw_hint);
            } else {
                topAlert.alertVideoUltraClear(8, R.string.manually_raw_hint);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckSpeechShutterDescTip() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.getTipsState(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC) && CameraSettings.isSpeechShutterOpen()) {
            topAlert.alertRecommendDescTip(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC, 0, R.string.speech_shutter_open_tip);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckSubtitleMode() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                BaseModule baseModule2 = baseModule.get();
                if (CameraSettings.isSubtitleEnabled(baseModule2.getModuleIndex()) && !isVideoRecoding(baseModule2)) {
                    topAlert.alertSubtitleHint(0, R.string.pref_video_subtitle);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckSuperEIS() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent() && CameraSettings.isSuperEISEnabled(getBaseModule().get().getModuleIndex())) {
            topAlert.alertSwitchTip(FragmentTopAlert.TIP_SUPER_EIS, 0, R.string.super_eis);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckSuperEISPro() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent()) {
            ComponentRunningEisPro componentRunningEisPro = DataRepository.dataItemRunning().getComponentRunningEisPro();
            String componentValue = componentRunningEisPro.getComponentValue(getBaseModule().get().getModuleIndex());
            String componentPreValue = componentRunningEisPro.getComponentPreValue();
            if (componentValue.equals("off")) {
                if (componentPreValue.equals("normal")) {
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_SUPER_EIS, 8, R.string.super_eis_disabled_hint);
                } else if (componentPreValue.equals(ComponentRunningEisPro.EIS_VALUE_PRO)) {
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_SUPER_EIS, 8, R.string.super_eis_pro_disabled_hint);
                }
            } else if (componentValue.equals("normal")) {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_SUPER_EIS, 0, R.string.super_eis);
            } else if (componentValue.equals(ComponentRunningEisPro.EIS_VALUE_PRO)) {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_SUPER_EIS, 0, R.string.super_eis_pro);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckTilt() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && CameraSettings.isTiltShiftOn()) {
            topAlert.alertSlideSwitchLayout(true, 228);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckTimerBurst() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && TimerBurstController.isSupportTimerBurst(baseModule.get().getModuleIndex()) && !topAlert.isExtraMenuShowing() && CameraSettings.isTimerBurstEnable()) {
                topAlert.alertTimerBurstHint(0, R.string.timer_burst);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckUltraWideBokeh() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && this.mActivity != null && getBaseModule().isPresent()) {
            if (DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled")) {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_WIDE_BOKEH, 0, R.string.ultra_wide_bokeh_open_tip);
            } else {
                topAlert.alertSwitchTip(FragmentTopAlert.TIP_ULTRA_WIDE_BOKEH, 8, R.string.ultra_wide_bokeh_close_tip);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoBeautify() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().getModuleIndex() == 162 && !topAlert.isExtraMenuShowing() && CameraSettings.isFaceBeautyOn(162, null)) {
                List<ComponentDataItem> items = DataRepository.dataItemRunning().getComponentRunningShine().getItems();
                if (items == null || items.size() <= 1) {
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_VIDEO_BEAUTIFY, 0, R.string.video_beauty_tip);
                } else {
                    topAlert.alertSwitchTip(FragmentTopAlert.TIP_VIDEO_BEAUTIFY, 0, R.string.video_beauty_tip_beautification);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoBeautyPipeline() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 162 || moduleIndex == 180) {
                changeModeWithoutConfigureData(baseModule.get().getModuleIndex(), false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoHDR10Tip() {
        ModeProtocol.TopAlert topAlert;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && CameraSettings.isHdr10Alive(baseModule.get().getModuleIndex()) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && !topAlert.isExtraMenuShowing()) {
            topAlert.alertVideoUltraClear(0, R.string.video_hdr10_tip);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoLog() {
        int moduleIndex;
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && (moduleIndex = baseModule.get().getModuleIndex()) == 180 && CameraSettings.isProVideoLogOpen(moduleIndex) && !topAlert.isExtraMenuShowing()) {
            topAlert.alertVideoUltraClear(0, R.string.pref_camera_video_log_tips);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reCheckVideoUltraClearTip() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 162 || moduleIndex == 169 || moduleIndex == 180) {
                CameraSize videoSize = ((VideoModule) baseModule.get()).getVideoSize();
                int i = videoSize.width;
                int i2 = videoSize.height;
                if (is8KQuality(i, i2)) {
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate == null || baseDelegate.getActiveFragment(R.id.bottom_action) != 65530) {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        boolean isProVideoLogOpen = CameraSettings.isProVideoLogOpen(moduleIndex);
                        if (topAlert != null && !topAlert.isExtraMenuShowing() && !isProVideoLogOpen && !CameraSettings.isReal8K()) {
                            topAlert.alertVideoUltraClear(0, R.string.video_ultra_clear_tip_8k);
                        }
                        if (DataRepository.dataItemGlobal().getBoolean(DataItemGlobal.DATA_COMMON_CAMCORDER_TIP_8K_MAX_VIDEO_DURATION_SHOWN, true)) {
                            DataRepository.dataItemGlobal().editor().putBoolean(DataItemGlobal.DATA_COMMON_CAMCORDER_TIP_8K_MAX_VIDEO_DURATION_SHOWN, false).apply();
                            if (topAlert != null && is8KQuality(i, i2)) {
                                topAlert.alertRecommendDescTip(FragmentTopAlert.TIP_8K_DESC, 0, R.string.camcorder_tip_8k_max_video_duration);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reConfigAiAudio(Context context, int i, boolean z) {
        int currentStringRes = DataRepository.dataItemRunning().getComponentRunningAiAudio().getCurrentStringRes(i);
        boolean isWiredHeadsetOn = Util.isWiredHeadsetOn();
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (currentStringRes != -1 && topAlert != null) {
            if (isWiredHeadsetOn) {
                topAlert.alertAiAudioBGHint(8, currentStringRes);
                topAlert.alertAiAudio(8, currentStringRes);
                topAlert.alertAiAudioMutexToastIfNeed(context, currentStringRes);
            } else if (z) {
                topAlert.alertAiAudio(0, currentStringRes);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void reConfigSpeechShutter() {
        ModeProtocol.CloneProcess cloneProcess;
        ModeProtocol.SpeechShutterDetect speechShutterDetect = (ModeProtocol.SpeechShutterDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(255);
        if (DataRepository.dataItemRunning().supportSpeechShutter()) {
            boolean isSpeechShutterOpen = CameraSettings.isSpeechShutterOpen();
            CameraSettings.setSpeechShutterStatus(isSpeechShutterOpen);
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if (isSpeechShutterOpen && moduleIndex == 185 && (cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418)) != null && cloneProcess.getMode() == FenShenCam.Mode.VIDEO) {
                    isSpeechShutterOpen = false;
                }
                if (moduleIndex == 254 || moduleIndex == 209 || moduleIndex == 210) {
                    isSpeechShutterOpen = false;
                }
                ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (actionProcessing != null) {
                    actionProcessing.processingSpeechShutter(isSpeechShutterOpen);
                }
                if (speechShutterDetect != null) {
                    speechShutterDetect.processingSpeechShutter(isSpeechShutterOpen);
                }
            }
        } else if (speechShutterDetect != null) {
            speechShutterDetect.processingSpeechShutter(false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void recheckFunAR() {
        getBaseModule().ifPresent(n.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void recheckVideoFps(boolean z) {
        getBaseModule().ifPresent(new p(z));
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(164, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void resetMeter(int i) {
        if (i == 167 || i == 180) {
            ComponentConfigMeter componentConfigMeter = DataRepository.dataItemConfig().getComponentConfigMeter();
            if (componentConfigMeter.isModified(i)) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                topAlert.reverseExpandTopBar(true);
                componentConfigMeter.reset(i);
                topAlert.updateConfigItem(214);
                getBaseModule().ifPresent(q.INSTANCE);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void restoreAllMutexElement(String str) {
        int[] iArr = this.mRecordingClosedElements;
        if (iArr != null) {
            int[] iArr2 = new int[iArr.length];
            int i = 0;
            while (true) {
                int[] iArr3 = this.mRecordingClosedElements;
                if (i < iArr3.length) {
                    int i2 = iArr3[i];
                    if (i2 == 193) {
                        updateComponentFlash(null, false);
                        iArr2[i] = 10;
                    } else if (i2 == 194) {
                        updateComponentHdr(false);
                        iArr2[i] = 11;
                    } else if (i2 == 196) {
                        updateComponentFilter(false);
                        iArr2[i] = 2;
                    } else if (i2 == 201) {
                        updateAiScene(false);
                        iArr2[i] = 36;
                    } else if (i2 == 206) {
                        updateLiveShot(false);
                        if (str != SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL) {
                            iArr2[i] = 49;
                        } else {
                            iArr2[i] = 50;
                        }
                    } else if (i2 == 209) {
                        updateUltraPixel(false);
                        iArr2[i] = 50;
                    } else if (i2 == 212) {
                        updateComponentShine(false);
                        iArr2[i] = 2;
                    } else if (i2 == 237) {
                        updateRaw(false);
                        iArr2[i] = 44;
                    } else if (i2 == 239) {
                        updateComponentBeauty(false);
                        iArr2[i] = 13;
                    } else if (i2 == 253) {
                        updateAutoZoom(false);
                        iArr2[i] = 51;
                    } else if (i2 == 254) {
                        updateEyeLight(false);
                        iArr2[i] = 45;
                    } else {
                        throw new RuntimeException("unknown mutex element");
                    }
                    i++;
                } else {
                    this.mRecordingClosedElements = null;
                    getBaseModule().ifPresent(new C0080c(iArr2));
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void restoreMutexFlash(String str) {
        if (DataRepository.dataItemConfig().getComponentFlash().isClosed()) {
            updateComponentFlash(str, false);
            getBaseModule().ifPresent(C0084g.INSTANCE);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setEyeLight(String str) {
        CameraSettings.setEyeLight(str);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.showTips(10, EyeLightConstant.getString(str), 4);
        }
        getBaseModule().ifPresent(i.INSTANCE);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setFilter(int i) {
        int shaderEffect = CameraSettings.getShaderEffect();
        persistFilter(i);
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if ((i == 0 || shaderEffect == 0) && shaderEffect != i && currentMode == 180) {
            CameraSettings.setVideoQuality8K(currentMode, false);
            CameraSettings.setProVideoLog(false);
            changeModeWithoutConfigureData(currentMode, false);
        }
        if ((i == 0 || shaderEffect == 0) && shaderEffect != i && currentMode == 169 && DataRepository.dataItemFeature().uj()) {
            changeModeWithoutConfigureData(currentMode, false);
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            if (dualController != null) {
                dualController.hideZoomButton();
            }
        }
        EffectController.getInstance().setInvertFlag(0);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (CameraSettings.isGroupShotOn()) {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configGroupSwitch(4);
            topAlert.refreshExtraMenu();
        }
        ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
        String str = TAG;
        Log.d(str, "setFilter: filterId = " + i + ", FilterProtocol = " + onShineChangedProtocol);
        String str2 = TAG;
        Log.d(str2, "onFilterChanged: category = " + FilterInfo.getCategory(i) + ", newIndex = " + FilterInfo.getIndex(i));
        if (onShineChangedProtocol != null) {
            onShineChangedProtocol.onShineChanged(false, 196);
        }
        if (CameraSettings.isUltraPixelFront32MPOn()) {
            configSwitchUltraPixel(3);
        }
        if (i != FilterInfo.FILTER_ID_NONE) {
            configDocumentMode(3);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setKaleidoscope(String str) {
        ModeProtocol.KaleidoscopeProtocol kaleidoscopeProtocol = (ModeProtocol.KaleidoscopeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(236);
        if (kaleidoscopeProtocol != null) {
            kaleidoscopeProtocol.onKaleidoscopeChanged(str);
        }
        ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
        if (onShineChangedProtocol != null) {
            onShineChangedProtocol.onShineChanged(false, 196);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setLighting(boolean z, String str, String str2, boolean z2) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        int portraitLightVersion = DataRepository.dataItemRunning().getComponentRunningLighting().getPortraitLightVersion();
        if (portraitLightVersion > 1 || str.equals("0") || str2.equals("0")) {
            boolean z3 = false;
            topAlert.updateConfigItem(203);
            if (str2.equals("0") || portraitLightVersion > 1) {
                z3 = true;
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (z3) {
                mainContentProtocol.lightingCancel();
                if (z) {
                    ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(6);
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(33);
                    }
                }
            } else {
                mainContentProtocol.lightingStart();
                actionProcessing.setLightingViewStatus(true);
            }
        }
        if (str2 == "0") {
            topAlert.alertLightingTip(-1);
        }
        getBaseModule().ifPresent(t.INSTANCE);
        if (z2) {
            CameraStatUtils.trackLightingChanged(171, str2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void setWatermarkEnable(boolean z) {
        clearToast();
        showDualController(z);
        showWatermarkSample(z);
        showOrHideTipImage(z);
        ModeProtocol.WatermarkProtocol watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            if (watermarkProtocol == null || !watermarkProtocol.isWatermarkPanelShow()) {
                bottomPopupTips.directShowOrHideLeftTipImage(z);
            } else {
                bottomPopupTips.directShowOrHideLeftTipImage(false);
            }
        }
        if (watermarkProtocol != null && !z) {
            watermarkProtocol.dismiss(2, 6);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideAIWatermark() {
        ModeProtocol.ManuallyAdjust manuallyAdjust;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            clearToast();
            int moduleIndex = baseModule.get().getModuleIndex();
            if (moduleIndex == 163 && (manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181)) != null) {
                manuallyAdjust.setManuallyVisible(0, 4, null);
            }
            ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
            ModeProtocol.WatermarkProtocol watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253);
            ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
            if (watermarkProtocol == null || !watermarkProtocol.isWatermarkPanelShow()) {
                bottomMenuProtocol.expandAIWatermarkBottomMenu(componentRunningAIWatermark);
                if (watermarkProtocol != null) {
                    watermarkProtocol.show();
                } else {
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(21);
                    }
                }
                if (moduleIndex != 188) {
                    CameraStatUtils.trackAIWatermarkClick(MistatsConstants.AIWatermark.AI_WATERMARK_LIST_SHOW);
                } else {
                    CameraStatUtils.trackSuperMoonClick(MistatsConstants.SuperMoon.PARAM_SUPER_MOON_EFFECT_CLICK);
                }
            } else {
                watermarkProtocol.dismiss(2, 6);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideFilter() {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null) {
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            boolean isShowLightingView = actionProcessing.isShowLightingView();
            boolean showOrHideFilterView = actionProcessing.showOrHideFilterView();
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            if (showOrHideFilterView && isShowLightingView) {
                actionProcessing.showOrHideLightingView();
                String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
                DataRepository.dataItemRunning().getComponentRunningLighting().setComponentValue(171, "0");
                setLighting(true, componentValue, "0", false);
            }
            if (showOrHideFilterView && dualController != null && currentMode == 171) {
                dualController.showBokehButton();
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (bottomPopupTips != null) {
                if (showOrHideFilterView) {
                    bottomPopupTips.updateLeftTipImage();
                } else if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                    bottomPopupTips.updateLeftTipImage();
                }
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideLighting(boolean z) {
        beautyMutexHandle();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            boolean showOrHideLightingView = ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).showOrHideLightingView();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
            if (showOrHideLightingView) {
                Optional<BaseModule> baseModule = getBaseModule();
                if (baseModule.isPresent()) {
                    if (DataRepository.dataItemRunning().getComponentRunningLighting().getPortraitLightVersion() <= 1) {
                        DataRepository.dataItemRunning().getComponentConfigFilter().reset(baseModule.get().getModuleIndex());
                        setFilter(FilterInfo.FILTER_ID_NONE);
                    }
                    if (dualController != null) {
                        dualController.hideBokehButton(false);
                    }
                    bottomPopupTips.directHideTipImage();
                    topAlert.refreshExtraMenu();
                    bottomMenuProtocol.onSwitchLiveActionMenu(167);
                    if (baseDelegate.getActiveFragment(R.id.bottom_beauty) != 4087) {
                        baseDelegate.delegateEvent(33);
                    }
                    setLighting(false, "0", DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171), false);
                } else {
                    return;
                }
            } else {
                String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
                String componentValue2 = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
                if (DataRepository.dataItemRunning().getComponentRunningLighting().getPortraitLightVersion() <= 1) {
                    DataRepository.dataItemRunning().getComponentRunningLighting().setComponentValue(171, "0");
                    componentValue2 = "0";
                }
                setLighting(true, componentValue, componentValue2, false);
            }
            if (z) {
                MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.PortraitAttr.VALUE_LIGHTING_OUT_BUTTON, null, null);
            }
            if (bottomPopupTips != null && showOrHideLightingView) {
                bottomPopupTips.hideLeftTipImage();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideMimoji() {
        beautyMutexHandle();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (baseDelegate != null && actionProcessing != null && bottomPopupTips != null && bottomMenuProtocol != null) {
            if (actionProcessing.showOrHideMiMojiView()) {
                if (topAlert != null) {
                    topAlert.updateConfigItem(193);
                }
                if (bottomPopupTips != null) {
                    bottomPopupTips.directlyHideTips();
                }
                bottomMenuProtocol.onSwitchLiveActionMenu(168);
            } else {
                bottomMenuProtocol.onRestoreCameraActionMenu(6);
            }
            bottomPopupTips.updateMimojiBottomTipImage();
            baseDelegate.delegateEvent(14);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0161  */
    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showOrHideShine() {
        ModeProtocol.AmbilightSelector ambilightSelector;
        ModeProtocol.TopAlert topAlert;
        boolean z;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            int moduleIndex = baseModule.get().getModuleIndex();
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            boolean z2 = true;
            boolean z3 = miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow();
            boolean isFaceBeautyOn = CameraSettings.isFaceBeautyOn(moduleIndex, null);
            ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
            if (moduleIndex == 162) {
                z = false;
            } else if (moduleIndex != 204) {
                z2 = false;
                if (!z3) {
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (bottomPopupTips != null) {
                        bottomPopupTips.hideQrCodeTip();
                        bottomPopupTips.directlyHideTips();
                        bottomPopupTips.setPortraitHintVisible(8);
                        bottomPopupTips.hideTipImage();
                        bottomPopupTips.hideLeftTipImage();
                        bottomPopupTips.hideRightTipImage();
                        bottomPopupTips.hideSpeedTipImage();
                        bottomPopupTips.hideCenterTipImage();
                        bottomPopupTips.directHideLeftImageIntro();
                        bottomPopupTips.directHideLyingDirectHint();
                    }
                    ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                    if (dualController != null) {
                        dualController.hideZoomButton();
                        if (!(moduleIndex == 171 || z2 || (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) == null)) {
                            topAlert.alertUpdateValue(0, null);
                        }
                    }
                    ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                    if (moduleIndex != 163) {
                        if (moduleIndex != 167) {
                            if (moduleIndex != 171) {
                                if (moduleIndex != 180) {
                                    if (moduleIndex == 187 && (ambilightSelector = (ModeProtocol.AmbilightSelector) ModeCoordinatorImpl.getInstance().getAttachProtocol(421)) != null) {
                                        ambilightSelector.setSelectorLayoutVisible(false);
                                    }
                                }
                            } else if (dualController != null && dualController.isButtonVisible()) {
                                dualController.hideBokehButton(false);
                            }
                        }
                        if (manuallyAdjust != null) {
                            manuallyAdjust.setManuallyLayoutVisible(false);
                        }
                    } else if (manuallyAdjust != null) {
                        manuallyAdjust.setManuallyVisible(0, 4, null);
                    }
                    ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).expandShineBottomMenu(componentRunningShine);
                    if (miBeautyProtocol != null) {
                        miBeautyProtocol.show();
                        return;
                    }
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(2);
                        return;
                    }
                    return;
                } else if (miBeautyProtocol != null) {
                    miBeautyProtocol.dismiss(2);
                    return;
                } else {
                    return;
                }
            } else {
                closeVideoFast();
                z = true;
            }
            setTipsState(FragmentTopAlert.TIP_VIDEO_BEAUTIFY, true);
            if (!isFaceBeautyOn) {
                mutexBeautyBusiness(moduleIndex);
            } else {
                z = true;
                z3 = false;
            }
            if (z3) {
                componentRunningShine.setTargetShow(true);
            }
            if (componentRunningShine.isTopBeautyEntry()) {
                singeSwitchVideoBeauty(!isFaceBeautyOn);
            } else {
                componentRunningShine.setVideoShineForceOn(162, !isFaceBeautyOn);
            }
            if (z) {
                CameraSettings.setFaceBeautySmoothLevel(0);
                CameraSettings.setShaderEffect(0);
                CameraSettings.setVideoBokehRatio(0.0f);
                ShineHelper.onShineStateChanged();
                ShineHelper.onBeautyChanged();
                ShineHelper.onVideoBokehRatioChanged();
                ShineHelper.onVideoFilterChanged();
                if (moduleIndex == 162) {
                    reCheckVideoBeautyPipeline();
                } else {
                    changeMode(162);
                }
            } else {
                reCheckVideoBeautyPipeline();
            }
            if (componentRunningShine.isTopBeautyEntry()) {
                return;
            }
            if (!z3) {
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showSetting() {
        ActivityBase activityBase = this.mActivity;
        if (activityBase != null) {
            switchOffElementsSilent(216);
            Intent intent = new Intent();
            intent.setClass(activityBase, CameraPreferenceActivity.class);
            intent.putExtra("from_where", DataRepository.dataItemGlobal().getCurrentMode());
            int intentType = DataRepository.dataItemGlobal().getIntentType();
            intent.putExtra(CameraPreferenceFragment.INTENT_TYPE, intentType);
            if (intentType == 2) {
                intent.putExtra(CameraPreferenceFragment.INTENT_VIDEO_QUALITY, DataRepository.dataItemGlobal().getIntentVideoQuality());
            }
            intent.putExtra(":miui:starting_window_label", activityBase.getResources().getString(R.string.pref_camera_settings_category));
            if (activityBase.startFromKeyguard()) {
                intent.putExtra("StartActivityWhenLocked", true);
            }
            activityBase.getIntent().removeExtra(CameraIntentManager.EXTRAS_CAMERA_FACING);
            activityBase.startActivity(intent);
            activityBase.setJumpFlag(2);
            trackGotoSettings();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void showWatermarkSample(boolean z) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol == null) {
            return;
        }
        if (z) {
            findBestWatermarkItem(88);
        } else {
            mainContentProtocol.setWatermarkVisible(4);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void switchOffElementsSilent(int... iArr) {
        for (int i : iArr) {
            if (i == 203) {
                ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (actionProcessing != null && actionProcessing.isShowLightingView()) {
                    actionProcessing.showOrHideLightingView();
                }
            } else if (i == 209) {
                this.mRecordingClosedElements = DataRepository.dataItemRunning().getRecordingClosedElements();
                if (this.mRecordingClosedElements != null) {
                    restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL);
                }
                CameraSettings.switchOffUltraPixel();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        this.mActivity = null;
        ModeCoordinatorImpl.getInstance().detachProtocol(164, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ConfigChanges
    public void updateASDForWatermark() {
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent()) {
            baseModule.get().updatePreferenceInWorkThread(73);
        }
    }
}
