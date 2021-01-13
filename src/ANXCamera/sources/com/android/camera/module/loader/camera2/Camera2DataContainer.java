package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraManager;
import android.os.SystemProperties;
import android.util.SparseArray;
import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.runing.ComponentRunningEisPro;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class Camera2DataContainer implements Camera2Compat {
    public static final int BOGUS_CAMERA_ID_BACK = 0;
    public static final int BOGUS_CAMERA_ID_FRONT = 1;
    private static final String TAG = "Camera2DataContainer";
    private static final Camera2DataContainer sInstance = new Camera2DataContainer();
    private Camera2CompatAdapter mCamera2CompatAdapter;

    protected Camera2DataContainer() {
        if (c.vn()) {
            this.mCamera2CompatAdapter = new Camera2CompatAdapterRole();
        } else {
            this.mCamera2CompatAdapter = new Camera2CompatAdapterCommon();
        }
    }

    public static Camera2DataContainer getInstance() {
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init((CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera"));
            }
        }
        return sInstance;
    }

    public static Camera2DataContainer getInstance(CameraManager cameraManager) {
        synchronized (sInstance) {
            if (!sInstance.isInitialized()) {
                sInstance.init(cameraManager);
            }
        }
        return sInstance;
    }

    private int getVideoModeCameraId(int i, int i2) {
        float f = HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(i2, "1.0"), 1.0f);
        if (CameraSettings.isVideoQuality8KOpen(i2)) {
            return (i2 != 162 || !DataRepository.dataItemFeature().pk() || !ComponentConfigVideoQuality.is8KCamcorderSupported(getInstance().getUltraTeleCameraId())) ? getMainBackCameraId() : f < HybridZoomingSystem.getUltraTeleMinZoomRatio() ? getMainBackCameraId() : getUltraTeleCameraId();
        }
        if (!CameraSettings.isDualCameraSatEnable() || !DataRepository.dataItemFeature().qm()) {
            return i;
        }
        DataRepository.dataItemConfig().getComponentConfigVideoQuality().getComponentValue(i2);
        return !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i2) ? getMainBackCameraId() : getVideoSATCameraId();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public synchronized int getActualOpenCameraId(int i, int i2) {
        int i3;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getActualOpenCameraId(): #init() failed.");
            return i;
        }
        if (i == 0) {
            if (!(CameraSettings.isDualCameraEnable() && (CameraSettings.isSupportedOpticalZoom() || CameraSettings.isSupportedPortrait()) && !DataRepository.dataItemGlobal().isForceMainBackCamera()) && i2 != 167) {
                return i;
            }
            if (DataRepository.dataItemGlobal().isNormalIntent() || !this.mCamera2CompatAdapter.getCapabilities().get(getMainBackCameraId()).isSupportLightTripartite()) {
                if (i2 != 169) {
                    if (!(i2 == 186 || i2 == 188)) {
                        if (i2 != 179) {
                            if (i2 != 180) {
                                if (i2 != 182) {
                                    if (i2 != 183) {
                                        if (i2 != 204) {
                                            if (i2 != 205) {
                                                switch (i2) {
                                                    case 161:
                                                    case 162:
                                                        break;
                                                    case 163:
                                                        break;
                                                    default:
                                                        switch (i2) {
                                                            case 165:
                                                                break;
                                                            case 166:
                                                            case 167:
                                                                break;
                                                            default:
                                                                switch (i2) {
                                                                    case 171:
                                                                        if (!DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled") || getUltraWideBokehCameraId() == -1) {
                                                                            if (getBokehCameraId() == -1) {
                                                                                i3 = getSATCameraId();
                                                                                break;
                                                                            } else {
                                                                                i3 = getBokehCameraId();
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            i3 = getUltraWideBokehCameraId();
                                                                            break;
                                                                        }
                                                                        break;
                                                                    case 172:
                                                                        if (DataRepository.dataItemFeature().gk()) {
                                                                            i3 = getUltraWideCameraId();
                                                                            break;
                                                                        }
                                                                        break;
                                                                    case 173:
                                                                        if (CameraSettings.isSuperNightUWOpen(i2)) {
                                                                            if (!CameraSettings.isUltraWideConfigOpen(i2)) {
                                                                                if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                                                                    float f = HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(i2, "1.0"), 1.0f);
                                                                                    String str = TAG;
                                                                                    Log.d(str, "Currently user selected zoom ratio is " + f);
                                                                                    if (f >= 1.0f) {
                                                                                        i3 = getMainBackCameraId();
                                                                                        break;
                                                                                    } else {
                                                                                        i3 = getUltraWideCameraId();
                                                                                        break;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                i3 = getUltraWideCameraId();
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            i3 = getMainBackCameraId();
                                                                            break;
                                                                        }
                                                                        break;
                                                                }
                                                        }
                                                }
                                            }
                                        } else {
                                            i3 = DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(100).intValue();
                                        }
                                    }
                                }
                                if (!CameraSettings.isZoomByCameraSwitchingSupported() || CameraSettings.isSRTo108mModeOn()) {
                                    i3 = getMainBackCameraId();
                                } else {
                                    String cameraLensType = CameraSettings.getCameraLensType(i2);
                                    if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                                        i3 = getMainBackCameraId();
                                    } else if (ComponentManuallyDualLens.LENS_ULTRA_TELE.equals(cameraLensType)) {
                                        i3 = getUltraTeleCameraId();
                                    }
                                }
                            }
                            if (CameraSettings.isZoomByCameraSwitchingSupported()) {
                                String cameraLensType2 = CameraSettings.getCameraLensType(i2);
                                if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType2)) {
                                    i3 = getMainBackCameraId();
                                } else if (ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType2)) {
                                    i3 = getAuxCameraId();
                                } else if (ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType2)) {
                                    i3 = getUltraWideCameraId();
                                } else if ("macro".equals(cameraLensType2)) {
                                    i3 = getStandaloneMacroCameraId();
                                } else if (ComponentManuallyDualLens.LENS_ULTRA_TELE.equals(cameraLensType2)) {
                                    i3 = getUltraTeleCameraId();
                                }
                            }
                        } else {
                            i3 = DataRepository.dataItemFeature().I() ? getUltraWideCameraId() : getMainBackCameraId();
                        }
                        Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                        return i3;
                    }
                    if (CameraSettings.isMacroModeEnabled(i2)) {
                        i3 = DataRepository.dataItemFeature().fm() ? getStandaloneMacroCameraId() : getUltraWideCameraId();
                    } else {
                        i3 = CameraSettings.isUltraPixelOn() ? getMainBackCameraId() : (!CameraSettings.isDualCameraSatEnable() || CameraSettings.isFakePartSAT() || !c.isSupportedOpticalZoom()) ? CameraSettings.isUltraWideConfigOpen(i2) ? getUltraWideCameraId() : getMainBackCameraId() : CameraSettings.isUltraWideConfigOpen(i2) ? getUltraWideCameraId() : getSATCameraId();
                        if ((!CameraSettings.isSupportedOpticalZoom() || CameraSettings.isFakePartSAT()) && HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isUltraPixelOn()) {
                            float f2 = HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(i2, "1.0"), 1.0f);
                            String str2 = TAG;
                            Log.d(str2, "Currently user selected zoom ratio is " + f2);
                            if (f2 < 1.0f) {
                                i3 = getUltraWideCameraId();
                            } else if (CameraSettings.isFakePartSAT()) {
                                i3 = getSATCameraId();
                            }
                        }
                    }
                    Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                    return i3;
                }
                if (CameraSettings.isMacroModeEnabled(i2)) {
                    i3 = (!DataRepository.dataItemFeature().fm() || c.Zg) ? getUltraWideCameraId() : getStandaloneMacroCameraId();
                } else if (CameraSettings.isAutoZoomEnabled(i2)) {
                    i3 = getUltraWideCameraId();
                } else if (CameraSettings.isVideoQuality8KOpen(i2)) {
                    i3 = getVideoModeCameraId(i, i2);
                } else if (CameraSettings.isSuperEISEnabled(i2)) {
                    i3 = CameraSettings.getSuperEISProValue(i2).equals(ComponentRunningEisPro.EIS_VALUE_PRO) ? getUltraWideCameraId() : CameraSettings.getSuperEISProValue(i2).equals("normal") ? getMainBackCameraId() : DataRepository.dataItemFeature().Vi() ? getMainBackCameraId() : getUltraWideCameraId();
                } else if (CameraSettings.isUltraWideConfigOpen(i2)) {
                    i3 = getUltraWideCameraId();
                } else {
                    if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                        float f3 = HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(i2, "1.0"), 1.0f);
                        String str3 = TAG;
                        Log.d(str3, "Currently user selected zoom ratio is " + f3);
                        if (f3 < 1.0f && !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i2)) {
                            i3 = getUltraWideCameraId();
                        } else if (DataRepository.dataItemFeature().tk() && f3 >= HybridZoomingSystem.getTeleMinZoomRatio() && !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i2)) {
                            i3 = getAuxCameraId();
                            if (!DataRepository.dataItemConfig().getComponentConfigVideoQuality().isSupportVideoQuality(i2, i3, true)) {
                                i3 = getMainBackCameraId();
                            }
                        } else if (DataRepository.dataItemFeature().uk() && f3 >= HybridZoomingSystem.getUltraTeleMinZoomRatio() && !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i2)) {
                            i3 = getUltraTeleCameraId();
                            if (!DataRepository.dataItemConfig().getComponentConfigVideoQuality().isSupportVideoQuality(i2, i3, true)) {
                                i3 = getMainBackCameraId();
                            }
                        }
                    }
                    i3 = getVideoModeCameraId(i, i2);
                }
                Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                return i3;
            }
            return getMainBackCameraId();
        } else if (i == 1) {
            if (i2 == 161 || i2 == 162) {
                if (CameraSettings.isVideoBokehOn() && getBokehFrontCameraId() != -1) {
                    i3 = getBokehFrontCameraId();
                    Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                    return i3;
                }
            } else if (i2 == 171 && getBokehFrontCameraId() != -1) {
                boolean isIntentAction = DataRepository.dataItemGlobal().isIntentAction();
                if (!c.Xm() || isIntentAction) {
                    i3 = getBokehFrontCameraId();
                    Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
                    return i3;
                }
            }
        }
        i3 = i;
        Log.d(TAG, String.format(Locale.US, "getActualOpenCameraId: mode=%x, id=%d->%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3)));
        return i3;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getAuxCameraId() {
        return this.mCamera2CompatAdapter.getAuxCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getAuxFrontCameraId() {
        return this.mCamera2CompatAdapter.getAuxFrontCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getBokehCameraId() {
        return this.mCamera2CompatAdapter.getBokehCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getBokehFrontCameraId() {
        return this.mCamera2CompatAdapter.getBokehFrontCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public SparseArray<CameraCapabilities> getCapabilities() {
        return this.mCamera2CompatAdapter.getCapabilities();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized CameraCapabilities getCapabilities(int i) {
        return this.mCamera2CompatAdapter.getCapabilities(i);
    }

    public synchronized CameraCapabilities getCapabilitiesByBogusCameraId(int i, int i2) {
        return getCapabilities(getActualOpenCameraId(i, i2));
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized CameraCapabilities getCurrentCameraCapabilities() {
        return this.mCamera2CompatAdapter.getCurrentCameraCapabilities();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public ConcurrentHashMap getDefaultDualVideoCameraIds() {
        int i = SystemProperties.getInt("camera.dualvideo.firstid", -1);
        int i2 = SystemProperties.getInt("camera.dualvideo.secondid", -1);
        if (i == -1 || i2 == -1 || i == i2) {
            return this.mCamera2CompatAdapter.getDefaultDualVideoCameraIds();
        }
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put(100, Integer.valueOf(i));
        concurrentHashMap.put(101, Integer.valueOf(i2));
        return concurrentHashMap;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getFrontCameraId() {
        return this.mCamera2CompatAdapter.getFrontCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getMainBackCameraId() {
        return this.mCamera2CompatAdapter.getMainBackCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public int getParallelVirtualCameraId() {
        return this.mCamera2CompatAdapter.getParallelVirtualCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getRoleIdByActualId(int i) {
        return this.mCamera2CompatAdapter.getRoleIdByActualId(i);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getSATCameraId() {
        return this.mCamera2CompatAdapter.getSATCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getSATFrontCameraId() {
        return this.mCamera2CompatAdapter.getSATFrontCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getStandaloneMacroCameraId() {
        return this.mCamera2CompatAdapter.getStandaloneMacroCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getUltraTeleCameraId() {
        return this.mCamera2CompatAdapter.getUltraTeleCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getUltraWideBokehCameraId() {
        return this.mCamera2CompatAdapter.getUltraWideBokehCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getUltraWideCameraId() {
        return this.mCamera2CompatAdapter.getUltraWideCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getVideoSATCameraId() {
        return this.mCamera2CompatAdapter.getVideoSATCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public int getVirtualBackCameraId() {
        return this.mCamera2CompatAdapter.getVirtualBackCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public int getVirtualFrontCameraId() {
        return this.mCamera2CompatAdapter.getVirtualFrontCameraId();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized boolean hasBokehCamera() {
        return this.mCamera2CompatAdapter.hasBokehCamera();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public boolean hasPortraitCamera() {
        return this.mCamera2CompatAdapter.hasPortraitCamera();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized boolean hasSATCamera() {
        return this.mCamera2CompatAdapter.hasSATCamera();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public boolean hasTeleCamera() {
        return this.mCamera2CompatAdapter.hasTeleCamera();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public void init(CameraManager cameraManager) {
        this.mCamera2CompatAdapter.init(cameraManager);
    }

    public synchronized boolean isFrontCameraId(int i) {
        CameraCapabilities capabilities = getCapabilities(i);
        boolean z = false;
        if (capabilities == null) {
            Log.d(TAG, "Warning: isFrontCameraId(): #init() failed.");
            return false;
        }
        if (capabilities.getFacing() == 0) {
            z = true;
        }
        return z;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public boolean isInitialized() {
        return this.mCamera2CompatAdapter.isInitialized();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized boolean isPartSAT() {
        return this.mCamera2CompatAdapter.isPartSAT();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized void reset() {
        this.mCamera2CompatAdapter.reset();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized void setCurrentOpenedCameraId(int i) {
        this.mCamera2CompatAdapter.setCurrentOpenedCameraId(i);
    }
}
