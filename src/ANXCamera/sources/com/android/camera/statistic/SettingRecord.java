package com.android.camera.statistic;

import android.content.Context;
import android.content.res.Resources;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.customization.ShutterSound;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.settings.CameraPreferenceFragment;
import com.android.camera.statistic.MistatsConstants;
import java.util.HashMap;

public class SettingRecord {
    private static String TAG = "SettingRecord";
    private Context mContext;
    private boolean mInRecording = false;

    public SettingRecord(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getMistatString(String str) {
        char c2;
        switch (str.hashCode()) {
            case -2144607600:
                if (str.equals(CameraSettings.KEY_ULTRA_WIDE_LDC)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -2108353415:
                if (str.equals(CameraSettings.KEY_NORMAL_WIDE_LDC)) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case -1717659284:
                if (str.equals(CameraPreferenceFragment.PREF_KEY_PRIVACY)) {
                    c2 = 29;
                    break;
                }
                c2 = 65535;
                break;
            case -1620641004:
                if (str.equals(CameraSettings.KEY_SCAN_QRCODE)) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -1427654064:
                if (str.equals(CameraSettings.KEY_VIDEO_DYNAMIC_FRAME_RATE)) {
                    c2 = 26;
                    break;
                }
                c2 = 65535;
                break;
            case -1408552375:
                if (str.equals(CameraSettings.KEY_LENS_DIRTY_TIP)) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case -1334394994:
                if (str.equals(CameraSettings.KEY_CAMERA_SOUND)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1153050370:
                if (str.equals(CameraSettings.KEY_MOVIE_SOLID)) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case -636369951:
                if (str.equals(CameraSettings.KEY_REFERENCE_LINE)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -435486694:
                if (str.equals("pref_camera_focus_shoot_key")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -316718012:
                if (str.equals(CameraSettings.KEY_CAMERA_NEAR_RANGE)) {
                    c2 = ' ';
                    break;
                }
                c2 = 65535;
                break;
            case -305641358:
                if (str.equals(CameraPreferenceFragment.PREF_KEY_RESTORE)) {
                    c2 = 30;
                    break;
                }
                c2 = 65535;
                break;
            case -233551147:
                if (str.equals(CameraSettings.KEY_960_WATERMARK_STATUS)) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case -44500048:
                if (str.equals(CameraSettings.KEY_VOLUME_CAMERA_FUNCTION)) {
                    c2 = 27;
                    break;
                }
                c2 = 65535;
                break;
            case -33912691:
                if (str.equals(CameraSettings.KEY_ANTIBANDING)) {
                    c2 = 28;
                    break;
                }
                c2 = 65535;
                break;
            case 4130057:
                if (str.equals(CameraSettings.KEY_PICTURE_FLAW_TIP)) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case 17536442:
                if (str.equals(CameraSettings.KEY_FRONT_MIRROR)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 386125541:
                if (str.equals(CameraSettings.KEY_HEIC_FORMAT)) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case 554750382:
                if (str.equals("pref_time_watermark_key")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 585576333:
                if (str.equals(CameraSettings.KEY_HDR10_VIDEO_ENCODER)) {
                    c2 = 25;
                    break;
                }
                c2 = 65535;
                break;
            case 852574760:
                if (str.equals(CameraSettings.KEY_CAMERA_SNAP)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 966436379:
                if (str.equals(CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL)) {
                    c2 = 24;
                    break;
                }
                c2 = 65535;
                break;
            case 1069539048:
                if (str.equals("pref_watermark_key")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 1167378432:
                if (str.equals(CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH)) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case 1324596611:
                if (str.equals(CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE)) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case 1516583362:
                if (str.equals(CameraSettings.KEY_CAMERA_HIGH_QUALITY_PREFERRED)) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case 1540922525:
                if (str.equals(CameraSettings.KEY_CAMERA_MANUALLY_DESCRIPTION_TIP)) {
                    c2 = 31;
                    break;
                }
                c2 = 65535;
                break;
            case 1613717468:
                if (str.equals(CameraSettings.KEY_VIDEO_ENCODER)) {
                    c2 = 23;
                    break;
                }
                c2 = 65535;
                break;
            case 1739638146:
                if (str.equals("pref_dualcamera_watermark_key")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 1752299636:
                if (str.equals("user_define_watermark_key")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 1761265663:
                if (str.equals(CameraSettings.KEY_RETAIN_CAMERA_MODE)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1934228025:
                if (str.equals(CameraSettings.KEY_JPEG_QUALITY)) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case 2069752292:
                if (str.equals(CameraSettings.KEY_RECORD_LOCATION)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                return MistatsConstants.Setting.PARAM_SAVE_LOCATION;
            case 1:
                return MistatsConstants.Setting.PARAM_CAMERA_SOUND;
            case 2:
                return MistatsConstants.Setting.PARAM_RETAIN_CAMERA_MODE;
            case 3:
                return MistatsConstants.Setting.PARAM_CAMERA_SNAP;
            case 4:
                return MistatsConstants.Setting.PARAM_FRONT_MIRROR;
            case 5:
                return MistatsConstants.Setting.PARAM_WATERMARK;
            case 6:
                return MistatsConstants.Setting.PARAM_TIME_WATERMARK;
            case 7:
                return MistatsConstants.Setting.PARAM_DEVICE_WATERMARK;
            case '\b':
                return MistatsConstants.Setting.PARAM_USERDEFINE_WATERMARK;
            case '\t':
                return "attr_reference_line";
            case '\n':
                return MistatsConstants.Setting.PARAM_ULTRA_WIDE_LDC;
            case 11:
                return MistatsConstants.Setting.PARAM_FOCUS_SHOOT;
            case '\f':
                return MistatsConstants.Setting.PARAM_SCAN_QRCODE;
            case '\r':
                return MistatsConstants.Setting.PARAM_LONG_PRESS_SHUTTER_FEATURE;
            case 14:
                return MistatsConstants.Setting.PARAM_HIGH_QUALITY_PREFERRED;
            case 15:
                return MistatsConstants.Setting.PARAM_JPEG_QUALITY;
            case 16:
                return MistatsConstants.Setting.PARAM_HEIC_FORMAT;
            case 17:
                return MistatsConstants.Setting.PARAM_LENS_DIRTY_SWITCH;
            case 18:
                return MistatsConstants.Setting.PARAM_LYING_TIP_SWITCH;
            case 19:
                return MistatsConstants.Setting.PARAM_NORMAL_WIDE_LDC;
            case 20:
                return MistatsConstants.Setting.PARAM_960_WATERMARK_STATUS;
            case 21:
                return MistatsConstants.Setting.PARAM_FLAW_TIP;
            case 22:
                return 180 == DataRepository.dataItemGlobal().getCurrentMode() ? MistatsConstants.Setting.PARAM_PRO_MODE_MOVIE_SOLID : MistatsConstants.Setting.PARAM_MOVIE_SOLID;
            case 23:
                return MistatsConstants.Setting.PARAM_VIDEO_ENCODER;
            case 24:
                return MistatsConstants.Setting.PARAM_VIDEO_TIME_LAPSE_FRAME_INTERVAL;
            case 25:
                return MistatsConstants.Setting.PARAM_VIDEO_HDR10_VIDEO_ENCODER;
            case 26:
                return MistatsConstants.Setting.PARAM_VIDEO_DYNAMIC_FRAME_RATE;
            case 27:
                return MistatsConstants.Setting.PARAM_VOLUME_CAMERA_FUNCTION;
            case 28:
                return MistatsConstants.Setting.PARAM_ANTIBANDING;
            case 29:
                return MistatsConstants.Setting.PREF_KEY_PRIVACY;
            case 30:
                return MistatsConstants.Setting.PREF_KEY_RESTORE;
            case 31:
                return MistatsConstants.Setting.PARAM_DESCRIPTION;
            case ' ':
                return MistatsConstants.CaptureAttr.PARAM_NEAR_RANGE_MODE;
            default:
                return null;
        }
    }

    private void uploadAdvanceSetting() {
        HashMap hashMap = new HashMap();
        Resources resources = this.mContext.getResources();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_ADVANCE);
        hashMap.put(MistatsConstants.Setting.PARAM_VOLUME_CAMERA_FUNCTION, DataRepository.dataItemGlobal().getString(CameraSettings.KEY_VOLUME_CAMERA_FUNCTION, resources.getString(R.string.pref_camera_volumekey_function_default)));
        hashMap.put(MistatsConstants.Setting.PARAM_ANTIBANDING, CameraSettings.getAntiBanding());
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    private void uploadCaptureSetting() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_CAPTURE);
        boolean isTimeWaterMarkOpen = CameraSettings.isTimeWaterMarkOpen();
        hashMap.put(MistatsConstants.Setting.PARAM_TIME_WATERMARK, Boolean.valueOf(CameraSettings.isTimeWaterMarkOpen()));
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        hashMap.put(MistatsConstants.Setting.PARAM_DEVICE_WATERMARK, Boolean.valueOf(isDualCameraWaterMarkOpen));
        boolean isCustomWatermarkOpen = CameraSettings.isCustomWatermarkOpen();
        hashMap.put(MistatsConstants.Setting.PARAM_USERDEFINE_WATERMARK, Boolean.valueOf(isCustomWatermarkOpen));
        hashMap.put(MistatsConstants.Setting.PARAM_WATERMARK, Boolean.valueOf((CameraSettings.isSupportedDualCameraWaterMark() && isDualCameraWaterMarkOpen) || isTimeWaterMarkOpen || isCustomWatermarkOpen));
        hashMap.put("attr_reference_line", Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_REFERENCE_LINE, false)));
        hashMap.put(MistatsConstants.Setting.PARAM_ULTRA_WIDE_LDC, Boolean.valueOf(CameraSettings.isUltraWideLDCEnabled()));
        hashMap.put(MistatsConstants.Setting.PARAM_NORMAL_WIDE_LDC, Boolean.valueOf(CameraSettings.isNormalWideLDCEnabled()));
        hashMap.put(MistatsConstants.Setting.PARAM_FOCUS_SHOOT, Boolean.valueOf(DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key")));
        hashMap.put(MistatsConstants.Setting.PARAM_SCAN_QRCODE, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_SCAN_QRCODE, Boolean.valueOf(this.mContext.getResources().getString(R.string.pref_scan_qrcode_default)).booleanValue())));
        hashMap.put(MistatsConstants.Setting.PARAM_LONG_PRESS_SHUTTER_FEATURE, DataRepository.dataItemGlobal().getString(CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE, this.mContext.getResources().getString(R.string.pref_camera_long_press_shutter_feature_default)));
        hashMap.put(MistatsConstants.Setting.PARAM_JPEG_QUALITY, DataRepository.dataItemConfig().getString(CameraSettings.KEY_JPEG_QUALITY, this.mContext.getResources().getString(R.string.pref_camera_jpegquality_default)));
        hashMap.put(MistatsConstants.Setting.PARAM_HEIC_FORMAT, Boolean.valueOf(CameraSettings.isHeicImageFormatSelected()));
        hashMap.put(MistatsConstants.Setting.PARAM_LYING_TIP_SWITCH, Boolean.valueOf(CameraSettings.isCameraLyingHintOn()));
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    private void uploadRecordSettingGlobal() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_GOLBAL);
        hashMap.put(MistatsConstants.Setting.PARAM_SAVE_LOCATION, Boolean.valueOf(CameraSettings.isRecordLocation()));
        hashMap.put(MistatsConstants.Setting.PARAM_CAMERA_SOUND, Boolean.valueOf(CameraSettings.isCameraSoundOpen()));
        hashMap.put(MistatsConstants.Setting.PARAM_RETAIN_CAMERA_MODE, Boolean.valueOf(CameraSettings.retainCameraMode()));
        hashMap.put(MistatsConstants.Setting.PARAM_CAMERA_SNAP, DataRepository.dataItemGlobal().getString(CameraSettings.KEY_CAMERA_SNAP, null));
        hashMap.put(MistatsConstants.Setting.PARAM_FRONT_MIRROR, Boolean.valueOf(CameraSettings.isRecordLocation()));
        boolean z = DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_CAMERA_SOUND, true);
        hashMap.put("attr_edit_sound", "day_" + z + ShutterSound.read());
        hashMap.put("attr_edit_tint", Integer.valueOf(TintColor.readColorId()));
        hashMap.put("attr_more_mode", Integer.valueOf(CameraSettings.getMoreModeStyle()));
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    private void uploadVideoSetting() {
        HashMap hashMap = new HashMap();
        Resources resources = this.mContext.getResources();
        hashMap.put(MistatsConstants.Setting.PARAM_SUB_MODULE, MistatsConstants.Setting.VALUE_SETTING_VIDEO_RECORD);
        hashMap.put(MistatsConstants.Setting.PARAM_MOVIE_SOLID, Boolean.valueOf(CameraSettings.isMovieSolidOn()));
        hashMap.put(MistatsConstants.Setting.PARAM_VIDEO_ENCODER, Integer.valueOf(CameraSettings.getVideoEncoder()));
        hashMap.put(MistatsConstants.Setting.PARAM_VIDEO_TIME_LAPSE_FRAME_INTERVAL, DataRepository.dataItemGlobal().getString(CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL, resources.getString(R.string.pref_video_time_lapse_frame_interval_default)));
        MistatsWrapper.settingSchedualEvent(hashMap);
    }

    public void endRecord() {
        if (this.mInRecording) {
            uploadRecordSettingGlobal();
            uploadCaptureSetting();
            uploadVideoSetting();
            uploadAdvanceSetting();
            this.mInRecording = false;
        }
    }

    public void startRecord() {
        this.mInRecording = true;
    }
}
