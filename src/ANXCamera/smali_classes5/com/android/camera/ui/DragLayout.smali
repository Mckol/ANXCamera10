.class public Lcom/android/camera/ui/DragLayout;
.super Landroid/widget/FrameLayout;
.source "DragLayout.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/ui/DragLayout$DragAnimationConfig;,
        Lcom/android/camera/ui/DragLayout$Bar;,
        Lcom/android/camera/ui/DragLayout$OnDragListener;,
        Lcom/android/camera/ui/DragLayout$Dependency;
    }
.end annotation


# static fields
.field private static final STATE_DRAGGING:I = 0x1

.field private static final STATE_EXPEND:I = 0x4

.field private static final STATE_IDLE:I = 0x0

.field private static final STATE_SETTLING:I = 0x2

.field private static final STATE_SHRINK:I = 0x3

.field public static final TAG:Ljava/lang/String; = "DragLayout"

.field private static debug:Z = true

.field private static sAnimationConfig:Lcom/android/camera/ui/DragLayout$DragAnimationConfig;


# instance fields
.field private mAnimator:Landroid/animation/ValueAnimator;

.field private mBar:Lcom/android/camera/ui/DragLayout$Bar;

.field private mCatchDrag:Z

.field private mCurDirectionIsUp:Z

.field private final mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

.field private mDragEnable:Z

.field private mDragInterpolator:Landroid/view/animation/Interpolator;

.field private mOffset:F

.field private mOnDragListeners:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/android/camera/ui/DragLayout$OnDragListener;",
            ">;"
        }
    .end annotation
.end field

.field private mSettlingInterpolator:Landroid/view/animation/Interpolator;

.field private mSkipDragUpDown:Z

.field private mStartDirectionIsUp:Z

.field private mStartX:F

.field private mStartY:F

.field private mState:I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1    # Landroid/content/Context;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/ui/DragLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1    # Landroid/content/Context;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Landroid/util/AttributeSet;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/ui/DragLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0
    .param p1    # Landroid/content/Context;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param
    .param p2    # Landroid/util/AttributeSet;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param

    invoke-direct {p0, p1, p2, p3}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    const/4 p2, 0x0

    iput-boolean p2, p0, Lcom/android/camera/ui/DragLayout;->mDragEnable:Z

    iput-boolean p2, p0, Lcom/android/camera/ui/DragLayout;->mSkipDragUpDown:Z

    iput-boolean p2, p0, Lcom/android/camera/ui/DragLayout;->mCatchDrag:Z

    iput p2, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    new-instance p2, Ljava/util/ArrayList;

    invoke-direct {p2}, Ljava/util/ArrayList;-><init>()V

    iput-object p2, p0, Lcom/android/camera/ui/DragLayout;->mOnDragListeners:Ljava/util/List;

    sget-object p2, Lcom/android/camera/ui/DragLayout;->sAnimationConfig:Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    if-nez p2, :cond_0

    new-instance p2, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    invoke-direct {p2, p1}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;-><init>(Landroid/content/Context;)V

    sput-object p2, Lcom/android/camera/ui/DragLayout;->sAnimationConfig:Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    :cond_0
    new-instance p1, Lcom/android/camera/ui/DragLayout$Dependency;

    const/4 p2, 0x0

    invoke-direct {p1, p0, p2}, Lcom/android/camera/ui/DragLayout$Dependency;-><init>(Lcom/android/camera/ui/DragLayout;Lcom/android/camera/ui/DragLayout$1;)V

    iput-object p1, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    new-instance p1, Landroid/view/animation/LinearInterpolator;

    invoke-direct {p1}, Landroid/view/animation/LinearInterpolator;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/DragLayout;->mDragInterpolator:Landroid/view/animation/Interpolator;

    new-instance p1, Lmiuix/view/animation/CubicEaseOutInterpolator;

    invoke-direct {p1}, Lmiuix/view/animation/CubicEaseOutInterpolator;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/DragLayout;->mSettlingInterpolator:Landroid/view/animation/Interpolator;

    new-instance p1, Landroid/animation/ValueAnimator;

    invoke-direct {p1}, Landroid/animation/ValueAnimator;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    iget-object p2, p0, Lcom/android/camera/ui/DragLayout;->mSettlingInterpolator:Landroid/view/animation/Interpolator;

    invoke-virtual {p1, p2}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    new-instance p2, Lcom/android/camera/ui/b;

    invoke-direct {p2, p0}, Lcom/android/camera/ui/b;-><init>(Lcom/android/camera/ui/DragLayout;)V

    invoke-virtual {p1, p2}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    new-instance p2, Lcom/android/camera/ui/DragLayout$1;

    invoke-direct {p2, p0}, Lcom/android/camera/ui/DragLayout$1;-><init>(Lcom/android/camera/ui/DragLayout;)V

    invoke-virtual {p1, p2}, Landroid/animation/ValueAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    return-void
