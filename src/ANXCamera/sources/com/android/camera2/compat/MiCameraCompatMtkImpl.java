package com.android.camera2.compat;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.StreamConfiguration;
import com.android.camera2.vendortag.CameraCharacteristicsVendorTags;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;

@TargetApi(21)
class MiCameraCompatMtkImpl extends MiCameraCompatBaseImpl {
    MiCameraCompatMtkImpl() {
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyAiShutterEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.XIAOMI_AISHUTTER_FEATURE_ENABLED, Boolean.valueOf(z));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyAiShutterExistMotion(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.XIAOMI_AISHUTTER_EXIST_MOTION, z ? CaptureRequestVendorTags.XIAOMI_AISHUTTER_EXIST_MOTION_ON : CaptureRequestVendorTags.XIAOMI_AISHUTTER_EXIST_MOTION_OFF);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyAmbilightAeTarget(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.AMBILIGHT_AE_TARGET, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyAmbilightMode(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.AMBILIGHT_MODE, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyCShotFeatureCapture(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_CSHOT_FEATURE_CAPTURE, Integer.valueOf(z ? 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyContrast(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.CONTRAST_LEVEL, Integer.valueOf(i + 1));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyCropFeature(CaptureRequest.Builder builder, int[] iArr) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.MTK_MULTI_CAM_CONFIG_SCALER_CROP_REGION, iArr);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyCustomWB(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.USE_CUSTOM_WB, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyExposureMeteringMode(CaptureRequest.Builder builder, int i) {
        if (i == 1) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, (byte) 0);
        } else if (i == 2) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, (byte) 1);
        } else if (i == 0) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, (byte) 2);
        }
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyExposureTime(CaptureRequest.Builder builder, long j) {
        if (j > 0) {
            builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, Long.valueOf(j));
            builder.set(CaptureRequest.CONTROL_AE_MODE, 0);
            return;
        }
        builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, 0L);
        CaptureRequest.Key key = CaptureRequest.CONTROL_AE_MODE;
        builder.set(key, (Integer) builder.get(key));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyFaceDetection(CaptureRequest.Builder builder, boolean z) {
        super.applyFaceDetection(builder, z);
        if (z) {
            builder.set(CaptureRequest.CONTROL_SCENE_MODE, 1);
            return;
        }
        CaptureRequest.Key key = CaptureRequest.CONTROL_SCENE_MODE;
        builder.set(key, (Integer) builder.get(key));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyFaceRectangles(CaptureRequest.Builder builder, Rect[] rectArr) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.MI_STATISTICS_FACE_RECTANGLES, rectArr);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyFeatureMode(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.MTK_MULTI_CAM_FEATURE_MODE, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyFrontMirror(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.FRONT_MIRROR, Boolean.valueOf(z));
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.SANPSHOT_FLIP_MODE, Integer.valueOf(z ? 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHDRVideoMode(CaptureRequest.Builder builder, boolean z) {
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHdrBracketMode(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_BRACKET_MODE, Byte.valueOf(b2));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHighFpsVideoRecordingMode(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.HFPSVR_MODE, Integer.valueOf(z ? 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHighQualityReprocess(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_HIGH_QUALITY_REPROCESS, Integer.valueOf(z ? 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHistogramStats(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.HISTOGRAM_STATS_ENABLED, Byte.valueOf(b2));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyISO(CaptureRequest.Builder builder, int i) {
        if (i > 0) {
            builder.set(CaptureRequest.SENSOR_SENSITIVITY, Integer.valueOf(i));
            builder.set(CaptureRequest.CONTROL_AE_MODE, 0);
            return;
        }
        builder.set(CaptureRequest.SENSOR_SENSITIVITY, 0);
        CaptureRequest.Key key = CaptureRequest.CONTROL_AE_MODE;
        builder.set(key, (Integer) builder.get(key));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyIspMetaEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_ISP_META_ENABLE, Byte.valueOf(z ? (byte) 2 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyIspMetaType(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_ISP_META_REQUEST, Byte.valueOf(b2));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyIspTuningHint(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_HINT_FOR_ISP_TUNING, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyNoiseReduction(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_SINGLE_YUV_NR, Integer.valueOf(z ? 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyNotificationTrigger(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_NOTIFICATION_TRIGGER, Integer.valueOf(z ? 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyPanoramaP2SEnabled(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.MI_PANORAMA_P2S_ENABLED, Boolean.valueOf(z));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyPostProcessCropRegion(CaptureRequest.Builder builder, Rect rect) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.POST_PROCESS_CROP_REGION, rect);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyPqFeature(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.MTK_CONFIGURE_SETTING_PROPRIETARY, z ? CaptureRequestVendorTags.MTK_CONFIGURE_SETTING_PROPRIETARY_ON : CaptureRequestVendorTags.MTK_CONFIGURE_SETTING_PROPRIETARY_OFF);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyQuickPreview(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW, z ? CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW_ON : CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW_OFF);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyRawReprocessHint(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.HINT_FOR_RAW_REPROCESS, Boolean.valueOf(z));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyRemosaicHint(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT, z ? CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT_ON : CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT_OFF);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applySaturation(CaptureRequest.Builder builder, int i) {
        int i2 = 0;
        switch (i) {
            case 1:
                i2 = 2;
                break;
            case 2:
                i2 = 4;
                break;
            case 3:
                i2 = 5;
                break;
            case 4:
                i2 = 6;
                break;
            case 5:
                i2 = 8;
                break;
            case 6:
                i2 = 10;
                break;
        }
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SATURATION, Integer.valueOf(i2));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applySharpness(CaptureRequest.Builder builder, int i) {
        int i2 = 0;
        switch (i) {
            case 1:
                i2 = 1;
                break;
            case 2:
                i2 = 2;
                break;
            case 3:
                i2 = 3;
                break;
            case 4:
                i2 = 4;
                break;
            case 5:
                i2 = 5;
                break;
            case 6:
                i2 = 6;
                break;
        }
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SHARPNESS_CONTROL, Integer.valueOf(i2));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applySlowMotionVideoRecordingMode(CaptureRequest.Builder builder, int[] iArr) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.SMVR_MODE, iArr);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applySpecshotMode(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_ENABLE_SPECSHOT_MODE, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyVideoHdrMode(CaptureRequest.Builder builder, int[] iArr) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.MTK_HDR_KEY_DETECTION_MODE, iArr);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyVideoStreamState(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.RECORDING_END_STREAM, Byte.valueOf(!z ? 1 : 0 ? (byte) 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyZsd(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.ZSL_CAPTURE_MODE, Byte.valueOf(z ? (byte) 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void copyAiSceneFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AI_SCENE_DETECTED);
        if (num != null) {
            VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_AI_SCENE_MODE, num);
        }
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void copyFpcDataFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
        byte[] bArr = (byte[]) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.DISTORTION_FPC_DATA);
        if (bArr != null && bArr.length / 8 == 23) {
            VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_DISTORTION_FPC_DATA, bArr);
        }
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> getDefaultSteamConfigurationsTag() {
        return CameraCharacteristicsVendorTags.SCALER_AVAILABLE_STREAM_CONFIGURATIONS;
    }
}
