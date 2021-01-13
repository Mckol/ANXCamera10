package com.android.camera.module;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Range;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.AutoLockManager;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.EncodingQuality;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.LocationManager;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.aiwatermark.chain.AbstractPriorityChain;
import com.android.camera.aiwatermark.chain.PriorityChainFactory;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.CameraScene;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBokeh;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.dualvideo.TouchEventView;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.fragment.GoogleLensFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.fragment.top.FragmentTopConfig;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.LiveMediaRecorder;
import com.android.camera.module.impl.component.CameraClickObservableImpl;
import com.android.camera.module.loader.FunctionParseAiScene;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.FunctionParseAsdHdr;
import com.android.camera.module.loader.FunctionParseAsdLivePhoto;
import com.android.camera.module.loader.FunctionParseAsdScene;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.android.camera.module.loader.FunctionParseNearRangeTip;
import com.android.camera.module.loader.FunctionParseSuperNight;
import com.android.camera.module.loader.PredicateFilterAiScene;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.scene.FunctionMiAlgoASDEngine;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.timerburst.CameraTimer;
import com.android.camera.timerburst.TimerBurstController;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera.zoommap.ZoomMapController;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraConfigs;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.CameraPreferredMode;
import com.android.camera2.CaptureResultParser;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.android.camera2.vendortag.struct.MarshalQueryableChiRect;
import com.android.camera2.vendortag.struct.MarshalQueryableSuperNightExif;
import com.android.gallery3d.exif.ExifHelper;
import com.android.gallery3d.ui.GLCanvas;
import com.android.lens.LensAgent;
import com.android.zxing.CacheImageDecoder;
import com.android.zxing.Decoder;
import com.android.zxing.DocumentDecoder;
import com.android.zxing.PreviewDecodeManager;
import com.android.zxing.PreviewImage;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.miui.filtersdk.filter.helper.FilterFactory;
import com.miui.filtersdk.filter.helper.FilterType;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.BaseBoostFramework;
import com.xiaomi.camera.core.BoostFrameworkImpl;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.liveshot.CircularMediaRecorder;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.rx.CameraSchedulers;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;
import com.xiaomi.ocr.sdk.imgprocess.DocumentProcess;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import com.xiaomi.stat.d;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import miui.text.ExtraTextUtils;

