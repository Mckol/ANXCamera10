package com.android.camera.module;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Range;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.ILive;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.FunctionParseAsdScene;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
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
import com.android.camera2.CameraHardwareFace;
import com.android.gallery3d.ui.GLCanvas;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.rx.CameraSchedulers;
import com.xiaomi.recordmediaprocess.SystemUtil;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MiLiveModule extends BaseModule implements ILiveModule, Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FocusCallback, Camera2Proxy.FaceDetectionCallback, ModeProtocol.CameraAction, ModeProtocol.KaleidoscopeProtocol, FocusManager2.Listener, Camera2Proxy.PictureCallback {
    private static final long CAPTURE_THRESHOLD = 500;
    public static int LIB_LOAD_CALLER_PLAYER = 2;
    public static int LIB_LOAD_CALLER_RECORDER = 1;
    private static final int MSG_WAIT_SHUTTER_SOUND_FINISH = 256;
    private static final long START_RECORDING_OFFSET = 300;
    private static int sLibLoaded;
    private final String TAG = (MiLiveModule.class.getSimpleName() + "@" + hashCode());
    private boolean m3ALocked;
    private String mBaseFileName;
    protected BeautyValues mBeautyValues;
    private V6CameraGLSurfaceView mCameraView;
    private long mCaptureTime = 0;
    private CtaNoticeFragment mCtaNoticeFragment;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    private FocusManager2 mFocusManager;
    private boolean mIsPreviewing = false;
    private ModeProtocol.MiLiveConfigChanges mLiveConfigChanges;
    private Disposable mMetaDataDisposable;
    private FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private int mOldOriginVolumeStream;
    private long mOnResumeTime;
    private int mQuality = 6;
    private ModeProtocol.MiLiveRecorderControl.IRecorderListener mRecorderListener = new ModeProtocol.MiLiveRecorderControl.IRecorderListener() {
        /* class com.android.camera.module.MiLiveModule.AnonymousClass1 */

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderCancel() {
            Log.d(MiLiveModule.this.TAG, "onRecorderCancel");
            MiLiveModule.this.resetToIdle();
        }

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderError() {
            Log.d(MiLiveModule.this.TAG, "onRecorderError");
            MiLiveModule.this.resetToIdle();
        }

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderFinish(List<ILive.ILiveSegmentData> list, String str) {
            boolean z = list != null && list.size() > 0 && MiLiveModule.this.mLiveConfigChanges.getTotalRecordingTime() >= 500;
            if (!z) {
                Log.d(MiLiveModule.this.TAG, "onFinish of no segments !!");
                MiLiveModule.this.resetToIdle();
            } else {
                MiLiveModule miLiveModule = MiLiveModule.this;
                miLiveModule.mTelephonyManager.listen(miLiveModule.mPhoneStateListener, 0);
                Log.v(MiLiveModule.this.TAG, "listen none");
                MiLiveModule.this.trackLiveVideoParams();
                MiLiveModule.this.initReview(list, str);
            }
            if (!z) {
                MiLiveModule.this.resetZoomPreview();
            }
        }

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderPaused(@NonNull List<ILive.ILiveSegmentData> list) {
        }
    };
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.MiLiveModule.AnonymousClass2 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return MiLiveModule.this.isAlive() && MiLiveModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
            MiLiveModule.this.mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
            Log.v(MiLiveModule.this.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!MiLiveModule.this.mMainProtocol.isEvAdjusted(true) && !MiLiveModule.this.mPaused && Util.isTimeout(System.currentTimeMillis(), MiLiveModule.this.mTouchFocusStartingTime, 3000) && !MiLiveModule.this.is3ALocked() && MiLiveModule.this.mFocusManager != null && MiLiveModule.this.mFocusManager.isNeedCancelAutoFocus() && !MiLiveModule.this.isRecording()) {
                MiLiveModule.this.mFocusManager.onDeviceKeepMoving(d2);
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepStable() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceLieChanged(boolean z) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceOrientationChanged(float f, boolean z) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceRotationChanged(float[] fArr) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onSensorChanged(SensorEvent sensorEvent) {
        }
    };
    protected boolean mShowFace = false;
    protected TelephonyManager mTelephonyManager;
    private long mTouchFocusStartingTime;
    private CameraSize mVideoSize;

    /* access modifiers changed from: private */
    public class LiveAsdConsumer implements Consumer<Integer> {
        private LiveAsdConsumer() {
        }

        public void accept(Integer num) throws Exception {
        }
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                MiLiveModule.this.getWindow().clearFlags(128);
            } else if (i == 4) {
                MiLiveModule.this.checkActivityOrientation();
                if (SystemClock.uptimeMillis() - MiLiveModule.this.mOnResumeTime < 5000) {
                    MiLiveModule.this.mHandler.sendEmptyMessageDelayed(4, 100);
                }
            } else if (i == 9) {
                MiLiveModule miLiveModule = MiLiveModule.this;
                miLiveModule.mMainProtocol.initializeFocusView(miLiveModule);
            } else if (i == 17) {
                MiLiveModule.this.mHandler.removeMessages(17);
                MiLiveModule.this.mHandler.removeMessages(2);
                MiLiveModule.this.getWindow().addFlags(128);
                MiLiveModule miLiveModule2 = MiLiveModule.this;
                miLiveModule2.mHandler.sendEmptyMessageDelayed(2, (long) miLiveModule2.getScreenDelay());
            } else if (i == 31) {
                MiLiveModule.this.setOrientationParameter();
            } else if (i == 35) {
                MiLiveModule miLiveModule3 = MiLiveModule.this;
                boolean z = false;
                boolean z2 = message.arg1 > 0;
                if (message.arg2 > 0) {
                    z = true;
                }
                miLiveModule3.handleUpdateFaceView(z2, z);
            } else if (i != 51) {
                if (i == 256) {
                    MiLiveModule.this.startVideoRecording();
                }
            } else if (!MiLiveModule.this.mActivity.isActivityPaused()) {
                MiLiveModule miLiveModule4 = MiLiveModule.this;
                miLiveModule4.mOpenCameraFail = true;
                miLiveModule4.onCameraException();
            }
        }
    }

    static /* synthetic */ void Bd() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setCenterHint(8, null, null, 0);
        }
    }

    private boolean configReview(boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        boolean z2 = false;
        if (configChanges == null || baseDelegate == null) {
            Log.w(this.TAG, "configChanges is null");
            return false;
        }
        if (baseDelegate.getActiveFragment(R.id.full_screen_feature) == 1048561) {
            z2 = true;
        }
        if (z ^ z2) {
            Log.d(this.TAG, "config live review~");
            configChanges.configLiveReview();
        } else {
            Log.d(this.TAG, "skip config live review~");
        }
        return true;
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private boolean doLaterReleaseIfNeed() {
        Camera camera = this.mActivity;
        if (camera == null || !camera.isActivityPaused()) {
            return false;
        }
        this.mActivity.pause();
        this.mActivity.releaseAll(true, true);
        return true;
    }

    private ContentValues genContentValues(int i, int i2, boolean z) {
        String str;
        String createName = createName(System.currentTimeMillis(), i2);
        if (i2 > 0) {
            createName = createName + String.format(Locale.ENGLISH, "_%d", Integer.valueOf(i2));
        }
        String str2 = createName + Util.convertOutputFormatToFileExt(i);
        String convertOutputFormatToMimeType = Util.convertOutputFormatToMimeType(i);
        if (z) {
            str = Storage.CAMERA_TEMP_DIRECTORY + '/' + str2;
            Util.createFile(new File(Storage.CAMERA_TEMP_DIRECTORY + File.separator + Storage.AVOID_SCAN_FILE_NAME));
        } else {
            str = Storage.DIRECTORY + '/' + str2;
        }
        Log.v(this.TAG, "genContentValues: path=" + str);
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", createName);
        contentValues.put("_display_name", str2);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str);
        contentValues.put("resolution", this.mVideoSize.width + "x" + this.mVideoSize.height);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        return contentValues;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            this.mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted && 1 != this.mCamera2Device.getFocusMode()) {
            this.mMainProtocol.updateFaceView(z, true, isFrontCamera, true, this.mCameraDisplayOrientation);
        }
    }

    private void initLiveConfig() {
        this.mLiveConfigChanges = (ModeProtocol.MiLiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(241);
        if (this.mLiveConfigChanges == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 241);
            this.mLiveConfigChanges = (ModeProtocol.MiLiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(241);
            this.mLiveConfigChanges.prepare();
        }
        this.mLiveConfigChanges.initResource();
        this.mLiveConfigChanges.setRecorderListener(this.mRecorderListener);
        this.mLiveConfigChanges.onOrientationChanged(this.mOrientation, this.mOrientationCompensation, 0);
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new ga(this), BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdScene(this)).subscribe(new LiveAsdConsumer());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initReview(List<ILive.ILiveSegmentData> list, String str) {
        ContentValues genContentValues = genContentValues(2, 0, false);
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        if (miLivePlayerControl != null) {
            miLivePlayerControl.prepare(genContentValues, list, str);
        } else {
            Log.d(this.TAG, "show review fail~");
            resetToIdle();
        }
        this.mIsPreviewing = true;
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

    private boolean isEisOn() {
        return (isBackCamera() || CameraSettings.isCurrentQualitySupportEis(this.mQuality, 30, this.mCameraCapabilities)) && CameraSettings.isMovieSolidOn() && this.mCameraCapabilities.isEISPreviewSupported();
    }

    private boolean isSaving() {
        return false;
    }

    public static void loadLibs(Context context, int i) {
        if (sLibLoaded == 0) {
            System.loadLibrary("vvc++_shared");
            System.loadLibrary("ffmpeg");
            System.loadLibrary("record_video");
            SystemUtil.Init(context, 50011);
        }
        sLibLoaded |= i;
        Log.d("MiLiveModule", "loadLibs sLibLoaded : " + sLibLoaded);
    }

    private void pauseVideoRecording(boolean z) {
        if (isAlive() && this.mLiveConfigChanges != null) {
            String str = this.TAG;
            Log.d(str, "pauseVideoRecording formRelease " + z);
            if (this.mLiveConfigChanges.canRecordingStop() || z) {
                CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_PAUSE);
                ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                if (recordState != null) {
                    recordState.onPause();
                } else {
                    Log.d(this.TAG, "recordState pause fail~");
                }
                this.mLiveConfigChanges.pauseRecording();
                resetZoomPreview();
                return;
            }
            Log.d(this.TAG, "too fast to pause recording.");
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.MI_LIVE_TYPES_ON_PREVIEW_SUCCESS);
        updatePreferenceTrampoline(71);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetToIdle() {
        Log.d(this.TAG, "resetToIdle");
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.reset();
        }
        configReview(false);
        DataRepository.dataItemLive().setMiLiveSegmentData(null);
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onFinish();
        }
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        this.mIsPreviewing = false;
        resetZoomPreview();
        if (doLaterReleaseIfNeed()) {
            Log.d(this.TAG, "onReviewDoneClicked -- ");
        } else if (this.mCamera2Device.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetZoomPreview() {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(false);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                setMaxZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR * this.mCameraCapabilities.getMaxZoomRatio());
                return;
            }
            setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
            setVideoMaxZoomRatioByTele();
        }
    }

    private void resetZoomRecoding() {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                setMaxZoomRatio(2.0f);
            } else if (isAuxCamera()) {
                setMinZoomRatio(HybridZoomingSystem.getTeleMinZoomRatio());
                setVideoMaxZoomRatioByTele();
            } else if (isUltraTeleCamera()) {
                setMinZoomRatio(HybridZoomingSystem.getUltraTeleMinZoomRatio());
                setVideoMaxZoomRatioByTele();
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            }
        }
    }

    private void resumeVideoRecording() {
        if (isAlive() && this.mLiveConfigChanges != null) {
            resetZoomRecoding();
            this.mLiveConfigChanges.resumeRecording();
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onResume();
            } else {
                Log.d(this.TAG, "recordState resume fail~");
            }
        }
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
                CameraSchedulers.sCameraSetupScheduler.scheduleDirect(new fa(this));
                return;
            }
            updatePreferenceInWorkThread(35);
        }
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mModuleIndex) && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void showReview() {
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        if (miLivePlayerControl != null) {
            miLivePlayerControl.show();
            pausePreview();
            return;
        }
        Log.d(this.TAG, "show review fail~");
        resetToIdle();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    @MainThread
    private void startVideoRecording() {
        keepScreenOn();
        if (this.mLiveConfigChanges != null) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            this.mLiveConfigChanges.startRecording();
            CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_START);
            this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
            getActivity().setVolumeControlStream(3);
            recordState.onStart();
            configReview(true);
            resetZoomRecoding();
            this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
            Log.v(this.TAG, "listen call state");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void trackLiveVideoParams() {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        CameraStatUtils.trackMiLiveRecordingParams(miLiveConfigChanges != null ? miLiveConfigChanges.getSegmentSize() : 0, CameraSettings.getCurrentLiveMusic()[1], CameraSettings.getShaderEffect(), Integer.valueOf(CameraSettings.getCurrentLiveSpeed()).intValue(), isFrontCamera(), this.mBeautyValues, this.mQuality, EffectController.getInstance().getCurrentKaleidoscope());
    }

    public static void unloadLibs(int i) {
        sLibLoaded = (~i) & sLibLoaded;
        Log.d("MiLiveModule", "unloadLibs sLibLoaded : " + sLibLoaded);
        if (sLibLoaded == 0) {
            SystemUtil.UnInit();
        }
    }

    private void updateBeauty() {
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        CameraSettings.initBeautyValues(this.mBeautyValues, this.mModuleIndex);
        String str = this.TAG;
        Log.d(str, "updateBeauty(): " + this.mBeautyValues);
        this.mCamera2Device.setBeautyValues(this.mBeautyValues);
    }

    private void updateDeviceOrientation() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    private void updateFaceView(boolean z, boolean z2) {
        if (this.mHandler.hasMessages(35)) {
            this.mHandler.removeMessages(35);
        }
        this.mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = this.TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFocusMode() {
        setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
    }

    private void updateFpsRange() {
        this.mCamera2Device.setFpsRange(new Range<>(30, 30));
    }

    private void updateKaleidoscope() {
        EffectController.getInstance().setKaleidoscope(DataRepository.dataItemRunning().getComponentRunningKaleidoscope().getKaleidoscopeValue());
    }

    private void updateLiveRelated() {
        if (this.mAlgorithmPreviewSize != null) {
            this.mCamera2Device.startPreviewCallback(this.mLiveConfigChanges, null);
        }
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSizeWithAssignedMode = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        float previewRatio = this.mLiveConfigChanges.getPreviewRatio();
        this.mQuality = CameraSettings.getPreferLiveVideoQuality(this.mActualCameraId, this.mModuleIndex);
        this.mVideoSize = null;
        if (this.mQuality != 6) {
            this.mVideoSize = new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH);
        } else {
            this.mVideoSize = new CameraSize(1920, 1080);
        }
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) previewRatio, this.mVideoSize);
        String str = this.TAG;
        Log.d(str, "previewSize: " + this.mPreviewSize.toString());
        CameraSize cameraSize = this.mPreviewSize;
        this.mPictureSize = cameraSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoBokeh() {
        float videoBokehRatio = CameraSettings.getVideoBokehRatio();
        if (isFrontCamera()) {
            String str = this.TAG;
            Log.i(str, "frontVideoBokeh: " + videoBokehRatio);
            this.mCamera2Device.setVideoBokehLevelFront(videoBokehRatio);
            return;
        }
        int i = (int) videoBokehRatio;
        String str2 = this.TAG;
        Log.i(str2, "backVideoBokeh: " + i);
        this.mCamera2Device.setVideoBokehLevelBack(i);
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (isEisOn()) {
                Log.d(this.TAG, "videoStabilization: EIS");
                this.mCamera2Device.setEnableEIS(true);
                this.mCamera2Device.setEnableOIS(false);
                if (!this.mCameraCapabilities.isEISPreviewSupported()) {
                    this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                    return;
                }
                return;
            }
            Log.d(this.TAG, "videoStabilization: OIS");
            this.mCamera2Device.setEnableEIS(false);
            this.mCamera2Device.setEnableOIS(true);
            this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }

    public /* synthetic */ void Ed() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    public /* synthetic */ void a(FlowableEmitter flowableEmitter) throws Exception {
        this.mMetaDataFlowableEmitter = flowableEmitter;
    }

    public /* synthetic */ void a(String str, Uri uri) {
        ContentValues saveContentValues;
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        if (miLivePlayerControl != null && (saveContentValues = miLivePlayerControl.getSaveContentValues()) != null) {
            String asString = saveContentValues.getAsString("title");
            String asString2 = saveContentValues.getAsString("_data");
            String str2 = this.TAG;
            Log.d(str2, "newUri: " + str + " | " + asString);
            if (asString.equals(str)) {
                miLivePlayerControl.onLiveSaveToLocalFinished(uri, asString2);
            }
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

    /* access modifiers changed from: protected */
    public boolean checkCallingState() {
        if (Storage.isLowStorageAtLastPoint()) {
            this.mActivity.getScreenHint().updateHint();
            return false;
        } else if (2 != this.mTelephonyManager.getCallState()) {
            return true;
        } else {
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_calling_alert);
            return false;
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
            camera2Proxy.setFocusCallback(null);
            this.mCamera2Device.setErrorCallback(null);
            this.mCamera2Device.setMetaDataCallback(null);
            this.mCamera2Device.stopPreviewCallback(true);
            this.mCamera2Device = null;
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            switch (i) {
                case 1:
                    updatePictureAndPreviewSize();
                    break;
                case 2:
                    updateFilter();
                    break;
                case 3:
                    updateFocusArea();
                    break;
                case 5:
                    updateFace();
                    break;
                case 9:
                    updateAntiBanding(CameraSettings.getAntiBanding());
                    break;
                case 10:
                    updateFlashPreference();
                    break;
                case 11:
                case 20:
                case 30:
                case 34:
                case 42:
                case 43:
                case 46:
                case 48:
                case 50:
                case 79:
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    break;
                case 14:
                    updateFocusMode();
                    break;
                case 19:
                    updateFpsRange();
                    break;
                case 24:
                    applyZoomRatio();
                    break;
                case 25:
                    focusCenter();
                    break;
                case 29:
                    updateExposureMeteringMode();
                    break;
                case 31:
                    updateVideoStabilization();
                    break;
                case 35:
                    updateDeviceOrientation();
                    break;
                case 47:
                    updateUltraWideLDC();
                    break;
                case 54:
                    updateLiveRelated();
                    break;
                case 55:
                    updateModuleRelated();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                case 66:
                    updateThermalLevel();
                    break;
                case 67:
                    updateVideoBokeh();
                    break;
                case 71:
                    updateKaleidoscope();
                    break;
                default:
                    throw new RuntimeException("no consumer for this updateType: " + i);
            }
        }
    }

    @Override // com.android.camera.module.ILiveModule
    public void doReverse() {
        if (this.mLiveConfigChanges != null && !isRecording()) {
            this.mLiveConfigChanges.deleteLastFragment();
            if (this.mLiveConfigChanges.getSegmentSize() == 0) {
                ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                if (backStack != null) {
                    backStack.handleBackStackFromKeyBack();
                }
                ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                if (recordState != null) {
                    recordState.onFinish();
                }
                stopVideoRecording(false, false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean enableFaceDetection() {
        return DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        int i = isEisOn() ? 32772 : (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) ? this.mCameraCapabilities.isSupportVideoBeauty() ? CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_BEAUTY : DataRepository.dataItemFeature().Ii() ? CameraCapabilities.SESSION_OPERATION_MODE_MCTF : 0 : 32770;
        String str = this.TAG;
        Log.d(str, "getOperatingMode: " + Integer.toHexString(i));
        return i;
    }

    @Override // com.android.camera.module.BaseModule
    public void initializeCapabilities() {
        super.initializeCapabilities();
        this.mContinuousFocusSupported = Util.isSupported(4, this.mCameraCapabilities.getSupportedFocusModes());
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
    }

    /* access modifiers changed from: protected */
    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    /* access modifiers changed from: protected */
    public boolean isAEAFLockSupported() {
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        int i;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = this.mModuleIndex) == 183 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera() && !isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        return isRecording() || (miLivePlayerControl != null && miLivePlayerControl.isShowing());
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isNeedMute() {
        return isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(256)) {
            return true;
        }
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        return miLiveConfigChanges != null && miLiveConfigChanges.getCurState() == 2;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecordingPaused() {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        return miLiveConfigChanges != null && miLiveConfigChanges.getCurState() == 3;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        return miLivePlayerControl != null && miLivePlayerControl.isShowing();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowAeAfLockIndicator() {
        return this.m3ALocked;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return isSupportFocusShoot();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isUseFaceInfo() {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        return getCameraState() != 3 && !isFrontCamera() && !DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate() && isFrameAvailable();
    }

    /* access modifiers changed from: protected */
    public void lockAEAF() {
        Log.d(this.TAG, "lockAEAF");
        if (this.mAeLockSupported) {
            this.mCamera2Device.setAELock(true);
        }
        this.mFocusManager.setAeAwbLock(true);
        this.m3ALocked = true;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean multiCapture() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void notifyError() {
        if (CameraSchedulers.isOnMainThread() && isRecording()) {
            stopVideoRecording(false, false);
        }
        super.notifyError();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return isSelectingCapturedResult();
        }
        if (this.mLiveConfigChanges == null) {
            return false;
        }
        if (!isRecording() && !isRecordingPaused()) {
            return super.onBackPressed();
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastBackPressedTime > 3000) {
            this.mLastBackPressedTime = currentTimeMillis;
            ToastUtils.showToast((Context) this.mActivity, (int) R.string.record_back_pressed_hint, true);
        } else {
            stopVideoRecording(true, true);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initLiveConfig();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        startPreview();
        DataRepository.dataItemGlobal().isFirstShowCTAConCollect();
        this.mOnResumeTime = SystemClock.uptimeMillis();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
        initMetaParser();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureCompleted(boolean z) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureProgress(boolean z, boolean z2, boolean z3, boolean z4) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z, boolean z2, boolean z3, boolean z4) {
        this.mActivity.getCameraScreenNail().requestFullReadPixels();
        CameraStatUtils.trackKaleidoscopeClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_KALEIDOSCOPE_CAPTURE);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z, boolean z2, boolean z3, boolean z4) {
        return null;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mHandler = new MainHandler(this.mActivity.getMainLooper());
        this.mTelephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        onCameraOpened();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
        this.mActivity.getSensorStateManager().setRotationVectorEnabled(true);
        this.mCameraView = this.mActivity.getGLView();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.TAG, "onDestroy");
        CtaNoticeFragment ctaNoticeFragment = this.mCtaNoticeFragment;
        if (ctaNoticeFragment != null) {
            ctaNoticeFragment.dismiss();
        }
        this.mHandler.post(ea.INSTANCE);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
        this.mActivity.getSensorStateManager().setRotationVectorEnabled(false);
        this.mActivity.getSensorStateManager().setTTARSensorEnabled(false);
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo, Rect rect) {
        if (!isCreated() || cameraHardwareFaceArr == null) {
            return;
        }
        if (!c.Qn() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
            if (!this.mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), rect)) {
            }
        } else if (this.mObjectTrackingStarted) {
            this.mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), rect);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FocusCallback
    public void onFocusStateChanged(FocusTask focusTask) {
        if (isCreated() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger != 1) {
                if (focusTrigger != 2) {
                    if (focusTrigger != 3) {
                        return;
                    }
                } else if (focusTask.isIsDepthFocus()) {
                    return;
                }
                String str = null;
                if (focusTask.isFocusing()) {
                    str = "onAutoFocusMoving start";
                }
                if (Util.sIsDumpLog && str != null) {
                    Log.v(this.TAG, str);
                }
                if ((getCameraState() != 3 || focusTask.getFocusTrigger() == 3) && !this.m3ALocked) {
                    this.mFocusManager.onFocusResult(focusTask);
                    return;
                }
                return;
            }
            Log.v(this.TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
            if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                setCameraState(1);
            }
            this.mFocusManager.onFocusResult(focusTask);
            this.mActivity.getSensorStateManager().reset();
            if (focusTask.isSuccess() && this.m3ALocked) {
                this.mCamera2Device.lockExposure(true);
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onHostStopAndNotifyActionStop() {
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(256)) {
            this.mHandler.removeMessages(256);
            Log.d(this.TAG, "skip stopVideoRecording & remove startVideoRecording");
        }
        if (isRecording()) {
            pauseVideoRecording(true);
        } else if (isSaving()) {
            return;
        }
        doLaterReleaseIfNeed();
    }

    @Override // com.android.camera.protocol.ModeProtocol.KaleidoscopeProtocol
    public void onKaleidoscopeChanged(String str) {
        updatePreferenceTrampoline(71);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
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
                    if (this.mIsPreviewing) {
                        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
                        if (miLivePlayerControl != null) {
                            miLivePlayerControl.startLiveRecordSaving();
                        }
                    } else if (!Util.isFingerPrintKeyEvent(keyEvent)) {
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
                        stopVideoRecording(true, true);
                    }
                } else if (isRecording() && !isPostProcessing()) {
                    if (!isBackCamera()) {
                        return false;
                    }
                    stopVideoRecording(true, true);
                }
                return super.onKeyDown(i, keyEvent);
            }
        }
        if (!this.mIsPreviewing) {
            if (i == 24 || i == 88) {
                z = true;
            }
            if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
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

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onLongPress(float f, float f2) {
        int i = (int) f;
        int i2 = (int) f2;
        if (isInTapableRect(i, i2)) {
            onSingleTapUp(i, i2, true);
            if (isAEAFLockSupported() && CameraSettings.isAEAFLockSupport()) {
                lockAEAF();
            }
            this.mMainProtocol.performHapticFeedback(0);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onNewUriArrived(Uri uri, String str) {
        Handler handler;
        super.onNewUriArrived(uri, str);
        if (isAlive() && (handler = this.mHandler) != null) {
            handler.post(new ha(this, str, uri));
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.onOrientationChanged(i, i2, i3);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
        Log.d(this.TAG, "onPause");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        this.mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera.module.ILiveModule
    public void onPauseButtonClick() {
        if (isRecording()) {
            pauseVideoRecording(false);
        } else {
            resumeVideoRecording();
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTakenFinished(boolean z) {
        this.mActivity.getCameraScreenNail().animateCaptureWithDraw(this.mOrientation);
        this.mActivity.getCameraScreenNail().setPreviewSaveListener(null);
        setCameraState(1);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public boolean onPictureTakenImageConsumed(Image image, TotalCaptureResult totalCaptureResult) {
        return false;
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
            FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
            if (flowableEmitter != null) {
                flowableEmitter.onNext(captureResult);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewRelease() {
        if (isRecording()) {
            stopVideoRecording(false, false);
        }
        super.onPreviewRelease();
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(this.mHandler)) {
            this.mHandler.sendEmptyMessage(51);
        } else {
            Log.d(this.TAG, "sessionFailed due to surfaceTexture expired, retry");
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
        Log.d(this.TAG, "onResume");
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
        Log.d(this.TAG, "onReviewCancelClicked");
        resetToIdle();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        Log.d(this.TAG, "onReviewDoneClicked");
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.stopRecording();
        }
        resetToIdle();
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 196) {
            updatePreferenceInWorkThread(2);
        } else if (i == 239) {
            updatePreferenceInWorkThread(13);
        } else {
            throw new RuntimeException("unknown configItem changed");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (getCameraState() == 0) {
            Log.d(this.TAG, "skip shutter caz preview paused.");
            return;
        }
        int i2 = 0;
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            i2 = miLiveConfigChanges.getCurState();
        }
        String str = this.TAG;
        Log.d(str, "onShutterButtonClick " + i2);
        if (i2 != 1) {
            if (i2 == 2 || i2 == 3) {
                stopVideoRecording(true, true);
            }
        } else if (!checkCallingState()) {
            Log.d(this.TAG, "ignore in calling state");
        } else {
            Handler handler = this.mHandler;
            if (handler == null || handler.hasMessages(256) || !CameraSettings.isCameraSoundOpen()) {
                startVideoRecording();
                return;
            }
            playCameraSound(2);
            this.mHandler.sendEmptyMessageDelayed(256, 300);
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
        if (!this.mPaused && (camera2Proxy = this.mCamera2Device) != null && camera2Proxy.isSessionReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 0) {
            if (!isFrameAvailable()) {
                Log.w(this.TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !this.mActivity.isScreenSlideOff()) && !((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromTapDown(i, i2)) {
                this.mMainProtocol.setFocusViewType(true);
                this.mTouchFocusStartingTime = System.currentTimeMillis();
                Point point = new Point(i, i2);
                mapTapCoordinate(point);
                unlockAEAF();
                this.mFocusManager.onSingleTapUp(point.x, point.y, z);
            }
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

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSurfaceTextureReleased() {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.onSurfaceTextureReleased();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSurfaceTextureUpdated(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
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

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionEnd(int i) {
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionStart(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        if (this.mCamera2Device.getFlashMode() == 2 || this.mCamera2Device.getFlashMode() == 5) {
            this.mCamera2Device.forceTurnFlashOffAndPausePreview();
        } else {
            this.mCamera2Device.pausePreview();
        }
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            if (isIgnoreTouchEvent()) {
                Log.w(this.TAG, "ignore volume key");
            } else {
                onShutterButtonClick(i);
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        Log.d(this.TAG, "registerProtocol");
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(236, this);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 212);
        } else {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 234, 212);
        }
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        previewWhenSessionSuccess();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void setFrameAvailable(boolean z) {
        super.setFrameAvailable(z);
        if (z && CameraSettings.isCameraSoundOpen()) {
            this.mActivity.loadCameraSound(2);
            this.mActivity.loadCameraSound(3);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return isRecording() || isSaving();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFaceDetection() {
        Camera2Proxy camera2Proxy;
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && isAlive() && this.mMaxFaceCount > 0 && (camera2Proxy = this.mCamera2Device) != null) {
            this.mFaceDetectionStarted = true;
            camera2Proxy.startFaceDetection();
            updateFaceView(true, true);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            Log.v(this.TAG, "startFocus");
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
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setMetaDataCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPictureSize);
            this.mCamera2Device.setPreviewSize(this.mPreviewSize);
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            Log.d(this.TAG, "LiveModule, startPreview");
            checkDisplayOrientation();
            ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
            CameraSize cameraSize = this.mVideoSize;
            miLiveConfigChanges.initPreview(cameraSize.width, cameraSize.height, this.mBogusCameraId, this.mActivity.getCameraScreenNail());
            SurfaceTexture inputSurfaceTexture = this.mLiveConfigChanges.getInputSurfaceTexture();
            String str = this.TAG;
            Log.d(str, "InputSurfaceTexture " + inputSurfaceTexture);
            this.mCamera2Device.startPreviewSession(inputSurfaceTexture == null ? new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()) : new Surface(inputSurfaceTexture), 0, false, false, null, getOperatingMode(), false, this);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            this.mFaceDetectionStarted = false;
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.stopFaceDetection();
            }
            this.mMainProtocol.setActiveIndicator(2);
            updateFaceView(false, z);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopObjectTracking(boolean z) {
    }

    public void stopVideoRecording(boolean z, boolean z2) {
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(256)) {
            this.mHandler.removeMessages(256);
            Log.d(this.TAG, "skip stopVideoRecording & remove startVideoRecording");
        } else if (isAlive() && this.mLiveConfigChanges != null) {
            String str = this.TAG;
            Log.d(str, "stopVideoRecording checkRecordingTime " + z + ", showReview = " + z2);
            keepScreenOnAwhile();
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mLiveConfigChanges.canRecordingStop() || !z) {
                if (z2) {
                    if (recordState != null) {
                        recordState.onPostPreview();
                        showReview();
                    } else {
                        Log.d(this.TAG, "record state post preview fail~");
                    }
                }
                this.mLiveConfigChanges.stopRecording();
                playCameraSound(3);
                return;
            }
            Log.d(this.TAG, "too fast to stop recording.");
        }
    }

    public void takePreviewSnapShoot() {
        if (getCameraState() != 3 && System.currentTimeMillis() - this.mCaptureTime >= 500) {
            setCameraState(3);
            this.mCamera2Device.setShotType(-8);
            this.mCamera2Device.takeSimplePicture(this, this.mActivity.getImageSaver(), this.mActivity.getCameraScreenNail());
            this.mCaptureTime = System.currentTimeMillis();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        Log.d(this.TAG, "unRegisterModulePersistProtocol");
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        Log.d(this.TAG, "unRegisterProtocol");
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(236, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    public void unlockAEAF() {
        Log.d(this.TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported) {
            this.mCamera2Device.setAELock(false);
        }
        this.mFocusManager.setAeAwbLock(false);
    }

    /* access modifiers changed from: protected */
    public void updateFace() {
        boolean enableFaceDetection = enableFaceDetection();
        ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.setSkipDrawFace(!enableFaceDetection || !this.mShowFace);
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

    @Override // com.android.camera.module.BaseModule
    public void updateFlashPreference() {
        setFlashMode(DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex));
    }

    /* access modifiers changed from: protected */
    public void updateFocusArea() {
        if (this.mCamera2Device == null) {
            Log.e(this.TAG, "updateFocusArea: null camera device");
            return;
        }
        Rect cropRegion = getCropRegion();
        Rect activeArraySize = getActiveArraySize();
        this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
        this.mCamera2Device.setAERegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
        if (this.mFocusAreaSupported) {
            this.mCamera2Device.setAFRegions(this.mFocusManager.getFocusAreas(cropRegion, activeArraySize));
        } else {
            this.mCamera2Device.resumePreview();
        }
    }
}
