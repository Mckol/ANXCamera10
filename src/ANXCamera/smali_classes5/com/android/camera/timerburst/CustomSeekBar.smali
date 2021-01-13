.class public Lcom/android/camera/timerburst/CustomSeekBar;
.super Landroid/widget/RelativeLayout;
.source "CustomSeekBar.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/timerburst/CustomSeekBar$JudgeSegmentResult;,
        Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;
    }
.end annotation


# instance fields
.field private final TAG:Ljava/lang/String;

.field private circleFullImageView:Lcom/android/camera/timerburst/CircleFullImageView;

.field private mCircleIndexX:F

.field mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

.field mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

.field private mConfigArray:[I

.field mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

.field mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

.field private mContext:Landroid/content/Context;

.field private mCurrentValue:I

.field private mCurrentX:F

.field private mEventType:I

.field private mInnerMarginHorizontal:F

.field private mIsEnlargeState:Z

.field private mMarginBuffer:F

.field private mRangeMax:[F

.field private mRangeMin:[F

.field private mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

.field private mRlSeekOutter:Landroid/widget/RelativeLayout;

.field private mRlSeekOutterHeight:F

.field mSeekBarValueListener:Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;

.field private mTotalwidth:I

.field private mTvCurrentValue:Landroid/widget/TextView;

.field private mTvEndValue:Landroid/widget/TextView;

.field private mTvEndX:F

.field mTvMoveDownAnimator:Landroid/view/animation/TranslateAnimation;

.field mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

.field private mTvParentLayout:Landroid/widget/RelativeLayout;

.field private mTvStartRightX:F

.field private mTvStartValue:Landroid/widget/TextView;

.field private mTvWidth:F

.field private mUnitString:Ljava/lang/String;

.field private mValueEnlargeTimes:I

.field private mXStartParent:I

.field private widthScreen:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    invoke-direct {p0, p1}, Landroid/widget/RelativeLayout;-><init>(Landroid/content/Context;)V

    const-string v0, "CustomSeekBar"

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->TAG:Ljava/lang/String;

    const/4 v0, 0x4

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    const-string v0, ""

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mUnitString:Ljava/lang/String;

    iput-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->initViews()V

    return-void

    :array_0
    .array-data 4
        0x5
        0x64
        0x1f4
        0x3e8
    .end array-data
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Landroid/widget/RelativeLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    const-string p2, "CustomSeekBar"

    iput-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->TAG:Ljava/lang/String;

    const/4 p2, 0x4

    new-array p2, p2, [I

    fill-array-data p2, :array_0

    iput-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    const/4 p2, 0x0

    iput-boolean p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    const-string p2, ""

    iput-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mUnitString:Ljava/lang/String;

    iput-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->initViews()V

    return-void

    :array_0
    .array-data 4
        0x5
        0x64
        0x1f4
        0x3e8
    .end array-data
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/widget/RelativeLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    const-string p2, "CustomSeekBar"

    iput-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->TAG:Ljava/lang/String;

    const/4 p2, 0x4

    new-array p2, p2, [I

    fill-array-data p2, :array_0

    iput-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    const/4 p2, 0x0

    iput-boolean p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    const-string p2, ""

    iput-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mUnitString:Ljava/lang/String;

    iput-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->initViews()V

    return-void

    :array_0
    .array-data 4
        0x5
        0x64
        0x1f4
        0x3e8
    .end array-data
.end method

.method static synthetic access$002(Lcom/android/camera/timerburst/CustomSeekBar;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    return p1
.end method

.method static synthetic access$100(Lcom/android/camera/timerburst/CustomSeekBar;)F
    .locals 0

    iget p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mMarginBuffer:F

    return p0
.end method

.method static synthetic access$1002(Lcom/android/camera/timerburst/CustomSeekBar;F)F
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndX:F

    return p1
.end method

.method static synthetic access$102(Lcom/android/camera/timerburst/CustomSeekBar;F)F
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mMarginBuffer:F

    return p1
.end method

.method static synthetic access$1100(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/widget/TextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$1202(Lcom/android/camera/timerburst/CustomSeekBar;F)F
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvWidth:F

    return p1
.end method

.method static synthetic access$1300(Lcom/android/camera/timerburst/CustomSeekBar;FF)F
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/timerburst/CustomSeekBar;->judgeCorridinatePosition(FF)F

    move-result p0

    return p0
.end method

.method static synthetic access$1400(Lcom/android/camera/timerburst/CustomSeekBar;)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->reverseEnlager()V

    return-void
.end method

.method static synthetic access$1502(Lcom/android/camera/timerburst/CustomSeekBar;I)I
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mXStartParent:I

    return p1
.end method

.method static synthetic access$1600(Lcom/android/camera/timerburst/CustomSeekBar;ZF)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/timerburst/CustomSeekBar;->updateTvCurrent(ZF)V

    return-void
.end method

.method static synthetic access$1700(Lcom/android/camera/timerburst/CustomSeekBar;)F
    .locals 0

    iget p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mInnerMarginHorizontal:F

    return p0
.end method

.method static synthetic access$1702(Lcom/android/camera/timerburst/CustomSeekBar;F)F
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mInnerMarginHorizontal:F

    return p1
.end method

.method static synthetic access$1800(Lcom/android/camera/timerburst/CustomSeekBar;)F
    .locals 0

    iget p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutterHeight:F

    return p0
.end method

.method static synthetic access$1900(Lcom/android/camera/timerburst/CustomSeekBar;)Lcom/android/camera/timerburst/CircleFullImageView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->circleFullImageView:Lcom/android/camera/timerburst/CircleFullImageView;

    return-object p0
.end method

.method static synthetic access$200(Lcom/android/camera/timerburst/CustomSeekBar;I)F
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result p0

    return p0
.end method

.method static synthetic access$2000(Lcom/android/camera/timerburst/CustomSeekBar;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    return p0
.end method

.method static synthetic access$2100(Lcom/android/camera/timerburst/CustomSeekBar;)[I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    return-object p0
.end method

.method static synthetic access$2200(Lcom/android/camera/timerburst/CustomSeekBar;)[F
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMin:[F

    return-object p0
.end method

.method static synthetic access$300(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/content/Context;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    return-object p0
.end method

.method static synthetic access$400(Lcom/android/camera/timerburst/CustomSeekBar;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->widthScreen:I

    return p0
.end method

.method static synthetic access$402(Lcom/android/camera/timerburst/CustomSeekBar;I)I
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->widthScreen:I

    return p1
.end method

.method static synthetic access$500(Lcom/android/camera/timerburst/CustomSeekBar;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTotalwidth:I

    return p0
.end method

.method static synthetic access$502(Lcom/android/camera/timerburst/CustomSeekBar;I)I
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTotalwidth:I

    return p1
.end method

.method static synthetic access$600(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/widget/RelativeLayout;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutter:Landroid/widget/RelativeLayout;

    return-object p0
.end method

.method static synthetic access$702(Lcom/android/camera/timerburst/CustomSeekBar;F)F
    .locals 0

    iput p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartRightX:F

    return p1
.end method

.method static synthetic access$800(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/widget/RelativeLayout;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    return-object p0
.end method

.method static synthetic access$900(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/widget/TextView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    return-object p0
.end method

.method private doAnim()V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->isRunning()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->cancel()V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->isRunning()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->cancel()V

    :cond_1
    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

    if-eqz v0, :cond_2

    invoke-virtual {v0}, Landroid/view/animation/TranslateAnimation;->hasEnded()Z

    move-result v0

    if-nez v0, :cond_2

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

    invoke-virtual {v0}, Landroid/view/animation/TranslateAnimation;->cancel()V

    :cond_2
    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->startEnlagerAnim()V

    goto :goto_0

    :cond_3
    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

    if-eqz v0, :cond_4

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->isRunning()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->cancel()V

    :cond_4
    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

    if-eqz v0, :cond_5

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->isRunning()Z

    move-result v0

    if-eqz v0, :cond_5

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->cancel()V

    :cond_5
    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveDownAnimator:Landroid/view/animation/TranslateAnimation;

    if-eqz v0, :cond_6

    invoke-virtual {v0}, Landroid/view/animation/TranslateAnimation;->hasEnded()Z

    move-result v0

    if-nez v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveDownAnimator:Landroid/view/animation/TranslateAnimation;

    invoke-virtual {v0}, Landroid/view/animation/TranslateAnimation;->cancel()V

    :cond_6
    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->reverseEnlagerAnim()V

    :goto_0
    return-void
.end method

.method private getCircleDiameter(I)F
    .locals 1

    const/4 v0, 0x1

    if-eq p1, v0, :cond_3

    const/4 v0, 0x2

    if-eq p1, v0, :cond_2

    const/4 v0, 0x3

    if-eq p1, v0, :cond_1

    const/4 v0, 0x4

    if-eq p1, v0, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const p1, 0x7f070417

    invoke-virtual {p0, p1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p0

    goto :goto_0

    :cond_1
    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const p1, 0x7f070414

    invoke-virtual {p0, p1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p0

    goto :goto_0

    :cond_2
    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const p1, 0x7f070416

    invoke-virtual {p0, p1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p0

    goto :goto_0

    :cond_3
    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const p1, 0x7f070412

    invoke-virtual {p0, p1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p0

    :goto_0
    return p0
.end method

.method private getUnitString()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mUnitString:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string p0, ""

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mUnitString:Ljava/lang/String;

    :goto_0
    return-object p0
.end method

.method private initViews()V
    .locals 4

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f0c00a2

    invoke-static {v0, v1, p0}, Landroid/widget/RelativeLayout;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    const v1, 0x7f09024b

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/RelativeLayout;

    iput-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvParentLayout:Landroid/widget/RelativeLayout;

    const v1, 0x7f0902dc

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iput-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvCurrentValue:Landroid/widget/TextView;

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvCurrentValue:Landroid/widget/TextView;

    invoke-static {}, Lcom/android/camera/customization/TintColor;->tintColor()I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setTextColor(I)V

    const v1, 0x7f0902e9

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iput-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    const v1, 0x7f0902df

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iput-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    const v1, 0x7f090148

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Lcom/android/camera/timerburst/CircleFullImageView;

    iput-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->circleFullImageView:Lcom/android/camera/timerburst/CircleFullImageView;

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->circleFullImageView:Lcom/android/camera/timerburst/CircleFullImageView;

    iget-object v2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContext:Landroid/content/Context;

    const v3, 0x7f060058

    invoke-virtual {v2, v3}, Landroid/content/Context;->getColor(I)I

    move-result v2

    const/4 v3, 0x1

    invoke-virtual {v1, v3, v2}, Lcom/android/camera/timerburst/CircleFullImageView;->updateView(II)V

    const v1, 0x7f090248

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/RelativeLayout;

    iput-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutter:Landroid/widget/RelativeLayout;

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f070417

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v1

    iput v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutterHeight:F

    const v1, 0x7f09024a

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout;

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    return-void
.end method

.method private judgeCorridinate(FF)I
    .locals 9

    const/4 v0, 0x2

    new-array v1, v0, [I

    iget-object v2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutter:Landroid/widget/RelativeLayout;

    invoke-virtual {v2, v1}, Landroid/widget/RelativeLayout;->getLocationOnScreen([I)V

    const/4 v2, 0x0

    aget v3, v1, v2

    int-to-float v3, v3

    const/4 v4, 0x4

    invoke-direct {p0, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v5

    const/high16 v6, 0x40000000    # 2.0f

    div-float/2addr v5, v6

    add-float/2addr v3, v5

    float-to-int v3, v3

    const/4 v5, 0x1

    aget v7, v1, v5

    new-array v8, v0, [F

    int-to-float v3, v3

    aput v3, v8, v2

    int-to-float v3, v7

    aput v3, v8, v5

    iput-object v8, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMin:[F

    invoke-direct {p0, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v3

    iget-object v8, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutter:Landroid/widget/RelativeLayout;

    invoke-virtual {v8}, Landroid/widget/RelativeLayout;->getWidth()I

    move-result v8

    aget v1, v1, v2

    add-int/2addr v8, v1

    int-to-float v1, v8

    invoke-direct {p0, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v8

    div-float/2addr v8, v6

    sub-float/2addr v1, v8

    iget-object v8, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutter:Landroid/widget/RelativeLayout;

    invoke-virtual {v8}, Landroid/widget/RelativeLayout;->getHeight()I

    move-result v8

    add-int/2addr v7, v8

    add-int/lit8 v7, v7, 0x32

    int-to-float v7, v7

    new-array v0, v0, [F

    aput v1, v0, v2

    aput v7, v0, v5

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMax:[F

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMin:[F

    aget v0, v0, v2

    invoke-direct {p0, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v1

    div-float/2addr v1, v6

    sub-float/2addr v0, v1

    cmpg-float v0, v0, p1

    const/4 v1, -0x1

    if-gtz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMax:[F

    aget v0, v0, v2

    invoke-direct {p0, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v4

    div-float/2addr v4, v6

    add-float/2addr v0, v4

    cmpg-float v0, p1, v0

    if-lez v0, :cond_0

    goto :goto_1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMin:[F

    aget v0, v0, v5

    cmpg-float v0, v0, p2

    if-gtz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMax:[F

    aget v0, v0, v5

    cmpg-float p2, p2, v0

    if-lez p2, :cond_2

    :cond_1
    iget-boolean p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    if-nez p2, :cond_2

    return v1

    :cond_2
    new-instance p2, Lcom/android/camera/timerburst/CustomSeekBar$JudgeSegmentResult;

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMax:[F

    aget v0, v0, v2

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMin:[F

    aget v1, v1, v2

    sub-float/2addr v0, v1

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    invoke-direct {p2, p0, v0, v1, v3}, Lcom/android/camera/timerburst/CustomSeekBar$JudgeSegmentResult;-><init>(Lcom/android/camera/timerburst/CustomSeekBar;F[IF)V

    invoke-virtual {p2, p1}, Lcom/android/camera/timerburst/CustomSeekBar$JudgeSegmentResult;->getJudgeValue(F)I

    move-result p1

    iget-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mSeekBarValueListener:Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;

    if-eqz p2, :cond_3

    iget v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mValueEnlargeTimes:I

    invoke-interface {p2, p0, v0, p1}, Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;->currentSeekBarValue(Landroid/view/View;II)V

    :cond_3
    iget-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    aget v0, p2, v2

    if-ge p1, v0, :cond_4

    aget p1, p2, v2

    :cond_4
    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    array-length p2, p0

    sub-int/2addr p2, v5

    aget p0, p0, p2

    if-le p1, p0, :cond_5

    goto :goto_0

    :cond_5
    move p0, p1

    :goto_0
    return p0

    :cond_6
    :goto_1
    return v1
.end method

.method private judgeCorridinatePosition(FF)F
    .locals 2

    new-instance v0, Lcom/android/camera/timerburst/CustomSeekBar$JudgeSegmentResult;

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    invoke-direct {v0, p0, p1, v1, p2}, Lcom/android/camera/timerburst/CustomSeekBar$JudgeSegmentResult;-><init>(Lcom/android/camera/timerburst/CustomSeekBar;F[IF)V

    iget p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    int-to-float p1, p1

    invoke-virtual {v0, p1}, Lcom/android/camera/timerburst/CustomSeekBar$JudgeSegmentResult;->getJudgePosition(F)F

    move-result p1

    float-to-int p2, p1

    invoke-direct {p0, p2}, Lcom/android/camera/timerburst/CustomSeekBar;->updateInnerContainer(I)V

    return p1
.end method

.method private reverseEnlager()V
    .locals 5

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    invoke-virtual {v0}, Landroid/widget/RelativeLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout$LayoutParams;

    iget v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutterHeight:F

    const/4 v2, 0x2

    invoke-direct {p0, v2}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v3

    sub-float/2addr v1, v3

    const/high16 v3, 0x40000000    # 2.0f

    div-float/2addr v1, v3

    iput v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mInnerMarginHorizontal:F

    invoke-direct {p0, v2}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v1

    float-to-int v1, v1

    iput v1, v0, Landroid/widget/RelativeLayout$LayoutParams;->height:I

    iget v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    const/4 v4, 0x0

    aget v3, v3, v4

    if-ne v1, v3, :cond_0

    invoke-direct {p0, v2}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v1

    float-to-int v1, v1

    iput v1, v0, Landroid/widget/RelativeLayout$LayoutParams;->width:I

    :cond_0
    iget v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mInnerMarginHorizontal:F

    float-to-int v2, v1

    float-to-int v1, v1

    invoke-virtual {v0, v2, v4, v1, v4}, Landroid/widget/RelativeLayout$LayoutParams;->setMargins(IIII)V

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    invoke-virtual {v1, v0}, Landroid/widget/RelativeLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f080503

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    invoke-virtual {v0}, Landroid/widget/RelativeLayout;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    check-cast v0, Landroid/graphics/drawable/GradientDrawable;

    invoke-static {}, Lcom/android/camera/customization/TintColor;->tintColor()I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->circleFullImageView:Lcom/android/camera/timerburst/CircleFullImageView;

    const/4 v1, 0x1

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v2, 0x7f060058

    invoke-virtual {p0, v2}, Landroid/content/res/Resources;->getColor(I)I

    move-result p0

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/timerburst/CircleFullImageView;->updateView(II)V

    return-void
.end method

.method private reverseEnlagerAnim()V
    .locals 7

    new-instance v0, Landroid/view/animation/TranslateAnimation;

    const/4 v1, 0x0

    const/high16 v2, -0x3ee00000    # -10.0f

    invoke-direct {v0, v1, v1, v2, v1}, Landroid/view/animation/TranslateAnimation;-><init>(FFFF)V

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveDownAnimator:Landroid/view/animation/TranslateAnimation;

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveDownAnimator:Landroid/view/animation/TranslateAnimation;

    const-wide/16 v1, 0xc8

    invoke-virtual {v0, v1, v2}, Landroid/view/animation/TranslateAnimation;->setDuration(J)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveDownAnimator:Landroid/view/animation/TranslateAnimation;

    new-instance v1, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v1}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/view/animation/TranslateAnimation;->setInterpolator(Landroid/view/animation/Interpolator;)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvParentLayout:Landroid/widget/RelativeLayout;

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveDownAnimator:Landroid/view/animation/TranslateAnimation;

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout;->startAnimation(Landroid/view/animation/Animation;)V

    const/4 v0, 0x2

    new-array v1, v0, [F

    const/4 v2, 0x3

    invoke-direct {p0, v2}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v2

    float-to-int v2, v2

    int-to-float v2, v2

    const/4 v3, 0x0

    aput v2, v1, v3

    invoke-direct {p0, v0}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v2

    float-to-int v2, v2

    int-to-float v2, v2

    const/4 v4, 0x1

    aput v2, v1, v4

    invoke-static {v1}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

    new-instance v2, Lcom/android/camera/timerburst/CustomSeekBar$4;

    invoke-direct {v2, p0}, Lcom/android/camera/timerburst/CustomSeekBar$4;-><init>(Lcom/android/camera/timerburst/CustomSeekBar;)V

    invoke-virtual {v1, v2}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

    const-wide/16 v5, 0x190

    invoke-virtual {v1, v5, v6}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

    new-instance v2, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v2}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v1, v2}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    new-array v1, v0, [F

    invoke-direct {p0, v0}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v2

    float-to-int v2, v2

    div-int/2addr v2, v0

    int-to-float v2, v2

    aput v2, v1, v3

    invoke-direct {p0, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v2

    float-to-int v2, v2

    div-int/2addr v2, v0

    int-to-float v0, v2

    aput v0, v1, v4

    invoke-static {v1}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

    new-instance v1, Lcom/android/camera/timerburst/CustomSeekBar$5;

    invoke-direct {v1, p0}, Lcom/android/camera/timerburst/CustomSeekBar$5;-><init>(Lcom/android/camera/timerburst/CustomSeekBar;)V

    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

    new-instance v1, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v1}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0, v5, v6}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueShrinkAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->start()V

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueShrinkAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {p0}, Landroid/animation/ValueAnimator;->start()V

    return-void
.end method

.method private showOrHideAnim(Landroid/view/View;Z)V
    .locals 0
    .annotation build Landroid/annotation/TargetApi;
        value = 0xb
    .end annotation

    if-eqz p2, :cond_0

    const/4 p0, 0x4

    invoke-virtual {p1, p0}, Landroid/view/View;->setVisibility(I)V

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    invoke-virtual {p1, p0}, Landroid/view/View;->setVisibility(I)V

    :goto_0
    return-void
.end method

.method private startEnlagerAnim()V
    .locals 7

    const-string v0, "CustomSeekBar"

    const-string v1, "startEnlagerAnim"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    new-instance v0, Landroid/view/animation/TranslateAnimation;

    const/4 v1, 0x0

    const/high16 v2, -0x3ee00000    # -10.0f

    invoke-direct {v0, v1, v1, v1, v2}, Landroid/view/animation/TranslateAnimation;-><init>(FFFF)V

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

    const-wide/16 v1, 0xc8

    invoke-virtual {v0, v1, v2}, Landroid/view/animation/TranslateAnimation;->setDuration(J)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

    const/4 v3, 0x1

    invoke-virtual {v0, v3}, Landroid/view/animation/TranslateAnimation;->setFillAfter(Z)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

    new-instance v4, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v4}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v0, v4}, Landroid/view/animation/TranslateAnimation;->setInterpolator(Landroid/view/animation/Interpolator;)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvParentLayout:Landroid/widget/RelativeLayout;

    iget-object v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvMoveUpAnimator:Landroid/view/animation/TranslateAnimation;

    invoke-virtual {v0, v4}, Landroid/widget/RelativeLayout;->startAnimation(Landroid/view/animation/Animation;)V

    const/4 v0, 0x2

    new-array v4, v0, [I

    invoke-direct {p0, v0}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v5

    float-to-int v5, v5

    const/4 v6, 0x0

    aput v5, v4, v6

    const/4 v5, 0x3

    invoke-direct {p0, v5}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v5

    float-to-int v5, v5

    aput v5, v4, v3

    invoke-static {v4}, Landroid/animation/ValueAnimator;->ofInt([I)Landroid/animation/ValueAnimator;

    move-result-object v4

    iput-object v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

    iget-object v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

    new-instance v5, Lcom/android/camera/timerburst/CustomSeekBar$2;

    invoke-direct {v5, p0}, Lcom/android/camera/timerburst/CustomSeekBar$2;-><init>(Lcom/android/camera/timerburst/CustomSeekBar;)V

    invoke-virtual {v4, v5}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    iget-object v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v4, v1, v2}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    iget-object v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

    new-instance v5, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v5}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v4, v5}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    iget-object v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mContainervalueLargerAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v4}, Landroid/animation/ValueAnimator;->start()V

    new-array v4, v0, [I

    invoke-direct {p0, v3}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v5

    float-to-int v5, v5

    div-int/2addr v5, v0

    aput v5, v4, v6

    invoke-direct {p0, v0}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v5

    float-to-int v5, v5

    div-int/2addr v5, v0

    aput v5, v4, v3

    invoke-static {v4}, Landroid/animation/ValueAnimator;->ofInt([I)Landroid/animation/ValueAnimator;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

    new-instance v3, Lcom/android/camera/timerburst/CustomSeekBar$3;

    invoke-direct {v3, p0}, Lcom/android/camera/timerburst/CustomSeekBar$3;-><init>(Lcom/android/camera/timerburst/CustomSeekBar;)V

    invoke-virtual {v0, v3}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0, v1, v2}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

    new-instance v1, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {v1}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleValueLargerAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {p0}, Landroid/animation/ValueAnimator;->start()V

    return-void
.end method

.method private updateInnerContainer(I)V
    .locals 5

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    invoke-virtual {v0}, Landroid/widget/RelativeLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout$LayoutParams;

    iget-boolean v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    if-eqz v1, :cond_0

    const/4 v1, 0x3

    goto :goto_0

    :cond_0
    const/4 v1, 0x2

    :goto_0
    invoke-direct {p0, v1}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v1

    float-to-int v1, v1

    iget v2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    const/4 v4, 0x0

    aget v3, v3, v4

    if-ne v2, v3, :cond_1

    const/4 v4, 0x1

    :cond_1
    if-eqz v4, :cond_2

    goto :goto_1

    :cond_2
    add-int/2addr v1, p1

    :goto_1
    iput v1, v0, Landroid/widget/RelativeLayout$LayoutParams;->width:I

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    invoke-virtual {p0, v0}, Landroid/widget/RelativeLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return-void
.end method

.method private updateTvCurrent(ZF)V
    .locals 9

    iget p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    const/4 v1, 0x0

    aget v0, v0, v1

    const/4 v2, 0x0

    if-ne p1, v0, :cond_0

    move p2, v2

    :cond_0
    const/4 p1, 0x2

    new-array v0, p1, [I

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-virtual {v3, v0}, Landroid/widget/TextView;->getLocationOnScreen([I)V

    aget v0, v0, v1

    int-to-float v0, v0

    iput v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndX:F

    new-array v0, p1, [I

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-virtual {v3, v0}, Landroid/widget/TextView;->getLocationOnScreen([I)V

    new-array v3, p1, [I

    iget-object v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekInnerContainer:Landroid/widget/RelativeLayout;

    invoke-virtual {v4, v3}, Landroid/widget/RelativeLayout;->getLocationOnScreen([I)V

    aget v3, v3, v1

    int-to-float v3, v3

    add-float/2addr v3, p2

    invoke-direct {p0, p1}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result v4

    const/high16 v5, 0x40000000    # 2.0f

    div-float/2addr v4, v5

    add-float/2addr v3, v4

    iput v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleIndexX:F

    iget v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    const/4 v4, 0x1

    const/4 v6, 0x3

    const/4 v7, 0x4

    if-ge v3, v6, :cond_2

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getVisibility()I

    move-result v3

    if-nez v3, :cond_1

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-direct {p0, v3, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->showOrHideAnim(Landroid/view/View;Z)V

    :cond_1
    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getVisibility()I

    move-result v3

    if-ne v3, v7, :cond_6

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-direct {p0, v3, v1}, Lcom/android/camera/timerburst/CustomSeekBar;->showOrHideAnim(Landroid/view/View;Z)V

    goto :goto_0

    :cond_2
    iget v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndX:F

    iget v8, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleIndexX:F

    sub-float/2addr v3, v8

    iget v8, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvWidth:F

    div-float/2addr v8, v5

    cmpg-float v3, v3, v8

    if-gtz v3, :cond_4

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getVisibility()I

    move-result v3

    if-nez v3, :cond_3

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-direct {p0, v3, v4}, Lcom/android/camera/timerburst/CustomSeekBar;->showOrHideAnim(Landroid/view/View;Z)V

    :cond_3
    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getVisibility()I

    move-result v3

    if-ne v3, v7, :cond_6

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-direct {p0, v3, v1}, Lcom/android/camera/timerburst/CustomSeekBar;->showOrHideAnim(Landroid/view/View;Z)V

    goto :goto_0

    :cond_4
    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getVisibility()I

    move-result v3

    if-ne v3, v7, :cond_5

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-direct {p0, v3, v1}, Lcom/android/camera/timerburst/CustomSeekBar;->showOrHideAnim(Landroid/view/View;Z)V

    :cond_5
    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getVisibility()I

    move-result v3

    if-ne v3, v7, :cond_6

    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-direct {p0, v3, v1}, Lcom/android/camera/timerburst/CustomSeekBar;->showOrHideAnim(Landroid/view/View;Z)V

    :cond_6
    :goto_0
    iget-object v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvCurrentValue:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v3

    check-cast v3, Landroid/widget/RelativeLayout$LayoutParams;

    iget v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCircleIndexX:F

    iget v8, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvWidth:F

    div-float/2addr v8, v5

    sub-float/2addr v4, v8

    float-to-int v4, v4

    iget-boolean v5, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    if-eqz v5, :cond_7

    invoke-direct {p0, v6}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result p1

    goto :goto_1

    :cond_7
    invoke-direct {p0, p1}, Lcom/android/camera/timerburst/CustomSeekBar;->getCircleDiameter(I)F

    move-result p1

    :goto_1
    float-to-int p1, p1

    aget v0, v0, v1

    sub-int/2addr v4, v0

    cmpl-float p2, p2, v2

    if-nez p2, :cond_8

    goto :goto_2

    :cond_8
    div-int/lit8 v1, p1, 0x4

    :goto_2
    add-int/2addr v4, v1

    iput v4, v3, Landroid/widget/RelativeLayout$LayoutParams;->leftMargin:I

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvCurrentValue:Landroid/widget/TextView;

    invoke-virtual {p0, v3}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return-void
.end method


# virtual methods
.method public initSeekBarConfig([IIILjava/lang/String;Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;)V
    .locals 2
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "ResourceType"
        }
    .end annotation

    iput-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    iput p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    iput p3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mValueEnlargeTimes:I

    iput-object p4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mUnitString:Ljava/lang/String;

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string p4, "initSeekBarConfig  mInialValue: "

    invoke-virtual {p2, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    invoke-virtual {p2, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p4, "\n valueEnlargeTimes:"

    invoke-virtual {p2, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p3, "\n"

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    const-string p3, "CustomSeekBar"

    invoke-static {p3, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p2, 0x0

    move p4, p2

    :goto_0
    array-length v0, p1

    if-ge p4, v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "index:  "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "   config:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget v1, p1, p4

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p3, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    add-int/lit8 p4, p4, 0x1

    goto :goto_0

    :cond_0
    iput-object p5, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mSeekBarValueListener:Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object p3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    aget p2, p3, p2

    iget p3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mValueEnlargeTimes:I

    mul-int/2addr p2, p3

    invoke-static {p2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->getUnitString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    iget-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvStartValue:Landroid/widget/TextView;

    invoke-virtual {p2, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mConfigArray:[I

    array-length p3, p2

    add-int/lit8 p3, p3, -0x1

    aget p2, p2, p3

    iget p3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mValueEnlargeTimes:I

    mul-int/2addr p2, p3

    invoke-static {p2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->getUnitString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    iget-object p2, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvEndValue:Landroid/widget/TextView;

    invoke-virtual {p2, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvCurrentValue:Landroid/widget/TextView;

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    iget p3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    iget p4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mValueEnlargeTimes:I

    mul-int/2addr p3, p4

    invoke-static {p3}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->getUnitString()Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    move-result-object p1

    new-instance p2, Lcom/android/camera/timerburst/CustomSeekBar$1;

    invoke-direct {p2, p0}, Lcom/android/camera/timerburst/CustomSeekBar$1;-><init>(Lcom/android/camera/timerburst/CustomSeekBar;)V

    invoke-virtual {p1, p2}, Landroid/view/ViewTreeObserver;->addOnGlobalLayoutListener(Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;)V

    return-void
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/widget/RelativeLayout;->onDraw(Landroid/graphics/Canvas;)V

    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 6

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutter:Landroid/widget/RelativeLayout;

    invoke-virtual {v0}, Landroid/widget/RelativeLayout;->getHeight()I

    move-result v0

    int-to-float v0, v0

    iput v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRlSeekOutterHeight:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawX()F

    move-result v0

    iput v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentX:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawY()F

    move-result v0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v1

    iput v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mEventType:I

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result p1

    const-string v1, "CustomSeekBar"

    const/4 v2, 0x1

    const/4 v3, 0x0

    if-eqz p1, :cond_2

    if-eq p1, v2, :cond_1

    const/4 v4, 0x2

    if-eq p1, v4, :cond_0

    :goto_0
    move p1, v3

    goto :goto_2

    :cond_0
    const-string p1, "MotionEvent.ACTION_MOVE"

    invoke-static {v1, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    :cond_1
    const-string p1, "MotionEvent.ACTION_UP"

    invoke-static {v1, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :cond_2
    const-string p1, "MotionEvent.ACTION_DOWN"

    invoke-static {v1, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1
    move p1, v2

    :goto_2
    iget v4, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentX:F

    invoke-direct {p0, v4, v0}, Lcom/android/camera/timerburst/CustomSeekBar;->judgeCorridinate(FF)I

    move-result v0

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, " isEnlargeStateTemp : "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v5, "\n  mIsEnlargeState:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v5, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v5, "\n  typeCor: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v1, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    if-eq p1, v1, :cond_3

    iput-boolean p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mIsEnlargeState:Z

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->doAnim()V

    :cond_3
    iget p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    if-eq p1, v0, :cond_5

    if-lez v0, :cond_5

    iput v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    iget p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentX:F

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mRangeMin:[F

    aget v1, v0, v3

    cmpg-float v1, p1, v1

    if-gtz v1, :cond_4

    invoke-direct {p0, v3}, Lcom/android/camera/timerburst/CustomSeekBar;->updateInnerContainer(I)V

    move p1, v3

    goto :goto_3

    :cond_4
    aget v0, v0, v3

    sub-float/2addr p1, v0

    float-to-int p1, p1

    invoke-direct {p0, p1}, Lcom/android/camera/timerburst/CustomSeekBar;->updateInnerContainer(I)V

    :goto_3
    int-to-float p1, p1

    invoke-direct {p0, v3, p1}, Lcom/android/camera/timerburst/CustomSeekBar;->updateTvCurrent(ZF)V

    iget-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mTvCurrentValue:Landroid/widget/TextView;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget v1, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mCurrentValue:I

    iget v3, p0, Lcom/android/camera/timerburst/CustomSeekBar;->mValueEnlargeTimes:I

    mul-int/2addr v1, v3

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->getUnitString()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p1, p0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_5
    return v2
.end method
