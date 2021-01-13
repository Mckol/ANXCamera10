package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import b.c.a.c;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureRequestVendorTags {
    public static final VendorTag<CaptureRequest.Key<Boolean>> AI_AIIE_PREVIEWENABLED = create(Ub.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> AI_MOON_EFFECT_ENABLED = create(Mb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> AI_SCENE = create(Yc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_APPLY = create(Hb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_PERIOD = create(Ja.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ALGO_UP_ENABLED = create(Gc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AMBILIGHT_AE_TARGET = create(Mc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AMBILIGHT_MODE = create(C0230vc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ASD_DIRTY_ENABLE = create(C0118cd.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ASD_ENABLE = create(Wc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_APPLY_IN_PREVIEW = create(C0206rc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_CENTER_OFFSET = create(C0170lb.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_FORCE_LOCK = create(C0140gb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_MINIMUM_SCALING = create(C0229vb.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_MODE = create(Uc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_SCALE_OFFSET = create(Ba.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_SELECT = create(C0212sc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_START = create(Pa.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_STOP = create(C0234wa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_UNSELECT = create(C0146hb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACKWARD_CAPTURE_HINT = create(C0160jd.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACK_SOFT_LIGHT = create(C0136fd.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BLUSHER = create(Fa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BODY_SLIM = create(C0159jc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_CHIN = create(C0188ob.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_ENLARGE_EYE = create(Eb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_EYEBROW_DYE = create(Kb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HAIRLINE = create(Oa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HEAD_SLIM = create(C0213sd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_JELLY_LIPS = create(Ua.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LEG_SLIM = create(Na.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BEAUTY_LEVEL = create(Rc.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LIPS = create(Cc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NECK = create(_a.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NOSE = create(Va.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_PUPIL_LINE = create(Lb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_RISORIUS = create(C0207rd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SHOULDER_SLIM = create(Db.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_COLOR = create(C0153ic.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_SMOOTH = create(Fb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_FACE = create(Jb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_NOSE = create(Aa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SMILE = create(C0241xb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BOKEH_F_NUMBER = create(C0225ud.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_CAPTURE_HINT = create(C0106ad.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_SHOOT_FPS = create(Gb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BUTT_SLIM = create(C0200qb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CAMERA_AI_30 = create(C0147hc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CINEMATIC_PHOTO_ENABLED = create(C0190od.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CINEMATIC_VIDEO_ENABLED = create(Qb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> COLOR_ENHANCE_ENABLED = create(C0228va.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTRAST_LEVEL = create(Zb.INSTANCE, Integer.class);
    public static VendorTag<CaptureRequest.Key<Integer>> CONTROL_AI_SCENE_MODE = create(C0171lc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_CAPTURE_FOR_MULTIFRAME_FRAME_COUNT = create(C0142gd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_CAPTURE_FOR_MULTIFRAME_FRAME_INDEX = create(C0178md.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_CAPTURE_HIGH_QUALITY_REPROCESS = create(Qc.INSTANCE, Integer.class);
    public static final int CONTROL_CAPTURE_HIGH_QUALITY_YUV_OFF = 0;
    public static final int CONTROL_CAPTURE_HIGH_QUALITY_YUV_ON = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_CAPTURE_HINT_FOR_ISP_TUNING = create(C0240xa.INSTANCE, Integer.class);
    public static final int CONTROL_CAPTURE_HINT_FOR_ISP_TUNING_HDR = 5005;
    public static final int CONTROL_CAPTURE_HINT_FOR_ISP_TUNING_LLHDR = 5007;
    public static final int CONTROL_CAPTURE_HINT_FOR_ISP_TUNING_MFSR = 5006;
    public static final VendorTag<CaptureRequest.Key<Byte>> CONTROL_CAPTURE_ISP_META_ENABLE = create(Rb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CONTROL_CAPTURE_ISP_META_REQUEST = create(Ec.INSTANCE, Byte.class);
    public static final byte CONTROL_CAPTURE_ISP_TUNING_DATA_BUFFER = 2;
    public static final byte CONTROL_CAPTURE_ISP_TUNING_DATA_IN_METADATA = 1;
    public static final byte CONTROL_CAPTURE_ISP_TUNING_DATA_NONE = 0;
    public static final byte CONTROL_CAPTURE_ISP_TUNING_REQ_RAW = 1;
    public static final byte CONTROL_CAPTURE_ISP_TUNING_REQ_YUV = 2;
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_CAPTURE_SINGLE_YUV_NR = create(La.INSTANCE, Integer.class);
    public static final int CONTROL_CAPTURE_SINGLE_YUV_NR_OFF = 0;
    public static final int CONTROL_CAPTURE_SINGLE_YUV_NR_ON = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_CSHOT_FEATURE_CAPTURE = create(C0195pc.INSTANCE, Integer.class);
    public static final int CONTROL_CSHOT_FEATURE_CAPTURE_OFF = 0;
    public static final int CONTROL_CSHOT_FEATURE_CAPTURE_ON = 1;
    public static final VendorTag<CaptureRequest.Key<byte[]>> CONTROL_DISTORTION_FPC_DATA = create(Pc.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> CONTROL_ENABLE_REMOSAIC = create(Hc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_ENABLE_SPECSHOT_MODE = create(C0141gc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTROL_NOTIFICATION_TRIGGER = create(Xc.INSTANCE, Integer.class);
    public static final int CONTROL_NOTIFICATION_TRIGGER_OFF = 0;
    public static final int CONTROL_NOTIFICATION_TRIGGER_ON = 1;
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_QUICK_PREVIEW = create(C0158jb.INSTANCE, int[].class);
    public static final int[] CONTROL_QUICK_PREVIEW_OFF = {0};
    public static final int[] CONTROL_QUICK_PREVIEW_ON = {1};
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_REMOSAIC_HINT = create(C0210sa.INSTANCE, int[].class);
    public static final int[] CONTROL_REMOSAIC_HINT_OFF = {0};
    public static final int[] CONTROL_REMOSAIC_HINT_ON = {1};
    public static final CaptureRequest.Key<Byte> CONTROL_SAT_FUSION_IMAGE_TYPE = requestKey("xiaomi.capturefusion.imageType", Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CONTROL_SAT_FUSION_SHOT = create(Nc.INSTANCE, Byte.class);
    public static final byte CONTROL_SAT_FUSION_SHOT_OFF = 0;
    public static final byte CONTROL_SAT_FUSION_SHOT_ON = 1;
    public static final VendorTag<CaptureRequest.Key<String>> CUSTOM_WATERMARK_TEXT = create(Ac.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> DEBUG_INFO_AS_WATERMARK = create(C0201qc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> DEFLICKER_ENABLED = create(Wb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> DEPURPLE = create(C0254zc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> DEVICE_ORIENTATION = create(Ab.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> DUAL_BOKEH_ENABLE = create(C0189oc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EXPOSURE_METERING = create(C0218tc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_STRENGTH = create(C0164kb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_TYPE = create(Ca.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_AGE_ANALYZE_ENABLED = create(C0236wc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_SCORE_ENABLED = create(Ka.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> FLASH_CURRENT = create(Ha.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> FLASH_MODE = create(Vc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FLAW_DETECT_ENABLE = create(Wa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FRONT_MIRROR = create(Sa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> HDR10_VIDEO = create(C0235wb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_BOKEH_ENABLED = create(Pb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> HDR_BRACKET_MODE = create(C0253zb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_CHECKER_ADRC = create(C0246ya.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_CHECKER_ENABLE = create(Tb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_CHECKER_SCENETYPE = create(C0112bd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_CHECKER_STATUS = create(Da.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_ENABLED = create(Sc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_MODE = create(C0104ab.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HFPSVR_MODE = create(Za.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HHT_ENABLED = create(Zc.INSTANCE, Boolean.class);
    public static VendorTag<CaptureRequest.Key<Byte>> HIGHQUALITY_PREFERRED = create(Ia.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HINT_FOR_RAW_REPROCESS = create(C0166kd.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> HISTOGRAM_STATS_ENABLED = create(C0184nd.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Long>> ISO_EXP = create(_b.INSTANCE, Long.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> IS_HFR_PREVIEW = create(C0123dc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> LENS_DIRTY_DETECT = create(C0117cc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> MACRO_MODE = create(Yb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> MFNR_ENABLED = create(Ra.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> MFNR_FRAME_NUM = create(Nb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> MI_HDR_SR_ENABLED = create(Ta.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> MI_PANORAMA_P2S_ENABLED = create(C0211sb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Rect[]>> MI_STATISTICS_FACE_RECTANGLES = create(C0128eb.INSTANCE, Rect[].class);
    public static final VendorTag<CaptureRequest.Key<int[]>> MTK_CONFIGURE_SETTING_PROPRIETARY = create(Fc.INSTANCE, int[].class);
    public static final int[] MTK_CONFIGURE_SETTING_PROPRIETARY_OFF = {0};
    public static final int[] MTK_CONFIGURE_SETTING_PROPRIETARY_ON = {1};
    public static final VendorTag<CaptureRequest.Key<Byte>> MTK_EXPOSURE_METERING_MODE = create(C0152ib.INSTANCE, Byte.class);
    public static final byte MTK_EXPOSURE_METERING_MODE_AVERAGE = 2;
    public static final byte MTK_EXPOSURE_METERING_MODE_CENTER_WEIGHT = 0;
    public static final byte MTK_EXPOSURE_METERING_MODE_SOPT = 1;
    public static final int[] MTK_HDR_FEATURE_HDR_MODE_OFF = {0};
    public static final int[] MTK_HDR_FEATURE_HDR_MODE_VIDEO_ON = {3};
    public static final VendorTag<CaptureRequest.Key<int[]>> MTK_HDR_KEY_DETECTION_MODE = create(C0222ua.INSTANCE, int[].class);
    public static final VendorTag<CaptureRequest.Key<int[]>> MTK_MULTI_CAM_CONFIG_SCALER_CROP_REGION = create(Ob.INSTANCE, int[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> MTK_MULTI_CAM_FEATURE_MODE = create(C0165kc.INSTANCE, Integer.class);
    public static final int MTK_MULTI_CAM_FEATURE_MODE_DENOISE = 2;
    public static final int MTK_MULTI_CAM_FEATURE_MODE_VSDOF = 1;
    public static final int MTK_MULTI_CAM_FEATURE_MODE_ZOOM = 0;
    public static final VendorTag<CaptureRequest.Key<int[]>> MTK_STREAMING_FEATURE_PIP_DEVICES = create(Sb.INSTANCE, int[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> MULTIFRAME_INPUTNUM = create(C0219td.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> NORMAL_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(Jc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<MarshalQueryableASDScene.ASDScene[]>> ON_TRIPOD_MODE = create(C0248yc.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> PARALLEL_ENABLED = create(Tc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<byte[]>> PARALLEL_PATH = create(C0116cb.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> PORTRAIT_LIGHTING = create(C0110bb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Rect>> POST_PROCESS_CROP_REGION = create(C0224uc.INSTANCE, Rect.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> PRO_VIDEO_LOG_ENABLED = create(C0130ed.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> RECORDING_END_STREAM = create(C0205rb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SANPSHOT_FLIP_MODE = create(C0217tb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SATURATION = create(C0124dd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> SAT_FALLBACK_DISABLE = create(C0135fc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SAT_FALLBACK_ENABLE = create(C0154id.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SAT_IS_ZOOMING = create(Xa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> SAT_ULTRA_WIDE_LENS_DISTORTION_CORRECTION_ENABLE = create(Qa.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> SCREEN_LIGHT_HINT = create(C0247yb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SELECT_PRIORITY = create(C0177mc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SHARPNESS_CONTROL = create(C0148hd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SHRINK_MEMORY_MODE = create(Ya.INSTANCE, Integer.class);
    public static final int SHRINK_MEMORY_MODE_ALL = 2;
    public static final int SHRINK_MEMORY_MODE_INACTIVE = 1;
    public static final int SHRINK_MEMORY_MODE_NONE = 0;
    public static final VendorTag<CaptureRequest.Key<Boolean>> SINGLE_CAMERA_BOKEH = create(C0242xc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> SMVR_MODE = create(Ma.INSTANCE, int[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SNAP_SHOT_TORCH = create(Vb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_ENABLED = create(C0216ta.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_FAST_ZOOM_IN = create(C0134fb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_NIGHT_SCENE_ENABLED = create(C0252za.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_RESOLUTION_ENABLED = create(Ib.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SW_MFNR_ENABLED = create(Ea.INSTANCE, Boolean.class);
    private static final String TAG = "CaptureRequestVendorTags";
    public static final VendorTag<CaptureRequest.Key<Float>> TARGET_ZOOM = create(Cb.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> THERMAL_LEVEL = create(Ic.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> TOF_LASERDIST = create(C0183nc.INSTANCE, Byte.class);
    public static VendorTag<CaptureRequest.Key<Boolean>> ULTRA_PIXEL_PORTRAIT_ENABLED = create(C0182nb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(Dc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_CUSTOM_WB = create(C0223ub.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_ISO_VALUE = create(Bb.INSTANCE, Integer.class);
    public static final int VALUE_HFPSVR_MODE_OFF = 0;
    public static final int VALUE_HFPSVR_MODE_ON = 1;
    public static final int VALUE_SANPSHOT_FLIP_MODE_OFF = 0;
    public static final int VALUE_SANPSHOT_FLIP_MODE_ON = 1;
    public static final int VALUE_SELECT_PRIORITY_EXP_TIME_PRIORITY = 1;
    public static final int VALUE_SELECT_PRIORITY_ISO_PRIORITY = 0;
    public static final int[] VALUE_SMVR_MODE_120FPS = {120, 4};
    public static final int[] VALUE_SMVR_MODE_240FPS = {240, 8};
    public static final int VALUE_VIDEO_RECORD_CONTROL_PREPARE = 0;
    public static final int VALUE_VIDEO_RECORD_CONTROL_START = 1;
    public static final int VALUE_VIDEO_RECORD_CONTROL_STOP = 2;
    public static final byte VALUE_ZSL_CAPTURE_MODE_OFF = 0;
    public static final byte VALUE_ZSL_CAPTURE_MODE_ON = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_BOKEH_BACK_LEVEL = create(_c.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_BOKEH_COLOR_RETENTION_BACK_MODE = create(C0122db.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_BOKEH_COLOR_RETENTION_FRONT_MODE = create(C0176mb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> VIDEO_BOKEH_FRONT_LEVEL = create(C0105ac.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> VIDEO_FILTER_COLOR_RETENTION_BACK = create(C0111bc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> VIDEO_FILTER_COLOR_RETENTION_FRONT = create(Lc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_FILTER_ID = create(Ga.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_RECORD_CONTROL = create(Oc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_APPLIEDTYPE = create(C0172ld.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_AVAILABLETYPE = create(Xb.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_FACE = create(C0202qd.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_TIME = create(Bc.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> WHOLE_BODY_SLIM = create(C0194pb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> XIAOMI_AISHUTTER_EXIST_MOTION = create(C0196pd.INSTANCE, int[].class);
    public static final int[] XIAOMI_AISHUTTER_EXIST_MOTION_OFF = {0};
    public static final int[] XIAOMI_AISHUTTER_EXIST_MOTION_ON = {1};
    public static final VendorTag<CaptureRequest.Key<Boolean>> XIAOMI_AISHUTTER_FEATURE_ENABLED = create(C0129ec.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ZSL_CAPTURE_MODE = create(Kc.INSTANCE, Byte.class);
    private static Constructor<CaptureRequest.Key> requestConstructor;

    static /* synthetic */ String Ae() {
        return "xiaomi.colorRetention.enable";
    }

    static /* synthetic */ String Af() {
        return "xiaomi.burst.totalReqNum";
    }

    static /* synthetic */ String Ag() {
        return "xiaomi.beauty.blushRatio";
    }

    static /* synthetic */ String Be() {
        return "xiaomi.colorRetention.frontEnable";
    }

    static /* synthetic */ String Bf() {
        return "com.mediatek.streamingfeature.hfpsMode";
    }

    static /* synthetic */ String Bg() {
        return "xiaomi.beauty.eyeLightType";
    }

    static /* synthetic */ String Ce() {
        return "xiaomi.colorRetention.value";
    }

    static /* synthetic */ String Cf() {
        return "com.mediatek.smvrfeature.smvrMode";
    }

    static /* synthetic */ String Cg() {
        return "xiaomi.beauty.eyeLightStrength";
    }

    static /* synthetic */ String De() {
        return "xiaomi.colorRetention.frontValue";
    }

    static /* synthetic */ String Df() {
        return "com.mediatek.control.capture.zsl.mode";
    }

    static /* synthetic */ String Dg() {
        return "xiaomi.supernight.enabled";
    }

    static /* synthetic */ String Ee() {
        return "xiaomi.smoothTransition.enabled";
    }

    static /* synthetic */ String Ef() {
        return "com.mediatek.control.capture.flipmode";
    }

    static /* synthetic */ String Eg() {
        return "xiaomi.mimovie.enabled";
    }

    static /* synthetic */ String Fe() {
        return "xiaomi.smoothTransition.fallback";
    }

    static /* synthetic */ String Ff() {
        return "com.mediatek.control.capture.remosaicenable";
    }

    static /* synthetic */ String Fg() {
        return "xiaomi.beauty.headSlimRatio";
    }

    static /* synthetic */ String Ge() {
        return "com.vidhance.autozoom.start_region";
    }

    static /* synthetic */ String Gf() {
        return "com.mediatek.control.capture.hintForIspTuning";
    }

    static /* synthetic */ String Gg() {
        return "xiaomi.beauty.bodySlimRatio";
    }

    static /* synthetic */ String He() {
        return "xiaomi.smoothTransition.fastZoomIn";
    }

    static /* synthetic */ String Hf() {
        return "xiaomi.remosaic.enabled";
    }

    static /* synthetic */ String Hg() {
        return "xiaomi.beauty.shoulderSlimRatio";
    }

    static /* synthetic */ String Ie() {
        return "xiaomi.smoothTransition.disablefallback";
    }

    static /* synthetic */ String If() {
        return "xiaomi.specshot.mode.enabled";
    }

    static /* synthetic */ String Ig() {
        return "xiaomi.beauty.legSlimRatio";
    }

    static /* synthetic */ String Je() {
        return "xiaomi.ai.add.enabled";
    }

    static /* synthetic */ String Jf() {
        return "xiaomi.distortion.distortioFpcData";
    }

    static /* synthetic */ String Jg() {
        return "xiaomi.beauty.oneKeySlimRatio";
    }

    static /* synthetic */ String Ke() {
        return "xiaomi.portrait.lighting";
    }

    static /* synthetic */ String Kf() {
        return "com.mediatek.control.capture.hintForRawReprocess";
    }

    static /* synthetic */ String Kg() {
        return "xiaomi.beauty.buttPlumpSlimRatio";
    }

    static /* synthetic */ String Le() {
        return "xiaomi.ai.segment.enabled";
    }

    static /* synthetic */ String Lf() {
        return "xiaomi.superResolution.cropRegionMtk";
    }

    static /* synthetic */ String Lg() {
        return "xiaomi.distortion.distortionLevelApplied";
    }

    static /* synthetic */ String Me() {
        return "xiaomi.faceGenderAndAge.enabled";
    }

    static /* synthetic */ String Mf() {
        return "com.mediatek.streamingfeature.pipDevices";
    }

    static /* synthetic */ String Mg() {
        return "xiaomi.distortion.ultraWideDistortionLevel";
    }

    static /* synthetic */ String Ne() {
        return "xiaomi.faceScore.enabled";
    }

    static /* synthetic */ String Nf() {
        return "com.mediatek.3afeature.aeMeteringMode";
    }

    static /* synthetic */ String Ng() {
        return "xiaomi.distortion.ultraWideDistortionEnable";
    }

    static /* synthetic */ String Oe() {
        return "xiaomi.device.orientation";
    }

    static /* synthetic */ String Of() {
        return "com.mediatek.configure.setting.initrequest";
    }

    static /* synthetic */ String Og() {
        return "xiaomi.snapshot.front.ScreenLighting.enabled";
    }

    static /* synthetic */ String Pe() {
        return "xiaomi.beauty.beautyLevelApplied";
    }

    static /* synthetic */ String Pf() {
        return "com.mediatek.configure.setting.proprietaryRequest";
    }

    static /* synthetic */ String Pg() {
        return "xiaomi.softlightMode.enabled";
    }

    static /* synthetic */ String Qe() {
        return "xiaomi.beauty.skinColorRatio";
    }

    static /* synthetic */ String Qf() {
        return "com.mediatek.multicamfeature.multiCamConfigScalerCropRegion";
    }

    static /* synthetic */ String Qg() {
        return "xiaomi.snapshot.backwardfetchframe.enabled";
    }

    static /* synthetic */ String Re() {
        return "com.vidhance.autozoom.select";
    }

    static /* synthetic */ String Rf() {
        return "com.mediatek.hdrfeature.hdrMode";
    }

    static /* synthetic */ String Rg() {
        return "org.codeaurora.qcamera3.iso_exp_priority.select_priority";
    }

    static /* synthetic */ String Se() {
        return "xiaomi.beauty.slimFaceRatio";
    }

    static /* synthetic */ String Sf() {
        return "xiaomi.aishutter.enabled";
    }

    static /* synthetic */ String Sg() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_exp_priority";
    }

    static /* synthetic */ String Te() {
        return "xiaomi.beauty.skinSmoothRatio";
    }

    static /* synthetic */ String Tf() {
        return "xiaomi.aishutter.existmotion";
    }

    static /* synthetic */ String Tg() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_value";
    }

    static /* synthetic */ String Ue() {
        return "xiaomi.beauty.enlargeEyeRatio";
    }

    static /* synthetic */ String Uf() {
        return "com.mediatek.multicamfeature.multiCamFeatureMode";
    }

    static /* synthetic */ String Ug() {
        return c.isMTKPlatform() ? "xiaomi.camera.awb.cct" : "com.qti.stats.awbwrapper.AWBCCT";
    }

    static /* synthetic */ String Ve() {
        return "xiaomi.beauty.noseRatio";
    }

    static /* synthetic */ String Vf() {
        return "com.mediatek.control.capture.early.notification.trigger";
    }

    static /* synthetic */ String Vg() {
        return "org.codeaurora.qcamera3.saturation.use_saturation";
    }

    static /* synthetic */ String We() {
        return "xiaomi.beauty.risoriusRatio";
    }

    static /* synthetic */ String Wf() {
        return "com.mediatek.cshotfeature.capture";
    }

    static /* synthetic */ String Wg() {
        return "org.codeaurora.qcamera3.sharpness.strength";
    }

    static /* synthetic */ String Xe() {
        return "xiaomi.beauty.lipsRatio";
    }

    static /* synthetic */ String Xf() {
        return "com.mediatek.control.capture.singleYuvNr";
    }

    static /* synthetic */ String Xg() {
        return "org.codeaurora.qcamera3.exposure_metering.exposure_metering_mode";
    }

    static /* synthetic */ String Ye() {
        return "xiaomi.beauty.chinRatio";
    }

    static /* synthetic */ String Yf() {
        return "com.mediatek.control.capture.highQualityYuv";
    }

    static /* synthetic */ String Yg() {
        return "org.quic.camera.recording.endOfStream";
    }

    static /* synthetic */ String Zd() {
        return "com.vidhance.autozoom.mode";
    }

    static /* synthetic */ String Ze() {
        return "xiaomi.beauty.neckRatio";
    }

    static /* synthetic */ String Zf() {
        return "com.mediatek.control.capture.ispMetaEnable";
    }

    static /* synthetic */ String Zg() {
        return "org.quic.camera2.streamconfigs.HDRVideoMode";
    }

    static /* synthetic */ String _d() {
        return "com.vidhance.autozoom.applyinpreview";
    }

    static /* synthetic */ String _e() {
        return "xiaomi.beauty.smileRatio";
    }

    static /* synthetic */ String _f() {
        return "com.mediatek.control.capture.ispTuningRequest";
    }

    static /* synthetic */ String _g() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String ae() {
        return "xiaomi.imageQuality.isHighQualityPreferred";
    }

    static /* synthetic */ String af() {
        return "xiaomi.beauty.slimNoseRatio";
    }

    static /* synthetic */ String ag() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String ah() {
        return "xiaomi.ai.asd.AiMoonEffectEnabled";
    }

    static /* synthetic */ String be() {
        return "xiaomi.video.recordControl";
    }

    static /* synthetic */ String bf() {
        return "com.vidhance.autozoom.unselect";
    }

    static /* synthetic */ String bg() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String ce() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String cf() {
        return "xiaomi.beauty.hairlineRatio";
    }

    static /* synthetic */ String cg() {
        return "xiaomi.ai.flaw.enabled";
    }

    private static <T> VendorTag<CaptureRequest.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureRequest.Key<T>>() {
            /* class com.android.camera2.vendortag.CaptureRequestVendorTags.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera2.vendortag.VendorTag
            public CaptureRequest.Key<T> create() {
                return CaptureRequestVendorTags.requestKey(getName(), cls);
            }

            @Override // com.android.camera2.vendortag.VendorTag
            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String de() {
        return "xiaomi.algoup.enabled";
    }

    static /* synthetic */ String df() {
        return "xiaomi.watermark.availableType";
    }

    static /* synthetic */ String dg() {
        return "xiaomi.ai.asd.dirtyEnable";
    }

    static /* synthetic */ String ee() {
        return "xiaomi.hdr.hdrChecker.enabled";
    }

    static /* synthetic */ String ef() {
        return "xiaomi.watermark.typeApplied";
    }

    static /* synthetic */ String eg() {
        return "xiaomi.pro.video.log.enabled";
    }

    static /* synthetic */ String fe() {
        return "xiaomi.hdr.hdrChecker.status";
    }

    static /* synthetic */ String ff() {
        return "xiaomi.watermark.time";
    }

    static /* synthetic */ String fg() {
        return "xiaomi.thermal.thermalLevel";
    }

    static /* synthetic */ String ge() {
        return "xiaomi.hdr.hdrChecker.sceneType";
    }

    static /* synthetic */ String gf() {
        return "xiaomi.watermark.face";
    }

    static /* synthetic */ String gg() {
        return "xiaomi.pro.video.movie.enabled";
    }

    static /* synthetic */ String he() {
        return "xiaomi.hdr.hdrChecker.adrc";
    }

    static /* synthetic */ String hf() {
        return "xiaomi.snapshotTorch.enabled";
    }

    static /* synthetic */ String hg() {
        return "xiaomi.pro.video.histogram.stats.enabled";
    }

    static /* synthetic */ String ie() {
        return "xiaomi.hdr.hdrMode";
    }

    /* renamed from: if  reason: not valid java name */
    static /* synthetic */ String m2if() {
        return "xiaomi.flip.enabled";
    }

    static /* synthetic */ String ig() {
        return "xiaomi.memory.shrinkMode";
    }

    static /* synthetic */ String je() {
        return "xiaomi.parallel.path";
    }

    static /* synthetic */ String jf() {
        return "xiaomi.burst.captureHint";
    }

    static /* synthetic */ String jg() {
        return "xiaomi.asd.enabled";
    }

    static /* synthetic */ String ke() {
        return "com.vidhance.autozoom.minimumscaling";
    }

    static /* synthetic */ String kf() {
        return "xiaomi.burst.shootFPS";
    }

    static /* synthetic */ String kg() {
        return "xiaomi.colorenhancement.enabled";
    }

    static /* synthetic */ String le() {
        return "xiaomi.parallel.enabled";
    }

    static /* synthetic */ String lf() {
        return "com.vidhance.autozoom.force_lock";
    }

    static /* synthetic */ String lg() {
        return "xiaomi.bokeh.hdrEnabled";
    }

    static /* synthetic */ String me() {
        return "xiaomi.hht.enabled";
    }

    static /* synthetic */ String mf() {
        return "com.vidhance.autozoom.center_offset";
    }

    static /* synthetic */ String mg() {
        return "xiaomi.sat.targetzoom";
    }

    static /* synthetic */ String ne() {
        return "xiaomi.node.hfr.deflicker.enabled";
    }

    static /* synthetic */ String nf() {
        return "com.vidhance.autozoom.scale_offset";
    }

    static /* synthetic */ String ng() {
        return "xiaomi.softlightMode.current";
    }

    static /* synthetic */ String oe() {
        return "xiaomi.superportrait.enabled";
    }

    static /* synthetic */ String of() {
        return "xiaomi.ai.asd.previewenabled";
    }

    static /* synthetic */ String og() {
        return "xiaomi.watermark.debug";
    }

    static /* synthetic */ String pe() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String pf() {
        return "xiaomi.ai.asd.sceneApplied";
    }

    static /* synthetic */ String pg() {
        return "xiaomi.flash.mode";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qe() {
        return "xiaomi.mfnr.enabled";
    }

    static /* synthetic */ String qf() {
        return "xiaomi.ai.asd.period";
    }

    static /* synthetic */ String qg() {
        return "xiaomi.statistics.faceRectangles";
    }

    static /* synthetic */ String re() {
        return "xiaomi.mfnr.frameNum";
    }

    static <T> CaptureRequest.Key<T> requestKey(String str, Class<T> cls) {
        try {
            if (requestConstructor == null) {
                requestConstructor = CaptureRequest.Key.class.getConstructor(String.class, cls.getClass());
                requestConstructor.setAccessible(true);
            }
            return requestConstructor.newInstance(str, cls);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }

    static /* synthetic */ String rf() {
        return "org.codeaurora.qcamera3.contrast.level";
    }

    static /* synthetic */ String rg() {
        return "xiaomi.panorama.p2s.enabled";
    }

    static /* synthetic */ String se() {
        return "xiaomi.swmf.enabled";
    }

    static /* synthetic */ String sf() {
        return "xiaomi.hfrPreview.isHFRPreview";
    }

    static /* synthetic */ String sg() {
        return "xiaomi.capturefusion.isFusionOn";
    }

    static /* synthetic */ String te() {
        return "xiaomi.bokeh.enabled";
    }

    static /* synthetic */ String tf() {
        return "org.codeaurora.qcamera3.ae_bracket.mode";
    }

    static /* synthetic */ String tg() {
        return "xiaomi.ai.misd.miaitof";
    }

    static /* synthetic */ String ue() {
        return "xiaomi.bokehrear.enabled";
    }

    static /* synthetic */ String uf() {
        return "xiaomi.multiframe.inputNum";
    }

    static /* synthetic */ String ug() {
        return "xiaomi.super.night.mode";
    }

    static /* synthetic */ String ve() {
        return "com.vidhance.autozoom.stop";
    }

    static /* synthetic */ String vf() {
        return "xiaomi.depurple.enabled";
    }

    static /* synthetic */ String vg() {
        return "xiaomi.super.night.target";
    }

    static /* synthetic */ String we() {
        return "xiaomi.bokeh.fNumberApplied";
    }

    static /* synthetic */ String wf() {
        return "xiaomi.MacroMode.enabled";
    }

    static /* synthetic */ String wg() {
        return "xiaomi.hdr.sr.enabled";
    }

    static /* synthetic */ String xe() {
        return "xiaomi.videoBokehParam.back";
    }

    static /* synthetic */ String xf() {
        return "xiaomi.watermark.custom";
    }

    static /* synthetic */ String xg() {
        return "xiaomi.beauty.eyeBrowDyeRatio";
    }

    static /* synthetic */ String ye() {
        return "xiaomi.videoBokehParam.front";
    }

    static /* synthetic */ String yf() {
        return "xiaomi.satIsZooming.satIsZooming";
    }

    static /* synthetic */ String yg() {
        return "xiaomi.beauty.pupilLineRatio";
    }

    static /* synthetic */ String ze() {
        return "xiaomi.videofilter.filterApplied";
    }

    static /* synthetic */ String zf() {
        return "xiaomi.burst.curReqIndex";
    }

    static /* synthetic */ String zg() {
        return "xiaomi.beauty.lipGlossRatio";
    }
}
