.class public Lcom/android/camera/fragment/top/FragmentTopAlert;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentTopAlert.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/fragment/top/FragmentTopAlert$AlertRecommendDescRunable;,
        Lcom/android/camera/fragment/top/FragmentTopAlert$TopAlertRunnable;,
        Lcom/android/camera/fragment/top/FragmentTopAlert$ShortDurationDescriptionTip;,
        Lcom/android/camera/fragment/top/FragmentTopAlert$PermanentToastTip;,
        Lcom/android/camera/fragment/top/FragmentTopAlert$ShortDurationToastTip;
    }
.end annotation


# static fields
.field public static final FRAGMENT_INFO:I = 0xfd

.field public static final HINT_DELAY_TIME_3S:J = 0xbb8L

.field private static final TAG:Ljava/lang/String; = "FragmentTopAlert"

.field public static final TIP_8K_DESC:Ljava/lang/String; = "8k_desc"

.field public static final TIP_AI:Ljava/lang/String; = "ai"

.field public static final TIP_AI_AUDIO:Ljava/lang/String; = "ai_audio"

.field public static final TIP_AI_WATERMARK:Ljava/lang/String; = "ai_watermark"

.field public static final TIP_AUTO_ZOOM:Ljava/lang/String; = "auto_zoom"

.field public static final TIP_COLOR_ENHANCE:Ljava/lang/String; = "color_enhance"

.field public static final TIP_COLOR_MODE:Ljava/lang/String; = "color_mode"

.field public static final TIP_FLASH:Ljava/lang/String; = "flash"

.field public static final TIP_HAND_GESTURE:Ljava/lang/String; = "hand_gesture"

.field public static final TIP_HAND_GESTURE_DESC:Ljava/lang/String; = "hand_gesture_desc"

.field public static final TIP_HDR:Ljava/lang/String; = "hdr"

.field public static final TIP_LIVE_SHOT:Ljava/lang/String; = "live_shot"

.field public static final TIP_MACRO:Ljava/lang/String; = "macro"

.field public static final TIP_METER:Ljava/lang/String; = "meter"

.field public static final TIP_RECOMMEND_ULTRA_WIDE_DESC:Ljava/lang/String; = "recommend_ultra_wide_desc"

.field public static final TIP_SPEECH_SHUTTER_DESC:Ljava/lang/String; = "speech_shutter_desc"

.field public static final TIP_SUBTITLE:Ljava/lang/String; = "subtitle"

.field public static final TIP_SUPER_EIS:Ljava/lang/String; = "super_eis"

.field public static final TIP_SUPER_EIS_PRO:Ljava/lang/String; = "super_eis_pro"

.field public static final TIP_TIMER_BURST:Ljava/lang/String; = "timer_burst"

.field public static final TIP_ULTRA_PIXEL:Ljava/lang/String; = "ultra_pixel"

.field public static final TIP_ULTRA_PIXEL_PORTRAIT:Ljava/lang/String; = "ultra_pixel_portrait"

.field public static final TIP_ULTRA_WIDE_BOKEH:Ljava/lang/String; = "ultra_wide_bokeh"

.field public static final TIP_UNKNOW:Ljava/lang/String; = "unknow"

.field public static final TIP_VIDEO_BEAUTIFY:Ljava/lang/String; = "video_beautify"

.field public static final TIP_VIDEO_BOKEH:Ljava/lang/String; = "video_bokeh"

.field private static sPendingRecordingTimeState:I


# instance fields
.field private mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

.field public final mAiAudioBGTipHideRunnable:Ljava/lang/Runnable;

.field private mAiAudioTip:Landroid/widget/TextView;

.field private mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

.field private mAlertAiDetectTipHitRunable:Ljava/lang/Runnable;

.field private mAlertAiSceneSwitchHintTime:J

.field private mAlertDialog:Landroid/app/AlertDialog;

.field private mAlertImageType:I

.field private mAlertRecommendDescRunable:Ljava/lang/Runnable;

.field private mAlertRecordingText:Landroid/widget/TextView;

.field private mAlertRecordingTextDenominator:Landroid/widget/TextView;

.field private mAlertRecordingTextNumerator:Landroid/widget/TextView;

.field private mAlertTopHintHideRunnable:Ljava/lang/Runnable;

.field private mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

.field private mBlingAnimation:Landroid/view/animation/AlphaAnimation;

.field private mCustomStubTransition:Landroid/animation/LayoutTransition;

.field private mCustomToastTransition:Landroid/animation/LayoutTransition;

.field private mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

.field private mDocumentStateMaps:Ljava/util/LinkedHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/LinkedHashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private mEndGravityTipLayout:Landroid/widget/LinearLayout;

.field private mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

.field private mFastmotionTip:Landroid/widget/LinearLayout;

.field private mFastmotionTipDesc:Landroid/widget/TextView;

.field private mFastmotionTipTitle:Landroid/widget/TextView;

.field public final mFastmotionTipToResetRunnable:Ljava/lang/Runnable;

.field private mHandGestureTip:Lcom/android/camera/customization/BGTintTextView;

.field private mHandler:Landroid/os/Handler;

.field private mHistogramView:Lcom/android/camera/ui/HistogramView;

.field private mIsAlertAnim:Z

.field private mIsParameterDescriptionVisibleBeforeRecording:Z

.field private mIsParameterResetVisibleBeforeRecording:Z

.field private mIsVideoRecording:Z

.field private mIsVideoUltraClearTipVisibleBeforeProVideoSimple:Z

.field private mLiveMusiHintText:Landroid/widget/TextView;

.field private mLiveMusicClose:Landroid/widget/ImageView;

.field private mLiveMusicHintLayout:Landroid/widget/LinearLayout;

.field private mLlAlertRecordingTimeView:Landroid/widget/LinearLayout;

.field private mLyingDirectHintText:Landroid/widget/TextView;

.field private mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

.field public final mMacroModeTipHideRunnable:Ljava/lang/Runnable;

.field private mManualParameterDescriptionTip:Landroid/widget/ImageView;

.field private mManualParameterResetTip:Landroid/widget/ImageView;

.field private mOnClickListener:Landroid/view/View$OnClickListener;

.field private mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

.field private mProColourTip:Lcom/android/camera/customization/BGTintTextView;

.field private mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

.field private mRecommendTip:Landroid/widget/TextView;

.field private mShortDurationDescriptionTip:Ljava/lang/String;

.field private mShortDurationToastTip:Ljava/lang/String;

.field private mShow:Z

.field private mShowAction:Ljava/lang/Runnable;

.field private mShowSlideSwitchLayout:Ljava/lang/Runnable;

.field private mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

.field private mStartGravityTipLayout:Landroid/widget/LinearLayout;

.field private mSubtitleTip:Lcom/android/camera/customization/BGTintTextView;

.field private mSuperNightSeTip:Lcom/android/camera/customization/BGTintTextView;

.field public final mTimerBurstRunnable:Ljava/lang/Runnable;

.field private mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

.field private mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

.field private mToastTipFlash:Landroid/widget/ImageView;

.field private mToastTopTipLayout:Landroid/widget/LinearLayout;

.field private mTopHintTextResource:I

.field private mTopTipLayout:Landroid/widget/LinearLayout;

.field private mVideoTagView:Lcom/android/camera/ui/VideoTagView;

.field private mVideoUltraClearTip:Landroid/widget/TextView;

.field public final mViewHideRunnable:Ljava/lang/Runnable;

.field private mZoomTip:Landroid/widget/TextView;

.field public final mZoomTipToResetRunnable:Ljava/lang/Runnable;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    const-string v0, "unknow"

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    const/4 v0, 0x0

    iput v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopHintTextResource:I

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertImageType:I

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    new-instance v0, Lcom/android/camera/fragment/top/e;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/e;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mOnClickListener:Landroid/view/View$OnClickListener;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$3;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$3;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShowAction:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$4;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$4;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mViewHideRunnable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$5;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$5;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTipToResetRunnable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$6;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$6;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipToResetRunnable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$7;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$7;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTipHideRunnable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$8;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$8;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstRunnable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$9;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$9;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTipHideRunnable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$10;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$10;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertTopHintHideRunnable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$11;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$11;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertAiDetectTipHitRunable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$AlertRecommendDescRunable;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$AlertRecommendDescRunable;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecommendDescRunable:Ljava/lang/Runnable;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$16;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$16;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShowSlideSwitchLayout:Ljava/lang/Runnable;

    return-void
.end method

.method static synthetic Ya()V
    .locals 0

    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtils;->trackManuallyResetDialogCancel()V

    return-void
.end method

.method static synthetic _a()V
    .locals 1

    const/4 v0, 0x0

    invoke-static {v0}, Lcom/android/camera/statistic/CameraStatUtils;->trackLyingDirectShow(I)V

    return-void
.end method

.method static synthetic a(Lcom/android/camera/ui/ToggleSwitch;Z)V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xa4

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-eqz p1, :cond_0

    if-eqz p0, :cond_1

    const/16 p1, 0xf8

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto :goto_0

    :cond_0
    if-eqz p0, :cond_1

    const/16 p1, 0xf9

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    :cond_1
    :goto_0
    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/fragment/top/FragmentTopAlert;)Landroid/widget/LinearLayout;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    return-object p0
.end method

.method static synthetic access$1000(Lcom/android/camera/fragment/top/FragmentTopAlert;)Landroid/widget/LinearLayout;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    return-object p0
.end method

