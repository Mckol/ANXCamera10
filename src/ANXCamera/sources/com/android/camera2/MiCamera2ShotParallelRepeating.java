package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.Image;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.Locale;

public class MiCamera2ShotParallelRepeating extends MiCamera2ShotParallel<byte[]> {
    private static final int INVALID_SEQUENCE_ID = -1;
    private static final String TAG = "ParallelRepeating";
    private boolean isFirstRequest = true;
    private int mCaptureRequestNum;
    private int mCapturedNum;
    private int mCurrentSequenceId;
    private final boolean mIsSatFusionShotEnabled;
    private boolean mIsSupportP2done = false;
    private int mLatestSequenceId;
    private int mMaxCapturedNum;
    private CaptureResult mRepeatingCaptureResult;
    private final Surface mZoomMapSurface;

    public MiCamera2ShotParallelRepeating(MiCamera2 miCamera2, int i, Surface surface) {
        super(miCamera2);
        boolean z = true;
        this.mMaxCapturedNum = i;
        this.mIsSatFusionShotEnabled = false;
        this.mZoomMapSurface = surface;
        this.mIsSupportP2done = (!c.isMTKPlatform() || !DataRepository.dataItemFeature().Uj() || !this.mMiCamera.getCapabilities().isSupportP2done() || !DataRepository.dataItemFeature().Yj()) ? false : z;
    }

