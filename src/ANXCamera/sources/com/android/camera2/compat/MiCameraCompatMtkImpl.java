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

    public void applyContrast(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.CONTRAST_LEVEL, Integer.valueOf(i + 1));
    }

    public void applyCustomWB(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.USE_CUSTOM_WB, Integer.valueOf(i));
    }

    public void applyExposureMeteringMode(CaptureRequest.Builder builder, int i) {
        if (i == 1) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, (byte) 0);
        } else if (i == 2) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, (byte) 1);
        } else if (i == 0) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MTK_EXPOSURE_METERING_MODE, (byte) 2);
        }
    }

    public void applyExposureTime(CaptureRequest.Builder builder, long j) {
        if (j > 0) {
            builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, Long.valueOf(j));
            builder.set(CaptureRequest.CONTROL_AE_MODE, 0);
            return;
        }
        builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, 0L);
        builder.set(CaptureRequest.CONTROL_AE_MODE, (Integer) builder.get(CaptureRequest.CONTROL_AE_MODE));
    }

    public void applyFaceDetection(CaptureRequest.Builder builder, boolean z) {
        super.applyFaceDetection(builder, z);
        if (z) {
            builder.set(CaptureRequest.CONTROL_SCENE_MODE, 1);
        } else {
            builder.set(CaptureRequest.CONTROL_SCENE_MODE, (Integer) builder.get(CaptureRequest.CONTROL_SCENE_MODE));
        }
    }

    public void applyFrontMirror(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.FRONT_MIRROR, Boolean.valueOf(z));
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.SANPSHOT_FLIP_MODE, Integer.valueOf(z ? 1 : 0));
    }

    public void applyHdrBracketMode(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_BRACKET_MODE, Byte.valueOf(b2));
    }

    public void applyHighFpsVideoRecordingMode(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.HFPSVR_MODE, Integer.valueOf(z ? 1 : 0));
    }

    public void applyISO(CaptureRequest.Builder builder, int i) {
        if (i > 0) {
            builder.set(CaptureRequest.SENSOR_SENSITIVITY, Integer.valueOf(i));
            builder.set(CaptureRequest.CONTROL_AE_MODE, 0);
            return;
        }
        builder.set(CaptureRequest.SENSOR_SENSITIVITY, 0);
        builder.set(CaptureRequest.CONTROL_AE_MODE, (Integer) builder.get(CaptureRequest.CONTROL_AE_MODE));
    }

    public void applyPostProcessCropRegion(CaptureRequest.Builder builder, Rect rect) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.POST_PROCESS_CROP_REGION, rect);
    }

    public void applyQuickPreview(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW, z ? CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW_ON : CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW_OFF);
    }

    public void applyRawReprocessHint(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.HINT_FOR_RAW_REPROCESS, Boolean.valueOf(z));
    }

    public void applyRemosaicEnabled(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_ENABLE_REMOSAIC, Boolean.valueOf(z));
    }

    public void applyRemosaicHint(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT, z ? CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT_ON : CaptureRequestVendorTags.CONTROL_REMOSAIC_HINT_OFF);
    }

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

    public void applySlowMotionVideoRecordingMode(CaptureRequest.Builder builder, int[] iArr) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.SMVR_MODE, iArr);
    }

    public void applyVideoStreamState(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.RECORDING_END_STREAM, Byte.valueOf(z ^ true ? (byte) 1 : 0));
    }

    public void applyZsd(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.ZSL_CAPTURE_MODE, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void copyAiSceneFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AI_SCENE_DETECTED);
        if (num != null) {
            VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_AI_SCENE_MODE, num);
        }
    }

    public void copyFpcDataFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
        byte[] bArr = (byte[]) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.DISTORTION_FPC_DATA);
        if (bArr != null && bArr.length / 8 == 23) {
            VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_DISTORTION_FPC_DATA, bArr);
        }
    }

    public VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> getDefaultSteamConfigurationsTag() {
        return CameraCharacteristicsVendorTags.SCALER_AVAILABLE_STREAM_CONFIGURATIONS;
    }
}
