package com.android.camera.module;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import androidx.annotation.WorkerThread;
import com.android.camera.Camera;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.LocationManager;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.clone.Status;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.log.Log;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.StartControlFeatureDetail;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.rx.CameraSchedulers;
import com.xiaomi.fenshen.FenShenCam;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CloneModule extends BaseModule implements Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FocusCallback, Camera2Proxy.PreviewCallback, SurfaceTextureScreenNail.ExternalFrameProcessor, V6CameraGLSurfaceView.RendererListener, ModeProtocol.CameraAction, ModeProtocol.CloneAction, Camera2Proxy.PictureCallback, FocusManager2.Listener {
    private static final int DURATION_VIDEO_RECORDING = 5000;
    private static final int MAX_PHOTO_SUBJECT_COUNT = 4;
    private static final int MAX_VIDEO_SUBJECT_COUNT = 2;
    private static final int MIN_SUBJECT_COUNT = 2;
    private static final long START_OFFSET_MS = 450;
    private static final String TAG = "CloneModule";
    private FenShenCam.Listener mCloneListener = new StateListener(this);
    private CountDownTimer mCountDownTimer;
    private FocusManager2 mFocusManager;
    private boolean mIsDuringShooting = false;
    private boolean mIsFinished;
    private boolean mIsSegmentRecording = false;
    private byte[] mJpgBytes;
    private FenShenCam.Message mLastMessage;
    private int mLastSubjectCount = 0;
    private FenShenCam.Mode mMode;
    private long mOnResumeTime;
    private boolean mPendingStart;
    private int mPhotoSubjectCount = 0;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateAdapter() {
        /* class com.android.camera.module.CloneModule.AnonymousClass2 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public boolean isWorking() {
            return CloneModule.this.isAlive() && CloneModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public void notifyDevicePostureChanged() {
            CloneModule.this.mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public void onDeviceBecomeStable() {
            Log.v(CloneModule.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public void onDeviceKeepMoving(double d2) {
            if (!CloneModule.this.mMainProtocol.isEvAdjusted(true) && !CloneModule.this.mPaused && Util.isTimeout(System.currentTimeMillis(), CloneModule.this.mTouchFocusStartingTime, 3000) && !CloneModule.this.mIsDuringShooting && CloneModule.this.mFocusManager != null && CloneModule.this.mFocusManager.isNeedCancelAutoFocus() && !CloneModule.this.isRecording()) {
                CloneModule.this.mFocusManager.onDeviceKeepMoving(d2);
            }
        }
    };
    private long mTouchFocusStartingTime;
    private String mVideoFileName;
    private String mVideoFilePath;

    /* access modifiers changed from: package-private */
    /* renamed from: com.android.camera.module.CloneModule$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$fenshen$FenShenCam$Message = new int[FenShenCam.Message.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|(3:11|12|14)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$xiaomi$fenshen$FenShenCam$Message[FenShenCam.Message.START.ordinal()] = 1;
            $SwitchMap$com$xiaomi$fenshen$FenShenCam$Message[FenShenCam.Message.PREVIEW_NO_PERSON.ordinal()] = 2;
            $SwitchMap$com$xiaomi$fenshen$FenShenCam$Message[FenShenCam.Message.ALIGN_OK.ordinal()] = 3;
            $SwitchMap$com$xiaomi$fenshen$FenShenCam$Message[FenShenCam.Message.ALIGN_WARNING.ordinal()] = 4;
            $SwitchMap$com$xiaomi$fenshen$FenShenCam$Message[FenShenCam.Message.ALIGN_TOO_LARGE_OR_FAILED.ordinal()] = 5;
            try {
                $SwitchMap$com$xiaomi$fenshen$FenShenCam$Message[FenShenCam.Message.NO_PERSON.ordinal()] = 6;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                CloneModule.this.getWindow().clearFlags(128);
            } else if (i == 4) {
                CloneModule.this.checkActivityOrientation();
                if (SystemClock.uptimeMillis() - CloneModule.this.mOnResumeTime < 5000) {
                    CloneModule.this.mHandler.sendEmptyMessageDelayed(4, 100);
                }
            } else if (i == 9) {
                CloneModule cloneModule = CloneModule.this;
                cloneModule.mMainProtocol.initializeFocusView(cloneModule);
                CloneModule.this.initCloneMode();
            } else if (i == 17) {
                CloneModule.this.mHandler.removeMessages(17);
                CloneModule.this.mHandler.removeMessages(2);
                CloneModule.this.getWindow().addFlags(128);
                CloneModule cloneModule2 = CloneModule.this;
                cloneModule2.mHandler.sendEmptyMessageDelayed(2, (long) cloneModule2.getScreenDelay());
            } else if (i == 31) {
                CloneModule.this.setOrientationParameter();
            } else if (i == 51 && !CloneModule.this.mActivity.isActivityPaused()) {
                CloneModule cloneModule3 = CloneModule.this;
                cloneModule3.mOpenCameraFail = true;
                cloneModule3.onCameraException();
            }
        }
    }

    private static class StateListener implements FenShenCam.Listener {
        private WeakReference<CloneModule> mModuleRef;

        public StateListener(CloneModule cloneModule) {
            this.mModuleRef = new WeakReference<>(cloneModule);
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        public void onMessage(FenShenCam.Message message) {
            CloneModule cloneModule = this.mModuleRef.get();
            if (cloneModule != null) {
                cloneModule.onCloneMessage(message);
            }
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        public void onPhotoResult(byte[] bArr) {
            CloneModule cloneModule = this.mModuleRef.get();
            Log.d(CloneModule.TAG, "onPhotoResult length = " + bArr.length + ", module = " + cloneModule);
            if (cloneModule != null) {
                cloneModule.onPhotoResult(bArr);
            }
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        public void onStartPreview() {
            CloneModule cloneModule = this.mModuleRef.get();
            Log.d(CloneModule.TAG, "onStartPreview " + cloneModule);
            if (cloneModule != null && cloneModule.isAlive()) {
                cloneModule.resumePreview();
            }
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        public void onStopPreview() {
            CloneModule cloneModule = this.mModuleRef.get();
            Log.d(CloneModule.TAG, "onStopPreview " + cloneModule);
            if (cloneModule != null) {
                cloneModule.pausePreview();
            }
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        public void onStopRecord() {
            Log.d(CloneModule.TAG, "onStopRecord");
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        public void onSubjectCount(int i) {
            CloneModule cloneModule = this.mModuleRef.get();
            if (cloneModule != null) {
                cloneModule.onSubjectCountChange(i);
            }
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        @WorkerThread
        public void onVideoSaved(int i) {
            Log.d(CloneModule.TAG, "saveVideo timeUsedMs " + i);
            CloneModule cloneModule = this.mModuleRef.get();
            if (cloneModule != null) {
                cloneModule.onVideoSaveFinish();
            }
        }

        @Override // com.xiaomi.fenshen.FenShenCam.Listener
        public void requestRender() {
            CloneModule cloneModule = this.mModuleRef.get();
            if (cloneModule != null) {
                cloneModule.mActivity.getGLView().requestRender();
            }
        }
    }

    static /* synthetic */ void Ad() {
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess != null) {
            cloneProcess.stopCaptureToPreviewResult(false);
        }
    }

    private void cancelPhotoOrVideo() {
        if (this.mMode == FenShenCam.Mode.PHOTO) {
            FenShenCam.cancelPhoto();
        } else {
            FenShenCam.cancelVideo();
        }
    }

    private void cancelVideoCountDown() {
        if (this.mCountDownTimer != null) {
            Log.d(TAG, "cancelVideoCountDown");
            this.mCountDownTimer.cancel();
            this.mCountDownTimer = null;
            hiddenTopRecordingTime();
        }
    }

    private boolean checkShutterCondition() {
        if (isIgnoreTouchEvent()) {
            Log.w(TAG, "checkShutterCondition: ignoreTouchEvent=" + isIgnoreTouchEvent());
            return false;
        } else if (Storage.isLowStorageAtLastPoint()) {
            Log.w(TAG, "checkShutterCondition: low storage");
            return false;
        } else {
            ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
            if (cloneProcess == null || !cloneProcess.canSnap()) {
                Log.w(TAG, "checkShutterCondition: can't snap");
                return false;
            }
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack == null) {
                return true;
            }
            backStack.handleBackStackFromShutter();
            return true;
        }
    }

    private void doLaterReleaseIfNeed() {
        Camera camera = this.mActivity;
        if (camera != null && camera.isActivityPaused()) {
            this.mActivity.pause();
            this.mActivity.releaseAll(true, true);
        }
    }

    private void hiddenTopRecordingTime() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.setRecordingTimeState(2);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initCloneMode() {
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess == null) {
            Log.w(TAG, "initCloneMode failed, cloneProcess is null");
            return;
        }
        this.mMode = cloneProcess.getMode();
        CameraSize cameraSize = this.mAlgorithmPreviewSize;
        FenShenCam.init(cameraSize.width, cameraSize.height, this.mActivity.getFilesDir().getAbsolutePath(), this.mActivity.getApplicationInfo().nativeLibraryDir);
        FenShenCam.setListener(this.mCloneListener);
        FenShenCam.setMode(this.mMode);
        this.mPendingStart = true;
        if ((this.mMode == FenShenCam.Mode.PHOTO && !DataRepository.dataItemGlobal().isFirstUseClonePhoto()) || (this.mMode == FenShenCam.Mode.VIDEO && !DataRepository.dataItemGlobal().isFirstUseCloneVideo())) {
            FenShenCam.start();
            this.mPendingStart = false;
        }
        this.mActivity.getGLView().setRendererListener(this);
    }

    private void initializeFocusManager() {
        this.mFocusManager = new FocusManager2(this.mCameraCapabilities, this, isFrontCamera(), this.mActivity.getMainLooper());
        Rect renderRect = this.mActivity.getCameraScreenNail() != null ? this.mActivity.getCameraScreenNail().getRenderRect() : null;
        if (renderRect == null || renderRect.width() <= 0) {
            this.mFocusManager.setRenderSize(Util.sWindowWidth, Util.sWindowHeight);
            this.mFocusManager.setPreviewSize(Util.sWindowWidth, Util.sWindowHeight);
            return;
        }
        this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
        this.mFocusManager.setPreviewSize(renderRect.width(), renderRect.height());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onCloneMessage(FenShenCam.Message message) {
        int i = AnonymousClass3.$SwitchMap$com$xiaomi$fenshen$FenShenCam$Message[message.ordinal()];
        int i2 = R.string.clone_no_person_found;
        switch (i) {
            case 1:
                i2 = R.string.clone_mode_start_hint;
                break;
            case 2:
                statCaptureHint(message);
                break;
            case 3:
                i2 = R.string.clone_do_not_move_phone;
                break;
            case 4:
                i2 = R.string.clone_put_phone_back_in_place;
                statCaptureHint(message);
                break;
            case 5:
                i2 = R.string.clone_offset_is_too_large;
                statCaptureHint(message);
                break;
            case 6:
                enableCameraControls(true);
                statCaptureHint(message);
                break;
            default:
                i2 = -1;
                break;
        }
        this.mLastMessage = message;
        if (i2 != -1) {
            this.mHandler.post(new C(this, message, i2));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onPhotoResult(byte[] bArr) {
        this.mJpgBytes = bArr;
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess == null) {
            Log.w(TAG, "onPhotoResult cloneProcess is null");
        } else {
            this.mHandler.post(new J(cloneProcess));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onSubjectCountChange(int i) {
        if (this.mLastSubjectCount != i) {
            Log.d(TAG, "onSubjectCountChange " + i);
            enableCameraControls(true);
            this.mLastSubjectCount = i;
            if (this.mMode == FenShenCam.Mode.PHOTO) {
                if (i == 2) {
                    showFinishAndCancel();
                } else if (i == 4) {
                    stopCaptureToPreviewResult();
                }
            } else if (i == 2) {
                stopCaptureToPreviewResult();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onVideoSaveFinish() {
        Log.d(TAG, "onSaveFinish " + this.mVideoFilePath);
        String name = new File(this.mVideoFilePath).getName();
        String convertOutputFormatToMimeType = Util.convertOutputFormatToMimeType(2);
        int max = Math.max(Math.min((int) (Util.getDuration(this.mVideoFilePath) / 1000), 5), 1);
        CameraStatUtils.trackCloneCaptureParams(FenShenCam.Mode.VIDEO, 2, max + "s");
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", name);
        contentValues.put("_display_name", name + Util.convertOutputFormatToFileExt(2));
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", this.mVideoFilePath);
        contentValues.put("resolution", this.mAlgorithmPreviewSize.width + "x" + this.mAlgorithmPreviewSize.height);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess != null) {
            cloneProcess.onPreviewPrepare(contentValues);
        }
        this.mActivity.getImageSaver().addVideo(this.mVideoFilePath, contentValues, true);
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.CLONE_TYPES_ON_PREVIEW_SUCCESS);
    }

    private void resetAndUnlock3A() {
        Log.d(TAG, "resetAndUnlock3A");
        this.mIsDuringShooting = false;
        this.mIsSegmentRecording = false;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy == null) {
            Log.w(TAG, "resetAndUnlock3A, mCamera2Device is null");
            return;
        }
        if (this.mAeLockSupported) {
            camera2Proxy.setAELock(false);
        }
        if (this.mAwbLockSupported) {
            this.mCamera2Device.setAWBLock(false);
        }
        this.mCamera2Device.setFocusMode(4);
        this.mCamera2Device.resumePreview();
    }

    private void resumePreviewIfNeeded() {
        Log.d(TAG, "resumePreviewIfNeeded");
        this.mIsFinished = false;
        resumePreview();
        cancelPhotoOrVideo();
        FenShenCam.start();
    }

    private void savePhoto(byte[] bArr) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        if (options.outWidth == 0 || options.outHeight == 0) {
            Log.w(TAG, "savePhoto error, can't decode bounds");
            return;
        }
        String createJpegName = Util.createJpegName(System.currentTimeMillis());
        Log.d(TAG, "savePhoto title %s, length %s", createJpegName, Integer.valueOf(bArr.length));
        this.mActivity.getImageSaver().addImage(bArr, true, createJpegName, null, System.currentTimeMillis(), null, null, options.outWidth, options.outHeight, null, 0, false, false, true, false, false, null, null, -1, null);
    }

    private void saveVideo() {
        String format = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(System.currentTimeMillis()));
        this.mVideoFileName = format + Util.convertOutputFormatToFileExt(2);
        this.mVideoFilePath = Storage.generateFilepath(this.mVideoFileName);
        Log.d(TAG, "saveVideo start, path = " + this.mVideoFilePath);
        FenShenCam.saveVideo(this.mVideoFilePath);
    }

    private void setOrientation(int i, int i2) {
        if (i != -1) {
            this.mOrientation = i;
            checkActivityOrientation();
            if (this.mOrientationCompensation != i2) {
                this.mOrientationCompensation = i2;
                setOrientationParameter();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setOrientationParameter() {
        if (!isDeparted() && this.mCamera2Device != null && this.mOrientation != -1) {
            if (!isFrameAvailable() || getCameraState() != 1) {
                CameraSchedulers.sCameraSetupScheduler.scheduleDirect(new F(this));
                return;
            }
            updatePreferenceInWorkThread(35);
        }
    }

    private void showFinishAndCancel() {
        this.mHandler.post(B.INSTANCE);
    }

    private void startCountDown() {
        this.mCountDownTimer = new CountDownTimer(5450, 1000) {
            /* class com.android.camera.module.CloneModule.AnonymousClass1 */

            public void onFinish() {
                CloneModule.this.onShutterButtonClick(10);
            }

            public void onTick(long j) {
                CloneModule.this.updateRecordingTime(j);
            }
        };
        this.mCountDownTimer.start();
    }

    private void startPreviewSession() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy == null) {
            Log.e(TAG, "startPreview: camera has been closed");
            return;
        }
        camera2Proxy.setDualCamWaterMarkEnable(false);
        this.mCamera2Device.setFocusCallback(this);
        this.mCamera2Device.setErrorCallback(this.mErrorCallback);
        this.mCamera2Device.setPreviewSize(this.mPreviewSize);
        this.mCamera2Device.setAlgorithmPreviewSize(this.mAlgorithmPreviewSize);
        this.mCamera2Device.setPictureSize(this.mPictureSize);
        this.mCamera2Device.setPictureMaxImages(3);
        this.mCamera2Device.setPictureFormat(35);
        this.mCamera2Device.startFaceDetection();
        this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
        this.mCamera2Device.startPreviewSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), 1, false, false, null, getOperatingMode(), false, this);
    }

    private void startVideoRecording() {
        Log.d(TAG, "startVideoRecording");
        playCameraSound(2);
        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).setRecordingTimeState(1);
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        cloneProcess.processingPrepare();
        cloneProcess.processingStart();
        startCountDown();
        FenShenCam.startRecordVideo();
    }

    private void statCaptureHint(FenShenCam.Message message) {
        if (message != this.mLastMessage) {
            CameraStatUtils.trackCloneCaptureHint(this.mMode, message);
        }
    }

    private void stopCaptureToPreviewResult() {
        this.mHandler.post(I.INSTANCE);
    }

    private void stopVideoRecording(boolean z) {
        Log.d(TAG, "stopVideoRecording giveUp " + z);
        playCameraSound(3);
        cancelVideoCountDown();
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess != null) {
            cloneProcess.processingFinish();
        }
        if (!z) {
            FenShenCam.stopRecordVideo();
            return;
        }
        FenShenCam.cancelVideo();
        FenShenCam.start();
    }

    private void updateDeviceOrientation() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    private void updateFocusArea() {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            Rect cropRegion = getCropRegion();
            Rect activeArraySize = getActiveArraySize();
            this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            this.mCamera2Device.setAERegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
            if (this.mFocusAreaSupported) {
                this.mCamera2Device.setAFRegions(this.mFocusManager.getFocusAreas(cropRegion, activeArraySize));
            }
            String focusMode = CameraSettings.getFocusMode();
            if (!this.mFocusAreaSupported || "manual".equals(focusMode)) {
                this.mCamera2Device.resumePreview();
            }
        }
    }

    private void updateFocusMode() {
        setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
    }

    private void updateLiveRelated() {
        this.mCamera2Device.startPreviewCallback(this, null);
    }

    private void updatePictureAndPreviewSize() {
        this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        this.mPreviewSize = new CameraSize(1920, 1080);
        Log.d(TAG, "updatePictureAndPreviewSize previewSize: " + this.mPreviewSize.toString());
        this.mAlgorithmPreviewSize = new CameraSize(1280, 960);
        this.mPictureSize = null;
        int vh = DataRepository.dataItemFeature().vh();
        PictureSizeManager.initialize(this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(35), vh, this.mModuleIndex, this.mBogusCameraId);
        this.mPictureSize = PictureSizeManager.getBestPictureSize(this.mModuleIndex);
        Log.d(TAG, "updatePictureAndPreviewSize sizeLimit " + vh + ", mPictureSize " + this.mPictureSize + ", mAlgorithmPreviewSize " + this.mAlgorithmPreviewSize);
        CameraSize cameraSize = this.mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateRecordingTime(long j) {
        String millisecondToTimeString = Util.millisecondToTimeString((j + 950) - 450, false);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.updateRecordingTime(millisecondToTimeString);
        }
    }

    static /* synthetic */ void wd() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setCenterHint(8, null, null, 0);
        }
    }

    static /* synthetic */ void zd() {
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess != null) {
            cloneProcess.showStopAndCancel();
        }
    }

    public /* synthetic */ void a(Uri uri) {
        this.mJpgBytes = null;
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess != null) {
            cloneProcess.onSaveFinish(uri);
        }
    }

    public /* synthetic */ void a(FenShenCam.Message message, int i) {
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess == null || !cloneProcess.isAdded()) {
            Log.w(TAG, "updateCaptureMessage return cloneProcess is null, " + message.toString());
        } else if (message != FenShenCam.Message.ALIGN_TOO_LARGE_OR_FAILED || FenShenCam.getCurrentSubjectCount() < 2) {
            if (message == FenShenCam.Message.PREVIEW_NO_PERSON || message == FenShenCam.Message.NO_PERSON) {
                cloneProcess.setDetectedPersonInPreview(false);
            } else if (message == FenShenCam.Message.START) {
                cloneProcess.setDetectedPersonInPreview(true);
            }
            boolean z = message == FenShenCam.Message.ALIGN_TOO_LARGE_OR_FAILED;
            Log.d(TAG, "updateCaptureMessage " + message.toString());
            cloneProcess.updateCaptureMessage(i, z);
            if (message == FenShenCam.Message.ALIGN_TOO_LARGE_OR_FAILED || (message == FenShenCam.Message.NO_PERSON && FenShenCam.getCurrentSubjectCount() == 0)) {
                Log.d(TAG, "onCloneMessage " + message.toString());
                resetAndUnlock3A();
                cancelVideoCountDown();
                cloneProcess.setDetectedPersonInPreview(false);
                cloneProcess.prepare(this.mMode, true);
                this.mHandler.postDelayed(H.INSTANCE, 3000);
            }
        } else {
            Log.d(TAG, "too much movement, stop capture");
            cloneProcess.updateCaptureMessage(R.string.clone_too_much_movement_stop, false);
            cloneProcess.stopCaptureToPreviewResult(true);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void cancelFocus(boolean z) {
        if (isAlive() && isFrameAvailable() && getCameraState() != 0) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                if (z) {
                    camera2Proxy.setFocusMode(4);
                }
                this.mCamera2Device.cancelFocus(this.mModuleIndex);
            }
            if (getCameraState() != 3) {
                setCameraState(1);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void checkDisplayOrientation() {
        if (isCreated()) {
            super.checkDisplayOrientation();
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.setCameraDisplayOrientation(this.mCameraDisplayOrientation);
            }
            FocusManager2 focusManager2 = this.mFocusManager;
            if (focusManager2 != null) {
                focusManager2.setDisplayOrientation(this.mCameraDisplayOrientation);
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
        Log.d(TAG, "closeCamera E");
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.getCameraScreenNail().setExternalFrameProcessor(null);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setFocusCallback(null);
            this.mCamera2Device.setErrorCallback(null);
            this.mCamera2Device.stopPreviewCallback(true);
            this.mCamera2Device = null;
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        Log.d(TAG, "closeCamera X");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 9) {
                updateAntiBanding(CameraSettings.getAntiBanding());
            } else if (i == 14) {
                updateFocusMode();
            } else if (i == 25) {
                focusCenter();
            } else if (i == 29) {
                updateExposureMeteringMode();
            } else if (i == 35) {
                updateDeviceOrientation();
            } else if (i == 58) {
                updateBackSoftLightPreference();
            } else if (i == 66) {
                updateThermalLevel();
            } else if (i == 54) {
                updateLiveRelated();
            } else if (i != 55) {
                Log.d(TAG, "no consumer for this updateType: " + i);
            } else {
                updateModuleRelated();
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void fillFeatureControl(StartControl startControl) {
        StartControlFeatureDetail featureDetail = startControl.getFeatureDetail();
        featureDetail.addFragmentInfo(R.id.full_screen_feature, BaseFragmentDelegate.FRAGMENT_CLONE_PROCESS);
        featureDetail.hideFragment(R.id.bottom_action);
        featureDetail.hideFragment(R.id.bottom_popup_tips);
        featureDetail.hideFragment(R.id.bottom_popup_dual_camera_adjust);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        return CameraCapabilities.SESSION_OPERATION_MODE_CLONE_MODE;
    }

    @Override // com.android.camera.module.BaseModule
    public void initializeCapabilities() {
        super.initializeCapabilities();
        this.mContinuousFocusSupported = Util.isSupported(4, this.mCameraCapabilities.getSupportedFocusModes());
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        return getCameraState() == 3;
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public boolean isProcessorReady() {
        return isFrameAvailable();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        return this.mIsDuringShooting;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean judgeTapableRectByUiStyle() {
        return false;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean multiCapture() {
        return false;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess == null) {
            return super.onBackPressed();
        }
        CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_BACK_CLICK);
        cloneProcess.showExitConfirm(true);
        return true;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onBroadcastReceived(Context context, Intent intent) {
        if (intent != null && isAlive()) {
            if (CameraIntentManager.ACTION_SPEECH_SHUTTER.equals(intent.getAction())) {
                Log.d(TAG, "on Receive speech shutter broadcast action intent");
                onShutterButtonClick(110);
            }
            super.onBroadcastReceived(context, intent);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        startPreviewSession();
        this.mOnResumeTime = SystemClock.uptimeMillis();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneAction
    public void onCancelClicked() {
        cancelPhotoOrVideo();
        resetAndUnlock3A();
        FenShenCam.start();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureProgress(boolean z, boolean z2, boolean z3, boolean z4) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z, boolean z2, boolean z3, boolean z4) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z, boolean z2, boolean z3, boolean z4) {
        return parallelTaskData;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneAction
    public void onCloneGuideClicked() {
        Log.d(TAG, "onCloneGuideClicked");
        this.mPendingStart = true;
        cancelPhotoOrVideo();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        FenShenCam.initResources(this.mActivity);
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mHandler = new MainHandler(this.mActivity.getMainLooper());
        onCameraOpened();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
        this.mActivity.getSensorStateManager().setRotationVectorEnabled(true);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        this.mHandler.post(G.INSTANCE);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
        this.mActivity.getSensorStateManager().setRotationVectorEnabled(false);
        this.mActivity.getGLView().setPendingChange(true);
        this.mActivity.getGLView().queueEvent(E.INSTANCE);
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void onDrawFrame(Rect rect, int i, int i2, boolean z) {
        FenShenCam.render();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneAction
    public void onExitClicked() {
        Log.d(TAG, "onExitClicked");
        if (this.mMode == FenShenCam.Mode.VIDEO) {
            cancelVideoCountDown();
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FocusCallback
    public void onFocusStateChanged(FocusTask focusTask) {
        if (isCreated() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
                if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                    setCameraState(1);
                }
                this.mFocusManager.onFocusResult(focusTask);
                this.mActivity.getSensorStateManager().reset();
            } else if (focusTrigger == 2 || focusTrigger == 3) {
                String str = null;
                if (focusTask.isFocusing()) {
                    str = "onAutoFocusMoving start";
                }
                if (Util.sIsDumpLog && str != null) {
                    Log.v(TAG, str);
                }
                if (getCameraState() != 3 || focusTask.getFocusTrigger() == 3) {
                    this.mFocusManager.onFocusResult(focusTask);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneAction
    public void onFragmentResume() {
        Log.d(TAG, "onFragmentResume, cameraState = " + getCameraState());
        if (this.mPendingStart) {
            this.mPendingStart = false;
            cancelPhotoOrVideo();
            FenShenCam.start();
        }
        if (getCameraState() == 0) {
            resumePreview();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneAction
    public void onGiveUpClicked() {
        Log.d(TAG, "onGiveUpClicked");
        cancelPhotoOrVideo();
        resetAndUnlock3A();
        FenShenCam.start();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onHostStopAndNotifyActionStop() {
        super.onHostStopAndNotifyActionStop();
        Log.d(TAG, "onHostStopAndNotifyActionStop " + this.mIsFinished);
        if (this.mIsSegmentRecording) {
            this.mIsSegmentRecording = false;
            stopVideoRecording(true);
        }
        resumePreviewIfNeeded();
        doLaterReleaseIfNeed();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        if (r6 != 88) goto L_0x0051;
     */
    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        if (!(i == 24 || i == 25)) {
            if (i == 27 || i == 66) {
                if (keyEvent.getRepeatCount() == 0) {
                    performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                }
                return true;
            } else if (i != 87) {
            }
        }
        if (i == 24 || i == 88) {
            z = true;
        }
        if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4 && ((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    @WorkerThread
    public void onNewUriArrived(Uri uri, String str) {
        Handler handler;
        super.onNewUriArrived(uri, str);
        if (isAlive() && (handler = this.mHandler) != null) {
            handler.post(new D(this, uri));
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        tryRemoveCountDownMessage();
        this.mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTakenFinished(boolean z) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public boolean onPictureTakenImageConsumed(Image image, TotalCaptureResult totalCaptureResult) {
        ModeProtocol.CloneProcess cloneProcess = (ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418);
        if (cloneProcess == null || cloneProcess.getStatus() == Status.CAPTURING) {
            FenShenCam.addPhoto(image);
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, false);
            trackGeneralInfo(hashMap, 1, false, null, false, 0);
            return true;
        }
        Log.w(TAG, "onPictureTakenImageConsumed not capturing");
        return true;
    }

    @Override // com.android.camera2.Camera2Proxy.PreviewCallback
    public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
        try {
            FenShenCam.addPreviewFrame(image);
            return true;
        } catch (IllegalStateException e2) {
            Log.w(TAG, "addPreviewFrame fail, " + e2.getMessage());
            return true;
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        this.mActivity.onLayoutChange(rect);
        if (this.mFocusManager != null && this.mActivity.getCameraScreenNail() != null) {
            this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
            this.mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraMetaDataCallback
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        if (captureResult != null) {
            super.onPreviewMetaDataUpdate(captureResult);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(this.mHandler)) {
            this.mHandler.sendEmptyMessage(51);
        } else {
            Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession != null && isAlive()) {
            this.mHandler.sendEmptyMessage(9);
            previewWhenSessionSuccess();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewSizeChanged(int i, int i2) {
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setPreviewSize(i, i2);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onResume() {
        super.onResume();
        keepScreenOnAwhile();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
            Log.w(TAG, "onReviewDoneClicked return, configChanges is null");
        } else {
            configChanges.configClone(FenShenCam.Mode.PHOTO, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
            Log.w(TAG, "onReviewDoneClicked return, configChanges is null");
        } else {
            configChanges.configClone(FenShenCam.Mode.PHOTO, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneAction
    public void onSaveClicked() {
        FenShenCam.Mode mode = this.mMode;
        FenShenCam.Mode mode2 = FenShenCam.Mode.PHOTO;
        if (mode != mode2) {
            saveVideo();
        } else if (this.mJpgBytes == null) {
            Log.e(TAG, "onSaveClicked mJpgBytes is null");
        } else {
            CameraStatUtils.trackCloneCaptureParams(mode2, this.mPhotoSubjectCount, "");
            savePhoto(this.mJpgBytes);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (checkShutterCondition()) {
            if (i == 110) {
                this.mActivity.onUserInteraction();
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.hideRecommendDescTip(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC);
                }
            }
            setTriggerMode(i);
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.clearFocusView(1);
            }
            Log.d(TAG, "onShutterButtonClick mIsDuringShooting = " + this.mIsDuringShooting);
            if (!this.mIsDuringShooting) {
                this.mIsDuringShooting = true;
                ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).disableMenuItem(true, 164);
                ((ModeProtocol.CloneProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(418)).processingStart();
                if (this.mAeLockSupported) {
                    this.mCamera2Device.setAELock(true);
                }
                if (this.mAwbLockSupported) {
                    this.mCamera2Device.setAWBLock(true);
                }
                this.mCamera2Device.setFocusMode(1);
                this.mCamera2Device.resumePreview();
            }
            if (this.mMode == FenShenCam.Mode.PHOTO) {
                enableCameraControls(false);
                this.mCamera2Device.takePicture(this, null);
                playCameraSound(0);
                return;
            }
            if (!this.mIsSegmentRecording) {
                startVideoRecording();
            } else {
                stopVideoRecording(false);
            }
            this.mIsSegmentRecording = !this.mIsSegmentRecording;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSingleTapUp(int i, int i2, boolean z) {
        Camera2Proxy camera2Proxy;
        if (this.mPaused || this.mIsDuringShooting || (camera2Proxy = this.mCamera2Device) == null || !camera2Proxy.isSessionReady() || !isInTapableRect(i, i2) || getCameraState() == 3 || getCameraState() == 0) {
            Log.w(TAG, "onSingleTapUp: ignore, mIsDuringShooting " + this.mIsDuringShooting + ", getCameraState = " + getCameraState());
        } else if (!isFrameAvailable()) {
            Log.w(TAG, "onSingleTapUp: frame not available");
        } else if (((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromTapDown(i, i2)) {
            Log.w(TAG, "onSingleTapUp: ignore, handleBackStackFromTapDown");
        } else {
            this.mMainProtocol.setFocusViewType(true);
            this.mTouchFocusStartingTime = System.currentTimeMillis();
            Point point = new Point(i, i2);
            mapTapCoordinate(point);
            this.mFocusManager.onSingleTapUp(point.x, point.y, z);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onStop() {
        super.onStop();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneAction
    public void onStopClicked() {
        Log.d(TAG, "onStopClicked");
        if (this.mMode == FenShenCam.Mode.PHOTO) {
            this.mPhotoSubjectCount = this.mLastSubjectCount;
            FenShenCam.finishPhoto();
        }
    }

    @Override // com.android.camera.ui.V6CameraGLSurfaceView.RendererListener
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        Rect displayRect = Util.getDisplayRect(1);
        FenShenCam.renderInit(i, i2, 0, displayRect.top, i, displayRect.height());
    }

    @Override // com.android.camera.ui.V6CameraGLSurfaceView.RendererListener
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onThermalConstrained() {
        super.onThermalConstrained();
        onReviewCancelClicked();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onThumbnailClicked(View view) {
        if (!isDoingAction() && this.mActivity.getThumbnailUpdater().getThumbnail() != null) {
            this.mActivity.gotoGallery();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean onWaitingFocusFinished() {
        return !isBlockSnap() && isAlive();
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        Log.d(TAG, "pausePreview");
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        this.mIsFinished = true;
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (!this.mPaused && getCameraState() != 0 && i2 == 0) {
            if (z) {
                onShutterButtonFocus(true, 1);
                onShutterButtonClick(i);
                return;
            }
            onShutterButtonFocus(false, 0);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(419, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 212);
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void releaseRender() {
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.d(TAG, "resumePreview");
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
            setCameraState(1);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void setFrameAvailable(boolean z) {
        super.setFrameAvailable(z);
        Log.d(TAG, "setFrameAvailable " + z);
        if (z) {
            this.mActivity.getCameraScreenNail().setExternalFrameProcessor(this);
            resumePreviewIfNeeded();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return this.mIsSegmentRecording || this.mIsFinished;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            Log.v(TAG, "startFocus");
            if (this.mFocusOrAELockSupported) {
                this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
            } else {
                this.mCamera2Device.resumePreview();
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            this.mCamera2Device.setDisplayOrientation(this.mCameraDisplayOrientation);
            if (this.mAeLockSupported) {
                this.mCamera2Device.setAELock(false);
            }
            if (this.mAwbLockSupported) {
                this.mCamera2Device.setAWBLock(false);
            }
            this.mCamera2Device.setFocusMode(4);
            this.mCamera2Device.resumePreview();
            setCameraState(1);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        Log.d(TAG, "unRegisterModulePersistProtocol");
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(419, this);
        getActivity().getImplFactory().detachAdditional();
    }

    @Override // com.android.camera.module.BaseModule
    public void updatePreviewSurface() {
        super.updatePreviewSurface();
        GLCanvasImpl gLCanvas = this.mActivity.getGLView().getGLCanvas();
        int width = gLCanvas.getWidth();
        int height = gLCanvas.getHeight();
        FenShenCam.renderInit(width, height, 0, 0, width, height);
    }

    public /* synthetic */ void yd() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }
}
