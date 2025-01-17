package com.android.camera2.compat;

import android.annotation.TargetApi;
import android.hardware.camera2.CaptureRequest;
import com.android.camera.log.Log;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;

@TargetApi(21)
class MiCameraCompatQcomImpl extends MiCameraCompatBaseImpl {
    MiCameraCompatQcomImpl() {
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
    public void applyContrast(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.CONTRAST_LEVEL, Integer.valueOf(i + 1));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyCustomWB(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.USE_CUSTOM_WB, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyExposureMeteringMode(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.EXPOSURE_METERING, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyExposureTime(CaptureRequest.Builder builder, long j) {
        Long l = (Long) VendorTagHelper.getValue(builder, CaptureRequestVendorTags.ISO_EXP);
        if ((l == null || l.longValue() == 0) && j > 0) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SELECT_PRIORITY, 1);
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, Long.valueOf(j));
            builder.set(CaptureRequest.SENSOR_SENSITIVITY, null);
        }
        super.applyExposureTime(builder, j);
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHDRVideoMode(CaptureRequest.Builder builder, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("HDR10VideoMode: ");
        sb.append(z ? "0x2" : "0x0");
        Log.d("MiCameraCompat", sb.toString());
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR10_VIDEO, Byte.valueOf(z ? (byte) 2 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHdrBracketMode(CaptureRequest.Builder builder, byte b2) {
        Log.d("MiCameraCompat", "applyHdrBracketMode: " + ((int) b2));
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_BRACKET_MODE, Byte.valueOf(b2));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyHighQualityPreferred(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.HIGHQUALITY_PREFERRED, Byte.valueOf(z ? (byte) 1 : 0));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyISO(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SELECT_PRIORITY, 0);
        if (i == 0) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 0L);
        } else if (i == 100) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 2L);
        } else if (i == 200) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 3L);
        } else if (i == 400) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 4L);
        } else if (i == 800) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 5L);
        } else if (i == 1600) {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 6L);
        } else if (i != 3200) {
            Log.d("MiCameraCompat", "applyISO(): set manual absolute iso value to " + i);
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 8L);
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.USE_ISO_VALUE, Integer.valueOf(i));
        } else {
            VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ISO_EXP, 7L);
        }
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyMfnrFrameNum(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.MFNR_FRAME_NUM, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyMultiFrameCount(CaptureRequest.Builder builder, int i) {
        Log.d("MiCameraCompat", "applyMultiFrameCount: " + i);
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_FOR_MULTIFRAME_FRAME_COUNT, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyMultiFrameIndex(CaptureRequest.Builder builder, int i) {
        Log.d("MiCameraCompat", "applyMultiFrameIndex: " + i);
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_FOR_MULTIFRAME_FRAME_INDEX, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applySatFallback(CaptureRequest.Builder builder, boolean z) {
        Log.d("MiCameraCompat", "applySatFallback: " + z);
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SAT_FALLBACK_ENABLE, Boolean.valueOf(z));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applySatFallbackDisable(CaptureRequest.Builder builder, boolean z) {
        Log.d("MiCameraCompat", "applySatFallbackDisable: " + (z ? 1 : 0));
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SAT_FALLBACK_DISABLE, Byte.valueOf(z ? (byte) 1 : 0));
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
    public void applySmoothTransition(CaptureRequest.Builder builder, boolean z) {
        Log.d("MiCameraCompat", "applySmoothTransition: " + z);
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ST_ENABLED, Boolean.valueOf(z));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyVideoBokehColorRetentionMode(CaptureRequest.Builder builder, int i, boolean z) {
        Log.d("MiCameraCompat", "applyVideoBokehColorRetentionMode: " + i + " isFront:" + z);
        VendorTagHelper.setValue(builder, z ? CaptureRequestVendorTags.VIDEO_BOKEH_COLOR_RETENTION_FRONT_MODE : CaptureRequestVendorTags.VIDEO_BOKEH_COLOR_RETENTION_BACK_MODE, Integer.valueOf(i));
    }

    @Override // com.android.camera2.compat.MiCameraCompatBaseImpl
    public void applyVideoStreamState(CaptureRequest.Builder builder, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("recordingEndOfStream: ");
        sb.append(z ? "0x0" : "0x1");
        Log.d("MiCameraCompat", sb.toString());
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.RECORDING_END_STREAM, Byte.valueOf(!z ? 1 : 0 ? (byte) 1 : 0));
    }
}
