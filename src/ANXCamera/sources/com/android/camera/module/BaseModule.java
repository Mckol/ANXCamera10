package com.android.camera.module;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.WorkerThread;
import b.c.a.b;
import b.c.a.c;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraErrorCallbackImpl;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.FileCompat;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.MutexModeManager;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.ThermalDetector;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.settings.CameraPreferenceFragment;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.module.loader.ActionHideLensDirtyDetectHint;
import com.android.camera.module.loader.ActionUpdateLensDirtyDetect;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.preferences.SettingsOverrider;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.FocusView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CaptureResultParser;
import com.android.gallery3d.ui.GLCanvas;
import com.xiaomi.camera.device.CameraHandlerThread;
import com.xiaomi.camera.device.exception.CameraNotOpenException;
import com.xiaomi.camera.rx.CameraSchedulers;
import com.xiaomi.camera.util.ThreadUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import miui.view.animation.CubicEaseOutInterpolator;

public abstract class BaseModule implements Module, FocusView.ExposureViewListener, Camera2Proxy.CameraMetaDataCallback, Camera2Proxy.CameraPreviewCallback, ModeProtocol.EvChangedProtocol, ModeProtocol.ZoomProtocol, ModeProtocol.CameraModuleSpecial, MutexModeManager.MutexCallBack, ModeProtocol.LensProtocol, ModeProtocol.ModeListManager, Consumer<int[]> {
    protected static final int BACK_PRESSED_TIME_INTERVAL = 3000;
    public static final int[] CAMERA_MODES = {0, 2, 4, 6};
    public static final int CAMERA_MODE_IMAGE_CAPTURE = 2;
    public static final int CAMERA_MODE_NORMAL = 0;
    public static final int CAMERA_MODE_SCAN_QR_CODE = 6;
    public static final int CAMERA_MODE_VIDEO_CAPTURE = 4;
    protected static final boolean DEBUG = Util.isDebugOsBuild();
    public static final int DOCUMENT_BLUR_DETECT_HINT_DURATION_3S = 3000;
    public static final int LENS_DIRTY_DETECT_HINT_DURATION_3S = 3000;
    public static final int LENS_DIRTY_DETECT_HINT_DURATION_8S = 8000;
    protected static final int LENS_DIRTY_DETECT_TIMEOUT = 15000;
    protected static final int SCREEN_DELAY = 60000;
    protected static final int SCREEN_DELAY_KEYGUARD = 30000;
    public static final int SHUTTER_DOWN_FROM_BUTTON = 2;
    public static final int SHUTTER_DOWN_FROM_HARD_KEY = 1;
    public static final int SHUTTER_DOWN_FROM_UNKNOWN = 0;
    public static final int SHUTTER_DOWN_FROM_UNLOCK_OR_LONG_CLICK_OR_AUDIO = 3;
    public static final String START_VIDEO_RECORDING_ACTION = "com.android.camera.action.start_video_recording";
    public static final String STOP_VIDEO_RECORDING_ACTION = "com.android.camera.action.stop_video_recording";
    private static final String TAG = "BaseModule";
    protected boolean isGradienterOn;
    private boolean isShowPreviewDebugInfo;
    protected boolean isZooming;
    protected boolean m3ALockSupported;
    protected boolean mAELockOnlySupported;
    protected Camera mActivity;
    protected int mActualCameraId;
    protected boolean mAeLockSupported;
    protected CameraSize mAlgorithmPreviewSize;
    private AudioController mAudioController;
    protected boolean mAwbLockSupported;
    protected CameraSize mBinningPictureSize;
    protected int mBogusCameraId;
    protected Camera2Proxy mCamera2Device;
    protected CameraCapabilities mCameraCapabilities;
    protected boolean mCameraDisabled;
    protected int mCameraDisplayOrientation;
    protected boolean mCameraHardwareError;
    private int mCameraState = 0;
    protected boolean mContinuousFocusSupported;
    private boolean mDetectLensDirty;
    protected float mDeviceRotation = -1.0f;
    private AlertDialog mDialog;
    protected Rect mDisplayRect;
    protected int mDisplayRotation = -1;
    protected CameraErrorCallbackImpl mErrorCallback;
    protected int mEvState;
    protected int mEvValue;
    private int mEvValueForTrack;
    protected CameraSize mFakeSatOutSize;
    protected CameraCapabilities mFakeTeleCameraCapabilities;
    protected CameraSize mFakeTelePictureSize;
    protected volatile boolean mFallbackProcessed;
    protected String mFlashAutoModeState;
    protected boolean mFocusAreaSupported;
    protected boolean mFocusOrAELockSupported;
    protected Handler mHandler;
    protected boolean mIgnoreFocusChanged;
    private volatile boolean mIgnoreTouchEvent;
    protected boolean mInStartingFocusRecording;
    private AtomicBoolean mIsCreated = new AtomicBoolean(false);
    private AtomicBoolean mIsDeparted = new AtomicBoolean(false);
    private AtomicBoolean mIsFrameAvailable = new AtomicBoolean(false);
    protected volatile int mIsSatFallback = 0;
    protected long mLastBackPressedTime;
    protected volatile int mLastSatFallbackRequestId = -1;
    private float mLastZoomRatio = 1.0f;
    private Disposable mLensDirtyDetectDisposable;
    private boolean mLensDirtyDetectEnable;
    private Disposable mLensDirtyDetectHintDisposable;
    protected CameraCapabilities mMacroCameraCapabilities;
    protected CameraSize mMacroPitureSize;
    protected ModeProtocol.MainContentProtocol mMainProtocol;
    protected int mMaxFaceCount;
    private float mMaxZoomRatio = 1.0f;
    protected volatile boolean mMediaRecorderRecording;
    private int mMessageId;
    protected boolean mMeteringAreaSupported;
    private float mMinZoomRatio = 1.0f;
    protected int mModuleIndex;
    protected MutexModeManager mMutexModePicker;
    protected boolean mObjectTrackingStarted;
    protected boolean mOpenCameraFail;
    protected int mOrientation = 0;
    protected int mOrientationCompensation = 0;
    protected int mOutputPictureFormat;
    protected CameraSize mOutputPictureSize;
    protected volatile boolean mPaused;
    protected int mPendingScreenSlideKeyCode;
    protected final PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        /* class com.android.camera.module.BaseModule.AnonymousClass4 */

