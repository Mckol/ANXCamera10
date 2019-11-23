.class public Lcom/android/camera/module/Camera2Module;
.super Lcom/android/camera/module/BaseModule;
.source "Camera2Module.java"

# interfaces
.implements Lcom/android/camera/module/loader/camera2/FocusManager2$Listener;
.implements Lcom/android/camera/protocol/ModeProtocol$CameraAction;
.implements Lcom/android/camera/protocol/ModeProtocol$CameraModuleSpecial;
.implements Lcom/android/camera/protocol/ModeProtocol$FilterProtocol;
.implements Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;
.implements Lcom/android/camera/ui/ObjectView$ObjectViewListener;
.implements Lcom/android/camera2/Camera2Proxy$BeautyBodySlimCountCallback;
.implements Lcom/android/camera2/Camera2Proxy$CameraMetaDataCallback;
.implements Lcom/android/camera2/Camera2Proxy$CameraPreviewCallback;
.implements Lcom/android/camera2/Camera2Proxy$FaceDetectionCallback;
.implements Lcom/android/camera2/Camera2Proxy$FocusCallback;
.implements Lcom/android/camera2/Camera2Proxy$HdrCheckerCallback;
.implements Lcom/android/camera2/Camera2Proxy$LivePhotoResultCallback;
.implements Lcom/android/camera2/Camera2Proxy$PictureCallback;
.implements Lcom/android/camera2/Camera2Proxy$ScreenLightCallback;
.implements Lcom/android/camera2/Camera2Proxy$SuperNightCallback;
.implements Lcom/android/camera2/Camera2Proxy$UltraWideCheckCallback;


# annotations
.annotation build Landroid/annotation/TargetApi;
    value = 0x15
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/module/Camera2Module$AsdSceneConsumer;,
        Lcom/android/camera/module/Camera2Module$MainHandler;,
        Lcom/android/camera/module/Camera2Module$JpegQuickPictureCallback;,
        Lcom/android/camera/module/Camera2Module$JpegRepeatingCaptureCallback;,
        Lcom/android/camera/module/Camera2Module$SuperNightEventConsumer;
    }
.end annotation


# static fields
.field private static final BURST_SHOOTING_DELAY:I = 0x0

.field private static final CAPTURE_DURATION_THRESHOLD:J = 0x2ee0L

.field private static final MOON_AF_DISTANCE:F = 0.5f

.field private static final REQUEST_CROP:I = 0x3e8

.field private static final TAG:Ljava/lang/String;

.field private static mIsBeautyFrontOn:Z = false

.field private static final sTempCropFilename:Ljava/lang/String; = "crop-temp"