@TargetApi(21)
public class Camera2Module extends BaseModule implements FocusManager2.Listener, ObjectView.ObjectViewListener, Camera2Proxy.CameraMetaDataCallback, ModeProtocol.CameraAction, ModeProtocol.TopConfigProtocol, Camera2Proxy.CameraPreviewCallback, Camera2Proxy.HdrCheckerCallback, Camera2Proxy.ScreenLightCallback, Camera2Proxy.PictureCallback, Camera2Proxy.FaceDetectionCallback, Camera2Proxy.FocusCallback, Camera2Proxy.BeautyBodySlimCountCallback, Camera2Proxy.SuperNightCallback, Camera2Proxy.LivePhotoResultCallback, Camera2Proxy.MagneticDetectedCallback, Camera2Proxy.NearRangeModeCallback, Camera2Proxy.ASDSceneCallback, Camera2Proxy.AnchorPreviewCallback {
    private static final int BURST_SHOOTING_DELAY = 0;
    private static final long CAPTURE_DURATION_THRESHOLD = 12000;
    private static final boolean DEBUG_ENABLE_DYNAMIC_HHT_FAST_SHOT = SystemProperties.getBoolean("debug.vendor.camera.app.dynamic.hht.quickshot.enable", true);
    private static final int MAX_HEIC_BURST_CAPTURE_COUNT = 50;
    private static final float MOON_AF_DISTANCE = 0.5f;
    private static final int MSG_START_RECORDING = 4097;
    private static final int REQUEST_CROP = 1000;
    private static final String TAG = "Camera2Module";
    private static final int UW_MAX_BURST_SHOT_NUM = 30;
    private static boolean mIsBeautyFrontOn = false;
    private static final String sTempCropFilename = "crop-temp";
    private final CameraSize SIZE_108M = new CameraSize(12032, 9024);
    private float[] curGyroscope;
    private volatile boolean isDetectedInHdr;
    private volatile boolean isResetFromMutex = false;
    private boolean isSilhouette;
    private float[] lastGyroscope;
    private boolean m3ALocked;
    private float mAECLux;
    private int mAFEndLogTimes;
    private boolean mAIWatermarkEnable = false;
    private Disposable mAiSceneDisposable;
    private boolean mAiSceneEnabled;
    private FlowableEmitter<CaptureResult> mAiSceneFlowableEmitter;
    private String mAlgorithmName;
    private float[] mApertures;
    private MarshalQueryableASDScene.ASDScene[] mAsdScenes;
    private boolean mAutoHDRTargetState;
    private BeautyValues mBeautyValues;
    private boolean mBlockQuickShot = (!CameraSettings.isCameraQuickShotEnable());
    private Intent mBroadcastIntent;
    private Disposable mBurstDisposable;
    private ObservableEmitter mBurstEmitter;
    private long mBurstNextDelayTime = 0;
    private long mBurstStartTime;
    private CacheImageDecoder mCacheImageDecoder;
    private ModeProtocol.CameraClickObservable.ClickObserver mCameraClickObserverAction = new ModeProtocol.CameraClickObservable.ClickObserver() {
        /* class com.android.camera.module.Camera2Module.AnonymousClass1 */

        @Override // com.android.camera.protocol.ModeProtocol.CameraClickObservable.ClickObserver
        public void action() {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.directlyHideTips();
            }
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(8, 0, 0);
            }
        }

        @Override // com.android.camera.protocol.ModeProtocol.CameraClickObservable.ClickObserver
        public int getObserver() {
            return 161;
        }
    };
    private final Object mCameraDeviceLock = new Object();
    private CameraTimer mCameraTimer;
    private long mCaptureStartTime;
    private String mCaptureWaterMarkStr;
    private AbstractPriorityChain mChain = null;
    private CircularMediaRecorder mCircularMediaRecorder = null;
    private final Object mCircularMediaRecorderStateLock = new Object();
    private boolean mConfigRawStream;
    private CountDownTimer mCountDownTimer;
    private BaseBoostFramework mCpuBoost;
    private String mCropValue;
    private int mCurrentAiScene;
    private int mCurrentAsdScene = -1;
    private int mCurrentDetectedScene;
    private String mDebugFaceInfos;
    private boolean mEnableParallelSession;
    private boolean mEnableShot2Gallery;
    private boolean mEnabledPreviewThumbnail;
    private boolean mEnteringMoonMode;
    protected boolean mFaceDetected;
    private boolean mFaceDetectionEnabled;
    private boolean mFaceDetectionStarted;
    private FaceAnalyzeInfo mFaceInfo;
    private boolean mFirstCreateCapture;
    private int mFixedShot2ShotTime = -1;
    private float[] mFocalLengths;
    private FocusManager2 mFocusManager;
    private FunctionParseAiScene mFunctionParseAiScene;
    private boolean mHasAiSceneFilterEffect;
    private boolean mHdrCheckEnabled;
    private String[] mIDCardPaths = new String[2];
    private boolean mIsAiConflict;
    private volatile boolean mIsAiShutterOn = false;
    private boolean mIsBeautyBodySlimOn;
    private boolean mIsCurrentLensEnabled;
    private boolean mIsFaceConflict;
    private boolean mIsGenderAgeOn;
    private volatile boolean mIsGoogleLensAvailable;
    private boolean mIsISORight4HWMFNR = false;
    private boolean mIsImageCaptureIntent;
    private boolean mIsInHDR;
    private boolean mIsLLSNeeded;
    private boolean mIsMacroModeEnable;
    private boolean mIsMagicMirrorOn;
    private boolean mIsMicrophoneEnabled = true;
    private boolean mIsMoonMode;
    private boolean mIsNearRangeMode;
    private boolean mIsNearRangeModeUITip;
    private boolean mIsNeedNightHDR;
    private boolean mIsPortraitLightingOn;
    private boolean mIsSaveCaptureImage;
    private int mIsShowLyingDirectHintStatus = -1;
    private boolean mIsShutterLongClickRecording;
    private boolean mIsStartCount;
    private boolean mIsTheShutterTime = false;
    private boolean mIsUltraWideConflict;
    private int mJpegRotation;
    private boolean mKeepBitmapTexture;
    private long mLastAsdSceneShowTime = 0;
    private long mLastCaptureTime;
    private long mLastChangeSceneTime = 0;
    private String mLastFlashMode;
    private String mLastHdrMode;
    private int mLightFilterId;
    private LiveMediaRecorder mLiveMediaRecorder;
    private Queue<LivePhotoResult> mLivePhotoQueue = new LinkedBlockingQueue(120);
    private boolean mLiveShotEnabled;
    private Location mLocation;
    private boolean mLongPressedAutoFocus;
    private ModeProtocol.MagneticSensorDetect mMagneticSensorDetect;
    protected int mMaxVideoDurationInMs = 15000;
    private final LiveMediaRecorder.EncoderListener mMediaEncoderListener = new LiveMediaRecorder.EncoderListener(this);
    private Disposable mMetaDataDisposable;
    private FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private boolean mMotionDetected;
    private boolean mMultiSnapStatus = false;
    private boolean mMultiSnapStopRequest = false;
    private boolean mNeedAutoFocus;
    private int mNormalFilterId;
    private long mOnResumeTime;
    private int mOperatingMode;
    private boolean mParallelSessionConfigured = false;
    private final Object mParallelSessionLock = new Object();
    private boolean mPendingMultiCapture;
    private ArrayList<SaveVideoTask> mPendingSaveTaskList = new ArrayList<>();
    private MarshalQueryableSuperNightExif.SuperNightExif mPreviewSuperNightExifInfo;
    private boolean mQuickCapture;
    private boolean mQuickShotAnimateEnable = false;
    private int mReceivedJpegCallbackNum = 0;
    protected int mRecordingStartDelay = 250;
    protected long mRecordingStartTime;
    private long mRequestStartTime;
    private DocumentProcess.RotateFlags mRotateFlags = DocumentProcess.RotateFlags.ROTATE_90;
    private Uri mSaveUri;
    private String mSceneMode;
    private SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.Camera2Module.AnonymousClass16 */
        private ModeProtocol.TopAlert mTopAlert;

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return Camera2Module.this.isAlive() && Camera2Module.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
            if (!Camera2Module.this.mPaused && CameraSettings.isEdgePhotoEnable()) {
                Camera2Module.this.mActivity.getEdgeShutterView().onDeviceMoving();
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!Camera2Module.this.mPaused && Camera2Module.this.mFocusManager != null && !Camera2Module.this.mMultiSnapStatus && !Camera2Module.this.is3ALocked() && !Camera2Module.this.mMainProtocol.isEvAdjusted(true) && !Camera2Module.this.mIsMoonMode) {
                Camera2Module.this.mFocusManager.onDeviceKeepMoving(d2);
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
            if (!Camera2Module.this.mPaused) {
                int i = Camera2Module.this.mIsShowLyingDirectHintStatus;
                Camera2Module camera2Module = Camera2Module.this;
                int i2 = camera2Module.mOrientationCompensation;
                int i3 = z == true ? 1 : 0;
                int i4 = z == true ? 1 : 0;
                if (i != i3 + i2) {
                    camera2Module.mIsShowLyingDirectHintStatus = i2 + z;
                    Camera2Module.this.mHandler.removeMessages(58);
                    if (this.mTopAlert == null) {
                        this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    }
                    ModeProtocol.TopAlert topAlert = this.mTopAlert;
                    if (topAlert != null ? topAlert.isContainAlertRecommendTip(R.string.dirty_tip_toast, R.string.pic_flaw_blink_one, R.string.pic_flaw_blink_more, R.string.pic_flaw_cover) : false) {
                        z = 0;
                    }
                    if (z != 0) {
                        Camera2Module camera2Module2 = Camera2Module.this;
                        Handler handler = camera2Module2.mHandler;
                        handler.sendMessageDelayed(handler.obtainMessage(58, 1, camera2Module2.mOrientationCompensation), 400);
                        Camera2Module camera2Module3 = Camera2Module.this;
                        Handler handler2 = camera2Module3.mHandler;
                        handler2.sendMessageDelayed(handler2.obtainMessage(58, 0, camera2Module3.mOrientationCompensation), 5000);
                        return;
                    }
                    Camera2Module camera2Module4 = Camera2Module.this;
                    Handler handler3 = camera2Module4.mHandler;
                    handler3.sendMessageDelayed(handler3.obtainMessage(58, 0, camera2Module4.mOrientationCompensation), 500);
                }
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceOrientationChanged(float f, boolean z) {
            Camera2Module camera2Module = Camera2Module.this;
            camera2Module.mDeviceRotation = !z ? f : (float) camera2Module.mOrientation;
            if (Camera2Module.this.getCameraState() != 3 || Camera2Module.this.isGradienterOn) {
                EffectController instance = EffectController.getInstance();
                Camera2Module camera2Module2 = Camera2Module.this;
                instance.setDeviceRotation(z, Util.getShootRotation(camera2Module2.mActivity, camera2Module2.mDeviceRotation));
            }
            Camera2Module.this.mHandler.removeMessages(33);
            if (!Camera2Module.this.mPaused && !z && f != -1.0f) {
                int roundOrientation = Util.roundOrientation(Math.round(f), Camera2Module.this.mOrientation);
                Camera2Module.this.mHandler.obtainMessage(33, roundOrientation, (Util.getDisplayRotation(Camera2Module.this.mActivity) + roundOrientation) % 360).sendToTarget();
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
                Camera2Module camera2Module = Camera2Module.this;
                camera2Module.lastGyroscope = camera2Module.curGyroscope;
                Camera2Module.this.curGyroscope = sensorEvent.values;
            } else if (type == 14 && (magneticSensorDetect = (ModeProtocol.MagneticSensorDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(2576)) != null) {
                magneticSensorDetect.onMagneticSensorChanged(sensorEvent);
            }
        }
    };
    private LocalParallelService.ServiceStatusListener mServiceStatusListener;
    private ISessionStatusCallBackListener mSessionStatusCallbackListener = new ISessionStatusCallBackListener.Stub() {
        /* class com.android.camera.module.Camera2Module.AnonymousClass24 */

        @Override // com.xiaomi.protocol.ISessionStatusCallBackListener
        public void onSessionStatusFlawResultData(int i, final int i2) throws RemoteException {
            String str = Camera2Module.TAG;
            Log.d(str, "resultId:" + i + ",flawResult:" + i2);
            if (HybridZoomingSystem.isZoomRatioNone(Camera2Module.this.getZoomRatio(), Camera2Module.this.isFrontCamera()) && !CameraSettings.isMacroModeEnabled(Camera2Module.this.getModuleIndex())) {
                final FragmentTopConfig fragmentTopConfig = (FragmentTopConfig) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (fragmentTopConfig == null || !fragmentTopConfig.isCurrentRecommendTipText(R.string.super_night_hint)) {
                    Camera2Proxy camera2Proxy = Camera2Module.this.mCamera2Device;
                    if (camera2Proxy == null || !camera2Proxy.isCaptureBusy(true)) {
                        AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                            /* class com.android.camera.module.Camera2Module.AnonymousClass24.AnonymousClass1 */

                            public void run() {
                                HashMap hashMap = new HashMap();
                                int i = i2;
                                if (i != 0) {
                                    if (i == 1) {
                                        FragmentTopConfig fragmentTopConfig = fragmentTopConfig;
                                        if (fragmentTopConfig != null) {
                                            fragmentTopConfig.alertAiDetectTipHint(0, R.string.pic_flaw_cover, 3000);
                                        }
                                        hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_FLAW_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_FLAW_COVER);
                                    } else if (i == 2) {
                                        FragmentTopConfig fragmentTopConfig2 = fragmentTopConfig;
                                        if (fragmentTopConfig2 != null) {
                                            fragmentTopConfig2.alertAiDetectTipHint(0, R.string.pic_flaw_blink_one, 3000);
                                        }
                                        hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_FLAW_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_FLAW_BLINK_ONE);
                                    } else if (i == 3) {
                                        FragmentTopConfig fragmentTopConfig3 = fragmentTopConfig;
                                        if (fragmentTopConfig3 != null) {
                                            fragmentTopConfig3.alertAiDetectTipHint(0, R.string.pic_flaw_blink_more, 3000);
                                        }
                                        hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_FLAW_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_FLAW_BLINK_MORE);
                                    }
                                }
                                MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
                            }
                        });
                    }
                }
            }
        }
    };
    private int mShootOrientation;
    private float mShootRotation;
    private boolean mShouldDoMFNR;
    private boolean mShowLLSHint;
    private boolean mShowSuperNightHint;
    private long mShutterCallbackTime;
    private long mShutterLag;
    private Disposable mSuperNightDisposable;
    private Consumer<Integer> mSuperNightEventConsumer;
    private boolean mSupportAnchorFrameAsThumbnail;
    private boolean mSupportFlashHDR;
    private boolean mSupportShotBoost;
    private int mTotalJpegCallbackNum = 1;
    private TouchEventView mTouchEventView;
    private CameraSize mTuningBufferSize;
    private volatile boolean mUltraWideAELocked;
    private boolean mUpdateImageTitle = false;
    private boolean mUpscaleImageWithSR;
    private CameraSize mVideoSize;
    private boolean mVolumeLongPress = false;
    private volatile boolean mWaitSaveFinish;
    private boolean mWaitingSuperNightResult;
    private WatermarkItem mWatermarkItem;
    private ZoomMapController mZoomMapController;

    /* access modifiers changed from: private */
    public static class AsdSceneConsumer implements Consumer<Integer> {
        private WeakReference<BaseModule> mModule;

        public AsdSceneConsumer(BaseModule baseModule) {
            this.mModule = new WeakReference<>(baseModule);
        }

        public void accept(Integer num) throws Exception {
            WeakReference<BaseModule> weakReference = this.mModule;
            if (weakReference != null && weakReference.get() != null) {
                BaseModule baseModule = this.mModule.get();
                if (baseModule instanceof Camera2Module) {
                    ((Camera2Module) baseModule).consumeAsdSceneResult(num.intValue());
                }
            }
        }
    }

    private final class JpegQuickPictureCallback extends Camera2Proxy.PictureCallbackWrapper {
        String mBurstShotTitle;
        boolean mDropped;
        Location mLocation;
        String mPressDownTitle;
        int mSavedJpegCallbackNum;

        public JpegQuickPictureCallback(Location location) {
            this.mLocation = location;
        }

        private String getBurstShotTitle() {
            String str;
            if (Camera2Module.this.mUpdateImageTitle && (str = this.mBurstShotTitle) != null && this.mSavedJpegCallbackNum == 1) {
                this.mPressDownTitle = str;
                this.mBurstShotTitle = null;
            }
            if (this.mBurstShotTitle == null) {
                long currentTimeMillis = System.currentTimeMillis();
                this.mBurstShotTitle = Util.createJpegName(currentTimeMillis);
                if (this.mBurstShotTitle.length() != 19) {
                    this.mBurstShotTitle = Util.createJpegName(currentTimeMillis + 1000);
                }
            }
            return this.mBurstShotTitle + "_BURST" + this.mSavedJpegCallbackNum;
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
            int i;
            int i2;
            if (!Camera2Module.this.mPaused && bArr != null && Camera2Module.this.mReceivedJpegCallbackNum < Camera2Module.this.mTotalJpegCallbackNum && Camera2Module.this.mMultiSnapStatus) {
                if (this.mSavedJpegCallbackNum == 1 && !Camera2Module.this.mMultiSnapStopRequest) {
                    Camera2Module.this.mActivity.getImageSaver().updateImage(getBurstShotTitle(), this.mPressDownTitle);
                }
                if (Storage.isLowStorageAtLastPoint()) {
                    if (Camera2Module.this.mMultiSnapStatus) {
                        Camera2Module.this.stopMultiSnap();
                    }
                    Log.d(Camera2Module.TAG, "onPictureTaken: stop multiple shot due to low storage");
                    return;
                }
                Camera2Module.access$904(Camera2Module.this);
                if (!Camera2Module.this.mActivity.getImageSaver().isSaveQueueFull()) {
                    this.mSavedJpegCallbackNum++;
                    Camera2Module.this.playCameraSound(4);
                    ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                    Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(this.mSavedJpegCallbackNum));
                    int orientation = ExifHelper.getOrientation(bArr);
                    if ((Camera2Module.this.mJpegRotation + orientation) % 180 == 0) {
                        i2 = Camera2Module.this.mPictureSize.getWidth();
                        i = Camera2Module.this.mPictureSize.getHeight();
                    } else {
                        i2 = Camera2Module.this.mPictureSize.getHeight();
                        i = Camera2Module.this.mPictureSize.getWidth();
                    }
                    Camera2Module.this.mActivity.getImageSaver().addImage(bArr, (Camera2Module.this.mReceivedJpegCallbackNum != 1 || Camera2Module.this.mMultiSnapStopRequest) && (Camera2Module.this.mReceivedJpegCallbackNum == Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped), getBurstShotTitle(), null, System.currentTimeMillis(), null, this.mLocation, i2, i, null, orientation, false, false, true, false, false, null, Camera2Module.this.getPictureInfo(), -1, captureResult);
                    this.mDropped = false;
                } else {
                    Log.e(Camera2Module.TAG, "CaptureBurst queue full and drop " + Camera2Module.this.mReceivedJpegCallbackNum);
                    this.mDropped = true;
                    if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum) {
                        Camera2Module.this.mActivity.getThumbnailUpdater().getLastThumbnailUncached();
                    }
                }
                if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped) {
                    Camera2Module.this.stopMultiSnap();
                }
            }
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTakenFinished(boolean z) {
            Camera2Module.this.stopMultiSnap();
            Camera2Module.this.mBurstEmitter.onComplete();
        }
    }

    private final class JpegRepeatingCaptureCallback extends Camera2Proxy.PictureCallbackWrapper {
        String mBurstShotTitle;
        private boolean mDropped;
        private WeakReference<Camera2Module> mModule;
        ParallelTaskDataParameter mParallelParameter = null;
        String mPressDownTitle;

        public JpegRepeatingCaptureCallback(Camera2Module camera2Module) {
            this.mModule = new WeakReference<>(camera2Module);
        }

        private String getBurstShotTitle() {
            if (Camera2Module.this.mUpdateImageTitle && this.mBurstShotTitle != null && Camera2Module.this.mReceivedJpegCallbackNum == 1) {
                this.mPressDownTitle = this.mBurstShotTitle;
                this.mBurstShotTitle = null;
            }
            if (this.mBurstShotTitle == null) {
                long currentTimeMillis = System.currentTimeMillis();
                this.mBurstShotTitle = Util.createJpegName(currentTimeMillis);
                if (this.mBurstShotTitle.length() != 19) {
                    this.mBurstShotTitle = Util.createJpegName(currentTimeMillis + 1000);
                }
            }
            return this.mBurstShotTitle + "_BURST" + Camera2Module.this.mReceivedJpegCallbackNum;
        }

        private boolean tryCheckNeedStop() {
            if (!Storage.isLowStorageAtLastPoint()) {
                return false;
            }
            if (!Camera2Module.this.mMultiSnapStatus) {
                return true;
            }
            Camera2Module.this.stopMultiSnap();
            return true;
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z, boolean z2, boolean z3, boolean z4) {
            boolean z5 = true;
            if (!Camera2Module.this.mEnableParallelSession || Camera2Module.this.mPaused || Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || !Camera2Module.this.mMultiSnapStatus) {
                Log.d(Camera2Module.TAG, "onCaptureStart: revNum = " + Camera2Module.this.mReceivedJpegCallbackNum + " paused = " + Camera2Module.this.mPaused + " status = " + Camera2Module.this.mMultiSnapStatus);
                if (DataRepository.dataItemFeature().Ol()) {
                    parallelTaskData.setRequireTuningData(true);
                }
                parallelTaskData.setAbandoned(true);
                return parallelTaskData;
            }
            if (Camera2Module.this.mReceivedJpegCallbackNum == 1 && !Camera2Module.this.mMultiSnapStopRequest) {
                if (!Camera2Module.this.is3ALocked()) {
                    Camera2Module.this.mFocusManager.onShutter();
                }
                Camera2Module.this.mActivity.getImageSaver().updateImage(getBurstShotTitle(), this.mPressDownTitle);
            }
            if (tryCheckNeedStop()) {
                Log.d(Camera2Module.TAG, "onCaptureStart: need stop multi capture, return null");
                return null;
            }
            if (this.mParallelParameter == null) {
                Log.d(Camera2Module.TAG, "onCaptureStart: inputSize = " + cameraSize);
                if ((Camera2Module.this.isIn3OrMoreSatMode() || Camera2Module.this.isInMultiSurfaceSatMode()) && (!cameraSize.equals(Camera2Module.this.mPictureSize) || c.isMTKPlatform())) {
                    Camera2Module camera2Module = Camera2Module.this;
                    camera2Module.mPictureSize = cameraSize;
                    camera2Module.updateOutputSize(cameraSize);
                }
                CameraSize cameraSize2 = Camera2Module.this.mOutputPictureSize;
                Size sizeObject = cameraSize2 == null ? cameraSize.toSizeObject() : cameraSize2.toSizeObject();
                Log.d(Camera2Module.TAG, "onCaptureStart: outputSize = " + sizeObject);
                boolean isHeicImageFormat = CompatibilityUtils.isHeicImageFormat(Camera2Module.this.mOutputPictureFormat);
                int clampQuality = Camera2Module.this.clampQuality(CameraSettings.getEncodingQuality(true).toInteger(isHeicImageFormat));
                Log.d(Camera2Module.TAG, "onCaptureStart: isHeic = " + isHeicImageFormat + ", quality = " + clampQuality);
                if (isHeicImageFormat && Camera2Module.this.mCameraCapabilities.isSupportZeroDegreeOrientationImage() && (Camera2Module.this.mJpegRotation == 90 || Camera2Module.this.mJpegRotation == 270)) {
                    Size size = new Size(sizeObject.getHeight(), sizeObject.getWidth());
                    Log.d(Camera2Module.TAG, "onCaptureStart: switched outputSize: " + size);
                    sizeObject = size;
                }
                Location location = Camera2Module.this.mActivity.getCameraIntentManager().checkIntentLocationPermission(Camera2Module.this.mActivity) ? Camera2Module.this.mLocation : null;
                ParallelTaskDataParameter.Builder filterId = new ParallelTaskDataParameter.Builder(Camera2Module.this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject, Camera2Module.this.mOutputPictureFormat).setHasDualWaterMark(false).setMirror(Camera2Module.this.isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(FilterInfo.FILTER_ID_NONE);
                int i = Camera2Module.this.mOrientation;
                if (-1 == i) {
                    i = 0;
                }
                this.mParallelParameter = filterId.setOrientation(i).setJpegRotation(Camera2Module.this.mJpegRotation).setShootRotation(Camera2Module.this.mShootRotation).setShootOrientation(Camera2Module.this.mShootOrientation).setSupportZeroDegreeOrientationImage(Camera2Module.this.mCameraCapabilities.isSupportZeroDegreeOrientationImage()).setLocation(location).setFrontCamera(Camera2Module.this.isFrontCamera()).setBokehFrontCamera(Camera2Module.this.isPictureUseDualFrontCamera()).setAlgorithmName(Camera2Module.this.mAlgorithmName).setPictureInfo(Camera2Module.this.getPictureInfo()).setSuffix(Camera2Module.this.getSuffix()).setSaveGroupshotPrimitive(false).setDeviceWatermarkParam(Camera2Module.this.getDeviceWaterMarkParam()).setJpegQuality(clampQuality).setReprocessBurstShotPicture(Camera2Module.this.isZoomRatioBetweenUltraAndWide() && DataRepository.dataItemFeature().ki()).build();
            }
            parallelTaskData.fillParameter(this.mParallelParameter);
            if (DataRepository.dataItemFeature().Ol()) {
                parallelTaskData.setRequireTuningData(true);
            }
            if (!Camera2Module.this.mActivity.getImageSaver().isSaveQueueFull()) {
                Camera2Module.access$904(Camera2Module.this);
                Camera2Module.this.playCameraSound(4);
                ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                Log.d(Camera2Module.TAG, "onCaptureStart: revNum = " + Camera2Module.this.mReceivedJpegCallbackNum);
                Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(Camera2Module.this.mReceivedJpegCallbackNum));
                if (Camera2Module.this.mReceivedJpegCallbackNum <= Camera2Module.this.mTotalJpegCallbackNum) {
                    String generateFilepath4Image = Storage.generateFilepath4Image(getBurstShotTitle(), CompatibilityUtils.isHeicImageFormat(Camera2Module.this.mOutputPictureFormat));
                    Log.d(Camera2Module.TAG, "onCaptureStart: savePath = " + generateFilepath4Image);
                    parallelTaskData.setSavePath(generateFilepath4Image);
                    if (Camera2Module.this.mReceivedJpegCallbackNum != Camera2Module.this.mTotalJpegCallbackNum && !Camera2Module.this.mMultiSnapStopRequest && !this.mDropped) {
                        z5 = false;
                    }
                    parallelTaskData.setNeedThumbnail(z5);
                    Camera2Module.this.beginParallelProcess(parallelTaskData, false);
                    this.mDropped = false;
                    if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped) {
                        Camera2Module.this.stopMultiSnap();
                    }
                    return parallelTaskData;
                }
            } else {
                Log.e(Camera2Module.TAG, "onCaptureStart: queue full and drop " + Camera2Module.this.mReceivedJpegCallbackNum);
                this.mDropped = true;
                if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum) {
                    Camera2Module.this.mActivity.getThumbnailUpdater().getLastThumbnailUncached();
                }
            }
            parallelTaskData = null;
            Camera2Module.this.stopMultiSnap();
            return parallelTaskData;
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTakenFinished(boolean z) {
            if (this.mModule.get() != null) {
                this.mModule.get().onBurstPictureTakenFinished(z);
            } else {
                Log.e(Camera2Module.TAG, "callback onShotFinished null");
            }
        }
    }

    /* access modifiers changed from: private */
    public static class LocalParallelServiceStatusListener implements LocalParallelService.ServiceStatusListener {
        private final WeakReference<Camera2Module> mCamera2ModuleRef;
        private final WeakReference<Camera2Proxy> mCameraDevice;

        LocalParallelServiceStatusListener(Camera2Proxy camera2Proxy, Camera2Module camera2Module) {
            this.mCameraDevice = new WeakReference<>(camera2Proxy);
            this.mCamera2ModuleRef = new WeakReference<>(camera2Module);
        }

        @Override // com.android.camera.LocalParallelService.ServiceStatusListener
        public void onImagePostProcessEnd(ParallelTaskData parallelTaskData) {
            Camera2Module camera2Module = this.mCamera2ModuleRef.get();
            if (camera2Module != null && parallelTaskData != null && parallelTaskData.isJpegDataReady()) {
                if (camera2Module.mIsImageCaptureIntent || (DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode() && !DataRepository.dataItemFeature().hm())) {
                    camera2Module.onPictureTakenFinished(true);
                }
            }
        }

        @Override // com.android.camera.LocalParallelService.ServiceStatusListener
        public void onImagePostProcessStart(ParallelTaskData parallelTaskData) {
            Camera2Module camera2Module = this.mCamera2ModuleRef.get();
            if (camera2Module != null && 4 != parallelTaskData.getAlgoType()) {
                if (!camera2Module.mIsImageCaptureIntent && (!DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode() || DataRepository.dataItemFeature().hm())) {
                    camera2Module.onPictureTakenFinished(true);
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
        private WeakReference<Camera2Module> mModule;

        public MainHandler(Camera2Module camera2Module, Looper looper) {
            super(looper);
            this.mModule = new WeakReference<>(camera2Module);
        }

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void handleMessage(Message message) {
            Camera2Proxy camera2Proxy;
            Camera2Module camera2Module = this.mModule.get();
            if (camera2Module != null) {
                if (!camera2Module.isCreated()) {
                    removeCallbacksAndMessages(null);
                } else if (camera2Module.getActivity() != null) {
                    int i = message.what;
                    if (i == 2) {
                        camera2Module.getWindow().clearFlags(128);
                    } else if (i == 4) {
                        camera2Module.checkActivityOrientation();
                        if (SystemClock.uptimeMillis() - camera2Module.mOnResumeTime < 5000) {
                            sendEmptyMessageDelayed(4, 100);
                        }
                    } else if (i == 17) {
                        removeMessages(17);
                        removeMessages(2);
                        camera2Module.getWindow().addFlags(128);
                        sendEmptyMessageDelayed(2, (long) camera2Module.getScreenDelay());
                    } else if (i == 31) {
                        camera2Module.setOrientationParameter();
                    } else if (i != 33) {
                        boolean z = true;
                        if (i == 35) {
                            boolean z2 = message.arg1 > 0;
                            if (message.arg2 <= 0) {
                                z = false;
                            }
                            camera2Module.handleUpdateFaceView(z2, z);
                        } else if (i == 4097) {
                            camera2Module.startVideoRecording();
                        } else if (i == 44) {
                            camera2Module.restartModule();
                        } else if (i != 45) {
                            switch (i) {
                                case 9:
                                    camera2Module.mMainProtocol.initializeFocusView(camera2Module);
                                    return;
                                case 10:
                                    break;
                                case 11:
                                    return;
                                default:
                                    switch (i) {
                                        case 48:
                                            camera2Module.setCameraState(1);
                                            return;
                                        case 49:
                                            if (camera2Module.isAlive()) {
                                                camera2Module.stopMultiSnap();
                                                camera2Module.mBurstEmitter.onComplete();
                                                return;
                                            }
                                            return;
                                        case 50:
                                            Log.w(Camera2Module.TAG, "Oops, capture timeout later release timeout!");
                                            camera2Module.onPictureTakenFinished(false);
                                            return;
                                        case 51:
                                            break;
                                        case 52:
                                            camera2Module.onShutterButtonClick(camera2Module.getTriggerMode());
                                            return;
                                        default:
                                            switch (i) {
                                                case 56:
                                                    ModeProtocol.MainContentProtocol mainContentProtocol = camera2Module.mMainProtocol;
                                                    if (mainContentProtocol != null && mainContentProtocol.isFaceExists(1) && camera2Module.mMainProtocol.isFocusViewVisible() && (camera2Proxy = camera2Module.mCamera2Device) != null && 4 == camera2Proxy.getFocusMode()) {
                                                        camera2Module.mMainProtocol.clearFocusView(7);
                                                        return;
                                                    }
                                                    return;
                                                case 57:
                                                    PreviewDecodeManager.getInstance().reset();
                                                    return;
                                                case 58:
                                                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                                                    if (configChanges != null) {
                                                        int i2 = camera2Module.mOrientationCompensation;
                                                        configChanges.configRotationChange(message.arg1, (360 - (i2 >= 0 ? i2 % 360 : (i2 % 360) + 360)) % 360);
                                                        return;
                                                    }
                                                    return;
                                                case 59:
                                                    Log.d(Camera2Module.TAG, "receive MSG_FIXED_SHOT2SHOT_TIME_OUT");
                                                    camera2Module.resetStatusToIdle();
                                                    return;
                                                case 60:
                                                    Log.d(Camera2Module.TAG, "fallback timeout");
                                                    camera2Module.mIsSatFallback = 0;
                                                    camera2Module.mFallbackProcessed = false;
                                                    camera2Module.mLastSatFallbackRequestId = -1;
                                                    if (camera2Module.mWaitingSnapshot && camera2Module.getCameraState() == 1) {
                                                        camera2Module.mWaitingSnapshot = false;
                                                        sendEmptyMessage(62);
                                                        return;
                                                    }
                                                    return;
                                                case 61:
                                                    Log.d(Camera2Module.TAG, "wait save finish timeout");
                                                    camera2Module.mWaitSaveFinish = false;
                                                    camera2Module.showOrHideLoadingProgress(false, true);
                                                    return;
                                                case 62:
                                                    camera2Module.onWaitingFocusFinished();
                                                    return;
                                                case 63:
                                                    ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                                                    if (cameraAction != null) {
                                                        cameraAction.onShutterButtonClick(120);
                                                        return;
                                                    }
                                                    return;
                                                default:
                                                    throw new RuntimeException("no consumer for this message: " + message.what);
                                            }
                                    }
                            }
                            if (!camera2Module.mActivity.isActivityPaused()) {
                                camera2Module.mOpenCameraFail = true;
                                camera2Module.onCameraException();
                            }
                        } else {
                            camera2Module.setActivity(null);
                        }
                    } else {
                        camera2Module.setOrientation(message.arg1, message.arg2);
                    }
                }
            }
        }
    }

    private static final class SaveVideoTask {
        public ContentValues contentValues;
        public String videoPath;

        public SaveVideoTask(String str, ContentValues contentValues2) {
            this.videoPath = str;
            this.contentValues = contentValues2;
        }
    }

    /* access modifiers changed from: private */
    public static class SuperNightEventConsumer implements Consumer<Integer> {
        private final WeakReference<Camera2Module> mCamera2ModuleRef;

        private SuperNightEventConsumer(Camera2Module camera2Module) {
            this.mCamera2ModuleRef = new WeakReference<>(camera2Module);
        }

        public void accept(Integer num) throws Exception {
            Camera2Module camera2Module = this.mCamera2ModuleRef.get();
            if (camera2Module != null && camera2Module.isAlive()) {
                int intValue = num.intValue();
                if (intValue == 300) {
                    Log.d(Camera2Module.TAG, "SuperNight: show capture instruction hint");
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        topAlert.alertAiDetectTipHint(0, R.string.super_night_toast, -1);
                    }
                } else if (intValue == 2000) {
                    Log.d(Camera2Module.TAG, "SuperNight: trigger shutter animation, sound and post saving");
                    camera2Module.mWaitingSuperNightResult = true;
                    camera2Module.animateCapture();
                    camera2Module.playCameraSound(0);
                    ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                    if (recordState != null) {
                        recordState.onPostSavingStart();
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(ModeProtocol.ActionProcessing actionProcessing, Bitmap bitmap, float[] fArr) {
        if (actionProcessing != null) {
            actionProcessing.showDocumentReviewViews(bitmap, fArr);
        } else {
            Log.d(TAG, "showDocumentPreview: actionProcessing == null");
        }
    }

    static /* synthetic */ int access$904(Camera2Module camera2Module) {
        int i = camera2Module.mReceivedJpegCallbackNum + 1;
        camera2Module.mReceivedJpegCallbackNum = i;
        return i;
    }

    private boolean anchorFrameWhenPortrait() {
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        return this.mModuleIndex == 171 && ((cameraCapabilities != null ? cameraCapabilities.getPortraitLightingVersion() : 1) > 1 || !this.mIsPortraitLightingOn);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void animateCapture() {
        if (!this.mIsImageCaptureIntent) {
            this.mActivity.getCameraScreenNail().animateCapture(getCameraRotation());
        }
    }

    private void applyBacklightEffect() {
        trackAISceneChanged(this.mModuleIndex, 23);
        setAiSceneEffect(23);
        updateHDR("normal");
        this.mCamera2Device.setASDScene(23);
        resetEvValue();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void beginParallelProcess(ParallelTaskData parallelTaskData, boolean z) {
        String str = TAG;
        Log.i(str, "algo begin: " + parallelTaskData.getSavePath() + " | " + Thread.currentThread().getName());
        if (this.mServiceStatusListener == null) {
            this.mServiceStatusListener = new LocalParallelServiceStatusListener(this.mCamera2Device, this);
            AlgoConnector.getInstance().setServiceStatusListener(this.mServiceStatusListener);
        }
    }

    private void blockSnapClickUntilSaveFinish(boolean z) {
        String str = TAG;
        Log.i(str, "blockSnapClickUntilFinish: " + z);
        this.mWaitSaveFinish = true;
        this.mHandler.sendEmptyMessageDelayed(61, 5000);
        if (z) {
            showOrHideLoadingProgress(true, false);
        }
    }

    private long calculateTimeout(int i) {
        if (i == 167) {
            return (Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) / ExtraTextUtils.MB) + CAPTURE_DURATION_THRESHOLD;
        }
        if (i == 173 || CameraSettings.isSuperNightOn()) {
            return 24000;
        }
        return CAPTURE_DURATION_THRESHOLD;
    }

    private void callGalleryDocumentPage(String str, String str2, Camera camera) {
        String str3 = TAG;
        Log.i(str3, "callGalleryDocumentPage effect: " + str2);
        Intent intent = new Intent();
        intent.setAction(CameraIntentManager.ACTION_EDIT_DOCOCUMENT_IMAGE);
        intent.setData(Util.photoUri(str));
        intent.putExtra(CameraIntentManager.DOCUMENT_IMAGE_EFFECT, str2);
        if (camera.startFromKeyguard()) {
            intent.putExtra("StartActivityWhenLocked", true);
        }
        if (Util.startActivityForResultCatchException(camera, intent, Util.REQUEST_CODE_OPEN_MIUI_EXTRA_PHOTO)) {
            camera.setJumpFlag(6);
        }
    }

    private void callGalleryIDCardPage(String[] strArr, Camera camera) {
        Log.i(TAG, "callGalleryIDCardPage");
        int entrance = ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).getEntrance();
        Intent intent = new Intent();
        intent.setAction(CameraIntentManager.ACTION_EDIT_IDCARD_IMAGE);
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        arrayList.add(Util.photoUri(strArr[0]));
        arrayList.add(Util.photoUri(strArr[1]));
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        if (camera.startFromKeyguard()) {
            intent.putExtra("StartActivityWhenLocked", true);
        }
        if (Util.startActivityForResultCatchException(camera, intent, Util.REQUEST_CODE_OPEN_MIUI_EXTRA_PHOTO)) {
            camera.setJumpFlag(6);
            ((DataItemGlobal) DataRepository.provider().dataGlobal()).setCurrentMode(entrance);
        }
    }

    private void checkLLS(CaptureResult captureResult) {
        boolean isLLSNeeded = CaptureResultParser.isLLSNeeded(captureResult);
        if (isLLSNeeded != this.mIsLLSNeeded) {
            String str = TAG;
            Log.d(str, "is lls needed = " + isLLSNeeded);
            this.mIsLLSNeeded = isLLSNeeded;
            this.mCamera2Device.setLLS(this.mIsLLSNeeded);
        }
    }

    private void checkMoreFrameCaptureLockAFAE(boolean z) {
        if (this.mCamera2Device == null) {
            Log.w(TAG, "mCamera2Device == null, return");
        } else if (DataRepository.dataItemFeature().Nl()) {
            if (!ModuleManager.isSuperNightScene() && !this.mShowSuperNightHint && ((!this.mMutexModePicker.isHdr() || !isBackCamera()) && !this.mIsLLSNeeded && !this.mCamera2Device.getCameraConfigs().isSuperResolutionEnabled())) {
                return;
            }
            if ((!ModuleManager.isSuperNightScene() && !this.mShowSuperNightHint) || DataRepository.dataItemFeature().jm()) {
                if ((!this.mMutexModePicker.isHdr() || !this.mCamera2Device.useSingleCaptureForHdrPlusMfnr()) && !is3ALocked()) {
                    this.mCamera2Device.setMFLockAfAe(z);
                }
            }
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

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int clampQuality(int i) {
        return this.mModuleIndex == 186 ? EncodingQuality.LOW.toInteger(false) : DataRepository.dataItemRunning().getComponentUltraPixel().isRear108MPSwitchOn() ? Util.clamp(i, 0, 90) : i;
    }

    private void configParallelSession() {
        GraphDescriptorBean graphDescriptorBean;
        BufferFormat bufferFormat;
        int cameraCombinationMode = CameraDeviceUtil.getCameraCombinationMode(Camera2DataContainer.getInstance().getRoleIdByActualId(this.mActualCameraId));
        if (isPortraitMode()) {
            graphDescriptorBean = new GraphDescriptorBean(32770, ((!isDualFrontCamera() || DataRepository.dataItemFeature().xi()) && !isDualCamera() && !isBokehUltraWideBackCamera()) ? 1 : 2, true, cameraCombinationMode);
        } else {
            int i = this.mModuleIndex;
            if (i == 167) {
                graphDescriptorBean = new GraphDescriptorBean(32771, 1, true, cameraCombinationMode);
            } else if (i == 175) {
                graphDescriptorBean = new GraphDescriptorBean(33011, 1, true, cameraCombinationMode);
            } else if (DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode()) {
                graphDescriptorBean = new GraphDescriptorBean(32778, 1, true, cameraCombinationMode);
            } else {
                if (cameraCombinationMode == 0) {
                    cameraCombinationMode = 513;
                }
                graphDescriptorBean = new GraphDescriptorBean(0, 1, true, cameraCombinationMode);
            }
        }
        String str = TAG;
        Log.d(str, "configParallelSession:    streamNbr = " + graphDescriptorBean.getStreamNumber());
        String str2 = TAG;
        Log.d(str2, "configParallelSession:  pictureSize = " + this.mPictureSize);
        String str3 = TAG;
        Log.d(str3, "configParallelSession:   outputSize = " + this.mOutputPictureSize);
        String str4 = TAG;
        Log.d(str4, "configParallelSession: outputFormat = " + this.mOutputPictureFormat);
        if (!DataRepository.dataItemFeature().hl() || !this.mUpscaleImageWithSR) {
            CameraSize cameraSize = this.mPictureSize;
            bufferFormat = new BufferFormat(cameraSize.width, cameraSize.height, 35, graphDescriptorBean);
        } else {
            CameraSize cameraSize2 = this.mOutputPictureSize;
            bufferFormat = new BufferFormat(cameraSize2.width, cameraSize2.height, 35, graphDescriptorBean);
        }
        AlgoConnector.getInstance().getLocalBinder(true).configCaptureSession(bufferFormat);
        synchronized (this.mParallelSessionLock) {
            this.mParallelSessionConfigured = true;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    @MainThread
    private void consumeAiSceneResult(int i, boolean z) {
        ModeProtocol.AIWatermarkDetect aIWatermarkDetect;
        if (this.mAIWatermarkEnable && (aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254)) != null) {
            aIWatermarkDetect.onASDChange(i);
        }
        if (this.mAiSceneEnabled) {
            realConsumeAiSceneResult(i, z);
            int i2 = this.mCurrentAiScene;
            if (!(i2 == -1 || i2 == 23 || i2 == 24 || i2 == 35)) {
                this.mCamera2Device.setASDScene(0);
            }
            resumePreviewInWorkThread();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void consumeAsdSceneResult(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastAsdSceneShowTime > 500 && this.mCurrentAsdScene != i) {
            if ((!isDoingAction() || isInCountDown()) && isAlive() && !this.mActivity.isActivityPaused()) {
                updateAsdSceneResult(i);
                this.mLastAsdSceneShowTime = currentTimeMillis;
            }
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x00f0 */
    private void doAttach() {
        FileOutputStream fileOutputStream;
        Throwable th;
        if (!this.mPaused) {
            byte[] storedJpegData = this.mActivity.getImageSaver().getStoredJpegData();
            if (this.mIsSaveCaptureImage) {
                this.mActivity.getImageSaver().saveStoredData();
            }
            fileOutputStream = null;
            if (this.mCropValue != null) {
                try {
                    File fileStreamPath = this.mActivity.getFileStreamPath(sTempCropFilename);
                    fileStreamPath.delete();
                    FileOutputStream openFileOutput = this.mActivity.openFileOutput(sTempCropFilename, 0);
                    try {
                        openFileOutput.write(storedJpegData);
                        openFileOutput.close();
                        Uri fromFile = Uri.fromFile(fileStreamPath);
                        Util.closeSilently(null);
                        Bundle bundle = new Bundle();
                        if (ComponentRunningTiltValue.TILT_CIRCLE.equals(this.mCropValue)) {
                            bundle.putString("circleCrop", MistatsConstants.BaseEvent.VALUE_TRUE);
                        }
                        Uri uri = this.mSaveUri;
                        if (uri != null) {
                            bundle.putParcelable("output", uri);
                        } else {
                            bundle.putBoolean("return-data", true);
                        }
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setData(fromFile);
                        intent.putExtras(bundle);
                        this.mActivity.startActivityForResult(intent, 1000);
                    } catch (FileNotFoundException unused) {
                        fileOutputStream = openFileOutput;
                        this.mActivity.setResult(0);
                        this.mActivity.finish();
                        Util.closeSilently(fileOutputStream);
                        return;
                    } catch (IOException unused2) {
                        fileOutputStream = openFileOutput;
                        try {
                            this.mActivity.setResult(0);
                            this.mActivity.finish();
                            Util.closeSilently(fileOutputStream);
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            Util.closeSilently(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream = openFileOutput;
                        Util.closeSilently(fileOutputStream);
                        throw th;
                    }
                } catch (FileNotFoundException unused3) {
                    this.mActivity.setResult(0);
                    this.mActivity.finish();
                    Util.closeSilently(fileOutputStream);
                    return;
                } catch (IOException unknown) {
                    this.mActivity.setResult(0);
                    this.mActivity.finish();
                    Util.closeSilently(fileOutputStream);
                    return;
                }
            } else if (this.mSaveUri != null) {
                try {
                    fileOutputStream = CameraAppImpl.getAndroidContext().getContentResolver().openOutputStream(this.mSaveUri);
                    fileOutputStream.write(storedJpegData);
                    fileOutputStream.close();
                    this.mActivity.setResult(-1);
                } catch (Exception e2) {
                    Log.e(TAG, "Exception when doAttach: ", e2);
                } catch (Throwable th4) {
                    this.mActivity.finish();
                    Util.closeSilently(fileOutputStream);
                    throw th4;
                }
                this.mActivity.finish();
                Util.closeSilently(fileOutputStream);
            } else {
                this.mActivity.setResult(-1, new Intent("inline-data").putExtra(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, Util.rotate(Util.makeBitmap(storedJpegData, 51200), ExifHelper.getOrientation(storedJpegData))));
                this.mActivity.finish();
            }
            this.mActivity.getImageSaver().releaseStoredJpegData();
        }
    }

    private void doLaterReleaseIfNeed() {
        if (this.mActivity == null) {
            Log.w(TAG, "doLaterReleaseIfNeed: mActivity is null...");
            return;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(50);
        }
        if (this.mActivity.isActivityPaused()) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            boolean z = camera2Proxy != null && camera2Proxy.isSessionReady();
            if (z) {
                Log.d(TAG, "doLaterRelease");
            } else {
                Log.d(TAG, "doLaterRelease but session is closed");
            }
            this.mActivity.releaseAll(true, z);
        } else if (isDeparted()) {
            Log.w(TAG, "doLaterReleaseIfNeed: isDeparted...");
        } else {
            this.mHandler.post(new va(this));
            if (isTextureExpired()) {
                Log.d(TAG, "doLaterReleaseIfNeed: surfaceTexture expired, restartModule");
                this.mHandler.post(new wa(this));
            }
        }
    }

    private boolean enableFrontMFNR() {
        int i;
        if (c.isMTKPlatform()) {
            return c.xn() && DataRepository.dataItemFeature().Ci();
        }
        if (!c.xn()) {
            return false;
        }
        if (this.mOperatingMode == 32773) {
            return true;
        }
        if (DataRepository.dataItemFeature().Bj() && ((i = this.mOperatingMode) == 32770 || i == 36864)) {
            return true;
        }
        if (DataRepository.dataItemFeature().Ci()) {
            int i2 = this.mOperatingMode;
            if (i2 == 36865) {
                return true;
            }
            return i2 == 36867 ? DataRepository.dataItemFeature().al() : isFrontCamera() && this.mOperatingMode == 36869;
        }
    }

    private boolean enablePreviewAsThumbnail() {
        Camera2Proxy camera2Proxy;
        if (!isAlive()) {
            return false;
        }
        if (this.mModuleIndex == 175) {
            return DataRepository.dataItemFeature().cm();
        }
        if (CameraSettings.isUltraPixelOn()) {
            return false;
        }
        if (this.mEnableParallelSession) {
            return true;
        }
        if (this.mIsPortraitLightingOn) {
            return false;
        }
        if (CameraSettings.isLiveShotOn() || CameraSettings.isPortraitModeBackOn()) {
            return true;
        }
        int i = this.mModuleIndex;
        return i != 167 && i != 173 && !CameraSettings.isSuperNightOn() && !CameraSettings.showGenderAge() && !CameraSettings.isMagicMirrorOn() && !CameraSettings.isTiltShiftOn() && (camera2Proxy = this.mCamera2Device) != null && camera2Proxy.isNeedPreviewThumbnail();
    }

    private void enterAsdScene(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (i != 0) {
            if (i == 7) {
                return;
            }
            if (i == 9) {
                String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
                if ("3".equals(componentValue)) {
                    topAlert.alertFlash(0, "1", false);
                    updatePreferenceInWorkThread(10);
                } else if (ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
                    topAlert.alertFlash(0, "1", false);
                    Log.d(TAG, "enterAsdScene(): turn off HDR as FLASH has higher priority than HDR");
                    onHdrSceneChanged(false);
                    updatePreferenceInWorkThread(10);
                }
            } else if (i == 4) {
            }
        } else if (CameraSettings.isSuperNightOn()) {
            setCurrentAsdScene(-1);
        } else if (getModuleIndex() == 182) {
            setCurrentAsdScene(-1);
        } else {
            Log.d(TAG, "alertFalsh");
            topAlert.alertFlash(0, "1", false);
            updateHDRPreference();
        }
    }

    private void exitAsdScene(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (i == 0) {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if (!"1".equals(componentValue) && !ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue) && !"2".equals(componentValue) && !"5".equals(componentValue)) {
                topAlert.alertFlash(8, "1", false);
                updateHDRPreference();
            }
        } else if (i == 9) {
            String componentValue2 = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
            if ("3".equals(componentValue2) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue2)) {
                topAlert.alertFlash(8, "1", false);
            }
            updatePreferenceInWorkThread(10);
        } else if (i != 4 && i != 5) {
        }
    }

    private void findBestWatermarkItem(int i) {
        final WatermarkItem aIWatermarkItem = getAIWatermarkItem(i);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass14 */

                public void run() {
                    ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
                    if (componentRunningAIWatermark != null && componentRunningAIWatermark.getAIWatermarkEnable() && componentRunningAIWatermark.getIWatermarkEnable()) {
                        Camera2Module.this.updateWatermarkUI(aIWatermarkItem);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: finishSuperNightState */
    public void G(boolean z) {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            if (z) {
                animateCapture();
                playCameraSound(0);
                recordState.onPostSavingStart();
            }
            recordState.onPostSavingFinish();
        }
    }

    private String generateFileTitle() {
        if (CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
            this.mWaitSaveFinish = true;
            this.mMainProtocol.hideOrShowDocument(false);
            PreviewDecodeManager.getInstance().stopDecode(3);
        } else if (CameraSettings.isDocumentModeOn(this.mModuleIndex)) {
            Storage.createHideFile();
            blockSnapClickUntilSaveFinish(true);
            return Storage.DOCUMENT_PICTURE;
        } else if (this.mModuleIndex == 182) {
            Storage.createHideFile();
            String currentIDCardPictureName = getCurrentIDCardPictureName();
            blockSnapClickUntilSaveFinish(currentIDCardPictureName.equals(Storage.ID_CARD_PICTURE_2));
            return currentIDCardPictureName;
        }
        if (!TimerBurstController.isSupportTimerBurst(this.mModuleIndex) || !CameraSettings.isTimerBurstEnable()) {
            return getPrefix() + Util.createJpegName(System.currentTimeMillis()) + getSuffix();
        }
        TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
        return getPrefix() + timerBurstController.getPictureTitle(Util.createJpegName(System.currentTimeMillis())) + Storage.TIMER_BURST_SUFFIX + timerBurstController.getCaptureIndex();
    }

    private WatermarkItem getAIWatermarkItem(int i) {
        if (this.mChain == null) {
            this.mChain = new PriorityChainFactory().createPriorityChain(DataRepository.dataItemFeature().ci());
        }
        return (i != 89 ? this.mChain.createChain(this.mActivity) : this.mChain.createASDChain(this.mActivity)).handleRequest();
    }

    private int getBurstNum() {
        int Am = c.Am();
        return isUltraWideBackCamera() ? Math.min(Am, 30) : isZoomRatioBetweenUltraAndWide() ? Math.min(Am, 30) : this.mIsNearRangeModeUITip ? Math.min(Am, 30) : Am;
    }

    private String getCalibrationDataFileName(int i) {
        return isFrontCamera() ? "front_dual_camera_caldata.bin" : i == Camera2DataContainer.getInstance().getUltraWideBokehCameraId() ? "back_dual_camera_caldata_wu.bin" : "back_dual_camera_caldata.bin";
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

    private String getCurrentAiSceneName() {
        int i = this.mCurrentAiScene;
        int i2 = this.mModuleIndex;
        if (i2 != 163 && i2 != 167) {
            return null;
        }
        if (!CameraSettings.getAiSceneOpen(this.mModuleIndex)) {
            return "off";
        }
        if (i == -1) {
            i = this.isSilhouette ? 24 : 23;
        }
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ai_scene_names);
        String string = (i < 0 || i >= obtainTypedArray.length()) ? MistatsConstants.BaseEvent.UNSPECIFIED : obtainTypedArray.getString(i);
        obtainTypedArray.recycle();
        return string;
    }

    private String getCurrentIDCardPictureName() {
        return ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).getCurrentPictureName();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
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

    private CameraSize getLimitSize(List<CameraSize> list) {
        Rect activeArraySize = this.mCameraCapabilities.getActiveArraySize();
        String str = TAG;
        Log.d(str, "getLimitSize: maxSize = " + activeArraySize.width() + "x" + activeArraySize.height());
        PictureSizeManager.initialize(list, activeArraySize.width() * activeArraySize.height(), this.mModuleIndex, this.mBogusCameraId);
        return PictureSizeManager.getBestPictureSize(this.mModuleIndex);
    }

    private String getManualValue(String str, String str2) {
        return ModuleManager.isProPhotoModule() ? CameraSettingPreferences.instance().getString(str, str2) : str2;
    }

    private int getPictureFormatSuitableForShot(int i) {
        if (CameraSettings.isDocumentModeOn(this.mModuleIndex) || this.mModuleIndex == 182) {
            return 256;
        }
        if (CameraSettings.isLiveShotOn() && isLiveShotAvailable(i)) {
            return 256;
        }
        return this.mOutputPictureFormat;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private PictureInfo getPictureInfo() {
        FaceAnalyzeInfo faceAnalyzeInfo;
        PictureInfo opMode = new PictureInfo().setFrontMirror(isFrontMirror()).setSensorType(isFrontCamera()).setBokehFrontCamera(isPictureUseDualFrontCamera()).setHdrType(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex)).setOpMode(getOperatingMode());
        opMode.setAiEnabled(this.mAiSceneEnabled);
        opMode.setAiType(this.mCurrentAiScene);
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
        String superNightExif = DebugInfoUtil.getSuperNightExif(this.mPreviewSuperNightExifInfo);
        if (!TextUtils.isEmpty(superNightExif)) {
            opMode.setPreviewSuperNightExif(superNightExif);
        }
        float[] fArr2 = this.mApertures;
        if (fArr2 != null && fArr2.length > 0) {
            opMode.setLensApertues(fArr2[0]);
        }
        if (!TextUtils.isEmpty(this.mDebugFaceInfos)) {
            opMode.setFaceRoi(this.mDebugFaceInfos);
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
        if (this.mBeautyValues != null && !BeautyConstant.LEVEL_CLOSE.equals(CameraSettings.getFaceBeautifyLevel()) && DataRepository.dataItemRunning().getComponentRunningShine().getBeautyVersion() == 2) {
            opMode.setBeautyLevel(this.mBeautyValues.mBeautyLevel);
        }
        if (this.mFaceDetectionEnabled && (faceAnalyzeInfo = this.mFaceInfo) != null) {
            opMode.setGender(faceAnalyzeInfo.mGender);
            opMode.setBaby(this.mFaceInfo.mAge);
        }
        if (this.mModuleIndex == 173) {
            opMode.setNightScene(1);
        }
        opMode.end();
        return opMode;
    }

    private CameraSize getPictureSize(int i, int i2, CameraSize cameraSize) {
        CameraSize cameraSize2;
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i);
        if (capabilities != null) {
            capabilities.setOperatingMode(this.mOperatingMode);
            List<CameraSize> supportedOutputSizeWithAssignedMode = capabilities.getSupportedOutputSizeWithAssignedMode(i2);
            if (cameraSize != null) {
                ArrayList arrayList = new ArrayList(0);
                for (int i3 = 0; i3 < supportedOutputSizeWithAssignedMode.size(); i3++) {
                    CameraSize cameraSize3 = supportedOutputSizeWithAssignedMode.get(i3);
                    if (cameraSize3.compareTo(cameraSize) <= 0) {
                        arrayList.add(cameraSize3);
                    }
                }
                supportedOutputSizeWithAssignedMode = arrayList;
            }
            String str = TAG;
            Log.d(str, "getPictureSize: matchSizes = " + supportedOutputSizeWithAssignedMode);
            cameraSize2 = PictureSizeManager.getBestPictureSize(supportedOutputSizeWithAssignedMode, this.mModuleIndex);
        } else {
            cameraSize2 = null;
        }
        String str2 = TAG;
        Log.d(str2, "getPictureSize: cameraId = " + i + " size = " + cameraSize2);
        return cameraSize2;
    }

    private String getPrefix() {
        return isLivePhotoStarted() ? Storage.LIVE_SHOT_PREFIX : "";
    }

    private String getRequestFlashMode() {
        if (isSupportSceneMode()) {
            String flashModeByScene = CameraSettings.getFlashModeByScene(this.mSceneMode);
            if (!TextUtils.isEmpty(flashModeByScene)) {
                return flashModeByScene;
            }
        }
        if (!this.mMutexModePicker.isSupportedFlashOn() && !this.mMutexModePicker.isSupportedTorch() && !this.mMutexModePicker.isHdrSupportTorch(this.mSupportFlashHDR)) {
            return "0";
        }
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
        if (this.mCurrentAsdScene == 9) {
            if (componentValue.equals("3")) {
                return "2";
            }
            if (componentValue.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
                return ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON;
            }
        }
        return componentValue;
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
        if (satMasterCameraId == 5) {
            return this.mFakeTelePictureSize;
        }
        String str = TAG;
        Log.e(str, "getSatPictureSize: invalid satMasterCameraId " + satMasterCameraId);
        return this.mWidePictureSize;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getSuffix() {
        return !this.mMutexModePicker.isNormal() ? this.mMutexModePicker.getSuffix() : "";
    }

    private static String getTiltShiftMode() {
        if (CameraSettings.isTiltShiftOn()) {
            return DataRepository.dataItemRunning().getComponentRunningTiltValue().getComponentValue(160);
        }
        return null;
    }

    private void handleLLSResultInCaptureMode() {
        if (this.mShowLLSHint) {
            this.mHandler.post(new RunnableC0101z(this));
        }
    }

    private void handleSaveFinishIfNeed(String str) {
        this.mWaitSaveFinish = false;
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive() && str != null) {
            String str2 = TAG;
            Log.i(str2, "handleSaveFinishIfNeed title: " + str);
            if (Storage.isDocumentPicture(str)) {
                Handler handler = this.mHandler;
                if (handler != null) {
                    handler.removeMessages(61);
                }
                AndroidSchedulers.mainThread().scheduleDirect(new RunnableC0095t(this));
                callGalleryDocumentPage(Storage.generateFilepath(str, Storage.JPEG_SUFFIX), DataRepository.dataItemRunning().getComponentRunningDocument().getComponentValue(this.mModuleIndex), camera);
            } else if (Storage.isIdCardPicture(str)) {
                String generateFilepath = Storage.generateFilepath(str, Storage.JPEG_SUFFIX);
                if (Storage.isIdCardPictureOne(str)) {
                    this.mIDCardPaths[0] = generateFilepath;
                    AndroidSchedulers.mainThread().scheduleDirect(RunnableC0077i.INSTANCE);
                    return;
                }
                Handler handler2 = this.mHandler;
                if (handler2 != null) {
                    handler2.removeMessages(61);
                }
                AndroidSchedulers.mainThread().scheduleDirect(new r(this));
                String[] strArr = this.mIDCardPaths;
                strArr[1] = generateFilepath;
                callGalleryIDCardPage(strArr, camera);
            }
        }
    }

    private boolean handleSuperNightResultIfNeed() {
        Disposable disposable = this.mSuperNightDisposable;
        if (disposable == null) {
            return false;
        }
        if (!disposable.isDisposed()) {
            this.mSuperNightDisposable.dispose();
        }
        this.mSuperNightDisposable = null;
        boolean z = !this.mWaitingSuperNightResult;
        this.mWaitingSuperNightResult = false;
        if (z) {
            Log.d(TAG, "SuperNight: force trigger shutter animation, sound and post saving");
        }
        stopCpuBoost();
        if (CameraSchedulers.isOnMainThread()) {
            G(z);
        } else {
            AndroidSchedulers.mainThread().scheduleDirect(new RunnableC0087k(this, z));
        }
        return true;
    }

    private void handleSuperNightResultInCaptureMode() {
        if (this.mShowSuperNightHint) {
            this.mHandler.post(new RunnableC0100y(this));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            this.mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if ((this.mFaceDetectionStarted || isFaceBeautyMode()) && 1 != this.mCamera2Device.getFocusMode()) {
            this.mMainProtocol.updateFaceView(z, true, isFrontCamera, true, this.mCameraDisplayOrientation);
        }
    }

    private void hidePostCaptureAlert() {
        enableCameraControls(true);
        if (this.mCamera2Device.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
        this.mMainProtocol.setEffectViewVisible(true);
        if (!this.mCameraCapabilities.isSupportLightTripartite()) {
            this.mMainProtocol.updateReferenceGradienterSwitched();
        }
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    private void hideSceneSelector() {
        this.mHandler.post(RunnableC0099x.INSTANCE);
    }

    private void initAiSceneParser() {
        this.mFunctionParseAiScene = new FunctionParseAiScene(this.mModuleIndex, getCameraCapabilities());
        this.mAiSceneDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass19 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                Camera2Module.this.mAiSceneFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(CameraSchedulers.sCameraSetupScheduler).map(this.mFunctionParseAiScene).filter(new PredicateFilterAiScene(this, DataRepository.dataItemFeature().Gj())).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass18 */

            public void accept(Integer num) {
                Camera2Module.this.consumeAiSceneResult(num.intValue(), false);
            }
        });
    }

    private void initFlashAutoStateForTrack(boolean z) {
        this.mFlashAutoModeState = null;
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
        if (!componentValue.equals("3") && !componentValue.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
            return;
        }
        if (this.mCurrentAsdScene == 9 || z) {
            this.mFlashAutoModeState = MistatsConstants.BaseEvent.AUTO_ON;
        } else {
            this.mFlashAutoModeState = MistatsConstants.BaseEvent.AUTO_OFF;
        }
    }

    private void initMetaParser() {
        boolean isHighQualityPreferred = CameraSettings.isHighQualityPreferred();
        String str = TAG;
        Log.d(str, "initMetaParser: HQPreferred = " + isHighQualityPreferred);
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass17 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                Camera2Module.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(CameraSchedulers.sCameraSetupScheduler).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdHdr(this, this.mCameraCapabilities.isMotionDetectionSupported())).map(new FunctionParseAsdLivePhoto(this)).map(new FunctionParseSuperNight(this, isHighQualityPreferred ^ true)).map(new FunctionMiAlgoASDEngine(this)).map(new FunctionParseNearRangeTip(this)).sample(500, TimeUnit.MILLISECONDS).observeOn(CameraSchedulers.sCameraSetupScheduler).map(new FunctionParseAsdScene(this)).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach().subscribe(new AsdSceneConsumer(this));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initParallelSession() {
        Log.d(TAG, "initParallelSession: E");
        configParallelSession();
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder(true);
        localBinder.setImageSaver(this.mActivity.getImageSaver());
        CameraSize cameraSize = this.mOutputPictureSize;
        localBinder.setOutputPictureSpec(cameraSize.width, cameraSize.height, this.mOutputPictureFormat);
        localBinder.setSRRequireReprocess(DataRepository.dataItemFeature().isSRRequireReprocess());
        Log.d(TAG, "initParallelSession: X");
    }

    private void initZoomMapControllerIfNeeded() {
        CameraCapabilities cameraCapabilities;
        if (this.mZoomMapController == null && c.isSupportedOpticalZoom() && isBackCamera() && !this.mIsImageCaptureIntent && (cameraCapabilities = this.mCameraCapabilities) != null && cameraCapabilities.isSatPipSupported()) {
            int i = this.mModuleIndex;
            if ((i == 163 || i == 165) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                this.mZoomMapController = new ZoomMapController(this.mActivity, CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex));
            }
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

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean is3ALocked() {
        return this.m3ALocked;
    }

    private boolean isCannotGotoGallery() {
        return this.mPaused || this.isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || isQueueFull() || isInCountDown() || (getCameraState() == 3 && DataRepository.dataItemConfig().getmComponentManuallyET().isLongExpose(this.mModuleIndex));
    }

    private boolean isCurrentRawDomainBasedSuperNight() {
        return this.mModuleIndex == 173 && DataRepository.dataItemFeature().Zl();
    }

    private boolean isDisableWatermark() {
        if (!c.uh) {
            return false;
        }
        if (!c.Og && !c.oh && !c.Pg) {
            return false;
        }
        float deviceBasedZoomRatio = getDeviceBasedZoomRatio();
        return deviceBasedZoomRatio > 0.6f && deviceBasedZoomRatio < 1.0f;
    }

    private boolean isEnableQcfaForAlgoUp() {
        if (!this.mCameraCapabilities.isSupportedQcfa() || !this.mEnableParallelSession) {
            return false;
        }
        if (c.isMTKPlatform()) {
            return CameraSettings.isUltraPixelOn();
        }
        if (isInQCFAMode()) {
            return true;
        }
        return CameraSettings.isUltraPixelOn() && DataRepository.dataItemFeature().mm();
    }

    private boolean isFaceBeautyOn(BeautyValues beautyValues) {
        if (beautyValues == null) {
            return false;
        }
        return beautyValues.isFaceBeautyOn();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isFrontMirror() {
        if (!isFrontCamera()) {
            return false;
        }
        if (CameraSettings.isLiveShotOn() || this.mModuleIndex == 205) {
            return true;
        }
        return CameraSettings.isFrontMirror();
    }

    private boolean isHeicPreferred() {
        if (this.mIsImageCaptureIntent || !this.mCameraCapabilities.isHeicSupported() || !DataRepository.dataItemFeature().dh() || !CameraSettings.isHeicImageFormatSelected() || !this.mEnableParallelSession) {
            return false;
        }
        int i = this.mModuleIndex;
        return i == 163 || i == 165 || (!c.uh && i == 175);
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

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isIn3OrMoreSatMode() {
        return 36866 == this.mOperatingMode && HybridZoomingSystem.IS_3_OR_MORE_SAT;
    }

    private boolean isInCountDown() {
        CameraTimer cameraTimer = this.mCameraTimer;
        return cameraTimer != null && cameraTimer.isRunning();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isInMultiSurfaceSatMode() {
        return this.mCamera2Device.isInMultiSurfaceSatMode();
    }

    private boolean isInQCFAMode() {
        return (getModuleIndex() == 163 || getModuleIndex() == 165 || getModuleIndex() == 186 || getModuleIndex() == 182 || getModuleIndex() == 205) && this.mCameraCapabilities.isSupportedQcfa() && isFrontCamera() && !DataRepository.dataItemFeature().wi();
    }

    private boolean isLaunchedByMainIntent() {
        Intent intent;
        Camera camera = this.mActivity;
        return "android.intent.action.MAIN".equals((camera == null || (intent = camera.getIntent()) == null) ? null : intent.getAction());
    }

    private boolean isLimitSize() {
        return isBackCamera() && !CameraSettings.isUltraPixelOn() && DataRepository.dataItemFeature().Li();
    }

    private static boolean isLiveShotAvailable(int i) {
        return i == 0 || i == 5 || i == 8;
    }

    private boolean isNeedFixedShotTime() {
        int i;
        Camera2Proxy camera2Proxy;
        boolean z = isParallelSessionEnable() && ParallelSnapshotManager.PARALLEL_QUALITY_FEATURE_SETTING == 0 && ((i = this.mModuleIndex) == 163 || i == 165) && !isFrontCamera() && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && ((double) getZoomRatio()) == 1.0d && !isInCountDown() && (((camera2Proxy = this.mCamera2Device) == null || !camera2Proxy.isNeedFlashOn()) && !this.mIsImageCaptureIntent && !CameraSettings.isLiveShotOn() && !this.mIsISORight4HWMFNR && (DataRepository.dataItemFeature().Bh() != 0 || DEBUG_ENABLE_DYNAMIC_HHT_FAST_SHOT));
        String str = TAG;
        Log.d(str, "isNeedFixedShotTime nfst:" + z + ", mIsISORight4HWMFNR:" + this.mIsISORight4HWMFNR);
        return z;
    }

    private boolean isParallelCameraSessionMode() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        return camera2Proxy != null && camera2Proxy.getCapabilities().isSupportParallelCameraDevice() && !isParallelUnSupported() && this.mCamera2Device.getSATSubCameraIds() != null && ((this.mMutexModePicker.isHdr() && this.mCamera2Device.getCameraConfigs().isSupportParallelHDREnable()) || getZoomRatio() < HybridZoomingSystem.getTeleMinZoomRatio());
    }

    private boolean isParallelQueueFull() {
        boolean z = false;
        if (!this.mEnableParallelSession || this.mActivity.getImageSaver() == null) {
            return false;
        }
        if (isImageSaverFull()) {
            return true;
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            z = localBinder.needWaitProcess();
        } else {
            Log.w(TAG, "isParallelQueueFull: NOTICE: CHECK WHY BINDER IS NULL!");
        }
        String str = TAG;
        Log.w(str, "isParallelQueueFull: isNeedWaitProcess:" + z);
        return z;
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

    private boolean isPortraitSuccessHintShowing() {
        return ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).isPortraitHintVisible();
    }

    private boolean isPreviewThumbnailWhenFlash() {
        if (!this.mUseLegacyFlashMode) {
            return true;
        }
        return !"3".equals(this.mLastFlashMode) && !"1".equals(this.mLastFlashMode);
    }

    private boolean isProColorEnable() {
        return DataRepository.dataItemRunning().getComponentRunningColorEnhance().isEnabled(this.mModuleIndex);
    }

    private boolean isQueueFull() {
        return this.mEnableParallelSession ? isParallelQueueFull() : isImageSaverFull();
    }

    private boolean isSensorRawStreamRequired() {
        int i = this.mModuleIndex;
        if (i == 167) {
            return DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(167);
        }
        if (i != 173) {
            return false;
        }
        return DataRepository.dataItemFeature().Zl();
    }

    private boolean isSuperNightSeEnable() {
        ModeProtocol.MainContentProtocol mainContentProtocol;
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
        if ("1".equals(componentValue) || "2".equals(componentValue)) {
            return false;
        }
        return !"3".equals(componentValue) || (mainContentProtocol = this.mMainProtocol) == null || !mainContentProtocol.isFaceExists(1);
    }

    private boolean isTestImageCaptureWithoutLocation() {
        Uri uri = this.mSaveUri;
        if (uri == null || !uri.toString().contains("android.providerui.cts.fileprovider")) {
            return !this.mActivity.getCameraIntentManager().checkIntentLocationPermission(this.mActivity);
        }
        Log.d(TAG, "isTestImageCaptureWithoutLocation");
        return true;
    }

    private boolean isTriggerFlashHDR() {
        if (this.mSupportFlashHDR && !CameraSettings.isSuperNightOn() && getZoomRatio() == 1.0f && this.mCamera2Device.getCameraConfigs().getHDRMode() == 2) {
            return this.mCamera2Device != null && DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex).equals("3") && this.mCamera2Device.isNeedFlashForAuto(-1, -1) && DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex).equals("auto");
        }
    }

    private boolean isTriggerQcfaModeChange(boolean z, boolean z2) {
        if (!this.mCameraCapabilities.isSupportedQcfa()) {
            return false;
        }
        if ((this.mModuleIndex != 171 || !isBokehFrontCamera()) && DataRepository.dataItemFeature().Eh() < 0 && z && !mIsBeautyFrontOn) {
            if (this.mOperatingMode == 32775) {
                return true;
            }
            DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex);
        }
        return false;
    }

    private boolean isUseSwMfnr() {
        int i;
        if (CameraSettings.isGroupShotOn()) {
            Log.d(TAG, "GroupShot is on");
            return false;
        } else if (!DataRepository.dataItemFeature().Gi() && (isUltraWideBackCamera() || isZoomRatioBetweenUltraAndWide())) {
            Log.d(TAG, "SwMfnr force off for ultra wide camera");
            return false;
        } else if (!CameraSettings.isMfnrSatEnable()) {
            Log.d(TAG, "Mfnr not enabled");
            return false;
        } else if (!DataRepository.dataItemFeature().km()) {
            Log.d(TAG, "SwMfnr is not supported");
            return false;
        } else if (!this.mMutexModePicker.isNormal()) {
            Log.d(TAG, "Mutex mode is not normal");
            return false;
        } else if (DataRepository.dataItemFeature().Gi() && (i = this.mModuleIndex) != 167 && i != 173 && !CameraSettings.isSuperNightOn()) {
            Log.d(TAG, "For the devices does not have hardware MFNR, use software MFNR");
            return true;
        } else if (!isFrontCamera() || isDualFrontCamera()) {
            return false;
        } else {
            if (this.mOperatingMode == 32773 && c.xn()) {
                return true;
            }
            if (this.mOperatingMode != 32773 || c.xn()) {
                return DataRepository.dataItemFeature().isSupportUltraWide() || c.Ig || c.Mg;
            }
            return false;
        }
    }

    private void lockAEAF() {
        Log.d(TAG, "lockAEAF");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(true);
        }
        this.m3ALocked = true;
    }

    static /* synthetic */ void md() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiSceneSelector(8);
        }
    }

    private boolean needActiveASD() {
        return DataRepository.dataItemRunning().getComponentRunningAIWatermark().needActive();
    }

    private boolean needQuickShot() {
        int i;
        BeautyValues beautyValues;
        boolean z = false;
        if (!this.mBlockQuickShot && !this.mIsImageCaptureIntent && CameraSettings.isCameraQuickShotEnable()) {
            if (enablePreviewAsThumbnail() && (((i = this.mModuleIndex) == 163 || i == 165) && getZoomRatio() == 1.0f && !isFrontCamera() && !CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !this.mCamera2Device.isNeedFlashOn() && !CameraSettings.isUltraPixelOn() && !CameraSettings.isLiveShotOn() && ((beautyValues = this.mBeautyValues) == null || !beautyValues.isFaceBeautyOn()))) {
                z = true;
            }
            String str = TAG;
            Log.d(str, "needQuickShot bRet:" + z);
        }
        return z;
    }

    private boolean needShowThumbProgressImmediately() {
        return Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > 250000000 && this.mModuleIndex != 173 && !DataRepository.dataItemConfig().getmComponentManuallyET().isLongExpose(this.mModuleIndex);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onBurstPictureTakenFinished(boolean z) {
        stopMultiSnap();
        ObservableEmitter observableEmitter = this.mBurstEmitter;
        if (observableEmitter != null) {
            observableEmitter.onComplete();
        }
        onPictureTakenFinished(z);
        handleLLSResultInCaptureMode();
        PerformanceTracker.trackPictureCapture(1);
    }

    private void onShutter(boolean z, boolean z2, boolean z3, boolean z4) {
        if (getCameraState() == 0) {
            Log.d(TAG, "onShutter: preview stopped");
            return;
        }
        this.mShutterCallbackTime = System.currentTimeMillis();
        this.mShutterLag = this.mShutterCallbackTime - this.mCaptureStartTime;
        String str = TAG;
        Log.v(str, "mShutterLag = " + this.mShutterLag + d.H);
        updateEnablePreviewThumbnail(z);
        if (CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
            Decoder decoder = PreviewDecodeManager.getInstance().getDecoder(3);
            if (decoder != null) {
                Pair<PreviewImage, float[]> cachePreview = ((DocumentDecoder) decoder).getCachePreview();
                showDocumentPreview((PreviewImage) cachePreview.first, (float[]) cachePreview.second);
            }
        } else if (this.mEnabledPreviewThumbnail && !z2) {
            this.mActivity.getCameraScreenNail().requestReadPixels();
        } else if (z2) {
            String str2 = TAG;
            Log.d(str2, "anchor frame onshutter anchor " + z2 + " doAnchor " + z3 + " anchor read pixel " + z4);
            if (z3 && !z4) {
                animateCapture();
                playCameraSound(0);
            } else if (!z3 && z4) {
                this.mActivity.getCameraScreenNail().requestReadPixels();
            }
        } else if (this.mModuleIndex != 173 || DataRepository.dataItemGlobal().isOnSuperNightAlgoUpAndQuickShot()) {
            updateThumbProgress(false);
            animateCapture();
            playCameraSound(0);
        }
    }

    private void onStartRecorderFail() {
        enableCameraControls(true);
        restoreOuterAudio();
        this.mMainProtocol.setEvAdjustable(true ^ CameraSettings.isSuperNightOn());
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onFailed();
        }
    }

    private void onStartRecorderSucceed() {
        enableCameraControls(true);
        this.mActivity.sendBroadcast(new Intent(BaseModule.START_VIDEO_RECORDING_ACTION));
        this.mMediaRecorderRecording = true;
        this.mRecordingStartTime = SystemClock.uptimeMillis();
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
        Log.v(TAG, "listen call state");
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null && this.mVolumeLongPress) {
            baseDelegate.getAnimationComposite().setClickEnable(false);
        }
        this.mMainProtocol.setEvAdjustable(false);
        updateRecordingTime();
        keepScreenOn();
        AutoLockManager.getInstance(this.mActivity).removeMessage();
        CameraStatUtils.trackLongPressRecord();
    }

    private void parseIntent() {
        CameraIntentManager cameraIntentManager = this.mActivity.getCameraIntentManager();
        this.mIsImageCaptureIntent = cameraIntentManager.isImageCaptureIntent();
        if (this.mIsImageCaptureIntent) {
            this.mSaveUri = cameraIntentManager.getExtraSavedUri();
            this.mCropValue = cameraIntentManager.getExtraCropValue();
            this.mIsSaveCaptureImage = cameraIntentManager.getExtraShouldSaveCapture().booleanValue();
            this.mQuickCapture = cameraIntentManager.isQuickCapture().booleanValue();
        }
    }

    private void prepareAIWatermark() {
        this.mWatermarkItem = null;
        if (CameraSettings.isAIWatermarkOn(this.mModuleIndex)) {
            this.mWatermarkItem = DataRepository.dataItemRunning().getComponentRunningAIWatermark().getMajorWatermarkItem();
            WatermarkItem watermarkItem = this.mWatermarkItem;
            if (watermarkItem != null) {
                watermarkItem.setCaptureCoordinate(watermarkItem.getCoordinate());
            }
        }
    }

    private void prepareLLSInCaptureMode() {
        if (this.mIsLLSNeeded) {
            this.mShowLLSHint = true;
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(0, R.string.super_night_hint, -1);
            }
        }
    }

    private void prepareMultiCapture() {
        Log.d(TAG, "prepareMultiCapture");
        this.mFocusManager.removeMessages();
        this.mMultiSnapStatus = true;
        this.mMultiSnapStopRequest = false;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setMultiSnapStopRequest(false);
        }
        Util.clearMemoryLimit();
        prepareNormalCapture();
        if (this.mMainProtocol.isFaceExists(1)) {
            this.mMainProtocol.hideFaceAnimator();
        }
        int burstNum = getBurstNum();
        if (isHeicPreferred()) {
            burstNum = Math.min(50, burstNum);
        }
        this.mTotalJpegCallbackNum = burstNum;
        String str = TAG;
        Log.d(str, "For best user experience, burst capture count is limited to " + this.mTotalJpegCallbackNum);
        this.mHandler.removeMessages(49);
        if (!is3ALocked()) {
            this.mFocusManager.onShutter();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x02b9  */
    private void prepareNormalCapture() {
        boolean z;
        boolean z2;
        Log.d(TAG, "prepareNormalCapture");
        CameraSize cameraSize = null;
        if (this.mMagneticSensorDetect != null && this.mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable()) {
            this.mMagneticSensorDetect.updateMagneticDetection();
            if (isHdrSceneDetectionStarted() && !this.mMagneticSensorDetect.isLockHDRChecker("prepareNormalCapture-2")) {
                if (this.mMutexModePicker.isHdr()) {
                    this.mCamera2Device.getCameraConfigs().setHdrCheckerEvValue(null);
                    this.mCamera2Device.getCameraConfigs().setHdrCheckerSceneType(0);
                    this.mCamera2Device.getCameraConfigs().setHdrCheckerAdrc(0);
                    if (!this.mIsInHDR) {
                        onHdrSceneChanged(false);
                        updateHDRTip(false);
                    }
                } else if (this.mIsInHDR) {
                    onHdrSceneChanged(true);
                    updateHDRTip(true);
                }
            }
            this.mMagneticSensorDetect.recordMagneticInfo();
        }
        initFlashAutoStateForTrack(this.mCamera2Device.isNeedFlashOn());
        this.mEnabledPreviewThumbnail = false;
        this.mTotalJpegCallbackNum = 1;
        this.mReceivedJpegCallbackNum = 0;
        this.mCaptureStartTime = System.currentTimeMillis();
        this.mCamera2Device.setCaptureTime(this.mCaptureStartTime);
        ScenarioTrackUtil.trackCaptureTimeStart(isFrontCamera(), this.mModuleIndex);
        ScenarioTrackUtil.trackShotToGalleryStart(isFrontCamera(), this.mModuleIndex, this.mCaptureStartTime);
        ScenarioTrackUtil.trackShotToViewStart(isFrontCamera(), this.mModuleIndex, this.mCaptureStartTime);
        this.mLastCaptureTime = this.mCaptureStartTime;
        setCameraState(3);
        if (!this.mMultiSnapStatus && CameraSettings.isTimerBurstEnable()) {
            DataRepository.dataItemLive().getTimerBurstController();
            if (TimerBurstController.isSupportTimerBurst(this.mModuleIndex)) {
                this.mCamera2Device.setInTimerBurstShotting(true);
                AutoLockManager.getInstance(this.mActivity).cancelHibernate();
            }
        }
        if (this.mModuleIndex == 182) {
            this.mJpegRotation = 0;
        } else {
            this.mJpegRotation = Util.getJpegRotation(this.mBogusCameraId, this.mOrientation);
            if (this.mCamera2Device.isInTimerBurstShotting()) {
                DataRepository.dataItemLive().getTimerBurstController().getOrientation(true, this.mOrientation);
                this.mJpegRotation = DataRepository.dataItemLive().getTimerBurstController().getJpegRotation(this.mJpegRotation);
            }
        }
        Log.d(TAG, "prepareNormalCapture: mOrientation = " + this.mOrientation + ", mJpegRotation = " + this.mJpegRotation);
        this.mCamera2Device.setJpegRotation(this.mJpegRotation);
        prepareAIWatermark();
        Location currentLocation = !isTestImageCaptureWithoutLocation() ? LocationManager.instance().getCurrentLocation() : null;
        this.mCamera2Device.setGpsLocation(currentLocation);
        this.mLocation = currentLocation;
        ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol == null || !mainContentProtocol.isFaceExists(1)) {
            this.mCamera2Device.setIsFaceExist(false);
            this.mDebugFaceInfos = DebugInfoUtil.getRetriveFaceInfo(null);
        } else {
            this.mCamera2Device.setIsFaceExist(true);
            this.mDebugFaceInfos = DebugInfoUtil.getRetriveFaceInfo(this.mMainProtocol.getViewRects(this.mPictureSize));
        }
        this.mUpscaleImageWithSR = false;
        if (!this.mMultiSnapStatus) {
            z = shouldDoQCFA(this.mCamera2Device.getPreviewCaptureResult());
            Log.d(TAG, "prepareNormalCapture: qcfa = " + z);
            if (CameraSettings.isUltraPixelOn() && DataRepository.dataItemFeature().hl() && CameraSettings.isSRTo108mModeOn() && !z) {
                this.mUpscaleImageWithSR = true;
                cameraSize = this.SIZE_108M;
            }
        } else {
            z = false;
        }
        this.mCamera2Device.getCameraConfigs().setLockedAlgoSize(cameraSize);
        this.mCamera2Device.getCameraConfigs().setDoRemosaic(z);
        updateSuperNight();
        updateFrontMirror();
        updateBeauty();
        updateSRAndMFNR();
        updateShotDetermine();
        updateDoDepurple();
        updateCaptureTriggerFlow();
        int i = CameraSettings.isLiveShotOn() && isLiveShotAvailable(this.mCamera2Device.getCameraConfigs().getShotType()) ? 256 : this.mOutputPictureFormat;
        String generateFileTitle = generateFileTitle();
        Log.d(TAG, "prepareNormalCapture title = " + generateFileTitle);
        this.mCamera2Device.setShotSavePath(Storage.generateFilepath4Image(generateFileTitle, CompatibilityUtils.isHeicImageFormat(i)), !this.mMultiSnapStatus && (this.mEnableParallelSession || this.mEnableShot2Gallery || updateAnchorFramePreview() || DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting()));
        int i2 = this.mModuleIndex;
        if (i2 == 163 || i2 == 165 || i2 == 171 || i2 == 175 || i2 == 186 || i2 == 182) {
            boolean z3 = CameraSettings.isCameraQuickShotEnable() || CameraSettings.isCameraQuickShotAnimateEnable();
            boolean z4 = this.mSupportShotBoost;
            if (z3 || z4) {
                z2 = true;
                this.mCamera2Device.setNeedSequence(z2);
                this.mCamera2Device.setModuleAnchorFrame(updateAnchorFramePreview());
                if (enablePreviewAsThumbnail() || this.mMutexModePicker.isHdr()) {
                    this.mQuickShotAnimateEnable = false;
                } else {
                    this.mQuickShotAnimateEnable = CameraSettings.isCameraQuickShotAnimateEnable();
                }
                setWaterMark();
                setPictureOrientation();
                updateJpegQuality();
                updateAlgorithmName();
                if (needShowThumbProgressImmediately()) {
                    updateThumbProgress(false);
                }
                prepareSuperNight();
                prepareSuperNightInCaptureMode();
                prepareLLSInCaptureMode();
                checkMoreFrameCaptureLockAFAE(true);
                this.mFirstCreateCapture = false;
            }
        }
        z2 = false;
        this.mCamera2Device.setNeedSequence(z2);
        this.mCamera2Device.setModuleAnchorFrame(updateAnchorFramePreview());
        if (enablePreviewAsThumbnail()) {
        }
        this.mQuickShotAnimateEnable = false;
        setWaterMark();
        setPictureOrientation();
        updateJpegQuality();
        updateAlgorithmName();
        if (needShowThumbProgressImmediately()) {
        }
        prepareSuperNight();
        prepareSuperNightInCaptureMode();
        prepareLLSInCaptureMode();
        checkMoreFrameCaptureLockAFAE(true);
        this.mFirstCreateCapture = false;
    }

    private void prepareSuperNight() {
        if (this.mModuleIndex == 173 && !DataRepository.dataItemGlobal().isOnSuperNightAlgoUpAndQuickShot()) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPrepare();
                recordState.onStart();
            }
            startCpuBoost();
            if (this.mSuperNightEventConsumer == null) {
                this.mSuperNightEventConsumer = new SuperNightEventConsumer();
            }
            this.mSuperNightDisposable = Observable.just(300, 2000).flatMap(new Function<Integer, ObservableSource<Integer>>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass7 */

                public ObservableSource<Integer> apply(Integer num) throws Exception {
                    return Observable.just(num).delaySubscription((long) num.intValue(), TimeUnit.MILLISECONDS);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mSuperNightEventConsumer);
        }
    }

    private void prepareSuperNightInCaptureMode() {
        if (!isRepeatingRequestInProgress() && CameraSettings.isSuperNightOn()) {
            this.mShowSuperNightHint = true;
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(0, R.string.super_night_hint, -1);
            }
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        this.mFaceDetectionEnabled = false;
        updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_ON_PREVIEW_SUCCESS);
        if (ModuleManager.isProPhotoModule()) {
            updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_MANUALLY);
        }
    }

    @MainThread
    private void realConsumeAiSceneResult(int i, boolean z) {
        int i2;
        if (i == 36) {
            i = 0;
        }
        if (this.mCurrentAiScene == i) {
            if (i == 0) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null && topAlert.getCurrentAiSceneLevel() == i) {
                    return;
                }
            } else {
                return;
            }
        }
        if (!isDoingAction() && isAlive() && !this.mActivity.isActivityPaused()) {
            if (!z || !this.isResetFromMutex) {
                if (!z) {
                    this.isResetFromMutex = false;
                }
                Log.d(TAG, "consumeAiSceneResult: " + i + "; isReset: " + z);
                ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                boolean z2 = true;
                if (!isFrontCamera() && !c.mn()) {
                    this.mCamera2Device.setCameraAI30(i == 25);
                }
                if (this.mIsGoogleLensAvailable) {
                    if (!LensAgent.isConflictAiScene(this.mCurrentAiScene) && LensAgent.isConflictAiScene(i)) {
                        this.mIsAiConflict = true;
                        showOrHideChip(false);
                    } else if (LensAgent.isConflictAiScene(this.mCurrentAiScene) && !LensAgent.isConflictAiScene(i)) {
                        this.mIsAiConflict = false;
                        showOrHideChip(true);
                    }
                }
                closeMoonMode(i, 8);
                closeBacklightTip(i, 8);
                if (i != -1) {
                    if (i == 1) {
                        int parseInt = Integer.parseInt(CameraSettings.getSharpness());
                        if (parseInt < 6) {
                            parseInt++;
                        }
                        this.mCurrentAiScene = i;
                        configChanges.restoreAllMutexElement("e");
                        this.mCamera2Device.setSharpness(parseInt);
                    } else if (i == 10) {
                        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
                        if (DataRepository.dataItemFeature().vm() && (componentValue.equals("3") || componentValue.equals("1"))) {
                            configChanges.closeMutexElement("e", 193);
                            setFlashMode("0");
                        }
                        updateMfnr(true);
                        updateOIS();
                    } else if (i == 15 || i == 19) {
                        int parseInt2 = Integer.parseInt(CameraSettings.getSharpness());
                        if (parseInt2 < 6) {
                            parseInt2++;
                        }
                        this.mCamera2Device.setSharpness(parseInt2);
                        this.mCurrentAiScene = i;
                        configChanges.restoreAllMutexElement("e");
                    } else if (i != 3) {
                        if (i == 4) {
                            this.mCamera2Device.setContrast(Integer.parseInt(CameraSettings.getContrast()));
                            this.mCurrentAiScene = i;
                            configChanges.restoreAllMutexElement("e");
                            updateSuperResolution();
                        } else if (i == 7 || i == 8) {
                            this.mCurrentAiScene = i;
                            configChanges.restoreAllMutexElement("e");
                        } else {
                            if (i != 34) {
                                if (i == 35) {
                                    if (showMoonMode(false)) {
                                        topAlert2.setAiSceneImageLevel(i);
                                        trackAISceneChanged(this.mModuleIndex, i);
                                        this.mCurrentAiScene = i;
                                        return;
                                    }
                                    z2 = false;
                                } else if (i != 37) {
                                    if (i != 38) {
                                        switch (i) {
                                            case 25:
                                                trackAISceneChanged(this.mModuleIndex, 25);
                                                topAlert2.setAiSceneImageLevel(25);
                                                setAiSceneEffect(25);
                                                this.mCurrentAiScene = i;
                                                updateHDRPreference();
                                                configChanges.restoreAllMutexElement("e");
                                                resumePreviewInWorkThread();
                                                return;
                                            case 26:
                                            case 27:
                                            case 28:
                                            case 29:
                                            case 30:
                                            case 31:
                                                if (!DataRepository.dataItemFeature().Gl()) {
                                                    configChanges.restoreAllMutexElement("e");
                                                    updatePreferenceInWorkThread(11);
                                                    updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                                    break;
                                                } else {
                                                    this.mCurrentAiScene = i;
                                                    configChanges.restoreAllMutexElement("e");
                                                    break;
                                                }
                                            default:
                                                if (this.mMagneticSensorDetect == null || !this.mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable() || !this.mMagneticSensorDetect.isLockHDRChecker("realConsumeAiSceneResult")) {
                                                    updateHDRPreference();
                                                }
                                                configChanges.restoreAllMutexElement("e");
                                                updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                                break;
                                        }
                                    } else {
                                        boolean aIWatermarkEnable = DataRepository.dataItemRunning().getComponentRunningAIWatermark().getAIWatermarkEnable(this.mModuleIndex);
                                        if (DataRepository.dataItemFeature().Gj() && (((i2 = this.mModuleIndex) == 163 || i2 == 165) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !CameraSettings.isUltraPixelPortraitFrontOn() && !aIWatermarkEnable)) {
                                            trackAISceneChanged(this.mModuleIndex, i);
                                            topAlert2.setAiSceneImageLevel(i);
                                            this.mCurrentAiScene = i;
                                            return;
                                        }
                                    }
                                    i = 0;
                                }
                            }
                            this.mCurrentAiScene = i;
                            z2 = false;
                        }
                    }
                    trackAISceneChanged(this.mModuleIndex, i);
                    topAlert2.setAiSceneImageLevel(i);
                    if (z2) {
                        setAiSceneEffect(i);
                    }
                    if (!z) {
                        this.mCurrentAiScene = i;
                    }
                    updateBeauty();
                    resumePreviewInWorkThread();
                    return;
                }
                showBacklightTip();
                topAlert2.setAiSceneImageLevel(23);
                this.mCurrentAiScene = i;
            }
        }
    }

    private void resetAiSceneInHdrOrFlashOn() {
        int i;
        if (this.mAiSceneEnabled && !this.isResetFromMutex && (i = this.mCurrentAiScene) != 0) {
            if (i == -1 || i == 10) {
                this.mHandler.post(new Runnable() {
                    /* class com.android.camera.module.Camera2Module.AnonymousClass20 */

                    public void run() {
                        Camera2Module.this.consumeAiSceneResult(0, true);
                        Camera2Module.this.isResetFromMutex = true;
                    }
                });
            }
        }
    }

    private void resetAsdSceneInHdrOrFlashChange() {
        int i;
        if (c.En() && isFrontCamera() && (i = this.mCurrentAsdScene) != -1 && i == 9) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass21 */

                public void run() {
                    Camera2Module.this.updateAsdSceneResult(-1);
                }
            });
        }
    }

    private void resumePreviewInWorkThread() {
        updatePreferenceInWorkThread(new int[0]);
    }

    private void setAiSceneEffect(int i) {
        String str = TAG;
        Log.d(str, "setAiSceneEffect: " + i);
        if (EffectController.getInstance().getAiColorCorrectionVersion() != 0 || !DataRepository.dataItemFeature().jl() || !CameraSettings.isBackCamera() || i != 25) {
            if (CameraSettings.isFrontCamera() || isPortraitMode()) {
                if (i != 0) {
                    Log.d(TAG, "setAiSceneEffect: front camera or portrait mode nonsupport!");
                    return;
                } else if (CameraSettings.getPortraitLightingPattern() != 0) {
                    Log.d(TAG, "setAiSceneEffect: scene = 0 but portrait lighting is on...");
                    return;
                }
            }
            int shaderEffect = CameraSettings.getShaderEffect();
            if (FilterInfo.getCategory(shaderEffect) != 5 && shaderEffect != FilterInfo.FILTER_ID_NONE) {
                return;
            }
            if (isProColorEnable()) {
                Log.d(TAG, "ProColor is enable, disable AI filter");
                return;
            }
            ArrayList<FilterInfo> filterInfo = EffectController.getInstance().getFilterInfo(5);
            if (i < 0 || i > filterInfo.size()) {
                String str2 = TAG;
                Log.e(str2, "setAiSceneEffect: scene unknown: " + i);
                return;
            }
            int aiColorCorrectionVersion = EffectController.getInstance().getAiColorCorrectionVersion();
            boolean z = true;
            int id = aiColorCorrectionVersion == 1 ? filterInfo.get(i).getId() : aiColorCorrectionVersion == 2 ? (!this.mAiSceneEnabled || i != 0) ? filterInfo.get(i).getId() : EffectController.createAiSceneEffectId(FilterType.A_COMMON) : filterInfo.get(i).getId();
            EffectController.getInstance().setAiSceneEffect(id, true);
            if (id == FilterInfo.FILTER_ID_NONE) {
                z = false;
            }
            this.mHasAiSceneFilterEffect = z;
            return;
        }
        Log.d(TAG, "supportAi30: AI 3.0 back camera in HUMAN SCENE not apply filter!");
    }

    private void setCurrentAsdScene(int i) {
        this.mCurrentAsdScene = i;
    }

    private void setEffectFilter(int i) {
        String str = TAG;
        Log.d(str, "setEffectFilter: " + i, new Throwable());
        EffectController.getInstance().setEffect(i);
        this.mNormalFilterId = i;
        CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
        if (circularMediaRecorder != null) {
            circularMediaRecorder.setFilterId(i);
        }
    }

    private void setLightingEffect() {
        int shaderEffect = CameraSettings.getShaderEffect();
        if (FilterInfo.getCategory(shaderEffect) != 5 && shaderEffect != FilterInfo.FILTER_ID_NONE) {
            return;
        }
        if (isProColorEnable()) {
            Log.d(TAG, "ProColor is enable, disable AI filter");
            return;
        }
        int portraitLightingPattern = CameraSettings.getPortraitLightingPattern();
        String str = TAG;
        Log.d(str, "setLightingEffect: " + portraitLightingPattern);
        ArrayList<FilterInfo> filterInfo = EffectController.getInstance().getFilterInfo(6);
        int i = FilterInfo.FILTER_ID_NONE;
        Iterator<FilterInfo> it = filterInfo.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FilterInfo next = it.next();
            if (next.getOrder() == portraitLightingPattern) {
                i = next.getId();
                break;
            }
        }
        this.mLightFilterId = i;
        EffectController.getInstance().setLightingEffect(i);
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
                changeZoom4Crop();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setOrientationParameter() {
        if (!isDeparted()) {
            if (!(this.mCamera2Device == null || this.mOrientation == -1)) {
                if (!isFrameAvailable() || getCameraState() != 1) {
                    CameraSchedulers.sCameraSetupScheduler.scheduleDirect(new RunnableC0086j(this));
                } else {
                    updatePreferenceInWorkThread(35);
                }
            }
            CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
            if (circularMediaRecorder != null) {
                circularMediaRecorder.setOrientationHint(this.mOrientationCompensation);
            }
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

    private void setPortraitSuccessHintVisible(int i) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).setPortraitHintVisible(i);
    }

    private boolean setSceneMode(String str) {
        int parseInt = Util.parseInt(str, -1);
        this.mCamera2Device.setSceneMode(parseInt);
        if (!Util.isSupported(parseInt, this.mCameraCapabilities.getSupportedSceneModes())) {
            return false;
        }
        String str2 = TAG;
        Log.d(str2, "sceneMode=" + str);
        return true;
    }

    private void setVideoSize(int i, int i2) {
        if (this.mCameraDisplayOrientation % 180 == 0) {
            this.mVideoSize = new CameraSize(i, i2);
        } else {
            this.mVideoSize = new CameraSize(i2, i);
        }
    }

    private void setWaterMark() {
        if (this.mMultiSnapStatus || CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex) || DataRepository.dataItemFeature().Rl() || ((!this.mEnableParallelSession && (this.mModuleIndex == 165 || CameraSettings.getShaderEffect() != FilterInfo.FILTER_ID_NONE || this.mHasAiSceneFilterEffect || CameraSettings.isTiltShiftOn())) || ((this.mEnableParallelSession && (!DataRepository.dataItemFeature().Sl() || this.mModuleIndex == 171)) || this.mModuleIndex == 205))) {
            this.mCamera2Device.setDualCamWaterMarkEnable(false);
            this.mCamera2Device.setTimeWaterMarkEnable(false);
            return;
        }
        if (CameraSettings.isDualCameraWaterMarkOpen()) {
            this.mCamera2Device.setDualCamWaterMarkEnable(true);
        } else if (CameraSettings.isFrontCameraWaterMarkOpen()) {
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

    private boolean shouldApplyNormalWideLDC() {
        if (CameraSettings.shouldNormalWideLDCBeVisibleInMode(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && this.mActualCameraId != Camera2DataContainer.getInstance().getUltraWideCameraId() && !CameraSettings.isUltraPixelOn() && !isZoomRatioBetweenUltraAndWide()) {
            return CameraSettings.isNormalWideLDCEnabled();
        }
        return false;
    }

    private boolean shouldApplyUltraWideLDC() {
        if (!CameraSettings.shouldUltraWideLDCBeVisibleInMode(this.mModuleIndex)) {
            return false;
        }
        if (CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !DataRepository.dataItemFeature().fm()) {
            return true;
        }
        if (this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideLDCEnabled();
        }
        if (isZoomRatioBetweenUltraAndWide()) {
            return CameraSettings.isUltraWideLDCEnabled();
        }
        return false;
    }

    private boolean shouldChangeAiScene(int i) {
        if (isDoingAction() || !isAlive() || this.mCurrentDetectedScene == i || System.currentTimeMillis() - this.mLastChangeSceneTime <= 300) {
            return false;
        }
        this.mCurrentDetectedScene = i;
        this.mLastChangeSceneTime = System.currentTimeMillis();
        return true;
    }

    private boolean shouldCheckLLS() {
        return this.mCameraCapabilities.isLLSSupported() && DataRepository.dataItemFeature().Nl();
    }

    private boolean shouldDoMultiFrameCapture() {
        boolean z = false;
        if (!this.mIsMoonMode || DataRepository.dataItemFeature().dk()) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy == null || this.mCameraCapabilities == null || !camera2Proxy.useLegacyFlashStrategy() || !this.mCamera2Device.isNeedFlashOn() || !this.mCameraCapabilities.isFlashSupported()) {
                if (this.mModuleIndex == 167 && DataRepository.dataItemFeature().Xl() && this.mCamera2Device.getCameraConfigs().isSuperResolutionEnabled()) {
                    Log.d(TAG, "shouldDoMultiFrameCapture: SR is enabled for pro mode");
                    return true;
                } else if (this.mUpscaleImageWithSR) {
                    Log.d(TAG, "shouldDoMultiFrameCapture: SR is enabled for upscaling image");
                    return true;
                } else {
                    if (this.mMutexModePicker.isHdr() || this.mShouldDoMFNR || this.mMutexModePicker.isSuperResolution() || CameraSettings.isGroupShotOn() || DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode()) {
                        z = true;
                    }
                    Log.d(TAG, "shouldDoMultiFrameCapture: " + z + " | " + this.mShouldDoMFNR);
                    return z;
                }
            } else {
                Log.d(TAG, "shouldDoMultiFrameCapture: return false in case of flash");
                return false;
            }
        } else {
            Log.d(TAG, "shouldDoMultiFrameCapture: return false in moon mode");
            return false;
        }
    }

    private boolean shouldDoQCFA(CaptureResult captureResult) {
        if (!isEnableQcfaForAlgoUp()) {
            return false;
        }
        if (CameraSettings.isFrontCamera() && !c.Ng) {
            return false;
        }
        if (this.mCamera2Device.getCapabilities().isRemosaicDetecedSupported()) {
            return CaptureResultParser.isRemosaicDetected(captureResult);
        }
        Integer num = (Integer) captureResult.get(CaptureResult.SENSOR_SENSITIVITY);
        String str = TAG;
        Log.d(str, "shouldDoQCFA: iso = " + num);
        return num != null && num.intValue() <= 200;
    }

    private void showDocumentDetectBlurHint() {
        FragmentTopConfig fragmentTopConfig;
        if (!Util.isGlobalVersion() && (fragmentTopConfig = (FragmentTopConfig) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            fragmentTopConfig.alertAiDetectTipHint(0, R.string.document_blur_warn, 3000);
            CameraStatUtils.trackDocumentDetectBlurHintShow();
        }
    }

    private boolean showMoonMode(boolean z) {
        ModeProtocol.TopAlert topAlert;
        if (CameraSettings.isUltraWideConfigOpen(getModuleIndex()) || CameraSettings.isMacroModeEnabled(this.mModuleIndex) || (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) == null) {
            return false;
        }
        this.mEnteringMoonMode = true;
        topAlert.alertMoonModeSelector(0, z);
        if (!z) {
            updateMoonNight();
        } else {
            updateMoon(true);
        }
        if (topAlert.isHDRShowing()) {
            topAlert.alertHDR(8, false, false);
        }
        String str = TAG;
        Log.d(str, "(moon_mode) show moon mode,button check status:" + z);
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showOrHideLoadingProgress(boolean z, boolean z2) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.showOrHideLoadingProgress(z, z2);
        }
    }

    private void showPostCaptureAlert() {
        enableCameraControls(false);
        this.mFocusManager.removeMessages();
        stopFaceDetection(true);
        pausePreview();
        this.mMainProtocol.setEffectViewVisible(false);
        this.mMainProtocol.hideReferenceGradienter();
        this.mMainProtocol.clearFocusView(7);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
        resetMetaDataManager();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startCount(final int i, final int i2, final int i3) {
        if (!this.mMediaRecorderRecording) {
            this.mIsStartCount = true;
            if (!checkShutterCondition()) {
                this.mIsStartCount = false;
                if (i3 == 120) {
                    this.mHandler.postDelayed(new Runnable() {
                        /* class com.android.camera.module.Camera2Module.AnonymousClass9 */

                        public void run() {
                            Camera2Module.this.startCount(i, i2, i3);
                        }
                    }, 300);
                }
            } else if (i3 != 120 || DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting()) {
                setTriggerMode(i3);
                tryRemoveCountDownMessage();
                String str = TAG;
                Log.d(str, "startCount: " + i);
                this.mCameraTimer = new CameraTimer();
                this.mCameraTimer.setCount(i).setRepeatTimes(i2).start(new Observer<Long>() {
                    /* class com.android.camera.module.Camera2Module.AnonymousClass10 */

                    public /* synthetic */ void Kd() {
                        Camera2Module.this.mTopAlert.hideAlert();
                    }

                    @Override // io.reactivex.Observer
                    public void onComplete() {
                        Camera camera;
                        ModeProtocol.ManuallyAdjust manuallyAdjust;
                        Camera2Module.this.mIsStartCount = false;
                        Camera2Module.this.tryRemoveCountDownMessage();
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (Camera2Module.this.isAlive() && (camera = Camera2Module.this.mActivity) != null && !camera.isActivityPaused()) {
                            if (CameraSettings.isTimerBurstEnable()) {
                                ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                                if (TimerBurstController.isSupportTimerBurst(Camera2Module.this.mModuleIndex) && i3 != 120) {
                                    TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
                                    if (!timerBurstController.isInTimerBurstShotting()) {
                                        timerBurstController.setInTimerBurstShotting(true);
                                        timerBurstController.resetTimerRunningData();
                                        recordState.onPrepare();
                                        topAlert.setRecordingTimeState(1);
                                        if (Camera2Module.this.mModuleIndex == 167 && (manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181)) != null) {
                                            manuallyAdjust.setManuallyLayoutVisible(false);
                                        }
                                    }
                                }
                            } else if (topAlert != null) {
                                Camera2Module.this.mTopAlert.reInitAlert(true);
                            }
                        }
                    }

                    @Override // io.reactivex.Observer
                    public void onError(Throwable th) {
                        Camera2Module.this.mIsStartCount = false;
                    }

                    public void onNext(Long l) {
                        int intValue = l.intValue();
                        if (intValue == i) {
                            Camera2Module.this.mTopAlert.hideAlert();
                            Camera2Module.this.playCameraSound(7);
                            Camera2Module.this.mTopAlert.showDelayNumber(intValue);
                        } else if (intValue == 0) {
                            Camera2Module.this.mTopAlert.hideDelayNumber();
                            Camera2Module camera2Module = Camera2Module.this;
                            camera2Module.startNormalCapture(camera2Module.getTriggerMode());
                        } else {
                            Camera2Module.this.playCameraSound(5);
                            Camera2Module.this.mTopAlert.showDelayNumber(intValue);
                        }
                    }

                    @Override // io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        if (Camera2Module.this.mTopAlert != null) {
                            AndroidSchedulers.mainThread().scheduleDirect(new RunnableC0074f(this), 120, TimeUnit.MILLISECONDS);
                        }
                        Camera2Module.this.mMainProtocol.clearFocusView(7);
                    }
                });
            }
        }
    }

    private void startCpuBoost() {
        if (c.isMTKPlatform()) {
            if (this.mCpuBoost == null) {
                this.mCpuBoost = new BoostFrameworkImpl();
            }
            BaseBoostFramework baseBoostFramework = this.mCpuBoost;
            if (baseBoostFramework != null) {
                baseBoostFramework.startBoost();
            }
        }
    }

    /* access modifiers changed from: private */
    public void startLensActivity() {
        if (CameraSettings.supportGoogleLens()) {
            Camera camera = this.mActivity;
            if (camera != null) {
                camera.startLensActivity();
                return;
            }
            return;
        }
        Log.d(TAG, "start ai lens");
        try {
            Intent intent = new Intent();
            intent.setAction("android.media.action.XIAOAI_CONTROL");
            intent.setPackage(CameraSettings.AI_LENS_PACKAGE);
            intent.putExtra("preview_width", this.mPreviewSize.width);
            intent.putExtra("preview_height", this.mPreviewSize.height);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        } catch (Exception e2) {
            Log.e(TAG, "onClick: occur a exception", e2);
        }
    }

    private void startLiveShotAnimation() {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (!(this.mCircularMediaRecorder == null || this.mHandler == null)) {
                this.mHandler.post(new Runnable() {
                    /* class com.android.camera.module.Camera2Module.AnonymousClass5 */

                    public void run() {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            topAlert.startLiveShotAnimation();
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startNormalCapture(int i) {
        int i2;
        int i3;
        Camera camera;
        String str = TAG;
        Log.d(str, "startNormalCapture mode -> " + i);
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null && DataRepository.dataItemConfig().getmComponentManuallyET().isLongExpose(this.mModuleIndex)) {
            recordState.onLongExposeStart();
        }
        this.mActivity.getScreenHint().updateHint();
        if (Storage.isLowStorageAtLastPoint()) {
            String str2 = TAG;
            Log.i(str2, "Not enough space or storage not ready. remaining=" + Storage.getLeftSpace());
            return;
        }
        if (!(!this.mIsImageCaptureIntent || (camera = this.mActivity) == null || camera.getImageSaver() == null)) {
            this.mActivity.getImageSaver().setDropBitmapTexture(false);
        }
        prepareNormalCapture();
        if (!CameraSettings.isGroupShotOn() || isParallelSessionEnable()) {
            this.mHandler.sendEmptyMessageDelayed(50, calculateTimeout(this.mModuleIndex));
            this.mCamera2Device.setQuickShotAnimation(this.mQuickShotAnimateEnable);
            if (DataRepository.dataItemFeature()._j()) {
                if ((getModuleIndex() == 163 || getModuleIndex() == 165 || getModuleIndex() == 186 || getModuleIndex() == 182) && HybridZoomingSystem.isZoomRatioNone(getZoomRatio(), isFrontCamera())) {
                    this.mCamera2Device.setFlawDetectEnable(true);
                } else {
                    this.mCamera2Device.setFlawDetectEnable(false);
                }
            }
            if (this.mCamera2Device.getCameraConfigs().isHDREnabled() && this.mCameraCapabilities.isSupportAIIE() && this.mAiSceneEnabled && this.mCamera2Device.getCameraConfigs().isAIIEPreviewEnabled()) {
                this.mCamera2Device.setAIIEPreviewEnable(false);
                resumePreviewInWorkThread();
            }
            if (!isFrontCamera() || !this.mSupportShotBoost || this.mIsImageCaptureIntent || (!((i2 = this.mModuleIndex) == 165 || i2 == 163) || 32775 == (i3 = this.mOperatingMode) || 32778 == i3)) {
                this.mCamera2Device.setShotBoostParams(null);
            } else {
                this.mCamera2Device.setShotBoostParams(new ia(this));
            }
            this.mCamera2Device.takePicture(this, this.mActivity.getImageSaver());
            if (!needQuickShot() || i == 90 || this.mFixedShot2ShotTime != -1) {
                this.mBlockQuickShot = true;
                String str3 = TAG;
                Log.d(str3, "isParallelSessionEnable:" + isParallelSessionEnable() + ", and block quick shot");
                return;
            }
            Log.d(TAG, "startNormalCapture force set CameraStateConstant.IDLE");
            setCameraState(1);
            enableCameraControls(true);
            return;
        }
        this.mCamera2Device.captureGroupShotPictures(this, this.mActivity.getImageSaver(), this.mTotalJpegCallbackNum, this.mActivity);
        this.mBlockQuickShot = true;
    }

    private void stopCpuBoost() {
        BaseBoostFramework baseBoostFramework;
        if (c.isMTKPlatform() && (baseBoostFramework = this.mCpuBoost) != null) {
            baseBoostFramework.stopBoost();
            this.mCpuBoost = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stopMultiSnap() {
        Log.d(TAG, "stopMultiSnap: start");
        this.mHandler.removeMessages(49);
        if (this.mMultiSnapStatus) {
            this.mLastCaptureTime = System.currentTimeMillis();
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null && this.mMultiSnapStatus) {
                camera2Proxy.captureAbortBurst();
                this.mMultiSnapStatus = false;
            }
            int i = this.mReceivedJpegCallbackNum;
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
            boolean z = true;
            trackGeneralInfo(hashMap, i, true, this.mBeautyValues, this.mLocation != null, this.mCurrentAiScene);
            MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
            pictureTakenParameter.takenNum = i;
            pictureTakenParameter.burst = true;
            if (this.mLocation == null) {
                z = false;
            }
            pictureTakenParameter.location = z;
            pictureTakenParameter.aiSceneName = getCurrentAiSceneName();
            pictureTakenParameter.isEnteringMoon = this.mEnteringMoonMode;
            pictureTakenParameter.isSelectMoonMode = this.mIsMoonMode;
            pictureTakenParameter.isSuperNightInCaptureMode = this.mShowSuperNightHint;
            pictureTakenParameter.beautyValues = this.mBeautyValues;
            pictureTakenParameter.isNearRangeMode = this.mIsNearRangeMode;
            trackPictureTaken(pictureTakenParameter);
            animateCapture();
            this.mUpdateImageTitle = false;
            this.mHandler.sendEmptyMessageDelayed(48, 800);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stopTimerBurst() {
        ModeProtocol.ManuallyAdjust manuallyAdjust;
        keepScreenOnAwhile();
        AutoLockManager.getInstance(this.mActivity).cancelHibernate();
        TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
        timerBurstController.setPendingStopTimerBurst(true);
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onFinish();
        }
        this.mHandler.removeMessages(63);
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.showZoomButton();
        }
        CameraStatUtils.trackTimerBurst(CameraSettings.getTimerBurstTotalCount(), (float) timerBurstController.getIntervalTimer(), timerBurstController.getCaptureIndex() - 1);
        timerBurstController.setInTimerBurstShotting(false);
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setInTimerBurstShotting(false);
        }
        if (this.mModuleIndex == 167 && (manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181)) != null) {
            manuallyAdjust.setManuallyLayoutVisible(true);
        }
        CameraTimer cameraTimer = this.mCameraTimer;
        if (cameraTimer != null && cameraTimer.isRunning()) {
            this.mTopAlert.hideDelayNumber();
            this.mCameraTimer.dispose();
        }
        this.mIsStartCount = false;
        this.mTopAlert.reInitAlert(true);
    }

    private boolean supportAnchorFrameAsThumbnail() {
        CameraCapabilities cameraCapabilities;
        if (!CameraSettings.isCameraParallelProcessEnable() || (cameraCapabilities = this.mCameraCapabilities) == null || !cameraCapabilities.isSupportAnchorFrame() || DataRepository.dataItemFeature().ti()) {
            return false;
        }
        int i = this.mModuleIndex;
        return (i == 163 || i == 165 || anchorFrameWhenPortrait()) && !CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex) && !isFrontCamera() && !this.mIsImageCaptureIntent;
    }

    private void trackAISceneChanged(int i, int i2) {
        CameraStatUtils.trackAISceneChanged(i, i2, getResources());
    }

    private void trackBeautyInfo(int i, boolean z, BeautyValues beautyValues) {
        CameraStatUtils.trackBeautyInfo(i, z ? "front" : "back", beautyValues);
    }

    private void trackCaptureModuleInfo(Map map, int i, boolean z, boolean z2) {
        if (map == null) {
            map = new HashMap();
        }
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        DataRepository.dataItemConfig();
        int triggerMode = getTriggerMode();
        boolean isFrontCamera = isFrontCamera();
        int i2 = this.mModuleIndex;
        map.put("attr_trigger_mode", CameraStatUtils.triggerModeToName(triggerMode));
        String str = "on";
        map.put(MistatsConstants.LiveShotAttr.PARAM_LIVESHOT, CameraSettings.isLiveShotOn() ? str : "off");
        map.put(MistatsConstants.BaseEvent.QUALITY, CameraSettings.getEncodingQuality(z).name().toLowerCase(Locale.ENGLISH));
        map.put(MistatsConstants.Setting.PARAM_TIME_WATERMARK, CameraSettings.isTimeWaterMarkOpen() ? str : "off");
        map.put(MistatsConstants.Setting.PARAM_DEVICE_WATERMARK, CameraSettings.isDualCameraWaterMarkOpen() || CameraSettings.isFrontCameraWaterMarkOpen() ? str : "off");
        if (!isFrontCamera) {
            map.put(MistatsConstants.Setting.PARAM_TILTSHIFT, (z || !CameraSettings.isTiltShiftOn()) ? "off" : dataItemRunning.getComponentRunningTiltValue().getComponentValue(i2));
            if (z || !this.isGradienterOn) {
                str = "off";
            }
            map.put(MistatsConstants.Setting.PARAM_GRADIENTER, str);
            if (DataRepository.dataItemFeature().qj() || DataRepository.dataItemFeature().rj()) {
                map.put(MistatsConstants.Setting.PARAM_DOCUMENT_MODE, CameraStatUtils.getDocumentModeValue(this.mModuleIndex));
            }
        }
        if (EffectController.getInstance().getAiColorCorrectionVersion() >= 1) {
            map.put(MistatsConstants.Setting.PARAM_AICC, Boolean.valueOf(this.mAiSceneEnabled));
        } else {
            map.put(MistatsConstants.Setting.PARAM_AICC, false);
        }
        if (isHeicPreferred()) {
            map.put(MistatsConstants.Setting.PARAM_HEIC, Boolean.valueOf(CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat)));
        } else {
            map.put(MistatsConstants.Setting.PARAM_HEIC, false);
        }
        if (z2) {
            map.put(MistatsConstants.CaptureAttr.PARAM_NEAR_RANGE_MODE, CameraStatUtils.modeIdToName(this.mModuleIndex));
        }
        MistatsWrapper.mistatEvent("M_capture_", map);
        if (z) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.CaptureSence.PARAM_BURST_COUNT, CameraStatUtils.burstShotNumToName(i));
            MistatsWrapper.mistatEventSimple(MistatsConstants.CaptureSence.KEY_BURST_SHOT_TIMES, hashMap);
        }
    }

    private void trackManualInfo(int i) {
        CameraStatUtils.trackPictureTakenInManual(i, getManualValue(CameraSettings.KEY_WHITE_BALANCE, getString(R.string.pref_camera_whitebalance_default)), getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)), getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default)), getManualValue(CameraSettings.KEY_QC_MANUAL_EXPOSURE_VALUE, getString(R.string.pref_camera_iso_default)), this.mModuleIndex, getActualCameraId());
    }

    private boolean triggerHDR(boolean z) {
        if (!this.isZooming && isDoingAction()) {
            return false;
        }
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (componentHdr.isEmpty()) {
            return false;
        }
        String componentValue = componentHdr.getComponentValue(this.mModuleIndex);
        if (!"auto".equals(componentValue) && !componentHdr.isHdrOnWithChecker(componentValue)) {
            return false;
        }
        if (z) {
            if ((getZoomRatio() > HybridZoomingSystem.getZoomRatioNone(isFrontCamera(), this.mOrientation) && ComponentConfigHdr.getHdrUIStatus(componentValue) != 1 && !DataRepository.dataItemFeature().El()) || this.mCurrentAiScene == -1) {
                return false;
            }
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null && camera2Proxy.isNeedFlashOn() && !this.mSupportFlashHDR) {
                return false;
            }
        }
        return !this.mIsNeedNightHDR;
    }

    private void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported) {
            if (isDeviceAlive()) {
                this.mCamera2Device.unlockExposure();
            }
            if (!DataRepository.dataItemFeature().nm() && this.mUltraWideAELocked) {
                String focusMode = CameraSettings.getFocusMode();
                String str = TAG;
                Log.d(str, "unlockAEAF: focusMode = " + focusMode);
                setFocusMode(focusMode);
                this.mUltraWideAELocked = false;
            }
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
        }
    }

    private void unregisterSensor() {
        if (this.isGradienterOn) {
            this.mActivity.getSensorStateManager().setGradienterEnabled(false);
        }
        this.mActivity.getSensorStateManager().setLieIndicatorEnabled(false);
        if (DataRepository.dataItemFeature().Cl()) {
            this.mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(false);
        }
        this.mIsShowLyingDirectHintStatus = -1;
        this.mHandler.removeMessages(58);
    }

    private boolean updateAIWatermark() {
        boolean needActiveASD = needActiveASD();
        if (this.mAIWatermarkEnable != needActiveASD) {
            FunctionParseAiScene functionParseAiScene = this.mFunctionParseAiScene;
            if (functionParseAiScene != null) {
                functionParseAiScene.resetScene();
            }
            this.mAIWatermarkEnable = needActiveASD;
            this.mCamera2Device.setAiASDEnable(this.mAiSceneEnabled || this.mAIWatermarkEnable);
            if (this.mAIWatermarkEnable) {
                this.mCamera2Device.setASDPeriod(300);
            }
        }
        return this.mAIWatermarkEnable;
    }

    private void updateASD() {
        Camera2Proxy camera2Proxy;
        if ((163 == getModuleIndex() || 165 == getModuleIndex() || 171 == getModuleIndex() || 186 == getModuleIndex() || 182 == getModuleIndex() || 205 == getModuleIndex() || 188 == getModuleIndex()) && (camera2Proxy = this.mCamera2Device) != null) {
            camera2Proxy.setASDEnable(true);
        }
    }

    private void updateASDDirtyDetect() {
        boolean isLensDirtyDetectEnabled = CameraSettings.isLensDirtyDetectEnabled();
        boolean z = DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_LENS_DIRTY_TIP, DataRepository.dataItemFeature().gh());
        Camera2Proxy cameraDevice = getCameraDevice();
        if (cameraDevice != null) {
            cameraDevice.setAsdDirtyEnable(isLensDirtyDetectEnabled && z && CameraSettings.shouldShowLensDirtyDetectHint());
        }
    }

    private void updateAiScene() {
        FunctionParseAiScene functionParseAiScene = this.mFunctionParseAiScene;
        if (functionParseAiScene != null) {
            functionParseAiScene.resetScene();
        }
        this.mCurrentAiScene = 0;
        this.mAiSceneEnabled = CameraSettings.getAiSceneOpen(this.mModuleIndex);
        if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
            this.mAiSceneEnabled = false;
        }
        this.mCamera2Device.setAiASDEnable(this.mAiSceneEnabled || this.mAIWatermarkEnable);
        if (!this.mAiSceneEnabled || !this.mCameraCapabilities.isSupportAIIE()) {
            this.mCamera2Device.setAIIEPreviewEnable(false);
        } else {
            this.mCamera2Device.setAIIEPreviewEnable(true);
        }
        if ((isFrontCamera() && ModuleManager.isCapture()) || !this.mAiSceneEnabled) {
            this.mCamera2Device.setCameraAI30(this.mAiSceneEnabled);
        }
        setAiSceneEffect(this.mCurrentAiScene);
        this.mCamera2Device.setASDScene(this.mCurrentAiScene);
        if (this.mAiSceneEnabled) {
            this.mCamera2Device.setASDPeriod(300);
            return;
        }
        hideSceneSelector();
        if (this.mMagneticSensorDetect == null || !this.mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable() || !this.mMagneticSensorDetect.isLockHDRChecker("updateAiScene")) {
            updateHDRPreference();
        }
        updateFlashPreference();
        updateBeauty();
    }

    private void updateAiShutter() {
        if (this.mCameraCapabilities.isSupportAiShutter()) {
            this.mIsAiShutterOn = CameraSettings.isAiShutterOn(this.mModuleIndex);
            this.mCamera2Device.applyAiShutterEnable(this.mIsAiShutterOn);
        }
    }

    private void updateAiShutterExistMotion(CaptureResult captureResult) {
        if (this.mIsAiShutterOn) {
            boolean isAishutExistMotion = CaptureResultParser.isAishutExistMotion(captureResult);
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setAiShutterExistMotion(isAishutExistMotion);
            }
        }
    }

    private void updateAlgorithmName() {
        String str;
        if (c.ym()) {
            str = null;
        } else if (this.mCamera2Device.isSingleBokehEnabled()) {
            str = DataRepository.dataItemFeature().zh() > 0 ? Util.ALGORITHM_NAME_MI_SOFT_PORTRAIT_ENCRYPTED : Util.ALGORITHM_NAME_MI_SOFT_PORTRAIT;
        } else if (isPortraitMode()) {
            CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
            if (cameraCapabilities == null || !cameraCapabilities.isSupportMiDualBokeh()) {
                CameraCapabilities cameraCapabilities2 = this.mCameraCapabilities;
                str = (cameraCapabilities2 == null || !cameraCapabilities2.isSupportMegviiDualBokeh()) ? Util.ALGORITHM_NAME_DUAL_PORTRAIT : Util.ALGORITHM_NAME_MEGVII_DUAL_PORTRAIT;
            } else {
                str = Util.ALGORITHM_NAME_MI_DUAL_PORTRAIT;
            }
        } else {
            str = this.mMutexModePicker.getAlgorithmName();
        }
        String str2 = TAG;
        Log.d(str2, "updateAlgorithmName:" + str);
        this.mAlgorithmName = str;
    }

    private boolean updateAnchorFramePreview() {
        return supportAnchorFrameAsThumbnail();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateAsdSceneResult(int i) {
        String str = TAG;
        Log.e(str, "update asd scene result,newResult:" + i);
        exitAsdScene(this.mCurrentAsdScene);
        enterAsdScene(i);
        setCurrentAsdScene(i);
    }

    private void updateBeauty() {
        boolean z = this.mModuleIndex == 173 && DataRepository.dataItemFeature().Cj();
        int i = this.mModuleIndex;
        if (i == 163 || i == 165 || i == 171 || z || i == 205) {
            if (this.mBeautyValues == null) {
                this.mBeautyValues = new BeautyValues();
            }
            CameraSettings.initBeautyValues(this.mBeautyValues, this.mModuleIndex);
            if (!DataRepository.dataItemConfig().getComponentConfigBeauty().isClosed(this.mModuleIndex) && this.mCurrentAiScene == 25 && !isFaceBeautyOn(this.mBeautyValues)) {
                ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                if (componentRunningShine.supportBeautyLevel()) {
                    this.mBeautyValues.mBeautyLevel = BeautyConstant.LEVEL_LOW;
                } else {
                    componentRunningShine.supportSmoothLevel();
                }
                Log.d(TAG, String.format(Locale.ENGLISH, "Human scene mode detected, auto set beauty level from %s to %s", BeautyConstant.LEVEL_CLOSE, this.mBeautyValues.mBeautyLevel));
            }
            String str = TAG;
            Log.d(str, "updateBeauty(): " + this.mBeautyValues);
            this.mCamera2Device.setBeautyValues(this.mBeautyValues);
            this.mIsBeautyBodySlimOn = this.mBeautyValues.isBeautyBodyOn();
            updateFaceAgeAnalyze();
        }
    }

    private void updateBokeh() {
        ComponentConfigBokeh componentBokeh = DataRepository.dataItemConfig().getComponentBokeh();
        if (!ModuleManager.isPortraitModule() && !"on".equals(componentBokeh.getComponentValue(this.mModuleIndex))) {
            this.mCamera2Device.setSingleBokeh(false);
            this.mCamera2Device.setDualBokehEnable(false);
        } else if (isSingleCamera() || (DataRepository.dataItemFeature().xi() && ModuleManager.isPortraitModule() && isFrontCamera())) {
            this.mCamera2Device.setSingleBokeh(true);
            this.mCamera2Device.setDualBokehEnable(false);
        } else {
            this.mCamera2Device.setSingleBokeh(false);
            this.mCamera2Device.setDualBokehEnable(true);
        }
    }

    private void updateCaptureTriggerFlow() {
    }

    private void updateCinematicPhoto() {
        boolean isCinematicAspectRatioEnabled = CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex);
        this.mCamera2Device.setCinematicPhotoEnabled(isCinematicAspectRatioEnabled);
        if (isCinematicAspectRatioEnabled) {
            int i = this.mModuleIndex;
            if (i == 165 || i == 163 || i == 171) {
                EffectController.getInstance().setCinematicEnable(true);
                CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
                if (circularMediaRecorder != null) {
                    circularMediaRecorder.setCinematicEnable(true);
                }
            }
        }
    }

    private void updateColorEnhance() {
        if (DataRepository.dataItemFeature().supportColorEnhance() && this.mCamera2Device != null) {
            this.mCamera2Device.setColorEnhanceEnable(DataRepository.dataItemRunning().getComponentRunningColorEnhance().isEnabled(getModuleIndex()));
        }
    }

    private void updateContrast() {
        this.mCamera2Device.setContrast(Integer.parseInt(getString(R.string.pref_camera_contrast_default)));
    }

    private void updateDecodePreview() {
        if (this.mIsGoogleLensAvailable || scanQRCodeEnabled() || c.Fj() || CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
            String str = TAG;
            Log.d(str, "updateDecodePreview: PreviewDecodeManager AlgorithmPreviewSize = " + this.mCamera2Device.getAlgorithmPreviewSize());
            this.mCamera2Device.startPreviewCallback(PreviewDecodeManager.getInstance().getPreviewCallback(), null);
            this.mMainProtocol.hideOrShowDocument(true);
            PreviewDecodeManager.getInstance().startDecode();
        }
        if (this.mSupportAnchorFrameAsThumbnail) {
            this.mCamera2Device.startPreviewCallback(null, this.mCacheImageDecoder.getAnchorPreviewCallback());
            this.mCacheImageDecoder.startDecode();
        }
    }

    private void updateDeviceOrientation() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    private void updateEnablePreviewThumbnail(boolean z) {
        if (Util.isSaveToHidenFolder(this.mModuleIndex)) {
            this.mEnabledPreviewThumbnail = false;
        } else if (this.mModuleIndex != 167 || (!DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key") && !DataRepository.dataItemRunning().isSwitchOn("pref_camera_exposure_feedback"))) {
            int i = this.mModuleIndex;
            if ((i == 165 || i == 163) && DataRepository.dataItemRunning().isSwitchOn("pref_camera_tilt_shift_mode")) {
                this.mEnabledPreviewThumbnail = false;
            } else if (!isPreviewThumbnailWhenFlash()) {
                this.mEnabledPreviewThumbnail = false;
            } else if (this.mIsImageCaptureIntent) {
                this.mEnabledPreviewThumbnail = false;
            } else if (this.mModuleIndex == 173) {
                if (DataRepository.dataItemGlobal().isOnSuperNightAlgoUpAndQuickShot()) {
                    this.mEnabledPreviewThumbnail = true;
                } else {
                    this.mEnabledPreviewThumbnail = false;
                }
            } else if (this.mEnableParallelSession || this.mEnableShot2Gallery || z || (this.mReceivedJpegCallbackNum == 0 && enablePreviewAsThumbnail())) {
                this.mEnabledPreviewThumbnail = true;
            }
        } else {
            this.mEnabledPreviewThumbnail = false;
        }
        this.mActivity.setPreviewThumbnail(this.mEnabledPreviewThumbnail);
    }

    private void updateEvValue() {
        String manualValue = getManualValue(CameraSettings.KEY_QC_MANUAL_EXPOSURE_VALUE, "0");
        this.mEvValue = (int) (Float.parseFloat(manualValue) / this.mCameraCapabilities.getExposureCompensationStep());
        this.mEvState = 3;
        setEvValue();
    }

    private void updateExposureTime() {
        this.mCamera2Device.setExposureTime(Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))));
    }

    private void updateEyeLight() {
        if (isFrontCamera() && DataRepository.dataItemFeature().tl()) {
            this.mCamera2Device.setEyeLight(Integer.parseInt(CameraSettings.getEyeLightType()));
        }
    }

    private void updateFNumber() {
        if (DataRepository.dataItemFeature().isSupportBokehAdjust()) {
            this.mCamera2Device.setFNumber(CameraSettings.readFNumber());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0063  */
    private void updateFace() {
        boolean z;
        boolean z2;
        ModeProtocol.MainContentProtocol mainContentProtocol;
        if (this.mModuleIndex == 182 || this.mMultiSnapStatus) {
            z2 = false;
        } else if (CameraSettings.isMagicMirrorOn() || CameraSettings.isPortraitModeBackOn() || CameraSettings.isGroupShotOn() || CameraSettings.showGenderAge()) {
            z2 = true;
            z = true;
            mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.setSkipDrawFace(!z2 || !z);
            }
            if (z2) {
                if (!this.mFaceDetectionEnabled) {
                    this.mFaceDetectionEnabled = true;
                    startFaceDetection();
                    return;
                }
                return;
            } else if (this.mFaceDetectionEnabled) {
                stopFaceDetection(true);
                this.mFaceDetectionEnabled = false;
                return;
            } else {
                return;
            }
        } else {
            z2 = DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
            if (CameraSettings.isTiltShiftOn()) {
                z = false;
                mainContentProtocol = this.mMainProtocol;
                if (mainContentProtocol != null) {
                }
                if (z2) {
                }
            }
        }
        z = true;
        mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
        }
        if (z2) {
        }
    }

    private void updateFaceAgeAnalyze() {
        boolean z;
        this.mIsGenderAgeOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_show_gender_age_key");
        if (this.mIsGenderAgeOn) {
            z = true;
        } else {
            BeautyValues beautyValues = this.mBeautyValues;
            z = beautyValues != null ? isFaceBeautyOn(beautyValues) : false;
        }
        this.mCamera2Device.setFaceAgeAnalyze(z);
    }

    private void updateFaceScore() {
        this.mIsMagicMirrorOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_magic_mirror_key");
        this.mCamera2Device.setFaceScore(this.mIsMagicMirrorOn);
    }

    private void updateFilter() {
        setEffectFilter(CameraSettings.getShaderEffect());
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
        String str;
        if (this.mIsMoonMode) {
            this.mFocusManager.removeMessages();
            str = this.mCamera2Device.getCapabilities().supportMoonAutoFocus() ? this.mFocusManager.setFocusMode(AutoFocus.LEGACY_CONTINUOUS_PICTURE) : this.mFocusManager.setFocusMode("manual");
        } else {
            str = this.mFocusManager.setFocusMode(CameraSettings.getFocusMode());
        }
        setFocusMode(str);
        if (CameraSettings.isFocusModeSwitching() && isBackCamera()) {
            CameraSettings.setFocusModeSwitching(false);
            this.mFocusManager.resetFocusStateIfNeeded();
        }
        if (str.equals("manual")) {
            float minimumFocusDistance = (this.mCameraCapabilities.getMinimumFocusDistance() * ((float) CameraSettings.getFocusPosition())) / 1000.0f;
            if (!this.mCamera2Device.getCapabilities().supportMoonAutoFocus() && this.mIsMoonMode) {
                minimumFocusDistance = 0.5f;
            }
            this.mCamera2Device.setFocusDistance(minimumFocusDistance);
        }
    }

    private void updateFpsRange() {
        Range<Integer>[] supportedFpsRange = this.mCameraCapabilities.getSupportedFpsRange();
        Range<Integer> range = supportedFpsRange[0];
        for (Range<Integer> range2 : supportedFpsRange) {
            if (range.getUpper().intValue() < range2.getUpper().intValue() || (range.getUpper() == range2.getUpper() && range.getLower().intValue() < range2.getLower().intValue())) {
                range = range2;
            }
        }
        if (c.Kg && CameraSettings.isPortraitModeBackOn()) {
            this.mCamera2Device.setFpsRange(new Range<>(30, 30));
        }
    }

    private void updateFrontMirror() {
        this.mCamera2Device.setFrontMirror(isFrontMirror());
    }

    private void updateHDR(String str) {
        if ("auto".equals(str)) {
            this.isDetectedInHdr = false;
        }
        int mutexHdrMode = getMutexHdrMode(str);
        stopObjectTracking(true);
        if (mutexHdrMode != 0) {
            this.mMutexModePicker.setMutexMode(mutexHdrMode);
        } else if (this.mMutexModePicker.isHdr()) {
            resetMutexModeManually();
            this.mIsNeedNightHDR = false;
            String str2 = TAG;
            Log.d(str2, "resetMutexModeManually,mIsNeedNightHDR:" + this.mIsNeedNightHDR);
        }
        if (isFrontCamera() && isTriggerQcfaModeChange(false, true)) {
            this.mHandler.sendEmptyMessage(44);
        }
        if (str != null && !str.equals(this.mLastHdrMode)) {
            updateScene();
            this.mLastHdrMode = str;
        }
    }

    private void updateHighQualityPreferred() {
        this.mCamera2Device.applyHighQualityPreferred(CameraSettings.isHighQualityPreferred());
    }

    private void updateISO() {
        String string = getString(R.string.pref_camera_iso_default);
        String manualValue = getManualValue(CameraSettings.KEY_QC_ISO, string);
        if (manualValue == null || manualValue.equals(string)) {
            this.mCamera2Device.setISO(0);
        } else {
            this.mCamera2Device.setISO(Math.min(Util.parseInt(manualValue, 0), this.mCameraCapabilities.getMaxIso()));
        }
    }

    private void updateJpegQuality() {
        this.mCamera2Device.setJpegQuality(clampQuality(CameraSettings.getEncodingQuality(this.mMultiSnapStatus).toInteger(false)));
    }

    private void updateJpegThumbnailSize() {
        CameraSize jpegThumbnailSize = getJpegThumbnailSize();
        this.mCamera2Device.setJpegThumbnailSize(jpegThumbnailSize);
        String str = TAG;
        Log.d(str, "thumbnailSize=" + jpegThumbnailSize);
    }

    private void updateLiveShot() {
        if (DataRepository.dataItemFeature().Lj() && this.mModuleIndex == 163) {
            if (CameraSettings.isLiveShotOn()) {
                startLiveShot();
            } else {
                stopLiveShot(false);
            }
        }
    }

    private void updateMacroMode() {
        this.mCamera2Device.setMacroMode(CameraSettings.isMacroModeEnabled(this.mModuleIndex));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0043, code lost:
        if (r8 == false) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007c, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().Ll() != false) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0090, code lost:
        if (java.lang.Long.parseLong(getManualValue(com.android.camera.CameraSettings.KEY_QC_EXPOSURETIME, getString(com.android.camera.R.string.pref_camera_exposuretime_default))) < 250000000) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ad, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn() != false) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b9, code lost:
        if (isDualCamera() != false) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00d6, code lost:
        if (enableFrontMFNR() != false) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e5, code lost:
        if (r7.mCameraCapabilities.isMfnrMacroZoomSupported() != false) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x010c, code lost:
        if (isZoomRatioBetweenUltraAndWide() != false) goto L_0x00af;
     */
    private void updateMfnr(boolean z) {
        boolean z2 = true;
        boolean z3 = false;
        if (!isUseSwMfnr() && z) {
            if (!CameraSettings.isUltraPixelOn() || !DataRepository.dataItemFeature().hl()) {
                int i = this.mModuleIndex;
                if (i == 167) {
                    boolean z4 = DataRepository.dataItemFeature().Ml() && (isSingleCamera() || isStandaloneMacroCamera() || isUltraWideBackCamera());
                    if (!isSensorRawStreamRequired()) {
                        if (!z4) {
                        }
                        if (this.mCamera2Device != null) {
                        }
                    }
                } else {
                    if (i == 175) {
                        if (DataRepository.dataItemFeature().bk()) {
                        }
                    }
                    if (c.Jg) {
                    }
                    if (this.mMutexModePicker.isNormal()) {
                        if (!CameraSettings.isGroupShotOn()) {
                            if (isFrontCamera()) {
                            }
                            if (isStandaloneMacroCamera()) {
                            }
                            if (!DataRepository.dataItemFeature().tm()) {
                                if (getZoomRatio() != 1.0f) {
                                    if (isBackCamera()) {
                                        if (!isUltraWideBackCamera()) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                z3 = z2;
            } else {
                boolean z5 = this.mModuleIndex == 167 && Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) >= 250000000;
                if (!this.mUpscaleImageWithSR) {
                }
            }
            z2 = false;
            z3 = z2;
        }
        if (this.mCamera2Device != null) {
            Log.d(TAG, "setMfnr to " + z3);
            this.mCamera2Device.setMfnr(z3);
        }
    }

    private void updateMute() {
    }

    private void updateNormalWideLDC() {
        this.mCamera2Device.setNormalWideLDC(shouldApplyNormalWideLDC());
    }

    private void updateOIS() {
        if (CameraSettings.isPortraitModeBackOn()) {
            this.mCamera2Device.setEnableOIS(false);
        } else if (this.mModuleIndex == 167 && Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > ExtraTextUtils.GB) {
            this.mCamera2Device.setEnableOIS(false);
        } else if ((c._g || c.eh) && isDualCamera() && getZoomRatio() > 1.0f) {
            this.mCamera2Device.setEnableOIS(true);
        } else if (!isDualCamera() || this.mCameraCapabilities.isTeleOISSupported() || getZoomRatio() <= 1.0f) {
            this.mCamera2Device.setEnableOIS(true);
        } else {
            this.mCamera2Device.setEnableOIS(false);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
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
        } else {
            this.mOutputPictureSize = cameraSize;
        }
        String str = TAG;
        Log.d(str, "updateOutputSize: " + this.mOutputPictureSize);
    }

    private void updatePictureAndPreviewSize() {
        CameraSize cameraSize;
        int ei;
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
                        int ei2 = DataRepository.dataItemFeature().ei();
                        if (ei2 != 0) {
                            PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode, ei2, this.mModuleIndex, this.mBogusCameraId);
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
            if (this.mFakeTeleCameraCapabilities == null) {
                this.mFakeTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getSATCameraId());
            }
            CameraCapabilities cameraCapabilities6 = this.mFakeTeleCameraCapabilities;
            if (cameraCapabilities6 != null && cameraCapabilities6.supportFakeSat()) {
                this.mFakeTeleCameraCapabilities.setOperatingMode(this.mOperatingMode);
                this.mFakeTelePictureSize = getBestPictureSize(this.mFakeTeleCameraCapabilities.getSupportFakeSatYuvSizes());
                this.mCamera2Device.setFakeTelePictureSize(this.mFakeTelePictureSize);
                this.mFakeSatOutSize = getBestPictureSize(this.mFakeTeleCameraCapabilities.getSupportFakeSatJpegSizes());
                this.mCamera2Device.setFakeTeleOutputSize(this.mFakeSatOutSize);
                Log.d(TAG, String.format(Locale.ENGLISH, "add fakesat size: %s, output size: %s", this.mFakeTelePictureSize, this.mFakeSatOutSize));
            }
            if (DataRepository.dataItemFeature().pk()) {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s, ultraTeleSize:%s", this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize, this.mUltraTelePictureSize));
            } else {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s, fakeTeleSize: %s", this.mUltraWidePictureSize, this.mWidePictureSize, this.mTelePictureSize, this.mFakeTelePictureSize));
            }
            this.mCamera2Device.setUltraWidePictureSize(this.mUltraWidePictureSize);
            this.mCamera2Device.setWidePictureSize(this.mWidePictureSize);
            this.mCamera2Device.setTelePictureSize(this.mTelePictureSize);
            this.mPictureSize = getSatPictureSize();
        } else {
            int _h = (!isUltraTeleCamera() || this.mModuleIndex != 167) ? 0 : DataRepository.dataItemFeature()._h();
            if (isSensorRawStreamRequired()) {
                List<CameraSize> supportedOutputSizeWithAssignedMode2 = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(32);
                if (this.mModuleIndex != 167) {
                    this.mSensorRawImageSize = getBestPictureSize(supportedOutputSizeWithAssignedMode2);
                } else if (supportedOutputSizeWithAssignedMode2 == null || supportedOutputSizeWithAssignedMode2.size() == 0) {
                    Log.w(TAG, "The supported raw size list return from hal is null!");
                } else if (_h == 0) {
                    this.mSensorRawImageSize = getBestPictureSize(supportedOutputSizeWithAssignedMode2, 1.3333333f);
                } else {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode2, _h, this.mModuleIndex, this.mBogusCameraId);
                    this.mSensorRawImageSize = PictureSizeManager.getBestPictureSize(1.3333333f);
                }
                String str2 = TAG;
                Log.d(str2, "The best sensor raw image size: " + this.mSensorRawImageSize);
            }
            if (!this.mEnableParallelSession || !isPortraitMode()) {
                List<CameraSize> supportedOutputSizeWithAssignedMode3 = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i);
                CameraSize bestPictureSize = getBestPictureSize(supportedOutputSizeWithAssignedMode3);
                if (c.Qg && getOperatingMode() == 36867) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                if (DataRepository.dataItemFeature().zj() && isFrontCamera()) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                if (_h != 0) {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode3, _h, this.mModuleIndex, this.mBogusCameraId);
                    bestPictureSize = PictureSizeManager.getBestPictureSize(this.mModuleIndex);
                }
                if (isLimitSize()) {
                    bestPictureSize = getLimitSize(supportedOutputSizeWithAssignedMode3);
                }
                if (this.mModuleIndex == 173 && (ei = DataRepository.dataItemFeature().ei()) != 0) {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode3, ei, this.mModuleIndex, this.mBogusCameraId);
                    bestPictureSize = PictureSizeManager.getBestPictureSize(this.mModuleIndex);
                }
                this.mPictureSize = bestPictureSize;
                if (isEnableQcfaForAlgoUp()) {
                    CameraSize cameraSize2 = this.mPictureSize;
                    int i4 = cameraSize2.width / 2;
                    int i5 = cameraSize2.height / 2;
                    this.mBinningPictureSize = PictureSizeManager.getBestPictureSize(this.mCameraCapabilities.getSupportedOutputStreamSizes(35), Util.getRatio(CameraSettings.getPictureSizeRatioString(this.mModuleIndex)), i4 * i5);
                    if (this.mBinningPictureSize.isEmpty()) {
                        this.mBinningPictureSize = new CameraSize(i4, i5);
                    }
                }
            } else {
                updatePortraitPictureSize(i);
            }
        }
        if (DataRepository.dataItemFeature().Ol()) {
            this.mTuningBufferSize = this.mCameraCapabilities.getTuningBufferSize();
            this.mCamera2Device.setTuningBufferSize(this.mTuningBufferSize);
            String str3 = TAG;
            Log.d(str3, "updateSize: tuning buffer size: " + this.mTuningBufferSize);
        }
        List<CameraSize> supportedOutputSizeWithAssignedMode4 = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        CameraSize cameraSize3 = this.mPictureSize;
        double previewAspectRatio = (double) CameraSettings.getPreviewAspectRatio(cameraSize3.width, cameraSize3.height);
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSizeWithAssignedMode4, previewAspectRatio);
        this.mCamera2Device.setPreviewSize(this.mPreviewSize);
        if (this.mIsGoogleLensAvailable) {
            this.mCamera2Device.setAlgorithmPreviewSize(Util.getAlgorithmPreviewSize(supportedOutputSizeWithAssignedMode4, previewAspectRatio, this.mPreviewSize));
        } else {
            this.mCamera2Device.setAlgorithmPreviewSize(this.mPreviewSize);
        }
        this.mCamera2Device.setAlgorithmPreviewFormat(35);
        this.mOutputPictureFormat = isHeicPreferred() ? CompatibilityUtils.IMAGE_FORMAT_HEIC : 256;
        String str4 = TAG;
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[1];
        String str5 = "HEIC";
        objArr[0] = CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? str5 : "JPEG";
        Log.d(str4, String.format(locale, "updateSize: use %s as preferred output image format", objArr));
        if (this.mEnableParallelSession) {
            List<CameraSize> supportedHeicOutputStreamSizes = CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? this.mCameraCapabilities.hasStandaloneHeicStreamConfigurations() ? this.mCameraCapabilities.getSupportedHeicOutputStreamSizes() : this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(this.mOutputPictureFormat) : this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256);
            if (CameraSettings.isUltraPixelOn() && CameraSettings.isSRTo108mModeOn()) {
                this.mOutputPictureSize = this.SIZE_108M;
            } else if (this.mModuleIndex == 165) {
                CameraSize cameraSize4 = this.mPictureSize;
                int min = Math.min(cameraSize4.width, cameraSize4.height);
                this.mOutputPictureSize = PictureSizeManager.getBestSquareSize(supportedHeicOutputStreamSizes, min, c.isMTKPlatform() && isFrontCamera());
                if (this.mOutputPictureSize.isEmpty()) {
                    String str6 = TAG;
                    Log.w(str6, "Could not find a proper squared Jpeg size, defaults to: " + min + "x" + min);
                    this.mOutputPictureSize = new CameraSize(min, min);
                }
            } else if (z) {
                this.mOutputPictureSize = this.mPictureSize;
            } else if (isLimitSize()) {
                this.mOutputPictureSize = getLimitSize(supportedHeicOutputStreamSizes);
            } else {
                this.mOutputPictureSize = PictureSizeManager.getBestPictureSize(supportedHeicOutputStreamSizes, this.mModuleIndex);
            }
            String str7 = TAG;
            Locale locale2 = Locale.ENGLISH;
            Object[] objArr2 = new Object[2];
            objArr2[0] = CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? str5 : "JPEG";
            objArr2[1] = this.mOutputPictureSize;
            Log.d(str7, String.format(locale2, "updateSize: algoUp output size (%s): %s", objArr2));
        }
        if (this.mIsImageCaptureIntent && this.mCameraCapabilities.isSupportLightTripartite() && this.mPictureSize.width > 4100) {
            CameraSize cameraSize5 = null;
            try {
                PictureSizeManager.initializeLimitWidth(this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i), 4100, this.mModuleIndex, this.mBogusCameraId);
                cameraSize5 = PictureSizeManager.getBestPictureSize(this.mModuleIndex);
            } catch (Exception unused) {
                Log.e(TAG, "No find tempSize for tripartite used");
            }
            if (cameraSize5 != null && cameraSize5.width >= 3000) {
                if (this.mEnableParallelSession) {
                    List<CameraSize> supportedOutputSizeWithAssignedMode5 = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256);
                    if (this.mModuleIndex == 165) {
                        int min2 = Math.min(cameraSize5.width, cameraSize5.height);
                        cameraSize = new CameraSize(min2, min2);
                    } else {
                        cameraSize = cameraSize5;
                    }
                    if (supportedOutputSizeWithAssignedMode5.contains(cameraSize)) {
                        this.mPictureSize = cameraSize5;
                        this.mOutputPictureSize = cameraSize;
                        Log.d(TAG, String.format(Locale.ENGLISH, "updateSize: algoUp picture size for tripartite (%s): %s", "JPEG", this.mOutputPictureSize));
                    }
                } else {
                    this.mPictureSize = cameraSize5;
                }
            }
        }
        String str8 = TAG;
        Locale locale3 = Locale.ENGLISH;
        Object[] objArr3 = new Object[4];
        if (this.mEnableParallelSession) {
            str5 = "YUV";
        } else if (!CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat)) {
            str5 = "JPEG";
        }
        objArr3[0] = str5;
        objArr3[1] = this.mPictureSize;
        objArr3[2] = this.mPreviewSize;
        objArr3[3] = this.mSensorRawImageSize;
        Log.d(str8, String.format(locale3, "updateSize: picture size (%s): %s, preview size: %s, sensor raw image size: %s", objArr3));
        CameraSize cameraSize6 = this.mPreviewSize;
        updateCameraScreenNailSize(cameraSize6.width, cameraSize6.height);
        checkDisplayOrientation();
        CameraSize cameraSize7 = this.mPreviewSize;
        setVideoSize(cameraSize7.width, cameraSize7.height);
    }

    private void updatePortraitLighting() {
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if (cameraCapabilities != null) {
            FilterFactory.setLightingVersion(cameraCapabilities.getPortraitLightingVersion());
        }
        String valueOf = String.valueOf(CameraSettings.getPortraitLightingPattern());
        this.mIsPortraitLightingOn = !valueOf.equals("0");
        this.mCamera2Device.setPortraitLighting(Integer.parseInt(valueOf));
        setLightingEffect();
    }

    private void updatePortraitPictureSize(int i) {
        int i2;
        boolean z;
        boolean z2;
        if (!isFrontCamera()) {
            z2 = DataRepository.dataItemFeature().ji();
            z = DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
            i2 = z ? Camera2DataContainer.getInstance().getUltraWideCameraId() : c.vn() ? this.mCamera2Device.getBokehAuxCameraId() : Camera2DataContainer.getInstance().getAuxCameraId();
        } else if (isDualFrontCamera()) {
            i2 = Camera2DataContainer.getInstance().getAuxFrontCameraId();
            z2 = true;
            z = false;
        } else {
            i2 = -1;
            z2 = false;
            z = false;
        }
        Log.d(TAG, "BS = " + z2 + " UW = " + z + " id = " + i2);
        PictureSizeManager.initializeLimitWidth(this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i), isBackCamera() ? DataRepository.dataItemFeature().rh() : 0, this.mModuleIndex, this.mBogusCameraId);
        CameraSize bestPictureSize = PictureSizeManager.getBestPictureSize(this.mModuleIndex);
        if (c.Qg && getOperatingMode() == 36867) {
            bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
        }
        if (DataRepository.dataItemFeature().zj() && isFrontCamera()) {
            bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
        }
        CameraSize cameraSize = null;
        if (-1 == i2) {
            this.mPictureSize = bestPictureSize;
            this.mSubPictureSize = null;
        } else {
            if (z2) {
                cameraSize = bestPictureSize;
            }
            CameraSize pictureSize = getPictureSize(i2, i, cameraSize);
            if (z || z2) {
                this.mPictureSize = bestPictureSize;
                this.mSubPictureSize = pictureSize;
            } else {
                this.mPictureSize = pictureSize;
                this.mSubPictureSize = bestPictureSize;
            }
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "mainSize = %s subSize = %s", this.mPictureSize, this.mSubPictureSize));
    }

    private void updateSRAndMFNR() {
        if (!this.mMutexModePicker.isHdr()) {
            boolean z = (c.Zg || c.bh) && isIn3OrMoreSatMode() && getZoomRatio() > 1.0f && this.mCamera2Device.getSatMasterCameraId() == 2;
            if (z) {
                this.mMutexModePicker.resetMutexMode();
            } else {
                updateSuperResolution();
            }
            if (!z && DataRepository.dataItemFeature().hl()) {
                updateMfnr(CameraSettings.isMfnrSatEnable());
            }
        }
    }

    private void updateSaturation() {
        this.mCamera2Device.setSaturation(Integer.parseInt(getString(R.string.pref_camera_saturation_default)));
    }

    private void updateScene() {
        if (c.Dn()) {
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            if (this.mMutexModePicker.isSceneHdr()) {
                this.mSceneMode = CameraScene.HDR;
            } else if (!dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key")) {
                this.mSceneMode = "-1";
            } else {
                this.mSceneMode = dataItemRunning.getComponentRunningSceneValue().getComponentValue(this.mModuleIndex);
            }
            String str = TAG;
            Log.d(str, "sceneMode=" + this.mSceneMode + " mutexMode=" + this.mMutexModePicker.getMutexMode());
            if (!setSceneMode(this.mSceneMode)) {
                this.mSceneMode = "-1";
            }
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass15 */

                public void run() {
                    Camera2Module.this.updateSceneModeUI();
                }
            });
            if ("0".equals(this.mSceneMode) || "-1".equals(this.mSceneMode)) {
                this.mFocusManager.overrideFocusMode(null);
            } else {
                this.mFocusManager.overrideFocusMode(AutoFocus.LEGACY_CONTINUOUS_PICTURE);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateSceneModeUI() {
        if (DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key")) {
            DataRepository.dataItemConfig().getComponentHdr().setComponentValue(163, "off");
            String flashModeByScene = CameraSettings.getFlashModeByScene(this.mSceneMode);
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.disableMenuItem(false, 194);
                if (flashModeByScene != null) {
                    this.mTopAlert.disableMenuItem(false, 193);
                } else {
                    this.mTopAlert.enableMenuItem(false, 193);
                }
                this.mTopAlert.hideExtraMenu();
            }
        } else {
            ModeProtocol.TopAlert topAlert2 = this.mTopAlert;
            if (topAlert2 != null) {
                topAlert2.enableMenuItem(false, 193, 194);
            }
        }
        ModeProtocol.TopAlert topAlert3 = this.mTopAlert;
        if (topAlert3 != null) {
            topAlert3.updateConfigItem(193, 194);
            if ("3".equals(DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex)) && this.mCurrentAsdScene == 0) {
                setCurrentAsdScene(-1);
            }
        }
        updatePreferenceInWorkThread(11, 10);
    }

    private void updateSharpness() {
        this.mCamera2Device.setSharpness(Integer.parseInt(getString(R.string.pref_camera_sharpness_default)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x012c, code lost:
        if (com.android.camera.CameraSettings.isUltraPixelRawOn() != false) goto L_0x013f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x013c, code lost:
        if (shouldDoMultiFrameCapture() != false) goto L_0x00ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0126, code lost:
        if (r9.mModuleIndex == 167) goto L_0x013f;
     */
    private void updateShotDetermine() {
        int i;
        int i2 = 1;
        boolean z = this.mModuleIndex == 171 && (!isBackCamera() ? DataRepository.dataItemFeature().Wl() || DataRepository.dataItemFeature().wl() : c.wn() || DataRepository.dataItemFeature().Vl());
        this.mEnableParallelSession = isParallelSessionEnable();
        if (!this.mIsImageCaptureIntent) {
            this.mEnableShot2Gallery = !this.mEnableParallelSession && DataRepository.dataItemFeature().cm() && enablePreviewAsThumbnail() && !CameraSettings.isLiveShotOn();
            int moduleIndex = getModuleIndex();
            if (!(moduleIndex == 163 || moduleIndex == 165 || moduleIndex == 167)) {
                if (moduleIndex != 171) {
                    if (moduleIndex == 173) {
                        if (!DataRepository.dataItemGlobal().isOnSuperNightHalfAlgoUp()) {
                            if (!DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode()) {
                                if (isCurrentRawDomainBasedSuperNight()) {
                                    i = 10;
                                }
                                i2 = 0;
                                Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                                this.mCamera2Device.setShotType(i2);
                                this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                            }
                            i2 = 8;
                            Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                            this.mCamera2Device.setShotType(i2);
                            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                        }
                        i2 = 5;
                        Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                        this.mCamera2Device.setShotType(i2);
                        this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                    } else if (!(moduleIndex == 175 || moduleIndex == 182 || moduleIndex == 186 || moduleIndex == 205)) {
                        this.mEnableParallelSession = false;
                        return;
                    }
                } else if (!this.mEnableParallelSession) {
                    if (z) {
                        i = 2;
                    }
                    i2 = 0;
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    this.mCamera2Device.setShotType(i2);
                    this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                } else if (!isBackCamera() || !DataRepository.dataItemFeature().Il()) {
                    if (!shouldDoMultiFrameCapture()) {
                        if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                            if (z) {
                                i = 6;
                            }
                            i2 = 5;
                            Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                            this.mCamera2Device.setShotType(i2);
                            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                        }
                        if (z) {
                            i = 7;
                        }
                        i2 = 5;
                        Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                        this.mCamera2Device.setShotType(i2);
                        this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                    }
                    i2 = 8;
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    this.mCamera2Device.setShotType(i2);
                    this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                } else {
                    i2 = 11;
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    this.mCamera2Device.setShotType(i2);
                    this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                }
            }
            if (!this.mEnableParallelSession) {
                if (this.mMultiSnapStatus) {
                    i2 = 3;
                } else if (this.mConfigRawStream) {
                }
            } else if (this.mMultiSnapStatus) {
                i2 = 9;
            }
            Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
            this.mCamera2Device.setShotType(i2);
            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
        } else if (this.mEnableParallelSession) {
            i2 = -5;
            if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                if (z) {
                    i = -7;
                }
                Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                this.mCamera2Device.setShotType(i2);
                this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
            }
            if (z) {
                i = -6;
            }
            Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
            this.mCamera2Device.setShotType(i2);
            this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
        } else {
            i = z ? -3 : -2;
        }
        i2 = i;
        Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
        this.mCamera2Device.setShotType(i2);
        this.mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
    }

    private boolean updateSpecshotMode() {
        boolean z = false;
        if (!c.isMTKPlatform()) {
            return false;
        }
        int i = this.mModuleIndex;
        if ((i == 163 || i == 165 || i == 175) && !CameraSettings.isFrontCamera() && ((Camera2DataContainer.getInstance().getMainBackCameraId() == getActualCameraId() || Camera2DataContainer.getInstance().getSATCameraId() == getActualCameraId()) && getZoomRatio() == 1.0f)) {
            z = true;
        }
        this.mCamera2Device.setSpecshotModeEnable(z);
        return z;
    }

    private void updateSuperNight() {
        boolean z = true;
        this.mCamera2Device.updateFlashAutoDetectionEnabled(true);
        if (this.mModuleIndex != 173 && !CameraSettings.isSuperNightOn()) {
            z = false;
        }
        if (CameraSettings.isSuperNightOn()) {
            this.mCamera2Device.updateFlashAutoDetectionEnabled(false);
        }
        this.mCamera2Device.setSuperNight(z);
    }

    private void updateSuperResolution() {
        if (isFrontCamera() || this.mModuleIndex == 173) {
            return;
        }
        if (isUltraWideBackCamera()) {
            Log.d(TAG, "SR force off for ultra wide camera");
        } else if (isStandaloneMacroCamera() && !DataRepository.dataItemFeature().Kl()) {
            Log.d(TAG, "HAL doesn't support SR in macro mode.");
        } else if (isStandaloneMacroCamera() && this.mCameraCapabilities.isMfnrMacroZoomSupported()) {
            Log.d(TAG, "macro camera prefers MFNR to SR");
        } else if (!CameraSettings.isSREnable()) {
            Log.d(TAG, "SR is disabled");
        } else {
            if (CameraSettings.isUltraPixelOn()) {
                if (DataRepository.dataItemFeature().hl()) {
                    this.mCamera2Device.setSuperResolution(this.mUpscaleImageWithSR);
                    return;
                } else if (DataRepository.dataItemFeature().bk() && DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn()) {
                    Log.d(TAG, "108MP or 64MP doesn't support SR");
                    return;
                }
            }
            if (this.mModuleIndex == 167) {
                boolean bm = DataRepository.dataItemFeature().bm();
                if (isSensorRawStreamRequired() || !bm || ((!isUltraTeleCamera() && !isAuxCamera()) || this.mCamera2Device == null || Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) >= 250000000)) {
                    Camera2Proxy camera2Proxy = this.mCamera2Device;
                    if (camera2Proxy != null) {
                        camera2Proxy.setSuperResolution(false);
                        return;
                    }
                    return;
                }
                this.mCamera2Device.setSuperResolution(true);
            } else if (getZoomRatio() > 1.0f || this.mUpscaleImageWithSR) {
                if (CameraSettings.isGroupShotOn()) {
                    if (this.mMutexModePicker.isSuperResolution()) {
                        this.mMutexModePicker.resetMutexMode();
                    }
                } else if (this.mMutexModePicker.isNormal()) {
                    this.mMutexModePicker.setMutexMode(9);
                }
            } else if (DataRepository.dataItemRunning().isSwitchOn("pref_camera_super_resolution_key")) {
            } else {
                if (this.mMutexModePicker.isSuperResolution()) {
                    this.mMutexModePicker.resetMutexMode();
                    return;
                }
                Camera2Proxy camera2Proxy2 = this.mCamera2Device;
                if (camera2Proxy2 != null) {
                    camera2Proxy2.setSuperResolution(false);
                }
            }
        }
    }

    private void updateSwMfnr() {
        boolean isUseSwMfnr = isUseSwMfnr();
        String str = TAG;
        Log.d(str, "setSwMfnr to " + isUseSwMfnr);
        this.mCamera2Device.setSwMfnr(isUseSwMfnr);
    }

    private void updateTargetZoom() {
        float readTargetZoom = CameraSettings.readTargetZoom();
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setTargetZoom(readTargetZoom);
        }
    }

    private void updateThumbProgress(boolean z) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.updateLoading(z);
        }
    }

    private void updateUltraPixelPortrait() {
        this.mCamera2Device.setUltraPixelPortrait(CameraSettings.isUltraPixelPortraitFrontOn());
    }

    private void updateUltraWideLDC() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setUltraWideLDC(shouldApplyUltraWideLDC());
            CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
            if (cameraCapabilities != null && cameraCapabilities.supportSATUltraWideLDCEnable()) {
                this.mCamera2Device.setSATUltraWideLDC(CameraSettings.isUltraWideLDCEnabled());
            }
        }
    }

    private void updateWatermarkTag() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setNewWatermark(true);
            if (Util.isGlobalVersion()) {
                this.mCamera2Device.setGlobalWatermark();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateWatermarkUI(WatermarkItem watermarkItem) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null && watermarkItem != null) {
            mainContentProtocol.updateWatermarkSample(watermarkItem);
        }
    }

    private void updateWhiteBalance() {
        setAWBMode(getManualValue(CameraSettings.KEY_WHITE_BALANCE, "1"));
    }

    private void updateZsl() {
        this.mCamera2Device.setEnableZsl(isZslPreferred());
    }

    public /* synthetic */ void H(boolean z) {
        this.mMainProtocol.setEvAdjustable(z);
    }

    public /* synthetic */ void a(float f, float f2, int i, int i2, int i3) {
        String str = TAG;
        Log.d(str, "onOptionClick: which = " + i3);
        CameraStatUtils.trackGoogleLensPickerValue(i3 == 0);
        if (i3 == 0) {
            DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_GOOGLE_LENS_OOBE, true).apply();
            DataRepository.dataItemGlobal().editor().putString(CameraSettings.KEY_LONG_PRESS_VIEWFINDER, getString(R.string.pref_camera_long_press_viewfinder_default)).apply();
            LensAgent.getInstance().onFocusChange(2, f / ((float) Util.sWindowWidth), f2 / ((float) Util.sWindowHeight));
        } else if (i3 == 1) {
            DataRepository.dataItemGlobal().editor().putString(CameraSettings.KEY_LONG_PRESS_VIEWFINDER, getString(R.string.pref_camera_long_press_viewfinder_lock_ae_af)).apply();
            DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_EN_FIRST_CHOICE_LOCK_AE_AF_TOAST, true).apply();
            onSingleTapUp(i, i2, true);
            if (this.m3ALockSupported) {
                lockAEAF();
            }
            this.mMainProtocol.performHapticFeedback(0);
        }
    }

    public /* synthetic */ void a(CameraHardwareFace[] cameraHardwareFaceArr) {
        if (cameraHardwareFaceArr.length > 0) {
            if (!this.mIsFaceConflict) {
                this.mIsFaceConflict = true;
                showOrHideChip(false);
            }
        } else if (this.mIsFaceConflict) {
            this.mIsFaceConflict = false;
            showOrHideChip(true);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void addSaveTask(String str, ContentValues contentValues) {
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        SaveVideoTask saveVideoTask = new SaveVideoTask(str, contentValues);
        synchronized (this) {
            this.mPendingSaveTaskList.add(saveVideoTask);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void applyZoomRatio() {
        super.applyZoomRatio();
        updateSpecshotMode();
    }

    public /* synthetic */ void b(String str, Bitmap bitmap) {
        try {
            String replace = str.replace("IMG_", "IMG_Preview_");
            String str2 = TAG;
            Log.i(str2, "showDocumentPreview mShootOrientation = " + this.mShootOrientation);
            if (this.mShootOrientation != 0) {
                bitmap = Util.adjustPhotoRotation(bitmap, this.mShootOrientation);
            }
            Util.saveToFile(bitmap, replace, Bitmap.CompressFormat.JPEG);
            MediaScannerConnection.scanFile(this.mActivity, new String[]{replace}, null, null);
        } catch (Exception e2) {
            e2.printStackTrace();
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
        if (2 != this.mTelephonyManager.getCallState()) {
            return true;
        }
        showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_calling_alert);
        return false;
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

    public void closeBacklightTip(int i, int i2) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        int i3 = this.mCurrentAiScene;
        if (i3 == -1 && i3 != i) {
            topAlert.alertAiSceneSelector(i2);
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
                if (this.mBurstDisposable != null) {
                    this.mBurstDisposable.dispose();
                }
                if (this.mMetaDataFlowableEmitter != null) {
                    this.mMetaDataFlowableEmitter.onComplete();
                }
                if (this.mMetaDataDisposable != null) {
                    this.mMetaDataDisposable.dispose();
                }
                if (this.mAiSceneFlowableEmitter != null) {
                    this.mAiSceneFlowableEmitter.onComplete();
                }
                if (this.mAiSceneDisposable != null) {
                    this.mAiSceneDisposable.dispose();
                }
                if (this.mSuperNightDisposable != null) {
                    this.mSuperNightDisposable.dispose();
                }
                this.mCamera2Device.setScreenLightCallback(null);
                this.mCamera2Device.setMetaDataCallback(null);
                this.mCamera2Device.setErrorCallback(null);
                this.mCamera2Device.setFocusCallback(null);
                this.mCamera2Device.setAiASDEnable(false);
                if (this.mCameraCapabilities.isSupportAIIE()) {
                    this.mCamera2Device.setAIIEPreviewEnable(false);
                }
                if (scanQRCodeEnabled() || c.Fj() || this.mIsGoogleLensAvailable || this.mSupportAnchorFrameAsThumbnail || CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
                    this.mCamera2Device.stopPreviewCallback(true);
                }
                if (this.mFaceDetectionStarted) {
                    this.mFaceDetectionStarted = false;
                }
                this.m3ALocked = false;
                this.mCamera2Device.setASDEnable(false);
                this.mCamera2Device.setColorEnhanceEnable(false);
                EffectController.getInstance().setAiSceneEffect(FilterInfo.FILTER_ID_NONE, false);
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
        if (scanQRCodeEnabled() || c.Fj() || this.mIsGoogleLensAvailable || CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
            PreviewDecodeManager.getInstance().quit();
        }
        if (this.mSupportAnchorFrameAsThumbnail) {
            this.mCacheImageDecoder.quit();
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            localBinder.setOnSessionStatusCallBackListener(null);
        }
        stopCpuBoost();
        Log.d(TAG, "closeCamera: X");
    }

    public void closeMoonMode(int i, int i2) {
        if (this.mEnteringMoonMode) {
            int i3 = this.mCurrentAiScene;
            if ((i3 == 10 || i3 == 35) && i != this.mCurrentAiScene) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.alertMoonModeSelector(i2, false);
                    if (i2 != 0) {
                        this.mEnteringMoonMode = false;
                    }
                    if (8 == i2) {
                        Log.d(TAG, "(moon_mode) close moon mode");
                    }
                    ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
                    if (!componentHdr.isEmpty() && !topAlert.isHDRShowing()) {
                        String componentValue = componentHdr.getComponentValue(this.mModuleIndex);
                        if ("on".equals(componentValue) || "normal".equals(componentValue)) {
                            topAlert.alertHDR(0, false, false);
                        }
                    }
                }
                updateMoon(false);
                if (this.mMutexModePicker.isSuperResolution() && !DataRepository.dataItemFeature().dk()) {
                    this.mCamera2Device.setSuperResolution(true);
                }
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
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
                case 4:
                    updateScene();
                    break;
                case 5:
                    updateFace();
                    break;
                case 6:
                    updateWhiteBalance();
                    break;
                case 7:
                    updateJpegQuality();
                    break;
                case 8:
                    updateJpegThumbnailSize();
                    break;
                case 9:
                    updateAntiBanding(CameraSettings.getAntiBanding());
                    break;
                case 10:
                    updateFlashPreference();
                    break;
                case 11:
                    updateHDRPreference();
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    updateEyeLight();
                    break;
                case 14:
                    updateFocusMode();
                    break;
                case 15:
                    updateISO();
                    break;
                case 16:
                    updateExposureTime();
                    break;
                case 17:
                case 18:
                case 31:
                case 32:
                case 33:
                case 41:
                case 51:
                case 53:
                case 54:
                case 64:
                case 65:
                case 67:
                case 68:
                case 69:
                case 71:
                case 72:
                case 75:
                case 80:
                case 81:
                case 83:
                case 85:
                case 86:
                case 87:
                default:
                    throw new RuntimeException("no consumer for this updateType: " + i);
                case 19:
                    updateFpsRange();
                    break;
                case 20:
                    updateOIS();
                    break;
                case 21:
                    updateMute();
                    break;
                case 22:
                    updateZsl();
                    break;
                case 23:
                    updateDecodePreview();
                    break;
                case 24:
                    applyZoomRatio();
                    break;
                case 25:
                    focusCenter();
                    break;
                case 26:
                    updateContrast();
                    break;
                case 27:
                    updateSaturation();
                    break;
                case 28:
                    updateSharpness();
                    break;
                case 29:
                    updateExposureMeteringMode();
                    break;
                case 30:
                    updateSuperResolution();
                    break;
                case 34:
                    updateMfnr(CameraSettings.isMfnrSatEnable());
                    break;
                case 35:
                    updateDeviceOrientation();
                    break;
                case 36:
                    updateAiScene();
                    break;
                case 37:
                    updateBokeh();
                    break;
                case 38:
                    updateFaceAgeAnalyze();
                    break;
                case 39:
                    updateFaceScore();
                    break;
                case 40:
                    updateFrontMirror();
                    break;
                case 42:
                    updateSwMfnr();
                    break;
                case 43:
                    updatePortraitLighting();
                    break;
                case 44:
                    updateShotDetermine();
                    break;
                case 45:
                    updateEyeLight();
                    break;
                case 46:
                    updateNormalWideLDC();
                    break;
                case 47:
                    updateUltraWideLDC();
                    break;
                case 48:
                    updateFNumber();
                    break;
                case 49:
                    updateLiveShot();
                    break;
                case 50:
                    break;
                case 52:
                    updateMacroMode();
                    break;
                case 55:
                    updateModuleRelated();
                    break;
                case 56:
                    updateSuperNight();
                    break;
                case 57:
                    updateUltraPixelPortrait();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                case 59:
                    updateOnTripMode();
                    break;
                case 60:
                    updateCinematicPhoto();
                    break;
                case 61:
                    updateASDDirtyDetect();
                    break;
                case 62:
                    updateWatermarkTag();
                    break;
                case 63:
                    updateEvValue();
                    break;
                case 66:
                    updateThermalLevel();
                    break;
                case 70:
                    updateASD();
                    break;
                case 73:
                    updateAIWatermark();
                    break;
                case 74:
                    updateColorEnhance();
                    break;
                case 76:
                    updateDoDepurple();
                    break;
                case 77:
                    updateNearRangeMode(true, !CameraSettings.isSettingNearRangeEnable());
                    break;
                case 78:
                    updateSpecshotMode();
                    break;
                case 79:
                    updateTargetZoom();
                    break;
                case 82:
                    updateAiShutter();
                    break;
                case 84:
                    updateHighQualityPreferred();
                    break;
                case 88:
                case 89:
                    findBestWatermarkItem(i);
                    break;
            }
        }
    }

    public void dealTimerBurst(long j) {
        if (CameraSettings.isTimerBurstEnable() && TimerBurstController.isSupportTimerBurst(this.mModuleIndex)) {
            TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
            final int totalCount = timerBurstController.getTotalCount();
            long intervalTimer = timerBurstController.getIntervalTimer();
            if (totalCount <= 1) {
                this.mHandler.post(new Runnable() {
                    /* class com.android.camera.module.Camera2Module.AnonymousClass13 */

                    public void run() {
                        Camera2Module.this.stopTimerBurst();
                        DataRepository.dataItemLive().getTimerBurstController().decreaseCount(totalCount);
                    }
                });
            } else if (timerBurstController.isInTimerBurstShotting()) {
                String str = TAG;
                Log.i(str, "dealTimerBurst: TimerTask" + intervalTimer + "   now:" + j);
                DataRepository.dataItemLive().getTimerBurstController().decreaseCount(totalCount);
                Handler handler = this.mHandler;
                handler.sendMessage(handler.obtainMessage(63));
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.MutexModeManager.MutexCallBack
    public void enterMutexMode(int i) {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy == null) {
            Log.d(TAG, "enterMutexMode error, mCamera2Device is null");
            return;
        }
        if (i == 1) {
            boolean z = false;
            if (DataRepository.dataItemFeature().El()) {
                String componentValue = DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex);
                if (getZoomRatio() > HybridZoomingSystem.getZoomRatioNone(isFrontCamera(), this.mOrientation) && "auto".equals(componentValue)) {
                    z = true;
                }
            }
            this.mCamera2Device.setHDR(new Camera2Proxy.HDRStatus(true, this.mIsNeedNightHDR, z));
        } else if (i == 3) {
            camera2Proxy.setHHT(true);
        } else if (i == 9) {
            camera2Proxy.setSuperResolution(true);
        }
        updateMfnr(CameraSettings.isMfnrSatEnable());
        updateSwMfnr();
    }

    @Override // com.android.camera.module.BaseModule
    public void executeSaveTask(boolean z) {
        synchronized (this) {
            do {
                if (this.mPendingSaveTaskList.isEmpty()) {
                    break;
                }
                SaveVideoTask remove = this.mPendingSaveTaskList.remove(0);
                String str = TAG;
                Log.d(str, "executeSaveTask: " + remove.videoPath);
                this.mActivity.getImageSaver().addVideo(remove.videoPath, remove.contentValues, true);
            } while (!z);
            doLaterReleaseIfNeed();
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.MutexModeManager.MutexCallBack
    public void exitMutexMode(int i) {
        if (i == 1) {
            this.mCamera2Device.setHDR(new Camera2Proxy.HDRStatus(false, false, false));
            updateSuperResolution();
        } else if (i == 3) {
            this.mCamera2Device.setHHT(false);
        } else if (i == 9) {
            this.mCamera2Device.setSuperResolution(false);
        }
        updateMfnr(CameraSettings.isMfnrSatEnable());
        updateSwMfnr();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void focusCenter() {
    }

    public /* synthetic */ void gd() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiDetectTipHint(8, R.string.super_night_hint, -1);
            this.mShowLLSHint = false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public boolean getAutoHDRTargetState() {
        return this.mAutoHDRTargetState;
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

    public CircularMediaRecorder getCircularMediaRecorder() {
        CircularMediaRecorder circularMediaRecorder;
        synchronized (this.mCircularMediaRecorderStateLock) {
            circularMediaRecorder = this.mCircularMediaRecorder;
        }
        return circularMediaRecorder;
    }

    public int getCurrentAiScene() {
        return this.mCurrentAiScene;
    }

    @Override // com.android.camera.module.BaseModule
    public String getDebugInfo() {
        CameraConfigs cameraConfigs;
        MeteringRectangle[] aFRegions;
        String str;
        CameraCharacteristics cameraCharacteristics;
        StringBuilder sb = new StringBuilder();
        int moduleIndex = getModuleIndex();
        CameraCapabilities cameraCapabilities = getCameraCapabilities();
        if (!(cameraCapabilities == null || (cameraCharacteristics = cameraCapabilities.getCameraCharacteristics()) == null)) {
            float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
            float[] fArr2 = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
            if (fArr != null && fArr.length > 0) {
                sb.append("LensFocal:" + fArr[0] + " ");
            }
            if (fArr2 != null && fArr2.length > 0) {
                sb.append("LensApertues:" + fArr2[0] + " ");
            }
        }
        if (moduleIndex == 167) {
            sb.append("SceneProfession:true");
        }
        sb.append("ZoomMultiple:" + getZoomRatio() + " ");
        Camera2Proxy cameraDevice = getCameraDevice();
        if (!(cameraDevice == null || (cameraConfigs = cameraDevice.getCameraConfigs()) == null || (aFRegions = cameraConfigs.getAFRegions()) == null || aFRegions.length <= 0)) {
            MeteringRectangle meteringRectangle = aFRegions[0];
            if (meteringRectangle == null) {
                str = "0";
            } else {
                int x = meteringRectangle.getX();
                int y = meteringRectangle.getY();
                str = "[" + x + "," + y + "," + (meteringRectangle.getWidth() + x) + "," + (meteringRectangle.getHeight() + y) + "]";
            }
            sb.append("afRoi:" + str + " ");
        }
        String retriveFaceInfo = DebugInfoUtil.getRetriveFaceInfo(this.mMainProtocol.getViewRects(this.mPictureSize));
        if (!TextUtils.isEmpty(retriveFaceInfo)) {
            sb.append("FaceRoi:" + retriveFaceInfo + " ");
        }
        sb.append("FilterId:" + CameraSettings.getShaderEffect() + " ");
        sb.append("AIScene:" + getCurrentAiScene() + " ");
        return sb.toString();
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public int getFilterId() {
        return this.mNormalFilterId;
    }

    /* access modifiers changed from: protected */
    public int getMaxPictureSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getMutexHdrMode(String str) {
        if ("normal".equals(str)) {
            return 1;
        }
        return (!c.Gn() || !"live".equals(str)) ? 0 : 2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01cf, code lost:
        if (r13.mCameraCapabilities.isSupportLightTripartite() != false) goto L_0x01d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01db, code lost:
        if (b.c.a.c._g != false) goto L_0x01d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x021f, code lost:
        if (b.c.a.c._g == false) goto L_0x01de;
     */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        int i = 32778;
        if (isParallelSessionEnable()) {
            if (isInQCFAMode()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_QCFA");
                i = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_QCFA;
            } else if (167 == getModuleIndex()) {
                if (CameraSettings.isUltraPixelOn()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL");
                    i = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL;
                } else {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL");
                    i = c.Um() ? CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL_G7 : CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL;
                }
            } else if (171 != getModuleIndex()) {
                if (182 == getModuleIndex()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
                } else if (DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode() || DataRepository.dataItemGlobal().isOnSuperNightHalfAlgoUp()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_SUPER_NIGHT");
                } else if (CameraSettings.isUltraPixelOn()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_HD");
                    i = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_HD;
                } else if (isUltraWideBackCamera()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
                } else if (!CameraSettings.isSupportedOpticalZoom() || (this.mIsImageCaptureIntent && this.mCameraCapabilities.isSupportLightTripartite())) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
                } else if (isFrontCamera()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
                } else if (DataRepository.dataItemRunning().getComponentRunningMacroMode().isSwitchOn(getModuleIndex())) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
                } else {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SAT");
                    i = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SAT;
                }
                i = 36869;
            } else if (!isFrontCamera() || isDualFrontCamera()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_DUAL_BOKEH");
                i = 36864;
            } else {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH");
                i = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH;
            }
            this.mOperatingMode = i;
            return i;
        }
        int i2 = 32770;
        if (isFrontCamera()) {
            mIsBeautyFrontOn = true;
            int i3 = 32775;
            if (!isPortraitMode() || !DataRepository.dataItemFeature().Wl()) {
                if (!isPortraitMode() || !isBokehFrontCamera()) {
                    i2 = (!this.mCameraCapabilities.isSupportedQcfa() || mIsBeautyFrontOn || !"off".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex)) || DataRepository.dataItemFeature().Eh() >= 0) ? 32773 : 32775;
                }
            } else if (!isBokehFrontCamera()) {
                i2 = 33009;
            }
            if (this.mModuleIndex != 163 || !CameraSettings.isUltraPixelOn()) {
                i3 = i2;
            }
            if (!DataRepository.dataItemFeature().yl() || this.mModuleIndex != 173) {
                i = i3;
            }
        } else {
            ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
            int moduleIndex = getModuleIndex();
            if (moduleIndex != 163) {
                if (moduleIndex == 167) {
                    i = CameraSettings.isUltraPixelOn() ? CameraCapabilities.SESSION_OPERATION_MODE_PROFESSIONAL_ULTRA_PIXEL_PHOTOGRAPHY : 32771;
                } else if (moduleIndex == 171) {
                    i = 32770;
                } else if (moduleIndex != 173) {
                    if (moduleIndex != 175) {
                        if (!(moduleIndex == 182 || moduleIndex == 186 || moduleIndex == 205)) {
                            if (this.mIsImageCaptureIntent) {
                            }
                            if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                            }
                            i = 32769;
                        }
                    }
                    i = 33011;
                }
            }
            if (!this.mIsImageCaptureIntent || !this.mCameraCapabilities.isSupportLightTripartite()) {
                if (!CameraSettings.isUltraPixelOn()) {
                    if (CameraSettings.isDualCameraSatEnable()) {
                        if (!DataRepository.dataItemFeature().vi()) {
                            if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                            }
                            i = 32769;
                        }
                    }
                }
                i = 33011;
            }
            i = 0;
        }
        this.mOperatingMode = i;
        Log.d(TAG, "getOperatingMode: " + String.format("operatingMode = 0x%x", Integer.valueOf(i)));
        return i;
    }

    public /* synthetic */ void hd() {
        showOrHideLoadingProgress(false, false);
    }

    @Override // com.android.camera.module.BaseModule
    public void initializeCapabilities() {
        super.initializeCapabilities();
        this.mContinuousFocusSupported = Util.isSupported(4, this.mCameraCapabilities.getSupportedFocusModes());
        this.mMaxFaceCount = this.mCameraCapabilities.getMaxFaceCount();
    }

    @Override // com.android.camera2.Camera2Proxy.ASDSceneCallback
    public boolean isAutoFlashOff() {
        return CameraSettings.isSuperNightOn();
    }

    /* access modifiers changed from: protected */
    public boolean isAutoRestartInNonZSL() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.BeautyBodySlimCountCallback
    public boolean isBeautyBodySlimCountDetectStarted() {
        return this.mIsBeautyBodySlimOn;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003d, code lost:
        if (r0.isParallelBusy(false) != false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005c, code lost:
        if (r0.isAnyRequestBlocked() != false) goto L_0x003f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008d  */
    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isBlockSnap() {
        boolean z;
        Decoder decoder;
        LocalParallelService.LocalBinder localBinder;
        if (!DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode() || DataRepository.dataItemFeature().hm() || (localBinder = AlgoConnector.getInstance().getLocalBinder()) == null || localBinder.isIdle()) {
            if (isParallelCameraSessionMode()) {
                Camera2Proxy camera2Proxy = this.mCamera2Device;
                if (camera2Proxy != null) {
                }
                z = false;
                if (!CameraSettings.isDocumentMode2On(this.mModuleIndex) && ((decoder = PreviewDecodeManager.getInstance().getDecoder(3)) == null || ((DocumentDecoder) decoder).getCachePreview() == null)) {
                    Log.d(TAG, "isBlockSnap: document cache preview is null...");
                    return true;
                } else if (this.mPaused) {
                    Log.d(TAG, "isBlockSnap: paused");
                    return true;
                } else if (this.isZooming) {
                    Log.d(TAG, "isBlockSnap: zooming");
                    return true;
                } else if (isKeptBitmapTexture()) {
                    Log.d(TAG, "isBlockSnap: isKeptBitmapTexture");
                    return true;
                } else if (this.mMultiSnapStatus) {
                    Log.d(TAG, "isBlockSnap: multiSnap");
                    return true;
                } else if (getCameraState() == 0) {
                    Log.d(TAG, "isBlockSnap: getCameraState() = CameraStateConstant.PREVIEW_STOPPED");
                    return true;
                } else if (z) {
                    Log.d(TAG, "isBlockSnap: snapshot is in progress");
                    return true;
                } else {
                    Camera2Proxy camera2Proxy2 = this.mCamera2Device;
                    if (camera2Proxy2 != null) {
                        if (camera2Proxy2.isCaptureBusy(this.mMutexModePicker.isHdr() && !isParallelCameraSessionMode())) {
                            Log.d(TAG, "isBlockSnap: mCamera2Device's boolean is true");
                            return true;
                        }
                    }
                    if (isQueueFull()) {
                        Log.d(TAG, "isBlockSnap: queue is full");
                        return true;
                    } else if (isInCountDown()) {
                        Log.d(TAG, "isBlockSnap: counting down");
                        return true;
                    } else if (this.mWaitSaveFinish) {
                        Log.d(TAG, "isBlockSnap: waiting save finish");
                        return true;
                    } else if (!isParallelSessionConfigured()) {
                        Log.d(TAG, "isBlockSnap: parallel session hasn't been configured");
                        return true;
                    } else if (!this.mHandler.hasMessages(62)) {
                        return false;
                    } else {
                        Log.d(TAG, "isBlockSnap: has message MSG_RESUME_CAPTURE");
                        return true;
                    }
                }
            } else {
                z = getCameraState() == 3;
                if (!z) {
                    LocalParallelService.LocalBinder localBinder2 = AlgoConnector.getInstance().getLocalBinder();
                    if (localBinder2 != null) {
                    }
                    z = false;
                }
                if (!CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
                }
                if (this.mPaused) {
                }
            }
            z = true;
            if (!CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
            }
            if (this.mPaused) {
            }
        } else {
            Log.i(TAG, "isBlockSnap: shooting super night and discard snap");
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        return (!CameraSettings.isSupportedOpticalZoom() || CameraSettings.isFakePartSAT()) ? HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !ModuleManager.isProModule() && isBackCamera() : super.isCameraSwitchingDuringZoomingAllowed();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isCaptureIntent() {
        return this.mIsImageCaptureIntent;
    }

    /* access modifiers changed from: protected */
    public boolean isDetectedHHT() {
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
        if (r0.isAnyRequestBlocked() != false) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0062, code lost:
        if (r0.isCaptureBusy(true) != false) goto L_0x0056;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0069  */
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
                Handler handler = this.mHandler;
                return (handler == null && handler.hasMessages(4097)) || this.mPaused || this.isZooming || this.mMediaRecorderRecording || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || z || isQueueFull() || this.mWaitSaveFinish || isInCountDown();
            }
            Camera2Proxy camera2Proxy2 = this.mCamera2Device;
            if (camera2Proxy2 != null) {
            }
            z = false;
            Handler handler2 = this.mHandler;
            if (handler2 == null) {
            }
            z = true;
            Handler handler22 = this.mHandler;
            if (handler22 == null) {
            }
        }
        Log.i(TAG, "[ALGOUP|MMCAMERA] Doing action");
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isFaceBeautyMode() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted && !this.mMultiSnapStatus;
    }

    public boolean isGoogleLensAvailable() {
        return this.mIsGoogleLensAvailable;
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public boolean isGyroStable() {
        return Util.isGyroscopeStable(this.curGyroscope, this.lastGyroscope);
    }

    @Override // com.android.camera2.Camera2Proxy.HdrCheckerCallback
    public boolean isHdrSceneDetectionStarted() {
        return this.mHdrCheckEnabled;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isKeptBitmapTexture() {
        return this.mKeepBitmapTexture;
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public boolean isLivePhotoStarted() {
        return this.mLiveShotEnabled;
    }

    @Override // com.android.camera2.Camera2Proxy.MagneticDetectedCallback
    public boolean isLockHDRChecker(String str) {
        ModeProtocol.MagneticSensorDetect magneticSensorDetect = this.mMagneticSensorDetect;
        if (magneticSensorDetect != null) {
            return magneticSensorDetect.isLockHDRChecker(str);
        }
        return false;
    }

    public boolean isLongPressedRecording() {
        return this.mMediaRecorderRecording;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isMeteringAreaOnly() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy == null) {
            return false;
        }
        int focusMode = camera2Proxy.getFocusMode();
        return (!this.mFocusAreaSupported && this.mMeteringAreaSupported && !this.mFocusOrAELockSupported) || 5 == focusMode || focusMode == 0;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isNeedMute() {
        return CameraSettings.isLiveShotOn();
    }

    public boolean isNeedNearRangeTip() {
        return !this.mMediaRecorderRecording && !this.mIsShutterLongClickRecording && !DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isParallelSessionEnable() {
        if (CameraSettings.isUltraPixelRawOn() || !CameraSettings.isCameraParallelProcessEnable()) {
            return false;
        }
        if (getModuleIndex() == 173 && DataRepository.dataItemGlobal().isOnSuperNightHalfAlgoUp()) {
            return true;
        }
        if (getModuleIndex() == 173 && !DataRepository.dataItemGlobal().isOnSuperNightAlgoUpMode()) {
            return false;
        }
        boolean isSwitchOn = DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(getModuleIndex());
        if (getModuleIndex() == 167 && (!DataRepository.dataItemFeature().Ni() || isSwitchOn)) {
            return false;
        }
        if (getModuleIndex() == 175 && DataRepository.dataItemFeature().Mi()) {
            return false;
        }
        if (isStandaloneMacroCamera() && !DataRepository.dataItemFeature().Ui()) {
            return false;
        }
        if (!isUltraWideBackCamera() || DataRepository.dataItemFeature().Hk()) {
            return (!this.mIsImageCaptureIntent || DataRepository.dataItemFeature().mi()) && !DataRepository.dataItemGlobal().isForceMainBackCamera();
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        return this.mMediaRecorderRecording || DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isRepeatingRequestInProgress() {
        return this.mMultiSnapStatus && 3 == getCameraState();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isSelectingCapturedResult() {
        ModeProtocol.BaseDelegate baseDelegate;
        return this.mIsImageCaptureIntent && (baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 4083;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isShot2GalleryOrEnableParallel() {
        return this.mEnableShot2Gallery || this.mEnableParallelSession || this.mSupportAnchorFrameAsThumbnail;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowAeAfLockIndicator() {
        return this.m3ALocked;
    }

    public boolean isShowBacklightTip() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            return topAlert.isShowBacklightSelector();
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return !this.mMutexModePicker.isBurstShoot() && isSupportFocusShoot();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial, com.android.camera.module.BaseModule
    public boolean isStartCountCapture() {
        return this.mIsStartCount;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key") && !this.mMediaRecorderRecording;
    }

    /* access modifiers changed from: protected */
    public boolean isSupportSceneMode() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.SuperNightCallback
    public boolean isSupportSuperNight() {
        if (!DataRepository.dataItemFeature().nk() || (c.mn() && !Util.sSuperNightDefaultModeEnable)) {
            return false;
        }
        if (163 == getModuleIndex() || 165 == getModuleIndex() || 186 == getModuleIndex() || 182 == getModuleIndex() || 205 == getModuleIndex()) {
            return (isBackCamera() || DataRepository.dataItemFeature().yl()) && 1.0f == CameraSettings.readZoom() && !this.mIsMacroModeEnable && !isRepeatingRequestInProgress() && !this.mIsNearRangeModeUITip && isSuperNightSeEnable();
        }
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        this.mUnInterruptableReason = null;
        if (isKeptBitmapTexture()) {
            this.mUnInterruptableReason = "bitmap cover";
        } else if (getCameraState() == 3) {
            this.mUnInterruptableReason = "snapshot";
        }
        return this.mUnInterruptableReason != null;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isUseFaceInfo() {
        return this.mIsGenderAgeOn || this.mIsMagicMirrorOn;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        Camera2Proxy camera2Proxy;
        boolean z = CameraSettings.isTimerBurstEnable() && TimerBurstController.isSupportTimerBurst(this.mModuleIndex) && DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting();
        if (getCameraState() == 3 || CameraSettings.isPortraitModeBackOn() || isFrontCamera() || z || (camera2Proxy = this.mCamera2Device) == null || camera2Proxy.isCaptureBusy(true)) {
            return false;
        }
        return (!CameraSettings.isUltraPixelOn() || DataRepository.dataItemFeature().bk()) && this.mModuleIndex != 182 && isFrameAvailable() && !this.mMediaRecorderRecording;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isZslPreferred() {
        if (c.isMTKPlatform()) {
            int i = this.mModuleIndex;
            if (i == 163 || i == 165 || i == 186 || i == 182 || i == 171) {
                return true;
            }
            if (i == 175 && DataRepository.dataItemFeature().Vj()) {
                return true;
            }
        } else if (this.mModuleIndex != 167) {
            return true;
        }
        return false;
    }

    public /* synthetic */ void kd() {
        showOrHideLoadingProgress(false, false);
    }

    public /* synthetic */ void ld() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiDetectTipHint(8, R.string.super_night_hint, -1);
        }
        this.mShowSuperNightHint = false;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean multiCapture() {
        if (isDoingAction() || !this.mPendingMultiCapture) {
            return false;
        }
        this.mPendingMultiCapture = false;
        this.mActivity.getScreenHint().updateHint();
        if (Storage.isLowStorageAtLastPoint()) {
            String str = TAG;
            Log.i(str, "Not enough space or storage not ready. remaining=" + Storage.getLeftSpace());
            return false;
        } else if (this.mActivity.getImageSaver().isBusy()) {
            Log.d(TAG, "ImageSaver is busy, wait for a moment!");
            RotateTextToast.getInstance(this.mActivity).show(R.string.toast_saving, 0);
            return false;
        } else if (this.mIsMoonMode) {
            return false;
        } else {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT, 193, 194, 196, 239, 201, 206);
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromLongPressShutter();
            }
            prepareMultiCapture();
            Observable.create(new ObservableOnSubscribe<Integer>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass2 */

                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                    Camera2Module.this.mBurstEmitter = observableEmitter;
                }
            }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Integer, Integer>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass4 */

                public Integer apply(Integer num) throws Exception {
                    ModeProtocol.SnapShotIndicator snapShotIndicator = (ModeProtocol.SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184);
                    if (snapShotIndicator != null) {
                        if (snapShotIndicator instanceof FragmentTopConfig) {
                            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                            if (topAlert != null) {
                                topAlert.hideAlert();
                            }
                            Camera2Module.this.mMainProtocol.clearFocusView(7);
                        }
                        snapShotIndicator.setSnapNumVisible(true, false);
                        snapShotIndicator.setSnapNumValue(num.intValue());
                    }
                    return num;
                }
            }).subscribe(new Observer<Integer>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass3 */

                @Override // io.reactivex.Observer
                public void onComplete() {
                    ModeProtocol.TopAlert topAlert;
                    ModeProtocol.SnapShotIndicator snapShotIndicator = (ModeProtocol.SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184);
                    if (snapShotIndicator != null) {
                        snapShotIndicator.setSnapNumVisible(false, true);
                        if ((snapShotIndicator instanceof FragmentTopConfig) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                            AndroidSchedulers.mainThread().scheduleDirect(new RunnableC0075g(topAlert), 500, TimeUnit.MILLISECONDS);
                        }
                    }
                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                    if (configChanges != null) {
                        configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);
                    }
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                public void onNext(Integer num) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    Camera2Module.this.mBurstStartTime = System.currentTimeMillis();
                    Camera2Module.this.mBurstDisposable = disposable;
                }
            });
            this.mBurstNextDelayTime = 0;
            if (isParallelSessionEnable()) {
                this.mCamera2Device.setShotType(9);
                this.mCamera2Device.captureBurstPictures(this.mTotalJpegCallbackNum, new JpegRepeatingCaptureCallback(this), this.mActivity.getImageSaver());
            } else {
                ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sShotToGalleryTimeScenario, String.valueOf(this.mCaptureStartTime));
                ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sShotToViewTimeScenario, String.valueOf(this.mCaptureStartTime));
                this.mCamera2Device.setShotType(3);
                this.mCamera2Device.setAWBLock(true);
                this.mCamera2Device.captureBurstPictures(this.mTotalJpegCallbackNum, new JpegQuickPictureCallback(LocationManager.instance().getCurrentLocation()), this.mActivity.getImageSaver());
            }
            return true;
        }
    }

    public /* synthetic */ void nd() {
        this.mIsFaceConflict = false;
        this.mIsAiConflict = false;
        this.mIsUltraWideConflict = false;
        showOrHideChip(true);
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    public /* synthetic */ void od() {
        consumeAiSceneResult(0, true);
        this.isResetFromMutex = true;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return false;
        }
        this.mIsStartCount = false;
        tryRemoveCountDownMessage();
        if (this.mMultiSnapStatus) {
            onShutterButtonLongClickCancel(false);
            return true;
        } else if (getCameraState() != 3 || System.currentTimeMillis() - this.mLastCaptureTime >= CAPTURE_DURATION_THRESHOLD) {
            return super.onBackPressed();
        } else {
            return true;
        }
    }

    @Override // com.android.camera2.Camera2Proxy.BeautyBodySlimCountCallback
    public void onBeautyBodySlimCountChange(final boolean z) {
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass8 */

            public void run() {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    if (z) {
                        topAlert.alertAiDetectTipHint(0, R.string.beauty_body_slim_count_tip, FunctionParseBeautyBodySlimCount.TIP_TIME);
                    } else {
                        topAlert.alertAiDetectTipHint(8, R.string.beauty_body_slim_count_tip, 0);
                    }
                }
            }
        });
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
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
        int i;
        super.onCameraOpened();
        int i2 = 0;
        if (!isBackCamera() || !((i = this.mModuleIndex) == 163 || i == 165 || i == 205)) {
            EffectController.getInstance().setAiColorCorrectionVersion(0);
        } else {
            EffectController instance = EffectController.getInstance();
            CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
            if (cameraCapabilities != null) {
                i2 = cameraCapabilities.getAiColorCorrectionVersion();
            }
            instance.setAiColorCorrectionVersion(i2);
        }
        initializeFocusManager();
        initZoomMapControllerIfNeeded();
        updatePreferenceTrampoline(UpdateConstant.CAMERA_TYPES_INIT);
        if (this.mEnableParallelSession && isPortraitMode()) {
            Util.saveCameraCalibrationToFile(this.mCameraCapabilities.getCameraCalibrationData(), getCalibrationDataFileName(this.mActualCameraId));
        }
        if (this.mCameraCapabilities.isSatFusionShotSupported() && this.mCameraCapabilities.getCameraId() == Camera2DataContainer.getInstance().getSATCameraId()) {
            Util.saveCameraCalibrationToFile(this.mCameraCapabilities.getSatFusionCalibrationDataArray());
        }
        if (!isKeptBitmapTexture()) {
            startPreview();
        }
        initMetaParser();
        if (DataRepository.dataItemFeature().dj()) {
            initAiSceneParser();
        }
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
        ModeProtocol.RecordState recordState;
        Camera2Proxy camera2Proxy;
        checkMoreFrameCaptureLockAFAE(false);
        handleLLSResultInCaptureMode();
        if (this.mCameraCapabilities.isSupportAIIE() && this.mAiSceneEnabled && (camera2Proxy = this.mCamera2Device) != null && !camera2Proxy.getCameraConfigs().isAIIEPreviewEnabled()) {
            this.mCamera2Device.setAIIEPreviewEnable(true);
            resumePreviewInWorkThread();
        }
        if (DataRepository.dataItemConfig().getmComponentManuallyET().isLongExpose(this.mModuleIndex) && !DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting() && (recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)) != null) {
            Handler handler = this.mHandler;
            Objects.requireNonNull(recordState);
            handler.post(new ma(recordState));
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureProgress(boolean z, boolean z2, boolean z3, boolean z4) {
        if (!isDeviceAlive()) {
            Log.d(TAG, "onCaptureProgress but departed");
            return;
        }
        String str = TAG;
        Log.d(str, "onCaptureProgressed quick " + z + " anchor " + z2 + " doanchor " + z3 + " anchorpixel " + z4);
        onShutter(z, z2, z3, z4);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z, boolean z2, boolean z3, boolean z4) {
        String str = TAG;
        Log.d(str, "onCaptureShutter: cameraState = " + getCameraState() + ", isParallel = " + this.mEnableParallelSession);
        onShutter(z, z2, z3, z4);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, @NonNull CameraSize cameraSize, boolean z, boolean z2, boolean z3, boolean z4) {
        Camera camera;
        ImageSaver imageSaver;
        CameraSize cameraSize2;
        int i;
        List<WaterMarkData> faceWaterMarkInfos;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.updateFlashStateTimeLock();
        }
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
        boolean z5 = CameraSettings.isLiveShotOn() && isLiveShotAvailable(parallelType);
        if (z5) {
            startLiveShotAnimation();
        }
        Log.d(TAG, "onCapturestart quickshotanimation " + z + " anchorFrame " + z2 + " doAnchor " + z3 + " doAnchorPixel " + z4);
        if (!z || (CameraSettings.isGroupShotOn() && !this.mEnableParallelSession)) {
            if (!CameraSettings.isSupportedZslShutter()) {
                updateEnablePreviewThumbnail(z);
                if (this.mEnabledPreviewThumbnail) {
                    CameraSettings.setPlayToneOnCaptureStart(false);
                }
            }
            if (CameraSettings.isUltraPixelOn() && this.mEnabledPreviewThumbnail) {
                CameraSettings.setPlayToneOnCaptureStart(false);
            } else if (!this.mEnabledPreviewThumbnail) {
                onShutter(z, z2, z3, z4);
                CameraSettings.setPlayToneOnCaptureStart(true);
            }
            if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && this.mBlockQuickShot && !CameraSettings.isGroupShotOn() && this.mFixedShot2ShotTime == 0) {
                resetStatusToIdle();
            }
        }
        String str = null;
        ArrayList arrayList = (!CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() || (faceWaterMarkInfos = ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).getFaceWaterMarkInfos()) == null || faceWaterMarkInfos.isEmpty()) ? null : new ArrayList(faceWaterMarkInfos);
        if ((isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) && (!cameraSize.equals(this.mPictureSize) || c.isMTKPlatform())) {
            this.mPictureSize = cameraSize;
            updateOutputSize(cameraSize);
        }
        CameraSize cameraSize3 = this.mOutputPictureSize;
        Size sizeObject = cameraSize3 == null ? cameraSize.toSizeObject() : cameraSize3.toSizeObject();
        Log.d(TAG, "onCaptureStart: outputSize = " + sizeObject);
        int pictureFormatSuitableForShot = getPictureFormatSuitableForShot(parallelType);
        boolean isHeicImageFormat = CompatibilityUtils.isHeicImageFormat(pictureFormatSuitableForShot);
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onCaptureStart: outputFormat = ");
        sb.append(isHeicImageFormat ? "HEIC" : "JPEG");
        Log.d(str2, sb.toString());
        if (isHeicImageFormat && this.mCameraCapabilities.isSupportZeroDegreeOrientationImage() && ((i = this.mJpegRotation) == 90 || i == 270)) {
            Size size = new Size(sizeObject.getHeight(), sizeObject.getWidth());
            Log.d(TAG, "onCaptureStart: switched outputSize: " + size);
            sizeObject = size;
        }
        int clampQuality = clampQuality(CameraSettings.getEncodingQuality(false).toInteger(isHeicImageFormat));
        Log.d(TAG, "onCaptureStart: outputQuality = " + clampQuality);
        CameraCharacteristics cameraCharacteristics = this.mCameraCapabilities.getCameraCharacteristics();
        this.mFocalLengths = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        this.mApertures = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
        ParallelTaskDataParameter.Builder builder = new ParallelTaskDataParameter.Builder(this.mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject, pictureFormatSuitableForShot);
        if (parallelType == 1 && (cameraSize2 = this.mSensorRawImageSize) != null && this.mConfigRawStream && this.mModuleIndex == 167) {
            builder.setRawSize(cameraSize2.width, cameraSize2.height);
        }
        boolean z6 = DataRepository.dataItemFeature().Sl() && (Util.isStringValueContained("device", this.mCamera2Device.getCameraConfigs().getWaterMarkAppliedList()) || Util.isStringValueContained(WatermarkConstant.ITEM_TAG, this.mCamera2Device.getCameraConfigs().getWaterMarkAppliedList()));
        Location location = this.mActivity.getCameraIntentManager().checkIntentLocationPermission(this.mActivity) ? this.mLocation : null;
        ParallelTaskDataParameter.Builder filterId = builder.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen() && !isDisableWatermark()).setVendorWaterMark(z6).setMirror(isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false));
        int i2 = this.mOrientation;
        if (-1 == i2) {
            i2 = 0;
        }
        ParallelTaskDataParameter.Builder location2 = filterId.setOrientation(i2).setJpegRotation(this.mJpegRotation).setShootRotation(this.mShootRotation).setShootOrientation(this.mShootOrientation).setSupportZeroDegreeOrientationImage(this.mCameraCapabilities.isSupportZeroDegreeOrientationImage()).setLocation(location);
        if (CameraSettings.isTimeWaterMarkOpen() && !isDisableWatermark()) {
            str = Util.getTimeWatermark();
        }
        parallelTaskData.fillParameter(location2.setTimeWaterMarkString(str).setFaceWaterMarkList(arrayList).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(isFrontCamera()).setBokehFrontCamera(isPictureUseDualFrontCamera()).setAlgorithmName(this.mAlgorithmName).setPictureInfo(getPictureInfo()).setSuffix(getSuffix()).setTiltShiftMode(getTiltShiftMode()).setSaveGroupshotPrimitive(CameraSettings.isSaveGroushotPrimitiveOn()).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setJpegQuality(clampQuality).setPrefix(getPrefix()).setMoonMode(this.mIsMoonMode).setMiMovieOpen(CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex)).setMajorAIWatermark(this.mWatermarkItem).setIsSupportMiDualBokeh(this.mCameraCapabilities.isSupportMiDualBokeh()).setPortraitLightingVersion(this.mCameraCapabilities.getPortraitLightingVersion()).setDocumentShot(CameraSettings.isDocumentMode2On(this.mModuleIndex)).setSensorOrientation(this.mCameraCapabilities.getSensorOrientation()).setCameraPreferredMode((CameraSettings.isHighQualityPreferred() ? CameraPreferredMode.HIGH_QUALITY_MODE : CameraPreferredMode.PERFORMANCE_MODE).ordinal()).build());
        parallelTaskData.setNeedThumbnail(!z && !this.mEnabledPreviewThumbnail);
        parallelTaskData.setCurrentModuleIndex(this.mModuleIndex);
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        parallelTaskData.setAdaptiveSnapshotSize(cameraCapabilities != null && cameraCapabilities.isAdaptiveSnapshotSizeInSatModeSupported());
        parallelTaskData.setLiveShotTask(false);
        if (!(!z5 || (camera = this.mActivity) == null || (imageSaver = camera.getImageSaver()) == null)) {
            synchronized (this.mCircularMediaRecorderStateLock) {
                if (this.mCircularMediaRecorder != null) {
                    parallelTaskData.setLiveShotTask(true);
                    this.mCircularMediaRecorder.snapshot(this.mOrientationCompensation, imageSaver, parallelTaskData, this.mNormalFilterId);
                }
            }
        }
        if (DataRepository.dataItemFeature().Ol()) {
            parallelTaskData.setRequireTuningData(true);
        }
        Log.d(TAG, "onCaptureStart: isParallel = " + this.mEnableParallelSession + ", shotType = " + parallelTaskData.getParallelType() + ", isLiveShot = " + z5);
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

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        parseIntent();
        this.mTelephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        this.mHandler = new MainHandler(this, this.mActivity.getMainLooper());
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        boolean z = false;
        this.mIsGoogleLensAvailable = 163 == getModuleIndex() && !this.mIsImageCaptureIntent && isBackCamera() && CameraSettings.isAvailableGoogleLens();
        this.mSupportAnchorFrameAsThumbnail = supportAnchorFrameAsThumbnail();
        if (DataRepository.dataItemFeature().ek() && !CameraSettings.isCameraParallelProcessEnable()) {
            z = true;
        }
        this.mSupportShotBoost = z;
        onCameraOpened();
        this.mFirstCreateCapture = true;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
        ZoomMapController zoomMapController = this.mZoomMapController;
        if (zoomMapController != null) {
            zoomMapController.onModuleDestroy();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void onExtraMenuVisibilityChange(boolean z) {
        if (!z) {
            this.mCurrentAiScene = 0;
            setCurrentAsdScene(-1);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo, Rect rect) {
        FocusManager2 focusManager2;
        Camera camera;
        int i;
        if (isAlive() && this.mActivity.getCameraScreenNail().getFrameAvailableFlag() && cameraHardwareFaceArr != null) {
            if (c.Xm()) {
                boolean z = cameraHardwareFaceArr.length > 0;
                if (z != this.mFaceDetected && isFrontCamera() && ((i = this.mModuleIndex) == 163 || i == 165 || i == 171)) {
                    this.mCamera2Device.resumePreview();
                }
                this.mFaceDetected = z;
            }
            this.mFaceInfo = faceAnalyzeInfo;
            if (!c.Qn() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
                if (this.mIsGoogleLensAvailable && (camera = this.mActivity) != null) {
                    camera.runOnUiThread(new RunnableC0098w(this, cameraHardwareFaceArr));
                }
                if (this.mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), rect)) {
                    if (this.mIsPortraitLightingOn) {
                        this.mMainProtocol.lightingDetectFace(cameraHardwareFaceArr, false);
                    }
                    if (!this.mMainProtocol.isFaceExists(1) || !this.mMainProtocol.isFocusViewVisible() || (focusManager2 = this.mFocusManager) == null || focusManager2.isFromTouch()) {
                        this.mHandler.removeMessages(56);
                    } else if (!this.mHandler.hasMessages(56)) {
                        this.mHandler.sendEmptyMessage(56);
                    }
                }
            } else if (this.mObjectTrackingStarted) {
                this.mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), rect);
            }
        }
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
        if (!isFrameAvailable()) {
            Log.d(TAG, "onFocusStateChanged: first frame has not arrived");
        } else if (!isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger != 1) {
                if (focusTrigger == 2) {
                    ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
                    boolean z = mainContentProtocol != null && mainContentProtocol.isFaceExists(1);
                    if (focusTask.isIsDepthFocus() && (!this.mFirstCreateCapture || !z)) {
                        return;
                    }
                } else if (focusTrigger != 3) {
                    return;
                }
                String str = null;
                if (focusTask.isFocusing()) {
                    this.mAFEndLogTimes = 0;
                    str = "onAutoFocusMoving start";
                } else if (this.mAFEndLogTimes == 0) {
                    str = "onAutoFocusMoving end. result=" + focusTask.isSuccess();
                    this.mAFEndLogTimes++;
                    if (this.mFirstCreateCapture) {
                        this.mFirstCreateCapture = false;
                    }
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
            } else {
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
            }
        }
    }

    @Override // com.android.camera2.Camera2Proxy.AnchorPreviewCallback
    public void onFrameThumbnailFail() {
        Log.e(TAG, "could not find anchor frame, use preview as thumbnail");
    }

    @Override // com.android.camera2.Camera2Proxy.AnchorPreviewCallback
    public void onFrameThumbnailSuccess(long j) {
        String str = TAG;
        Log.d(str, "anchor frame as thumbnail success " + j);
    }

    public void onHanGestureSwitched(boolean z) {
        if (z) {
            PreviewDecodeManager.getInstance().init(this.mBogusCameraId, 1);
            PreviewDecodeManager.getInstance().startDecode();
            return;
        }
        PreviewDecodeManager.getInstance().stopDecode(1);
    }

    @Override // com.android.camera2.Camera2Proxy.HdrCheckerCallback
    public void onHdrMotionDetectionResult(boolean z) {
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if (cameraCapabilities != null && cameraCapabilities.isMotionDetectionSupported() && this.mMotionDetected != z) {
            this.mMotionDetected = z;
            updateHDRPreference();
        }
    }

    @Override // com.android.camera2.Camera2Proxy.HdrCheckerCallback
    public void onHdrSceneChanged(boolean z) {
        if (!this.mPaused) {
            this.mIsInHDR = z;
            if (this.isDetectedInHdr != z && triggerHDR(z)) {
                ModeProtocol.MagneticSensorDetect magneticSensorDetect = this.mMagneticSensorDetect;
                if (magneticSensorDetect == null || !magneticSensorDetect.isLockHDRChecker("onHdrSceneChanged")) {
                    if (getCameraDevice().getCapabilities().getMiAlgoASDVersion() < 3.0f) {
                        updateHDRTip(z);
                    }
                    if (z) {
                        if (this.mMutexModePicker.isNormal() || this.mMutexModePicker.isSuperResolution()) {
                            this.mMutexModePicker.setMutexMode(1);
                        }
                    } else if (this.mMutexModePicker.isMorphoHdr()) {
                        this.mMutexModePicker.resetMutexMode();
                    }
                    this.isDetectedInHdr = z;
                    String str = TAG;
                    Log.d(str, "onHdrSceneChanged: " + z + ", caller: " + Util.getCallers(3));
                }
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onHostStopAndNotifyActionStop() {
        super.onHostStopAndNotifyActionStop();
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(4097)) {
            this.mHandler.removeMessages(4097);
        }
        boolean z = false;
        if (this.mInStartingFocusRecording) {
            this.mInStartingFocusRecording = false;
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
        }
        boolean isInTimerBurstShotting = DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting();
        stopTimerBurst();
        if (isRecording()) {
            stopVideoRecording(true, true);
        }
        if (this.mMultiSnapStatus) {
            onBurstPictureTakenFinished(true);
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        if (isInTimerBurstShotting || handleSuperNightResultIfNeed()) {
            z = true;
        }
        if (z) {
            doLaterReleaseIfNeed();
        }
        handleSaveFinishIfNeed(null);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean onInterceptZoomingEvent(float f, float f2, int i) {
        ModeProtocol.MiAsdDetect miAsdDetect;
        if (f2 < 1.0f && (miAsdDetect = (ModeProtocol.MiAsdDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(235)) != null) {
            miAsdDetect.updateUltraWide(false, -1);
        }
        if (DataRepository.dataItemFeature().nm() && !this.mIsMoonMode) {
            if (this.m3ALocked) {
                unlockAEAF();
                FocusManager2 focusManager2 = this.mFocusManager;
                if (focusManager2 != null) {
                    focusManager2.cancelFocus();
                }
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips != null) {
                    bottomPopupTips.directlyHideTips();
                }
            } else {
                Camera2Proxy camera2Proxy = this.mCamera2Device;
                if (!(camera2Proxy == null || 4 == camera2Proxy.getFocusMode())) {
                    Log.d(TAG, "onInterceptZoomingEvent: should cancel focus.");
                    FocusManager2 focusManager22 = this.mFocusManager;
                    if (focusManager22 != null) {
                        focusManager22.cancelFocus();
                    }
                }
            }
        }
        return super.onInterceptZoomingEvent(f, f2, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        if (r6 != 88) goto L_0x0079;
     */
    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
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

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4 && ((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public void onLivePhotoResultCallback(LivePhotoResult livePhotoResult) {
        this.mLivePhotoQueue.offer(livePhotoResult);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onLongPress(float f, float f2) {
        if (this.mModuleIndex != 182) {
            int i = (int) f;
            int i2 = (int) f2;
            if (isInTapableRect(i, i2)) {
                if (!this.mIsCurrentLensEnabled || !this.mIsGoogleLensAvailable || this.mActivity.startFromSecureKeyguard() || !CameraSettings.isAvailableLongPressGoogleLens()) {
                    onSingleTapUp(i, i2, true);
                    if (this.m3ALockSupported && this.mCamera2Device.getFocusMode() != AutoFocus.convertToFocusMode("manual")) {
                        lockAEAF();
                    }
                    this.mMainProtocol.performHapticFeedback(0);
                } else if (DataRepository.dataItemGlobal().getString(CameraSettings.KEY_LONG_PRESS_VIEWFINDER, null) == null) {
                    CameraStatUtils.trackGoogleLensPicker();
                    GoogleLensFragment.showOptions(this.mActivity.getFragmentManager(), new C0088l(this, f, f2, i, i2));
                } else {
                    CameraStatUtils.trackGoogleLensTouchAndHold();
                    LensAgent.getInstance().onFocusChange(0, f / ((float) Util.sWindowWidth), f2 / ((float) Util.sWindowHeight));
                }
            }
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

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onNewUriArrived(Uri uri, String str) {
        if (uri == null) {
            handleSaveFinishIfNeed(str);
        }
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void onObjectStable() {
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onOrientationChanged(int i, int i2, int i3) {
        if (!this.isGradienterOn || this.mActivity.getSensorStateManager().isDeviceLying()) {
            setOrientation(i, i2);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onPause() {
        Camera camera;
        super.onPause();
        if (!(!this.mIsImageCaptureIntent || (camera = this.mActivity) == null || camera.getImageSaver() == null)) {
            Log.d(TAG, "onPause dropBitmapTexture");
            this.mActivity.getImageSaver().setDropBitmapTexture(true);
        }
        stopLiveShot(true);
        LiveMediaRecorder liveMediaRecorder = this.mLiveMediaRecorder;
        if (liveMediaRecorder != null) {
            liveMediaRecorder.release();
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        this.mWaitingSnapshot = false;
        unregisterSensor();
        this.mIsStartCount = false;
        tryRemoveCountDownMessage();
        this.mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        setAiSceneEffect(0);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.mIsNeedNightHDR = false;
        String str = TAG;
        Log.e(str, "(onPause)mIsNeedNightHDR:" + this.mIsNeedNightHDR);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTakenFinished(boolean z) {
        String str = TAG;
        Log.d(str, "onPictureTakenFinished: succeed = " + z);
        long currentTimeMillis = System.currentTimeMillis();
        if (z) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
            trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, this.mLocation != null, this.mCurrentAiScene);
            MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
            pictureTakenParameter.takenNum = 1;
            pictureTakenParameter.burst = false;
            pictureTakenParameter.location = this.mLocation != null;
            pictureTakenParameter.aiSceneName = getCurrentAiSceneName();
            pictureTakenParameter.isEnteringMoon = this.mEnteringMoonMode;
            pictureTakenParameter.isSelectMoonMode = this.mIsMoonMode;
            pictureTakenParameter.isSuperNightInCaptureMode = this.mShowSuperNightHint;
            pictureTakenParameter.beautyValues = this.mBeautyValues;
            pictureTakenParameter.isNearRangeMode = this.mIsNearRangeMode;
            trackPictureTaken(pictureTakenParameter);
            long j = currentTimeMillis - this.mCaptureStartTime;
            CameraStatUtils.trackTakePictureCost(j, isFrontCamera(), this.mModuleIndex);
            ScenarioTrackUtil.trackCaptureTimeEnd();
            String str2 = TAG;
            Log.d(str2, "mCaptureStartTime(from onShutterButtonClick start to jpegCallback finished) = " + j + d.H);
            if (this.mIsImageCaptureIntent) {
                if (this.mQuickCapture) {
                    doAttach();
                } else if (isAlive()) {
                    this.mKeepBitmapTexture = true;
                    Log.d(TAG, "onPictureTakenFinished: showPostCaptureAlert");
                    showPostCaptureAlert();
                } else {
                    Log.d(TAG, "onPictureTakenFinished: isAlive false");
                    Camera camera = this.mActivity;
                    if (!(camera == null || camera.getCameraScreenNail() == null)) {
                        Log.d(TAG, "releaseBitmapIfNeeded for isAlive false");
                        this.mActivity.getCameraScreenNail().releaseBitmapIfNeeded();
                    }
                }
            } else if (this.mLongPressedAutoFocus) {
                this.mLongPressedAutoFocus = false;
                this.mFocusManager.cancelLongPressedAutoFocus();
            }
        }
        if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && this.mBlockQuickShot && ((!CameraSettings.isGroupShotOn() || (CameraSettings.isGroupShotOn() && z)) && this.mFixedShot2ShotTime == -1)) {
            resetStatusToIdle();
        }
        this.mHandler.removeMessages(50);
        handleSuperNightResultIfNeed();
        handleSuperNightResultInCaptureMode();
        PreviewDecodeManager.getInstance().resetScanResult();
        doLaterReleaseIfNeed();
        dealTimerBurst(currentTimeMillis);
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
            if (this.mZoomMapController != null) {
                this.mZoomMapController.setMapRect(DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting() ? new MarshalQueryableChiRect.ChiRect(0, 0, 0, 0) : CaptureResultParser.getZoomMapRIO(this.mCameraCapabilities, captureResult));
            }
            Integer num = (Integer) captureResult.get(CaptureResult.SENSOR_SENSITIVITY);
            int minIso = this.mCameraCapabilities.getMinIso();
            if (minIso == 0) {
                this.mIsISORight4HWMFNR = num != null && num.intValue() >= 800;
            } else {
                this.mIsISORight4HWMFNR = num != null && num.intValue() >= minIso * 8;
            }
            if ((DataRepository.dataItemFeature().Ci() || !isFrontCamera()) && !DataRepository.dataItemFeature().ll()) {
                this.mShouldDoMFNR = false;
            } else if (c.Sm() || c.Km()) {
                this.mShouldDoMFNR = true;
            } else {
                String str = TAG;
                Log.c(str, "onPreviewMetaDataUpdate: iso = " + num + " minIso = " + minIso);
                this.mShouldDoMFNR = this.mIsISORight4HWMFNR;
            }
            if (shouldCheckSatFallbackState()) {
                checkSatFallback(captureResult);
            }
            FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
            if (flowableEmitter != null) {
                flowableEmitter.onNext(captureResult);
            }
            if (this.mAiSceneFlowableEmitter != null && ((this.mAiSceneEnabled || this.mAIWatermarkEnable) && this.mCamera2Device != null)) {
                this.mAiSceneFlowableEmitter.onNext(captureResult);
            }
            if (this.mIsTheShutterTime) {
                this.mPreviewSuperNightExifInfo = CaptureResultParser.getSuperNightInfo(captureResult);
                this.mIsTheShutterTime = false;
            }
            if (shouldCheckLLS()) {
                checkLLS(captureResult);
            }
            if (this.mMagneticSensorDetect != null && this.mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable()) {
                this.mMagneticSensorDetect.updatePreview(captureResult);
            }
            this.mAECLux = CaptureResultParser.getAecLux(captureResult);
            updateAiShutterExistMotion(captureResult);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
        animateCapture();
        playCameraSound(0);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        boolean z = isFrontCamera() && !isFrontMirror();
        synchronized (this.mCameraDeviceLock) {
            if (isAlive()) {
                if (isDeviceAlive()) {
                    if ((this.mEnableParallelSession || this.mEnableShot2Gallery || updateAnchorFramePreview()) && !this.mIsImageCaptureIntent) {
                        saveBitmapAsThumbnail(createBitmap, i, i2, false, false);
                        return;
                    }
                    int i3 = this.mShootOrientation - this.mDisplayRotation;
                    if (isFrontCamera() && c.Rm() && i3 % 180 == 0) {
                        i3 = 0;
                    }
                    Thumbnail createThumbnail = Thumbnail.createThumbnail(null, createBitmap, i3, z);
                    createThumbnail.startWaitingForUri();
                    this.mActivity.getThumbnailUpdater().setThumbnail(createThumbnail, true, true);
                    this.mCamera2Device.onPreviewThumbnailReceived(createThumbnail);
                    return;
                }
            }
            Log.d(TAG, "onPreviewPixelsRead: module is dead");
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
        Log.d(TAG, "onPreviewSessionClosed: ");
        setCameraState(0);
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
                    /* class com.android.camera.module.Camera2Module.AnonymousClass12 */

                    public void run() {
                        Camera2Module.this.initParallelSession();
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

    @Override // com.android.camera.module.BaseModule
    public void onPreviewSizeChanged(int i, int i2) {
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setPreviewSize(i, i2);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onResume() {
        super.onResume();
        this.mHandler.removeMessages(50);
        Camera camera = this.mActivity;
        CameraScreenNail cameraScreenNail = camera != null ? camera.getCameraScreenNail() : null;
        if ((cameraScreenNail != null && !cameraScreenNail.hasBitMapTexture()) || !isSelectingCapturedResult()) {
            this.mKeepBitmapTexture = false;
            if (cameraScreenNail != null) {
                Log.d(TAG, "onResume releaseBitmapIfNeeded");
                cameraScreenNail.releaseBitmapIfNeeded();
            }
            if (isSelectingCapturedResult()) {
                Log.d(TAG, "onResume hidePostCaptureAlert");
                hidePostCaptureAlert();
            }
        }
        keepScreenOnAwhile();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
        this.mKeepBitmapTexture = false;
        if (isSelectingCapturedResult()) {
            this.mActivity.getCameraScreenNail().releaseBitmapIfNeeded();
            hidePostCaptureAlert();
            return;
        }
        this.mActivity.setResult(0, new Intent());
        this.mActivity.finish();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        doAttach();
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 196) {
            updatePreferenceTrampoline(2);
            this.mMainProtocol.updateEffectViewVisible();
        } else if (i != 212 && i != 239) {
            throw new RuntimeException("unknown configItem changed");
        } else if (c.xn()) {
            updatePreferenceInWorkThread(13, 34, 42);
        } else {
            updatePreferenceInWorkThread(13);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        ModeProtocol.ManuallyAdjust manuallyAdjust;
        if (this.mMediaRecorderRecording) {
            Log.v(TAG, "skip shutter when recording.");
            return;
        }
        if (i == 100) {
            this.mActivity.onUserInteraction();
        } else if (i == 110) {
            this.mActivity.onUserInteraction();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.hideRecommendDescTip(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC);
            }
        }
        this.mIsTheShutterTime = true;
        int countDownTimes = getCountDownTimes(i);
        boolean z = countDownTimes > 0;
        TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
        String str = TAG;
        Log.i(str, "isInShotting: " + timerBurstController.isInTimerBurstShotting() + "\n(20:volume 10:shutter 120:timer) triggerMode:  " + i + ",isMenuTimer = " + z);
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (CameraSettings.isTimerBurstEnable() && TimerBurstController.isSupportTimerBurst(this.mModuleIndex)) {
            ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (!timerBurstController.isInTimerBurstShotting() && ((i == 10 || i == 20 || i == 110 || i == 100) && !z)) {
                keepScreenOn();
                topAlert2.hideAlert();
                timerBurstController.setInTimerBurstShotting(true);
                timerBurstController.resetTimerRunningData();
                recordState.onPrepare();
                topAlert2.setRecordingTimeState(1);
                if (this.mModuleIndex == 167 && (manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181)) != null) {
                    manuallyAdjust.setManuallyLayoutVisible(false);
                }
            } else if (timerBurstController.isInTimerBurstShotting() && i == 120) {
                topAlert2.setRecordingTimeState(1);
            } else if (timerBurstController.isInTimerBurstShotting() && i != 120) {
                stopTimerBurst();
                return;
            }
        } else if (DataRepository.dataItemConfig().getmComponentManuallyET().isLongExpose(this.mModuleIndex)) {
            recordState.onLongExposePrepare();
        }
        int timerBurstInterval = CameraSettings.getTimerBurstInterval();
        if (!CameraSettings.isTimerBurstEnable() || !TimerBurstController.isSupportTimerBurst(this.mModuleIndex)) {
            if (z) {
                startCount(countDownTimes, 1, i);
                return;
            }
        } else if (timerBurstController.getIsDecreasedCount()) {
            keepScreenOn();
            startCount(timerBurstInterval, 1, 120);
            return;
        } else if (z) {
            startCount(countDownTimes, 1, 10);
            return;
        }
        MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
        ModeProtocol.TopAlert topAlert3 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert3 != null) {
            pictureTakenParameter.isASDBacklitTip = topAlert3.isShowBacklightSelector();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            pictureTakenParameter.isASDPortraitTip = bottomPopupTips.containTips(R.string.recommend_portrait);
        }
        if (checkShutterCondition()) {
            if (isNeedFixedShotTime()) {
                this.mFixedShot2ShotTime = DataRepository.dataItemFeature().Bh();
            } else {
                this.mFixedShot2ShotTime = -1;
            }
            if (this.mFixedShot2ShotTime != -1) {
                this.mCamera2Device.setFixShotTimeEnabled(true);
                if (this.mFixedShot2ShotTime > 0) {
                    this.mHandler.removeMessages(59);
                    this.mHandler.sendEmptyMessageDelayed(59, (long) this.mFixedShot2ShotTime);
                    String str2 = TAG;
                    Log.d(str2, ":send MSG_FIXED_SHOT2SHOT_TIME_OUT" + this.mFixedShot2ShotTime);
                }
            } else {
                this.mCamera2Device.setFixShotTimeEnabled(false);
            }
            setTriggerMode(i);
            String str3 = TAG;
            Log.d(str3, "onShutterButtonClick " + getCameraState());
            this.mFocusManager.prepareCapture(this.mNeedAutoFocus, 2);
            this.mFocusManager.doSnap();
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
        Handler handler;
        if (isDoingAction()) {
            Log.d(TAG, "onShutterButtonLongClick: doing action");
            return false;
        } else if (this.mIsSatFallback == 0 || !shouldCheckSatFallbackState()) {
            boolean z = (c.Og || c.fh) && isZoomRatioBetweenUltraAndWide();
            if (CameraSettings.isLongPressRecordEnable() && ModuleManager.isCameraModule() && !this.mIsImageCaptureIntent && !this.mMediaRecorderRecording && (handler = this.mHandler) != null && !handler.hasMessages(4097) && !CameraSettings.isTiltShiftOn() && !CameraSettings.isAIWatermarkOn() && !CameraSettings.isDocumentModeOn(this.mModuleIndex)) {
                ModeProtocol.ModeChangeController modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
                if (modeChangeController == null || !modeChangeController.modeChanging()) {
                    this.mInStartingFocusRecording = false;
                    ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                    if (recordState == null) {
                        return true;
                    }
                    recordState.onPrepare();
                    if (!checkCallingState()) {
                        recordState.onFailed();
                        return true;
                    }
                    this.mActivity.getScreenHint().updateHint();
                    if (Storage.isLowStorageAtLastPoint()) {
                        recordState.onFailed();
                        return true;
                    }
                    setTriggerMode(10);
                    enableCameraControls(false);
                    playVideoSound(false);
                    this.mRequestStartTime = System.currentTimeMillis();
                    if (this.mFocusManager.canRecord()) {
                        this.mIsShutterLongClickRecording = true;
                        this.mHandler.sendEmptyMessageDelayed(4097, 250);
                    } else {
                        Log.v(TAG, "wait for autoFocus");
                        this.mInStartingFocusRecording = true;
                    }
                    return true;
                }
                Log.d(TAG, "skip record caz mode changing.");
                return true;
            } else if (!CameraSettings.isBurstShootingEnable() || !ModuleManager.isCameraModule() || this.mIsImageCaptureIntent || CameraSettings.isGroupShotOn() || CameraSettings.isTiltShiftOn() || DataRepository.dataItemRunning().isSwitchOn("pref_camera_hand_night_key") || DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key") || CameraSettings.isPortraitModeBackOn() || !isBackCamera() || this.mMultiSnapStatus || this.mHandler.hasMessages(24) || this.mPendingMultiCapture || isUltraWideBackCamera() || z || CameraSettings.isUltraPixelOn() || isStandaloneMacroCamera() || CameraSettings.isAIWatermarkOn() || CameraSettings.isDocumentModeOn(this.mModuleIndex) || CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex)) {
                this.mLongPressedAutoFocus = true;
                this.mMainProtocol.setFocusViewType(false);
                unlockAEAF();
                this.mFocusManager.requestAutoFocus();
                this.mActivity.getScreenHint().updateHint();
                return false;
            } else {
                this.mPendingMultiCapture = true;
                this.mFocusManager.doMultiSnap(true);
                return true;
            }
        } else {
            Log.d(TAG, "onShutterButtonLongClick: sat fallback");
            return false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
        Log.d(TAG, "onShutterButtonLongClickCancel: start");
        Handler handler = this.mHandler;
        if (handler != null && handler.hasMessages(4097)) {
            this.mHandler.removeMessages(4097);
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFailed();
            }
            Log.v(TAG, "onShutterButtonLongClickCancel, remove start recording task");
            enableCameraControls(true);
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
            }
        } else if (this.mMediaRecorderRecording) {
            this.mIsShutterLongClickRecording = false;
            stopVideoRecording(true, false);
        } else {
            this.mPendingMultiCapture = false;
            if (this.mMultiSnapStatus) {
                this.mHandler.sendEmptyMessageDelayed(49, 2000);
            }
            this.mMultiSnapStopRequest = true;
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setMultiSnapStopRequest(true);
            }
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
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        String str = TAG;
        Log.v(str, "onSingleTapUp mPaused: " + this.mPaused + "; mCamera2Device: " + this.mCamera2Device + "; isInCountDown: " + isInCountDown() + "; getCameraState: " + getCameraState() + "; mMultiSnapStatus: " + this.mMultiSnapStatus + "; Camera2Module: " + this);
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && this.mCamera2Device.isPreviewReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 4 && getCameraState() != 0 && !isInCountDown() && !this.mMultiSnapStatus && this.mModuleIndex != 182) {
            if (this.mIsMoonMode) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                boolean z2 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
                boolean z3 = topAlert != null && topAlert.isExtraMenuShowing();
                if (!z2 && !z3) {
                    return;
                }
            }
            if (isFrameAvailable()) {
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
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onStop() {
        super.onStop();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.SuperNightCallback
    public void onSuperNightChanged(final boolean z) {
        if (z != CameraSettings.isSuperNightOn()) {
            CameraSettings.setSuperNightOn(z);
            String str = TAG;
            Log.d(str, "super night changed：" + z);
            if (z) {
                this.mCamera2Device.setSuperResolution(false);
            } else if (this.mMutexModePicker.isSuperResolution()) {
                this.mCamera2Device.setSuperResolution(true);
            }
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass6 */

                public void run() {
                    Camera2Module.this.mMainProtocol.setEvAdjustable(!z);
                }
            });
            if (z && this.mMagneticSensorDetect != null && this.mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable() && this.mMagneticSensorDetect.isLockHDRChecker("realConsumeAiSceneResult")) {
                resetMagneticInfo();
            }
            updateHDRPreference();
            resumePreviewInWorkThread();
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSurfaceTextureReleased() {
        Log.d(TAG, "onSurfaceTextureReleased: no further preview frame will be available");
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSurfaceTextureUpdated(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
        if (this.mMediaRecorderRecording) {
            LiveMediaRecorder liveMediaRecorder = this.mLiveMediaRecorder;
            if (liveMediaRecorder != null) {
                liveMediaRecorder.onSurfaceTextureUpdated(drawExtTexAttribute, this.mMediaRecorderRecording);
                return;
            }
            return;
        }
        CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
        if (circularMediaRecorder != null) {
            circularMediaRecorder.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onPreviewComing();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onThermalConstrained() {
        super.onThermalConstrained();
        if (this.mMediaRecorderRecording) {
            stopVideoRecording(true, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onThumbnailClicked(View view) {
        if (this.mWaitSaveFinish) {
            Log.d(TAG, "onThumbnailClicked: CannotGotoGallery...mWaitSaveFinish");
            return;
        }
        if (this.mEnableParallelSession || this.mEnableShot2Gallery || this.mSupportAnchorFrameAsThumbnail) {
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

    public void onTiltShiftSwitched(boolean z) {
        if (z) {
            resetEvValue();
        }
        this.mMainProtocol.initEffectCropView();
        updatePreferenceTrampoline(2, 5);
        this.mMainProtocol.updateEffectViewVisible();
        this.mMainProtocol.setEvAdjustable(!z);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!isDoingAction()) {
            keepScreenOnAwhile();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean onWaitingFocusFinished() {
        if (isBlockSnap() || !isAlive()) {
            return false;
        }
        if (this.mInStartingFocusRecording) {
            this.mInStartingFocusRecording = false;
            if (this.mIsSatFallback == 0 || !shouldCheckSatFallbackState()) {
                startVideoRecording();
                return true;
            }
            Log.w(TAG, "video record check: sat fallback");
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
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            if (CameraSettings.isUltraPixelOn() && ((dualController == null || !dualController.isZoomVisible()) && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null)) {
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
        if (this.mMagneticSensorDetect != null && this.mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable()) {
            this.mMagneticSensorDetect.resetMagneticInfo();
        }
        if (this.mSupportFlashHDR) {
            Camera2Proxy.HDRStatus hDRStatus = this.mCamera2Device.getCameraConfigs().getHDRStatus();
            if (hDRStatus.isFlashHDR() && hDRStatus.isEnable()) {
                this.mIsNeedNightHDR = false;
                this.mMutexModePicker.resetMutexMode();
            }
        }
        ZoomMapController zoomMapController = this.mZoomMapController;
        if (zoomMapController != null) {
            zoomMapController.onZoomRatioUpdate(f);
        }
        return super.onZoomingActionUpdate(f, i);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.mActivity, CameraPreferenceActivity.class);
        intent.putExtra("from_where", this.mModuleIndex);
        intent.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_camera_settings_category));
        if (this.mActivity.startFromKeyguard()) {
            intent.putExtra("StartActivityWhenLocked", true);
        }
        this.mActivity.startActivity(intent);
        this.mActivity.setJumpFlag(2);
        CameraStatUtils.trackGotoSettings(this.mModuleIndex);
    }

    public void pauseOrResumeTimer() {
        CameraTimer cameraTimer = this.mCameraTimer;
        if (cameraTimer != null) {
            if (cameraTimer.isRunning()) {
                this.mCameraTimer.pause();
            } else {
                this.mCameraTimer.resume();
            }
        }
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

    public /* synthetic */ void pd() {
        this.mActivity.getSensorStateManager().setLieIndicatorEnabled(true);
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
                        startCount(2, 1, 20);
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

    @Override // com.android.camera.module.BaseModule
    public void playVideoSound(boolean z) {
        if (!z) {
            playCameraSound(2);
        } else if (!this.mPaused) {
            playCameraSound(3);
        }
    }

    public /* synthetic */ void qd() {
        this.mCamera2Device.setDeviceOrientation(this.mOrientation);
    }

    public /* synthetic */ void rd() {
        this.mWaitSaveFinish = false;
        this.mHandler.removeMessages(61);
        Log.i(TAG, "showDocumentPreview finished");
        this.mMainProtocol.hideOrShowDocument(true);
        PreviewDecodeManager.getInstance().startDecode();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void reShowMoon() {
        if (this.mEnteringMoonMode) {
            showMoonMode(this.mIsMoonMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void registerProtocol() {
        Camera camera;
        ModeProtocol.CameraClickObservable cameraClickObservable;
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(195, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 227, 235, 212, 254);
        boolean z = false;
        if (DataRepository.dataItemFeature().Cl()) {
            getActivity().getImplFactory().initAdditional(getActivity(), 2576);
            this.mMagneticSensorDetect = (ModeProtocol.MagneticSensorDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(2576);
        }
        if (getModuleIndex() == 163 && (cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227)) != null) {
            cameraClickObservable.addObservable(new int[]{R.string.recommend_portrait, R.string.recommend_super_night, R.string.lens_dirty_detected_title_back, R.string.recommend_macro_mode, R.string.ultra_wide_recommend_tip_hint_sat}, this.mCameraClickObserverAction, 161, 162, 166, 163, 164, 165, 167, 168);
        }
        if (getModuleIndex() == 173) {
            getActivity().getImplFactory().initAdditional(getActivity(), 212);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("registerProtocol: mIsGoogleLensAvailable = ");
        sb.append(this.mIsGoogleLensAvailable);
        sb.append(", activity is null ? ");
        if (this.mActivity == null) {
            z = true;
        }
        sb.append(z);
        Log.d(str, sb.toString());
        if (this.mIsGoogleLensAvailable && (camera = this.mActivity) != null) {
            camera.runOnUiThread(new RunnableC0076h(this));
        }
        this.mIsMacroModeEnable = CameraSettings.isMacroModeEnabled(this.mModuleIndex);
        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
        if (cameraCapabilities != null) {
            this.mSupportFlashHDR = cameraCapabilities.isSupportFlashHdr();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void resetAiSceneInDocumentModeOn() {
        int i;
        if (this.mAiSceneEnabled && !this.isResetFromMutex && (i = this.mCurrentAiScene) != 0) {
            if (i == -1 || i == 10 || i == 35) {
                this.mHandler.post(new RunnableC0097v(this));
            }
        }
    }

    @Override // com.android.camera2.Camera2Proxy.MagneticDetectedCallback
    public void resetMagneticInfo() {
        if (this.mMagneticSensorDetect != null && this.mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable()) {
            this.mMagneticSensorDetect.resetMagneticInfo();
        }
    }

    /* access modifiers changed from: protected */
    public void resetMetaDataManager() {
        CameraSettings.isSupportedMetadata();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void resetScreenOn() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(17);
            this.mHandler.removeMessages(2);
        }
    }

    /* access modifiers changed from: protected */
    public void resetStatusToIdle() {
        Log.d(TAG, "reset Status to Idle");
        setCameraState(1);
        enableCameraControls(true);
        this.mBlockQuickShot = false;
        this.mFixedShot2ShotTime = -1;
    }

    public void resetToIdleNoKeep(boolean z) {
        if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && z) {
            setCameraState(1);
            enableCameraControls(true);
        }
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        previewWhenSessionSuccess();
        this.mBlockQuickShot = !CameraSettings.isCameraQuickShotEnable();
    }

    @Override // com.android.camera2.Camera2Proxy.AnchorPreviewCallback
    public void saveBitmapAsThumbnail(Bitmap bitmap, int i, int i2, boolean z, boolean z2) {
        boolean z3 = isFrontCamera() && !isFrontMirror();
        int orientation = DataRepository.dataItemLive().getTimerBurstController().getOrientation(this.mCamera2Device.isInTimerBurstShotting(), this.mOrientation);
        Bitmap cropBitmap = Util.cropBitmap(bitmap, this.mShootRotation, z3, (float) orientation, this.mModuleIndex == 165, CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex));
        if (cropBitmap == null) {
            Log.w(TAG, "saveBitmapAsThumbnail: bitmap is null!");
            return;
        }
        byte[] bitmapData = Util.getBitmapData(cropBitmap, EncodingQuality.NORMAL.toInteger(false));
        if (bitmapData == null) {
            Log.w(TAG, "saveBitmapAsThumbnail: jpegData is null!");
            return;
        }
        int pictureFormatSuitableForShot = getPictureFormatSuitableForShot(this.mCamera2Device.getCameraConfigs().getShotType());
        int i3 = this.mNormalFilterId;
        if (i3 == FilterInfo.FILTER_ID_NONE) {
            i3 = this.mLightFilterId;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("saveBitmapAsThumbnail: isParallel = ");
        sb.append(this.mEnableParallelSession);
        sb.append(", shot2Gallery = ");
        sb.append(this.mEnableShot2Gallery);
        sb.append(", format = ");
        sb.append(CompatibilityUtils.isHeicImageFormat(pictureFormatSuitableForShot) ? "HEIC" : "JPEG");
        sb.append(", data = ");
        sb.append(bitmapData);
        sb.append(", anchorFrame= ");
        sb.append(z);
        sb.append(", noGaussian= ");
        sb.append(z2);
        sb.append(", filterId= ");
        sb.append(i3);
        Log.d(str, sb.toString());
        ParallelTaskData parallelTaskData = new ParallelTaskData(this.mActualCameraId, System.currentTimeMillis(), -1, this.mCamera2Device.getParallelShotSavePath(), this.mCaptureStartTime);
        parallelTaskData.setNeedThumbnail(this.mEnableParallelSession || this.mEnableShot2Gallery || updateAnchorFramePreview());
        parallelTaskData.fillJpegData(bitmapData, 0);
        parallelTaskData.setNoGaussian(z2);
        parallelTaskData.fillParameter(new ParallelTaskDataParameter.Builder(new Size(i, i2), new Size(i, i2), new Size(i, i2), pictureFormatSuitableForShot).setOrientation(orientation).setJpegRotation(this.mJpegRotation).setShootRotation(this.mShootRotation).setShootOrientation(this.mShootOrientation).setSupportZeroDegreeOrientationImage(this.mCameraCapabilities.isSupportZeroDegreeOrientationImage()).setLocation(this.mLocation).setFilterId(i3).setAnchorPreview(z).setPictureInfo(getPictureInfo()).setMirror(isFrontMirror()).setFrontCamera(isFrontCamera()).build());
        if (DataRepository.dataItemFeature().Ol()) {
            parallelTaskData.setRequireTuningData(true);
        }
        this.mActivity.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
    }

    public boolean scanQRCodeEnabled() {
        int i;
        return CameraSettings.isScanQRCode(this.mActivity) && ((i = this.mModuleIndex) == 163 || i == 165) && !this.mIsImageCaptureIntent && CameraSettings.isBackCamera() && !this.mMultiSnapStatus && !CameraSettings.isPortraitModeBackOn() && ((!DataRepository.dataItemFeature().Oi() || !CameraSettings.isUltraPixelOn()) && !CameraSettings.isUltraWideConfigOpen(this.mModuleIndex) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex));
    }

    public /* synthetic */ void sd() {
        showOrHideChip(false);
        this.mIsFaceConflict = false;
        this.mIsUltraWideConflict = false;
        this.mIsAiConflict = false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void sendOpenFailMessage() {
        this.mHandler.sendEmptyMessage(10);
    }

    public void setAsdScenes(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        this.mAsdScenes = aSDSceneArr;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
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
        if (z && isBackCamera()) {
            int i = this.mModuleIndex;
            if ((i == 165 || i == 163) && CameraSettings.isCameraLyingHintOn()) {
                this.mHandler.post(new RunnableC0092p(this));
            }
        }
    }

    public void setIsUltraWideConflict(boolean z) {
        this.mIsUltraWideConflict = z;
    }

    @Override // com.android.camera2.Camera2Proxy.NearRangeModeCallback
    public void setNearRangeMode(boolean z) {
        this.mIsNearRangeMode = z;
    }

    @Override // com.android.camera2.Camera2Proxy.NearRangeModeCallback
    public void setNearRangeModeUIStatus(boolean z) {
        this.mIsNearRangeModeUITip = z;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        Camera2Proxy camera2Proxy;
        return this.mUseLegacyFlashMode && (camera2Proxy = this.mCamera2Device) != null && camera2Proxy.isNeedFlashOn();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean shouldCheckSatFallbackState() {
        return isIn3OrMoreSatMode() && DataRepository.dataItemFeature().shouldCheckSatFallbackState();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean shouldReleaseLater() {
        Handler handler;
        Handler handler2;
        Camera2Proxy camera2Proxy;
        this.mIsStartCount = false;
        tryRemoveCountDownMessage();
        boolean z = getCameraState() == 3 || ((camera2Proxy = this.mCamera2Device) != null && camera2Proxy.isCaptureBusy(true));
        boolean isInTimerBurstShotting = DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting();
        if (this.mInStartingFocusRecording || isRecording() || isInTimerBurstShotting || ((handler = this.mHandler) != null && handler.hasMessages(4097))) {
            return true;
        }
        if (this.mIsImageCaptureIntent) {
            return false;
        }
        if (!z && (!this.mEnableShot2Gallery || (handler2 = this.mHandler) == null || !handler2.hasMessages(50))) {
            return false;
        }
        Handler handler3 = this.mHandler;
        if ((handler3 == null || (!handler3.hasMessages(48) && !this.mHandler.hasMessages(49))) && !this.mFocusManager.isFocusing()) {
            return this.mModuleIndex != 167 || getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)).equals(getString(R.string.pref_camera_exposuretime_default));
        }
        return false;
    }

    public void showBacklightTip() {
        if (!CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement("e", 193);
            topAlert.alertHDR(8, false, false);
            topAlert.alertAiSceneSelector(0);
            applyBacklightEffect();
            resumePreviewInWorkThread();
        }
    }

    public void showDocumentPreview(PreviewImage previewImage, float[] fArr) {
        playCameraSound(0);
        String parallelShotSavePath = this.mCamera2Device.getParallelShotSavePath();
        String componentValue = DataRepository.dataItemRunning().getComponentRunningDocument().getComponentValue(this.mModuleIndex);
        String str = TAG;
        Log.d(str, "showDocumentPreview: savePath = " + parallelShotSavePath + ", value = " + componentValue);
        Bitmap doCropAndEnhance = DocumentProcess.getInstance().doCropAndEnhance(previewImage.getData(), previewImage.getWidth(), previewImage.getHeight(), fArr, DocumentProcess.EnhanceType.valueOf(componentValue.toUpperCase(Locale.ENGLISH)), false, this.mRotateFlags);
        float[] rotatePoints = DocumentProcess.getInstance().rotatePoints(fArr, previewImage.getWidth(), previewImage.getHeight(), this.mRotateFlags);
        String str2 = TAG;
        Log.d(str2, "showDocumentPreview: points = " + Arrays.toString(fArr) + ", rotatePoints = " + Arrays.toString(rotatePoints));
        if (doCropAndEnhance == null) {
            Log.d(TAG, "showDocumentPreview cropImage is null...");
            this.mMainProtocol.hideOrShowDocument(true);
            PreviewDecodeManager.getInstance().startDecode();
            return;
        }
        this.mHandler.post(new RunnableC0090n((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162), doCropAndEnhance, rotatePoints));
        this.mHandler.postDelayed(new RunnableC0091o(this), Util.getEnterDuration() + Util.getSuspendDuration() + Util.getExitDuration());
        if (Util.isSaveDocPreview()) {
            Schedulers.io().scheduleDirect(new A(this, parallelShotSavePath, doCropAndEnhance));
        }
        byte[] bitmapData = Util.getBitmapData(doCropAndEnhance, EncodingQuality.NORMAL.toInteger(false));
        if (bitmapData.length < 1) {
            Log.w(TAG, "showDocumentPreview: jpegData is null!");
            return;
        }
        int width = doCropAndEnhance.getWidth();
        int height = doCropAndEnhance.getHeight();
        ParallelTaskData parallelTaskData = new ParallelTaskData(this.mActualCameraId, System.currentTimeMillis(), -1, parallelShotSavePath);
        parallelTaskData.setNeedThumbnail(this.mEnableParallelSession || this.mEnableShot2Gallery);
        parallelTaskData.fillJpegData(bitmapData, 0);
        parallelTaskData.fillParameter(new ParallelTaskDataParameter.Builder(new Size(width, height), new Size(width, height), new Size(width, height), 256).setOrientation(this.mOrientation).build());
        parallelTaskData.setRequireTuningData(true);
        this.mActivity.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial, com.android.camera.module.BaseModule
    public void showOrHideChip(boolean z) {
        if (this.mIsGoogleLensAvailable) {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            boolean z2 = true;
            if (z) {
                boolean z3 = bottomPopupTips != null && bottomPopupTips.isTipShowing();
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                boolean z4 = dualController != null && dualController.isZoomPanelVisible();
                ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                boolean z5 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                boolean z6 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
                boolean z7 = !this.mIsAiConflict && !this.mIsFaceConflict && !this.mIsUltraWideConflict && !this.mIsMoonMode && !z3 && !z4 && !z5 && !z6;
                Log.d(TAG, "pre showOrHideChip: isTipsShow = " + z3 + ", isZoomSlideVisible = " + z4 + ", isSeekBarVisible = " + z5 + ", isMakeupVisible = " + z6 + ", mIsAiConflict = " + this.mIsAiConflict + ", mIsUltraWideConflict = " + this.mIsUltraWideConflict + ", mIsMoonMode = " + this.mIsMoonMode + ", mIsFaceConflict = " + this.mIsFaceConflict + ", final isShow = " + z7 + ", mIsCurrentLensEnabled = " + this.mIsCurrentLensEnabled);
                z = z7;
            }
            if (this.mIsCurrentLensEnabled != z) {
                this.mIsCurrentLensEnabled = z;
                Log.d(TAG, "showOrHideChip: show = " + z + ", isChipsEnabled = " + CameraSettings.isAvailableChipsGoogleLens());
                LensAgent instance = LensAgent.getInstance();
                if (!z || !CameraSettings.isAvailableChipsGoogleLens()) {
                    z2 = false;
                }
                instance.showOrHideChip(z2);
                if (bottomPopupTips != null) {
                    bottomPopupTips.reConfigQrCodeTip();
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial, com.android.camera.module.BaseModule
    public void showQRCodeResult() {
        if (!this.mPaused) {
            String scanResult = PreviewDecodeManager.getInstance().getScanResult();
            if (scanResult == null || scanResult.isEmpty()) {
                Log.e(TAG, "showQRCodeResult: get a null result!");
                return;
            }
            Camera camera = this.mActivity;
            camera.dismissKeyguard();
            Intent intent = new Intent(Util.QRCODE_RECEIVER_ACTION);
            intent.addFlags(32);
            intent.setPackage("com.xiaomi.scanner");
            intent.putExtra("result", scanResult);
            camera.sendBroadcast(intent);
            camera.setJumpFlag(3);
            PreviewDecodeManager.getInstance().resetScanResult();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void startAiLens() {
        this.mHandler.postDelayed(new RunnableC0093q(this), 300);
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFaceDetection() {
        Camera camera;
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && (camera = this.mActivity) != null && !camera.isActivityPaused() && isAlive() && this.mMaxFaceCount > 0 && this.mCamera2Device != null) {
            this.mFaceDetectionStarted = true;
            this.mMainProtocol.setActiveIndicator(1);
            this.mCamera2Device.startFaceDetection();
            updateFaceView(true, true);
        }
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

    public void startLiveShot() {
        synchronized (this.mCircularMediaRecorderStateLock) {
            try {
                if (this.mCircularMediaRecorder == null) {
                    this.mCircularMediaRecorder = new CircularMediaRecorder(this.mVideoSize.width, this.mVideoSize.height, getActivity().getGLView().getEGLContext14(), this.mIsMicrophoneEnabled, this.mLivePhotoQueue);
                }
                this.mLiveShotEnabled = true;
                this.mCircularMediaRecorder.setOrientationHint(this.mOrientationCompensation);
                this.mCircularMediaRecorder.start();
            } catch (Exception e2) {
                String str = TAG;
                Log.w(str, "startLiveShot: " + e2.getMessage());
                return;
            }
        }
        this.mActivity.getSensorStateManager().setGyroscopeEnabled(true);
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void startObjectTracking() {
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        int i;
        int i2;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setActivityHashCode(this.mActivity.hashCode());
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setMetaDataCallback(this);
            this.mCamera2Device.setScreenLightCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setMagneticDetectedCallback(this);
            Log.d(TAG, "startPreview: set PictureSize with " + this.mPictureSize);
            this.mCamera2Device.setPictureSize(this.mPictureSize);
            this.mCamera2Device.setPictureFormat(this.mOutputPictureFormat);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("startPreview: set PictureFormat to ");
            sb.append(CompatibilityUtils.isHeicImageFormat(this.mOutputPictureFormat) ? "HEIC" : "JPEG");
            Log.d(str, sb.toString());
            if (isSensorRawStreamRequired() && this.mSensorRawImageSize != null) {
                Log.d(TAG, "startPreview: set SensorRawImageSize with " + this.mSensorRawImageSize);
                this.mCamera2Device.setSensorRawImageSize(this.mSensorRawImageSize, isCurrentRawDomainBasedSuperNight());
            }
            if (this.mEnableParallelSession && isPortraitMode()) {
                Log.d(TAG, "startPreview: set SubPictureSize with " + this.mSubPictureSize);
                this.mCamera2Device.setSubPictureSize(this.mSubPictureSize);
            }
            boolean isEnableQcfaForAlgoUp = isEnableQcfaForAlgoUp();
            Log.d(TAG, "[QCFA] startPreview: set qcfa enable " + isEnableQcfaForAlgoUp);
            this.mCamera2Device.setQcfaEnable(isEnableQcfaForAlgoUp);
            if (isEnableQcfaForAlgoUp) {
                Log.d(TAG, "startPreview: set binning picture size to " + this.mBinningPictureSize);
                this.mCamera2Device.setBinningPictureSize(this.mBinningPictureSize);
            }
            boolean scanQRCodeEnabled = scanQRCodeEnabled();
            boolean z = true;
            boolean z2 = isFrontCamera() && ((i2 = this.mModuleIndex) == 163 || i2 == 165 || i2 == 186 || i2 == 171 || i2 == 182 || i2 == 205) && DataRepository.dataItemRunning().supportHandGesture();
            if (this.mIsGoogleLensAvailable) {
                PreviewDecodeManager.getInstance().init(this.mActualCameraId, 2);
                i = 8;
            } else {
                i = 0;
            }
            if (scanQRCodeEnabled) {
                i |= 2;
                PreviewDecodeManager.getInstance().init(this.mBogusCameraId, 0);
            }
            if (z2) {
                i |= 4;
                PreviewDecodeManager.getInstance().init(this.mBogusCameraId, 1);
            }
            if (isBackCamera() && CameraSettings.isDocumentMode2On(this.mModuleIndex)) {
                CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
                if (cameraCapabilities != null) {
                    this.mRotateFlags = DocumentDecoder.getRotateFlag(cameraCapabilities.getSensorOrientation());
                }
                i |= 32;
                PreviewDecodeManager.getInstance().init(this.mActualCameraId, 3);
            }
            if (this.mSupportAnchorFrameAsThumbnail) {
                i |= 16;
                this.mCacheImageDecoder = new CacheImageDecoder();
                this.mCacheImageDecoder.init(this.mBogusCameraId);
                this.mCacheImageDecoder.setAnchorPreviewCallback(this);
                this.mCamera2Device.setCacheImageDecoder(this.mCacheImageDecoder);
                this.mCamera2Device.setPreviewMaxImages(21);
            }
            SurfaceTexture surfaceTexture = this.mActivity.getCameraScreenNail().getSurfaceTexture();
            Log.d(TAG, "startPreview: surfaceTexture = " + surfaceTexture);
            if (surfaceTexture != null) {
                this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            }
            Surface surface = surfaceTexture != null ? new Surface(surfaceTexture) : null;
            if (!isSensorRawStreamRequired() || this.mSensorRawImageSize == null) {
                z = false;
            }
            this.mConfigRawStream = z;
            int operatingMode = getOperatingMode();
            if (CameraSettings.isMacro2Sat() && 36866 == operatingMode && DataRepository.dataItemFeature().Hi()) {
                int lensIndex = CameraSettings.getLensIndex();
                operatingMode |= lensIndex << 8;
                Log.d(TAG, "getOperatingMode = " + operatingMode);
                Log.d(TAG, "Index = " + lensIndex);
            }
            if (CameraSettings.isMacro2Sat()) {
                CameraSettings.setMacro2Sat(false);
            }
            ZoomMapController zoomMapController = this.mZoomMapController;
            this.mCamera2Device.startPreviewSession(surface, i, this.mConfigRawStream, isCurrentRawDomainBasedSuperNight(), zoomMapController != null ? zoomMapController.createZoomMapSurfaceIfNeeded() : null, operatingMode, this.mEnableParallelSession, this);
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null && CameraSettings.isPictureFlawCheckOn()) {
            localBinder.setOnSessionStatusCallBackListener(this.mSessionStatusCallbackListener);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.ScreenLightCallback
    public void startScreenLight(final int i, final int i2) {
        if (!this.mPaused) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass22 */

                public void run() {
                    ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                    if (fullScreenProtocol != null) {
                        fullScreenProtocol.setScreenLightColor(i);
                        if (fullScreenProtocol.showScreenLight()) {
                            Camera2Proxy camera2Proxy = Camera2Module.this.mCamera2Device;
                            if (camera2Proxy != null) {
                                camera2Proxy.setAELock(true);
                            }
                            Camera camera = Camera2Module.this.mActivity;
                            if (camera != null) {
                                camera.setWindowBrightness(i2);
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void startVideoRecording() {
        if (this.mCamera2Device == null) {
            Log.e(TAG, "initializeRecorder: null camera");
            return;
        }
        Log.v(TAG, "startVideoRecording");
        silenceOuterAudio();
        if (this.mLiveMediaRecorder == null) {
            this.mLiveMediaRecorder = new LiveMediaRecorder();
        }
        String genVideoPath = Util.genVideoPath(2, getString(R.string.video_file_name_format));
        CameraSize cameraSize = this.mVideoSize;
        if (!this.mLiveMediaRecorder.init(Util.genContentValues(2, genVideoPath, cameraSize.width, cameraSize.height), this.mOrientationCompensation, getActivity().getGLView().getEGLContext14(), this.mMediaEncoderListener) || !this.mLiveMediaRecorder.startRecorder(this.mRequestStartTime)) {
            onStartRecorderFail();
            return;
        }
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
        }
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onStart();
        }
        Log.v(TAG, "startVideoRecording process done");
        onStartRecorderSucceed();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            if (!c.isMTKPlatform() || !(getCameraState() == 3 || getCameraState() == 0)) {
                this.mCamera2Device.stopFaceDetection();
            }
            this.mFaceDetectionStarted = false;
            this.mMainProtocol.setActiveIndicator(2);
            updateFaceView(false, z);
        }
    }

    public void stopLiveShot(boolean z) {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (this.mCircularMediaRecorder != null) {
                if (z) {
                    this.mCircularMediaRecorder.moduleSwitched();
                }
                this.mCircularMediaRecorder.stop();
                if (z) {
                    this.mCircularMediaRecorder.release();
                    this.mCircularMediaRecorder = null;
                }
            }
            this.mLiveShotEnabled = false;
        }
        this.mActivity.getSensorStateManager().setGyroscopeEnabled(false);
        this.mLivePhotoQueue.clear();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener, com.android.camera.ui.ObjectView.ObjectViewListener
    public void stopObjectTracking(boolean z) {
    }

    @Override // com.android.camera2.Camera2Proxy.ScreenLightCallback
    public void stopScreenLight() {
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass23 */

            public void run() {
                Camera2Proxy camera2Proxy = Camera2Module.this.mCamera2Device;
                if (camera2Proxy != null) {
                    camera2Proxy.setAELock(false);
                }
                Camera camera = Camera2Module.this.mActivity;
                if (camera != null) {
                    camera.restoreWindowBrightness();
                }
                ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                String str = Camera2Module.TAG;
                Log.d(str, "stopScreenLight: protocol = " + fullScreenProtocol + ", mHandler = " + Camera2Module.this.mHandler);
                if (fullScreenProtocol != null) {
                    fullScreenProtocol.hideScreenLight();
                }
            }
        });
    }

    public void stopVideoRecording(boolean z, boolean z2) {
        if (this.mMediaRecorderRecording) {
            if (is3ALocked()) {
                unlockAEAF();
            }
            this.mMediaRecorderRecording = false;
            LiveMediaRecorder liveMediaRecorder = this.mLiveMediaRecorder;
            if (liveMediaRecorder != null) {
                liveMediaRecorder.stopRecorder(this.mRecordingStartTime, false);
            }
            this.mActivity.sendBroadcast(new Intent(BaseModule.STOP_VIDEO_RECORDING_ACTION));
            this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
            Log.v(TAG, "listen none");
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            if (isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
            }
            ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
            if (baseDelegate != null) {
                baseDelegate.getAnimationComposite().setClickEnable(true);
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
            this.mMainProtocol.setEvAdjustable(true ^ CameraSettings.isSuperNightOn());
            restoreOuterAudio();
            keepScreenOnAwhile();
            AutoLockManager.getInstance(this.mActivity).hibernateDelayed();
        }
    }

    public /* synthetic */ void td() {
        ModeProtocol.TopAlert topAlert = this.mTopAlert;
        if (topAlert != null && topAlert.canProvide()) {
            this.mTopAlert.updateConfigItem(194);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void trackModeCustomInfo(Map map, boolean z, BeautyValues beautyValues, int i, boolean z2) {
        if (map == null) {
            map = new HashMap();
        }
        int i2 = this.mModuleIndex;
        if (i2 == 167) {
            trackManualInfo(i);
        } else if (i2 == 163 || i2 == 165 || i2 == 186 || i2 == 182 || i2 == 205) {
            CameraStatUtils.trackLyingDirectPictureTaken(map, this.mIsShowLyingDirectHintStatus);
            trackCaptureModuleInfo(map, i, z, z2);
            trackBeautyInfo(i, isFrontCamera(), beautyValues);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void tryRemoveCountDownMessage() {
        if (isInCountDown()) {
            this.mCameraTimer.dispose();
            this.mCameraTimer = null;
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass11 */

                public void run() {
                    Log.d(Camera2Module.TAG, "run: hide delay number in main thread");
                    Camera2Module.this.mTopAlert.hideDelayNumber();
                }
            });
        }
    }

    public /* synthetic */ void ud() {
        this.mFocusManager.cancelFocus();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void unRegisterProtocol() {
        Camera camera;
        super.unRegisterProtocol();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unRegisterProtocol: mIsGoogleLensAvailable = ");
        sb.append(this.mIsGoogleLensAvailable);
        sb.append(", activity is null ? ");
        sb.append(this.mActivity == null);
        Log.d(str, sb.toString());
        if (this.mIsGoogleLensAvailable && (camera = this.mActivity) != null) {
            camera.runOnUiThread(new RunnableC0094s(this));
        }
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(195, this);
        getActivity().getImplFactory().detachAdditional();
    }

    public void updateBacklight() {
        if (!isDoingAction() && isAlive()) {
            this.isSilhouette = false;
            applyBacklightEffect();
            resumePreviewInWorkThread();
        }
    }

    public void updateDoDepurple() {
        List asList = Arrays.asList(DataRepository.dataItemFeature().Wh().toUpperCase(Locale.ENGLISH).split(":"));
        boolean z = (DataRepository.dataItemFeature().nj() && getZoomRatio() >= HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR && getZoomRatio() < HybridZoomingSystem.getTeleMinZoomRatio() && getActualCameraId() == Camera2DataContainer.getInstance().getSATCameraId()) || (asList.contains("MACRO") && Camera2DataContainer.getInstance().getStandaloneMacroCameraId() == getActualCameraId()) || ((asList.contains("TELE") && Camera2DataContainer.getInstance().getAuxCameraId() == getActualCameraId()) || ((asList.contains("ULTRA_TELE") && Camera2DataContainer.getInstance().getUltraTeleCameraId() == getActualCameraId()) || ((asList.contains("WIDE") && Camera2DataContainer.getInstance().getMainBackCameraId() == getActualCameraId()) || ((asList.contains("ULTRA_WIDE") && Camera2DataContainer.getInstance().getUltraWideCameraId() == getActualCameraId()) || (asList.contains("SAT") && Camera2DataContainer.getInstance().getSATCameraId() == getActualCameraId())))));
        if (!this.mCamera2Device.getCameraConfigs().isHDREnabled()) {
            z = true;
        }
        if (this.mCamera2Device.isShotQueueMultitasking() || this.mCamera2Device.getCapabilities().isSensorDepurpleDisable()) {
            z = false;
        }
        this.mCamera2Device.setDodepurpleEnabled(z);
    }

    /* access modifiers changed from: protected */
    public void updateFaceView(boolean z, boolean z2) {
        if (this.mHandler.hasMessages(35)) {
            this.mHandler.removeMessages(35);
        }
        this.mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    @Override // com.android.camera.module.BaseModule
    public void updateFlashPreference() {
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex);
        String requestFlashMode = getRequestFlashMode();
        if (Util.parseInt(requestFlashMode, 0) != 0) {
            resetAiSceneInHdrOrFlashOn();
        }
        setFlashMode(requestFlashMode);
        if (!TextUtils.equals(componentValue, this.mLastFlashMode) && (Util.parseInt(componentValue, 0) == 103 || Util.parseInt(componentValue, 0) == 0)) {
            resetAsdSceneInHdrOrFlashChange();
        }
        this.mLastFlashMode = componentValue;
        stopObjectTracking(true);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            if (requestFlashMode.equals("200")) {
                topAlert.disableMenuItem(false, 193);
            } else {
                topAlert.enableMenuItem(false, 193);
            }
        }
        if ("3".equals(requestFlashMode)) {
            setCurrentAsdScene(-1);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updateHDRPreference() {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty()) {
            String componentValue = componentHdr.getComponentValue(this.mModuleIndex);
            if ((!HybridZoomingSystem.isZoomRatioNone(getZoomRatio(), isFrontCamera()) || this.mMotionDetected) && this.mMutexModePicker.isHdr() && "auto".equals(componentValue)) {
                onHdrSceneChanged(false);
            }
            boolean isHdrOnWithChecker = componentHdr.isHdrOnWithChecker(componentValue);
            if (this.mIsMoonMode || this.mMotionDetected) {
                updateHDR("off");
            } else if (isTriggerFlashHDR()) {
                this.mIsNeedNightHDR = true;
                updateHDR("normal");
                String str = TAG;
                Log.d(str, "flash auto into hdr mode,mIsNeedNightHDR:" + this.mIsNeedNightHDR);
            } else if (isHdrOnWithChecker) {
                updateHDR("auto");
            } else {
                updateHDR(componentValue);
            }
            if ((!"off".equals(componentValue) || this.mAiSceneEnabled) && ((getZoomRatio() <= HybridZoomingSystem.getZoomRatioNone(isFrontCamera(), this.mOrientation) || "normal".equals(componentValue) || DataRepository.dataItemFeature().El()) && !this.mIsMoonMode && ((!c.Zg || !isStandaloneMacroCamera() || !"auto".equals(componentValue)) && !CameraSettings.isSuperNightOn()))) {
                resetAiSceneInHdrOrFlashOn();
                resetAsdSceneInHdrOrFlashChange();
                if (isHdrOnWithChecker || "auto".equals(componentValue)) {
                    this.mHdrCheckEnabled = true;
                    if (DataRepository.dataItemFeature().Cl()) {
                        this.mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(true);
                    }
                } else {
                    this.mHdrCheckEnabled = false;
                    if (DataRepository.dataItemFeature().Cl()) {
                        this.mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(false);
                    }
                }
                this.mCamera2Device.setHDRCheckerEnable(true);
            } else {
                this.mCamera2Device.setHDRCheckerEnable(false);
                this.mHdrCheckEnabled = false;
                if (DataRepository.dataItemFeature().Cl()) {
                    this.mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(false);
                }
            }
            this.mCamera2Device.setHDRCheckerStatus(ComponentConfigHdr.getHdrUIStatus(componentValue));
            this.mCamera2Device.setHDRMode(ComponentConfigHdr.getHdrUIStatus(componentValue));
        }
    }

    public void updateHDRTip(boolean z) {
        ModeProtocol.TopAlert topAlert;
        ModeProtocol.TopAlert topAlert2;
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        boolean z2 = !componentHdr.isEmpty() && componentHdr.isHdrOnWithChecker(componentHdr.getComponentValue(this.mModuleIndex));
        if (triggerHDR(z) && !z2) {
            ModeProtocol.MagneticSensorDetect magneticSensorDetect = this.mMagneticSensorDetect;
            if (magneticSensorDetect != null && magneticSensorDetect.isLockHDRChecker("updateHDRTip")) {
                if (this.mCamera2Device.getCameraConfigs().isHDREnabled() && (topAlert2 = this.mTopAlert) != null && !topAlert2.isHDRShowing()) {
                    z = true;
                } else if (!this.mCamera2Device.getCameraConfigs().isHDREnabled() && (topAlert = this.mTopAlert) != null && topAlert.isHDRShowing()) {
                    z = false;
                } else {
                    return;
                }
            }
            if (this.mAutoHDRTargetState != z) {
                this.mAutoHDRTargetState = z;
                if (isAlive()) {
                    this.mHandler.post(new RunnableC0073e(this));
                }
            }
        }
    }

    public void updateManualEvAdjust() {
        if (this.mModuleIndex == 167) {
            String manualValue = getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default));
            String manualValue2 = getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default));
            String str = TAG;
            Log.d(str, "MODE_MANUAL: exposureTime = " + manualValue + "iso = " + manualValue2);
            boolean equals = c.mo() ? getString(R.string.pref_camera_exposuretime_default).equals(manualValue) : getString(R.string.pref_camera_iso_default).equals(manualValue2) || getString(R.string.pref_camera_exposuretime_default).equals(manualValue);
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new RunnableC0096u(this, equals));
            }
            if (1 == this.mCamera2Device.getFocusMode() && this.m3ALocked) {
                Camera camera = this.mActivity;
                if (camera != null) {
                    camera.runOnUiThread(new RunnableC0089m(this));
                }
                unlockAEAF();
            }
        }
    }

    public void updateMoon(boolean z) {
        if (z) {
            this.mIsMoonMode = true;
            if (!DataRepository.dataItemFeature().dk()) {
                this.mCamera2Device.setSuperResolution(false);
            }
            updateFocusMode();
            updateHDRPreference();
            this.mCurrentAiScene = 35;
            this.mCamera2Device.setASDScene(35);
            resumePreviewInWorkThread();
            if (this.mZoomSupported) {
                setMaxZoomRatio(Math.max(20.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                String str = TAG;
                Log.d(str, "updateMoon(): Override zoom ratio range to: [" + getMinZoomRatio() + "," + getMaxZoomRatio() + "]");
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.clearFocusView(1);
            }
        } else if (this.mIsMoonMode) {
            this.mIsMoonMode = false;
            setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
            updateHDRPreference();
            this.mCamera2Device.setASDScene(-35);
            initializeZoomRangeFromCapabilities();
            String str2 = TAG;
            Log.d(str2, "updateMoon(): Restore zoom ratio range to: [" + getMinZoomRatio() + "," + getMaxZoomRatio() + "]");
            if (getZoomRatio() > getMaxZoomRatio()) {
                onZoomingActionUpdate(getMaxZoomRatio(), -1);
            }
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.hideAllPanel();
        }
        String str3 = TAG;
        Log.d(str3, "(moon_mode) updateMoon,status:" + z);
    }

    public void updateMoonNight() {
        this.mIsMoonMode = false;
        closeMoonMode(10, 0);
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement("e", 193);
        setFlashMode("0");
        updateMfnr(true);
        updateOIS();
        setAiSceneEffect(10);
        this.mCurrentAiScene = 10;
        resumePreviewInWorkThread();
        Log.d(TAG, "(moon_mode) updateMoonNight");
    }

    public void updateNearRangeMode(boolean z, boolean z2) {
        this.mCamera2Device.sendSatFallbackDisableRequest(z, z2);
    }

    public void updateOnTripMode() {
        MarshalQueryableASDScene.ASDScene[] aSDSceneArr;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null && (aSDSceneArr = this.mAsdScenes) != null) {
            camera2Proxy.setOnTripodModeStatus(aSDSceneArr);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updatePreviewSurface() {
        ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.initEffectCropView();
        }
        checkDisplayOrientation();
        if (this.mActivity != null) {
            CameraSize cameraSize = this.mPreviewSize;
            if (cameraSize != null) {
                updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
            }
            if (this.mCamera2Device != null) {
                SurfaceTexture surfaceTexture = this.mActivity.getCameraScreenNail().getSurfaceTexture();
                String str = TAG;
                Log.d(str, "updatePreviewSurface: surfaceTexture = " + surfaceTexture);
                if (surfaceTexture != null) {
                    this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
                }
                this.mCamera2Device.updateDeferPreviewSession(new Surface(surfaceTexture));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateRecordingTime() {
        if (this.mMediaRecorderRecording) {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            this.mCountDownTimer = new CountDownTimer((long) (this.mMaxVideoDurationInMs + this.mRecordingStartDelay), 1000) {
                /* class com.android.camera.module.Camera2Module.AnonymousClass25 */

                public void onFinish() {
                    Camera2Module.this.stopVideoRecording(true, false);
                }

                public void onTick(long j) {
                    String millisecondToTimeString = Util.millisecondToTimeString((j + 950) - 450, false);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        topAlert.updateRecordingTime(millisecondToTimeString);
                    }
                }
            };
            this.mCountDownTimer.start();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updateSATZooming(boolean z) {
        Camera2Proxy camera2Proxy;
        if (DataRepository.dataItemFeature().Xi() && HybridZoomingSystem.IS_3_OR_MORE_SAT && (camera2Proxy = this.mCamera2Device) != null) {
            camera2Proxy.setSatIsZooming(z);
            resumePreviewInWorkThread();
        }
    }

    public void updateSilhouette() {
        if (!isDoingAction() && isAlive()) {
            this.isSilhouette = true;
            trackAISceneChanged(this.mModuleIndex, 24);
            setAiSceneEffect(24);
            updateHDR("off");
            this.mCamera2Device.setASDScene(24);
            resumePreviewInWorkThread();
        }
    }
}