        public void onCallStateChanged(int i, String str) {
            if (i == 2 && BaseModule.this.isRecording()) {
                Log.w(BaseModule.TAG, "CALL_STATE_OFFHOOK");
                BaseModule.this.onHostStopAndNotifyActionStop();
            }
            super.onCallStateChanged(i, str);
        }
    };
    protected CameraSize mPictureSize;
    protected CameraSize mPreviewSize;
    private boolean mRestoring;
    protected CameraSize mSensorRawImageSize;
    protected SettingsOverrider mSettingsOverrider = new SettingsOverrider();
    protected CameraCapabilities mStandaloneMacroCameraCapabilities;
    protected CameraSize mSubPictureSize;
    protected long mSurfaceCreatedTimestamp;
    protected CameraCapabilities mTeleCameraCapabilities;
    protected CameraSize mTelePictureSize;
    protected TelephonyManager mTelephonyManager;
    private int mThermalLevel = 0;
    private int mTitleId;
    protected ModeProtocol.TopAlert mTopAlert;
    protected int mTriggerMode = 10;
    protected int mUIStyle = -1;
    protected CameraCapabilities mUltraCameraCapabilities;
    protected CameraCapabilities mUltraTeleCameraCapabilities;
    protected CameraSize mUltraTelePictureSize;
    protected CameraSize mUltraWidePictureSize;
    protected String mUnInterruptableReason;
    private Disposable mUpdateWorkThreadDisposable;
    private ObservableEmitter<int[]> mUpdateWorkThreadEmitter;
    protected boolean mUseLegacyFlashMode;
    private ValueAnimator mValueAnimator;
    protected volatile boolean mWaitingSnapshot;
    protected CameraCapabilities mWideCameraCapabilities;
    protected CameraSize mWidePictureSize;
    protected float mZoomRatio = 1.0f;
    private float mZoomScaled;
    protected boolean mZoomSupported;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CameraMode {
    }

    private void detectLensDirty(CaptureResult captureResult) {
        if (this.mDetectLensDirty && CaptureResultParser.isLensDirtyDetected(captureResult)) {
            showLensDirtyTip();
        }
    }

    protected static String getColorEffectKey() {
        return c.Un() ? "pref_camera_shader_coloreffect_key" : CameraSettings.KEY_COLOR_EFFECT;
    }

    public static int getPreferencesLocalId(int i, int i2) {
        return i + i2;
    }

    private boolean isTeleSupportVideoQuality() {
        int i = this.mModuleIndex;
        if (i != 161 && i != 162 && i != 168 && i != 169 && i != 172 && i != 174 && i != 179 && i != 180 && i != 183 && i != 185 && i != 204) {
            return false;
        }
        return DataRepository.dataItemConfig().getComponentConfigVideoQuality().isSupportVideoQuality(this.mModuleIndex, DataRepository.dataItemFeature().uk() ? Camera2DataContainer.getInstance().getUltraTeleCameraId() : Camera2DataContainer.getInstance().getAuxCameraId(), false);
    }

    private boolean limitZoomByAIWatermark(float f) {
        return DataRepository.dataItemRunning().getComponentRunningAIWatermark().getAIWatermarkEnable() && f < 1.0f;
    }

    private void setCameraDevice(Camera2Proxy camera2Proxy) {
        this.mCamera2Device = camera2Proxy;
        this.mCameraCapabilities = camera2Proxy.getCapabilities();
        this.mZoomSupported = this.mCameraCapabilities.isZoomSupported();
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && !DataRepository.dataItemFeature().Xj()) {
            this.mUltraCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraWideCameraId());
            CameraCapabilities cameraCapabilities = this.mUltraCameraCapabilities;
            if (cameraCapabilities != null) {
                cameraCapabilities.setOperatingMode(getOperatingMode());
            }
        }
        if (DataRepository.dataItemFeature().fm()) {
            this.mStandaloneMacroCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getStandaloneMacroCameraId());
            CameraCapabilities cameraCapabilities2 = this.mStandaloneMacroCameraCapabilities;
            if (cameraCapabilities2 != null) {
                cameraCapabilities2.setOperatingMode(getOperatingMode());
            }
        }
    }

    private void setCreated(boolean z) {
        this.mIsCreated.set(z);
    }

    private void setIgnoreTouchEvent(boolean z) {
        this.mIgnoreTouchEvent = z;
    }

    private void showDebug(final CaptureResult captureResult, final boolean z) {
        if (Util.isShowAfRegionView() && z) {
            this.mMainProtocol.setAfRegionView((MeteringRectangle[]) captureResult.get(CaptureResult.CONTROL_AF_REGIONS), getActiveArraySize(), getDeviceBasedZoomRatio());
        }
        if (Util.isShowDebugInfoView()) {
            this.mActivity.runOnUiThread(new Runnable() {
                /* class com.android.camera.module.BaseModule.AnonymousClass3 */

                public void run() {
                    if (z) {
                        BaseModule baseModule = BaseModule.this;
                        baseModule.mActivity.showDebugInfo(Util.getDebugInformation(captureResult, baseModule.getDebugInfo()));
                        return;
                    }
                    BaseModule.this.mActivity.showDebugInfo("");
                }
            });
        }
    }

    private void updateUltraWideCapability(float f) {
        if (this.mUltraCameraCapabilities == null) {
            this.mUltraCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraWideCameraId());
            CameraCapabilities cameraCapabilities = this.mUltraCameraCapabilities;
            if (cameraCapabilities != null) {
                cameraCapabilities.setOperatingMode(getOperatingMode());
            }
        }
        Log.i(TAG, "updateUltraWideCapability: currZoomRatio = " + f);
        onCapabilityChanged(f < 1.0f ? this.mUltraCameraCapabilities : this.mCameraCapabilities);
    }

    public void accept(@UpdateConstant.UpdateType int[] iArr) throws Exception {
        Log.e(TAG, "accept " + join(iArr) + ". " + this);
        if (this.mUpdateWorkThreadDisposable.isDisposed()) {
            Log.e(TAG, "mUpdateWorkThreadDisposable isDisposed. " + this + " " + this.mUpdateWorkThreadDisposable);
        } else if (isDeviceAlive()) {
            Log.e(TAG, "begin to consumePreference..");
            consumePreference(iArr);
            if (!isAlive() || this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp() != this.mSurfaceCreatedTimestamp || isRepeatingRequestInProgress()) {
                Log.d(TAG, "skip resumePreview on accept. isAlive = " + isAlive() + " isRequestInProgress = " + isRepeatingRequestInProgress());
                return;
            }
            int resumePreview = this.mCamera2Device.resumePreview();
            if (shouldCheckSatFallbackState()) {
                for (int i : iArr) {
                    if (24 == i) {
                        if (Math.abs(this.mLastZoomRatio - this.mCamera2Device.getZoomRatio()) > 0.001f && maySwitchCameraLens(this.mLastZoomRatio, this.mCamera2Device.getZoomRatio())) {
                            this.mLastZoomRatio = this.mCamera2Device.getZoomRatio();
                            this.mIsSatFallback = 1;
                            this.mFallbackProcessed = false;
                            this.mLastSatFallbackRequestId = resumePreview;
                            this.mHandler.removeMessages(60);
                            this.mHandler.sendEmptyMessageDelayed(60, 1500);
                            Log.d(TAG, "lastFallbackRequestId = " + this.mLastSatFallbackRequestId);
                            return;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public void addSaveTask(String str, ContentValues contentValues) {
    }

    /* access modifiers changed from: protected */
    public boolean addZoom(float f) {
        return onZoomingActionUpdate(HybridZoomingSystem.add(this.mZoomRatio, f), 1);
    }

    /* access modifiers changed from: protected */
    public void applyZoomRatio() {
        if (this.mCamera2Device != null) {
            float deviceBasedZoomRatio = getDeviceBasedZoomRatio();
            Log.d(TAG, "applyZoomRatio(): apply zoom ratio to device = " + deviceBasedZoomRatio);
            this.mCamera2Device.setZoomRatio(deviceBasedZoomRatio);
        }
    }

    @Override // com.android.camera.module.Module
    public boolean canIgnoreFocusChanged() {
        return this.mIgnoreFocusChanged;
    }

    public void changeZoom4Crop() {
        if (ModuleManager.isSupportCropFrontMode() && isFrontCamera() && DataRepository.dataItemFeature().jj()) {
            ValueAnimator valueAnimator = this.mValueAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mValueAnimator.cancel();
            }
            int i = this.mOrientation;
            if (i == 0 || i == 180) {
                this.mValueAnimator = ValueAnimator.ofFloat(getZoomRatio(), DataRepository.dataItemFeature().wh());
            } else if (i == 90 || i == 270) {
                this.mValueAnimator = ValueAnimator.ofFloat(getZoomRatio(), 1.0f);
            } else {
                return;
            }
            this.mValueAnimator.setDuration(300L);
            this.mValueAnimator.setInterpolator(new CubicEaseOutInterpolator());
            this.mValueAnimator.addUpdateListener(new C0072d(this));
            this.mValueAnimator.start();
        }
    }

    @Override // com.android.camera.module.Module
    public void checkActivityOrientation() {
        if (isDeviceAlive() && this.mDisplayRotation != Util.getDisplayRotation(this.mActivity)) {
            checkDisplayOrientation();
        }
    }

    public void checkDisplayOrientation() {
        Camera camera = this.mActivity;
        if (camera == null) {
            Log.w(TAG, "checkDisplayOrientation: activity == null");
            return;
        }
        this.mDisplayRotation = Util.getDisplayRotation(camera);
        this.mCameraDisplayOrientation = Util.getDisplayOrientation(this.mDisplayRotation, this.mBogusCameraId);
        Log.v(TAG, "checkDisplayOrientation: " + this.mDisplayRotation + " | " + this.mCameraDisplayOrientation);
        if (camera.getCameraScreenNail() != null) {
            camera.getCameraScreenNail().setDisplayOrientation(this.mDisplayRotation);
        }
    }

    /* access modifiers changed from: protected */
    public final void checkSatFallback(CaptureResult captureResult) {
        boolean isSatFallbackDetected = CaptureResultParser.isSatFallbackDetected(captureResult);
        if (this.mIsSatFallback != 2 && isSatFallbackDetected && !this.mWaitingSnapshot) {
            int sendSatFallbackRequest = this.mCamera2Device.sendSatFallbackRequest(this.mModuleIndex);
            Log.d(TAG, "checkSatFallback: lastFallbackRequestId = " + sendSatFallbackRequest);
            if (sendSatFallbackRequest >= 0) {
                this.mIsSatFallback = 2;
                this.mFallbackProcessed = false;
                this.mLastSatFallbackRequestId = sendSatFallbackRequest;
                this.mHandler.removeMessages(60);
                this.mHandler.sendEmptyMessageDelayed(60, 1500);
            }
        } else if (this.mLastSatFallbackRequestId >= 0) {
            if (this.mLastSatFallbackRequestId <= captureResult.getSequenceId()) {
                this.mFallbackProcessed = true;
            }
            Log.d(TAG, "checkSatFallback: fallbackDetected = " + isSatFallbackDetected + " requestId = " + captureResult.getSequenceId() + "|" + captureResult.getFrameNumber());
            if (this.mFallbackProcessed && !isSatFallbackDetected) {
                this.mIsSatFallback = 0;
                this.mFallbackProcessed = false;
                this.mLastSatFallbackRequestId = -1;
                this.mHandler.removeMessages(60);
                if (this.mWaitingSnapshot && getCameraState() == 1) {
                    this.mWaitingSnapshot = false;
                    this.mHandler.sendEmptyMessage(62);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
    }

    public /* synthetic */ void d(ValueAnimator valueAnimator) {
        setZoomRatio(((Float) valueAnimator.getAnimatedValue()).floatValue());
        updatePreferenceInWorkThread(24);
    }

    @Override // com.android.camera.module.Module
    public void enableCameraControls(boolean z) {
        Log.d(TAG, "enableCameraControls: enable = " + z + ", caller: " + Util.getCallers(1));
        setIgnoreTouchEvent(z ^ true);
    }

    @Override // com.android.camera.MutexModeManager.MutexCallBack
    public void enterMutexMode(int i) {
    }

    public void executeSaveTask(boolean z) {
    }

    @Override // com.android.camera.MutexModeManager.MutexCallBack
    public void exitMutexMode(int i) {
    }

    @Override // com.android.camera.module.Module
    public void fillFeatureControl(StartControl startControl) {
    }

    public View findViewById(int i) {
        return this.mActivity.findViewById(i);
    }

    /* access modifiers changed from: protected */
    public void focusCenter() {
    }

    /* access modifiers changed from: protected */
    public Rect getActiveArraySize() {
        return this.mCameraCapabilities.getActiveArraySize();
    }

    public Camera getActivity() {
        return this.mActivity;
    }

    public int getActualCameraId() {
        return this.mActualCameraId;
    }

    public int getBogusCameraId() {
        return this.mBogusCameraId;
    }

    public CameraCapabilities getCameraCapabilities() {
        return this.mCameraCapabilities;
    }

    public Camera2Proxy getCameraDevice() {
        return this.mCamera2Device;
    }

    /* access modifiers changed from: protected */
    public int getCameraRotation() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getCameraState() {
        return this.mCameraState;
    }

    /* access modifiers changed from: protected */
    public CameraHandlerThread.CookieStore getCookieStore() {
        return Camera2OpenManager.getInstance().getCookieStore();
    }

    /* access modifiers changed from: protected */
    public Rect getCropRegion() {
        return HybridZoomingSystem.toCropRegion(getDeviceBasedZoomRatio(), getActiveArraySize());
    }

    /* access modifiers changed from: protected */
    public String getDebugInfo() {
        return null;
    }

    public final float getDeviceBasedZoomRatio() {
        float f;
        int i = this.mModuleIndex;
        if (i == 182) {
            return 2.0f;
        }
        float f2 = this.mZoomRatio;
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return f2;
        }
        int i2 = this.mActualCameraId;
        if (!(i == 165 || i == 167 || i == 169 || i == 180 || i == 183 || i == 186 || i == 188)) {
            if (i != 173) {
                if (i != 174) {
                    switch (i) {
                        case 161:
                        case 162:
                        case 163:
                            break;
                        default:
                            return f2;
                    }
                }
            } else if (!CameraSettings.isSuperNightUWOpen(i)) {
                return f2;
            }
        }
        if (i2 == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            String ai = DataRepository.dataItemFeature().ai();
            if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex) || HybridZoomingSystem.sDefaultMacroOpticalZoomRatio == HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR) {
                f = HybridZoomingSystem.clamp(this.mZoomRatio / HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR, 1.0f, this.mCameraCapabilities.getMaxZoomRatio());
            } else if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex) || ai == null) {
                return f2;
            } else {
                f = HybridZoomingSystem.clamp(this.mZoomRatio * Float.valueOf(ai).floatValue(), getMinZoomRatio() * Float.valueOf(ai).floatValue(), getMaxZoomRatio() * Float.valueOf(ai).floatValue());
            }
            return f;
        } else if (i2 == Camera2DataContainer.getInstance().getAuxCameraId()) {
            String bi = DataRepository.dataItemFeature().bi();
            float maxZoomRatio = this.mCameraCapabilities.getMaxZoomRatio();
            int i3 = this.mModuleIndex;
            if ((i3 == 162 || i3 == 180) && bi != null) {
                maxZoomRatio = Math.min(maxZoomRatio, Float.parseFloat(bi));
            }
            return HybridZoomingSystem.clamp(this.mZoomRatio / HybridZoomingSystem.getTeleMinZoomRatio(), 1.0f, maxZoomRatio);
        } else if (i2 != Camera2DataContainer.getInstance().getUltraTeleCameraId()) {
            return f2;
        } else {
            String bi2 = DataRepository.dataItemFeature().bi();
            float maxZoomRatio2 = this.mCameraCapabilities.getMaxZoomRatio();
            int i4 = this.mModuleIndex;
            if ((i4 == 162 || i4 == 180) && bi2 != null) {
                maxZoomRatio2 = Math.min(maxZoomRatio2, Float.parseFloat(bi2));
            }
            return HybridZoomingSystem.clamp(this.mZoomRatio / HybridZoomingSystem.getUltraTeleMinZoomRatio(), 1.0f, maxZoomRatio2);
        }
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    /* access modifiers changed from: protected */
    public CameraSize getJpegThumbnailSize() {
        return Util.getOptimalJpegThumbnailSize(this.mCameraCapabilities.getSupportedThumbnailSizes(), ((double) this.mPreviewSize.getWidth()) / ((double) this.mPreviewSize.getHeight()));
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public float getMaxZoomRatio() {
        return this.mMaxZoomRatio;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public float getMinZoomRatio() {
        return this.mMinZoomRatio;
    }

    @Override // com.android.camera.module.Module
    public int getModuleIndex() {
        return this.mModuleIndex;
    }

    public MutexModeManager getMutexModePicker() {
        return this.mMutexModePicker;
    }

    /* access modifiers changed from: protected */
    public abstract int getOperatingMode();

    @Override // com.android.camera.module.Module
    public int getPortraitLightingVersion() {
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if (cameraCapabilities != null) {
            return cameraCapabilities.getPortraitLightingVersion();
        }
        return 1;
    }

    public CameraSize getPreviewSize() {
        return this.mPreviewSize;
    }

    public Resources getResources() {
        return this.mActivity.getResources();
    }

    /* access modifiers changed from: protected */
    public int getScreenDelay() {
        Camera camera = this.mActivity;
        return (camera == null || camera.startFromKeyguard()) ? 30000 : 60000;
    }

    public String getString(int i) {
        return CameraAppImpl.getAndroidContext().getString(i);
    }

    @Override // com.android.camera.module.Module
    public List<String> getSupportedSettingKeys() {
        return new ArrayList();
    }

    /* access modifiers changed from: protected */
    public int getTriggerMode() {
        return this.mTriggerMode;
    }

    @Override // com.android.camera.module.Module
    public String getUnInterruptableReason() {
        return this.mUnInterruptableReason;
    }

    public Window getWindow() {
        return this.mActivity.getWindow();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public float getZoomRatio() {
        return this.mZoomRatio;
    }

    /* access modifiers changed from: protected */
    public void handlePendingScreenSlide() {
        if (this.mPendingScreenSlideKeyCode > 0 && this.mActivity != null) {
            Log.d(TAG, "process pending screen slide: " + this.mPendingScreenSlideKeyCode);
            this.mActivity.handleScreenSlideKeyEvent(this.mPendingScreenSlideKeyCode, null);
            this.mPendingScreenSlideKeyCode = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean handleVolumeKeyEvent(boolean z, boolean z2, int i, boolean z3) {
        String str;
        int i2;
        if (!isAlive()) {
            return true;
        }
        if (z3) {
            str = getString(R.string.pref_camera_volumekey_function_entryvalue_shutter);
        } else {
            int i3 = this.mModuleIndex;
            if (i3 == 161 || i3 == 174 || i3 == 183 || ((i3 == 184 && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPhoto()) || (i2 = this.mModuleIndex) == 179 || i2 == 209)) {
                str = CameraSettings.getKeyVolumeLiveFunction("");
            } else if (ModuleManager.isVideoCategory(getModuleIndex())) {
                str = getModuleIndex() == 180 ? DataRepository.dataItemGlobal().getString(CameraSettings.KEY_VOLUME_PRO_VIDEO_FUNCTION, getString(R.string.pref_pro_video_volumekey_function_default)) : DataRepository.dataItemGlobal().getString(CameraSettings.KEY_VOLUME_VIDEO_FUNCTION, getString(R.string.pref_video_volumekey_function_default));
            } else {
                str = CameraSettings.getVolumeCameraFunction("");
                int moduleIndex = getModuleIndex();
                if ((173 == moduleIndex || 186 == moduleIndex) && str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
                    str = getString(R.string.pref_camera_volumekey_function_entryvalue_shutter);
                }
            }
        }
        if (str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_shutter)) || str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
            performKeyClicked(20, str, i, z2);
            return true;
        } else if (ModuleManager.isPanoramaModule() || ModuleManager.isWideSelfieModule() || !str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_zoom))) {
            return false;
        } else {
            if (isZoomSupported()) {
                if (z2) {
                    onZoomingActionStart(1);
                    if (!isZoomEnabled()) {
                        performKeyClicked(20, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), i, z2);
                        return true;
                    }
                    boolean zoomIn = z ? zoomIn(0.1f) : zoomOut(0.1f);
                    if (i == 0 && zoomIn) {
                        CameraStatUtils.trackZoomAdjusted("volume", false);
                    }
                } else {
                    onZoomingActionEnd(1);
                }
                return true;
            }
            performKeyClicked(20, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), i, z2);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasCameraException() {
        return this.mCameraDisabled || this.mOpenCameraFail || this.mCameraHardwareError || !this.mActivity.couldShowErrorDialog();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeListManager
    public void hideMoreMode(boolean z) {
        Log.d(TAG, "hideMore");
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null && isMoreModeShowing(z)) {
            configChanges.configModeMore(z);
        }
    }

    /* access modifiers changed from: protected */
    public void hideTipMessage(@StringRes int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips == null) {
            return;
        }
        if (i <= 0 || bottomPopupTips.containTips(i)) {
            bottomPopupTips.directlyHideTips();
        }
    }

    public void initByCapability(CameraCapabilities cameraCapabilities) {
        if (cameraCapabilities == null) {
            Log.w(TAG, "init by capability(capability == null)");
            return;
        }
        this.mAeLockSupported = cameraCapabilities.isAELockSupported();
        this.mAwbLockSupported = cameraCapabilities.isAWBLockSupported();
        this.mFocusAreaSupported = cameraCapabilities.isAFRegionSupported();
        this.mMeteringAreaSupported = cameraCapabilities.isAERegionSupported();
        b dataItemFeature = DataRepository.dataItemFeature();
        boolean z = true;
        this.mAELockOnlySupported = dataItemFeature.si() && !this.mFocusAreaSupported && this.mMeteringAreaSupported && this.mAeLockSupported;
        this.mFocusOrAELockSupported = this.mFocusAreaSupported || this.mAELockOnlySupported;
        boolean Aj = dataItemFeature.Aj();
        if (!CameraSettings.isAEAFLockSupport() || (!Aj && !isBackCamera() && !this.mAELockOnlySupported)) {
            z = false;
        }
        this.m3ALockSupported = z;
    }

    /* access modifiers changed from: protected */
    public void initializeCapabilities() {
        this.mCameraCapabilities.setOperatingMode(getOperatingMode());
        initByCapability(this.mCameraCapabilities);
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
        initializeZoomRangeFromCapabilities();
    }

    /* access modifiers changed from: protected */
    public void initializeZoomRangeFromCapabilities() {
        if (this.mZoomSupported) {
            float f = 1.0f;
            if (isBackCamera()) {
                int moduleIndex = getModuleIndex();
                if (moduleIndex != 165) {
                    if (moduleIndex != 167) {
                        if (moduleIndex != 169) {
                            if (moduleIndex != 180) {
                                if (moduleIndex != 183) {
                                    if (moduleIndex != 186) {
                                        if (moduleIndex != 188) {
                                            if (moduleIndex == 204) {
                                                setMinZoomRatio(1.0f);
                                                setMaxZoomRatio(6.0f);
                                                return;
                                            } else if (moduleIndex != 205) {
                                                switch (moduleIndex) {
                                                    case 161:
                                                        break;
                                                    case 162:
                                                        if (!DataRepository.dataItemGlobal().isNormalIntent() && this.mCameraCapabilities.isSupportLightTripartite()) {
                                                            setMinZoomRatio(1.0f);
                                                            setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                                            return;
                                                        } else if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                                            setMinZoomRatio(1.0f);
                                                            if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                                                setMaxZoomRatio(2.0f);
                                                                return;
                                                            } else {
                                                                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                                                return;
                                                            }
                                                        } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                                            setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                                                            setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                                                            return;
                                                        } else if (isInVideoSAT()) {
                                                            if (!CameraSettings.isVideoQuality8KOpen(this.mModuleIndex)) {
                                                                f = HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex);
                                                            }
                                                            setMinZoomRatio(f);
                                                            setMaxZoomRatio(DataRepository.dataItemFeature().Gh());
                                                            return;
                                                        } else {
                                                            if (!CameraSettings.isVideoQuality8KOpen(this.mModuleIndex)) {
                                                                f = HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex);
                                                            }
                                                            setMinZoomRatio(f);
                                                            setVideoMaxZoomRatioByTele();
                                                            return;
                                                        }
                                                    case 163:
                                                        break;
                                                    default:
                                                        switch (moduleIndex) {
                                                            case 172:
                                                                if (isUltraWideBackCamera()) {
                                                                    setMinZoomRatio(1.0f);
                                                                    setMaxZoomRatio(2.0f);
                                                                    return;
                                                                }
                                                                setMinZoomRatio(1.0f);
                                                                setMaxZoomRatio(3.0f);
                                                                return;
                                                            case 173:
                                                                if (!CameraSettings.isSuperNightUWOpen(this.mModuleIndex)) {
                                                                    setMinZoomRatio(1.0f);
                                                                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                                                    return;
                                                                }
                                                                setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                                                                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                                                return;
                                                            case 174:
                                                                break;
                                                            case 175:
                                                                if (DataRepository.dataItemFeature().bk()) {
                                                                    setMinZoomRatio(1.0f);
                                                                    setMaxZoomRatio(HybridZoomingSystem.getMaximumOpticalZoomRatio(175));
                                                                    return;
                                                                }
                                                                break;
                                                            default:
                                                                setMinZoomRatio(1.0f);
                                                                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                                                                return;
                                                        }
                                                }
                                            } else {
                                                setMinZoomRatio(1.0f);
                                                setMaxZoomRatio(Math.min(10.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                                return;
                                            }
                                        }
                                        setMinZoomRatio(5.0f);
                                        setMaxZoomRatio(Math.min(60.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                        return;
                                    }
                                }
                                if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                    setMinZoomRatio(1.0f);
                                    if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                        setMaxZoomRatio(2.0f);
                                        return;
                                    } else {
                                        setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                        return;
                                    }
                                } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                                    setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                                    setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                                    return;
                                } else {
                                    setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                                    setVideoMaxZoomRatioByTele();
                                    return;
                                }
                            }
                        } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                            setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                            if (isInVideoSAT()) {
                                setMaxZoomRatio(Math.min(DataRepository.dataItemFeature().Gh(), this.mCameraCapabilities.getMaxZoomRatio()));
                                return;
                            } else {
                                setVideoMaxZoomRatioByTele();
                                return;
                            }
                        } else {
                            setMinZoomRatio(1.0f);
                            if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex)) {
                                setMaxZoomRatio(2.0f);
                                return;
                            } else {
                                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                return;
                            }
                        }
                    }
                    if (DataRepository.dataItemFeature().bk() && DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn()) {
                        setMinZoomRatio(1.0f);
                        setMaxZoomRatio(HybridZoomingSystem.getMaximumOpticalZoomRatio(175));
                        return;
                    } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex) || "macro".equals(DataRepository.dataItemConfig().getManuallyDualLens().getComponentValue(this.mModuleIndex))) {
                        setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                        setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                        return;
                    } else if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || ComponentManuallyDualLens.LENS_ULTRA.equals(DataRepository.dataItemConfig().getManuallyDualLens().getComponentValue(this.mModuleIndex))) {
                        setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                        setMaxZoomRatio(2.0f);
                        return;
                    } else {
                        setMinZoomRatio(1.0f);
                        if (this.mModuleIndex == 180) {
                            if (isAuxCamera()) {
                                setMinZoomRatio(HybridZoomingSystem.getTeleMinZoomRatio());
                                setVideoMaxZoomRatioByTele();
                                return;
                            } else if (isUltraTeleCamera()) {
                                setMinZoomRatio(HybridZoomingSystem.getUltraTeleMinZoomRatio());
                                setVideoMaxZoomRatioByTele();
                                return;
                            } else {
                                setMinZoomRatio(1.0f);
                                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                                return;
                            }
                        } else if (isAuxCamera()) {
                            setMinZoomRatio(HybridZoomingSystem.getTeleMinZoomRatio());
                            if (!c.isSupportedOpticalZoom() || DataRepository.dataItemFeature().pk()) {
                                setMaxZoomRatio(HybridZoomingSystem.getTeleMinZoomRatio() * this.mCameraCapabilities.getMaxZoomRatio());
                                return;
                            } else {
                                setMaxZoomRatio(Math.min(Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getSATCameraId()).getMaxZoomRatio(), HybridZoomingSystem.getTeleMinZoomRatio() * this.mCameraCapabilities.getMaxZoomRatio()));
                                return;
                            }
                        } else if (isUltraTeleCamera()) {
                            setMinZoomRatio(HybridZoomingSystem.getUltraTeleMinZoomRatio());
                            if (c.isSupportedOpticalZoom()) {
                                setMaxZoomRatio(Math.min(Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getSATCameraId()).getMaxZoomRatio(), HybridZoomingSystem.getUltraTeleMinZoomRatio() * this.mCameraCapabilities.getMaxZoomRatio()));
                                return;
                            } else {
                                setMaxZoomRatio(HybridZoomingSystem.getUltraTeleMinZoomRatio() * this.mCameraCapabilities.getMaxZoomRatio());
                                return;
                            }
                        } else {
                            setMinZoomRatio(1.0f);
                            setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                            return;
                        }
                    }
                }
                if (!DataRepository.dataItemGlobal().isNormalIntent() && this.mCameraCapabilities.isSupportLightTripartite()) {
                    setMinZoomRatio(1.0f);
                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                } else if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    setMinZoomRatio(1.0f);
                    if (CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) || CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                        setMaxZoomRatio(2.0f);
                    } else {
                        setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                    }
                } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                    setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                    setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                } else {
                    setMinZoomRatio(HybridZoomingSystem.getMinimumOpticalZoomRatio(this.mModuleIndex));
                    setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
                }
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(this.mCameraCapabilities.getMaxZoomRatio());
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0244  */
    /* JADX WARNING: Removed duplicated region for block: B:87:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    public void initializeZoomRatio() {
        float f = 1.0f;
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT || !isBackCamera()) {
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to 1.0");
            if (DataRepository.dataItemFeature().jj()) {
                int i = this.mOrientation;
                if ((i == 0 || i == 180) && ModuleManager.isSupportCropFrontMode()) {
                    f = DataRepository.dataItemFeature().wh();
                }
                setZoomRatio(f);
                return;
            }
            setZoomRatio(1.0f);
            return;
        }
        int i2 = this.mModuleIndex;
        if (i2 != 165) {
            if (i2 != 167) {
                if (i2 != 169) {
                    if (i2 != 177) {
                        if (i2 != 180) {
                            if (i2 != 186) {
                                if (i2 == 188) {
                                    setZoomRatio(5.0f);
                                } else if (i2 != 173) {
                                    if (!(i2 == 174 || i2 == 183)) {
                                        if (i2 != 184) {
                                            switch (i2) {
                                                case 161:
                                                case 162:
                                                    break;
                                                case 163:
                                                    break;
                                                default:
                                                    Log.d(TAG, "resetZoomRatio(): set zoom ratio to 1.0");
                                                    setZoomRatio(1.0f);
                                                    break;
                                            }
                                        }
                                    }
                                } else if (!CameraSettings.isSuperNightUWOpen(i2)) {
                                    String zoomRatioHistory = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(1.0f));
                                    Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory);
                                    setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory, 1.0f));
                                }
                                if (c.isSupportedOpticalZoom() && this.mZoomRatio < 1.0f) {
                                    Log.d(TAG, "resetZoomRatio(): need updateUltraWideCapability");
                                    updateUltraWideCapability(this.mZoomRatio);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                }
                if (!CameraSettings.isUltraWideConfigOpen(this.mModuleIndex)) {
                    if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
                        Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                        setZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                    } else if (CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isUltraWideBackCamera()) {
                        Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                        setZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                    } else if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                        Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + HybridZoomingSystem.sDefaultMacroOpticalZoomRatio);
                        setZoomRatio(HybridZoomingSystem.sDefaultMacroOpticalZoomRatio);
                    } else {
                        String zoomRatioHistory2 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(1.0f));
                        Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory2);
                        setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory2, 1.0f));
                    }
                }
                if (c.isSupportedOpticalZoom()) {
                    return;
                }
                return;
            }
            if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                String zoomRatioHistory3 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(HybridZoomingSystem.sDefaultMacroOpticalZoomRatio));
                Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory3);
                setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory3, HybridZoomingSystem.sDefaultMacroOpticalZoomRatio));
            } else if (isUltraWideBackCamera()) {
                float f2 = HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR;
                setZoomRatio(f2);
                Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + f2);
            } else if (isAuxCamera()) {
                float teleMinZoomRatio = HybridZoomingSystem.getTeleMinZoomRatio();
                setZoomRatio(teleMinZoomRatio);
                Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + teleMinZoomRatio);
            } else if (isUltraTeleCamera()) {
                float ultraTeleMinZoomRatio = HybridZoomingSystem.getUltraTeleMinZoomRatio();
                setZoomRatio(ultraTeleMinZoomRatio);
                Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + ultraTeleMinZoomRatio);
            } else if (!CameraSettings.isUltraPixelRearOn()) {
                String zoomRatioHistory4 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(1.0f));
                Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory4);
                setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory4, 1.0f));
            } else {
                Log.d(TAG, "resetZoomRatio(): set zoom ratio to 1.0");
                setZoomRatio(1.0f);
            }
            if (c.isSupportedOpticalZoom()) {
            }
        }
        if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            String zoomRatioHistory5 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(HybridZoomingSystem.sDefaultMacroOpticalZoomRatio));
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory5);
            setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory5, HybridZoomingSystem.sDefaultMacroOpticalZoomRatio));
        } else if (!CameraSettings.isUltraPixelRearOn()) {
            String zoomRatioHistory6 = HybridZoomingSystem.getZoomRatioHistory(this.mModuleIndex, Float.toString(1.0f));
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to " + zoomRatioHistory6);
            setZoomRatio(HybridZoomingSystem.toFloat(zoomRatioHistory6, 1.0f));
        } else {
            Log.d(TAG, "resetZoomRatio(): set zoom ratio to 1.0");
            setZoomRatio(1.0f);
        }
        if (c.isSupportedOpticalZoom()) {
        }
    }

    public boolean isAlive() {
        return isCreated() && !isDeparted();
    }

    /* access modifiers changed from: protected */
    public final boolean isAuxCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getAuxCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isBackCamera() {
        return this.mBogusCameraId == 0;
    }

    @Override // com.android.camera.module.Module
    public boolean isBlockSnap() {
        return isDoingAction();
    }

    /* access modifiers changed from: protected */
    public final boolean isBokehFrontCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getBokehFrontCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isBokehUltraWideBackCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideBokehCameraId();
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        return CameraSettings.isSuperNightUWOpen(this.mModuleIndex) && HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && isBackCamera();
    }

    @Override // com.android.camera.module.Module
    public boolean isCaptureIntent() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isCreated() {
        return this.mIsCreated.get();
    }

    @Override // com.android.camera.module.Module
    public boolean isDeparted() {
        return this.mIsDeparted.get();
    }

    public boolean isDeviceAlive() {
        boolean z = this.mCamera2Device != null && isAlive();
        if (!z) {
            Object[] objArr = new Object[3];
            objArr[0] = this.mCamera2Device != null ? "valid" : "invalid";
            objArr[1] = isCreated() ? "created" : "destroyed";
            objArr[2] = isDeparted() ? "departed" : "alive";
            String format = String.format("device: %s module: %s|%s", objArr);
            if (DEBUG) {
                new RuntimeException(format).printStackTrace();
            } else {
                Log.e(TAG, Util.getCallers(1) + "|" + format);
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public final boolean isDualCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getSATCameraId() || this.mActualCameraId == Camera2DataContainer.getInstance().getBokehCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isDualFrontCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getSATFrontCameraId() || this.mActualCameraId == Camera2DataContainer.getInstance().getBokehFrontCameraId();
    }

    @Override // com.android.camera.module.Module
    public boolean isFrameAvailable() {
        return this.mIsFrameAvailable.get();
    }

    public final boolean isFrontCamera() {
        return this.mBogusCameraId == 1;
    }

    @Override // com.android.camera.module.Module
    public boolean isIgnoreTouchEvent() {
        return this.mIgnoreTouchEvent;
    }

    public boolean isInDisplayRect(int i, int i2) {
        Rect rect = this.mDisplayRect;
        if (rect == null) {
            return false;
        }
        return rect.contains(i, i2);
    }

    public boolean isInTapableRect(int i, int i2) {
        if (this.mDisplayRect == null) {
            return false;
        }
        return Util.getTapableRectWithEdgeSlop(judgeTapableRectByUiStyle(), this.mDisplayRect, this.mModuleIndex, this.mActivity).contains(i, i2);
    }

    /* access modifiers changed from: protected */
    public boolean isInVideoSAT() {
        return DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(getModuleIndex()) && getActualCameraId() == Camera2DataContainer.getInstance().getVideoSATCameraId();
    }

    @Override // com.android.camera.module.Module
    public boolean isKeptBitmapTexture() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isMainBackCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getMainBackCameraId();
    }

    @Override // com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isMeteringAreaOnly() {
        return false;
    }

    public boolean isMimojiMode() {
        int i = this.mModuleIndex;
        return i == 177 || i == 184;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeListManager
    public boolean isMoreModeShowing(boolean z) {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            return z ? baseDelegate.getActiveFragment(R.id.full_screen_feature) == 65526 : baseDelegate.getActiveFragment(R.id.full_screen_feature) == 65525;
        }
        return false;
    }

    @Override // com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isNeedHapticFeedback() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isNeedMute() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isParallelSessionEnable() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isPictureUseDualFrontCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getBokehFrontCameraId() && !DataRepository.dataItemFeature().xi();
    }

    public boolean isPortraitMode() {
        return this.mModuleIndex == 171;
    }

    @Override // com.android.camera.module.Module
    public boolean isPostProcessing() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isRecording() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isRepeatingRequestInProgress() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isRestoring() {
        return this.mRestoring;
    }

    @Override // com.android.camera.module.Module
    public boolean isSelectingCapturedResult() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isShot2GalleryOrEnableParallel() {
        return false;
    }

    @Override // com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowAeAfLockIndicator() {
        return false;
    }

    @Override // com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isSingleCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getMainBackCameraId() || this.mActualCameraId == Camera2DataContainer.getInstance().getFrontCameraId();
    }

    /* access modifiers changed from: protected */
    public boolean isSquareModeChange() {
        return ModuleManager.isSquareModule() != (this.mActivity.getCameraScreenNail().getRenderTargetRatio() == 2);
    }

    /* access modifiers changed from: protected */
    public final boolean isStandaloneMacroCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getStandaloneMacroCameraId();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial
    public boolean isStartCountCapture() {
        return false;
    }

    @Override // com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportAELockOnly() {
        return this.mAELockOnlySupported;
    }

    @Override // com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isTextureExpired() {
        Camera camera = this.mActivity;
        return camera == null || camera.getCameraScreenNail().getSurfaceCreatedTimestamp() != this.mSurfaceCreatedTimestamp;
    }

    public boolean isThermalThreshold() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isUltraTeleCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getUltraTeleCameraId();
    }

    /* access modifiers changed from: protected */
    public final boolean isUltraWideBackCamera() {
        return this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId();
    }

    public boolean isZoomEnabled() {
        return true;
    }

    public boolean isZoomRatioBetweenUltraAndWide() {
        float f = this.mZoomRatio;
        return f >= HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR && f < 1.0f;
    }

    public boolean isZoomRatioBetweenUltraAndWide(float f) {
        return f >= HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR && f < 1.0f;
    }

    /* access modifiers changed from: protected */
    public boolean isZoomSupported() {
        return this.mZoomSupported;
    }

    /* access modifiers changed from: protected */
    public boolean isZslPreferred() {
        return false;
    }

    public String join(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i : iArr) {
            sb.append(i);
            sb.append(",");
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean judgeTapableRectByUiStyle() {
        return !isRecording();
    }

    /* access modifiers changed from: protected */
    public void keepScreenOn() {
        Handler handler;
        if (!this.mActivity.isActivityPaused() && (handler = this.mHandler) != null) {
            handler.removeMessages(17);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(52);
            getWindow().addFlags(128);
        }
    }

    /* access modifiers changed from: protected */
    public void keepScreenOnAwhile() {
        Handler handler;
        if (!this.mActivity.isActivityPaused() && (handler = this.mHandler) != null) {
            handler.sendEmptyMessageDelayed(17, 1000);
        }
    }

    /* access modifiers changed from: protected */
    public void mapTapCoordinate(Object obj) {
        if (obj instanceof Point) {
            Point point = (Point) obj;
            int i = point.x;
            Rect rect = this.mDisplayRect;
            point.x = i - rect.left;
            point.y -= rect.top;
        } else if (obj instanceof RectF) {
            RectF rectF = (RectF) obj;
            float f = rectF.left;
            Rect rect2 = this.mDisplayRect;
            int i2 = rect2.left;
            rectF.left = f - ((float) i2);
            rectF.right -= (float) i2;
            float f2 = rectF.top;
            int i3 = rect2.top;
            rectF.top = f2 - ((float) i3);
            rectF.bottom -= (float) i3;
        }
    }

    /* access modifiers changed from: protected */
    public boolean maySwitchCameraLens(float f, float f2) {
        float teleMinZoomRatio = HybridZoomingSystem.getTeleMinZoomRatio();
        if (f < f2) {
            if (f < 1.0f && f2 >= 1.0f) {
                return true;
            }
            if (f >= teleMinZoomRatio || f2 < teleMinZoomRatio) {
                return HybridZoomingSystem.IS_4_OR_MORE_SAT && f < 3.7f && f2 >= 3.7f;
            }
            return true;
        } else if (f <= f2) {
            return false;
        } else {
            if (HybridZoomingSystem.IS_4_OR_MORE_SAT && f >= 3.7f && f2 < 3.7f) {
                return true;
            }
            if (f < teleMinZoomRatio || f2 >= teleMinZoomRatio) {
                return f >= 1.0f && f2 < 1.0f;
            }
            return true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public void notifyDualZoom(boolean z) {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null && HybridZoomingSystem.IS_2_SAT) {
            camera2Proxy.setOpticalZoomToTele(z);
            this.mCamera2Device.resumePreview();
        }
    }

    @Override // com.android.camera.module.Module
    public void notifyError() {
        this.mCameraHardwareError = true;
        setCameraState(0);
        if (!this.mActivity.isActivityPaused()) {
            onCameraException();
            return;
        }
        this.mActivity.releaseAll(true, true);
        this.mActivity.finish();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public void notifyZooming(boolean z) {
        this.isZooming = z;
    }

    @Override // com.android.camera.module.Module
    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @Override // com.android.camera.module.Module
    public boolean onBackPressed() {
        ModeProtocol.ModeChangeController modeChangeController;
        if (isCaptureIntent() || DataRepository.dataItemGlobal().getComponentModuleList().isCommonMode(this.mModuleIndex) || (modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179)) == null) {
            return false;
        }
        modeChangeController.resetToCommonMode();
        return true;
    }

    @Override // com.android.camera.module.Module
    @CallSuper
    public void onBroadcastReceived(Context context, Intent intent) {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isFinishing() && intent != null) {
            String action = intent.getAction();
            Log.v(TAG, "onReceive: action=" + action);
            if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
                Log.d(TAG, "SD card available");
                Storage.initStorage(context);
                this.mActivity.getScreenHint().updateHint();
            } else if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
                Log.d(TAG, "SD card unavailable");
                FileCompat.updateSDPath();
                this.mActivity.getScreenHint().updateHint();
                this.mActivity.getThumbnailUpdater().getLastThumbnail();
            } else if ("android.intent.action.MEDIA_SCANNER_STARTED".equals(action)) {
                Log.d(TAG, "media scanner started");
            } else if ("android.intent.action.MEDIA_SCANNER_FINISHED".equals(action)) {
                this.mActivity.getThumbnailUpdater().getLastThumbnail();
                Log.d(TAG, "media scanner finisheded");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCameraException() {
        Log.e(TAG, "onCameraException: mid = " + this.mModuleIndex + ", cid = " + this.mActualCameraId);
        if (CameraSchedulers.isOnMainThread()) {
            Camera camera = this.mActivity;
            if (camera == null || camera.getCurrentModule() == this) {
                if ((this.mOpenCameraFail || this.mCameraHardwareError) && !this.mActivity.isActivityPaused() && this.mActivity.couldShowErrorDialog()) {
                    Util.showErrorAndFinish(this.mActivity, CameraSettings.updateOpenCameraFailTimes() > 1 ? R.string.cannot_connect_camera_twice : R.string.cannot_connect_camera_once, false);
                    this.mActivity.showErrorDialog();
                }
                if (this.mCameraDisabled && this.mActivity.couldShowErrorDialog()) {
                    Util.showErrorAndFinish(this.mActivity, R.string.camera_disabled, false);
                    this.mActivity.showErrorDialog();
                }
            } else {
                Log.d(TAG, "onCameraException: module changed: prev = " + this);
                Log.d(TAG, "onCameraException: module changed: curr = " + this.mActivity.getCurrentModule());
                return;
            }
        } else {
            sendOpenFailMessage();
        }
        enableCameraControls(false);
    }

    /* access modifiers changed from: protected */
    public void onCameraOpened() {
        initializeCapabilities();
        initializeZoomRatio();
        this.mCamera2Device.setMetaDataCallback(this);
        if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
            this.mCamera2Device.setAELock(true);
        }
        updateLensDirtyDetect(false);
    }

    /* access modifiers changed from: protected */
    public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
        initByCapability(cameraCapabilities);
        Log.d(TAG, "onCapabilityChanged: mFocusAreaSupported = " + this.mFocusAreaSupported + ", mAELockOnlySupported = " + this.mAELockOnlySupported);
    }

    @Override // com.android.camera.module.Module
    public void onCreate(int i, int i2) {
        ThreadUtils.assertCameraSetupThread();
        Log.d(TAG, "onCreate: moduleIndex->" + i + ", cameraId->" + i2 + "@" + this);
        this.mModuleIndex = i;
        this.mBogusCameraId = i2;
        this.mActualCameraId = Camera2DataContainer.getInstance().getActualOpenCameraId(i2, i);
        for (CameraHandlerThread.Cookie cookie : getCookieStore().getCookies()) {
            Log.d(TAG, "enumerating: " + cookie.mCamera2Device);
            Camera2Proxy camera2Proxy = cookie.mCamera2Device;
            if (camera2Proxy != null && camera2Proxy.getId() == this.mActualCameraId) {
                Log.d(TAG, "setCameraDevice: " + cookie.mCamera2Device);
                setCameraDevice(cookie.mCamera2Device);
            }
        }
        if (this.mCamera2Device != null) {
            this.mErrorCallback = new CameraErrorCallbackImpl(this.mActivity);
            this.mMainProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            this.mMutexModePicker = new MutexModeManager(this);
            this.mUpdateWorkThreadDisposable = Observable.create(new ObservableOnSubscribe<int[]>() {
                /* class com.android.camera.module.BaseModule.AnonymousClass1 */

                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(ObservableEmitter<int[]> observableEmitter) throws Exception {
                    BaseModule.this.mUpdateWorkThreadEmitter = observableEmitter.serialize();
                }
            }).observeOn(CameraSchedulers.sCameraSetupScheduler).subscribe(this);
            Log.d(TAG, "create disposable " + this + " " + this.mUpdateWorkThreadDisposable);
            if ((getModuleIndex() == 165 || getModuleIndex() == 163) && DataRepository.dataItemFeature().Ij() && CameraSettings.isLensDirtyDetectEnabled() && DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_LENS_DIRTY_TIP, DataRepository.dataItemFeature().gh())) {
                if (this.mCameraCapabilities.getMiAlgoASDVersion() >= 2.0f) {
                    this.mDetectLensDirty = false;
                } else {
                    this.mDetectLensDirty = true;
                }
                if (this.mCameraCapabilities.getMiAlgoASDVersion() >= 3.0f) {
                    this.mLensDirtyDetectEnable = true;
                } else {
                    this.mLensDirtyDetectDisposable = Completable.complete().delay(15000, TimeUnit.MILLISECONDS, CameraSchedulers.sCameraSetupScheduler).doOnComplete(new ActionUpdateLensDirtyDetect(this, true)).subscribe();
                }
            }
            setCreated(true);
            this.mIsDeparted.set(false);
            this.isShowPreviewDebugInfo = Util.isShowPreviewDebugInfo();
            this.isGradienterOn = CameraSettings.isGradienterOn();
            return;
        }
        throw new CameraNotOpenException();
    }

    @Override // com.android.camera.module.Module
    @CallSuper
    public void onDestroy() {
        LocalParallelService.LocalBinder localBinder;
        Log.d(TAG, "onDestroy: E");
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.getSensorStateManager().setSensorStateListener(null);
        }
        setCreated(false);
        ImageSaver imageSaver = this.mActivity.getImageSaver();
        if (imageSaver != null) {
            imageSaver.onModuleDestroy();
        }
        if (isParallelSessionEnable() && (localBinder = AlgoConnector.getInstance().getLocalBinder()) != null) {
            localBinder.stopPostProcessor(this.mActivity.hashCode());
        }
        Log.d(TAG, "onDestroy: X");
    }

    @Override // com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    @MainThread
    public void onEvChanged(int i, int i2) {
        if (isAlive()) {
            this.mEvValue = i;
            this.mEvState = i2;
            if (i2 == 1 || i2 == 3) {
                this.mEvValueForTrack = i;
                CameraSettings.writeExposure(i);
                ViberatorContext.getInstance(getActivity().getApplicationContext()).performEVChange();
            }
            updatePreferenceInWorkThread(12);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void onFocusAreaChanged(int i, int i2) {
    }

    @Override // com.android.camera.module.Module
    public boolean onGestureTrack(RectF rectF, boolean z) {
        return true;
    }

    public void onGradienterSwitched(boolean z) {
        this.isGradienterOn = z;
        this.mActivity.getSensorStateManager().setGradienterEnabled(z);
        this.mActivity.getSensorStateManager().register();
        updatePreferenceTrampoline(2, 5);
    }

    @Override // com.android.camera.module.Module
    public void onHostStopAndNotifyActionStop() {
    }

    /* access modifiers changed from: protected */
    public boolean onInterceptZoomingEvent(float f, float f2, int i) {
        int i2;
        if (isCameraSwitchingDuringZoomingAllowed()) {
            boolean z = CameraSettings.isSupportedOpticalZoom() || !((i2 = this.mModuleIndex) == 163 || i2 == 165 || i2 == 186);
            int i3 = (f2 > 1.0f ? 1 : (f2 == 1.0f ? 0 : -1));
            if (i3 < 0) {
                CameraSettings.setVideoQuality8K(this.mModuleIndex, false);
            }
            if (DataRepository.dataItemFeature().tk() || DataRepository.dataItemFeature().uk()) {
                float ultraTeleMinZoomRatio = DataRepository.dataItemFeature().uk() ? HybridZoomingSystem.getUltraTeleMinZoomRatio() : HybridZoomingSystem.getTeleMinZoomRatio();
                int i4 = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
                if (i4 < 0 || f >= ultraTeleMinZoomRatio || i3 >= 0) {
                    int i5 = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
                    if (i5 < 0 && f2 >= 1.0f && f2 < ultraTeleMinZoomRatio) {
                        switchCameraLens(z, false, false);
                        return true;
                    } else if (i4 < 0 || f >= ultraTeleMinZoomRatio || f2 < ultraTeleMinZoomRatio || !isTeleSupportVideoQuality()) {
                        int i6 = (f > ultraTeleMinZoomRatio ? 1 : (f == ultraTeleMinZoomRatio ? 0 : -1));
                        if (i6 < 0 || f2 < 1.0f || f2 >= ultraTeleMinZoomRatio || (!isAuxCamera() && !isUltraTeleCamera())) {
                            if (i5 < 0 && f2 >= ultraTeleMinZoomRatio) {
                                switchCameraLens(z, false, false);
                                return true;
                            } else if (i6 >= 0 && i3 < 0) {
                                switchCameraLens(z, false, false);
                                return true;
                            } else if (isUltraWideBackCamera() && f2 >= 1.0f) {
                                switchCameraLens(z, false, false);
                                return true;
                            } else if (isMainBackCamera() && f2 >= ultraTeleMinZoomRatio && isTeleSupportVideoQuality()) {
                                switchCameraLens(z, false, false);
                                return true;
                            }
                        } else if (CameraSettings.isSuperNightUWOpen(this.mModuleIndex)) {
                            return false;
                        } else {
                            if (CameraSettings.isVideoQuality8KOpen(getModuleIndex()) && (!DataRepository.dataItemFeature().pk() || !ComponentConfigVideoQuality.is8KCamcorderSupported(Camera2DataContainer.getInstance().getUltraTeleCameraId()))) {
                                return false;
                            }
                            switchCameraLens(z, false, false);
                            return true;
                        }
                    } else if (CameraSettings.isSuperNightUWOpen(this.mModuleIndex)) {
                        return false;
                    } else {
                        switchCameraLens(z, false, false);
                        return true;
                    }
                } else {
                    switchCameraLens(z, false, false);
                    return true;
                }
            } else if (f >= 1.0f && i3 < 0) {
                switchCameraLens(z, false, false);
                return true;
            } else if (f < 1.0f && f2 >= 1.0f) {
                switchCameraLens(z, false, false);
                return true;
            } else if (isUltraWideBackCamera() && f2 >= 1.0f) {
                switchCameraLens(z, false, false);
                return true;
            }
        }
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 701 && i != 700) {
            ModeProtocol.KeyEvent keyEvent2 = (ModeProtocol.KeyEvent) ModeCoordinatorImpl.getInstance().getAttachProtocol(239);
            if (keyEvent2 != null) {
                return keyEvent2.onKeyDown(i, keyEvent);
            }
            return false;
        } else if (!isUnInterruptable()) {
            this.mPendingScreenSlideKeyCode = 0;
            this.mActivity.handleScreenSlideKeyEvent(i, keyEvent);
            return true;
        } else if (i != 701 || !this.mActivity.getCameraIntentManager().isFromScreenSlide().booleanValue() || this.mActivity.isModeSwitched()) {
            this.mPendingScreenSlideKeyCode = i;
            Log.d(TAG, "pending screen slide: " + i + ", reason: " + getUnInterruptableReason());
            return false;
        } else {
            this.mActivity.moveTaskToBack(false);
            this.mActivity.overridePendingTransition(R.anim.anim_screen_slide_fade_in, R.anim.anim_screen_slide_fade_out);
            return true;
        }
    }

    @Override // com.android.camera.module.Module
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 82 || this.mActivity.startFromSecureKeyguard()) {
            if (i == 24 || i == 25 || i == 87 || i == 88) {
                if (handleVolumeKeyEvent(i == 24 || i == 88, false, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                    return true;
                }
            }
            ModeProtocol.KeyEvent keyEvent2 = (ModeProtocol.KeyEvent) ModeCoordinatorImpl.getInstance().getAttachProtocol(239);
            if (keyEvent2 != null) {
                return keyEvent2.onKeyUp(i, keyEvent);
            }
            return false;
        }
        openSettingActivity();
        return true;
    }

    @Override // com.android.camera.module.Module
    public void onLongPress(float f, float f2) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void onMeteringAreaChanged(int i, int i2) {
    }

    @Override // com.android.camera.module.Module
    public void onNewIntent() {
    }

    @Override // com.android.camera.module.Module
    public void onNewUriArrived(Uri uri, String str) {
    }

    @Override // com.android.camera.module.Module
    public void onOrientationChanged(int i, int i2, int i3) {
        if (i != -1) {
            this.mOrientation = i;
            EffectController.getInstance().setOrientation(Util.getShootOrientation(this.mActivity, this.mOrientation));
            checkActivityOrientation();
            if (this.mOrientationCompensation != i2) {
                this.mOrientationCompensation = i2;
            }
        }
    }

    @Override // com.android.camera.module.Module
    @CallSuper
    public void onPause() {
        Log.d(TAG, "onPause");
        this.mPaused = true;
        this.mPendingScreenSlideKeyCode = 0;
        this.mUpdateWorkThreadDisposable.dispose();
        Disposable disposable = this.mLensDirtyDetectDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.mLensDirtyDetectHintDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
            Camera camera = this.mActivity;
            if (camera != null) {
                camera.hideLensDirtyDetectedHint();
            }
        }
        this.mLensDirtyDetectEnable = false;
    }

    public void onPreviewLayoutChanged(Rect rect) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraMetaDataCallback
    @CallSuper
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        detectLensDirty(captureResult);
        if (this.isShowPreviewDebugInfo) {
            boolean z = true;
            if (isDoingAction() && this.mModuleIndex != 162) {
                z = false;
            }
            if (this.mModuleIndex == 166) {
                z = false;
            }
            showDebug(captureResult, z);
        }
        if (shouldCheckSatFallbackState()) {
            checkSatFallback(captureResult);
        }
    }

    @Override // com.android.camera.module.Module
    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewRelease() {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused()) {
            this.mActivity.finish();
        }
    }

    public void onPreviewSizeChanged(int i, int i2) {
    }

    @Override // com.android.camera.module.Module
    @CallSuper
    public void onResume() {
        Log.d(TAG, "onResume");
        this.mPaused = false;
    }

    @Override // com.android.camera.module.Module
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(Util.KEY_KILLED_MODULE_INDEX, ModuleManager.getActiveModuleIndex());
    }

    @Override // com.android.camera.module.Module
    public boolean onScale(float f, float f2, float f3) {
        Log.d(TAG, "onScale(): scale = " + f3);
        if (f3 == 0.0f) {
            Log.d(TAG, "onScale(): scale illegal 0.0");
            return true;
        }
        if (isZoomEnabled()) {
            this.mZoomScaled += (f3 - 1.0f) / 4.0f;
            float min = Math.min(this.mMaxZoomRatio, 10.0f);
            if (HybridZoomingSystem.IS_4_OR_MORE_SAT) {
                float f4 = this.mZoomRatio;
                min = f4 < 1.0f ? Math.min(this.mMaxZoomRatio, HybridZoomingSystem.getTeleMinZoomRatio()) : f4 < 5.0f ? Math.min(this.mMaxZoomRatio, HybridZoomingSystem.getUltraTeleMinZoomRatio()) : f4 < 10.0f ? Math.min(this.mMaxZoomRatio, 10.0f) : Math.min(this.mMaxZoomRatio, 30.0f);
            } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mZoomRatio < 1.0f) {
                min = Math.min(this.mMaxZoomRatio, HybridZoomingSystem.getTeleMinZoomRatio());
            }
            float f5 = min * this.mZoomScaled;
            Log.d(TAG, "onScale(): delta = " + f5 + ", mZoomRatio = " + this.mZoomRatio);
            if (Math.abs(f5) >= 0.01f && onZoomingActionUpdate(this.mZoomRatio + f5, 2)) {
                this.mZoomScaled = 0.0f;
                return true;
            }
        }
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean onScaleBegin(float f, float f2) {
        this.mZoomScaled = 0.0f;
        updateSATZooming(true);
        onZoomingActionStart(2);
        return true;
    }

    @Override // com.android.camera.module.Module
    public void onScaleEnd() {
        Log.d(TAG, "onScaleEnd()");
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isZoomPanelVisible()) {
            updateSATZooming(false);
        }
        onZoomingActionEnd(2);
    }

    public void onSharedPreferenceChanged() {
    }

    public void onShineChanged(int i) {
    }

    @Override // com.android.camera.module.Module
    public void onSingleTapUp(int i, int i2, boolean z) {
    }

    @Override // com.android.camera.module.Module
    public void onStop() {
    }

    @Override // com.android.camera.module.Module
    public boolean onSurfaceTexturePending(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
        return false;
    }

    @Override // com.android.camera.module.Module
    public void onSurfaceTextureReleased() {
    }

    @Override // com.android.camera.module.Module
    public void onSurfaceTextureUpdated(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.LensProtocol
    public void onSwitchLens(boolean z, boolean z2) {
        switchCameraLens(z, true, z2);
    }

    /* access modifiers changed from: protected */
    public void onThermalConstrained() {
    }

    @Override // com.android.camera.module.Module
    public void onUserInteraction() {
    }

    @Override // com.android.camera.module.Module
    public void onWindowFocusChanged(boolean z) {
        if (z) {
            this.mIgnoreFocusChanged = false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public void onZoomRatioChanged(float f, int i) {
        onZoomingActionUpdate(f, i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public void onZoomSwitchCamera() {
        if (isAlive() && CameraSettings.isSupportedOpticalZoom() && CameraSettings.isZoomByCameraSwitchingSupported()) {
            this.mActivity.getCameraScreenNail().disableSwitchAnimationOnce();
        }
    }

    public void onZoomingActionEnd(int i) {
    }

    public void onZoomingActionStart(int i) {
    }

    /* access modifiers changed from: protected */
    public boolean onZoomingActionUpdate(float f, int i) {
        if (!isDeviceAlive() || limitZoomByAIWatermark(f)) {
            return false;
        }
        String simpleName = getClass().getSimpleName();
        Log.d(simpleName, "onZoomingActionUpdate(): newValue = " + f + ", minValue = " + this.mMinZoomRatio + ", maxValue = " + this.mMaxZoomRatio);
        float f2 = this.mZoomRatio;
        float clamp = HybridZoomingSystem.clamp(f, this.mMinZoomRatio, this.mMaxZoomRatio);
        if (f2 == clamp) {
            if (((double) Math.abs(clamp - HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR)) < 0.001d) {
                this.mZoomScaled = 0.0f;
            }
            return false;
        }
        String simpleName2 = getClass().getSimpleName();
        Log.d(simpleName2, "onZoomingActionUpdate(): changed from " + f2 + " to " + clamp);
        ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.updateCurrentZoomRatio(clamp);
        }
        setZoomRatio(clamp);
        if (onInterceptZoomingEvent(f2, clamp, i)) {
            return false;
        }
        if (f2 <= 1.0f || clamp <= 1.0f) {
            updatePreferenceTrampoline(11, 30, 34, 42, 20);
            if (c.isSupportedOpticalZoom()) {
                updateUltraWideCapability(clamp);
            }
        }
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            updatePreferenceInWorkThread(24, 46, 47);
        } else {
            updatePreferenceInWorkThread(24);
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (dualController != null && dualController.isButtonVisible()) {
            dualController.updateSlideAndZoomRatio(i);
        }
        if (i != 0) {
            topAlert.alertUpdateValue(1, HybridZoomingSystem.toDecimal(this.mZoomRatio) + "X");
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void openCamera() {
    }

    /* access modifiers changed from: protected */
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.mActivity, CameraPreferenceActivity.class);
        intent.putExtra("from_where", this.mModuleIndex);
        intent.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_camera_settings_category));
        int intentType = DataRepository.dataItemGlobal().getIntentType();
        intent.putExtra(CameraPreferenceFragment.INTENT_TYPE, intentType);
        if (intentType == 2) {
            intent.putExtra(CameraPreferenceFragment.INTENT_VIDEO_QUALITY, DataRepository.dataItemGlobal().getIntentVideoQuality());
        }
        if (this.mActivity.startFromKeyguard()) {
            intent.putExtra("StartActivityWhenLocked", true);
        }
        this.mActivity.startActivity(intent);
        this.mActivity.setJumpFlag(2);
        CameraStatUtils.trackGotoSettings(this.mModuleIndex);
    }

    /* access modifiers changed from: protected */
    public void performKeyClicked(int i, String str, int i2, boolean z) {
    }

    /* access modifiers changed from: protected */
    public final void playCameraSound(int i) {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && !isNeedMute() && CameraSettings.isCameraSoundOpen()) {
            this.mActivity.playCameraSound(i);
        }
    }

    public void playVideoSound(boolean z) {
    }

    @Override // com.android.camera.module.Module
    public void preTransferOrientation(int i, int i2) {
        if (i == -1) {
            i = 0;
        }
        this.mOrientation = i;
        this.mOrientationCompensation = i2;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module
    public void registerProtocol() {
        Log.d(TAG, "registerProtocol");
        ModeCoordinatorImpl.getInstance().attachProtocol(170, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(200, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(2560, this);
    }

    @Override // com.android.camera.module.Module
    public void requestRender() {
    }

    public void resetAiSceneInDocumentModeOn() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void resetEvValue() {
        if (isDeviceAlive()) {
            this.mCamera2Device.setExposureCompensation(0);
            this.mCamera2Device.setAWBLock(false);
            CameraSettings.resetExposure();
            updatePreferenceInWorkThread(new int[0]);
        }
    }

    @CallSuper
    public void resetMutexModeManually() {
        this.mMutexModePicker.resetMutexMode();
    }

    /* access modifiers changed from: protected */
    public void resetScreenOn() {
        Handler handler;
        if (!this.mActivity.isActivityPaused() && (handler = this.mHandler) != null) {
            handler.removeMessages(17);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(52);
        }
    }

    public final void restartModule() {
        if (!this.mActivity.isActivityPaused()) {
            this.mActivity.onModeSelected(StartControl.create(this.mModuleIndex).setViewConfigType(3).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void restoreBottom() {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack != null) {
            backStack.handleBackStackFromShutter();
        }
        if (baseDelegate.getActiveFragment(R.id.bottom_action) != 241) {
            baseDelegate.delegateEvent(7);
        }
    }

    /* access modifiers changed from: protected */
    public void restoreOuterAudio() {
        if (this.mAudioController == null) {
            this.mAudioController = new AudioController(this.mActivity.getApplicationContext());
        }
        this.mAudioController.restoreAudio();
    }

    /* access modifiers changed from: protected */
    public boolean retryOnceIfCameraError(Handler handler) {
        if (DataRepository.dataItemGlobal().isRetriedIfCameraError() || this.mActivity.isActivityPaused()) {
            return false;
        }
        Log.e(TAG, "onCameraException: retry1");
        DataRepository.dataItemGlobal().setRetriedIfCameraError(true);
        handler.post(new Runnable() {
            /* class com.android.camera.module.BaseModule.AnonymousClass2 */

            public void run() {
                BaseModule baseModule = BaseModule.this;
                baseModule.mActivity.onModeSelected(StartControl.create(baseModule.mModuleIndex).setViewConfigType(1).setNeedBlurAnimation(false).setNeedReConfigureCamera(true));
            }
        });
        return true;
    }

    /* access modifiers changed from: protected */
    public void sendOpenFailMessage() {
    }

    /* access modifiers changed from: protected */
    public void setAWBMode(String str) {
        if (isDeviceAlive()) {
            this.mCamera2Device.setAWBLock(false);
            if (str.equals("manual")) {
                if (c.isMTKPlatform()) {
                    this.mCamera2Device.setAWBMode(10);
                } else {
                    this.mCamera2Device.setAWBMode(0);
                }
                this.mCamera2Device.setCustomAWB(CameraSettings.getCustomWB());
                return;
            }
            int parseInt = Util.parseInt(str, 1);
            if (Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedAWBModes())) {
                this.mCamera2Device.setAWBMode(parseInt);
            } else {
                this.mCamera2Device.setAWBMode(1);
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void setActivity(Camera camera) {
        this.mActivity = camera;
    }

    public void setCameraId(int i) {
        this.mBogusCameraId = i;
    }

    /* access modifiers changed from: protected */
    public void setCameraState(int i) {
        Log.d(TAG, "setCameraState: " + i);
        this.mCameraState = i;
    }

    /* access modifiers changed from: protected */
    public void setColorEffect(String str) {
        if (isDeviceAlive()) {
            int parseInt = Util.parseInt(str, 0);
            if (Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedColorEffects())) {
                Log.d(TAG, "colorEffect: " + str);
                this.mCamera2Device.setColorEffect(parseInt);
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void setDeparted() {
        Log.d(TAG, "setDeparted");
        Disposable disposable = this.mUpdateWorkThreadDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.mLensDirtyDetectDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.mLensDirtyDetectHintDisposable;
        if (disposable3 != null) {
            disposable3.dispose();
            Camera camera = this.mActivity;
            if (camera != null) {
                camera.hideLensDirtyDetectedHint();
            }
        }
        this.mLensDirtyDetectEnable = false;
        this.mIsDeparted.set(true);
        this.mIsFrameAvailable.set(false);
    }

    public void setDisplayRectAndUIStyle(Rect rect, int i) {
        this.mDisplayRect = rect;
        this.mUIStyle = i;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public void setEvValue() {
        if (isAlive()) {
            int i = this.mEvState;
            if (i == 2) {
                this.mCamera2Device.setAWBLock(false);
            } else if (i == 1 || i == 3) {
                this.mCamera2Device.setExposureCompensation(this.mEvValue);
                if (this.mEvState != 1) {
                    return;
                }
                if (this.mEvValue != 0) {
                    this.mCamera2Device.setAWBLock(true);
                } else {
                    this.mCamera2Device.setAWBLock(false);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setFlashMode(String str) {
        if (isDeviceAlive()) {
            Log.d(TAG, "flashMode: " + str);
            int parseInt = Util.parseInt(str, 0);
            if ((ThermalDetector.getInstance().thermalCloseFlash() && DataRepository.dataItemConfig().getComponentFlash().isHardwareSupported()) || (isFrontCamera() && this.mActivity.isScreenSlideOff())) {
                parseInt = 0;
            }
            this.mUseLegacyFlashMode = DataRepository.dataItemFeature().vm();
            this.mCamera2Device.setUseLegacyFlashMode(this.mUseLegacyFlashMode);
            this.mCamera2Device.setFlashMode(parseInt);
        }
    }

    /* access modifiers changed from: protected */
    public void setFocusMode(String str) {
        if (isDeviceAlive()) {
            int convertToFocusMode = AutoFocus.convertToFocusMode(str);
            if (Util.isSupported(convertToFocusMode, this.mCameraCapabilities.getSupportedFocusModes())) {
                this.mCamera2Device.setFocusMode(convertToFocusMode);
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void setFrameAvailable(boolean z) {
        this.mIsFrameAvailable.set(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public void setMaxZoomRatio(float f) {
        Log.d(TAG, "setMaxZoomRatio(): " + f);
        this.mMaxZoomRatio = f;
        this.mMainProtocol.updateZoomRatio(this.mMinZoomRatio, this.mMaxZoomRatio);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public void setMinZoomRatio(float f) {
        Log.d(TAG, "setMinZoomRatio(): " + f);
        this.mMinZoomRatio = f;
    }

    public void setModuleIndex(int i) {
        this.mModuleIndex = i;
    }

    @Override // com.android.camera.module.Module
    public void setRestoring(boolean z) {
        this.mRestoring = z;
    }

    public void setThermalLevel(int i) {
        this.mThermalLevel = i;
    }

    /* access modifiers changed from: protected */
    public void setTriggerMode(int i) {
        this.mTriggerMode = i;
    }

    /* access modifiers changed from: protected */
    public void setVideoMaxZoomRatioByTele() {
        String bi = DataRepository.dataItemFeature().bi();
        if (!Camera2DataContainer.getInstance().hasTeleCamera() || ((!DataRepository.dataItemFeature().tk() && !DataRepository.dataItemFeature().uk()) || bi == null || !isTeleSupportVideoQuality())) {
            setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
        } else {
            setMaxZoomRatio((isUltraTeleCamera() ? HybridZoomingSystem.getUltraTeleMinZoomRatio() : HybridZoomingSystem.getTeleMinZoomRatio()) * Float.parseFloat(bi));
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public void setZoomRatio(float f) {
        Log.d(TAG, "setZoomRatio(): " + f);
        this.mZoomRatio = f;
        CameraSettings.writeZoom(f);
        HybridZoomingSystem.setZoomRatioHistory(this.mModuleIndex, String.valueOf(f));
    }

    /* access modifiers changed from: protected */
    public boolean shouldCheckSatFallbackState() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean shouldReleaseLater() {
        return false;
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
        this.mDialog = RotateDialogController.showSystemAlertDialog(camera, camera.getString(i), this.mActivity.getString(i2), this.mActivity.getString(17039370), null, null, null, null, null);
    }

    public void showLensDirtyTip() {
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if (cameraCapabilities == null || cameraCapabilities.getMiAlgoASDVersion() < 3.0f) {
            Disposable disposable = this.mLensDirtyDetectDisposable;
            if (disposable != null) {
                disposable.dispose();
                this.mLensDirtyDetectDisposable = null;
            } else {
                return;
            }
        } else if (this.mLensDirtyDetectEnable) {
            this.mLensDirtyDetectEnable = false;
        } else {
            return;
        }
        Completable.complete().observeOn(CameraSchedulers.sCameraSetupScheduler).doOnComplete(new ActionUpdateLensDirtyDetect(this, false)).subscribe();
        if (CameraSettings.shouldShowLensDirtyDetectHint()) {
            this.mActivity.showLensDirtyDetectedHint();
            this.mLensDirtyDetectHintDisposable = Completable.complete().delay(8000, TimeUnit.MILLISECONDS, CameraSchedulers.sCameraSetupScheduler).doOnComplete(new ActionHideLensDirtyDetectHint(this)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeListManager
    public void showMoreMode(boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        boolean isMoreModeShowing = isMoreModeShowing(z);
        Log.d(TAG, "showMore isShowing = " + isMoreModeShowing + ",configChanges = " + configChanges);
        if (configChanges != null && !isMoreModeShowing(z)) {
            configChanges.configModeMore(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial
    public void showOrHideChip(boolean z) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial
    public void showQRCodeResult() {
    }

    /* access modifiers changed from: protected */
    public void silenceOuterAudio() {
        if (this.mAudioController == null) {
            this.mAudioController = new AudioController(this.mActivity.getApplicationContext());
        }
        this.mAudioController.silenceAudio();
    }

    /* access modifiers changed from: protected */
    public void switchCameraLens(boolean z, boolean z2, boolean z3) {
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        int currentMode = dataItemGlobal.getCurrentMode();
        dataItemGlobal.setCameraId(0);
        this.mActivity.onModeSelected(StartControl.create(currentMode).setStartDelay(0).setResetType(z3 ? 7 : 5).setViewConfigType(2).setNeedBlurAnimation(z2));
    }

    public final void thermalConstrained() {
        Handler handler;
        if (!this.mActivity.isActivityPaused() && (handler = this.mHandler) != null) {
            handler.post(new xa(this));
        }
    }

    @NonNull
    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode() + ": mid = " + this.mModuleIndex + ", cid = " + this.mActualCameraId + ", created = " + isCreated() + ", departed = " + isDeparted();
    }

    /* access modifiers changed from: protected */
    public void trackGeneralInfo(Map map, int i, boolean z, BeautyValues beautyValues, boolean z2, int i2) {
        HashMap hashMap = map == null ? new HashMap() : map;
        hashMap.put(MistatsConstants.Manual.PARAM_EV, Integer.valueOf(this.mEvValueForTrack));
        hashMap.put(MistatsConstants.BaseEvent.COUNT, String.valueOf(i));
        hashMap.put(MistatsConstants.AlgoAttr.PARAM_AI_SCENE, Integer.valueOf(i2));
        CameraStatUtils.trackGeneralInfo(hashMap, z, z2, this.mModuleIndex, getTriggerMode(), isFrontCamera(), getActualCameraId(), beautyValues, this.mMutexModePicker, this.mFlashAutoModeState);
    }

    /* access modifiers changed from: protected */
    public void trackModeCustomInfo(Map map, boolean z, BeautyValues beautyValues, int i, boolean z2) {
    }

    /* access modifiers changed from: protected */
    public void trackPictureTaken(MistatsWrapper.PictureTakenParameter pictureTakenParameter) {
        if (pictureTakenParameter != null) {
            CameraStatUtils.trackMacroModeTaken(this.mModuleIndex);
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.PARAM_PHOTO_RATIO_MOVIE, CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex) ? "on" : "off");
            int i = this.mModuleIndex;
            if (i == 163) {
                CameraStatUtils.trackMoonMode(hashMap, pictureTakenParameter.isEnteringMoon, pictureTakenParameter.isSelectMoonMode);
                CameraStatUtils.trackSuperNightInCaptureMode(hashMap, pictureTakenParameter.isSuperNightInCaptureMode);
            } else if (i == 171) {
                BeautyValues beautyValues = pictureTakenParameter.beautyValues;
                if (beautyValues != null) {
                    hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, Integer.valueOf(beautyValues.mBeautySkinSmooth));
                }
                CameraStatUtils.trackCapturePortrait(hashMap);
            } else if (i == 173) {
                CameraStatUtils.trackCaptureSuperNight(hashMap);
            }
            trackModeCustomInfo(hashMap, pictureTakenParameter.burst, pictureTakenParameter.beautyValues, pictureTakenParameter.takenNum, pictureTakenParameter.isNearRangeMode);
        }
    }

    public void tryRemoveCountDownMessage() {
    }

    @Override // com.android.camera.module.Module
    public void unRegisterModulePersistProtocol() {
        Log.d(TAG, "unRegisterModulePersist");
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module
    public void unRegisterProtocol() {
        Log.d(TAG, "unRegisterProtocol");
        ModeCoordinatorImpl.getInstance().detachProtocol(170, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(200, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(2560, this);
    }

    /* access modifiers changed from: protected */
    public void updateAntiBanding(String str) {
        if (isDeviceAlive()) {
            int parseInt = Util.parseInt(str, 3);
            if (Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedAntiBandingModes())) {
                Log.d(TAG, "antiBanding: " + str);
                this.mCamera2Device.setAntiBanding(parseInt);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateBackSoftLightPreference() {
        setFlashMode("0");
    }

    /* access modifiers changed from: protected */
    public void updateCameraScreenNailSize(int i, int i2) {
        String simpleName = getClass().getSimpleName();
        Log.d(simpleName, "updateCameraScreenNailSize: " + i + "x" + i2);
        this.mActivity.getCameraScreenNail().setPreviewSize(i, i2);
        this.mMainProtocol.setPreviewSize(i, i2);
    }

    /* access modifiers changed from: protected */
    public final void updateExposureMeteringMode() {
        if (isDeviceAlive()) {
            this.mCamera2Device.setExposureMeteringMode(CameraSettings.getExposureMeteringMode());
        }
    }

    /* access modifiers changed from: protected */
    public void updateFlashPreference() {
    }

    /* access modifiers changed from: protected */
    public void updateHDRPreference() {
    }

    public void updateLensDirtyDetect(boolean z) {
        Disposable disposable;
        if (this.mCamera2Device == null) {
            Log.e(TAG, "updateLensDirtyDetect: mCamera2Device is null...");
            return;
        }
        boolean isLensDirtyDetectEnabled = CameraSettings.isLensDirtyDetectEnabled();
        if (!isLensDirtyDetectEnabled && (disposable = this.mLensDirtyDetectDisposable) != null) {
            disposable.dispose();
            this.mLensDirtyDetectDisposable = null;
        }
        this.mCamera2Device.setLensDirtyDetect(isLensDirtyDetectEnabled);
        if (z && isFrameAvailable() && !isDoingAction() && !isRecording()) {
            this.mCamera2Device.resumePreview();
        }
    }

    /* access modifiers changed from: protected */
    public void updateModuleRelated() {
        this.mCamera2Device.setModuleParameter(this.mModuleIndex, this.mBogusCameraId);
    }

    @WorkerThread
    public final void updatePreferenceInWorkThread(@UpdateConstant.UpdateType int... iArr) {
        Disposable disposable = this.mUpdateWorkThreadDisposable;
        if (disposable == null || disposable.isDisposed()) {
            Log.d(TAG, "the mUpdateWorkThreadDisposable is not available." + this.mUpdateWorkThreadDisposable + ". " + this);
            return;
        }
        Log.e(TAG, "types:" + join(iArr) + ", " + this);
        this.mUpdateWorkThreadEmitter.onNext(iArr);
    }

    public final void updatePreferenceTrampoline(@UpdateConstant.UpdateType int... iArr) {
        consumePreference(iArr);
    }

    public void updatePreviewSurface() {
    }

    public void updateSATZooming(boolean z) {
    }

    public void updateScreenSlide(boolean z) {
        Camera2Proxy camera2Proxy;
        if (z && (camera2Proxy = this.mCamera2Device) != null) {
            camera2Proxy.setAELock(false);
            int i = this.mModuleIndex;
            if (i == 163 || i == 171 || i == 165 || i == 188) {
                updatePreferenceInWorkThread(10, 36);
            } else {
                updatePreferenceInWorkThread(10);
            }
        }
    }

    public void updateThermalLevel() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setThermalLevel(this.mThermalLevel);
        }
    }

    /* access modifiers changed from: protected */
    public void updateTipMessage(int i, @StringRes int i2, int i3) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.showTips(i, i2, i3);
        }
    }

    /* access modifiers changed from: protected */
    public void updateZoomRatioToggleButtonState(boolean z) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.setRecordingOrPausing(z);
            if (z) {
                dualController.hideZoomButton();
            } else {
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                    dualController.showZoomButton();
                }
            }
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert == null) {
            return;
        }
        if (z) {
            topAlert.alertUpdateValue(0, null);
        } else if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            topAlert.clearAlertStatus();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public boolean zoomIn(float f) {
        if (f <= 0.0f) {
            return false;
        }
        return addZoom(f);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ZoomProtocol
    public boolean zoomOut(float f) {
        if (f <= 0.0f) {
            return false;
        }
        return addZoom(-f);
    }
}