# instance fields
.field private curGyroscope:[F

.field private volatile isDetectedInHdr:Z

.field private volatile isResetFromMutex:Z

.field private isSilhouette:Z

.field private lastGyroscope:[F

.field private m3ALocked:Z

.field private mAFEndLogTimes:I

.field private mAiSceneDisposable:Lio/reactivex/disposables/Disposable;

.field private mAiSceneEnabled:Z

.field private mAiSceneFlowableEmitter:Lio/reactivex/FlowableEmitter;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lio/reactivex/FlowableEmitter<",
            "Landroid/hardware/camera2/CaptureResult;",
            ">;"
        }
    .end annotation
.end field

.field private mAlgorithmName:Ljava/lang/String;

.field private mApertures:[F

.field private mAsdScenes:[Lcom/android/camera2/vendortag/struct/MarshalQueryableASDScene$ASDScene;

.field private mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

.field private mBlockQuickShot:Z

.field private mBroadcastIntent:Landroid/content/Intent;

.field private mBurstDisposable:Lio/reactivex/disposables/Disposable;

.field private mBurstEmitter:Lio/reactivex/ObservableEmitter;

.field private mBurstNextDelayTime:J

.field private mBurstStartTime:J

.field private mCameraClickObserverAction:Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable$ClickObserver;

.field private final mCameraDeviceLock:Ljava/lang/Object;

.field private mCaptureStartTime:J

.field private mCaptureWaterMarkStr:Ljava/lang/String;

.field private mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

.field private final mCircularMediaRecorderStateLock:Ljava/lang/Object;

.field private mConfigRawStream:Z

.field private mCountdownDisposable:Lio/reactivex/disposables/Disposable;

.field private mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

.field private mCropValue:Ljava/lang/String;

.field private mCurrentAiScene:I

.field private mCurrentAsdScene:I

.field private mCurrentDetectedScene:I

.field private mEnableParallelSession:Z

.field private mEnableShot2Gallery:Z

.field private mEnabledPreviewThumbnail:Z

.field private mEnteringMoonMode:Z

.field protected mFaceDetected:Z

.field private mFaceDetectionEnabled:Z

.field private mFaceDetectionStarted:Z

.field private mFaceInfo:Lcom/android/camera/effect/FaceAnalyzeInfo;

.field private mFilterId:I

.field private mFocalLengths:[F

.field private mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

.field private mFunctionParseAiScene:Lcom/android/camera/module/loader/FunctionParseAiScene;

.field private mHasAiSceneFilterEffect:Z

.field private mHdrCheckEnabled:Z

.field private mIsAiConflict:Z

.field private mIsBeautyBodySlimOn:Z

.field private mIsCurrentLensEnabled:Z

.field private mIsFaceConflict:Z

.field private mIsGenderAgeOn:Z

.field private volatile mIsGoogleLensAvailable:Z

.field private mIsGradienterOn:Z

.field private mIsImageCaptureIntent:Z

.field private mIsLensServiceBound:Z

.field private mIsMagicMirrorOn:Z

.field private mIsMicrophoneEnabled:Z

.field private mIsMoonMode:Z

.field private mIsPortraitLightingOn:Z

.field private mIsSaveCaptureImage:Z

.field private mIsShowLyingDirectHintStatus:I

.field private mIsUltraWideConflict:Z

.field private mJpegRotation:I

.field private mKeepBitmapTexture:Z

.field private mLastAsdSceneShowTime:J

.field private mLastCaptureTime:J

.field private mLastChangeSceneTime:J

.field private mLastFlashMode:Ljava/lang/String;

.field private mLensApi:Lcom/google/lens/sdk/LensApi;

.field private mLensStatus:I

.field private mLivePhotoQueue:Ljava/util/Queue;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Queue<",
            "Lcom/xiaomi/camera/liveshot/LivePhotoResult;",
            ">;"
        }
    .end annotation
.end field

.field private mLiveShotEnabled:Z

.field private mLocation:Landroid/location/Location;

.field private mLongPressedAutoFocus:Z

.field private mMetaDataDisposable:Lio/reactivex/disposables/Disposable;

.field private mMetaDataFlowableEmitter:Lio/reactivex/FlowableEmitter;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lio/reactivex/FlowableEmitter<",
            "Landroid/hardware/camera2/CaptureResult;",
            ">;"
        }
    .end annotation
.end field

.field private mMotionDetected:Z

.field private mMultiSnapStatus:Z

.field private mMultiSnapStopRequest:Z

.field private mNeedAutoFocus:Z

.field private mOnResumeTime:J

.field private mOperatingMode:I

.field private mPendingMultiCapture:Z

.field private mQuickCapture:Z

.field private mQuickShotAnimateEnable:Z

.field private mReceivedJpegCallbackNum:I

.field private mSaveUri:Landroid/net/Uri;

.field private mSceneMode:Ljava/lang/String;

.field private mSensorStateListener:Lcom/android/camera/SensorStateManager$SensorStateListener;

.field private mServiceStatusListener:Lcom/android/camera/LocalParallelService$ServiceStatusListener;

.field private mSessionStatusCallbackListener:Lcom/xiaomi/protocol/ISessionStatusCallBackListener;

.field private mShootOrientation:I

.field private mShootRotation:F

.field private mShouldDoMFNR:Z

.field private mShowSuperNightHint:Z

.field private mShutterCallbackTime:J

.field private mShutterLag:J

.field private mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

.field private mSuperNightEventConsumer:Lio/reactivex/functions/Consumer;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lio/reactivex/functions/Consumer<",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private mTotalJpegCallbackNum:I

.field private volatile mUltraWideAELocked:Z

.field private mUpdateImageTitle:Z

.field private mVideoSize:Lcom/android/camera/CameraSize;

.field private mVolumeLongPress:Z

.field private volatile mWaitingSnapshot:Z

.field private mWaitingSuperNightResult:Z


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-class v0, Lcom/android/camera/module/Camera2Module;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 6

    invoke-direct {p0}, Lcom/android/camera/module/BaseModule;-><init>()V

    new-instance v0, Ljava/util/concurrent/LinkedBlockingQueue;

    const/16 v1, 0x78

    invoke-direct {v0, v1}, Ljava/util/concurrent/LinkedBlockingQueue;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLivePhotoQueue:Ljava/util/Queue;

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStopRequest:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mVolumeLongPress:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mUpdateImageTitle:Z

    const-wide/16 v1, 0x0

    iput-wide v1, p0, Lcom/android/camera/module/Camera2Module;->mBurstNextDelayTime:J

    const/4 v3, 0x1

    iput v3, p0, Lcom/android/camera/module/Camera2Module;->mTotalJpegCallbackNum:I

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    const/4 v4, -0x1

    iput v4, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    iput-wide v1, p0, Lcom/android/camera/module/Camera2Module;->mLastAsdSceneShowTime:J

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsLensServiceBound:Z

    iput v3, p0, Lcom/android/camera/module/Camera2Module;->mLensStatus:I

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraQuickShotEnable()Z

    move-result v5

    xor-int/2addr v5, v3

    iput-boolean v5, p0, Lcom/android/camera/module/Camera2Module;->mBlockQuickShot:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mQuickShotAnimateEnable:Z

    new-instance v5, Ljava/lang/Object;

    invoke-direct {v5}, Ljava/lang/Object;-><init>()V

    iput-object v5, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorderStateLock:Ljava/lang/Object;

    const/4 v5, 0x0

    iput-object v5, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    iput-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mIsMicrophoneEnabled:Z

    new-instance v3, Ljava/lang/Object;

    invoke-direct {v3}, Ljava/lang/Object;-><init>()V

    iput-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCameraDeviceLock:Ljava/lang/Object;

    iput v4, p0, Lcom/android/camera/module/Camera2Module;->mIsShowLyingDirectHintStatus:I

    new-instance v3, Lcom/android/camera/module/Camera2Module$1;

    invoke-direct {v3, p0}, Lcom/android/camera/module/Camera2Module$1;-><init>(Lcom/android/camera/module/Camera2Module;)V

    iput-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCameraClickObserverAction:Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable$ClickObserver;

    new-instance v3, Lcom/android/camera/module/Camera2Module$13;

    invoke-direct {v3, p0}, Lcom/android/camera/module/Camera2Module$13;-><init>(Lcom/android/camera/module/Camera2Module;)V

    iput-object v3, p0, Lcom/android/camera/module/Camera2Module;->mSensorStateListener:Lcom/android/camera/SensorStateManager$SensorStateListener;

    iput-wide v1, p0, Lcom/android/camera/module/Camera2Module;->mLastChangeSceneTime:J

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isResetFromMutex:Z

    new-instance v0, Lcom/android/camera/module/Camera2Module$22;

    invoke-direct {v0, p0}, Lcom/android/camera/module/Camera2Module$22;-><init>(Lcom/android/camera/module/Camera2Module;)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSessionStatusCallbackListener:Lcom/xiaomi/protocol/ISessionStatusCallBackListener;

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/module/Camera2Module;)Lio/reactivex/ObservableEmitter;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/Camera2Module;->mBurstEmitter:Lio/reactivex/ObservableEmitter;

    return-object p0
.end method

.method static synthetic access$002(Lcom/android/camera/module/Camera2Module;Lio/reactivex/ObservableEmitter;)Lio/reactivex/ObservableEmitter;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mBurstEmitter:Lio/reactivex/ObservableEmitter;

    return-object p1
.end method

.method static synthetic access$1000(Lcom/android/camera/module/Camera2Module;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/module/Camera2Module;->mTotalJpegCallbackNum:I

    return p0
.end method

.method static synthetic access$102(Lcom/android/camera/module/Camera2Module;J)J
    .locals 0

    iput-wide p1, p0, Lcom/android/camera/module/Camera2Module;->mBurstStartTime:J

    return-wide p1
.end method

.method static synthetic access$1100(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    return p0
.end method

.method static synthetic access$1200(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStopRequest:Z

    return p0
.end method

.method static synthetic access$1300(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->is3ALocked()Z

    move-result p0

    return p0
.end method

.method static synthetic access$1400(Lcom/android/camera/module/Camera2Module;)Lcom/android/camera/module/loader/camera2/FocusManager2;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    return-object p0
.end method

.method static synthetic access$1500(Lcom/android/camera/module/Camera2Module;)Lcom/android/camera/effect/renders/DeviceWatermarkParam;
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getDeviceWaterMarkParam()Lcom/android/camera/effect/renders/DeviceWatermarkParam;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$1600()Ljava/lang/String;
    .locals 1

    invoke-static {}, Lcom/android/camera/module/Camera2Module;->getTiltShiftMode()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1700(Lcom/android/camera/module/Camera2Module;)Ljava/lang/String;
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getSuffix()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$1800(Lcom/android/camera/module/Camera2Module;)Lcom/xiaomi/camera/core/PictureInfo;
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getPictureInfo()Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$1900(Lcom/android/camera/module/Camera2Module;)Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/Camera2Module;->mAlgorithmName:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$2000(Lcom/android/camera/module/Camera2Module;)Landroid/location/Location;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/Camera2Module;->mLocation:Landroid/location/Location;

    return-object p0
.end method

.method static synthetic access$202(Lcom/android/camera/module/Camera2Module;Lio/reactivex/disposables/Disposable;)Lio/reactivex/disposables/Disposable;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mBurstDisposable:Lio/reactivex/disposables/Disposable;

    return-object p1
.end method

.method static synthetic access$2100(Lcom/android/camera/module/Camera2Module;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/module/Camera2Module;->mShootOrientation:I

    return p0
.end method

.method static synthetic access$2200(Lcom/android/camera/module/Camera2Module;)F
    .locals 0

    iget p0, p0, Lcom/android/camera/module/Camera2Module;->mShootRotation:F

    return p0
.end method

.method static synthetic access$2300(Lcom/android/camera/module/Camera2Module;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/module/Camera2Module;->mJpegRotation:I

    return p0
.end method

.method static synthetic access$2400(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isFrontMirror()Z

    move-result p0

    return p0
.end method

.method static synthetic access$2500(Lcom/android/camera/module/Camera2Module;Lcom/xiaomi/camera/core/ParallelTaskData;Z)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->beginParallelProcess(Lcom/xiaomi/camera/core/ParallelTaskData;Z)V

    return-void
.end method

.method static synthetic access$2600(Lcom/android/camera/module/Camera2Module;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->stopMultiSnap()V

    return-void
.end method

.method static synthetic access$2700(Lcom/android/camera/module/Camera2Module;)Ljava/lang/String;
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getCurrentAiSceneName()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$2800(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mEnteringMoonMode:Z

    return p0
.end method

.method static synthetic access$2900(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    return p0
.end method

.method static synthetic access$300()Ljava/lang/String;
    .locals 1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$3000(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mShowSuperNightHint:Z

    return p0
.end method

.method static synthetic access$3100(Lcom/android/camera/module/Camera2Module;Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->onBurstPictureTakenFinished(Z)V

    return-void
.end method

.method static synthetic access$3202(Lcom/android/camera/module/Camera2Module;Lio/reactivex/disposables/Disposable;)Lio/reactivex/disposables/Disposable;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCountdownDisposable:Lio/reactivex/disposables/Disposable;

    return-object p1
.end method

.method static synthetic access$3300(Lcom/android/camera/module/Camera2Module;I)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->startNormalCapture(I)V

    return-void
.end method

.method static synthetic access$3400(Lcom/android/camera/module/Camera2Module;)J
    .locals 2

    iget-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mOnResumeTime:J

    return-wide v0
.end method

.method static synthetic access$3500(Lcom/android/camera/module/Camera2Module;II)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->setOrientation(II)V

    return-void
.end method

.method static synthetic access$3600(Lcom/android/camera/module/Camera2Module;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->setOrientationParameter()V

    return-void
.end method

.method static synthetic access$3700(Lcom/android/camera/module/Camera2Module;ZZ)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->handleUpdateFaceView(ZZ)V

    return-void
.end method

.method static synthetic access$3800(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    return p0
.end method

.method static synthetic access$3802(Lcom/android/camera/module/Camera2Module;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    return p1
.end method

.method static synthetic access$3900(Lcom/android/camera/module/Camera2Module;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/module/Camera2Module;->mLensStatus:I

    return p0
.end method

.method static synthetic access$3902(Lcom/android/camera/module/Camera2Module;I)I
    .locals 0

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mLensStatus:I

    return p1
.end method

.method static synthetic access$4000(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    return p0
.end method

.method static synthetic access$402(Lcom/android/camera/module/Camera2Module;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSuperNightResult:Z

    return p1
.end method

.method static synthetic access$4100(Lcom/android/camera/module/Camera2Module;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSceneModeUI()V

    return-void
.end method

.method static synthetic access$4200(Lcom/android/camera/module/Camera2Module;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/module/Camera2Module;->mIsShowLyingDirectHintStatus:I

    return p0
.end method

.method static synthetic access$4202(Lcom/android/camera/module/Camera2Module;I)I
    .locals 0

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mIsShowLyingDirectHintStatus:I

    return p1
.end method

.method static synthetic access$4302(Lcom/android/camera/module/Camera2Module;[F)[F
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->lastGyroscope:[F

    return-object p1
.end method

.method static synthetic access$4400(Lcom/android/camera/module/Camera2Module;)[F
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/Camera2Module;->curGyroscope:[F

    return-object p0
.end method

.method static synthetic access$4402(Lcom/android/camera/module/Camera2Module;[F)[F
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->curGyroscope:[F

    return-object p1
.end method

.method static synthetic access$4500(Lcom/android/camera/module/Camera2Module;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->startLensActivity()V

    return-void
.end method

.method static synthetic access$4602(Lcom/android/camera/module/Camera2Module;Lio/reactivex/FlowableEmitter;)Lio/reactivex/FlowableEmitter;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataFlowableEmitter:Lio/reactivex/FlowableEmitter;

    return-object p1
.end method

.method static synthetic access$4700(Lcom/android/camera/module/Camera2Module;I)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->consumeAsdSceneResult(I)V

    return-void
.end method

.method static synthetic access$4800(Lcom/android/camera/module/Camera2Module;IZ)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->consumeAiSceneResult(IZ)V

    return-void
.end method

.method static synthetic access$4902(Lcom/android/camera/module/Camera2Module;Lio/reactivex/FlowableEmitter;)Lio/reactivex/FlowableEmitter;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneFlowableEmitter:Lio/reactivex/FlowableEmitter;

    return-object p1
.end method

.method static synthetic access$500(Lcom/android/camera/module/Camera2Module;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->animateCapture()V

    return-void
.end method

.method static synthetic access$5002(Lcom/android/camera/module/Camera2Module;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->isResetFromMutex:Z

    return p1
.end method

.method static synthetic access$700(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mUpdateImageTitle:Z

    return p0
.end method

.method static synthetic access$800(Lcom/android/camera/module/Camera2Module;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    return p0
.end method

.method static synthetic access$804(Lcom/android/camera/module/Camera2Module;)I
    .locals 1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    add-int/lit8 v0, v0, 0x1

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    return v0
.end method

.method static synthetic access$900(Lcom/android/camera/module/Camera2Module;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    return p0
.end method

.method private animateCapture()V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraRotation()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera/CameraScreenNail;->animateCapture(I)V

    :cond_0
    return-void
.end method

.method private applyBacklightEffect()V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0x17

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->trackAISceneChanged(II)V

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->setAiSceneEffect(I)V

    const-string v0, "normal"

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->updateHDR(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setASDScene(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->resetEvValue()V

    return-void
.end method

.method private beginParallelProcess(Lcom/xiaomi/camera/core/ParallelTaskData;Z)V
    .locals 2

    sget-object p2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "algo begin: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/xiaomi/camera/core/ParallelTaskData;->getSavePath()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, " | "

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mServiceStatusListener:Lcom/android/camera/LocalParallelService$ServiceStatusListener;

    if-nez p1, :cond_0

    new-instance p1, Lcom/android/camera/module/Camera2Module$11;

    invoke-direct {p1, p0}, Lcom/android/camera/module/Camera2Module$11;-><init>(Lcom/android/camera/module/Camera2Module;)V

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mServiceStatusListener:Lcom/android/camera/LocalParallelService$ServiceStatusListener;

    invoke-static {}, Lcom/android/camera/parallel/AlgoConnector;->getInstance()Lcom/android/camera/parallel/AlgoConnector;

    move-result-object p1

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mServiceStatusListener:Lcom/android/camera/LocalParallelService$ServiceStatusListener;

    invoke-virtual {p1, p2}, Lcom/android/camera/parallel/AlgoConnector;->setServiceStatusListener(Lcom/android/camera/LocalParallelService$ServiceStatusListener;)V

    :cond_0
    return-void
.end method

.method private calculateTimeout(I)J
    .locals 6

    const-wide/16 v0, 0x2ee0

    const/16 v2, 0xa7

    if-ne p1, v2, :cond_0

    const-string p1, "pref_qc_camera_exposuretime_key"

    const v2, 0x7f090142

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, p1, v2}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v2

    const-wide/32 v4, 0xf4240

    div-long/2addr v2, v4

    add-long/2addr v2, v0

    return-wide v2

    :cond_0
    const/16 v2, 0xad

    if-eq p1, v2, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result p1

    if-eqz p1, :cond_1

    goto :goto_0

    :cond_1
    goto :goto_1

    :cond_2
    :goto_0
    const-wide/16 v0, 0x5dc0

    :goto_1
    return-wide v0
.end method

.method private checkMoreFrameCaptureLockAFAE(Z)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-nez v0, :cond_0

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "mCamera2Device == null, return"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hM()Z

    move-result v0

    if-nez v0, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isSuperNightScene()Z

    move-result v0

    if-nez v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mShowSuperNightHint:Z

    if-nez v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isHdr()Z

    move-result v0

    if-nez v0, :cond_2

    return-void

    :cond_2
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->is3ALocked()Z

    move-result v0

    if-eqz v0, :cond_3

    return-void

    :cond_3
    if-eqz p1, :cond_4

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "more frame capture lock af/ae"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setFocusMode(I)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v0, v0}, Lcom/android/camera2/Camera2Proxy;->lockExposure(ZZ)V

    goto :goto_0

    :cond_4
    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "more frame capture unlock af/ae"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFocusMode()V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->unlockExposure()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->resumePreview()V

    :goto_0
    return-void
.end method

.method private checkSatFallback(Landroid/hardware/camera2/CaptureResult;)V
    .locals 9

    invoke-static {p1}, Lcom/android/camera2/CaptureResultParser;->isSatFallbackDetected(Landroid/hardware/camera2/CaptureResult;)Z

    move-result v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mLastSatFallbackRequestId:I

    const/4 v2, -0x1

    const/16 v3, 0x3c

    const/4 v4, 0x1

    const/4 v5, 0x0

    if-ne v1, v2, :cond_1

    if-eqz v0, :cond_1

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    if-nez v1, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->sendSatFallbackRequest()I

    move-result p1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "checkSatFallback: lastFallbackRequestId = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-ltz p1, :cond_0

    iput-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mIsSatFallback:Z

    iput-boolean v5, p0, Lcom/android/camera/module/Camera2Module;->mFallbackProcessed:Z

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mLastSatFallbackRequestId:I

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    invoke-virtual {p1, v3}, Landroid/os/Handler;->removeMessages(I)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const-wide/16 v0, 0x5dc

    invoke-virtual {p1, v3, v0, v1}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    :cond_0
    goto :goto_0

    :cond_1
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mLastSatFallbackRequestId:I

    if-ltz v1, :cond_3

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mLastSatFallbackRequestId:I

    invoke-virtual {p1}, Landroid/hardware/camera2/CaptureResult;->getSequenceId()I

    move-result v6

    if-gt v1, v6, :cond_2

    iput-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mFallbackProcessed:Z

    :cond_2
    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "checkSatFallback: fallbackDetected = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v7, " requestId = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/hardware/camera2/CaptureResult;->getSequenceId()I

    move-result v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string/jumbo v7, "|"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/hardware/camera2/CaptureResult;->getFrameNumber()J

    move-result-wide v7

    invoke-virtual {v6, v7, v8}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mFallbackProcessed:Z

    if-eqz p1, :cond_3

    if-nez v0, :cond_3

    iput-boolean v5, p0, Lcom/android/camera/module/Camera2Module;->mIsSatFallback:Z

    iput-boolean v5, p0, Lcom/android/camera/module/Camera2Module;->mFallbackProcessed:Z

    iput v2, p0, Lcom/android/camera/module/Camera2Module;->mLastSatFallbackRequestId:I

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    invoke-virtual {p1, v3}, Landroid/os/Handler;->removeMessages(I)V

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    if-eqz p1, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result p1

    if-ne p1, v4, :cond_3

    iput-boolean v5, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v0, 0x34

    invoke-virtual {p1, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :cond_3
    :goto_0
    return-void
.end method

.method private checkShutterCondition()Z
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBlockSnap()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isIgnoreTouchEvent()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/storage/Storage;->isLowStorageAtLastPoint()Z

    move-result v0

    if-eqz v0, :cond_1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "checkShutterCondition: low storage"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->isScreenSlideOff()Z

    move-result v0

    if-eqz v0, :cond_2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "checkShutterCondition: screen is slide off"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_2
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsSatFallback:Z

    const/4 v2, 0x1

    if-eqz v0, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->shouldCheckSatFallbackState()Z

    move-result v0

    if-eqz v0, :cond_3

    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "checkShutterCondition: sat fallback"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_3
    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xab

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BackStack;

    if-eqz v0, :cond_4

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BackStack;->handleBackStackFromShutter()V

    :cond_4
    return v2

    :cond_5
    :goto_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "checkShutterCondition: blockSnap="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBlockSnap()Z

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, " ignoreTouchEvent="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isIgnoreTouchEvent()Z

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return v1
.end method

.method private configParallelSession()V
    .locals 6

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualFrontCamera()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualCamera()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehUltraWideBackCamera()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    move v0, v1

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x2

    :goto_1
    sget-object v2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "configParallelSession: inputStreamNum = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v2, Lcom/xiaomi/engine/GraphDescriptorBean;

    const v3, 0x8002

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-static {v4}, Lcom/xiaomi/camera/base/CameraDeviceUtil;->getCameraCombinationMode(I)I

    move-result v4

    invoke-direct {v2, v3, v0, v1, v4}, Lcom/xiaomi/engine/GraphDescriptorBean;-><init>(IIZI)V

    goto :goto_2

    :cond_2
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa7

    if-ne v0, v2, :cond_3

    new-instance v2, Lcom/xiaomi/engine/GraphDescriptorBean;

    const v0, 0x8003

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-static {v3}, Lcom/xiaomi/camera/base/CameraDeviceUtil;->getCameraCombinationMode(I)I

    move-result v3

    invoke-direct {v2, v0, v1, v1, v3}, Lcom/xiaomi/engine/GraphDescriptorBean;-><init>(IIZI)V

    goto :goto_2

    :cond_3
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xaf

    if-ne v0, v2, :cond_4

    new-instance v2, Lcom/xiaomi/engine/GraphDescriptorBean;

    const v0, 0x80f3

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-static {v3}, Lcom/xiaomi/camera/base/CameraDeviceUtil;->getCameraCombinationMode(I)I

    move-result v3

    invoke-direct {v2, v0, v1, v1, v3}, Lcom/xiaomi/engine/GraphDescriptorBean;-><init>(IIZI)V

    goto :goto_2

    :cond_4
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-static {v0}, Lcom/xiaomi/camera/base/CameraDeviceUtil;->getCameraCombinationMode(I)I

    move-result v0

    if-nez v0, :cond_5

    const/16 v0, 0x201

    :cond_5
    new-instance v2, Lcom/xiaomi/engine/GraphDescriptorBean;

    const/4 v3, 0x0

    invoke-direct {v2, v3, v1, v1, v0}, Lcom/xiaomi/engine/GraphDescriptorBean;-><init>(IIZI)V

    :goto_2
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "configParallelSession: pictureSize = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "configParallelSession: outputSize = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Lcom/xiaomi/engine/BufferFormat;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v3, v3, Lcom/android/camera/CameraSize;->width:I

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v4, v4, Lcom/android/camera/CameraSize;->height:I

    const/16 v5, 0x23

    invoke-direct {v0, v3, v4, v5, v2}, Lcom/xiaomi/engine/BufferFormat;-><init>(IIILcom/xiaomi/engine/GraphDescriptorBean;)V

    invoke-static {}, Lcom/android/camera/parallel/AlgoConnector;->getInstance()Lcom/android/camera/parallel/AlgoConnector;

    move-result-object v2

    invoke-virtual {v2, v1}, Lcom/android/camera/parallel/AlgoConnector;->getLocalBinder(Z)Lcom/android/camera/LocalParallelService$LocalBinder;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/LocalParallelService$LocalBinder;->configCaptureSession(Lcom/xiaomi/engine/BufferFormat;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v0

    invoke-virtual {v1, v0}, Lcom/android/camera/LocalParallelService$LocalBinder;->setImageSaver(Lcom/android/camera/storage/ImageSaver;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    iget v0, v0, Lcom/android/camera/CameraSize;->width:I

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    iget v2, v2, Lcom/android/camera/CameraSize;->height:I

    invoke-virtual {v1, v0, v2}, Lcom/android/camera/LocalParallelService$LocalBinder;->setJpegOutputSize(II)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Ljava/lang/Object;->hashCode()I

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/LocalParallelService$LocalBinder;->setCallerIdentity(I)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->isSRRequireReprocess()Z

    move-result v0

    invoke-virtual {v1, v0}, Lcom/android/camera/LocalParallelService$LocalBinder;->setSRRequireReprocess(Z)V

    return-void
.end method

.method private consumeAiSceneResult(IZ)V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->realConsumeAiSceneResult(IZ)V

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/4 p2, -0x1

    if-eq p1, p2, :cond_1

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 p2, 0x17

    if-eq p1, p2, :cond_1

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 p2, 0x18

    if-eq p1, p2, :cond_1

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 p2, 0x23

    if-eq p1, p2, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 p2, 0x0

    invoke-virtual {p1, p2}, Lcom/android/camera2/Camera2Proxy;->setASDScene(I)V

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void
.end method

.method private consumeAsdSceneResult(I)V
    .locals 6

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-wide v2, p0, Lcom/android/camera/module/Camera2Module;->mLastAsdSceneShowTime:J

    sub-long v2, v0, v2

    const-wide/16 v4, 0x1f4

    cmp-long v2, v2, v4

    if-lez v2, :cond_2

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    if-eq v2, p1, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v2

    if-nez v2, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v2

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v2}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result v2

    if-eqz v2, :cond_0

    goto :goto_0

    :cond_0
    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    invoke-direct {p0, v2}, Lcom/android/camera/module/Camera2Module;->exitAsdScene(I)V

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->enterAsdScene(I)V

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mLastAsdSceneShowTime:J

    goto :goto_1

    :cond_1
    :goto_0
    return-void

    :cond_2
    :goto_1
    return-void
.end method

.method private doAttach()V
    .locals 6

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/storage/ImageSaver;->getStoredJpegData()[B

    move-result-object v0

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsSaveCaptureImage:Z

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/storage/ImageSaver;->saveStoredData()V

    :cond_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCropValue:Ljava/lang/String;

    const/4 v2, 0x0

    if-nez v1, :cond_3

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSaveUri:Landroid/net/Uri;

    const/4 v3, -0x1

    if-eqz v1, :cond_2

    nop

    :try_start_0
    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mSaveUri:Landroid/net/Uri;

    invoke-virtual {v1, v4}, Landroid/content/ContentResolver;->openOutputStream(Landroid/net/Uri;)Ljava/io/OutputStream;

    move-result-object v1
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    :try_start_1
    invoke-virtual {v1, v0}, Ljava/io/OutputStream;->write([B)V

    invoke-virtual {v1}, Ljava/io/OutputStream;->close()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0, v3}, Lcom/android/camera/Camera;->setResult(I)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->finish()V

    invoke-static {v1}, Lcom/android/camera/Util;->closeSilently(Ljava/io/Closeable;)V

    goto :goto_1

    :catchall_0
    move-exception v0

    move-object v2, v1

    goto :goto_2

    :catch_0
    move-exception v0

    move-object v2, v1

    goto :goto_0

    :catchall_1
    move-exception v0

    goto :goto_2

    :catch_1
    move-exception v0

    :goto_0
    :try_start_2
    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v3, "Exception when doAttach: "

    invoke-static {v1, v3, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->finish()V

    invoke-static {v2}, Lcom/android/camera/Util;->closeSilently(Ljava/io/Closeable;)V

    :goto_1
    nop

    goto/16 :goto_4

    :goto_2
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->finish()V

    invoke-static {v2}, Lcom/android/camera/Util;->closeSilently(Ljava/io/Closeable;)V

    throw v0

    :cond_2
    const v1, 0xc800

    invoke-static {v0, v1}, Lcom/android/camera/Util;->makeBitmap([BI)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-static {v0}, Lcom/android/camera/Exif;->getOrientation([B)I

    move-result v0

    invoke-static {v1, v0}, Lcom/android/camera/Util;->rotate(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v2, Landroid/content/Intent;

    const-string v4, "inline-data"

    invoke-direct {v2, v4}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const-string v4, "data"

    invoke-virtual {v2, v4, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    move-result-object v0

    invoke-virtual {v1, v3, v0}, Lcom/android/camera/Camera;->setResult(ILandroid/content/Intent;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->finish()V

    goto :goto_4

    :cond_3
    nop

    nop

    const/4 v1, 0x0

    :try_start_3
    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const-string v4, "crop-temp"

    invoke-virtual {v3, v4}, Lcom/android/camera/Camera;->getFileStreamPath(Ljava/lang/String;)Ljava/io/File;

    move-result-object v3

    invoke-virtual {v3}, Ljava/io/File;->delete()Z

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const-string v5, "crop-temp"

    invoke-virtual {v4, v5, v1}, Lcom/android/camera/Camera;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;

    move-result-object v4
    :try_end_3
    .catch Ljava/io/FileNotFoundException; {:try_start_3 .. :try_end_3} :catch_5
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_4
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    :try_start_4
    invoke-virtual {v4, v0}, Ljava/io/FileOutputStream;->write([B)V

    invoke-virtual {v4}, Ljava/io/FileOutputStream;->close()V
    :try_end_4
    .catch Ljava/io/FileNotFoundException; {:try_start_4 .. :try_end_4} :catch_3
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_2
    .catchall {:try_start_4 .. :try_end_4} :catchall_2

    nop

    :try_start_5
    invoke-static {v3}, Landroid/net/Uri;->fromFile(Ljava/io/File;)Landroid/net/Uri;

    move-result-object v0
    :try_end_5
    .catch Ljava/io/FileNotFoundException; {:try_start_5 .. :try_end_5} :catch_5
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_4
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    invoke-static {v2}, Lcom/android/camera/Util;->closeSilently(Ljava/io/Closeable;)V

    nop

    new-instance v1, Landroid/os/Bundle;

    invoke-direct {v1}, Landroid/os/Bundle;-><init>()V

    const-string v2, "circle"

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCropValue:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4

    const-string v2, "circleCrop"

    const-string/jumbo v3, "true"

    invoke-virtual {v1, v2, v3}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    :cond_4
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mSaveUri:Landroid/net/Uri;

    if-eqz v2, :cond_5

    const-string v2, "output"

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mSaveUri:Landroid/net/Uri;

    invoke-virtual {v1, v2, v3}, Landroid/os/Bundle;->putParcelable(Ljava/lang/String;Landroid/os/Parcelable;)V

    goto :goto_3

    :cond_5
    const-string v2, "return-data"

    const/4 v3, 0x1

    invoke-virtual {v1, v2, v3}, Landroid/os/Bundle;->putBoolean(Ljava/lang/String;Z)V

    :goto_3
    new-instance v2, Landroid/content/Intent;

    const-string v3, "com.android.camera.action.CROP"

    invoke-direct {v2, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v0}, Landroid/content/Intent;->setData(Landroid/net/Uri;)Landroid/content/Intent;

    invoke-virtual {v2, v1}, Landroid/content/Intent;->putExtras(Landroid/os/Bundle;)Landroid/content/Intent;

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const/16 v1, 0x3e8

    invoke-virtual {v0, v2, v1}, Lcom/android/camera/Camera;->startActivityForResult(Landroid/content/Intent;I)V

    :goto_4
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/storage/ImageSaver;->releaseStoredJpegData()V

    return-void

    :catchall_2
    move-exception v0

    move-object v2, v4

    goto :goto_7

    :catch_2
    move-exception v0

    move-object v2, v4

    goto :goto_5

    :catch_3
    move-exception v0

    move-object v2, v4

    goto :goto_6

    :catchall_3
    move-exception v0

    goto :goto_7

    :catch_4
    move-exception v0

    :goto_5
    :try_start_6
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->setResult(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->finish()V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_3

    invoke-static {v2}, Lcom/android/camera/Util;->closeSilently(Ljava/io/Closeable;)V

    return-void

    :catch_5
    move-exception v0

    :goto_6
    :try_start_7
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->setResult(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->finish()V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_3

    invoke-static {v2}, Lcom/android/camera/Util;->closeSilently(Ljava/io/Closeable;)V

    return-void

    :goto_7
    invoke-static {v2}, Lcom/android/camera/Util;->closeSilently(Ljava/io/Closeable;)V

    throw v0
.end method

.method private doLaterReleaseIfNeed()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-nez v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "doLaterReleaseIfNeed: mActivity is null..."

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x32

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v1, 0x1

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isSessionReady()Z

    move-result v0

    if-eqz v0, :cond_2

    move v0, v1

    goto :goto_0

    :cond_2
    const/4 v0, 0x0

    :goto_0
    if-eqz v0, :cond_3

    sget-object v2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v3, "doLaterRelease"

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    :cond_3
    sget-object v2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v3, "doLaterRelease but session is closed"

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v2, v1, v0}, Lcom/android/camera/Camera;->releaseAll(ZZ)V

    return-void

    :cond_4
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDeparted()Z

    move-result v0

    if-eqz v0, :cond_5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "doLaterReleaseIfNeed: isDeparted..."

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_5
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$iJazxTWGeEWbboRRZZOouZNfP1Y;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$iJazxTWGeEWbboRRZZOouZNfP1Y;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isTextureExpired()Z

    move-result v0

    if-eqz v0, :cond_6

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "doLaterReleaseIfNeed: surfaceTexture expired, restartModule"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$oOvsnMVgkAj6Z-9eQJtbx5l97Hw;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$oOvsnMVgkAj6Z-9eQJtbx5l97Hw;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_6
    return-void
.end method

.method private enableFrontMFNR()Z
    .locals 4

    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/mi/config/b;->km()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->he()Z

    move-result v0

    if-eqz v0, :cond_0

    move v1, v2

    nop

    :cond_0
    return v1

    :cond_1
    invoke-static {}, Lcom/mi/config/b;->km()Z

    move-result v0

    if-eqz v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    const v3, 0x8005

    if-eq v0, v3, :cond_2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->he()Z

    move-result v0

    if-eqz v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    const v3, 0x9001

    if-eq v0, v3, :cond_2

    const-string/jumbo v0, "tucana"

    sget-object v3, Lcom/mi/config/b;->rp:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    const v3, 0x9003

    if-ne v0, v3, :cond_3

    :cond_2
    move v1, v2

    goto :goto_0

    :cond_3
    nop

    :goto_0
    return v1
.end method

.method private enablePreviewAsThumbnail()Z
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_1

    return v1

    :cond_1
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xaf

    if-ne v0, v2, :cond_2

    return v1

    :cond_2
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    const/4 v2, 0x1

    if-eqz v0, :cond_3

    return v2

    :cond_3
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsPortraitLightingOn:Z

    if-eqz v0, :cond_4

    return v1

    :cond_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v0

    if-eqz v0, :cond_5

    return v2

    :cond_5
    invoke-static {}, Lcom/android/camera/CameraSettings;->isPortraitModeBackOn()Z

    move-result v0

    if-eqz v0, :cond_6

    return v2

    :cond_6
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xa7

    if-ne v0, v3, :cond_7

    return v1

    :cond_7
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xad

    if-eq v0, v3, :cond_c

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v0

    if-eqz v0, :cond_8

    goto :goto_1

    :cond_8
    invoke-static {}, Lcom/android/camera/CameraSettings;->showGenderAge()Z

    move-result v0

    if-nez v0, :cond_b

    invoke-static {}, Lcom/android/camera/CameraSettings;->isMagicMirrorOn()Z

    move-result v0

    if-nez v0, :cond_b

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTiltShiftOn()Z

    move-result v0

    if-eqz v0, :cond_9

    goto :goto_0

    :cond_9
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_a

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isNeedPreviewThumbnail()Z

    move-result v0

    if-eqz v0, :cond_a

    move v1, v2

    nop

    :cond_a
    return v1

    :cond_b
    :goto_0
    return v1

    :cond_c
    :goto_1
    return v1
.end method

.method private enterAsdScene(I)V
    .locals 5

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/4 v1, 0x0

    if-eqz p1, :cond_2

    const/16 v2, 0x9

    if-eq p1, v2, :cond_0

    const/4 v0, 0x4

    const/4 v2, 0x6

    packed-switch p1, :pswitch_data_0

    goto/16 :goto_0

    :pswitch_0
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-nez p1, :cond_3

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->setPortraitSuccessHintVisible(I)V

    goto :goto_0

    :pswitch_1
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-nez p1, :cond_3

    const p1, 0x7f0901a3

    invoke-virtual {p0, v2, p1, v0}, Lcom/android/camera/module/Camera2Module;->updateTipMessage(III)V

    goto :goto_0

    :pswitch_2
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-nez p1, :cond_3

    const p1, 0x7f0901a5

    invoke-virtual {p0, v2, p1, v0}, Lcom/android/camera/module/Camera2Module;->updateTipMessage(III)V

    goto :goto_0

    :pswitch_3
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-nez p1, :cond_3

    const p1, 0x7f0901a4

    invoke-virtual {p0, v2, p1, v0}, Lcom/android/camera/module/Camera2Module;->updateTipMessage(III)V

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object p1

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {p1, v2}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object p1

    const-string v2, "3"

    invoke-virtual {v2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    const/16 v3, 0xa

    const/4 v4, 0x1

    if-eqz v2, :cond_1

    const-string p1, "1"

    invoke-interface {v0, v1, p1, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertFlash(ILjava/lang/String;Z)V

    new-array p1, v4, [I

    aput v3, p1, v1

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    goto :goto_0

    :cond_1
    const-string v2, "103"

    invoke-virtual {v2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    const-string p1, "1"

    invoke-interface {v0, v1, p1, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertFlash(ILjava/lang/String;Z)V

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "enterAsdScene(): turn off HDR as FLASH has higher priority than HDR"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->onHdrSceneChanged(Z)V

    new-array p1, v4, [I

    aput v3, p1, v1

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    goto :goto_0

    :cond_2
    const-string p1, "1"

    invoke-interface {v0, v1, p1, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertFlash(ILjava/lang/String;Z)V

    nop

    :cond_3
    :goto_0
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private exitAsdScene(I)V
    .locals 4

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/16 v1, 0x8

    const/4 v2, 0x0

    if-eqz p1, :cond_3

    const/16 v3, 0x9

    if-eq p1, v3, :cond_0

    packed-switch p1, :pswitch_data_0

    goto/16 :goto_0

    :pswitch_0
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-nez p1, :cond_4

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->setPortraitSuccessHintVisible(I)V

    goto :goto_0

    :pswitch_1
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-nez p1, :cond_4

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->hideTipMessage(I)V

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object p1

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {p1, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object p1

    const-string v3, "3"

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_1

    const-string v3, "103"

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_2

    :cond_1
    const-string p1, "1"

    invoke-interface {v0, v1, p1, v2}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertFlash(ILjava/lang/String;Z)V

    :cond_2
    const/4 p1, 0x1

    new-array p1, p1, [I

    const/16 v0, 0xa

    aput v0, p1, v2

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    goto :goto_0

    :cond_3
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object p1

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {p1, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object p1

    const-string v3, "1"

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_4

    const-string v3, "101"

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_4

    const-string v3, "2"

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_4

    const-string v3, "5"

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_4

    const-string p1, "1"

    invoke-interface {v0, v1, p1, v2}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertFlash(ILjava/lang/String;Z)V

    :cond_4
    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x4
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private finishSuperNightState(Z)V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xd4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$RecordState;

    if-eqz v0, :cond_1

    if-eqz p1, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->animateCapture()V

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->playCameraSound(I)V

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onPostSavingStart()V

    :cond_0
    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onPostSavingFinish()V

    :cond_1
    return-void
.end method

.method private getCalibrationDataFileName(I)Ljava/lang/String;
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_0

    const-string p1, "front_dual_camera_caldata.bin"

    return-object p1

    :cond_0
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideBokehCameraId()I

    move-result v0

    if-ne p1, v0, :cond_1

    const-string p1, "back_dual_camera_caldata_wu.bin"

    return-object p1

    :cond_1
    const-string p1, "back_dual_camera_caldata.bin"

    return-object p1
.end method

.method private getCountDownTimes(I)I
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBroadcastIntent:Landroid/content/Intent;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBroadcastIntent:Landroid/content/Intent;

    invoke-static {v0}, Lcom/android/camera/CameraIntentManager;->getInstance(Landroid/content/Intent;)Lcom/android/camera/CameraIntentManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->getTimerDurationSeconds()I

    move-result v0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraIntentManager()Lcom/android/camera/CameraIntentManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->getTimerDurationSeconds()I

    move-result v0

    :goto_0
    const/4 v1, -0x1

    const/4 v2, 0x3

    if-eq v0, v1, :cond_4

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mBroadcastIntent:Landroid/content/Intent;

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mBroadcastIntent:Landroid/content/Intent;

    const-string v1, "android.intent.extra.TIMER_DURATION_SECONDS"

    invoke-virtual {p1, v1}, Landroid/content/Intent;->removeExtra(Ljava/lang/String;)V

    goto :goto_1

    :cond_1
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getIntent()Landroid/content/Intent;

    move-result-object p1

    const-string v1, "android.intent.extra.TIMER_DURATION_SECONDS"

    invoke-virtual {p1, v1}, Landroid/content/Intent;->removeExtra(Ljava/lang/String;)V

    :goto_1
    if-eqz v0, :cond_3

    const/4 p1, 0x5

    if-eq v0, p1, :cond_2

    return v2

    :cond_2
    return p1

    :cond_3
    const/4 p1, 0x0

    return p1

    :cond_4
    const/16 v0, 0x64

    if-ne p1, v0, :cond_6

    invoke-static {}, Lcom/android/camera/CameraSettings;->isHandGestureOpen()Z

    move-result p1

    if-eqz p1, :cond_6

    invoke-static {}, Lcom/android/camera/CameraSettings;->getCountDownTimes()I

    move-result p1

    if-eqz p1, :cond_5

    goto :goto_2

    :cond_5
    move p1, v2

    :goto_2
    return p1

    :cond_6
    invoke-static {}, Lcom/android/camera/CameraSettings;->getCountDownTimes()I

    move-result p1

    return p1
.end method

.method private getCurrentAiSceneName()Ljava/lang/String;
    .locals 4

    nop

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa3

    if-eq v1, v2, :cond_1

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa7

    if-ne v1, v2, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_3

    :cond_1
    :goto_0
    const-string v1, "off"

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v2}, Lcom/android/camera/CameraSettings;->getAiSceneOpen(I)Z

    move-result v2

    if-eqz v2, :cond_5

    const-string/jumbo v1, "\u672a\u77e5"

    const/4 v2, -0x1

    if-ne v0, v2, :cond_3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isSilhouette:Z

    if-eqz v0, :cond_2

    const/16 v0, 0x18

    goto :goto_1

    :cond_2
    const/16 v0, 0x17

    :cond_3
    :goto_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const/high16 v3, 0x7f0f0000

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->obtainTypedArray(I)Landroid/content/res/TypedArray;

    move-result-object v2

    if-ltz v0, :cond_4

    invoke-virtual {v2}, Landroid/content/res/TypedArray;->length()I

    move-result v3

    if-ge v0, v3, :cond_4

    invoke-virtual {v2, v0}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v0

    goto :goto_2

    :cond_4
    move-object v0, v1

    :goto_2
    invoke-virtual {v2}, Landroid/content/res/TypedArray;->recycle()V

    goto :goto_3

    :cond_5
    move-object v0, v1

    :goto_3
    return-object v0
.end method

.method private getDeviceWaterMarkParam()Lcom/android/camera/effect/renders/DeviceWatermarkParam;
    .locals 10

    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result v1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCameraWaterMarkOpen()Z

    move-result v2

    nop

    nop

    nop

    const/4 v0, 0x0

    if-eqz v1, :cond_0

    const v3, 0x7f0d0186

    invoke-static {v3, v0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v3

    const v4, 0x7f0d0187

    invoke-static {v4, v0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v4

    const v5, 0x7f0d0188

    invoke-static {v5, v0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v0

    :goto_0
    move v8, v0

    move v6, v3

    move v7, v4

    goto :goto_2

    :cond_0
    if-eqz v2, :cond_2

    const v3, 0x7f0d0183

    invoke-static {v3, v0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v3

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v4

    if-eqz v4, :cond_1

    cmpl-float v4, v3, v0

    if-eqz v4, :cond_1

    goto :goto_1

    :cond_1
    const v3, 0x7f0d0182

    invoke-static {v3, v0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v3

    :goto_1
    const v4, 0x7f0d0184

    invoke-static {v4, v0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v4

    const v5, 0x7f0d0185

    invoke-static {v5, v0}, Lcom/android/camera/CameraSettings;->getResourceFloat(IF)F

    move-result v0

    goto :goto_0

    :cond_2
    move v6, v0

    move v7, v6

    move v8, v7

    :goto_2
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMiMovieOpen(I)Z

    move-result v4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelRearOn()Z

    move-result v3

    new-instance v9, Lcom/android/camera/effect/renders/DeviceWatermarkParam;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getDualCameraWaterMarkFilePathVendor()Ljava/lang/String;

    move-result-object v5

    move-object v0, v9

    invoke-direct/range {v0 .. v8}, Lcom/android/camera/effect/renders/DeviceWatermarkParam;-><init>(ZZZZLjava/lang/String;FFF)V

    return-object v9
.end method

.method private getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    invoke-static {}, Lcom/android/camera/preferences/CameraSettingPreferences;->instance()Lcom/android/camera/preferences/CameraSettingPreferences;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isManualModule()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-virtual {v0, p1, p2}, Lcom/android/camera/preferences/CameraSettingPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p2

    :cond_0
    return-object p2
.end method

.method private getPictureInfo()Lcom/xiaomi/camera/core/PictureInfo;
    .locals 5

    new-instance v0, Lcom/xiaomi/camera/core/PictureInfo;

    invoke-direct {v0}, Lcom/xiaomi/camera/core/PictureInfo;-><init>()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isFrontMirror()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setFrontMirror(Z)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setSensorType(Z)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehFrontCamera()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setBokehFrontCamera(Z)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v1, v2}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setHdrType(Ljava/lang/String;)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getOperatingMode()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setOpMode(I)Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setAiEnabled(Z)Lcom/xiaomi/camera/core/PictureInfo;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setAiType(I)Lcom/xiaomi/camera/core/PictureInfo;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/4 v2, 0x1

    const/16 v3, 0xa6

    if-ne v1, v3, :cond_0

    invoke-virtual {v0, v2}, Lcom/xiaomi/camera/core/PictureInfo;->setPanorama(Z)V

    goto :goto_0

    :cond_0
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xa7

    if-ne v1, v3, :cond_1

    invoke-virtual {v0, v2}, Lcom/xiaomi/camera/core/PictureInfo;->setProfession(Z)V

    :cond_1
    :goto_0
    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setShotBurst(Z)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->getShaderEffect()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setFilter(I)V

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v1}, Lcom/android/camera/CameraSettings;->getCameraLensType(I)Ljava/lang/String;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v1

    if-eqz v1, :cond_2

    const-string v1, "front"

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensType(Ljava/lang/String;)V

    goto/16 :goto_1

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActualCameraId()I

    move-result v1

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v3

    if-ne v1, v3, :cond_3

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "_RearUltra"

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensType(Ljava/lang/String;)V

    goto/16 :goto_1

    :cond_3
    const/16 v3, 0x16

    if-ne v1, v3, :cond_4

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "_RearMacro"

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensType(Ljava/lang/String;)V

    goto/16 :goto_1

    :cond_4
    const/16 v3, 0x14

    if-ne v1, v3, :cond_5

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "_RearTele"

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensType(Ljava/lang/String;)V

    goto :goto_1

    :cond_5
    const/16 v3, 0x17

    if-ne v1, v3, :cond_6

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "_RearTele4x"

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensType(Ljava/lang/String;)V

    goto :goto_1

    :cond_6
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result v3

    if-ne v1, v3, :cond_7

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "_RearWide"

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensType(Ljava/lang/String;)V

    goto :goto_1

    :cond_7
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getSATCameraId()I

    move-result v3

    if-ne v1, v3, :cond_8

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "_"

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "rear"

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensType(Ljava/lang/String;)V

    :cond_8
    :goto_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocalLengths:[F

    const/4 v3, 0x0

    if-eqz v1, :cond_9

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocalLengths:[F

    array-length v1, v1

    if-lez v1, :cond_9

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocalLengths:[F

    aget v1, v1, v3

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensfocal(F)V

    :cond_9
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mApertures:[F

    if-eqz v1, :cond_a

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mApertures:[F

    array-length v1, v1

    if-lez v1, :cond_a

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mApertures:[F

    aget v1, v1, v3

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setLensApertues(F)V

    :cond_a
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->getFaces()[Lcom/android/camera2/CameraHardwareFace;

    move-result-object v1

    invoke-static {v1}, Lcom/android/camera/module/DebugInfoUtil;->getFaceInfoString([Lcom/android/camera2/CameraHardwareFace;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_b

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setFaceRoi(Ljava/lang/String;)V

    :cond_b
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setOperateMode(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setZoomMulti(F)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v1, :cond_c

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getExposureCompensation()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setEvValue(I)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getCameraConfigs()Lcom/android/camera2/CameraConfigs;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera2/CameraConfigs;->getAFRegions()[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v1

    if-eqz v1, :cond_c

    array-length v4, v1

    if-lez v4, :cond_c

    aget-object v1, v1, v3

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setTouchRoi(Landroid/hardware/camera2/params/MeteringRectangle;)V

    :cond_c
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    if-eqz v1, :cond_d

    const-string v1, "i:0"

    invoke-static {}, Lcom/android/camera/CameraSettings;->getFaceBeautifyLevel()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_d

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningShine()Lcom/android/camera/data/data/runing/ComponentRunningShine;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->getBeautyVersion()I

    move-result v1

    const/4 v3, 0x2

    if-ne v1, v3, :cond_d

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    iget-object v1, v1, Lcom/android/camera/fragment/beauty/BeautyValues;->mBeautyLevel:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setBeautyLevel(Ljava/lang/String;)Lcom/xiaomi/camera/core/PictureInfo;

    :cond_d
    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    if-eqz v1, :cond_e

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFaceInfo:Lcom/android/camera/effect/FaceAnalyzeInfo;

    if-eqz v1, :cond_e

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFaceInfo:Lcom/android/camera/effect/FaceAnalyzeInfo;

    iget-object v1, v1, Lcom/android/camera/effect/FaceAnalyzeInfo;->mGender:[F

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setGender([F)Lcom/xiaomi/camera/core/PictureInfo;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFaceInfo:Lcom/android/camera/effect/FaceAnalyzeInfo;

    iget-object v1, v1, Lcom/android/camera/effect/FaceAnalyzeInfo;->mAge:[F

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/core/PictureInfo;->setBaby([F)Lcom/xiaomi/camera/core/PictureInfo;

    :cond_e
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xad

    if-ne v1, v3, :cond_f

    invoke-virtual {v0, v2}, Lcom/xiaomi/camera/core/PictureInfo;->setNightScene(I)Lcom/xiaomi/camera/core/PictureInfo;

    :cond_f
    invoke-virtual {v0}, Lcom/xiaomi/camera/core/PictureInfo;->end()V

    return-object v0
.end method

.method private getPictureSize(IILcom/android/camera/CameraSize;)Lcom/android/camera/CameraSize;
    .locals 4

    nop

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    if-eqz v0, :cond_3

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    invoke-virtual {v0, v1}, Lcom/android/camera2/CameraCapabilities;->setOperatingMode(I)V

    invoke-virtual {v0, p2}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object p2

    nop

    if-eqz p3, :cond_2

    new-instance v0, Ljava/util/ArrayList;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Ljava/util/ArrayList;-><init>(I)V

    :goto_0
    invoke-interface {p2}, Ljava/util/List;->size()I

    move-result v2

    if-ge v1, v2, :cond_1

    invoke-interface {p2, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/CameraSize;

    invoke-virtual {v2, p3}, Lcom/android/camera/CameraSize;->compareTo(Lcom/android/camera/CameraSize;)I

    move-result v3

    if-gtz v3, :cond_0

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_1
    move-object p2, v0

    :cond_2
    sget-object p3, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "getPictureSize: matchSizes = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p3, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {p2}, Lcom/android/camera/PictureSizeManager;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object p2

    goto :goto_1

    :cond_3
    const/4 p2, 0x0

    :goto_1
    sget-object p3, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "getPictureSize: cameraId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, " size = "

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p3, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object p2
.end method

.method private getPrefix()Ljava/lang/String;
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isLivePhotoStarted()Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "MV"

    return-object v0

    :cond_0
    const-string v0, ""

    return-object v0
.end method

.method private getRequestFlashMode()Ljava/lang/String;
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isSupportSceneMode()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->getFlashModeByScene(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    return-object v0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isSupportedFlashOn()Z

    move-result v0

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isSupportedTorch()Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "0"

    return-object v0

    :cond_1
    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isManualModule()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFlashSupportedInManualMode()Z

    move-result v0

    if-nez v0, :cond_2

    const-string v0, "200"

    return-object v0

    :cond_2
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    const/16 v2, 0x9

    if-ne v1, v2, :cond_4

    const-string v1, "3"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3

    const-string v0, "2"

    return-object v0

    :cond_3
    const-string v1, "103"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4

    const-string v0, "101"

    return-object v0

    :cond_4
    return-object v0
.end method

.method private getSatPictureSize()Lcom/android/camera/CameraSize;
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getSatMasterCameraId()I

    move-result v0

    packed-switch v0, :pswitch_data_0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getSatPictureSize: invalid satMasterCameraId "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mWidePictureSize:Lcom/android/camera/CameraSize;

    return-object v0

    :pswitch_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mUltraTelePictureSize:Lcom/android/camera/CameraSize;

    return-object v0

    :pswitch_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mTelePictureSize:Lcom/android/camera/CameraSize;

    return-object v0

    :pswitch_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mWidePictureSize:Lcom/android/camera/CameraSize;

    return-object v0

    :pswitch_3
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mUltraWidePictureSize:Lcom/android/camera/CameraSize;

    return-object v0

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private getSuffix()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isNormal()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->getSuffix()Ljava/lang/String;

    move-result-object v0

    return-object v0

    :cond_0
    const-string v0, ""

    return-object v0
.end method

.method private static getTiltShiftMode()Ljava/lang/String;
    .locals 2

    nop

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTiltShiftOn()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningTiltValue()Lcom/android/camera/data/data/runing/ComponentRunningTiltValue;

    move-result-object v0

    const/16 v1, 0xa0

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningTiltValue;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return-object v0
.end method

.method private handleSuperNightResultIfNeed()Z
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v0}, Lio/reactivex/disposables/Disposable;->isDisposed()Z

    move-result v0

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v0}, Lio/reactivex/disposables/Disposable;->dispose()V

    :cond_1
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSuperNightResult:Z

    const/4 v2, 0x1

    xor-int/2addr v0, v2

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSuperNightResult:Z

    if-eqz v0, :cond_2

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v3, "SuperNight: force trigger shutter animation, sound and post saving"

    invoke-static {v1, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_2
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->stopCpuBoost()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->currentIsMainThread()Z

    move-result v1

    if-eqz v1, :cond_3

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->finishSuperNightState(Z)V

    goto :goto_0

    :cond_3
    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v1

    new-instance v3, Lcom/android/camera/module/-$$Lambda$Camera2Module$njT3AVlF4xz_DT_miFyB6vNBM3U;

    invoke-direct {v3, p0, v0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$njT3AVlF4xz_DT_miFyB6vNBM3U;-><init>(Lcom/android/camera/module/Camera2Module;Z)V

    invoke-virtual {v1, v3}, Lio/reactivex/Scheduler;->scheduleDirect(Ljava/lang/Runnable;)Lio/reactivex/disposables/Disposable;

    :goto_0
    return v2
.end method

.method private handleSuperNightResultInCaptureMode()V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mShowSuperNightHint:Z

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$R3a2H0RvOKOcb8cVF4J_17agmrM;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$R3a2H0RvOKOcb8cVF4J_17agmrM;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_0
    return-void
.end method

.method private handleUpdateFaceView(ZZ)V
    .locals 6

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v3

    if-eqz p1, :cond_1

    iget-boolean p2, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    if-nez p2, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFaceBeautyMode()Z

    move-result p2

    if-eqz p2, :cond_2

    :cond_0
    const/4 p2, 0x1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getFocusMode()I

    move-result v0

    if-eq p2, v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    const/4 v2, 0x1

    const/4 v4, 0x1

    iget v5, p0, Lcom/android/camera/module/Camera2Module;->mCameraDisplayOrientation:I

    move v1, p1

    invoke-interface/range {v0 .. v5}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->updateFaceView(ZZZZI)V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    const/4 v4, 0x0

    const/4 v5, -0x1

    move v1, p1

    move v2, p2

    invoke-interface/range {v0 .. v5}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->updateFaceView(ZZZZI)V

    :cond_2
    :goto_0
    return-void
.end method

.method private hidePostCaptureAlert()V
    .locals 2

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->enableCameraControls(Z)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->isSessionReady()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->resumePreview()V

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->startPreview()V

    :goto_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v1, v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setEffectViewVisible(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa0

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    const/4 v1, 0x6

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->delegateEvent(I)V

    return-void
.end method

.method private hideSceneSelector()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    sget-object v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$qHFMpOo-CCTSO6YFl_47nU6Q0rA;->INSTANCE:Lcom/android/camera/module/-$$Lambda$Camera2Module$qHFMpOo-CCTSO6YFl_47nU6Q0rA;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method private initAiSceneParser()V
    .locals 3

    new-instance v0, Lcom/android/camera/module/loader/FunctionParseAiScene;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v2

    invoke-direct {v0, v1, v2}, Lcom/android/camera/module/loader/FunctionParseAiScene;-><init>(ILcom/android/camera2/CameraCapabilities;)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFunctionParseAiScene:Lcom/android/camera/module/loader/FunctionParseAiScene;

    new-instance v0, Lcom/android/camera/module/Camera2Module$17;

    invoke-direct {v0, p0}, Lcom/android/camera/module/Camera2Module$17;-><init>(Lcom/android/camera/module/Camera2Module;)V

    sget-object v1, Lio/reactivex/BackpressureStrategy;->DROP:Lio/reactivex/BackpressureStrategy;

    invoke-static {v0, v1}, Lio/reactivex/Flowable;->create(Lio/reactivex/FlowableOnSubscribe;Lio/reactivex/BackpressureStrategy;)Lio/reactivex/Flowable;

    move-result-object v0

    sget-object v1, Lcom/android/camera/constant/GlobalConstant;->sCameraSetupScheduler:Lio/reactivex/Scheduler;

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Flowable;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFunctionParseAiScene:Lcom/android/camera/module/loader/FunctionParseAiScene;

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/PredicateFilterAiScene;

    invoke-direct {v1, p0}, Lcom/android/camera/module/loader/PredicateFilterAiScene;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->filter(Lio/reactivex/functions/Predicate;)Lio/reactivex/Flowable;

    move-result-object v0

    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v1

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/Camera2Module$16;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$16;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->subscribe(Lio/reactivex/functions/Consumer;)Lio/reactivex/disposables/Disposable;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneDisposable:Lio/reactivex/disposables/Disposable;

    return-void
.end method

.method private initFlashAutoStateForTrack(Z)V
    .locals 2

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFlashAutoModeState:Ljava/lang/String;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "3"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    const-string v1, "103"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    const/16 v1, 0x9

    if-eq v0, v1, :cond_2

    if-eqz p1, :cond_1

    goto :goto_0

    :cond_1
    const-string p1, "auto-off"

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFlashAutoModeState:Ljava/lang/String;

    goto :goto_1

    :cond_2
    :goto_0
    const-string p1, "auto-on"

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFlashAutoModeState:Ljava/lang/String;

    :cond_3
    :goto_1
    return-void
.end method

.method private initMetaParser()V
    .locals 4

    new-instance v0, Lcom/android/camera/module/Camera2Module$15;

    invoke-direct {v0, p0}, Lcom/android/camera/module/Camera2Module$15;-><init>(Lcom/android/camera/module/Camera2Module;)V

    sget-object v1, Lio/reactivex/BackpressureStrategy;->DROP:Lio/reactivex/BackpressureStrategy;

    invoke-static {v0, v1}, Lio/reactivex/Flowable;->create(Lio/reactivex/FlowableOnSubscribe;Lio/reactivex/BackpressureStrategy;)Lio/reactivex/Flowable;

    move-result-object v0

    sget-object v1, Lcom/android/camera/constant/GlobalConstant;->sCameraSetupScheduler:Lio/reactivex/Scheduler;

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/FunctionParseAsdFace;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v2

    invoke-direct {v1, p0, v2}, Lcom/android/camera/module/loader/FunctionParseAsdFace;-><init>(Lcom/android/camera2/Camera2Proxy$FaceDetectionCallback;Z)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/FunctionParseAsdHdr;

    invoke-direct {v1, p0}, Lcom/android/camera/module/loader/FunctionParseAsdHdr;-><init>(Lcom/android/camera2/Camera2Proxy$HdrCheckerCallback;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/FunctionParseAsdUltraWide;

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-direct {v1, v2, p0}, Lcom/android/camera/module/loader/FunctionParseAsdUltraWide;-><init>(ILcom/android/camera2/Camera2Proxy$UltraWideCheckCallback;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/FunctionParseAsdLivePhoto;

    invoke-direct {v1, p0}, Lcom/android/camera/module/loader/FunctionParseAsdLivePhoto;-><init>(Lcom/android/camera2/Camera2Proxy$LivePhotoResultCallback;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/FunctionParseSuperNight;

    invoke-direct {v1, p0}, Lcom/android/camera/module/loader/FunctionParseSuperNight;-><init>(Lcom/android/camera2/Camera2Proxy$SuperNightCallback;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/scene/FunctionMiAlgoASDEngine;

    invoke-direct {v1, p0}, Lcom/android/camera/scene/FunctionMiAlgoASDEngine;-><init>(Lcom/android/camera/module/BaseModule;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    sget-object v1, Ljava/util/concurrent/TimeUnit;->MILLISECONDS:Ljava/util/concurrent/TimeUnit;

    const-wide/16 v2, 0x1f4

    invoke-virtual {v0, v2, v3, v1}, Lio/reactivex/Flowable;->sample(JLjava/util/concurrent/TimeUnit;)Lio/reactivex/Flowable;

    move-result-object v0

    sget-object v1, Lcom/android/camera/constant/GlobalConstant;->sCameraSetupScheduler:Lio/reactivex/Scheduler;

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/FunctionParseAsdScene;

    invoke-direct {v1, p0}, Lcom/android/camera/module/loader/FunctionParseAsdScene;-><init>(Lcom/android/camera/module/BaseModule;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Flowable;

    move-result-object v0

    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v1

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Flowable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Flowable;->onTerminateDetach()Lio/reactivex/Flowable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/Camera2Module$AsdSceneConsumer;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$AsdSceneConsumer;-><init>(Lcom/android/camera/module/BaseModule;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Flowable;->subscribe(Lio/reactivex/functions/Consumer;)Lio/reactivex/disposables/Disposable;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataDisposable:Lio/reactivex/disposables/Disposable;

    return-void
.end method

.method private initializeFocusManager()V
    .locals 4

    new-instance v0, Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v2

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v3}, Lcom/android/camera/Camera;->getMainLooper()Landroid/os/Looper;

    move-result-object v3

    invoke-direct {v0, v1, p0, v2, v3}, Lcom/android/camera/module/loader/camera2/FocusManager2;-><init>(Lcom/android/camera2/CameraCapabilities;Lcom/android/camera/module/loader/camera2/FocusManager2$Listener;ZLandroid/os/Looper;)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    nop

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraScreenNail;->getRenderRect()Landroid/graphics/Rect;

    move-result-object v0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroid/graphics/Rect;->width()I

    move-result v1

    if-lez v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v2}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/CameraScreenNail;->getRenderWidth()I

    move-result v2

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v3}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/CameraScreenNail;->getRenderHeight()I

    move-result v3

    invoke-virtual {v1, v2, v3}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setRenderSize(II)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Landroid/graphics/Rect;->width()I

    move-result v2

    invoke-virtual {v0}, Landroid/graphics/Rect;->height()I

    move-result v0

    invoke-virtual {v1, v2, v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setPreviewSize(II)V

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    sget v2, Lcom/android/camera/Util;->sWindowHeight:I

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setRenderSize(II)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    sget v2, Lcom/android/camera/Util;->sWindowHeight:I

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setPreviewSize(II)V

    :goto_1
    return-void
.end method

.method private is3ALocked()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    return v0
.end method

.method private isCannotGotoGallery()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isZooming:Z

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result v0

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isQueueFull()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInCountDown()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method private isCurrentRawDomainBasedSuperNight()Z
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xad

    if-ne v0, v1, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hK()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private isEnableQcfaForAlgoUp()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isSupportedQcfa()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    return v0

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInQCFAMode()Z

    move-result v0

    const/4 v2, 0x1

    if-eqz v0, :cond_2

    return v2

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hS()Z

    move-result v0

    if-eqz v0, :cond_3

    move v1, v2

    nop

    :cond_3
    return v1

    :cond_4
    :goto_0
    return v1
.end method

.method private isFaceBeautyOn(Lcom/android/camera/fragment/beauty/BeautyValues;)Z
    .locals 0

    if-nez p1, :cond_0

    const/4 p1, 0x0

    return p1

    :cond_0
    invoke-virtual {p1}, Lcom/android/camera/fragment/beauty/BeautyValues;->isFaceBeautyOn()Z

    move-result p1

    return p1
.end method

.method private isFrontMirror()Z
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    return v0

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontMirror()Z

    move-result v0

    return v0
.end method

.method private isImageSaverFull()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "isParallelQueueFull: ImageSaver is null"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/storage/ImageSaver;->isSaveQueueFull()Z

    move-result v0

    if-eqz v0, :cond_1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "isParallelQueueFull: ImageSaver queue is full"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x1

    return v0

    :cond_1
    return v1
.end method

.method private isIn3OrMoreSatMode()Z
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "isIn3OrMoreSatMode: opMode=0x"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    invoke-static {v2}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, " isIn3OrMore="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-boolean v2, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    const v1, 0x9002

    if-ne v1, v0, :cond_0

    sget-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private isInCountDown()Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCountdownDisposable:Lio/reactivex/disposables/Disposable;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCountdownDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v0}, Lio/reactivex/disposables/Disposable;->isDisposed()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private isInMultiSurfaceSatMode()Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isInMultiSurfaceSatMode()Z

    move-result v0

    return v0
.end method

.method private isInQCFAMode()Z
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    const/4 v1, 0x0

    const/16 v2, 0xa3

    if-eq v0, v2, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    const/16 v2, 0xa5

    if-eq v0, v2, :cond_0

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isSupportedQcfa()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v1, 0x1

    nop

    :cond_1
    return v1
.end method

.method private isLaunchedByMainIntent()Z
    .locals 2

    nop

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getIntent()Landroid/content/Intent;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    const-string v1, "android.intent.action.MAIN"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method private static isLiveShotAvailable(I)Z
    .locals 1

    if-eqz p0, :cond_0

    const/4 v0, 0x5

    if-eq p0, v0, :cond_0

    const/16 v0, 0x8

    if-eq p0, v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    const/4 p0, 0x1

    return p0
.end method

.method private isParallelQueueFull()Z
    .locals 4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v0

    if-nez v0, :cond_1

    return v1

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isImageSaverFull()Z

    move-result v0

    if-eqz v0, :cond_2

    const/4 v0, 0x1

    return v0

    :cond_2
    nop

    invoke-static {}, Lcom/android/camera/parallel/AlgoConnector;->getInstance()Lcom/android/camera/parallel/AlgoConnector;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/parallel/AlgoConnector;->getLocalBinder()Lcom/android/camera/LocalParallelService$LocalBinder;

    move-result-object v0

    if-eqz v0, :cond_3

    invoke-virtual {v0}, Lcom/android/camera/LocalParallelService$LocalBinder;->needWaitProcess()Z

    move-result v1

    goto :goto_0

    :cond_3
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "isParallelQueueFull: NOTICE: CHECK WHY BINDER IS NULL!"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "isParallelQueueFull: isNeedWaitProcess:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return v1
.end method

.method private isPortraitSuccessHintShowing()Z
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xaf

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->isPortraitHintVisible()Z

    move-result v0

    return v0
.end method

.method private isPreviewThumbnailWhenFlash()Z
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mUseLegacyFlashMode:Z

    const/4 v1, 0x1

    if-nez v0, :cond_0

    return v1

    :cond_0
    const-string v0, "3"

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mLastFlashMode:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    const-string v0, "1"

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mLastFlashMode:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    return v1

    :cond_2
    :goto_0
    const/4 v0, 0x0

    return v0
.end method

.method private isQueueFull()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isParallelQueueFull()Z

    move-result v0

    goto :goto_0

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isImageSaverFull()Z

    move-result v0

    :goto_0
    return v0
.end method

.method private isSensorRawStreamRequired()Z
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa7

    if-eq v0, v1, :cond_1

    const/16 v1, 0xad

    if-eq v0, v1, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hK()Z

    move-result v0

    return v0

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRaw()Lcom/android/camera/data/data/config/ComponentConfigRaw;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigRaw;->isSwitchOn(I)Z

    move-result v0

    return v0
.end method

.method private isTriggerQcfaModeChange(ZZ)Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isSupportedQcfa()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xab

    if-ne v0, v2, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_1

    return v1

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->gW()I

    move-result v0

    if-ltz v0, :cond_2

    return v1

    :cond_2
    nop

    if-eqz p1, :cond_4

    sget-boolean p1, Lcom/android/camera/module/Camera2Module;->mIsBeautyFrontOn:Z

    const/4 p2, 0x1

    if-eq p1, p2, :cond_5

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    const v0, 0x8007

    if-ne p1, v0, :cond_3

    return p2

    :cond_3
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object p1

    iget p2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {p1, p2}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    nop

    goto :goto_0

    :cond_4
    if-eqz p2, :cond_5

    :cond_5
    :goto_0
    return v1
.end method

.method private isUseSwMfnr()Z
    .locals 4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "GroupShot is on"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fI()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZoomRatioBetweenUltraAndWide()Z

    move-result v0

    if-eqz v0, :cond_2

    :cond_1
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "SwMfnr force off for ultra wide camera"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->isMfnrSatEnable()Z

    move-result v0

    if-nez v0, :cond_3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "Mfnr not enabled"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_3
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fH()Z

    move-result v0

    if-nez v0, :cond_4

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "SwMfnr is not supported"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_4
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isNormal()Z

    move-result v0

    if-nez v0, :cond_5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "Mutex mode is not normal"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_5
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fI()Z

    move-result v0

    const/4 v2, 0x1

    if-eqz v0, :cond_6

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xa7

    if-eq v0, v3, :cond_6

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xad

    if-eq v0, v3, :cond_6

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v0

    if-nez v0, :cond_6

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "For the devices does not have hardware MFNR, use software MFNR"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v2

    :cond_6
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_b

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_7

    return v1

    :cond_7
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    const v3, 0x8005

    if-ne v0, v3, :cond_8

    invoke-static {}, Lcom/mi/config/b;->km()Z

    move-result v0

    if-eqz v0, :cond_8

    return v2

    :cond_8
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    if-ne v0, v3, :cond_9

    invoke-static {}, Lcom/mi/config/b;->km()Z

    move-result v0

    if-nez v0, :cond_9

    return v1

    :cond_9
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->isSupportUltraWide()Z

    move-result v0

    if-nez v0, :cond_a

    sget-boolean v0, Lcom/mi/config/b;->sA:Z

    if-nez v0, :cond_a

    sget-boolean v0, Lcom/mi/config/b;->sJ:Z

    if-eqz v0, :cond_b

    :cond_a
    return v2

    :cond_b
    return v1
.end method

.method private keepScreenOnAwhile()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x11

    const-wide/16 v2, 0x3e8

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    return-void
.end method

.method public static synthetic lambda$handleSuperNightResultIfNeed$6(Lcom/android/camera/module/Camera2Module;Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->finishSuperNightState(Z)V

    return-void
.end method

.method public static synthetic lambda$handleSuperNightResultInCaptureMode$7(Lcom/android/camera/module/Camera2Module;)V
    .locals 5

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/16 v1, 0x8

    const v2, 0x7f090396

    const-wide/16 v3, -0x1

    invoke-interface {v0, v1, v2, v3, v4}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertAiDetectTipHint(IIJ)V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mShowSuperNightHint:Z

    return-void
.end method

.method static synthetic lambda$hideSceneSelector$2()V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_0

    const/16 v1, 0x8

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertAiSceneSelector(I)V

    :cond_0
    return-void
.end method

.method public static synthetic lambda$onFaceDetected$4(Lcom/android/camera/module/Camera2Module;[Lcom/android/camera2/CameraHardwareFace;)V
    .locals 2

    array-length p1, p1

    const/4 v0, 0x0

    const/4 v1, 0x1

    if-lez p1, :cond_0

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    if-nez p1, :cond_1

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->showOrHideChip(Z)V

    goto :goto_0

    :cond_0
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    if-eqz p1, :cond_1

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->showOrHideChip(Z)V

    :cond_1
    :goto_0
    return-void
.end method

.method static synthetic lambda$onHdrSceneChanged$3(Z)V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    if-eqz p0, :cond_0

    move p0, v1

    goto :goto_0

    :cond_0
    const/16 p0, 0x8

    :goto_0
    invoke-interface {v0, p0, v1, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertHDR(IZZ)V

    :cond_1
    return-void
.end method

.method public static synthetic lambda$onLongPress$8(Lcom/android/camera/module/Camera2Module;FFIII)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onOptionClick: which = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    const/4 v1, 0x1

    if-nez p5, :cond_0

    move v2, v1

    goto :goto_0

    :cond_0
    move v2, v0

    :goto_0
    invoke-static {v2}, Lcom/android/camera/statistic/CameraStatUtil;->trackGoogleLensPickerValue(Z)V

    packed-switch p5, :pswitch_data_0

    goto :goto_1

    :pswitch_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p1

    const-string p2, "pref_camera_long_press_viewfinder_key"

    const p5, 0x7f0902a5

    invoke-virtual {p0, p5}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object p5

    invoke-interface {p1, p2, p5}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p1

    const-string p2, "pref_en_first_choice_lock_ae_af_toast_key"

    invoke-interface {p1, p2, v1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putBoolean(Ljava/lang/String;Z)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    invoke-virtual {p0, p3, p4, v1}, Lcom/android/camera/module/Camera2Module;->onSingleTapUp(IIZ)V

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALockSupported:Z

    if-eqz p1, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->lockAEAF()V

    :cond_1
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1, v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->performHapticFeedback(I)V

    goto :goto_1

    :pswitch_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object p3

    invoke-virtual {p3}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p3

    const-string p4, "pref_google_lens_oobe_key"

    invoke-interface {p3, p4, v1}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putBoolean(Ljava/lang/String;Z)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p3

    invoke-interface {p3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object p3

    invoke-virtual {p3}, Lcom/android/camera/data/data/global/DataItemGlobal;->editor()Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p3

    const-string p4, "pref_camera_long_press_viewfinder_key"

    const p5, 0x7f0902a6

    invoke-virtual {p0, p5}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object p5

    invoke-interface {p3, p4, p5}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->putString(Ljava/lang/String;Ljava/lang/String;)Lcom/android/camera/data/provider/DataProvider$ProviderEditor;

    move-result-object p3

    invoke-interface {p3}, Lcom/android/camera/data/provider/DataProvider$ProviderEditor;->apply()V

    invoke-static {}, Lcom/android/lens/LensAgent;->getInstance()Lcom/android/lens/LensAgent;

    move-result-object p3

    const/4 p4, 0x2

    sget p5, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float p5, p5

    div-float/2addr p1, p5

    sget p5, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float p5, p5

    div-float/2addr p2, p5

    invoke-virtual {p3, p4, p1, p2}, Lcom/android/lens/LensAgent;->onFocusChange(IFF)V

    nop

    :goto_1
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public static synthetic lambda$onPause$11(Lcom/android/camera/module/Camera2Module;)V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsLensServiceBound:Z

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "Unbind Lens service: E"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsLensServiceBound:Z

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    invoke-virtual {v0}, Lcom/google/lens/sdk/LensApi;->onPause()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Unknown error when pause LensAPI->"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "Unbind Lens service: X"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    return-void
.end method

.method public static synthetic lambda$onResume$9(Lcom/android/camera/module/Camera2Module;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsLensServiceBound:Z

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result v0

    if-nez v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "Bind Lens service: E"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    invoke-virtual {v0}, Lcom/google/lens/sdk/LensApi;->onResume()V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsLensServiceBound:Z

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "Bind Lens service: X"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    return-void
.end method

.method public static synthetic lambda$onUltraWideChanged$15(Lcom/android/camera/module/Camera2Module;ZI)V
    .locals 5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "isOpenUltraWide: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, "; type: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v0, :cond_0

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsUltraWideConflict:Z

    xor-int/lit8 v0, p1, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->showOrHideChip(Z)V

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xaf

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-nez v0, :cond_1

    return-void

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZoomRatioBetweenUltraAndWide()Z

    move-result v1

    const v2, 0x7f0903af

    const v3, 0x7f090251

    if-eqz v1, :cond_3

    invoke-interface {v0, v3}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->containTips(I)Z

    move-result v1

    if-nez v1, :cond_2

    invoke-interface {v0, v2}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->containTips(I)Z

    move-result v1

    if-eqz v1, :cond_3

    :cond_2
    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->directlyHideTips()V

    return-void

    :cond_3
    if-eqz p1, :cond_b

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v1, 0xb6

    invoke-virtual {p1, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$DualController;

    const/4 v1, 0x1

    if-eqz p1, :cond_4

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$DualController;->isSlideVisible()Z

    move-result p1

    if-eqz p1, :cond_4

    move p1, v1

    goto :goto_0

    :cond_4
    const/4 p1, 0x0

    :goto_0
    if-eqz p1, :cond_5

    return-void

    :cond_5
    invoke-static {}, Lcom/android/camera/scene/MiAlgoAsdSceneProfile;->isAlreadyTip()Z

    move-result p1

    if-eqz p1, :cond_6

    return-void

    :cond_6
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result p1

    const/high16 v4, 0x3f800000    # 1.0f

    cmpl-float p1, p1, v4

    if-lez p1, :cond_7

    return-void

    :cond_7
    const/16 p1, 0xab

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v4

    if-ne p1, v4, :cond_8

    return-void

    :cond_8
    const/4 p1, 0x2

    if-eq p2, p1, :cond_9

    goto :goto_1

    :cond_9
    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 p2, 0x24

    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->trackAISceneChanged(II)V

    nop

    :goto_1
    sget p1, Lcom/android/camera/scene/MiAlgoAsdSceneProfile;->ULTRA_WIDE:I

    invoke-static {p1, v1}, Lcom/android/camera/scene/MiAlgoAsdSceneProfile;->setTipEnable(IZ)V

    const/16 p1, 0xe

    sget-boolean p2, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-eqz p2, :cond_a

    goto :goto_2

    :cond_a
    move v2, v3

    :goto_2
    const/4 p2, 0x7

    invoke-interface {v0, p1, v2, p2}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->showTips(III)V

    goto :goto_3

    :cond_b
    invoke-interface {v0, v3}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->containTips(I)Z

    move-result p1

    if-nez p1, :cond_c

    invoke-interface {v0, v2}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->containTips(I)Z

    move-result p1

    if-eqz p1, :cond_d

    :cond_c
    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->directlyHideTips()V

    :cond_d
    :goto_3
    return-void
.end method

.method public static synthetic lambda$registerProtocol$0(Lcom/android/camera/module/Camera2Module;)V
    .locals 1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsAiConflict:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsUltraWideConflict:Z

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->showOrHideChip(Z)V

    return-void
.end method

.method public static synthetic lambda$setFrameAvailable$10(Lcom/android/camera/module/Camera2Module;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/SensorStateManager;->setLieIndicatorEnabled(Z)V

    return-void
.end method

.method public static synthetic lambda$setOrientationParameter$14(Lcom/android/camera/module/Camera2Module;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setDeviceOrientation(I)V

    return-void
.end method

.method public static synthetic lambda$startNormalCapture$5(Lcom/android/camera/module/Camera2Module;Z)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onCaptureCompleted and enable shot = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hm()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result v0

    if-nez v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v0, :cond_0

    if-eqz p1, :cond_0

    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->enableCameraControls(Z)V

    :cond_0
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->unRegisterCaptureCallback()V

    :cond_1
    return-void
.end method

.method public static synthetic lambda$unRegisterProtocol$1(Lcom/android/camera/module/Camera2Module;)V
    .locals 1

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->showOrHideChip(Z)V

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsUltraWideConflict:Z

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsAiConflict:Z

    return-void
.end method

.method public static synthetic lambda$updateManualEvAdjust$12(Lcom/android/camera/module/Camera2Module;Z)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0, p1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setEvAdjustable(Z)V

    return-void
.end method

.method public static synthetic lambda$updateManualEvAdjust$13(Lcom/android/camera/module/Camera2Module;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->cancelFocus()V

    return-void
.end method

.method private lockAEAF()V
    .locals 2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "lockAEAF"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setAeAwbLock(Z)V

    :cond_0
    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    return-void
.end method

.method private needQuickShot()Z
    .locals 4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mBlockQuickShot:Z

    const/4 v1, 0x0

    if-nez v0, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraQuickShotEnable()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->enablePreviewAsThumbnail()Z

    move-result v0

    if-eqz v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa3

    if-eq v0, v2, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa5

    if-ne v0, v2, :cond_3

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v0

    const/high16 v2, 0x3f800000    # 1.0f

    cmpl-float v0, v0, v2

    if-nez v0, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-nez v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isUltraWideConfigOpen(I)Z

    move-result v0

    if-nez v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    if-nez v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isNeedFlashOn()Z

    move-result v0

    if-nez v0, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-nez v0, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v0

    if-nez v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-virtual {v0}, Lcom/android/camera/fragment/beauty/BeautyValues;->isFaceBeautyOn()Z

    move-result v0

    if-nez v0, :cond_3

    :cond_2
    const/4 v1, 0x1

    nop

    :cond_3
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "needQuickShot bRet:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_4
    :goto_0
    return v1
.end method

.method private needShowThumbProgressImmediately()Z
    .locals 4

    const-string v0, "pref_qc_camera_exposuretime_key"

    const v1, 0x7f090142

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v0

    const-wide/32 v2, 0xee6b280

    cmp-long v0, v0, v2

    if-lez v0, :cond_0

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xad

    if-eq v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private onBurstPictureTakenFinished(Z)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->stopMultiSnap()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBurstEmitter:Lio/reactivex/ObservableEmitter;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBurstEmitter:Lio/reactivex/ObservableEmitter;

    invoke-interface {v0}, Lio/reactivex/ObservableEmitter;->onComplete()V

    :cond_0
    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->onPictureTakenFinished(Z)V

    const/4 p1, 0x1

    invoke-static {p1}, Lcom/xiaomi/camera/base/PerformanceTracker;->trackPictureCapture(I)V

    return-void
.end method

.method private onShutter(Z)V
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-nez v0, :cond_0

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "onShutter: preview stopped"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mShutterCallbackTime:J

    iget-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mShutterCallbackTime:J

    iget-wide v2, p0, Lcom/android/camera/module/Camera2Module;->mCaptureStartTime:J

    sub-long/2addr v0, v2

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mShutterLag:J

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mShutterLag = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v2, p0, Lcom/android/camera/module/Camera2Module;->mShutterLag:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v2, "ms"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->updateEnablePreviewThumbnail(Z)V

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/CameraScreenNail;->requestReadPixels()V

    goto :goto_0

    :cond_1
    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    if-nez p1, :cond_2

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v0, 0xad

    if-eq p1, v0, :cond_2

    const/4 p1, 0x0

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->updateThumbProgress(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->animateCapture()V

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->playCameraSound(I)V

    :cond_2
    :goto_0
    return-void
.end method

.method private parseIntent()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraIntentManager()Lcom/android/camera/CameraIntentManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->isImageCaptureIntent()Z

    move-result v1

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-nez v1, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->getExtraSavedUri()Landroid/net/Uri;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSaveUri:Landroid/net/Uri;

    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->getExtraCropValue()Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCropValue:Ljava/lang/String;

    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->getExtraShouldSaveCapture()Ljava/lang/Boolean;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v1

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsSaveCaptureImage:Z

    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->isQuickCapture()Ljava/lang/Boolean;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mQuickCapture:Z

    return-void
.end method

.method private prepareEffectProcessor(I)V
    .locals 0

    return-void
.end method

.method private prepareMultiCapture()V
    .locals 2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "prepareMultiCapture"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStopRequest:Z

    invoke-static {}, Lcom/android/camera/Util;->clearMemoryLimit()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->prepareNormalCapture()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-static {}, Lcom/android/camera2/CameraCapabilities;->getBurstShootCount()I

    move-result v0

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mTotalJpegCallbackNum:I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x31

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->is3ALocked()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onShutter()V

    :cond_0
    return-void
.end method

.method private prepareNormalCapture()V
    .locals 6

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "prepareNormalCapture"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isNeedFlashOn()Z

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->initFlashAutoStateForTrack(Z)V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    const/4 v1, 0x1

    iput v1, p0, Lcom/android/camera/module/Camera2Module;->mTotalJpegCallbackNum:I

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    iput-wide v2, p0, Lcom/android/camera/module/Camera2Module;->mCaptureStartTime:J

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v2

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v2, v3}, Lcom/android/camera/statistic/ScenarioTrackUtil;->trackCaptureTimeStart(ZI)V

    iget-wide v2, p0, Lcom/android/camera/module/Camera2Module;->mCaptureStartTime:J

    iput-wide v2, p0, Lcom/android/camera/module/Camera2Module;->mLastCaptureTime:J

    const/4 v2, 0x3

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    invoke-static {v2, v3}, Lcom/android/camera/Util;->getJpegRotation(II)I

    move-result v2

    iput v2, p0, Lcom/android/camera/module/Camera2Module;->mJpegRotation:I

    sget-object v2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "prepareNormalCapture: mOrientation = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ", mJpegRotation = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mJpegRotation:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mJpegRotation:I

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->setJpegRotation(I)V

    invoke-static {}, Lcom/android/camera/LocationManager;->instance()Lcom/android/camera/LocationManager;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/LocationManager;->getCurrentLocation()Landroid/location/Location;

    move-result-object v2

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3, v2}, Lcom/android/camera2/Camera2Proxy;->setGpsLocation(Landroid/location/Location;)V

    iput-object v2, p0, Lcom/android/camera/module/Camera2Module;->mLocation:Landroid/location/Location;

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFrontMirror()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateBeauty()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSRAndMFNR()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateShotDetermine()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateCaptureTriggerFlow()V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getPrefix()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    invoke-static {v4, v5}, Lcom/android/camera/Util;->createJpegName(J)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getSuffix()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/android/camera/storage/Storage;->generateFilepath4Jpeg(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v4, :cond_1

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v4, :cond_0

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    if-eqz v4, :cond_1

    :cond_0
    move v4, v1

    goto :goto_0

    :cond_1
    nop

    move v4, v0

    :goto_0
    invoke-virtual {v2, v3, v4}, Lcom/android/camera2/Camera2Proxy;->setShotSavePath(Ljava/lang/String;Z)V

    nop

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xa3

    if-eq v2, v3, :cond_3

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xa5

    if-eq v2, v3, :cond_3

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xab

    if-eq v2, v3, :cond_3

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xaf

    if-ne v2, v3, :cond_2

    goto :goto_2

    :cond_2
    :goto_1
    move v2, v0

    goto :goto_6

    :cond_3
    :goto_2
    nop

    nop

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraQuickShotEnable()Z

    move-result v2

    if-nez v2, :cond_5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraQuickShotAnimateEnable()Z

    move-result v2

    if-eqz v2, :cond_4

    goto :goto_3

    :cond_4
    move v2, v0

    goto :goto_4

    :cond_5
    :goto_3
    nop

    move v2, v1

    :goto_4
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v3

    invoke-virtual {v3}, Lcom/mi/config/a;->hm()Z

    move-result v3

    if-nez v2, :cond_7

    if-eqz v3, :cond_6

    goto :goto_5

    :cond_6
    goto :goto_1

    :cond_7
    :goto_5
    move v2, v1

    :goto_6
    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3, v2}, Lcom/android/camera2/Camera2Proxy;->setNeedSequence(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->enablePreviewAsThumbnail()Z

    move-result v2

    if-eqz v2, :cond_9

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v2}, Lcom/android/camera/MutexModeManager;->isHdr()Z

    move-result v2

    if-eqz v2, :cond_8

    goto :goto_7

    :cond_8
    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraQuickShotAnimateEnable()Z

    move-result v2

    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mQuickShotAnimateEnable:Z

    goto :goto_8

    :cond_9
    :goto_7
    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mQuickShotAnimateEnable:Z

    :goto_8
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->setWaterMark()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->setPictureOrientation()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateJpegQuality()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateAlgorithmName()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->needShowThumbProgressImmediately()Z

    move-result v2

    if-eqz v2, :cond_a

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->updateThumbProgress(Z)V

    :cond_a
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->prepareSuperNight()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->prepareSuperNightInCaptureMode()V

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->checkMoreFrameCaptureLockAFAE(Z)V

    return-void
.end method

.method private prepareSuperNight()V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xad

    if-eq v0, v1, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xd4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$RecordState;

    if-eqz v0, :cond_1

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onPrepare()V

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onStart()V

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->startCpuBoost()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightEventConsumer:Lio/reactivex/functions/Consumer;

    if-nez v0, :cond_2

    new-instance v0, Lcom/android/camera/module/Camera2Module$SuperNightEventConsumer;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/android/camera/module/Camera2Module$SuperNightEventConsumer;-><init>(Lcom/android/camera/module/Camera2Module;Lcom/android/camera/module/Camera2Module$1;)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightEventConsumer:Lio/reactivex/functions/Consumer;

    :cond_2
    const/16 v0, 0x12c

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    const/16 v1, 0x7d0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-static {v0, v1}, Lio/reactivex/Observable;->just(Ljava/lang/Object;Ljava/lang/Object;)Lio/reactivex/Observable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/Camera2Module$6;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$6;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Observable;->flatMap(Lio/reactivex/functions/Function;)Lio/reactivex/Observable;

    move-result-object v0

    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v1

    invoke-virtual {v0, v1}, Lio/reactivex/Observable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Observable;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightEventConsumer:Lio/reactivex/functions/Consumer;

    invoke-virtual {v0, v1}, Lio/reactivex/Observable;->subscribe(Lio/reactivex/functions/Consumer;)Lio/reactivex/disposables/Disposable;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

    return-void
.end method

.method private prepareSuperNightInCaptureMode()V
    .locals 5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    const/4 v2, 0x1

    invoke-interface {v1, v2}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->isFaceExists(I)Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v1, "3"

    if-ne v0, v1, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->ie()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setForceBackSoftLight(Z)V

    :cond_0
    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mShowSuperNightHint:Z

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/4 v1, 0x0

    const v2, 0x7f090396

    const-wide/16 v3, -0x1

    invoke-interface {v0, v1, v2, v3, v4}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertAiDetectTipHint(IIJ)V

    :cond_1
    return-void
.end method

.method private previewWhenSessionSuccess()V
    .locals 1

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    sget-object v0, Lcom/android/camera/constant/UpdateConstant;->CAMERA_TYPES_ON_PREVIEW_SUCCESS:[I

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isManualModule()Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/constant/UpdateConstant;->CAMERA_TYPES_MANUALLY:[I

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    :cond_0
    return-void
.end method

.method private realConsumeAiSceneResult(IZ)V
    .locals 8

    const/4 v0, 0x0

    const/16 v1, 0x24

    if-ne p1, v1, :cond_0

    nop

    move p1, v0

    :cond_0
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 v2, 0xac

    if-ne v1, p1, :cond_2

    if-eqz p1, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v1, :cond_2

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->getCurrentAiSceneLevel()I

    move-result v1

    if-ne v1, p1, :cond_2

    return-void

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v1

    if-nez v1, :cond_16

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v1

    if-eqz v1, :cond_16

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result v1

    if-eqz v1, :cond_3

    goto/16 :goto_4

    :cond_3
    if-eqz p2, :cond_4

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->isResetFromMutex:Z

    if-eqz v1, :cond_4

    return-void

    :cond_4
    if-nez p2, :cond_5

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isResetFromMutex:Z

    :cond_5
    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "consumeAiSceneResult: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, "; isReset: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v2

    const/16 v3, 0xa4

    invoke-virtual {v2, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v2

    check-cast v2, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v3

    const/16 v4, 0x19

    const/4 v5, 0x1

    if-nez v3, :cond_7

    sget-boolean v3, Lcom/mi/config/b;->sU:Z

    if-nez v3, :cond_7

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-ne p1, v4, :cond_6

    move v6, v5

    goto :goto_0

    :cond_6
    move v6, v0

    :goto_0
    invoke-virtual {v3, v6}, Lcom/android/camera2/Camera2Proxy;->setCameraAI30(Z)V

    :cond_7
    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v3, :cond_9

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    invoke-static {v3}, Lcom/android/lens/LensAgent;->isConflictAiScene(I)Z

    move-result v3

    if-nez v3, :cond_8

    invoke-static {p1}, Lcom/android/lens/LensAgent;->isConflictAiScene(I)Z

    move-result v3

    if-eqz v3, :cond_8

    iput-boolean v5, p0, Lcom/android/camera/module/Camera2Module;->mIsAiConflict:Z

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->showOrHideChip(Z)V

    goto :goto_1

    :cond_8
    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    invoke-static {v3}, Lcom/android/lens/LensAgent;->isConflictAiScene(I)Z

    move-result v3

    if-eqz v3, :cond_9

    invoke-static {p1}, Lcom/android/lens/LensAgent;->isConflictAiScene(I)Z

    move-result v3

    if-nez v3, :cond_9

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsAiConflict:Z

    invoke-virtual {p0, v5}, Lcom/android/camera/module/Camera2Module;->showOrHideChip(Z)V

    :cond_9
    :goto_1
    const/16 v3, 0x8

    invoke-virtual {p0, p1, v3}, Lcom/android/camera/module/Camera2Module;->closeMoonMode(II)V

    invoke-virtual {p0, p1, v3}, Lcom/android/camera/module/Camera2Module;->closeBacklightTip(II)V

    nop

    const/4 v6, -0x1

    if-eq p1, v6, :cond_15

    const/4 v6, 0x6

    if-eq p1, v5, :cond_f

    const/16 v7, 0xa

    if-eq p1, v7, :cond_e

    const/16 v7, 0xf

    if-eq p1, v7, :cond_c

    const/16 v7, 0x13

    if-eq p1, v7, :cond_c

    const/16 v6, 0x25

    if-eq p1, v6, :cond_b

    packed-switch p1, :pswitch_data_0

    packed-switch p1, :pswitch_data_1

    packed-switch p1, :pswitch_data_2

    packed-switch p1, :pswitch_data_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    const-string v0, "e"

    invoke-interface {v2, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    sget-object v0, Lcom/android/camera/constant/UpdateConstant;->AI_SCENE_CONFIG:[I

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    goto/16 :goto_2

    :pswitch_0
    nop

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->showMoonMode(Z)Z

    move-result v2

    if-eqz v2, :cond_12

    invoke-interface {v1, p1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->setAiSceneImageLevel(I)V

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p2, v5}, Lcom/android/camera2/Camera2Proxy;->setASD(Z)V

    iget p2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-direct {p0, p2, p1}, Lcom/android/camera/module/Camera2Module;->trackAISceneChanged(II)V

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    return-void

    :pswitch_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v3

    invoke-virtual {v3}, Lcom/mi/config/a;->fD()Z

    move-result v3

    if-eqz v3, :cond_a

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const-string v0, "e"

    invoke-interface {v2, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    goto/16 :goto_2

    :cond_a
    nop

    const-string p1, "e"

    invoke-interface {v2, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    sget-object p1, Lcom/android/camera/constant/UpdateConstant;->AI_SCENE_CONFIG:[I

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    nop

    move p1, v0

    goto/16 :goto_2

    :pswitch_2
    iget p2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-direct {p0, p2, v4}, Lcom/android/camera/module/Camera2Module;->trackAISceneChanged(II)V

    invoke-interface {v1, v4}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->setAiSceneImageLevel(I)V

    invoke-interface {v1, v3}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertAiSceneSelector(I)V

    invoke-direct {p0, v4}, Lcom/android/camera/module/Camera2Module;->setAiSceneEffect(I)V

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    const-string p1, "e"

    invoke-interface {v2, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setASD(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void

    :pswitch_3
    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const-string v0, "e"

    invoke-interface {v2, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    goto/16 :goto_2

    :pswitch_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->getContrast()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3, v0}, Lcom/android/camera2/Camera2Proxy;->setContrast(I)V

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const-string v0, "e"

    invoke-interface {v2, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSuperResolution()V

    goto/16 :goto_2

    :pswitch_5
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->ie()Z

    move-result v0

    if-eqz v0, :cond_11

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_11

    invoke-static {}, Lcom/android/camera/scene/MiAlgoAsdSceneProfile;->isAlreadyTip()Z

    move-result v0

    if-nez v0, :cond_11

    sget v0, Lcom/android/camera/scene/MiAlgoAsdSceneProfile;->AI_SCENE_MODE_FOOD:I

    invoke-static {v0, v5}, Lcom/android/camera/scene/MiAlgoAsdSceneProfile;->setTipEnable(IZ)V

    const/16 v0, 0x14

    const v2, 0x7f0903b0

    const/4 v3, 0x2

    invoke-virtual {p0, v0, v2, v3}, Lcom/android/camera/module/Camera2Module;->updateTipMessage(III)V

    goto :goto_2

    :cond_b
    :pswitch_6
    nop

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    goto :goto_3

    :cond_c
    invoke-static {}, Lcom/android/camera/CameraSettings;->getSharpness()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    if-ge v0, v6, :cond_d

    add-int/lit8 v0, v0, 0x1

    :cond_d
    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3, v0}, Lcom/android/camera2/Camera2Proxy;->setSharpness(I)V

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const-string v0, "e"

    invoke-interface {v2, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    goto :goto_2

    :cond_e
    const-string v3, "e"

    new-array v4, v5, [I

    const/16 v6, 0xc1

    aput v6, v4, v0

    invoke-interface {v2, v3, v4}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->closeMutexElement(Ljava/lang/String;[I)V

    const-string v0, "0"

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setFlashMode(Ljava/lang/String;)V

    invoke-direct {p0, v5}, Lcom/android/camera/module/Camera2Module;->updateMfnr(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateOIS()V

    goto :goto_2

    :cond_f
    invoke-static {}, Lcom/android/camera/CameraSettings;->getSharpness()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    if-ge v0, v6, :cond_10

    add-int/lit8 v0, v0, 0x1

    :cond_10
    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const-string v3, "e"

    invoke-interface {v2, v3}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreAllMutexElement(Ljava/lang/String;)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v0}, Lcom/android/camera2/Camera2Proxy;->setSharpness(I)V

    nop

    :cond_11
    :goto_2
    move v0, v5

    :cond_12
    :goto_3
    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-direct {p0, v2, p1}, Lcom/android/camera/module/Camera2Module;->trackAISceneChanged(II)V

    invoke-interface {v1, p1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->setAiSceneImageLevel(I)V

    if-eqz v0, :cond_13

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->setAiSceneEffect(I)V

    :cond_13
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v5}, Lcom/android/camera2/Camera2Proxy;->setASD(Z)V

    if-nez p2, :cond_14

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    :cond_14
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateBeauty()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void

    :cond_15
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->showBacklightTip()V

    const/16 p2, 0x17

    invoke-interface {v1, p2}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->setAiSceneImageLevel(I)V

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    return-void

    :cond_16
    :goto_4
    return-void

    :pswitch_data_0
    .packed-switch 0x3
        :pswitch_5
        :pswitch_4
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x7
        :pswitch_3
        :pswitch_3
    .end packed-switch

    :pswitch_data_2
    .packed-switch 0x19
        :pswitch_2
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_1
    .end packed-switch

    :pswitch_data_3
    .packed-switch 0x22
        :pswitch_6
        :pswitch_0
    .end packed-switch
.end method

.method private releaseEffectProcessor()V
    .locals 0

    return-void
.end method

.method private resetAiSceneInHdrOrFlashOn()V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    if-eqz v0, :cond_3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isResetFromMutex:Z

    if-nez v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/4 v1, -0x1

    if-eq v0, v1, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 v1, 0xa

    if-ne v0, v1, :cond_2

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$18;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$18;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_2
    return-void

    :cond_3
    :goto_0
    return-void
.end method

.method private resetAsdSceneInHdrOrFlashChange()V
    .locals 2

    invoke-static {}, Lcom/mi/config/b;->kk()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    const/4 v1, -0x1

    if-ne v0, v1, :cond_0

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAsdScene:I

    const/16 v1, 0x9

    if-ne v0, v1, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$19;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$19;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_1
    return-void

    :cond_2
    :goto_0
    return-void
.end method

.method private resetScreenOn()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x11

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    :cond_0
    return-void
.end method

.method private resumePreviewInWorkThread()V
    .locals 1

    const/4 v0, 0x0

    new-array v0, v0, [I

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    return-void
.end method

.method private setAiSceneEffect(I)V
    .locals 4

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setAiSceneEffect: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fY()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_0

    const/16 v0, 0x19

    if-ne p1, v0, :cond_0

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v0, "supportAi30: AI 3.0 back camera in HUMAN SCENE not apply filter!"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v0

    if-eqz v0, :cond_3

    :cond_1
    if-eqz p1, :cond_2

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "setAiSceneEffect: front camera or portrait mode nonsupport!"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->getPortraitLightingPattern()I

    move-result v0

    if-eqz v0, :cond_3

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "setAiSceneEffect: scene = 0 but portrait lighting is on..."

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_3
    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    const/4 v1, 0x5

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/EffectController;->getFilterInfo(I)Ljava/util/ArrayList;

    move-result-object v0

    if-ltz p1, :cond_8

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v2

    if-le p1, v2, :cond_4

    goto :goto_1

    :cond_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->getShaderEffect()I

    move-result v2

    invoke-static {v2}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result v3

    if-eq v3, v1, :cond_5

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-eq v2, v1, :cond_5

    return-void

    :cond_5
    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/effect/FilterInfo;

    invoke-virtual {p1}, Lcom/android/camera/effect/FilterInfo;->getId()I

    move-result p1

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/android/camera/effect/EffectController;->setAiSceneEffect(I)V

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-eq p1, v0, :cond_6

    const/4 v0, 0x1

    goto :goto_0

    :cond_6
    const/4 v0, 0x0

    :goto_0
    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mHasAiSceneFilterEffect:Z

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/effect/EffectController;->hasEffect()Z

    move-result v0

    if-eqz v0, :cond_7

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->prepareEffectProcessor(I)V

    :cond_7
    return-void

    :cond_8
    :goto_1
    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "setAiSceneEffect: scene unknown!"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private setEffectFilter(I)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setEffectFilter: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/android/camera/effect/EffectController;->setEffect(I)V

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mFilterId:I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    invoke-virtual {v0, p1}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->setFilterId(I)V

    :cond_0
    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/effect/EffectController;->hasEffect()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->prepareEffectProcessor(I)V

    :cond_1
    return-void
.end method

.method private setLightingEffect()V
    .locals 4

    invoke-static {}, Lcom/android/camera/CameraSettings;->getShaderEffect()I

    move-result v0

    invoke-static {v0}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result v1

    const/4 v2, 0x5

    if-eq v1, v2, :cond_0

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-eq v0, v1, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->getPortraitLightingPattern()I

    move-result v0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "setLightingEffect: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v1

    const/4 v2, 0x6

    invoke-virtual {v1, v2}, Lcom/android/camera/effect/EffectController;->getFilterInfo(I)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/effect/FilterInfo;

    invoke-virtual {v0}, Lcom/android/camera/effect/FilterInfo;->getId()I

    move-result v0

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/effect/EffectController;->setLightingEffect(I)V

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/effect/EffectController;->hasEffect()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->prepareEffectProcessor(I)V

    :cond_1
    return-void
.end method

.method private setOrientation(II)V
    .locals 2

    const/4 v0, -0x1

    if-ne p1, v0, :cond_0

    return-void

    :cond_0
    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    invoke-static {v0, v1}, Lcom/android/camera/Util;->getShootOrientation(Landroid/app/Activity;I)I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/effect/EffectController;->setOrientation(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->checkActivityOrientation()V

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mOrientationCompensation:I

    if-eq p1, p2, :cond_1

    iput p2, p0, Lcom/android/camera/module/Camera2Module;->mOrientationCompensation:I

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->setOrientationParameter()V

    :cond_1
    return-void
.end method

.method private setOrientationParameter()V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDeparted()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    const/4 v1, -0x1

    if-eq v0, v1, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrameAvailable()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    new-array v0, v1, [I

    const/4 v1, 0x0

    const/16 v2, 0x23

    aput v2, v0, v1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    goto :goto_0

    :cond_1
    sget-object v0, Lcom/android/camera/constant/GlobalConstant;->sCameraSetupScheduler:Lio/reactivex/Scheduler;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$5JyVqqx_wsAzpzsbmsoEIGWy8HM;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$5JyVqqx_wsAzpzsbmsoEIGWy8HM;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Scheduler;->scheduleDirect(Ljava/lang/Runnable;)Lio/reactivex/disposables/Disposable;

    :cond_2
    :goto_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mOrientationCompensation:I

    invoke-virtual {v0, v1}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->setOrientationHint(I)V

    :cond_3
    return-void
.end method

.method private setPictureOrientation()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/SensorStateManager;->isDeviceLying()Z

    move-result v0

    if-eqz v0, :cond_0

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    int-to-float v0, v0

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mDeviceRotation:F

    :goto_0
    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mShootRotation:F

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    const/4 v1, -0x1

    if-ne v0, v1, :cond_1

    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    :goto_1
    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mShootOrientation:I

    return-void
.end method

.method private setPortraitSuccessHintVisible(I)V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xaf

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {v0, p1}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->setPortraitHintVisible(I)V

    return-void
.end method

.method private setSceneMode(Ljava/lang/String;)Z
    .locals 3

    const/4 v0, -0x1

    invoke-static {p1, v0}, Lcom/android/camera/Util;->parseInt(Ljava/lang/String;I)I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setSceneMode(I)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v1}, Lcom/android/camera2/CameraCapabilities;->getSupportedSceneModes()[I

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/Util;->isSupported(I[I)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "sceneMode="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x1

    return p1

    :cond_0
    const/4 p1, 0x0

    return p1
.end method

.method private setVideoSize(II)V
    .locals 1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraDisplayOrientation:I

    rem-int/lit16 v0, v0, 0xb4

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/CameraSize;

    invoke-direct {v0, p1, p2}, Lcom/android/camera/CameraSize;-><init>(II)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mVideoSize:Lcom/android/camera/CameraSize;

    goto :goto_0

    :cond_0
    new-instance v0, Lcom/android/camera/CameraSize;

    invoke-direct {v0, p2, p1}, Lcom/android/camera/CameraSize;-><init>(II)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mVideoSize:Lcom/android/camera/CameraSize;

    :goto_0
    return-void
.end method

.method private setWaterMark()V
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    const/4 v1, 0x0

    if-nez v0, :cond_5

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fQ()Z

    move-result v0

    if-nez v0, :cond_5

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v0, :cond_0

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa5

    if-eq v0, v2, :cond_5

    invoke-static {}, Lcom/android/camera/CameraSettings;->getShaderEffect()I

    move-result v0

    sget v2, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-ne v0, v2, :cond_5

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mHasAiSceneFilterEffect:Z

    if-nez v0, :cond_5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTiltShiftOn()Z

    move-result v0

    if-nez v0, :cond_5

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fS()Z

    move-result v0

    if-eqz v0, :cond_5

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xab

    if-ne v0, v2, :cond_1

    goto :goto_2

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result v0

    const/4 v2, 0x1

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setDualCamWaterMarkEnable(Z)V

    goto :goto_0

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCameraWaterMarkOpen()Z

    move-result v0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setDualCamWaterMarkEnable(Z)V

    goto :goto_0

    :cond_3
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setDualCamWaterMarkEnable(Z)V

    :goto_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isTimeWaterMarkOpen()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setTimeWaterMarkEnable(Z)V

    invoke-static {}, Lcom/android/camera/Util;->getTimeWatermark()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCaptureWaterMarkStr:Ljava/lang/String;

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCaptureWaterMarkStr:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setTimeWatermarkValue(Ljava/lang/String;)V

    goto :goto_1

    :cond_4
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCaptureWaterMarkStr:Ljava/lang/String;

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setTimeWaterMarkEnable(Z)V

    :goto_1
    return-void

    :cond_5
    :goto_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setDualCamWaterMarkEnable(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setTimeWaterMarkEnable(Z)V

    return-void
.end method

.method private shouldApplyNormalWideLDC()Z
    .locals 3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->shouldNormalWideLDCBeVisibleInMode(I)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    if-eqz v0, :cond_1

    return v1

    :cond_1
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v2

    if-ne v0, v2, :cond_2

    return v1

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_3

    return v1

    :cond_3
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZoomRatioBetweenUltraAndWide()Z

    move-result v0

    if-eqz v0, :cond_4

    return v1

    :cond_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->isNormalWideLDCEnabled()Z

    move-result v0

    return v0
.end method

.method private shouldApplyUltraWideLDC()Z
    .locals 3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->shouldUltraWideLDCBeVisibleInMode(I)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hH()Z

    move-result v0

    if-nez v0, :cond_1

    const/4 v0, 0x1

    return v0

    :cond_1
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v2

    if-ne v0, v2, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraWideLDCEnabled()Z

    move-result v0

    return v0

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZoomRatioBetweenUltraAndWide()Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraWideLDCEnabled()Z

    move-result v0

    return v0

    :cond_3
    return v1
.end method

.method private shouldChangeAiScene(I)Z
    .locals 6

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentDetectedScene:I

    if-eq v0, p1, :cond_1

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    iget-wide v4, p0, Lcom/android/camera/module/Camera2Module;->mLastChangeSceneTime:J

    sub-long/2addr v2, v4

    const-wide/16 v4, 0x12c

    cmp-long v0, v2, v4

    if-lez v0, :cond_1

    iput p1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentDetectedScene:I

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mLastChangeSceneTime:J

    const/4 p1, 0x1

    return p1

    :cond_1
    return v1

    :cond_2
    :goto_0
    return v1
.end method

.method private shouldDoMultiFrameCapture()Z
    .locals 4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->iz()Z

    move-result v0

    if-nez v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v2, "shouldDoMultiFrameCapture: return false in moon mode"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->useLegacyFlashStrategy()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isNeedFlashOn()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isFlashSupported()Z

    move-result v0

    if-eqz v0, :cond_1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v2, "shouldDoMultiFrameCapture: return false in case of flash"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isHdr()Z

    move-result v0

    if-nez v0, :cond_3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mShouldDoMFNR:Z

    if-nez v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isSuperResolution()Z

    move-result v0

    if-nez v0, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v0

    if-eqz v0, :cond_2

    goto :goto_0

    :cond_2
    goto :goto_1

    :cond_3
    :goto_0
    const/4 v1, 0x1

    :goto_1
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "shouldDoMultiFrameCapture: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1
.end method

.method private showMoonMode(Z)Z
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isUltraWideConfigOpen(I)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_1

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xac

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_2

    const/4 v2, 0x1

    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mEnteringMoonMode:Z

    invoke-interface {v0, v1, p1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertMoonModeSelector(IZ)V

    if-nez p1, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateMoonNight()V

    goto :goto_0

    :cond_1
    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->updateMoon(Z)V

    :goto_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "(moon_mode) show moon mode,button check status:"

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v2

    :cond_2
    return v1

    :cond_3
    :goto_1
    return v1
.end method

.method private showPostCaptureAlert()V
    .locals 2

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->enableCameraControls(Z)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->removeMessages()V

    const/4 v1, 0x1

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->stopFaceDetection(Z)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->pausePreview()V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v1, v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setEffectViewVisible(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa0

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    const/4 v1, 0x6

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->delegateEvent(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->resetMetaDataManager()V

    return-void
.end method

.method private startCount(II)V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->checkShutterCondition()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0, p2}, Lcom/android/camera/module/Camera2Module;->setTriggerMode(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->tryRemoveCountDownMessage()V

    sget-object p2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v1, "startCount: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p2, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const-wide/16 v0, 0x1

    sget-object p2, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-static {v0, v1, p2}, Lio/reactivex/Observable;->interval(JLjava/util/concurrent/TimeUnit;)Lio/reactivex/Observable;

    move-result-object p2

    int-to-long v0, p1

    invoke-virtual {p2, v0, v1}, Lio/reactivex/Observable;->take(J)Lio/reactivex/Observable;

    move-result-object p2

    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v0

    invoke-virtual {p2, v0}, Lio/reactivex/Observable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Observable;

    move-result-object p2

    new-instance v0, Lcom/android/camera/module/Camera2Module$8;

    invoke-direct {v0, p0, p1}, Lcom/android/camera/module/Camera2Module$8;-><init>(Lcom/android/camera/module/Camera2Module;I)V

    invoke-virtual {p2, v0}, Lio/reactivex/Observable;->subscribe(Lio/reactivex/Observer;)V

    return-void
.end method

.method private startCpuBoost()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

    if-nez v0, :cond_0

    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    if-eqz v0, :cond_0

    new-instance v0, Lcom/xiaomi/camera/core/MtkBoost;

    invoke-direct {v0}, Lcom/xiaomi/camera/core/MtkBoost;-><init>()V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

    invoke-interface {v0}, Lcom/xiaomi/camera/core/BaseBoostFramework;->startBoost()Z

    :cond_1
    return-void
.end method

.method private startLensActivity()V
    .locals 3

    invoke-static {}, Lcom/mi/config/b;->gQ()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mLensStatus:I

    if-eqz v0, :cond_1

    return-void

    :cond_1
    const/4 v0, 0x0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1, v2, v0}, Lcom/google/lens/sdk/LensApi;->launchLensActivity(Landroid/app/Activity;I)V

    goto :goto_0

    :cond_2
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v1, "start ai lens"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :try_start_0
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "android.media.action.XIAOAI_CONTROL"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    const-string v1, "com.xiaomi.lens"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    const-string v1, "preview_width"

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v2, v2, Lcom/android/camera/CameraSize;->width:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "preview_height"

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v2, v2, Lcom/android/camera/CameraSize;->height:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/Camera;->startActivity(Landroid/content/Intent;)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    const/high16 v1, 0x7f050000

    const v2, 0x7f050001

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/Camera;->overridePendingTransition(II)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "onClick: occur a exception"

    invoke-static {v1, v2, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_0
    return-void
.end method

.method private startLiveShotAnimation()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorderStateLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v2, Lcom/android/camera/module/Camera2Module$5;

    invoke-direct {v2, p0}, Lcom/android/camera/module/Camera2Module$5;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_0
    monitor-exit v0

    return-void

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method private startNormalCapture(I)V
    .locals 5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "startNormalCapture mode -> "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->prepareNormalCapture()V

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isParallelSessionEnable()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v0

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mTotalJpegCallbackNum:I

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1, p0, v0, v2, v3}, Lcom/android/camera2/Camera2Proxy;->captureGroupShotPictures(Lcom/android/camera2/Camera2Proxy$PictureCallback;Lcom/xiaomi/camera/core/ParallelCallback;ILandroid/content/Context;)V

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mBlockQuickShot:Z

    goto/16 :goto_2

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v2, 0x32

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-direct {p0, v3}, Lcom/android/camera/module/Camera2Module;->calculateTimeout(I)J

    move-result-wide v3

    invoke-virtual {v0, v2, v3, v4}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mQuickShotAnimateEnable:Z

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setQuickShotAnimation(Z)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->in()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    const/16 v2, 0xa3

    if-ne v0, v2, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v0

    const/high16 v2, 0x3f800000    # 1.0f

    cmpl-float v0, v0, v2

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setFlawDetectEnable(Z)V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v2, 0x0

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setFlawDetectEnable(Z)V

    :cond_2
    :goto_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v2}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v2

    invoke-virtual {v0, p0, v2}, Lcom/android/camera2/Camera2Proxy;->takePicture(Lcom/android/camera2/Camera2Proxy$PictureCallback;Lcom/xiaomi/camera/core/ParallelCallback;)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->needQuickShot()Z

    move-result v0

    if-eqz v0, :cond_3

    const/16 v0, 0x5a

    if-eq p1, v0, :cond_3

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v0, "startNormalCapture force set CameraStateConstant.IDLE"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->enableCameraControls(Z)V

    goto :goto_1

    :cond_3
    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mBlockQuickShot:Z

    :goto_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result p1

    if-eqz p1, :cond_4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->hm()Z

    move-result p1

    if-eqz p1, :cond_4

    const p1, 0x8007

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    if-eq p1, v0, :cond_4

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    new-instance v0, Lcom/android/camera/module/-$$Lambda$Camera2Module$6RqRQ0o-4FJHIru0LO8WaynbJeQ;

    invoke-direct {v0, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$6RqRQ0o-4FJHIru0LO8WaynbJeQ;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->registerCaptureCallback(Lcom/android/camera2/Camera2Proxy$CaptureCallback;)Z

    :cond_4
    :goto_2
    return-void
.end method

.method private stopCpuBoost()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

    invoke-interface {v0}, Lcom/xiaomi/camera/core/BaseBoostFramework;->stopBoost()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCpuBoost:Lcom/xiaomi/camera/core/BaseBoostFramework;

    :cond_0
    return-void
.end method

.method private stopMultiSnap()V
    .locals 5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v1, "stopMultiSnap: start"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x31

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mLastCaptureTime:J

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->captureAbortBurst()V

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isUbiFocus()Z

    move-result v0

    const/4 v2, 0x1

    if-nez v0, :cond_2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    goto :goto_0

    :cond_2
    move v0, v2

    :goto_0
    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v3}, Lcom/android/camera/MutexModeManager;->isUbiFocus()Z

    move-result v3

    xor-int/2addr v3, v2

    invoke-virtual {p0, v0, v3}, Lcom/android/camera/module/Camera2Module;->trackGeneralInfo(IZ)V

    new-instance v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;

    invoke-direct {v4}, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;-><init>()V

    iput v0, v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->takenNum:I

    iput-boolean v3, v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->burst:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLocation:Landroid/location/Location;

    if-eqz v0, :cond_3

    goto :goto_1

    :cond_3
    move v2, v1

    :goto_1
    iput-boolean v2, v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->location:Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getCurrentAiSceneName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->aiSceneName:Ljava/lang/String;

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnteringMoonMode:Z

    iput-boolean v0, v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isEnteringMoon:Z

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    iput-boolean v0, v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isSelectMoonMode:Z

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mShowSuperNightHint:Z

    iput-boolean v0, v4, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isSuperNightInCaptureMode:Z

    invoke-virtual {p0, v4}, Lcom/android/camera/module/Camera2Module;->trackPictureTaken(Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->animateCapture()V

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mUpdateImageTitle:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x30

    const-wide/16 v2, 0x320

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    return-void
.end method

.method private trackAISceneChanged(II)V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-static {p1, p2, v0}, Lcom/android/camera/statistic/CameraStatUtil;->trackAISceneChanged(IILandroid/content/res/Resources;)V

    return-void
.end method

.method private trackBeautyInfo(IZLcom/android/camera/fragment/beauty/BeautyValues;)V
    .locals 0

    nop

    if-eqz p2, :cond_0

    const-string/jumbo p2, "\u524d\u6444"

    goto :goto_0

    :cond_0
    const-string/jumbo p2, "\u540e\u6444"

    :goto_0
    invoke-static {p1, p2, p3}, Lcom/android/camera/statistic/CameraStatUtil;->trackBeautyInfo(ILjava/lang/String;Lcom/android/camera/fragment/beauty/BeautyValues;)V

    return-void
.end method

.method private trackManualInfo(I)V
    .locals 4

    const-string v0, "pref_camera_whitebalance_key"

    const v1, 0x7f09006b

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "pref_qc_camera_exposuretime_key"

    const v2, 0x7f090142

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v1, v2}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "pref_qc_camera_iso_key"

    const v3, 0x7f0900b1

    invoke-virtual {p0, v3}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {p1, v0, v1, v2, v3}, Lcom/android/camera/statistic/CameraStatUtil;->trackPictureTakenInManual(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtil;->trackUltraWideManualTaken(I)V

    return-void
.end method

.method private unlockAEAF()V
    .locals 5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v1, "unlockAEAF"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mAeLockSupported:Z

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->unlockExposure()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v1

    invoke-virtual {v1}, Lcom/mi/config/a;->hG()Z

    move-result v1

    if-nez v1, :cond_0

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mUltraWideAELocked:Z

    if-eqz v1, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->getFocusMode()Ljava/lang/String;

    move-result-object v1

    sget-object v2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v4, "unlockAEAF: focusMode = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->setFocusMode(Ljava/lang/String;)V

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mUltraWideAELocked:Z

    :cond_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v1, v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setAeAwbLock(Z)V

    :cond_1
    return-void
.end method

.method private unregisterSensor()V
    .locals 2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGradienterOn()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/camera/SensorStateManager;->setGradienterEnabled(Z)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/camera/SensorStateManager;->setLieIndicatorEnabled(Z)V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mIsShowLyingDirectHintStatus:I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x3a

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    return-void
.end method

.method private updateASDDirtyDetect()V
    .locals 5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isLensDirtyDetectEnabled()Z

    move-result v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v1

    const-string v2, "pref_lens_dirty_tip"

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    const v4, 0x7f10001b

    invoke-virtual {v3, v4}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v3

    invoke-virtual {v1, v2, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_0

    if-eqz v1, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->shouldShowLensDirtyDetectHint()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    invoke-virtual {v2, v0}, Lcom/android/camera2/Camera2Proxy;->setAsdDirtyEnable(Z)V

    return-void
.end method

.method private updateAiScene()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFunctionParseAiScene:Lcom/android/camera/module/loader/FunctionParseAiScene;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFunctionParseAiScene:Lcom/android/camera/module/loader/FunctionParseAiScene;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/FunctionParseAiScene;->resetScene()V

    :cond_0
    const/4 v0, 0x0

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v1}, Lcom/android/camera/CameraSettings;->getAiSceneOpen(I)Z

    move-result v1

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v1

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->isScreenSlideOff()Z

    move-result v1

    if-eqz v1, :cond_1

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    :cond_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    invoke-virtual {v1, v2}, Lcom/android/camera2/Camera2Proxy;->setASD(Z)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isCapture()Z

    move-result v1

    if-nez v1, :cond_3

    :cond_2
    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    if-nez v1, :cond_4

    :cond_3
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    invoke-virtual {v1, v2}, Lcom/android/camera2/Camera2Proxy;->setCameraAI30(Z)V

    :cond_4
    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->setAiSceneEffect(I)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setASDScene(I)V

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    if-eqz v0, :cond_5

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/16 v1, 0x12c

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setASDPeriod(I)V

    goto :goto_0

    :cond_5
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->hideSceneSelector()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateFlashPreference()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateBeauty()V

    :goto_0
    return-void
.end method

.method private updateAlgorithmName()V
    .locals 1

    nop

    invoke-static {}, Lcom/mi/config/b;->ks()Z

    move-result v0

    if-nez v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isBokehEnabled()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->gP()I

    move-result v0

    if-lez v0, :cond_0

    const-string/jumbo v0, "soft-portrait-enc"

    goto :goto_0

    :cond_0
    const-string/jumbo v0, "soft-portrait"

    :goto_0
    goto :goto_1

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v0

    if-eqz v0, :cond_2

    const-string v0, "portrait"

    goto :goto_1

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->getAlgorithmName()Ljava/lang/String;

    move-result-object v0

    goto :goto_1

    :cond_3
    const/4 v0, 0x0

    :goto_1
    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mAlgorithmName:Ljava/lang/String;

    return-void
.end method

.method private updateBeauty()V
    .locals 6

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa3

    if-eq v0, v1, :cond_0

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa5

    if-eq v0, v1, :cond_0

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xab

    if-eq v0, v1, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    if-nez v0, :cond_1

    new-instance v0, Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-direct {v0}, Lcom/android/camera/fragment/beauty/BeautyValues;-><init>()V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0, v1}, Lcom/android/camera/CameraSettings;->initBeautyValues(Lcom/android/camera/fragment/beauty/BeautyValues;I)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigBeauty()Lcom/android/camera/data/data/config/ComponentConfigBeauty;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigBeauty;->isClosed(I)Z

    move-result v0

    if-nez v0, :cond_4

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 v1, 0x19

    if-ne v0, v1, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->isFaceBeautyOn(Lcom/android/camera/fragment/beauty/BeautyValues;)Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningShine()Lcom/android/camera/data/data/runing/ComponentRunningShine;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->supportBeautyLevel()Z

    move-result v1

    if-eqz v1, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    const-string v1, "i:1"

    iput-object v1, v0, Lcom/android/camera/fragment/beauty/BeautyValues;->mBeautyLevel:Ljava/lang/String;

    goto :goto_0

    :cond_2
    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->supportSmoothLevel()Z

    move-result v0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    const/16 v1, 0xa

    iput v1, v0, Lcom/android/camera/fragment/beauty/BeautyValues;->mBeautySkinSmooth:I

    :cond_3
    :goto_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v2, "Human scene mode detected, auto set beauty level from %s to %s"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    const-string v5, "i:0"

    aput-object v5, v3, v4

    const/4 v4, 0x1

    iget-object v5, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    iget-object v5, v5, Lcom/android/camera/fragment/beauty/BeautyValues;->mBeautyLevel:Ljava/lang/String;

    aput-object v5, v3, v4

    invoke-static {v1, v2, v3}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_4
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "updateBeauty(): "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setBeautyValues(Lcom/android/camera/fragment/beauty/BeautyValues;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-virtual {v0}, Lcom/android/camera/fragment/beauty/BeautyValues;->isBeautyBodyOn()Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsBeautyBodySlimOn:Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFaceAgeAnalyze()V

    return-void
.end method

.method private updateBokeh()V
    .locals 4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentBokeh()Lcom/android/camera/data/data/config/ComponentConfigBokeh;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isPortraitModule()Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_0

    const-string v1, "on"

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v3}, Lcom/android/camera/data/data/config/ComponentConfigBokeh;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setMiBokeh(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setRearBokehEnable(Z)V

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isSingleCamera()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setMiBokeh(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setRearBokehEnable(Z)V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setMiBokeh(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setRearBokehEnable(Z)V

    :goto_0
    return-void
.end method

.method private updateCaptureTriggerFlow()V
    .locals 0

    return-void
.end method

.method private updateContrast()V
    .locals 2

    const v0, 0x7f09009d

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setContrast(I)V

    return-void
.end method

.method private updateDecodePreview()V
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->scanQRCodeEnabled()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {}, Lcom/mi/config/b;->gX()Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "updateDecodePreview: PreviewDecodeManager AlgorithmPreviewSize = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2}, Lcom/android/camera2/Camera2Proxy;->getAlgorithmPreviewSize()Lcom/android/camera/CameraSize;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/zxing/PreviewDecodeManager;->getPreviewCallback()Lcom/android/camera2/Camera2Proxy$PreviewCallback;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->startPreviewCallback(Lcom/android/camera2/Camera2Proxy$PreviewCallback;)V

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/zxing/PreviewDecodeManager;->startDecode()V

    :cond_1
    return-void
.end method

.method private updateDeviceOrientation()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setDeviceOrientation(I)V

    return-void
.end method

.method private updateEnablePreviewThumbnail(Z)V
    .locals 3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/4 v1, 0x0

    const/16 v2, 0xa7

    if-ne v0, v2, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v2, "pref_camera_peak_key"

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa5

    if-eq v0, v2, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa3

    if-ne v0, v2, :cond_2

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v2, "pref_camera_tilt_shift_mode"

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_2

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    goto :goto_0

    :cond_2
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMiMovieOpen(I)Z

    move-result v0

    if-eqz v0, :cond_3

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    goto :goto_0

    :cond_3
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isPreviewThumbnailWhenFlash()Z

    move-result v0

    if-nez v0, :cond_4

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    goto :goto_0

    :cond_4
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-eqz v0, :cond_5

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    goto :goto_0

    :cond_5
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v0, :cond_6

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    if-nez v0, :cond_6

    if-nez p1, :cond_6

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    if-nez p1, :cond_7

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->enablePreviewAsThumbnail()Z

    move-result p1

    if-eqz p1, :cond_7

    :cond_6
    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    :cond_7
    :goto_0
    return-void
.end method

.method private updateExposureTime()V
    .locals 4

    const-string v0, "pref_qc_camera_exposuretime_key"

    const v1, 0x7f090142

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-static {v0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v2

    invoke-virtual {v1, v2, v3}, Lcom/android/camera2/Camera2Proxy;->setExposureTime(J)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateManualEvAdjust()V

    return-void
.end method

.method private updateEyeLight()V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->gq()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->getEyeLightType()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setEyeLight(I)V

    :cond_0
    return-void
.end method

.method private updateFNumber()V
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->isSupportBokehAdjust()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->readFNumber()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setFNumber(Ljava/lang/String;)V

    return-void
.end method

.method private updateFace()V
    .locals 6

    nop

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-nez v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isUbiFocus()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_1

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isMagicMirrorOn()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isPortraitModeBackOn()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->showGenderAge()Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    const-string v3, "pref_camera_facedetection_key"

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    const v5, 0x7f100006

    invoke-virtual {v4, v5}, Landroid/content/res/Resources;->getBoolean(I)Z

    move-result v4

    invoke-virtual {v0, v3, v4}, Lcom/android/camera/data/data/global/DataItemGlobal;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTiltShiftOn()Z

    move-result v3

    if-eqz v3, :cond_4

    nop

    move v3, v1

    goto :goto_2

    :cond_2
    :goto_0
    nop

    move v0, v2

    move v3, v0

    goto :goto_2

    :cond_3
    :goto_1
    nop

    move v0, v1

    :cond_4
    move v3, v2

    :goto_2
    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz v4, :cond_7

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz v0, :cond_6

    if-nez v3, :cond_5

    goto :goto_3

    :cond_5
    move v3, v1

    goto :goto_4

    :cond_6
    :goto_3
    move v3, v2

    :goto_4
    invoke-interface {v4, v3}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setSkipDrawFace(Z)V

    :cond_7
    if-eqz v0, :cond_8

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    if-nez v0, :cond_9

    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->startFaceDetection()V

    goto :goto_5

    :cond_8
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    if-eqz v0, :cond_9

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->stopFaceDetection(Z)V

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    :cond_9
    :goto_5
    return-void
.end method

.method private updateFaceAgeAnalyze()V
    .locals 2

    nop

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v1, "pref_camera_show_gender_age_key"

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGenderAgeOn:Z

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGenderAgeOn:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->isFaceBeautyOn(Lcom/android/camera/fragment/beauty/BeautyValues;)Z

    move-result v0

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setFaceAgeAnalyze(Z)V

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/effect/EffectController;->hasEffect()Z

    move-result v0

    if-eqz v0, :cond_2

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->prepareEffectProcessor(I)V

    :cond_2
    return-void
.end method

.method private updateFaceScore()V
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v1, "pref_camera_magic_mirror_key"

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMagicMirrorOn:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsMagicMirrorOn:Z

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setFaceScore(Z)V

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/effect/EffectController;->hasEffect()Z

    move-result v0

    if-eqz v0, :cond_0

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->prepareEffectProcessor(I)V

    :cond_0
    return-void
.end method

.method private updateFilter()V
    .locals 1

    invoke-static {}, Lcom/android/camera/CameraSettings;->getShaderEffect()I

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->setEffectFilter(I)V

    return-void
.end method

.method private updateFocusArea()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result v0

    if-nez v0, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_2

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCropRegion()Landroid/graphics/Rect;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActiveArraySize()Landroid/graphics/Rect;

    move-result-object v1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v2}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v2

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v3, v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->getMeteringAreas(Landroid/graphics/Rect;Landroid/graphics/Rect;)[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v3

    if-eqz v3, :cond_1

    const/4 v3, 0x1

    goto :goto_0

    :cond_1
    const/4 v3, 0x0

    :goto_0
    invoke-virtual {v2, v3}, Lcom/android/camera/SensorStateManager;->setFocusSensorEnabled(Z)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v3, v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->getMeteringAreas(Landroid/graphics/Rect;Landroid/graphics/Rect;)[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->setAERegions([Landroid/hardware/camera2/params/MeteringRectangle;)V

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mFocusAreaSupported:Z

    if-eqz v2, :cond_2

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v3, v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->getFocusAreas(Landroid/graphics/Rect;Landroid/graphics/Rect;)[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v0

    invoke-virtual {v2, v0}, Lcom/android/camera2/Camera2Proxy;->setAFRegions([Landroid/hardware/camera2/params/MeteringRectangle;)V

    goto :goto_1

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->resumePreview()I

    :goto_1
    return-void

    :cond_3
    :goto_2
    return-void
.end method

.method private updateFocusMode()V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->removeMessages()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    const-string v1, "manual"

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setFocusMode(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getFocusMode()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setFocusMode(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    :goto_0
    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setFocusMode(Ljava/lang/String;)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFocusModeSwitching()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result v1

    if-eqz v1, :cond_1

    const/4 v1, 0x0

    invoke-static {v1}, Lcom/android/camera/CameraSettings;->setFocusModeSwitching(Z)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->resetFocusStateIfNeeded()V

    :cond_1
    const-string v1, "manual"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->getFocusPosition()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v1}, Lcom/android/camera2/CameraCapabilities;->getMinimumFocusDistance()F

    move-result v1

    int-to-float v0, v0

    mul-float/2addr v1, v0

    const/high16 v0, 0x447a0000    # 1000.0f

    div-float/2addr v1, v0

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-eqz v0, :cond_2

    const/high16 v1, 0x3f000000    # 0.5f

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setFocusDistance(F)V

    :cond_3
    return-void
.end method

.method private updateFpsRange()V
    .locals 7

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedFpsRange()[Landroid/util/Range;

    move-result-object v0

    const/4 v1, 0x0

    aget-object v2, v0, v1

    array-length v3, v0

    :goto_0
    if-ge v1, v3, :cond_2

    aget-object v4, v0, v1

    invoke-virtual {v2}, Landroid/util/Range;->getUpper()Ljava/lang/Comparable;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    invoke-virtual {v4}, Landroid/util/Range;->getUpper()Ljava/lang/Comparable;

    move-result-object v6

    check-cast v6, Ljava/lang/Integer;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v6

    if-ge v5, v6, :cond_0

    goto :goto_1

    :cond_0
    invoke-virtual {v2}, Landroid/util/Range;->getUpper()Ljava/lang/Comparable;

    move-result-object v5

    invoke-virtual {v4}, Landroid/util/Range;->getUpper()Ljava/lang/Comparable;

    move-result-object v6

    if-ne v5, v6, :cond_1

    invoke-virtual {v2}, Landroid/util/Range;->getLower()Ljava/lang/Comparable;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    invoke-virtual {v4}, Landroid/util/Range;->getLower()Ljava/lang/Comparable;

    move-result-object v6

    check-cast v6, Ljava/lang/Integer;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v6

    if-ge v5, v6, :cond_1

    nop

    :goto_1
    move-object v2, v4

    :cond_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_2
    sget-boolean v0, Lcom/mi/config/b;->sF:Z

    if-eqz v0, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isPortraitModeBackOn()Z

    move-result v0

    if-eqz v0, :cond_3

    new-instance v0, Landroid/util/Range;

    const/16 v1, 0x1e

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-direct {v0, v2, v1}, Landroid/util/Range;-><init>(Ljava/lang/Comparable;Ljava/lang/Comparable;)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setFpsRange(Landroid/util/Range;)V

    return-void

    :cond_3
    return-void
.end method

.method private updateFrontMirror()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isFrontMirror()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setFrontMirror(Z)V

    return-void
.end method

.method private updateHDR(Ljava/lang/String;)V
    .locals 3

    const-string v0, "auto"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->isDetectedInHdr:Z

    :cond_0
    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->getMutexHdrMode(Ljava/lang/String;)I

    move-result p1

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->stopObjectTracking(Z)V

    if-eqz p1, :cond_1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v2, p1}, Lcom/android/camera/MutexModeManager;->setMutexMode(I)V

    goto :goto_0

    :cond_1
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {p1}, Lcom/android/camera/MutexModeManager;->isHdr()Z

    move-result p1

    if-eqz p1, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->resetMutexModeManually()V

    :cond_2
    :goto_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result p1

    if-eqz p1, :cond_3

    invoke-direct {p0, v1, v0}, Lcom/android/camera/module/Camera2Module;->isTriggerQcfaModeChange(ZZ)Z

    move-result p1

    if-eqz p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v0, 0x2c

    invoke-virtual {p1, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :cond_3
    return-void
.end method

.method private updateISO()V
    .locals 3

    const v0, 0x7f0900b1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "pref_qc_camera_iso_key"

    invoke-direct {p0, v1, v0}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    if-eqz v1, :cond_0

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-static {v1, v2}, Lcom/android/camera/Util;->parseInt(Ljava/lang/String;I)I

    move-result v1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v2}, Lcom/android/camera2/CameraCapabilities;->getMaxIso()I

    move-result v2

    invoke-static {v1, v2}, Ljava/lang/Math;->min(II)I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setISO(I)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setISO(I)V

    :goto_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateManualEvAdjust()V

    return-void
.end method

.method private updateJpegQuality()V
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->getJpegQuality(Z)I

    move-result v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentUltraPixel()Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isRear108MPSwitchOn()Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 v1, 0x0

    const/16 v2, 0x5a

    invoke-static {v0, v1, v2}, Lcom/android/camera/Util;->clamp(III)I

    move-result v0

    :cond_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setJpegQuality(I)V

    return-void
.end method

.method private updateJpegThumbnailSize()V
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getJpegThumbnailSize()Lcom/android/camera/CameraSize;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setJpegThumbnailSize(Lcom/android/camera/CameraSize;)V

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "thumbnailSize="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private updateLiveShot()V
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->gz()Z

    move-result v0

    if-eqz v0, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa3

    if-ne v0, v1, :cond_1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->startLiveShot()V

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->stopLiveShot(Z)V

    :cond_1
    :goto_0
    return-void
.end method

.method private updateMacroMode()V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setMacroMode(Z)V

    return-void
.end method

.method private updateMfnr(Z)V
    .locals 7

    nop

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isUseSwMfnr()Z

    move-result v0

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_0

    nop

    goto/16 :goto_1

    :cond_0
    if-nez p1, :cond_1

    nop

    goto/16 :goto_1

    :cond_1
    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v0, 0xa7

    if-ne p1, v0, :cond_4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->hu()Z

    move-result p1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentUltraPixel()Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isRear108MPSwitchOn()Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isSensorRawStreamRequired()Z

    move-result v0

    if-nez v0, :cond_3

    if-eqz p1, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isSingleCamera()Z

    move-result p1

    if-nez p1, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isStandaloneMacroCamera()Z

    move-result p1

    if-nez p1, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result p1

    if-eqz p1, :cond_3

    :cond_2
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz p1, :cond_3

    const-string p1, "pref_qc_camera_exposuretime_key"

    const v0, 0x7f090142

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v3

    const-wide/32 v5, 0xee6b280

    cmp-long p1, v3, v5

    if-gez p1, :cond_3

    goto :goto_0

    :cond_3
    nop

    goto :goto_1

    :cond_4
    sget-boolean p1, Lcom/mi/config/b;->sE:Z

    if-eqz p1, :cond_5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualCamera()Z

    move-result p1

    if-nez p1, :cond_5

    nop

    goto :goto_1

    :cond_5
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {p1}, Lcom/android/camera/MutexModeManager;->isNormal()Z

    move-result p1

    if-nez p1, :cond_6

    nop

    goto :goto_1

    :cond_6
    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result p1

    if-eqz p1, :cond_7

    nop

    goto :goto_1

    :cond_7
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result p1

    if-eqz p1, :cond_8

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->enableFrontMFNR()Z

    move-result p1

    if-nez p1, :cond_8

    nop

    goto :goto_1

    :cond_8
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->fF()Z

    move-result p1

    if-nez p1, :cond_9

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result p1

    const/high16 v0, 0x3f800000    # 1.0f

    cmpl-float p1, p1, v0

    if-eqz p1, :cond_9

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result p1

    if-nez p1, :cond_9

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZoomRatioBetweenUltraAndWide()Z

    move-result p1

    if-nez p1, :cond_9

    goto :goto_1

    :cond_9
    :goto_0
    move v2, v1

    :goto_1
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz p1, :cond_a

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setMfnr to "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v2}, Lcom/android/camera2/Camera2Proxy;->setMfnr(Z)V

    :cond_a
    return-void
.end method

.method private updateMiMovie()V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMiMovieOpen(I)Z

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setMiMovie(Z)V

    return-void
.end method

.method private updateMute()V
    .locals 0

    return-void
.end method

.method private updateNormalWideLDC()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->shouldApplyNormalWideLDC()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setNormalWideLDC(Z)V

    return-void
.end method

.method private updateOIS()V
    .locals 8

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualCamera()Z

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isTeleOISSupported()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v0

    const/high16 v3, 0x3f800000    # 1.0f

    cmpl-float v0, v0, v3

    if-lez v0, :cond_0

    move v0, v2

    goto :goto_0

    :cond_0
    nop

    move v0, v1

    :goto_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isPortraitModeBackOn()Z

    move-result v3

    nop

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v5, 0xa7

    if-ne v4, v5, :cond_1

    const-string v4, "pref_qc_camera_exposuretime_key"

    const v5, 0x7f090142

    invoke-virtual {p0, v5}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v4, v5}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v4

    const-wide/32 v6, 0x3b9aca00

    cmp-long v4, v4, v6

    if-lez v4, :cond_1

    nop

    move v4, v2

    goto :goto_1

    :cond_1
    move v4, v1

    :goto_1
    if-nez v0, :cond_3

    if-nez v3, :cond_3

    if-eqz v4, :cond_2

    goto :goto_2

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setEnableOIS(Z)V

    goto :goto_3

    :cond_3
    :goto_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setEnableOIS(Z)V

    :goto_3
    return-void
.end method

.method private updateOutputSize(Lcom/android/camera/CameraSize;)V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa5

    if-ne v1, v0, :cond_1

    iget v0, p1, Lcom/android/camera/CameraSize;->width:I

    iget v1, p1, Lcom/android/camera/CameraSize;->height:I

    if-le v0, v1, :cond_0

    iget p1, p1, Lcom/android/camera/CameraSize;->height:I

    goto :goto_0

    :cond_0
    iget p1, p1, Lcom/android/camera/CameraSize;->width:I

    :goto_0
    new-instance v0, Lcom/android/camera/CameraSize;

    invoke-direct {v0, p1, p1}, Lcom/android/camera/CameraSize;-><init>(II)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    goto :goto_1

    :cond_1
    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    :goto_1
    return-void
.end method

.method private updatePictureAndPreviewSize()V
    .locals 13

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    const/16 v1, 0x100

    const/16 v2, 0x23

    if-eqz v0, :cond_0

    nop

    move v0, v2

    goto :goto_0

    :cond_0
    nop

    move v0, v1

    :goto_0
    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3}, Lcom/android/camera2/Camera2Proxy;->getSATSubCameraIds()[I

    move-result-object v3

    const/4 v4, 0x1

    const/4 v5, 0x0

    if-eqz v3, :cond_1

    move v6, v4

    goto :goto_1

    :cond_1
    nop

    move v6, v5

    :goto_1
    const/4 v7, 0x3

    const/4 v8, 0x2

    if-eqz v6, :cond_d

    sget-object v9, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "[SAT] camera list: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v3}, Ljava/util/Arrays;->toString([I)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    array-length v9, v3

    move v10, v5

    :goto_2
    if-ge v10, v9, :cond_b

    aget v11, v3, v10

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v12

    if-ne v11, v12, :cond_3

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-nez v11, :cond_2

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v11

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v12

    invoke-virtual {v11, v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    :cond_2
    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz v11, :cond_a

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    iget v12, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    invoke-virtual {v11, v12}, Lcom/android/camera2/CameraCapabilities;->setOperatingMode(I)V

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v11, v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v11

    invoke-virtual {p0, v11}, Lcom/android/camera/module/Camera2Module;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraWidePictureSize:Lcom/android/camera/CameraSize;

    goto/16 :goto_3

    :cond_3
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result v12

    if-ne v11, v12, :cond_5

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWideCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-nez v11, :cond_4

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v11

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result v12

    invoke-virtual {v11, v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWideCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    :cond_4
    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWideCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz v11, :cond_a

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWideCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    iget v12, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    invoke-virtual {v11, v12}, Lcom/android/camera2/CameraCapabilities;->setOperatingMode(I)V

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWideCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v11, v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v11

    invoke-virtual {p0, v11}, Lcom/android/camera/module/Camera2Module;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWidePictureSize:Lcom/android/camera/CameraSize;

    goto/16 :goto_3

    :cond_5
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxCameraId()I

    move-result v12

    if-ne v11, v12, :cond_7

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-nez v11, :cond_6

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v11

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxCameraId()I

    move-result v12

    invoke-virtual {v11, v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    :cond_6
    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz v11, :cond_a

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    iget v12, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    invoke-virtual {v11, v12}, Lcom/android/camera2/CameraCapabilities;->setOperatingMode(I)V

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v11, v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v11

    invoke-virtual {p0, v11}, Lcom/android/camera/module/Camera2Module;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTelePictureSize:Lcom/android/camera/CameraSize;

    goto :goto_3

    :cond_7
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraTeleCameraId()I

    move-result v12

    if-ne v11, v12, :cond_a

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-nez v11, :cond_8

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v11

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v12

    invoke-virtual {v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraTeleCameraId()I

    move-result v12

    invoke-virtual {v11, v12}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    :cond_8
    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz v11, :cond_9

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    iget v12, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    invoke-virtual {v11, v12}, Lcom/android/camera2/CameraCapabilities;->setOperatingMode(I)V

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraTeleCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v11, v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v11

    invoke-virtual {p0, v11}, Lcom/android/camera/module/Camera2Module;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object v11

    iput-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraTelePictureSize:Lcom/android/camera/CameraSize;

    :cond_9
    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v12, p0, Lcom/android/camera/module/Camera2Module;->mUltraTelePictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v11, v12}, Lcom/android/camera2/Camera2Proxy;->setUltraTelePictureSize(Lcom/android/camera/CameraSize;)V

    :cond_a
    :goto_3
    add-int/lit8 v10, v10, 0x1

    goto/16 :goto_2

    :cond_b
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hW()Z

    move-result v0

    if-eqz v0, :cond_c

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    sget-object v3, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string/jumbo v9, "ultraWideSize: %s, wideSize: %s, teleSize: %s, ultraTeleSize:%s"

    const/4 v10, 0x4

    new-array v10, v10, [Ljava/lang/Object;

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraWidePictureSize:Lcom/android/camera/CameraSize;

    aput-object v11, v10, v5

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWidePictureSize:Lcom/android/camera/CameraSize;

    aput-object v11, v10, v4

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTelePictureSize:Lcom/android/camera/CameraSize;

    aput-object v11, v10, v8

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraTelePictureSize:Lcom/android/camera/CameraSize;

    aput-object v11, v10, v7

    invoke-static {v3, v9, v10}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_4

    :cond_c
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    sget-object v3, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string/jumbo v9, "ultraWideSize: %s, wideSize: %s, teleSize: %s"

    new-array v10, v7, [Ljava/lang/Object;

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mUltraWidePictureSize:Lcom/android/camera/CameraSize;

    aput-object v11, v10, v5

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mWidePictureSize:Lcom/android/camera/CameraSize;

    aput-object v11, v10, v4

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mTelePictureSize:Lcom/android/camera/CameraSize;

    aput-object v11, v10, v8

    invoke-static {v3, v9, v10}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :goto_4
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mUltraWidePictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setUltraWidePictureSize(Lcom/android/camera/CameraSize;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mWidePictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setWidePictureSize(Lcom/android/camera/CameraSize;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mTelePictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setTelePictureSize(Lcom/android/camera/CameraSize;)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getSatPictureSize()Lcom/android/camera/CameraSize;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    goto/16 :goto_8

    :cond_d
    nop

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraTeleCamera()Z

    move-result v3

    const/16 v9, 0xa7

    if-eqz v3, :cond_e

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    if-ne v3, v9, :cond_e

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v3

    invoke-virtual {v3}, Lcom/mi/config/a;->ir()I

    move-result v3

    goto :goto_5

    :cond_e
    move v3, v5

    :goto_5
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isSensorRawStreamRequired()Z

    move-result v10

    if-eqz v10, :cond_13

    iget-object v10, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    const/16 v11, 0x20

    invoke-virtual {v10, v11}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v10

    iget v11, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    if-ne v11, v9, :cond_12

    if-eqz v10, :cond_11

    invoke-interface {v10}, Ljava/util/List;->size()I

    move-result v9

    if-nez v9, :cond_f

    goto :goto_6

    :cond_f
    const v9, 0x3faaaaaa

    if-nez v3, :cond_10

    invoke-virtual {p0, v10, v9}, Lcom/android/camera/module/Camera2Module;->getBestPictureSize(Ljava/util/List;F)Lcom/android/camera/CameraSize;

    move-result-object v9

    iput-object v9, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    goto :goto_7

    :cond_10
    iget v11, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    iget v12, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-static {v10, v3, v11, v12}, Lcom/android/camera/PictureSizeManager;->initializeLimitWidth(Ljava/util/List;III)V

    invoke-static {v9}, Lcom/android/camera/PictureSizeManager;->getBestPictureSize(F)Lcom/android/camera/CameraSize;

    move-result-object v9

    iput-object v9, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    goto :goto_7

    :cond_11
    :goto_6
    sget-object v9, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v10, "The supported raw size list return from hal is null!"

    invoke-static {v9, v10}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_7

    :cond_12
    invoke-virtual {p0, v10}, Lcom/android/camera/module/Camera2Module;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object v9

    iput-object v9, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    :goto_7
    sget-object v9, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "The best sensor raw image size: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_13
    iget-boolean v9, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v9, :cond_14

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v9

    if-eqz v9, :cond_14

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePortraitPictureSize(I)V

    goto :goto_8

    :cond_14
    iget-object v9, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v9, v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object v9

    sget-boolean v10, Lcom/mi/config/b;->sM:Z

    if-eqz v10, :cond_15

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getOperatingMode()I

    move-result v10

    const v11, 0x9003

    if-ne v10, v11, :cond_15

    new-instance v10, Lcom/android/camera/CameraSize;

    iget v11, v9, Lcom/android/camera/CameraSize;->width:I

    div-int/2addr v11, v8

    iget v9, v9, Lcom/android/camera/CameraSize;->height:I

    div-int/2addr v9, v8

    invoke-direct {v10, v11, v9}, Lcom/android/camera/CameraSize;-><init>(II)V

    move-object v9, v10

    :cond_15
    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v10

    if-eqz v10, :cond_16

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v10

    if-eqz v10, :cond_16

    new-instance v10, Lcom/android/camera/CameraSize;

    iget v11, v9, Lcom/android/camera/CameraSize;->width:I

    div-int/2addr v11, v8

    iget v9, v9, Lcom/android/camera/CameraSize;->height:I

    div-int/2addr v9, v8

    invoke-direct {v10, v11, v9}, Lcom/android/camera/CameraSize;-><init>(II)V

    move-object v9, v10

    :cond_16
    if-eqz v3, :cond_17

    iget v9, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    iget v10, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-static {v0, v3, v9, v10}, Lcom/android/camera/PictureSizeManager;->initializeLimitWidth(Ljava/util/List;III)V

    invoke-static {}, Lcom/android/camera/PictureSizeManager;->getBestPictureSize()Lcom/android/camera/CameraSize;

    move-result-object v9

    :cond_17
    iput-object v9, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    :goto_8
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    const-class v3, Landroid/graphics/SurfaceTexture;

    invoke-virtual {v0, v3}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(Ljava/lang/Class;)Ljava/util/List;

    move-result-object v0

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v3, v3, Lcom/android/camera/CameraSize;->width:I

    iget-object v9, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v9, v9, Lcom/android/camera/CameraSize;->height:I

    invoke-static {v3, v9}, Lcom/android/camera/CameraSettings;->getPreviewAspectRatio(II)F

    move-result v3

    float-to-double v9, v3

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-static {v5, v3, v0, v9, v10}, Lcom/android/camera/Util;->getOptimalPreviewSize(ZILjava/util/List;D)Lcom/android/camera/CameraSize;

    move-result-object v3

    iput-object v3, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v11, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v3, v11}, Lcom/android/camera2/Camera2Proxy;->setPreviewSize(Lcom/android/camera/CameraSize;)V

    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v3, :cond_18

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    invoke-static {v0, v9, v10, v3}, Lcom/android/camera/Util;->getAlgorithmPreviewSize(Ljava/util/List;DLcom/android/camera/CameraSize;)Lcom/android/camera/CameraSize;

    move-result-object v0

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3, v0}, Lcom/android/camera2/Camera2Proxy;->setAlgorithmPreviewSize(Lcom/android/camera/CameraSize;)V

    goto :goto_9

    :cond_18
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setAlgorithmPreviewSize(Lcom/android/camera/CameraSize;)V

    :goto_9
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setAlgorithmPreviewFormat(I)V

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v0, :cond_1d

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0, v1}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v2, 0xa5

    if-ne v1, v2, :cond_1b

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v1, v1, Lcom/android/camera/CameraSize;->width:I

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v2, v2, Lcom/android/camera/CameraSize;->height:I

    if-le v1, v2, :cond_19

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v1, v1, Lcom/android/camera/CameraSize;->height:I

    goto :goto_a

    :cond_19
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iget v1, v1, Lcom/android/camera/CameraSize;->width:I

    :goto_a
    invoke-static {v0, v1}, Lcom/android/camera/PictureSizeManager;->getBestSquareSize(Ljava/util/List;I)Lcom/android/camera/CameraSize;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v0}, Lcom/android/camera/CameraSize;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1a

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    sget-object v2, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string/jumbo v3, "size %dx%d is not supported!"

    new-array v6, v8, [Ljava/lang/Object;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v6, v5

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v6, v4

    invoke-static {v2, v3, v6}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Lcom/android/camera/CameraSize;

    invoke-direct {v0, v1, v1}, Lcom/android/camera/CameraSize;-><init>(II)V

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    :cond_1a
    goto :goto_b

    :cond_1b
    if-eqz v6, :cond_1c

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    goto :goto_b

    :cond_1c
    invoke-static {v0}, Lcom/android/camera/PictureSizeManager;->getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    :goto_b
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "output picture size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_1d
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string/jumbo v2, "updateSize: picture size is: %s, preview size is: %s, sensor raw image size is: %s"

    new-array v3, v7, [Ljava/lang/Object;

    iget-object v6, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    aput-object v6, v3, v5

    iget-object v5, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    aput-object v5, v3, v4

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    aput-object v4, v3, v8

    invoke-static {v1, v2, v3}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v0, v0, Lcom/android/camera/CameraSize;->width:I

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v1, v1, Lcom/android/camera/CameraSize;->height:I

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->updateCameraScreenNailSize(II)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->checkDisplayOrientation()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v0, v0, Lcom/android/camera/CameraSize;->width:I

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v1, v1, Lcom/android/camera/CameraSize;->height:I

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->setVideoSize(II)V

    return-void
.end method

.method private updatePortraitLighting()V
    .locals 2

    invoke-static {}, Lcom/android/camera/CameraSettings;->getPortraitLightingPattern()I

    move-result v0

    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "0"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsPortraitLightingOn:Z

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setPortraitLighting(I)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->setLightingEffect()V

    return-void
.end method

.method private updatePortraitPictureSize(I)V
    .locals 10

    nop

    nop

    nop

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    const/4 v1, 0x1

    const/4 v2, -0x1

    const/4 v3, 0x0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxFrontCameraId()I

    move-result v0

    nop

    move v5, v0

    move v0, v1

    move v4, v3

    goto :goto_0

    :cond_0
    move v5, v2

    move v0, v3

    move v4, v0

    goto :goto_0

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->im()Z

    move-result v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v4

    const-string v5, "pref_ultra_wide_bokeh_enabled"

    invoke-virtual {v4, v5}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_2

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v5

    invoke-virtual {v5}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v5

    goto :goto_0

    :cond_2
    invoke-static {}, Lcom/mi/config/b;->kG()Z

    move-result v5

    if-eqz v5, :cond_3

    iget-object v5, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v5}, Lcom/android/camera2/Camera2Proxy;->getBokehAuxCameraId()I

    move-result v5

    goto :goto_0

    :cond_3
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v5

    invoke-virtual {v5}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxCameraId()I

    move-result v5

    :goto_0
    sget-object v6, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "BS = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v8, " UW = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v8, " id = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v6, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v6, p1}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSize(I)Ljava/util/List;

    move-result-object v6

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v7

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result v8

    invoke-virtual {v7, v8}, Lcom/mi/config/a;->r(Z)I

    move-result v7

    iget v8, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    iget v9, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-static {v6, v7, v8, v9}, Lcom/android/camera/PictureSizeManager;->initializeLimitWidth(Ljava/util/List;III)V

    invoke-static {}, Lcom/android/camera/PictureSizeManager;->getBestPictureSize()Lcom/android/camera/CameraSize;

    move-result-object v6

    sget-boolean v7, Lcom/mi/config/b;->sM:Z

    const/4 v8, 0x2

    if-eqz v7, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getOperatingMode()I

    move-result v7

    const v9, 0x9003

    if-ne v7, v9, :cond_4

    new-instance v7, Lcom/android/camera/CameraSize;

    iget v9, v6, Lcom/android/camera/CameraSize;->width:I

    div-int/2addr v9, v8

    iget v6, v6, Lcom/android/camera/CameraSize;->height:I

    div-int/2addr v6, v8

    invoke-direct {v7, v9, v6}, Lcom/android/camera/CameraSize;-><init>(II)V

    move-object v6, v7

    :cond_4
    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v7

    if-eqz v7, :cond_5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v7

    if-eqz v7, :cond_5

    new-instance v7, Lcom/android/camera/CameraSize;

    iget v9, v6, Lcom/android/camera/CameraSize;->width:I

    div-int/2addr v9, v8

    iget v6, v6, Lcom/android/camera/CameraSize;->height:I

    div-int/2addr v6, v8

    invoke-direct {v7, v9, v6}, Lcom/android/camera/CameraSize;-><init>(II)V

    move-object v6, v7

    :cond_5
    const/4 v7, 0x0

    if-ne v2, v5, :cond_6

    iput-object v6, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iput-object v7, p0, Lcom/android/camera/module/Camera2Module;->mSubPictureSize:Lcom/android/camera/CameraSize;

    goto :goto_2

    :cond_6
    if-eqz v0, :cond_7

    move-object v7, v6

    nop

    :cond_7
    invoke-direct {p0, v5, p1, v7}, Lcom/android/camera/module/Camera2Module;->getPictureSize(IILcom/android/camera/CameraSize;)Lcom/android/camera/CameraSize;

    move-result-object p1

    if-nez v4, :cond_9

    if-eqz v0, :cond_8

    goto :goto_1

    :cond_8
    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iput-object v6, p0, Lcom/android/camera/module/Camera2Module;->mSubPictureSize:Lcom/android/camera/CameraSize;

    goto :goto_2

    :cond_9
    :goto_1
    iput-object v6, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mSubPictureSize:Lcom/android/camera/CameraSize;

    :goto_2
    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v2, "mainSize = %s subSize = %s"

    new-array v4, v8, [Ljava/lang/Object;

    iget-object v5, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    aput-object v5, v4, v3

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mSubPictureSize:Lcom/android/camera/CameraSize;

    aput-object v3, v4, v1

    invoke-static {v0, v2, v4}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private updateSRAndMFNR()V
    .locals 2

    sget-object v0, Lcom/mi/config/b;->rp:Ljava/lang/String;

    const-string/jumbo v1, "tucana"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isHdr()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isIn3OrMoreSatMode()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v0

    const/high16 v1, 0x3f800000    # 1.0f

    cmpl-float v0, v0, v1

    if-lez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getSatMasterCameraId()I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->resetMutexMode()V

    goto :goto_1

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSuperResolution()V

    :cond_2
    :goto_1
    return-void
.end method

.method private updateSaturation()V
    .locals 2

    const v0, 0x7f09009f

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setSaturation(I)V

    return-void
.end method

.method private updateScene()V
    .locals 3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v1}, Lcom/android/camera/MutexModeManager;->isSceneHdr()Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v0, "18"

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    goto :goto_0

    :cond_0
    const-string v1, "pref_camera_scenemode_setting_key"

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v0, "-1"

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    goto :goto_0

    :cond_1
    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningSceneValue()Lcom/android/camera/data/data/runing/ComponentRunningSceneValue;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningSceneValue;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    :goto_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "sceneMode="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, " mutexMode="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v2}, Lcom/android/camera/MutexModeManager;->getMutexMode()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->setSceneMode(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_2

    const-string v0, "-1"

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$12;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$12;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    const-string v0, "0"

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_4

    const-string v0, "-1"

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    goto :goto_1

    :cond_3
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    const-string v1, "continuous-picture"

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->overrideFocusMode(Ljava/lang/String;)V

    goto :goto_2

    :cond_4
    :goto_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->overrideFocusMode(Ljava/lang/String;)V

    :goto_2
    return-void
.end method

.method private updateSceneModeUI()V
    .locals 7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v1, "pref_camera_scenemode_setting_key"

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xac

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/4 v2, 0x2

    const/4 v3, 0x0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v0

    const/16 v4, 0xa3

    const-string v5, "off"

    invoke-virtual {v0, v4, v5}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->setComponentValue(ILjava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSceneMode:Ljava/lang/String;

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->getFlashModeByScene(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v1, :cond_1

    const/4 v4, 0x1

    new-array v5, v4, [I

    const/16 v6, 0xc2

    aput v6, v5, v3

    invoke-interface {v1, v3, v5}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->disableMenuItem(Z[I)V

    const/16 v5, 0xc1

    if-eqz v0, :cond_0

    new-array v0, v4, [I

    aput v5, v0, v3

    invoke-interface {v1, v3, v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->disableMenuItem(Z[I)V

    goto :goto_0

    :cond_0
    new-array v0, v4, [I

    aput v5, v0, v3

    invoke-interface {v1, v3, v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->enableMenuItem(Z[I)V

    :goto_0
    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->hideExtraMenu()V

    :cond_1
    goto :goto_1

    :cond_2
    if-eqz v1, :cond_3

    new-array v0, v2, [I

    fill-array-data v0, :array_0

    invoke-interface {v1, v3, v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->enableMenuItem(Z[I)V

    :cond_3
    :goto_1
    if-eqz v1, :cond_4

    new-array v0, v2, [I

    fill-array-data v0, :array_1

    invoke-interface {v1, v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->updateConfigItem([I)V

    :cond_4
    new-array v0, v2, [I

    fill-array-data v0, :array_2

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    return-void

    nop

    :array_0
    .array-data 4
        0xc1
        0xc2
    .end array-data

    :array_1
    .array-data 4
        0xc1
        0xc2
    .end array-data

    :array_2
    .array-data 4
        0xb
        0xa
    .end array-data
.end method

.method private updateSharpness()V
    .locals 2

    const v0, 0x7f0900a1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setSharpness(I)V

    return-void
.end method

.method private updateShotDetermine()V
    .locals 9

    nop

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xab

    const/4 v2, 0x1

    const/4 v3, 0x0

    if-ne v0, v1, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/mi/config/b;->kl()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fJ()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    goto :goto_1

    :cond_1
    :goto_0
    move v0, v2

    goto :goto_2

    :cond_2
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fK()Z

    move-result v0

    goto :goto_2

    :cond_3
    :goto_1
    move v0, v3

    :goto_2
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isParallelSessionEnable()Z

    move-result v4

    iput-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    const/16 v5, 0x8

    if-eqz v4, :cond_a

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v1, :cond_8

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualFrontCamera()Z

    move-result v1

    const/4 v2, -0x5

    if-nez v1, :cond_6

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualCamera()Z

    move-result v1

    if-nez v1, :cond_6

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehUltraWideBackCamera()Z

    move-result v1

    if-eqz v1, :cond_4

    goto :goto_4

    :cond_4
    if-eqz v0, :cond_5

    const/4 v0, -0x6

    :goto_3
    move v2, v0

    goto/16 :goto_a

    :cond_5
    goto/16 :goto_a

    :cond_6
    :goto_4
    if-eqz v0, :cond_7

    const/4 v0, -0x7

    goto :goto_3

    :cond_7
    goto/16 :goto_a

    :cond_8
    if-eqz v0, :cond_9

    const/4 v0, -0x3

    goto :goto_3

    :cond_9
    const/4 v0, -0x2

    goto :goto_3

    :cond_a
    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v4, :cond_b

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v4

    invoke-virtual {v4}, Lcom/mi/config/a;->gS()Z

    move-result v4

    if-eqz v4, :cond_b

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->enablePreviewAsThumbnail()Z

    move-result v4

    if-eqz v4, :cond_b

    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v4

    if-nez v4, :cond_b

    move v4, v2

    goto :goto_5

    :cond_b
    move v4, v3

    :goto_5
    iput-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v6, 0xa3

    const/16 v7, 0xa7

    const/4 v8, 0x5

    if-eq v4, v6, :cond_16

    const/16 v6, 0xa5

    if-eq v4, v6, :cond_16

    if-eq v4, v7, :cond_16

    if-eq v4, v1, :cond_e

    const/16 v0, 0xad

    if-eq v4, v0, :cond_c

    const/16 v0, 0xaf

    if-eq v4, v0, :cond_16

    iput-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    return-void

    :cond_c
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isCurrentRawDomainBasedSuperNight()Z

    move-result v0

    if-eqz v0, :cond_d

    const/16 v0, 0xa

    move v2, v0

    goto :goto_6

    :cond_d
    nop

    move v2, v3

    :goto_6
    goto/16 :goto_a

    :cond_e
    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v1, :cond_14

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->shouldDoMultiFrameCapture()Z

    move-result v1

    if-eqz v1, :cond_f

    nop

    move v2, v5

    goto :goto_a

    :cond_f
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualFrontCamera()Z

    move-result v1

    if-nez v1, :cond_12

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualCamera()Z

    move-result v1

    if-nez v1, :cond_12

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehUltraWideBackCamera()Z

    move-result v1

    if-eqz v1, :cond_10

    goto :goto_8

    :cond_10
    if-eqz v0, :cond_11

    const/4 v0, 0x7

    goto :goto_3

    :cond_11
    :goto_7
    move v2, v8

    goto :goto_a

    :cond_12
    :goto_8
    if-eqz v0, :cond_13

    const/4 v0, 0x6

    goto/16 :goto_3

    :cond_13
    goto :goto_7

    :cond_14
    if-eqz v0, :cond_15

    const/4 v0, 0x2

    move v2, v0

    goto :goto_9

    :cond_15
    nop

    move v2, v3

    :goto_9
    goto :goto_a

    :cond_16
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v0, :cond_18

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mConfigRawStream:Z

    if-eqz v0, :cond_17

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    if-ne v0, v7, :cond_17

    goto :goto_a

    :cond_17
    nop

    move v2, v3

    goto :goto_a

    :cond_18
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->shouldDoMultiFrameCapture()Z

    move-result v0

    if-eqz v0, :cond_19

    move v2, v5

    goto :goto_a

    :cond_19
    nop

    move v2, v8

    :goto_a
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "enableParallel="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, " mEnableShot2Gallery="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, " shotType="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setShotType(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setShot2Gallery(Z)V

    return-void
.end method

.method private updateSuperNight()V
    .locals 2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xad

    if-eq v0, v1, :cond_1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setSuperNight(Z)V

    return-void
.end method

.method private updateSuperResolution()V
    .locals 6

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xad

    if-ne v0, v1, :cond_1

    return-void

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result v0

    if-eqz v0, :cond_2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "SR force off for ultra wide camera"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->isSREnable()Z

    move-result v0

    if-nez v0, :cond_3

    return-void

    :cond_3
    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isManualModule()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hu()Z

    move-result v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentUltraPixel()Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->isRear108MPSwitchOn()Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isSensorRawStreamRequired()Z

    move-result v2

    if-nez v2, :cond_5

    if-eqz v0, :cond_5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraTeleCamera()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAuxCamera()Z

    move-result v0

    if-eqz v0, :cond_5

    :cond_4
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_5

    const-string v0, "pref_qc_camera_exposuretime_key"

    const v2, 0x7f090142

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v0, v2}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v2

    const-wide/32 v4, 0xee6b280

    cmp-long v0, v2, v4

    if-gez v0, :cond_5

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    goto :goto_0

    :cond_5
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    :cond_6
    :goto_0
    return-void

    :cond_7
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v0

    const/high16 v2, 0x3f800000    # 1.0f

    cmpg-float v0, v0, v2

    if-gtz v0, :cond_9

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v2, "pref_camera_super_resolution_key"

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_b

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isSuperResolution()Z

    move-result v0

    if-eqz v0, :cond_8

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->resetMutexMode()V

    goto :goto_1

    :cond_8
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_b

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    goto :goto_1

    :cond_9
    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v0

    if-eqz v0, :cond_a

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isSuperResolution()Z

    move-result v0

    if-eqz v0, :cond_b

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->resetMutexMode()V

    goto :goto_1

    :cond_a
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isNormal()Z

    move-result v0

    if-eqz v0, :cond_b

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    const/16 v1, 0xa

    invoke-virtual {v0, v1}, Lcom/android/camera/MutexModeManager;->setMutexMode(I)V

    :cond_b
    :goto_1
    return-void
.end method

.method private updateSwMfnr()V
    .locals 4

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isUseSwMfnr()Z

    move-result v0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "setSwMfnr to "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setSwMfnr(Z)V

    return-void
.end method

.method private updateThumbProgress(Z)V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa2

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    if-eqz v0, :cond_0

    invoke-interface {v0, p1}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->updateLoading(Z)V

    :cond_0
    return-void
.end method

.method private updateUltraPixelPortrait()V
    .locals 2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelPortraitFrontOn()Z

    move-result v0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setUltraPixelPortrait(Z)V

    return-void
.end method

.method private updateUltraWideLDC()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->shouldApplyUltraWideLDC()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setUltraWideLDC(Z)V

    return-void
.end method

.method private updateWhiteBalance()V
    .locals 2

    const-string v0, "pref_camera_whitebalance_key"

    const-string v1, "1"

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setAWBMode(Ljava/lang/String;)V

    return-void
.end method

.method private updateZsl()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZslPreferred()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setEnableZsl(Z)V

    return-void
.end method


# virtual methods
.method public cancelFocus(Z)V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrameAvailable()Z

    move-result v0

    if-nez v0, :cond_1

    return-void

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-nez v0, :cond_2

    return-void

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_4

    if-eqz p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v0, 0x4

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setFocusMode(I)V

    :cond_3
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->cancelFocus(I)V

    :cond_4
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result p1

    const/4 v0, 0x3

    if-eq p1, v0, :cond_5

    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    :cond_5
    return-void
.end method

.method public checkDisplayOrientation()V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isCreated()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->checkDisplayOrientation()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mCameraDisplayOrientation:I

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setCameraDisplayOrientation(I)V

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mCameraDisplayOrientation:I

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setDisplayOrientation(I)V

    :cond_2
    return-void
.end method

.method public closeBacklightTip(II)V
    .locals 3

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/4 v2, -0x1

    if-eq v1, v2, :cond_0

    return-void

    :cond_0
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    if-ne v1, p1, :cond_1

    return-void

    :cond_1
    invoke-interface {v0, p2}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertAiSceneSelector(I)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 p2, 0x1

    invoke-virtual {p1, p2}, Lcom/android/camera2/Camera2Proxy;->setASD(Z)V

    return-void
.end method

.method public closeCamera()V
    .locals 5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "closeCamera: E"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCameraDeviceLock:Ljava/lang/Object;

    monitor-enter v1

    :try_start_0
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v3, 0x0

    if-eqz v2, :cond_a

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-eqz v2, :cond_0

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2}, Lcom/android/camera2/Camera2Proxy;->captureAbortBurst()V

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    :cond_0
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mBurstDisposable:Lio/reactivex/disposables/Disposable;

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mBurstDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v2}, Lio/reactivex/disposables/Disposable;->dispose()V

    :cond_1
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataFlowableEmitter:Lio/reactivex/FlowableEmitter;

    if-eqz v2, :cond_2

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataFlowableEmitter:Lio/reactivex/FlowableEmitter;

    invoke-interface {v2}, Lio/reactivex/FlowableEmitter;->onComplete()V

    :cond_2
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataDisposable:Lio/reactivex/disposables/Disposable;

    if-eqz v2, :cond_3

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v2}, Lio/reactivex/disposables/Disposable;->dispose()V

    :cond_3
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneFlowableEmitter:Lio/reactivex/FlowableEmitter;

    if-eqz v2, :cond_4

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneFlowableEmitter:Lio/reactivex/FlowableEmitter;

    invoke-interface {v2}, Lio/reactivex/FlowableEmitter;->onComplete()V

    :cond_4
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneDisposable:Lio/reactivex/disposables/Disposable;

    if-eqz v2, :cond_5

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v2}, Lio/reactivex/disposables/Disposable;->dispose()V

    :cond_5
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

    if-eqz v2, :cond_6

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mSuperNightDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v2}, Lio/reactivex/disposables/Disposable;->dispose()V

    :cond_6
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->setScreenLightCallback(Lcom/android/camera2/Camera2Proxy$ScreenLightCallback;)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->setMetaDataCallback(Lcom/android/camera2/Camera2Proxy$CameraMetaDataCallback;)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->setErrorCallback(Lcom/android/camera2/Camera2Proxy$CameraErrorCallback;)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->releaseCameraPreviewCallback(Lcom/android/camera2/Camera2Proxy$CameraPreviewCallback;)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->setFocusCallback(Lcom/android/camera2/Camera2Proxy$FocusCallback;)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v0}, Lcom/android/camera2/Camera2Proxy;->setASD(Z)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->scanQRCodeEnabled()Z

    move-result v2

    if-nez v2, :cond_7

    invoke-static {}, Lcom/mi/config/b;->gX()Z

    move-result v2

    if-nez v2, :cond_7

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v2, :cond_8

    :cond_7
    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v4, 0x1

    invoke-virtual {v2, v4}, Lcom/android/camera2/Camera2Proxy;->stopPreviewCallback(Z)V

    :cond_8
    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    if-eqz v2, :cond_9

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    :cond_9
    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    iput-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    :cond_a
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v1, :cond_b

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v1, v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setAeAwbLock(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->destroy()V

    :cond_b
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->scanQRCodeEnabled()Z

    move-result v0

    if-nez v0, :cond_c

    invoke-static {}, Lcom/mi/config/b;->gX()Z

    move-result v0

    if-nez v0, :cond_c

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v0, :cond_d

    :cond_c
    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/zxing/PreviewDecodeManager;->quit()V

    :cond_d
    invoke-static {}, Lcom/android/camera/parallel/AlgoConnector;->getInstance()Lcom/android/camera/parallel/AlgoConnector;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/parallel/AlgoConnector;->getLocalBinder()Lcom/android/camera/LocalParallelService$LocalBinder;

    move-result-object v0

    if-eqz v0, :cond_e

    invoke-virtual {v0, v3}, Lcom/android/camera/LocalParallelService$LocalBinder;->setOnSessionStatusCallBackListener(Lcom/xiaomi/protocol/ISessionStatusCallBackListener;)V

    :cond_e
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->stopCpuBoost()V

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "closeCamera: X"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :catchall_0
    move-exception v0

    :try_start_1
    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v0
.end method

.method public closeMoonMode(II)V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnteringMoonMode:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 v1, 0xa

    if-eq v0, v1, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/16 v1, 0x23

    if-eq v0, v1, :cond_1

    return-void

    :cond_1
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    if-ne p1, v0, :cond_2

    return-void

    :cond_2
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xac

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/4 v0, 0x0

    if-eqz p1, :cond_4

    invoke-interface {p1, p2, v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertMoonModeSelector(IZ)V

    if-eqz p2, :cond_3

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnteringMoonMode:Z

    :cond_3
    const/16 p1, 0x8

    if-ne p1, p2, :cond_4

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string p2, "(moon_mode) close moon mode"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_4
    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updateMoon(Z)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {p1}, Lcom/android/camera/MutexModeManager;->isSuperResolution()Z

    move-result p1

    if-eqz p1, :cond_5

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->iz()Z

    move-result p1

    if-nez p1, :cond_5

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 p2, 0x1

    invoke-virtual {p1, p2}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    :cond_5
    return-void
.end method

.method public varargs consumePreference([I)V
    .locals 3
    .param p1    # [I
        .annotation build Lcom/android/camera/constant/UpdateConstant$UpdateType;
        .end annotation
    .end param

    array-length v0, p1

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_0

    aget v2, p1, v1

    packed-switch v2, :pswitch_data_0

    :pswitch_0
    new-instance p1, Ljava/lang/RuntimeException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "no consumer for this updateType: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p1, v0}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw p1

    :pswitch_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateASDDirtyDetect()V

    goto/16 :goto_1

    :pswitch_2
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateMiMovie()V

    goto/16 :goto_1

    :pswitch_3
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateOnTripMode()V

    goto/16 :goto_1

    :pswitch_4
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateBackSoftLightPreference()V

    goto/16 :goto_1

    :pswitch_5
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateUltraPixelPortrait()V

    goto/16 :goto_1

    :pswitch_6
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSuperNight()V

    goto/16 :goto_1

    :pswitch_7
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateModuleRelated()V

    goto/16 :goto_1

    :pswitch_8
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateMacroMode()V

    goto/16 :goto_1

    :pswitch_9
    goto/16 :goto_1

    :pswitch_a
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateLiveShot()V

    goto/16 :goto_1

    :pswitch_b
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFNumber()V

    goto/16 :goto_1

    :pswitch_c
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateUltraWideLDC()V

    goto/16 :goto_1

    :pswitch_d
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateNormalWideLDC()V

    goto/16 :goto_1

    :pswitch_e
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateEyeLight()V

    goto/16 :goto_1

    :pswitch_f
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateShotDetermine()V

    goto/16 :goto_1

    :pswitch_10
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updatePortraitLighting()V

    goto/16 :goto_1

    :pswitch_11
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSwMfnr()V

    goto/16 :goto_1

    :pswitch_12
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFrontMirror()V

    goto/16 :goto_1

    :pswitch_13
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFaceScore()V

    goto/16 :goto_1

    :pswitch_14
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFaceAgeAnalyze()V

    goto/16 :goto_1

    :pswitch_15
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateBokeh()V

    goto/16 :goto_1

    :pswitch_16
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateAiScene()V

    goto/16 :goto_1

    :pswitch_17
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateDeviceOrientation()V

    goto/16 :goto_1

    :pswitch_18
    invoke-static {}, Lcom/android/camera/CameraSettings;->isMfnrSatEnable()Z

    move-result v2

    invoke-direct {p0, v2}, Lcom/android/camera/module/Camera2Module;->updateMfnr(Z)V

    goto/16 :goto_1

    :pswitch_19
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSuperResolution()V

    goto/16 :goto_1

    :pswitch_1a
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateExposureMeteringMode()V

    goto/16 :goto_1

    :pswitch_1b
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSharpness()V

    goto/16 :goto_1

    :pswitch_1c
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSaturation()V

    goto/16 :goto_1

    :pswitch_1d
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateContrast()V

    goto/16 :goto_1

    :pswitch_1e
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->focusCenter()V

    goto/16 :goto_1

    :pswitch_1f
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->applyZoomRatio()V

    goto/16 :goto_1

    :pswitch_20
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateDecodePreview()V

    goto/16 :goto_1

    :pswitch_21
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateZsl()V

    goto :goto_1

    :pswitch_22
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateMute()V

    goto :goto_1

    :pswitch_23
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateOIS()V

    goto :goto_1

    :pswitch_24
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFpsRange()V

    goto :goto_1

    :pswitch_25
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateExposureTime()V

    goto :goto_1

    :pswitch_26
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateISO()V

    goto :goto_1

    :pswitch_27
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFocusMode()V

    goto :goto_1

    :pswitch_28
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateBeauty()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateEyeLight()V

    goto :goto_1

    :pswitch_29
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->setEvValue()V

    goto :goto_1

    :pswitch_2a
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    goto :goto_1

    :pswitch_2b
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateFlashPreference()V

    goto :goto_1

    :pswitch_2c
    invoke-static {}, Lcom/android/camera/CameraSettings;->getAntiBanding()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->updateAntiBanding(Ljava/lang/String;)V

    goto :goto_1

    :pswitch_2d
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateJpegThumbnailSize()V

    goto :goto_1

    :pswitch_2e
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateJpegQuality()V

    goto :goto_1

    :pswitch_2f
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateWhiteBalance()V

    goto :goto_1

    :pswitch_30
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFace()V

    goto :goto_1

    :pswitch_31
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateScene()V

    goto :goto_1

    :pswitch_32
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFocusArea()V

    goto :goto_1

    :pswitch_33
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFilter()V

    goto :goto_1

    :pswitch_34
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updatePictureAndPreviewSize()V

    nop

    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto/16 :goto_0

    :cond_0
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_34
        :pswitch_33
        :pswitch_32
        :pswitch_31
        :pswitch_30
        :pswitch_2f
        :pswitch_2e
        :pswitch_2d
        :pswitch_2c
        :pswitch_2b
        :pswitch_2a
        :pswitch_29
        :pswitch_28
        :pswitch_27
        :pswitch_26
        :pswitch_25
        :pswitch_0
        :pswitch_0
        :pswitch_24
        :pswitch_23
        :pswitch_22
        :pswitch_21
        :pswitch_20
        :pswitch_1f
        :pswitch_1e
        :pswitch_1d
        :pswitch_1c
        :pswitch_1b
        :pswitch_1a
        :pswitch_19
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_18
        :pswitch_17
        :pswitch_16
        :pswitch_15
        :pswitch_14
        :pswitch_13
        :pswitch_12
        :pswitch_0
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_0
        :pswitch_8
        :pswitch_0
        :pswitch_0
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
    .end packed-switch
.end method

.method public enterMutexMode(I)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-nez v0, :cond_0

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "enterMutexMode error, mCamera2Device is null"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    const/4 v0, 0x1

    if-eq p1, v0, :cond_3

    const/4 v1, 0x3

    if-eq p1, v1, :cond_2

    const/16 v1, 0xa

    if-eq p1, v1, :cond_1

    goto :goto_0

    :cond_1
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    goto :goto_0

    :cond_2
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setHHT(Z)V

    goto :goto_0

    :cond_3
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setHDR(Z)V

    nop

    :goto_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isMfnrSatEnable()Z

    move-result p1

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->updateMfnr(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSwMfnr()V

    return-void
.end method

.method public exitMutexMode(I)V
    .locals 2

    const/4 v0, 0x1

    const/4 v1, 0x0

    if-eq p1, v0, :cond_2

    const/4 v0, 0x3

    if-eq p1, v0, :cond_1

    const/16 v0, 0xa

    if-eq p1, v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    goto :goto_0

    :cond_1
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setHHT(Z)V

    goto :goto_0

    :cond_2
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setHDR(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSuperResolution()V

    nop

    :goto_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isMfnrSatEnable()Z

    move-result p1

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->updateMfnr(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSwMfnr()V

    return-void
.end method

.method protected focusCenter()V
    .locals 0

    return-void
.end method

.method protected getBestPictureSize(Ljava/util/List;)Lcom/android/camera/CameraSize;
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/CameraSize;",
            ">;)",
            "Lcom/android/camera/CameraSize;"
        }
    .end annotation

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMaxPictureSize()I

    move-result v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-static {p1, v0, v1, v2}, Lcom/android/camera/PictureSizeManager;->initialize(Ljava/util/List;III)V

    invoke-static {}, Lcom/android/camera/PictureSizeManager;->getBestPictureSize()Lcom/android/camera/CameraSize;

    move-result-object p1

    return-object p1
.end method

.method protected getBestPictureSize(Ljava/util/List;F)Lcom/android/camera/CameraSize;
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/CameraSize;",
            ">;F)",
            "Lcom/android/camera/CameraSize;"
        }
    .end annotation

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMaxPictureSize()I

    move-result v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-static {p1, v0, v1, v2}, Lcom/android/camera/PictureSizeManager;->initialize(Ljava/util/List;III)V

    invoke-static {p2}, Lcom/android/camera/PictureSizeManager;->getBestPictureSize(F)Lcom/android/camera/CameraSize;

    move-result-object p1

    return-object p1
.end method

.method public getCircularMediaRecorder()Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorderStateLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method public getCurrentAiScene()I
    .locals 1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    return v0
.end method

.method public getDebugInfo()Ljava/lang/String;
    .locals 7

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v2

    const/4 v3, 0x0

    if-eqz v2, :cond_1

    invoke-virtual {v2}, Lcom/android/camera2/CameraCapabilities;->getCameraCharacteristics()Landroid/hardware/camera2/CameraCharacteristics;

    move-result-object v2

    if-eqz v2, :cond_1

    sget-object v4, Landroid/hardware/camera2/CameraCharacteristics;->LENS_INFO_AVAILABLE_FOCAL_LENGTHS:Landroid/hardware/camera2/CameraCharacteristics$Key;

    invoke-virtual {v2, v4}, Landroid/hardware/camera2/CameraCharacteristics;->get(Landroid/hardware/camera2/CameraCharacteristics$Key;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [F

    sget-object v5, Landroid/hardware/camera2/CameraCharacteristics;->LENS_INFO_AVAILABLE_APERTURES:Landroid/hardware/camera2/CameraCharacteristics$Key;

    invoke-virtual {v2, v5}, Landroid/hardware/camera2/CameraCharacteristics;->get(Landroid/hardware/camera2/CameraCharacteristics$Key;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, [F

    if-eqz v4, :cond_0

    array-length v5, v4

    if-lez v5, :cond_0

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "LensFocal:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget v4, v4, v3

    invoke-virtual {v5, v4}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v4, " "

    invoke-virtual {v5, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_0
    if-eqz v2, :cond_1

    array-length v4, v2

    if-lez v4, :cond_1

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "LensApertues:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget v2, v2, v3

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v2, " "

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_1
    const/16 v2, 0xa7

    if-ne v1, v2, :cond_2

    const-string v1, "SceneProfession:true"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_2
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "ZoomMultiple:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v2, " "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraDevice()Lcom/android/camera2/Camera2Proxy;

    move-result-object v1

    if-eqz v1, :cond_4

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getCameraConfigs()Lcom/android/camera2/CameraConfigs;

    move-result-object v1

    if-eqz v1, :cond_4

    invoke-virtual {v1}, Lcom/android/camera2/CameraConfigs;->getAFRegions()[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v1

    if-eqz v1, :cond_4

    array-length v2, v1

    if-lez v2, :cond_4

    aget-object v1, v1, v3

    if-nez v1, :cond_3

    const-string v1, "0"

    goto :goto_0

    :cond_3
    invoke-virtual {v1}, Landroid/hardware/camera2/params/MeteringRectangle;->getX()I

    move-result v2

    invoke-virtual {v1}, Landroid/hardware/camera2/params/MeteringRectangle;->getY()I

    move-result v3

    invoke-virtual {v1}, Landroid/hardware/camera2/params/MeteringRectangle;->getWidth()I

    move-result v4

    add-int/2addr v4, v2

    invoke-virtual {v1}, Landroid/hardware/camera2/params/MeteringRectangle;->getHeight()I

    move-result v1

    add-int/2addr v1, v3

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "["

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ","

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ","

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ","

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "]"

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    :goto_0
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "afRoi:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, " "

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_4
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->getFaces()[Lcom/android/camera2/CameraHardwareFace;

    move-result-object v1

    invoke-static {v1}, Lcom/android/camera/module/DebugInfoUtil;->getFaceInfoString([Lcom/android/camera2/CameraHardwareFace;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_5

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "FaceRoi:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, " "

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_5
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "FilterId:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getShaderEffect()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "AIScene:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCurrentAiScene()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public getFilterId()I
    .locals 1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mFilterId:I

    return v0
.end method

.method protected getMaxPictureSize()I
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method protected getMutexHdrMode(Ljava/lang/String;)I
    .locals 1

    const-string v0, "normal"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p1, 0x1

    return p1

    :cond_0
    invoke-static {}, Lcom/mi/config/b;->ja()Z

    move-result v0

    if-eqz v0, :cond_1

    const-string v0, "live"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    const/4 p1, 0x2

    return p1

    :cond_1
    const/4 p1, 0x0

    return p1
.end method

.method protected getOperatingMode()I
    .locals 9

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isParallelSessionEnable()Z

    move-result v0

    const/16 v1, 0xab

    const/16 v2, 0xa7

    if-eqz v0, :cond_8

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInQCFAMode()Z

    move-result v0

    const v3, 0x9005

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_QCFA"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v3, 0x9001

    goto/16 :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    if-ne v2, v0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v3, 0x9007

    goto/16 :goto_0

    :cond_1
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v3, 0x9006

    goto :goto_0

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    if-ne v1, v0, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDualFrontCamera()Z

    move-result v0

    if-nez v0, :cond_3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v3, 0x9003

    goto :goto_0

    :cond_3
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_DUAL_BOKEH"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v3, 0x9000

    goto :goto_0

    :cond_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_HD"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v3, 0x9004

    goto :goto_0

    :cond_5
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result v0

    if-eqz v0, :cond_6

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :cond_6
    invoke-static {}, Lcom/android/camera/CameraSettings;->isSupportedOpticalZoom()Z

    move-result v0

    if-eqz v0, :cond_7

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SAT"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v3, 0x9002

    goto :goto_0

    :cond_7
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    nop

    :goto_0
    iput v3, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    return v3

    :cond_8
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    const v3, 0x80f3

    const v4, 0x8001

    const/16 v5, 0xa3

    const v6, 0x8007

    const/4 v7, 0x1

    const v8, 0x8002

    if-eqz v0, :cond_d

    sput-boolean v7, Lcom/android/camera/module/Camera2Module;->mIsBeautyFrontOn:Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v0

    if-eqz v0, :cond_a

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fK()Z

    move-result v0

    if-eqz v0, :cond_a

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_9

    nop

    :goto_1
    move v3, v8

    goto :goto_3

    :cond_9
    const v0, 0x80f1

    :goto_2
    move v3, v0

    goto :goto_3

    :cond_a
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v0

    if-eqz v0, :cond_b

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_b

    goto :goto_1

    :cond_b
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isSupportedQcfa()Z

    move-result v0

    if-eqz v0, :cond_c

    sget-boolean v0, Lcom/android/camera/module/Camera2Module;->mIsBeautyFrontOn:Z

    if-nez v0, :cond_c

    const-string v0, "off"

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v1, v2}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_c

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->gW()I

    move-result v0

    if-gez v0, :cond_c

    nop

    move v3, v6

    goto :goto_3

    :cond_c
    const v0, 0x8005

    goto :goto_2

    :goto_3
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    if-ne v0, v5, :cond_15

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_15

    nop

    move v3, v6

    goto :goto_5

    :cond_d
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    if-eq v0, v5, :cond_13

    if-eq v0, v2, :cond_11

    if-eq v0, v1, :cond_10

    const/16 v1, 0xad

    if-eq v0, v1, :cond_f

    const/16 v1, 0xaf

    if-eq v0, v1, :cond_e

    nop

    :goto_4
    move v3, v4

    goto :goto_5

    :cond_e
    nop

    goto :goto_5

    :cond_f
    const v3, 0x800a

    goto :goto_5

    :cond_10
    nop

    nop

    move v3, v8

    goto :goto_5

    :cond_11
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_12

    const v3, 0x80f5

    goto :goto_5

    :cond_12
    const v3, 0x8003

    goto :goto_5

    :cond_13
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_14

    goto :goto_5

    :cond_14
    nop

    goto :goto_4

    :cond_15
    :goto_5
    iput v3, p0, Lcom/android/camera/module/Camera2Module;->mOperatingMode:I

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getOperatingMode: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "operatingMode = 0x%x"

    new-array v4, v7, [Ljava/lang/Object;

    const/4 v5, 0x0

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    aput-object v6, v4, v5

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v3
.end method

.method public initializeCapabilities()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->initializeCapabilities()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedFocusModes()[I

    move-result-object v0

    const/4 v1, 0x4

    invoke-static {v1, v0}, Lcom/android/camera/Util;->isSupported(I[I)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mContinuousFocusSupported:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->getMaxFaceCount()I

    move-result v0

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mMaxFaceCount:I

    return-void
.end method

.method protected isAutoRestartInNonZSL()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public isBeautyBodySlimCountDetectStarted()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsBeautyBodySlimOn:Z

    return v0
.end method

.method public isBlockSnap()Z
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-nez v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isZooming:Z

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result v0

    if-nez v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x3

    if-eq v0, v1, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v1}, Lcom/android/camera/MutexModeManager;->isHdr()Z

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->isCaptureBusy(Z)Z

    move-result v0

    if-nez v0, :cond_2

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isQueueFull()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInCountDown()Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    goto :goto_1

    :cond_2
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method protected isCameraSwitchingDuringZoomingAllowed()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSupportedOpticalZoom()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->isCameraSwitchingDuringZoomingAllowed()Z

    move-result v0

    return v0

    :cond_0
    sget-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-eqz v0, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method protected isDetectedHHT()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public isDoingAction()Z
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    const/4 v1, 0x1

    if-nez v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isZooming:Z

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result v0

    if-nez v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v2, 0x3

    if-eq v0, v2, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->isCaptureBusy(Z)Z

    move-result v0

    if-nez v0, :cond_2

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isQueueFull()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInCountDown()Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    const/4 v1, 0x0

    nop

    :cond_2
    :goto_0
    return v1
.end method

.method protected isFaceBeautyMode()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public isFaceDetectStarted()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    return v0
.end method

.method public isGyroStable()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->curGyroscope:[F

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->lastGyroscope:[F

    invoke-static {v0, v1}, Lcom/android/camera/Util;->isGyroscopeStable([F[F)Z

    move-result v0

    return v0
.end method

.method public isHdrSceneDetectionStarted()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mHdrCheckEnabled:Z

    return v0
.end method

.method public isKeptBitmapTexture()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mKeepBitmapTexture:Z

    return v0
.end method

.method public isLivePhotoStarted()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mLiveShotEnabled:Z

    return v0
.end method

.method public isMeteringAreaOnly()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getFocusMode()I

    move-result v0

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mFocusAreaSupported:Z

    if-nez v2, :cond_1

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mMeteringAreaSupported:Z

    if-eqz v2, :cond_1

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mFocusOrAELockSupported:Z

    if-eqz v2, :cond_3

    :cond_1
    const/4 v2, 0x5

    if-eq v2, v0, :cond_3

    if-nez v0, :cond_2

    goto :goto_0

    :cond_2
    goto :goto_1

    :cond_3
    :goto_0
    const/4 v1, 0x1

    :goto_1
    return v1
.end method

.method public isNeedMute()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v0

    return v0
.end method

.method protected isParallelSessionEnable()Z
    .locals 5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraParallelProcessEnable()Z

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    const/16 v3, 0xad

    if-eq v0, v3, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    if-eqz v0, :cond_2

    :cond_0
    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-eqz v0, :cond_1

    sget-boolean v0, Lcom/mi/config/b;->sQ:Z

    if-eqz v0, :cond_2

    :cond_1
    move v0, v2

    goto :goto_0

    :cond_2
    nop

    move v0, v1

    :goto_0
    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v3

    if-nez v3, :cond_5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result v3

    if-nez v3, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v3

    const/16 v4, 0xa7

    if-eq v3, v4, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isStandaloneMacroCamera()Z

    move-result v3

    if-nez v3, :cond_4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v3

    invoke-virtual {v3}, Lcom/mi/config/a;->hu()Z

    move-result v3

    if-eqz v3, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v3

    const/16 v4, 0xaf

    if-eq v3, v4, :cond_4

    :cond_3
    move v1, v2

    nop

    :cond_4
    and-int/2addr v0, v1

    :cond_5
    return v0
.end method

.method protected isRepeatingRequestInProgress()Z
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v1

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isSelectingCapturedResult()Z
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xa0

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;

    if-eqz v0, :cond_1

    const v2, 0x7f0e002a

    invoke-interface {v0, v2}, Lcom/android/camera/protocol/ModeProtocol$BaseDelegate;->getActiveFragment(I)I

    move-result v0

    const/16 v2, 0xff3

    if-ne v0, v2, :cond_1

    const/4 v1, 0x1

    nop

    :cond_1
    return v1
.end method

.method public isShot2GalleryOrEnableParallel()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public isShowAeAfLockIndicator()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    return v0
.end method

.method public isShowCaptureButton()Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isBurstShoot()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isSupportFocusShoot()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isSupportFocusShoot()Z
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    const-string v1, "pref_camera_focus_shoot_key"

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->isGlobalSwitchOn(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method protected isSupportSceneMode()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public isSupportSuperNight()Z
    .locals 3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->il()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_4

    sget-boolean v0, Lcom/mi/config/b;->sU:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lcom/android/camera/Util;->sSuperNightDefaultModeEnable:Z

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/16 v0, 0xa3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v2

    if-eq v0, v2, :cond_1

    const/16 v0, 0xa5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v2

    if-eq v0, v2, :cond_1

    return v1

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result v0

    if-nez v0, :cond_2

    return v1

    :cond_2
    const/high16 v0, 0x3f800000    # 1.0f

    invoke-static {}, Lcom/android/camera/CameraSettings;->readZoom()F

    move-result v2

    cmpl-float v0, v0, v2

    if-eqz v0, :cond_3

    return v1

    :cond_3
    const/4 v0, 0x1

    return v0

    :cond_4
    :goto_0
    return v1
.end method

.method public isUltraWideDetectStarted()Z
    .locals 1

    const/4 v0, 0x1

    return v0
.end method

.method public isUnInterruptable()Z
    .locals 2

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mUnInterruptableReason:Ljava/lang/String;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "bitmap cover"

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mUnInterruptableReason:Ljava/lang/String;

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x3

    if-ne v0, v1, :cond_1

    const-string/jumbo v0, "snapshot"

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mUnInterruptableReason:Ljava/lang/String;

    :cond_1
    :goto_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mUnInterruptableReason:Ljava/lang/String;

    if-eqz v0, :cond_2

    const/4 v0, 0x1

    goto :goto_1

    :cond_2
    const/4 v0, 0x0

    :goto_1
    return v0
.end method

.method public isUseFaceInfo()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGenderAgeOn:Z

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMagicMirrorOn:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method protected isZoomEnabled()Z
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x1

    const/4 v2, 0x3

    if-eq v0, v2, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isUbiFocus()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isStereoModeOn()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isPortraitModeBackOn()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->isCaptureBusy(Z)Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method protected isZslPreferred()Z
    .locals 4

    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xa3

    if-eq v0, v3, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xab

    if-ne v0, v3, :cond_0

    goto :goto_1

    :cond_0
    :goto_0
    move v1, v2

    goto :goto_2

    :cond_1
    :goto_1
    goto :goto_2

    :cond_2
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v3, 0xa7

    if-eq v0, v3, :cond_3

    goto :goto_2

    :cond_3
    goto :goto_0

    :goto_2
    return v1
.end method

.method public multiCapture()Z
    .locals 5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPendingMultiCapture:Z

    if-nez v0, :cond_1

    return v1

    :cond_1
    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mPendingMultiCapture:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getScreenHint()Lcom/android/camera/ui/ScreenHint;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/ScreenHint;->updateHint()V

    invoke-static {}, Lcom/android/camera/storage/Storage;->isLowStorageAtLastPoint()Z

    move-result v0

    if-eqz v0, :cond_2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Not enough space or storage not ready. remaining="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/android/camera/storage/Storage;->getLeftSpace()J

    move-result-wide v3

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/storage/ImageSaver;->isBusy()Z

    move-result v0

    if-eqz v0, :cond_3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "ImageSaver is busy, wait for a moment!"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-static {v0}, Lcom/android/camera/ui/RotateTextToast;->getInstance(Landroid/app/Activity;)Lcom/android/camera/ui/RotateTextToast;

    move-result-object v0

    const v2, 0x7f0901be

    invoke-virtual {v0, v2, v1}, Lcom/android/camera/ui/RotateTextToast;->show(II)V

    return v1

    :cond_3
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-eqz v0, :cond_4

    return v1

    :cond_4
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    const-string v1, "d"

    const/4 v2, 0x6

    new-array v2, v2, [I

    fill-array-data v2, :array_0

    invoke-interface {v0, v1, v2}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->closeMutexElement(Ljava/lang/String;[I)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xab

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BackStack;

    if-eqz v0, :cond_5

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BackStack;->handleBackStackFromShutter()V

    :cond_5
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->prepareMultiCapture()V

    new-instance v0, Lcom/android/camera/module/Camera2Module$2;

    invoke-direct {v0, p0}, Lcom/android/camera/module/Camera2Module$2;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-static {v0}, Lio/reactivex/Observable;->create(Lio/reactivex/ObservableOnSubscribe;)Lio/reactivex/Observable;

    move-result-object v0

    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v1

    invoke-virtual {v0, v1}, Lio/reactivex/Observable;->observeOn(Lio/reactivex/Scheduler;)Lio/reactivex/Observable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/Camera2Module$4;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$4;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Observable;->map(Lio/reactivex/functions/Function;)Lio/reactivex/Observable;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/Camera2Module$3;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$3;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lio/reactivex/Observable;->subscribe(Lio/reactivex/Observer;)V

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mBurstNextDelayTime:J

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isParallelSessionEnable()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/16 v2, 0x9

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setShotType(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mTotalJpegCallbackNum:I

    new-instance v3, Lcom/android/camera/module/Camera2Module$JpegRepeatingCaptureCallback;

    invoke-direct {v3, p0, p0}, Lcom/android/camera/module/Camera2Module$JpegRepeatingCaptureCallback;-><init>(Lcom/android/camera/module/Camera2Module;Lcom/android/camera/module/Camera2Module;)V

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v4}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v4

    invoke-virtual {v0, v2, v3, v4}, Lcom/android/camera2/Camera2Proxy;->captureBurstPictures(ILcom/android/camera2/Camera2Proxy$PictureCallback;Lcom/xiaomi/camera/core/ParallelCallback;)V

    goto :goto_0

    :cond_6
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v2, 0x3

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setShotType(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setAWBLock(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mTotalJpegCallbackNum:I

    new-instance v3, Lcom/android/camera/module/Camera2Module$JpegQuickPictureCallback;

    invoke-static {}, Lcom/android/camera/LocationManager;->instance()Lcom/android/camera/LocationManager;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/LocationManager;->getCurrentLocation()Landroid/location/Location;

    move-result-object v4

    invoke-direct {v3, p0, v4}, Lcom/android/camera/module/Camera2Module$JpegQuickPictureCallback;-><init>(Lcom/android/camera/module/Camera2Module;Landroid/location/Location;)V

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v4}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object v4

    invoke-virtual {v0, v2, v3, v4}, Lcom/android/camera2/Camera2Proxy;->captureBurstPictures(ILcom/android/camera2/Camera2Proxy$PictureCallback;Lcom/xiaomi/camera/core/ParallelCallback;)V

    :goto_0
    return v1

    :array_0
    .array-data 4
        0xc1
        0xc2
        0xc4
        0xef
        0xc9
        0xce
    .end array-data
.end method

.method public notifyFocusAreaUpdate()V
    .locals 3

    const/4 v0, 0x1

    new-array v0, v0, [I

    const/4 v1, 0x0

    const/4 v2, 0x3

    aput v2, v0, v1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceTrampoline([I)V

    return-void
.end method

.method public onBackPressed()Z
    .locals 7

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrameAvailable()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->tryRemoveCountDownMessage()V

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    const/4 v2, 0x1

    if-eqz v0, :cond_1

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->onShutterButtonLongClickCancel(Z)V

    return v2

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x3

    if-ne v0, v1, :cond_3

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v4, 0xad

    if-ne v3, v4, :cond_2

    iget-wide v3, p0, Lcom/android/camera/module/Camera2Module;->mLastBackPressedTime:J

    sub-long v3, v0, v3

    const-wide/16 v5, 0xbb8

    cmp-long v3, v3, v5

    if-lez v3, :cond_3

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mLastBackPressedTime:J

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const v1, 0x7f09012f

    invoke-static {v0, v1}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;I)V

    return v2

    :cond_2
    iget-wide v3, p0, Lcom/android/camera/module/Camera2Module;->mLastCaptureTime:J

    sub-long/2addr v0, v3

    const-wide/16 v3, 0x2ee0

    cmp-long v0, v0, v3

    if-gez v0, :cond_3

    return v2

    :cond_3
    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onBackPressed()Z

    move-result v0

    return v0
.end method

.method public onBeautyBodySlimCountChange(Z)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$7;

    invoke-direct {v1, p0, p1}, Lcom/android/camera/module/Camera2Module$7;-><init>(Lcom/android/camera/module/Camera2Module;Z)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public onBeautyParameterChanged()V
    .locals 3

    invoke-static {}, Lcom/mi/config/b;->km()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x3

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    goto :goto_0

    :cond_0
    const/4 v0, 0x1

    new-array v0, v0, [I

    const/4 v1, 0x0

    const/16 v2, 0xd

    aput v2, v0, v1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceInWorkThread([I)V

    :goto_0
    return-void

    :array_0
    .array-data 4
        0xd
        0x22
        0x2a
    .end array-data
.end method

.method public onBroadcastReceived(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 4

    if-eqz p2, :cond_5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_2

    :cond_0
    const-string v0, "android.media.action.VOICE_COMMAND"

    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "on Receive voice control broadcast action intent"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {p2}, Lcom/android/camera/CameraIntentManager;->getInstance(Landroid/content/Intent;)Lcom/android/camera/CameraIntentManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraIntentManager;->getVoiceControlAction()Ljava/lang/String;

    move-result-object v0

    iput-object p2, p0, Lcom/android/camera/module/Camera2Module;->mBroadcastIntent:Landroid/content/Intent;

    const/4 v1, -0x1

    invoke-virtual {v0}, Ljava/lang/String;->hashCode()I

    move-result v2

    const v3, 0x4bbb5326    # 2.4553036E7f

    if-eq v2, v3, :cond_1

    goto :goto_0

    :cond_1
    const-string v2, "CAPTURE"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    const/4 v1, 0x0

    :cond_2
    :goto_0
    if-eqz v1, :cond_3

    goto :goto_1

    :cond_3
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getTriggerMode()I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->onShutterButtonClick(I)V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBroadcastIntent:Landroid/content/Intent;

    nop

    :goto_1
    invoke-static {p2}, Lcom/android/camera/CameraIntentManager;->removeInstance(Landroid/content/Intent;)V

    :cond_4
    invoke-super {p0, p1, p2}, Lcom/android/camera/module/BaseModule;->onBroadcastReceived(Landroid/content/Context;Landroid/content/Intent;)V

    return-void

    :cond_5
    :goto_2
    return-void
.end method

.method protected onCameraOpened()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onCameraOpened()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->initializeFocusManager()V

    sget-object v0, Lcom/android/camera/constant/UpdateConstant;->CAMERA_TYPES_INIT:[I

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceTrampoline([I)V

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->getCameraCalibrationData()[B

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->getCalibrationDataFileName(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/Util;->saveCameraCalibrationToFile([BLjava/lang/String;)Z

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->startPreview()V

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->initMetaParser()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->fM()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->initAiSceneParser()V

    :cond_2
    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/android/camera/module/Camera2Module;->mOnResumeTime:J

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x1f

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void
.end method

.method protected onCapabilityChanged(Lcom/android/camera2/CameraCapabilities;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/module/BaseModule;->onCapabilityChanged(Lcom/android/camera2/CameraCapabilities;)V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mUltraWideAELocked:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setCharacteristics(Lcom/android/camera2/CameraCapabilities;)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, p1}, Lcom/android/camera2/Camera2Proxy;->onCapabilityChanged(Lcom/android/camera2/CameraCapabilities;)V

    :cond_1
    return-void
.end method

.method public onCaptureShutter(Z)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onCaptureShutter: cameraState = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", isParallel = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->onShutter(Z)V

    return-void
.end method

.method public onCaptureStart(Lcom/xiaomi/camera/core/ParallelTaskData;Lcom/android/camera/CameraSize;Z)Lcom/xiaomi/camera/core/ParallelTaskData;
    .locals 10

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDeparted()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    sget-object p2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string p3, "onCaptureStart: departed"

    invoke-static {p2, p3}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p1, v1}, Lcom/xiaomi/camera/core/ParallelTaskData;->setAbandoned(Z)V

    return-object p1

    :cond_0
    invoke-virtual {p1}, Lcom/xiaomi/camera/core/ParallelTaskData;->getParallelType()I

    move-result v0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v2

    const/4 v3, 0x0

    if-eqz v2, :cond_1

    invoke-static {v0}, Lcom/android/camera/module/Camera2Module;->isLiveShotAvailable(I)Z

    move-result v2

    if-eqz v2, :cond_1

    move v2, v1

    goto :goto_0

    :cond_1
    nop

    move v2, v3

    :goto_0
    if-eqz v2, :cond_2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->startLiveShotAnimation()V

    :cond_2
    if-eqz p3, :cond_3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v4

    if-eqz v4, :cond_6

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v4, :cond_6

    :cond_3
    invoke-static {}, Lcom/android/camera/CameraSettings;->isSupportedZslShutter()Z

    move-result v4

    if-nez v4, :cond_4

    invoke-direct {p0, p3}, Lcom/android/camera/module/Camera2Module;->updateEnablePreviewThumbnail(Z)V

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    if-eqz v4, :cond_4

    invoke-static {v3}, Lcom/android/camera/CameraSettings;->setPlayToneOnCaptureStart(Z)V

    :cond_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v4

    if-eqz v4, :cond_5

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v4, :cond_5

    invoke-static {v3}, Lcom/android/camera/CameraSettings;->setPlayToneOnCaptureStart(Z)V

    goto :goto_1

    :cond_5
    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    if-nez v4, :cond_6

    invoke-direct {p0, p3}, Lcom/android/camera/module/Camera2Module;->onShutter(Z)V

    invoke-static {v1}, Lcom/android/camera/CameraSettings;->setPlayToneOnCaptureStart(Z)V

    :cond_6
    :goto_1
    nop

    invoke-static {}, Lcom/android/camera/CameraSettings;->isAgeGenderAndMagicMirrorWaterOpen()Z

    move-result v4

    const/4 v5, 0x0

    if-eqz v4, :cond_7

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v4

    const/16 v6, 0xa6

    invoke-virtual {v4, v6}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v4

    check-cast v4, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v4}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->getFaceWaterMarkInfos()Ljava/util/List;

    move-result-object v4

    if-eqz v4, :cond_7

    invoke-interface {v4}, Ljava/util/List;->isEmpty()Z

    move-result v6

    if-nez v6, :cond_7

    new-instance v6, Ljava/util/ArrayList;

    invoke-direct {v6, v4}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    goto :goto_2

    :cond_7
    move-object v6, v5

    :goto_2
    sget-object v4, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "onCaptureStart: inputSize = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v4, v7}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isIn3OrMoreSatMode()Z

    move-result v4

    if-nez v4, :cond_8

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInMultiSurfaceSatMode()Z

    move-result v4

    if-eqz v4, :cond_9

    :cond_8
    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {p2, v4}, Lcom/android/camera/CameraSize;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_9

    iput-object p2, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    invoke-direct {p0, p2}, Lcom/android/camera/module/Camera2Module;->updateOutputSize(Lcom/android/camera/CameraSize;)V

    :cond_9
    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    if-nez v4, :cond_a

    invoke-virtual {p2}, Lcom/android/camera/CameraSize;->toSizeObject()Landroid/util/Size;

    move-result-object v4

    goto :goto_3

    :cond_a
    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mOutPutSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v4}, Lcom/android/camera/CameraSize;->toSizeObject()Landroid/util/Size;

    move-result-object v4

    :goto_3
    sget-object v7, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "onCaptureStart: outputSize = "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v7, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v7}, Lcom/android/camera2/CameraCapabilities;->getCameraCharacteristics()Landroid/hardware/camera2/CameraCharacteristics;

    move-result-object v7

    sget-object v8, Landroid/hardware/camera2/CameraCharacteristics;->LENS_INFO_AVAILABLE_FOCAL_LENGTHS:Landroid/hardware/camera2/CameraCharacteristics$Key;

    invoke-virtual {v7, v8}, Landroid/hardware/camera2/CameraCharacteristics;->get(Landroid/hardware/camera2/CameraCharacteristics$Key;)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, [F

    iput-object v8, p0, Lcom/android/camera/module/Camera2Module;->mFocalLengths:[F

    sget-object v8, Landroid/hardware/camera2/CameraCharacteristics;->LENS_INFO_AVAILABLE_APERTURES:Landroid/hardware/camera2/CameraCharacteristics$Key;

    invoke-virtual {v7, v8}, Landroid/hardware/camera2/CameraCharacteristics;->get(Landroid/hardware/camera2/CameraCharacteristics$Key;)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, [F

    iput-object v7, p0, Lcom/android/camera/module/Camera2Module;->mApertures:[F

    new-instance v7, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    iget-object v8, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v8}, Lcom/android/camera/CameraSize;->toSizeObject()Landroid/util/Size;

    move-result-object v8

    invoke-virtual {p2}, Lcom/android/camera/CameraSize;->toSizeObject()Landroid/util/Size;

    move-result-object p2

    invoke-direct {v7, v8, p2, v4}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;-><init>(Landroid/util/Size;Landroid/util/Size;Landroid/util/Size;)V

    if-ne v0, v1, :cond_b

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    iget p2, p2, Lcom/android/camera/CameraSize;->width:I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    iget v0, v0, Lcom/android/camera/CameraSize;->height:I

    invoke-virtual {v7, p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setRawSize(II)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    :cond_b
    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result p2

    invoke-virtual {v7, p2}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setHasDualWaterMark(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getCameraConfigs()Lcom/android/camera2/CameraConfigs;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera2/CameraConfigs;->getWaterMarkAppliedList()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-lez v0, :cond_c

    move v0, v1

    goto :goto_4

    :cond_c
    move v0, v3

    :goto_4
    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setVendorWaterMark(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isFrontMirror()Z

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setMirror(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-static {}, Lcom/android/camera/CameraSettings;->getPortraitLightingPattern()I

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setLightingPattern(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v0

    invoke-virtual {v0, v3}, Lcom/android/camera/effect/EffectController;->getEffectForSaving(Z)I

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setFilterId(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    const/4 v0, -0x1

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    if-ne v0, v4, :cond_d

    move v0, v3

    goto :goto_5

    :cond_d
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    :goto_5
    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setOrientation(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mJpegRotation:I

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setJpegRotation(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mShootRotation:F

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setShootRotation(F)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mShootOrientation:I

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setShootOrientation(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLocation:Landroid/location/Location;

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setLocation(Landroid/location/Location;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTimeWaterMarkOpen()Z

    move-result v0

    if-eqz v0, :cond_e

    invoke-static {}, Lcom/android/camera/Util;->getTimeWatermark()Ljava/lang/String;

    move-result-object v5

    nop

    :cond_e
    invoke-virtual {p2, v5}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setTimeWaterMarkString(Ljava/lang/String;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-virtual {p2, v6}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setFaceWaterMarkList(Ljava/util/List;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isAgeGenderAndMagicMirrorWaterOpen()Z

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setAgeGenderAndMagicMirrorWater(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setFrontCamera(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBokehFrontCamera()Z

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setBokehFrontCamera(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mAlgorithmName:Ljava/lang/String;

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setAlgorithmName(Ljava/lang/String;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getPictureInfo()Lcom/xiaomi/camera/core/PictureInfo;

    move-result-object v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setPictureInfo(Lcom/xiaomi/camera/core/PictureInfo;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getSuffix()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setSuffix(Ljava/lang/String;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-static {}, Lcom/android/camera/module/Camera2Module;->getTiltShiftMode()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setTiltShiftMode(Ljava/lang/String;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSaveGroushotPrimitiveOn()Z

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setSaveGroupshotPrimitive(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getDeviceWaterMarkParam()Lcom/android/camera/effect/renders/DeviceWatermarkParam;

    move-result-object v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setDeviceWatermarkParam(Lcom/android/camera/effect/renders/DeviceWatermarkParam;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-static {v3}, Lcom/android/camera/module/Camera2Module;->getJpegQuality(Z)I

    move-result v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setJpegQuality(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getPrefix()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setPrefix(Ljava/lang/String;)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    invoke-virtual {p2, v0}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setMoonMode(Z)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p2

    invoke-virtual {p2}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->build()Lcom/xiaomi/camera/core/ParallelTaskDataParameter;

    move-result-object p2

    invoke-virtual {p1, p2}, Lcom/xiaomi/camera/core/ParallelTaskData;->fillParameter(Lcom/xiaomi/camera/core/ParallelTaskDataParameter;)V

    if-nez p3, :cond_f

    iget-boolean p2, p0, Lcom/android/camera/module/Camera2Module;->mEnabledPreviewThumbnail:Z

    if-nez p2, :cond_f

    move p2, v1

    goto :goto_6

    :cond_f
    move p2, v3

    :goto_6
    invoke-virtual {p1, p2}, Lcom/xiaomi/camera/core/ParallelTaskData;->setNeedThumbnail(Z)V

    iget p2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {p1, p2}, Lcom/xiaomi/camera/core/ParallelTaskData;->setCurrentModuleIndex(I)V

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz p2, :cond_10

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {p2}, Lcom/android/camera2/CameraCapabilities;->isAdaptiveSnapshotSizeInSatModeSupported()Z

    move-result p2

    if-eqz p2, :cond_10

    move p2, v1

    goto :goto_7

    :cond_10
    nop

    move p2, v3

    :goto_7
    invoke-virtual {p1, p2}, Lcom/xiaomi/camera/core/ParallelTaskData;->setAdaptiveSnapshotSize(Z)V

    invoke-virtual {p1, v3}, Lcom/xiaomi/camera/core/ParallelTaskData;->setLiveShotTask(Z)V

    if-eqz v2, :cond_12

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz p2, :cond_12

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p2}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object p2

    if-eqz p2, :cond_12

    iget-object p3, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorderStateLock:Ljava/lang/Object;

    monitor-enter p3

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    if-eqz v0, :cond_11

    invoke-virtual {p1, v1}, Lcom/xiaomi/camera/core/ParallelTaskData;->setLiveShotTask(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mOrientationCompensation:I

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mFilterId:I

    invoke-virtual {v0, v3, p2, p1, v4}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->snapshot(ILcom/xiaomi/camera/liveshot/CircularMediaRecorder$VideoClipSavingCallback;Ljava/lang/Object;I)V

    :cond_11
    monitor-exit p3

    goto :goto_8

    :catchall_0
    move-exception p1

    monitor-exit p3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1

    :cond_12
    :goto_8
    sget-object p2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onCaptureStart: isParallel = "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v0, ", shotType = "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/xiaomi/camera/core/ParallelTaskData;->getParallelType()I

    move-result v0

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v0, ", isLiveShot = "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    invoke-static {p2, p3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean p2, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz p2, :cond_13

    invoke-direct {p0, p1, v1}, Lcom/android/camera/module/Camera2Module;->beginParallelProcess(Lcom/xiaomi/camera/core/ParallelTaskData;Z)V

    :cond_13
    invoke-static {}, Lcom/android/camera/CameraSettings;->isHandGestureOpen()Z

    move-result p2

    if-eqz p2, :cond_14

    sget-object p2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string p3, "send msg: reset hand gesture"

    invoke-static {p2, p3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 p3, 0x39

    invoke-virtual {p2, p3}, Landroid/os/Handler;->removeMessages(I)V

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const-wide/16 v0, 0x0

    invoke-virtual {p2, p3, v0, v1}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    :cond_14
    return-object p1
.end method

.method public onCreate(II)V
    .locals 0

    invoke-super {p0, p1, p2}, Lcom/android/camera/module/BaseModule;->onCreate(II)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->parseIntent()V

    new-instance p1, Lcom/android/camera/module/Camera2Module$MainHandler;

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p2}, Lcom/android/camera/Camera;->getMainLooper()Landroid/os/Looper;

    move-result-object p2

    invoke-direct {p1, p0, p2}, Lcom/android/camera/module/Camera2Module$MainHandler;-><init>(Lcom/android/camera/module/Camera2Module;Landroid/os/Looper;)V

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object p1

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mSensorStateListener:Lcom/android/camera/SensorStateManager$SensorStateListener;

    invoke-virtual {p1, p2}, Lcom/android/camera/SensorStateManager;->setSensorStateListener(Lcom/android/camera/SensorStateManager$SensorStateListener;)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result p1

    const/16 p2, 0xa3

    if-ne p2, p1, :cond_0

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-nez p1, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result p1

    if-eqz p1, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isAvailableGoogleLens()Z

    move-result p1

    if-eqz p1, :cond_0

    const/4 p1, 0x1

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    :goto_0
    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->onCameraOpened()V

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-static {}, Lcom/mi/config/b;->gQ()Z

    move-result p1

    if-nez p1, :cond_1

    new-instance p1, Lcom/google/lens/sdk/LensApi;

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-direct {p1, p2}, Lcom/google/lens/sdk/LensApi;-><init>(Landroid/content/Context;)V

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mLensApi:Lcom/google/lens/sdk/LensApi;

    new-instance p2, Lcom/android/camera/module/Camera2Module$10;

    invoke-direct {p2, p0}, Lcom/android/camera/module/Camera2Module$10;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {p1, p2}, Lcom/google/lens/sdk/LensApi;->checkLensAvailability(Lcom/google/lens/sdk/LensApi$LensAvailabilityCallback;)V

    :cond_1
    return-void
.end method

.method public onDestroy()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onDestroy()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x2d

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :cond_0
    return-void
.end method

.method public onFaceDetected([Lcom/android/camera2/CameraHardwareFace;Lcom/android/camera/effect/FaceAnalyzeInfo;)V
    .locals 5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-eqz v0, :cond_c

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraScreenNail;->getFrameAvailableFlag()Z

    move-result v0

    if-nez v0, :cond_0

    goto/16 :goto_2

    :cond_0
    if-nez p1, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/mi/config/b;->kD()Z

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz v0, :cond_5

    array-length v0, p1

    if-lez v0, :cond_2

    move v0, v2

    goto :goto_0

    :cond_2
    nop

    move v0, v1

    :goto_0
    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetected:Z

    if-eq v0, v3, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v3

    if-eqz v3, :cond_4

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v4, 0xa3

    if-eq v3, v4, :cond_3

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v4, 0xa5

    if-eq v3, v4, :cond_3

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v4, 0xab

    if-ne v3, v4, :cond_4

    :cond_3
    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3}, Lcom/android/camera2/Camera2Proxy;->resumePreview()I

    :cond_4
    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetected:Z

    :cond_5
    iput-object p2, p0, Lcom/android/camera/module/Camera2Module;->mFaceInfo:Lcom/android/camera/effect/FaceAnalyzeInfo;

    invoke-static {}, Lcom/mi/config/b;->jc()Z

    move-result p2

    if-eqz p2, :cond_6

    array-length p2, p1

    if-lez p2, :cond_6

    aget-object p2, p1, v1

    iget p2, p2, Lcom/android/camera2/CameraHardwareFace;->faceType:I

    const v0, 0xface

    if-ne p2, v0, :cond_6

    iget-boolean p2, p0, Lcom/android/camera/module/Camera2Module;->mObjectTrackingStarted:Z

    if-eqz p2, :cond_b

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    const/4 v0, 0x3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActiveArraySize()Landroid/graphics/Rect;

    move-result-object v1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getDeviceBasedZoomRatio()F

    move-result v2

    invoke-interface {p2, v0, p1, v1, v2}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setFaces(I[Lcom/android/camera2/CameraHardwareFace;Landroid/graphics/Rect;F)Z

    goto :goto_1

    :cond_6
    iget-boolean p2, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz p2, :cond_7

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz p2, :cond_7

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v0, Lcom/android/camera/module/-$$Lambda$Camera2Module$k1-PUTQXNxWdzyjIsuO9-338h4o;

    invoke-direct {v0, p0, p1}, Lcom/android/camera/module/-$$Lambda$Camera2Module$k1-PUTQXNxWdzyjIsuO9-338h4o;-><init>(Lcom/android/camera/module/Camera2Module;[Lcom/android/camera2/CameraHardwareFace;)V

    invoke-virtual {p2, v0}, Lcom/android/camera/Camera;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_7
    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActiveArraySize()Landroid/graphics/Rect;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getDeviceBasedZoomRatio()F

    move-result v3

    invoke-interface {p2, v2, p1, v0, v3}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setFaces(I[Lcom/android/camera2/CameraHardwareFace;Landroid/graphics/Rect;F)Z

    move-result p2

    if-nez p2, :cond_8

    return-void

    :cond_8
    iget-boolean p2, p0, Lcom/android/camera/module/Camera2Module;->mIsPortraitLightingOn:Z

    if-eqz p2, :cond_9

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p2, p1, v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->lightingDetectFace([Lcom/android/camera2/CameraHardwareFace;Z)V

    :cond_9
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1, v2}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->isFaceExists(I)Z

    move-result p1

    const/16 p2, 0x38

    if-eqz p1, :cond_a

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->isFocusViewVisible()Z

    move-result p1

    if-eqz p1, :cond_a

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz p1, :cond_a

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->isFromTouch()Z

    move-result p1

    if-nez p1, :cond_a

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->hasMessages(I)Z

    move-result p1

    if-nez p1, :cond_b

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    goto :goto_1

    :cond_a
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->removeMessages(I)V

    :cond_b
    :goto_1
    return-void

    :cond_c
    :goto_2
    return-void
.end method

.method public onFilterChanged(II)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onFilterChanged: category = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, ", newIndex = "

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 p2, 0x0

    const/4 v0, 0x2

    aput v0, p1, p2

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->updatePreferenceTrampoline([I)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->updateEffectViewVisible()V

    return-void
.end method

.method public onFocusStateChanged(Lcom/android/camera/module/loader/camera2/FocusTask;)V
    .locals 9

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrameAvailable()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDeparted()Z

    move-result v0

    if-eqz v0, :cond_1

    return-void

    :cond_1
    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->getFocusTrigger()I

    move-result v0

    const/4 v1, 0x3

    const/4 v2, 0x0

    const/4 v3, 0x1

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_2

    :pswitch_0
    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->isFocusing()Z

    move-result v0

    nop

    const/4 v4, 0x0

    if-eqz v0, :cond_2

    const-string v4, "onAutoFocusMoving start"

    iput v2, p0, Lcom/android/camera/module/Camera2Module;->mAFEndLogTimes:I

    goto :goto_0

    :cond_2
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mAFEndLogTimes:I

    if-nez v0, :cond_3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onAutoFocusMoving end. result="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->isSuccess()Z

    move-result v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    nop

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mAFEndLogTimes:I

    add-int/2addr v0, v3

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mAFEndLogTimes:I

    :cond_3
    :goto_0
    sget-boolean v0, Lcom/android/camera/Util;->sIsDumpLog:Z

    if-eqz v0, :cond_4

    if-eqz v4, :cond_4

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    invoke-static {v0, v4}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :cond_4
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-ne v0, v1, :cond_6

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->getFocusTrigger()I

    move-result v0

    if-ne v0, v1, :cond_5

    goto :goto_1

    :cond_5
    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->isSuccess()Z

    move-result v0

    if-eqz v0, :cond_9

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onFocusResult(Lcom/android/camera/module/loader/camera2/FocusTask;)V

    goto/16 :goto_2

    :cond_6
    :goto_1
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-nez v0, :cond_9

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onFocusResult(Lcom/android/camera/module/loader/camera2/FocusTask;)V

    goto/16 :goto_2

    :pswitch_1
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    sget-object v4, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v5, "FocusTime=%1$dms focused=%2$b"

    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->getElapsedTime()J

    move-result-wide v7

    invoke-static {v7, v8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v7

    aput-object v7, v6, v2

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->isSuccess()Z

    move-result v7

    invoke-static {v7}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v7

    aput-object v7, v6, v3

    invoke-static {v4, v5, v6}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v0, v4}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->isFocusingSnapOnFinish()Z

    move-result v0

    if-nez v0, :cond_7

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-eq v0, v1, :cond_7

    invoke-virtual {p0, v3}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    :cond_7
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onFocusResult(Lcom/android/camera/module/loader/camera2/FocusTask;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/SensorStateManager;->reset()V

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->isSuccess()Z

    move-result p1

    if-eqz p1, :cond_9

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-eqz p1, :cond_9

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object p1

    invoke-virtual {p1}, Lcom/mi/config/a;->hG()Z

    move-result p1

    if-nez p1, :cond_8

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZoomRatioBetweenUltraAndWide()Z

    move-result p1

    if-eqz p1, :cond_8

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mUltraCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz p1, :cond_8

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mUltraCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {p1}, Lcom/android/camera2/CameraCapabilities;->isAFRegionSupported()Z

    move-result p1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onFocusStateChanged: isUltraFocusAreaSupported = "

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-nez p1, :cond_8

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v2}, Lcom/android/camera2/Camera2Proxy;->setFocusMode(I)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setFocusDistance(F)V

    iput-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mUltraWideAELocked:Z

    :cond_8
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1, v3}, Lcom/android/camera2/Camera2Proxy;->lockExposure(Z)V

    :cond_9
    :goto_2
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public onGradienterSwitched(Z)V
    .locals 1

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsGradienterOn:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/android/camera/SensorStateManager;->setGradienterEnabled(Z)V

    const/4 p1, 0x2

    new-array p1, p1, [I

    fill-array-data p1, :array_0

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->updatePreferenceTrampoline([I)V

    return-void

    nop

    :array_0
    .array-data 4
        0x2
        0x5
    .end array-data
.end method

.method public onHanGestureSwitched(Z)V
    .locals 2

    const/4 v0, 0x1

    if-eqz p1, :cond_0

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object p1

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-virtual {p1, v1, v0}, Lcom/android/zxing/PreviewDecodeManager;->init(II)V

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/zxing/PreviewDecodeManager;->startDecode()V

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/android/zxing/PreviewDecodeManager;->stopDecode(I)V

    :goto_0
    return-void
.end method

.method public onHdrMotionDetectionResult(Z)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isMotionDetectionSupported()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMotionDetected:Z

    if-eq v0, p1, :cond_0

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mMotionDetected:Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    :cond_0
    return-void
.end method

.method public onHdrSceneChanged(Z)V
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isDetectedInHdr:Z

    if-eq v0, p1, :cond_7

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-eqz v0, :cond_0

    goto/16 :goto_1

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_1

    return-void

    :cond_1
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "auto"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    return-void

    :cond_2
    if-eqz p1, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v0

    const/high16 v1, 0x3f800000    # 1.0f

    cmpl-float v0, v0, v1

    if-gtz v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    const/4 v1, -0x1

    if-eq v0, v1, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isNeedFlashOn()Z

    move-result v0

    if-eqz v0, :cond_4

    :cond_3
    return-void

    :cond_4
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$DucaL1JCbwLhR0KtSaeOoY6Ry2g;

    invoke-direct {v1, p1}, Lcom/android/camera/module/-$$Lambda$Camera2Module$DucaL1JCbwLhR0KtSaeOoY6Ry2g;-><init>(Z)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    if-eqz p1, :cond_5

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isNormal()Z

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/MutexModeManager;->setMutexMode(I)V

    goto :goto_0

    :cond_5
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isMorphoHdr()Z

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->resetMutexMode()V

    :cond_6
    :goto_0
    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->isDetectedInHdr:Z

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onHdrSceneChanged: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_7
    :goto_1
    return-void
.end method

.method public onHostStopAndNotifyActionStop()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onHostStopAndNotifyActionStop()V

    nop

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->onBurstPictureTakenFinished(Z)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setAeAwbLock(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->destroy()V

    :cond_1
    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->checkMoreFrameCaptureLockAFAE(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->handleSuperNightResultIfNeed()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->doLaterReleaseIfNeed()V

    :cond_2
    return-void
.end method

.method protected onInterceptZoomingEvent(FFI)Z
    .locals 2

    const/high16 v0, 0x3f800000    # 1.0f

    cmpg-float v0, p2, v0

    if-gez v0, :cond_0

    const/4 v0, 0x0

    const/4 v1, -0x1

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->onUltraWideChanged(ZI)V

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hG()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-nez v0, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-eqz v0, :cond_3

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->unlockAEAF()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->cancelFocus()V

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xaf

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz v0, :cond_2

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->directlyHideTips()V

    :cond_2
    goto :goto_0

    :cond_3
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_4

    const/4 v0, 0x4

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getFocusMode()I

    move-result v1

    if-eq v0, v1, :cond_4

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "onInterceptZoomingEvent: should cancel focus."

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->cancelFocus()V

    :cond_4
    :goto_0
    invoke-super {p0, p1, p2, p3}, Lcom/android/camera/module/BaseModule;->onInterceptZoomingEvent(FFI)Z

    move-result p1

    return p1
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrameAvailable()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    const/4 v0, 0x1

    sparse-switch p1, :sswitch_data_0

    goto :goto_3

    :sswitch_0
    invoke-virtual {p2}, Landroid/view/KeyEvent;->getRepeatCount()I

    move-result p1

    if-nez p1, :cond_1

    invoke-virtual {p0, v0, v0}, Lcom/android/camera/module/Camera2Module;->onShutterButtonFocus(ZI)V

    :cond_1
    return v0

    :sswitch_1
    invoke-virtual {p2}, Landroid/view/KeyEvent;->getRepeatCount()I

    move-result p1

    if-nez p1, :cond_3

    invoke-static {p2}, Lcom/android/camera/Util;->isFingerPrintKeyEvent(Landroid/view/KeyEvent;)Z

    move-result p1

    const v1, 0x7f090128

    if-eqz p1, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFingerprintCaptureEnable()Z

    move-result p1

    if-eqz p1, :cond_3

    const/16 p1, 0x1e

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p2}, Landroid/view/KeyEvent;->getRepeatCount()I

    move-result p2

    invoke-virtual {p0, p1, v1, p2, v0}, Lcom/android/camera/module/Camera2Module;->performKeyClicked(ILjava/lang/String;IZ)V

    goto :goto_0

    :cond_2
    const/16 p1, 0x28

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p2}, Landroid/view/KeyEvent;->getRepeatCount()I

    move-result p2

    invoke-virtual {p0, p1, v1, p2, v0}, Lcom/android/camera/module/Camera2Module;->performKeyClicked(ILjava/lang/String;IZ)V

    :cond_3
    :goto_0
    return v0

    :sswitch_2
    const/16 v2, 0x18

    if-eq p1, v2, :cond_5

    const/16 v2, 0x58

    if-ne p1, v2, :cond_4

    goto :goto_1

    :cond_4
    goto :goto_2

    :cond_5
    :goto_1
    move v1, v0

    :goto_2
    invoke-virtual {p2}, Landroid/view/KeyEvent;->getRepeatCount()I

    move-result v2

    invoke-virtual {p2}, Landroid/view/KeyEvent;->getDevice()Landroid/view/InputDevice;

    move-result-object v3

    invoke-virtual {v3}, Landroid/view/InputDevice;->isExternal()Z

    move-result v3

    invoke-virtual {p0, v1, v0, v2, v3}, Lcom/android/camera/module/Camera2Module;->handleVolumeKeyEvent(ZZIZ)Z

    move-result v1

    if-eqz v1, :cond_6

    return v0

    :cond_6
    :goto_3
    invoke-super {p0, p1, p2}, Lcom/android/camera/module/BaseModule;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result p1

    return p1

    nop

    :sswitch_data_0
    .sparse-switch
        0x18 -> :sswitch_2
        0x19 -> :sswitch_2
        0x1b -> :sswitch_1
        0x42 -> :sswitch_1
        0x50 -> :sswitch_0
        0x57 -> :sswitch_2
        0x58 -> :sswitch_2
    .end sparse-switch
.end method

.method public onKeyUp(ILandroid/view/KeyEvent;)Z
    .locals 2

    const/4 v0, 0x4

    if-eq p1, v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xab

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BackStack;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BackStack;->handleBackStackFromKeyBack()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p1, 0x1

    return p1

    :cond_1
    :goto_0
    invoke-super {p0, p1, p2}, Lcom/android/camera/module/BaseModule;->onKeyUp(ILandroid/view/KeyEvent;)Z

    move-result p1

    return p1
.end method

.method public onLivePhotoResultCallback(Lcom/xiaomi/camera/liveshot/LivePhotoResult;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLivePhotoQueue:Ljava/util/Queue;

    invoke-interface {v0, p1}, Ljava/util/Queue;->offer(Ljava/lang/Object;)Z

    return-void
.end method

.method public onLongPress(FF)V
    .locals 8

    float-to-int v4, p1

    float-to-int v5, p2

    invoke-virtual {p0, v4, v5}, Lcom/android/camera/module/Camera2Module;->isInTapableRect(II)Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsCurrentLensEnabled:Z

    const/4 v1, 0x0

    if-eqz v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isAvailableLongPressGoogleLens()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    const-string v2, "pref_camera_long_press_viewfinder_key"

    const/4 v3, 0x0

    invoke-virtual {v0, v2, v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtil;->trackGoogleLensPicker()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v6

    new-instance v7, Lcom/android/camera/module/-$$Lambda$Camera2Module$IyAHJeItOvbXf2FkenUGJSeZEsw;

    move-object v0, v7

    move-object v1, p0

    move v2, p1

    move v3, p2

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/module/-$$Lambda$Camera2Module$IyAHJeItOvbXf2FkenUGJSeZEsw;-><init>(Lcom/android/camera/module/Camera2Module;FFII)V

    invoke-static {v6, v7}, Lcom/android/camera/fragment/GoogleLensFragment;->showOptions(Landroid/app/FragmentManager;Lcom/android/camera/fragment/GoogleLensFragment$OnClickListener;)Lcom/android/camera/fragment/GoogleLensFragment;

    goto :goto_0

    :cond_1
    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtil;->trackGoogleLensTouchAndHold()V

    invoke-static {}, Lcom/android/lens/LensAgent;->getInstance()Lcom/android/lens/LensAgent;

    move-result-object v0

    sget v2, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v2, v2

    div-float/2addr p1, v2

    sget v2, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v2, v2

    div-float/2addr p2, v2

    invoke-virtual {v0, v1, p1, p2}, Lcom/android/lens/LensAgent;->onFocusChange(IFF)V

    :goto_0
    return-void

    :cond_2
    const/4 p1, 0x1

    invoke-virtual {p0, v4, v5, p1}, Lcom/android/camera/module/Camera2Module;->onSingleTapUp(IIZ)V

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->m3ALockSupported:Z

    if-eqz p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->getFocusMode()I

    move-result p1

    const-string p2, "manual"

    invoke-static {p2}, Lcom/android/camera/constant/AutoFocus;->convertToFocusMode(Ljava/lang/String;)I

    move-result p2

    if-eq p1, p2, :cond_3

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->lockAEAF()V

    :cond_3
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p1, v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->performHapticFeedback(I)V

    return-void
.end method

.method public onObjectStable()V
    .locals 0

    return-void
.end method

.method public onOrientationChanged(III)V
    .locals 0

    iget-boolean p3, p0, Lcom/android/camera/module/Camera2Module;->mIsGradienterOn:Z

    if-eqz p3, :cond_0

    iget-object p3, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p3}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object p3

    invoke-virtual {p3}, Lcom/android/camera/SensorStateManager;->isDeviceLying()Z

    move-result p3

    if-eqz p3, :cond_1

    :cond_0
    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->setOrientation(II)V

    :cond_1
    return-void
.end method

.method public onPause()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onPause()V

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->stopLiveShot(Z)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->removeMessages()V

    :cond_0
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mWaitingSnapshot:Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->unregisterSensor()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->tryRemoveCountDownMessage()V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/SensorStateManager;->reset()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resetScreenOn()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->closeCamera()V

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->setAiSceneEffect(I)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->releaseEffectProcessor()V

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/mi/config/b;->gQ()Z

    move-result v0

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$KPfZd9iaY7a-5FXCMg6ImsruoUI;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$KPfZd9iaY7a-5FXCMg6ImsruoUI;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    :cond_2
    return-void
.end method

.method public onPictureTaken([B)V
    .locals 0

    return-void
.end method

.method public onPictureTakenFinished(Z)V
    .locals 7

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onPictureTakenFinished: succeed = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    const/4 v1, 0x1

    if-eqz p1, :cond_4

    invoke-virtual {p0, v1, v0}, Lcom/android/camera/module/Camera2Module;->trackGeneralInfo(IZ)V

    new-instance v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;

    invoke-direct {v2}, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;-><init>()V

    iput v1, v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->takenNum:I

    iput-boolean v0, v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->burst:Z

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mLocation:Landroid/location/Location;

    if-eqz v3, :cond_0

    move v3, v1

    goto :goto_0

    :cond_0
    move v3, v0

    :goto_0
    iput-boolean v3, v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->location:Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getCurrentAiSceneName()Ljava/lang/String;

    move-result-object v3

    iput-object v3, v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->aiSceneName:Ljava/lang/String;

    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mEnteringMoonMode:Z

    iput-boolean v3, v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isEnteringMoon:Z

    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    iput-boolean v3, v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isSelectMoonMode:Z

    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mShowSuperNightHint:Z

    iput-boolean v3, v2, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isSuperNightInCaptureMode:Z

    invoke-virtual {p0, v2}, Lcom/android/camera/module/Camera2Module;->trackPictureTaken(Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    iget-wide v4, p0, Lcom/android/camera/module/Camera2Module;->mCaptureStartTime:J

    sub-long/2addr v2, v4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v4

    iget v5, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v2, v3, v4, v5}, Lcom/android/camera/statistic/CameraStatUtil;->trackTakePictureCost(JZI)V

    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v5, 0xab

    if-ne v4, v5, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v4

    invoke-virtual {v4}, Lcom/mi/config/a;->isSupportBokehAdjust()Z

    move-result v4

    if-eqz v4, :cond_1

    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtil;->trackBokehTaken()V

    :cond_1
    invoke-static {}, Lcom/android/camera/statistic/ScenarioTrackUtil;->trackCaptureTimeEnd()V

    sget-object v4, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mCaptureStartTime(from onShutterButtonClick start to jpegCallback finished) = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v2, "ms"

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v4, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-eqz v2, :cond_3

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mQuickCapture:Z

    if-nez v2, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v2

    if-eqz v2, :cond_4

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mKeepBitmapTexture:Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->showPostCaptureAlert()V

    goto :goto_1

    :cond_2
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->doAttach()V

    goto :goto_1

    :cond_3
    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mLongPressedAutoFocus:Z

    if-eqz v2, :cond_4

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mLongPressedAutoFocus:Z

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->cancelLongPressedAutoFocus()V

    :cond_4
    :goto_1
    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    add-int/2addr v2, v1

    iput v2, p0, Lcom/android/camera/module/Camera2Module;->mReceivedJpegCallbackNum:I

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result v2

    if-nez v2, :cond_6

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v2, :cond_6

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mBlockQuickShot:Z

    if-eqz v2, :cond_6

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v2

    if-eqz v2, :cond_5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v2

    if-eqz v2, :cond_6

    if-eqz p1, :cond_6

    :cond_5
    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->enableCameraControls(Z)V

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mBlockQuickShot:Z

    :cond_6
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x32

    invoke-virtual {p1, v1}, Landroid/os/Handler;->removeMessages(I)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->handleSuperNightResultIfNeed()Z

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->checkMoreFrameCaptureLockAFAE(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->handleSuperNightResultInCaptureMode()V

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/zxing/PreviewDecodeManager;->resetScanResult()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->doLaterReleaseIfNeed()V

    return-void
.end method

.method public onPictureTakenImageConsumed(Landroid/media/Image;)Z
    .locals 0

    const/4 p1, 0x0

    return p1
.end method

.method public onPreviewLayoutChanged(Landroid/graphics/Rect;)V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0, p1}, Lcom/android/camera/Camera;->onLayoutChange(Landroid/graphics/Rect;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/CameraScreenNail;->getRenderWidth()I

    move-result v1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v2}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/CameraScreenNail;->getRenderHeight()I

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setRenderSize(II)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v1

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result p1

    invoke-virtual {v0, v1, p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setPreviewSize(II)V

    :cond_0
    return-void
.end method

.method public onPreviewMetaDataUpdate(Landroid/hardware/camera2/CaptureResult;)V
    .locals 6

    if-nez p1, :cond_0

    return-void

    :cond_0
    invoke-super {p0, p1}, Lcom/android/camera/module/BaseModule;->onPreviewMetaDataUpdate(Landroid/hardware/camera2/CaptureResult;)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->he()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-nez v0, :cond_1

    goto :goto_0

    :cond_1
    invoke-static {}, Lcom/mi/config/b;->kF()Z

    move-result v0

    const/4 v2, 0x1

    if-eqz v0, :cond_2

    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mShouldDoMFNR:Z

    goto :goto_1

    :cond_2
    sget-object v0, Landroid/hardware/camera2/CaptureResult;->SENSOR_SENSITIVITY:Landroid/hardware/camera2/CaptureResult$Key;

    invoke-virtual {p1, v0}, Landroid/hardware/camera2/CaptureResult;->get(Landroid/hardware/camera2/CaptureResult$Key;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    sget-object v3, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "onPreviewMetaDataUpdate: iso = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->c(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz v0, :cond_3

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const/16 v3, 0x320

    if-lt v0, v3, :cond_3

    move v1, v2

    nop

    :cond_3
    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mShouldDoMFNR:Z

    goto :goto_1

    :cond_4
    :goto_0
    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mShouldDoMFNR:Z

    :goto_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->shouldCheckSatFallbackState()Z

    move-result v0

    if-eqz v0, :cond_5

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->checkSatFallback(Landroid/hardware/camera2/CaptureResult;)V

    :cond_5
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataFlowableEmitter:Lio/reactivex/FlowableEmitter;

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMetaDataFlowableEmitter:Lio/reactivex/FlowableEmitter;

    invoke-interface {v0, p1}, Lio/reactivex/FlowableEmitter;->onNext(Ljava/lang/Object;)V

    :cond_6
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneFlowableEmitter:Lio/reactivex/FlowableEmitter;

    if-eqz v0, :cond_7

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    if-eqz v0, :cond_7

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_7

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneFlowableEmitter:Lio/reactivex/FlowableEmitter;

    invoke-interface {v0, p1}, Lio/reactivex/FlowableEmitter;->onNext(Ljava/lang/Object;)V

    :cond_7
    return-void
.end method

.method public onPreviewPixelsRead([BII)V
    .locals 12

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->animateCapture()V

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->playCameraSound(I)V

    sget-object v1, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {p2, p3, v1}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-static {p1}, Ljava/nio/ByteBuffer;->wrap([B)Ljava/nio/ByteBuffer;

    move-result-object p1

    invoke-virtual {v1, p1}, Landroid/graphics/Bitmap;->copyPixelsFromBuffer(Ljava/nio/Buffer;)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result p1

    const/4 v2, 0x1

    if-eqz p1, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isFrontMirror()Z

    move-result p1

    if-nez p1, :cond_0

    move p1, v2

    goto :goto_0

    :cond_0
    nop

    move p1, v0

    :goto_0
    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mCameraDeviceLock:Ljava/lang/Object;

    monitor-enter v3

    :try_start_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v4

    if-eqz v4, :cond_b

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDeviceAlive()Z

    move-result v4

    if-nez v4, :cond_1

    goto/16 :goto_3

    :cond_1
    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    const/4 v5, 0x0

    if-nez v4, :cond_2

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    if-eqz v4, :cond_3

    :cond_2
    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-eqz v4, :cond_5

    :cond_3
    iget p2, p0, Lcom/android/camera/module/Camera2Module;->mShootOrientation:I

    iget p3, p0, Lcom/android/camera/module/Camera2Module;->mDisplayRotation:I

    sub-int/2addr p2, p3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result p3

    if-eqz p3, :cond_4

    invoke-static {}, Lcom/mi/config/b;->kc()Z

    move-result p3

    if-eqz p3, :cond_4

    rem-int/lit16 p3, p2, 0xb4

    if-nez p3, :cond_4

    nop

    move p2, v0

    :cond_4
    invoke-static {v5, v1, p2, p1}, Lcom/android/camera/Thumbnail;->createThumbnail(Landroid/net/Uri;Landroid/graphics/Bitmap;IZ)Lcom/android/camera/Thumbnail;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/Thumbnail;->startWaitingForUri()V

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p2}, Lcom/android/camera/Camera;->getThumbnailUpdater()Lcom/android/camera/ThumbnailUpdater;

    move-result-object p2

    invoke-virtual {p2, p1, v2, v2}, Lcom/android/camera/ThumbnailUpdater;->setThumbnail(Lcom/android/camera/Thumbnail;ZZ)V

    iget-object p2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p2, p1}, Lcom/android/camera2/Camera2Proxy;->onPreviewThumbnailReceived(Lcom/android/camera/Thumbnail;)V

    monitor-exit v3

    return-void

    :cond_5
    iget v4, p0, Lcom/android/camera/module/Camera2Module;->mShootRotation:F

    iget v6, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    int-to-float v6, v6

    iget v7, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v8, 0xa5

    if-ne v7, v8, :cond_6

    move v7, v2

    goto :goto_1

    :cond_6
    move v7, v0

    :goto_1
    invoke-static {v1, v4, p1, v6, v7}, Lcom/android/camera/Util;->cropBitmap(Landroid/graphics/Bitmap;FZFZ)Landroid/graphics/Bitmap;

    move-result-object p1

    if-nez p1, :cond_7

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string p2, "onPreviewPixelsRead: bitmap is null!"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    monitor-exit v3

    return-void

    :cond_7
    const/16 v1, 0x57

    invoke-static {p1, v1}, Lcom/android/camera/Util;->getBitmapData(Landroid/graphics/Bitmap;I)[B

    move-result-object p1

    if-nez p1, :cond_8

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string p2, "onPreviewPixelsRead: jpegData is null!"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    monitor-exit v3

    return-void

    :cond_8
    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onPreviewPixelsRead: isParallel = "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v6, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v6, ", mEnableShot2Gallery = "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v6, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v6, ", data = "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v1, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getParallelShotSavePath()Ljava/lang/String;

    move-result-object v11

    new-instance v1, Lcom/xiaomi/camera/core/ParallelTaskData;

    iget v7, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v8

    const/4 v10, -0x1

    move-object v6, v1

    invoke-direct/range {v6 .. v11}, Lcom/xiaomi/camera/core/ParallelTaskData;-><init>(IJILjava/lang/String;)V

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez v4, :cond_a

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    if-eqz v4, :cond_9

    goto :goto_2

    :cond_9
    move v2, v0

    nop

    :cond_a
    :goto_2
    invoke-virtual {v1, v2}, Lcom/xiaomi/camera/core/ParallelTaskData;->setNeedThumbnail(Z)V

    invoke-virtual {v1, p1, v0}, Lcom/xiaomi/camera/core/ParallelTaskData;->fillJpegData([BI)V

    new-instance p1, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    new-instance v0, Landroid/util/Size;

    invoke-direct {v0, p2, p3}, Landroid/util/Size;-><init>(II)V

    new-instance v2, Landroid/util/Size;

    invoke-direct {v2, p2, p3}, Landroid/util/Size;-><init>(II)V

    new-instance v4, Landroid/util/Size;

    invoke-direct {v4, p2, p3}, Landroid/util/Size;-><init>(II)V

    invoke-direct {p1, v0, v2, v4}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;-><init>(Landroid/util/Size;Landroid/util/Size;Landroid/util/Size;)V

    iget p2, p0, Lcom/android/camera/module/Camera2Module;->mOrientation:I

    invoke-virtual {p1, p2}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->setOrientation(I)Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;

    move-result-object p1

    invoke-virtual {p1}, Lcom/xiaomi/camera/core/ParallelTaskDataParameter$Builder;->build()Lcom/xiaomi/camera/core/ParallelTaskDataParameter;

    move-result-object p1

    invoke-virtual {v1, p1}, Lcom/xiaomi/camera/core/ParallelTaskData;->fillParameter(Lcom/xiaomi/camera/core/ParallelTaskDataParameter;)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getImageSaver()Lcom/android/camera/storage/ImageSaver;

    move-result-object p1

    invoke-virtual {p1, v1, v5, v5}, Lcom/android/camera/storage/ImageSaver;->onParallelProcessFinish(Lcom/xiaomi/camera/core/ParallelTaskData;Landroid/hardware/camera2/CaptureResult;Landroid/hardware/camera2/CameraCharacteristics;)Z

    monitor-exit v3

    return-void

    :cond_b
    :goto_3
    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string p2, "onPreviewPixelsRead: module is dead"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    monitor-exit v3

    return-void

    :catchall_0
    move-exception p1

    monitor-exit v3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public onPreviewSessionClosed(Landroid/hardware/camera2/CameraCaptureSession;)V
    .locals 1

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "onPreviewSessionClosed: "

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    return-void
.end method

.method public onPreviewSessionFailed(Landroid/hardware/camera2/CameraCaptureSession;)V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isTextureExpired()Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->retryOnceIfCameraError(Landroid/os/Handler;)Z

    move-result p1

    if-eqz p1, :cond_0

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "sessionFailed due to surfaceTexture expired, retry"

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v0, 0x33

    invoke-virtual {p1, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onPreviewSessionSuccess(Landroid/hardware/camera2/CameraCaptureSession;)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onPreviewSessionSuccess: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, " "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x1

    if-nez p1, :cond_0

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onPreviewSessionSuccess null session."

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v0}, Lcom/android/camera/Util;->getCallers(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result p1

    if-nez p1, :cond_1

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onPreviewSessionSuccess module not alive."

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {v0}, Lcom/android/camera/Util;->getCallers(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isKeptBitmapTexture()Z

    move-result p1

    if-nez p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v0, 0x9

    invoke-virtual {p1, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :cond_2
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz p1, :cond_3

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->configParallelSession()V

    :cond_3
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->previewWhenSessionSuccess()V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getCameraIntentManager()Lcom/android/camera/CameraIntentManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/CameraIntentManager;->checkCallerLegality()Z

    move-result p1

    if-eqz p1, :cond_5

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result p1

    if-nez p1, :cond_5

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getCameraIntentManager()Lcom/android/camera/CameraIntentManager;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1, v0}, Lcom/android/camera/CameraIntentManager;->isOpenOnly(Landroid/app/Activity;)Z

    move-result p1

    if-nez p1, :cond_4

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getIntent()Landroid/content/Intent;

    move-result-object p1

    const-string v0, "android.intent.extra.CAMERA_OPEN_ONLY"

    invoke-virtual {p1, v0}, Landroid/content/Intent;->removeExtra(Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v0, 0x34

    const-wide/16 v1, 0x3e8

    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    goto :goto_0

    :cond_4
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getIntent()Landroid/content/Intent;

    move-result-object p1

    const-string v0, "android.intent.extra.TIMER_DURATION_SECONDS"

    invoke-virtual {p1, v0}, Landroid/content/Intent;->removeExtra(Ljava/lang/String;)V

    :cond_5
    :goto_0
    return-void
.end method

.method public onPreviewSizeChanged(II)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, p1, p2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setPreviewSize(II)V

    :cond_0
    return-void
.end method

.method public onResume()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onResume()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x32

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isSelectingCapturedResult()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mKeepBitmapTexture:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraScreenNail;->releaseBitmapIfNeeded()V

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->keepScreenOnAwhile()V

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/mi/config/b;->gQ()Z

    move-result v0

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$L_rJ_2rvbZGXHAjtPqfy3v9zJTE;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$L_rJ_2rvbZGXHAjtPqfy3v9zJTE;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_1
    return-void
.end method

.method public onReviewCancelClicked()V
    .locals 3

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mKeepBitmapTexture:Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isSelectingCapturedResult()Z

    move-result v1

    if-eqz v1, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraScreenNail;->releaseBitmapIfNeeded()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->hidePostCaptureAlert()V

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2}, Landroid/content/Intent;-><init>()V

    invoke-virtual {v1, v0, v2}, Lcom/android/camera/Camera;->setResult(ILandroid/content/Intent;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->finish()V

    :goto_0
    return-void
.end method

.method public onReviewDoneClicked()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->doAttach()V

    return-void
.end method

.method public onShutterButtonClick(I)V
    .locals 3

    const/16 v0, 0x64

    if-ne p1, v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->onUserInteraction()V

    :cond_0
    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->getCountDownTimes(I)I

    move-result v0

    if-lez v0, :cond_1

    invoke-direct {p0, v0, p1}, Lcom/android/camera/module/Camera2Module;->startCount(II)V

    return-void

    :cond_1
    new-instance v0, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;

    invoke-direct {v0}, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;-><init>()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xac

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v1, :cond_2

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->isShowBacklightSelector()Z

    move-result v1

    iput-boolean v1, v0, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isASDBacklitTip:Z

    :cond_2
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xaf

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz v1, :cond_3

    const v2, 0x7f0903a4

    invoke-interface {v1, v2}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->containTips(I)Z

    move-result v1

    iput-boolean v1, v0, Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;->isASDPortraitTip:Z

    :cond_3
    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->trackTouchShutterButtom(Lcom/android/camera/statistic/CameraStat$PictureTakenParameter;)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->checkShutterCondition()Z

    move-result v0

    if-nez v0, :cond_4

    return-void

    :cond_4
    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->setTriggerMode(I)V

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onShutterButtonClick "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v1

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mNeedAutoFocus:Z

    const/4 v1, 0x2

    invoke-virtual {p1, v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->prepareCapture(ZI)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->doSnap()V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->isFocusingSnapOnFinish()Z

    move-result p1

    if-eqz p1, :cond_5

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->enableCameraControls(Z)V

    :cond_5
    return-void
.end method

.method public onShutterButtonFocus(ZI)V
    .locals 0

    return-void
.end method

.method public onShutterButtonLongClick()Z
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "onShutterButtonLongClick: doing action"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-eqz v0, :cond_1

    return v1

    :cond_1
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsSatFallback:Z

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->shouldCheckSatFallbackState()Z

    move-result v0

    if-eqz v0, :cond_2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v2, "onShutterButtonLongClick: sat fallback"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->isBurstShootingEnable()Z

    move-result v0

    const/4 v2, 0x1

    if-eqz v0, :cond_4

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isCameraModule()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGroupShotOn()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isGradienterOn()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTiltShiftOn()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v3, "pref_camera_hand_night_key"

    invoke-virtual {v0, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const-string v3, "pref_camera_scenemode_setting_key"

    invoke-virtual {v0, v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isStereoModeOn()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isPortraitModeBackOn()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v3, 0x18

    invoke-virtual {v0, v3}, Landroid/os/Handler;->hasMessages(I)Z

    move-result v0

    if-nez v0, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPendingMultiCapture:Z

    if-nez v0, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isUltraWideBackCamera()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isStandaloneMacroCamera()Z

    move-result v0

    if-nez v0, :cond_4

    invoke-static {}, Lcom/mi/config/b;->jo()Z

    move-result v0

    if-eqz v0, :cond_3

    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mUpdateImageTitle:Z

    :cond_3
    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mPendingMultiCapture:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, v2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->doMultiSnap(Z)V

    return v2

    :cond_4
    iput-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mLongPressedAutoFocus:Z

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setFocusViewType(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->unlockAEAF()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->requestAutoFocus()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getScreenHint()Lcom/android/camera/ui/ScreenHint;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/ScreenHint;->updateHint()V

    return v1
.end method

.method public onShutterButtonLongClickCancel(Z)V
    .locals 5

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "onShutterButtonLongClickCancel: start"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPendingMultiCapture:Z

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v2, 0x31

    const-wide/16 v3, 0x7d0

    invoke-virtual {v1, v2, v3, v4}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    :cond_0
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStopRequest:Z

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mLongPressedAutoFocus:Z

    if-eqz v1, :cond_2

    if-eqz p1, :cond_1

    const/16 p1, 0xa

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->onShutterButtonClick(I)V

    goto :goto_0

    :cond_1
    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mLongPressedAutoFocus:Z

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->cancelLongPressedAutoFocus()V

    :cond_2
    :goto_0
    return-void
.end method

.method public onSingleTapUp(IIZ)V
    .locals 4

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onSingleTapUp mPaused: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, "; mCamera2Device: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v2, "; isInCountDown: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInCountDown()Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, "; getCameraState: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, "; mMultiSnapStatus: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, "; Camera2Module: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-nez v0, :cond_d

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_d

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->hasCameraException()Z

    move-result v0

    if-nez v0, :cond_d

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isSessionReady()Z

    move-result v0

    if-eqz v0, :cond_d

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isPreviewReady()Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->isInTapableRect(II)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x3

    if-eq v0, v1, :cond_d

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x4

    if-eq v0, v1, :cond_d

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-eqz v0, :cond_d

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isInCountDown()Z

    move-result v0

    if-nez v0, :cond_d

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-eqz v0, :cond_0

    goto/16 :goto_3

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    const/4 v1, 0x1

    if-eqz v0, :cond_3

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xac

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v2

    const/16 v3, 0xc2

    invoke-virtual {v2, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v2

    check-cast v2, Lcom/android/camera/protocol/ModeProtocol$MiBeautyProtocol;

    const/4 v3, 0x0

    if-eqz v2, :cond_1

    invoke-interface {v2}, Lcom/android/camera/protocol/ModeProtocol$MiBeautyProtocol;->isBeautyPanelShow()Z

    move-result v2

    if-eqz v2, :cond_1

    move v2, v1

    goto :goto_0

    :cond_1
    nop

    move v2, v3

    :goto_0
    if-eqz v0, :cond_2

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->isExtraMenuShowing()Z

    move-result v0

    if-eqz v0, :cond_2

    move v3, v1

    goto :goto_1

    :cond_2
    nop

    :goto_1
    if-nez v2, :cond_3

    if-nez v3, :cond_3

    return-void

    :cond_3
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrameAvailable()Z

    move-result v0

    if-nez v0, :cond_4

    return-void

    :cond_4
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    if-eqz v0, :cond_5

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->isScreenSlideOff()Z

    move-result v0

    if-eqz v0, :cond_5

    return-void

    :cond_5
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xab

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BackStack;

    if-eqz v0, :cond_c

    invoke-interface {v0, p1, p2}, Lcom/android/camera/protocol/ModeProtocol$BackStack;->handleBackStackFromTapDown(II)Z

    move-result v0

    if-eqz v0, :cond_6

    goto :goto_2

    :cond_6
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->tryRemoveCountDownMessage()V

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusAreaSupported:Z

    if-nez v0, :cond_7

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMeteringAreaSupported:Z

    if-nez v0, :cond_7

    return-void

    :cond_7
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v0}, Lcom/android/camera/MutexModeManager;->isUbiFocus()Z

    move-result v0

    if-eqz v0, :cond_8

    return-void

    :cond_8
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mObjectTrackingStarted:Z

    if-eqz v0, :cond_9

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->stopObjectTracking(Z)V

    :cond_9
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setFocusViewType(Z)V

    new-instance v0, Landroid/graphics/Point;

    invoke-direct {v0, p1, p2}, Landroid/graphics/Point;-><init>(II)V

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->mapTapCoordinate(Ljava/lang/Object;)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->unlockAEAF()V

    const/4 p1, 0x2

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget p2, v0, Landroid/graphics/Point;->x:I

    iget v0, v0, Landroid/graphics/Point;->y:I

    invoke-virtual {p1, p2, v0, p3}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onSingleTapUp(IIZ)V

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusAreaSupported:Z

    if-nez p1, :cond_a

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mMeteringAreaSupported:Z

    if-eqz p1, :cond_a

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/SensorStateManager;->reset()V

    :cond_a
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 p2, 0xe3

    invoke-virtual {p1, p2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/module/impl/component/CameraClickObservableImpl;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p2

    const/16 v0, 0xaf

    invoke-virtual {p2, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p2

    check-cast p2, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-nez p3, :cond_b

    if-eqz p1, :cond_b

    const/16 p2, 0xa5

    invoke-virtual {p1, p2}, Lcom/android/camera/module/impl/component/CameraClickObservableImpl;->subscribe(I)V

    :cond_b
    return-void

    :cond_c
    :goto_2
    return-void

    :cond_d
    :goto_3
    return-void
.end method

.method public onStop()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onStop()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    :cond_0
    return-void
.end method

.method public onSuperNightChanged(Z)V
    .locals 3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v0

    if-ne p1, v0, :cond_0

    return-void

    :cond_0
    invoke-static {p1}, Lcom/android/camera/CameraSettings;->setSuperNightOn(Z)V

    const/4 v0, 0x1

    if-eqz p1, :cond_1

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    goto :goto_0

    :cond_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v1}, Lcom/android/camera/MutexModeManager;->isSuperResolution()Z

    move-result v1

    if-eqz v1, :cond_2

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    :cond_2
    :goto_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    xor-int/2addr p1, v0

    invoke-interface {v1, p1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setEvAdjustable(Z)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateSuperNight()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void
.end method

.method public onSurfaceTextureReleased()V
    .locals 2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "onSurfaceTextureReleased: no further preview frame will be available"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onSurfaceTextureUpdated(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    invoke-virtual {v0, p1}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->onSurfaceTextureUpdated(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    :cond_0
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->onPreviewComing()V

    :cond_1
    return-void
.end method

.method public onThumbnailClicked(Landroid/view/View;)V
    .locals 1

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-nez p1, :cond_1

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    if-eqz p1, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result p1

    if-eqz p1, :cond_2

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "onThumbnailClicked: DoingAction.."

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    :goto_0
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isCannotGotoGallery()Z

    move-result p1

    if-eqz p1, :cond_2

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v0, "onThumbnailClicked: CannotGotoGallery..."

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_2
    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getThumbnailUpdater()Lcom/android/camera/ThumbnailUpdater;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/ThumbnailUpdater;->getThumbnail()Lcom/android/camera/Thumbnail;

    move-result-object p1

    if-eqz p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->gotoGallery()V

    :cond_3
    return-void
.end method

.method public onTiltShiftSwitched(Z)V
    .locals 1

    if-eqz p1, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->resetEvValue()V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->initEffectCropView()V

    const/4 v0, 0x2

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->updatePreferenceTrampoline([I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->updateEffectViewVisible()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    xor-int/lit8 p1, p1, 0x1

    invoke-interface {v0, p1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setEvAdjustable(Z)V

    return-void

    :array_0
    .array-data 4
        0x2
        0x5
    .end array-data
.end method

.method public onUltraWideChanged(ZI)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$X79wvihnMCnj9TzUB5XW2yNIAVw;

    invoke-direct {v1, p0, p1, p2}, Lcom/android/camera/module/-$$Lambda$Camera2Module$X79wvihnMCnj9TzUB5XW2yNIAVw;-><init>(Lcom/android/camera/module/Camera2Module;ZI)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public onUserInteraction()V
    .locals 1

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->onUserInteraction()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->keepScreenOnAwhile()V

    :cond_0
    return-void
.end method

.method public onWaitingFocusFinished()Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBlockSnap()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_1

    return v1

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getTriggerMode()I

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->startNormalCapture(I)V

    const/4 v0, 0x1

    return v0
.end method

.method public onZoomingActionEnd(I)V
    .locals 3

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onZoomingActionEnd(): "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p1}, Lcom/android/camera/ui/zoom/ZoomingAction;->toString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xb6

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$DualController;

    if-eqz v0, :cond_1

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$DualController;->isSlideVisible()Z

    move-result v1

    if-nez v1, :cond_1

    const/4 v1, 0x1

    if-eq p1, v1, :cond_0

    const/4 v1, 0x2

    if-ne p1, v1, :cond_1

    :cond_0
    const/4 p1, 0x0

    invoke-interface {v0, p1}, Lcom/android/camera/protocol/ModeProtocol$DualController;->setImmersiveModeEnabled(Z)V

    :cond_1
    return-void
.end method

.method public onZoomingActionStart(I)V
    .locals 4

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onZoomingActionStart(): "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p1}, Lcom/android/camera/ui/zoom/ZoomingAction;->toString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->isExtraMenuShowing()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->hideExtraMenu()V

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isZoomEnabled()Z

    move-result v0

    const/4 v1, 0x1

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xaf

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz v0, :cond_1

    const/16 v2, 0xf

    invoke-static {}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getNoSupportZoomTip()Ljava/lang/String;

    move-result-object v3

    invoke-interface {v0, v2, v3, v1}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->showTips(ILjava/lang/String;I)V

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xb6

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$DualController;

    if-eqz v0, :cond_3

    if-eq p1, v1, :cond_2

    const/4 v2, 0x2

    if-ne p1, v2, :cond_3

    :cond_2
    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$DualController;->setImmersiveModeEnabled(Z)V

    :cond_3
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xe3

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;

    if-eqz p1, :cond_4

    const/16 v0, 0xa8

    invoke-interface {p1, v0}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->subscribe(I)V

    :cond_4
    return-void
.end method

.method protected openSettingActivity()V
    .locals 4

    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const-class v2, Lcom/android/camera/CameraPreferenceActivity;

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    const-string v1, "from_where"

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "IsCaptureIntent"

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    const-string v1, ":miui:starting_window_label"

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f090049

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->startFromKeyguard()Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v1, "StartActivityWhenLocked"

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    :cond_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1, v0}, Lcom/android/camera/Camera;->startActivity(Landroid/content/Intent;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->setJumpFlag(I)V

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/statistic/CameraStatUtil;->trackGotoSettings(I)V

    return-void
.end method

.method public pausePreview()V
    .locals 2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "pausePreview"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->pausePreview()V

    :cond_0
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setCameraState(I)V

    return-void
.end method

.method protected performKeyClicked(ILjava/lang/String;IZ)V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-nez v0, :cond_6

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_1

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->restoreBottom()V

    :cond_1
    const/4 v0, 0x1

    if-nez p3, :cond_4

    if-eqz p4, :cond_3

    invoke-virtual {p0, v0, v0}, Lcom/android/camera/module/Camera2Module;->onShutterButtonFocus(ZI)V

    const p3, 0x7f090129

    invoke-virtual {p0, p3}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p2, p3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_2

    const/4 p1, 0x2

    const/16 p2, 0x14

    invoke-direct {p0, p1, p2}, Lcom/android/camera/module/Camera2Module;->startCount(II)V

    goto :goto_0

    :cond_2
    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->onShutterButtonClick(I)V

    goto :goto_0

    :cond_3
    const/4 p1, 0x0

    invoke-virtual {p0, p1, p1}, Lcom/android/camera/module/Camera2Module;->onShutterButtonFocus(ZI)V

    iget-boolean p2, p0, Lcom/android/camera/module/Camera2Module;->mVolumeLongPress:Z

    if-eqz p2, :cond_5

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mVolumeLongPress:Z

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->onShutterButtonLongClickCancel(Z)V

    goto :goto_0

    :cond_4
    if-eqz p4, :cond_5

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mVolumeLongPress:Z

    if-nez p1, :cond_5

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->onShutterButtonLongClick()Z

    move-result p1

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mVolumeLongPress:Z

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mVolumeLongPress:Z

    if-nez p1, :cond_5

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mLongPressedAutoFocus:Z

    if-eqz p1, :cond_5

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mVolumeLongPress:Z

    :cond_5
    :goto_0
    return-void

    :cond_6
    :goto_1
    return-void
.end method

.method public playFocusSound(I)V
    .locals 0

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->playCameraSound(I)V

    return-void
.end method

.method public reShowMoon()V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnteringMoonMode:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->showMoonMode(Z)Z

    return-void
.end method

.method public registerProtocol()V
    .locals 6

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->registerProtocol()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa1

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa9

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa5

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xc1

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xc3

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImplFactory()Lcom/android/camera/module/impl/ImplFactory;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v1

    const/4 v2, 0x4

    new-array v3, v2, [I

    fill-array-data v3, :array_0

    invoke-virtual {v0, v1, v3}, Lcom/android/camera/module/impl/ImplFactory;->initAdditional(Lcom/android/camera/ActivityBase;[I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    const/16 v1, 0xa3

    if-ne v0, v1, :cond_0

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xe3

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;

    if-eqz v0, :cond_0

    new-array v1, v2, [I

    fill-array-data v1, :array_1

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCameraClickObserverAction:Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable$ClickObserver;

    const/16 v3, 0x8

    new-array v3, v3, [I

    fill-array-data v3, :array_2

    invoke-interface {v0, v1, v2, v3}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->addObservable([ILcom/android/camera/protocol/ModeProtocol$CameraClickObservable$ClickObserver;[I)V

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getModuleIndex()I

    move-result v0

    const/16 v1, 0xad

    const/4 v2, 0x0

    const/4 v3, 0x1

    if-ne v0, v1, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImplFactory()Lcom/android/camera/module/impl/ImplFactory;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v1

    new-array v4, v3, [I

    const/16 v5, 0xd4

    aput v5, v4, v2

    invoke-virtual {v0, v1, v4}, Lcom/android/camera/module/impl/ImplFactory;->initAdditional(Lcom/android/camera/ActivityBase;[I)V

    :cond_1
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "registerProtocol: mIsGoogleLensAvailable = "

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v4, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v4, ", activity is null ? "

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-nez v4, :cond_2

    move v2, v3

    nop

    :cond_2
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$325jVf9Rqffi7VeB_RP64qHE4x8;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$325jVf9Rqffi7VeB_RP64qHE4x8;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_3
    return-void

    :array_0
    .array-data 4
        0xa4
        0xae
        0xc7
        0xe3
    .end array-data

    :array_1
    .array-data 4
        0x7f0903a4
        0x7f0903a5
        0x7f090209
        0x7f0903a6
    .end array-data

    :array_2
    .array-data 4
        0xa1
        0xa2
        0xa6
        0xa3
        0xa4
        0xa5
        0xa7
        0xa8
    .end array-data
.end method

.method protected resetMetaDataManager()V
    .locals 0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSupportedMetadata()Z

    return-void
.end method

.method public resumePreview()V
    .locals 2

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string v1, "resumePreview"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->previewWhenSessionSuccess()V

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraQuickShotEnable()Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mBlockQuickShot:Z

    return-void
.end method

.method public scanQRCodeEnabled()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isScanQRCode(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa3

    if-ne v0, v1, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mMultiSnapStatus:Z

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isStereoModeOn()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isPortraitModeBackOn()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->hf()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-nez v0, :cond_1

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isUltraWideConfigOpen(I)Z

    move-result v0

    if-nez v0, :cond_1

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    if-nez v0, :cond_1

    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method protected sendOpenFailMessage()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0xa

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public setAsdScenes([Lcom/android/camera2/vendortag/struct/MarshalQueryableASDScene$ASDScene;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mAsdScenes:[Lcom/android/camera2/vendortag/struct/MarshalQueryableASDScene$ASDScene;

    return-void
.end method

.method public setFrameAvailable(Z)V
    .locals 2

    invoke-super {p0, p1}, Lcom/android/camera/module/BaseModule;->setFrameAvailable(Z)V

    if-eqz p1, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraSoundOpen()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->loadCameraSound(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->loadCameraSound(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->loadCameraSound(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const/4 v1, 0x5

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->loadCameraSound(I)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    const/4 v1, 0x7

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->loadCameraSound(I)V

    :cond_0
    if-eqz p1, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->releaseFakeSurfaceIfNeed()V

    :cond_1
    if-eqz p1, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isBackCamera()Z

    move-result p1

    if-eqz p1, :cond_3

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v0, 0xa5

    if-eq p1, v0, :cond_2

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v0, 0xa3

    if-ne p1, v0, :cond_3

    :cond_2
    invoke-static {}, Lcom/android/camera/CameraSettings;->isCameraLyingHintOn()Z

    move-result p1

    if-eqz p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v0, Lcom/android/camera/module/-$$Lambda$Camera2Module$e6odMFftkPBIREZ9caw-CZirioM;

    invoke-direct {v0, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$e6odMFftkPBIREZ9caw-CZirioM;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {p1, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_3
    return-void
.end method

.method public shouldCaptureDirectly()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mUseLegacyFlashMode:Z

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isNeedFlashOn()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method protected shouldCheckSatFallbackState()Z
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isIn3OrMoreSatMode()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->shouldCheckSatFallbackState()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public shouldReleaseLater()Z
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsImageCaptureIntent:Z

    if-nez v0, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x3

    if-eq v0, v1, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableShot2Gallery:Z

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x32

    invoke-virtual {v0, v1}, Landroid/os/Handler;->hasMessages(I)Z

    move-result v0

    if-eqz v0, :cond_3

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x30

    invoke-virtual {v0, v1}, Landroid/os/Handler;->hasMessages(I)Z

    move-result v0

    if-nez v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x31

    invoke-virtual {v0, v1}, Landroid/os/Handler;->hasMessages(I)Z

    move-result v0

    if-nez v0, :cond_3

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->isFocusing()Z

    move-result v0

    if-nez v0, :cond_3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa7

    if-ne v0, v1, :cond_2

    const-string v0, "pref_qc_camera_exposuretime_key"

    const v1, 0x7f090142

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v0, v2}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    :cond_2
    const/4 v0, 0x1

    goto :goto_0

    :cond_3
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public showBacklightTip()V
    .locals 6

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa4

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    const-string v2, "e"

    const/4 v3, 0x1

    new-array v3, v3, [I

    const/16 v4, 0xc1

    const/4 v5, 0x0

    aput v4, v3, v5

    invoke-interface {v1, v2, v3}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->closeMutexElement(Ljava/lang/String;[I)V

    const/16 v1, 0x8

    invoke-interface {v0, v1, v5, v5}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertHDR(IZZ)V

    invoke-interface {v0, v5}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertAiSceneSelector(I)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->applyBacklightEffect()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v5}, Lcom/android/camera2/Camera2Proxy;->setASD(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void
.end method

.method public showOrHideChip(Z)V
    .locals 10

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xaf

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz p1, :cond_6

    if-eqz v0, :cond_1

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->isTipShowing()Z

    move-result p1

    if-eqz p1, :cond_1

    move p1, v2

    goto :goto_0

    :cond_1
    nop

    move p1, v1

    :goto_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v3

    const/16 v4, 0xb6

    invoke-virtual {v3, v4}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v3

    check-cast v3, Lcom/android/camera/protocol/ModeProtocol$DualController;

    if-eqz v3, :cond_2

    invoke-interface {v3}, Lcom/android/camera/protocol/ModeProtocol$DualController;->isSlideVisible()Z

    move-result v3

    if-eqz v3, :cond_2

    move v3, v2

    goto :goto_1

    :cond_2
    nop

    move v3, v1

    :goto_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v4

    const/16 v5, 0xb4

    invoke-virtual {v4, v5}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v4

    check-cast v4, Lcom/android/camera/protocol/ModeProtocol$MakeupProtocol;

    if-eqz v4, :cond_3

    invoke-interface {v4}, Lcom/android/camera/protocol/ModeProtocol$MakeupProtocol;->isSeekBarVisible()Z

    move-result v4

    if-eqz v4, :cond_3

    move v4, v2

    goto :goto_2

    :cond_3
    nop

    move v4, v1

    :goto_2
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v5

    const/16 v6, 0xc2

    invoke-virtual {v5, v6}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v5

    check-cast v5, Lcom/android/camera/protocol/ModeProtocol$MiBeautyProtocol;

    if-eqz v5, :cond_4

    invoke-interface {v5}, Lcom/android/camera/protocol/ModeProtocol$MiBeautyProtocol;->isBeautyPanelShow()Z

    move-result v5

    if-eqz v5, :cond_4

    move v5, v2

    goto :goto_3

    :cond_4
    nop

    move v5, v1

    :goto_3
    iget-boolean v6, p0, Lcom/android/camera/module/Camera2Module;->mIsAiConflict:Z

    if-nez v6, :cond_5

    iget-boolean v6, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    if-nez v6, :cond_5

    iget-boolean v6, p0, Lcom/android/camera/module/Camera2Module;->mIsUltraWideConflict:Z

    if-nez v6, :cond_5

    iget-boolean v6, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-nez v6, :cond_5

    if-nez p1, :cond_5

    if-nez v3, :cond_5

    if-nez v4, :cond_5

    if-nez v5, :cond_5

    move v6, v2

    goto :goto_4

    :cond_5
    nop

    move v6, v1

    :goto_4
    sget-object v7, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "pre showOrHideChip: isTipsShow = "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", isZoomSlideVisible = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", isSeekBarVisible = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", isMakeupVisible = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", mIsAiConflict = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsAiConflict:Z

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", mIsUltraWideConflict = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsUltraWideConflict:Z

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", mIsMoonMode = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", mIsFaceConflict = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsFaceConflict:Z

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", final isShow = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", mIsCurrentLensEnabled = "

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsCurrentLensEnabled:Z

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v7, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    move p1, v6

    :cond_6
    iget-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mIsCurrentLensEnabled:Z

    if-ne v3, p1, :cond_7

    return-void

    :cond_7
    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsCurrentLensEnabled:Z

    sget-object v3, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v5, "showOrHideChip: show = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v5, ", isChipsEnabled = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/android/camera/CameraSettings;->isAvailableChipsGoogleLens()Z

    move-result v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/lens/LensAgent;->getInstance()Lcom/android/lens/LensAgent;

    move-result-object v3

    if-eqz p1, :cond_8

    invoke-static {}, Lcom/android/camera/CameraSettings;->isAvailableChipsGoogleLens()Z

    move-result p1

    if-eqz p1, :cond_8

    move v1, v2

    nop

    :cond_8
    invoke-virtual {v3, v1}, Lcom/android/lens/LensAgent;->showOrHideChip(Z)V

    if-eqz v0, :cond_9

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->reConfigQrCodeTip()Z

    :cond_9
    return-void
.end method

.method public showQRCodeResult()V
    .locals 4

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-eqz v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/zxing/PreviewDecodeManager;->getScanResult()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_2

    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_1

    goto :goto_0

    :cond_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/ActivityBase;->dismissKeyguard()V

    new-instance v2, Landroid/content/Intent;

    const-string v3, "com.xiaomi.scanner.receiver.senderbarcodescanner"

    invoke-direct {v2, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const/16 v3, 0x20

    invoke-virtual {v2, v3}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    const-string v3, "com.xiaomi.scanner"

    invoke-virtual {v2, v3}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    const-string v3, "result"

    invoke-virtual {v2, v3, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    invoke-virtual {v1, v2}, Lcom/android/camera/ActivityBase;->sendBroadcast(Landroid/content/Intent;)V

    const/4 v0, 0x3

    invoke-virtual {v1, v0}, Lcom/android/camera/ActivityBase;->setJumpFlag(I)V

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/zxing/PreviewDecodeManager;->resetScanResult()V

    return-void

    :cond_2
    :goto_0
    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    const-string/jumbo v1, "showQRCodeResult: get a null result!"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public startAiLens()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$14;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$14;-><init>(Lcom/android/camera/module/Camera2Module;)V

    const-wide/16 v2, 0x12c

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    return-void
.end method

.method public startFaceDetection()V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    if-eqz v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    if-nez v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->isActivityPaused()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mMaxFaceCount:I

    if-lez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v1, v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setActiveIndicator(I)V

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->startFaceDetection()V

    invoke-virtual {p0, v0, v0}, Lcom/android/camera/module/Camera2Module;->updateFaceView(ZZ)V

    :cond_1
    return-void

    :cond_2
    :goto_0
    return-void
.end method

.method public startFocus()V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDeviceAlive()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrameAvailable()Z

    move-result v0

    if-nez v0, :cond_1

    return-void

    :cond_1
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFocusOrAELockSupported:Z

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v1, 0x1

    invoke-static {v1}, Lcom/android/camera/module/loader/camera2/FocusTask;->create(I)Lcom/android/camera/module/loader/camera2/FocusTask;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1, v2}, Lcom/android/camera2/Camera2Proxy;->startFocus(Lcom/android/camera/module/loader/camera2/FocusTask;I)V

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->resumePreview()I

    :goto_0
    return-void
.end method

.method public startLiveShot()V
    .locals 8

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorderStateLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    if-nez v1, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/Camera;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->getEGLContext14()Landroid/opengl/EGLContext;

    move-result-object v5

    new-instance v1, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mVideoSize:Lcom/android/camera/CameraSize;

    iget v3, v2, Lcom/android/camera/CameraSize;->width:I

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mVideoSize:Lcom/android/camera/CameraSize;

    iget v4, v2, Lcom/android/camera/CameraSize;->height:I

    iget-boolean v6, p0, Lcom/android/camera/module/Camera2Module;->mIsMicrophoneEnabled:Z

    iget-object v7, p0, Lcom/android/camera/module/Camera2Module;->mLivePhotoQueue:Ljava/util/Queue;

    move-object v2, v1

    invoke-direct/range {v2 .. v7}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;-><init>(IILandroid/opengl/EGLContext;ZLjava/util/Queue;)V

    iput-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    :cond_0
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mLiveShotEnabled:Z

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    iget v3, p0, Lcom/android/camera/module/Camera2Module;->mOrientationCompensation:I

    invoke-virtual {v2, v3}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->setOrientationHint(I)V

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    invoke-virtual {v2}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->start()V

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/camera/SensorStateManager;->setGyroscopeEnabled(Z)V

    return-void

    :catchall_0
    move-exception v1

    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v1
.end method

.method public startObjectTracking()V
    .locals 0

    return-void
.end method

.method public startPreview()V
    .locals 13

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_c

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, p0}, Lcom/android/camera2/Camera2Proxy;->setFocusCallback(Lcom/android/camera2/Camera2Proxy$FocusCallback;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, p0}, Lcom/android/camera2/Camera2Proxy;->setMetaDataCallback(Lcom/android/camera2/Camera2Proxy$CameraMetaDataCallback;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, p0}, Lcom/android/camera2/Camera2Proxy;->setScreenLightCallback(Lcom/android/camera2/Camera2Proxy$ScreenLightCallback;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mErrorCallback:Lcom/android/camera/CameraErrorCallbackImpl;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setErrorCallback(Lcom/android/camera2/Camera2Proxy$CameraErrorCallback;)V

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "startPreview: set PictureSize with "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mPictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setPictureSize(Lcom/android/camera/CameraSize;)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isSensorRawStreamRequired()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "startPreview: set SensorRawImageSize with "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isCurrentRawDomainBasedSuperNight()Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/android/camera2/Camera2Proxy;->setSensorRawImageSize(Lcom/android/camera/CameraSize;Z)V

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isPortraitMode()Z

    move-result v0

    if-eqz v0, :cond_1

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "startPreview: set SubPictureSize with "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mSubPictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSubPictureSize:Lcom/android/camera/CameraSize;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setSubPictureSize(Lcom/android/camera/CameraSize;)V

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isEnableQcfaForAlgoUp()Z

    move-result v0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "[QCFA] startPreview: set qcfa enable "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setQcfaEnable(Z)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->scanQRCodeEnabled()Z

    move-result v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->supportHandGesture()Z

    move-result v1

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-nez v2, :cond_3

    if-nez v0, :cond_3

    if-eqz v1, :cond_2

    goto :goto_0

    :cond_2
    move v7, v4

    goto :goto_1

    :cond_3
    :goto_0
    nop

    move v7, v3

    :goto_1
    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v2, :cond_4

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v2

    iget v5, p0, Lcom/android/camera/module/Camera2Module;->mActualCameraId:I

    const/4 v6, 0x2

    invoke-virtual {v2, v5, v6}, Lcom/android/zxing/PreviewDecodeManager;->init(II)V

    :cond_4
    if-eqz v0, :cond_5

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v0

    iget v2, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-virtual {v0, v2, v4}, Lcom/android/zxing/PreviewDecodeManager;->init(II)V

    :cond_5
    if-eqz v1, :cond_6

    invoke-static {}, Lcom/android/zxing/PreviewDecodeManager;->getInstance()Lcom/android/zxing/PreviewDecodeManager;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mBogusCameraId:I

    invoke-virtual {v0, v1, v3}, Lcom/android/zxing/PreviewDecodeManager;->init(II)V

    :cond_6
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraScreenNail;->getSurfaceTexture()Landroid/graphics/SurfaceTexture;

    move-result-object v0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v5, "startPreview: surfaceTexture = "

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz v0, :cond_7

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/CameraScreenNail;->getSurfaceCreatedTimestamp()J

    move-result-wide v1

    iput-wide v1, p0, Lcom/android/camera/module/Camera2Module;->mSurfaceCreatedTimestamp:J

    :cond_7
    if-eqz v0, :cond_8

    new-instance v1, Landroid/view/Surface;

    invoke-direct {v1, v0}, Landroid/view/Surface;-><init>(Landroid/graphics/SurfaceTexture;)V

    move-object v6, v1

    goto :goto_2

    :cond_8
    const/4 v0, 0x0

    move-object v6, v0

    :goto_2
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isSensorRawStreamRequired()Z

    move-result v0

    if-eqz v0, :cond_9

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mSensorRawImageSize:Lcom/android/camera/CameraSize;

    if-eqz v0, :cond_9

    goto :goto_3

    :cond_9
    move v3, v4

    :goto_3
    iput-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mConfigRawStream:Z

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getOperatingMode()I

    move-result v0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isMacro2Sat()Z

    move-result v1

    if-eqz v1, :cond_a

    const v1, 0x9002

    if-ne v1, v0, :cond_a

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v1

    invoke-virtual {v1}, Lcom/mi/config/a;->iB()Z

    move-result v1

    if-eqz v1, :cond_a

    invoke-static {}, Lcom/android/camera/CameraSettings;->getLensIndex()I

    move-result v1

    shl-int/lit8 v2, v1, 0x8

    or-int/2addr v0, v2

    sget-object v2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "getOperatingMode = "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v2, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Index = "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_a
    move v10, v0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isMacro2Sat()Z

    move-result v0

    if-eqz v0, :cond_b

    invoke-static {v4}, Lcom/android/camera/CameraSettings;->setMacro2Sat(Z)V

    :cond_b
    iget-object v5, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-boolean v8, p0, Lcom/android/camera/module/Camera2Module;->mConfigRawStream:Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->isCurrentRawDomainBasedSuperNight()Z

    move-result v9

    iget-boolean v11, p0, Lcom/android/camera/module/Camera2Module;->mEnableParallelSession:Z

    move-object v12, p0

    invoke-virtual/range {v5 .. v12}, Lcom/android/camera2/Camera2Proxy;->startPreviewSession(Landroid/view/Surface;ZZZIZLcom/android/camera2/Camera2Proxy$CameraPreviewCallback;)V

    :cond_c
    invoke-static {}, Lcom/android/camera/parallel/AlgoConnector;->getInstance()Lcom/android/camera/parallel/AlgoConnector;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/parallel/AlgoConnector;->getLocalBinder()Lcom/android/camera/LocalParallelService$LocalBinder;

    move-result-object v0

    if-eqz v0, :cond_d

    invoke-static {}, Lcom/android/camera/CameraSettings;->isPictureFlawCheckOn()Z

    move-result v1

    if-eqz v1, :cond_d

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mSessionStatusCallbackListener:Lcom/xiaomi/protocol/ISessionStatusCallBackListener;

    invoke-virtual {v0, v1}, Lcom/android/camera/LocalParallelService$LocalBinder;->setOnSessionStatusCallBackListener(Lcom/xiaomi/protocol/ISessionStatusCallBackListener;)V

    :cond_d
    return-void
.end method

.method public startScreenLight(II)V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mPaused:Z

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$20;

    invoke-direct {v1, p0, p1, p2}, Lcom/android/camera/module/Camera2Module$20;-><init>(Lcom/android/camera/module/Camera2Module;II)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public stopFaceDetection(Z)V
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionEnabled:Z

    if-eqz v0, :cond_3

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/mi/config/b;->isMTKPlatform()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    const/4 v1, 0x3

    if-eq v0, v1, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getCameraState()I

    move-result v0

    if-eqz v0, :cond_2

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->stopFaceDetection()V

    :cond_2
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mFaceDetectionStarted:Z

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    const/4 v2, 0x2

    invoke-interface {v1, v2}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->setActiveIndicator(I)V

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/module/Camera2Module;->updateFaceView(ZZ)V

    return-void

    :cond_3
    :goto_0
    return-void
.end method

.method public stopLiveShot(Z)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorderStateLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    invoke-virtual {v1}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->stop()V

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    invoke-virtual {p1}, Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;->release()V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCircularMediaRecorder:Lcom/xiaomi/camera/liveshot/CircularMediaRecorder;

    :cond_0
    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mLiveShotEnabled:Z

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/android/camera/SensorStateManager;->setGyroscopeEnabled(Z)V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mLivePhotoQueue:Ljava/util/Queue;

    invoke-interface {p1}, Ljava/util/Queue;->clear()V

    return-void

    :catchall_0
    move-exception p1

    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p1
.end method

.method public stopObjectTracking(Z)V
    .locals 0

    return-void
.end method

.method public stopScreenLight()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$21;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$21;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method protected trackModeCustomInfo(I)V
    .locals 3

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa7

    if-ne v0, v1, :cond_0

    invoke-direct {p0, p1}, Lcom/android/camera/module/Camera2Module;->trackManualInfo(I)V

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa3

    if-ne v0, v1, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->isFaceBeautyOn(Lcom/android/camera/fragment/beauty/BeautyValues;)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isFrontCamera()Z

    move-result v0

    new-instance v1, Lcom/android/camera/fragment/beauty/BeautyValues;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-direct {v1, v2}, Lcom/android/camera/fragment/beauty/BeautyValues;-><init>(Lcom/android/camera/fragment/beauty/BeautyValues;)V

    invoke-direct {p0, p1, v0, v1}, Lcom/android/camera/module/Camera2Module;->trackBeautyInfo(IZLcom/android/camera/fragment/beauty/BeautyValues;)V

    :cond_1
    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtil;->trackUltraWidePictureTaken()V

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mIsShowLyingDirectHintStatus:I

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtil;->trackLyingDirectPictureTaken(I)V

    goto :goto_0

    :cond_2
    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v0, 0xa5

    if-ne p1, v0, :cond_3

    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtil;->trackUltraWidePictureTaken()V

    iget p1, p0, Lcom/android/camera/module/Camera2Module;->mIsShowLyingDirectHintStatus:I

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtil;->trackLyingDirectPictureTaken(I)V

    :cond_3
    :goto_0
    return-void
.end method

.method public tryRemoveCountDownMessage()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCountdownDisposable:Lio/reactivex/disposables/Disposable;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCountdownDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v0}, Lio/reactivex/disposables/Disposable;->isDisposed()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCountdownDisposable:Lio/reactivex/disposables/Disposable;

    invoke-interface {v0}, Lio/reactivex/disposables/Disposable;->dispose()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCountdownDisposable:Lio/reactivex/disposables/Disposable;

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/module/Camera2Module$9;

    invoke-direct {v1, p0}, Lcom/android/camera/module/Camera2Module$9;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_0
    return-void
.end method

.method public unRegisterProtocol()V
    .locals 3

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->unRegisterProtocol()V

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "unRegisterProtocol: mIsGoogleLensAvailable = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, ", activity is null ? "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-nez v2, :cond_0

    const/4 v2, 0x1

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsGoogleLensAvailable:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$UkytpyAlcW8SHq35N2p-nRCW_yM;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$UkytpyAlcW8SHq35N2p-nRCW_yM;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa1

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa9

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa5

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xc1

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xc3

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImplFactory()Lcom/android/camera/module/impl/ImplFactory;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/impl/ImplFactory;->detachAdditional()V

    return-void
.end method

.method public updateBacklight()V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isSilhouette:Z

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->applyBacklightEffect()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void

    :cond_1
    :goto_0
    return-void
.end method

.method protected updateFaceView(ZZ)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x23

    invoke-virtual {v0, v1}, Landroid/os/Handler;->hasMessages(I)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeMessages(I)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    nop

    invoke-virtual {v0, v1, p1, p2}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    move-result-object p1

    invoke-virtual {p1}, Landroid/os/Message;->sendToTarget()V

    return-void
.end method

.method public updateFlashPreference()V
    .locals 5

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->getRequestFlashMode()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    invoke-static {v1, v2}, Lcom/android/camera/Util;->parseInt(Ljava/lang/String;I)I

    move-result v3

    if-eqz v3, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resetAiSceneInHdrOrFlashOn()V

    :cond_0
    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->setFlashMode(Ljava/lang/String;)V

    iget-object v3, p0, Lcom/android/camera/module/Camera2Module;->mLastFlashMode:Ljava/lang/String;

    invoke-static {v0, v3}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_2

    invoke-static {v0, v2}, Lcom/android/camera/Util;->parseInt(Ljava/lang/String;I)I

    move-result v3

    const/16 v4, 0x67

    if-eq v3, v4, :cond_1

    invoke-static {v0, v2}, Lcom/android/camera/Util;->parseInt(Ljava/lang/String;I)I

    move-result v3

    if-nez v3, :cond_2

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resetAsdSceneInHdrOrFlashChange()V

    :cond_2
    iput-object v0, p0, Lcom/android/camera/module/Camera2Module;->mLastFlashMode:Ljava/lang/String;

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->stopObjectTracking(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v3

    const/16 v4, 0xac

    invoke-virtual {v3, v4}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v3

    check-cast v3, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v3, :cond_4

    const-string v4, "200"

    invoke-virtual {v1, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/16 v4, 0xc1

    if-eqz v1, :cond_3

    new-array v0, v0, [I

    aput v4, v0, v2

    invoke-interface {v3, v2, v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->disableMenuItem(Z[I)V

    goto :goto_0

    :cond_3
    new-array v0, v0, [I

    aput v4, v0, v2

    invoke-interface {v3, v2, v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->enableMenuItem(Z[I)V

    :cond_4
    :goto_0
    return-void
.end method

.method public updateHDRPreference()V
    .locals 4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_0

    return-void

    :cond_0
    iget v1, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v1

    const/high16 v2, 0x3f800000    # 1.0f

    cmpl-float v1, v1, v2

    const/4 v3, 0x0

    if-nez v1, :cond_1

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mMotionDetected:Z

    if-eqz v1, :cond_2

    :cond_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mMutexModePicker:Lcom/android/camera/MutexModeManager;

    invoke-virtual {v1}, Lcom/android/camera/MutexModeManager;->isHdr()Z

    move-result v1

    if-eqz v1, :cond_2

    const-string v1, "auto"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-virtual {p0, v3}, Lcom/android/camera/module/Camera2Module;->onHdrSceneChanged(Z)V

    :cond_2
    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-nez v1, :cond_4

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mMotionDetected:Z

    if-nez v1, :cond_4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v1

    if-eqz v1, :cond_3

    goto :goto_0

    :cond_3
    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->updateHDR(Ljava/lang/String;)V

    goto :goto_1

    :cond_4
    :goto_0
    const-string v1, "off"

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->updateHDR(Ljava/lang/String;)V

    :goto_1
    const-string v1, "off"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_5

    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mAiSceneEnabled:Z

    if-eqz v1, :cond_a

    :cond_5
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result v1

    cmpl-float v1, v1, v2

    if-lez v1, :cond_6

    const-string v1, "normal"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_a

    :cond_6
    iget-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-nez v1, :cond_a

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v1

    invoke-virtual {v1}, Lcom/mi/config/a;->ii()Z

    move-result v1

    if-eqz v1, :cond_7

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isStandaloneMacroCamera()Z

    move-result v1

    if-eqz v1, :cond_7

    const-string v1, "auto"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_a

    :cond_7
    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v1

    if-eqz v1, :cond_8

    goto :goto_3

    :cond_8
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resetAiSceneInHdrOrFlashOn()V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resetAsdSceneInHdrOrFlashChange()V

    const-string v1, "auto"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_9

    iput-boolean v1, p0, Lcom/android/camera/module/Camera2Module;->mHdrCheckEnabled:Z

    goto :goto_2

    :cond_9
    iput-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mHdrCheckEnabled:Z

    :goto_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setHDRCheckerEnable(Z)V

    goto :goto_4

    :cond_a
    :goto_3
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setHDRCheckerEnable(Z)V

    iput-boolean v3, p0, Lcom/android/camera/module/Camera2Module;->mHdrCheckEnabled:Z

    :goto_4
    return-void
.end method

.method public updateManualEvAdjust()V
    .locals 7

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0xa7

    if-ne v0, v1, :cond_5

    const-string v0, "pref_qc_camera_exposuretime_key"

    const v1, 0x7f090142

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v0, v2}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "pref_qc_camera_iso_key"

    const v3, 0x7f0900b1

    invoke-virtual {p0, v3}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v4

    invoke-direct {p0, v2, v4}, Lcom/android/camera/module/Camera2Module;->getManualValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    sget-object v4, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "MODE_MANUAL: exposureTime = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, "iso = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/mi/config/b;->kt()Z

    move-result v4

    const/4 v5, 0x1

    if-eqz v4, :cond_0

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    goto :goto_1

    :cond_0
    invoke-virtual {p0, v3}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_2

    invoke-virtual {p0, v1}, Lcom/android/camera/module/Camera2Module;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    goto :goto_1

    :cond_2
    :goto_0
    move v0, v5

    :goto_1
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    if-eqz v1, :cond_3

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mHandler:Landroid/os/Handler;

    new-instance v2, Lcom/android/camera/module/-$$Lambda$Camera2Module$ZDPMd_jihny-51GYG4qN3SWTpE8;

    invoke-direct {v2, p0, v0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$ZDPMd_jihny-51GYG4qN3SWTpE8;-><init>(Lcom/android/camera/module/Camera2Module;Z)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_3
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getFocusMode()I

    move-result v0

    if-ne v5, v0, :cond_5

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->m3ALocked:Z

    if-eqz v0, :cond_5

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    new-instance v1, Lcom/android/camera/module/-$$Lambda$Camera2Module$EDTNXO3ufwPYraDH3BpupgcSD1I;

    invoke-direct {v1, p0}, Lcom/android/camera/module/-$$Lambda$Camera2Module$EDTNXO3ufwPYraDH3BpupgcSD1I;-><init>(Lcom/android/camera/module/Camera2Module;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/Camera;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_4
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->unlockAEAF()V

    :cond_5
    return-void
.end method

.method public updateMoon(Z)V
    .locals 3

    const/4 v0, 0x0

    if-eqz p1, :cond_3

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v1

    invoke-virtual {v1}, Lcom/mi/config/a;->iz()Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setSuperResolution(Z)V

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateFocusMode()V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    const/16 v0, 0x23

    iput v0, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setASDScene(I)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    iget-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mZoomSupported:Z

    if-eqz v0, :cond_1

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setMinZoomRatio(F)V

    const/high16 v0, 0x41a00000    # 20.0f

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v1}, Lcom/android/camera2/CameraCapabilities;->getMaxZoomRatio()F

    move-result v1

    invoke-static {v0, v1}, Ljava/lang/Math;->max(FF)F

    move-result v0

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setMaxZoomRatio(F)V

    sget-object v0, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "updateMoon(): Override zoom ratio range to: ["

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMinZoomRatio()F

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v2, ","

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMaxZoomRatio()F

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v2, "]"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa6

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz v0, :cond_2

    invoke-interface {v0, p1}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->clearFocusView(I)V

    :cond_2
    goto :goto_0

    :cond_3
    iget-boolean p1, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    if-eqz p1, :cond_4

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getFocusMode()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setFocusMode(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/camera/module/Camera2Module;->setFocusMode(Ljava/lang/String;)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->updateHDRPreference()V

    iget-object p1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/16 v0, -0x23

    invoke-virtual {p1, v0}, Lcom/android/camera2/Camera2Proxy;->setASDScene(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->initializeZoomRangeFromCapabilities()V

    sget-object p1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v1, "updateMoon(): Restore zoom ratio range to: ["

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMinZoomRatio()F

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, ","

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMaxZoomRatio()F

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, "]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getZoomRatio()F

    move-result p1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMaxZoomRatio()F

    move-result v0

    cmpl-float p1, p1, v0

    if-lez p1, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->getMaxZoomRatio()F

    move-result p1

    const/4 v0, -0x1

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/module/Camera2Module;->onZoomingActionUpdate(FI)Z

    :cond_4
    :goto_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xb6

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$DualController;

    if-eqz p1, :cond_5

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$DualController;->hideSlideView()V

    :cond_5
    return-void
.end method

.method public updateMoonNight()V
    .locals 7

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->mIsMoonMode:Z

    const/16 v1, 0xa

    invoke-virtual {p0, v1, v0}, Lcom/android/camera/module/Camera2Module;->closeMoonMode(II)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v2

    const/16 v3, 0xa4

    invoke-virtual {v2, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v2

    check-cast v2, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    const-string v3, "e"

    const/4 v4, 0x1

    new-array v5, v4, [I

    const/16 v6, 0xc1

    aput v6, v5, v0

    invoke-interface {v2, v3, v5}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->closeMutexElement(Ljava/lang/String;[I)V

    const-string v0, "0"

    invoke-virtual {p0, v0}, Lcom/android/camera/module/Camera2Module;->setFlashMode(Ljava/lang/String;)V

    invoke-direct {p0, v4}, Lcom/android/camera/module/Camera2Module;->updateMfnr(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->updateOIS()V

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->setAiSceneEffect(I)V

    iput v1, p0, Lcom/android/camera/module/Camera2Module;->mCurrentAiScene:I

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void
.end method

.method public updateOnTripMode()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mAsdScenes:[Lcom/android/camera2/vendortag/struct/MarshalQueryableASDScene$ASDScene;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mAsdScenes:[Lcom/android/camera2/vendortag/struct/MarshalQueryableASDScene$ASDScene;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setOnTripodModeStatus([Lcom/android/camera2/vendortag/struct/MarshalQueryableASDScene$ASDScene;)V

    :cond_0
    return-void
.end method

.method public updatePreviewSurface()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->initEffectCropView()V

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->checkDisplayOrientation()V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    if-nez v0, :cond_1

    return-void

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v0, v0, Lcom/android/camera/CameraSize;->width:I

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mPreviewSize:Lcom/android/camera/CameraSize;

    iget v1, v1, Lcom/android/camera/CameraSize;->height:I

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->updateCameraScreenNailSize(II)V

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/CameraScreenNail;->getSurfaceTexture()Landroid/graphics/SurfaceTexture;

    move-result-object v0

    sget-object v1, Lcom/android/camera/module/Camera2Module;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "updatePreviewSurface: surfaceTexture = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz v0, :cond_3

    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v1}, Lcom/android/camera/Camera;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/CameraScreenNail;->getSurfaceCreatedTimestamp()J

    move-result-wide v1

    iput-wide v1, p0, Lcom/android/camera/module/Camera2Module;->mSurfaceCreatedTimestamp:J

    :cond_3
    iget-object v1, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    new-instance v2, Landroid/view/Surface;

    invoke-direct {v2, v0}, Landroid/view/Surface;-><init>(Landroid/graphics/SurfaceTexture;)V

    invoke-virtual {v1, v2}, Lcom/android/camera2/Camera2Proxy;->updateDeferPreviewSession(Landroid/view/Surface;)Z

    :cond_4
    return-void
.end method

.method public updateSATZooming(Z)V
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lcom/mi/config/a;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/config/a;->ij()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    sget-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-nez v0, :cond_1

    return-void

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-nez v0, :cond_2

    return-void

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, p1}, Lcom/android/camera2/Camera2Proxy;->setSatIsZooming(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void
.end method

.method public updateSilhouette()V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isDoingAction()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/Camera2Module;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/module/Camera2Module;->isSilhouette:Z

    iget v0, p0, Lcom/android/camera/module/Camera2Module;->mModuleIndex:I

    const/16 v1, 0x18

    invoke-direct {p0, v0, v1}, Lcom/android/camera/module/Camera2Module;->trackAISceneChanged(II)V

    invoke-direct {p0, v1}, Lcom/android/camera/module/Camera2Module;->setAiSceneEffect(I)V

    const-string v0, "off"

    invoke-direct {p0, v0}, Lcom/android/camera/module/Camera2Module;->updateHDR(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/module/Camera2Module;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setASDScene(I)V

    invoke-direct {p0}, Lcom/android/camera/module/Camera2Module;->resumePreviewInWorkThread()V

    return-void

    :cond_1
    :goto_0
    return-void
.end method
