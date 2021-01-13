package com.android.camera.module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.location.Location;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.Camera;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.EncodingQuality;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.LocationManager;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.Util;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.CameraClickObservableImpl;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera.zoommap.ZoomMapController;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CaptureResultParser;
import com.android.gallery3d.ui.GLCanvas;
import com.android.zxing.PreviewDecodeManager;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.rx.CameraSchedulers;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;
import com.xiaomi.stat.d;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SuperMoonModule extends BaseModule implements FocusManager2.Listener, ModeProtocol.CameraAction, ModeProtocol.TopConfigProtocol, Camera2Proxy.FocusCallback, Camera2Proxy.PictureCallback, Camera2Proxy.CameraMetaDataCallback, Camera2Proxy.CameraPreviewCallback {
    private static final long CAPTURE_DURATION_THRESHOLD = 12000;
    private static final String TAG = "SuperMoonModule";
    private float[] curGyroscope;
    private float[] lastGyroscope;
    private boolean m3ALocked;
    private int mAFEndLogTimes;
    private String mAlgorithmName;
    private float[] mApertures;
    private boolean mBlockQuickShot = (!CameraSettings.isCameraQuickShotEnable());
    private Intent mBroadcastIntent;
    private final Object mCameraDeviceLock = new Object();
    private long mCaptureStartTime;
    private boolean mConfigRawStream;
    private Disposable mCountdownDisposable;
    private int mCurrentAiScene;
    private boolean mEnableParallelSession;
    private boolean mEnableShot2Gallery;
    private boolean mEnabledPreviewThumbnail;
    private boolean mFirstCreateCapture;
    private float[] mFocalLengths;
    private FocusManager2 mFocusManager;
    private int mIsShowLyingDirectHintStatus = -1;
    private boolean mIsStartCount;
    private int mJpegRotation;
    private long mLastCaptureTime;
    private Location mLocation;
    private boolean mLongPressedAutoFocus;
    private WatermarkItem mMajorItem;
    private WatermarkItem mMinorItem;
    private boolean mMultiSnapStatus = false;
    private long mOnResumeTime;
    private int mOperatingMode;
    private boolean mParallelSessionConfigured = false;
    private final Object mParallelSessionLock = new Object();
    private boolean mQuickShotAnimateEnable = false;
    private SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.SuperMoonModule.AnonymousClass1 */
        private ModeProtocol.TopAlert mTopAlert;

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return SuperMoonModule.this.isAlive() && SuperMoonModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
            if (!SuperMoonModule.this.mPaused && CameraSettings.isEdgePhotoEnable()) {
                SuperMoonModule.this.mActivity.getEdgeShutterView().onDeviceMoving();
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!SuperMoonModule.this.mPaused && SuperMoonModule.this.mFocusManager != null && !SuperMoonModule.this.mMultiSnapStatus && !SuperMoonModule.this.is3ALocked() && !SuperMoonModule.this.mMainProtocol.isEvAdjusted(true)) {
                SuperMoonModule.this.mFocusManager.onDeviceKeepMoving(d2);
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepStable() {
        }

        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:11:0x0044 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v0, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r6v1 */
        /* JADX WARN: Type inference failed for: r6v7 */
        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceLieChanged(boolean z) {
            if (!SuperMoonModule.this.mPaused) {
                int i = SuperMoonModule.this.mIsShowLyingDirectHintStatus;
                SuperMoonModule superMoonModule = SuperMoonModule.this;
                int i2 = superMoonModule.mOrientationCompensation;
                int i3 = z == true ? 1 : 0;
                int i4 = z == true ? 1 : 0;
                if (i != i3 + i2) {
                    superMoonModule.mIsShowLyingDirectHintStatus = i2 + z;
                    SuperMoonModule.this.mHandler.removeMessages(58);
                    if (this.mTopAlert == null) {
                        this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    }
                    ModeProtocol.TopAlert topAlert = this.mTopAlert;
                    if (topAlert != null ? topAlert.isContainAlertRecommendTip(R.string.dirty_tip_toast, R.string.pic_flaw_blink_one, R.string.pic_flaw_blink_more, R.string.pic_flaw_cover) : false) {
                        z = 0;
                    }
                    if (z != 0) {
                        SuperMoonModule superMoonModule2 = SuperMoonModule.this;
                        Handler handler = superMoonModule2.mHandler;
                        handler.sendMessageDelayed(handler.obtainMessage(58, 1, superMoonModule2.mOrientationCompensation), 400);
                        SuperMoonModule superMoonModule3 = SuperMoonModule.this;
                        Handler handler2 = superMoonModule3.mHandler;
                        handler2.sendMessageDelayed(handler2.obtainMessage(58, 0, superMoonModule3.mOrientationCompensation), 5000);
                        return;
                    }
                    SuperMoonModule superMoonModule4 = SuperMoonModule.this;
                    Handler handler3 = superMoonModule4.mHandler;
                    handler3.sendMessageDelayed(handler3.obtainMessage(58, 0, superMoonModule4.mOrientationCompensation), 500);
                }
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceOrientationChanged(float f, boolean z) {
            SuperMoonModule superMoonModule = SuperMoonModule.this;
            superMoonModule.mDeviceRotation = !z ? f : (float) superMoonModule.mOrientation;
            if (SuperMoonModule.this.getCameraState() != 3 || SuperMoonModule.this.isGradienterOn) {
                EffectController instance = EffectController.getInstance();
                SuperMoonModule superMoonModule2 = SuperMoonModule.this;
                instance.setDeviceRotation(z, Util.getShootRotation(superMoonModule2.mActivity, superMoonModule2.mDeviceRotation));
            }
            SuperMoonModule.this.mHandler.removeMessages(33);
            if (!SuperMoonModule.this.mPaused && !z && f != -1.0f) {
                int roundOrientation = Util.roundOrientation(Math.round(f), SuperMoonModule.this.mOrientation);
                SuperMoonModule.this.mHandler.obtainMessage(33, roundOrientation, (Util.getDisplayRotation(SuperMoonModule.this.mActivity) + roundOrientation) % 360).sendToTarget();
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceRotationChanged(float[] fArr) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            ModeProtocol.MagneticSensorDetect magneticSensorDetect;
            int type = sensorEvent.sensor.getType();
            if (type == 4) {
                SuperMoonModule superMoonModule = SuperMoonModule.this;
                superMoonModule.lastGyroscope = superMoonModule.curGyroscope;
                SuperMoonModule.this.curGyroscope = sensorEvent.values;
            } else if (type == 14 && (magneticSensorDetect = (ModeProtocol.MagneticSensorDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(2576)) != null) {
                magneticSensorDetect.onMagneticSensorChanged(sensorEvent);
            }
        }
    };
    private LocalParallelService.ServiceStatusListener mServiceStatusListener;
    private int mShootOrientation;
    private float mShootRotation;
    private long mShutterCallbackTime;
    private long mShutterLag;
    private volatile boolean mUltraWideAELocked;
    private boolean mVolumeLongPress = false;
    private volatile boolean mWaitSaveFinish;
    private ZoomMapController mZoomMapController;

    /* access modifiers changed from: private */
    public static class LocalParallelServiceStatusListener implements LocalParallelService.ServiceStatusListener {
        private final WeakReference<Camera2Proxy> mCameraDevice;
        private final WeakReference<SuperMoonModule> mSuperMoonModuleRef;

        LocalParallelServiceStatusListener(Camera2Proxy camera2Proxy, SuperMoonModule superMoonModule) {
            this.mCameraDevice = new WeakReference<>(camera2Proxy);
            this.mSuperMoonModuleRef = new WeakReference<>(superMoonModule);
        }

        @Override // com.android.camera.LocalParallelService.ServiceStatusListener
        public void onImagePostProcessEnd(ParallelTaskData parallelTaskData) {
            SuperMoonModule superMoonModule = this.mSuperMoonModuleRef.get();
            if (superMoonModule != null && parallelTaskData != null && parallelTaskData.isJpegDataReady() && DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode() && !DataRepository.dataItemFeature().hm()) {
                superMoonModule.onPictureTakenFinished(true);
            }
        }

        @Override // com.android.camera.LocalParallelService.ServiceStatusListener
        public void onImagePostProcessStart(ParallelTaskData parallelTaskData) {
            SuperMoonModule superMoonModule = this.mSuperMoonModuleRef.get();
            if (superMoonModule != null && 4 != parallelTaskData.getAlgoType()) {
                if (!DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode() || DataRepository.dataItemFeature().hm()) {
                    superMoonModule.onPictureTakenFinished(true);
                }
                PerformanceTracker.trackPictureCapture(1);
                Camera2Proxy camera2Proxy = this.mCameraDevice.get();
                if (camera2Proxy != null) {
                    camera2Proxy.onParallelImagePostProcStart();
                }
            }
        }
    }

    private static class MainHandler extends Handler {
        private WeakReference<SuperMoonModule> mModule;

        public MainHandler(SuperMoonModule superMoonModule, Looper looper) {
            super(looper);
            this.mModule = new WeakReference<>(superMoonModule);
        }

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void handleMessage(Message message) {
            Camera2Proxy camera2Proxy;
            SuperMoonModule superMoonModule = this.mModule.get();
            if (superMoonModule != null) {
                if (!superMoonModule.isCreated()) {
                    removeCallbacksAndMessages(null);
                } else if (superMoonModule.getActivity() != null) {
                    int i = message.what;
                    if (i == 2) {
                        superMoonModule.getWindow().clearFlags(128);
                    } else if (i == 4) {
                        superMoonModule.checkActivityOrientation();
                        if (SystemClock.uptimeMillis() - superMoonModule.mOnResumeTime < 5000) {
                            sendEmptyMessageDelayed(4, 100);
                        }
                    } else if (i == 17) {
                        removeMessages(17);
                        removeMessages(2);
                        superMoonModule.getWindow().addFlags(128);
                        sendEmptyMessageDelayed(2, (long) superMoonModule.getScreenDelay());
                    } else if (i == 31) {
                        superMoonModule.setOrientationParameter();
                    } else if (i == 33) {
                        superMoonModule.setOrientation(message.arg1, message.arg2);
                    } else if (i == 44) {
                        superMoonModule.restartModule();
                    } else if (i != 45) {
                        switch (i) {
                            case 9:
                                superMoonModule.mMainProtocol.initializeFocusView(superMoonModule);
                                return;
                            case 10:
                                break;
                            case 11:
                                return;
                            default:
                                switch (i) {
                                    case 50:
                                        Log.w(SuperMoonModule.TAG, "Oops, capture timeout later release timeout!");
                                        superMoonModule.onPictureTakenFinished(false);
                                        return;
                                    case 51:
                                        break;
                                    case 52:
                                        superMoonModule.onShutterButtonClick(superMoonModule.getTriggerMode());
                                        return;
                                    default:
                                        switch (i) {
                                            case 56:
                                                ModeProtocol.MainContentProtocol mainContentProtocol = superMoonModule.mMainProtocol;
                                                if (mainContentProtocol != null && mainContentProtocol.isFaceExists(1) && superMoonModule.mMainProtocol.isFocusViewVisible() && (camera2Proxy = superMoonModule.mCamera2Device) != null && 4 == camera2Proxy.getFocusMode()) {
                                                    superMoonModule.mMainProtocol.clearFocusView(7);
                                                    return;
                                                }
                                                return;
                                            case 57:
                                                PreviewDecodeManager.getInstance().reset();
                                                return;
                                            case 58:
                                                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                                                if (configChanges != null) {
                                                    int i2 = superMoonModule.mOrientationCompensation;
                                                    configChanges.configRotationChange(message.arg1, (360 - (i2 >= 0 ? i2 % 360 : (i2 % 360) + 360)) % 360);
                                                    return;
                                                }
                                                return;
                                            default:
                                                switch (i) {
                                                    case 60:
                                                        Log.d(SuperMoonModule.TAG, "fallback timeout");
                                                        superMoonModule.mIsSatFallback = 0;
                                                        superMoonModule.mFallbackProcessed = false;
                                                        superMoonModule.mLastSatFallbackRequestId = -1;
                                                        if (superMoonModule.mWaitingSnapshot && superMoonModule.getCameraState() == 1) {
                                                            superMoonModule.mWaitingSnapshot = false;
                                                            sendEmptyMessage(62);
                                                            return;
                                                        }
                                                        return;
                                                    case 61:
                                                        Log.d(SuperMoonModule.TAG, "wait save finish timeout");
                                                        superMoonModule.mWaitSaveFinish = false;
                                                        return;
                                                    case 62:
                                                        superMoonModule.onWaitingFocusFinished();
                                                        return;
                                                    default:
                                                        throw new RuntimeException("no consumer for this message: " + message.what);
                                                }
                                        }
                                }
                        }
                        if (!superMoonModule.mActivity.isActivityPaused()) {
                            superMoonModule.mOpenCameraFail = true;
                            superMoonModule.onCameraException();
                        }
                    } else {
                        superMoonModule.setActivity(null);
                    }
                }
            }
        }
    }

    private void beginParallelProcess(ParallelTaskData parallelTaskData, boolean z) {
        String str = TAG;
        Log.i(str, "algo begin: " + parallelTaskData.getSavePath() + " | " + Thread.currentThread().getName());
        if (this.mServiceStatusListener == null) {
            this.mServiceStatusListener = new LocalParallelServiceStatusListener(this.mCamera2Device, this);
            AlgoConnector.getInstance().setServiceStatusListener(this.mServiceStatusListener);
        }
    }

    private void blockSnapClickUntilSaveFinish() {
        Log.i(TAG, "blockSnapClickUntilFinish");
        this.mWaitSaveFinish = true;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(61, 5000);
        }
    }

    private boolean checkShutterCondition() {
        ModeProtocol.DualController dualController;
        if (isBlockSnap() || isIgnoreTouchEvent()) {
            String str = TAG;
            Log.w(str, "checkShutterCondition: blockSnap=" + isBlockSnap() + " ignoreTouchEvent=" + isIgnoreTouchEvent());
            return false;
        } else if (Storage.isLowStorageAtLastPoint()) {
            Log.w(TAG, "checkShutterCondition: low storage");
            return false;
        } else if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
            Log.w(TAG, "checkShutterCondition: screen is slide off");
            return false;
        } else if (!isIn3OrMoreSatMode() || (dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182)) == null || dualController.isZoomSliderViewIdle()) {
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack == null) {
                return true;
            }
            backStack.handleBackStackFromShutter();
            return true;
        } else {
            Log.w(TAG, "checkShutterCondition: 3SAT zooming");
            return false;
        }
    }

    private int clampQuality(int i) {
        return DataRepository.dataItemRunning().getComponentUltraPixel().isRear108MPSwitchOn() ? Util.clamp(i, 0, 90) : i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void configParallelSession() {
        GraphDescriptorBean graphDescriptorBean;
        int cameraCombinationMode = CameraDeviceUtil.getCameraCombinationMode(Camera2DataContainer.getInstance().getRoleIdByActualId(this.mActualCameraId));
        if (isPortraitMode()) {
            int i = ((!isDualFrontCamera() || DataRepository.dataItemFeature().xi()) && !isDualCamera() && !isBokehUltraWideBackCamera()) ? 1 : 2;
            String str = TAG;
            Log.d(str, "configParallelSession: inputStreamNum = " + i);
            graphDescriptorBean = new GraphDescriptorBean(32770, i, true, cameraCombinationMode);
        } else if (this.mModuleIndex == 167) {
            graphDescriptorBean = new GraphDescriptorBean(32771, 1, true, cameraCombinationMode);
        } else if (DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode()) {
            graphDescriptorBean = new GraphDescriptorBean(32778, 1, true, cameraCombinationMode);
        } else {
            if (cameraCombinationMode == 0) {
                cameraCombinationMode = 513;
            }
            graphDescriptorBean = new GraphDescriptorBean(0, 1, true, cameraCombinationMode);
        }
        String str2 = TAG;
        Log.d(str2, "configParallelSession: pictureSize = " + this.mPictureSize);
        String str3 = TAG;
        Log.d(str3, "configParallelSession: outputSize = " + this.mOutputPictureSize);
        String str4 = TAG;
        Log.d(str4, "configParallelSession: outputFormat = " + this.mOutputPictureFormat);
        CameraSize cameraSize = this.mPictureSize;
        BufferFormat bufferFormat = new BufferFormat(cameraSize.width, cameraSize.height, 35, graphDescriptorBean);
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder(true);
        localBinder.configCaptureSession(bufferFormat);
        localBinder.setImageSaver(this.mActivity.getImageSaver());
        CameraSize cameraSize2 = this.mOutputPictureSize;
        localBinder.setOutputPictureSpec(cameraSize2.width, cameraSize2.height, this.mOutputPictureFormat);
        localBinder.setSRRequireReprocess(DataRepository.dataItemFeature().isSRRequireReprocess());
        synchronized (this.mParallelSessionLock) {
            this.mParallelSessionConfigured = true;
        }
    }

    private boolean enablePreviewAsThumbnail() {
        return this.mEnableParallelSession;
    }

    private int getCountDownTimes(int i) {
        Intent intent = this.mBroadcastIntent;
        int timerDurationSeconds = intent != null ? CameraIntentManager.getInstance(intent).getTimerDurationSeconds() : this.mActivity.getCameraIntentManager().getTimerDurationSeconds();
        if (timerDurationSeconds != -1) {
            Intent intent2 = this.mBroadcastIntent;
            if (intent2 != null) {
                intent2.removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            } else {
                this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            }
            if (timerDurationSeconds != 0) {
                return timerDurationSeconds != 5 ? 3 : 5;
            }
            return 0;
        } else if (i != 100 || !CameraSettings.isHandGestureOpen()) {
            return CameraSettings.getCountDownTimes();
        } else {
            int countDownTimes = CameraSettings.getCountDownTimes();
            if (countDownTimes != 0) {
                return countDownTimes;
            }
            return 3;
        }
    }

    private DeviceWatermarkParam getDeviceWaterMarkParam() {
        float f;
        float f2;
        float f3;
        float resourceFloat;
        float resourceFloat2;
        float resourceFloat3;
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        boolean isFrontCameraWaterMarkOpen = CameraSettings.isFrontCameraWaterMarkOpen();
        if (isDualCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_y_ratio, 0.0f);
        } else if (isFrontCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.global_frontcamera_watermark_size_ratio, 0.0f);
            if (!Util.isGlobalVersion() || resourceFloat == 0.0f) {
                resourceFloat = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_size_ratio, 0.0f);
            }
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_y_ratio, 0.0f);
        } else {
            f3 = 0.0f;
            f2 = 0.0f;
            f = 0.0f;
            return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f3, f2, f);
        }
        f = resourceFloat3;
        f3 = resourceFloat;
        f2 = resourceFloat2;
        return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f3, f2, f);
    }

    private PictureInfo getPictureInfo() {
        PictureInfo opMode = new PictureInfo().setFrontMirror(isFrontMirror()).setSensorType(isFrontCamera()).setBokehFrontCamera(isPictureUseDualFrontCamera()).setHdrType(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex)).setOpMode(getOperatingMode());
        opMode.setAiEnabled(false);
        opMode.setAiType(0);
        int i = this.mModuleIndex;
        if (i == 166) {
            opMode.setPanorama(true);
        } else if (i == 167) {
            opMode.setProfession(true);
        }
        opMode.setShotBurst(this.mMultiSnapStatus);
        opMode.setFilter(CameraSettings.getShaderEffect());
        CameraSettings.getCameraLensType(this.mModuleIndex);
        if (isFrontCamera()) {
            opMode.setLensType("front");
        } else {
            int actualCameraId = getActualCameraId();
            if (actualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                opMode.setLensType(actualCameraId + "_RearUltra");
            } else if (actualCameraId == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
                opMode.setLensType(actualCameraId + "_RearMacro");
            } else if (actualCameraId == Camera2DataContainer.getInstance().getAuxCameraId()) {
                opMode.setLensType(actualCameraId + PictureInfo.SENSOR_TYPE_REAR_TELE);
            } else if (actualCameraId == Camera2DataContainer.getInstance().getUltraTeleCameraId()) {
                opMode.setLensType(actualCameraId + "_RearTele4x");
            } else if (actualCameraId == Camera2DataContainer.getInstance().getMainBackCameraId()) {
                opMode.setLensType(actualCameraId + "_RearWide");
            } else if (actualCameraId == Camera2DataContainer.getInstance().getSATCameraId()) {
                opMode.setLensType(String.valueOf(actualCameraId) + "_" + PictureInfo.SENSOR_TYPE_REAR);
            }
        }
        float[] fArr = this.mFocalLengths;
        if (fArr != null && fArr.length > 0) {
            opMode.setLensfocal(fArr[0]);
        }
        float[] fArr2 = this.mApertures;
        if (fArr2 != null && fArr2.length > 0) {
            opMode.setLensApertues(fArr2[0]);
        }
        String retriveFaceInfo = DebugInfoUtil.getRetriveFaceInfo(this.mMainProtocol.getViewRects(this.mPictureSize));
        if (!TextUtils.isEmpty(retriveFaceInfo)) {
            opMode.setFaceRoi(retriveFaceInfo);
        }
        opMode.setOperateMode(this.mOperatingMode);
        opMode.setZoomMulti(getZoomRatio());
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            opMode.setEvValue(camera2Proxy.getExposureCompensation());
            MeteringRectangle[] aFRegions = this.mCamera2Device.getCameraConfigs().getAFRegions();
            if (aFRegions != null && aFRegions.length > 0) {
                opMode.setTouchRoi(aFRegions[0]);
            }
        }
        opMode.end();
        return opMode;
    }

    private String getPrefix() {
        return "";
    }

    private CameraSize getSatPictureSize() {
        int satMasterCameraId = this.mCamera2Device.getSatMasterCameraId();
        if (satMasterCameraId == 1) {
            return this.mUltraWidePictureSize;
        }
        if (satMasterCameraId == 2) {
            return this.mWidePictureSize;
        }
        if (satMasterCameraId == 3) {
            return this.mTelePictureSize;
        }
        if (satMasterCameraId == 4) {
            return this.mUltraTelePictureSize;
        }
        String str = TAG;
        Log.e(str, "getSatPictureSize: invalid satMasterCameraId " + satMasterCameraId);
        return this.mWidePictureSize;
    }

    private String getSuffix() {
        return !this.mMutexModePicker.isNormal() ? this.mMutexModePicker.getSuffix() : "";
    }

    private static String getTiltShiftMode() {
        if (CameraSettings.isTiltShiftOn()) {
            return DataRepository.dataItemRunning().getComponentRunningTiltValue().getComponentValue(160);
        }
        return null;
    }

    private void handleSaveFinishIfNeed() {
        this.mWaitSaveFinish = false;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(61);
        }
    }

    private void initZoomMapControllerIfNeeded() {
        CameraCapabilities cameraCapabilities;
        if (this.mZoomMapController == null && c.isSupportedOpticalZoom() && isBackCamera() && (cameraCapabilities = this.mCameraCapabilities) != null && cameraCapabilities.isSatPipSupported()) {
            this.mZoomMapController = new ZoomMapController(this.mActivity, false);
        }
    }

    private void initializeFocusManager() {
        this.mFocusManager = new FocusManager2(this.mCameraCapabilities, this, false, this.mActivity.getMainLooper());
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
    private boolean is3ALocked() {
        return this.m3ALocked;
    }

    private boolean isCannotGotoGallery() {
        return this.mPaused || this.isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || isQueueFull() || isInCountDown();
    }

    private boolean isFrontMirror() {
        return false;
    }

    private boolean isImageSaverFull() {
        ImageSaver imageSaver = this.mActivity.getImageSaver();
        if (imageSaver == null) {
            Log.w(TAG, "isParallelQueueFull: ImageSaver is null");
            return false;
        } else if (!imageSaver.isSaveQueueFull()) {
            return false;
        } else {
            Log.d(TAG, "isParallelQueueFull: ImageSaver queue is full");
            return true;
        }
    }

    private boolean isIn3OrMoreSatMode() {
        return 36866 == this.mOperatingMode && HybridZoomingSystem.IS_3_OR_MORE_SAT;
    }

    private boolean isInCountDown() {
        Disposable disposable = this.mCountdownDisposable;
        return disposable != null && !disposable.isDisposed();
    }

    private boolean isInMultiSurfaceSatMode() {
        return this.mCamera2Device.isInMultiSurfaceSatMode();
    }

    private boolean isParallelCameraSessionMode() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        return camera2Proxy != null && camera2Proxy.getCapabilities().isSupportParallelCameraDevice() && !isParallelUnSupported() && this.mCamera2Device.getSATSubCameraIds() != null && getZoomRatio() < HybridZoomingSystem.getTeleMinZoomRatio();
    }

    private boolean isParallelQueueFull() {
        if (!this.mEnableParallelSession || this.mActivity.getImageSaver() == null) {
            return false;
        }
        if (isImageSaverFull()) {
            return true;
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            return localBinder.needWaitProcess();
        }
        Log.w(TAG, "isParallelQueueFull: NOTICE: CHECK WHY BINDER IS NULL!");
        return false;
    }

    private boolean isParallelSessionConfigured() {
        boolean z;
        if (!this.mEnableParallelSession) {
            return true;
        }
        synchronized (this.mParallelSessionLock) {
            z = this.mParallelSessionConfigured;
        }
        return z;
    }

    private boolean isParallelUnSupported() {
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        return camera2Proxy != null && (!camera2Proxy.getCameraConfigs().isParallelSupportedCaptureMode() || (localBinder != null && localBinder.isAnyRequestBlocked()));
    }

    private boolean isQueueFull() {
        return this.mEnableParallelSession ? isParallelQueueFull() : isImageSaverFull();
    }

    private void lockAEAF() {
        Log.d(TAG, "lockAEAF");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(true);
        }
        this.m3ALocked = true;
    }

    private void onShutter(boolean z) {
        if (getCameraState() == 0) {
            Log.d(TAG, "onShutter: preview stopped");
            return;
        }
        this.mShutterCallbackTime = System.currentTimeMillis();
        this.mShutterLag = this.mShutterCallbackTime - this.mCaptureStartTime;
        String str = TAG;
        Log.v(str, "mShutterLag = " + this.mShutterLag + d.H);
        updateEnablePreviewThumbnail(z);
        String str2 = TAG;
        Log.d(str2, "onShutter mEnabledPreviewThumbnail:" + this.mEnabledPreviewThumbnail);
        if (this.mEnabledPreviewThumbnail) {
            this.mActivity.getCameraScreenNail().requestReadPixels();
        }
        if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && this.mBlockQuickShot && !CameraSettings.isGroupShotOn()) {
            resetStatusToIdle();
        }
    }

    private void prepareNormalCapture() {
        Log.d(TAG, "prepareNormalCapture E");
        this.mEnabledPreviewThumbnail = false;
        this.mCaptureStartTime = System.currentTimeMillis();
        this.mCamera2Device.setCaptureTime(this.mCaptureStartTime);
        ScenarioTrackUtil.trackCaptureTimeStart(isFrontCamera(), this.mModuleIndex);
        ScenarioTrackUtil.trackShotToGalleryStart(isFrontCamera(), this.mModuleIndex, this.mCaptureStartTime);
        ScenarioTrackUtil.trackShotToViewStart(isFrontCamera(), this.mModuleIndex, this.mCaptureStartTime);
        this.mLastCaptureTime = this.mCaptureStartTime;
        setCameraState(3);
        WatermarkItem watermarkItem = null;
        this.mMajorItem = null;
        this.mMinorItem = null;
        ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
        String str = WatermarkConstant.SUPER_MOON_RESET;
        if (componentRunningAIWatermark != null) {
            this.mMajorItem = DataRepository.dataItemRunning().getComponentRunningAIWatermark().getMajorWatermarkItem();
            WatermarkItem watermarkItem2 = this.mMajorItem;
            this.mMajorItem = (watermarkItem2 == null || !watermarkItem2.getKey().equals(str)) ? this.mMajorItem : null;
            this.mMinorItem = DataRepository.dataItemRunning().getComponentRunningAIWatermark().getMinorWatermarkItem();
            WatermarkItem watermarkItem3 = this.mMinorItem;
            if (watermarkItem3 == null || !watermarkItem3.getKey().equals(str)) {
                watermarkItem = this.mMinorItem;
            }
            this.mMinorItem = watermarkItem;
        }
        WatermarkItem watermarkItem4 = this.mMajorItem;
        String key = watermarkItem4 == null ? str : watermarkItem4.getKey();
        WatermarkItem watermarkItem5 = this.mMinorItem;
        if (watermarkItem5 != null) {
            str = watermarkItem5.getKey();
        }
        CameraStatUtils.trackSuperMoonCapture(key, str, (this.mMajorItem == null && this.mMinorItem == null) ? MistatsConstants.BaseEvent.VALUE_FALSE : MistatsConstants.BaseEvent.VALUE_TRUE);
        if (this.mMajorItem == null && this.mMinorItem == null) {
            this.mJpegRotation = Util.getJpegRotation(this.mBogusCameraId, this.mOrientation);
        } else {
            this.mJpegRotation = 90;
            Log.d(TAG, "prepareNormalCapture: watermark switch on, force change jpeg rotation to 90");
        }
        Log.d(TAG, "prepareNormalCapture: mOrientation = " + this.mOrientation + ", mJpegRotation = " + this.mJpegRotation);
        this.mCamera2Device.setJpegRotation(this.mJpegRotation);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        this.mCamera2Device.setGpsLocation(currentLocation);
        this.mLocation = currentLocation;
        updateMfnr();
        updateSuperResolution();
        updateShotDetermine();
        String str2 = getPrefix() + Util.createJpegName(System.currentTimeMillis()) + getSuffix();
        Log.d(TAG, "prepareNormalCapture title = " + str2);
        this.mCamera2Device.setShotSavePath(Storage.generateFilepath4Image(str2, CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat)), true);
        this.mCamera2Device.setNeedSequence(false);
        this.mAlgorithmName = this.mMutexModePicker.getAlgorithmName();
        setPictureOrientation();
        Log.d(TAG, "prepareNormalCapture X");
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.SUPER_MOON_TYPES_ON_PREVIEW_SUCCESS);
    }

    private void resumePreviewInWorkThread() {
        updatePreferenceInWorkThread(new int[0]);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setOrientation(int i, int i2) {
        if (i != -1) {
            this.mOrientation = i;
            EffectController.getInstance().setOrientation(Util.getShootOrientation(this.mActivity, this.mOrientation));
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
                CameraSchedulers.sCameraSetupScheduler.scheduleDirect(new la(this));
                return;
            }
            updatePreferenceInWorkThread(35);
        }
    }

    private void setPictureOrientation() {
        this.mShootRotation = this.mActivity.getSensorStateManager().isDeviceLying() ? (float) this.mOrientation : this.mDeviceRotation;
        int i = this.mOrientation;
        if (i == -1) {
            i = 0;
        }
        this.mShootOrientation = i;
    }

    private void startCount(final int i, int i2) {
        if (!this.mMediaRecorderRecording) {
            this.mIsStartCount = true;
            if (!checkShutterCondition()) {
                this.mIsStartCount = false;
                return;
            }
            setTriggerMode(i2);
            tryRemoveCountDownMessage();
            String str = TAG;
            Log.d(str, "startCount: " + i);
            Observable.interval(1, TimeUnit.SECONDS).take((long) i).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
                /* class com.android.camera.module.SuperMoonModule.AnonymousClass3 */

                @Override // io.reactivex.Observer
                public void onComplete() {
                    Camera camera;
                    SuperMoonModule.this.mIsStartCount = false;
                    SuperMoonModule.this.tryRemoveCountDownMessage();
                    if (SuperMoonModule.this.isAlive() && (camera = SuperMoonModule.this.mActivity) != null && !camera.isActivityPaused()) {
                        SuperMoonModule.this.onShutterButtonFocus(true, 3);
                        SuperMoonModule superMoonModule = SuperMoonModule.this;
                        superMoonModule.startNormalCapture(superMoonModule.getTriggerMode());
                        SuperMoonModule.this.onShutterButtonFocus(false, 0);
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            topAlert.reInitAlert(true);
                        }
                    }
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    SuperMoonModule.this.mIsStartCount = false;
                }

                public void onNext(Long l) {
                    int intValue = i - (l.intValue() + 1);
                    if (intValue > 0) {
                        SuperMoonModule.this.playCameraSound(5);
                        SuperMoonModule.this.mTopAlert.showDelayNumber(intValue);
                    }
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    SuperMoonModule.this.mCountdownDisposable = disposable;
                    SuperMoonModule.this.playCameraSound(7);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        AndroidSchedulers.mainThread().scheduleDirect(new ja(topAlert), 120, TimeUnit.MILLISECONDS);
                    }
                    SuperMoonModule.this.mMainProtocol.clearFocusView(7);
                    SuperMoonModule.this.mTopAlert.showDelayNumber(i);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startNormalCapture(int i) {
        String str = TAG;
        Log.d(str, "startNormalCapture mode -> " + i);
        this.mActivity.getScreenHint().updateHint();
        if (Storage.isLowStorageAtLastPoint()) {
            String str2 = TAG;
            Log.i(str2, "Not enough space or storage not ready. remaining=" + Storage.getLeftSpace());
            return;
        }
        blockSnapClickUntilSaveFinish();
        prepareNormalCapture();
        this.mHandler.sendEmptyMessageDelayed(50, CAPTURE_DURATION_THRESHOLD);
        this.mCamera2Device.setQuickShotAnimation(false);
        if (DataRepository.dataItemFeature()._j()) {
            if ((getModuleIndex() == 163 || getModuleIndex() == 165 || getModuleIndex() == 186 || getModuleIndex() == 182) && getZoomRatio() == 1.0f) {
                this.mCamera2Device.setFlawDetectEnable(true);
            } else {
                this.mCamera2Device.setFlawDetectEnable(false);
            }
        }
        this.mCamera2Device.takePicture(this, this.mActivity.getImageSaver());
        this.mBlockQuickShot = true;
        String str3 = TAG;
        Log.d(str3, "isParallelSessionEnable:" + isParallelSessionEnable() + ", and block quick shot");
    }

    private void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported && isDeviceAlive()) {
            this.mCamera2Device.unlockExposure();
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
        }
    }

    private void updateASD() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setASDEnable(true);
            Log.d(TAG, "updateASD call setASDEnable with true");
        }
    }

    private void updateAiScene() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setAiASDEnable(true);
            this.mCamera2Device.setASDScene(35);
            Log.d(TAG, "updateAiScene call setASDScene with AI_SCENE_MODE_MOON");
        }
    }

    private void updateEnablePreviewThumbnail(boolean z) {
        this.mEnabledPreviewThumbnail = enablePreviewAsThumbnail();
        this.mActivity.setPreviewThumbnail(this.mEnabledPreviewThumbnail);
        String str = TAG;
        Log.d(str, "updateEnablePreviewThumbnail mEnabledPreviewThumbnail:" + this.mEnabledPreviewThumbnail);
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
        }
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
        Log.d(TAG, "updateFocusMode E");
        String focusMode = this.mFocusManager.setFocusMode(CameraSettings.getFocusMode());
        setFocusMode(focusMode);
        if (CameraSettings.isFocusModeSwitching() && isBackCamera()) {
            CameraSettings.setFocusModeSwitching(false);
            this.mFocusManager.resetFocusStateIfNeeded();
        }
        if (focusMode.equals("manual")) {
            int focusPosition = CameraSettings.getFocusPosition();
            this.mCamera2Device.setFocusDistance((this.mCameraCapabilities.getMinimumFocusDistance() * ((float) focusPosition)) / 1000.0f);
        }
        Log.d(TAG, "updateFocusMode X");
    }

    private void updateMfnr() {
        if (this.mCamera2Device != null) {
            Log.d(TAG, "updateMfnr setMfnr to true");
            this.mCamera2Device.setMfnr(true);
        }
    }

    private void updateOIS() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setEnableOIS(true);
            Log.d(TAG, "updateOIS call setEnableOIS with true");
        }
    }

    private void updateOutputSize(@NonNull CameraSize cameraSize) {
        Camera2Proxy camera2Proxy;
        if (c.isMTKPlatform() && (camera2Proxy = this.mCamera2Device) != null) {
            cameraSize = PictureSizeManager.getBestPictureSize(camera2Proxy.getSatCapabilities().getSupportedOutputSizeWithAssignedMode(256), this.mModuleIndex);
        }
        if (165 == this.mModuleIndex) {
            int i = cameraSize.width;
            int i2 = cameraSize.height;
            if (i <= i2) {
                i2 = i;
            }
            this.mOutputPictureSize = new CameraSize(i2, i2);
            return;
        }
        this.mOutputPictureSize = cameraSize;
    }

    private void updatePictureAndPreviewSize() {
        this.mCameraCapabilities.setOperatingMode(getOperatingMode());
        int i = this.mEnableParallelSession ? 35 : 256;
        int[] sATSubCameraIds = this.mCamera2Device.getSATSubCameraIds();
        boolean z = sATSubCameraIds != null;
        if (z) {
            String str = TAG;
            Log.d(str, "[SAT] camera list: " + Arrays.toString(sATSubCameraIds));
            int length = sATSubCameraIds.length;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = sATSubCameraIds[i2];
                if (i3 == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                    if (this.mUltraCameraCapabilities == null) {
                        this.mUltraCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraWideCameraId());
                    }
                    CameraCapabilities cameraCapabilities = this.mUltraCameraCapabilities;
                    if (cameraCapabilities != null) {
                        cameraCapabilities.setOperatingMode(this.mOperatingMode);
                        this.mUltraWidePictureSize = getBestPictureSize(this.mUltraCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getMainBackCameraId()) {
                    if (this.mWideCameraCapabilities == null) {
                        this.mWideCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getMainBackCameraId());
                    }
                    CameraCapabilities cameraCapabilities2 = this.mWideCameraCapabilities;
                    if (cameraCapabilities2 != null) {
                        cameraCapabilities2.setOperatingMode(this.mOperatingMode);
                        List<CameraSize> supportedOutputSizeWithAssignedMode = this.mWideCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i);
                        int ei = DataRepository.dataItemFeature().ei();
                        if (ei != 0) {
                            PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode, ei, this.mModuleIndex, this.mBogusCameraId);
                            this.mWidePictureSize = PictureSizeManager.getBestPictureSize(this.mModuleIndex);
                        } else {
                            this.mWidePictureSize = getBestPictureSize(supportedOutputSizeWithAssignedMode);
                        }
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getAuxCameraId()) {
                    if (this.mTeleCameraCapabilities == null) {
                        this.mTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getAuxCameraId());
                    }
                    CameraCapabilities cameraCapabilities3 = this.mTeleCameraCapabilities;
                    if (cameraCapabilities3 != null) {
                        cameraCapabilities3.setOperatingMode(this.mOperatingMode);
                        this.mTelePictureSize = getBestPictureSize(this.mTeleCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getUltraTeleCameraId()) {
                    if (this.mUltraTeleCameraCapabilities == null) {
                        this.mUltraTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraTeleCameraId());
                    }
                    CameraCapabilities cameraCapabilities4 = this.mUltraTeleCameraCapabilities;
                    if (cameraCapabilities4 != null) {
                        cameraCapabilities4.setOperatingMode(this.mOperatingMode);
                        this.mUltraTelePictureSize = getBestPictureSize(this.mUltraTeleCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                    this.mCamera2Device.setUltraTelePictureSize(this.mUltraTelePictureSize);
                } else if (i3 == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
                    if (this.mMacroCameraCapabilities == null) {
                        this.mMacroCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getStandaloneMacroCameraId());
                    }
                    CameraCapabilities cameraCapabilities5 = this.mMacroCameraCapabilities;
                    if (cameraCapabilities5 != null) {
                        cameraCapabilities5.setOperatingMode(this.mOperatingMode);
                        this.mMacroPitureSize = getBestPictureSize(this.mMacroCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                    this.mCamera2Device.setMarcroPictureSize(this.mMacroPitureSize);
                }
            }
            if (DataRepository.dataItemFeature().pk()) {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s, ultraTeleSize:%s", this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize, this.mUltraTelePictureSize));
            } else {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s", this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize));
            }
            this.mCamera2Device.setUltraWidePictureSize(this.mUltraWidePictureSize);
            this.mCamera2Device.setWidePictureSize(this.mWidePictureSize);
            this.mCamera2Device.setTelePictureSize(this.mTelePictureSize);
            this.mPictureSize = getSatPictureSize();
            List<CameraSize> supportedOutputSizeWithAssignedMode2 = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
            CameraSize cameraSize = this.mPictureSize;
            this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSizeWithAssignedMode2, (double) CameraSettings.getPreviewAspectRatio(cameraSize.width, cameraSize.height));
            this.mCamera2Device.setPreviewSize(this.mPreviewSize);
            this.mCamera2Device.setAlgorithmPreviewSize(this.mPreviewSize);
            this.mCamera2Device.setAlgorithmPreviewFormat(35);
            this.mOutputPictureFormat = 256;
            String str2 = TAG;
            Locale locale = Locale.ENGLISH;
            Object[] objArr = new Object[1];
            String str3 = "HEIC";
            objArr[0] = CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? str3 : "JPEG";
            Log.d(str2, String.format(locale, "updateSize: use %s as preferred output image format", objArr));
            if (this.mEnableParallelSession) {
                List<CameraSize> supportedHeicOutputStreamSizes = CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? this.mCameraCapabilities.hasStandaloneHeicStreamConfigurations() ? this.mCameraCapabilities.getSupportedHeicOutputStreamSizes() : this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(this.mOutputPictureFormat) : this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256);
                int i4 = this.mModuleIndex;
                if (i4 == 165) {
                    CameraSize cameraSize2 = this.mPictureSize;
                    int min = Math.min(cameraSize2.width, cameraSize2.height);
                    this.mOutputPictureSize = PictureSizeManager.getBestSquareSize(supportedHeicOutputStreamSizes, min, c.isMTKPlatform() && isFrontCamera());
                    if (this.mOutputPictureSize.isEmpty()) {
                        String str4 = TAG;
                        Log.w(str4, "Could not find a proper squared Jpeg size, defaults to: " + min + "x" + min);
                        this.mOutputPictureSize = new CameraSize(min, min);
                    }
                } else if (z) {
                    this.mOutputPictureSize = this.mPictureSize;
                } else {
                    this.mOutputPictureSize = PictureSizeManager.getBestPictureSize(supportedHeicOutputStreamSizes, i4);
                }
                String str5 = TAG;
                Locale locale2 = Locale.ENGLISH;
                Object[] objArr2 = new Object[2];
                objArr2[0] = CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? str3 : "JPEG";
                objArr2[1] = this.mOutputPictureSize;
                Log.d(str5, String.format(locale2, "updateSize: algoUp picture size (%s): %s", objArr2));
            }
            String str6 = TAG;
            Locale locale3 = Locale.ENGLISH;
            Object[] objArr3 = new Object[4];
            if (this.mEnableParallelSession) {
                str3 = "YUV";
            } else if (!CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat)) {
                str3 = "JPEG";
            }
            objArr3[0] = str3;
            objArr3[1] = this.mPictureSize;
            objArr3[2] = this.mPreviewSize;
            objArr3[3] = this.mSensorRawImageSize;
            Log.d(str6, String.format(locale3, "updateSize: picture size (%s): %s, preview size: %s, sensor raw image size: %s", objArr3));
            CameraSize cameraSize3 = this.mPreviewSize;
            updateCameraScreenNailSize(cameraSize3.width, cameraSize3.height);
            checkDisplayOrientation();
            return;
        }
        throw new RuntimeException("SuperMoon Mode must with SAT!!");
    }

    private void updateShotDetermine() {
        this.mEnableParallelSession = isParallelSessionEnable();
        this.mEnableShot2Gallery = !this.mEnableParallelSession && DataRepository.dataItemFeature().cm() && enablePreviewAsThumbnail() && !CameraSettings.isLiveShotOn();
        String str = TAG;
        Log.d(str, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + 8);
        this.mCamera2Device.setShotType(8);
        this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
    }

    private void updateSuperResolution() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setSuperResolution(false);
            Log.d(TAG, "updateSuperResolution call setSuperResolution with false");
        }
    }

    public /* synthetic */ void Fd() {
        this.mActivity.getSensorStateManager().setLieIndicatorEnabled(true);
    }

    public /* synthetic */ void Gd() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
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
        Log.d(TAG, "closeCamera: E");
        setCameraState(0);
        synchronized (this.mCameraDeviceLock) {
            if (this.mCamera2Device != null) {
                if (this.mMultiSnapStatus) {
                    this.mCamera2Device.captureAbortBurst();
                    this.mMultiSnapStatus = false;
                }
                this.mCamera2Device.setScreenLightCallback(null);
                this.mCamera2Device.setMetaDataCallback(null);
                this.mCamera2Device.setErrorCallback(null);
                this.mCamera2Device.setFocusCallback(null);
                this.mCamera2Device.setAiASDEnable(false);
                if (this.mCameraCapabilities.isSupportAIIE()) {
                    this.mCamera2Device.setAIIEPreviewEnable(false);
                }
                if (c.Fj()) {
                    this.mCamera2Device.stopPreviewCallback(true);
                }
                this.m3ALocked = false;
                this.mCamera2Device.setASDEnable(false);
                this.mCamera2Device.setAiMoonEffectEnable(false);
                this.mCamera2Device.setEnableOIS(false);
                this.mCamera2Device.setSuperResolution(false);
                this.mCamera2Device.setMfnr(false);
                this.mCamera2Device.setColorEnhanceEnable(false);
                if (!isParallelCameraSessionMode()) {
                    this.mCamera2Device = null;
                }
            }
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        if (c.Fj()) {
            PreviewDecodeManager.getInstance().quit();
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            localBinder.setOnSessionStatusCallBackListener(null);
        }
        Log.d(TAG, "closeCamera: X");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 2) {
                updateFilter();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 12) {
                setEvValue();
            } else if (i == 14) {
                updateFocusMode();
            } else if (i == 20) {
                updateOIS();
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 30) {
                updateSuperResolution();
            } else if (i == 34) {
                updateMfnr();
            } else if (i == 36) {
                updateAiScene();
            } else if (i == 44) {
                updateShotDetermine();
            } else if (i == 55) {
                updateModuleRelated();
            } else if (i == 70) {
                updateASD();
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void fillFeatureControl(StartControl startControl) {
        super.fillFeatureControl(startControl);
    }

    /* access modifiers changed from: protected */
    public CameraSize getBestPictureSize(List<CameraSize> list) {
        PictureSizeManager.initialize(list, getMaxPictureSize(), this.mModuleIndex, this.mBogusCameraId);
        return PictureSizeManager.getBestPictureSize(this.mModuleIndex);
    }

    /* access modifiers changed from: protected */
    public CameraSize getBestPictureSize(List<CameraSize> list, float f) {
        PictureSizeManager.initialize(list, getMaxPictureSize(), this.mModuleIndex, this.mBogusCameraId);
        return PictureSizeManager.getBestPictureSize(f);
    }

    /* access modifiers changed from: protected */
    public int getMaxPictureSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        this.mOperatingMode = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SAT;
        return this.mOperatingMode;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        if (r0.isParallelBusy(false) != false) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005c, code lost:
        if (r0.isAnyRequestBlocked() != false) goto L_0x003e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isBlockSnap() {
        boolean z;
        LocalParallelService.LocalBinder localBinder;
        if (!DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode() || DataRepository.dataItemFeature().hm() || (localBinder = AlgoConnector.getInstance().getLocalBinder()) == null || localBinder.isIdle()) {
            if (isParallelCameraSessionMode()) {
                Camera2Proxy camera2Proxy = this.mCamera2Device;
                if (camera2Proxy != null) {
                }
                z = false;
                if (this.mPaused || this.isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || z) {
                    return true;
                }
                Camera2Proxy camera2Proxy2 = this.mCamera2Device;
                return (camera2Proxy2 != null && camera2Proxy2.isCaptureBusy(this.mMutexModePicker.isHdr())) || isQueueFull() || isInCountDown() || this.mWaitSaveFinish || !isParallelSessionConfigured() || this.mHandler.hasMessages(62);
            }
            z = getCameraState() == 3;
            if (!z) {
                LocalParallelService.LocalBinder localBinder2 = AlgoConnector.getInstance().getLocalBinder();
                if (localBinder2 != null) {
                }
                z = false;
            }
            return this.mPaused ? true : true;
            z = true;
            if (this.mPaused) {
            }
        } else {
            Log.i(TAG, "is shoting super night and discard snap");
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
        if (r0.isAnyRequestBlocked() != false) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0062, code lost:
        if (r0.isCaptureBusy(true) != false) goto L_0x0056;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        boolean z;
        LocalParallelService.LocalBinder localBinder;
        if (!DataRepository.dataItemFeature().ll() || (localBinder = AlgoConnector.getInstance().getLocalBinder()) == null || localBinder.isIdle()) {
            if (getCameraState() == 3) {
                return true;
            }
            if (isParallelCameraSessionMode()) {
                Camera2Proxy camera2Proxy = this.mCamera2Device;
                z = camera2Proxy != null && camera2Proxy.isParallelBusy(true);
                if (!z) {
                    LocalParallelService.LocalBinder localBinder2 = AlgoConnector.getInstance().getLocalBinder();
                    if (localBinder2 != null) {
                    }
                    z = false;
                }
                return this.mPaused ? true : true;
            }
            Camera2Proxy camera2Proxy2 = this.mCamera2Device;
            if (camera2Proxy2 != null) {
            }
            z = false;
            return this.mPaused ? true : true;
            z = true;
            if (this.mPaused) {
            }
        } else {
            Log.i(TAG, "[ALGOUP|MMCAMERA] Doing action");
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isParallelSessionEnable() {
        return true;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowAeAfLockIndicator() {
        return this.m3ALocked;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return !this.mMutexModePicker.isBurstShoot() && isSupportFocusShoot();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key");
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        Camera2Proxy camera2Proxy;
        boolean z = true;
        if (getCameraState() == 3 || isFrontCamera() || (camera2Proxy = this.mCamera2Device) == null || camera2Proxy.isCaptureBusy(true) || !isFrameAvailable() || this.mMediaRecorderRecording) {
            z = false;
        }
        String str = TAG;
        Log.d(str, "isZoomEnabled: " + z);
        return z;
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
    public void onBroadcastReceived(Context context, Intent intent) {
        if (intent != null && isAlive()) {
            if (CameraIntentManager.ACTION_VOICE_CONTROL.equals(intent.getAction())) {
                Log.d(TAG, "on Receive voice control broadcast action intent");
                String voiceControlAction = CameraIntentManager.getInstance(intent).getVoiceControlAction();
                this.mBroadcastIntent = intent;
                char c2 = 65535;
                if (voiceControlAction.hashCode() == 1270567718 && voiceControlAction.equals("CAPTURE")) {
                    c2 = 0;
                }
                if (c2 == 0) {
                    if (isBlockSnap()) {
                        Log.w(TAG, "on voice control: block snap");
                        this.mBroadcastIntent = null;
                        return;
                    }
                    onShutterButtonClick(getTriggerMode());
                    this.mBroadcastIntent = null;
                }
                CameraIntentManager.removeInstance(intent);
            } else if (CameraIntentManager.ACTION_SPEECH_SHUTTER.equals(intent.getAction())) {
                Log.d(TAG, "on Receive speech shutter broadcast action intent");
                if (isBlockSnap()) {
                    Log.w(TAG, "on Speech shutter: block snap");
                    return;
                }
                onShutterButtonClick(110);
            }
            super.onBroadcastReceived(context, intent);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initZoomMapControllerIfNeeded();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.SUPER_MOON_TYPES_INIT);
        startPreview();
        this.mOnResumeTime = SystemClock.uptimeMillis();
        this.mHandler.sendEmptyMessage(4);
        this.mHandler.sendEmptyMessage(31);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
        super.onCapabilityChanged(cameraCapabilities);
        this.mUltraWideAELocked = false;
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setCharacteristics(cameraCapabilities);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onCapabilityChanged(cameraCapabilities);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureCompleted(boolean z) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureProgress(boolean z, boolean z2, boolean z3, boolean z4) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z, boolean z2, boolean z3, boolean z4) {
        String str = TAG;
        Log.d(str, "onCaptureShutter: cameraState = " + getCameraState() + ", isParallel = " + this.mEnableParallelSession);
        onShutter(z);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, @NonNull CameraSize cameraSize, boolean z, boolean z2, boolean z3, boolean z4) {
        List<WaterMarkData> faceWaterMarkInfos;
        this.mCamera2Device.updateFlashStateTimeLock();
        if (isDeparted()) {
            Log.w(TAG, "onCaptureStart: departed");
            if (DataRepository.dataItemFeature().Ol()) {
                parallelTaskData.setRequireTuningData(true);
            }
            parallelTaskData.setAbandoned(true);
            return parallelTaskData;
        }
        parallelTaskData.setServiceStatusListener(this.mServiceStatusListener);
        int parallelType = parallelTaskData.getParallelType();
        if (!this.mEnabledPreviewThumbnail) {
            onShutter(z);
            CameraSettings.setPlayToneOnCaptureStart(true);
        }
        String str = null;
        ArrayList arrayList = (!CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() || (faceWaterMarkInfos = ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).getFaceWaterMarkInfos()) == null || faceWaterMarkInfos.isEmpty()) ? null : new ArrayList(faceWaterMarkInfos);
        Log.d(TAG, "onCaptureStart: inputSize = " + cameraSize);
        if ((isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) && (!cameraSize.equals(this.mPictureSize) || c.isMTKPlatform())) {
            this.mPictureSize = cameraSize;
            updateOutputSize(cameraSize);
        }
        CameraSize cameraSize2 = this.mOutputPictureSize;
        Size sizeObject = cameraSize2 == null ? cameraSize.toSizeObject() : cameraSize2.toSizeObject();
        Log.d(TAG, "onCaptureStart: outputSize = " + sizeObject);
        int i = this.mOutputPictureFormat;
        boolean isHeicImageFormat = CompatibilityUtils.isHeicImageFormat(i);
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onCaptureStart: outputFormat = ");
        sb.append(isHeicImageFormat ? "HEIC" : "JPEG");
        Log.d(str2, sb.toString());
        int clampQuality = clampQuality(CameraSettings.getEncodingQuality(false).toInteger(isHeicImageFormat));
        Log.d(TAG, "onCaptureStart: outputQuality = " + clampQuality);
        CameraCharacteristics cameraCharacteristics = this.mCameraCapabilities.getCameraCharacteristics();
        this.mFocalLengths = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        this.mApertures = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
        ParallelTaskDataParameter.Builder builder = new ParallelTaskDataParameter.Builder(this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject, i);
        if (parallelType == 1) {
            CameraSize cameraSize3 = this.mSensorRawImageSize;
            builder.setRawSize(cameraSize3.width, cameraSize3.height);
        }
        boolean z5 = DataRepository.dataItemFeature().Sl() && (Util.isStringValueContained("device", this.mCamera2Device.getCameraConfigs().getWaterMarkAppliedList()) || Util.isStringValueContained(WatermarkConstant.ITEM_TAG, this.mCamera2Device.getCameraConfigs().getWaterMarkAppliedList()));
        Location location = this.mActivity.getCameraIntentManager().checkIntentLocationPermission(this.mActivity) ? this.mLocation : null;
        ParallelTaskDataParameter.Builder filterId = builder.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setVendorWaterMark(z5).setMirror(isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false));
        int i2 = this.mOrientation;
        if (-1 == i2) {
            i2 = 0;
        }
        ParallelTaskDataParameter.Builder location2 = filterId.setOrientation(i2).setJpegRotation(this.mJpegRotation).setShootRotation(this.mShootRotation).setShootOrientation(this.mShootOrientation).setSupportZeroDegreeOrientationImage(this.mCameraCapabilities.isSupportZeroDegreeOrientationImage()).setLocation(location);
        if (CameraSettings.isTimeWaterMarkOpen()) {
            str = Util.getTimeWatermark();
        }
        parallelTaskData.fillParameter(location2.setTimeWaterMarkString(str).setFaceWaterMarkList(arrayList).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(isFrontCamera()).setBokehFrontCamera(isPictureUseDualFrontCamera()).setAlgorithmName(this.mAlgorithmName).setPictureInfo(getPictureInfo()).setSuffix(getSuffix()).setTiltShiftMode(getTiltShiftMode()).setSaveGroupshotPrimitive(CameraSettings.isSaveGroushotPrimitiveOn()).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setJpegQuality(clampQuality).setPrefix(getPrefix()).setMoonMode(true).setMiMovieOpen(CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex)).setMajorAIWatermark(this.mMajorItem).setMinorAIWatermark(this.mMinorItem).setIsSupportMiDualBokeh(this.mCameraCapabilities.isSupportMiDualBokeh()).build());
        parallelTaskData.setNeedThumbnail(!z && !this.mEnabledPreviewThumbnail);
        parallelTaskData.setCurrentModuleIndex(this.mModuleIndex);
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        parallelTaskData.setAdaptiveSnapshotSize(cameraCapabilities != null && cameraCapabilities.isAdaptiveSnapshotSizeInSatModeSupported());
        parallelTaskData.setLiveShotTask(false);
        if (DataRepository.dataItemFeature().Ol()) {
            parallelTaskData.setRequireTuningData(true);
        }
        Log.d(TAG, "onCaptureStart: isParallel = " + this.mEnableParallelSession + ", shotType = " + parallelTaskData.getParallelType());
        if (this.mEnableParallelSession) {
            beginParallelProcess(parallelTaskData, true);
        }
        if (CameraSettings.isHandGestureOpen()) {
            Log.d(TAG, "send msg: reset hand gesture");
            this.mHandler.removeMessages(57);
            this.mHandler.sendEmptyMessageDelayed(57, 0);
        }
        return parallelTaskData;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        this.mTelephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        this.mHandler = new MainHandler(this, this.mActivity.getMainLooper());
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        onCameraOpened();
        this.mFirstCreateCapture = true;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        ZoomMapController zoomMapController = this.mZoomMapController;
        if (zoomMapController != null) {
            zoomMapController.onModuleDestroy();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void onExtraMenuVisibilityChange(boolean z) {
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void onFocusAreaChanged(int i, int i2) {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            Rect cropRegion = getCropRegion();
            Rect activeArraySize = getActiveArraySize();
            this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            this.mCamera2Device.setAFRegions(this.mFocusManager.getMeteringOrFocusAreas(i, i2, cropRegion, activeArraySize, true));
            this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FocusCallback
    public void onFocusStateChanged(FocusTask focusTask) {
        CameraCapabilities cameraCapabilities;
        if (isFrameAvailable() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
                if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                    setCameraState(1);
                }
                this.mFocusManager.onFocusResult(focusTask);
                this.mActivity.getSensorStateManager().reset();
                if (focusTask.isSuccess() && this.m3ALocked) {
                    if (!DataRepository.dataItemFeature().nm() && isZoomRatioBetweenUltraAndWide() && (cameraCapabilities = this.mUltraCameraCapabilities) != null) {
                        boolean isAFRegionSupported = cameraCapabilities.isAFRegionSupported();
                        Log.d(TAG, "onFocusStateChanged: isUltraFocusAreaSupported = " + isAFRegionSupported);
                        if (!isAFRegionSupported) {
                            this.mCamera2Device.setFocusMode(0);
                            this.mCamera2Device.setFocusDistance(0.0f);
                            this.mUltraWideAELocked = true;
                        }
                    }
                    this.mCamera2Device.lockExposure(true);
                }
            } else if (focusTrigger != 2 && focusTrigger == 3) {
                String str = null;
                if (focusTask.isFocusing()) {
                    this.mAFEndLogTimes = 0;
                    str = "onAutoFocusMoving start";
                } else if (this.mAFEndLogTimes == 0) {
                    str = "onAutoFocusMoving end. result=" + focusTask.isSuccess();
                    this.mAFEndLogTimes++;
                }
                if (Util.sIsDumpLog && str != null) {
                    Log.v(TAG, str);
                }
                if (getCameraState() != 3 || focusTask.getFocusTrigger() == 3) {
                    if (!this.m3ALocked) {
                        this.mFocusManager.onFocusResult(focusTask);
                    }
                } else if (focusTask.isSuccess()) {
                    this.mFocusManager.onFocusResult(focusTask);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        if (r6 != 88) goto L_0x0079;
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
                    if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable()) {
                        performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    }
                }
                return true;
            } else if (i == 80) {
                if (keyEvent.getRepeatCount() == 0) {
                    onShutterButtonFocus(true, 1);
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
    public void onLongPress(float f, float f2) {
        int i = (int) f;
        int i2 = (int) f2;
        if (isInTapableRect(i, i2)) {
            onSingleTapUp(i, i2, true);
            if (this.m3ALockSupported && this.mCamera2Device.getFocusMode() != AutoFocus.convertToFocusMode("manual")) {
                lockAEAF();
            }
            this.mMainProtocol.performHapticFeedback(0);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void onMeteringAreaChanged(int i, int i2) {
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            Rect cropRegion = getCropRegion();
            Rect activeArraySize = getActiveArraySize();
            this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            this.mCamera2Device.setAERegions(this.mFocusManager.getMeteringOrFocusAreas(i, i2, cropRegion, activeArraySize, false));
            this.mCamera2Device.resumePreview();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        if (!this.isGradienterOn || this.mActivity.getSensorStateManager().isDeviceLying()) {
            setOrientation(i, i2);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTakenFinished(boolean z) {
        String str = TAG;
        Log.d(str, "onPictureTakenFinished: succeed = " + z);
        if (z) {
            long currentTimeMillis = System.currentTimeMillis() - this.mCaptureStartTime;
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
            trackGeneralInfo(hashMap, 1, false, null, this.mLocation != null, this.mCurrentAiScene);
            CameraStatUtils.trackTakePictureCost(currentTimeMillis, isFrontCamera(), this.mModuleIndex);
            ScenarioTrackUtil.trackCaptureTimeEnd();
            String str2 = TAG;
            Log.d(str2, "mCaptureStartTime(from onShutterButtonClick start to jpegCallback finished) = " + currentTimeMillis + d.H);
            if (this.mLongPressedAutoFocus) {
                this.mLongPressedAutoFocus = false;
                this.mFocusManager.cancelLongPressedAutoFocus();
            }
            handleSaveFinishIfNeed();
        }
        if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && this.mBlockQuickShot && (!CameraSettings.isGroupShotOn() || (CameraSettings.isGroupShotOn() && z))) {
            resetStatusToIdle();
        }
        this.mHandler.removeMessages(50);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public boolean onPictureTakenImageConsumed(Image image, TotalCaptureResult totalCaptureResult) {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        this.mActivity.onLayoutChange(rect);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraMetaDataCallback
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        if (captureResult != null) {
            super.onPreviewMetaDataUpdate(captureResult);
            if (this.mZoomMapController != null) {
                this.mZoomMapController.setMapRect(CaptureResultParser.getZoomMapRIO(this.mCameraCapabilities, captureResult));
            }
            int asdDetectedModes = CaptureResultParser.getAsdDetectedModes(captureResult);
            if (this.mCurrentAiScene != asdDetectedModes) {
                String str = TAG;
                Log.d(str, "consumeAiSceneResult mCurrentAiScene:" + this.mCurrentAiScene + " newResult:" + asdDetectedModes);
                this.mCurrentAiScene = asdDetectedModes;
                this.mCamera2Device.setAiMoonEffectEnable(this.mCurrentAiScene == 35);
                resumePreviewInWorkThread();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0121  */
    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
        boolean z;
        Log.d(TAG, "onPreviewPixelsRead E");
        this.mActivity.getCameraScreenNail().animateCapture(getCameraRotation());
        playCameraSound(0);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        boolean z2 = isFrontCamera() && !isFrontMirror();
        synchronized (this.mCameraDeviceLock) {
            if (isAlive()) {
                if (isDeviceAlive()) {
                    Bitmap cropBitmap = Util.cropBitmap(createBitmap, this.mShootRotation, z2, (float) this.mOrientation, this.mModuleIndex == 165, CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex));
                    if (cropBitmap == null) {
                        Log.w(TAG, "onPreviewPixelsRead: bitmap is null!");
                        return;
                    }
                    byte[] bitmapData = Util.getBitmapData(cropBitmap, EncodingQuality.NORMAL.toInteger(false));
                    if (bitmapData == null) {
                        Log.w(TAG, "onPreviewPixelsRead: jpegData is null!");
                        return;
                    }
                    this.mCamera2Device.getCameraConfigs().getShotType();
                    int i3 = this.mOutputPictureFormat;
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onPreviewPixelsRead: isParallel = ");
                    sb.append(this.mEnableParallelSession);
                    sb.append(", shot2Gallery = ");
                    sb.append(this.mEnableShot2Gallery);
                    sb.append(", format = ");
                    sb.append(CompatibilityUtils.isHeicImageFormat(i3) ? "HEIC" : "JPEG");
                    sb.append(", data = ");
                    sb.append(bitmapData);
                    Log.d(str, sb.toString());
                    ParallelTaskData parallelTaskData = new ParallelTaskData(this.mActualCameraId, System.currentTimeMillis(), -1, this.mCamera2Device.getParallelShotSavePath(), this.mCaptureStartTime);
                    if (!this.mEnableParallelSession) {
                        if (!this.mEnableShot2Gallery) {
                            z = false;
                            parallelTaskData.setNeedThumbnail(z);
                            parallelTaskData.fillJpegData(bitmapData, 0);
                            parallelTaskData.fillParameter(new ParallelTaskDataParameter.Builder(new Size(i, i2), new Size(i, i2), new Size(i, i2), i3).setOrientation(this.mOrientation).build());
                            if (DataRepository.dataItemFeature().Ol()) {
                                parallelTaskData.setRequireTuningData(true);
                            }
                            this.mActivity.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
                            Log.d(TAG, "onPreviewPixelsRead X");
                            return;
                        }
                    }
                    z = true;
                    parallelTaskData.setNeedThumbnail(z);
                    parallelTaskData.fillJpegData(bitmapData, 0);
                    parallelTaskData.fillParameter(new ParallelTaskDataParameter.Builder(new Size(i, i2), new Size(i, i2), new Size(i, i2), i3).setOrientation(this.mOrientation).build());
                    if (DataRepository.dataItemFeature().Ol()) {
                    }
                    this.mActivity.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
                    Log.d(TAG, "onPreviewPixelsRead X");
                    return;
                }
            }
            Log.d(TAG, "onPreviewPixelsRead: module is dead");
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        String str = TAG;
        Log.d(str, "onPreviewSessionSuccess: " + Thread.currentThread().getName() + " " + this);
        if (cameraCaptureSession == null) {
            String str2 = TAG;
            Log.d(str2, "onPreviewSessionSuccess null session." + Util.getCallers(1));
        } else if (!isAlive()) {
            String str3 = TAG;
            Log.d(str3, "onPreviewSessionSuccess module not alive." + Util.getCallers(1));
        } else {
            if (!isKeptBitmapTexture()) {
                this.mHandler.sendEmptyMessage(9);
            }
            if (this.mEnableParallelSession) {
                ParallelDataZipper.getInstance().getHandler().post(new Runnable() {
                    /* class com.android.camera.module.SuperMoonModule.AnonymousClass4 */

                    public void run() {
                        SuperMoonModule.this.configParallelSession();
                    }
                });
            }
            previewWhenSessionSuccess();
            if (this.mActivity.getCameraIntentManager().checkCallerLegality() && !this.mActivity.isActivityPaused()) {
                if (!this.mActivity.getCameraIntentManager().isOpenOnly(this.mActivity)) {
                    this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.CAMERA_OPEN_ONLY);
                    if (!this.mActivity.isIntentPhotoDone()) {
                        this.mHandler.sendEmptyMessageDelayed(52, 1000);
                        this.mActivity.setIntnetPhotoDone(true);
                        return;
                    }
                    return;
                }
                this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (i == 100) {
            this.mActivity.onUserInteraction();
        } else if (i == 110) {
            this.mActivity.onUserInteraction();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.hideRecommendDescTip(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC);
            }
        }
        int countDownTimes = getCountDownTimes(i);
        if (countDownTimes > 0) {
            startCount(countDownTimes, i);
            return;
        }
        MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
        ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert2 != null) {
            pictureTakenParameter.isASDBacklitTip = topAlert2.isShowBacklightSelector();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            pictureTakenParameter.isASDPortraitTip = bottomPopupTips.containTips(R.string.recommend_portrait);
        }
        if (checkShutterCondition()) {
            this.mCamera2Device.setFixShotTimeEnabled(false);
            setTriggerMode(i);
            String str = TAG;
            Log.d(str, "onShutterButtonClick " + getCameraState());
            this.mFocusManager.prepareCapture(false, 2);
            this.mFocusManager.doSnap();
            this.mFirstCreateCapture = false;
            if (this.mFocusManager.isFocusingSnapOnFinish()) {
                enableCameraControls(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        if (isDoingAction()) {
            Log.d(TAG, "onShutterButtonLongClick: doing action");
            return false;
        } else if (this.mIsSatFallback == 0 || !shouldCheckSatFallbackState()) {
            this.mLongPressedAutoFocus = true;
            this.mMainProtocol.setFocusViewType(false);
            unlockAEAF();
            this.mFocusManager.requestAutoFocus();
            this.mActivity.getScreenHint().updateHint();
            return false;
        } else {
            Log.d(TAG, "onShutterButtonLongClick: sat fallback");
            return false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
        if (!this.mLongPressedAutoFocus) {
            return;
        }
        if (z) {
            onShutterButtonClick(10);
            return;
        }
        this.mLongPressedAutoFocus = false;
        this.mFocusManager.cancelLongPressedAutoFocus();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        String str = TAG;
        Log.v(str, "onSingleTapUp mPaused: " + this.mPaused + "; mCamera2Device: " + this.mCamera2Device + "; isInCountDown: " + isInCountDown() + "; getCameraState: " + getCameraState() + "; mMultiSnapStatus: " + this.mMultiSnapStatus + "; SuperMoonModule: " + this);
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && this.mCamera2Device.isPreviewReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 4 && getCameraState() != 0 && !isInCountDown() && !this.mMultiSnapStatus && isFrameAvailable()) {
            if ((!isFrontCamera() || !this.mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2)) {
                this.mIsStartCount = false;
                tryRemoveCountDownMessage();
                if (this.mFocusAreaSupported || this.mMeteringAreaSupported) {
                    if (this.mObjectTrackingStarted) {
                        stopObjectTracking(true);
                    }
                    this.mMainProtocol.setFocusViewType(true);
                    Point point = new Point(i, i2);
                    mapTapCoordinate(point);
                    unlockAEAF();
                    setCameraState(2);
                    this.mFocusManager.onSingleTapUp(point.x, point.y, z);
                    if (!this.mFocusAreaSupported && this.mMeteringAreaSupported) {
                        this.mActivity.getSensorStateManager().reset();
                    }
                    CameraClickObservableImpl cameraClickObservableImpl = (CameraClickObservableImpl) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (!z && cameraClickObservableImpl != null) {
                        cameraClickObservableImpl.subscribe(165);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSurfaceTextureUpdated(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onPreviewComing();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onThumbnailClicked(View view) {
        if (this.mWaitSaveFinish) {
            Log.d(TAG, "onThumbnailClicked: CannotGotoGallery...mWaitSaveFinish");
            return;
        }
        if (this.mEnableParallelSession || this.mEnableShot2Gallery) {
            if (isCannotGotoGallery()) {
                Log.d(TAG, "onThumbnailClicked: CannotGotoGallery...");
                return;
            }
        } else if (isDoingAction()) {
            Log.d(TAG, "onThumbnailClicked: DoingAction..");
            return;
        }
        if (this.mActivity.getThumbnailUpdater().getThumbnail() != null) {
            this.mActivity.gotoGallery();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean onWaitingFocusFinished() {
        if (isBlockSnap() || !isAlive()) {
            return false;
        }
        if (this.mIsSatFallback == 0 || !shouldCheckSatFallbackState()) {
            this.mWaitingSnapshot = false;
            startNormalCapture(getTriggerMode());
        } else {
            this.mWaitingSnapshot = true;
            Log.w(TAG, "capture check: sat fallback");
        }
        return true;
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionEnd(int i) {
        String str = TAG;
        Log.d(str, "onZoomingActionEnd(): " + ZoomingAction.toString(i));
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && dualController.isZoomPanelVisible()) {
            dualController.updateZoomIndexsButton();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionStart(int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        String str = TAG;
        Log.d(str, "onZoomingActionStart(): " + ZoomingAction.toString(i));
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        if (!isZoomEnabled()) {
            if (CameraSettings.isUltraPixelOn() && !DataRepository.dataItemFeature().bk() && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
                bottomPopupTips.showTips(15, ComponentRunningUltraPixel.getNoSupportZoomTip(), 1);
            }
            Log.d(TAG, "onZoomingActionStart(): zoom is currently disallowed");
            return;
        }
        ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
        if (cameraClickObservable != null) {
            cameraClickObservable.subscribe(168);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean onZoomingActionUpdate(float f, int i) {
        ZoomMapController zoomMapController = this.mZoomMapController;
        if (zoomMapController != null) {
            zoomMapController.onZoomRatioUpdate(f);
        }
        return super.onZoomingActionUpdate(f, i);
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        Log.v(TAG, "pausePreview");
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (!this.mPaused && getCameraState() != 0) {
            if (!isDoingAction()) {
                restoreBottom();
            }
            if (i2 == 0) {
                if (z) {
                    onShutterButtonFocus(true, 1);
                    if (str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
                        startCount(2, 20);
                    } else {
                        onShutterButtonClick(i);
                    }
                } else {
                    onShutterButtonFocus(false, 0);
                    if (this.mVolumeLongPress) {
                        this.mVolumeLongPress = false;
                        onShutterButtonLongClickCancel(false);
                    }
                }
            } else if (!isInCountDown() && z && !this.mVolumeLongPress) {
                this.mVolumeLongPress = onShutterButtonLongClick();
                if (!this.mVolumeLongPress && this.mLongPressedAutoFocus) {
                    this.mVolumeLongPress = true;
                }
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        if (!this.mMediaRecorderRecording) {
            playCameraSound(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void reShowMoon() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(195, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 227, 235, 212, 254);
    }

    /* access modifiers changed from: protected */
    public void resetStatusToIdle() {
        Log.d(TAG, "reset Status to Idle");
        setCameraState(1);
        enableCameraControls(true);
        this.mBlockQuickShot = false;
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        previewWhenSessionSuccess();
        this.mBlockQuickShot = !CameraSettings.isCameraQuickShotEnable();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void setFrameAvailable(boolean z) {
        Camera2Proxy camera2Proxy;
        super.setFrameAvailable(z);
        if (z && this.mActivity != null && CameraSettings.isCameraSoundOpen()) {
            this.mActivity.loadCameraSound(1);
            this.mActivity.loadCameraSound(0);
            this.mActivity.loadCameraSound(4);
            this.mActivity.loadCameraSound(5);
            this.mActivity.loadCameraSound(7);
            this.mActivity.loadCameraSound(2);
            this.mActivity.loadCameraSound(3);
        }
        if (z && (camera2Proxy = this.mCamera2Device) != null) {
            camera2Proxy.releaseFakeSurfaceIfNeed();
        }
        if (z && CameraSettings.isCameraLyingHintOn()) {
            this.mHandler.post(new ka(this));
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void startAiLens() {
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFocus() {
        if (!isDeviceAlive() || !isFrameAvailable()) {
            return;
        }
        if (this.mFocusOrAELockSupported) {
            this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
        } else {
            this.mCamera2Device.resumePreview();
        }
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setActivityHashCode(this.mActivity.hashCode());
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setMetaDataCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            String str = TAG;
            Log.d(str, "startPreview: set PictureSize with " + this.mPictureSize);
            this.mCamera2Device.setPictureSize(this.mPictureSize);
            this.mCamera2Device.setPictureFormat(this.mOutputPictureFormat);
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("startPreview: set PictureFormat to ");
            sb.append(CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? "HEIC" : "JPEG");
            Log.d(str2, sb.toString());
            SurfaceTexture surfaceTexture = this.mActivity.getCameraScreenNail().getSurfaceTexture();
            String str3 = TAG;
            Log.d(str3, "startPreview: surfaceTexture = " + surfaceTexture);
            if (surfaceTexture != null) {
                this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            }
            Surface surface = null;
            Surface surface2 = surfaceTexture != null ? new Surface(surfaceTexture) : null;
            this.mConfigRawStream = false;
            ZoomMapController zoomMapController = this.mZoomMapController;
            if (zoomMapController != null) {
                surface = zoomMapController.createZoomMapSurfaceIfNeeded();
            }
            this.mCamera2Device.startPreviewSession(surface2, 0, this.mConfigRawStream, false, surface, getOperatingMode(), this.mEnableParallelSession, this);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void tryRemoveCountDownMessage() {
        Disposable disposable = this.mCountdownDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mCountdownDisposable.dispose();
            this.mCountdownDisposable = null;
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.module.SuperMoonModule.AnonymousClass2 */

                public void run() {
                    Log.d(SuperMoonModule.TAG, "run: hide delay number in main thread");
                    SuperMoonModule.this.mTopAlert.hideDelayNumber();
                }
            });
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        Log.d(TAG, "unRegisterProtocol");
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(195, this);
        getActivity().getImplFactory().detachAdditional();
    }
}
