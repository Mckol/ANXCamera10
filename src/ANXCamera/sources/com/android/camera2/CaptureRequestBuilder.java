package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.util.Range;
import android.util.Rational;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.Util;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;

public class CaptureRequestBuilder {
    private static final long MAX_REALTIME_EXPOSURE_TIME = 125000000;
    private static final String TAG = "CaptureRequestBuilder";

    static void applyAELock(CaptureRequest.Builder builder, boolean z) {
        if (builder != null) {
            String str = TAG;
            Log.d(str, "applyAELock: " + z);
            builder.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.valueOf(z));
        }
    }

    static void applyAERegions(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MeteringRectangle[] aERegions = cameraConfigs.getAERegions();
            if (aERegions != null) {
                builder.set(CaptureRequest.CONTROL_AE_REGIONS, aERegions);
            } else {
                builder.set(CaptureRequest.CONTROL_AE_REGIONS, MiCamera2.ZERO_WEIGHT_3A_REGION);
            }
        }
    }

    static void applyAFRegions(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MeteringRectangle[] aFRegions = cameraConfigs.getAFRegions();
            if (aFRegions != null) {
                builder.set(CaptureRequest.CONTROL_AF_REGIONS, aFRegions);
            } else {
                builder.set(CaptureRequest.CONTROL_AF_REGIONS, MiCamera2.ZERO_WEIGHT_3A_REGION);
            }
        }
    }

    public static void applyASDEnable(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.ASD_ENABLE.getName())) {
            MiCameraCompat.applyASDEnable(builder, cameraConfigs.isASDEnabled());
        }
    }

    public static void applyASDScene(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null) {
            if (!cameraCapabilities.isASDSceneSupported()) {
                Log.d(TAG, "applyASDScene(): unsupported");
                return;
            }
            int aSDScene = cameraConfigs.getASDScene();
            String str = TAG;
            Log.d(str, "applyASDScene: " + aSDScene);
            MiCameraCompat.applyASDScene(builder, aSDScene);
        }
    }

    static void applyAWBLock(CaptureRequest.Builder builder, boolean z) {
        if (builder != null) {
            String str = TAG;
            Log.d(str, "applyAWBLock: " + z);
            builder.set(CaptureRequest.CONTROL_AWB_LOCK, Boolean.valueOf(z));
        }
    }

    static void applyAWBMode(CaptureRequest.Builder builder, int i) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(i));
        }
    }

    static void applyAiAIIEPreviewEnable(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            if (cameraCapabilities == null || !cameraCapabilities.isTagDefined(CaptureRequestVendorTags.AI_AIIE_PREVIEWENABLED.getName())) {
                Log.e(TAG, "is tag defined:false");
            } else {
                MiCameraCompat.applyAiAIIEPreviewEnable(builder, cameraConfigs.isAIIEPreviewEnabled());
            }
        }
    }

    static void applyAiASDEnable(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            boolean isAiASDEnabled = cameraConfigs.isAiASDEnabled();
            String str = TAG;
            Log.d(str, "applyAiASDEnable:" + isAiASDEnabled);
            MiCameraCompat.applyAiASDEnable(builder, isAiASDEnabled);
        }
    }

    static void applyAiMoonEffectEnable(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            if (!cameraCapabilities.isAiMoonEffectEnableSupported()) {
                Log.e(TAG, "applyAiMoonEffectEnable: is not Support");
                return;
            }
            boolean isAiMoonEffectEnabled = cameraConfigs.isAiMoonEffectEnabled();
            String str = TAG;
            Log.d(str, "applyAiMoonEffectEnable:" + isAiMoonEffectEnabled);
            MiCameraCompat.applyAiMoonEffectEnable(builder, isAiMoonEffectEnabled);
        }
    }

    static void applyAiSceneDetectPeriod(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyAiScenePeriod(builder, cameraConfigs.getAiSceneDetectPeriod());
        }
    }

    public static void applyAiShutterEnable(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportAiShutter()) {
            boolean isAiShutterEnable = cameraConfigs.isAiShutterEnable();
            String str = TAG;
            Log.d(str, "applyAiShutterEnable: " + isAiShutterEnable);
            MiCameraCompat.applyAiShutterEnable(builder, isAiShutterEnable);
        }
    }

    public static void applyAiShutterExistMotion(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 3 && cameraCapabilities.isSupportAiShutter()) {
            boolean isAiShutterEnable = cameraConfigs.isAiShutterEnable();
            String str = TAG;
            Log.d(str, "applyAiShutterExistMotion.isAiShutterEnable: " + isAiShutterEnable);
            if (isAiShutterEnable) {
                boolean isAiShutterExistMotion = cameraConfigs.isAiShutterExistMotion();
                String str2 = TAG;
                Log.d(str2, "applyAiShutterExistMotion.isAiShutterExistMotion: " + isAiShutterExistMotion);
                MiCameraCompat.applyAiShutterExistMotion(builder, isAiShutterExistMotion);
            }
        }
    }

    public static void applyAmbilightAeTarget(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.AMBILIGHT_AE_TARGET.getName()) && cameraConfigs.getAmbilightMode() != 0) {
            int i = cameraConfigs.getmAmbilightAeTarget();
            String str = TAG;
            Log.d(str, "applyAmbilightAeTarget: " + i);
            MiCameraCompat.applyAmbilightAeTarget(builder, i);
        }
    }

    public static void applyAmbilightMode(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.AMBILIGHT_MODE.getName())) {
            int ambilightMode = cameraConfigs.getAmbilightMode();
            String str = TAG;
            Log.d(str, "applyAmbilightMode: " + ambilightMode);
            MiCameraCompat.applyAmbilightMode(builder, ambilightMode);
        }
    }

    static void applyAntiBanding(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        int antiBanding;
        if (builder != null && (antiBanding = cameraConfigs.getAntiBanding()) != -1) {
            builder.set(CaptureRequest.CONTROL_AE_ANTIBANDING_MODE, Integer.valueOf(antiBanding));
        }
    }

    static void applyAntiShake(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            boolean isEISEnabled = cameraConfigs.isEISEnabled();
            boolean isOISEnabled = cameraConfigs.isOISEnabled();
            if (!isEISEnabled || !isOISEnabled || !Util.isDebugOsBuild()) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("EIS: ");
                String str2 = "on";
                sb.append(isEISEnabled ? str2 : "off");
                Log.v(str, sb.toString());
                builder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, Integer.valueOf(isEISEnabled ? 1 : 0));
                if (cameraCapabilities.isSupportOIS()) {
                    String str3 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("OIS: ");
                    if (isEISEnabled || !isOISEnabled) {
                        str2 = "off";
                    }
                    sb2.append(str2);
                    Log.v(str3, sb2.toString());
                    builder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, Integer.valueOf((isEISEnabled || !isOISEnabled) ? 0 : 1));
                    return;
                }
                return;
            }
            throw new RuntimeException("EIS&OIS are both on");
        }
    }

    public static void applyAsdDirtyEnable(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.ASD_DIRTY_ENABLE.getName())) {
            MiCameraCompat.applyAsdDirtyEnable(builder, cameraConfigs.isAsdDirtyEnable());
        }
    }

    static void applyAutoZoomMode(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyAutoZoomMode(builder, cameraConfigs.getAutoZoomMode());
        }
    }

    static void applyAutoZoomScaleOffset(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyAutoZoomScaleOffset(builder, cameraConfigs.getAutoZoomScaleOffset());
        }
    }

    public static void applyBackSoftLight(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, boolean z) {
        if (builder != null) {
            if (!cameraCapabilities.isBackSoftLightSupported()) {
                Log.d(TAG, "applyBackSoftLight(): unsupported");
                return;
            }
            String str = TAG;
            Log.d(str, "applyBackSoftLight(): " + z);
            MiCameraCompat.applyBackSoftLight(builder, z ? (byte) 1 : 0);
        }
    }

    public static void applyBackwardCaptureHint(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, boolean z) {
        if (builder != null) {
            if (!cameraCapabilities.isBackwardCaptureSupported()) {
                Log.d(TAG, "applyBackwardCaptureHint(): unsupported");
                return;
            }
            String str = TAG;
            Log.d(str, "applyBackwardCaptureHint(): " + z);
            MiCameraCompat.applyBackwardCaptureHint(builder, z ? (byte) 1 : 0);
        }
    }

    static void applyBeautyValues(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportBeauty() && cameraConfigs.getBeautyValues() != null) {
            MiCameraCompat.applyBeautyParameter(builder, cameraCapabilities.getCaptureRequestVendorKeys(), cameraConfigs.getBeautyValues());
        }
    }

    static void applyCameraAi30Enable(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportCameraAi30()) {
            MiCameraCompat.applyCameraAi30Enable(builder, cameraConfigs.isCameraAi30Enabled());
        }
    }

    static void applyCinematicPhoto(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        applyCinematicPhoto(builder, i, cameraCapabilities, cameraConfigs.isCinematicPhotoEnabled());
    }

    static void applyCinematicPhoto(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, boolean z) {
        if (builder != null && cameraCapabilities.isCinematicPhotoSupported() && i == 3) {
            String str = TAG;
            Log.d(str, "applyCinematicPhoto: " + z);
            MiCameraCompat.applyCinematicPhoto(builder, z ? (byte) 1 : 0);
        }
    }

    public static void applyCinematicVideo(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isCinematicVideoSupported()) {
            boolean isCinematicVideoEnabled = cameraConfigs.isCinematicVideoEnabled();
            String str = TAG;
            Log.d(str, "applyCinematicVideo: " + isCinematicVideoEnabled);
            MiCameraCompat.applyCinematicVideo(builder, isCinematicVideoEnabled ? (byte) 1 : 0);
        }
    }

    static void applyColorEffect(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        int colorEffect;
        if (builder != null && (colorEffect = cameraConfigs.getColorEffect()) != -1) {
            builder.set(CaptureRequest.CONTROL_EFFECT_MODE, Integer.valueOf(colorEffect));
        }
    }

    public static void applyColorEnhance(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.COLOR_ENHANCE_ENABLED.getName())) {
            MiCameraCompat.applyColorEnhanceEnable(builder, cameraConfigs.getColorEnhanceEnabled());
        }
    }

    static void applyColorRetentionBack(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.getFacing() != 0 && cameraCapabilities.isSupportColorRetentionBackRequestTag()) {
            MiCameraCompat.applyVideoFilterColorRetentionBack(builder, cameraConfigs.getVideoFilterColorRetentionBack());
        }
    }

    static void applyColorRetentionFront(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.getFacing() == 0 && cameraCapabilities.isSupportColorRetentionFrontRequestTag()) {
            MiCameraCompat.applyVideoFilterColorRetentionFront(builder, cameraConfigs.getVideoFilterColorRetentionFront());
        }
    }

    static void applyContrast(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        int contrastLevel;
        if (builder != null && cameraCapabilities.isSupportContrast() && (contrastLevel = cameraConfigs.getContrastLevel()) != -1) {
            MiCameraCompat.applyContrast(builder, contrastLevel);
        }
    }

    static void applyCustomAWB(CaptureRequest.Builder builder, int i) {
        MiCameraCompat.applyCustomAWB(builder, i);
    }

    static void applyDepurpleEnable(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            boolean isDodepurpleEnabled = cameraConfigs.isDodepurpleEnabled();
            if (i == 4) {
                isDodepurpleEnabled = false;
            }
            if (!cameraCapabilities.isSupportDepurple()) {
                Log.d(TAG, "applyDepurpleEnable: is not Support  ");
                return;
            }
            String str = TAG;
            Log.d(str, "applyDepurpleEnable: dodepurpleEnabled = " + isDodepurpleEnabled);
            MiCameraCompat.applyDepurpleEnable(builder, isDodepurpleEnabled);
        }
    }

    static void applyDeviceOrientation(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportDeviceOrientation()) {
            String str = TAG;
            Log.d(str, "applyDeviceOrientation: " + cameraConfigs.getDeviceOrientation());
            MiCameraCompat.applyDeviceOrientation(builder, cameraConfigs.getDeviceOrientation());
        }
    }

    static void applyDualBokeh(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportDualBokeh()) {
            String str = TAG;
            Log.d(str, "applyDualBokeh: " + cameraConfigs.isDualBokehEnabled());
            MiCameraCompat.applyDualBokehEnable(builder, cameraConfigs.isDualBokehEnabled());
        }
    }

    static void applyExposureCompensation(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            int exposureCompensationIndex = cameraConfigs.getExposureCompensationIndex();
            if (c.mo() && ModuleManager.isProModule() && i == 1 && cameraConfigs.getISO() == 0 && cameraConfigs.getExposureTime() > MAX_REALTIME_EXPOSURE_TIME) {
                double log = Math.log((double) ((float) (((double) cameraConfigs.getExposureTime()) / 1.25E8d))) / Math.log(2.0d);
                String str = TAG;
                Log.d(str, "applyExposureCompensation: EV = " + log);
                Rational exposureCompensationRational = cameraCapabilities.getExposureCompensationRational();
                exposureCompensationIndex = Math.min((int) ((log * ((double) exposureCompensationRational.getDenominator())) / ((double) exposureCompensationRational.getNumerator())), cameraCapabilities.getMaxExposureCompensation());
            }
            String str2 = TAG;
            Log.d(str2, "applyExposureCompensation: " + exposureCompensationIndex);
            builder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(exposureCompensationIndex));
        }
    }

    static void applyExposureMeteringMode(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        int exposureMeteringMode;
        if (builder != null && (exposureMeteringMode = cameraConfigs.getExposureMeteringMode()) != -1) {
            MiCameraCompat.applyExposureMeteringMode(builder, exposureMeteringMode);
        }
    }

    static void applyExposureTime(CaptureRequest.Builder builder, int i, CameraConfigs cameraConfigs) {
        if (builder != null) {
            long exposureTime = cameraConfigs.getExposureTime();
            if (c.mo() && i == 1) {
                exposureTime = Math.min(exposureTime, (long) MAX_REALTIME_EXPOSURE_TIME);
            }
            if (c.mo() || i == 3) {
                String str = TAG;
                Log.d(str, "applyExposureTime: " + exposureTime);
                MiCameraCompat.applyExposureTime(builder, exposureTime);
            }
        }
    }

    static void applyEyeLight(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportEyeLight()) {
            int eyeLightType = cameraConfigs.getEyeLightType();
            if (eyeLightType < 0) {
                MiCameraCompat.applyEyeLight(builder, 0, 0);
            } else {
                MiCameraCompat.applyEyeLight(builder, eyeLightType, 100);
            }
        }
    }

    static void applyFNumber(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            if (!cameraCapabilities.isSupportBokehAdjust()) {
                Log.d(TAG, "set f number on unsupported devices");
            } else if (cameraConfigs.getFNumber() != null) {
                MiCameraCompat.applyFNumber(builder, cameraConfigs.getFNumber());
            }
        }
    }

    static void applyFaceAgeAnalyze(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportFaceAgeAnalyze()) {
            MiCameraCompat.applyFaceAgeAnalyzeEnable(builder, cameraConfigs.isFaceAgeAnalyzeEnabled());
        }
    }

    static void applyFaceDetection(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            MiCameraCompat.applyFaceDetection(builder, cameraConfigs.isFaceDetectionEnabled());
        }
    }

    static void applyFaceScore(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportFaceScore()) {
            MiCameraCompat.applyFaceScoreEnable(builder, cameraConfigs.isFaceScoreEnabled());
        }
    }

    public static void applyFlashCurrent(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.FLASH_CURRENT.getName())) {
            MiCameraCompat.applyFlashCurrent(builder, cameraConfigs.getFlashCurrent());
        }
    }

    public static void applyFlashMode(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (DataRepository.dataItemFeature().am() && builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.FLASH_MODE.getName())) {
            MiCameraCompat.applyFlashMode(builder, cameraConfigs.getFlashMode());
        }
    }

    public static void applyFlawDetectEnable(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, boolean z) {
        if (builder != null && cameraCapabilities != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.FLAW_DETECT_ENABLE.getName())) {
            MiCameraCompat.applyFlawDetectEnable(builder, z);
        }
    }

    static void applyFocusDistance(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs.getFocusMode() == 0) {
            float focusDistance = cameraConfigs.getFocusDistance();
            if (focusDistance > -1.0f) {
                builder.set(CaptureRequest.LENS_FOCUS_DISTANCE, Float.valueOf(focusDistance));
            }
        }
    }

    static void applyFocusMode(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(cameraConfigs.getFocusMode()));
            applyAFRegions(builder, cameraConfigs);
            applyAERegions(builder, cameraConfigs);
        }
    }

    static void applyFpsRange(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            Range<Integer> previewFpsRange = cameraConfigs.getPreviewFpsRange();
            String str = TAG;
            Log.d(str, "applyFpsRange: fpsRange = " + previewFpsRange);
            if (previewFpsRange != null) {
                builder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, previewFpsRange);
            }
        }
    }

    static void applyFrontMirror(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportFrontMirror() && i == 3) {
            String str = TAG;
            Log.d(str, "applyFrontMirror: " + cameraConfigs.isFrontMirror());
            MiCameraCompat.applyFrontMirror(builder, cameraConfigs.isFrontMirror());
        }
    }

    static void applyHDR(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder == null || !cameraCapabilities.isSupportHdr()) {
            return;
        }
        if (i != 3) {
            MiCameraCompat.applyHDR(builder, false);
            return;
        }
        String str = TAG;
        Log.d(str, "applyHDR:" + cameraConfigs.isHDREnabled());
        MiCameraCompat.applyHDR(builder, cameraConfigs.isHDREnabled());
    }

    public static void applyHDR10Video(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.HDR10_VIDEO.getName())) {
            MiCameraCompat.applyHDR10Video(builder, cameraConfigs.getHDR10Video());
        }
    }

    static void applyHDRCheckerEnable(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 1 && cameraCapabilities.isSupportAutoHdr()) {
            MiCameraCompat.applyHDRCheckerEnable(builder, cameraConfigs.isHDRCheckerEnabled());
        }
    }

    static void applyHDRCheckerStatus(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 1 && cameraCapabilities.isSupportHdrCheckerStatus()) {
            MiCameraCompat.applyHDRCheckerStatus(builder, cameraConfigs.getHDRCheckerStatus());
        }
    }

    static void applyHDRMode(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 1 && cameraCapabilities.isSupportHdrMode()) {
            MiCameraCompat.applyHDRMode(builder, cameraConfigs.getHDRMode());
        }
    }

    public static void applyHFRDeflicker(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraCapabilities.isSupportHFRDeflicker()) {
            boolean isHFRDeflicker = cameraConfigs.isHFRDeflicker();
            String str = TAG;
            Log.d(str, "applyHFRDeflicker: " + isHFRDeflicker);
            MiCameraCompat.applyHFRDeflicker(builder, isHFRDeflicker);
        }
    }

    static void applyHHT(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && i == 3 && cameraCapabilities.isSupportHHT()) {
            MiCameraCompat.applyHHT(builder, cameraConfigs.isHHTEnabled());
        }
    }

    public static void applyHighQualityPreferred(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportHighQualityPreferred()) {
            boolean isHighQualityPreferred = cameraConfigs.isHighQualityPreferred();
            String str = TAG;
            Log.d(str, "applyHighQualityPreferred: " + isHighQualityPreferred);
            MiCameraCompat.applyHighQualityPreferred(builder, isHighQualityPreferred);
        }
    }

    public static void applyHistogramStats(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isHistogramStatsSupported()) {
            boolean isHistogramStatsEnabled = cameraConfigs.isHistogramStatsEnabled();
            String str = TAG;
            Log.d(str, "applyHistogramStats: " + isHistogramStatsEnabled);
            MiCameraCompat.applyHistogramStats(builder, isHistogramStatsEnabled ? (byte) 1 : 0);
        }
    }

    static void applyHwMfnr(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        int Hh;
        if (builder == null || !cameraCapabilities.isSupportMfnr()) {
            return;
        }
        if (i != 3) {
            MiCameraCompat.applyMfnrEnable(builder, false);
            return;
        }
        MiCameraCompat.applyMfnrEnable(builder, cameraConfigs.isMfnrEnabled());
        if (cameraConfigs.isMfnrEnabled() && !CameraSettings.isHighQualityPreferred() && (Hh = DataRepository.dataItemFeature().Hh()) > 0) {
            String str = TAG;
            Log.d(str, "applyMfnrFrameNum: " + Hh);
            MiCameraCompat.applyMfnrFrameNum(builder, Hh);
        }
    }

    static void applyIso(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            int iso = cameraConfigs.getISO();
            if (c.mo() && i == 1 && iso > 0 && cameraConfigs.getExposureTime() > MAX_REALTIME_EXPOSURE_TIME) {
                iso = Math.min((int) (((float) iso) * ((float) (((double) cameraConfigs.getExposureTime()) / 1.25E8d))), cameraCapabilities.getMaxIso());
            }
            String str = TAG;
            Log.d(str, "applyIso: " + iso);
            MiCameraCompat.applyISO(builder, iso);
        }
    }

    static void applyLensDirtyDetect(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportLensDirtyDetect()) {
            MiCameraCompat.applyLensDirtyDetect(builder, cameraConfigs.isLensDirtyDetectEnabled());
        }
    }

    public static void applyMacroMode(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraCapabilities.isSupportMacroMode()) {
            boolean isMacroMode = cameraConfigs.isMacroMode();
            String str = TAG;
            Log.d(str, "applyMacroMode: " + isMacroMode);
            MiCameraCompat.applyMacroMode(builder, isMacroMode);
        }
    }

    public static void applyMtkPipDevices(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.MTK_STREAMING_FEATURE_PIP_DEVICES, cameraConfigs.getmMtkPipDevices());
    }

    private void applyNoiseReduction(CaptureRequest.Builder builder) {
        if (builder != null) {
            builder.set(CaptureRequest.NOISE_REDUCTION_MODE, 2);
        }
    }

    static void applyNormalWideLDC(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportNormalWideLDC()) {
            boolean normalWideLDCEnabled = cameraConfigs.getNormalWideLDCEnabled();
            if (i == 4) {
                normalWideLDCEnabled = false;
            }
            MiCameraCompat.applyNormalWideLDC(builder, normalWideLDCEnabled);
        }
    }

    public static void applyOnTripodModeStatus(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraConfigs.getOnTripodScenes() != null) {
            MiCameraCompat.applyOnTripodModeStatus(builder, cameraConfigs.getOnTripodScenes());
        }
    }

    static void applyPortraitLighting(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            if ((i == 3 || DataRepository.dataItemFeature().mh()) && cameraCapabilities.isSupportPortraitLighting()) {
                MiCameraCompat.applyPortraitLighting(builder, cameraConfigs.getPortraitLightingPattern());
            }
        }
    }

    static void applySATUltraWideLDC(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, boolean z) {
        if (builder != null && cameraCapabilities.supportSATUltraWideLDCEnable()) {
            MiCameraCompat.applySATUltraWideLDC(builder, z);
        }
    }

    public static void applySatFallback(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, boolean z) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.SAT_FALLBACK_ENABLE.getName())) {
            MiCameraCompat.applySatFallback(builder, z);
        }
    }

    public static void applySatFallbackDisable(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, boolean z) {
        if (builder == null) {
            Log.e(TAG, "applySatFallbackDisable: request null");
        } else if (!cameraCapabilities.isTagDefined(CaptureRequestVendorTags.SAT_FALLBACK_DISABLE.getName())) {
            Log.e(TAG, "applySatFallbackDisable: %s not defined", CaptureRequestVendorTags.SAT_FALLBACK_DISABLE.getName());
        } else {
            MiCameraCompat.applySatFallbackDisable(builder, z);
        }
    }

    public static void applySatIsZooming(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null) {
            MiCameraCompat.applySatIsZooming(builder, cameraConfigs.isSatIsZooming());
        }
    }

    static void applySaturation(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        int saturationLevel;
        if (builder != null && (saturationLevel = cameraConfigs.getSaturationLevel()) != -1) {
            MiCameraCompat.applySaturation(builder, saturationLevel);
        }
    }

    static void applySceneMode(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            int sceneMode = cameraConfigs.getSceneMode();
            if (!"-1".equals(String.valueOf(sceneMode))) {
                builder.set(CaptureRequest.CONTROL_SCENE_MODE, Integer.valueOf(sceneMode));
                builder.set(CaptureRequest.CONTROL_MODE, 2);
                return;
            }
            builder.set(CaptureRequest.CONTROL_MODE, 1);
        }
    }

    public static void applyScreenLightHint(CameraCapabilities cameraCapabilities, CaptureRequest.Builder builder, boolean z) {
        if (builder != null) {
            if (!cameraCapabilities.isScreenLightHintSupported()) {
                Log.d(TAG, "applyScreenLightHint(): unsupported");
                return;
            }
            String str = TAG;
            Log.d(str, "applyScreenLightHint(): " + z);
            MiCameraCompat.applyScreenLightHint(builder, z ? (byte) 1 : 0);
        }
    }

    public static void applySessionParameters(CaptureRequest.Builder builder, CaptureSessionConfigurations captureSessionConfigurations) {
        if (builder != null && captureSessionConfigurations != null) {
            captureSessionConfigurations.apply(builder);
        }
    }

    static void applySharpness(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        int sharpnessLevel;
        if (builder != null && (sharpnessLevel = cameraConfigs.getSharpnessLevel()) != -1) {
            MiCameraCompat.applySharpness(builder, sharpnessLevel);
        }
    }

    public static void applyShrinkMemoryMode(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, int i) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.SHRINK_MEMORY_MODE.getName())) {
            MiCameraCompat.applyShrinkMemoryMode(builder, i);
        }
    }

    static void applySingleBokeh(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportMiBokeh()) {
            String str = TAG;
            Log.d(str, "applySingleBokeh: " + cameraConfigs.isSingleBokehEnabled());
            MiCameraCompat.applySingleBokehEnable(builder, cameraConfigs.isSingleBokehEnabled());
        }
    }

    public static void applySmoothTransition(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, boolean z) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.ST_ENABLED.getName())) {
            MiCameraCompat.applySmoothTransition(builder, z);
        }
    }

    static void applySuperNightScene(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && i == 3 && cameraCapabilities.isSupportSuperNight()) {
            boolean isSuperNightEnabled = cameraConfigs.isSuperNightEnabled();
            if (cameraConfigs.getShotType() == 9) {
                isSuperNightEnabled = false;
            }
            String str = TAG;
            Log.d(str, "applySuperNightScene: " + isSuperNightEnabled);
            MiCameraCompat.applySuperNightScene(builder, isSuperNightEnabled);
            if (cameraCapabilities.isDebugInfoAsWatermarkSupported()) {
                boolean isDebugInfoAsWatermarkEnabled = CameraSettings.isDebugInfoAsWatermarkEnabled();
                String str2 = TAG;
                Log.d(str2, "show debug info as watermark: " + isDebugInfoAsWatermarkEnabled);
                VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.DEBUG_INFO_AS_WATERMARK, Boolean.valueOf(isDebugInfoAsWatermarkEnabled));
            }
        }
    }

    static void applySuperResolution(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportSuperResolution()) {
            boolean isSuperResolutionEnabled = cameraConfigs.isSuperResolutionEnabled();
            if (!c.isMTKPlatform()) {
                boolean ii = DataRepository.dataItemFeature().ii();
                String str = TAG;
                Log.d(str, "applySuperResolution: applySr2PreviewStream = " + ii);
                if (!ii || ModuleManager.isProPhotoModule()) {
                    isSuperResolutionEnabled &= i == 3;
                }
                String str2 = TAG;
                Log.d(str2, "applySuperResolution: " + isSuperResolutionEnabled + ", applyType = " + i);
                MiCameraCompat.applySuperResolution(builder, isSuperResolutionEnabled);
            } else if (i != 3) {
                String str3 = TAG;
                Log.d(str3, "applySuperResolution: ignored for applyType(" + i + ")");
            } else {
                String str4 = TAG;
                Log.d(str4, "applySuperResolution: " + isSuperResolutionEnabled + ", applyType = " + i);
                MiCameraCompat.applySuperResolution(builder, isSuperResolutionEnabled);
            }
        }
    }

    static void applySwMfnr(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder == null || !cameraCapabilities.isSupportSwMfnr()) {
            return;
        }
        if (i != 3) {
            MiCameraCompat.applySwMfnrEnable(builder, false);
            return;
        }
        String str = TAG;
        Log.d(str, "applySwMfnrEnable: " + cameraConfigs.isSwMfnrEnabled());
        MiCameraCompat.applySwMfnrEnable(builder, cameraConfigs.isSwMfnrEnabled());
    }

    static void applyTargetZoom(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportTargetZoom()) {
            float targetZoom = cameraConfigs.getTargetZoom();
            String str = TAG;
            Log.v(str, "applyTargetZoom(): " + targetZoom);
            MiCameraCompat.applyTargetZoom(builder, targetZoom);
        }
    }

    public static void applyThermal(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.THERMAL_LEVEL.getName())) {
            MiCameraCompat.applyThermalLevel(builder, Integer.valueOf(cameraConfigs.getThermalLevel()));
        }
    }

    public static void applyUltraPixelPortrait(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraConfigs != null && cameraCapabilities.isUltraPixelPortraitTagDefined()) {
            boolean isUltraPixelPortraitEnabled = cameraConfigs.isUltraPixelPortraitEnabled();
            String str = TAG;
            Log.d(str, "applyUltraPixelPortrait: " + isUltraPixelPortraitEnabled);
            MiCameraCompat.applyUltraPixelPortrait(builder, isUltraPixelPortraitEnabled);
        }
    }

    static void applyUltraWideLDC(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportUltraWideLDC()) {
            MiCameraCompat.applyUltraWideLDC(builder, cameraConfigs.isUltraWideLDCEnabled());
        }
    }

    public static void applyVideoBokehColorRetentionMode(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs, boolean z) {
        if (builder != null && cameraCapabilities.isSupportVideoBokehColorRetentionTag(z)) {
            MiCameraCompat.applyVideoBokehColorRetentionMode(builder, cameraConfigs.getVideoBokehColorRetentionMode(z), z);
        }
    }

    static void applyVideoBokehLevelBack(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.getFacing() != 0 && cameraCapabilities.isSupportVideoBokehRequestTag(false)) {
            MiCameraCompat.applyVideoBokehBackLevel(builder, cameraConfigs.getVideoBokehLevelBack());
        }
    }

    static void applyVideoBokehLevelFront(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.getFacing() == 0 && cameraCapabilities.isSupportVideoBokehRequestTag(true)) {
            MiCameraCompat.applyVideoBokehFrontLevel(builder, cameraConfigs.getVideoBokehLevelFront());
        }
    }

    static void applyVideoFilterId(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        int videoFilterId;
        if (builder != null && cameraCapabilities.isSupportVideoFilterRequestTag() && (videoFilterId = cameraConfigs.getVideoFilterId()) != -1) {
            MiCameraCompat.applyVideoFilterId(builder, videoFilterId);
        }
    }

    static void applyVideoFlash(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            boolean z = 5 == cameraConfigs.getFlashMode();
            if (2 == cameraConfigs.getFlashMode() || z) {
                if (z) {
                    MiCameraCompat.applyBackSoftLight(builder, (byte) 1);
                }
                builder.set(CaptureRequest.FLASH_MODE, 2);
                return;
            }
            builder.set(CaptureRequest.FLASH_MODE, 0);
        }
    }

    static void applyVideoFpsRange(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            Range<Integer> videoFpsRange = cameraConfigs.getVideoFpsRange();
            String str = TAG;
            Log.d(str, "applyVideoFpsRange: fpsRange = " + videoFpsRange);
            if (videoFpsRange != null) {
                builder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, videoFpsRange);
            }
        }
    }

    public static void applyVideoHdrMode(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isSupportVideoHdr()) {
            boolean isVideoHdrEnable = cameraConfigs.isVideoHdrEnable();
            String str = TAG;
            Log.d(str, "applyVideoHdrMode: " + isVideoHdrEnable);
            MiCameraCompat.applyVideoHdrMode(builder, isVideoHdrEnable ? CaptureRequestVendorTags.MTK_HDR_FEATURE_HDR_MODE_VIDEO_ON : CaptureRequestVendorTags.MTK_HDR_FEATURE_HDR_MODE_OFF);
        }
    }

    public static void applyVideoLog(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null && cameraCapabilities.isTagDefined(CaptureRequestVendorTags.PRO_VIDEO_LOG_ENABLED.getName())) {
            MiCameraCompat.applyVideoLogEnable(builder, cameraConfigs.isVideoLogEnabled() ? (byte) 1 : 0);
        }
    }

    static void applyWaterMark(CaptureRequest.Builder builder, int i, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder == null || !cameraCapabilities.isSupportWatermark()) {
            return;
        }
        if (i != 3) {
            if (i == 4) {
                MiCameraCompat.applyWaterMarkAppliedList(builder, "");
            }
        } else if (!DataRepository.dataItemFeature().Rl()) {
            String join = Util.join(",", cameraConfigs.getWaterMarkAppliedList());
            String str = TAG;
            Log.d(str, "applyWaterMark appliedList:" + join);
            MiCameraCompat.applyWaterMarkAppliedList(builder, join);
            if (DataRepository.dataItemFeature().om() && cameraCapabilities.isSupportCustomWatermark() && join.contains("device")) {
                String watermarkFileName = Util.getWatermarkFileName(cameraConfigs.isCinematicPhotoEnabled());
                MiCameraCompat.applyCustomWaterMark(builder, Util.WATERMARK_STORAGE_DIRECTORY + watermarkFileName);
            }
            if (join.contains(WatermarkConstant.ITEM_TAG)) {
                MiCameraCompat.applyTimeWaterMark(builder, cameraConfigs.getTimeWaterMarkValue());
            }
            if (join.contains("beautify")) {
                MiCameraCompat.applyFaceWaterMark(builder, cameraConfigs.getFaceWaterMarkFormat());
            }
            cameraConfigs.setNewWatermark(false);
        }
    }

    static void applyZoomRatio(CaptureRequest.Builder builder, CameraCapabilities cameraCapabilities, CameraConfigs cameraConfigs) {
        if (builder != null) {
            applyTargetZoom(builder, cameraCapabilities, cameraConfigs);
            float zoomRatio = cameraConfigs != null ? cameraConfigs.getZoomRatio() : 1.0f;
            Rect activeArraySize = cameraCapabilities.getActiveArraySize();
            Rect cropRegion = HybridZoomingSystem.toCropRegion(zoomRatio, activeArraySize);
            builder.set(CaptureRequest.SCALER_CROP_REGION, cropRegion);
            String str = TAG;
            Log.v(str, "applyZoomRatio(): cameraId = " + cameraCapabilities.getCameraId() + ", zoomRatio = " + zoomRatio + ", activeArraySize = " + activeArraySize + ", cropRegion = " + cropRegion);
        }
    }

    static void applyZsl(CaptureRequest.Builder builder, CameraConfigs cameraConfigs) {
        if (builder != null) {
            boolean isZslEnabled = cameraConfigs.isZslEnabled();
            String str = TAG;
            Log.v(str, "applyZsl(): " + isZslEnabled);
            MiCameraCompat.applyZsl(builder, isZslEnabled);
        }
    }
}
