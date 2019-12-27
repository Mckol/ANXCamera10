package com.android.camera.module;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.MiuiSettings;
import android.support.v4.app.FrameMetricsAggregator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.ChangeManager;
import com.android.camera.FileCompat;
import com.android.camera.LocationManager;
import com.android.camera.OnClickAttr;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.SensorStateManager;
import com.android.camera.ThermalDetector;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.permission.PermissionManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraHardwareFace;
import com.mi.config.b;
import com.ss.android.vesdk.VEEditor;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class VideoBase extends BaseModule implements Camera2Proxy.FaceDetectionCallback, Camera2Proxy.FocusCallback, Camera2Proxy.CameraPreviewCallback, FocusManager2.Listener, ModeProtocol.CameraAction, ModeProtocol.PlayVideoProtocol {
    protected static final int FILE_NUMBER_SINGLE = -1;
    private static final boolean HOLD_WHEN_SAVING_VIDEO = false;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MIN_BACK_RECORDING_MINUTE = 20;
    private static final int MIN_FRONT_RECORDING_MINUTE = 10;
    private static final boolean SHOW_FACE_VIEW = false;
    public static final String START_VIDEO_RECORDING_ACTION = "com.android.camera.action.start_video_recording";
    public static final String STOP_VIDEO_RECORDING_ACTION = "com.android.camera.action.stop_video_recording";
    protected static String TAG;
    private boolean m3ALocked;
    protected AudioManager mAudioManager;
    protected String mBaseFileName;
    protected BeautyValues mBeautyValues;
    protected CameraCaptureSession mCurrentSession;
    protected String mCurrentVideoFilename;
    protected Uri mCurrentVideoUri;
    protected ContentValues mCurrentVideoValues;
    private AlertDialog mDialog;
    protected boolean mFaceDetected;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    protected FocusManager2 mFocusManager;
    protected boolean mInStartingFocusRecording;
    protected long mIntentRequestSize;
    protected boolean mIsBeautyFrontOn;
    private boolean mIsSessionReady;
    private boolean mIsVideoCaptureIntent;
    protected int mMaxVideoDurationInMs;
    protected volatile boolean mMediaRecorderRecording;
    protected boolean mMediaRecorderRecordingPaused;
    private int mMessageId;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    protected long mOnResumeTime;
    protected int mOrientationCompensationAtRecordStart;
    protected int mOriginalMusicVolume;
    protected int mOutputFormat;
    protected final PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        public void onCallStateChanged(int i, String str) {
            if (i == 2 && VideoBase.this.mMediaRecorderRecording) {
                Log.w(VideoBase.TAG, "CALL_STATE_OFFHOOK");
                VideoBase.this.onStop();
            }
            super.onCallStateChanged(i, str);
        }
    };
    protected boolean mPreviewing;
    protected long mRecordingStartTime;
    protected boolean mSavePowerMode;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        public boolean isWorking() {
            return VideoBase.this.isAlive() && VideoBase.this.mPreviewing;
        }

        public void notifyDevicePostureChanged() {
            VideoBase.this.mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        public void onDeviceBecomeStable() {
            Log.v(VideoBase.TAG, "onDeviceBecomeStable");
        }

        public void onDeviceBeginMoving() {
        }

        public void onDeviceKeepMoving(double d2) {
            if (!VideoBase.this.mMainProtocol.isEvAdjusted(true) && !VideoBase.this.mPaused && Util.isTimeout(System.currentTimeMillis(), VideoBase.this.mTouchFocusStartingTime, 3000) && !VideoBase.this.is3ALocked()) {
                FocusManager2 focusManager2 = VideoBase.this.mFocusManager;
                if (focusManager2 != null && focusManager2.isNeedCancelAutoFocus() && !VideoBase.this.isRecording()) {
                    VideoBase.this.mFocusManager.onDeviceKeepMoving(d2);
                }
            }
        }

        public void onDeviceKeepStable() {
        }

        public void onDeviceLieChanged(boolean z) {
        }

        public void onDeviceOrientationChanged(float f2, boolean z) {
        }

        public void onDeviceRotationChanged(float[] fArr) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
        }
    };
    protected boolean mSnapshotInProgress;
    protected StereoSwitchThread mStereoSwitchThread;
    protected TelephonyManager mTelephonyManager;
    private int mTitleId;
    protected long mTouchFocusStartingTime;
    protected ParcelFileDescriptor mVideoFileDescriptor;
    protected String mVideoFocusMode;
    protected CameraSize mVideoSize;

    private static class MainHandler extends Handler {
        private WeakReference<VideoBase> mModule;

        public MainHandler(VideoBase videoBase) {
            this.mModule = new WeakReference<>(videoBase);
        }

        public void handleMessage(Message message) {
            VideoBase videoBase = (VideoBase) this.mModule.get();
            if (videoBase != null) {
                if (!videoBase.isCreated()) {
                    removeCallbacksAndMessages((Object) null);
                } else if (videoBase.getActivity() != null) {
                    int i = message.what;
                    if (i != 2) {
                        if (i == 4) {
                            if (Util.getDisplayRotation(videoBase.mActivity) != videoBase.mDisplayRotation && !videoBase.mMediaRecorderRecording) {
                                videoBase.startPreview();
                            }
                            if (SystemClock.uptimeMillis() - videoBase.mOnResumeTime < 5000) {
                                sendEmptyMessageDelayed(4, 100);
                                return;
                            }
                            return;
                        } else if (i != 17) {
                            boolean z = false;
                            if (i == 35) {
                                boolean z2 = message.arg1 > 0;
                                if (message.arg2 > 0) {
                                    z = true;
                                }
                                videoBase.handleUpdateFaceView(z2, z);
                                return;
                            } else if (i != 40) {
                                if (i == 55) {
                                    Log.e(VideoBase.TAG, "autoFocus timeout!");
                                    videoBase.mFocusManager.resetFocused();
                                    videoBase.onWaitingFocusFinished();
                                    return;
                                } else if (i == 9) {
                                    videoBase.onPreviewStart();
                                    videoBase.mStereoSwitchThread = null;
                                    return;
                                } else if (i == 10) {
                                    videoBase.stopVideoRecording(true, false);
                                    videoBase.mOpenCameraFail = true;
                                    videoBase.onCameraException();
                                    return;
                                } else if (i == 42) {
                                    videoBase.updateRecordingTime();
                                    return;
                                } else if (i == 43) {
                                    videoBase.restoreMusicSound();
                                    return;
                                } else if (i == 45) {
                                    videoBase.setActivity((Camera) null);
                                    return;
                                } else if (i == 46) {
                                    videoBase.onWaitStopCallbackTimeout();
                                    return;
                                } else if (i == 51) {
                                    videoBase.stopVideoRecording(true, false);
                                    if (!videoBase.mActivity.isActivityPaused()) {
                                        videoBase.mOpenCameraFail = true;
                                        videoBase.onCameraException();
                                        return;
                                    }
                                    return;
                                } else if (i == 52) {
                                    videoBase.enterSavePowerMode();
                                    return;
                                } else if (!BaseModule.DEBUG) {
                                    Log.e(VideoBase.TAG, "no consumer for this message: " + message.what);
                                } else {
                                    throw new RuntimeException("no consumer for this message: " + message.what);
                                }
                            } else if (CameraSettings.isStereoModeOn()) {
                                videoBase.updateTipMessage(6, R.string.dual_camera_use_hint, 2);
                                return;
                            } else {
                                return;
                            }
                        } else {
                            removeMessages(17);
                            removeMessages(2);
                            videoBase.getWindow().addFlags(128);
                            sendEmptyMessageDelayed(2, (long) videoBase.getScreenDelay());
                            return;
                        }
                    }
                    videoBase.getWindow().clearFlags(128);
                }
            }
        }
    }

    protected class StereoSwitchThread extends Thread {
        private volatile boolean mCancelled;

        protected StereoSwitchThread() {
        }

        public void cancel() {
            this.mCancelled = true;
        }

        public void run() {
            VideoBase.this.closeCamera();
            if (!this.mCancelled) {
                VideoBase.this.openCamera();
                if (VideoBase.this.hasCameraException()) {
                    VideoBase.this.onCameraException();
                } else if (!this.mCancelled) {
                    CameraSettings.resetZoom();
                    CameraSettings.resetExposure();
                    VideoBase.this.onCameraOpened();
                    VideoBase.this.readVideoPreferences();
                    VideoBase.this.resizeForPreviewAspectRatio();
                    if (!this.mCancelled) {
                        VideoBase.this.startPreview();
                        VideoBase.this.onPreviewStart();
                        VideoBase.this.mHandler.sendEmptyMessage(9);
                    }
                }
            }
        }
    }

    public VideoBase(String str) {
        TAG = str;
        this.mHandler = new MainHandler(this);
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private void deleteCurrentVideo() {
        String str = this.mCurrentVideoFilename;
        if (str != null) {
            deleteVideoFile(str);
            this.mCurrentVideoFilename = null;
            Uri uri = this.mCurrentVideoUri;
            if (uri != null) {
                Util.safeDelete(uri, (String) null, (String[]) null);
                this.mCurrentVideoUri = null;
            }
        }
        this.mActivity.getScreenHint().updateHint();
    }

    private Bitmap getReviewBitmap() {
        Bitmap bitmap;
        ParcelFileDescriptor parcelFileDescriptor = this.mVideoFileDescriptor;
        if (parcelFileDescriptor != null) {
            bitmap = Thumbnail.createVideoThumbnailBitmap(parcelFileDescriptor.getFileDescriptor(), this.mDisplayRect.width());
        } else {
            String str = this.mCurrentVideoFilename;
            bitmap = str != null ? Thumbnail.createVideoThumbnailBitmap(str, this.mDisplayRect.width()) : null;
        }
        return bitmap != null ? Util.rotateAndMirror(bitmap, -this.mOrientationCompensationAtRecordStart, isFrontCamera()) : bitmap;
    }

    /* access modifiers changed from: private */
    public void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            this.mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null && 1 != camera2Proxy.getFocusMode()) {
                this.mMainProtocol.updateFaceView(z, true, isFrontCamera, true, this.mCameraDisplayOrientation);
            }
        }
    }

    private void hideAlert() {
        if (this.mCamera2Device.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
        this.mMainProtocol.hideReviewViews();
        enableCameraControls(true);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = VideoBase.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).subscribe();
    }

    private void onStereoModeChanged() {
        enableCameraControls(false);
        this.mActivity.getSensorStateManager().setFocusSensorEnabled(false);
        cancelFocus(false);
        this.mStereoSwitchThread = new StereoSwitchThread();
        this.mStereoSwitchThread.start();
    }

    private void restorePreferences() {
        if (isZoomSupported()) {
            setZoomRatio(1.0f);
        }
        onSharedPreferenceChanged();
    }

    private void setOrientationParameter() {
        if (!isDeparted() && this.mCamera2Device != null && this.mOrientation != -1) {
            if (this.mPreviewing) {
                updatePreferenceInWorkThread(35);
                return;
            }
            GlobalConstant.sCameraSetupScheduler.scheduleDirect(new w(this));
        }
    }

    private void startPlayVideoActivity() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(this.mCurrentVideoUri, Util.convertOutputFormatToMimeType(this.mOutputFormat));
        intent.setFlags(1);
        try {
            this.mActivity.startActivity(intent);
        } catch (ActivityNotFoundException e2) {
            String str = TAG;
            Log.e(str, "failed to view video " + this.mCurrentVideoUri, e2);
        }
    }

    private void updateFaceView(boolean z, boolean z2) {
        if (this.mHandler.hasMessages(35)) {
            this.mHandler.removeMessages(35);
        }
        this.mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    public /* synthetic */ void Bd() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    /* access modifiers changed from: protected */
    public void animateHold() {
    }

    /* access modifiers changed from: protected */
    public void animateSlide() {
    }

    public void cancelFocus(boolean z) {
        if (isDeviceAlive()) {
            if (!isFrameAvailable()) {
                Log.e(TAG, "cancelFocus: frame not available");
                return;
            }
            String str = TAG;
            Log.v(str, "cancelFocus: " + z);
            if (z) {
                setVideoFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO, true);
            }
            this.mCamera2Device.cancelFocus(this.mModuleIndex);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkCallingState() {
        if (2 != this.mTelephonyManager.getCallState()) {
            return true;
        }
        showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_calling_alert);
        return false;
    }

    public void checkDisplayOrientation() {
        if (isCreated()) {
            super.checkDisplayOrientation();
            FocusManager2 focusManager2 = this.mFocusManager;
            if (focusManager2 != null) {
                focusManager2.setDisplayOrientation(this.mCameraDisplayOrientation);
            }
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setDisplayOrientation(this.mCameraDisplayOrientation);
            }
            this.mMainProtocol.setCameraDisplayOrientation(this.mCameraDisplayOrientation);
        }
    }

    /* access modifiers changed from: protected */
    public void cleanupEmptyFile() {
        String str = this.mCurrentVideoFilename;
        if (str != null) {
            File file = new File(str);
            if (!file.exists()) {
                String str2 = TAG;
                Log.d(str2, "no video file: " + this.mCurrentVideoFilename);
                this.mCurrentVideoFilename = null;
            } else if (file.length() == 0) {
                if (!Storage.isUseDocumentMode()) {
                    file.delete();
                } else {
                    FileCompat.deleteFile(this.mCurrentVideoFilename);
                }
                String str3 = TAG;
                Log.d(str3, "delete empty video file: " + this.mCurrentVideoFilename);
                this.mCurrentVideoFilename = null;
            }
        }
    }

    public void closeCamera() {
        Log.v(TAG, "closeCamera: E");
        this.mPreviewing = false;
        this.mSnapshotInProgress = false;
        FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onComplete();
        }
        Disposable disposable = this.mMetaDataDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setMetaDataCallback((Camera2Proxy.CameraMetaDataCallback) null);
            this.mCamera2Device.setFocusCallback((Camera2Proxy.FocusCallback) null);
            this.mCamera2Device.setErrorCallback((Camera2Proxy.CameraErrorCallback) null);
            unlockAEAF();
            this.mCamera2Device = null;
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.destroy();
        }
        Log.v(TAG, "closeCamera: X");
    }

    /* access modifiers changed from: protected */
    public void closeVideoFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = this.mVideoFileDescriptor;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e2) {
                Log.e(TAG, "fail to close fd", e2);
            }
            this.mVideoFileDescriptor = null;
        }
    }

    /* access modifiers changed from: protected */
    public void deleteVideoFile(String str) {
        String str2 = TAG;
        Log.v(str2, "delete invalid video " + str);
        if (!new File(str).delete()) {
            String str3 = TAG;
            Log.v(str3, "fail to delete " + str);
        }
    }

    /* access modifiers changed from: protected */
    public void doLaterReleaseIfNeed() {
        Camera camera = this.mActivity;
        if (camera != null && camera.isActivityPaused()) {
            Log.d(TAG, "doLaterRelease");
            this.mActivity.pause();
            this.mActivity.releaseAll(true, true);
        }
    }

    /* access modifiers changed from: protected */
    public void doReturnToCaller(boolean z) {
        int i;
        Intent intent = new Intent();
        if (z) {
            i = -1;
            intent.setData(this.mCurrentVideoUri);
            intent.setFlags(1);
        } else {
            i = 0;
        }
        this.mActivity.setResult(i, intent);
        this.mActivity.finish();
    }

    /* access modifiers changed from: protected */
    public boolean enableFaceDetection() {
        return DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    public void enterMutexMode(int i) {
        setZoomRatio(1.0f);
        this.mSettingsOverrider.overrideSettings(CameraSettings.KEY_WHITE_BALANCE, null, CameraSettings.KEY_COLOR_EFFECT, null);
        onSharedPreferenceChanged();
    }

    /* access modifiers changed from: protected */
    public void enterSavePowerMode() {
        String str = TAG;
        Log.d(str, "currentBrightness: " + this.mActivity.getCurrentBrightness());
        Camera camera = this.mActivity;
        if (camera != null && camera.getCurrentBrightness() == 255) {
            Log.d(TAG, "enterSavePowerMode");
            this.mHandler.post(new Runnable() {
                public void run() {
                    Camera camera = VideoBase.this.mActivity;
                    if (camera != null) {
                        camera.setWindowBrightness(81);
                        VideoBase.this.mSavePowerMode = true;
                    }
                }
            });
        }
    }

    public void exitMutexMode(int i) {
        this.mSettingsOverrider.restoreSettings();
        onSharedPreferenceChanged();
    }

    /* access modifiers changed from: protected */
    public void exitSavePowerMode() {
        this.mHandler.removeMessages(52);
        if (this.mSavePowerMode) {
            Log.d(TAG, "exitSavePowerMode");
            this.mHandler.post(new Runnable() {
                public void run() {
                    Camera camera = VideoBase.this.mActivity;
                    if (camera != null) {
                        camera.restoreWindowBrightness();
                        VideoBase.this.mSavePowerMode = false;
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public ContentValues genContentValues(int i, int i2) {
        return genContentValues(i, i2, (String) null, true);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0051, code lost:
        if (r8.equals(com.android.camera.data.data.config.ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120) != false) goto L_0x0055;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006c  */
    public ContentValues genContentValues(int i, int i2, String str, boolean z) {
        String str2;
        String str3;
        String createName = createName(System.currentTimeMillis(), i2);
        boolean z2 = false;
        if (i2 > 0) {
            String format = String.format(Locale.ENGLISH, "_%d", new Object[]{Integer.valueOf(i2)});
            createName = createName + format;
        }
        if (!TextUtils.isEmpty(str)) {
            int hashCode = str.hashCode();
            if (hashCode != -1150307548) {
                if (hashCode == -1150306525 && str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240)) {
                    z2 = true;
                    if (z2) {
                        createName = createName + Storage.HSR_120_SUFFIX;
                    } else if (z2) {
                        createName = createName + Storage.HSR_240_SUFFIX;
                    }
                }
            }
            z2 = true;
            if (z2) {
            }
        }
        String str4 = createName + Util.convertOutputFormatToFileExt(i);
        String convertOutputFormatToMimeType = Util.convertOutputFormatToMimeType(i);
        if (!TextUtils.isEmpty(str) && str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960)) {
            String generatePrimaryTempFile = Storage.isUseDocumentMode() ? Storage.generatePrimaryTempFile() : Storage.generateTempFilepath();
            str2 = generatePrimaryTempFile + '/' + str4;
            Util.createFile(new File(generatePrimaryTempFile + File.separator + Storage.AVOID_SCAN_FILE_NAME));
        } else if (z) {
            str2 = Storage.generateFilepath(str4);
        } else {
            String generatePrimaryDirectoryPath = Storage.generatePrimaryDirectoryPath();
            Util.mkdirs(new File(generatePrimaryDirectoryPath), FrameMetricsAggregator.EVERY_DURATION, -1, -1);
            if (Util.isPathExist(generatePrimaryDirectoryPath)) {
                str3 = Storage.generatePrimaryFilepath(str4);
            } else {
                str3 = Storage.DIRECTORY + '/' + str4;
            }
            str2 = str3;
        }
        Log.d(TAG, "genContentValues: path=" + str2);
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", createName);
        contentValues.put("_display_name", str4);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str2);
        contentValues.put("resolution", Integer.toString(this.mVideoSize.width) + "x" + Integer.toString(this.mVideoSize.height));
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        return contentValues;
    }

    /* access modifiers changed from: protected */
    public int getCameraRotation() {
        return ((this.mOrientationCompensation - this.mDisplayRotation) + MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT) % MiuiSettings.ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
    }

    public CameraSize getVideoSize() {
        return this.mVideoSize;
    }

    /* access modifiers changed from: protected */
    public void initializeCapabilities() {
        super.initializeCapabilities();
        this.mContinuousFocusSupported = Util.isSupported(3, this.mCameraCapabilities.getSupportedFocusModes());
    }

    /* access modifiers changed from: protected */
    public void initializeFocusManager() {
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

    /* access modifiers changed from: protected */
    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    /* access modifiers changed from: protected */
    public boolean isAEAFLockSupported() {
        return true;
    }

    public boolean isCameraEnabled() {
        return this.mPreviewing;
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSessionReady() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        return camera2Proxy != null && camera2Proxy.isSessionReady();
    }

    public boolean isCaptureIntent() {
        return this.mIsVideoCaptureIntent;
    }

    public boolean isDoingAction() {
        return this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused;
    }

    /* access modifiers changed from: protected */
    public boolean isFaceBeautyOn(BeautyValues beautyValues) {
        if (beautyValues == null) {
            return false;
        }
        return beautyValues.isFaceBeautyOn();
    }

    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    public boolean isMeteringAreaOnly() {
        return !this.mFocusAreaSupported && this.mMeteringAreaSupported && !this.mFocusOrAELockSupported;
    }

    public boolean isRecording() {
        return this.mMediaRecorderRecording;
    }

    public boolean isSelectingCapturedResult() {
        return isCaptureIntent() && ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getActiveFragment(R.id.bottom_action) == 4083;
    }

    /* access modifiers changed from: protected */
    public boolean isSessionReady() {
        return this.mIsSessionReady;
    }

    public boolean isShowAeAfLockIndicator() {
        return this.m3ALocked;
    }

    public boolean isThermalThreshold() {
        if (!this.mMediaRecorderRecording) {
            return false;
        }
        long uptimeMillis = (SystemClock.uptimeMillis() - this.mRecordingStartTime) / 60000;
        return isFrontCamera() ? uptimeMillis >= 10 : uptimeMillis >= 20;
    }

    public boolean isUnInterruptable() {
        this.mUnInterruptableReason = null;
        if (isPostProcessing()) {
            this.mUnInterruptableReason = "post process";
        }
        return this.mUnInterruptableReason != null;
    }

    public boolean isUseFaceInfo() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isZoomEnabled() {
        return !CameraSettings.isStereoModeOn() && !CameraSettings.isFrontCamera() && !CameraSettings.isVideoBokehOn();
    }

    /* access modifiers changed from: protected */
    public void keepPowerSave() {
        Log.d(TAG, "keepPowerSave");
        exitSavePowerMode();
        this.mHandler.sendEmptyMessageDelayed(52, 1500000);
    }

    /* access modifiers changed from: protected */
    public void keepScreenOn() {
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(52);
        getWindow().addFlags(128);
    }

    /* access modifiers changed from: protected */
    public void keepScreenOnAwhile() {
        this.mHandler.sendEmptyMessageDelayed(17, 1000);
    }

    /* access modifiers changed from: protected */
    public void lockAEAF() {
        Log.d(TAG, "lockAEAF");
        if (this.mAeLockSupported) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setAELock(true);
            }
        }
        this.mFocusManager.setAeAwbLock(true);
        this.m3ALocked = true;
    }

    public boolean multiCapture() {
        Log.v(TAG, "multiCapture");
        return false;
    }

    public void notifyError() {
        if (currentIsMainThread() && isRecording() && !isPostProcessing()) {
            stopVideoRecording(true, false);
        }
        super.notifyError();
    }

    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    public boolean onBackPressed() {
        if (!isFrameAvailable() || this.mStereoSwitchThread != null) {
            return false;
        }
        if (this.mMediaRecorderRecording) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mLastBackPressedTime > 3000) {
                this.mLastBackPressedTime = currentTimeMillis;
                Toast.makeText(this.mActivity, getString(R.string.record_back_pressed_hint), 0).show();
            } else {
                stopVideoRecording(true, false);
            }
            return true;
        } else if (CameraSettings.isStereoModeOn()) {
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    public void onBeautyParameterChanged() {
        boolean isFaceBeautyOn = CameraSettings.isFaceBeautyOn(this.mModuleIndex, this.mBeautyValues);
        if (this.mIsBeautyFrontOn != isFaceBeautyOn) {
            boolean isVideoBokehOn = CameraSettings.isVideoBokehOn();
            String str = TAG;
            Log.d(str, "onBeautyParameterChanged: isVideoBokehOn = " + isVideoBokehOn);
            restartModule();
        } else {
            updatePreferenceInWorkThread(13);
        }
        this.mIsBeautyFrontOn = isFaceBeautyOn;
    }

    public void onBroadcastReceived(Context context, Intent intent) {
        super.onBroadcastReceived(context, intent);
        String action = intent.getAction();
        if ("android.intent.action.MEDIA_EJECT".equals(action)) {
            if (Storage.isCurrentStorageIsSecondary()) {
                Storage.switchToPhoneStorage();
                stopVideoRecording(true, false);
            }
        } else if ("android.intent.action.ACTION_SHUTDOWN".equals(action) || "android.intent.action.REBOOT".equals(action)) {
            Log.i(TAG, "onBroadcastReceived: device shutdown or reboot");
            stopVideoRecording(true, false);
        }
    }

    /* access modifiers changed from: protected */
    public void onCameraOpened() {
        super.onCameraOpened();
        initMetaParser();
    }

    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (isCreated() && cameraHardwareFaceArr != null && b.hi()) {
            boolean z = cameraHardwareFaceArr.length > 0;
            if (z != this.mFaceDetected && isFrontCamera() && this.mModuleIndex == 162) {
                this.mCamera2Device.resumePreview();
            }
            this.mFaceDetected = z;
        }
    }

    public void onFocusStateChanged(FocusTask focusTask) {
        if (isCreated() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                String str = TAG;
                Log.v(str, "focusTime=" + focusTask.getElapsedTime() + "ms focused=" + focusTask.isSuccess() + " waitForRecording=" + this.mFocusManager.isFocusingSnapOnFinish());
                this.mMainProtocol.setFocusViewType(true);
                this.mFocusManager.onFocusResult(focusTask);
                this.mActivity.getSensorStateManager().reset();
                if (focusTask.isSuccess() && this.m3ALocked) {
                    this.mCamera2Device.lockExposure(true);
                }
            } else if (focusTrigger == 2 && !this.mMediaRecorderRecording && !this.m3ALocked) {
                this.mFocusManager.onFocusResult(focusTask);
            }
        }
    }

    public void onHostStopAndNotifyActionStop() {
        if (this.mInStartingFocusRecording) {
            this.mInStartingFocusRecording = false;
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
        }
        if (isRecording() && isCameraSessionReady()) {
            stopVideoRecording(true, true);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.mPaused) {
            return true;
        }
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        if (!(i == 24 || i == 25)) {
            if (i == 27 || i == 66) {
                if (keyEvent.getRepeatCount() == 0) {
                    if (isIgnoreTouchEvent()) {
                        return true;
                    }
                    if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable()) {
                        performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    }
                    return true;
                }
                return super.onKeyDown(i, keyEvent);
            } else if (!(i == 87 || i == 88)) {
                if (i != 700) {
                    if (i == 701 && isRecording() && !isPostProcessing()) {
                        if (!isFrontCamera()) {
                            return false;
                        }
                        stopVideoRecording(true, false);
                    }
                } else if (isRecording() && !isPostProcessing()) {
                    if (!isBackCamera()) {
                        return false;
                    }
                    stopVideoRecording(true, false);
                }
                return super.onKeyDown(i, keyEvent);
            }
        }
        if (isIgnoreTouchEvent()) {
            return true;
        }
        if (i == 24 || i == 88) {
            z = true;
        }
        if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            if (i == 27 || i == 66) {
                return true;
            }
        } else if (((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onLongPress(float f2, float f3) {
        int i = (int) f2;
        int i2 = (int) f3;
        if (isInTapableRect(i, i2)) {
            onSingleTapUp(i, i2, true);
            if (isAEAFLockSupported() && CameraSettings.isAEAFLockSupport()) {
                lockAEAF();
            }
            this.mMainProtocol.performHapticFeedback(0);
        }
    }

    public void onNewIntent() {
        changeConflictPreference();
        setCaptureIntent(this.mActivity.getCameraIntentManager().isVideoCaptureIntent());
    }

    public void onOrientationChanged(int i, int i2, int i3) {
        if (!CameraSettings.isVideoBokehOn()) {
            super.onOrientationChanged(i, i2, i3);
        } else if (i != -1) {
            this.mOrientation = i;
            EffectController.getInstance().setOrientation(Util.getShootOrientation(this.mActivity, this.mOrientation));
            checkActivityOrientation();
            if (this.mOrientationCompensation != i2) {
                this.mOrientationCompensation = i2;
                setOrientationParameter();
            }
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        if (captureResult != null) {
            super.onPreviewMetaDataUpdate(captureResult);
            FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
            if (flowableEmitter != null) {
                flowableEmitter.onNext(captureResult);
            }
        }
    }

    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
        String str = TAG;
        Log.d(str, "onPreviewSessionClosed: " + cameraCaptureSession);
        CameraCaptureSession cameraCaptureSession2 = this.mCurrentSession;
        if (cameraCaptureSession2 != null && cameraCaptureSession2.equals(cameraCaptureSession)) {
            this.mCurrentSession = null;
            setSessionReady(false);
        }
    }

    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(this.mHandler)) {
            String str = TAG;
            Log.d(str, "onPreviewSessionFailed: " + cameraCaptureSession);
            CameraCaptureSession cameraCaptureSession2 = this.mCurrentSession;
            if (cameraCaptureSession2 != null && cameraCaptureSession2.equals(cameraCaptureSession)) {
                this.mCurrentSession = null;
                setSessionReady(false);
            }
            this.mHandler.sendEmptyMessage(51);
            return;
        }
        Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
    }

    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        String str = TAG;
        Log.d(str, "onPreviewSessionSuccess: " + cameraCaptureSession);
        if (cameraCaptureSession != null && isAlive()) {
            this.mCurrentSession = cameraCaptureSession;
            setSessionReady(true);
        }
    }

    public void onPreviewSizeChanged(int i, int i2) {
        Log.v(TAG, String.format(Locale.ENGLISH, "onPreviewSizeChanged: %dx%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setPreviewSize(i, i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onPreviewStart() {
    }

    public void onResume() {
        super.onResume();
        if (!isRecording() && !this.mOpenCameraFail && !this.mCameraDisabled && PermissionManager.checkCameraLaunchPermissions()) {
            updateStereoSettings(true);
            if (!this.mPreviewing) {
                startPreview();
            }
            this.mHandler.sendEmptyMessage(9);
            keepScreenOnAwhile();
            onSettingsBack();
            if (this.mPreviewing) {
                this.mOnResumeTime = SystemClock.uptimeMillis();
                this.mHandler.sendEmptyMessageDelayed(4, 100);
            }
        }
    }

    @OnClickAttr
    public void onReviewCancelClicked() {
        if (isSelectingCapturedResult()) {
            deleteCurrentVideo();
            hideAlert();
            return;
        }
        stopVideoRecording(true, false);
        doReturnToCaller(false);
    }

    @OnClickAttr
    public void onReviewDoneClicked() {
        this.mMainProtocol.hideReviewViews();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(6);
        }
        doReturnToCaller(true);
    }

    @OnClickAttr
    public void onReviewPlayClicked(View view) {
        startPlayVideoActivity();
    }

    /* access modifiers changed from: protected */
    public void onSettingsBack() {
        ChangeManager changeManager = CameraSettings.sCameraChangeManager;
        if (changeManager.check(3)) {
            changeManager.clear(3);
            restorePreferences();
        } else if (changeManager.check(1)) {
            changeManager.clear(1);
            onSharedPreferenceChanged();
        }
    }

    public void onShutterButtonClick(int i) {
    }

    public void onShutterButtonFocus(boolean z, int i) {
    }

    public boolean onShutterButtonLongClick() {
        Log.v(TAG, "onShutterButtonLongClick");
        return false;
    }

    public void onShutterButtonLongClickCancel(boolean z) {
        onShutterButtonFocus(false, 2);
    }

    @OnClickAttr
    public void onThumbnailClicked(View view) {
        if (!this.mMediaRecorderRecording && this.mActivity.getThumbnailUpdater().getThumbnail() != null) {
            this.mActivity.gotoGallery();
        }
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        if (!this.mMediaRecorderRecording) {
            keepScreenOnAwhile();
        }
    }

    /* access modifiers changed from: protected */
    public void onWaitStopCallbackTimeout() {
    }

    public boolean onWaitingFocusFinished() {
        if (!isFrameAvailable()) {
            return false;
        }
        Log.v(TAG, CameraStat.CATEGORY_CAMERA);
        this.mHandler.removeMessages(55);
        if (!this.mInStartingFocusRecording) {
            return false;
        }
        this.mInStartingFocusRecording = false;
        startVideoRecording();
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        String str = TAG;
        Log.d(str, "onWindowFocusChanged: " + z);
        if (!this.mMediaRecorderRecording) {
            return;
        }
        if (z) {
            keepPowerSave();
        } else {
            exitSavePowerMode();
        }
    }

    /* access modifiers changed from: protected */
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.mActivity, CameraPreferenceActivity.class);
        intent.putExtra(CameraPreferenceActivity.FROM_WHERE, this.mModuleIndex);
        intent.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_camera_settings_category));
        if (this.mActivity.startFromKeyguard()) {
            intent.putExtra(CameraIntentManager.EXTRA_START_WHEN_LOCKED, true);
        }
        this.mActivity.startActivity(intent);
        this.mActivity.setJumpFlag(2);
        CameraStatUtil.trackGotoSettings(this.mModuleIndex);
    }

    /* access modifiers changed from: protected */
    public void parseIntent(Intent intent) {
        if (intent.getExtras() != null) {
            this.mIntentRequestSize = this.mActivity.getCameraIntentManager().getRequestSize();
            Uri extraSavedUri = this.mActivity.getCameraIntentManager().getExtraSavedUri();
            if (extraSavedUri != null) {
                try {
                    this.mVideoFileDescriptor = CameraAppImpl.getAndroidContext().getContentResolver().openFileDescriptor(extraSavedUri, "rw");
                    this.mCurrentVideoUri = extraSavedUri;
                    String str = TAG;
                    Log.d(str, "parseIntent: outputUri=" + extraSavedUri);
                } catch (FileNotFoundException e2) {
                    Log.e(TAG, e2.getMessage());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            if (isIgnoreTouchEvent()) {
                Log.w(TAG, "ignore volume key");
                return;
            }
            ModeProtocol.LiveVVChooser liveVVChooser = (ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229);
            if (liveVVChooser == null || !liveVVChooser.isShow()) {
                restoreBottom();
                onShutterButtonClick(i);
                return;
            }
            liveVVChooser.startShot();
        }
    }

    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    public void playVideo() {
        startPlayVideoActivity();
    }

    /* access modifiers changed from: protected */
    public void readVideoPreferences() {
    }

    /* access modifiers changed from: protected */
    public void resetScreenOn() {
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(52);
    }

    /* access modifiers changed from: protected */
    public void resizeForPreviewAspectRatio() {
    }

    /* access modifiers changed from: protected */
    public void restoreMusicSound() {
        if (this.mOriginalMusicVolume != 0 && this.mAudioManager.getStreamVolume(3) == 0) {
            this.mAudioManager.setStreamMute(3, false);
        }
        this.mOriginalMusicVolume = 0;
        this.mHandler.removeMessages(43);
    }

    /* access modifiers changed from: protected */
    public void sendOpenFailMessage() {
        this.mHandler.sendEmptyMessage(10);
    }

    /* access modifiers changed from: protected */
    public void setCaptureIntent(boolean z) {
        this.mIsVideoCaptureIntent = z;
    }

    public void setFrameAvailable(boolean z) {
        super.setFrameAvailable(z);
        if (z && CameraSettings.isCameraSoundOpen()) {
            this.mActivity.loadCameraSound(1);
            this.mActivity.loadCameraSound(0);
            this.mActivity.loadCameraSound(2);
            this.mActivity.loadCameraSound(3);
        }
    }

    /* access modifiers changed from: protected */
    public void setSessionReady(boolean z) {
        this.mIsSessionReady = z;
    }

    /* access modifiers changed from: protected */
    public void setVideoFocusMode(String str, boolean z) {
        this.mVideoFocusMode = str;
        if (z) {
            updateVideoFocusMode();
        }
    }

    public boolean shouldCaptureDirectly() {
        return false;
    }

    public boolean shouldReleaseLater() {
        return this.mInStartingFocusRecording || isRecording() || isSelectingCapturedResult();
    }

    /* access modifiers changed from: protected */
    public void showAlert() {
        pausePreview();
        this.mMainProtocol.showReviewViews(getReviewBitmap());
        enableCameraControls(false);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    /* access modifiers changed from: protected */
    public void showConfirmMessage(int i, int i2) {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            if (this.mTitleId != i && this.mMessageId != i2) {
                this.mDialog.dismiss();
            } else {
                return;
            }
        }
        this.mTitleId = i;
        this.mMessageId = i2;
        Camera camera = this.mActivity;
        this.mDialog = RotateDialogController.showSystemAlertDialog(camera, camera.getString(i), this.mActivity.getString(i2), this.mActivity.getString(17039370), (Runnable) null, (String) null, (Runnable) null);
    }

    /* access modifiers changed from: protected */
    public void silenceSounds() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mActivity.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        }
        this.mAudioManager.requestAudioFocus((AudioManager.OnAudioFocusChangeListener) null, 3, 2);
        this.mOriginalMusicVolume = this.mAudioManager.getStreamVolume(3);
        if (this.mOriginalMusicVolume != 0) {
            this.mAudioManager.setStreamMute(3, true);
            this.mHandler.sendEmptyMessageDelayed(43, 3000);
        }
    }

    public void startFaceDetection() {
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && isAlive() && this.mMaxFaceCount > 0) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                this.mFaceDetectionStarted = true;
                camera2Proxy.startFaceDetection();
            }
        }
    }

    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            Log.v(TAG, "startFocus");
            if (this.mFocusOrAELockSupported) {
                setVideoFocusMode("auto", true);
                this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
                return;
            }
            this.mCamera2Device.resumePreview();
        }
    }

    /* access modifiers changed from: protected */
    public void startVideoRecording() {
    }

    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            this.mFaceDetectionStarted = false;
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.stopFaceDetection();
            }
        }
    }

    public void stopObjectTracking(boolean z) {
    }

    public void stopVideoRecording(boolean z, boolean z2) {
    }

    /* access modifiers changed from: protected */
    public boolean supportTouchFocus() {
        return !isFrontCamera();
    }

    /* access modifiers changed from: protected */
    public void switchMutexHDR() {
        if ("off".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex))) {
            resetMutexModeManually();
        } else {
            this.mMutexModePicker.setMutexMode(2);
        }
    }

    /* access modifiers changed from: protected */
    public void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported) {
            this.mCamera2Device.setAELock(false);
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
        }
    }

    /* access modifiers changed from: protected */
    public void updateBeauty() {
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if (cameraCapabilities != null && cameraCapabilities.isSupportVideoBeauty()) {
            int i = this.mModuleIndex;
            if (i == 162 || i == 161) {
                if (this.mBeautyValues == null) {
                    this.mBeautyValues = new BeautyValues();
                }
                CameraSettings.initBeautyValues(this.mBeautyValues, this.mModuleIndex);
                String str = TAG;
                Log.d(str, "updateBeauty(): " + this.mBeautyValues);
                this.mCamera2Device.setBeautyValues(this.mBeautyValues);
                this.mIsBeautyFrontOn = isFaceBeautyOn(this.mBeautyValues);
                return;
            }
        }
        this.mBeautyValues = null;
    }

    /* access modifiers changed from: protected */
    public void updateDeviceOrientation() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    /* access modifiers changed from: protected */
    public void updateFace() {
        boolean enableFaceDetection = enableFaceDetection();
        ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.setSkipDrawFace(!enableFaceDetection);
        }
        if (enableFaceDetection) {
            if (!this.mFaceDetectionEnabled) {
                this.mFaceDetectionEnabled = true;
                startFaceDetection();
            }
        } else if (this.mFaceDetectionEnabled) {
            stopFaceDetection(true);
            this.mFaceDetectionEnabled = false;
        }
    }

    public void updateFlashPreference() {
        if (!this.mMutexModePicker.isNormal() && !this.mMutexModePicker.isSupportedFlashOn() && !this.mMutexModePicker.isSupportedTorch()) {
            resetMutexModeManually();
        }
        setFlashMode(DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex));
    }

    /* access modifiers changed from: protected */
    public void updateFocusArea() {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            if (this.mCamera2Device == null) {
                Log.e(TAG, "updateFocusArea: null camera device");
                return;
            }
            Rect cropRegion = getCropRegion();
            Rect activeArraySize = getActiveArraySize();
            this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            this.mCamera2Device.setAERegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
            if (this.mFocusAreaSupported) {
                this.mCamera2Device.setAFRegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
            } else {
                this.mCamera2Device.resumePreview();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateFocusCallback() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy == null) {
            Log.e(TAG, "updateFocusCallback: null camera device");
        } else if (this.mContinuousFocusSupported) {
            if (AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(this.mVideoFocusMode)) {
                this.mCamera2Device.setFocusCallback(this);
            }
        } else if (this.mAELockOnlySupported) {
            camera2Proxy.setFocusCallback(this);
        }
    }

    /* access modifiers changed from: protected */
    public void updateMotionFocusManager() {
        this.mActivity.getSensorStateManager().setFocusSensorEnabled("auto".equals(this.mVideoFocusMode));
    }

    /* access modifiers changed from: protected */
    public void updateRecordingTime() {
        if (isThermalThreshold() && !"0".equals(CameraSettings.getFlashMode(this.mModuleIndex))) {
            ThermalDetector.getInstance().onThermalNotification();
        }
    }

    /* access modifiers changed from: protected */
    public void updateStereoSettings(boolean z) {
        if (!CameraSettings.isStereoModeOn()) {
            return;
        }
        if (z) {
            this.mSettingsOverrider.overrideSettings(CameraSettings.KEY_VIDEOCAMERA_FLASH_MODE, String.valueOf(0));
            return;
        }
        this.mSettingsOverrider.restoreSettings();
    }

    /* access modifiers changed from: protected */
    public void updateVideoFocusMode() {
        if (this.mCamera2Device == null) {
            Log.e(TAG, "updateVideoFocusMode: null camera device");
            return;
        }
        int[] supportedFocusModes = this.mCameraCapabilities.getSupportedFocusModes();
        int convertToFocusMode = AutoFocus.convertToFocusMode(this.mVideoFocusMode);
        if (Util.isSupported(convertToFocusMode, supportedFocusModes)) {
            this.mCamera2Device.setFocusMode(convertToFocusMode);
            updateMotionFocusManager();
            updateFocusCallback();
        }
    }

    /* access modifiers changed from: protected */
    public void waitStereoSwitchThread() {
        try {
            if (this.mStereoSwitchThread != null) {
                this.mStereoSwitchThread.cancel();
                this.mStereoSwitchThread.join();
                this.mStereoSwitchThread = null;
            }
        } catch (InterruptedException e2) {
            Log.e(TAG, e2.getMessage(), e2);
        }
    }
}
