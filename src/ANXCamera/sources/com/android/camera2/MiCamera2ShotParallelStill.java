package com.android.camera2;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.utils.SurfaceUtils;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.MemoryHelper;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.Locale;

@TargetApi(21)
public class MiCamera2ShotParallelStill extends MiCamera2ShotParallel<ParallelTaskData> {
    private static final String TAG = "ShotParallelStill";
    private int mAlgoType;
    private boolean mAnchorFrame;
    private long mCaptureTimestamp = -1;
    private final boolean mIsSatFusionShotEnabled;
    private volatile boolean mNeedDoAnchorFrame = true;
    private final int mOperationMode;
    private CameraSize mPreviewSize;
    private boolean mShouldDoQcfaCapture;
    private CaptureResult mStillCaptureResult;
    private boolean mUseParallelVtCam;

    public MiCamera2ShotParallelStill(@NonNull MiCamera2 miCamera2, @NonNull CaptureResult captureResult, boolean z, boolean z2) {
        super(miCamera2);
        this.mPreviewCaptureResult = captureResult;
        this.mOperationMode = miCamera2.getCapabilities().getOperatingMode();
        this.mUseParallelVtCam = z;
        this.mIsSatFusionShotEnabled = isSatFusionShotEnabled();
        this.mShouldDoQcfaCapture = z2;
    }