.end method

.method private LogD(Ljava/lang/String;)V
    .locals 0

    sget-object p0, Lcom/android/camera/ui/DragLayout;->TAG:Ljava/lang/String;

    invoke-static {p0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private LogV(Ljava/lang/String;)V
    .locals 0

    sget-boolean p0, Lcom/android/camera/ui/DragLayout;->debug:Z

    if-eqz p0, :cond_0

    sget-object p0, Lcom/android/camera/ui/DragLayout;->TAG:Ljava/lang/String;

    invoke-static {p0, p1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    return-void
.end method

.method static synthetic access$100(Lcom/android/camera/ui/DragLayout;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    return p0
.end method

.method static synthetic access$200(Lcom/android/camera/ui/DragLayout;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    return p0
.end method

.method static synthetic access$202(Lcom/android/camera/ui/DragLayout;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    return p1
.end method

.method static synthetic access$300(Lcom/android/camera/ui/DragLayout;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    return p0
.end method

.method static synthetic access$302(Lcom/android/camera/ui/DragLayout;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    return p1
.end method

.method static synthetic access$400(Lcom/android/camera/ui/DragLayout;)Lcom/android/camera/ui/DragLayout$Dependency;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    return-object p0
.end method

.method static synthetic access$500(Lcom/android/camera/ui/DragLayout;I)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/ui/DragLayout;->setState(I)V

    return-void
.end method

.method static synthetic access$700(Lcom/android/camera/ui/DragLayout;)Ljava/util/List;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout;->mOnDragListeners:Ljava/util/List;

    return-object p0
.end method

.method static synthetic access$800(Lcom/android/camera/ui/DragLayout;Ljava/lang/String;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$900(Lcom/android/camera/ui/DragLayout;)Lcom/android/camera/ui/DragLayout$Bar;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    return-object p0
.end method

.method private ensureView()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    if-nez v0, :cond_1

    new-instance v0, Lcom/android/camera/ui/DragLayout$Bar;

    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/android/camera/ui/DragLayout$Bar;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    new-instance v0, Landroid/widget/FrameLayout$LayoutParams;

    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f07035a

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v1

    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    int-to-float v2, v2

    const v3, 0x3f19999a    # 0.6f

    mul-float/2addr v2, v3

    float-to-int v2, v2

    invoke-direct {v0, v1, v2}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    const/4 v1, 0x1

    iput v1, v0, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f070358

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v2

    iput v2, v0, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    iget-object v2, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    invoke-virtual {p0, v2, v1, v0}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;ILandroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    iget-boolean v1, p0, Lcom/android/camera/ui/DragLayout;->mDragEnable:Z

    if-eqz v1, :cond_0

    const/4 v1, 0x0

    goto :goto_0

    :cond_0
    const/16 v1, 0x8

    :goto_0
    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    :cond_1
    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-nez v0, :cond_2

    const/4 v0, 0x3

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->setState(I)V

    :cond_2
    return-void
.end method

.method public static getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;
    .locals 1

    sget-object v0, Lcom/android/camera/ui/DragLayout;->sAnimationConfig:Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    return-object v0
.end method

.method private onDrag(Landroid/view/MotionEvent;)Z
    .locals 6

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onDrag skip ?"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/android/camera/ui/DragLayout;->mSkipDragUpDown:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", mState "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    const/4 v1, 0x3

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x1

    if-eq v0, v1, :cond_8

    const/4 v5, 0x4

    if-ne v0, v5, :cond_0

    goto :goto_0

    :cond_0
    if-ne v0, v4, :cond_7

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawY()F

    move-result p1

    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mStartY:F

    sub-float/2addr p1, v0

    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    if-eqz v0, :cond_1

    cmpl-float v0, p1, v3

    if-lez v0, :cond_1

    return v4

    :cond_1
    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    if-nez v0, :cond_2

    cmpg-float v0, p1, v3

    if-gez v0, :cond_2

    return v4

    :cond_2
    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v1

    cmpg-float v0, v0, v1

    if-gez v0, :cond_6

    invoke-static {p1}, Ljava/lang/Math;->abs(F)F

    move-result v0

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v1

    invoke-static {v0, v1}, Ljava/lang/Math;->min(FF)F

    move-result v0

    invoke-static {v0, v3}, Ljava/lang/Math;->max(FF)F

    move-result v0

    iget-object v1, p0, Lcom/android/camera/ui/DragLayout;->mDragInterpolator:Landroid/view/animation/Interpolator;

    invoke-interface {v1, v0}, Landroid/view/animation/Interpolator;->getInterpolation(F)F

    move-result v0

    cmpg-float p1, p1, v3

    if-gez p1, :cond_3

    neg-float v0, v0

    :cond_3
    iget p1, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    cmpl-float v1, v0, p1

    if-eqz v1, :cond_5

    cmpg-float p1, v0, p1

    if-gez p1, :cond_4

    move v2, v4

    :cond_4
    iput-boolean v2, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    :cond_5
    iput v0, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    iget p0, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    float-to-int p0, p0

    invoke-virtual {p1, p0}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragProgress(I)V

    :cond_6
    return v4

    :cond_7
    return v2

    :cond_8
    :goto_0
    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-ne v0, v1, :cond_9

    move v2, v4

    :cond_9
    iput-boolean v2, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    iput-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    iget-object v1, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    invoke-virtual {v1, v0}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragStart(Z)V

    invoke-direct {p0, v4}, Lcom/android/camera/ui/DragLayout;->setState(I)V

    iput v3, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawY()F

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/DragLayout;->mStartY:F

    return v4
.end method

.method private setState(I)V
    .locals 2

    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-eq v0, p1, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setState "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogD(Ljava/lang/String;)V

    iput p1, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    :cond_0
    return-void
.end method


# virtual methods
.method public addOnDragListener(Lcom/android/camera/ui/DragLayout$OnDragListener;)V
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "addOnDragListener "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mOnDragListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout;->mOnDragListeners:Ljava/util/List;

    invoke-interface {p0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method public synthetic b(Landroid/animation/ValueAnimator;)V
    .locals 2

    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    const/4 v1, 0x2

    if-eq v0, v1, :cond_0

    return-void

    :cond_0
    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->getAnimatedValue()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Float;

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    iget p0, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    float-to-int p0, p0

    invoke-virtual {p1, p0}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragProgress(I)V

    return-void
.end method

.method public dispatchTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 10

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "dispatchTouchEvent "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mDragEnable:Z

    if-nez v0, :cond_0

    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0

    :cond_0
    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    const/4 v1, 0x2

    const/4 v2, 0x1

    if-ne v0, v1, :cond_1

    return v2

    :cond_1
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v0

    const/4 v3, -0x1

    const/4 v4, 0x4

    const/4 v5, 0x3

    const/4 v6, 0x0

    if-eqz v0, :cond_17

    const/4 v7, 0x0

    if-eq v0, v2, :cond_11

    if-eq v0, v1, :cond_2

    goto/16 :goto_6

    :cond_2
    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mSkipDragUpDown:Z

    if-eqz v0, :cond_3

    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0

    :cond_3
    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mCatchDrag:Z

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    invoke-virtual {v0}, Lcom/android/camera/ui/DragLayout$Dependency;->onInterceptDrag()Z

    move-result v0

    if-eqz v0, :cond_4

    const-string v0, "skip drag caz dependency intercept."

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iput-boolean v2, p0, Lcom/android/camera/ui/DragLayout;->mSkipDragUpDown:Z

    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0

    :cond_4
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawX()F

    move-result v0

    iget v8, p0, Lcom/android/camera/ui/DragLayout;->mStartX:F

    sub-float/2addr v0, v8

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawY()F

    move-result v8

    iget v9, p0, Lcom/android/camera/ui/DragLayout;->mStartY:F

    sub-float/2addr v8, v9

    new-array v1, v1, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v9

    aput-object v9, v1, v6

    invoke-static {v8}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v9

    aput-object v9, v1, v2

    const-string v9, "onMove distanceX = %s distanceY = %s"

    invoke-static {v9, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-direct {p0, v1}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iget v1, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-eq v1, v5, :cond_5

    if-ne v1, v4, :cond_6

    :cond_5
    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v1

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v9

    invoke-virtual {v9}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getDragThreshold()F

    move-result v9

    cmpg-float v1, v1, v9

    if-gez v1, :cond_6

    invoke-static {v8}, Ljava/lang/Math;->abs(F)F

    move-result v1

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v9

    invoke-virtual {v9}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getDragThreshold()F

    move-result v9

    cmpg-float v1, v1, v9

    if-gez v1, :cond_6

    return v2

    :cond_6
    iget v1, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-ne v1, v5, :cond_9

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    invoke-static {v8}, Ljava/lang/Math;->abs(F)F

    move-result v1

    cmpl-float v0, v0, v1

    if-gtz v0, :cond_7

    cmpl-float v0, v8, v7

    if-lez v0, :cond_9

    :cond_7
    iput-boolean v2, p0, Lcom/android/camera/ui/DragLayout;->mSkipDragUpDown:Z

    const-string v0, "skip drag up."

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogD(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    if-eqz v0, :cond_8

    invoke-static {v0, v6}, Lcom/android/camera/ui/DragLayout$Bar;->access$600(Lcom/android/camera/ui/DragLayout$Bar;I)Z

    :cond_8
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0

    :cond_9
    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-ne v0, v4, :cond_b

    cmpl-float v0, v8, v7

    const-string v1, "skip drag down."

    if-nez v0, :cond_a

    invoke-direct {p0, v1}, Lcom/android/camera/ui/DragLayout;->LogD(Ljava/lang/String;)V

    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0

    :cond_a
    cmpg-float v0, v8, v7

    if-gez v0, :cond_b

    iput-boolean v2, p0, Lcom/android/camera/ui/DragLayout;->mSkipDragUpDown:Z

    invoke-direct {p0, v1}, Lcom/android/camera/ui/DragLayout;->LogD(Ljava/lang/String;)V

    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0

    :cond_b
    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    if-eqz v0, :cond_e

    iget v1, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-eq v1, v5, :cond_d

    if-eq v1, v4, :cond_c

    goto :goto_0

    :cond_c
    invoke-static {v0, v3}, Lcom/android/camera/ui/DragLayout$Bar;->access$600(Lcom/android/camera/ui/DragLayout$Bar;I)Z

    goto :goto_0

    :cond_d
    invoke-static {v0, v2}, Lcom/android/camera/ui/DragLayout$Bar;->access$600(Lcom/android/camera/ui/DragLayout$Bar;I)Z

    :cond_e
    :goto_0
    invoke-direct {p0, p1}, Lcom/android/camera/ui/DragLayout;->onDrag(Landroid/view/MotionEvent;)Z

    move-result v0

    if-nez v0, :cond_10

    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    if-eqz p0, :cond_f

    goto :goto_1

    :cond_f
    move v2, v6

    :cond_10
    :goto_1
    return v2

    :cond_11
    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    if-eqz v0, :cond_12

    invoke-static {v0, v6}, Lcom/android/camera/ui/DragLayout$Bar;->access$600(Lcom/android/camera/ui/DragLayout$Bar;I)Z

    :cond_12
    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-ne v0, v2, :cond_1b

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "onUp "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v3, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, " "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v3, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, ", mOffset = "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v3

    cmpg-float v0, v0, v3

    if-gez v0, :cond_15

    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mStartDirectionIsUp:Z

    iget-boolean v3, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    if-ne v0, v3, :cond_14

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v0

    iget v3, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    invoke-static {v3}, Ljava/lang/Math;->abs(F)F

    move-result v3

    sub-float/2addr v0, v3

    invoke-static {v7, v0}, Ljava/lang/Math;->max(FF)F

    move-result v0

    iget-object v3, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    new-array v4, v1, [F

    iget v5, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    aput v5, v4, v6

    iget-boolean v5, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    if-eqz v5, :cond_13

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v5

    invoke-virtual {v5}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v5

    neg-float v5, v5

    goto :goto_2

    :cond_13
    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v5

    invoke-virtual {v5}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v5

    :goto_2
    aput v5, v4, v2

    invoke-virtual {v3, v4}, Landroid/animation/ValueAnimator;->setFloatValues([F)V

    iget-object v3, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getDuration()I

    move-result v4

    int-to-float v4, v4

    mul-float/2addr v4, v0

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v0

    div-float/2addr v4, v0

    float-to-long v4, v4

    invoke-virtual {v3, v4, v5}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    goto :goto_3

    :cond_14
    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    new-array v3, v1, [F

    iget v4, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    aput v4, v3, v6

    aput v7, v3, v2

    invoke-virtual {v0, v3}, Landroid/animation/ValueAnimator;->setFloatValues([F)V

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getDuration()I

    move-result v3

    int-to-float v3, v3

    iget v4, p0, Lcom/android/camera/ui/DragLayout;->mOffset:F

    invoke-static {v4}, Ljava/lang/Math;->abs(F)F

    move-result v4

    mul-float/2addr v3, v4

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v4

    div-float/2addr v3, v4

    float-to-long v3, v3

    invoke-virtual {v0, v3, v4}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    :goto_3
    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->start()V

    invoke-direct {p0, v1}, Lcom/android/camera/ui/DragLayout;->setState(I)V

    goto :goto_5

    :cond_15
    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    iget-boolean v1, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    invoke-virtual {v0, v1}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragDone(Z)V

    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mCurDirectionIsUp:Z

    if-eqz v0, :cond_16

    goto :goto_4

    :cond_16
    move v4, v5

    :goto_4
    invoke-direct {p0, v4}, Lcom/android/camera/ui/DragLayout;->setState(I)V

    :goto_5
    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mCatchDrag:Z

    if-nez v0, :cond_1b

    return v2

    :cond_17
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onDown "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iput-boolean v6, p0, Lcom/android/camera/ui/DragLayout;->mSkipDragUpDown:Z

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawX()F

    move-result v0

    iput v0, p0, Lcom/android/camera/ui/DragLayout;->mStartX:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getRawY()F

    move-result v0

    iput v0, p0, Lcom/android/camera/ui/DragLayout;->mStartY:F

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    iget v1, p0, Lcom/android/camera/ui/DragLayout;->mStartX:F

    float-to-int v1, v1

    iget v6, p0, Lcom/android/camera/ui/DragLayout;->mStartY:F

    float-to-int v6, v6

    invoke-virtual {v0, v1, v6}, Lcom/android/camera/ui/DragLayout$Dependency;->catchDrag(II)Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mCatchDrag:Z

    iget-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mCatchDrag:Z

    if-eqz v0, :cond_18

    const-string v0, "dependency wanna catch drag event."

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    :cond_18
    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    iget v1, p0, Lcom/android/camera/ui/DragLayout;->mStartX:F

    float-to-int v1, v1

    iget v6, p0, Lcom/android/camera/ui/DragLayout;->mStartY:F

    float-to-int v6, v6

    invoke-virtual {v0, v1, v6}, Lcom/android/camera/ui/DragLayout$Dependency;->showPress(II)Z

    move-result v0

    if-eqz v0, :cond_1b

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    if-eqz v0, :cond_1b

    iget v1, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    if-eq v1, v5, :cond_1a

    if-eq v1, v4, :cond_19

    goto :goto_6

    :cond_19
    invoke-static {v0, v3}, Lcom/android/camera/ui/DragLayout$Bar;->access$600(Lcom/android/camera/ui/DragLayout$Bar;I)Z

    goto :goto_6

    :cond_1a
    invoke-static {v0, v2}, Lcom/android/camera/ui/DragLayout$Bar;->access$600(Lcom/android/camera/ui/DragLayout$Bar;I)Z

    :cond_1b
    :goto_6
    invoke-super {p0, p1}, Landroid/widget/FrameLayout;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0
.end method

.method public isExpanded()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    const/4 v0, 0x4

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isShrink()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    const/4 v0, 0x3

    if-eq p0, v0, :cond_1

    if-nez p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method protected onFinishInflate()V
    .locals 0

    invoke-super {p0}, Landroid/widget/FrameLayout;->onFinishInflate()V

    return-void
.end method

.method protected onLayout(ZIIII)V
    .locals 0

    invoke-super/range {p0 .. p5}, Landroid/widget/FrameLayout;->onLayout(ZIIII)V

    return-void
.end method

.method protected onMeasure(II)V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/ui/DragLayout;->ensureView()V

    invoke-super {p0, p1, p2}, Landroid/widget/FrameLayout;->onMeasure(II)V

    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/ui/DragLayout;->mDragEnable:Z

    return p0
.end method

.method public removeOnDragListener(Lcom/android/camera/ui/DragLayout$OnDragListener;)V
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "removeOnDragListener "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/DragLayout;->LogV(Ljava/lang/String;)V

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout;->mOnDragListeners:Ljava/util/List;

    invoke-interface {p0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    return-void
.end method

.method public reset()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragStart(Z)V

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    invoke-virtual {v0, v1}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragProgress(I)V

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mDependency:Lcom/android/camera/ui/DragLayout$Dependency;

    invoke-virtual {v0, v1}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragDone(Z)V

    invoke-direct {p0, v1}, Lcom/android/camera/ui/DragLayout;->setState(I)V

    return-void
.end method

.method public setDragEnable(Z)V
    .locals 4

    invoke-static {}, Lcom/android/camera/CameraSettings;->getMoreModeStyle()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eq v0, v2, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    move v0, p1

    :goto_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/data/data/global/DataItemGlobal;->isNormalIntent()Z

    move-result v2

    if-nez v2, :cond_1

    move v0, v1

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/data/data/global/DataItemGlobal;->getComponentModuleList()Lcom/android/camera/data/data/global/ComponentModuleList;

    move-result-object v2

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->getActiveModuleIndex()I

    move-result v3

    invoke-virtual {v2, v3}, Lcom/android/camera/data/data/global/ComponentModuleList;->isCommonMode(I)Z

    move-result v2

    if-nez v2, :cond_2

    move v0, v1

    :cond_2
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "setDragEnable : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p1, ", force : "

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/android/camera/ui/DragLayout;->LogD(Ljava/lang/String;)V

    iget-boolean p1, p0, Lcom/android/camera/ui/DragLayout;->mDragEnable:Z

    if-eq p1, v0, :cond_4

    iput-boolean v0, p0, Lcom/android/camera/ui/DragLayout;->mDragEnable:Z

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout;->mBar:Lcom/android/camera/ui/DragLayout$Bar;

    if-eqz p1, :cond_4

    iget-boolean p0, p0, Lcom/android/camera/ui/DragLayout;->mDragEnable:Z

    if-eqz p0, :cond_3

    goto :goto_1

    :cond_3
    const/16 v1, 0x8

    :goto_1
    invoke-virtual {p1, v1}, Landroid/view/View;->setVisibility(I)V

    :cond_4
    return-void
.end method

.method public shrink(Z)Z
    .locals 4

    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    const/4 v1, 0x0

    const/4 v2, 0x4

    if-eq v0, v2, :cond_0

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "shrink fail, state error. now state :"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v0, p0, Lcom/android/camera/ui/DragLayout;->mState:I

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/android/camera/ui/DragLayout;->LogD(Ljava/lang/String;)V

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    const/4 v2, 0x2

    new-array v2, v2, [F

    const/4 v3, 0x0

    aput v3, v2, v1

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getTotalDragDistance()F

    move-result v1

    const/4 v3, 0x1

    aput v1, v2, v3

    invoke-virtual {v0, v2}, Landroid/animation/ValueAnimator;->setFloatValues([F)V

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    if-eqz p1, :cond_1

    invoke-static {}, Lcom/android/camera/ui/DragLayout;->getAnimationConfig()Lcom/android/camera/ui/DragLayout$DragAnimationConfig;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/ui/DragLayout$DragAnimationConfig;->getDuration()I

    move-result p1

    int-to-long v1, p1

    goto :goto_0

    :cond_1
    const-wide/16 v1, 0x0

    :goto_0
    invoke-virtual {v0, v1, v2}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout;->mAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {p0}, Landroid/animation/ValueAnimator;->start()V

    return v3
.end method
