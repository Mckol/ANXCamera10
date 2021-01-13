package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.util.Log;
import b.c.a.c;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.camera2.vendortag.struct.AWBFrameControl;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.android.camera2.vendortag.struct.MarshalQueryableChiRect;
import com.android.camera2.vendortag.struct.MarshalQueryableSuperNightExif;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureResultVendorTags {
    public static final VendorTag<CaptureResult.Key<AECFrameControl>> AEC_FRAME_CONTROL = create(Ie.INSTANCE, AECFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Float>> AEC_LUX = create(C0149he.INSTANCE, Float.class);
    public static final VendorTag<CaptureResult.Key<AFFrameControl>> AF_FRAME_CONTROL = create(Ae.INSTANCE, AFFrameControl.class);
    public static final VendorTag<CaptureResult.Key<int[]>> AISHUT_EXIST_MOTION = create(Pe.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<Byte>> AI_HDR_DETECTED = create(bf.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Integer>> AI_SCENE_DETECTED = create(df.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> AI_SCENE_ENABLE = create(C0191oe.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Long>> ANCHOR_FRAME_TIMESTAMP = create(Id.INSTANCE, Long.class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_ACTIVE_OBJECTS = create(Ge.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_BOUNDS = create(Se.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_STABILIZED = create(C0137fe.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_ZOOMED = create(Ze.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_STABILIZED = create(C0237wd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_ZOOMED = create(Me.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_PAUSED_OBJECTS = create(Re.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_SELECTED_OBJECTS = create(Kd.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> AUTOZOOM_STATUS = create(hf.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_TARGET_BOUNDS_STABILIZED = create(Nd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_TARGET_BOUNDS_ZOOMED = create(C0255zd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<AWBFrameControl>> AWB_FRAME_CONTROL = create(af.INSTANCE, AWBFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BLUSHER = create(C0185ne.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BODY_SLIM = create(C0119ce.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BODY_SLIM_COUNT = create(Ke.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_CHIN = create(Bd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_ENLARGE_EYE = create(Ee.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_EYEBROW_DYE = create(C0250ye.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_HAIRLINE = create(C0161je.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_HEAD_SLIM = create(Od.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_JELLY_LIPS = create(Be.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_LEG_SLIM = create(Xd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<String>> BEAUTY_LEVEL = create(De.INSTANCE, String.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_LIPS = create(C0167ke.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_NECK = create(Vd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_NOSE = create(Md.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_PUPIL_LINE = create(Pd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_RISORIUS = create(C0243xd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SHOULDER_SLIM = create(Le.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SKIN_COLOR = create(Ed.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SKIN_SMOOTH = create(Yd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SLIM_FACE = create(C0143ge.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SLIM_NOSE = create(C0238we.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SMILE = create(Cif.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BUTT_SLIM = create(Sd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> CONTROL_ENABLE_REMOSAIC = create(Gd.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> CONTROL_ENABLE_SPECSHOT_DETECTED = create(Ud.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> CONTROL_ENABLE_SPECSHOT_MODE = create(Rd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> DEPURPLE = create(We.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> DISTORTION_FPC_DATA = create(Qe.INSTANCE, byte[].class);
    public static VendorTag<CaptureResult.Key<byte[]>> EXIF_INFO_VALUES = create(_d.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> EYE_LIGHT_STRENGTH = create(Ue.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> EYE_LIGHT_TYPE = create(Hd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> FAKE_SAT_ENABLE = create(C0107ae.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> FAST_ZOOM_RESULT = create(C0256ze.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> FRONT_SINGLE_CAMERA_BOKEH = create(Ve.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> HDR_CHECKER_ADRC = create(C0113be.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> HDR_CHECKER_EV_VALUES = create(Ad.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> HDR_CHECKER_SCENETYPE = create(C0203qe.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> HDR_MOTION_DETECTED = create(C0231vd.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<int[]>> HISTOGRAM_STATS = create(C0173le.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<Byte>> HISTOGRAM_STATS_ENABLED = create(C0232ve.INSTANCE, Byte.class);
    public static final CaptureResult.Key<Integer> ISO_VALUE = new CaptureResult.Key<>("xiaomi.algoup.iso_value", Integer.TYPE);
    public static final VendorTag<CaptureResult.Key<Integer>> IS_DEPTH_FOCUS = create(C0249yd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> IS_HDR_ENABLE = create(cf.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> IS_LLS_NEEDED = create(Je.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> IS_SR_ENABLE = create(Zd.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> LENS_DIRTY_DETECTED = create(Dd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> MFNR_ENABLED = create(Xe.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Rect[]>> MI_STATISTICS_FACE_RECTANGLES = create(C0197pe.INSTANCE, Rect[].class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> NON_SEMANTIC_SCENE = create(Te.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<int[]>> P2_KEY_NOTIFICATION_RESULT = create(Jd.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<Rect>> POST_PROCESS_CROP_REGION = create(C0131ee.INSTANCE, Rect.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> REAR_BOKEH_ENABLE = create(Ye.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> REMOSAIC_DETECTED = create(C0208re.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> SAT_DBG_INFO = create(C0179me.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Byte>> SAT_FALLBACKROLE = create(C0220te.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SAT_FALLBACK_DETECTED = create(Wd.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Byte>> SAT_FUSION_SHOT_PIPELINE_READY = create(C0125de.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Integer>> SAT_MATER_CAMERA_ID = create(Ce.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> SCENE_DETECTION_RESULT = create(ff.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> SEMANTIC_SCENE = create(C0226ue.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<Byte>> SENSOR_HDR_ENABLE = create(Td.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> STATE_SCENE = create(ef.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_AGE = create(Fd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_FACESCORE = create(Ld.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_GENDER = create(_e.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<byte[]>> STATISTICS_FACE_INFO = create(Oe.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_PROP = create(gf.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<byte[]>> SUPER_NIGHT_CHECKER_EV = create(C0244xe.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableSuperNightExif.SuperNightExif>> SUPER_NIGHT_EXIF = create(jf.INSTANCE, MarshalQueryableSuperNightExif.SuperNightExif.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SUPER_NIGHT_SCENE_ENABLED = create(Fe.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SW_MFNR_ENABLED = create(Ne.INSTANCE, Boolean.class);
    private static final String TAG = "CaptureResultVendorTags";
    public static final VendorTag<CaptureResult.Key<Byte>> ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(C0214se.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Integer>> ULTRA_WIDE_RECOMMENDED_RESULT = create(Qd.INSTANCE, Integer.class);
    public static final int VALUE_SAT_MATER_CAMERA_ID_TELE = 3;
    public static final int VALUE_SAT_MATER_CAMERA_ID_ULTRA_WIDE = 1;
    public static final int VALUE_SAT_MATER_CAMERA_ID_WIDE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_IDLE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_PROCESS = 1;
    public static final VendorTag<CaptureResult.Key<Integer>> VIDEO_RECORD_STATE = create(C0155ie.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> WHOLE_BODY_SLIM = create(He.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableChiRect.ChiRect>> ZOOM_MAP_RIO = create(Cd.INSTANCE, MarshalQueryableChiRect.ChiRect.class);
    private static Constructor<CaptureResult.Key> resultConstructor;

    static /* synthetic */ String Ae() {
        return "xiaomi.beauty.legSlimRatio";
    }

    static /* synthetic */ String Ag() {
        return "xiaomi.beauty.bodySlimCnt";
    }

    static /* synthetic */ String Be() {
        return "xiaomi.beauty.oneKeySlimRatio";
    }

    static /* synthetic */ String Bg() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String Ce() {
        return "xiaomi.beauty.buttPlumpSlimRatio";
    }

    static /* synthetic */ String Cg() {
        return "xiaomi.superResolution.cropRegionMtk";
    }

    static /* synthetic */ String De() {
        return "xiaomi.bokeh.enabled";
    }

    static /* synthetic */ String Dg() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String Ee() {
        return "xiaomi.remosaic.enabled";
    }

    static /* synthetic */ String Eg() {
        return "xiaomi.remosaic.detected";
    }

    static /* synthetic */ String Fe() {
        return "xiaomi.specshot.mode.detected";
    }

    static /* synthetic */ String Fg() {
        return "xiaomi.pro.video.histogram.stats.enabled";
    }

    static /* synthetic */ String Ge() {
        return "com.vidhance.autozoom.active_objects";
    }

    static /* synthetic */ String Gg() {
        return "xiaomi.ai.misd.SemanticScene";
    }

    static /* synthetic */ String He() {
        return "xiaomi.specshot.mode.enabled";
    }

    static /* synthetic */ String Hg() {
        return "xiaomi.ai.misd.NonSemanticScene";
    }

    static /* synthetic */ String Ie() {
        return "xiaomi.distortion.ultraWideDistortionLevel";
    }

    static /* synthetic */ String Ig() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String Je() {
        return "xiaomi.depurple.enabled";
    }

    static /* synthetic */ String Jg() {
        return "xiaomi.distortion.distortioFpcData";
    }

    static /* synthetic */ String Ke() {
        return "xiaomi.bokehrear.enabled";
    }

    static /* synthetic */ String Kg() {
        return "xiaomi.smoothTransition.masterCameraId";
    }

    static /* synthetic */ String Le() {
        return "xiaomi.mfnr.enabled";
    }

    static /* synthetic */ String Lg() {
        return "xiaomi.FakeSat.enabled";
    }

    static /* synthetic */ String Me() {
        return "xiaomi.swmf.enabled";
    }

    static /* synthetic */ String Mg() {
        return "xiaomi.smoothTransition.detected";
    }

    static /* synthetic */ String Ne() {
        return "xiaomi.video.recordState";
    }

    static /* synthetic */ String Ng() {
        return "xiaomi.sat.dbg.satDbgInfo";
    }

    static /* synthetic */ String Oe() {
        return "xiaomi.faceAnalyzeResult.result";
    }

    static /* synthetic */ String Og() {
        return "xiaomi.ai.misd.hdrmotionDetected";
    }

    static /* synthetic */ String Pe() {
        return "xiaomi.faceAnalyzeResult.age";
    }

    static /* synthetic */ String Pg() {
        return "xiaomi.ai.misd.SuperNightExif";
    }

    static /* synthetic */ String Qe() {
        return "xiaomi.faceAnalyzeResult.gender";
    }

    static /* synthetic */ String Qg() {
        return "com.qti.stats_control.is_lls_needed";
    }

    static /* synthetic */ String Re() {
        return "com.vidhance.autozoom.selected_objects";
    }

    static /* synthetic */ String Rg() {
        return "xiaomi.supernight.checker";
    }

    static /* synthetic */ String Se() {
        return "xiaomi.faceAnalyzeResult.score";
    }

    static /* synthetic */ String Sg() {
        return "xiaomi.smoothTransition.fallbackRole";
    }

    static /* synthetic */ String Te() {
        return "xiaomi.faceAnalyzeResult.prop";
    }

    static /* synthetic */ String Tg() {
        return "xiaomi.smoothTransition.mapROI";
    }

    static /* synthetic */ String Ue() {
        return "org.quic.camera2.statsconfigs.AECIsInsensorHDR";
    }

    static /* synthetic */ String Ug() {
        return "xiaomi.statistics.faceRectangles";
    }

    static /* synthetic */ String Ve() {
        return "xiaomi.scene.result";
    }

    static /* synthetic */ String Vg() {
        return "xiaomi.capturefusion.isPipelineReady";
    }

    static /* synthetic */ String We() {
        return c.isMTKPlatform() ? "com.xiaomi.statsconfigs.AecLux" : "com.qti.chi.statsaec.AecLux";
    }

    static /* synthetic */ String Wg() {
        return "xiaomi.mfnr.anchorTimeStamp";
    }

    static /* synthetic */ String Xe() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String Ye() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String Zd() {
        return "com.vidhance.autozoom.bounds";
    }

    static /* synthetic */ String Ze() {
        return "xiaomi.hdr.hdrDetected";
    }

    static /* synthetic */ String _d() {
        return "com.vidhance.autozoom.target_bounds_stabilized";
    }

    static /* synthetic */ String _e() {
        return "xiaomi.ai.add.lensDirtyDetected";
    }

    static /* synthetic */ String ae() {
        return "com.vidhance.autozoom.delayed_target_bounds_zoomed";
    }

    static /* synthetic */ String af() {
        return c.isMTKPlatform() ? "xiaomi.camera.awb.colorTemperature" : "org.quic.camera2.statsconfigs.AWBFrameControl";
    }

    static /* synthetic */ String be() {
        return "xiaomi.beauty.beautyLevelApplied";
    }

    static /* synthetic */ String bf() {
        return "com.vidhance.autozoom.paused_objects";
    }

    static /* synthetic */ String ce() {
        return "xiaomi.beauty.skinColorRatio";
    }

    static /* synthetic */ String cf() {
        return "org.quic.camera2.statsconfigs.AECFrameControl";
    }

    private static <T> VendorTag<CaptureResult.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureResult.Key<T>>() {
            /* class com.android.camera2.vendortag.CaptureResultVendorTags.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera2.vendortag.VendorTag
            public CaptureResult.Key<T> create() {
                return CaptureResultVendorTags.resultKey(getName(), cls);
            }

            @Override // com.android.camera2.vendortag.VendorTag
            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String de() {
        return "xiaomi.beauty.slimFaceRatio";
    }

    static /* synthetic */ String df() {
        return "org.quic.camera2.statsconfigs.AFFrameControl";
    }

    static /* synthetic */ String ee() {
        return "xiaomi.beauty.skinSmoothRatio";
    }

    static /* synthetic */ String ef() {
        return c.isMTKPlatform() ? "xiaomi.histogram.stats" : "org.codeaurora.qcamera3.histogram.stats";
    }

    static /* synthetic */ String fe() {
        return "xiaomi.beauty.enlargeEyeRatio";
    }

    static /* synthetic */ String ff() {
        return "xiaomi.aishutter.existmotion";
    }

    static /* synthetic */ String ge() {
        return "xiaomi.beauty.noseRatio";
    }

    static /* synthetic */ String gf() {
        return "com.mediatek.control.capture.next.ready";
    }

    static /* synthetic */ String he() {
        return "xiaomi.beauty.risoriusRatio";
    }

    static /* synthetic */ String hf() {
        return "org.quic.camera.isDepthFocus.isDepthFocus";
    }

    static /* synthetic */ String ie() {
        return "xiaomi.beauty.lipsRatio";
    }

    /* renamed from: if  reason: not valid java name */
    static /* synthetic */ String m3if() {
        return "xiaomi.smoothTransition.result";
    }

    static /* synthetic */ String je() {
        return "xiaomi.beauty.chinRatio";
    }

    static /* synthetic */ String jf() {
        return "xiaomi.hdr.hdrChecker";
    }

    static /* synthetic */ String ke() {
        return "com.vidhance.autozoom.target_bounds_zoomed";
    }

    static /* synthetic */ String kf() {
        return "xiaomi.hdr.hdrChecker.sceneType";
    }

    static /* synthetic */ String le() {
        return "xiaomi.beauty.neckRatio";
    }

    static /* synthetic */ String lf() {
        return "com.vidhance.autozoom.object_bounds_stabilized";
    }

    static /* synthetic */ String me() {
        return "xiaomi.beauty.smileRatio";
    }

    static /* synthetic */ String mf() {
        return "com.vidhance.autozoom.object_bounds_zoomed";
    }

    static /* synthetic */ String ne() {
        return "xiaomi.beauty.slimNoseRatio";
    }

    static /* synthetic */ String nf() {
        return "com.vidhance.autozoom.delayed_target_bounds_stabilized";
    }

    static /* synthetic */ String oe() {
        return "xiaomi.beauty.hairlineRatio";
    }

    static /* synthetic */ String pe() {
        return "xiaomi.beauty.eyeBrowDyeRatio";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qe() {
        return "xiaomi.beauty.pupilLineRatio";
    }

    static /* synthetic */ String re() {
        return "xiaomi.beauty.lipGlossRatio";
    }

    /* access modifiers changed from: private */
    public static <T> CaptureResult.Key<T> resultKey(String str, Class<T> cls) {
        try {
            if (resultConstructor == null) {
                resultConstructor = CaptureResult.Key.class.getConstructor(String.class, cls.getClass());
                resultConstructor.setAccessible(true);
            }
            return resultConstructor.newInstance(str, cls);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }

    static /* synthetic */ String se() {
        return "xiaomi.beauty.blushRatio";
    }

    static /* synthetic */ String te() {
        return "xiaomi.beauty.eyeLightType";
    }

    static /* synthetic */ String ue() {
        return "xiaomi.beauty.eyeLightStrength";
    }

    static /* synthetic */ String ve() {
        return "com.vidhance.autozoom.status";
    }

    static /* synthetic */ String we() {
        return "xiaomi.supernight.enabled";
    }

    static /* synthetic */ String xe() {
        return "xiaomi.beauty.headSlimRatio";
    }

    static /* synthetic */ String xg() {
        return "xiaomi.hdr.hdrChecker.adrc";
    }

    static /* synthetic */ String ye() {
        return "xiaomi.beauty.bodySlimRatio";
    }

    static /* synthetic */ String yg() {
        return "xiaomi.debugInfo.info";
    }

    static /* synthetic */ String ze() {
        return "xiaomi.beauty.shoulderSlimRatio";
    }

    static /* synthetic */ String zg() {
        return "xiaomi.ai.misd.ultraWideRecommended";
    }
}
