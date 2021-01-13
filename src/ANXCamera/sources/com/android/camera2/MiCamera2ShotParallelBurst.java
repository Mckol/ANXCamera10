package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.SystemProperties;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.MemoryHelper;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.HdrEvValue;
import com.android.camera2.vendortag.struct.SuperNightEvValue;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MiCamera2ShotParallelBurst extends MiCamera2ShotParallel<ParallelTaskData> {
    private static final String TAG = "ShotParallelBurst";
    private int mAlgoType = 0;
    private boolean mAnchorFrame;
    private long mCaptureTimestamp = -1;
    private int mCompletedNum;
    private boolean mFirstNum;
    private int mHdrCheckerAdrc;
    private int[] mHdrCheckerEvValue;
    private int mHdrCheckerSceneType;
    private int mHdrType;
    private boolean mIsHdrBokeh;
    private boolean mIsHdrSR;
    private boolean mIsSatFusionShotEnabled;
    private int mMultiFrameNum;
    private volatile boolean mNeedDoAnchorFrame = true;
    private final int mOperationMode;
    private CameraSize mPreviewSize;
    private int mSequenceNum;
    private boolean mShouldDoMFNR;
    private boolean mShouldDoSR;
    private boolean mSingleCaptureForHDRplusMFNR;
    private SuperNightEvValue mSuperNightValue;
    private final boolean mUseParallelVtCam;

    public MiCamera2ShotParallelBurst(MiCamera2 miCamera2, CaptureResult captureResult, boolean z) {
        super(miCamera2);
        this.mUseParallelVtCam = z;
        this.mPreviewCaptureResult = captureResult;
        this.mOperationMode = this.mMiCamera.getCapabilities().getOperatingMode();
    }

    static /* synthetic */ int access$1008(MiCamera2ShotParallelBurst miCamera2ShotParallelBurst) {
        int i = miCamera2ShotParallelBurst.mCompletedNum;
        miCamera2ShotParallelBurst.mCompletedNum = i + 1;
        return i;
    }

    private void applyAlgoParameter(CaptureRequest.Builder builder, int i, int i2) {
        if (i2 == 1) {
            applyHdrParameter(builder, i);
        } else if (i2 == 2) {
            applyClearShotParameter(builder);
        } else if (i2 != 3) {
            if (i2 == 7) {
                MiCameraCompat.applySwMfnrEnable(builder, this.mShouldDoMFNR);
                MiCameraCompat.applyMfnrEnable(builder, false);
                MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
                MiCameraCompat.applyHHT(builder, true);
                Log.i(TAG, "HHT algo in applyAlgoParameter");
            } else if (i2 != 9) {
                if (i2 == 10) {
                    applySuperNightParameter(builder, i);
                }
            }
            applyLowLightBokehParameter(builder);
        } else {
            applySuperResolutionParameter(builder, i);
        }
        if (c.isMTKPlatform()) {
            MiCameraCompat.copyAiSceneFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
        } else if (isIn3OrMoreSatMode()) {
            CaptureRequestBuilder.applySmoothTransition(builder, this.mMiCamera.getCapabilities(), false);
            CaptureRequestBuilder.applySatFallback(builder, this.mMiCamera.getCapabilities(), false);
        }
    }

    private void applyClearShotParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applySwMfnrEnable(builder, this.mShouldDoMFNR);
        MiCameraCompat.applyMfnrEnable(builder, false);
        if (c.Xm() || c.Vg) {
            CompatibilityUtils.setZsl(builder, true);
        }
    }

    private void applyHdrParameter(CaptureRequest.Builder builder, int i) {
        CameraCapabilities capabilities;
        if (i <= this.mSequenceNum) {
            MiCameraCompat.applyMultiFrameIndex(builder, i + 1);
            MiCameraCompat.applyMultiFrameCount(builder, this.mSequenceNum);
            if (!DataRepository.dataItemFeature().ll()) {
                if (this.mIsHdrBokeh) {
                    MiCameraCompat.applyHdrBracketMode(builder, (byte) (this.mHdrCheckerEvValue[i] < 0 ? 1 : 0));
                } else {
                    MiCameraCompat.applyHdrBracketMode(builder, (byte) 1);
                }
            }
            if (!DataRepository.dataItemFeature().ll() || !this.mMiCamera.getCameraConfigs().isHDREnabled()) {
                MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
            } else {
                Log.e(TAG, "[ALGOUP|MMCAMERA] Algo Up HDR!!!!");
                MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum * 2);
                CaptureRequestBuilder.applyAELock(builder, true);
            }
            if (c.isMTKPlatform() || c.Zg || c._g || c.hh || c.bh || c.dh || c.eh) {
                CaptureRequestBuilder.applyAELock(builder, true);
            }
            builder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(this.mHdrCheckerEvValue[i]));
            MiCameraCompat.applyHdrParameter(builder, Integer.valueOf(this.mHdrCheckerSceneType), Integer.valueOf(this.mHdrCheckerAdrc));
            MiCameraCompat.applyMiHDRSR(builder, false);
            boolean z = !c.Zg ? !DataRepository.dataItemFeature().Yk() ? (c.eh || c.mh) && this.mHdrCheckerEvValue[i] == 0 : this.mHdrCheckerEvValue[i] == 0 : this.mHdrCheckerEvValue[i] >= 0;
            boolean z2 = (this.mMiCamera.getSatMasterCameraId() == 2 || this.mMiCamera.getSatMasterCameraId() == 1) ? true : this.mMiCamera.getSatMasterCameraId() == 3 ? c.hh : false;
            boolean z3 = this.mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getFrontCameraId() && (c.dh || c.eh);
            if (z && z2 && isIn3OrMoreSatMode() && this.mSequenceNum < 4) {
                Log.d(TAG, "applyHdrParameter enable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, true);
            } else if (z && z3 && this.mSequenceNum < 4) {
                Log.d(TAG, "applyHdrParameter enable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, true);
            } else if (this.mSingleCaptureForHDRplusMFNR) {
                Log.d(TAG, "applyHdrParameter enable mfnr");
                MiCameraCompat.applyMfnrEnable(builder, true);
            } else if (z && z2 && c.mh) {
                Log.d(TAG, "applyHdrParameter enable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, true);
            } else if (!z || !this.mIsHdrBokeh) {
                Log.d(TAG, "applyHdrParameter disable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, false);
            } else {
                Log.d(TAG, "applyHdrParameter enable mfnr EV = " + this.mHdrCheckerEvValue[i]);
                MiCameraCompat.applyMfnrEnable(builder, true);
            }
            if (this.mIsHdrBokeh && (capabilities = this.mMiCamera.getCapabilities()) != null && capabilities.isSupportHdrBokeh()) {
                MiCameraCompat.applyHdrBokeh(builder, true);
            }
            if (DataRepository.dataItemFeature().Ol()) {
                int i2 = this.mHdrType;
                if (i2 == 0) {
                    Log.d(TAG, "enable isp tuning capture hint for HDR");
                    MiCameraCompat.applyIspTuningHint(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_HINT_FOR_ISP_TUNING_HDR);
                } else if (i2 == 1) {
                    Log.d(TAG, "enable isp tuning capture hint for LLHDR");
                    MiCameraCompat.applyIspTuningHint(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_HINT_FOR_ISP_TUNING_LLHDR);
                }
            }
        } else {
            throw new RuntimeException("wrong request index " + i);
        }
    }

    private void applyLowLightBokehParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
        MiCameraCompat.applySwMfnrEnable(builder, false);
        MiCameraCompat.applyMfnrEnable(builder, false);
    }

    private void applySuperNightParameter(CaptureRequest.Builder builder, int i) {
        if (i <= this.mSequenceNum) {
            Log.d(TAG, "applySuperNightParameter: requestIndex > " + i + " | ev = " + this.mSuperNightValue.getValue()[i]);
            if (c.isMTKPlatform()) {
                CaptureRequestBuilder.applyAELock(builder, true);
            } else if (CameraSettings.isFrontCamera()) {
                MiCameraCompat.applyHdrBracketMode(builder, (byte) 1);
            }
            MiCameraCompat.applyMultiFrameInputNum(builder, this.mSequenceNum);
            builder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(this.mSuperNightValue.getValue()[i]));
            MiCameraCompat.applySwMfnrEnable(builder, false);
            MiCameraCompat.applyMfnrEnable(builder, false);
            return;
        }
        throw new RuntimeException("wrong request index " + i);
    }

    private void applySuperResolutionParameter(CaptureRequest.Builder builder, int i) {
        MiCameraCompat.applyMultiFrameIndex(builder, i + 1);
        MiCameraCompat.applyMultiFrameCount(builder, this.mSequenceNum);
        MiCameraCompat.applyMultiFrameInputNum(builder, this.mMultiFrameNum);
        MiCameraCompat.applyMfnrEnable(builder, false);
        MiCameraCompat.applyHDR(builder, false);
        MiCameraCompat.applySuperResolution(builder, true);
        CaptureRequestBuilder.applyAELock(builder, true);
        CaptureRequestBuilder.applyAWBLock(builder, true);
        if (c.isMTKPlatform() && DataRepository.dataItemFeature().Ol()) {
            Log.d(TAG, "enable isp tuning capture hint for MFSR");
            MiCameraCompat.applyIspTuningHint(builder, CaptureRequestVendorTags.CONTROL_CAPTURE_HINT_FOR_ISP_TUNING_MFSR);
            MiCameraCompat.applyNoiseReduction(builder, false);
            MiCameraCompat.applyHighQualityReprocess(builder, false);
        }
        if (this.mHdrCheckerEvValue == null || !this.mIsHdrSR) {
            MiCameraCompat.applyMiHDRSR(builder, false);
            return;
        }
        MiCameraCompat.applyMiHDRSR(builder, true);
        MiCameraCompat.applyHDR(builder, false);
        if (this.mHdrCheckerEvValue[i] == 0) {
            MiCameraCompat.applySuperResolution(builder, true);
        } else {
            MiCameraCompat.applySuperResolution(builder, false);
        }
        builder.set(CaptureRequest.CONTROL_AE_LOCK, true);
        Log.d(TAG, String.format(Locale.ENGLISH, "HdrSrEv[%d]=%d", Integer.valueOf(i), Integer.valueOf(this.mHdrCheckerEvValue[i])));
        builder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(this.mHdrCheckerEvValue[i]));
        MiCameraCompat.applyHdrBracketMode(builder, (byte) 1);
    }

    private boolean doAnchorFrameAsThumbnail() {
        if (!this.mMiCamera.isModuleAnchorFrame()) {
            Log.d(TAG, "anchor frame do not enable");
            return false;
        }
        int i = this.mAlgoType;
        if (i == 3) {
            Log.d(TAG, "SR disable anchor frame");
            return false;
        } else if (i == 1) {
            Log.d(TAG, "HDR enable anchor frame");
            return true;
        } else if (i != 10) {
            return true;
        } else {
            Log.d(TAG, "super night disable anchor frame");
            return false;
        }
    }

    private int getGroupShotMaxImage() {
        Face[] faceArr = (Face[]) this.mPreviewCaptureResult.get(CaptureResult.STATISTICS_FACES);
        return Util.clamp((faceArr != null ? faceArr.length : 0) + 1, 2, 4);
    }

    private int getGroupShotNum() {
        if (Util.isMemoryRich(CameraAppImpl.getAndroidContext())) {
            return getGroupShotMaxImage();
        }
        Log.w(TAG, "getGroupShotNum: low memory");
        return 2;
    }

    private boolean isUpdateHDRCheckerValues() {
        return getMagneticDetectedCallback() == null || !getMagneticDetectedCallback().isLockHDRChecker(TAG);
    }

    private void prepareClearShot(int i) {
        if (c.Xm()) {
            this.mSequenceNum = 10;
            this.mMultiFrameNum = 10;
            return;
        }
        this.mSequenceNum = 5;
        this.mMultiFrameNum = 5;
    }

    private void prepareGroupShot() {
        int groupShotNum = getGroupShotNum();
        this.mSequenceNum = groupShotNum;
        this.mMultiFrameNum = groupShotNum;
    }

    private void prepareHHT() {
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder == null || localBinder.isIdle() || this.mMiCamera.getCameraConfigs().isAiASDEnabled() || this.mMiCamera.getCameraConfigs().getBeautyValues().isSmoothLevelOn()) {
            this.mSequenceNum = 5;
            this.mMultiFrameNum = 5;
            Log.i(TAG, "hht(5 -> 1)");
            return;
        }
        this.mSequenceNum = 3;
        this.mMultiFrameNum = 3;
        Log.i(TAG, "switch to quick shot hht(3 -> 1)");
    }

    private void prepareHdr() {
        if (this.mMiCamera.getCameraConfigs().getHDRStatus().isFlashHDR()) {
            Log.d(TAG, "prepareHDR night hdr ev_value:" + Arrays.toString(this.mHdrCheckerEvValue));
        }
        if (this.mSingleCaptureForHDRplusMFNR) {
            this.mSequenceNum = 1;
            this.mMultiFrameNum = 1;
            this.mHdrCheckerEvValue = new int[]{0};
        } else {
            HdrEvValue hdrEvValue = new HdrEvValue(CaptureResultParser.getHdrCheckerValues(this.mPreviewCaptureResult));
            this.mHdrType = hdrEvValue.getHdrType();
            int sequenceNum = hdrEvValue.getSequenceNum();
            this.mSequenceNum = sequenceNum;
            this.mMultiFrameNum = sequenceNum;
            this.mHdrCheckerEvValue = hdrEvValue.getHdrCheckerEvValue();
        }
        if (isUpdateHDRCheckerValues() || this.mMiCamera.getCameraConfigs().getHdrCheckerEvValue() == null) {
            this.mHdrCheckerSceneType = CaptureResultParser.getHdrCheckerSceneType(this.mPreviewCaptureResult);
            this.mHdrCheckerAdrc = CaptureResultParser.getHdrCheckerAdrc(this.mPreviewCaptureResult);
            Log.d(TAG, "prepareHdr: scene = " + this.mHdrCheckerSceneType + " adrc = " + this.mHdrCheckerAdrc);
            this.mMiCamera.getCameraConfigs().setHdrCheckerEvValue(this.mHdrCheckerEvValue);
            this.mMiCamera.getCameraConfigs().setHdrCheckerSceneType(this.mHdrCheckerSceneType);
            this.mMiCamera.getCameraConfigs().setHdrCheckerAdrc(this.mHdrCheckerAdrc);
            return;
        }
        Log.d(TAG, "hdr checker values not update：");
        this.mHdrCheckerEvValue = this.mMiCamera.getCameraConfigs().getHdrCheckerEvValue();
        this.mHdrCheckerSceneType = this.mMiCamera.getCameraConfigs().getHdrCheckerSceneType();
        this.mHdrCheckerAdrc = this.mMiCamera.getCameraConfigs().getHdrCheckerAdrc();
        StringBuilder sb = new StringBuilder();
        sb.append("prepareHdr: scene = ");
        sb.append(this.mHdrCheckerSceneType);
        sb.append(",adrc = ");
        sb.append(this.mHdrCheckerAdrc);
        sb.append(",EvValue = ");
        int[] iArr = this.mHdrCheckerEvValue;
        sb.append(iArr != null ? Arrays.toString(iArr) : null);
        Log.d(TAG, sb.toString());
    }

    private void prepareLowLightBokeh() {
        this.mSequenceNum = 6;
        this.mMultiFrameNum = 6;
    }

    private void prepareSR(boolean z) {
        if (z) {
            HdrEvValue hdrEvValue = new HdrEvValue(CaptureResultParser.getHdrCheckerValues(this.mPreviewCaptureResult), z);
            Log.d(TAG, "hdr ev value is " + hdrEvValue);
            this.mSequenceNum = hdrEvValue.getSequenceNum();
            this.mHdrCheckerEvValue = hdrEvValue.getHdrCheckerEvValue();
            int[] iArr = this.mHdrCheckerEvValue;
            if (iArr != null) {
                this.mSequenceNum = iArr.length;
            }
            this.mMultiFrameNum = (this.mSequenceNum - 10) + 1;
            return;
        }
        int i = SystemProperties.getInt("camera.sr.framecount", DataRepository.dataItemFeature().Rh());
        this.mSequenceNum = i;
        this.mMultiFrameNum = i;
        Log.d(TAG, "prepareSR: captureNum=" + this.mSequenceNum);
    }

    private void prepareSuperNight() {
        this.mSuperNightValue = SuperNightEvValue.parseSuperNightEvValue(CaptureResultParser.getSuperNightCheckerEv(this.mPreviewCaptureResult), SystemProperties.get("camera.debug.superlowlight"), CameraSettings.isFrontCamera());
        Log.d(TAG, "prepareSuperNight: mSuperNightValue >> " + this.mSuperNightValue.toString());
        int sequenceNum = this.mSuperNightValue.getSequenceNum();
        this.mSequenceNum = sequenceNum;
        this.mMultiFrameNum = sequenceNum;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotParallelBurst.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                MiCamera2ShotParallelBurst.access$1008(MiCamera2ShotParallelBurst.this);
                if (!DataRepository.dataItemFeature().ll() || !MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().isHDREnabled()) {
                    Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: " + MiCamera2ShotParallelBurst.this.mCompletedNum + "/" + MiCamera2ShotParallelBurst.this.mSequenceNum);
                } else {
                    Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: " + MiCamera2ShotParallelBurst.this.mCompletedNum + "/" + (MiCamera2ShotParallelBurst.this.mSequenceNum * 2));
                }
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(totalCaptureResult), MiCamera2ShotParallelBurst.this.mCompletedNum == 1);
                if (!DataRepository.dataItemFeature().ll() || !MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().isHDREnabled()) {
                    if (MiCamera2ShotParallelBurst.this.mSequenceNum == MiCamera2ShotParallelBurst.this.mCompletedNum) {
                        MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                        miCamera2ShotParallelBurst.mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelBurst);
                    }
                } else if (MiCamera2ShotParallelBurst.this.mSequenceNum * 2 == MiCamera2ShotParallelBurst.this.mCompletedNum) {
                    MiCamera2ShotParallelBurst miCamera2ShotParallelBurst2 = MiCamera2ShotParallelBurst.this;
                    miCamera2ShotParallelBurst2.mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelBurst2);
                }
                boolean isSREnable = CaptureResultParser.isSREnable(totalCaptureResult);
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: isSREnabled = " + isSREnable);
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: hdrEnabled = " + ((Boolean) VendorTagHelper.getValue(totalCaptureResult, CaptureResultVendorTags.IS_HDR_ENABLE)));
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureCompleted: fusion = " + MiCamera2ShotParallelBurst.this.mIsSatFusionShotEnabled);
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelBurst.TAG, "onCaptureFailed: reason=" + captureFailure.getReason() + " timestamp=" + MiCamera2ShotParallelBurst.this.mCaptureTimestamp);
                MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                miCamera2ShotParallelBurst.mMiCamera.onCapturePictureFinished(false, miCamera2ShotParallelBurst);
                if (MiCamera2ShotParallelBurst.this.mCaptureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(MiCamera2ShotParallelBurst.this.mCaptureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
                Camera2Proxy.PictureCallback pictureCallback;
                super.onCaptureProgressed(cameraCaptureSession, captureRequest, captureResult);
                if (MiCamera2ShotParallelBurst.this.mMiCamera.getPreviewCallbackEnabled() > 0 && (MiCamera2ShotParallelBurst.this.mMiCamera.getPreviewCallbackEnabled() & 16) != 0) {
                    Long l = (Long) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.ANCHOR_FRAME_TIMESTAMP);
                    long longValue = l == null ? -1 : l.longValue();
                    Log.d(MiCamera2ShotParallelBurst.TAG, "begin to choose anchor frame " + longValue);
                    int i = (longValue > 0 ? 1 : (longValue == 0 ? 0 : -1));
                    if (i > 0 && MiCamera2ShotParallelBurst.this.mAnchorFrame) {
                        Camera2Proxy.PictureCallback pictureCallback2 = MiCamera2ShotParallelBurst.this.getPictureCallback();
                        if (pictureCallback2 != null && MiCamera2ShotParallelBurst.this.mNeedDoAnchorFrame) {
                            pictureCallback2.onCaptureProgress(MiCamera2ShotParallelBurst.this.isQuickShotAnimation(), MiCamera2ShotParallelBurst.this.mAnchorFrame, true, false);
                            MiCamera2ShotParallelBurst.this.mNeedDoAnchorFrame = false;
                        }
                        MiCamera2ShotParallelBurst.this.mMiCamera.getCacheImageDecoder().saveAnchorFrameThumbnail(longValue, MiCamera2ShotParallelBurst.this.mPreviewSize.width, MiCamera2ShotParallelBurst.this.mPreviewSize.height, null, String.valueOf(System.currentTimeMillis()));
                    } else if (i == 0 && MiCamera2ShotParallelBurst.this.mAnchorFrame && (pictureCallback = MiCamera2ShotParallelBurst.this.getPictureCallback()) != null && MiCamera2ShotParallelBurst.this.mNeedDoAnchorFrame) {
                        pictureCallback.onCaptureProgress(MiCamera2ShotParallelBurst.this.isQuickShotAnimation(), MiCamera2ShotParallelBurst.this.mAnchorFrame, false, true);
                        MiCamera2ShotParallelBurst.this.mNeedDoAnchorFrame = false;
                    }
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2 + " isFirst=" + MiCamera2ShotParallelBurst.this.mFirstNum);
                if (MiCamera2ShotParallelBurst.this.mUseParallelVtCam) {
                    MiCamera2ShotParallelBurst.this.mMiCamera.enableSat();
                }
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if (MiCamera2ShotParallelBurst.this.mFirstNum) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelBurst.this.getPictureCallback();
                    if (pictureCallback != null) {
                        ParallelTaskData parallelTaskData = new ParallelTaskData(MiCamera2ShotParallelBurst.this.mMiCamera.getId(), j, MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().getShotType(), MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().getShotPath(), MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().getCaptureTime());
                        MiCamera2ShotParallelBurst miCamera2ShotParallelBurst = MiCamera2ShotParallelBurst.this;
                        ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, miCamera2ShotParallelBurst.mAlgoSize, miCamera2ShotParallelBurst.isQuickShotAnimation(), MiCamera2ShotParallelBurst.this.mAnchorFrame, false, false);
                        if (onCaptureStart != null) {
                            onCaptureStart.setIsSatFusionShot(MiCamera2ShotParallelBurst.this.mIsSatFusionShotEnabled);
                            onCaptureStart.setAlgoType(MiCamera2ShotParallelBurst.this.mAlgoType);
                            onCaptureStart.setHdrSR(MiCamera2ShotParallelBurst.this.mIsHdrSR);
                            if (!DataRepository.dataItemFeature().ll() || !MiCamera2ShotParallelBurst.this.mMiCamera.getCameraConfigs().isHDREnabled()) {
                                onCaptureStart.setBurstNum(MiCamera2ShotParallelBurst.this.mSequenceNum);
                            } else {
                                onCaptureStart.setBurstNum(MiCamera2ShotParallelBurst.this.mSequenceNum * 2);
                            }
                            MiCamera2ShotParallelBurst.this.mCaptureTimestamp = j;
                            AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        } else {
                            Log.w(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: null task data");
                        }
                    } else {
                        Log.w(MiCamera2ShotParallelBurst.TAG, "onCaptureStarted: null picture callback");
                    }
                    MiCamera2ShotParallelBurst.this.mFirstNum = false;
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        Surface tuningRemoteSurface;
        CaptureRequest.Builder createCaptureRequest = !this.mUseParallelVtCam ? this.mMiCamera.getCameraDevice().createCaptureRequest(2) : ParallelSnapshotManager.getInstance().getCameraDevice().createCaptureRequest(2);
        if (this.mMiCamera.isQcfaEnable()) {
            Surface qcfaRemoteSurface = this.mMiCamera.getQcfaRemoteSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(qcfaRemoteSurface);
            Log.d(TAG, String.format(Locale.ENGLISH, "[QCFA]add surface %s to capture request, size is: %s", qcfaRemoteSurface, surfaceSize));
            createCaptureRequest.addTarget(qcfaRemoteSurface);
            if (c.Xm() || c.Vg) {
                createCaptureRequest.addTarget(this.mMiCamera.getPreviewSurface());
            }
            Size size = this.mLockedAlgoSize;
            if (size != null) {
                configParallelSession(size);
            } else {
                configParallelSession(surfaceSize);
            }
        } else {
            if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
                Surface mainCaptureSurface = !this.mUseParallelVtCam ? getMainCaptureSurface() : ParallelSnapshotManager.getInstance().getCaptureSurface(this.mMiCamera.getSatMasterCameraId());
                Size surfaceSize2 = SurfaceUtils.getSurfaceSize(mainCaptureSurface);
                Log.d(TAG, String.format(Locale.ENGLISH, "[SAT]add main surface %s to capture request, size is: %s", mainCaptureSurface, surfaceSize2));
                createCaptureRequest.addTarget(mainCaptureSurface);
                int i = 513;
                if (mainCaptureSurface == this.mMiCamera.getUltraWideRemoteSurface() || (this.mUseParallelVtCam && mainCaptureSurface == ParallelSnapshotManager.getInstance().getCaptureSurface(1))) {
                    i = 3;
                }
                if (this.mIsSatFusionShotEnabled) {
                    Surface ultraTeleRemoteSurface = this.mMiCamera.getUltraTeleRemoteSurface();
                    Log.d(TAG, String.format(Locale.ENGLISH, "[SAT]add ultra tele surface %s to capture request, size is: %s", ultraTeleRemoteSurface, SurfaceUtils.getSurfaceSize(ultraTeleRemoteSurface)));
                    createCaptureRequest.addTarget(ultraTeleRemoteSurface);
                    VendorTagHelper.setValueQuietly(createCaptureRequest, CaptureRequestVendorTags.CONTROL_SAT_FUSION_SHOT, (byte) 1);
                    i = 516;
                } else {
                    VendorTagHelper.setValueQuietly(createCaptureRequest, CaptureRequestVendorTags.CONTROL_SAT_FUSION_SHOT, (byte) 0);
                }
                Log.d(TAG, "[SAT]combinationMode: " + i);
                configParallelSession(surfaceSize2, i);
            } else {
                for (Surface surface : this.mMiCamera.getRemoteSurfaceList()) {
                    Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", surface, SurfaceUtils.getSurfaceSize(surface)));
                    createCaptureRequest.addTarget(surface);
                }
                this.mAlgoSize = this.mMiCamera.getPictureSize();
            }
            if (!c.isMTKPlatform() && this.mOperationMode != 36865 && (c.Xm() || c.Vg || this.mOperationMode != 36867)) {
                Surface previewSurface = this.mMiCamera.getPreviewSurface();
                Log.d(TAG, String.format(Locale.ENGLISH, "add preview surface %s to capture request, size is: %s", previewSurface, SurfaceUtils.getSurfaceSize(previewSurface)));
                if (!this.mUseParallelVtCam) {
                    createCaptureRequest.addTarget(previewSurface);
                }
            }
        }
        if (DataRepository.dataItemFeature().Ol() && (tuningRemoteSurface = this.mMiCamera.getTuningRemoteSurface()) != null) {
            Log.d(TAG, "add tuning surface to capture request, size is: %s", SurfaceUtils.getSurfaceSize(tuningRemoteSurface));
            createCaptureRequest.addTarget(tuningRemoteSurface);
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, 1);
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (this.mAlgoType == 1) {
            if (this.mIsHdrBokeh) {
                Log.d(TAG, "enable ZSL for HDR");
                CompatibilityUtils.setZsl(createCaptureRequest, true);
            } else {
                Log.d(TAG, "disable ZSL for HDR");
                CompatibilityUtils.setZsl(createCaptureRequest, false);
            }
        } else if (ModuleManager.isSuperMoonMode()) {
            Log.d(TAG, "enable ZSL for SuperMoonMode");
            CompatibilityUtils.setZsl(createCaptureRequest, true);
        } else if (!c.isMTKPlatform()) {
            Log.d(TAG, "disable ZSL for algo " + this.mAlgoType);
            CompatibilityUtils.setZsl(createCaptureRequest, false);
        }
        CaptureRequestBuilder.applyFlawDetectEnable(this.mMiCamera.getCapabilities(), createCaptureRequest, this.mMiCamera.getCameraConfigs().isFlawDetectEnable());
        return createCaptureRequest;
    }

    @Override // com.android.camera2.MiCamera2Shot
    public boolean isShutterReturned() {
        return this.mCaptureTimestamp != -1;
    }

    @Override // com.android.camera2.MiCamera2Shot
    public void onCaptureShutter() {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onCaptureShutter(true, this.mAnchorFrame, false, false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        this.mFirstNum = true;
        CameraConfigs cameraConfigs = this.mMiCamera.getCameraConfigs();
        this.mShouldDoSR = cameraConfigs.isSuperResolutionEnabled();
        this.mPreviewSize = cameraConfigs.getAlgorithmPreviewSize();
        if (cameraConfigs.isHDREnabled()) {
            this.mIsHdrBokeh = cameraConfigs.isDualBokehEnabled();
            this.mSingleCaptureForHDRplusMFNR = this.mMiCamera.useSingleCaptureForHdrPlusMfnr();
            Log.d(TAG, "singleFrameHDR = " + this.mSingleCaptureForHDRplusMFNR);
            if (cameraConfigs.getHDRStatus().isSuperResolutionHDR()) {
                Log.d(TAG, "prepare: HdrSR");
                this.mAlgoType = 3;
                this.mIsHdrSR = true;
                prepareSR(true);
            } else {
                this.mAlgoType = 1;
                prepareHdr();
            }
        } else if (DataRepository.dataItemFeature().Il() && cameraConfigs.isDualBokehEnabled()) {
            this.mAlgoType = 9;
            prepareLowLightBokeh();
        } else if (CameraSettings.isGroupShotOn()) {
            this.mAlgoType = 5;
            prepareGroupShot();
        } else if (this.mShouldDoSR) {
            this.mAlgoType = 3;
            prepareSR(false);
        } else if (this.mOperationMode == 32778) {
            this.mAlgoType = 10;
            prepareSuperNight();
        } else {
            Integer num = (Integer) this.mPreviewCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
            boolean isMfnrEnabled = this.mMiCamera.getCameraConfigs().isMfnrEnabled();
            Log.d(TAG, "prepare: iso = " + num + " isHwMFNREnabled = " + isMfnrEnabled);
            if (c.Sm() || c.Km()) {
                this.mShouldDoMFNR = true;
            } else {
                this.mShouldDoMFNR = num != null && num.intValue() >= 800;
            }
            if (!this.mShouldDoMFNR || (ModuleManager.isSuperMoonMode() && isMfnrEnabled)) {
                this.mAlgoType = 0;
                this.mSequenceNum = 1;
                this.mMultiFrameNum = 1;
            } else if (c.Sm() && CameraSettings.isFrontCamera()) {
                this.mAlgoType = 2;
                prepareClearShot(num.intValue());
            } else if (!c.Km() || !CameraSettings.isBackCamera()) {
                this.mAlgoType = 2;
                prepareClearShot(num.intValue());
            } else {
                this.mAlgoType = 7;
                prepareHHT();
            }
        }
        this.mIsSatFusionShotEnabled = isSatFusionShotEnabled() && this.mAlgoType == 3;
        this.mAnchorFrame = doAnchorFrameAsThumbnail();
        this.mNeedDoAnchorFrame = this.mAnchorFrame;
        Log.d(TAG, String.format(Locale.ENGLISH, "prepare: algo=%d captureNum=%d doMFNR=%b doSR=%b anchor=%b mUseParallelVtCam=%b", Integer.valueOf(this.mAlgoType), Integer.valueOf(this.mSequenceNum), Boolean.valueOf(this.mShouldDoMFNR), Boolean.valueOf(this.mShouldDoSR), Boolean.valueOf(this.mAnchorFrame), Boolean.valueOf(this.mUseParallelVtCam)));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        try {
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            ArrayList arrayList = new ArrayList();
            Log.d(TAG, "startSessionCapture mSequenceNum:" + this.mSequenceNum);
            for (int i = 0; i < this.mSequenceNum; i++) {
                if (c.isMTKPlatform()) {
                    if (this.mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                        MiCameraCompat.copyFpcDataFromCaptureResultToRequest(this.mPreviewCaptureResult, generateRequestBuilder);
                    }
                    if (!isIn3OrMoreSatMode()) {
                        if (!isInMultiSurfaceSatMode()) {
                            if (this.mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId() || this.mAlgoType == 3) {
                                Log.d(TAG, "uw/sr set crop = " + this.mActiveArraySize);
                                generateRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, this.mActiveArraySize);
                                Rect cropRegion = HybridZoomingSystem.toCropRegion(this.mMiCamera.getZoomRatio(), this.mActiveArraySize);
                                Log.d(TAG, "uw/sr set mtkCrop = " + cropRegion);
                                MiCameraCompat.applyPostProcessCropRegion(generateRequestBuilder, cropRegion);
                            }
                        }
                    }
                    Rect[] rectArr = (Rect[]) VendorTagHelper.getValueSafely(this.mPreviewCaptureResult, CaptureResultVendorTags.MI_STATISTICS_FACE_RECTANGLES);
                    if (rectArr != null) {
                        Log.d(TAG, "set mtk face");
                        MiCameraCompat.applyFaceRectangles(generateRequestBuilder, rectArr);
                    } else {
                        Log.d(TAG, "get mtk face = null");
                    }
                    MiCameraCompat.applyNotificationTrigger(generateRequestBuilder, true);
                    Rect rect = (Rect) VendorTagHelper.getValueSafely(this.mPreviewCaptureResult, CaptureResultVendorTags.POST_PROCESS_CROP_REGION);
                    if (rect != null) {
                        Log.d(TAG, "sat set mtkCrop = " + rect);
                        MiCameraCompat.applyPostProcessCropRegion(generateRequestBuilder, rect);
                    } else {
                        Log.d(TAG, "sat get mtkCrop = null");
                    }
                }
                if (DataRepository.dataItemFeature().ll()) {
                    MiCameraCompat.applyAlgoUpEnabled(generateRequestBuilder, true);
                }
                applyAlgoParameter(generateRequestBuilder, i, this.mAlgoType);
                arrayList.add(generateRequestBuilder.build());
                if (DataRepository.dataItemFeature().ll() && this.mMiCamera.getCameraConfigs().isHDREnabled()) {
                    CaptureRequest.Builder generateRequestBuilder2 = generateRequestBuilder();
                    MiCameraCompat.applyAlgoUpEnabled(generateRequestBuilder2, true);
                    applyAlgoParameter(generateRequestBuilder2, i, this.mAlgoType);
                    arrayList.add(generateRequestBuilder2.build());
                }
            }
            Log.d(TAG, "startSessionCapture request number:" + arrayList.size());
            if (this.mUseParallelVtCam) {
                ParallelSnapshotManager.getInstance().getCaptureSession().captureBurst(arrayList, generateCaptureCallback, this.mCameraHandler);
            } else {
                this.mMiCamera.getCaptureSession().captureBurst(arrayList, generateCaptureCallback, this.mCameraHandler);
            }
            MemoryHelper.addCapturedNumber(this.mMiCamera.hashCode(), this.mSequenceNum);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot captureBurst");
            this.mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to captureBurst, IllegalState", e3);
            this.mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e4);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
