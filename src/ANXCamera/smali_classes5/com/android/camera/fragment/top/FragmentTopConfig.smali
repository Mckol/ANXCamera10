.class public Lcom/android/camera/fragment/top/FragmentTopConfig;
.super Lcom/android/camera/fragment/BaseFragment;
.source "FragmentTopConfig.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Lcom/android/camera/fragment/top/TopExpandAdapter$ExpandListener;
.implements Lcom/android/camera/protocol/ModeProtocol$TopAlert;
.implements Lcom/android/camera/protocol/ModeProtocol$SnapShotIndicator;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;
.implements Lcom/android/camera/protocol/ModeProtocol$HandleBeautyRecording;
.implements Lcom/android/camera/ui/SlideSwitchButton$SlideSwitchListener;


# static fields
.field private static final EXPAND_STATE_CENTER:I = 0x2

.field private static final EXPAND_STATE_LEFT:I = 0x0

.field private static final EXPAND_STATE_LEFT_FROM_SIBLING:I = 0x1

.field private static final EXPAND_STATE_RIGHT:I = 0x4

.field private static final EXPAND_STATE_RIGHT_FROM_SIBLING:I = 0x3

.field private static final TAG:Ljava/lang/String; = "FragmentTopConfig"

.field public static final TIP_HINT_DURATION_1S:I = 0x3e8

.field public static final TIP_HINT_DURATION_2S:I = 0x7d0

.field public static final TIP_HINT_DURATION_3S:I = 0xbb8


