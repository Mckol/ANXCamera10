package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;

public class MiCamera2ShotBurst extends MiCamera2Shot<byte[]> {
    private static final int INVALID_SEQUENCE_ID = -1;
    private static final String TAG = "MiCamera2ShotBurst";
    private boolean isFirstRequest = true;
    private int mCaptureRequestNum;
    private TotalCaptureResult mCaptureResult;
    private int mCurrentSequenceId;
    private int mLatestSequenceId = 0;
    private int mMaxCapturedNum;
    private boolean mNeedPausePreview;

    public MiCamera2ShotBurst(MiCamera2 miCamera2, int i, boolean z) {
        super(miCamera2);
        this.mMaxCapturedNum = i;
        this.mNeedPausePreview = z;
    }

    private void notifyResultData(byte[] bArr, CaptureResult captureResult) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onPictureTaken(bArr, captureResult);
        } else {
            Log.w(TAG, "notifyResultData: null picture callback");
        }
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
            /* class com.android.camera2.MiCamera2ShotBurst.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                MiCamera2ShotBurst.this.mCaptureResult = totalCaptureResult;
                MiCamera2ShotBurst.this.mMiCamera.updateFrameNumber(totalCaptureResult.getFrameNumber());
                if (c.isMTKPlatform() && DataRepository.dataItemFeature().Uj() && MiCamera2ShotBurst.this.mMaxCapturedNum > 0) {
                    if (MiCamera2ShotBurst.this.mMiCamera.isMultiSnapStopRequest()) {
                        MiCamera2ShotBurst miCamera2ShotBurst = MiCamera2ShotBurst.this;
                        miCamera2ShotBurst.mLatestSequenceId = miCamera2ShotBurst.mCurrentSequenceId;
                        String str = MiCamera2ShotBurst.TAG;
                        Log.d(str, "onCaptureCompleted:  mLatestSequenceId: " + MiCamera2ShotBurst.this.mLatestSequenceId);
                        return;
                    }
                    MiCamera2ShotBurst.this.startSessionCapture();
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                MiCamera2ShotBurst.this.onRepeatingEnd(false, -1);
                String str = MiCamera2ShotBurst.TAG;
                Log.e(str, "onCaptureFailed: " + captureFailure.getReason());
            }

            public void onCaptureSequenceAborted(@NonNull CameraCaptureSession cameraCaptureSession, int i) {
                super.onCaptureSequenceAborted(cameraCaptureSession, i);
                String str = MiCamera2ShotBurst.TAG;
                Log.w(str, "onCaptureSequenceAborted: " + i);
                if (MiCamera2ShotBurst.this.mLatestSequenceId == i) {
                    MiCamera2ShotBurst.this.onRepeatingEnd(false, i);
                }
            }

            public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession cameraCaptureSession, int i, long j) {
                String str = MiCamera2ShotBurst.TAG;
                Log.d(str, "onCaptureSequenceCompleted: sequenceId=" + i + " mLatestSequenceId = " + MiCamera2ShotBurst.this.mLatestSequenceId + " frameNumber=" + j);
                if (MiCamera2ShotBurst.this.mLatestSequenceId == i) {
                    MiCamera2ShotBurst.this.onRepeatingEnd(true, i);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder builder;
        if (!c.isMTKPlatform()) {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 2);
        } else if (!DataRepository.dataItemFeature().Uj() || this.mMaxCapturedNum <= 0) {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(1);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 1);
            Log.d(TAG, "applyPanoramaP2SEnabled true");
            MiCameraCompat.applyPanoramaP2SEnabled(builder, true);
        } else {
            builder = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
            MiCameraCompat.applyCShotFeatureCapture(builder, true);
            MiCameraCompat.applyNotificationTrigger(builder, true);
        }
        builder.addTarget(this.mMiCamera.getPhotoImageReader().getSurface());
        if (!this.mNeedPausePreview) {
            builder.addTarget(this.mMiCamera.getPreviewSurface());
        }
        this.mMiCamera.applySettingsForCapture(builder, 4);
        if (c.isMTKPlatform()) {
            MiCameraCompat.applyZsl(builder, false);
        }
        if (this.mMiCamera.getCapabilities().isSupportBurstHint()) {
            MiCameraCompat.applyBurstHint(builder, 1);
        }
        return builder;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
        notifyResultData(bArr, null);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback == null) {
            image.close();
        } else if (!pictureCallback.onPictureTakenImageConsumed(image, this.mCaptureResult)) {
            byte[] firstPlane = Util.getFirstPlane(image);
            image.close();
            notifyResultData(firstPlane, this.mCaptureResult);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        int i;
        if (!c.isMTKPlatform() || !DataRepository.dataItemFeature().Uj() || (i = this.mMaxCapturedNum) <= 0 || this.mCaptureRequestNum < i) {
            Log.d(TAG, "startSessionCapture");
            this.mMiCamera.pausePreview();
            try {
                CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
                CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
                if (!c.isMTKPlatform() || !DataRepository.dataItemFeature().Uj() || this.mMaxCapturedNum <= 0) {
                    this.mLatestSequenceId = this.mMiCamera.getCaptureSession().setRepeatingRequest(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
                    Log.d(TAG, "repeating sequenceId: " + this.mLatestSequenceId);
                    return;
                }
                int i2 = this.isFirstRequest ? 3 : 1;
                this.isFirstRequest = false;
                for (int i3 = 0; i3 < i2; i3++) {
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
            }
        }
    }
}
