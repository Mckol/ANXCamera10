package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfiguration;
import android.util.Log;
import android.util.Size;
import b.c.a.c;
import com.android.camera2.vendortag.struct.SatFusionCalibrationData;
import com.android.camera2.vendortag.struct.SlowMotionVideoConfiguration;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CameraCharacteristicsVendorTags {
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> ADAPTIVE_SNAPSHOT_SIZE_IN_SAT_MODE_SUPPORTED = create(C0144h.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> AI_SCENE_AVAILABLE_MODES = create(B.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> BEAUTY_MAKEUP = create(X.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> BEAUTY_VERSION = create(C0138g.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> CAMERA_ROLE_ID = create(C.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<byte[]>> CAM_CALIBRATION_DATA = create(C0132f.INSTANCE, byte[].class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> COLOR_ENHANCE = create(C0108b.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> CONTROL_CAPTURE_ISP_TUNING_DATA_SIZE_FOR_YUV = create(C0163ka.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> CUSTOM_HFR_FPS_TABLE = create(C0193pa.INSTANCE, int[].class);
    public static VendorTag<CameraCharacteristics.Key<Byte>> DYNAMIC_60FPS_SUPPORTED = create(C0156j.INSTANCE, Byte.class);
    public static VendorTag<CameraCharacteristics.Key<Byte>> EIS_4K_60FPS_SUPPORTED = create(C0174m.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> EIS_PREVIEW_SUPPORTED = create(C0139ga.INSTANCE, Byte.class);
    public static VendorTag<CameraCharacteristics.Key<Integer[]>> EIS_QUALITY_SUPPORTED = create(C0186o.INSTANCE, Integer[].class);
    public static VendorTag<CameraCharacteristics.Key<Integer>> ENABLE_ZERO_DEGREE_ORIENTATION_IMAGE = create(C0162k.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> EXTRA_HIGH_SPEED_VIDEO_CONFIGURATIONS = create(C0133fa.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> EXTRA_HIGH_SPEED_VIDEO_NUMBER = create(Z.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<Size[]>> FAKE_SAT_JPEG_SIZE = create(C0239x.INSTANCE, Size[].class);
    public static final VendorTag<CameraCharacteristics.Key<Size[]>> FAKE_SAT_YUV_SIZE = create(C0151ia.INSTANCE, Size[].class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> FOVC_SUPPORTED = create(W.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> HDR_KEY_AVAILABLE_HDR_MODES_VIDEO = create(C0227v.INSTANCE, int[].class);
    public static VendorTag<CameraCharacteristics.Key<Integer>> HIGHQUALITY_PREFERRED_SUPPORTED = create(C0187oa.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> IS_QCFA_SENSOR = create(T.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> LOG_FORMAT = create(C0199qa.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> MACRO_HDR_MUTEX = create(H.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> MACRO_ZOOM_FEATURE = create(C0102a.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> MFNR_BOKEH_SUPPORTED = create(L.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Float>> MI_ALGO_ASD_VERSION = create(A.INSTANCE, Float.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> MI_DUAL_BOKEH_VENDOR = create(C0126e.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> P2_KEY_SUPPORT_MODES = create(C0215t.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> PARALLEL_CAMERA_DEVICE = create(C0245y.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> PORTRAIT_LIGHTING_VERSION = create(C0103aa.INSTANCE, Integer.class);
    public static final int PORTRAIT_LIGHTING_VERSION_1 = 1;
    public static final int PORTRAIT_LIGHTING_VERSION_2 = 2;
    public static final VendorTag<CameraCharacteristics.Key<Rect>> QCFA_ACTIVE_ARRAY_SIZE = create(C0127ea.INSTANCE, Rect.class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> QCFA_STREAM_CONFIGURATIONS = create(O.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<SatFusionCalibrationData[]>> SAT_FUSION_SHOT_CALIBRATION_INFO = create(C0204ra.INSTANCE, SatFusionCalibrationData[].class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> SCALER_AVAILABLE_LIMIT_STREAM_CONFIGURATIONS = create(I.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<Float>> SCALER_AVAILABLE_MIN_DIGITAL_ZOOM = create(C0169la.INSTANCE, Float.class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> SCALER_AVAILABLE_SR_STREAM_CONFIGURATIONS = create(C0145ha.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> SCALER_AVAILABLE_STREAM_CONFIGURATIONS = create(Q.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> SCREEN_LIGHT_BRIGHTNESS = create(C0198q.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> SENSOR_DEPURPLE_DISABLE = create(C0181na.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<SlowMotionVideoConfiguration[]>> SLOW_MOTION_VIDEO_CONFIGURATIONS = create(G.INSTANCE, SlowMotionVideoConfiguration[].class);
    public static VendorTag<CameraCharacteristics.Key<Integer[]>> SPECIAL_VIDEOSIZE = create(M.INSTANCE, Integer[].class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> SPECSHOT_MODE = create(E.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> SUPER_PORTRAIT_SUPPORTED = create(P.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> SUPPORT_ANCHOR_FRAME = create(C0251z.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> SUPPORT_FLASH_HDR = create(C0192p.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> SUPPORT_MOON_AUTO_FOCUS = create(C0157ja.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> SUPPORT_NEAR_RANGE_MODE = create(C0115ca.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer[]>> SUPPORT_PORTRAIT_LIGHTING_ARRAY = create(S.INSTANCE, Integer[].class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> SUPPORT_SAT_FUSION_SHOT = create(C0109ba.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> SUPPORT_SAT_PIP = create(C0120d.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> SUPPORT_VIDEO_HDR10 = create(Y.INSTANCE, Integer.class);
    private static final String TAG = "CameraCharacteristicsVendorTags";
    public static final VendorTag<CameraCharacteristics.Key<Byte>> TELE_OIS_SUPPORTED = create(K.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> TRIPARTITE_LIGHT = create(C0121da.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_BEAUTY = create(C0209s.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_BEAUTY_FORCE_EIS = create(r.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_BOKEH_ADJUEST = create(C0221u.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_BOKEH_FRONT_ADJUEST = create(C0150i.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> VIDEO_COLOR_BOKEH_VERSION = create(J.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_COLOR_RENTENTION_BACK = create(C0233w.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_COLOR_RENTENTION_FRONT = create(D.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_FILTER = create(N.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_MIMOVIE = create(C0114c.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> XIAOMI_AISHUTTER_SUPPORTED = create(U.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> XIAOMI_AI_COLOR_CORRECTION_VERSION = create(C0180n.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> XIAOMI_PRECAPTUREAF_SUPPORTED = create(C0168l.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> XIAOMI_SCALER_HEIC_STREAM_CONFIGURATIONS = create(C0175ma.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> XIAOMI_SENSOR_INFO_SENSITIVITY_RANGE = create(F.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> XIAOMI_YUV_FORMAT = create(V.INSTANCE, Integer.class);
    private static Constructor<CameraCharacteristics.Key> characteristicsConstructor;

    static /* synthetic */ String Ae() {
        return "com.xiaomi.aishutter.supported";
    }

    static /* synthetic */ String Be() {
        return "com.xiaomi.precaptureaf.supported";
    }

    static /* synthetic */ String Ce() {
        return "xiaomi.imageQuality.available";
    }

    static /* synthetic */ String De() {
        return "com.mediatek.control.capture.early.notification.support";
    }

    static /* synthetic */ String Ee() {
        return "xiaomi.ai.misd.MiAlgoAsdVersion";
    }

    static /* synthetic */ String Fe() {
        return "xiaomi.sensorDepurple.disable";
    }

    static /* synthetic */ String Ge() {
        return "com.xiaomi.camera.supportedfeatures.videoBokehFront";
    }

    static /* synthetic */ String He() {
        return "com.xiaomi.camera.supportedfeatures.TeleOisSupported";
    }

    static /* synthetic */ String Ie() {
        return "com.xiaomi.cameraid.role.cameraId";
    }

    static /* synthetic */ String Je() {
        return "com.xiaomi.camera.supportedfeatures.superportraitSupported";
    }

    static /* synthetic */ String Ke() {
        return "com.xiaomi.camera.supportedfeatures.videomimovie";
    }

    static /* synthetic */ String Le() {
        return "com.xiaomi.camera.supportedfeatures.videologformat";
    }

    static /* synthetic */ String Me() {
        return "com.xiaomi.camera.supportedfeatures.3rdLightWeightSupported";
    }

    static /* synthetic */ String Ne() {
        return "com.xiaomi.camera.supportedfeatures.isMacroMutexWithHdr";
    }

    static /* synthetic */ String Oe() {
        return "xiaomi.scaler.availableHeicStreamConfigurations";
    }

    static /* synthetic */ String Pe() {
        return "xiaomi.yuv.format";
    }

    static /* synthetic */ String Qe() {
        return "com.xiaomi.camera.supportedfeatures.AIEnhancementVersion";
    }

    static /* synthetic */ String Re() {
        return "com.xiaomi.camera.supportedfeatures.videoColorRetentionFront";
    }

    static /* synthetic */ String Se() {
        return "xiaomi.sensor.info.sensitivityRange";
    }

    static /* synthetic */ String Te() {
        return "com.xiaomi.camera.supportedfeatures.colorenhancement";
    }

    static /* synthetic */ String Ue() {
        return "com.xiaomi.camera.supportedfeatures.parallelCameraDevice";
    }

    static /* synthetic */ String Ve() {
        return "xiaomi.ai.supportedMoonAutoFocus";
    }

    static /* synthetic */ String We() {
        return "com.xiaomi.camera.supportedfeatures.specshot";
    }

    static /* synthetic */ String Xe() {
        return "xiaomi.hdr.supportedFlashHdr";
    }

    static /* synthetic */ String Ye() {
        return "com.xiaomi.camera.supportedfeatures.bokehVendorID";
    }

    static /* synthetic */ String Zd() {
        return c.isMTKPlatform() ? "com.xiaomi.ai.asd.availableSceneMode" : "xiaomi.ai.asd.availableSceneMode";
    }

    static /* synthetic */ String Ze() {
        return "xiaomi.capturefusion.supportCPFusion";
    }

    static /* synthetic */ String _d() {
        return c.isMTKPlatform() ? "com.xiaomi.qcfa.supported" : "org.codeaurora.qcamera3.quadra_cfa.is_qcfa_sensor";
    }

    static /* synthetic */ String _e() {
        return "com.xiaomi.camera.supportedfeatures.satPip";
    }

    static /* synthetic */ String ae() {
        return "org.codeaurora.qcamera3.additional_hfr_video_sizes.hfr_video_size";
    }

    static /* synthetic */ String af() {
        return "com.xiaomi.camera.dualcal.info.dataInfo";
    }

    static /* synthetic */ String be() {
        return c.isMTKPlatform() ? "com.mediatek.streamingfeature.availableHfpsMaxResolutions" : "org.quic.camera2.customhfrfps.info.CustomHFRFpsTable";
    }

    static /* synthetic */ String bf() {
        return "com.xiaomi.camera.supportedfeatures.videoColorRetentionBack";
    }

    static /* synthetic */ String ce() {
        return c.isMTKPlatform() ? "com.xiaomi.scaler.availableStreamConfigurations" : "xiaomi.scaler.availableStreamConfigurations";
    }

    static /* synthetic */ String cf() {
        return "com.xiaomi.fakesat.FakeSatYuvSize";
    }

    /* access modifiers changed from: private */
    public static <T> CameraCharacteristics.Key<T> characteristicsKey(String str, Class<T> cls) {
        try {
            if (characteristicsConstructor == null) {
                characteristicsConstructor = CameraCharacteristics.Key.class.getConstructor(String.class, cls.getClass());
                characteristicsConstructor.setAccessible(true);
            }
            return characteristicsConstructor.newInstance(str, cls);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }

    private static <T> VendorTag<CameraCharacteristics.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CameraCharacteristics.Key<T>>() {
            /* class com.android.camera2.vendortag.CameraCharacteristicsVendorTags.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera2.vendortag.VendorTag
            public CameraCharacteristics.Key<T> create() {
                return CameraCharacteristicsVendorTags.characteristicsKey(getName(), cls);
            }

            @Override // com.android.camera2.vendortag.VendorTag
            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String de() {
        return "com.xiaomi.scaler.availableMinDigitalZoom";
    }

    static /* synthetic */ String df() {
        return "com.xiaomi.fakesat.FakeSatJpegSize";
    }

    static /* synthetic */ String ee() {
        return c.isMTKPlatform() ? "com.xiaomi.scaler.availableLimitStreamConfigurations" : "xiaomi.scaler.availableLimitStreamConfigurations";
    }

    static /* synthetic */ String ef() {
        return "xiaomi.videohdrmode.value";
    }

    static /* synthetic */ String fe() {
        return "org.codeaurora.qcamera3.quadra_cfa.activeArraySize";
    }

    static /* synthetic */ String ff() {
        return "xiaomi.videosize.CustomSizes";
    }

    static /* synthetic */ String ge() {
        return "org.codeaurora.qcamera3.quadra_cfa.availableStreamConfigurations";
    }

    static /* synthetic */ String gf() {
        return "com.xiaomi.camera.supportedfeatures.bokehRelightVerion";
    }

    static /* synthetic */ String he() {
        return c.isMTKPlatform() ? "com.xiaomi.scaler.availableSuperResolutionStreamConfigurations" : "xiaomi.scaler.availableSuperResolutionStreamConfigurations";
    }

    static /* synthetic */ String hf() {
        return "com.xiaomi.camera.supportedfeatures.bokehRelightModes";
    }

    static /* synthetic */ String ie() {
        return "com.xiaomi.camera.algoup.dualCalibrationData";
    }

    /* renamed from: if  reason: not valid java name */
    static /* synthetic */ String m1if() {
        return "xiaomi.smoothTransition.nearRangeMode";
    }

    static /* synthetic */ String je() {
        return c.isMTKPlatform() ? "com.xiaomi.capabilities.videoStabilization.previewSupported" : "xiaomi.capabilities.videoStabilization.previewSupported";
    }

    static /* synthetic */ String jf() {
        return "xiaomi.capabilities.quick_view_support";
    }

    static /* synthetic */ String ke() {
        return "com.xiaomi.camera.supportedfeatures.videofilter";
    }

    static /* synthetic */ String kf() {
        return "com.xiaomi.camera.supportedfeatures.videobeautyeis";
    }

    static /* synthetic */ String le() {
        return c.isMTKPlatform() ? "com.xiaomi.flash.screenLight.brightness" : "xiaomi.flash.screenLight.brightness";
    }

    static /* synthetic */ String lf() {
        return "com.xiaomi.camera.supportedfeatures.colorBokehVersion";
    }

    static /* synthetic */ String me() {
        return c.isMTKPlatform() ? "com.xiaomi.capabilities.mfnr_bokeh_supported" : "xiaomi.capabilities.mfnr_bokeh_supported";
    }

    static /* synthetic */ String mf() {
        return "com.xiaomi.camera.supportedfeatures.videoBeauty";
    }

    static /* synthetic */ String ne() {
        return "xiaomi.capabilities.macro_zoom_feature";
    }

    static /* synthetic */ String nf() {
        return "com.xiaomi.camera.supportedfeatures.beautyMakeup";
    }

    static /* synthetic */ String oe() {
        return "com.xiaomi.camera.supportedfeatures.fovcEnable";
    }

    static /* synthetic */ String pe() {
        return "com.xiaomi.camera.supportedfeatures.beautyVersion";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qe() {
        return c.isMTKPlatform() ? "com.xiaomi.capabilities.satAdaptiveSnapshotSizeSupported" : "xiaomi.capabilities.satAdaptiveSnapshotSizeSupported";
    }

    static /* synthetic */ String re() {
        return "org.codeaurora.qcamera3.additional_hfr_video_sizes.valid_number";
    }

    static /* synthetic */ String se() {
        return "xiaomi.capabilities.videoStabilization.60fpsSupported";
    }

    static /* synthetic */ String te() {
        return c.isMTKPlatform() ? "com.xiaomi.capabilities.videoStabilization.quality" : "xiaomi.capabilities.videoStabilization.quality";
    }

    static /* synthetic */ String ue() {
        return "com.xiaomi.gpu.enableGPURotation";
    }

    static /* synthetic */ String ve() {
        return "com.xiaomi.camera.supportedfeatures.videoBokeh";
    }

    static /* synthetic */ String we() {
        return "xiaomi.capabilities.videoStabilization.60fpsDynamicSupported";
    }

    static /* synthetic */ String xe() {
        return "com.mediatek.smvrfeature.availableSmvrModes";
    }

    static /* synthetic */ String ye() {
        return "com.mediatek.control.capture.ispMetaSizeForYuv";
    }

    static /* synthetic */ String ze() {
        return "com.mediatek.hdrfeature.availableHdrModesVideo";
    }
}
