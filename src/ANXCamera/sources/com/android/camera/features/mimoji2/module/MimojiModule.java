package com.android.camera.features.mimoji2.module;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Range;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import b.c.a.c;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.impl.component.FileUtils;
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
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraHardwareFace;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.rx.CameraSchedulers;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MimojiModule extends BaseModule implements Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FocusCallback, Camera2Proxy.FaceDetectionCallback, ModeProtocol.CameraAction, Camera2Proxy.PictureCallback, FocusManager2.Listener {
    private static final int BEAUTY_SWITCH;
    private static final int FILTER_SWITCH;
    public static final int IGNORE_GIF_TIME;
    private static final int STICKER_SWITCH;
    public static final int STOP_RECORD_FOROM_BACK;
    public static final int STOP_RECORD_FOROM_NORMAL;
    public static final int STOP_RECORD_FOROM_RELEASE;
    private static final String TAG;
    public static final int VALID_VIDEO_TIME;
    private boolean m3ALocked;
    private String mBaseFileName;
    protected BeautyValues mBeautyValues;
    private String mCaptureWaterMarkStr;
    private int mDeviceOrientation = 90;
    private boolean mDisableSingleTapUp = false;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    private FocusManager2 mFocusManager;
    private boolean mIsLowLight;
    private Disposable mMetaDataDisposable;
    private FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private MimojiModeProtocol.MimojiVideoEditor mMimojiVideoEditor;
    private int mOldOriginVolumeStream;
    private long mOnResumeTime;
    private int mQuality = 5;
    private long mRecordTime;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.features.mimoji2.module.MimojiModule.AnonymousClass2 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return MimojiModule.this.isAlive() && MimojiModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
            ((BaseModule) MimojiModule.this).mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
            Log.v(MimojiModule.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!((BaseModule) MimojiModule.this).mMainProtocol.isEvAdjusted(true) && !((BaseModule) MimojiModule.this).mPaused && Util.isTimeout(System.currentTimeMillis(), MimojiModule.this.mTouchFocusStartingTime, 3000) && !MimojiModule.this.is3ALocked() && MimojiModule.this.mFocusManager != null && MimojiModule.this.mFocusManager.isNeedCancelAutoFocus() && !MimojiModule.this.isRecording()) {
                MimojiModule.this.mFocusManager.onDeviceKeepMoving(d2);
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
            MimojiModule mimojiModule = MimojiModule.this;
            if (z) {
                f = (float) ((BaseModule) mimojiModule).mOrientation;
            }
            ((BaseModule) mimojiModule).mDeviceRotation = f;
            if (((BaseModule) MimojiModule.this).isGradienterOn) {
                EffectController.getInstance().setDeviceRotation(z, Util.getShootRotation(((BaseModule) MimojiModule.this).mActivity, ((BaseModule) MimojiModule.this).mDeviceRotation));
            }
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
    private MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2;
    private boolean misFaceLocationOk;

    public class LiveAsdConsumer implements Consumer<Integer> {
        private LiveAsdConsumer() {
            MimojiModule.this = r1;
        }

        public void accept(Integer num) {
            MimojiModule.this.mimojiLightDetect(num);
        }
    }

    private class MainHandler extends Handler {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public MainHandler(Looper looper) {
            super(looper);
            MimojiModule.this = r1;
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                MimojiModule.this.getWindow().clearFlags(128);
            } else if (i == 4) {
                MimojiModule.this.checkActivityOrientation();
                if (SystemClock.uptimeMillis() - MimojiModule.this.mOnResumeTime < 5000) {
                    ((BaseModule) MimojiModule.this).mHandler.sendEmptyMessageDelayed(4, 100);
                }
            } else if (i == 9) {
                ((BaseModule) MimojiModule.this).mMainProtocol.initializeFocusView(MimojiModule.this);
            } else if (i == 17) {
                ((BaseModule) MimojiModule.this).mHandler.removeMessages(17);
                ((BaseModule) MimojiModule.this).mHandler.removeMessages(2);
                MimojiModule.this.getWindow().addFlags(128);
                ((BaseModule) MimojiModule.this).mHandler.sendEmptyMessageDelayed(2, (long) MimojiModule.this.getScreenDelay());
            } else if (i == 31) {
                MimojiModule.this.setOrientationParameter();
            } else if (i == 35) {
                MimojiModule mimojiModule = MimojiModule.this;
                boolean z = false;
                boolean z2 = message.arg1 > 0;
                if (message.arg2 > 0) {
                    z = true;
                }
                mimojiModule.handleUpdateFaceView(z2, z);
            } else if (i == 51 && !((BaseModule) MimojiModule.this).mActivity.isActivityPaused()) {
                ((BaseModule) MimojiModule.this).mOpenCameraFail = true;
                MimojiModule.this.onCameraException();
            }
        }
    }

    static /* synthetic */ void ad() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setCenterHint(8, null, null, 0);
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.hideSwitchTip();
        }
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private void deleteMimojiCache() {
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine22 != null) {
            mimojiAvatarEngine22.deleteMimojiCache(0);
        } else {
            Log.e(TAG, "mimoji void deleteMimojiCache[] null");
        }
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

    private void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            this.mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted && 1 != this.mCamera2Device.getFocusMode()) {
            this.mMainProtocol.updateFaceView(z, true, isFrontCamera, true, this.mCameraDisplayOrientation);
        }
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.features.mimoji2.module.MimojiModule.AnonymousClass1 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) {
                MimojiModule.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdScene(this)).subscribe(new LiveAsdConsumer());
    }

    private void initMimojiEngine() {
        this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
        this.mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (this.mimojiAvatarEngine2 == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 246);
            this.mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            this.mimojiAvatarEngine2.onDeviceRotationChange(this.mDeviceOrientation);
        }
        this.mMimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (this.mMimojiVideoEditor == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 252);
            this.mMimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        }
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

    private boolean isPreviewEisOn() {
        return isBackCamera() && CameraSettings.isMovieSolidOn() && this.mCameraCapabilities.isEISPreviewSupported();
    }

    private boolean isSaving() {
        if (((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)) == null) {
        }
        return false;
    }

    private void mimojiLightDetect(Integer num) {
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine22 != null && mimojiAvatarEngine22.isOnCreateMimoji()) {
            if (!this.misFaceLocationOk) {
                ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
                if (mainContentProtocol != null) {
                    mainContentProtocol.updateMimojiFaceDetectResultTip(false);
                    return;
                }
                return;
            }
            ModeProtocol.MainContentProtocol mainContentProtocol2 = this.mMainProtocol;
            if (mainContentProtocol2 != null) {
                mainContentProtocol2.setMimojiDetectTipType(162);
            }
            int intValue = num.intValue();
            if (intValue == 6 || intValue == 9) {
                this.mIsLowLight = true;
                ModeProtocol.MainContentProtocol mainContentProtocol3 = this.mMainProtocol;
                if (mainContentProtocol3 != null) {
                    mainContentProtocol3.updateMimojiFaceDetectResultTip(true);
                    return;
                }
                return;
            }
            this.mIsLowLight = false;
            ModeProtocol.MainContentProtocol mainContentProtocol4 = this.mMainProtocol;
            if (mainContentProtocol4 != null) {
                mainContentProtocol4.updateMimojiFaceDetectResultTip(false);
            }
        }
    }

    private void onMimojiCapture() {
        this.mCamera2Device.setShotType(-1);
        this.mCamera2Device.takePicture(this, this.mActivity.getImageSaver());
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.FUN_TYPES_ON_PREVIEW_SUCCESS);
    }

    private void setOrientation(int i, int i2) {
        if (isAlive() && i != -1) {
            this.mOrientation = i;
            checkActivityOrientation();
            if (this.mOrientationCompensation != i2) {
                this.mOrientationCompensation = i2;
                setOrientationParameter();
            }
        }
    }

    private void setOrientationParameter() {
        if (!isDeparted() && this.mCamera2Device != null && this.mOrientation != -1) {
            if (!isFrameAvailable() || getCameraState() != 1) {
                CameraSchedulers.sCameraSetupScheduler.scheduleDirect(new d(this));
                return;
            }
            updatePreferenceInWorkThread(35);
        }
    }

    private void setWaterMark() {
        if (isDeviceAlive()) {
            if (CameraSettings.isDualCameraWaterMarkOpen()) {
                this.mCamera2Device.setDualCamWaterMarkEnable(true);
            } else {
                this.mCamera2Device.setDualCamWaterMarkEnable(false);
            }
            if (CameraSettings.isTimeWaterMarkOpen()) {
                this.mCamera2Device.setTimeWaterMarkEnable(true);
                this.mCaptureWaterMarkStr = Util.getTimeWatermark();
                this.mCamera2Device.setTimeWatermarkValue(this.mCaptureWaterMarkStr);
                return;
            }
            this.mCaptureWaterMarkStr = null;
            this.mCamera2Device.setTimeWaterMarkEnable(false);
        }
    }

    private boolean shouldApplyUltraWideLDC() {
        if (c.isMTKPlatform()) {
            return this.mModuleIndex == 174 && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId();
        }
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mModuleIndex) && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void showPreview() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (isAlive() && recordState != null) {
            keepScreenOnAwhile();
            recordState.onPostPreview();
            pausePreview();
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
            if (mimojiAvatarEngine22 != null) {
                mimojiAvatarEngine22.releaseRender();
                if (CameraSettings.isGifOn()) {
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(20);
                    }
                } else if (!FileUtils.checkFileConsist(this.mimojiAvatarEngine2.getVideoCache())) {
                    onReviewCancelClicked();
                } else {
                    ((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)).startMimojiRecordPreview();
                }
            } else {
                Log.e(TAG, " mimoji  showPreview contentValues null error");
            }
        }
    }

    private boolean startScreenLight(int i, int i2) {
        if (this.mPaused) {
            return false;
        }
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.setWindowBrightness(i2);
        }
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        if (fullScreenProtocol == null) {
            return true;
        }
        fullScreenProtocol.setScreenLightColor(i);
        return fullScreenProtocol.showScreenLight();
    }

    private void startVideoRecording() {
        ModeProtocol.MimojiGifEditor mimojiGifEditor = (ModeProtocol.MimojiGifEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(251);
        if (!this.mimojiAvatarEngine2.isRecordStopping() && mimojiGifEditor == null && System.currentTimeMillis() - this.mRecordTime >= 500) {
            keepScreenOn();
            this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
            getActivity().setVolumeControlStream(3);
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            recordState.onStart();
            this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
            Log.v(TAG, "listen call state");
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
            if (mimojiAvatarEngine22 != null) {
                mimojiAvatarEngine22.onRecordStart();
                this.mRecordTime = System.currentTimeMillis();
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips != null) {
                    bottomPopupTips.hideTipImage();
                }
            }
            MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = this.mMimojiVideoEditor;
            if (mimojiVideoEditor != null) {
                CameraSize cameraSize = this.mPreviewSize;
                mimojiVideoEditor.setRecordParameter(cameraSize.height, cameraSize.width, this.mOrientation);
            }
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
            trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, -1);
        }
    }

    private void stopScreenLight() {
        this.mHandler.post(new c(this));
    }

    private void trackLiveRecordingParams() {
        boolean z;
        boolean z2;
        boolean z3;
        int liveAllSwitchAllValue = CameraSettings.getLiveAllSwitchAllValue();
        String str = CameraSettings.getCurrentLiveMusic()[1];
        boolean z4 = !str.isEmpty();
        LiveBeautyFilterFragment.LiveFilterItem findLiveFilter = EffectController.getInstance().findLiveFilter(CameraAppImpl.getAndroidContext(), DataRepository.dataItemLive().getLiveFilter());
        if (!findLiveFilter.directoryName.isEmpty()) {
            if ((liveAllSwitchAllValue & 2) == 0) {
                liveAllSwitchAllValue += 2;
            }
            z = true;
        } else {
            z = false;
        }
        String currentLiveStickerName = CameraSettings.getCurrentLiveStickerName();
        if (!currentLiveStickerName.isEmpty()) {
            if ((liveAllSwitchAllValue & 4) == 0) {
                liveAllSwitchAllValue += 4;
            }
            z2 = true;
        } else {
            z2 = false;
        }
        String currentLiveSpeedText = CameraSettings.getCurrentLiveSpeedText();
        int faceBeautyRatio = CameraSettings.getFaceBeautyRatio("key_live_shrink_face_ratio");
        int faceBeautyRatio2 = CameraSettings.getFaceBeautyRatio("key_live_enlarge_eye_ratio");
        int faceBeautyRatio3 = CameraSettings.getFaceBeautyRatio("key_live_smooth_strength");
        if (faceBeautyRatio > 0 || faceBeautyRatio2 > 0 || faceBeautyRatio3 > 0) {
            if ((liveAllSwitchAllValue & 8) == 0) {
                liveAllSwitchAllValue += 8;
            }
            z3 = true;
        } else {
            z3 = false;
        }
        CameraStatUtils.trackLiveRecordingParams(z4, str, z, findLiveFilter.directoryName, z2, currentLiveStickerName, currentLiveSpeedText, z3, faceBeautyRatio, faceBeautyRatio2, faceBeautyRatio3, this.mQuality, isFrontCamera());
        CameraSettings.setLiveAllSwitchAddValue(liveAllSwitchAllValue);
    }

    private void updateBeauty() {
        if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreview()) {
            if (this.mBeautyValues == null) {
                this.mBeautyValues = new BeautyValues();
            }
            CameraSettings.initBeautyValues(this.mBeautyValues, this.mModuleIndex);
            String str = TAG;
            Log.d(str, "updateBeauty(): " + this.mBeautyValues);
            this.mCamera2Device.setBeautyValues(this.mBeautyValues);
        }
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
        String str = TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFocusMode() {
        setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
    }

    private void updateFpsRange() {
        this.mCamera2Device.setFpsRange(new Range<>(30, 30));
    }

    private void updateGif() {
    }

    private void updateLiveRelated() {
        this.mCamera2Device.startPreviewCallback(this.mimojiAvatarEngine2, null);
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
        int i = this.mCameraDisplayOrientation;
        int i2 = isFrontCamera() ? 270 : 90;
        CameraSize cameraSize = this.mPreviewSize;
        mimojiAvatarEngine22.initAvatarEngine(i, i2, cameraSize.width, cameraSize.height, isFrontCamera());
    }

    private void updateMimojiVideoCache() {
        new Thread(new e(this)).start();
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSizeWithAssignedMode = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        float ratio = Util.getRatio(CameraSettings.getPictureSizeRatioString(this.mModuleIndex));
        CameraSize optimalPreviewSize = CameraSettings.isGifOn() ? Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) ratio, new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH)) : Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) ratio, new CameraSize(1920, 1080));
        this.mPreviewSize = optimalPreviewSize;
        String str = TAG;
        Log.d(str, "previewSize: " + this.mPreviewSize.toString());
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSizeWithAssignedMode, (double) CameraSettings.getPreviewAspectRatio(16, 9), Util.sWindowHeight, Util.sWindowWidth);
        String str2 = TAG;
        Log.d(str2, "displaySize: " + optimalVideoSnapshotPictureSize.toString());
        this.mCamera2Device.setAlgorithmPreviewSize(optimalPreviewSize);
        this.mCamera2Device.setAlgorithmPreviewFormat(35);
        updateCameraScreenNailSize(optimalPreviewSize.width, optimalPreviewSize.height);
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (isPreviewEisOn()) {
                Log.d(TAG, "videoStabilization: EIS");
                this.mCamera2Device.setEnableEIS(true);
                this.mCamera2Device.setEnableOIS(false);
                this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
                return;
            }
            Log.d(TAG, "videoStabilization: OIS");
            this.mCamera2Device.setEnableEIS(false);
            this.mCamera2Device.setEnableOIS(true);
            this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }

    public /* synthetic */ void bd() {
        deleteMimojiCache();
        startSaveToLocal(null);
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

    public /* synthetic */ void cd() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

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
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.getCameraScreenNail().setExternalFrameProcessor(null);
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

    @Override // com.android.camera.module.BaseModule
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 2) {
                updateFilter();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 5) {
                updateFace();
            } else if (i == 50) {
                continue;
            } else if (i == 58) {
                updateBackSoftLightPreference();
            } else if (i == 66) {
                updateThermalLevel();
            } else if (i == 72) {
                updateGif();
            } else if (i == 19) {
                updateFpsRange();
            } else if (i == 20) {
                continue;
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 25) {
                focusCenter();
            } else if (i == 34) {
                continue;
            } else if (i == 35) {
                updateDeviceOrientation();
            } else if (!(i == 42 || i == 43)) {
                if (i == 54) {
                    updateLiveRelated();
                } else if (i != 55) {
                    switch (i) {
                        case 9:
                            updateAntiBanding(CameraSettings.getAntiBanding());
                            continue;
                        case 10:
                            updateFlashPreference();
                            continue;
                        case 11:
                            continue;
                        case 12:
                            setEvValue();
                            continue;
                        case 13:
                            updateBeauty();
                            continue;
                        case 14:
                            updateFocusMode();
                            continue;
                        default:
                            switch (i) {
                                case 29:
                                    updateExposureMeteringMode();
                                    continue;
                                case 30:
                                    continue;
                                case 31:
                                    updateVideoStabilization();
                                    continue;
                                default:
                                    switch (i) {
                                        case 46:
                                        case 48:
                                            continue;
                                        case 47:
                                            updateUltraWideLDC();
                                            continue;
                                        default:
                                            throw new RuntimeException("no consumer for this updateType: " + i);
                                    }
                            }
                    }
                } else {
                    updateModuleRelated();
                }
            }
        }
    }

    public /* synthetic */ void dd() {
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.restoreWindowBrightness();
        }
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        String str = TAG;
        Log.d(str, "stopScreenLight: protocol = " + fullScreenProtocol + ", mHandler = " + this.mHandler);
        if (fullScreenProtocol != null) {
            fullScreenProtocol.hideScreenLight();
        }
    }

    public /* synthetic */ void ed() {
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor;
        MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        if (mimojiStatusManager2.isInMimojiPreview() && mimojiStatusManager2.getMimojiPanelState() == 0) {
            String str = null;
            if (mimojiStatusManager2.getCurrentMimojiTimbreInfo() != null) {
                if (FileUtils.checkFileConsist(MimojiHelper2.VIDEO_DEAL_CACHE_FILE)) {
                    str = MimojiHelper2.VIDEO_DEAL_CACHE_FILE;
                } else if (FileUtils.checkFileConsist(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE) && (mimojiVideoEditor = this.mMimojiVideoEditor) != null) {
                    mimojiVideoEditor.combineVideoAudio(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE, 2);
                }
            } else if (FileUtils.checkFileConsist(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE)) {
                str = MimojiHelper2.VIDEO_NORMAL_CACHE_FILE;
            }
            if (!TextUtils.isEmpty(str)) {
                Log.d(TAG, "mimoji void updateMimojiVideoCache[]");
                String str2 = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_", "mp4");
                try {
                    FileUtils.copyFile(new File(str), new File(str2));
                    startSaveToLocal(str2);
                } catch (IOException e2) {
                    Log.e(TAG, "mimoji void updateMimojiVideoCache[] " + e2.getMessage());
                }
                deleteMimojiCache();
            }
        }
    }

    public boolean enableFaceDetection() {
        return DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        return CameraCapabilities.SESSION_OPERATION_MODE_MIMOJI;
    }

    @Override // com.android.camera.module.BaseModule
    public void initializeCapabilities() {
        super.initializeCapabilities();
        this.mContinuousFocusSupported = Util.isSupported(4, this.mCameraCapabilities.getSupportedFocusModes());
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
    }

    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    public boolean isAEAFLockSupported() {
        return true;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        int i;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = this.mModuleIndex) == 174 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera();
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor;
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        return isRecording() || getCameraState() == 3 || ((mimojiVideoEditor = this.mMimojiVideoEditor) != null && mimojiVideoEditor.isComposing()) || (mimojiFullScreenProtocol != null && mimojiFullScreenProtocol.isMimojiRecordPreviewShowing());
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
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
        return mimojiAvatarEngine22 != null && mimojiAvatarEngine22.isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        return mimojiFullScreenProtocol != null && mimojiFullScreenProtocol.isMimojiRecordPreviewShowing();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return isSupportFocusShoot();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPhoto() && DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key");
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
        return getCameraState() != 3 && !isFrontCamera() && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate() && isFrameAvailable();
    }

    public void lockAEAF() {
        Log.d(TAG, "lockAEAF");
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
            stopVideoRecording(1);
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
        if (this.mimojiAvatarEngine2 != null && DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
            ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onMimojiCreateBack();
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_SOFT_BACK, MistatsConstants.BaseEvent.CREATE);
            return true;
        } else if (!isRecording()) {
            return super.onBackPressed();
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mLastBackPressedTime > 3000) {
                this.mLastBackPressedTime = currentTimeMillis;
                ToastUtils.showToast(this.mActivity, (int) R.string.record_back_pressed_hint, 80);
            } else {
                stopVideoRecording(2);
            }
            return true;
        }
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

    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        initMimojiEngine();
        startPreview();
        this.mOnResumeTime = SystemClock.uptimeMillis();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
        initMetaParser();
        updateMimojiVideoCache();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureProgress(boolean z, boolean z2, boolean z3, boolean z4) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z, boolean z2, boolean z3, boolean z4) {
        setWaterMark();
        this.mActivity.getCameraScreenNail().animateCapture(this.mOrientation);
        playCameraSound(0);
        this.mimojiAvatarEngine2.onCaptureImage();
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
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.post(b.INSTANCE);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
        this.mActivity.getSensorStateManager().setRotationVectorEnabled(false);
        this.mActivity.getSensorStateManager().setTTARSensorEnabled(false);
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo, Rect rect) {
        if (isCreated() && cameraHardwareFaceArr != null) {
            if (!c.Qn() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
                if (!this.mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), rect)) {
                    return;
                }
            } else if (this.mObjectTrackingStarted) {
                this.mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), rect);
            }
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
            if (mimojiAvatarEngine22 != null && mimojiAvatarEngine22.isOnCreateMimoji()) {
                this.mMainProtocol.lightingDetectFace(cameraHardwareFaceArr, true);
                this.misFaceLocationOk = this.mMainProtocol.isFaceLocationOK();
            }
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
                    Log.v(TAG, str);
                }
                if ((getCameraState() != 3 || focusTask.getFocusTrigger() == 3) && !this.m3ALocked) {
                    this.mFocusManager.onFocusResult(focusTask);
                    return;
                }
                return;
            }
            Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
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
        if (isRecording()) {
            stopVideoRecording(1);
        }
        if (!isSaving()) {
            doLaterReleaseIfNeed();
        }
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
                    if (isSelectingCapturedResult()) {
                        ((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)).startMimojiRecordSaving();
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
                        stopVideoRecording(0);
                    }
                } else if (isRecording() && !isPostProcessing()) {
                    if (!isBackCamera()) {
                        return false;
                    }
                    stopVideoRecording(0);
                }
                return super.onKeyDown(i, keyEvent);
            }
        }
        if (!isSelectingCapturedResult()) {
            ModeProtocol.CloneChooser cloneChooser = (ModeProtocol.CloneChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(416);
            if (cloneChooser == null || !cloneChooser.isShow()) {
                if (i == 24 || i == 88) {
                    z = true;
                }
                if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                    return true;
                }
            } else {
                Log.d(TAG, "ignore onKeyDown " + i);
                return false;
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

    public void onMimojiCaptureCallback() {
        setCameraState(1);
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onParallelImagePostProcStart();
        }
        if (isFrontCamera() && this.mCamera2Device.isNeedFlashOn()) {
            stopScreenLight();
        }
    }

    public void onMimojiCreateCompleted(boolean z) {
        setCameraState(1);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onNewUriArrived(Uri uri, String str) {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol;
        super.onNewUriArrived(uri, str);
        if (isAlive() && (mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)) != null) {
            mimojiFullScreenProtocol.onMimojiSaveToLocalFinished(uri);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
        this.mDeviceOrientation = i;
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine22 != null) {
            mimojiAvatarEngine22.onDeviceRotationChange(i);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        getActivity();
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

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
        super.onPreviewPixelsRead(bArr, i, i2);
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
        Camera2Proxy camera2Proxy;
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        if (doLaterReleaseIfNeed() || (camera2Proxy = this.mCamera2Device) == null) {
            return;
        }
        if (camera2Proxy.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        Log.d(TAG, "mimoji void onReviewDoneClicked[]");
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        new Thread(new a(this)).start();
        if (!doLaterReleaseIfNeed()) {
            if (this.mCamera2Device.isSessionReady()) {
                resumePreview();
            } else {
                startPreview();
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 239) {
            updatePreferenceInWorkThread(13);
            return;
        }
        throw new RuntimeException("unknown configItem changed");
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (i == 110) {
            this.mActivity.onUserInteraction();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.hideRecommendDescTip(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC);
            }
        }
        setTriggerMode(i);
        if (isRecording()) {
            stopVideoRecording(0);
        } else if (!isDoingAction()) {
            if (this.mimojiAvatarEngine2 == null) {
                Log.e(TAG, "onShutterButtonClick  mimojiAvatarEngine2 NULL");
            } else if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreview()) {
                ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                if (backStack != null) {
                    backStack.handleBackStackFromShutter();
                }
                if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 1) {
                    startVideoRecording();
                } else if (turnOnFlash()) {
                    onMimojiCapture();
                }
                HashMap hashMap = new HashMap();
                hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
                trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, 0);
            } else if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                Log.d(TAG, "start create mimoji");
                if (this.mIsLowLight) {
                    Log.d(TAG, "mimoji create low light!");
                } else if (this.mimojiAvatarEngine2.onCreateCapture()) {
                    Log.d(TAG, "create mimoji success");
                    setCameraState(3);
                }
            }
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
        ModeProtocol.BackStack backStack;
        if (!this.mPaused && !this.mDisableSingleTapUp && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 0) {
            if (!isFrameAvailable()) {
                Log.w(TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !this.mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2)) {
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
        DataRepository.dataItemLive().getMimojiStatusManager2().setCurrentMimojiBgInfo(null);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onThermalConstrained() {
        super.onThermalConstrained();
        if (isRecording()) {
            stopVideoRecording(1);
            onReviewCancelClicked();
        }
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

    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            if (isIgnoreTouchEvent()) {
                Log.w(TAG, "ignore volume key");
            } else {
                onShutterButtonClick(i);
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void preTransferOrientation(int i, int i2) {
        super.preTransferOrientation(i, i2);
        this.mDeviceOrientation = i;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
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

    public void setCameraStatePublic(int i) {
        setCameraState(i);
    }

    public void setDisableSingleTapUp(boolean z) {
        this.mDisableSingleTapUp = z;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return isRecording() || isSelectingCapturedResult();
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
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setMetaDataCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPictureSize);
            this.mCamera2Device.setPreviewSize(this.mPreviewSize);
            this.mQuality = Util.convertSizeToQuality(this.mPreviewSize);
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            Log.d(TAG, "MimojiModule, startPreview");
            checkDisplayOrientation();
            isFrontCamera();
            Surface surface = new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture());
            this.mActivity.getCameraScreenNail().setExternalFrameProcessor(this.mimojiAvatarEngine2);
            if (!isSelectingCapturedResult()) {
                this.mCamera2Device.startPreviewSession(surface, 1, false, false, null, getOperatingMode(), false, this);
            }
        }
    }

    public void startSaveToLocal(String str) {
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine22 == null || mimojiAvatarEngine22.isRecordStopping()) {
            Log.e(TAG, "mimoji void startSaveToLocal[] error");
            return;
        }
        String str2 = TAG;
        Log.d(str2, "mimoji void startSaveToLocal[]" + str);
        if (TextUtils.isEmpty(str)) {
            str = ((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)).getMimojiVideoSavePath();
        }
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "mimoji void startSaveToLocal[videoSavePath] null");
            return;
        }
        CameraSize cameraSize = this.mPreviewSize;
        ContentValues genContentValues = Util.genContentValues(2, str, cameraSize.width, cameraSize.height);
        genContentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        getActivity().getImageSaver().addVideo(genContentValues.getAsString("_data"), genContentValues, true);
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

    public void stopVideoRecording(int i) {
        boolean z = false;
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        Log.v(TAG, "listen none");
        if (i != 0) {
            z = true;
        }
        if (CameraSettings.isGifOn()) {
            this.mimojiAvatarEngine2.onRecordStop(i);
            if (System.currentTimeMillis() - this.mRecordTime > 1000) {
                showPreview();
            } else {
                this.mRecordTime = System.currentTimeMillis();
                ToastUtils.showToast(this.mActivity, (int) R.string.mimoji_gif_record_time_short, 80);
                onReviewCancelClicked();
            }
            if (z) {
                this.mRecordTime = System.currentTimeMillis();
            }
        } else if (z) {
            this.mRecordTime = System.currentTimeMillis();
            this.mimojiAvatarEngine2.onRecordStop(i);
            onReviewCancelClicked();
        } else if (System.currentTimeMillis() - this.mRecordTime > 500 && !this.mimojiAvatarEngine2.isRecordStopping()) {
            this.mRecordTime = System.currentTimeMillis();
            this.mimojiAvatarEngine2.onRecordStop(i);
            showPreview();
        }
    }

    public boolean turnOnFlash() {
        if (!this.mCamera2Device.isNeedFlashOn() || 101 != this.mCamera2Device.getFlashMode()) {
            return true;
        }
        return startScreenLight(Util.getScreenLightColor(SystemProperties.getInt("camera_screen_light_wb", 0)), CameraSettings.getScreenLightBrightness());
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        getActivity().getImplFactory().detachAdditional();
    }

    public void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported) {
            this.mCamera2Device.setAELock(false);
        }
        this.mFocusManager.setAeAwbLock(false);
    }

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

    public void updateFocusArea() {
        if (this.mCamera2Device == null) {
            Log.e(TAG, "updateFocusArea: null camera device");
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