.method static synthetic access$102(Lcom/android/camera/fragment/top/FragmentTopAlert;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    return-object p1
.end method

.method static synthetic access$1100(Lcom/android/camera/fragment/top/FragmentTopAlert;Landroid/view/View;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    return-void
.end method

.method static synthetic access$1200(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/android/camera/customization/BGTintTextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method static synthetic access$1300(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/android/camera/customization/BGTintTextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method static synthetic access$1400(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/android/camera/customization/BGTintTextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method static synthetic access$1500(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/android/camera/customization/BGTintTextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method static synthetic access$1600(Lcom/android/camera/fragment/top/FragmentTopAlert;)Landroid/widget/TextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$1702(Lcom/android/camera/fragment/top/FragmentTopAlert;Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$1800(Lcom/android/camera/fragment/top/FragmentTopAlert;)Landroid/widget/LinearLayout;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    return-object p0
.end method

.method static synthetic access$1900(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/airbnb/lottie/LottieAnimationView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    return-object p0
.end method

.method static synthetic access$2000(Lcom/android/camera/fragment/top/FragmentTopAlert;)Landroid/widget/LinearLayout;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    return-object p0
.end method

.method static synthetic access$2100(Lcom/android/camera/fragment/top/FragmentTopAlert;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    return p0
.end method

.method static synthetic access$2200(Lcom/android/camera/fragment/top/FragmentTopAlert;I)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initEndGravityTipLayout(I)V

    return-void
.end method

.method static synthetic access$2300(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/android/camera/ui/TopAlertSlideSwitchButton;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    return-object p0
.end method

.method static synthetic access$2400(Lcom/android/camera/fragment/top/FragmentTopAlert;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    return p0
.end method

.method static synthetic access$2500()Ljava/lang/String;
    .locals 1

    sget-object v0, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$300(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/android/camera/ui/ToggleSwitch;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    return-object p0
.end method

.method static synthetic access$400(Lcom/android/camera/fragment/top/FragmentTopAlert;Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3, p4}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;I)V

    return-void
.end method

.method static synthetic access$502(Lcom/android/camera/fragment/top/FragmentTopAlert;Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$600(Lcom/android/camera/fragment/top/FragmentTopAlert;)Lcom/android/camera/customization/BGTintTextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method static synthetic access$700(Lcom/android/camera/fragment/top/FragmentTopAlert;Landroid/view/View;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    return-void
.end method

.method static synthetic access$800(Lcom/android/camera/fragment/top/FragmentTopAlert;Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertZoom(Z)V

    return-void
.end method

.method static synthetic access$900(Lcom/android/camera/fragment/top/FragmentTopAlert;Z)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAudioZoomIndicator(Z)V

    return-void
.end method

.method private addTextViewShadowStyle(Landroid/view/View;)V
    .locals 0

    instance-of p0, p1, Lcom/android/camera/customization/BGTintTextView;

    if-nez p0, :cond_0

    instance-of p0, p1, Landroid/widget/TextView;

    if-eqz p0, :cond_0

    check-cast p1, Landroid/widget/TextView;

    const p0, 0x7f11011b

    invoke-virtual {p1, p0}, Landroid/widget/TextView;->setTextAppearance(I)V

    :cond_0
    return-void
.end method

.method private addViewToTipLayout(Landroid/view/View;)V
    .locals 3

    const/4 v0, 0x1

    const/4 v1, 0x0

    const/4 v2, -0x1

    invoke-direct {p0, p1, v0, v1, v2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;I)V

    return-void
.end method

.method private addViewToTipLayout(Landroid/view/View;Ljava/lang/Runnable;Ljava/lang/Runnable;)V
    .locals 6

    const/4 v2, 0x1

    const/4 v3, 0x0

    move-object v0, p0

    move-object v1, p1

    move-object v4, p2

    move-object v5, p3

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;Ljava/lang/Runnable;Ljava/lang/Runnable;)V

    return-void
.end method

.method private addViewToTipLayout(Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;I)V
    .locals 2

    if-eqz p1, :cond_6

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    goto :goto_2

    :cond_0
    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v1, -0x1

    if-eq v0, v1, :cond_1

    return-void

    :cond_1
    if-eqz p2, :cond_2

    iget-boolean p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    if-eqz p2, :cond_2

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    if-eq p2, v0, :cond_3

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    invoke-virtual {p2, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    goto :goto_0

    :cond_2
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x0

    invoke-virtual {p2, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    :cond_3
    :goto_0
    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addTextViewShadowStyle(Landroid/view/View;)V

    if-gez p4, :cond_4

    :try_start_0
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2, p1}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    goto :goto_1

    :cond_4
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2, p1, p4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_1

    :catch_0
    sget-object p2, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    const-string p4, "The specified child already has a parent. You must call removeView() on the child\'s parent first"

    invoke-static {p2, p4}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1
    if-nez p3, :cond_5

    new-instance p3, Landroid/widget/LinearLayout$LayoutParams;

    const/4 p2, -0x2

    invoke-direct {p3, p2, p2}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    :cond_5
    invoke-virtual {p1, p3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->checkChildMargin()V

    :cond_6
    :goto_2
    return-void
.end method

.method private addViewToTipLayout(Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;Ljava/lang/Runnable;Ljava/lang/Runnable;)V
    .locals 2

    if-eqz p1, :cond_7

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    goto :goto_2

    :cond_0
    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v1, -0x1

    if-eq v0, v1, :cond_1

    return-void

    :cond_1
    if-eqz p2, :cond_2

    iget-boolean p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    if-eqz p2, :cond_2

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    if-eq p2, v0, :cond_3

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    invoke-virtual {p2, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    goto :goto_0

    :cond_2
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x0

    invoke-virtual {p2, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    :cond_3
    :goto_0
    if-eqz p4, :cond_4

    invoke-interface {p4}, Ljava/lang/Runnable;->run()V

    :cond_4
    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addTextViewShadowStyle(Landroid/view/View;)V

    :try_start_0
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2, p1}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V
    :try_end_0
    .catch Ljava/lang/IllegalStateException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_1

    :catch_0
    sget-object p2, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    const-string p4, "The specified child already has a parent. You must call removeView() on the child\'s parent first"

    invoke-static {p2, p4}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1
    if-nez p3, :cond_5

    new-instance p3, Landroid/widget/LinearLayout$LayoutParams;

    const/4 p2, -0x2

    invoke-direct {p3, p2, p2}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    :cond_5
    invoke-virtual {p1, p3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    if-eqz p5, :cond_6

    invoke-interface {p5}, Ljava/lang/Runnable;->run()V

    :cond_6
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->checkChildMargin()V

    :cond_7
    :goto_2
    return-void
.end method

.method private addViewToToastLayout(Landroid/view/View;)V
    .locals 2

    const/4 v0, 0x1

    const/4 v1, -0x1

    invoke-direct {p0, p1, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;ZI)V

    return-void
.end method

.method private addViewToToastLayout(Landroid/view/View;Z)V
    .locals 1

    const/4 v0, -0x1

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;ZI)V

    return-void
.end method

.method private addViewToToastLayout(Landroid/view/View;ZI)V
    .locals 2

    if-eqz p1, :cond_8

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    goto/16 :goto_2

    :cond_0
    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v1, -0x1

    if-eq v0, v1, :cond_1

    return-void

    :cond_1
    const/4 v0, 0x0

    if-eqz p2, :cond_4

    iget-boolean p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    if-eqz p2, :cond_4

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object p2

    if-eqz p2, :cond_2

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customToastLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v1

    if-eq p2, v1, :cond_3

    :cond_2
    const/4 p2, 0x1

    invoke-direct {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setToastTipLayoutParams(Z)V

    :cond_3
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v1

    if-eq p2, v1, :cond_5

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v1

    invoke-virtual {p2, v1}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    goto :goto_0

    :cond_4
    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setToastTipLayoutParams(Z)V

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    invoke-virtual {p2, v1}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    :cond_5
    :goto_0
    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addTextViewShadowStyle(Landroid/view/View;)V

    if-gez p3, :cond_6

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2, p1}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    goto :goto_1

    :cond_6
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2, p1, p3}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;I)V

    :goto_1
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p2

    check-cast p2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 p3, -0x2

    iput p3, p2, Landroid/widget/LinearLayout$LayoutParams;->width:I

    iput p3, p2, Landroid/widget/LinearLayout$LayoutParams;->height:I

    invoke-virtual {p1, p2}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result p1

    if-lez p1, :cond_7

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_7
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->checkChildMargin()V

    :cond_8
    :goto_2
    return-void
.end method

.method private alertAiSceneSelector(Ljava/lang/String;Ljava/lang/String;ILcom/android/camera/ui/ToggleSwitch$OnCheckedChangeListener;Z)V
    .locals 4

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-virtual {v0, p1, p2}, Lcom/android/camera/ui/ToggleSwitch;->setTextOnAndOff(Ljava/lang/String;Ljava/lang/String;)V

    :cond_0
    if-nez p3, :cond_2

    const-wide/16 p1, 0xbb8

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-wide v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertAiSceneSwitchHintTime:J

    sub-long/2addr v0, v2

    sub-long/2addr p1, v0

    invoke-static {}, Lcom/android/camera/CameraSettings;->getShaderEffect()I

    move-result p3

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-ne p3, v0, :cond_3

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShowAction:Ljava/lang/Runnable;

    const-wide/16 v1, 0x0

    cmp-long v3, p1, v1

    if-gez v3, :cond_1

    move-wide p1, v1

    :cond_1
    invoke-virtual {p3, v0, p1, p2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShowAction:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->removeCallbacks(Ljava/lang/Runnable;)Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :cond_3
    :goto_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-virtual {p1, p4}, Lcom/android/camera/ui/ToggleSwitch;->setOnCheckedChangeListener(Lcom/android/camera/ui/ToggleSwitch$OnCheckedChangeListener;)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-virtual {p0, p5}, Lcom/android/camera/ui/ToggleSwitch;->setChecked(Z)V

    return-void
.end method

.method private alertAudioZoomIndicator(Z)V
    .locals 5

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->cj()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/Util;->isWiredHeadsetOn()Z

    move-result p1

    if-eqz p1, :cond_2

    return-void

    :cond_2
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ActivityBase;

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p1

    check-cast p1, Lcom/android/camera/module/BaseModule;

    if-eqz p1, :cond_a

    invoke-virtual {p1}, Lcom/android/camera/module/BaseModule;->isAlive()Z

    move-result v0

    if-nez v0, :cond_3

    goto :goto_1

    :cond_3
    const/4 v0, 0x0

    instance-of v1, p1, Lcom/android/camera/module/VideoModule;

    if-eqz v1, :cond_4

    move-object v0, p1

    check-cast v0, Lcom/android/camera/module/VideoModule;

    :cond_4
    if-nez v0, :cond_5

    return-void

    :cond_5
    invoke-virtual {v0}, Lcom/android/camera/module/VideoModule;->isNeedAlertAudioZoomIndicator()Z

    move-result p1

    if-nez p1, :cond_6

    return-void

    :cond_6
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    if-nez p1, :cond_7

    new-instance p1, Lcom/android/camera/ui/AudioZoomIndicator;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {p1, v1}, Lcom/android/camera/ui/AudioZoomIndicator;-><init>(Landroid/content/Context;)V

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    :cond_7
    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getMinZoomRatio()F

    move-result p1

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getMaxZoomRatio()F

    move-result v1

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getZoomRatio()F

    move-result v0

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v2

    const/4 v3, -0x1

    const/4 v4, 0x1

    if-eq v2, v3, :cond_8

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    invoke-virtual {v2}, Landroid/view/View;->getVisibility()I

    move-result v2

    if-nez v2, :cond_8

    move v2, v4

    goto :goto_0

    :cond_8
    const/4 v2, 0x0

    :goto_0
    if-eqz v2, :cond_9

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    invoke-virtual {p0, p1, v1, v0}, Lcom/android/camera/ui/AudioZoomIndicator;->update(FFF)V

    goto :goto_1

    :cond_9
    new-instance p1, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v0, -0x2

    invoke-direct {p1, v0, v0}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    iput v4, p1, Landroid/widget/LinearLayout$LayoutParams;->gravity:I

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAudioZoomIndicator:Lcom/android/camera/ui/AudioZoomIndicator;

    invoke-direct {p0, v0, v4, p1, v3}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;I)V

    :cond_a
    :goto_1
    return-void
.end method

.method private alertTintColor()I
    .locals 1

    invoke-static {}, Lcom/android/camera/customization/TintColor;->isYellowTintColor()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/customization/TintColor;->tintColor()I

    move-result p0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v0, 0x7f060170

    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->getColor(I)I

    move-result p0

    :goto_0
    return p0
.end method

.method private alertZoom(Z)V
    .locals 4

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v0

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa6

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v2

    const/16 v3, 0xb6

    invoke-virtual {v2, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v2

    check-cast v2, Lcom/android/camera/protocol/ModeProtocol$DualController;

    if-eqz v2, :cond_7

    if-eqz v2, :cond_0

    invoke-interface {v2}, Lcom/android/camera/protocol/ModeProtocol$DualController;->isButtonVisible()Z

    move-result v2

    if-nez v2, :cond_7

    :cond_0
    if-eqz v1, :cond_1

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$EffectCropViewController;->isZoomAdjustVisible()Z

    move-result v1

    if-eqz v1, :cond_1

    goto/16 :goto_2

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa1

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    const/16 v2, 0xa9

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v3

    if-ne v2, v3, :cond_2

    if-eqz v1, :cond_2

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isRecording()Z

    move-result v1

    if-nez v1, :cond_2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v1

    invoke-virtual {v1}, Lb/c/a/b;->uj()Z

    move-result v1

    if-eqz v1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    return-void

    :cond_2
    const/16 v1, 0xcc

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-ne v1, v2, :cond_3

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    return-void

    :cond_3
    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getZoomRatioTipText(Z)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_6

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_6

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    const/high16 v0, 0x422c0000    # 43.0f

    if-eqz p1, :cond_4

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    const/4 v1, 0x2

    new-array v1, v1, [F

    const/4 v2, 0x0

    const/high16 v3, 0x428c0000    # 70.0f

    invoke-static {v3}, Lcom/android/camera/Util;->pixelTodp(F)F

    move-result v3

    aput v3, v1, v2

    const/4 v2, 0x1

    invoke-static {v0}, Lcom/android/camera/Util;->pixelTodp(F)F

    move-result v0

    aput v0, v1, v2

    const-string v0, "textSize"

    invoke-static {p1, v0, v1}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    move-result-object p1

    const-wide/16 v0, 0x12c

    invoke-virtual {p1, v0, v1}, Landroid/animation/ObjectAnimator;->setDuration(J)Landroid/animation/ObjectAnimator;

    move-result-object p1

    invoke-virtual {p1}, Landroid/animation/ObjectAnimator;->start()V

    goto :goto_0

    :cond_4
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-static {v0}, Lcom/android/camera/Util;->pixelTodp(F)F

    move-result v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextSize(F)V

    :goto_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result p1

    const/4 v0, -0x1

    if-eq p1, v0, :cond_5

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-virtual {p1}, Landroid/widget/TextView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_5

    return-void

    :cond_5
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;)V

    goto :goto_1

    :cond_6
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :goto_1
    return-void

    :cond_7
    :goto_2
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    return-void
.end method

.method static synthetic b(Lcom/android/camera/ui/ToggleSwitch;Z)V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xa4

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-eqz p1, :cond_0

    if-eqz p0, :cond_1

    const/16 p1, 0xf6

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto :goto_0

    :cond_0
    if-eqz p0, :cond_1

    const/16 p1, 0xf7

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    :cond_1
    :goto_0
    return-void
.end method

.method private checkChildMargin()V
    .locals 7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->qj()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->rj()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_1

    goto :goto_2

    :cond_1
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f07048a

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f070488

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v1

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v2}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result v2

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-lez v2, :cond_2

    move v2, v3

    goto :goto_0

    :cond_2
    move v2, v4

    :goto_0
    iget-object v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object v6, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v5

    const/4 v6, -0x1

    if-eq v5, v6, :cond_3

    goto :goto_1

    :cond_3
    move v3, v4

    :goto_1
    iget-object v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v5}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v5

    check-cast v5, Landroid/view/ViewGroup$MarginLayoutParams;

    iget-object v6, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    invoke-virtual {v6}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v6

    check-cast v6, Landroid/view/ViewGroup$MarginLayoutParams;

    if-eqz v3, :cond_5

    if-eqz v2, :cond_4

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertTopMargin()I

    move-result p0

    iput p0, v5, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    sub-int/2addr v1, v0

    iput v1, v6, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    goto :goto_2

    :cond_4
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertTopMarginSupportDocument()I

    move-result p0

    iput p0, v5, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    iput v4, v6, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    goto :goto_2

    :cond_5
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertTopMargin()I

    move-result p0

    iput p0, v5, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    :cond_6
    :goto_2
    return-void
.end method

.method private clearHandlerCallbacks()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecommendDescRunable:Ljava/lang/Runnable;

    invoke-virtual {v0, p0}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    return-void
.end method

.method private customStubViewTransition()Landroid/animation/LayoutTransition;
    .locals 8

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    if-nez v0, :cond_0

    new-instance v0, Landroid/animation/LayoutTransition;

    invoke-direct {v0}, Landroid/animation/LayoutTransition;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    const/4 v0, 0x2

    new-array v1, v0, [F

    fill-array-data v1, :array_0

    const-string v2, "alpha"

    const/4 v3, 0x0

    invoke-static {v3, v2, v1}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    move-result-object v1

    new-instance v4, Lmiui/view/animation/SineEaseOutInterpolator;

    invoke-direct {v4}, Lmiui/view/animation/SineEaseOutInterpolator;-><init>()V

    invoke-virtual {v1, v4}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    new-array v4, v0, [F

    fill-array-data v4, :array_1

    invoke-static {v3, v2, v4}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    move-result-object v2

    new-instance v3, Lmiui/view/animation/SineEaseOutInterpolator;

    invoke-direct {v3}, Lmiui/view/animation/SineEaseOutInterpolator;-><init>()V

    invoke-virtual {v2, v3}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    const-wide/16 v4, 0x0

    invoke-virtual {v3, v0, v4, v5}, Landroid/animation/LayoutTransition;->setStartDelay(IJ)V

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    const-wide/16 v6, 0x12c

    invoke-virtual {v3, v0, v6, v7}, Landroid/animation/LayoutTransition;->setDuration(IJ)V

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    invoke-virtual {v3, v0, v1}, Landroid/animation/LayoutTransition;->setAnimator(ILandroid/animation/Animator;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    const/4 v1, 0x3

    invoke-virtual {v0, v1, v4, v5}, Landroid/animation/LayoutTransition;->setStartDelay(IJ)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    const-wide/16 v3, 0xc8

    invoke-virtual {v0, v1, v3, v4}, Landroid/animation/LayoutTransition;->setDuration(IJ)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    invoke-virtual {v0, v1, v2}, Landroid/animation/LayoutTransition;->setAnimator(ILandroid/animation/Animator;)V

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomStubTransition:Landroid/animation/LayoutTransition;

    return-object p0

    nop

    :array_0
    .array-data 4
        0x0
        0x3f800000    # 1.0f
    .end array-data

    :array_1
    .array-data 4
        0x3f800000    # 1.0f
        0x0
    .end array-data
.end method

.method private customToastLayoutTransition()Landroid/animation/LayoutTransition;
    .locals 8

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    if-nez v0, :cond_0

    const/4 v0, 0x2

    new-array v1, v0, [F

    fill-array-data v1, :array_0

    const-string v2, "alpha"

    const/4 v3, 0x0

    invoke-static {v3, v2, v1}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    move-result-object v1

    new-instance v4, Lmiui/view/animation/SineEaseOutInterpolator;

    invoke-direct {v4}, Lmiui/view/animation/SineEaseOutInterpolator;-><init>()V

    invoke-virtual {v1, v4}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    new-array v4, v0, [F

    fill-array-data v4, :array_1

    invoke-static {v3, v2, v4}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    move-result-object v2

    new-instance v3, Lmiui/view/animation/SineEaseOutInterpolator;

    invoke-direct {v3}, Lmiui/view/animation/SineEaseOutInterpolator;-><init>()V

    invoke-virtual {v2, v3}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    new-instance v3, Lcom/android/camera/fragment/top/FragmentTopAlert$1;

    invoke-direct {v3, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$1;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    invoke-virtual {v2, v3}, Landroid/animation/ObjectAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    new-instance v3, Landroid/animation/LayoutTransition;

    invoke-direct {v3}, Landroid/animation/LayoutTransition;-><init>()V

    iput-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    const-wide/16 v4, 0x0

    invoke-virtual {v3, v0, v4, v5}, Landroid/animation/LayoutTransition;->setStartDelay(IJ)V

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    const-wide/16 v6, 0x12c

    invoke-virtual {v3, v0, v6, v7}, Landroid/animation/LayoutTransition;->setDuration(IJ)V

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    invoke-virtual {v3, v0, v1}, Landroid/animation/LayoutTransition;->setAnimator(ILandroid/animation/Animator;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    const/4 v1, 0x3

    invoke-virtual {v0, v1, v4, v5}, Landroid/animation/LayoutTransition;->setStartDelay(IJ)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    const-wide/16 v3, 0xc8

    invoke-virtual {v0, v1, v3, v4}, Landroid/animation/LayoutTransition;->setDuration(IJ)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    invoke-virtual {v0, v1, v2}, Landroid/animation/LayoutTransition;->setAnimator(ILandroid/animation/Animator;)V

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mCustomToastTransition:Landroid/animation/LayoutTransition;

    return-object p0

    nop

    :array_0
    .array-data 4
        0x0
        0x3f800000    # 1.0f
    .end array-data

    :array_1
    .array-data 4
        0x3f800000    # 1.0f
        0x0
    .end array-data
.end method

.method private getAlertStartGravityTipLayoutTopMargin()I
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDisplayMode()I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    invoke-static {v0}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v0

    iget v0, v0, Landroid/graphics/Rect;->top:I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v1, 0x7f070278

    invoke-virtual {p0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p0

    add-int/2addr v0, p0

    return v0
.end method

.method private getAlertTopMargin()I
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDisplayMode()I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    invoke-static {v0}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v0

    iget v0, v0, Landroid/graphics/Rect;->top:I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v1, 0x7f0704a8

    invoke-virtual {p0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p0

    add-int/2addr v0, p0

    return v0
.end method

.method private getAlertTopMarginSupportDocument()I
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDisplayMode()I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    invoke-static {v0}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v0

    iget v0, v0, Landroid/graphics/Rect;->top:I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v1, 0x7f07048a

    invoke-virtual {p0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p0

    add-int/2addr v0, p0

    return v0
.end method

.method private getDividerDrawable()Landroid/graphics/drawable/Drawable;
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->qj()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->rj()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v0, 0x7f08051b

    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p0

    return-object p0

    :cond_1
    :goto_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v0, 0x7f08051c

    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p0

    return-object p0
.end method

.method private getEndGravityTipDividerDrawable()Landroid/graphics/drawable/Drawable;
    .locals 1

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v0, 0x7f080519

    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p0

    return-object p0
.end method

.method private getZoomRatioTipText(Z)Ljava/lang/String;
    .locals 6

    invoke-static {}, Lcom/android/camera/CameraSettings;->readZoom()F

    move-result v0

    invoke-static {v0}, Lcom/android/camera/HybridZoomingSystem;->toDecimal(F)F

    move-result v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v1

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v2

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v2, v1, v3}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getActualOpenCameraId(II)I

    move-result v1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v2

    invoke-virtual {v2}, Lb/c/a/b;->jj()Z

    move-result v2

    const/4 v3, 0x0

    if-eqz v2, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result v2

    if-eqz v2, :cond_0

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isSupportCropFrontMode()Z

    move-result v2

    if-eqz v2, :cond_0

    return-object v3

    :cond_0
    const/high16 v2, 0x3f800000    # 1.0f

    cmpl-float v4, v0, v2

    if-nez v4, :cond_11

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result v4

    if-ne v1, v4, :cond_1

    return-object v3

    :cond_1
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getSATCameraId()I

    move-result v4

    if-ne v1, v4, :cond_2

    return-object v3

    :cond_2
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getBokehCameraId()I

    move-result v4

    if-ne v1, v4, :cond_3

    return-object v3

    :cond_3
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideBokehCameraId()I

    move-result v4

    if-ne v1, v4, :cond_4

    return-object v3

    :cond_4
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getFrontCameraId()I

    move-result v4

    if-ne v1, v4, :cond_5

    return-object v3

    :cond_5
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getBokehFrontCameraId()I

    move-result v4

    if-ne v1, v4, :cond_6

    return-object v3

    :cond_6
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getStandaloneMacroCameraId()I

    move-result v4

    if-ne v1, v4, :cond_7

    return-object v3

    :cond_7
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v4

    invoke-virtual {v4}, Lb/c/a/b;->qm()Z

    move-result v4

    if-eqz v4, :cond_8

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getVideoSATCameraId()I

    move-result v4

    if-ne v1, v4, :cond_8

    return-object v3

    :cond_8
    sget-boolean v4, Lcom/android/camera/HybridZoomingSystem;->IS_2_SAT:Z

    if-nez v4, :cond_9

    sget-boolean v4, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-nez v4, :cond_a

    invoke-static {}, Lcom/android/camera/CameraSettings;->isSupportedOpticalZoom()Z

    move-result v4

    if-nez v4, :cond_a

    :cond_9
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v4

    if-ne v1, v4, :cond_a

    return-object v3

    :cond_a
    iget v4, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v5, 0xa7

    if-eq v4, v5, :cond_10

    const/16 v5, 0xb4

    if-ne v4, v5, :cond_b

    goto :goto_0

    :cond_b
    const/16 v5, 0xa6

    if-ne v4, v5, :cond_c

    return-object v3

    :cond_c
    const/16 v5, 0xaf

    if-ne v4, v5, :cond_d

    return-object v3

    :cond_d
    const/16 v5, 0xb3

    if-ne v4, v5, :cond_e

    return-object v3

    :cond_e
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v4

    invoke-virtual {v4}, Lb/c/a/b;->gk()Z

    move-result v4

    if-eqz v4, :cond_f

    iget v4, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v5, 0xac

    if-ne v4, v5, :cond_f

    return-object v3

    :cond_f
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v4

    if-ne v1, v4, :cond_11

    if-eqz p1, :cond_11

    sget p1, Lcom/android/camera/HybridZoomingSystem;->sDefaultMacroOpticalZoomRatio:F

    cmpl-float p1, p1, v2

    if-nez p1, :cond_11

    :cond_10
    :goto_0
    return-object v3

    :cond_11
    sget p1, Lcom/android/camera/HybridZoomingSystem;->FLOAT_ZOOM_RATIO_ULTR:F

    cmpl-float p1, v0, p1

    if-nez p1, :cond_13

    sget-boolean p1, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-eqz p1, :cond_12

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result p1

    if-ne v1, p1, :cond_12

    return-object v3

    :cond_12
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result p1

    if-ne v1, p1, :cond_13

    return-object v3

    :cond_13
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxCameraId()I

    move-result p1

    if-ne v1, p1, :cond_14

    invoke-static {}, Lcom/android/camera/HybridZoomingSystem;->getTeleMinZoomRatio()F

    move-result p1

    cmpg-float p1, v0, p1

    if-gtz p1, :cond_14

    return-object v3

    :cond_14
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraTeleCameraId()I

    move-result p1

    if-ne v1, p1, :cond_15

    invoke-static {}, Lcom/android/camera/HybridZoomingSystem;->getUltraTeleMinZoomRatio()F

    move-result p1

    cmpg-float p1, v0, p1

    if-gtz p1, :cond_15

    return-object v3

    :cond_15
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAIWatermark()Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;

    move-result-object p1

    iget p0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {p1, p0}, Lcom/android/camera/data/data/runing/ComponentRunningAIWatermark;->getAIWatermarkEnable(I)Z

    move-result p0

    if-eqz p0, :cond_16

    return-object v3

    :cond_16
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string p1, "X"

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private handleProVideoRecordingSimple(Landroid/view/View;)V
    .locals 5

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    invoke-virtual {p1}, Lcom/airbnb/lottie/LottieAnimationView;->cancelAnimation()V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    const v0, 0x3ebd70a4    # 0.37f

    invoke-virtual {p1, v0}, Lcom/airbnb/lottie/LottieAnimationView;->setScale(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    const v0, 0x7f0f0020

    invoke-virtual {p1, v0}, Lcom/airbnb/lottie/LottieAnimationView;->k(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-virtual {p1, v0}, Lcom/airbnb/lottie/LottieAnimationView;->setProgress(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    invoke-virtual {p1}, Lcom/airbnb/lottie/LottieAnimationView;->T()V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopAlert$14;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$14;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    invoke-virtual {p1, v0}, Lcom/airbnb/lottie/LottieAnimationView;->a(Landroid/animation/Animator$AnimatorListener;)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getProVideoRecordingSimpleRunning()Z

    move-result p1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    xor-int/lit8 v1, p1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->setProVideoRecordingSimpleRunning(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xb5

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa6

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v2

    const/16 v3, 0xa2

    invoke-virtual {v2, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v2

    check-cast v2, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    const/4 v3, 0x0

    const/4 v4, 0x1

    if-nez p1, :cond_4

    if-eqz v0, :cond_0

    invoke-interface {v0, v3}, Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;->forceUpdateManualView(Z)V

    :cond_0
    if-eqz v1, :cond_1

    invoke-interface {v1, v3}, Lcom/android/camera/protocol/ModeProtocol$EffectCropViewController;->setZoomViewVisible(Z)V

    :cond_1
    if-eqz v2, :cond_2

    invoke-interface {v2, v3}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->updatePauseAndCaptureView(Z)V

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {p1}, Lcom/android/camera/CameraSettings;->isProVideoHistogramOpen(I)Z

    move-result p1

    if-eqz p1, :cond_3

    new-instance p1, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-direct {p1, v0}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    invoke-virtual {p1}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_3
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-virtual {p1}, Landroid/widget/TextView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_9

    iput-boolean v4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoUltraClearTipVisibleBeforeProVideoSimple:Z

    new-instance p1, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-direct {p1, p0}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-virtual {p1}, Lcom/android/camera/animation/type/BaseOnSubScribe;->targetGone()Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    invoke-static {p0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    goto :goto_0

    :cond_4
    if-eqz v0, :cond_5

    invoke-interface {v0, v4}, Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;->forceUpdateManualView(Z)V

    :cond_5
    if-eqz v1, :cond_6

    invoke-interface {v1, v4}, Lcom/android/camera/protocol/ModeProtocol$EffectCropViewController;->setZoomViewVisible(Z)V

    :cond_6
    if-eqz v2, :cond_7

    invoke-interface {v2, v4}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->updatePauseAndCaptureView(Z)V

    :cond_7
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {p1}, Lcom/android/camera/CameraSettings;->isProVideoHistogramOpen(I)Z

    move-result p1

    if-eqz p1, :cond_8

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-direct {p1, v0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    invoke-virtual {p1}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_8
    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoUltraClearTipVisibleBeforeProVideoSimple:Z

    if-eqz p1, :cond_9

    iput-boolean v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoUltraClearTipVisibleBeforeProVideoSimple:Z

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-direct {p1, p0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_9
    :goto_0
    return-void
.end method

.method private initAiAudioBGTip()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c0020

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initDocumentStateButton()V
    .locals 3

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const v1, 0x7f0c002c

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/MutiStateButton;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    new-instance v0, Ljava/util/LinkedHashMap;

    invoke-direct {v0}, Ljava/util/LinkedHashMap;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateMaps:Ljava/util/LinkedHashMap;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateMaps:Ljava/util/LinkedHashMap;

    const v1, 0x7f100190

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "raw"

    invoke-virtual {v0, v2, v1}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateMaps:Ljava/util/LinkedHashMap;

    const v1, 0x7f10018c

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "bin"

    invoke-virtual {v0, v2, v1}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateMaps:Ljava/util/LinkedHashMap;

    const v1, 0x7f100191

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "color"

    invoke-virtual {v0, v2, v1}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateMaps:Ljava/util/LinkedHashMap;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/ui/MutiStateButton;->initItems(Ljava/util/LinkedHashMap;Landroid/view/View$OnClickListener;)V

    return-void
.end method

.method private initEndGravityTipLayout(I)V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    int-to-float p1, p1

    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->setRotation(F)V

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p1, v0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    const/16 v0, 0x12c

    invoke-virtual {p1, v0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p1

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    invoke-virtual {p1}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->getWidth()I

    move-result p1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getHeight()I

    move-result v0

    sub-int/2addr p1, v0

    div-int/lit8 p1, p1, 0x2

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isLandScape()Z

    move-result v0

    const v1, 0x7f0704a7

    const v2, 0x800005

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isLeftLandScape()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    const v2, 0x800003

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setGravity(I)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setGravity(I)V

    :goto_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/content/res/Resources;->getDimensionPixelOffset(I)I

    move-result v1

    sub-int/2addr v1, p1

    invoke-direct {p0, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setViewEndMargin(Landroid/view/View;I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertTopMargin()I

    move-result v1

    add-int/2addr v1, p1

    invoke-direct {p0, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setViewTopMargin(Landroid/view/View;I)V

    goto :goto_1

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v2}, Landroid/widget/LinearLayout;->setGravity(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setViewEndMargin(Landroid/view/View;I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertTopMargin()I

    move-result v0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setViewTopMargin(Landroid/view/View;I)V

    :goto_1
    return-void
.end method

.method private initFastmotionTip()Landroid/widget/LinearLayout;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c0034

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Landroid/widget/LinearLayout;

    return-object p0
.end method

.method private initHandGestureTip()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c009d

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initHandler()V
    .locals 1

    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    return-void
.end method

.method private initHorizonDirectTipText()Landroid/widget/TextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c0101

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Landroid/widget/TextView;

    return-object p0
.end method

.method private initLandscapeTopTipLayout()V
    .locals 8

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout$LayoutParams;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigRatio()Lcom/android/camera/data/data/config/ComponentConfigRatio;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v1, v2}, Lcom/android/camera/data/data/config/ComponentConfigRatio;->getComponentValue(I)Ljava/lang/String;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v2}, Lcom/android/camera/CameraSettings;->isSubtitleEnabled(I)Z

    move-result v2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v3

    const/16 v4, 0xa1

    invoke-virtual {v3, v4}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v3

    check-cast v3, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    const/4 v4, 0x1

    const/4 v5, 0x0

    if-eqz v3, :cond_1

    if-eqz v2, :cond_1

    invoke-interface {v3}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isRecording()Z

    move-result v2

    if-eqz v2, :cond_1

    move v2, v4

    goto :goto_0

    :cond_1
    move v2, v5

    :goto_0
    if-nez v2, :cond_3

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v3, 0xb3

    if-eq v2, v3, :cond_3

    const/16 v3, 0xcc

    if-ne v2, v3, :cond_2

    goto :goto_1

    :cond_2
    move v2, v5

    goto :goto_2

    :cond_3
    :goto_1
    move v2, v4

    :goto_2
    invoke-static {v2}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v3

    iget v3, v3, Landroid/graphics/Rect;->top:I

    invoke-static {v2}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v2

    invoke-virtual {v2}, Landroid/graphics/Rect;->height()I

    move-result v2

    const-string v6, "1x1"

    if-ne v1, v6, :cond_4

    invoke-static {}, Lcom/android/camera/Util;->getTopCoverHeight()I

    move-result v3

    invoke-static {}, Lcom/android/camera/Util;->getSquareBottomCoverHeight()I

    move-result v1

    sub-int/2addr v2, v1

    invoke-static {}, Lcom/android/camera/Util;->getTopCoverHeight()I

    move-result v1

    invoke-static {}, Lcom/android/camera/Util;->getTopHeight()I

    move-result v6

    sub-int/2addr v1, v6

    sub-int/2addr v2, v1

    :cond_4
    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    sub-int/2addr v2, v1

    div-int/lit8 v2, v2, 0x2

    add-int/2addr v2, v3

    iput v2, v0, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v1}, Lcom/android/camera/CameraSettings;->isCinematicAspectRatioEnabled(I)Z

    move-result v1

    if-eqz v1, :cond_5

    invoke-static {v4}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v1

    invoke-virtual {v1}, Landroid/graphics/Rect;->width()I

    move-result v1

    invoke-static {v4}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v2

    invoke-virtual {v2}, Landroid/graphics/Rect;->height()I

    move-result v2

    int-to-double v3, v1

    int-to-double v1, v2

    invoke-static {}, Lcom/android/camera/Util;->getCinematicAspectRatio()D

    move-result-wide v6

    div-double/2addr v1, v6

    sub-double/2addr v3, v1

    const-wide/high16 v1, 0x4000000000000000L    # 2.0

    div-double/2addr v3, v1

    double-to-int v1, v3

    goto :goto_3

    :cond_5
    move v1, v5

    :goto_3
    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isLeftLandScape()Z

    move-result v2

    const v3, 0x7f0704a8

    if-eqz v2, :cond_6

    iput v5, v0, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    add-int/2addr v2, v1

    iput v2, v0, Landroid/widget/FrameLayout$LayoutParams;->rightMargin:I

    goto :goto_4

    :cond_6
    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isRightLandScape()Z

    move-result v2

    if-eqz v2, :cond_7

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    add-int/2addr v2, v1

    iput v2, v0, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    iput v5, v0, Landroid/widget/FrameLayout$LayoutParams;->rightMargin:I

    :cond_7
    :goto_4
    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    iput v1, v0, Landroid/widget/FrameLayout$LayoutParams;->height:I

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v1, v0}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget p0, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    int-to-float p0, p0

    invoke-virtual {v0, p0}, Landroid/widget/LinearLayout;->setRotation(F)V

    return-void
.end method

.method private initMacroModeTip()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00a9

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initMusicTipText()Landroid/widget/LinearLayout;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c0102

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Landroid/widget/LinearLayout;

    return-object p0
.end method

.method private initPermanentTip()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00de

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initPortraitTopTipLayout()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertTopMargin()I

    move-result v1

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    const/4 v1, 0x0

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->leftMargin:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->rightMargin:I

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    mul-int/lit8 v1, v1, 0x4

    int-to-float v1, v1

    const/high16 v2, 0x40400000    # 3.0f

    div-float/2addr v1, v2

    float-to-int v1, v1

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v1, v0}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getDividerDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setDividerDrawable(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setShowDividers(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setRotation(F)V

    return-void
.end method

.method private initProColourTip()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00f0

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initRecommendTipText()Landroid/widget/TextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00f1

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Landroid/widget/TextView;

    return-object p0
.end method

.method private initSubtitleTip()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00f9

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initTimerBurstTip()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00fc

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initToastTipLayout()V
    .locals 2

    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setToastTipLayoutParams(Z)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initToastTopTipImage()Landroid/widget/ImageView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initToastTopTipText()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initToastTopTipText()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSuperNightSeTip:Lcom/android/camera/customization/BGTintTextView;

    return-void
.end method

.method private initToastTopTipImage()Landroid/widget/ImageView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00fe

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Landroid/widget/ImageView;

    return-object p0
.end method

.method private initToastTopTipText()Lcom/android/camera/customization/BGTintTextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c00ff

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/customization/BGTintTextView;

    return-object p0
.end method

.method private initTopSlideSwitchButton()Lcom/android/camera/ui/TopAlertSlideSwitchButton;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c0103

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    return-object p0
.end method

.method private initTopTipToggleSwitch()Lcom/android/camera/ui/ToggleSwitch;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c0104

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Lcom/android/camera/ui/ToggleSwitch;

    return-object p0
.end method

.method private initZoomTipText()Landroid/widget/TextView;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p0

    const v0, 0x7f0c0113

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    check-cast p0, Landroid/widget/TextView;

    return-object p0
.end method

.method private removeViewToTipLayout(Landroid/view/View;)V
    .locals 1

    const/4 v0, 0x1

    invoke-direct {p0, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;Z)V

    return-void
.end method

.method private removeViewToTipLayout(Landroid/view/View;Z)V
    .locals 2

    if-eqz p1, :cond_5

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    goto :goto_1

    :cond_0
    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v1, -0x1

    if-ne v0, v1, :cond_1

    return-void

    :cond_1
    if-eqz p2, :cond_2

    iget-boolean p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    if-eqz p2, :cond_2

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object p2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    if-eq p2, v0, :cond_3

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    invoke-virtual {p2, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    goto :goto_0

    :cond_2
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x0

    invoke-virtual {p2, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    :cond_3
    :goto_0
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p2, p1}, Landroid/widget/LinearLayout;->removeView(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result p1

    if-gtz p1, :cond_4

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->removeAllViews()V

    :cond_4
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->checkChildMargin()V

    :cond_5
    :goto_1
    return-void
.end method

.method private removeViewToToastLayout(Landroid/view/View;)V
    .locals 1

    const/4 v0, 0x1

    invoke-direct {p0, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;Z)V

    return-void
.end method

.method private removeViewToToastLayout(Landroid/view/View;Z)V
    .locals 2

    if-eqz p1, :cond_8

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    goto :goto_1

    :cond_0
    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v1, -0x1

    if-ne v0, v1, :cond_1

    return-void

    :cond_1
    if-eqz p2, :cond_4

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customToastLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v1

    if-eq v0, v1, :cond_3

    :cond_2
    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setToastTipLayoutParams(Z)V

    :cond_3
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v1

    if-eq v0, v1, :cond_5

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customStubViewTransition()Landroid/animation/LayoutTransition;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    goto :goto_0

    :cond_4
    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setToastTipLayoutParams(Z)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    :cond_5
    :goto_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0, p1}, Landroid/widget/LinearLayout;->removeView(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result p1

    if-gtz p1, :cond_7

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->removeAllViews()V

    if-eqz p2, :cond_6

    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    if-nez p1, :cond_7

    :cond_6
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    const/16 p2, 0x8

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_7
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->checkChildMargin()V

    :cond_8
    :goto_1
    return-void
.end method

.method public static setPendingRecordingState(I)V
    .locals 0

    sput p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->sPendingRecordingTimeState:I

    return-void
.end method

.method private setToastTipLayoutParams(Z)V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f08051a

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setDividerDrawable(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setShowDividers(I)V

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->customToastLayoutTransition()Landroid/animation/LayoutTransition;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    goto :goto_0

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    :goto_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    const/16 p1, 0x11

    invoke-virtual {p0, p1}, Landroid/widget/LinearLayout;->setGravity(I)V

    return-void
.end method

.method private setViewEndMargin(Landroid/view/View;I)V
    .locals 0

    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p0

    check-cast p0, Landroid/view/ViewGroup$MarginLayoutParams;

    invoke-virtual {p0, p2}, Landroid/view/ViewGroup$MarginLayoutParams;->setMarginEnd(I)V

    invoke-virtual {p1, p0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return-void
.end method

.method private setViewTopMargin(Landroid/view/View;I)V
    .locals 0

    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p0

    check-cast p0, Landroid/view/ViewGroup$MarginLayoutParams;

    iput p2, p0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    invoke-virtual {p1, p0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    const/4 p0, 0x0

    invoke-static {p1, p0}, Landroidx/core/view/ViewCompat;->setTranslationY(Landroid/view/View;F)V

    return-void
.end method

.method private showCloseConfirm()V
    .locals 10

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const/4 v2, 0x0

    const v0, 0x7f100218

    invoke-virtual {p0, v0}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v3

    const v0, 0x7f100219

    invoke-virtual {p0, v0}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v4

    new-instance v5, Lcom/android/camera/fragment/top/FragmentTopAlert$12;

    invoke-direct {v5, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$12;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    const/4 v6, 0x0

    const/4 v7, 0x0

    const v0, 0x7f100217

    invoke-virtual {p0, v0}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v8

    const/4 v9, 0x0

    invoke-static/range {v1 .. v9}, Lcom/android/camera/RotateDialogController;->showSystemAlertDialog(Landroid/content/Context;Ljava/lang/String;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/Runnable;Ljava/lang/CharSequence;Ljava/lang/Runnable;Ljava/lang/CharSequence;Ljava/lang/Runnable;)Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    new-instance v1, Lcom/android/camera/fragment/top/FragmentTopAlert$13;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$13;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    return-void
.end method

.method private showManualParameterResetDialog()V
    .locals 10

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const/4 v2, 0x0

    const v0, 0x7f100171

    invoke-virtual {p0, v0}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v3

    const v0, 0x7f100558

    invoke-virtual {p0, v0}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v4

    new-instance v5, Lcom/android/camera/fragment/top/f;

    invoke-direct {v5, p0}, Lcom/android/camera/fragment/top/f;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    const/4 v6, 0x0

    const/4 v7, 0x0

    const/high16 v0, 0x1040000

    invoke-virtual {p0, v0}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v8

    sget-object v9, Lcom/android/camera/fragment/top/i;->INSTANCE:Lcom/android/camera/fragment/top/i;

    invoke-static/range {v1 .. v9}, Lcom/android/camera/RotateDialogController;->showSystemAlertDialog(Landroid/content/Context;Ljava/lang/String;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/Runnable;Ljava/lang/CharSequence;Ljava/lang/Runnable;Ljava/lang/CharSequence;Ljava/lang/Runnable;)Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    new-instance v1, Lcom/android/camera/fragment/top/FragmentTopAlert$2;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$2;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    return-void
.end method

.method private updateDocumentState(Z)V
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDocument()Lcom/android/camera/data/data/runing/ComponentRunningDocument;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    if-eqz v1, :cond_0

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/ui/MutiStateButton;->updateCurrentIndex(Ljava/lang/String;Z)V

    :cond_0
    return-void
.end method

.method private updateFlashForPhotoRecording(Z)V
    .locals 2

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v1, 0xa3

    if-eq v0, v1, :cond_0

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertImageType:I

    const/4 v1, 0x2

    if-eq v0, v1, :cond_3

    const/4 v1, 0x5

    if-ne v0, v1, :cond_1

    goto :goto_1

    :cond_1
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-eqz p1, :cond_2

    const/4 p1, 0x4

    goto :goto_0

    :cond_2
    const/4 p1, 0x0

    :goto_0
    invoke-virtual {p0, p1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_3
    :goto_1
    return-void
.end method

.method private updateTopHint(ILjava/lang/String;J)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertTopHintHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result p2

    invoke-virtual {p1, p2}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    const-wide/16 p1, 0x0

    cmp-long p1, p3, p1

    if-lez p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertTopHintHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p0, p3, p4}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :cond_1
    :goto_0
    return-void
.end method


# virtual methods
.method public synthetic Va()V
    .locals 1

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->isAdded()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandGestureTip:Lcom/android/camera/customization/BGTintTextView;

    const v0, 0x8000

    invoke-virtual {p0, v0}, Landroid/widget/TextView;->sendAccessibilityEvent(I)V

    :cond_0
    return-void
.end method

.method public synthetic Wa()V
    .locals 1

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->isAdded()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    const v0, 0x8000

    invoke-virtual {p0, v0}, Landroid/widget/TextView;->sendAccessibilityEvent(I)V

    :cond_0
    return-void
.end method

.method public synthetic Xa()V
    .locals 10

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xb5

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;->resetManually()V

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-eqz v0, :cond_1

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->resetMeter(I)V

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFlashSupportedInManualMode()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_2

    const/4 v1, 0x1

    new-array v1, v1, [I

    const/4 v2, 0x0

    const/16 v3, 0xc1

    aput v3, v1, v2

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->updateConfigItem([I)V

    :cond_2
    const/4 v5, 0x0

    const/16 v6, 0x8

    const/4 v7, 0x0

    const/4 v8, 0x0

    const/4 v9, 0x0

    move-object v4, p0

    invoke-virtual/range {v4 .. v9}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertParameterResetTip(ZIIIZ)V

    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtils;->trackManuallyResetDialogOk()V

    return-void
.end method

.method public synthetic Za()V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setLayoutTransition(Landroid/animation/LayoutTransition;)V

    return-void
.end method

.method public alertAiAudio(IIZ)V
    .locals 2
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    invoke-virtual {v0}, Landroid/widget/TextView;->getVisibility()I

    move-result v0

    const/16 v1, 0x8

    if-ne v0, v1, :cond_0

    if-ne p1, v1, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    if-nez p1, :cond_1

    if-eqz p3, :cond_1

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    invoke-virtual {p3, p1}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    const/4 v0, 0x0

    invoke-static {p3, v0}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    invoke-static {p3}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p3

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-virtual {p3, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p3

    const-wide/16 v0, 0x140

    invoke-virtual {p3, v0, v1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p3

    invoke-virtual {p3}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_0

    :cond_1
    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    invoke-virtual {p3, p1}, Landroid/widget/TextView;->setVisibility(I)V

    :goto_0
    if-nez p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    const/4 p3, 0x0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f070484

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    int-to-float v0, v0

    invoke-virtual {p1, p3, v0}, Landroid/widget/TextView;->setTextSize(IF)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateEndGravityTip()V

    return-void
.end method

.method public alertAiAudioBGHint(II)V
    .locals 3

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTipHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, v0}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTipHideRunnable:Ljava/lang/Runnable;

    const-wide/16 v1, 0xbb8

    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAiAudioMutexToastIfNeed(Landroid/content/Context;I)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :goto_0
    return-void
.end method

.method public alertAiAudioMutexToastIfNeed(Landroid/content/Context;I)V
    .locals 0

    invoke-static {}, Lcom/android/camera/Util;->isWiredHeadsetOn()Z

    move-result p0

    if-eqz p0, :cond_1

    const/4 p0, -0x1

    if-ne p2, p0, :cond_0

    goto :goto_1

    :cond_0
    packed-switch p2, :pswitch_data_0

    move p2, p0

    goto :goto_0

    :pswitch_0
    const p2, 0x7f1005b8

    goto :goto_0

    :pswitch_1
    const p2, 0x7f1005b7

    :goto_0
    if-eq p2, p0, :cond_1

    const/4 p0, 0x1

    invoke-static {p1, p2, p0}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;IZ)V

    :cond_1
    :goto_1
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x7f100451
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public alertAiSceneSelector(I)V
    .locals 7

    if-nez p1, :cond_0

    sget-object v0, Lcom/android/camera/fragment/top/g;->INSTANCE:Lcom/android/camera/fragment/top/g;

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    move-object v5, v0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f10058d

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f10058c

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v3

    const/4 v6, 0x0

    move-object v1, p0

    move v4, p1

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAiSceneSelector(Ljava/lang/String;Ljava/lang/String;ILcom/android/camera/ui/ToggleSwitch$OnCheckedChangeListener;Z)V

    return-void
.end method

.method public alertFastmotionIndicator(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    invoke-virtual {v0}, Landroid/view/View;->getVisibility()I

    move-result v0

    const/16 v1, 0x8

    if-ne v0, v1, :cond_0

    if-ne p1, v1, :cond_0

    return-void

    :cond_0
    if-nez p1, :cond_1

    if-eqz p5, :cond_1

    iget-object p5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    invoke-virtual {p5, p1}, Landroid/view/View;->setVisibility(I)V

    iget-object p5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    const/4 v0, 0x0

    invoke-static {p5, v0}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    invoke-static {p5}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p5

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-virtual {p5, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p5

    const-wide/16 v0, 0x140

    invoke-virtual {p5, v0, v1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p5

    invoke-virtual {p5}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_0

    :cond_1
    iget-object p5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    invoke-virtual {p5, p1}, Landroid/view/View;->setVisibility(I)V

    :goto_0
    if-nez p1, :cond_2

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    invoke-virtual {p0, p2, p3, p4}, Lcom/android/camera/ui/FastmotionIndicatorView;->showFastmotion(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    :cond_2
    return-void
.end method

.method public alertFastmotionTip(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeFastmotionTipResetRunnable()V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipTitle:Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipDesc:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipToResetRunnable:Ljava/lang/Runnable;

    const-wide/16 v0, 0x3e8

    invoke-virtual {p1, p2, v0, v1}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result p1

    const/4 p2, -0x1

    if-eq p1, p2, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->getVisibility()I

    move-result p1

    if-nez p1, :cond_0

    return-void

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;)V

    return-void
.end method

.method public alertFlash(ILjava/lang/String;)V
    .locals 6

    const/4 v0, 0x5

    const/4 v1, -0x1

    const/4 v2, 0x2

    const/4 v3, 0x1

    if-nez p1, :cond_c

    invoke-virtual {p2}, Ljava/lang/String;->hashCode()I

    move-result p1

    const/16 v4, 0x31

    const/4 v5, 0x0

    if-eq p1, v4, :cond_2

    const/16 v4, 0x32

    if-eq p1, v4, :cond_1

    const/16 v4, 0x35

    if-eq p1, v4, :cond_0

    goto :goto_0

    :cond_0
    const-string p1, "5"

    invoke-virtual {p2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    move p1, v2

    goto :goto_1

    :cond_1
    const-string p1, "2"

    invoke-virtual {p2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    move p1, v3

    goto :goto_1

    :cond_2
    const-string p1, "1"

    invoke-virtual {p2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    move p1, v5

    goto :goto_1

    :cond_3
    :goto_0
    move p1, v1

    :goto_1
    if-eqz p1, :cond_6

    if-eq p1, v3, :cond_5

    if-eq p1, v2, :cond_4

    goto :goto_2

    :cond_4
    move v1, v0

    goto :goto_2

    :cond_5
    move v1, v2

    goto :goto_2

    :cond_6
    move v1, v3

    :goto_2
    iget p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertImageType:I

    if-ne p1, v1, :cond_7

    return-void

    :cond_7
    iput v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertImageType:I

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result p1

    if-eqz p1, :cond_8

    invoke-static {}, Lb/c/a/c;->yn()Z

    move-result p1

    if-eqz p1, :cond_8

    move v1, v3

    :cond_8
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const p2, 0x7f080516

    invoke-virtual {p1, p2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result p2

    invoke-static {p1, p2}, Landroidx/core/graphics/drawable/DrawableCompat;->setTint(Landroid/graphics/drawable/Drawable;I)V

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-virtual {p2, p1}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    if-eq v1, v3, :cond_b

    if-eq v1, v2, :cond_a

    if-eq v1, v0, :cond_9

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    goto :goto_3

    :cond_9
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    const p2, 0x7f080186

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-direct {p0, p1, v3, v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;ZI)V

    goto :goto_3

    :cond_a
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    const p2, 0x7f080187

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-direct {p0, p1, v3, v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;ZI)V

    goto :goto_3

    :cond_b
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    const p2, 0x7f080185

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-direct {p0, p1, v3, v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;ZI)V

    goto :goto_3

    :cond_c
    iget p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertImageType:I

    if-eq p1, v2, :cond_d

    if-eq p1, v3, :cond_d

    if-eq p1, v0, :cond_d

    return-void

    :cond_d
    iput v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertImageType:I

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :goto_3
    return-void
.end method

.method public alertHandGestureHint(I)V
    .locals 3

    invoke-virtual {p0, p1}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandGestureTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandGestureTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandGestureTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandGestureTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    new-instance v0, Lcom/android/camera/fragment/top/a;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/a;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    const-wide/16 v1, 0x12c

    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    return-void
.end method

.method public alertHistogram(I)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    if-eqz v0, :cond_0

    invoke-virtual {v0, p1}, Landroid/view/View;->setVisibility(I)V

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateEndGravityTip()V

    return-void
.end method

.method public alertLightingTip(I)V
    .locals 3

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->parseLightingRes(I)I

    move-result p1

    const-wide/16 v0, -0x1

    const/4 v2, -0x1

    if-ne p1, v2, :cond_0

    const/16 v2, 0x8

    invoke-virtual {p0, v2, p1, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertRecommendTipHint(IIJ)V

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    invoke-virtual {p0, v2, p1, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertRecommendTipHint(IIJ)V

    :goto_0
    return-void
.end method

.method public alertMacroModeHint(IIZ)V
    .locals 1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTipHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    if-eqz p3, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTipHideRunnable:Ljava/lang/Runnable;

    const-wide/16 p2, 0xbb8

    invoke-virtual {p1, p0, p2, p3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public alertMimojiFaceDetect(ZI)V
    .locals 0

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result p2

    invoke-virtual {p1, p2}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    const/4 p2, 0x0

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p1}, Landroid/widget/TextView;->getVisibility()I

    move-result p1

    const/16 p2, 0x8

    if-eq p1, p2, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mViewHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->removeCallbacks(Ljava/lang/Runnable;)Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public alertMoonSelector(Ljava/lang/String;Ljava/lang/String;IZ)V
    .locals 7

    if-nez p3, :cond_0

    sget-object v0, Lcom/android/camera/fragment/top/d;->INSTANCE:Lcom/android/camera/fragment/top/d;

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    move-object v5, v0

    move-object v1, p0

    move-object v2, p1

    move-object v3, p2

    move v4, p3

    move v6, p4

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAiSceneSelector(Ljava/lang/String;Ljava/lang/String;ILcom/android/camera/ui/ToggleSwitch$OnCheckedChangeListener;Z)V

    return-void
.end method

.method public alertMusicClose(Z)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicClose:Landroid/widget/ImageView;

    if-eqz v0, :cond_1

    if-eqz p1, :cond_0

    const/high16 p1, 0x3f800000    # 1.0f

    invoke-virtual {v0, p1}, Landroid/widget/ImageView;->setAlpha(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setClickable(Z)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    invoke-static {p0}, Lcom/android/camera/animation/FolmeUtils;->touchTint(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    const p1, 0x3ecccccd    # 0.4f

    invoke-virtual {v0, p1}, Landroid/widget/ImageView;->setAlpha(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setClickable(Z)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    invoke-static {p0}, Lcom/android/camera/animation/FolmeUtils;->clean(Landroid/view/View;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public alertMusicTip(ILjava/lang/String;)V
    .locals 0

    if-nez p1, :cond_0

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusiHintText:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :goto_0
    return-void
.end method

.method public alertParameterDescriptionTip(IZ)V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0, p1}, Landroid/widget/ImageView;->setVisibility(I)V

    :cond_0
    const/4 v0, 0x0

    if-nez p1, :cond_1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v1}, Landroid/widget/ImageView;->getVisibility()I

    move-result v1

    if-nez v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v1}, Landroid/widget/ImageView;->getAlpha()F

    move-result v1

    cmpl-float v1, v1, v0

    if-lez v1, :cond_1

    return-void

    :cond_1
    if-nez p1, :cond_2

    if-eqz p2, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-static {p1, v0}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-static {p0}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    const/high16 p1, 0x3f800000    # 1.0f

    invoke-virtual {p0, p1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    const-wide/16 p1, 0x140

    invoke-virtual {p0, p1, p2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    :cond_2
    return-void
.end method

.method public alertParameterResetTip(ZIIIZ)V
    .locals 2
    .param p3    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    iget-object p4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {p4}, Landroid/widget/ImageView;->getVisibility()I

    move-result p4

    if-eq p4, p2, :cond_2

    iget-boolean p4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    if-eqz p4, :cond_0

    goto :goto_1

    :cond_0
    if-nez p2, :cond_1

    if-eqz p5, :cond_1

    iget-object p4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {p4, p2}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    const/4 p5, 0x0

    invoke-static {p4, p5}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-static {p4}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p4

    const/high16 p5, 0x3f800000    # 1.0f

    invoke-virtual {p4, p5}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p4

    const-wide/16 v0, 0x140

    invoke-virtual {p4, v0, v1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p4

    invoke-virtual {p4}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_0

    :cond_1
    iget-object p4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {p4, p2}, Landroid/widget/ImageView;->setVisibility(I)V

    :goto_0
    if-nez p2, :cond_2

    invoke-virtual {p0, p3}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {p0, p2}, Landroid/widget/ImageView;->setContentDescription(Ljava/lang/CharSequence;)V

    if-nez p1, :cond_2

    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtils;->trackManuallyResetShow()V

    :cond_2
    :goto_1
    return-void
.end method

.method public alertProColourHint(II)V
    .locals 1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProColourTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProColourTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProColourTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result p2

    invoke-virtual {p1, p2}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProColourTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProColourTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :goto_0
    return-void
.end method

.method public alertRecommendDescTip(Ljava/lang/String;IIJ)V
    .locals 3
    .param p3    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    if-eqz p2, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    const-string v0, "unknow"

    if-nez p2, :cond_1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-direct {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :cond_1
    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecommendDescRunable:Ljava/lang/Runnable;

    invoke-virtual {v1, v2}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    if-nez p2, :cond_2

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-virtual {p2, p3}, Landroid/widget/TextView;->setText(I)V

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-virtual {p0, p3}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p2, p3}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-direct {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;)V

    const-wide/16 p2, 0x0

    cmp-long p2, p4, p2

    if-ltz p2, :cond_3

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecommendDescRunable:Ljava/lang/Runnable;

    check-cast p2, Lcom/android/camera/fragment/top/FragmentTopAlert$AlertRecommendDescRunable;

    invoke-virtual {p2, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert$AlertRecommendDescRunable;->setTipType(Ljava/lang/String;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecommendDescRunable:Ljava/lang/Runnable;

    invoke-virtual {p1, p0, p4, p5}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    :cond_2
    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :cond_3
    :goto_0
    return-void
.end method

.method public alertRecommendTipHint(IIJ)V
    .locals 2
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertAiDetectTipHitRunable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;)V

    const-wide/16 p1, 0x0

    cmp-long p1, p3, p1

    if-ltz p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertAiDetectTipHitRunable:Ljava/lang/Runnable;

    invoke-virtual {p1, p0, p3, p4}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public alertSlideSwitchLayout(ZI)V
    .locals 4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    const/4 v1, 0x0

    if-eqz p1, :cond_3

    const/16 p1, 0xdd

    const/4 v2, 0x1

    if-eq p2, p1, :cond_2

    const/16 p1, 0xe4

    if-eq p2, p1, :cond_1

    const/16 p1, 0xf6

    if-eq p2, p1, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningMoon()Lcom/android/camera/data/data/runing/ComponentRunningMoon;

    move-result-object v1

    goto :goto_0

    :cond_1
    const/4 v2, 0x0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningTiltValue()Lcom/android/camera/data/data/runing/ComponentRunningTiltValue;

    move-result-object v1

    goto :goto_0

    :cond_2
    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDocument()Lcom/android/camera/data/data/runing/ComponentRunningDocument;

    move-result-object v1

    :goto_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    invoke-virtual {p1, p2}, Lcom/android/camera/ui/TopAlertSlideSwitchButton;->setType(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f060170

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/TopAlertSlideSwitchButton;->setIndicatorColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    invoke-virtual {p1, v1, p2, v2}, Lcom/android/camera/ui/TopAlertSlideSwitchButton;->setComponentData(Lcom/android/camera/data/data/ComponentData;IZ)V

    new-instance v1, Lcom/android/camera/fragment/top/FragmentTopAlert$17;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$17;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShowSlideSwitchLayout:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    goto :goto_1

    :cond_3
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShowSlideSwitchLayout:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->removeCallbacks(Ljava/lang/Runnable;)Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :goto_1
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    invoke-virtual {p0, v1}, Lcom/android/camera/ui/TopAlertSlideSwitchButton;->setSlideSwitchListener(Lcom/android/camera/ui/TopAlertSlideSwitchButton$SlideSwitchListener;)V

    return-void
.end method

.method public alertSubtitleHint(II)V
    .locals 1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSubtitleTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSubtitleTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSubtitleTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result p2

    invoke-virtual {p1, p2}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSubtitleTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSubtitleTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :goto_0
    return-void
.end method

.method public alertSuperNightSeTip(I)V
    .locals 1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSuperNightSeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSuperNightSeTip:Lcom/android/camera/customization/BGTintTextView;

    const v0, 0x7f100075

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSuperNightSeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSuperNightSeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :goto_0
    return-void
.end method

.method public alertSwitchTip(Ljava/lang/String;IILjava/lang/String;J)V
    .locals 4

    if-eqz p2, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    const-string v0, "unknow"

    if-nez p2, :cond_1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :cond_1
    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p3

    invoke-virtual {v1, p3}, Landroid/widget/TextView;->setTag(Ljava/lang/Object;)V

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p3, p4}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result v1

    invoke-virtual {p3, v1}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    iput-wide v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertAiSceneSwitchHintTime:J

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    new-instance v1, Lcom/android/camera/fragment/top/h;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/top/h;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    const-wide/16 v2, 0x12c

    invoke-virtual {p3, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mViewHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {p3, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    if-eqz p2, :cond_2

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    goto :goto_0

    :cond_2
    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p1, p4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    const-wide/16 p1, 0x0

    cmp-long p1, p5, p1

    if-lez p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mViewHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p0, p5, p6}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_3
    :goto_0
    return-void
.end method

.method public alertTimerBurstHint(IIZ)V
    .locals 1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTintColor()I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/customization/BGTintTextView;->setBGColor(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToToastLayout(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    if-eqz p3, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstRunnable:Ljava/lang/Runnable;

    const-wide/16 p2, 0xbb8

    invoke-virtual {p1, p0, p2, p3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public alertTopHint(II)V
    .locals 2
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    const-wide/16 v0, 0x0

    invoke-virtual {p0, p1, p2, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTopHint(IIJ)V

    return-void
.end method

.method public alertTopHint(IIJ)V
    .locals 3
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    const/16 v0, 0x8

    if-lez p2, :cond_0

    if-nez p1, :cond_0

    iput p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopHintTextResource:I

    goto :goto_0

    :cond_0
    if-ne p1, v0, :cond_1

    const/4 v1, 0x0

    iput v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopHintTextResource:I

    :cond_1
    :goto_0
    const/4 v1, 0x0

    iget v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopHintTextResource:I

    if-nez v2, :cond_2

    move p1, v0

    goto :goto_1

    :cond_2
    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v1

    :goto_1
    invoke-direct {p0, p1, v1, p3, p4}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateTopHint(ILjava/lang/String;J)V

    return-void
.end method

.method public alertTopHint(ILjava/lang/String;)V
    .locals 2

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    if-nez p1, :cond_0

    const/16 p1, 0x8

    :cond_0
    const-wide/16 v0, 0x0

    invoke-direct {p0, p1, p2, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateTopHint(ILjava/lang/String;J)V

    return-void
.end method

.method public alertTopHint(ILjava/lang/String;J)V
    .locals 1

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    if-nez p1, :cond_0

    const/16 p1, 0x8

    :cond_0
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateTopHint(ILjava/lang/String;J)V

    return-void
.end method

.method public alertUpdateValue(ILjava/lang/String;)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeZoomTipRestRunnable()V

    const/4 v0, 0x0

    if-nez p1, :cond_0

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertZoom(Z)V

    return-void

    :cond_0
    const/4 v1, 0x1

    if-ne p1, v1, :cond_5

    if-eqz p2, :cond_1

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_2

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    const/high16 v1, 0x428c0000    # 70.0f

    invoke-static {v1}, Lcom/android/camera/Util;->pixelTodp(F)F

    move-result v1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setTextSize(F)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTipToResetRunnable:Ljava/lang/Runnable;

    const-wide/16 v1, 0x3e8

    invoke-virtual {p1, p2, v1, v2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result p1

    const/4 p2, -0x1

    if-eq p1, p2, :cond_3

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-virtual {p1}, Landroid/widget/TextView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_3

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAudioZoomIndicator(Z)V

    return-void

    :cond_3
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 p2, 0xcc

    if-eq p1, p2, :cond_4

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;)V

    :cond_4
    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAudioZoomIndicator(Z)V

    :cond_5
    return-void
.end method

.method public alertVideoUltraClear(IIZ)V
    .locals 2
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-virtual {v0}, Landroid/widget/TextView;->getVisibility()I

    move-result v0

    const/16 v1, 0x8

    if-ne v0, v1, :cond_0

    if-ne p1, v1, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0, p2}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    if-nez p1, :cond_1

    if-eqz p3, :cond_1

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-virtual {p3, p1}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    const/4 v0, 0x0

    invoke-static {p3, v0}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-static {p3}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p3

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-virtual {p3, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p3

    const-wide/16 v0, 0x140

    invoke-virtual {p3, v0, v1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p3

    invoke-virtual {p3}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_0

    :cond_1
    iget-object p3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-virtual {p3, p1}, Landroid/widget/TextView;->setVisibility(I)V

    :goto_0
    if-nez p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    const/4 p3, 0x0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f070484

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    int-to-float v0, v0

    invoke-virtual {p1, p3, v0}, Landroid/widget/TextView;->setTextSize(IF)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setContentDescription(Ljava/lang/CharSequence;)V

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateEndGravityTip()V

    return-void
.end method

.method public clear(Z)V
    .locals 7

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeZoomTipRestRunnable()V

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clearAlertStatus()V

    if-eqz p1, :cond_1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xac

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->clearAllTipsState()V

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clearHandlerCallbacks()V

    :cond_1
    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertImageType:I

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result v0

    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    const/4 v2, 0x0

    move v3, v2

    :goto_0
    if-ge v3, v0, :cond_5

    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v4, v3}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    move-result-object v4

    invoke-virtual {v4}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_2

    if-eqz v5, :cond_3

    instance-of v6, v5, Ljava/lang/Integer;

    if-eqz v6, :cond_3

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    const/4 v6, 0x2

    if-eq v5, v6, :cond_3

    :cond_2
    invoke-interface {v1, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_3
    if-eqz p1, :cond_4

    invoke-interface {v1, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_4
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_5
    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_1
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_6

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/View;

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v3, v0}, Landroid/widget/LinearLayout;->removeView(Landroid/view/View;)V

    goto :goto_1

    :cond_6
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result p1

    const/4 v0, 0x1

    const/16 v3, 0x8

    if-gtz p1, :cond_7

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->removeAllViews()V

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setToastTipLayoutParams(Z)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v3}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_7
    invoke-interface {v1}, Ljava/util/List;->clear()V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {p1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result p1

    :goto_2
    if-ge v2, p1, :cond_9

    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v4, v2}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    move-result-object v4

    if-eqz v2, :cond_8

    invoke-interface {v1, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_3

    :cond_8
    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setToastTipLayoutParams(Z)V

    :goto_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_2

    :cond_9
    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_4
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_a

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/View;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v1, v0}, Landroid/widget/LinearLayout;->removeView(Landroid/view/View;)V

    goto :goto_4

    :cond_a
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clearVideoUltraClear()V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    if-eqz p1, :cond_b

    invoke-virtual {p1}, Landroid/widget/ImageView;->getVisibility()I

    move-result p1

    if-eq p1, v3, :cond_b

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {p1, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    :cond_b
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    if-eqz p1, :cond_c

    invoke-virtual {p1}, Landroid/widget/ImageView;->getVisibility()I

    move-result p1

    if-eq p1, v3, :cond_c

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {p1, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    :cond_c
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    if-eqz p1, :cond_d

    invoke-virtual {p1}, Landroid/view/View;->getVisibility()I

    move-result p1

    if-eq p1, v3, :cond_d

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-virtual {p1, v3}, Landroid/view/View;->setVisibility(I)V

    :cond_d
    const-string p1, "unknow"

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    return-void
.end method

.method public clearAlertStatus()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    return-void
.end method

.method public clearFastmotionTip()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    invoke-direct {p0, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;Z)V

    return-void
.end method

.method public clearVideoUltraClear()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    const/16 v1, 0x8

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/widget/TextView;->getVisibility()I

    move-result v0

    if-eq v0, v1, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    const-string v2, ""

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroid/view/View;->getVisibility()I

    move-result v0

    if-eq v0, v1, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateEndGravityTip()V

    return-void
.end method

.method public containShortDurationDescriptionTips(Ljava/lang/String;)Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationDescriptionTip:Ljava/lang/String;

    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method public synthetic f(Landroid/view/View;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->showCloseConfirm()V

    return-void
.end method

.method public synthetic g(Landroid/view/View;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->handleProVideoRecordingSimple(Landroid/view/View;)V

    return-void
.end method

.method public getFragmentInto()I
    .locals 0

    const/16 p0, 0xfd

    return p0
.end method

.method protected getLayoutResourceId()I
    .locals 0

    const p0, 0x7f0c008b

    return p0
.end method

.method public getVideoTag()Lcom/android/camera/ui/VideoTagView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoTagView:Lcom/android/camera/ui/VideoTagView;

    return-object p0
.end method

.method public synthetic h(Landroid/view/View;)V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isEnableClick()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa1

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    if-eqz v0, :cond_1

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isDoingAction()Z

    move-result v0

    if-eqz v0, :cond_1

    sget-object p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    const-string p1, "cameraAction.isDoingAction return"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    if-eqz p1, :cond_3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateMaps:Ljava/util/LinkedHashMap;

    if-eqz v0, :cond_3

    invoke-virtual {v0, p1}, Ljava/util/LinkedHashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    goto :goto_0

    :cond_2
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDocument()Lcom/android/camera/data/data/runing/ComponentRunningDocument;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v1, p1}, Lcom/android/camera/data/data/ComponentData;->setComponentValue(ILjava/lang/String;)V

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackDocumentModeChanged(Ljava/lang/String;)V

    const/4 p1, 0x1

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateDocumentState(Z)V

    :cond_3
    :goto_0
    return-void
.end method

.method public hideRecommendDescTip(Ljava/lang/String;)V
    .locals 0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->containShortDurationDescriptionTips(Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :cond_0
    return-void
.end method

.method public hideRecordingTime()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/view/animation/AlphaAnimation;->cancel()V

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const/4 v0, 0x4

    invoke-virtual {p0, v0}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_1
    return-void
.end method

.method public hideSwitchTip()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToToastLayout(Landroid/view/View;)V

    const-string v0, "unknow"

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getVisibility()I

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mViewHideRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, p0}, Landroid/widget/LinearLayout;->removeCallbacks(Ljava/lang/Runnable;)Z

    :cond_0
    return-void
.end method

.method protected initView(Landroid/view/View;)V
    .locals 5

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initHandler()V

    const v0, 0x7f090184

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLlAlertRecordingTimeView:Landroid/widget/LinearLayout;

    const v0, 0x7f090045

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const v0, 0x7f090043

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    const v0, 0x7f090044

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLlAlertRecordingTimeView:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    sget v1, Lcom/android/camera/Util;->sTopBarHeight:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    sget v1, Lcom/android/camera/Util;->sTopMargin:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLlAlertRecordingTimeView:Landroid/widget/LinearLayout;

    invoke-direct {p0, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setViewTopMargin(Landroid/view/View;I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    new-instance v0, Lcom/android/camera/ui/VideoTagView;

    invoke-direct {v0}, Lcom/android/camera/ui/VideoTagView;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoTagView:Lcom/android/camera/ui/VideoTagView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoTagView:Lcom/android/camera/ui/VideoTagView;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v0, p1, v1}, Lcom/android/camera/ui/VideoTagView;->init(Landroid/view/View;Landroid/content/Context;)V

    const v0, 0x7f090318

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    const v0, 0x7f0900fe

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/FastmotionIndicatorView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionIndicatorView:Lcom/android/camera/ui/FastmotionIndicatorView;

    const v0, 0x7f09003e

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioTip:Landroid/widget/TextView;

    const v0, 0x7f0900de

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getEndGravityTipDividerDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setDividerDrawable(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setShowDividers(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertTopMargin()I

    move-result v2

    invoke-direct {p0, v0, v2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setViewTopMargin(Landroid/view/View;I)V

    const v0, 0x7f09028d

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mStartGravityTipLayout:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mStartGravityTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getAlertStartGravityTipLayoutTopMargin()I

    move-result v2

    invoke-direct {p0, v0, v2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setViewTopMargin(Landroid/view/View;I)V

    const v0, 0x7f09023a

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0901b2

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    new-array v0, v1, [Landroid/view/View;

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    const/4 v3, 0x0

    aput-object v2, v0, v3

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    const/4 v4, 0x1

    aput-object v2, v0, v4

    invoke-static {v0}, Lcom/android/camera/animation/FolmeUtils;->touchTint([Landroid/view/View;)V

    sget v0, Lcom/android/camera/fragment/top/FragmentTopAlert;->sPendingRecordingTimeState:I

    if-eqz v0, :cond_0

    invoke-virtual {p0, v0, v3}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setRecordingTimeState(IZ)V

    invoke-static {v3}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setPendingRecordingState(I)V

    :cond_0
    const v0, 0x7f0902c7

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initPortraitTopTipLayout()V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const v2, 0x7f0902c8

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initToastTipLayout()V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initTopTipToggleSwitch()Lcom/android/camera/ui/ToggleSwitch;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initTopSlideSwitchButton()Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSlideSwitchButton:Lcom/android/camera/ui/TopAlertSlideSwitchButton;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initRecommendTipText()Landroid/widget/TextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initZoomTipText()Landroid/widget/TextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTip:Landroid/widget/TextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initMusicTipText()Landroid/widget/LinearLayout;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    const v2, 0x7f090168

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusiHintText:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    const v2, 0x7f090167

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicClose:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    invoke-static {v0}, Lcom/android/camera/animation/FolmeUtils;->touchTint(Landroid/view/View;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLiveMusicHintLayout:Landroid/widget/LinearLayout;

    new-instance v2, Lcom/android/camera/fragment/top/k;

    invoke-direct {v2, p0}, Lcom/android/camera/fragment/top/k;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initPermanentTip()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mPermanentTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initSubtitleTip()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mSubtitleTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initProColourTip()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProColourTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initHorizonDirectTipText()Landroid/widget/TextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLyingDirectHintText:Landroid/widget/TextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initMacroModeTip()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mMacroModeTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initTimerBurstTip()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTimerBurstTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initAiAudioBGTip()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiAudioBGTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initHandGestureTip()Lcom/android/camera/customization/BGTintTextView;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandGestureTip:Lcom/android/camera/customization/BGTintTextView;

    const v0, 0x7f09023d

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/HistogramView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initFastmotionTip()Landroid/widget/LinearLayout;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    const v2, 0x7f090101

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipTitle:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTip:Landroid/widget/LinearLayout;

    const v2, 0x7f090100

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipDesc:Landroid/widget/TextView;

    const-string v0, "mipro-regular"

    invoke-static {v0, v3}, Landroid/graphics/Typeface;->create(Ljava/lang/String;I)Landroid/graphics/Typeface;

    move-result-object v0

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipTitle:Landroid/widget/TextView;

    invoke-virtual {v2, v0}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;)V

    const-string v0, "mipro-medium"

    invoke-static {v0, v3}, Landroid/graphics/Typeface;->create(Ljava/lang/String;I)Landroid/graphics/Typeface;

    move-result-object v0

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipDesc:Landroid/widget/TextView;

    invoke-virtual {v2, v0}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;)V

    const v0, 0x7f090232

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/airbnb/lottie/LottieAnimationView;

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    invoke-static {p1}, Lcom/android/camera/animation/FolmeUtils;->touchTint(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    invoke-virtual {p1}, Landroid/widget/ImageView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v2, 0x7f0703fd

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    sget v0, Lcom/android/camera/Util;->sTopMargin:I

    sget v3, Lcom/android/camera/Util;->sTopBarHeight:I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    invoke-virtual {v4, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    sub-int/2addr v3, v2

    div-int/2addr v3, v1

    add-int/2addr v0, v3

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    new-instance v0, Lcom/android/camera/fragment/top/j;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/j;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    return-void
.end method

.method public varargs isContainAlertRecommendTip([I)Z
    .locals 5
    .param p1    # [I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    if-eqz v0, :cond_4

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    if-eqz v2, :cond_4

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v2, -0x1

    if-ne v0, v2, :cond_0

    goto :goto_2

    :cond_0
    if-eqz p1, :cond_4

    array-length v0, p1

    if-gtz v0, :cond_1

    goto :goto_2

    :cond_1
    array-length v0, p1

    move v2, v1

    :goto_0
    if-ge v2, v0, :cond_4

    aget v3, p1, v2

    if-gtz v3, :cond_2

    goto :goto_1

    :cond_2
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    invoke-virtual {v4, v3}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v3

    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-virtual {v4}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_3

    const/4 p0, 0x1

    return p0

    :cond_3
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_4
    :goto_2
    return v1
.end method

.method public isCurrentRecommendTipText(I)Z
    .locals 2

    const/4 v0, 0x0

    if-gtz p1, :cond_0

    return v0

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_1

    return v0

    :cond_1
    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-virtual {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isShowTopLayoutSpecifyTip(Landroid/view/View;)Z

    move-result v1

    if-nez v1, :cond_2

    return v0

    :cond_2
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mRecommendTip:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object p0

    invoke-virtual {p1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-nez p0, :cond_3

    return v0

    :cond_3
    const/4 p0, 0x1

    return p0
.end method

.method public isFlashShowing()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    const-string v2, "flash"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    return v1

    :cond_1
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    invoke-virtual {p0}, Landroid/widget/ImageView;->getVisibility()I

    move-result p0

    if-nez p0, :cond_2

    const/4 v1, 0x1

    :cond_2
    :goto_0
    return v1
.end method

.method public isHDRShowing()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastTipFlash:Landroid/widget/ImageView;

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShortDurationToastTip:Ljava/lang/String;

    const-string v2, "hdr"

    if-eq v0, v2, :cond_1

    return v1

    :cond_1
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mToastSwitchTip:Lcom/android/camera/customization/BGTintTextView;

    invoke-virtual {p0}, Landroid/widget/TextView;->getVisibility()I

    move-result p0

    if-nez p0, :cond_2

    const/4 v1, 0x1

    :cond_2
    :goto_0
    return v1
.end method

.method public isShow()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShow:Z

    return p0
.end method

.method public isShowBacklightSelector()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v2, -0x1

    if-ne v0, v2, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v2, 0x7f10058d

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-virtual {p0}, Lcom/android/camera/ui/ToggleSwitch;->getTextOn()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_1

    const/4 p0, 0x1

    return p0

    :cond_1
    :goto_0
    return v1
.end method

.method public isShowMoonSelector()Z
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v0

    const/4 v2, -0x1

    if-ne v0, v2, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v2, 0x7f100076

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAiSceneSelectView:Lcom/android/camera/ui/ToggleSwitch;

    invoke-virtual {p0}, Lcom/android/camera/ui/ToggleSwitch;->getTextOn()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_1

    const/4 p0, 0x1

    return p0

    :cond_1
    :goto_0
    return v1
.end method

.method public isShowTopLayoutSpecifyTip(Landroid/view/View;)Z
    .locals 1

    const/4 v0, 0x0

    if-eqz p1, :cond_2

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    if-nez p0, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p0, p1}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result p0

    const/4 p1, -0x1

    if-ne p0, p1, :cond_1

    return v0

    :cond_1
    const/4 p0, 0x1

    return p0

    :cond_2
    :goto_0
    return v0
.end method

.method public notifyAfterFrameAvailable(I)V
    .locals 0

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->notifyAfterFrameAvailable(I)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {p1}, Lcom/android/camera/CameraSettings;->isProVideoHistogramOpen(I)Z

    move-result p1

    if-eqz p1, :cond_0

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-direct {p1, p0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_0
    return-void
.end method

.method public onActivityCreated(Landroid/os/Bundle;)V
    .locals 0
    .param p1    # Landroid/os/Bundle;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param

    invoke-super {p0, p1}, Landroidx/fragment/app/Fragment;->onActivityCreated(Landroid/os/Bundle;)V

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isEnableClick()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa1

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    if-eqz v0, :cond_1

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isDoingAction()Z

    move-result v0

    if-eqz v0, :cond_1

    sget-object p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    const-string p1, "cameraAction.isDoingAction return"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    const v0, 0x7f0901b2

    if-eq p1, v0, :cond_3

    const v0, 0x7f09023a

    if-eq p1, v0, :cond_2

    goto/16 :goto_3

    :cond_2
    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtils;->trackManuallyResetClick()V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->showManualParameterResetDialog()V

    goto :goto_3

    :cond_3
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v0, 0xb4

    const/16 v1, 0xa9

    if-ne p1, v0, :cond_4

    const-string p1, "M_proVideo_"

    goto :goto_0

    :cond_4
    if-ne p1, v1, :cond_5

    const-string p1, "M_fastMotion_"

    goto :goto_0

    :cond_5
    const-string p1, "M_manual_"

    :goto_0
    const-string v0, "parameter_description"

    const-string v2, "on"

    invoke-static {p1, v0, v2}, Lcom/android/camera/statistic/MistatsWrapper;->moduleUIClickEvent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v0, 0xbb

    if-ne p1, v0, :cond_6

    sget-object p1, Lcom/android/camera/fragment/manually/FragmentAmbilightDescription;->TAG:Ljava/lang/String;

    goto :goto_1

    :cond_6
    if-ne p1, v1, :cond_7

    sget-object p1, Lcom/android/camera/fragment/fastmotion/FragmentFastMotionDescription;->TAG:Ljava/lang/String;

    goto :goto_1

    :cond_7
    sget-object p1, Lcom/android/camera/fragment/manually/FragmentParameterDescription;->TAG:Ljava/lang/String;

    :goto_1
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getFragmentManager()Landroidx/fragment/app/FragmentManager;

    move-result-object v2

    invoke-static {v2, p1}, Lcom/android/camera/fragment/FragmentUtils;->getFragmentByTag(Landroidx/fragment/app/FragmentManager;Ljava/lang/String;)Landroidx/fragment/app/Fragment;

    move-result-object v2

    if-nez v2, :cond_a

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-ne v2, v0, :cond_8

    new-instance v0, Lcom/android/camera/fragment/manually/FragmentAmbilightDescription;

    invoke-direct {v0}, Lcom/android/camera/fragment/manually/FragmentAmbilightDescription;-><init>()V

    const-string v1, "attr_ambilight_about_button_click"

    invoke-static {v1}, Lcom/android/camera/statistic/CameraStatUtils;->trackAmbilightClick(Ljava/lang/String;)V

    goto :goto_2

    :cond_8
    if-ne v2, v1, :cond_9

    new-instance v0, Lcom/android/camera/fragment/fastmotion/FragmentFastMotionDescription;

    invoke-direct {v0}, Lcom/android/camera/fragment/fastmotion/FragmentFastMotionDescription;-><init>()V

    goto :goto_2

    :cond_9
    new-instance v0, Lcom/android/camera/fragment/manually/FragmentParameterDescription;

    invoke-direct {v0}, Lcom/android/camera/fragment/manually/FragmentParameterDescription;-><init>()V

    :goto_2
    const/4 v1, 0x2

    const v2, 0x7f1100b8

    invoke-virtual {v0, v1, v2}, Landroidx/fragment/app/DialogFragment;->setStyle(II)V

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getFragmentManager()Landroidx/fragment/app/FragmentManager;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/fragment/app/FragmentManager;->beginTransaction()Landroidx/fragment/app/FragmentTransaction;

    move-result-object p0

    invoke-virtual {p0, v0, p1}, Landroidx/fragment/app/FragmentTransaction;->add(Landroidx/fragment/app/Fragment;Ljava/lang/String;)Landroidx/fragment/app/FragmentTransaction;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/fragment/app/FragmentTransaction;->commitAllowingStateLoss()I

    :cond_a
    :goto_3
    return-void
.end method

.method public onStart()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onStart()V

    sget-object v0, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    const-string v1, "onStart"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->showRecordingTime()V

    return-void
.end method

.method public onStop()V
    .locals 3

    invoke-super {p0}, Lcom/android/camera/fragment/BaseFragment;->onStop()V

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clear(Z)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    const/4 v2, 0x0

    if-eqz v1, :cond_0

    invoke-virtual {v1}, Landroid/app/AlertDialog;->dismiss()V

    iput-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertDialog:Landroid/app/AlertDialog;

    :cond_0
    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    if-eqz v1, :cond_1

    invoke-virtual {v1, v2}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    :cond_1
    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    if-eqz v1, :cond_2

    invoke-virtual {v1}, Landroid/view/animation/AlphaAnimation;->cancel()V

    iput-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    :cond_2
    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const/16 v2, 0x8

    if-eqz v1, :cond_3

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_3
    iput-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    if-eqz v0, :cond_4

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_4
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    if-eqz p0, :cond_5

    invoke-virtual {p0, v2}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_5
    return-void
.end method

.method public parseLightingRes(I)I
    .locals 1

    const/4 p0, -0x1

    if-eq p1, p0, :cond_3

    const/4 v0, 0x3

    if-eq p1, v0, :cond_2

    const/4 v0, 0x4

    if-eq p1, v0, :cond_1

    const/4 v0, 0x5

    if-eq p1, v0, :cond_0

    const/4 v0, 0x6

    goto :goto_0

    :cond_0
    const p0, 0x7f1001f9

    goto :goto_0

    :cond_1
    const p0, 0x7f1001fc

    goto :goto_0

    :cond_2
    const p0, 0x7f1001fb

    :cond_3
    :goto_0
    return p0
.end method

.method public provideAnimateElement(ILjava/util/List;I)V
    .locals 6
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/util/List<",
            "Lio/reactivex/Completable;",
            ">;I)V"
        }
    .end annotation

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    sget-object v2, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "provideAnimateElement "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v2, 0x3

    const/4 v3, 0x0

    const/16 v4, 0x8

    if-ne p3, v2, :cond_0

    iput-boolean v3, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const/4 v5, 0x0

    invoke-virtual {v2, v5}, Landroid/widget/TextView;->setAnimation(Landroid/view/animation/Animation;)V

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v2, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {v2, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {v2, v4}, Landroid/widget/TextView;->setVisibility(I)V

    goto :goto_0

    :cond_0
    iget-boolean v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    if-eqz v5, :cond_1

    invoke-virtual {p0, v2, v3}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setRecordingTimeState(IZ)V

    :cond_1
    :goto_0
    invoke-super {p0, p1, p2, p3}, Lcom/android/camera/fragment/BaseFragment;->provideAnimateElement(ILjava/util/List;I)V

    if-eq v0, p1, :cond_4

    const/16 p3, 0xa5

    const/16 v2, 0xa3

    if-ne v0, v2, :cond_2

    if-eq p1, p3, :cond_4

    :cond_2
    if-ne v0, p3, :cond_3

    if-eq p1, v2, :cond_4

    :cond_3
    move v3, v1

    :cond_4
    invoke-virtual {p0, v3}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clear(Z)V

    invoke-virtual {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setShow(Z)V

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateEndGravityTip()V

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateTopAlertLayout()V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 p3, 0xb4

    if-eq p1, p3, :cond_6

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-virtual {p1}, Landroid/view/View;->getVisibility()I

    move-result p1

    if-nez p1, :cond_6

    if-eqz p2, :cond_5

    new-instance p1, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-direct {p1, p0}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-interface {p2, p0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_1

    :cond_5
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-virtual {p0, v4}, Landroid/view/View;->setVisibility(I)V

    :cond_6
    :goto_1
    return-void
.end method

.method public provideRotateItem(Ljava/util/List;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/view/View;",
            ">;I)V"
        }
    .end annotation

    invoke-super {p0, p1, p2}, Lcom/android/camera/fragment/BaseFragment;->provideRotateItem(Ljava/util/List;I)V

    invoke-direct {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initEndGravityTipLayout(I)V

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateTopAlertLayout()V

    return-void
.end method

.method public refreshHistogramStatsView()V
    .locals 1
    .annotation build Landroidx/annotation/MainThread;
    .end annotation

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/view/View;->getVisibility()I

    move-result v0

    if-nez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-virtual {p0}, Lcom/android/camera/ui/HistogramView;->refresh()V

    :cond_0
    return-void
.end method

.method public removeFastmotionTipResetRunnable()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mFastmotionTipToResetRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, p0}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    return-void
.end method

.method public removeZoomTipRestRunnable()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mZoomTipToResetRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, p0}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    return-void
.end method

.method public setAlertAnim(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsAlertAnim:Z

    return-void
.end method

.method public setRecordingTimeState(IZ)V
    .locals 12

    sget-object v0, Lcom/android/camera/fragment/top/FragmentTopAlert;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, " setRecordingTimeState "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, "   mCurrentMode: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/16 v0, 0xbb

    const/16 v1, 0xb4

    const/16 v2, 0xa9

    const/4 v3, 0x4

    const/16 v4, 0x8

    const/4 v5, 0x1

    const/4 v6, 0x0

    if-eq p1, v5, :cond_a

    const/4 p2, 0x0

    const/4 v5, 0x2

    if-eq p1, v5, :cond_3

    const/4 v0, 0x3

    if-eq p1, v0, :cond_1

    if-eq p1, v3, :cond_0

    goto/16 :goto_4

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    if-eqz p0, :cond_18

    invoke-virtual {p0}, Landroid/view/animation/AlphaAnimation;->cancel()V

    goto/16 :goto_4

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    if-nez p1, :cond_2

    new-instance p1, Landroid/view/animation/AlphaAnimation;

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-direct {p1, v0, p2}, Landroid/view/animation/AlphaAnimation;-><init>(FF)V

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    const-wide/16 v0, 0x190

    invoke-virtual {p1, v0, v1}, Landroid/view/animation/AlphaAnimation;->setDuration(J)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    const-wide/16 v0, 0x64

    invoke-virtual {p1, v0, v1}, Landroid/view/animation/AlphaAnimation;->setStartOffset(J)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    new-instance p2, Landroid/view/animation/DecelerateInterpolator;

    invoke-direct {p2}, Landroid/view/animation/DecelerateInterpolator;-><init>()V

    invoke-virtual {p1, p2}, Landroid/view/animation/AlphaAnimation;->setInterpolator(Landroid/view/animation/Interpolator;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    invoke-virtual {p1, v5}, Landroid/view/animation/AlphaAnimation;->setRepeatMode(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    const/4 p2, -0x1

    invoke-virtual {p1, p2}, Landroid/view/animation/AlphaAnimation;->setRepeatCount(I)V

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    invoke-virtual {p1, p0}, Landroid/widget/TextView;->startAnimation(Landroid/view/animation/Animation;)V

    goto/16 :goto_4

    :cond_3
    iput-boolean v6, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {p1, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {p1, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-ne p1, v1, :cond_5

    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterResetVisibleBeforeRecording:Z

    if-eqz p1, :cond_4

    iput-boolean v6, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterResetVisibleBeforeRecording:Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {p1, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    :cond_4
    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterDescriptionVisibleBeforeRecording:Z

    if-eqz p1, :cond_7

    iput-boolean v6, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterDescriptionVisibleBeforeRecording:Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {p1, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    :cond_5
    if-eq p1, v0, :cond_6

    if-ne p1, v2, :cond_7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p1

    invoke-virtual {p1}, Lb/c/a/b;->uj()Z

    move-result p1

    if-eqz p1, :cond_7

    :cond_6
    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterDescriptionVisibleBeforeRecording:Z

    if-eqz p1, :cond_7

    iput-boolean v6, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterDescriptionVisibleBeforeRecording:Z

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {p1, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    :cond_7
    :goto_0
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    if-eqz p1, :cond_8

    invoke-virtual {p1}, Landroid/view/animation/AlphaAnimation;->cancel()V

    :cond_8
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {p1}, Landroid/widget/TextView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_9

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {p1}, Landroid/widget/TextView;->getAlpha()F

    move-result p1

    cmpl-float p1, p1, p2

    if-eqz p1, :cond_9

    new-instance p1, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-direct {p1, p2}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    invoke-virtual {p1}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    goto :goto_1

    :cond_9
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-static {p1}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;->directSetResult(Landroid/view/View;)V

    :goto_1
    invoke-direct {p0, v6}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateFlashForPhotoRecording(Z)V

    goto/16 :goto_4

    :cond_a
    iput-boolean v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getTimerBurstController()Lcom/android/camera/timerburst/TimerBurstController;

    move-result-object p1

    iget v7, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v8, 0xa5

    const-string v9, "/"

    const v10, 0x7f06016d

    if-eq v7, v8, :cond_16

    const/16 v8, 0xa7

    if-eq v7, v8, :cond_15

    if-eq v7, v2, :cond_f

    const/16 v8, 0xac

    if-eq v7, v8, :cond_f

    const/16 v8, 0xae

    const-string v11, "00:15"

    if-eq v7, v8, :cond_e

    const/16 v8, 0xb1

    if-eq v7, v8, :cond_e

    if-eq v7, v1, :cond_f

    if-eq v7, v0, :cond_f

    const/16 v8, 0xb7

    if-eq v7, v8, :cond_e

    const/16 v8, 0xb8

    if-eq v7, v8, :cond_c

    packed-switch v7, :pswitch_data_0

    goto/16 :goto_3

    :pswitch_0
    invoke-virtual {p1}, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting()Z

    move-result v0

    if-eqz v0, :cond_b

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {v0, v6}, Landroid/widget/TextView;->setVisibility(I)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {p1}, Lcom/android/camera/timerburst/TimerBurstController;->getCaptureIndex()I

    move-result v2

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {v1, v6}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v10}, Landroid/content/res/Resources;->getColor(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_3

    :cond_b
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v0, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    invoke-direct {p0, v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateFlashForPhotoRecording(Z)V

    goto/16 :goto_3

    :cond_c
    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result v0

    if-eqz v0, :cond_d

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const-string v1, "00:05"

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_3

    :cond_d
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v0, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_3

    :cond_e
    :pswitch_1
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v0, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_3

    :cond_f
    :pswitch_2
    iget v6, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-ne v6, v1, :cond_11

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {v0}, Landroid/widget/ImageView;->getVisibility()I

    move-result v0

    if-nez v0, :cond_10

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterResetTip:Landroid/widget/ImageView;

    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    iput-boolean v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterResetVisibleBeforeRecording:Z

    :cond_10
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0}, Landroid/widget/ImageView;->getVisibility()I

    move-result v0

    if-nez v0, :cond_13

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    iput-boolean v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterDescriptionVisibleBeforeRecording:Z

    goto :goto_2

    :cond_11
    if-ne v6, v0, :cond_12

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const-string v1, "monospace"

    invoke-static {v1, v5}, Landroid/graphics/Typeface;->create(Ljava/lang/String;I)Landroid/graphics/Typeface;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0}, Landroid/widget/ImageView;->getVisibility()I

    move-result v0

    if-nez v0, :cond_13

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    iput-boolean v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterDescriptionVisibleBeforeRecording:Z

    goto :goto_2

    :cond_12
    if-ne v6, v2, :cond_13

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->uj()Z

    move-result v0

    if-eqz v0, :cond_13

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0}, Landroid/widget/ImageView;->getVisibility()I

    move-result v0

    if-nez v0, :cond_13

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mManualParameterDescriptionTip:Landroid/widget/ImageView;

    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    iput-boolean v5, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsParameterDescriptionVisibleBeforeRecording:Z

    :cond_13
    :goto_2
    if-eqz p2, :cond_14

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setVisibility(I)V

    goto/16 :goto_3

    :cond_14
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const-string v1, "00:00"

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_3

    :cond_15
    invoke-virtual {p1}, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting()Z

    move-result v0

    if-eqz v0, :cond_17

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {v0, v6}, Landroid/widget/TextView;->setVisibility(I)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {p1}, Lcom/android/camera/timerburst/TimerBurstController;->getCaptureIndex()I

    move-result v2

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {v1, v6}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v10}, Landroid/content/res/Resources;->getColor(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_3

    :cond_16
    invoke-virtual {p1}, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting()Z

    move-result v0

    if-eqz v0, :cond_17

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {v0, v6}, Landroid/widget/TextView;->setVisibility(I)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextNumerator:Landroid/widget/TextView;

    invoke-virtual {p1}, Lcom/android/camera/timerburst/TimerBurstController;->getCaptureIndex()I

    move-result v2

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {v1, v6}, Landroid/widget/TextView;->setVisibility(I)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v10}, Landroid/content/res/Resources;->getColor(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingTextDenominator:Landroid/widget/TextView;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_17
    :goto_3
    if-nez p2, :cond_18

    invoke-virtual {p1}, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting()Z

    move-result p1

    if-nez p1, :cond_18

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-direct {p1, p0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_18
    :goto_4
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0xa1
        :pswitch_1
        :pswitch_2
        :pswitch_0
    .end packed-switch
.end method

.method public setShow(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mShow:Z

    return-void
.end method

.method public showDocumentStateButton(I)V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initDocumentStateButton()V

    :cond_0
    if-nez p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    const/4 v0, 0x0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result v1

    const/4 v2, 0x1

    if-lez v1, :cond_1

    move v1, v2

    goto :goto_0

    :cond_1
    const/4 v1, -0x1

    :goto_0
    invoke-direct {p0, p1, v2, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;ZLandroid/widget/LinearLayout$LayoutParams;I)V

    const/4 p1, 0x0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateDocumentState(Z)V

    goto :goto_1

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mDocumentStateButton:Lcom/android/camera/ui/MutiStateButton;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :goto_1
    return-void
.end method

.method public showRecordingTime()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    if-eqz v0, :cond_1

    iget-boolean v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoRecording:Z

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mBlingAnimation:Landroid/view/animation/AlphaAnimation;

    if-eqz v1, :cond_0

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->startAnimation(Landroid/view/animation/Animation;)V

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_1
    return-void
.end method

.method public updateEndGravityTip()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mEndGravityTipLayout:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    move-result-object v0

    new-instance v1, Lcom/android/camera/fragment/top/FragmentTopAlert$15;

    invoke-direct {v1, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert$15;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    invoke-virtual {v0, v1}, Landroid/view/ViewTreeObserver;->addOnGlobalLayoutListener(Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;)V

    :cond_0
    return-void
.end method

.method public updateHistogramStatsData([I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/HistogramView;->updateStats([I)V

    :cond_0
    return-void
.end method

.method public updateHistogramStatsData([I[I[I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-virtual {p0, p1, p2, p3}, Lcom/android/camera/ui/HistogramView;->updateStats([I[I[I)V

    return-void
.end method

.method public updateLyingDirectHint(Z)V
    .locals 3

    const/4 v0, -0x1

    if-eqz p1, :cond_0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLyingDirectHintText:Landroid/widget/TextView;

    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result v1

    if-ne v1, v0, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLyingDirectHintText:Landroid/widget/TextView;

    new-instance v0, Lcom/android/camera/fragment/top/c;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/c;-><init>(Lcom/android/camera/fragment/top/FragmentTopAlert;)V

    sget-object v1, Lcom/android/camera/fragment/top/b;->INSTANCE:Lcom/android/camera/fragment/top/b;

    invoke-direct {p0, p1, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->addViewToTipLayout(Landroid/view/View;Ljava/lang/Runnable;Ljava/lang/Runnable;)V

    goto :goto_0

    :cond_0
    if-nez p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mTopTipLayout:Landroid/widget/LinearLayout;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLyingDirectHintText:Landroid/widget/TextView;

    invoke-virtual {p1, v1}, Landroid/widget/LinearLayout;->indexOfChild(Landroid/view/View;)I

    move-result p1

    if-eq p1, v0, :cond_1

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mLyingDirectHintText:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->removeViewToTipLayout(Landroid/view/View;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public updateProVideoRecordingSimpleView(Z)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mProVideoRecordingSimpleView:Lcom/airbnb/lottie/LottieAnimationView;

    if-eqz v0, :cond_3

    const/4 v1, 0x0

    if-eqz p1, :cond_0

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_0

    :cond_0
    const/16 p1, 0x8

    invoke-virtual {v0, p1}, Landroid/widget/ImageView;->setVisibility(I)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getProVideoRecordingSimpleRunning()Z

    move-result p1

    if-eqz p1, :cond_3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    invoke-virtual {p1, v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->setProVideoRecordingSimpleRunning(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xb5

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;

    if-eqz p1, :cond_1

    const/4 v0, 0x1

    invoke-interface {p1, v0}, Lcom/android/camera/protocol/ModeProtocol$ManuallyAdjust;->forceUpdateManualView(Z)V

    :cond_1
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {p1}, Lcom/android/camera/CameraSettings;->isProVideoHistogramOpen(I)Z

    move-result p1

    if-eqz p1, :cond_2

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-direct {p1, v0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p1

    invoke-virtual {p1}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_2
    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoUltraClearTipVisibleBeforeProVideoSimple:Z

    if-eqz p1, :cond_3

    iput-boolean v1, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mIsVideoUltraClearTipVisibleBeforeProVideoSimple:Z

    new-instance p1, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mVideoUltraClearTip:Landroid/widget/TextView;

    invoke-direct {p1, p0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {p1}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_3
    :goto_0
    return-void
.end method

.method public updateRGBHistogramSwitched(Z)V
    .locals 0

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-static {p0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;->directSetResult(Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mHistogramView:Lcom/android/camera/ui/HistogramView;

    invoke-static {p0}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;->directSetResult(Landroid/view/View;)V

    :goto_0
    return-void
.end method

.method public updateRecordingTime(Ljava/lang/String;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    return-void
.end method

.method public updateRecordingTimeStyle(Z)V
    .locals 1

    const/4 v0, 0x0

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    const p1, 0x7f080524

    invoke-virtual {p0, p1, v0, v0, v0}, Landroid/widget/TextView;->setCompoundDrawablesWithIntrinsicBounds(IIII)V

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopAlert;->mAlertRecordingText:Landroid/widget/TextView;

    invoke-virtual {p0, v0, v0, v0, v0}, Landroid/widget/TextView;->setCompoundDrawablesWithIntrinsicBounds(IIII)V

    :goto_0
    return-void
.end method

.method public updateTopAlertLayout()V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isLandScape()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initLandscapeTopTipLayout()V

    goto :goto_0

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->initPortraitTopTipLayout()V

    :goto_0
    return-void
.end method
