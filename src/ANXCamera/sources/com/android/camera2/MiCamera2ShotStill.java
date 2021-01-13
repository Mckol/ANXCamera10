package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.JpegUtil;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.stat.d;

public class MiCamera2ShotStill extends MiCamera2Shot<ParallelTaskData> {
    private static final String TAG = "MiCamera2ShotStill";
    private TotalCaptureResult mCaptureResult;
    private ParallelTaskData mCurrentParallelTaskData;
    private boolean mHasDepth;
    private boolean mIsIntent;
    private boolean mNeedCaptureResult;

    public MiCamera2ShotStill(MiCamera2 miCamera2) {
        super(miCamera2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyResultData(ParallelTaskData parallelTaskData, @Nullable CaptureResult captureResult, @Nullable CameraCharacteristics cameraCharacteristics) {
        ParallelCallback parallelCallback = getParallelCallback();
        if (parallelCallback == null) {
            Log.w(TAG, "notifyResultData: null parallel callback");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mCurrentParallelTaskData.setPreviewThumbnailHash(this.mPreviewThumbnailHash);
        parallelCallback.onParallelProcessFinish(parallelTaskData, captureResult, cameraCharacteristics);
        String str = TAG;
        Log.d(str, "mJpegCallbackFinishTime = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotStill.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                Log.d(MiCamera2ShotStill.TAG, "onCaptureCompleted: " + totalCaptureResult.getFrameNumber());
                boolean z = false;
                if (MiCamera2ShotStill.this.mMiCamera.getSuperNight()) {
                    MiCamera2ShotStill.this.mMiCamera.setAWBLock(false);
                }
                if (!(DataRepository.dataItemGlobal().getCurrentMode() == 173 && CameraSettings.isFrontCamera() && MiCamera2ShotStill.this.mMiCamera.getCameraConfigs().getShotType() == 0)) {
                    MiCamera2ShotStill miCamera2ShotStill = MiCamera2ShotStill.this;
                    miCamera2ShotStill.mMiCamera.onCapturePictureFinished(true, miCamera2ShotStill);
                }
                MiCamera2ShotStill.this.mCaptureResult = totalCaptureResult;
                if (MiCamera2ShotStill.this.mNeedCaptureResult) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotStill.this.getPictureCallback();
                    if (pictureCallback == null || MiCamera2ShotStill.this.mCurrentParallelTaskData == null) {
                        Log.w(MiCamera2ShotStill.TAG, "onCaptureCompleted: something wrong: callback = " + pictureCallback + " mCurrentParallelTaskData = " + MiCamera2ShotStill.this.mCurrentParallelTaskData);
                        return;
                    }
                    if (MiCamera2ShotStill.this.mCurrentParallelTaskData.isJpegDataReady() && MiCamera2ShotStill.this.mCaptureResult != null) {
                        z = true;
                    }
                    if (!z) {
                        return;
                    }
                    if (MiCamera2ShotStill.this.mIsIntent) {
                        MiCamera2ShotStill miCamera2ShotStill2 = MiCamera2ShotStill.this;
                        miCamera2ShotStill2.notifyResultData(miCamera2ShotStill2.mCurrentParallelTaskData);
                        pictureCallback.onPictureTakenFinished(true);
                        return;
                    }
                    pictureCallback.onPictureTakenFinished(true);
                    MiCamera2ShotStill miCamera2ShotStill3 = MiCamera2ShotStill.this;
                    miCamera2ShotStill3.notifyResultData(miCamera2ShotStill3.mCurrentParallelTaskData, MiCamera2ShotStill.this.mCaptureResult, MiCamera2ShotStill.this.mMiCamera.getCapabilities().getCameraCharacteristics());
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                String str = MiCamera2ShotStill.TAG;
                Log.e(str, "onCaptureFailed: reason=" + captureFailure.getReason());
                if (MiCamera2ShotStill.this.mMiCamera.getSuperNight()) {
                    MiCamera2ShotStill.this.mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotStill miCamera2ShotStill = MiCamera2ShotStill.this;
                miCamera2ShotStill.mMiCamera.onCapturePictureFinished(false, miCamera2ShotStill);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if ((!CameraSettings.isSupportedZslShutter() || CameraSettings.isUltraPixelOn()) && !CameraSettings.getPlayToneOnCaptureStart()) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotStill.this.getPictureCallback();
                    if (pictureCallback != null) {
                        pictureCallback.onCaptureShutter(false, false, false, false);
                    } else {
                        Log.w(MiCamera2ShotStill.TAG, "onCaptureStarted: null picture callback");
                    }
                }
                if (0 == MiCamera2ShotStill.this.mCurrentParallelTaskData.getTimestamp()) {
                    MiCamera2ShotStill.this.mCurrentParallelTaskData.setTimestamp(j);
                }
                String str = MiCamera2ShotStill.TAG;
                Log.d(str, "onCaptureStarted: mCurrentParallelTaskData: " + MiCamera2ShotStill.this.mCurrentParallelTaskData.getTimestamp());
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = this.mMiCamera.getCameraDevice().createCaptureRequest(2);
        ImageReader photoImageReader = this.mMiCamera.getPhotoImageReader();
        createCaptureRequest.addTarget(photoImageReader.getSurface());
        String str = TAG;
        Log.d(str, "size=" + photoImageReader.getWidth() + "x" + photoImageReader.getHeight());
        if (DataRepository.dataItemFeature().oi() && (!isInQcfaMode() || Camera2DataContainer.getInstance().getBokehFrontCameraId() == this.mMiCamera.getId())) {
            createCaptureRequest.addTarget(this.mMiCamera.getPreviewSurface());
        }
        if (this.mMiCamera.isConfigRawStream()) {
            createCaptureRequest.addTarget(this.mMiCamera.getRawSurface());
        }
        if (this.mHasDepth) {
            createCaptureRequest.addTarget(this.mMiCamera.getDepthImageReader().getSurface());
            createCaptureRequest.addTarget(this.mMiCamera.getPortraitRawImageReader().getSurface());
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) this.mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        this.mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (CameraSettings.isUltraPixelRawOn()) {
            if (c.isMTKPlatform()) {
                Log.d(TAG, "enable remosaic capture hint");
                MiCameraCompat.applyRemosaicHint(createCaptureRequest, true);
            }
            Log.d(TAG, "apply remosaic capture request: true");
            MiCameraCompat.applyRemosaicEnabled(createCaptureRequest, true);
        }
        if (this.mMiCamera.useLegacyFlashStrategy() && this.mMiCamera.isNeedFlashOn()) {
            this.mMiCamera.pausePreview();
        }
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public long getTimeStamp() {
        ParallelTaskData parallelTaskData = this.mCurrentParallelTaskData;
        if (parallelTaskData == null) {
            return 0;
        }
        return parallelTaskData.getTimestamp();
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(ParallelTaskData parallelTaskData) {
        notifyResultData(parallelTaskData, null, null);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback == null || this.mCurrentParallelTaskData == null) {
            Log.w(TAG, "onImageReceived: something wrong happened when image received: " + image.getTimestamp() + " callback = " + pictureCallback + " mCurrentParallelTaskData = " + this.mCurrentParallelTaskData);
            image.close();
            return;
        }
        if (DataRepository.dataItemGlobal().getCurrentMode() == 173 && CameraSettings.isFrontCamera() && this.mMiCamera.getCameraConfigs().getShotType() == 0) {
            this.mMiCamera.onCapturePictureFinished(true, this);
        }
        if (pictureCallback.onPictureTakenImageConsumed(image, this.mCaptureResult)) {
            image.close();
            pictureCallback.onPictureTakenFinished(true);
            return;
        }
        if (0 == this.mCurrentParallelTaskData.getTimestamp()) {
            Log.w(TAG, "onImageReceived: image arrived first");
            this.mCurrentParallelTaskData.setTimestamp(image.getTimestamp());
        }
        if (this.mCurrentParallelTaskData.getTimestamp() == image.getTimestamp() || !this.mCurrentParallelTaskData.isDataFilled(i)) {
            Log.d(TAG, "onImageReceived mCurrentParallelTaskData timestamp:" + this.mCurrentParallelTaskData.getTimestamp() + " image timestamp:" + image.getTimestamp());
            Image.Plane[] planesExtra = JpegUtil.getPlanesExtra(image);
            boolean z = false;
            byte[] jpegData = JpegUtil.getJpegData(planesExtra, 0);
            byte[] jpegData2 = JpegUtil.getJpegData(planesExtra, 1);
            if (jpegData == null) {
                jpegData = Util.getFirstPlane(image);
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onImageReceived: dataLen=");
            sb.append(jpegData == null ? "null" : Integer.valueOf(jpegData.length));
            sb.append(" resultType = ");
            sb.append(i);
            sb.append(" timeStamp=");
            sb.append(image.getTimestamp());
            sb.append(" holder=");
            sb.append(hashCode());
            Log.d(str, sb.toString());
            image.close();
            this.mCurrentParallelTaskData.fillJpegData(jpegData, i);
            if (jpegData2 != null) {
                this.mCurrentParallelTaskData.setDataOfTheRegionUnderWatermarks(jpegData2);
                ParallelTaskDataParameter dataParameter = this.mCurrentParallelTaskData.getDataParameter();
                int[] watermarkRange = Util.getWatermarkRange(dataParameter.getPictureSize().getWidth(), dataParameter.getPictureSize().getHeight(), (dataParameter.getJpegRotation() + 270) % 360, true, true, false);
                Log.d(TAG, "watermarkRange:" + watermarkRange[0] + "_" + watermarkRange[1] + "_" + watermarkRange[2] + "_" + watermarkRange[3]);
                this.mCurrentParallelTaskData.setCoordinatesOfTheRegionUnderWatermarks(watermarkRange);
            }
            if (!this.mNeedCaptureResult) {
                z = this.mCurrentParallelTaskData.isJpegDataReady();
            } else if (this.mCurrentParallelTaskData.isJpegDataReady() && this.mCaptureResult != null) {
                z = true;
            }
            if (!z) {
                return;
            }
            if (this.mIsIntent) {
                notifyResultData(this.mCurrentParallelTaskData);
                pictureCallback.onPictureTakenFinished(true);
                return;
            }
            pictureCallback.onPictureTakenFinished(true);
            notifyResultData(this.mCurrentParallelTaskData, this.mCaptureResult, this.mMiCamera.getCapabilities().getCameraCharacteristics());
            return;
        }
        Log.e(TAG, "image has been filled " + i);
        image.close();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        if (this.mMiCamera.getSuperNight()) {
            this.mMiCamera.setAWBLock(true);
        }
        this.mShotBoostParams = this.mMiCamera.getShotBoostParams();
        int shotType = this.mMiCamera.getCameraConfigs().getShotType();
        if (shotType == -3) {
            this.mHasDepth = true;
            this.mIsIntent = true;
        } else if (shotType == -2) {
            this.mIsIntent = true;
        } else if (shotType == 1) {
            this.mNeedCaptureResult = true;
        } else if (shotType == 2) {
            this.mHasDepth = true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        try {
            this.mCurrentParallelTaskData = generateParallelTaskData(0);
            if (this.mCurrentParallelTaskData == null) {
                Log.w(TAG, "startSessionCapture: null task data");
                return;
            }
            this.mCurrentParallelTaskData.setShot2Gallery(this.mMiCamera.getCameraConfigs().isShot2Gallery());
            this.mCurrentParallelTaskData.setInTimerBurstShotting(this.mMiCamera.isInTimerBurstShotting());
            String savePath = this.mCurrentParallelTaskData.getSavePath();
            if (savePath != null) {
                this.mCurrentParallelTaskData.setSaveToHiddenFolder(Storage.isSaveToHidenFolder(Util.getFileTitleFromPath(savePath)));
            }
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            PerformanceTracker.trackPictureCapture(0);
            Log.dumpRequest("shotstill  for camera " + this.mMiCamera.getId(), generateRequestBuilder.build());
            this.mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, this.mCameraHandler);
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