    private void applyAlgoParameter(CaptureRequest.Builder builder) {
        MiCameraCompat.applySwMfnrEnable(builder, false);
        MiCameraCompat.applyHDR(builder, false);
        MiCameraCompat.applySuperResolution(builder, false);
        MiCameraCompat.applyMultiFrameInputNum(builder, 1);
        if (c.isMTKPlatform()) {
            if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
                Rect[] rectArr = (Rect[]) VendorTagHelper.getValueSafely(this.mPreviewCaptureResult, CaptureResultVendorTags.MI_STATISTICS_FACE_RECTANGLES);
                if (rectArr != null) {
                    Log.d(TAG, "set mtk face");
                    MiCameraCompat.applyFaceRectangles(builder, rectArr);
                } else {
                    Log.d(TAG, "get mtk face = null");
                }
                Rect rect = (Rect) VendorTagHelper.getValueSafely(this.mPreviewCaptureResult, CaptureResultVendorTags.POST_PROCESS_CROP_REGION);
                if (rect != null) {
                    Log.d(TAG, "sat set mtkCrop = " + rect);
                    MiCameraCompat.applyPostProcessCropRegion(builder, rect);
                } else {
                    Log.d(TAG, "sat get mtkCrop = null");
                }
                MiCameraCompat.applyNotificationTrigger(builder, true);
                Log.d(TAG, "sat applyNotificationTrigger true");
            } else if (this.mMiCamera.getCapabilities().getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                MiCameraCompat.copyFpcDataFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
                Rect rect2 = (Rect) builder.get(CaptureRequest.SCALER_CROP_REGION);
                Log.d(TAG, "uw set crop = " + this.mActiveArraySize);
                builder.set(CaptureRequest.SCALER_CROP_REGION, this.mActiveArraySize);
                Log.d(TAG, "uw set mtkCrop = " + rect2);
                MiCameraCompat.applyPostProcessCropRegion(builder, rect2);
            }
            MiCameraCompat.copyAiSceneFromCaptureResultToRequest(this.mPreviewCaptureResult, builder);
        } else if (isIn3OrMoreSatMode()) {
            CaptureRequestBuilder.applySmoothTransition(builder, this.mMiCamera.getCapabilities(), false);
        }
    }

    private boolean doAnchorFrameAsThumbnail() {
        if (!this.mMiCamera.isModuleAnchorFrame()) {
            Log.d(TAG, "anchor frame not enabled");
            return false;
        } else if (this.mAlgoType == 8) {
            Log.d(TAG, "LLS disable anchor frame");
            return false;
        } else if (!this.mMiCamera.isQcfaEnable() || !this.mShouldDoQcfaCapture) {
            return true;
        } else {
            Log.d(TAG, "qcfa disable anchor frame");
            return false;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotParallelStill.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: frameNumber=" + totalCaptureResult.getFrameNumber());
                MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                miCamera2ShotParallelStill.mMiCamera.onCapturePictureFinished(true, miCamera2ShotParallelStill);
                Boolean bool = (Boolean) VendorTagHelper.getValue(totalCaptureResult, CaptureResultVendorTags.IS_HDR_ENABLE);
                if (bool != null && bool.booleanValue()) {
                    Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: HDR error");
                }
                Boolean bool2 = (Boolean) VendorTagHelper.getValue(totalCaptureResult, CaptureResultVendorTags.IS_SR_ENABLE);
                if (bool2 != null && bool2.booleanValue()) {
                    Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureCompleted: SR error");
                }
                MiCamera2ShotParallelStill.this.mStillCaptureResult = totalCaptureResult;
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(MiCamera2ShotParallelStill.this.mStillCaptureResult), true);
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelStill.TAG, "onCaptureFailed: reason=" + captureFailure.getReason() + " frameNumber=" + captureFailure.getFrameNumber());
                MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                miCamera2ShotParallelStill.mMiCamera.onCapturePictureFinished(false, miCamera2ShotParallelStill);
                if (MiCamera2ShotParallelStill.this.mCaptureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(MiCamera2ShotParallelStill.this.mCaptureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
                Camera2Proxy.PictureCallback pictureCallback;
                super.onCaptureProgressed(cameraCaptureSession, captureRequest, captureResult);
                Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureProgressed");
                if (MiCamera2ShotParallelStill.this.mMiCamera.getPreviewCallbackEnabled() > 0 && (MiCamera2ShotParallelStill.this.mMiCamera.getPreviewCallbackEnabled() & 16) != 0) {
                    Long l = (Long) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.ANCHOR_FRAME_TIMESTAMP);
                    long longValue = l == null ? -1 : l.longValue();
                    Log.d(MiCamera2ShotParallelStill.TAG, "begin to choose anchor frame " + longValue);
                    int i = (longValue > 0 ? 1 : (longValue == 0 ? 0 : -1));
                    if (i > 0 && MiCamera2ShotParallelStill.this.mAnchorFrame) {
                        Camera2Proxy.PictureCallback pictureCallback2 = MiCamera2ShotParallelStill.this.getPictureCallback();
                        if (pictureCallback2 != null && MiCamera2ShotParallelStill.this.mNeedDoAnchorFrame) {
                            pictureCallback2.onCaptureProgress(MiCamera2ShotParallelStill.this.isQuickShotAnimation(), MiCamera2ShotParallelStill.this.mAnchorFrame, true, false);
                            MiCamera2ShotParallelStill.this.mNeedDoAnchorFrame = false;
                        }
                        MiCamera2ShotParallelStill.this.mMiCamera.getCacheImageDecoder().saveAnchorFrameThumbnail(longValue, MiCamera2ShotParallelStill.this.mPreviewSize.width, MiCamera2ShotParallelStill.this.mPreviewSize.height, null, String.valueOf(System.currentTimeMillis()));
                    } else if (i == 0 && MiCamera2ShotParallelStill.this.mAnchorFrame && (pictureCallback = MiCamera2ShotParallelStill.this.getPictureCallback()) != null && MiCamera2ShotParallelStill.this.mNeedDoAnchorFrame) {
                        pictureCallback.onCaptureProgress(MiCamera2ShotParallelStill.this.isQuickShotAnimation(), MiCamera2ShotParallelStill.this.mAnchorFrame, false, true);
                        MiCamera2ShotParallelStill.this.mNeedDoAnchorFrame = false;
                    }
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2);
                if (MiCamera2ShotParallelStill.this.mUseParallelVtCam) {
                    CaptureRequestBuilder.applySmoothTransition(MiCamera2ShotParallelStill.this.mMiCamera.getPreviewRequestBuilder(), MiCamera2ShotParallelStill.this.mMiCamera.getCapabilities(), true);
                }
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelStill.this.getPictureCallback();
                String shotPath = MiCamera2ShotParallelStill.this.mMiCamera.getCameraConfigs().getShotPath();
                if (pictureCallback != null) {
                    ParallelTaskData parallelTaskData = new ParallelTaskData(MiCamera2ShotParallelStill.this.mMiCamera.getId(), j, MiCamera2ShotParallelStill.this.mMiCamera.getCameraConfigs().getShotType(), shotPath, MiCamera2ShotParallelStill.this.mMiCamera.getCameraConfigs().getCaptureTime());
                    MiCamera2ShotParallelStill miCamera2ShotParallelStill = MiCamera2ShotParallelStill.this;
                    ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, miCamera2ShotParallelStill.mAlgoSize, miCamera2ShotParallelStill.isQuickShotAnimation(), MiCamera2ShotParallelStill.this.mAnchorFrame, false, false);
                    Boolean bool = (Boolean) VendorTagHelper.getValue(captureRequest, CaptureRequestVendorTags.MFNR_ENABLED);
                    if (onCaptureStart != null) {
                        onCaptureStart.setIsSatFusionShot(MiCamera2ShotParallelStill.this.mIsSatFusionShotEnabled);
                        onCaptureStart.setAlgoType(MiCamera2ShotParallelStill.this.mAlgoType);
                        onCaptureStart.setBurstNum(1);
                        if (bool == null || !bool.booleanValue()) {
                            onCaptureStart.setHWMFNRProcessing(false);
                        } else {
                            Log.d(MiCamera2ShotParallelStill.TAG, "onCaptureStarted, set HWMFNRProcessing is true");
                            onCaptureStart.setHWMFNRProcessing(true);
                        }
                        MiCamera2ShotParallelStill.this.mCaptureTimestamp = j;
                        onCaptureStart.setParallelVTCameraSnapshot(MiCamera2ShotParallelStill.this.mUseParallelVtCam);
                        AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        return;
                    }
                    Log.w(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: null task data");
                    return;
                }
                Log.w(MiCamera2ShotParallelStill.TAG, "onCaptureStarted: null picture callback");
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        Surface tuningRemoteSurface;
        int i;
        CaptureRequest.Builder createCaptureRequest = this.mUseParallelVtCam ? ParallelSnapshotManager.getInstance().getCameraDevice().createCaptureRequest(2) : this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        if (this.mMiCamera.isQcfaEnable()) {
            Surface wideRemoteSurface = (this.mMiCamera.alwaysUseRemosaicSize() || this.mShouldDoQcfaCapture) ? this.mMiCamera.getWideRemoteSurface() : this.mMiCamera.getQcfaRemoteSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(wideRemoteSurface);
            Size size = this.mLockedAlgoSize;
            if (size != null) {
                configParallelSession(size);
            } else {
                configParallelSession(surfaceSize);
            }
            Log.d(TAG, String.format(Locale.ENGLISH, "[QCFA]add surface %s to capture request, size is: %s", wideRemoteSurface, surfaceSize));
            createCaptureRequest.addTarget(wideRemoteSurface);
        } else {
            if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
                Surface captureSurface = this.mUseParallelVtCam ? ParallelSnapshotManager.getInstance().getCaptureSurface(this.mMiCamera.getSatMasterCameraId()) : getMainCaptureSurface();
                Size surfaceSize2 = SurfaceUtils.getSurfaceSize(captureSurface);
                Log.d(TAG, String.format(Locale.ENGLISH, "[SAT]add master surface %s to capture request, size is: %s", captureSurface, surfaceSize2));
                createCaptureRequest.addTarget(captureSurface);
                int i2 = 513;
                if (captureSurface == this.mMiCamera.getUltraWideRemoteSurface() || (this.mUseParallelVtCam && captureSurface == ParallelSnapshotManager.getInstance().getCaptureSurface(1))) {
                    i2 = 3;
                }
                if (this.mIsSatFusionShotEnabled) {
                    Surface ultraTeleRemoteSurface = this.mMiCamera.getUltraTeleRemoteSurface();
                    surfaceSize2 = SurfaceUtils.getSurfaceSize(ultraTeleRemoteSurface);
                    Log.d(TAG, String.format(Locale.ENGLISH, "[SAT]add ultra tele surface %s to capture request, size is: %s", ultraTeleRemoteSurface, surfaceSize2));
                    createCaptureRequest.addTarget(ultraTeleRemoteSurface);
                    VendorTagHelper.setValueQuietly(createCaptureRequest, CaptureRequestVendorTags.CONTROL_SAT_FUSION_SHOT, (byte) 1);
                    i2 = 516;
                } else {
                    VendorTagHelper.setValueQuietly(createCaptureRequest, CaptureRequestVendorTags.CONTROL_SAT_FUSION_SHOT, (byte) 0);
                }
                Log.d(TAG, "[SAT]combinationMode: " + i2);
                configParallelSession(surfaceSize2, i2);
            } else {
                for (Surface surface : this.mMiCamera.getRemoteSurfaceList()) {
                    Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", surface, SurfaceUtils.getSurfaceSize(surface)));
                    createCaptureRequest.addTarget(surface);
                }
                this.mAlgoSize = this.mMiCamera.getPictureSize();
            }
            if (!c.isMTKPlatform() && (i = this.mOperationMode) != 36865 && i != 36867 && ((!this.mMiCamera.isFacingFront() || this.mOperationMode != 36869) && !DataRepository.dataItemGlobal().isOnSuperNightHalfAlgoUp())) {
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
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (this.mShouldDoQcfaCapture && CameraSettings.isFrontCamera()) {
            MiCameraCompat.applyMfnrEnable(createCaptureRequest, false);
        }
        if (this.mMiCamera.isQcfaEnable()) {
            if (c.isMTKPlatform()) {
                Log.d(TAG, "enable remosaic capture hint");
                MiCameraCompat.applyRemosaicHint(createCaptureRequest, true);
            }
            Log.d(TAG, "apply remosaic capture request: " + this.mShouldDoQcfaCapture);
            MiCameraCompat.applyRemosaicEnabled(createCaptureRequest, this.mShouldDoQcfaCapture);
        }
        if (c.isMTKPlatform() && this.mMiCamera.getCameraConfigs().isSpecshotModeEnable()) {
            Integer isSpecshotDetected = CaptureResultParser.isSpecshotDetected(this.mPreviewCaptureResult);
            Log.d(TAG, "apply specshot mode capture request: " + isSpecshotDetected);
            if (isSpecshotDetected != null) {
                MiCameraCompat.applySpecshotMode(createCaptureRequest, isSpecshotDetected.intValue());
            }
        }
        CaptureRequestBuilder.applyFlawDetectEnable(this.mMiCamera.getCapabilities(), createCaptureRequest, this.mMiCamera.getCameraConfigs().isFlawDetectEnable());
        if (this.mMiCamera.isFixShotTime() && DataRepository.dataItemFeature().Ji() && AlgoConnector.getInstance().getLocalBinder().isAnyRequestIsHWMFNRProcessing()) {
            Log.d(TAG, "Do not apply hwmfnr.");
            MiCameraCompat.applyMfnrEnable(createCaptureRequest, false);
            MiCameraCompat.applyMultiFrameInputNum(createCaptureRequest, 1);
        }
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
        this.mAlgoType = 0;
        this.mPreviewSize = this.mMiCamera.getCameraConfigs().getAlgorithmPreviewSize();
        if (this.mShouldDoQcfaCapture && (this.mMiCamera.getCameraConfigs().isHDREnabled() || this.mMiCamera.isBeautyOn())) {
            this.mShouldDoQcfaCapture = false;
        }
        Log.d(TAG, "prepare: qcfa = " + this.mShouldDoQcfaCapture);
        if (this.mShouldDoQcfaCapture) {
            this.mAlgoType = 6;
        }
        if (this.mMiCamera.getCameraConfigs().isLLSEnabled()) {
            this.mAlgoType = 8;
        }
        Log.d(TAG, "prepare: mUseParallelVtCam:" + this.mUseParallelVtCam);
        this.mAnchorFrame = doAnchorFrameAsThumbnail();
        this.mNeedDoAnchorFrame = this.mAnchorFrame;
        Log.d(TAG, "use anchor frame " + this.mAnchorFrame);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        PerformanceTracker.trackPictureCapture(0);
        try {
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            applyAlgoParameter(generateRequestBuilder);
            Log.dumpRequest("parallel shotstill  for camera " + this.mMiCamera.getId(), generateRequestBuilder.build());
            if (this.mUseParallelVtCam) {
                ParallelSnapshotManager.getInstance().getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
            } else {
                this.mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
            }
            MemoryHelper.addCapturedNumber(this.mMiCamera.hashCode(), 1);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot capture a still picture");
            this.mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture a still picture, IllegalState", e3);
            this.mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e4);
            this.mMiCamera.notifyOnError(256);
        }
    }
}