    static /* synthetic */ int access$008(MiCamera2ShotParallelRepeating miCamera2ShotParallelRepeating) {
        int i = miCamera2ShotParallelRepeating.mCapturedNum;
        miCamera2ShotParallelRepeating.mCapturedNum = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onRepeatingEnd(boolean z, int i) {
        this.mMiCamera.setAWBLock(false);
        this.mMiCamera.resumePreview();
        if (-1 != i) {
            Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
            if (pictureCallback != null) {
                pictureCallback.onPictureTakenFinished(z);
            } else {
                Log.w(TAG, "onRepeatingEnd: null picture callback");
            }
            this.mMiCamera.onMultiSnapEnd(z, this);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotParallelRepeating.AnonymousClass1 */
            long mCaptureTimestamp = -1;

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                super.onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
                Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureCompleted: frameNumber=" + totalCaptureResult.getFrameNumber());
                MiCamera2ShotParallelRepeating.this.mRepeatingCaptureResult = totalCaptureResult;
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(MiCamera2ShotParallelRepeating.this.mRepeatingCaptureResult), true);
                if (c.isMTKPlatform() && DataRepository.dataItemFeature().Uj() && !MiCamera2ShotParallelRepeating.this.mIsSupportP2done) {
                    if (MiCamera2ShotParallelRepeating.this.mMiCamera.isMultiSnapStopRequest()) {
                        MiCamera2ShotParallelRepeating miCamera2ShotParallelRepeating = MiCamera2ShotParallelRepeating.this;
                        miCamera2ShotParallelRepeating.mLatestSequenceId = miCamera2ShotParallelRepeating.mCurrentSequenceId;
                        Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureCompleted:  mLatestSequenceId: " + MiCamera2ShotParallelRepeating.this.mLatestSequenceId);
                        return;
                    }
                    MiCamera2ShotParallelRepeating.this.startSessionCapture();
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelRepeating.TAG, "onCaptureFailed: reason=" + captureFailure.getReason());
                MiCamera2ShotParallelRepeating.this.onRepeatingEnd(false, -1);
                if (this.mCaptureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(this.mCaptureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
                super.onCaptureProgressed(cameraCaptureSession, captureRequest, captureResult);
                Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureProgressed: frameNumber=" + captureResult.getFrameNumber());
                if (MiCamera2ShotParallelRepeating.this.mIsSupportP2done && CaptureResultParser.isP2doneReady(captureResult)) {
                    if (MiCamera2ShotParallelRepeating.this.mMiCamera.isMultiSnapStopRequest()) {
                        MiCamera2ShotParallelRepeating miCamera2ShotParallelRepeating = MiCamera2ShotParallelRepeating.this;
                        miCamera2ShotParallelRepeating.mLatestSequenceId = miCamera2ShotParallelRepeating.mCurrentSequenceId;
                        Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureProgressed:  mLatestSequenceId: " + MiCamera2ShotParallelRepeating.this.mLatestSequenceId);
                        return;
                    }
                    MiCamera2ShotParallelRepeating.this.startSessionCapture();
                }
            }

            public void onCaptureSequenceAborted(@NonNull CameraCaptureSession cameraCaptureSession, int i) {
                super.onCaptureSequenceAborted(cameraCaptureSession, i);
                Log.w(MiCamera2ShotParallelRepeating.TAG, "onCaptureSequenceAborted: sequenceId=" + i);
                MiCamera2ShotParallelRepeating.this.onRepeatingEnd(false, i);
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession cameraCaptureSession, int i, long j) {
                Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureSequenceCompleted: sequenceId=" + i + " mLatestSequenceId= " + MiCamera2ShotParallelRepeating.this.mLatestSequenceId + " frameNumber=" + j);
                if (!c.isMTKPlatform() || !DataRepository.dataItemFeature().Uj() || MiCamera2ShotParallelRepeating.this.mLatestSequenceId == i) {
                    MiCamera2ShotParallelRepeating.this.onRepeatingEnd(true, i);
                    ImagePool.getInstance().trimPoolBuffer();
                }
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2);
                boolean z = false;
                if (c.kh) {
                    MiCamera2ShotParallelRepeating.access$008(MiCamera2ShotParallelRepeating.this);
                    if (MiCamera2ShotParallelRepeating.this.mCapturedNum % 3 == 0) {
                        z = true;
                    }
                }
                if (z) {
                    Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureStarted: drop task " + j);
                    ParallelTaskData parallelTaskData = new ParallelTaskData(MiCamera2ShotParallelRepeating.this.mMiCamera.getId(), j, MiCamera2ShotParallelRepeating.this.mMiCamera.getCameraConfigs().getShotType(), "");
                    parallelTaskData.setIsSatFusionShot(MiCamera2ShotParallelRepeating.this.mIsSatFusionShotEnabled);
                    parallelTaskData.setAlgoType(4);
                    parallelTaskData.setBurstNum(1);
                    if (DataRepository.dataItemFeature().Ol()) {
                        parallelTaskData.setRequireTuningData(true);
                    }
                    parallelTaskData.setAbandoned(true);
                    this.mCaptureTimestamp = j;
                    AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(parallelTaskData);
                    return;
                }
                Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelRepeating.this.getPictureCallback();
                if (pictureCallback != null) {
                    ParallelTaskData parallelTaskData2 = new ParallelTaskData(MiCamera2ShotParallelRepeating.this.mMiCamera.getId(), j, MiCamera2ShotParallelRepeating.this.mMiCamera.getCameraConfigs().getShotType(), null);
                    MiCamera2ShotParallelRepeating miCamera2ShotParallelRepeating = MiCamera2ShotParallelRepeating.this;
                    ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData2, miCamera2ShotParallelRepeating.mAlgoSize, miCamera2ShotParallelRepeating.isQuickShotAnimation(), false, false, false);
                    if (onCaptureStart != null) {
                        onCaptureStart.setIsSatFusionShot(MiCamera2ShotParallelRepeating.this.mIsSatFusionShotEnabled);
                        onCaptureStart.setAlgoType(4);
                        onCaptureStart.setBurstNum(1);
                        this.mCaptureTimestamp = j;
                        AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        return;
                    }
                    Log.w(MiCamera2ShotParallelRepeating.TAG, "onCaptureStarted: null task data");
                    return;
                }
                Log.w(MiCamera2ShotParallelRepeating.TAG, "onCaptureStarted: null picture callback");
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder builder;
        Surface tuningRemoteSurface;
        if (!c.isMTKPlatform()) {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        } else if (DataRepository.dataItemFeature().Uj()) {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
            MiCameraCompat.applyCShotFeatureCapture(builder, true);
            MiCameraCompat.applyNotificationTrigger(builder, true);
        } else {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(1);
            Log.d(TAG, "applyPanoramaP2SEnabled true");
            MiCameraCompat.applyPanoramaP2SEnabled(builder, true);
        }
        if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
            Surface mainCaptureSurface = getMainCaptureSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(mainCaptureSurface);
            Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", mainCaptureSurface, surfaceSize));
            builder.addTarget(mainCaptureSurface);
            int i = 513;
            if (mainCaptureSurface == this.mMiCamera.getUltraWideRemoteSurface()) {
                i = 3;
            }
            Log.d(TAG, "combinationMode: " + i);
            configParallelSession(surfaceSize, i);
        } else {
            for (Surface surface : this.mMiCamera.getRemoteSurfaceList()) {
                Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", surface, SurfaceUtils.getSurfaceSize(surface)));
                builder.addTarget(surface);
            }
            this.mAlgoSize = this.mMiCamera.getPictureSize();
        }
        if (!this.mIsSupportP2done) {
            builder.addTarget(this.mMiCamera.getPreviewSurface());
        }
        if (DataRepository.dataItemFeature().Ol() && (tuningRemoteSurface = this.mMiCamera.getTuningRemoteSurface()) != null) {
            Log.d(TAG, "add tuning surface to capture request, size is: %s", SurfaceUtils.getSurfaceSize(tuningRemoteSurface));
            builder.addTarget(tuningRemoteSurface);
        }
        builder.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        CameraCapabilities capabilities = this.mMiCamera.getCapabilities();
        this.mMiCamera.applySettingsForCapture(builder, 3);
        MiCameraCompat.applyMfnrEnable(builder, false);
        MiCameraCompat.applyHDR(builder, false);
        MiCameraCompat.applySuperResolution(builder, false);
        MiCameraCompat.applyDepurpleEnable(builder, false);
        if (capabilities.isSupportBeauty()) {
            BeautyValues beautyValues = new BeautyValues();
            beautyValues.mBeautyLevel = BeautyConstant.LEVEL_CLOSE;
            MiCameraCompat.applyBeautyParameter(builder, capabilities.getCaptureRequestVendorKeys(), beautyValues);
        }
        if (c.isMTKPlatform() && DataRepository.dataItemFeature().Uj() && !this.mIsSupportP2done) {
            MiCameraCompat.applyZsl(builder, false);
        }
        if (isIn3OrMoreSatMode() && !c.isMTKPlatform()) {
            CaptureRequestBuilder.applySmoothTransition(builder, capabilities, false);
            CaptureRequestBuilder.applySatFallback(builder, capabilities, false);
        }
        MiCameraCompat.applyMultiFrameInputNum(builder, 1);
        if (capabilities.isSupportBurstHint()) {
            MiCameraCompat.applyBurstHint(builder, 1);
        }
        return builder;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2ShotParallel, com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        this.mMiCamera.setAWBLock(true);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        if (!c.isMTKPlatform() || !DataRepository.dataItemFeature().Uj() || this.mCaptureRequestNum < this.mMaxCapturedNum) {
            Log.d(TAG, "startSessionCapture: " + this.isFirstRequest + "  mIsSupportP2done: " + this.mIsSupportP2done);
            PerformanceTracker.trackPictureCapture(0);
            if (!this.mIsSupportP2done) {
                this.mMiCamera.pausePreview();
            }
            try {
                CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
                CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
                if (this.mZoomMapSurface != null) {
                    generateRequestBuilder.addTarget(this.mZoomMapSurface);
                }
                if (!c.isMTKPlatform() || !DataRepository.dataItemFeature().Uj()) {
                    Log.d(TAG, "repeating sequenceId: " + this.mMiCamera.getCaptureSession().setRepeatingRequest(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler));
                    return;
                }
                int i = this.isFirstRequest ? 3 : 1;
                this.isFirstRequest = false;
                for (int i2 = 0; i2 < i; i2++) {
                    int capture = this.mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
                    this.mCaptureRequestNum++;
                    this.mCurrentSequenceId = capture;
                    if (this.mCaptureRequestNum == this.mMaxCapturedNum) {
                        this.mLatestSequenceId = capture;
                        Log.d(TAG, "mtk cshot repeating mLatestSequenceId: " + this.mLatestSequenceId);
                    }
                    Log.d(TAG, "mtk cshot repeating sequenceId: " + capture + " mCaptureRequestNum=" + this.mCaptureRequestNum);
                }
            } catch (CameraAccessException e2) {
                e2.printStackTrace();
                this.mMiCamera.notifyOnError(e2.getReason());
            } catch (IllegalStateException e3) {
                Log.e(TAG, "Failed to capture burst, IllegalState", e3);
                this.mMiCamera.notifyOnError(256);
            } catch (IllegalArgumentException e4) {
                Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e4);
                this.mMiCamera.notifyOnError(256);
            }
        }
    }
}
