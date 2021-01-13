package com.android.camera.module.impl.component;

import b.c.a.c;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentManuallyET;
import com.android.camera.data.data.config.ComponentManuallyEV;
import com.android.camera.data.data.config.ComponentManuallyFocus;
import com.android.camera.data.data.config.ComponentManuallyISO;
import com.android.camera.data.data.config.ComponentManuallyWB;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.module.BaseModule;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import java.util.ArrayList;
import java.util.List;

public class ManuallyValueChangeImpl implements ModeProtocol.ManuallyValueChanged {
    private static final String TAG = "ManuallyValueChangeImpl";
    private ActivityBase mActivity;
    private BaseModule mCurrentModule = ((BaseModule) this.mActivity.getCurrentModule());

    private ManuallyValueChangeImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    public static ManuallyValueChangeImpl create(ActivityBase activityBase) {
        return new ManuallyValueChangeImpl(activityBase);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onBokehFNumberValueChanged(String str) {
        CameraSettings.writeFNumber(str);
        this.mCurrentModule.updatePreferenceInWorkThread(48);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onDualLensSwitch(ComponentManuallyDualLens componentManuallyDualLens, int i) {
        HybridZoomingSystem.clearZoomRatioHistory();
        String componentValue = componentManuallyDualLens.getComponentValue(i);
        String next = componentManuallyDualLens.next(componentValue, i);
        if (!(i == 167 || i == 180)) {
            componentValue = next;
        }
        if (i == 180 && "macro".equalsIgnoreCase(componentValue)) {
            DataRepository.dataItemRunning().getComponentRunningAiAudio().setComponentValue(i, "normal");
        }
        if (!ComponentManuallyDualLens.LENS_WIDE.equalsIgnoreCase(componentValue)) {
            CameraSettings.setProVideoLog(false);
        }
        componentManuallyDualLens.setComponentValue(i, componentValue);
        CameraSettings.setUltraWideConfig(i, ComponentManuallyDualLens.LENS_ULTRA.equalsIgnoreCase(componentValue));
        if ((!DataRepository.dataItemFeature().ak() || !ComponentManuallyDualLens.LENS_ULTRA_TELE.equalsIgnoreCase(componentValue)) && !ComponentManuallyDualLens.LENS_WIDE.equalsIgnoreCase(componentValue)) {
            CameraSettings.switchOffUltraPixel();
        }
        ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
        if ("macro".equalsIgnoreCase(componentValue)) {
            componentRunningMacroMode.setSwitchOn(i);
        } else {
            componentRunningMacroMode.setSwitchOff(i);
        }
        CameraStatUtils.trackLensChanged(componentValue, this.mCurrentModule.getModuleIndex());
        if (CameraSettings.isVideoQuality8KOpen(i)) {
            if (!ComponentConfigVideoQuality.is8KCamcorderSupported(ComponentManuallyDualLens.LENS_TELE.equals(componentValue) ? Camera2DataContainer.getInstance().getAuxCameraId() : ComponentManuallyDualLens.LENS_ULTRA.equals(componentValue) ? Camera2DataContainer.getInstance().getUltraWideCameraId() : "macro".equals(componentValue) ? Camera2DataContainer.getInstance().getStandaloneMacroCameraId() : ComponentManuallyDualLens.LENS_ULTRA_TELE.equals(componentValue) ? Camera2DataContainer.getInstance().getUltraTeleCameraId() : Camera2DataContainer.getInstance().getMainBackCameraId())) {
                CameraSettings.setVideoQuality8K(i, false);
            }
        }
        if (i != 180) {
            ComponentConfigVideoQuality componentConfigVideoQuality = DataRepository.dataItemConfig().getComponentConfigVideoQuality();
            componentConfigVideoQuality.setComponentValue(i, componentConfigVideoQuality.getDefaultValue(i));
        }
        this.mActivity.onModeSelected(StartControl.create(i).setResetType(5).setViewConfigType(2).setNeedBlurAnimation(true));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onDualLensZooming(boolean z) {
        BaseModule baseModule = this.mCurrentModule;
        if (baseModule.isAlive() && !CameraSettings.isZoomByCameraSwitchingSupported() && baseModule.getActualCameraId() == Camera2DataContainer.getInstance().getSATCameraId()) {
            baseModule.notifyZooming(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onDualZoomHappened(boolean z) {
        BaseModule baseModule = this.mCurrentModule;
        if (baseModule.isAlive() && !CameraSettings.isZoomByCameraSwitchingSupported() && baseModule.getActualCameraId() == Camera2DataContainer.getInstance().getSATCameraId()) {
            baseModule.notifyDualZoom(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onDualZoomValueChanged(float f, int i) {
        if (this.mCurrentModule.isAlive()) {
            this.mCurrentModule.onZoomRatioChanged(f, i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onETValueChanged(ComponentManuallyET componentManuallyET, String str, String str2) {
        ModeProtocol.TopAlert topAlert;
        CameraStatUtils.trackExposureTimeChanged(str2, this.mCurrentModule.getModuleIndex());
        if (this.mCurrentModule.getModuleIndex() == 167 && DataRepository.dataItemFeature().qi() && (("0".equals(str) || "0".equals(str2)) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null)) {
            topAlert.updateConfigItem(193);
        }
        this.mCurrentModule.updatePreferenceInWorkThread(16, 20, 30, 34, 10);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onEVValueChanged(ComponentManuallyEV componentManuallyEV, String str) {
        CameraStatUtils.trackEVChanged(str, this.mCurrentModule.getModuleIndex());
        ModeProtocol.EvChangedProtocol evChangedProtocol = (ModeProtocol.EvChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(169);
        int parseFloat = (int) (Float.parseFloat(str) / Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().getCurrentMode()).getExposureCompensationStep());
        if (evChangedProtocol != null) {
            evChangedProtocol.onEvChanged(parseFloat, 3);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onFocusValueChanged(ComponentManuallyFocus componentManuallyFocus, String str, String str2) {
        if (!CameraSettings.getMappingFocusMode(Integer.valueOf(str).intValue()).equals(CameraSettings.getMappingFocusMode(Integer.valueOf(str2).intValue()))) {
            CameraSettings.setFocusModeSwitching(true);
            boolean equals = str2.equals(componentManuallyFocus.getDefaultValue(167));
            if (c.Rn()) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                if (equals) {
                    mainContentProtocol.updateFocusMode("auto");
                } else {
                    mainContentProtocol.updateFocusMode("manual");
                }
            }
        }
        if (this.mCurrentModule.isIgnoreTouchEvent()) {
            this.mCurrentModule.enableCameraControls(true);
        }
        this.mCurrentModule.updatePreferenceInWorkThread(14);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onISOValueChanged(ComponentManuallyISO componentManuallyISO, String str, String str2) {
        ModeProtocol.TopAlert topAlert;
        CameraStatUtils.trackIsoChanged(str2, this.mCurrentModule.getModuleIndex());
        if (this.mCurrentModule.getModuleIndex() == 167 && DataRepository.dataItemFeature().qi() && (("0".equals(str) || "0".equals(str2)) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null)) {
            topAlert.updateConfigItem(193);
        }
        this.mCurrentModule.updatePreferenceInWorkThread(15, 10);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void onWBValueChanged(ComponentManuallyWB componentManuallyWB, String str, boolean z) {
        if (!z) {
            componentManuallyWB.getKey(167);
        }
        if (z) {
            str = "manual";
        }
        CameraStatUtils.trackAwbChanged(str, this.mCurrentModule.getModuleIndex());
        this.mCurrentModule.updatePreferenceInWorkThread(6);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(174, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void resetManuallyParameters(List<ComponentData> list) {
        if (!(list == null || list.size() == 0)) {
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                ComponentData componentData = list.get(i);
                if (componentData instanceof ComponentManuallyWB) {
                    arrayList.add(6);
                } else if (componentData instanceof ComponentManuallyISO) {
                    configChanges.restoreMutexFlash(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE);
                    arrayList.add(15);
                    arrayList.add(10);
                } else if (componentData instanceof ComponentManuallyET) {
                    configChanges.restoreMutexFlash(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE);
                    arrayList.add(16);
                    arrayList.add(30);
                    arrayList.add(34);
                    arrayList.add(20);
                    arrayList.add(10);
                } else if (componentData instanceof ComponentManuallyFocus) {
                    arrayList.add(14);
                } else if (componentData instanceof ComponentManuallyEV) {
                    ((ModeProtocol.EvChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(169)).resetEvValue();
                }
            }
            int[] iArr = new int[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
            }
            this.mCurrentModule.updatePreferenceInWorkThread((int[]) iArr.clone());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(174, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyValueChanged
    public void updateSATIsZooming(boolean z) {
        this.mCurrentModule.updateSATZooming(z);
    }
}