# instance fields
.field private mAiSceneResources:[I

.field private mAutoZoomResources:[I

.field private mCaptureDelayNumber:Landroid/widget/TextView;

.field private mCinematicRatioResources:[I

.field private mConfigViews:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroid/widget/ImageView;",
            ">;"
        }
    .end annotation
.end field

.field private mCurrentAiSceneLevel:I

.field private mCustomSeekBarCount:Lcom/android/camera/timerburst/CustomSeekBar;

.field private mCustomSeekBarInterval:Lcom/android/camera/timerburst/CustomSeekBar;

.field private mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

.field private mDisplayRectTopMargin:I

.field private mDocumentResources:[I

.field private mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

.field private mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

.field private mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

.field private mGifResource:I

.field private mImageViewBack:Landroid/widget/ImageView;

.field private mIsRTL:Z

.field private mIsShowExtraMenu:Z

.field private mIsShowExtraTimerMenu:Z

.field private mIsShowTopLyingDirectHint:Z

.field private mLightingResource:[I

.field private mLiveShotAnimator:Landroid/animation/ObjectAnimator;

.field private mLiveShotResource:[I

.field private mLlTimerMenu:Landroid/widget/LinearLayout;

.field private mMoreResources:[I

.field private mMultiSnapNum:Landroid/widget/TextView;

.field private mSnapStyle:Landroid/text/style/TextAppearanceSpan;

.field private mSpacesItemWidth:I

.field private mStringBuilder:Landroid/text/SpannableStringBuilder;

.field private mSuperEISResources:[I

.field private mSuperMacroResources:[I

.field private mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

.field private mTipsState:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation
.end field

.field private mTopBackgroundHeight:I

.field private mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

.field private mTopBarAnimationComponent:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

.field private mTopConfigMenu:Landroid/view/View;

.field private mTopConfigMenuHeight:I

.field private mTopConfigViewGroup:Landroid/view/ViewGroup;

.field private mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

.field private mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

.field private mTopExtraMenuHeight:I

.field private mTotalWidth:I

.field private mTvShotInterval:Landroid/widget/TextView;

.field private mUltraPixelPhotographyIconResources:[I

.field private mUltraPixelPhotographyTipString:[Ljava/lang/String;

.field private mUltraPixelPortraitResources:[I

.field private mUltraWideBokehResources:[I

.field private mVideo8KResource:[I

.field private mVideoBokehResource:[I

.field private mZoomInAnimator:Landroid/animation/AnimatorSet;

.field private mZoomOutAnimator:Landroid/animation/AnimatorSet;


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/BaseFragment;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTipsState:Ljava/util/Map;

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/fragment/top/FragmentTopConfig;)Lcom/android/camera/fragment/top/ExtraAdapter;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    return-object p0
.end method

.method static synthetic access$100(Lcom/android/camera/fragment/top/FragmentTopConfig;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->directHiddenExtraMenu()V

    return-void
.end method

.method static synthetic access$202(Lcom/android/camera/fragment/top/FragmentTopConfig;Landroid/animation/ValueAnimator;)Landroid/animation/ValueAnimator;
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    return-object p1
.end method

.method static synthetic access$300(Lcom/android/camera/fragment/top/FragmentTopConfig;)Landroid/view/ViewGroup;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigViewGroup:Landroid/view/ViewGroup;

    return-object p0
.end method

.method static synthetic access$400(Lcom/android/camera/fragment/top/FragmentTopConfig;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenuHeight:I

    return p0
.end method

.method static synthetic access$500(Lcom/android/camera/fragment/top/FragmentTopConfig;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    return p0
.end method

.method static synthetic access$600(Lcom/android/camera/fragment/top/FragmentTopConfig;)Lcom/android/camera/ui/ShapeBackGroundView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    return-object p0
.end method

.method static synthetic access$700(Lcom/android/camera/fragment/top/FragmentTopConfig;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundHeight:I

    return p0
.end method

.method private adjustViewBackground()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/fragment/top/ExtraAdapter;->animationRunning()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v0}, Landroid/widget/FrameLayout;->getTag()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v1}, Lcom/android/camera/ui/ShapeBackGroundView;->getCurrentMaskHeight()I

    move-result v1

    if-eq v0, v1, :cond_1

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getTag()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const/4 v1, 0x1

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/ui/ShapeBackGroundView;->setMaskSpecificHeight(IZ)V

    :cond_1
    return-void
.end method

.method private alertHDR(IZZZ)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p2

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isShowMoonSelector()Z

    move-result p2

    if-eqz p2, :cond_1

    return-void

    :cond_1
    if-eqz p4, :cond_3

    if-nez p1, :cond_2

    if-eqz p3, :cond_3

    const/16 p2, 0xc2

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object p2

    if-eqz p2, :cond_3

    invoke-virtual {p2}, Landroid/widget/ImageView;->performClick()Z

    goto :goto_0

    :cond_2
    const/4 p2, 0x1

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reverseExpandTopBar(Z)Z

    :cond_3
    :goto_0
    const p2, 0x7f1001db

    const-string p3, "hdr"

    invoke-virtual {p0, p3, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSwitchTip(Ljava/lang/String;II)V

    return-void
.end method

.method private alertTopMusicHint(ILjava/lang/String;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertMusicTip(ILjava/lang/String;)V

    :cond_0
    return-void
.end method

.method private animatorExtraMenu(Z)V
    .locals 13

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->getVisibility()I

    move-result v0

    if-eqz v0, :cond_0

    if-nez p1, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    if-nez p1, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->isRunning()Z

    move-result v0

    if-eqz v0, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getUiStyle()I

    move-result v0

    const/4 v1, 0x3

    const/16 v2, 0xff

    const/4 v3, 0x0

    if-ne v0, v1, :cond_2

    const/16 v2, 0x99

    move v6, v2

    move v5, v3

    goto :goto_0

    :cond_2
    move v5, v2

    move v6, v5

    :goto_0
    new-instance v12, Lcom/android/camera/fragment/top/FragmentTopConfig$2;

    invoke-direct {v12, p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig$2;-><init>(Lcom/android/camera/fragment/top/FragmentTopConfig;Z)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v0}, Lcom/android/camera/ui/ShapeBackGroundView;->getBlackOriginHeight()I

    move-result v7

    const/16 v0, 0x8

    if-eqz p1, :cond_4

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->initExtraTimerBurstMenu()V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-virtual {p1, v0}, Landroid/view/View;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigViewGroup:Landroid/view/ViewGroup;

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    iget v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigViewGroup:Landroid/view/ViewGroup;

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    if-nez v7, :cond_3

    goto :goto_1

    :cond_3
    iget v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    :goto_1
    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/FrameLayout;->setTag(Ljava/lang/Object;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {p1}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    iget v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    iput v0, p1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v0, p1}, Landroid/widget/FrameLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    iget v8, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    const/4 v9, 0x0

    const/16 v10, 0x42

    const/16 v11, 0xc8

    invoke-virtual/range {v4 .. v12}, Lcom/android/camera/ui/ShapeBackGroundView;->startBackGroundAnimator(IIIIIIILandroid/animation/Animator$AnimatorListener;)V

    goto :goto_2

    :cond_4
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {p1, v1}, Landroid/widget/FrameLayout;->setTag(Ljava/lang/Object;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-virtual {p1, v3}, Landroid/view/View;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const/4 v0, 0x0

    invoke-static {p1, v0}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-static {p1}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-virtual {p1, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    new-instance v0, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v0}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {p1, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setInterpolator(Landroid/view/animation/Interpolator;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    const-wide/16 v0, 0xc8

    invoke-virtual {p1, v0, v1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    const-wide/16 v2, 0x64

    invoke-virtual {p1, v2, v3}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setStartDelay(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    if-nez p1, :cond_5

    const/4 p1, 0x2

    new-array p1, p1, [F

    fill-array-data p1, :array_0

    invoke-static {p1}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    new-instance v2, Lcom/android/camera/fragment/top/FragmentTopConfig$3;

    invoke-direct {v2, p0, v7, v5, v6}, Lcom/android/camera/fragment/top/FragmentTopConfig$3;-><init>(Lcom/android/camera/fragment/top/FragmentTopConfig;III)V

    invoke-virtual {p1, v2}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {p1, v12}, Landroid/animation/ValueAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    new-instance v2, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v2}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {p1, v2}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {p1, v0, v1}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    :cond_5
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraMenuHideAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {p0}, Landroid/animation/ValueAnimator;->start()V

    :goto_2
    return-void

    :array_0
    .array-data 4
        0x3f800000    # 1.0f
        0x0
    .end array-data
.end method

.method private configBottomPopupTips(Z)V
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p0

    invoke-virtual {p0}, Lb/c/a/b;->Gj()Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xaf

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz p0, :cond_1

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->showIDCardTip(Z)V

    :cond_1
    return-void
.end method

.method private directHiddenExtraMenu()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigViewGroup:Landroid/view/ViewGroup;

    invoke-virtual {v0}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    iget v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenuHeight:I

    if-ne v1, v2, :cond_0

    return-void

    :cond_0
    iput v2, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigViewGroup:Landroid/view/ViewGroup;

    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v0}, Lcom/android/camera/ui/ShapeBackGroundView;->getBlackOriginHeight()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/FrameLayout;->setTag(Ljava/lang/Object;)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v1, v0}, Lcom/android/camera/ui/ShapeBackGroundView;->setCurrentHeight(I)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    const/4 v2, 0x0

    invoke-virtual {v1, v0, v2}, Lcom/android/camera/ui/ShapeBackGroundView;->setMaskSpecificHeight(IZ)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v0, v2}, Lcom/android/camera/ui/ShapeBackGroundView;->setCurrentRadius(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v0}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    iget v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundHeight:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v1, v0}, Landroid/widget/FrameLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-virtual {v0, v2}, Landroid/view/View;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    iput-boolean v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    return-void
.end method

.method private enableAllDisabledMenuItem()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    if-eqz v0, :cond_3

    invoke-virtual {v0}, Landroid/util/SparseBooleanArray;->size()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    invoke-virtual {v0}, Landroid/util/SparseBooleanArray;->size()I

    move-result v0

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_2

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    invoke-virtual {v2, v1}, Landroid/util/SparseBooleanArray;->keyAt(I)I

    move-result v2

    invoke-virtual {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object v2

    if-eqz v2, :cond_1

    invoke-static {v2}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;->directSetResult(Landroid/view/View;)V

    :cond_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_2
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    invoke-virtual {p0}, Landroid/util/SparseBooleanArray;->clear()V

    :cond_3
    :goto_1
    return-void
.end method

.method private expandExtraView(Lcom/android/camera/data/data/ComponentData;Landroid/view/View;I)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-virtual {v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->hideRecordingTime()V

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reverseExpandTopBar(Z)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    new-instance v0, Lcom/android/camera/fragment/top/TopExpandAdapter;

    invoke-direct {v0, p1, p0}, Lcom/android/camera/fragment/top/TopExpandAdapter;-><init>(Lcom/android/camera/data/data/ComponentData;Lcom/android/camera/fragment/top/TopExpandAdapter$ExpandListener;)V

    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsRTL:Z

    if-eqz p1, :cond_1

    invoke-virtual {p2}, Landroid/view/View;->getRight()I

    move-result p1

    goto :goto_0

    :cond_1
    invoke-virtual {p2}, Landroid/view/View;->getLeft()I

    move-result p1

    :goto_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/TopExpandAdapter;->setAnchorViewX(I)V

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isLandscapeMode()Z

    move-result p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

    const/16 v1, 0x5a

    invoke-virtual {p1, v1}, Lcom/android/camera/fragment/top/TopExpendView;->setRotation(I)V

    goto :goto_1

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    invoke-virtual {p1, v1}, Lcom/android/camera/fragment/top/TopExpendView;->setRotation(I)V

    :goto_1
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

    invoke-virtual {p1, v0}, Lcom/android/camera/fragment/top/TopExpendView;->setAdapter(Lcom/android/camera/fragment/top/TopExpandAdapter;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

    new-instance v0, Lcom/android/camera/fragment/top/l;

    invoke-direct {v0, p0}, Lcom/android/camera/fragment/top/l;-><init>(Lcom/android/camera/fragment/top/FragmentTopConfig;)V

    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBarAnimationComponent:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

    iput-object v0, p1, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->mTopExpendView:Lcom/android/camera/fragment/top/TopExpendView;

    invoke-virtual {p2}, Landroid/view/View;->getLeft()I

    move-result v0

    iput v0, p1, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->mReverseLeft:I

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBarAnimationComponent:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-virtual {p1, p3, v0}, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->hideOtherViews(ILjava/util/List;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBarAnimationComponent:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    iput-object p2, p1, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->mAnchorView:Landroid/view/View;

    iget p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSpacesItemWidth:I

    iput p0, p1, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->spacesItemWidth:I

    invoke-virtual {p1}, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->showExpendView()V

    return-void
.end method

.method private getAiSceneDrawable(I)Landroid/graphics/drawable/Drawable;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const/high16 v0, 0x7f030000

    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->obtainTypedArray(I)Landroid/content/res/TypedArray;

    move-result-object p0

    const/4 v0, 0x0

    if-ltz p1, :cond_0

    invoke-virtual {p0}, Landroid/content/res/TypedArray;->length()I

    move-result v1

    if-ge p1, v1, :cond_0

    invoke-virtual {p0, p1}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    :cond_0
    invoke-virtual {p0}, Landroid/content/res/TypedArray;->recycle()V

    return-object v0
.end method

.method private getAiSceneResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0802a0
        0x7f0802a1
    .end array-data
.end method

.method private getAiSceneShadowDrawable(I)Landroid/graphics/drawable/Drawable;
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v0, 0x7f030002

    invoke-virtual {p0, v0}, Landroid/content/res/Resources;->obtainTypedArray(I)Landroid/content/res/TypedArray;

    move-result-object p0

    const/4 v0, 0x0

    if-ltz p1, :cond_0

    invoke-virtual {p0}, Landroid/content/res/TypedArray;->length()I

    move-result v1

    if-ge p1, v1, :cond_0

    invoke-virtual {p0, p1}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    :cond_0
    invoke-virtual {p0}, Landroid/content/res/TypedArray;->recycle()V

    return-object v0
.end method

.method private getAutoZoomResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f08018d
        0x7f08018e
    .end array-data
.end method

.method private getCinematicRatioResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0801a6
        0x7f0801a7
    .end array-data
.end method

.method private getDocumentResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f080118
        0x7f080119
    .end array-data
.end method

.method private getExposureFeedbackResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0801c7
        0x7f0801c8
    .end array-data
.end method

.method private getGifRecource()I
    .locals 0

    const p0, 0x7f08033c

    return p0
.end method

.method private getInitialMargin(Lcom/android/camera/data/data/config/TopConfigItem;Landroid/widget/ImageView;I)I
    .locals 8

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/data/data/config/SupportedConfigs;->getConfigsSize()I

    move-result v0

    :goto_0
    if-gtz v0, :cond_1

    return v1

    :cond_1
    iget v2, p1, Lcom/android/camera/data/data/config/TopConfigItem;->index:I

    invoke-virtual {p2}, Landroid/widget/ImageView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v3

    check-cast v3, Landroid/widget/FrameLayout$LayoutParams;

    iput v1, v3, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    const v4, 0x800005

    const/4 v5, 0x1

    if-eq v0, v5, :cond_9

    const/4 v6, 0x2

    const v7, 0x800003

    if-eq v0, v6, :cond_4

    if-nez v2, :cond_2

    iput v1, v3, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    iput v7, v3, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    invoke-virtual {p2, v3}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return v1

    :cond_2
    sub-int/2addr v0, v5

    if-ne v2, v0, :cond_3

    iput v1, v3, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    iput v4, v3, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    invoke-virtual {p2, v3}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return v1

    :cond_3
    iget p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTotalWidth:I

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {p2}, Lcom/android/camera/data/data/config/SupportedConfigs;->getConfigsSize()I

    move-result p2

    mul-int/2addr p2, p3

    sub-int/2addr p1, p2

    div-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSpacesItemWidth:I

    iget p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSpacesItemWidth:I

    mul-int/2addr p0, v2

    mul-int/2addr v2, p3

    add-int/2addr p0, v2

    return p0

    :cond_4
    if-nez v2, :cond_6

    iput v1, v3, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    iget p1, p1, Lcom/android/camera/data/data/config/TopConfigItem;->gravity:I

    if-nez p1, :cond_5

    move p1, v7

    :cond_5
    iput p1, v3, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    goto :goto_1

    :cond_6
    if-ne v2, v5, :cond_8

    iput v1, v3, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    iget p1, p1, Lcom/android/camera/data/data/config/TopConfigItem;->gravity:I

    if-nez p1, :cond_7

    move p1, v4

    :cond_7
    iput p1, v3, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    :cond_8
    :goto_1
    iget p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTotalWidth:I

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {v2}, Lcom/android/camera/data/data/config/SupportedConfigs;->getConfigsSize()I

    move-result v2

    mul-int/2addr v2, p3

    sub-int/2addr p1, v2

    sub-int/2addr v0, v5

    div-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSpacesItemWidth:I

    invoke-virtual {p2, v3}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return v1

    :cond_9
    iput v1, v3, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    iget p0, p1, Lcom/android/camera/data/data/config/TopConfigItem;->gravity:I

    if-nez p0, :cond_a

    move p0, v4

    :cond_a
    iput p0, v3, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    invoke-virtual {p2, v3}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return v1
.end method

.method private getLightingResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0802cc
        0x7f0802cd
    .end array-data
.end method

.method private getLiveShotResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f08029a
        0x7f08029f
    .end array-data
.end method

.method private getMoreResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0802ce
        0x7f0802cf
    .end array-data
.end method

.method private getPortraitResources()I
    .locals 0

    const p0, 0x7f0802d0

    return p0
.end method

.method private getSettingResources()I
    .locals 0

    const p0, 0x7f0802c6

    return p0
.end method

.method private getSuperEISResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0801e8
        0x7f0801e9
    .end array-data
.end method

.method private getSuperMacroResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0801eb
        0x7f0801ec
    .end array-data
.end method

.method private getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;
    .locals 3

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    const/4 v1, 0x0

    const-string v2, "FragmentTopConfig"

    if-nez v0, :cond_0

    const-string p0, "getTopAlert(): fragment is null"

    invoke-static {v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object v1

    :cond_0
    invoke-virtual {v0}, Landroidx/fragment/app/Fragment;->isAdded()Z

    move-result v0

    if-nez v0, :cond_1

    const-string p0, "getTopAlert(): fragment is not added yet"

    invoke-static {v2, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object v1

    :cond_1
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    return-object p0
.end method

.method private getUltraPixelPortraitResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0801f4
        0x7f0801f4
    .end array-data
.end method

.method private getUltraWideBokehResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f080315
        0x7f080317
    .end array-data
.end method

.method private getVideo8KRecource()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0801f6
        0x7f0801f5
    .end array-data
.end method

.method private getVideoBokehResources()[I
    .locals 0

    const/4 p0, 0x2

    new-array p0, p0, [I

    fill-array-data p0, :array_0

    return-object p0

    nop

    :array_0
    .array-data 4
        0x7f0802d0
        0x7f0802d1
    .end array-data
.end method

.method private initExtraMenu()V
    .locals 7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v1, v0, v2}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilitiesByBogusCameraId(II)Lcom/android/camera2/CameraCapabilities;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->isNormalIntent()Z

    move-result v3

    invoke-static {v2, v0, v1, v3}, Lcom/android/camera/data/data/config/SupportedConfigFactory;->getSupportedExtraConfigs(IILcom/android/camera2/CameraCapabilities;Z)Lcom/android/camera/data/data/config/SupportedConfigs;

    move-result-object v0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f0a0002

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v1

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f0a000e

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/data/data/global/DataItemGlobal;->getDisplayMode()I

    move-result v3

    const/4 v4, 0x2

    if-ne v3, v4, :cond_0

    move v1, v2

    :cond_0
    new-instance v2, Landroidx/recyclerview/widget/GridLayoutManager;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v2, v3, v1}, Landroidx/recyclerview/widget/GridLayoutManager;-><init>(Landroid/content/Context;I)V

    new-instance v3, Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v5

    invoke-direct {v3, v5, v0, p0, p0}, Lcom/android/camera/fragment/top/ExtraAdapter;-><init>(Landroid/content/Context;Lcom/android/camera/data/data/config/SupportedConfigs;Landroid/view/View$OnClickListener;Lcom/android/camera/ui/SlideSwitchButton$SlideSwitchListener;)V

    iput-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView;->getRecycledViewPool()Landroidx/recyclerview/widget/RecyclerView$RecycledViewPool;

    move-result-object v0

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {v3, v4}, Lcom/android/camera/fragment/top/ExtraAdapter;->getItemCount(I)I

    move-result v3

    invoke-virtual {v0, v4, v3}, Landroidx/recyclerview/widget/RecyclerView$RecycledViewPool;->setMaxRecycledViews(II)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView;->getRecycledViewPool()Landroidx/recyclerview/widget/RecyclerView$RecycledViewPool;

    move-result-object v0

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    const/4 v5, 0x1

    invoke-virtual {v3, v5}, Lcom/android/camera/fragment/top/ExtraAdapter;->getItemCount(I)I

    move-result v3

    invoke-virtual {v0, v5, v3}, Landroidx/recyclerview/widget/RecyclerView$RecycledViewPool;->setMaxRecycledViews(II)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView;->getItemAnimator()Landroidx/recyclerview/widget/RecyclerView$ItemAnimator;

    move-result-object v0

    check-cast v0, Landroidx/recyclerview/widget/SimpleItemAnimator;

    const/4 v3, 0x0

    invoke-virtual {v0, v3}, Landroidx/recyclerview/widget/SimpleItemAnimator;->setSupportsChangeAnimations(Z)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    iget-object v6, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {v0, v6}, Landroidx/recyclerview/widget/RecyclerView;->setAdapter(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V

    new-instance v0, Lcom/android/camera/fragment/top/FragmentTopConfig$1;

    invoke-direct {v0, p0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig$1;-><init>(Lcom/android/camera/fragment/top/FragmentTopConfig;I)V

    invoke-virtual {v2, v0}, Landroidx/recyclerview/widget/GridLayoutManager;->setSpanSizeLookup(Landroidx/recyclerview/widget/GridLayoutManager$SpanSizeLookup;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0, v2}, Landroidx/recyclerview/widget/RecyclerView;->setLayoutManager(Landroidx/recyclerview/widget/RecyclerView$LayoutManager;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0, v3}, Landroid/view/ViewGroup;->setFocusable(Z)V

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f070475

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f070477

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v1

    add-int/2addr v0, v1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {v1, v5}, Lcom/android/camera/fragment/top/ExtraAdapter;->getTotalRow(I)I

    move-result v1

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f07046f

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    mul-int/2addr v1, v2

    add-int/2addr v0, v1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {v1, v4}, Lcom/android/camera/fragment/top/ExtraAdapter;->getTotalRow(I)I

    move-result v1

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f070479

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    mul-int/2addr v1, v2

    add-int/2addr v0, v1

    iput v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    iget v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    iget p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenuHeight:I

    sub-int/2addr v1, p0

    invoke-virtual {v0, v1}, Lcom/android/camera/fragment/top/ExtraAdapter;->setAnimateHeight(I)V

    return-void
.end method

.method private initExtraTimerBurstMenu()V
    .locals 3

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    const/16 v1, 0x8

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    invoke-virtual {p0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto :goto_0

    :cond_0
    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    const/4 v2, 0x0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    invoke-virtual {p0, v2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto :goto_0

    :cond_1
    if-nez v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0, v2}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    invoke-virtual {p0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_2
    :goto_0
    return-void
.end method

.method private initSnapNumAnimator()V
    .locals 2

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f020001

    invoke-static {v0, v1}, Landroid/animation/AnimatorInflater;->loadAnimator(Landroid/content/Context;I)Landroid/animation/Animator;

    move-result-object v0

    check-cast v0, Landroid/animation/AnimatorSet;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomInAnimator:Landroid/animation/AnimatorSet;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomInAnimator:Landroid/animation/AnimatorSet;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/animation/AnimatorSet;->setTarget(Ljava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomInAnimator:Landroid/animation/AnimatorSet;

    new-instance v1, Lmiui/view/animation/QuadraticEaseInOutInterpolator;

    invoke-direct {v1}, Lmiui/view/animation/QuadraticEaseInOutInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/animation/AnimatorSet;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f020003

    invoke-static {v0, v1}, Landroid/animation/AnimatorInflater;->loadAnimator(Landroid/content/Context;I)Landroid/animation/Animator;

    move-result-object v0

    check-cast v0, Landroid/animation/AnimatorSet;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomOutAnimator:Landroid/animation/AnimatorSet;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomOutAnimator:Landroid/animation/AnimatorSet;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/animation/AnimatorSet;->setTarget(Ljava/lang/Object;)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomOutAnimator:Landroid/animation/AnimatorSet;

    new-instance v0, Lmiui/view/animation/QuadraticEaseInOutInterpolator;

    invoke-direct {v0}, Lmiui/view/animation/QuadraticEaseInOutInterpolator;-><init>()V

    invoke-virtual {p0, v0}, Landroid/animation/AnimatorSet;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    return-void
.end method

.method private initTopView()V
    .locals 13

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v1, 0x7f0902b7

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/ColorImageView;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v2, 0x7f0902b8

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Lcom/android/camera/ui/ColorImageView;

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v3, 0x7f0902b9

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Lcom/android/camera/ui/ColorImageView;

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v4, 0x7f0902ba

    invoke-virtual {v3, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Lcom/android/camera/ui/ColorImageView;

    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v5, 0x7f0902bb

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Lcom/android/camera/ui/ColorImageView;

    iget-object v5, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v6, 0x7f0902bc

    invoke-virtual {v5, v6}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Lcom/android/camera/ui/ColorImageView;

    iget-object v6, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v7, 0x7f0902bd

    invoke-virtual {v6, v7}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v6

    check-cast v6, Lcom/android/camera/ui/ColorImageView;

    iget-object v7, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v8, 0x7f0902be

    invoke-virtual {v7, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Lcom/android/camera/ui/ColorImageView;

    iget-object v8, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v9, 0x7f0902bf

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v8

    check-cast v8, Lcom/android/camera/ui/ColorImageView;

    iget-object v9, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v10, 0x7f0902c0

    invoke-virtual {v9, v10}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Lcom/android/camera/ui/ColorImageView;

    iget-object v10, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v11, 0x7f0902c1

    invoke-virtual {v10, v11}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v10

    check-cast v10, Lcom/android/camera/ui/ColorImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v1, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v2, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v3, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v4, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v5, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v6, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v7, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v8, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v9, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-virtual {v10, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const/16 v11, 0xb

    new-array v11, v11, [Landroid/view/View;

    const/4 v12, 0x0

    aput-object v0, v11, v12

    const/4 v12, 0x1

    aput-object v1, v11, v12

    const/4 v12, 0x2

    aput-object v2, v11, v12

    const/4 v12, 0x3

    aput-object v3, v11, v12

    const/4 v12, 0x4

    aput-object v4, v11, v12

    const/4 v12, 0x5

    aput-object v5, v11, v12

    const/4 v12, 0x6

    aput-object v6, v11, v12

    const/4 v12, 0x7

    aput-object v7, v11, v12

    const/16 v12, 0x8

    aput-object v8, v11, v12

    const/16 v12, 0x9

    aput-object v9, v11, v12

    const/16 v12, 0xa

    aput-object v10, v11, v12

    invoke-static {v11}, Lcom/android/camera/animation/FolmeUtils;->touchTint([Landroid/view/View;)V

    new-instance v11, Ljava/util/ArrayList;

    invoke-direct {v11}, Ljava/util/ArrayList;-><init>()V

    iput-object v11, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    iget-object v11, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v11, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v6}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v8}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v9}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {p0, v10}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    return-void
.end method

.method private notifyExtraMenuVisibilityChange(Z)V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xc1

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;

    if-eqz p0, :cond_0

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;->onExtraMenuVisibilityChange(Z)V

    :cond_0
    return-void
.end method

.method private onClickByExtraMenu(Landroid/view/View;Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;)V
    .locals 3

    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const/16 v1, 0xaa

    if-eq v0, v1, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    const v2, 0x7f090245

    if-ne p1, v2, :cond_1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->showExtraTimerBurstMenu()V

    const/4 p0, 0x0

    invoke-static {p0}, Lcom/android/camera/CameraSettings;->setTimerBurstEnable(Z)V

    invoke-interface {p2, v1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    return-void

    :cond_1
    :goto_0
    invoke-interface {p2, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {p1, v0}, Lcom/android/camera/fragment/top/ExtraAdapter;->setOnClictTag(I)V

    :cond_2
    const/16 p1, 0xe1

    if-eq v0, p1, :cond_4

    const/16 p1, 0xf2

    if-eq v0, p1, :cond_3

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    goto :goto_1

    :cond_3
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->hideExtraMenu()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xc1

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;->startAiLens()V

    new-instance p0, Ljava/util/HashMap;

    invoke-direct {p0}, Ljava/util/HashMap;-><init>()V

    const-string p1, "attr_operate_state"

    const-string p2, "start_ai_detect_googleLens"

    invoke-interface {p0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string p1, "key_google_lens"

    invoke-static {p1, p0}, Lcom/android/camera/statistic/MistatsWrapper;->mistatEvent(Ljava/lang/String;Ljava/util/Map;)V

    goto :goto_1

    :cond_4
    const/4 p1, 0x5

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->removeExtraMenu(I)V

    :goto_1
    return-void
.end method

.method private reConfigTipOfFlash(Z)V
    .locals 6

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v1, 0xb6

    if-ne v0, v1, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v1

    invoke-virtual {v0}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_6

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    const-string v2, "1"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    const/4 v4, 0x0

    if-nez v3, :cond_5

    const-string v3, "101"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_2

    goto :goto_0

    :cond_2
    const-string v3, "2"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_3

    invoke-virtual {p0, v4, v3, v4, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertFlash(ILjava/lang/String;ZZ)V

    goto :goto_1

    :cond_3
    const-string v3, "5"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    invoke-virtual {p0, v4, v3, v4, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertFlash(ILjava/lang/String;ZZ)V

    goto :goto_1

    :cond_4
    if-eqz v1, :cond_6

    invoke-virtual {v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isFlashShowing()Z

    move-result v0

    if-nez v0, :cond_6

    const/16 v0, 0x8

    invoke-virtual {p0, v0, v2, v4, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertFlash(ILjava/lang/String;ZZ)V

    goto :goto_1

    :cond_5
    :goto_0
    invoke-virtual {p0, v4, v2, v4, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertFlash(ILjava/lang/String;ZZ)V

    :cond_6
    :goto_1
    return-void
.end method

.method private reConfigTipOfHdr(Z)V
    .locals 3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_3

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "on"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_2

    const-string v1, "normal"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    const-string v1, "live"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    invoke-direct {p0, v2, v0, v2, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertHDR(IZZZ)V

    goto :goto_1

    :cond_1
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isHDRShowing()Z

    move-result v0

    if-eqz v0, :cond_3

    const/16 v0, 0x8

    invoke-direct {p0, v0, v2, v2, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertHDR(IZZZ)V

    goto :goto_1

    :cond_2
    :goto_0
    invoke-direct {p0, v2, v2, v2, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertHDR(IZZZ)V

    :cond_3
    :goto_1
    return-void
.end method

.method private reConfigTipOfMusicHint(Z)V
    .locals 2

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v0, 0xae

    if-eq p1, v0, :cond_0

    const/16 v0, 0xb7

    if-eq p1, v0, :cond_0

    const/16 p1, 0x8

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertTopMusicHint(ILjava/lang/String;)V

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p1

    if-eqz p1, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->getCurrentLiveMusic()[Ljava/lang/String;

    move-result-object p1

    const/4 v0, 0x1

    aget-object v1, p1, v0

    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_2

    const/4 v1, 0x0

    aget-object p1, p1, v0

    invoke-direct {p0, v1, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertTopMusicHint(ILjava/lang/String;)V

    :cond_2
    return-void
.end method

.method private reConfigTipOfSubtitle()V
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningSubtitle()Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningSubtitle;->isSwitchOn(I)Z

    move-result v0

    const v1, 0x7f100538

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSubtitleHint(II)V

    goto :goto_0

    :cond_0
    const/16 v0, 0x8

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSubtitleHint(II)V

    :goto_0
    return-void
.end method

.method private reConfigTipOfSuperNightSe()V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isSuperNightOn()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v0, 0x0

    goto :goto_0

    :cond_1
    const/16 v0, 0x8

    :goto_0
    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSuperNightSeTip(I)V

    return-void
.end method

.method private resetImages()V
    .locals 11

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v6

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v0

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/data/data/global/DataItemGlobal;->isNormalIntent()Z

    move-result v2

    invoke-static {v1, v0, v2}, Lcom/android/camera/data/data/config/SupportedConfigFactory;->getSupportedTopConfigs(IIZ)Lcom/android/camera/data/data/config/SupportedConfigs;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    const/4 v7, 0x0

    move v8, v7

    :goto_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-ge v8, v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v8}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    move-object v9, v0

    check-cast v9, Landroid/widget/ImageView;

    const/4 v0, 0x1

    invoke-virtual {v9, v0}, Landroid/widget/ImageView;->setEnabled(Z)V

    const/4 v0, 0x0

    invoke-virtual {v9, v0}, Landroid/widget/ImageView;->setColorFilter(Landroid/graphics/ColorFilter;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {v0, v8}, Lcom/android/camera/data/data/config/SupportedConfigs;->getConfigItem(I)Lcom/android/camera/data/data/config/TopConfigItem;

    move-result-object v10

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/4 v5, 0x0

    move-object v0, p0

    move-object v1, v10

    move-object v2, v9

    move-object v4, v6

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTopImageResource(Lcom/android/camera/data/data/config/TopConfigItem;Landroid/widget/ImageView;ILcom/android/camera/data/data/config/DataItemConfig;Z)Z

    move-result v0

    invoke-virtual {v9}, Landroid/widget/ImageView;->getTag()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/data/data/config/TopConfigItem;

    if-eqz v1, :cond_0

    iget v1, v1, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    iget v2, v10, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    if-ne v1, v2, :cond_0

    invoke-virtual {v9, v10}, Landroid/widget/ImageView;->setTag(Ljava/lang/Object;)V

    goto :goto_1

    :cond_0
    invoke-virtual {v9, v10}, Landroid/widget/ImageView;->setTag(Ljava/lang/Object;)V

    invoke-virtual {v9}, Landroid/widget/ImageView;->clearAnimation()V

    invoke-virtual {v9, v7}, Landroid/widget/ImageView;->setVisibility(I)V

    if-eqz v0, :cond_1

    const/4 v0, 0x0

    invoke-static {v9, v0}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    invoke-static {v9}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object v0

    const/high16 v1, 0x3f800000    # 1.0f

    invoke-virtual {v0, v1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object v0

    const-wide/16 v1, 0x96

    invoke-virtual {v0, v1, v2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object v0

    invoke-virtual {v0, v1, v2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setStartDelay(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object v0

    invoke-virtual {v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_1

    :cond_1
    const/4 v0, 0x4

    invoke-virtual {v9, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    :goto_1
    add-int/lit8 v8, v8, 0x1

    goto :goto_0

    :cond_2
    return-void
.end method

.method private setTopImageResource(Lcom/android/camera/data/data/config/TopConfigItem;Landroid/widget/ImageView;ILcom/android/camera/data/data/config/DataItemConfig;Z)Z
    .locals 19
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "ResourceType"
        }
    .end annotation

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move-object/from16 v2, p2

    move/from16 v3, p3

    iget v4, v1, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    const/16 v5, 0xc1

    const-string v6, "normal"

    const v7, 0x7f100092

    const-string v8, "on"

    const/4 v9, 0x0

    const/4 v10, 0x0

    const/4 v11, 0x1

    sparse-switch v4, :sswitch_data_0

    goto/16 :goto_22

    :sswitch_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigVideoQuality()Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;

    move-result-object v3

    iget v4, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v3, v4}, Lcom/android/camera/data/data/config/ComponentConfigVideoQuality;->getComponentValue(I)Ljava/lang/String;

    move-result-object v3

    iget v4, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v4}, Lcom/android/camera/CameraSettings;->isVideoQuality8KOpen(I)Z

    move-result v4

    if-nez v4, :cond_1

    const-string v4, "3001"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_1

    const-string v4, "3001,24"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_0

    goto :goto_0

    :cond_0
    move v3, v10

    goto :goto_1

    :cond_1
    :goto_0
    move v3, v11

    :goto_1
    if-eqz v3, :cond_2

    const v4, 0x7f10001d

    goto :goto_2

    :cond_2
    const v4, 0x7f10001c

    :goto_2
    move v7, v4

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mVideo8KResource:[I

    aget v4, v4, v10

    goto/16 :goto_10

    :sswitch_1
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isMacroModeEnabled(I)Z

    move-result v3

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSuperMacroResources:[I

    aget v6, v4, v10

    aget v4, v4, v11

    :goto_3
    move-object v8, v9

    :goto_4
    move v12, v11

    :goto_5
    move/from16 v17, v6

    move v6, v3

    goto/16 :goto_17

    :sswitch_2
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isAutoZoomEnabled(I)Z

    move-result v4

    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isAutoZoomEnabled(I)Z

    move-result v3

    if-eqz v3, :cond_3

    iget-object v3, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAutoZoomResources:[I

    aget v3, v3, v11

    goto/16 :goto_d

    :cond_3
    iget-object v3, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAutoZoomResources:[I

    aget v3, v3, v10

    goto/16 :goto_d

    :sswitch_3
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isCinematicAspectRatioEnabled(I)Z

    move-result v4

    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isCinematicAspectRatioEnabled(I)Z

    move-result v6

    if-eqz v6, :cond_4

    iget-object v6, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCinematicRatioResources:[I

    aget v6, v6, v11

    goto :goto_6

    :cond_4
    iget-object v6, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCinematicRatioResources:[I

    aget v6, v6, v10

    :goto_6
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->getAiSceneOpen(I)Z

    move-result v3

    if-eqz v3, :cond_5

    const v3, 0x7f10005e

    goto :goto_7

    :cond_5
    const v3, 0x7f10005d

    :goto_7
    move v7, v3

    move v3, v6

    move-object v8, v9

    move v12, v11

    move v6, v4

    move v4, v10

    goto/16 :goto_26

    :sswitch_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->getCurrentLiveMusic()[Ljava/lang/String;

    move-result-object v3

    const v4, 0x7f080330

    const v6, 0x7f080331

    aget-object v3, v3, v11

    invoke-virtual {v3}, Ljava/lang/String;->isEmpty()Z

    move-result v3

    if-nez v3, :cond_6

    move v3, v4

    move v4, v6

    move-object v8, v9

    move v7, v10

    goto/16 :goto_13

    :cond_6
    move v3, v4

    move v4, v6

    goto/16 :goto_1f

    :sswitch_5
    invoke-static {}, Lcom/android/camera/CameraSettings;->isVideoBokehOn()Z

    move-result v3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "setTopImageResource: VIDEO_BOKEH isSwitchOn = "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    const-string v6, "FragmentTopConfig"

    invoke-static {v6, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz v3, :cond_7

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mVideoBokehResource:[I

    aget v4, v4, v11

    goto :goto_8

    :cond_7
    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mVideoBokehResource:[I

    aget v4, v4, v10

    :goto_8
    if-eqz v3, :cond_8

    const v6, 0x7f100495

    goto :goto_9

    :cond_8
    const v6, 0x7f100494

    :goto_9
    move v7, v6

    goto/16 :goto_10

    :sswitch_6
    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v3

    if-eqz v3, :cond_9

    const v3, 0x7f0801bd

    const v4, 0x7f0801be

    const v7, 0x7f1004dd

    move-object v8, v9

    move v6, v10

    goto/16 :goto_25

    :cond_9
    const v3, 0x7f0801bb

    const v7, 0x7f100324

    goto/16 :goto_18

    :sswitch_7
    const/16 v4, 0xae

    if-eq v3, v4, :cond_a

    iget v3, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v3, v9}, Lcom/android/camera/CameraSettings;->isFaceBeautyOn(ILcom/android/camera/fragment/beauty/BeautyValues;)Z

    move-result v3

    goto :goto_a

    :cond_a
    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveBeautyOpen()Z

    move-result v3

    :goto_a
    const v4, 0x7f080192

    const v7, 0x7f100022

    goto/16 :goto_10

    :sswitch_8
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningColorEnhance()Lcom/android/camera/data/data/runing/ComponentRunningColorEnhance;

    move-result-object v3

    iget v4, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v3, v4}, Lcom/android/camera/data/data/runing/ComponentRunningColorEnhance;->isEnabled(I)Z

    move-result v4

    invoke-virtual {v3, v4}, Lcom/android/camera/data/data/runing/ComponentRunningColorEnhance;->getResIcon(Z)I

    move-result v4

    iget v6, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v3, v6}, Lcom/android/camera/data/data/runing/ComponentRunningColorEnhance;->isEnabled(I)Z

    move-result v3

    move v6, v3

    move v3, v4

    move-object v8, v9

    move v4, v10

    move v7, v4

    goto/16 :goto_25

    :sswitch_9
    invoke-direct/range {p0 .. p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getSettingResources()I

    move-result v3

    const v7, 0x7f100068

    goto/16 :goto_18

    :sswitch_a
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isDocumentModeOn(I)Z

    move-result v4

    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isDocumentModeOn(I)Z

    move-result v3

    if-eqz v3, :cond_b

    iget-object v3, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDocumentResources:[I

    aget v3, v3, v11

    goto :goto_b

    :cond_b
    iget-object v3, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDocumentResources:[I

    aget v3, v3, v10

    :goto_b
    const v7, 0x7f1004ce

    goto :goto_d

    :sswitch_b
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isSubtitleEnabled(I)Z

    move-result v4

    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isSubtitleEnabled(I)Z

    move-result v3

    if-eqz v3, :cond_c

    iget-object v3, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAutoZoomResources:[I

    aget v3, v3, v11

    goto :goto_c

    :cond_c
    iget-object v3, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAutoZoomResources:[I

    aget v3, v3, v10

    :goto_c
    const v7, 0x7f100538

    if-nez p5, :cond_d

    invoke-direct/range {p0 .. p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reConfigTipOfSubtitle()V

    :cond_d
    :goto_d
    move v6, v4

    goto :goto_11

    :sswitch_c
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isSuperEISEnabled(I)Z

    move-result v4

    if-eqz v4, :cond_e

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSuperEISResources:[I

    aget v4, v4, v11

    goto :goto_e

    :cond_e
    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSuperEISResources:[I

    aget v4, v4, v10

    :goto_e
    const v7, 0x7f100581

    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->isSuperEISEnabled(I)Z

    move-result v3

    goto :goto_10

    :sswitch_d
    iget v3, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v4, 0xcc

    if-ne v3, v4, :cond_f

    const v3, 0x7f0801ff

    goto/16 :goto_20

    :cond_f
    const v3, 0x7f08018f

    goto/16 :goto_20

    :sswitch_e
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelPortraitFrontOn()Z

    move-result v3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelPortraitFrontOn()Z

    move-result v4

    if-eqz v4, :cond_10

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPortraitResources:[I

    aget v4, v4, v11

    goto :goto_f

    :cond_10
    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPortraitResources:[I

    aget v4, v4, v10

    :goto_f
    const v7, 0x7f1005a8

    :goto_10
    move v6, v3

    move v3, v4

    :goto_11
    move-object v8, v9

    move v4, v10

    goto/16 :goto_25

    :sswitch_f
    invoke-virtual/range {p4 .. p4}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigMeter()Lcom/android/camera/data/data/config/ComponentConfigMeter;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v6

    if-nez v6, :cond_1e

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigMeter;->getValueSelectedDrawableIgnoreClose(I)I

    move-result v6

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigMeter;->getValueSelectedShadowDrawableId(I)I

    move-result v7

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigMeter;->getValueSelectedStringIdIgnoreClose(I)I

    move-result v3

    move v4, v7

    move-object v8, v9

    move v12, v11

    move v7, v3

    move v3, v6

    move v6, v10

    goto/16 :goto_26

    :sswitch_10
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningShine()Lcom/android/camera/data/data/runing/ComponentRunningShine;

    move-result-object v4

    const v7, 0x7f100040

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->getTopConfigEntryRes(I)I

    move-result v6

    invoke-virtual {v4, v6}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->getTopConfigEntryShadowRes(I)I

    move-result v8

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->isTopFilterEntry()Z

    move-result v12

    xor-int/2addr v12, v11

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->determineStatus(I)Z

    move-result v3

    move v4, v8

    move-object v8, v9

    goto/16 :goto_5

    :sswitch_11
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v3

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPhotographyIconResources:[I

    aget v6, v4, v10

    aget v4, v4, v11

    if-eqz v3, :cond_11

    iget-object v7, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPhotographyTipString:[Ljava/lang/String;

    aget-object v7, v7, v11

    goto :goto_12

    :cond_11
    iget-object v7, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPhotographyTipString:[Ljava/lang/String;

    aget-object v7, v7, v10

    :goto_12
    move-object v8, v7

    move v7, v10

    goto/16 :goto_4

    :sswitch_12
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v3

    const-string v4, "pref_ultra_wide_bokeh_enabled"

    invoke-virtual {v3, v4}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result v3

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraWideBokehResources:[I

    aget v6, v4, v10

    aget v4, v4, v11

    if-eqz v3, :cond_12

    const v7, 0x7f100034

    goto/16 :goto_3

    :cond_12
    const v7, 0x7f100033

    goto/16 :goto_3

    :sswitch_13
    invoke-static {}, Lcom/android/camera/CameraSettings;->isLiveShotOn()Z

    move-result v3

    iget-object v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotResource:[I

    aget v6, v4, v10

    aget v4, v4, v11

    if-eqz v3, :cond_13

    const v7, 0x7f10002c

    goto/16 :goto_3

    :cond_13
    const v7, 0x7f10002b

    goto/16 :goto_3

    :sswitch_14
    invoke-virtual/range {p4 .. p4}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigUltraWide()Lcom/android/camera/data/data/config/ComponentConfigUltraWide;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v6

    if-nez v6, :cond_1e

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigUltraWide;->getValueSelectedDrawableIgnoreClose(I)I

    move-result v6

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigUltraWide;->getValueSelectedStringIdIgnoreClose(I)I

    move-result v7

    move v3, v6

    move-object v8, v9

    move v4, v10

    :goto_13
    move v6, v11

    move v12, v6

    goto/16 :goto_26

    :sswitch_15
    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->getAiSceneOpen(I)Z

    move-result v4

    iget-object v6, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAiSceneResources:[I

    aget v7, v6, v10

    aget v6, v6, v11

    invoke-static/range {p3 .. p3}, Lcom/android/camera/CameraSettings;->getAiSceneOpen(I)Z

    move-result v3

    if-eqz v3, :cond_14

    const v3, 0x7f10001f

    goto :goto_14

    :cond_14
    const v3, 0x7f10001e

    :goto_14
    invoke-direct {v0, v10}, Lcom/android/camera/fragment/top/FragmentTopConfig;->configBottomPopupTips(Z)V

    move-object v8, v9

    move v12, v11

    move/from16 v17, v7

    move v7, v3

    move/from16 v3, v17

    move/from16 v18, v6

    move v6, v4

    move/from16 v4, v18

    goto/16 :goto_26

    :sswitch_16
    invoke-virtual/range {p4 .. p4}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentBokeh()Lcom/android/camera/data/data/config/ComponentConfigBokeh;

    move-result-object v4

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v8, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_15

    const v4, 0x7f0802f0

    goto :goto_15

    :cond_15
    const v4, 0x7f0802ef

    :goto_15
    invoke-virtual {v8, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_16

    const v6, 0x7f100028

    goto :goto_16

    :cond_16
    const v6, 0x7f100027

    :goto_16
    move v7, v6

    invoke-virtual {v8, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    goto/16 :goto_10

    :sswitch_17
    iget-object v3, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMoreResources:[I

    aget v4, v3, v10

    aget v3, v3, v11

    const v7, 0x7f10005f

    move-object v8, v9

    move v6, v10

    move v12, v11

    move/from16 v17, v4

    move v4, v3

    :goto_17
    move/from16 v3, v17

    goto/16 :goto_26

    :sswitch_18
    invoke-direct/range {p0 .. p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getPortraitResources()I

    move-result v3

    const v7, 0x7f100063

    :goto_18
    move-object v8, v9

    move v4, v10

    move v6, v4

    goto/16 :goto_25

    :sswitch_19
    invoke-virtual/range {p4 .. p4}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v7

    if-nez v7, :cond_1e

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v7

    invoke-virtual {v7, v5}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v7

    check-cast v7, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v8, v12}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-nez v8, :cond_18

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v6, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_18

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getComponentValue(I)Ljava/lang/String;

    move-result-object v6

    const-string v8, "auto"

    invoke-virtual {v8, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_17

    if-eqz v7, :cond_17

    invoke-interface {v7}, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;->getAutoHDRTargetState()Z

    move-result v6

    if-eqz v6, :cond_17

    goto :goto_19

    :cond_17
    move v6, v10

    goto :goto_1a

    :cond_18
    :goto_19
    move v6, v11

    :goto_1a
    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getValueSelectedDrawableIgnoreClose(I)I

    move-result v7

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getValueSelectedShadowDrawable(I)I

    move-result v8

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigHdr;->getValueSelectedStringIdIgnoreClose(I)I

    move-result v3

    goto/16 :goto_1e

    :sswitch_1a
    invoke-virtual/range {p4 .. p4}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/ComponentData;->isEmpty()Z

    move-result v6

    if-nez v6, :cond_1c

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v6

    const-string v7, "1"

    invoke-virtual {v7, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_1a

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v6

    const-string v7, "2"

    invoke-virtual {v7, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_1a

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v6

    const-string v7, "101"

    invoke-virtual {v7, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_1a

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object v6

    const-string v7, "5"

    invoke-virtual {v7, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_19

    goto :goto_1b

    :cond_19
    move v6, v10

    goto :goto_1c

    :cond_1a
    :goto_1b
    move v6, v11

    :goto_1c
    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getValueSelectedDrawableIgnoreClose(I)I

    move-result v7

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getValueSelectedShadowDrawable(I)I

    move-result v8

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getValueSelectedStringIdIgnoreClose(I)I

    move-result v3

    if-nez p5, :cond_1b

    invoke-direct {v0, v11}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reConfigTipOfFlash(Z)V

    goto :goto_1d

    :cond_1b
    if-eqz p5, :cond_1d

    iget v12, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v13, 0xa7

    if-ne v12, v13, :cond_1d

    invoke-direct {v0, v11}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reConfigTipOfFlash(Z)V

    goto :goto_1d

    :cond_1c
    move v3, v10

    move v6, v3

    move v7, v6

    move v8, v7

    :cond_1d
    :goto_1d
    iget v12, v0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v4, v12}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->isDisabled(I)Z

    move-result v4

    xor-int/2addr v4, v11

    iput-boolean v4, v1, Lcom/android/camera/data/data/config/TopConfigItem;->enable:Z

    :goto_1e
    move v4, v8

    move-object v8, v9

    move v12, v11

    move/from16 v17, v7

    move v7, v3

    goto/16 :goto_17

    :sswitch_1b
    const v3, 0x7f0802aa

    goto/16 :goto_20

    :sswitch_1c
    return v10

    :sswitch_1d
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/data/data/runing/DataItemRunning;->getAi108Running()Z

    move-result v3

    const v4, 0x7f080312

    const v6, 0x7f080313

    move-object v8, v9

    move v7, v10

    goto/16 :goto_21

    :sswitch_1e
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAiAudio()Lcom/android/camera/data/data/runing/ComponentRunningAiAudio;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/ComponentRunningAiAudio;->isEmpty()Z

    move-result v7

    if-nez v7, :cond_1e

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/runing/ComponentRunningAiAudio;->getValueSelectedDrawableIgnoreClose(I)I

    move-result v7

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/runing/ComponentRunningAiAudio;->getValueSelectedStringIdIgnoreClose(I)I

    move-result v8

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object v3

    invoke-static {v6, v3}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v3

    xor-int/2addr v3, v11

    move v6, v3

    move v3, v7

    move v7, v8

    goto/16 :goto_11

    :sswitch_1f
    const v3, 0x7f080163

    const v4, 0x7f080164

    :goto_1f
    move-object v8, v9

    move v6, v10

    goto :goto_24

    :sswitch_20
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningEisPro()Lcom/android/camera/data/data/runing/ComponentRunningEisPro;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/ComponentRunningEisPro;->isEmpty()Z

    move-result v6

    if-nez v6, :cond_1e

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/runing/ComponentRunningEisPro;->getValueSelectedDrawableIgnoreClose(I)I

    move-result v6

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/runing/ComponentRunningEisPro;->getValueSelectedStringIdIgnoreClose(I)I

    move-result v7

    invoke-virtual {v4, v3}, Lcom/android/camera/data/data/runing/ComponentRunningEisPro;->getComponentValue(I)Ljava/lang/String;

    move-result-object v3

    const-string v4, "off"

    invoke-static {v4, v3}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v3

    xor-int/2addr v3, v11

    move-object v8, v9

    move v4, v10

    goto/16 :goto_4

    :sswitch_21
    const v3, 0x7f0801c5

    :goto_20
    move-object v8, v9

    move v4, v10

    goto :goto_23

    :sswitch_22
    invoke-static {}, Lcom/android/camera/CameraSettings;->isGifOn()Z

    move-result v3

    const v7, 0x7f100272

    iget v4, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mGifResource:I

    const v6, 0x7f08033d

    move-object v8, v9

    :goto_21
    move v12, v11

    move/from16 v17, v6

    move v6, v3

    move v3, v4

    move/from16 v4, v17

    goto :goto_26

    :cond_1e
    :goto_22
    move-object v8, v9

    move v3, v10

    move v4, v3

    :goto_23
    move v6, v4

    :goto_24
    move v7, v6

    :goto_25
    move v12, v11

    :goto_26
    if-lez v3, :cond_2a

    invoke-virtual/range {p0 .. p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v13

    invoke-virtual {v13, v3}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v3}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v13

    invoke-direct {v0, v1, v2, v13}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getInitialMargin(Lcom/android/camera/data/data/config/TopConfigItem;Landroid/widget/ImageView;I)I

    move-result v13

    iput v13, v1, Lcom/android/camera/data/data/config/TopConfigItem;->margin:I

    invoke-virtual/range {p2 .. p2}, Landroid/widget/ImageView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v13

    check-cast v13, Landroid/widget/FrameLayout$LayoutParams;

    iget v14, v1, Lcom/android/camera/data/data/config/TopConfigItem;->margin:I

    if-lez v14, :cond_20

    iget v15, v13, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    const v16, 0x800003

    or-int v15, v15, v16

    iput v15, v13, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    iget-boolean v15, v0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsRTL:Z

    if-eqz v15, :cond_1f

    iput v14, v13, Landroid/widget/FrameLayout$LayoutParams;->rightMargin:I

    goto :goto_27

    :cond_1f
    iput v14, v13, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    goto :goto_27

    :cond_20
    invoke-virtual {v13, v10}, Landroid/widget/FrameLayout$LayoutParams;->setMarginStart(I)V

    :goto_27
    iget v14, v13, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    or-int/lit8 v14, v14, 0x10

    iput v14, v13, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    invoke-virtual {v2, v13}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget v13, v1, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    const/16 v14, 0xb1

    if-ne v13, v14, :cond_21

    invoke-virtual {v2, v9}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_28

    :cond_21
    invoke-virtual {v2, v3}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    :goto_28
    if-lez v4, :cond_22

    invoke-virtual {v2, v4}, Landroid/widget/ImageView;->setBackgroundResource(I)V

    goto :goto_29

    :cond_22
    invoke-virtual {v2, v9}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :goto_29
    if-eqz v12, :cond_24

    if-eqz v6, :cond_23

    invoke-static {}, Lcom/android/camera/customization/TintColor;->tintColor()I

    move-result v3

    goto :goto_2a

    :cond_23
    const/4 v3, -0x1

    :goto_2a
    invoke-virtual {v2, v3}, Landroid/widget/ImageView;->setColorFilter(I)V

    goto :goto_2b

    :cond_24
    invoke-virtual {v2, v10}, Landroid/widget/ImageView;->setColorFilter(I)V

    :goto_2b
    iget v3, v1, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    if-ne v3, v5, :cond_26

    iget-boolean v1, v1, Lcom/android/camera/data/data/config/TopConfigItem;->enable:Z

    const v3, 0x3ecccccd    # 0.4f

    if-nez v1, :cond_25

    invoke-virtual {v2, v3}, Landroid/widget/ImageView;->setAlpha(F)V

    goto :goto_2c

    :cond_25
    invoke-virtual/range {p2 .. p2}, Landroid/widget/ImageView;->getAlpha()F

    move-result v1

    cmpl-float v1, v1, v3

    if-nez v1, :cond_26

    const/high16 v1, 0x3f800000    # 1.0f

    invoke-virtual {v2, v1}, Landroid/widget/ImageView;->setAlpha(F)V

    :cond_26
    :goto_2c
    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isLandscapeMode()Z

    move-result v1

    if-eqz v1, :cond_27

    const/high16 v1, 0x42b40000    # 90.0f

    invoke-virtual {v2, v1}, Landroid/widget/ImageView;->setRotation(F)V

    goto :goto_2d

    :cond_27
    iget v1, v0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    int-to-float v1, v1

    invoke-virtual {v2, v1}, Landroid/widget/ImageView;->setRotation(F)V

    :goto_2d
    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result v1

    if-nez v1, :cond_28

    invoke-static {}, Lcom/android/camera/Util;->isSetContentDesc()Z

    move-result v1

    if-eqz v1, :cond_2a

    :cond_28
    if-lez v7, :cond_29

    invoke-virtual {v0, v7}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, v0}, Landroid/widget/ImageView;->setContentDescription(Ljava/lang/CharSequence;)V

    goto :goto_2e

    :cond_29
    invoke-static {v8}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_2a

    invoke-virtual {v2, v8}, Landroid/widget/ImageView;->setContentDescription(Ljava/lang/CharSequence;)V

    :cond_2a
    :goto_2e
    return v11

    :sswitch_data_0
    .sparse-switch
        0xa2 -> :sswitch_22
        0xa4 -> :sswitch_21
        0xa5 -> :sswitch_20
        0xa6 -> :sswitch_1f
        0xa8 -> :sswitch_1e
        0xab -> :sswitch_1d
        0xb0 -> :sswitch_1c
        0xb1 -> :sswitch_1b
        0xc1 -> :sswitch_1a
        0xc2 -> :sswitch_19
        0xc3 -> :sswitch_18
        0xc5 -> :sswitch_17
        0xc8 -> :sswitch_16
        0xc9 -> :sswitch_15
        0xcd -> :sswitch_14
        0xce -> :sswitch_13
        0xcf -> :sswitch_12
        0xd1 -> :sswitch_11
        0xd4 -> :sswitch_10
        0xd6 -> :sswitch_f
        0xd7 -> :sswitch_e
        0xd9 -> :sswitch_d
        0xda -> :sswitch_c
        0xdc -> :sswitch_b
        0xdd -> :sswitch_a
        0xe1 -> :sswitch_9
        0xe3 -> :sswitch_8
        0xef -> :sswitch_7
        0xf2 -> :sswitch_6
        0xf3 -> :sswitch_5
        0xf5 -> :sswitch_4
        0xfb -> :sswitch_3
        0xfd -> :sswitch_2
        0xff -> :sswitch_1
        0x100 -> :sswitch_0
    .end sparse-switch
.end method

.method private setTopTipMarin(Landroid/view/View;)V
    .locals 10

    invoke-static {}, Lcom/android/camera/Util;->getTopHeight()I

    move-result v0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f0700be

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v1

    int-to-float v2, v1

    const v3, 0x3fa9db23    # 1.327f

    mul-float/2addr v2, v3

    invoke-static {v2}, Ljava/lang/Math;->round(F)I

    move-result v2

    sub-int v3, v2, v1

    iget v4, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v5, 0xa5

    if-ne v4, v5, :cond_0

    int-to-long v4, v0

    sget v0, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-double v6, v0

    const-wide v8, 0x3f8e4f765fd8adacL    # 0.0148

    mul-double/2addr v6, v8

    invoke-static {v6, v7}, Ljava/lang/Math;->round(D)J

    move-result-wide v6

    :goto_0
    add-long/2addr v4, v6

    long-to-int v0, v4

    goto :goto_1

    :cond_0
    iget v4, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisplayRectTopMargin:I

    if-nez v4, :cond_1

    int-to-long v4, v0

    sget v0, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-double v6, v0

    const-wide v8, 0x3fad63886594af4fL    # 0.0574

    mul-double/2addr v6, v8

    invoke-static {v6, v7}, Ljava/lang/Math;->round(D)J

    move-result-wide v6

    goto :goto_0

    :cond_1
    int-to-long v4, v0

    sget v0, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-double v6, v0

    const-wide v8, 0x3fb6c226809d4952L    # 0.0889

    mul-double/2addr v6, v8

    invoke-static {v6, v7}, Ljava/lang/Math;->round(D)J

    move-result-wide v6

    goto :goto_0

    :goto_1
    sub-int/2addr v0, v3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "showDelayNumber: topMargin = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, ", topHeight = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/android/camera/Util;->getTopHeight()I

    move-result v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, ", fontHeight = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", viewHeight = "

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", offset = "

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "FragmentTopConfig"

    invoke-static {v2, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    check-cast v1, Landroid/view/ViewGroup$MarginLayoutParams;

    iput v0, v1, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    iget p0, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    if-lez p0, :cond_2

    int-to-float p0, p0

    invoke-static {p1, p0}, Landroidx/core/view/ViewCompat;->setRotation(Landroid/view/View;F)V

    :cond_2
    return-void
.end method

.method private showExtraMenu()V
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->hideSwitchTip()V

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->hideAlert()V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->initExtraMenu()V

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->animatorExtraMenu(Z)V

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->notifyExtraMenuVisibilityChange(Z)V

    return-void
.end method

.method private showExtraTimerBurstMenu()V
    .locals 1

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->switchExtraTimerBurstMenu()V

    return-void
.end method

.method private showTips(Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;Z)V
    .locals 4

    const-string p2, "ultra_pixel"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentUltraPixel()Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getUltraPixelOpenTip()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, p2, v1, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSwitchTip(Ljava/lang/String;ILjava/lang/String;)V

    :cond_0
    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckModuleUltraPixel()V

    const-string p2, "video_beautify"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckVideoBeautify()V

    :cond_1
    const-string p2, "ai_watermark"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    const/4 v2, 0x1

    if-eqz v0, :cond_2

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1, v2}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckAIWatermark(Z)V

    :cond_2
    const-string p2, "super_eis"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckSuperEIS()V

    :cond_3
    const-string p2, "super_eis_pro"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_4

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckSuperEISPro()V

    :cond_4
    const-string p2, "live_shot"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_5

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckLiveShot()V

    :cond_5
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    if-nez p2, :cond_6

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckColorEnhance()V

    :cond_6
    const-string p2, "ultra_wide_bokeh"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_7

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckUltraWideBokeh()V

    :cond_7
    const-string p2, "ai_audio"

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTipsState(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_8

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckAiAudio()V

    :cond_8
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    if-nez p2, :cond_9

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckEyeLight()V

    :cond_9
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p2

    const/16 v0, 0xa1

    invoke-virtual {p2, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p2

    check-cast p2, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v3, 0xa2

    if-ne v0, v3, :cond_a

    if-eqz p2, :cond_a

    invoke-interface {p2}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isRecording()Z

    move-result p2

    if-eqz p2, :cond_a

    goto :goto_0

    :cond_a
    move v2, v1

    :goto_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    if-nez p2, :cond_b

    if-nez v2, :cond_b

    const-string p2, "macro"

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckMacroMode()V

    :cond_b
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    if-nez p2, :cond_c

    const-string p2, "timer_burst"

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTipsState(Ljava/lang/String;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckTimerBurst()V

    :cond_c
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    if-nez p2, :cond_d

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckFrontBokenTip()V

    :cond_d
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    if-nez p2, :cond_e

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckHandGesture()V

    :cond_e
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    if-nez p2, :cond_f

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckSubtitleMode()V

    :cond_f
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p2

    const/16 v0, 0xc3

    invoke-virtual {p2, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p2

    check-cast p2, Lcom/android/camera/protocol/ModeProtocol$CameraModuleSpecial;

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p0

    if-nez p0, :cond_11

    if-eqz p2, :cond_10

    invoke-interface {p2}, Lcom/android/camera/protocol/ModeProtocol$CameraModuleSpecial;->isStartCountCapture()Z

    move-result p0

    if-nez p0, :cond_11

    :cond_10
    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckTilt()V

    :cond_11
    return-void
.end method

.method private switchExtraTimerBurstMenu()V
    .locals 14

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    xor-int/lit8 v0, v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    const-wide/16 v1, 0x12c

    const/16 v3, 0x8

    const/4 v4, 0x0

    if-eqz v0, :cond_1

    const/4 v0, 0x0

    const-string v5, "goto_timer_burst_menu"

    invoke-static {v5, v0, v0}, Lcom/android/camera/statistic/MistatsWrapper;->commonKeyTriggerEvent(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0, v3}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v4}, Landroid/widget/LinearLayout;->setVisibility(I)V

    new-instance v0, Landroid/view/animation/ScaleAnimation;

    const v6, 0x3f666666    # 0.9f

    const/high16 v7, 0x3f800000    # 1.0f

    const v8, 0x3f666666    # 0.9f

    const/high16 v9, 0x3f800000    # 1.0f

    const/4 v10, 0x1

    const/high16 v11, 0x3f000000    # 0.5f

    const/4 v12, 0x1

    const/high16 v13, 0x3f800000    # 1.0f

    move-object v5, v0

    invoke-direct/range {v5 .. v13}, Landroid/view/animation/ScaleAnimation;-><init>(FFFFIFIF)V

    invoke-virtual {v0, v1, v2}, Landroid/view/animation/ScaleAnimation;->setDuration(J)V

    new-instance v1, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v1}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/view/animation/ScaleAnimation;->setInterpolator(Landroid/view/animation/Interpolator;)V

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    invoke-virtual {v1, v0}, Landroid/widget/LinearLayout;->startAnimation(Landroid/view/animation/Animation;)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstInterval()I

    move-result v4

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v1, 0xa7

    if-ne v0, v1, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTvShotInterval:Landroid/widget/TextView;

    const v1, 0x7f100597

    invoke-virtual {p0, v1}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTvShotInterval:Landroid/widget/TextView;

    const v1, 0x7f100598

    invoke-virtual {p0, v1}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :goto_0
    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCustomSeekBarInterval:Lcom/android/camera/timerburst/CustomSeekBar;

    sget-object v3, Lcom/android/camera/timerburst/TimerBurstController;->timeInterval:[I

    const/4 v5, 0x1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getTimerBurstController()Lcom/android/camera/timerburst/TimerBurstController;

    move-result-object v7

    const-string v6, "S"

    invoke-virtual/range {v2 .. v7}, Lcom/android/camera/timerburst/CustomSeekBar;->initSeekBarConfig([IIILjava/lang/String;Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v0

    div-int/lit8 v3, v0, 0xa

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCustomSeekBarCount:Lcom/android/camera/timerburst/CustomSeekBar;

    sget-object v2, Lcom/android/camera/timerburst/TimerBurstController;->shotCount:[I

    const/16 v4, 0xa

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/extra/DataItemLive;->getTimerBurstController()Lcom/android/camera/timerburst/TimerBurstController;

    move-result-object v6

    const-string v5, ""

    invoke-virtual/range {v1 .. v6}, Lcom/android/camera/timerburst/CustomSeekBar;->initSeekBarConfig([IIILjava/lang/String;Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;)V

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {v0, v4}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->setVisibility(I)V

    new-instance v0, Landroid/view/animation/ScaleAnimation;

    const v5, 0x3f8851ec    # 1.065f

    const/high16 v6, 0x3f800000    # 1.0f

    const v7, 0x3f8851ec    # 1.065f

    const/high16 v8, 0x3f800000    # 1.0f

    const/4 v9, 0x1

    const/high16 v10, 0x3f000000    # 0.5f

    const/4 v11, 0x1

    const/high16 v12, 0x3f800000    # 1.0f

    move-object v4, v0

    invoke-direct/range {v4 .. v12}, Landroid/view/animation/ScaleAnimation;-><init>(FFFFIFIF)V

    invoke-virtual {v0, v1, v2}, Landroid/view/animation/ScaleAnimation;->setDuration(J)V

    new-instance v1, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v1}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/view/animation/ScaleAnimation;->setInterpolator(Landroid/view/animation/Interpolator;)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {p0, v0}, Landroid/view/ViewGroup;->startAnimation(Landroid/view/animation/Animation;)V

    :goto_1
    return-void
.end method


# virtual methods
.method public alertAiAudio(II)V
    .locals 2
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    invoke-static {}, Lcom/android/camera/Util;->isWiredHeadsetOn()Z

    move-result v0

    if-eqz v0, :cond_0

    const/16 p1, 0x8

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    if-nez v0, :cond_1

    return-void

    :cond_1
    iget-boolean p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    const/4 v1, 0x1

    xor-int/2addr p0, v1

    if-nez p0, :cond_2

    goto :goto_0

    :cond_2
    const/4 v1, 0x0

    :goto_0
    invoke-virtual {v0, p1, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAiAudio(IIZ)V

    return-void
.end method

.method public alertAiAudioBGHint(II)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAiAudioBGHint(II)V

    return-void
.end method

.method public alertAiAudioMutexToastIfNeed(Landroid/content/Context;I)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAiAudioMutexToastIfNeed(Landroid/content/Context;I)V

    return-void
.end method

.method public alertAiDetectTipHint(IIJ)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2, p3, p4}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertRecommendTipHint(IIJ)V

    return-void
.end method

.method public alertAiSceneSelector(I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertAiSceneSelector(I)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xaf

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz p0, :cond_1

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->reConfigQrCodeTip()Z

    :cond_1
    return-void
.end method

.method public alertFastmotionIndicator(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V
    .locals 6

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    move v1, p1

    move-object v2, p2

    move-object v3, p3

    move-object v4, p4

    move v5, p5

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertFastmotionIndicator(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V

    return-void
.end method

.method public alertFastmotionValue(Ljava/lang/String;Ljava/lang/String;)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertFastmotionTip(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public alertFlash(ILjava/lang/String;Z)V
    .locals 1

    const/4 v0, 0x1

    invoke-virtual {p0, p1, p2, p3, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertFlash(ILjava/lang/String;ZZ)V

    return-void
.end method

.method public alertFlash(ILjava/lang/String;ZZ)V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result v1

    if-nez v1, :cond_0

    return-void

    :cond_0
    if-eqz p4, :cond_2

    if-nez p1, :cond_1

    if-eqz p3, :cond_2

    const/16 p3, 0xc1

    invoke-virtual {p0, p3}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object p0

    if-eqz p0, :cond_2

    invoke-virtual {p0}, Landroid/widget/ImageView;->performClick()Z

    goto :goto_0

    :cond_1
    const/4 p3, 0x1

    invoke-virtual {p0, p3}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reverseExpandTopBar(Z)Z

    :cond_2
    :goto_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertFlash(ILjava/lang/String;)V

    return-void
.end method

.method public alertHDR(IZZ)V
    .locals 1

    const/4 v0, 0x1

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertHDR(IZZZ)V

    return-void
.end method

.method public alertHandGestureHint(I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertHandGestureHint(I)V

    return-void
.end method

.method public alertLightingTip(I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertLightingTip(I)V

    return-void
.end method

.method public alertLiveShotHint(II)V
    .locals 1

    const-string v0, "live_shot"

    invoke-virtual {p0, v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSwitchTip(Ljava/lang/String;II)V

    return-void
.end method

.method public alertMacroModeHint(II)V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result v1

    if-nez v1, :cond_0

    return-void

    :cond_0
    const/16 v1, 0xff

    invoke-virtual {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopConfig(I)Z

    move-result p0

    invoke-virtual {v0, p1, p2, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertMacroModeHint(IIZ)V

    return-void
.end method

.method public alertMimojiFaceDetect(ZI)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertMimojiFaceDetect(ZI)V

    return-void
.end method

.method public alertMoonModeSelector(IZ)V
    .locals 3

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningMoon()Lcom/android/camera/data/data/runing/ComponentRunningMoon;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-eqz p2, :cond_1

    const-string p2, "moon"

    goto :goto_0

    :cond_1
    const-string p2, "night"

    :goto_0
    invoke-virtual {v0, v1, p2}, Lcom/android/camera/data/data/ComponentData;->setComponentValue(ILjava/lang/String;)V

    const/4 p2, 0x1

    const/4 v0, 0x0

    if-nez p1, :cond_2

    move v1, p2

    goto :goto_1

    :cond_2
    move v1, v0

    :goto_1
    const/16 v2, 0xf6

    invoke-virtual {p0, v1, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSlideSwitchLayout(ZI)V

    iget p0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v1, 0xa3

    if-ne p0, v1, :cond_4

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v1, 0xc3

    invoke-virtual {p0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$CameraModuleSpecial;

    if-eqz p0, :cond_4

    if-eqz p1, :cond_3

    goto :goto_2

    :cond_3
    move p2, v0

    :goto_2
    invoke-interface {p0, p2}, Lcom/android/camera/protocol/ModeProtocol$CameraModuleSpecial;->showOrHideChip(Z)V

    :cond_4
    return-void
.end method

.method public alertMusicClose(Z)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertMusicClose(Z)V

    :cond_0
    return-void
.end method

.method public alertParameterDescriptionTip(I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    const/4 v0, 0x1

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertParameterDescriptionTip(IZ)V

    return-void
.end method

.method public alertParameterResetTip(ZII)V
    .locals 6
    .param p3    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-boolean v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    const/4 v2, 0x1

    xor-int/2addr v1, v2

    const/4 v3, 0x0

    invoke-static {v3}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v4

    iget v4, v4, Landroid/graphics/Rect;->top:I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v5, 0x7f070407

    invoke-virtual {p0, v5}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p0

    add-int/2addr v4, p0

    if-nez v1, :cond_1

    move v5, v2

    goto :goto_0

    :cond_1
    move v5, v3

    :goto_0
    move v1, p1

    move v2, p2

    move v3, p3

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertParameterResetTip(ZIIIZ)V

    return-void
.end method

.method public alertProColourHint(II)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertProColourHint(II)V

    return-void
.end method

.method public alertRecommendDescTip(Ljava/lang/String;II)V
    .locals 6

    const-wide/16 v4, 0xbb8

    move-object v0, p0

    move-object v1, p1

    move v2, p2

    move v3, p3

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertRecommendDescTip(Ljava/lang/String;IIJ)V

    return-void
.end method

.method public alertRecommendDescTip(Ljava/lang/String;IIJ)V
    .locals 6

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    move-object v1, p1

    move v2, p2

    move v3, p3

    move-wide v4, p4

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertRecommendDescTip(Ljava/lang/String;IIJ)V

    return-void
.end method

.method public alertSlideSwitchLayout(ZI)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertSlideSwitchLayout(ZI)V

    return-void
.end method

.method public alertSubtitleHint(II)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertSubtitleHint(II)V

    return-void
.end method

.method public alertSuperNightSeTip(I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertSuperNightSeTip(I)V

    return-void
.end method

.method public alertSwitchTip(Ljava/lang/String;II)V
    .locals 0

    invoke-virtual {p0, p3}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p0, p1, p2, p3}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSwitchTip(Ljava/lang/String;ILjava/lang/String;)V

    return-void
.end method

.method public alertSwitchTip(Ljava/lang/String;IIIJ)V
    .locals 7

    invoke-virtual {p0, p4}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object v4

    move-object v0, p0

    move-object v1, p1

    move v2, p2

    move v3, p3

    move-wide v5, p5

    invoke-virtual/range {v0 .. v6}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSwitchTip(Ljava/lang/String;IILjava/lang/String;J)V

    return-void
.end method

.method public alertSwitchTip(Ljava/lang/String;IILjava/lang/String;J)V
    .locals 7

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    move-object v1, p1

    move v2, p2

    move v3, p3

    move-object v4, p4

    move-wide v5, p5

    invoke-virtual/range {v0 .. v6}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertSwitchTip(Ljava/lang/String;IILjava/lang/String;J)V

    return-void
.end method

.method public alertSwitchTip(Ljava/lang/String;ILjava/lang/String;)V
    .locals 7

    const/4 v3, 0x1

    const-wide/16 v5, 0xbb8

    move-object v0, p0

    move-object v1, p1

    move v2, p2

    move-object v4, p3

    invoke-virtual/range {v0 .. v6}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertSwitchTip(Ljava/lang/String;IILjava/lang/String;J)V

    return-void
.end method

.method public alertTimerBurstHint(II)V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result v1

    if-nez v1, :cond_0

    return-void

    :cond_0
    const/16 v1, 0xaa

    invoke-virtual {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopConfig(I)Z

    move-result p0

    invoke-virtual {v0, p1, p2, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTimerBurstHint(IIZ)V

    return-void
.end method

.method public alertTopHint(II)V
    .locals 1
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTopHint(II)V

    return-void
.end method

.method public alertTopHint(IIJ)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2, p3, p4}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTopHint(IIJ)V

    return-void
.end method

.method public alertTopHint(ILjava/lang/String;)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTopHint(ILjava/lang/String;)V

    return-void
.end method

.method public alertTopHint(ILjava/lang/String;J)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2, p3, p4}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertTopHint(ILjava/lang/String;J)V

    return-void
.end method

.method public alertUpdateValue(ILjava/lang/String;)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertUpdateValue(ILjava/lang/String;)V

    return-void
.end method

.method public alertVideoUltraClear(II)V
    .locals 2
    .param p2    # I
        .annotation build Landroidx/annotation/StringRes;
        .end annotation
    .end param

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-boolean p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    const/4 v1, 0x1

    xor-int/2addr p0, v1

    if-nez p0, :cond_1

    goto :goto_0

    :cond_1
    const/4 v1, 0x0

    :goto_0
    invoke-virtual {v0, p1, p2, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertVideoUltraClear(IIZ)V

    return-void
.end method

.method public clearAlertStatus()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clearAlertStatus()V

    return-void
.end method

.method public clearAllTipsState()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTipsState:Ljava/util/Map;

    invoke-interface {p0}, Ljava/util/Map;->clear()V

    return-void
.end method

.method public clearFastmotionValue()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clearFastmotionTip()V

    return-void
.end method

.method public clearVideoUltraClear()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clearVideoUltraClear()V

    return-void
.end method

.method public containShortDurationDescriptionTips(Ljava/lang/String;)Z
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->containShortDurationDescriptionTips(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method public directHideLyingDirectHint()V
    .locals 0

    return-void
.end method

.method public varargs disableMenuItem(Z[I)V
    .locals 4

    if-eqz p2, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    if-eqz v0, :cond_1

    array-length v0, p2

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_1

    aget v2, p2, v1

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    invoke-virtual {v3, v2, p1}, Landroid/util/SparseBooleanArray;->put(IZ)V

    if-eqz p1, :cond_0

    invoke-virtual {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object v2

    if-eqz v2, :cond_0

    invoke-static {v2}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;->directSetResult(Landroid/view/View;)V

    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method

.method public varargs enableMenuItem(Z[I)V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    if-eqz v0, :cond_2

    invoke-virtual {v0}, Landroid/util/SparseBooleanArray;->size()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_1

    :cond_0
    array-length v0, p2

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_2

    aget v2, p2, v1

    iget-object v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    invoke-virtual {v3, v2}, Landroid/util/SparseBooleanArray;->delete(I)V

    if-eqz p1, :cond_1

    invoke-virtual {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object v2

    if-eqz v2, :cond_1

    invoke-static {v2}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;->directSetResult(Landroid/view/View;)V

    :cond_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_2
    :goto_1
    return-void
.end method

.method public enableSwitch()Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isEnableClick()Z

    move-result p0

    const/4 v0, 0x0

    if-nez p0, :cond_0

    return v0

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v1, 0xa1

    invoke-virtual {p0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    if-eqz p0, :cond_1

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isDoingAction()Z

    move-result p0

    if-eqz p0, :cond_1

    return v0

    :cond_1
    const/4 p0, 0x1

    return p0
.end method

.method public getAlertIsShow()Z
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isShow()Z

    move-result p0

    return p0
.end method

.method public getCurrentAiSceneLevel()I
    .locals 0

    iget p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCurrentAiSceneLevel:I

    return p0
.end method

.method public getFragmentInto()I
    .locals 0

    const/16 p0, 0xf4

    return p0
.end method

.method protected getLayoutResourceId()I
    .locals 0

    const p0, 0x7f0c008c

    return p0
.end method

.method public getTipsState(Ljava/lang/String;)Z
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTipsState:Ljava/util/Map;

    invoke-interface {p0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Boolean;

    if-eqz p0, :cond_2

    invoke-virtual {p0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p0

    if-nez p0, :cond_1

    goto :goto_0

    :cond_1
    const/4 p0, 0x1

    return p0

    :cond_2
    :goto_0
    return v1
.end method

.method public getTopImage(I)Landroid/widget/ImageView;
    .locals 2

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    invoke-virtual {v0}, Landroid/widget/ImageView;->getTag()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/data/data/config/TopConfigItem;

    if-eqz v1, :cond_0

    iget v1, v1, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    if-ne v1, p1, :cond_0

    return-object v0

    :cond_1
    const/4 p0, 0x0

    return-object p0
.end method

.method public getVideoTag()Lcom/android/camera/ui/VideoTagView;
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->getVideoTag()Lcom/android/camera/ui/VideoTagView;

    move-result-object p0

    return-object p0
.end method

.method public hideAlert()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->clear(Z)V

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setShow(Z)V

    return-void
.end method

.method public hideConfigMenu()V
    .locals 1

    new-instance v0, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-direct {v0, p0}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    return-void
.end method

.method public hideDelayNumber()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    invoke-virtual {v0}, Landroid/widget/TextView;->getVisibility()I

    move-result v0

    const/16 v1, 0x8

    if-eq v0, v1, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    invoke-virtual {p0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    :cond_0
    return-void
.end method

.method public hideExtraMenu()V
    .locals 1

    const/4 v0, 0x6

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->onBackEvent(I)Z

    return-void
.end method

.method public hideRecommendDescTip(Ljava/lang/String;)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->hideRecommendDescTip(Ljava/lang/String;)V

    return-void
.end method

.method public hideSwitchTip()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->hideSwitchTip()V

    return-void
.end method

.method public synthetic i(Landroid/view/View;)V
    .locals 0

    const/4 p1, 0x6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->onBackEvent(I)Z

    return-void
.end method

.method protected initView(Landroid/view/View;)V
    .locals 3

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getMoreResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMoreResources:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getAiSceneResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAiSceneResources:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getCinematicRatioResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCinematicRatioResources:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getAutoZoomResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAutoZoomResources:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getUltraWideBokehResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraWideBokehResources:[I

    invoke-static {}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getUltraPixelTopMenuResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPhotographyIconResources:[I

    invoke-static {}, Lcom/android/camera/data/data/config/ComponentRunningUltraPixel;->getUltraPixelSwitchTipsString()[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPhotographyTipString:[Ljava/lang/String;

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getLiveShotResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotResource:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getLightingResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLightingResource:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getVideoBokehResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mVideoBokehResource:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getSuperMacroResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSuperMacroResources:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getUltraPixelPortraitResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mUltraPixelPortraitResources:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getSuperEISResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSuperEISResources:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getVideo8KRecource()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mVideo8KResource:[I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getGifRecource()I

    move-result v0

    iput v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mGifResource:I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getDocumentResources()[I

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDocumentResources:[I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/Util;->isLayoutRTL(Landroid/content/Context;)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsRTL:Z

    new-instance v0, Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    invoke-direct {v0}, Lcom/android/camera/fragment/top/TopBarAnimationComponent;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBarAnimationComponent:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    const v0, 0x7f0902fd

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    const v0, 0x7f0902f7

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    new-instance v0, Landroid/util/SparseBooleanArray;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Landroid/util/SparseBooleanArray;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    const v0, 0x7f0902c2

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/ShapeBackGroundView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    const v0, 0x7f0902b6

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigViewGroup:Landroid/view/ViewGroup;

    const v0, 0x7f0902c4

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroidx/recyclerview/widget/RecyclerView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    const v0, 0x7f0902c5

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    const v0, 0x7f090157

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    const v0, 0x7f090151

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mImageViewBack:Landroid/widget/ImageView;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mImageViewBack:Landroid/widget/ImageView;

    invoke-static {v0}, Lcom/android/camera/animation/FolmeUtils;->touchTint(Landroid/view/View;)V

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mImageViewBack:Landroid/widget/ImageView;

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0902e8

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTvShotInterval:Landroid/widget/TextView;

    const v0, 0x7f0900b8

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/timerburst/CustomSeekBar;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCustomSeekBarInterval:Lcom/android/camera/timerburst/CustomSeekBar;

    const v0, 0x7f0900b7

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/timerburst/CustomSeekBar;

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCustomSeekBarCount:Lcom/android/camera/timerburst/CustomSeekBar;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    sget v1, Lcom/android/camera/Util;->sTopMargin:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    sget v1, Lcom/android/camera/Util;->sTopBarHeight:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigViewGroup:Landroid/view/ViewGroup;

    invoke-virtual {v1}, Landroid/view/ViewGroup;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    check-cast v1, Landroid/view/ViewGroup$MarginLayoutParams;

    iget v2, v0, Landroid/view/ViewGroup$MarginLayoutParams;->topMargin:I

    iget v0, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    add-int/2addr v2, v0

    iput v2, v1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget v0, v1, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iput v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenuHeight:I

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v0}, Landroid/widget/FrameLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup$MarginLayoutParams;

    invoke-static {}, Lcom/android/camera/Util;->getTopCoverHeight()I

    move-result v1

    iput v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundHeight:I

    iget v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundHeight:I

    iput v1, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    iget v0, v0, Landroid/view/ViewGroup$MarginLayoutParams;->height:I

    invoke-virtual {v1, v0}, Lcom/android/camera/ui/ShapeBackGroundView;->initHeight(I)V

    const v0, 0x7f0902c3

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/android/camera/fragment/top/TopExpendView;

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

    sget p1, Lcom/android/camera/Util;->sWindowWidth:I

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f07046d

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    const/4 v1, 0x2

    mul-int/2addr v0, v1

    sub-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTotalWidth:I

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->initTopView()V

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ActivityBase;

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCameraIntentManager()Lcom/android/camera/CameraIntentManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/CameraIntentManager;->isFromScreenSlide()Ljava/lang/Boolean;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-static {p1}, Lcom/android/camera/Util;->startScreenSlideAlphaInAnimation(Landroid/view/View;)V

    :cond_0
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->provideAnimateElement(ILjava/util/List;I)V

    return-void
.end method

.method public insertConfigItem(I)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->resetImages()V

    return-void
.end method

.method public isContainAlertLightingTip(I)Z
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    const/4 v1, 0x0

    if-nez p0, :cond_0

    return v1

    :cond_0
    const/4 p0, 0x1

    new-array p0, p0, [I

    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->parseLightingRes(I)I

    move-result p1

    aput p1, p0, v1

    invoke-virtual {v0, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isContainAlertRecommendTip([I)Z

    move-result p0

    return p0
.end method

.method public varargs isContainAlertRecommendTip([I)Z
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isContainAlertRecommendTip([I)Z

    move-result p0

    return p0
.end method

.method public isCurrentRecommendTipText(I)Z
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isCurrentRecommendTipText(I)Z

    move-result p0

    return p0
.end method

.method public isExtraMenuShowing()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    return p0
.end method

.method public isHDRShowing()Z
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isHDRShowing()Z

    move-result p0

    return p0
.end method

.method public isShowBacklightSelector()Z
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isShowBacklightSelector()Z

    move-result p0

    return p0
.end method

.method public isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z
    .locals 0

    if-eqz p1, :cond_1

    invoke-virtual {p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->isShow()Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x1

    return p0

    :cond_1
    :goto_0
    const/4 p0, 0x0

    return p0
.end method

.method public isTopConfig(I)Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/SupportedConfigs;->getLength()I

    move-result v0

    if-lez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/SupportedConfigs;->contains(I)Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public needViewClear()Z
    .locals 0

    const/4 p0, 0x1

    return p0
.end method

.method public notifyAfterFrameAvailable(I)V
    .locals 3

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->notifyAfterFrameAvailable(I)V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->notifyAfterFrameAvailable(I)V

    :cond_0
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v0, 0xfe

    const/4 v1, 0x0

    if-eq p1, v0, :cond_3

    const/16 v2, 0xd1

    if-eq p1, v2, :cond_3

    const/16 v2, 0xd2

    if-eq p1, v2, :cond_3

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p1

    if-eqz p1, :cond_3

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v2, 0xb1

    if-ne p1, v2, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager()Lcom/android/camera/module/impl/component/MimojiStatusManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/module/impl/component/MimojiStatusManager;->IsInMimojiCreate()Z

    move-result p1

    if-nez p1, :cond_3

    :cond_1
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v2, 0xb8

    if-ne p1, v2, :cond_2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->isInMimojiCreate()Z

    move-result p1

    if-nez p1, :cond_3

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->clearVideoUltraClear()V

    invoke-direct {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reConfigTipOfFlash(Z)V

    invoke-direct {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reConfigTipOfMusicHint(Z)V

    const/4 p1, 0x0

    invoke-virtual {p0, v1, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertUpdateValue(ILjava/lang/String;)V

    :cond_3
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->adjustViewBackground()V

    iget-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    if-eqz p1, :cond_7

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    if-eqz p1, :cond_6

    invoke-virtual {p1}, Landroidx/recyclerview/widget/RecyclerView;->getAdapter()Landroidx/recyclerview/widget/RecyclerView$Adapter;

    move-result-object p1

    if-eqz p1, :cond_6

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    if-nez p1, :cond_4

    goto :goto_0

    :cond_4
    invoke-virtual {p1}, Lcom/android/camera/fragment/top/ExtraAdapter;->animationRunning()Z

    move-result p1

    if-eqz p1, :cond_5

    return-void

    :cond_5
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    const/4 v2, 0x1

    invoke-virtual {p1, v2}, Lcom/android/camera/fragment/top/ExtraAdapter;->getTotalRow(I)I

    move-result p1

    if-eqz p1, :cond_7

    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {v2, v1, p1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemRangeChanged(II)V

    goto :goto_1

    :cond_6
    :goto_0
    return-void

    :cond_7
    :goto_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v2, 0xa4

    invoke-virtual {p1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-eq v2, v0, :cond_8

    if-eqz p1, :cond_8

    invoke-direct {p0, p1, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->showTips(Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;Z)V

    :cond_8
    return-void
.end method

.method public notifyDataChanged(II)V
    .locals 1

    invoke-super {p0, p1, p2}, Lcom/android/camera/fragment/BaseFragment;->notifyDataChanged(II)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    iget p2, p0, Lcom/android/camera/fragment/BaseFragment;->mResetType:I

    const/4 v0, 0x7

    if-ne p2, v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x2

    :goto_0
    const/4 p2, 0x0

    invoke-virtual {p0, p1, p2, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->provideAnimateElement(ILjava/util/List;I)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    if-nez p1, :cond_1

    new-instance p1, Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-direct {p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p2

    xor-int/lit8 p2, p2, 0x1

    invoke-virtual {p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setShow(Z)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    iget p2, p0, Lcom/android/camera/fragment/BaseFragment;->mDegree:I

    invoke-virtual {p1, p2}, Lcom/android/camera/fragment/BaseFragment;->setDegree(I)V

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getChildFragmentManager()Landroidx/fragment/app/FragmentManager;

    move-result-object p1

    const p2, 0x7f0902b5

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->getFragmentTag()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, p2, p0, v0}, Lcom/android/camera/fragment/FragmentUtils;->addFragmentWithTag(Landroidx/fragment/app/FragmentManager;ILandroidx/fragment/app/Fragment;Ljava/lang/String;)V

    :cond_1
    return-void
.end method

.method public onAngleChanged(F)V
    .locals 0

    return-void
.end method

.method public onBackEvent(I)Z
    .locals 6

    const/4 v0, 0x4

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eq p1, v0, :cond_0

    move v3, v1

    goto :goto_0

    :cond_0
    move v3, v2

    :goto_0
    invoke-virtual {p0, v3}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reverseExpandTopBar(Z)Z

    move-result v3

    if-eqz v3, :cond_1

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->showRecordingTime()V

    return v1

    :cond_1
    iget-boolean v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    if-nez v3, :cond_3

    iget-boolean v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    if-eqz v3, :cond_2

    goto :goto_1

    :cond_2
    move v3, v2

    goto :goto_2

    :cond_3
    :goto_1
    move v3, v1

    :goto_2
    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    if-eqz v4, :cond_4

    invoke-virtual {v4}, Landroid/view/ViewGroup;->getVisibility()I

    move-result v4

    if-eqz v4, :cond_5

    :cond_4
    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLlTimerMenu:Landroid/widget/LinearLayout;

    if-eqz v4, :cond_c

    invoke-virtual {v4}, Landroid/widget/LinearLayout;->getVisibility()I

    move-result v4

    if-nez v4, :cond_c

    :cond_5
    const/4 v4, 0x7

    if-eq p1, v1, :cond_7

    const/4 v5, 0x2

    if-eq p1, v5, :cond_7

    if-eq p1, v0, :cond_6

    const/4 v5, 0x6

    if-eq p1, v5, :cond_6

    if-eq p1, v4, :cond_9

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->directHiddenExtraMenu()V

    :goto_3
    move v3, v2

    goto :goto_4

    :cond_6
    invoke-direct {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->animatorExtraMenu(Z)V

    goto :goto_3

    :cond_7
    iget-boolean v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    if-nez v3, :cond_8

    return v2

    :cond_8
    invoke-direct {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->animatorExtraMenu(Z)V

    goto :goto_3

    :cond_9
    :goto_4
    if-eq p1, v0, :cond_a

    if-eq p1, v4, :cond_a

    invoke-virtual {p0, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reInitAlert(Z)V

    :cond_a
    iput-boolean v3, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    if-nez v0, :cond_b

    const/4 v0, 0x3

    if-eq p1, v0, :cond_b

    invoke-direct {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->notifyExtraMenuVisibilityChange(Z)V

    iput-boolean v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraTimerMenu:Z

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->initExtraTimerBurstMenu()V

    :cond_b
    return v1

    :cond_c
    return v2
.end method

.method public onBeautyRecordingStart()V
    .locals 1

    const/4 v0, 0x5

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->onBackEvent(I)Z

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-static {p0}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    return-void
.end method

.method public onBeautyRecordingStop()V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-static {p0}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-virtual {p0, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 13

    const-string v0, "FragmentTopConfig"

    const-string v1, "top config onclick"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isEnableClick()Z

    move-result v1

    if-nez v1, :cond_0

    return-void

    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v1

    const v2, 0x7f090151

    if-eq v1, v2, :cond_1a

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa01

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$MoreModePopupController;

    const/4 v2, 0x1

    if-eqz v1, :cond_1

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$MoreModePopupController;->isExpanded()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v1, v2}, Lcom/android/camera/protocol/ModeProtocol$MoreModePopupController;->shrink(Z)Z

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v3, 0xa4

    invoke-virtual {v1, v3}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-nez v1, :cond_2

    return-void

    :cond_2
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v4

    const/16 v5, 0xa1

    invoke-virtual {v4, v5}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v4

    check-cast v4, Lcom/android/camera/protocol/ModeProtocol$CameraAction;

    if-eqz v4, :cond_3

    invoke-interface {v4}, Lcom/android/camera/protocol/ModeProtocol$CameraAction;->isDoingAction()Z

    move-result v4

    if-eqz v4, :cond_3

    return-void

    :cond_3
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result v4

    if-eqz v4, :cond_4

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v4

    check-cast v4, Lcom/android/camera/Camera;

    invoke-virtual {v4}, Lcom/android/camera/Camera;->isScreenSlideOff()Z

    move-result v4

    if-eqz v4, :cond_4

    return-void

    :cond_4
    iget-boolean v4, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    if-eqz v4, :cond_5

    invoke-direct {p0, p1, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->onClickByExtraMenu(Landroid/view/View;Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;)V

    return-void

    :cond_5
    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/camera/data/data/config/TopConfigItem;

    if-nez v4, :cond_6

    return-void

    :cond_6
    iget-object v6, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    invoke-virtual {v6}, Landroid/util/SparseBooleanArray;->size()I

    move-result v6

    if-lez v6, :cond_7

    iget-object v6, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    iget v7, v4, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    invoke-virtual {v6, v7}, Landroid/util/SparseBooleanArray;->indexOfKey(I)I

    move-result v6

    if-ltz v6, :cond_7

    return-void

    :cond_7
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v6

    const/16 v7, 0xe3

    invoke-virtual {v6, v7}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v6

    check-cast v6, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;

    iget v8, v4, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    const/16 v9, 0xc8

    if-eq v8, v9, :cond_17

    const/16 v9, 0xa6

    const/16 v10, 0xc9

    if-eq v8, v10, :cond_16

    const/16 v10, 0xf2

    const/4 v11, 0x0

    if-eq v8, v10, :cond_14

    const/16 v10, 0xf3

    if-eq v8, v10, :cond_13

    const/16 v10, 0xff

    if-eq v8, v10, :cond_12

    const/16 v10, 0x100

    if-eq v8, v10, :cond_11

    const/16 v10, 0xab

    const/16 v12, 0xa2

    sparse-switch v8, :sswitch_data_0

    packed-switch v8, :pswitch_data_0

    const-string v7, "attr_feature_name"

    packed-switch v8, :pswitch_data_1

    packed-switch v8, :pswitch_data_2

    packed-switch v8, :pswitch_data_3

    goto/16 :goto_3

    :pswitch_0
    const/16 p0, 0xda

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_1
    const/16 p0, 0xd9

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_2
    const-string p0, "value_vv_icon_click"

    invoke-static {p0}, Lcom/android/camera/statistic/CameraStatUtils;->trackVVClick(Ljava/lang/String;)V

    const/16 p0, 0xd8

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_3
    const/16 p0, 0xd7

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_4
    invoke-static {}, Lcom/android/camera/statistic/CameraStatUtils;->trackMeterClick()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->provider()Lcom/android/camera/data/provider/DataProvider;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider;->dataConfig()Lcom/android/camera/data/provider/DataProvider$ProviderEvent;

    move-result-object v0

    check-cast v0, Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentConfigMeter()Lcom/android/camera/data/data/config/ComponentConfigMeter;

    move-result-object v0

    iget v1, v4, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    invoke-direct {p0, v0, p1, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->expandExtraView(Lcom/android/camera/data/data/ComponentData;Landroid/view/View;I)V

    goto/16 :goto_3

    :pswitch_5
    const/16 p0, 0xcf

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_6
    const-string p0, "liveshot_topmenu_click"

    invoke-static {p0, v11, v11}, Lcom/android/camera/statistic/MistatsWrapper;->commonKeyTriggerEvent(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V

    const/16 p0, 0xce

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    if-eqz v6, :cond_19

    const/16 p0, 0xa3

    invoke-interface {v6, p0}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->subscribe(I)V

    goto/16 :goto_3

    :pswitch_7
    const/16 p0, 0xcd

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_8
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    const-string v0, "menu_more"

    invoke-static {v0, p1, v11}, Lcom/android/camera/statistic/MistatsWrapper;->commonKeyTriggerEvent(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->showExtraMenu()V

    if-eqz v6, :cond_19

    invoke-interface {v6, v3}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->subscribe(I)V

    goto/16 :goto_3

    :pswitch_9
    const/16 p0, 0xc4

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_a
    const/16 p0, 0xc3

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_b
    invoke-static {}, Lcom/android/camera/data/DataRepository;->provider()Lcom/android/camera/data/provider/DataProvider;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/data/provider/DataProvider;->dataConfig()Lcom/android/camera/data/provider/DataProvider$ProviderEvent;

    move-result-object v0

    check-cast v0, Lcom/android/camera/data/data/config/DataItemConfig;

    const-string v1, "hdr_out_button"

    invoke-static {v7, v1, v11}, Lcom/android/camera/statistic/MistatsWrapper;->commonKeyTriggerEvent(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V

    invoke-virtual {v0}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentHdr()Lcom/android/camera/data/data/config/ComponentConfigHdr;

    move-result-object v0

    iget v1, v4, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    invoke-direct {p0, v0, p1, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->expandExtraView(Lcom/android/camera/data/data/ComponentData;Landroid/view/View;I)V

    if-eqz v6, :cond_19

    invoke-interface {v6, v12}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->subscribe(I)V

    goto/16 :goto_3

    :pswitch_c
    invoke-static {}, Lcom/android/camera/data/DataRepository;->provider()Lcom/android/camera/data/provider/DataProvider;

    move-result-object v1

    invoke-interface {v1}, Lcom/android/camera/data/provider/DataProvider;->dataConfig()Lcom/android/camera/data/provider/DataProvider$ProviderEvent;

    move-result-object v1

    check-cast v1, Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentFlash()Lcom/android/camera/data/data/config/ComponentConfigFlash;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v1, v2}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->isDisabled(I)Z

    move-result v2

    if-eqz v2, :cond_8

    goto/16 :goto_3

    :cond_8
    const-string v2, "flash_out_button"

    invoke-static {v7, v2, v11}, Lcom/android/camera/statistic/MistatsWrapper;->commonKeyTriggerEvent(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V

    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->disableUpdate()Z

    move-result v2

    if-nez v2, :cond_b

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-ne v0, v10, :cond_a

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->wj()Z

    move-result v0

    if-eqz v0, :cond_a

    invoke-static {}, Lcom/android/camera/CameraSettings;->isBackCamera()Z

    move-result v0

    if-eqz v0, :cond_a

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v1, p1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getComponentValue(I)Ljava/lang/String;

    move-result-object p1

    const-string v0, "0"

    if-ne p1, v0, :cond_9

    const-string v0, "5"

    goto :goto_0

    :cond_9
    const-string v0, "0"

    :goto_0
    iget v2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v1, v2, v0}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->setComponentValue(ILjava/lang/String;)V

    invoke-virtual {p0, v1, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->onExpandValueChange(Lcom/android/camera/data/data/ComponentData;Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    :cond_a
    iget v0, v4, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    invoke-direct {p0, v1, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->expandExtraView(Lcom/android/camera/data/data/ComponentData;Landroid/view/View;I)V

    goto :goto_1

    :cond_b
    invoke-virtual {v1}, Lcom/android/camera/data/data/config/ComponentConfigFlash;->getDisableReasonString()I

    move-result p1

    if-eqz p1, :cond_c

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object p0

    invoke-static {p0, p1}, Lcom/android/camera/ToastUtils;->showToast(Landroid/content/Context;I)V

    :cond_c
    const-string p0, "ignore click flash for disable update"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1
    if-eqz v6, :cond_19

    invoke-interface {v6, v5}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->subscribe(I)V

    goto/16 :goto_3

    :pswitch_d
    invoke-interface {v1, v9}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :pswitch_e
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningEisPro()Lcom/android/camera/data/data/runing/ComponentRunningEisPro;

    move-result-object v0

    if-eqz v0, :cond_19

    const/16 v1, 0xa5

    invoke-direct {p0, v0, p1, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->expandExtraView(Lcom/android/camera/data/data/ComponentData;Landroid/view/View;I)V

    goto/16 :goto_3

    :pswitch_f
    iget p0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 p1, 0xcc

    if-ne p0, p1, :cond_d

    goto/16 :goto_3

    :cond_d
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0x1a3

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$CloneAction;

    if-eqz p0, :cond_e

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$CloneAction;->onCloneGuideClicked()V

    :cond_e
    invoke-interface {v1, v3}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_0
    const/16 p0, 0xfd

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_1
    const/16 p0, 0xfb

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    if-eqz v6, :cond_19

    const/16 p0, 0xa9

    invoke-interface {v6, p0}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->subscribe(I)V

    goto/16 :goto_3

    :sswitch_2
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v0, 0xae

    if-ne p1, v0, :cond_f

    const-string p1, "feature_live_music_click"

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackLiveClick(Ljava/lang/String;)V

    goto :goto_2

    :cond_f
    const/16 v0, 0xb7

    if-ne p1, v0, :cond_10

    const-string p1, "mi_live_click_music"

    invoke-static {p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackMiLiveClick(Ljava/lang/String;)V

    :cond_10
    :goto_2
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object p0

    check-cast p0, Lcom/android/camera/Camera;

    new-instance p1, Landroid/content/Intent;

    const-class v0, Lcom/android/camera/fragment/music/LiveMusicActivity;

    invoke-direct {p1, p0, v0}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->startFromKeyguard()Z

    move-result v0

    const-string v1, "StartActivityWhenLocked"

    invoke-virtual {p1, v1, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    invoke-virtual {p0, p1}, Landroid/app/Activity;->startActivity(Landroid/content/Intent;)V

    const/16 p1, 0x8

    invoke-virtual {p0, p1}, Lcom/android/camera/ActivityBase;->setJumpFlag(I)V

    goto/16 :goto_3

    :sswitch_3
    const/16 p0, 0xef

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_4
    invoke-interface {v1, v7}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_5
    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->showSetting()V

    goto/16 :goto_3

    :sswitch_6
    const/16 p0, 0xdc

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_7
    const-string p0, "beauty_top_button"

    invoke-static {p0, v11, v11}, Lcom/android/camera/statistic/MistatsWrapper;->commonKeyTriggerEvent(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V

    const/16 p0, 0xd4

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_8
    const/16 p0, 0xd1

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_9
    const/16 p0, 0xcb

    invoke-interface {v1, p0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_a
    invoke-interface {v1, v10}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto/16 :goto_3

    :sswitch_b
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningAiAudio()Lcom/android/camera/data/data/runing/ComponentRunningAiAudio;

    move-result-object v0

    if-eqz v0, :cond_19

    const/16 v1, 0xa8

    invoke-direct {p0, v0, p1, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->expandExtraView(Lcom/android/camera/data/data/ComponentData;Landroid/view/View;I)V

    goto/16 :goto_3

    :sswitch_c
    invoke-interface {v1, v12}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto :goto_3

    :cond_11
    invoke-interface {v1, v10}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto :goto_3

    :cond_12
    invoke-interface {v1, v10}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto :goto_3

    :cond_13
    invoke-interface {v1, v10}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto :goto_3

    :cond_14
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xc1

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;

    if-eqz p0, :cond_15

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;->startAiLens()V

    :cond_15
    const-string p0, "ai_detect_changed"

    invoke-static {p0, v11, v11}, Lcom/android/camera/statistic/MistatsWrapper;->commonKeyTriggerEvent(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V

    goto :goto_3

    :cond_16
    invoke-interface {v1, v10}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    if-eqz v6, :cond_19

    invoke-interface {v6, v9}, Lcom/android/camera/protocol/ModeProtocol$CameraClickObservable;->subscribe(I)V

    goto :goto_3

    :cond_17
    invoke-static {}, Lcom/android/camera/data/DataRepository;->provider()Lcom/android/camera/data/provider/DataProvider;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/data/provider/DataProvider;->dataConfig()Lcom/android/camera/data/provider/DataProvider$ProviderEvent;

    move-result-object p1

    check-cast p1, Lcom/android/camera/data/data/config/DataItemConfig;

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentBokeh()Lcom/android/camera/data/data/config/ComponentConfigBokeh;

    move-result-object v0

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v3}, Lcom/android/camera/data/data/config/ComponentConfigBokeh;->toggle(I)V

    invoke-virtual {p1}, Lcom/android/camera/data/data/config/DataItemConfig;->getComponentBokeh()Lcom/android/camera/data/data/config/ComponentConfigBokeh;

    move-result-object v0

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, v3}, Lcom/android/camera/data/data/ComponentData;->getComponentValue(I)Ljava/lang/String;

    move-result-object v0

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v3, v0}, Lcom/android/camera/statistic/CameraStatUtils;->tarckBokenChanged(ILjava/lang/String;)V

    new-array v3, v2, [I

    const/4 v4, 0x0

    aput v9, v3, v4

    invoke-virtual {p0, v3}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {p1, v3, v0}, Lcom/android/camera/data/data/config/DataItemConfig;->reConfigHdrIfBokehChanged(ILjava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_18

    new-array p1, v2, [I

    const/4 v2, 0x0

    const/16 v3, 0xc2

    aput v3, p1, v2

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    :cond_18
    invoke-interface {v1, v0}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->configBokeh(Ljava/lang/String;)V

    :cond_19
    :goto_3
    return-void

    :cond_1a
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->switchExtraTimerBurstMenu()V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    return-void

    :sswitch_data_0
    .sparse-switch
        0xa2 -> :sswitch_c
        0xa8 -> :sswitch_b
        0xab -> :sswitch_a
        0xcb -> :sswitch_9
        0xd1 -> :sswitch_8
        0xd4 -> :sswitch_7
        0xdc -> :sswitch_6
        0xe1 -> :sswitch_5
        0xe3 -> :sswitch_4
        0xef -> :sswitch_3
        0xf5 -> :sswitch_2
        0xfb -> :sswitch_1
        0xfd -> :sswitch_0
    .end sparse-switch

    :pswitch_data_0
    .packed-switch 0xa4
        :pswitch_f
        :pswitch_e
        :pswitch_d
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0xc1
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
    .end packed-switch

    :pswitch_data_2
    .packed-switch 0xcd
        :pswitch_7
        :pswitch_6
        :pswitch_5
    .end packed-switch

    :pswitch_data_3
    .packed-switch 0xd6
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onExpandValueChange(Lcom/android/camera/data/data/ComponentData;Ljava/lang/String;Ljava/lang/String;)V
    .locals 7

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isEnableClick()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v1

    const/16 v2, 0xa4

    invoke-virtual {v1, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-nez v1, :cond_1

    return-void

    :cond_1
    iget-object v2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mFragmentTopAlert:Lcom/android/camera/fragment/top/FragmentTopAlert;

    invoke-virtual {v2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->showRecordingTime()V

    const/4 v2, 0x1

    if-ne p2, p3, :cond_2

    invoke-virtual {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reverseExpandTopBar(Z)Z

    return-void

    :cond_2
    invoke-virtual {p1}, Lcom/android/camera/data/data/ComponentData;->getDisplayTitleString()I

    move-result v3

    const/16 v4, 0xc1

    const/16 v5, 0xc2

    const/4 v6, 0x0

    sparse-switch v3, :sswitch_data_0

    goto/16 :goto_0

    :sswitch_0
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {p1, p3}, Lcom/android/camera/statistic/CameraStatUtils;->trackHdrChanged(ILjava/lang/String;)V

    new-array p1, v2, [I

    aput v5, p1, v6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    const-string p1, "e"

    invoke-interface {v1, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->restoreMutexFlash(Ljava/lang/String;)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, p1, p3}, Lcom/android/camera/data/data/config/DataItemConfig;->reConfigFlashIfHdrChanged(ILjava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_3

    new-array p1, v2, [I

    aput v4, p1, v6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    :cond_3
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, p1, p3}, Lcom/android/camera/data/data/config/DataItemConfig;->reConfigBokehIfHdrChanged(ILjava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_4

    new-array p1, v2, [I

    const/16 p2, 0xc8

    aput p2, p1, v6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    :cond_4
    invoke-interface {v1, p3}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->configHdr(Ljava/lang/String;)V

    goto/16 :goto_0

    :sswitch_1
    invoke-virtual {p1}, Lcom/android/camera/data/data/ComponentData;->getDisplayTitleString()I

    move-result p1

    const v3, 0x7f1003c8

    if-ne p1, v3, :cond_6

    const-string p1, "5"

    if-eq p2, p1, :cond_5

    if-ne p3, p1, :cond_6

    :cond_5
    const-string p1, "0"

    if-eq p3, p1, :cond_6

    const-string v3, "200"

    if-eq p3, v3, :cond_6

    invoke-interface {v1, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->configBackSoftLightSwitch(Ljava/lang/String;)V

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v3, p1}, Lcom/android/camera/statistic/CameraStatUtils;->trackFlashChanged(ILjava/lang/String;)V

    :cond_6
    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {p1, p3}, Lcom/android/camera/statistic/CameraStatUtils;->trackFlashChanged(ILjava/lang/String;)V

    new-array p1, v2, [I

    aput v4, p1, v6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {v0, p1, p2, p3}, Lcom/android/camera/data/data/config/DataItemConfig;->reConfigHhrIfFlashChanged(ILjava/lang/String;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_7

    new-array p2, v2, [I

    aput v5, p2, v6

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    :cond_7
    invoke-interface {v1, p3, p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->configFlash(Ljava/lang/String;Z)V

    goto :goto_0

    :sswitch_2
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningEisPro()Lcom/android/camera/data/data/runing/ComponentRunningEisPro;

    move-result-object p1

    invoke-virtual {p1, p2}, Lcom/android/camera/data/data/runing/ComponentRunningEisPro;->setComponentPreValue(Ljava/lang/String;)V

    new-array p1, v2, [I

    const/16 p2, 0xa5

    aput p2, p1, v6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    invoke-interface {v1, p2}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    goto :goto_0

    :sswitch_3
    iget p2, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-virtual {p1, p2}, Lcom/android/camera/data/data/ComponentData;->getKey(I)Ljava/lang/String;

    move-result-object p1

    const-string p2, "auto_exposure"

    invoke-static {p1, p2, p3}, Lcom/android/camera/statistic/MistatsWrapper;->moduleUIClickEvent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V

    new-array p1, v2, [I

    const/16 p2, 0xd6

    aput p2, p1, v6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    invoke-interface {v1, p3}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->configMeter(Ljava/lang/String;)V

    goto :goto_0

    :sswitch_4
    new-array p1, v2, [I

    const/16 p2, 0xa8

    aput p2, p1, v6

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateConfigItem([I)V

    invoke-interface {v1, p2}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigChanged(I)V

    :goto_0
    invoke-virtual {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reverseExpandTopBar(Z)Z

    return-void

    :sswitch_data_0
    .sparse-switch
        0x7f10032d -> :sswitch_4
        0x7f100345 -> :sswitch_3
        0x7f100377 -> :sswitch_2
        0x7f1003c8 -> :sswitch_1
        0x7f1003de -> :sswitch_0
    .end sparse-switch
.end method

.method public provideAnimateElement(ILjava/util/List;I)V
    .locals 17
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/util/List<",
            "Lio/reactivex/Completable;",
            ">;I)V"
        }
    .end annotation

    move-object/from16 v6, p0

    move/from16 v7, p1

    move-object/from16 v8, p2

    move/from16 v0, p3

    iget v9, v6, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/4 v1, 0x3

    const/4 v10, 0x1

    const/4 v11, 0x0

    if-ne v0, v1, :cond_0

    move v2, v10

    goto :goto_0

    :cond_0
    move v2, v11

    :goto_0
    invoke-super/range {p0 .. p3}, Lcom/android/camera/fragment/BaseFragment;->provideAnimateElement(ILjava/util/List;I)V

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/fragment/BaseFragment;->isInModeChanging()Z

    move-result v3

    if-nez v3, :cond_1

    if-ne v0, v1, :cond_2

    :cond_1
    iput-boolean v11, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowTopLyingDirectHint:Z

    :cond_2
    invoke-virtual {v6, v11, v10}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setSnapNumVisible(ZZ)V

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->hideDelayNumber()V

    const/16 v1, 0xa1

    if-eq v9, v1, :cond_7

    const/16 v1, 0xa2

    if-eq v9, v1, :cond_6

    const/16 v1, 0xa9

    if-eq v9, v1, :cond_5

    const/16 v1, 0xae

    if-eq v9, v1, :cond_8

    const/16 v1, 0xb4

    if-eq v9, v1, :cond_4

    const/16 v1, 0xb7

    if-eq v9, v1, :cond_3

    goto :goto_2

    :cond_3
    if-ne v7, v1, :cond_8

    goto :goto_1

    :cond_4
    if-ne v7, v1, :cond_8

    goto :goto_1

    :cond_5
    if-ne v7, v1, :cond_8

    goto :goto_1

    :cond_6
    if-ne v7, v1, :cond_8

    goto :goto_1

    :cond_7
    if-ne v7, v1, :cond_8

    :goto_1
    move v1, v11

    goto :goto_3

    :cond_8
    :goto_2
    move v1, v10

    :goto_3
    if-eqz v1, :cond_a

    const/4 v1, 0x7

    if-ne v0, v1, :cond_9

    goto :goto_4

    :cond_9
    const/4 v1, 0x4

    :goto_4
    invoke-virtual {v6, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->onBackEvent(I)Z

    :cond_a
    iget v1, v6, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {v1, v11, v11}, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->createModeChange(IZZ)Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    move-result-object v1

    invoke-virtual {v1, v7}, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->getTopMaskTargetHeight(I)I

    move-result v1

    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v3, v1}, Lcom/android/camera/ui/ShapeBackGroundView;->setBlackOriginHeight(I)V

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result v3

    if-eqz v3, :cond_c

    if-nez v1, :cond_b

    move v1, v11

    goto :goto_5

    :cond_b
    iget v1, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenuHeight:I

    :goto_5
    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    const/16 v4, 0x99

    invoke-virtual {v3, v4}, Lcom/android/camera/ui/ShapeBackGroundView;->setBackgroundAlpha(I)V

    goto :goto_6

    :cond_c
    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v3, v11}, Lcom/android/camera/ui/ShapeBackGroundView;->setCurrentRadius(I)V

    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v3, v11}, Lcom/android/camera/ui/ShapeBackGroundView;->setBackgroundAlpha(I)V

    :goto_6
    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v3}, Landroid/widget/FrameLayout;->getTag()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    const/16 v12, 0xa5

    if-eqz v3, :cond_d

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v3

    if-eq v3, v1, :cond_14

    :cond_d
    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/widget/FrameLayout;->setTag(Ljava/lang/Object;)V

    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    invoke-virtual {v3}, Lcom/android/camera/ui/ShapeBackGroundView;->getCurrentMaskHeight()I

    move-result v3

    if-le v1, v3, :cond_e

    move v3, v10

    goto :goto_7

    :cond_e
    move v3, v11

    :goto_7
    if-eqz v3, :cond_10

    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    if-eqz v8, :cond_f

    move v4, v10

    goto :goto_8

    :cond_f
    move v4, v11

    :goto_8
    invoke-virtual {v3, v1, v4}, Lcom/android/camera/ui/ShapeBackGroundView;->setMaskSpecificHeight(IZ)V

    goto :goto_b

    :cond_10
    if-nez v8, :cond_12

    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    if-eqz v8, :cond_11

    move v4, v10

    goto :goto_9

    :cond_11
    move v4, v11

    :goto_9
    invoke-virtual {v3, v1, v4}, Lcom/android/camera/ui/ShapeBackGroundView;->setMaskSpecificHeight(IZ)V

    goto :goto_b

    :cond_12
    if-ne v9, v12, :cond_14

    const/16 v3, 0xfe

    if-ne v7, v3, :cond_14

    iget-object v3, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBackgroundView:Lcom/android/camera/ui/ShapeBackGroundView;

    if-eqz v8, :cond_13

    move v4, v10

    goto :goto_a

    :cond_13
    move v4, v11

    :goto_a
    invoke-virtual {v3, v1, v4}, Lcom/android/camera/ui/ShapeBackGroundView;->setMaskSpecificHeight(IZ)V

    :cond_14
    :goto_b
    if-eqz v2, :cond_15

    invoke-direct/range {p0 .. p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->enableAllDisabledMenuItem()V

    :cond_15
    invoke-direct/range {p0 .. p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v1

    if-eqz v1, :cond_16

    invoke-virtual {v1, v7, v8, v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->provideAnimateElement(ILjava/util/List;I)V

    :cond_16
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v13

    if-eqz v2, :cond_17

    iget-object v0, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getVisibility()I

    move-result v0

    if-eqz v0, :cond_17

    iget-object v0, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-static {v0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;->directSetResult(Landroid/view/View;)V

    :cond_17
    iget v0, v6, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentCameraId()I

    move-result v1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/data/data/global/DataItemGlobal;->isNormalIntent()Z

    move-result v2

    invoke-static {v0, v1, v2}, Lcom/android/camera/data/data/config/SupportedConfigFactory;->getSupportedTopConfigs(IIZ)Lcom/android/camera/data/data/config/SupportedConfigs;

    move-result-object v0

    iput-object v0, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    iget-object v0, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    if-nez v0, :cond_18

    return-void

    :cond_18
    move v14, v11

    :goto_c
    iget-object v0, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-ge v14, v0, :cond_23

    iget-object v0, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {v0, v14}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    move-object v15, v0

    check-cast v15, Landroid/widget/ImageView;

    invoke-virtual {v15, v10}, Landroid/widget/ImageView;->setEnabled(Z)V

    iget-object v0, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSupportedConfigs:Lcom/android/camera/data/data/config/SupportedConfigs;

    invoke-virtual {v0, v14}, Lcom/android/camera/data/data/config/SupportedConfigs;->getConfigItem(I)Lcom/android/camera/data/data/config/TopConfigItem;

    move-result-object v5

    if-eqz v8, :cond_19

    move/from16 v16, v10

    goto :goto_d

    :cond_19
    move/from16 v16, v11

    :goto_d
    move-object/from16 v0, p0

    move-object v1, v5

    move-object v2, v15

    move/from16 v3, p1

    move-object v4, v13

    move-object v10, v5

    move/from16 v5, v16

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTopImageResource(Lcom/android/camera/data/data/config/TopConfigItem;Landroid/widget/ImageView;ILcom/android/camera/data/data/config/DataItemConfig;Z)Z

    move-result v0

    if-eqz v0, :cond_1a

    iget-object v1, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    iget v2, v10, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    invoke-virtual {v1, v2}, Landroid/util/SparseBooleanArray;->indexOfKey(I)I

    move-result v1

    if-ltz v1, :cond_1a

    iget-object v1, v6, Lcom/android/camera/fragment/top/FragmentTopConfig;->mDisabledFunctionMenu:Landroid/util/SparseBooleanArray;

    iget v2, v10, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    invoke-virtual {v1, v2}, Landroid/util/SparseBooleanArray;->get(I)Z

    move-result v1

    if-eqz v1, :cond_1a

    goto/16 :goto_10

    :cond_1a
    invoke-virtual {v15}, Landroid/widget/ImageView;->getTag()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/data/data/config/TopConfigItem;

    if-eqz v1, :cond_1b

    iget v1, v1, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    iget v2, v10, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    if-ne v1, v2, :cond_1b

    invoke-virtual {v15, v10}, Landroid/widget/ImageView;->setTag(Ljava/lang/Object;)V

    goto :goto_10

    :cond_1b
    invoke-virtual {v15, v10}, Landroid/widget/ImageView;->setTag(Ljava/lang/Object;)V

    if-nez v8, :cond_1d

    if-eqz v0, :cond_1c

    invoke-static {v15}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;->directSetResult(Landroid/view/View;)V

    goto :goto_10

    :cond_1c
    invoke-static {v15}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;->directSetResult(Landroid/view/View;)V

    goto :goto_10

    :cond_1d
    const/16 v1, 0x96

    if-eqz v0, :cond_20

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    invoke-direct {v0, v15}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    iget v2, v6, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v3, 0xa7

    if-ne v2, v3, :cond_1f

    const/16 v2, 0xc1

    iget v3, v10, Lcom/android/camera/data/data/config/TopConfigItem;->configItem:I

    if-ne v2, v3, :cond_1f

    iget-boolean v2, v10, Lcom/android/camera/data/data/config/TopConfigItem;->enable:Z

    if-eqz v2, :cond_1e

    const/high16 v2, 0x3f800000    # 1.0f

    goto :goto_e

    :cond_1e
    const v2, 0x3ecccccd    # 0.4f

    :goto_e
    invoke-virtual {v0, v2}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;->setTargetAlpha(F)V

    :cond_1f
    invoke-virtual {v0, v1}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setStartDelayTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object v2

    invoke-virtual {v2, v1}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-interface {v8, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_10

    :cond_20
    if-eq v9, v12, :cond_22

    iget v0, v6, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    if-ne v0, v12, :cond_21

    goto :goto_f

    :cond_21
    new-instance v0, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    invoke-direct {v0, v15}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setDurationTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object v0

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-interface {v8, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_10

    :cond_22
    :goto_f
    invoke-static {v15}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;->directSetResult(Landroid/view/View;)V

    :goto_10
    add-int/lit8 v14, v14, 0x1

    const/4 v10, 0x1

    goto/16 :goto_c

    :cond_23
    return-void
.end method

.method public provideRotateItem(Ljava/util/List;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/view/View;",
            ">;I)V"
        }
    .end annotation

    invoke-super {p0, p1, p2}, Lcom/android/camera/fragment/BaseFragment;->provideRotateItem(Ljava/util/List;I)V

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isLandscapeMode()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mConfigViews:Ljava/util/List;

    invoke-interface {p1, v0}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    invoke-interface {p1, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    invoke-interface {p1, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    if-eqz v0, :cond_1

    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->provideRotateItem(Ljava/util/List;I)V

    :cond_1
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExpandView:Lcom/android/camera/fragment/top/TopExpendView;

    if-eqz p0, :cond_2

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/TopExpendView;->provideRotateItem(Ljava/util/List;I)V

    :cond_2
    return-void
.end method

.method public reInitAlert(Z)V
    .locals 3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isHandGestureOpen()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getHandGestureRunning()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lio/reactivex/android/schedulers/AndroidSchedulers;->mainThread()Lio/reactivex/Scheduler;

    move-result-object v0

    new-instance v1, Lcom/android/camera/fragment/top/m;

    invoke-direct {v1, p0, p1}, Lcom/android/camera/fragment/top/m;-><init>(Lcom/android/camera/fragment/top/FragmentTopConfig;Z)V

    iget-boolean p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    if-eqz p0, :cond_1

    const-wide/16 p0, 0x78

    goto :goto_0

    :cond_1
    const-wide/16 p0, 0x0

    :goto_0
    sget-object v2, Ljava/util/concurrent/TimeUnit;->MILLISECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-virtual {v0, v1, p0, p1, v2}, Lio/reactivex/Scheduler;->scheduleDirect(Ljava/lang/Runnable;JLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;

    return-void
.end method

.method public refreshExtraMenu()V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowExtraMenu:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroidx/recyclerview/widget/RecyclerView;->getAdapter()Landroidx/recyclerview/widget/RecyclerView$Adapter;

    move-result-object v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopExtraMenu:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView;->getAdapter()Landroidx/recyclerview/widget/RecyclerView$Adapter;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    nop

    :cond_1
    :goto_0
    return-void
.end method

.method public refreshHistogramStatsView()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->refreshHistogramStatsView()V

    return-void
.end method

.method protected register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->register(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->registerBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    const/16 v0, 0xac

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lb/c/a/c;->isSupportedOpticalZoom()Z

    move-result v0

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-nez v0, :cond_0

    const/16 v0, 0xb8

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    :cond_0
    return-void
.end method

.method public removeConfigItem(I)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->resetImages()V

    return-void
.end method

.method public removeExtraMenu(I)V
    .locals 0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->onBackEvent(I)Z

    return-void
.end method

.method public reverseExpandTopBar(Z)Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopBarAnimationComponent:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->reverse(Z)Z

    move-result p0

    return p0
.end method

.method public rotate()V
    .locals 0

    return-void
.end method

.method public setAiSceneImageLevel(I)V
    .locals 5

    const/16 v0, 0x19

    if-ne p1, v0, :cond_0

    const/16 p1, 0x17

    :cond_0
    iput p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCurrentAiSceneLevel:I

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getAiSceneDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getAiSceneShadowDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    const/4 v2, 0x1

    const/4 v3, 0x0

    if-eqz v0, :cond_1

    if-nez v1, :cond_2

    :cond_1
    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAiSceneResources:[I

    aget v1, v1, v3

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    iget-object v4, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mAiSceneResources:[I

    aget v4, v4, v2

    invoke-virtual {v1, v4}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    :cond_2
    const/16 v4, 0xc9

    invoke-virtual {p0, v4}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object v4

    if-eqz v0, :cond_5

    if-eqz v4, :cond_5

    if-nez v1, :cond_3

    goto :goto_1

    :cond_3
    invoke-virtual {v4, v0}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    invoke-virtual {v4, v1}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    const/16 v0, 0x26

    if-ne p1, v0, :cond_4

    goto :goto_0

    :cond_4
    move v2, v3

    :goto_0
    invoke-direct {p0, v2}, Lcom/android/camera/fragment/top/FragmentTopConfig;->configBottomPopupTips(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 p1, 0xaf

    invoke-virtual {p0, p1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    if-eqz p0, :cond_5

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->reConfigQrCodeTip()Z

    :cond_5
    :goto_1
    return-void
.end method

.method public setAlertAnim(Z)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setAlertAnim(Z)V

    return-void
.end method

.method public setClickEnable(Z)V
    .locals 0

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->setClickEnable(Z)V

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->setClickEnable(Z)V

    :cond_0
    return-void
.end method

.method public setRecordingTimeState(I)V
    .locals 1

    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setRecordingTimeState(IZ)V

    return-void
.end method

.method public setRecordingTimeState(IZ)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setRecordingTimeState(IZ)V

    goto :goto_0

    :cond_0
    invoke-static {p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setPendingRecordingState(I)V

    :goto_0
    return-void
.end method

.method public setShow(Z)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setShow(Z)V

    :cond_0
    return-void
.end method

.method public setSnapNumValue(I)V
    .locals 3
    .annotation build Landroid/annotation/TargetApi;
        value = 0x15
    .end annotation

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSnapStyle:Landroid/text/style/TextAppearanceSpan;

    if-nez v0, :cond_0

    new-instance v0, Landroid/text/style/TextAppearanceSpan;

    invoke-virtual {p0}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x7f11011d

    invoke-direct {v0, v1, v2}, Landroid/text/style/TextAppearanceSpan;-><init>(Landroid/content/Context;I)V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSnapStyle:Landroid/text/style/TextAppearanceSpan;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mStringBuilder:Landroid/text/SpannableStringBuilder;

    if-nez v0, :cond_1

    new-instance v0, Landroid/text/SpannableStringBuilder;

    invoke-direct {v0}, Landroid/text/SpannableStringBuilder;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mStringBuilder:Landroid/text/SpannableStringBuilder;

    goto :goto_0

    :cond_1
    invoke-virtual {v0}, Landroid/text/SpannableStringBuilder;->clear()V

    :goto_0
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mStringBuilder:Landroid/text/SpannableStringBuilder;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    aput-object p1, v1, v2

    const-string p1, "%02d"

    invoke-static {p1, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mSnapStyle:Landroid/text/style/TextAppearanceSpan;

    const/16 v2, 0x21

    invoke-virtual {v0, p1, v1, v2}, Landroid/text/SpannableStringBuilder;->append(Ljava/lang/CharSequence;Ljava/lang/Object;I)Landroid/text/SpannableStringBuilder;

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mStringBuilder:Landroid/text/SpannableStringBuilder;

    invoke-virtual {p1, p0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    return-void
.end method

.method public setSnapNumVisible(ZZ)V
    .locals 1

    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    invoke-virtual {p2}, Landroid/widget/TextView;->getVisibility()I

    move-result p2

    const/4 v0, 0x0

    if-nez p2, :cond_0

    const/4 p2, 0x1

    goto :goto_0

    :cond_0
    move p2, v0

    :goto_0
    if-ne p1, p2, :cond_1

    return-void

    :cond_1
    iget-object p2, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomInAnimator:Landroid/animation/AnimatorSet;

    if-nez p2, :cond_2

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->initSnapNumAnimator()V

    :cond_2
    if-eqz p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTopTipMarin(Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    invoke-static {p1}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;->directSetResult(Landroid/view/View;)V

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setSnapNumValue(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomInAnimator:Landroid/animation/AnimatorSet;

    invoke-virtual {p0}, Landroid/animation/AnimatorSet;->start()V

    goto :goto_1

    :cond_3
    iget-object p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mZoomOutAnimator:Landroid/animation/AnimatorSet;

    invoke-virtual {p1}, Landroid/animation/AnimatorSet;->start()V

    new-instance p1, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mMultiSnapNum:Landroid/widget/TextView;

    invoke-direct {p1, p0}, Lcom/android/camera/animation/type/AlphaOutOnSubscribe;-><init>(Landroid/view/View;)V

    const/16 p0, 0x1f4

    invoke-virtual {p1, p0}, Lcom/android/camera/animation/type/BaseOnSubScribe;->setStartDelayTime(I)Lcom/android/camera/animation/type/BaseOnSubScribe;

    move-result-object p0

    invoke-static {p0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :goto_1
    return-void
.end method

.method public setTipsState(Ljava/lang/String;Z)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTipsState:Ljava/util/Map;

    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p2

    invoke-interface {p0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public showConfigMenu()V
    .locals 1

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mTopConfigMenu:Landroid/view/View;

    invoke-direct {v0, p0}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object p0

    invoke-virtual {p0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    return-void
.end method

.method public showDelayNumber(I)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    invoke-virtual {v0}, Landroid/widget/TextView;->getVisibility()I

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    invoke-direct {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTopTipMarin(Landroid/view/View;)V

    new-instance v0, Lcom/android/camera/animation/type/AlphaInOnSubscribe;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    invoke-direct {v0, v1}, Lcom/android/camera/animation/type/AlphaInOnSubscribe;-><init>(Landroid/view/View;)V

    invoke-static {v0}, Lio/reactivex/Completable;->create(Lio/reactivex/CompletableOnSubscribe;)Lio/reactivex/Completable;

    move-result-object v0

    invoke-virtual {v0}, Lio/reactivex/Completable;->subscribe()Lio/reactivex/disposables/Disposable;

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mCaptureDelayNumber:Landroid/widget/TextView;

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    return-void
.end method

.method public showDocumentStateButton(I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->showDocumentStateButton(I)V

    return-void
.end method

.method public startLiveShotAnimation()V
    .locals 3

    const/16 v0, 0xce

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object v0

    if-eqz v0, :cond_3

    invoke-virtual {v0}, Landroid/widget/ImageView;->getDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    instance-of v1, v0, Landroid/graphics/drawable/LayerDrawable;

    if-eqz v1, :cond_3

    check-cast v0, Landroid/graphics/drawable/LayerDrawable;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/graphics/drawable/LayerDrawable;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    check-cast v0, Landroid/graphics/drawable/RotateDrawable;

    iget-object v1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotAnimator:Landroid/animation/ObjectAnimator;

    if-eqz v1, :cond_0

    invoke-virtual {v1}, Landroid/animation/ObjectAnimator;->getTarget()Ljava/lang/Object;

    move-result-object v1

    if-eq v1, v0, :cond_1

    :cond_0
    const/4 v1, 0x2

    new-array v1, v1, [I

    fill-array-data v1, :array_0

    const-string v2, "level"

    invoke-static {v0, v2, v1}, Landroid/animation/ObjectAnimator;->ofInt(Ljava/lang/Object;Ljava/lang/String;[I)Landroid/animation/ObjectAnimator;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotAnimator:Landroid/animation/ObjectAnimator;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotAnimator:Landroid/animation/ObjectAnimator;

    const-wide/16 v1, 0x3e8

    invoke-virtual {v0, v1, v2}, Landroid/animation/ObjectAnimator;->setDuration(J)Landroid/animation/ObjectAnimator;

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotAnimator:Landroid/animation/ObjectAnimator;

    new-instance v1, Lmiui/view/animation/CubicEaseInOutInterpolator;

    invoke-direct {v1}, Lmiui/view/animation/CubicEaseInOutInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    :cond_1
    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotAnimator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v0}, Landroid/animation/ObjectAnimator;->isRunning()Z

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotAnimator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v0}, Landroid/animation/ObjectAnimator;->cancel()V

    :cond_2
    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mLiveShotAnimator:Landroid/animation/ObjectAnimator;

    invoke-virtual {p0}, Landroid/animation/ObjectAnimator;->start()V

    :cond_3
    return-void

    nop

    :array_0
    .array-data 4
        0x0
        0x2710
    .end array-data
.end method

.method public toSlideSwitch(ILjava/lang/String;)V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/fragment/BaseFragment;->isEnableClick()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa4

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-nez v0, :cond_1

    return-void

    :cond_1
    invoke-interface {v0, p1, p2}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->onConfigValueChanged(ILjava/lang/String;)V

    iget-object p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mExtraAdapter:Lcom/android/camera/fragment/top/ExtraAdapter;

    if-eqz p0, :cond_2

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    :cond_2
    return-void
.end method

.method protected unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/fragment/BaseFragment;->unRegister(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;)V

    invoke-virtual {p0, p1, p0}, Lcom/android/camera/fragment/BaseFragment;->unRegisterBackStack(Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;Lcom/android/camera/protocol/ModeProtocol$HandleBackTrace;)V

    const/16 v0, 0xac

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    invoke-static {}, Lb/c/a/c;->isSupportedOpticalZoom()Z

    move-result v0

    if-nez v0, :cond_0

    sget-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-nez v0, :cond_0

    const/16 v0, 0xb8

    invoke-interface {p1, v0, p0}, Lcom/android/camera/protocol/ModeProtocol$ModeCoordinator;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    :cond_0
    return-void
.end method

.method public varargs updateConfigItem([I)V
    .locals 9

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemConfig()Lcom/android/camera/data/data/config/DataItemConfig;

    move-result-object v6

    array-length v7, p1

    const/4 v0, 0x0

    move v8, v0

    :goto_0
    if-ge v8, v7, :cond_1

    aget v0, p1, v8

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object v2

    if-nez v2, :cond_0

    goto :goto_1

    :cond_0
    invoke-virtual {v2}, Landroid/widget/ImageView;->getTag()Ljava/lang/Object;

    move-result-object v0

    move-object v1, v0

    check-cast v1, Lcom/android/camera/data/data/config/TopConfigItem;

    iget v3, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/4 v5, 0x0

    move-object v0, p0

    move-object v4, v6

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/fragment/top/FragmentTopConfig;->setTopImageResource(Lcom/android/camera/data/data/config/TopConfigItem;Landroid/widget/ImageView;ILcom/android/camera/data/data/config/DataItemConfig;Z)Z

    :goto_1
    add-int/lit8 v8, v8, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method

.method public updateContentDescription()V
    .locals 2

    const/16 v0, 0xc4

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopImage(I)Landroid/widget/ImageView;

    move-result-object v0

    if-eqz v0, :cond_0

    const v1, 0x7f100040

    invoke-virtual {p0, v1}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Landroid/widget/ImageView;->setContentDescription(Ljava/lang/CharSequence;)V

    :cond_0
    return-void
.end method

.method public updateHistogramStatsData([I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateHistogramStatsData([I)V

    return-void
.end method

.method public updateHistogramStatsData([I[I[I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0, p1, p2, p3}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateHistogramStatsData([I[I[I)V

    return-void
.end method

.method public updateLyingDirectHint(ZZ)V
    .locals 0

    if-nez p2, :cond_0

    iput-boolean p1, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowTopLyingDirectHint:Z

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isExtraMenuShowing()Z

    move-result p1

    if-eqz p1, :cond_1

    return-void

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p2

    if-nez p2, :cond_2

    return-void

    :cond_2
    iget-boolean p0, p0, Lcom/android/camera/fragment/top/FragmentTopConfig;->mIsShowTopLyingDirectHint:Z

    invoke-virtual {p1, p0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateLyingDirectHint(Z)V

    return-void
.end method

.method public updateProVideoRecordingSimpleView(Z)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateProVideoRecordingSimpleView(Z)V

    return-void
.end method

.method public updateRGBHistogramSwitched(Z)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    :cond_0
    return-void
.end method

.method public updateRecordingTime(Ljava/lang/String;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateRecordingTime(Ljava/lang/String;)V

    :cond_0
    return-void
.end method

.method public updateRecordingTimeStyle(Z)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object p0

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateRecordingTimeStyle(Z)V

    :cond_0
    return-void
.end method

.method public updateTopAlertLayout()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->isTopAlertShowing(Lcom/android/camera/fragment/top/FragmentTopAlert;)Z

    move-result p0

    if-nez p0, :cond_0

    return-void

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/fragment/top/FragmentTopAlert;->updateTopAlertLayout()V

    return-void
.end method

.method public synthetic x(Z)V
    .locals 4

    invoke-direct {p0}, Lcom/android/camera/fragment/top/FragmentTopConfig;->getTopAlert()Lcom/android/camera/fragment/top/FragmentTopAlert;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/fragment/top/FragmentTopAlert;->setShow(Z)V

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reConfigTipOfFlash(Z)V

    invoke-direct {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->reConfigTipOfMusicHint(Z)V

    const/4 p1, 0x0

    const/4 v2, 0x0

    invoke-virtual {p0, v2, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->alertUpdateValue(ILjava/lang/String;)V

    invoke-virtual {p0, v2, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->updateLyingDirectHint(ZZ)V

    iget p1, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    invoke-static {p1}, Lcom/android/camera/CameraSettings;->isProVideoHistogramOpen(I)Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-virtual {v0, v2}, Lcom/android/camera/fragment/top/FragmentTopAlert;->alertHistogram(I)V

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xc1

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;

    if-eqz p1, :cond_3

    iget v0, p0, Lcom/android/camera/fragment/BaseFragment;->mCurrentMode:I

    const/16 v3, 0xa2

    if-eq v0, v3, :cond_2

    const/16 v3, 0xa3

    if-eq v0, v3, :cond_2

    goto :goto_0

    :cond_2
    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$TopConfigProtocol;->reShowMoon()V

    :cond_3
    :goto_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xa4

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;

    if-eqz p1, :cond_4

    invoke-direct {p0, p1, v1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->showTips(Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckVideoUltraClearTip()V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckVideoHDR10Tip()V

    invoke-interface {p1, v1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckParameterResetTip(Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckParameterDescriptionTip()V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckVideoLog()V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckRaw()V

    invoke-interface {p1, v1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckFastMotion(Z)V

    invoke-interface {p1, v2}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->recheckVideoFps(Z)V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckDocumentMode()V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckHanGestureDescTip()V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckSpeechShutterDescTip()V

    invoke-interface {p1}, Lcom/android/camera/protocol/ModeProtocol$ConfigChanges;->reCheckAmbilight()V

    :cond_4
    return-void
.end method
