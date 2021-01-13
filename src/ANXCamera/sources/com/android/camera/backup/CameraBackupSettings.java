package com.android.camera.backup;

import com.android.camera.CameraSettings;
import miui.cloud.backup.data.PrefsBackupHelper;

public class CameraBackupSettings {
    private static final String KEY_ANTIBANDING = "camera_antibanding";
    private static final String KEY_AUTOEXPOSURE = "camera_autoexposure";
    private static final String KEY_AUTO_CHROMA_FLASH = "auto_chroma_flash";
    private static final String KEY_BEAUTIFY_ENLARGE_EYE_RATIO = "beautify_enlarge_eye_ratio";
    private static final String KEY_BEAUTIFY_SKIN_COLOR_RATIO = "beautify_skin_color_ratio";
    private static final String KEY_BEAUTIFY_SKIN_SMOOTH_RATIO = "beautify_skin_smooth_ratio";
    private static final String KEY_BEAUTIFY_SLIM_FACE_RATIO = "beautify_slim_face_ratio";
    private static final String KEY_CAMERA_ASD_NIGHT = "camera_asd_night";
    private static final String KEY_CAMERA_GROUPSHOT_PRIMITIVE = "groupshot_with_primitive_picture";
    private static final String KEY_CAMERA_LYING_TIP_SWITCH = "camera_lying_tip_switch";
    private static final String KEY_CAMERA_PROXIMITY_LOCK = "camera_proximity_lock";
    private static final String KEY_CAMERA_SNAP = "camera_snap";
    private static final String KEY_CAMERA_SOUND = "camerasound";
    private static final String KEY_CAMERA_USER_WATERMARK = "camera_user_watermark";
    private static final String KEY_CAPTURE_WHEN_STABLE = "capture_when_stable";
    private static final String KEY_DUALCAMERA_WATERMARK = "dualcamera_watermark";
    private static final String KEY_FACE_BEAUTY = "camera_face_beauty";
    private static final String KEY_FACE_BEAUTY_SWITCH = "camera_face_beauty_switch";
    private static final String KEY_FACE_DETECTION = "camera_facedetection";
    private static final String KEY_FINGERPRINT_CAPTURE = "fingerprint_capture";
    private static final String KEY_FOCUS_SHOOT = "camera_focus_shoot";
    private static final String KEY_FRONT_MIRROR = "front_mirror";
    private static final String KEY_HEIC_FORMAT = "camera_heic_format";
    private static final String KEY_JPEG_QUALITY = "camera_jpegquality";
    private static final String KEY_LONG_PRESS_SHUTTER = "camera_long_press_shutter";
    private static final String KEY_LONG_PRESS_SHUTTER_FEATURE = "camera_long_press_shutter_feature";
    private static final String KEY_MOVIE_SOLID = "camera_movie_solid";
    private static final String KEY_PICTURE_SIZE = "camera_picturesize";
    private static final String KEY_PRIORITY_STORAGE = "priority_storage";
    private static final String KEY_PRO_MODE_MOVIE_SOLID = "camera_pro_mode_movie_solid";
    private static final String KEY_QC_CONTRAST = "qc_camera_contrast";
    private static final String KEY_QC_EXPOSURETIME = "qc_camera_exposuretime";
    private static final String KEY_QC_FOCUS_POSITION = "focus_position";
    private static final String KEY_QC_ISO = "qc_camera_iso";
    private static final String KEY_QC_MANUAL_WHITEBALANCE_VALUE = "qc_manual_whitebalance_k_value";
    private static final String KEY_QC_SATURATION = "qc_camera_saturation";
    private static final String KEY_QC_SHARPNESS = "qc_camera_sharpness";
    private static final String KEY_RECORD_LOCATION = "camera_recordlocation";
    private static final String KEY_REFERENCE_LINE = "camera_referenceline";
    private static final String KEY_RETAIN_CAMERA_MODE = "retain_camera_mode";
    private static final String KEY_SCAN_QRCODE = "scan_qrcode";
    private static final String KEY_TIME_WATERMARK = "watermark";
    private static final String KEY_VERSION = "version";
    private static final String KEY_VIDEO_ENCODER = "video_encoder";
    private static final String KEY_VIDEO_FOCUS_MODE = "video_focusmode";
    private static final String KEY_VIDEO_HIGH_FRAME_RATE = "video_hfr";
    private static final String KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL = "video_time_lapse_frame_interval";
    private static final String KEY_VOLUME_CAMERA_FUNCTION = "camera_volumekey_function";
    private static final String KEY_VOLUME_LIVE_FUNCTION = "live_volumekey_function";
    private static final String KEY_VOLUME_PRO_VIDEO_FUNCTION = "pro_video_volumekey_function";
    private static final String KEY_VOLUME_VIDEO_FUNCTION = "video_volumekey_function";
    private static final String KEY_WHITE_BALANCE = "camera_whitebalance";
    static final PrefsBackupHelper.PrefEntry[] PREF_ENTRIES = {PrefsBackupHelper.PrefEntry.createIntEntry("version", CameraSettings.KEY_VERSION), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_RECORD_LOCATION, CameraSettings.KEY_RECORD_LOCATION), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_CAMERA_SOUND, CameraSettings.KEY_CAMERA_SOUND), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_CAMERA_PROXIMITY_LOCK, CameraSettings.KEY_CAMERA_PROXIMITY_LOCK), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_PRIORITY_STORAGE, CameraSettings.KEY_PRIORITY_STORAGE), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_RETAIN_CAMERA_MODE, CameraSettings.KEY_RETAIN_CAMERA_MODE), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_CAMERA_SNAP, CameraSettings.KEY_CAMERA_SNAP), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_CAPTURE_WHEN_STABLE, CameraSettings.KEY_CAPTURE_WHEN_STABLE), PrefsBackupHelper.PrefEntry.createBoolEntry("watermark", "pref_time_watermark_key"), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_DUALCAMERA_WATERMARK, "pref_dualcamera_watermark_key"), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_CAMERA_USER_WATERMARK, "user_define_watermark_key"), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_AUTO_CHROMA_FLASH, CameraSettings.KEY_AUTO_CHROMA_FLASH), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_REFERENCE_LINE, CameraSettings.KEY_REFERENCE_LINE), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_FOCUS_SHOOT, "pref_camera_focus_shoot_key"), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_SCAN_QRCODE, CameraSettings.KEY_SCAN_QRCODE), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_CAMERA_ASD_NIGHT, CameraSettings.KEY_CAMERA_ASD_NIGHT), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_CAMERA_GROUPSHOT_PRIMITIVE, CameraSettings.KEY_CAMERA_GROUPSHOT_PRIMITIVE), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_LONG_PRESS_SHUTTER, CameraSettings.KEY_LONG_PRESS_SHUTTER), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_LONG_PRESS_SHUTTER_FEATURE, CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_FRONT_MIRROR, CameraSettings.KEY_FRONT_MIRROR), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_JPEG_QUALITY, CameraSettings.KEY_JPEG_QUALITY), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_HEIC_FORMAT, CameraSettings.KEY_HEIC_FORMAT), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_CAMERA_LYING_TIP_SWITCH, CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_MOVIE_SOLID, CameraSettings.KEY_MOVIE_SOLID), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_VIDEO_ENCODER, CameraSettings.KEY_VIDEO_ENCODER), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL, CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_FACE_DETECTION, CameraSettings.KEY_FACE_DETECTION), PrefsBackupHelper.PrefEntry.createBoolEntry(KEY_FINGERPRINT_CAPTURE, CameraSettings.KEY_FINGERPRINT_CAPTURE), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_VOLUME_CAMERA_FUNCTION, CameraSettings.KEY_VOLUME_CAMERA_FUNCTION), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_VOLUME_VIDEO_FUNCTION, CameraSettings.KEY_VOLUME_VIDEO_FUNCTION), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_VOLUME_PRO_VIDEO_FUNCTION, CameraSettings.KEY_VOLUME_PRO_VIDEO_FUNCTION), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_VOLUME_LIVE_FUNCTION, CameraSettings.KEY_VOLUME_LIVE_FUNCTION), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_ANTIBANDING, CameraSettings.KEY_ANTIBANDING), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_QC_CONTRAST, CameraSettings.KEY_QC_CONTRAST), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_QC_SATURATION, CameraSettings.KEY_QC_SATURATION), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_QC_SHARPNESS, CameraSettings.KEY_QC_SHARPNESS), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_WHITE_BALANCE, CameraSettings.KEY_WHITE_BALANCE), PrefsBackupHelper.PrefEntry.createStringEntry("focus_position", CameraSettings.KEY_QC_FOCUS_POSITION), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_QC_EXPOSURETIME, CameraSettings.KEY_QC_EXPOSURETIME), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_QC_ISO, CameraSettings.KEY_QC_ISO), PrefsBackupHelper.PrefEntry.createIntEntry(KEY_QC_MANUAL_WHITEBALANCE_VALUE, "pref_qc_manual_whitebalance_k_value_key"), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_FACE_BEAUTY, "pref_beautify_level_key_capture"), PrefsBackupHelper.PrefEntry.createStringEntry(KEY_FACE_BEAUTY_SWITCH, CameraSettings.KEY_FACE_BEAUTY_SWITCH), PrefsBackupHelper.PrefEntry.createIntEntry(KEY_BEAUTIFY_SLIM_FACE_RATIO, "pref_beautify_slim_face_ratio_key"), PrefsBackupHelper.PrefEntry.createIntEntry(KEY_BEAUTIFY_SKIN_SMOOTH_RATIO, "pref_beautify_skin_smooth_ratio_key"), PrefsBackupHelper.PrefEntry.createIntEntry(KEY_BEAUTIFY_SKIN_COLOR_RATIO, "pref_beautify_skin_color_ratio_key"), PrefsBackupHelper.PrefEntry.createIntEntry(KEY_BEAUTIFY_ENLARGE_EYE_RATIO, "pref_beautify_enlarge_eye_ratio_key")};
}
