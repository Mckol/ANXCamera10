.class public Lcom/android/camera/wideselfie/WideSelfieConfig;
.super Ljava/lang/Object;
.source "WideSelfieConfig.java"


# static fields
.field private static final INSTANCE:Ljava/util/concurrent/atomic/AtomicReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/atomic/AtomicReference<",
            "Lcom/android/camera/wideselfie/WideSelfieConfig;",
            ">;"
        }
    .end annotation
.end field

.field public static final TAG:Ljava/lang/String; = "WideSelfieConstants"

.field public static final THUMB_BORDER:I = 0x1

.field private static final UI_RATE:F = 1.3333334f


# instance fields
.field private mStillPreviewHeight:I

.field private mStillPreviewWidth:I

.field private mThumbBgHeight:I

.field private mThumbBgHeightVertical:I

.field private mThumbBgTopMargin:I

.field private mThumbBgTopMarginVertical:I

.field private mThumbBgWidth:I

.field private mThumbBgWidthVertical:I

.field private mThumbViewHeight:I

.field private mThumbViewHeightVertical:I

.field private mThumbViewTopMargin:I

.field private mThumbViewTopMarginVertical:I

.field private mThumbViewWidth:I

.field private mThumbViewWidthVertical:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Ljava/util/concurrent/atomic/AtomicReference;

    invoke-direct {v0}, Ljava/util/concurrent/atomic/AtomicReference;-><init>()V

    sput-object v0, Lcom/android/camera/wideselfie/WideSelfieConfig;->INSTANCE:Ljava/util/concurrent/atomic/AtomicReference;

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    invoke-virtual {p0, p1}, Lcom/android/camera/wideselfie/WideSelfieConfig;->init(Landroid/content/Context;)V

    return-void
.end method

.method public static getInstance(Landroid/content/Context;)Lcom/android/camera/wideselfie/WideSelfieConfig;
    .locals 3

    :cond_0
    sget-object v0, Lcom/android/camera/wideselfie/WideSelfieConfig;->INSTANCE:Ljava/util/concurrent/atomic/AtomicReference;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/wideselfie/WideSelfieConfig;

    if-eqz v0, :cond_1

    return-object v0

    :cond_1
    new-instance v0, Lcom/android/camera/wideselfie/WideSelfieConfig;

    invoke-direct {v0, p0}, Lcom/android/camera/wideselfie/WideSelfieConfig;-><init>(Landroid/content/Context;)V

    sget-object v1, Lcom/android/camera/wideselfie/WideSelfieConfig;->INSTANCE:Ljava/util/concurrent/atomic/AtomicReference;

    const/4 v2, 0x0

    invoke-virtual {v1, v2, v0}, Ljava/util/concurrent/atomic/AtomicReference;->compareAndSet(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    return-object v0
.end method


# virtual methods
.method public getStillPreviewHeight()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewHeight:I

    return p0
.end method

.method public getStillPreviewWidth()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewWidth:I

    return p0
.end method

.method public getThumbBgHeight()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgHeight:I

    return p0
.end method

.method public getThumbBgHeightVertical()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgHeightVertical:I

    return p0
.end method

.method public getThumbBgTopMargin()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgTopMargin:I

    return p0
.end method

.method public getThumbBgTopMarginVertical()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgTopMarginVertical:I

    return p0
.end method

.method public getThumbBgWidth()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgWidth:I

    return p0
.end method

.method public getThumbBgWidthVertical()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgWidthVertical:I

    return p0
.end method

.method public getThumbViewHeight()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewHeight:I

    return p0
.end method

.method public getThumbViewHeightVertical()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewHeightVertical:I

    return p0
.end method

.method public getThumbViewTopMargin()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewTopMargin:I

    return p0
.end method

.method public getThumbViewTopMarginVertical()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewTopMarginVertical:I

    return p0
.end method

.method public getThumbViewWidth()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewWidth:I

    return p0
.end method

.method public getThumbViewWidthVertical()I
    .locals 0

    iget p0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewWidthVertical:I

    return p0
.end method

.method public init(Landroid/content/Context;)V
    .locals 3

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f0704f6

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelOffset(I)I

    move-result v0

    iput v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewWidth:I

    iget v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewWidth:I

    int-to-float v0, v0

    const v1, 0x3faaaaab

    mul-float/2addr v0, v1

    float-to-int v0, v0

    iput v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewHeight:I

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "mStillPreviewWidth "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewWidth:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", mStillPreviewHeight = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewHeight:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "WideSelfieConstants"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v0, 0x7f0704f3

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    add-int/lit8 v0, v0, 0x2

    iput v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgWidth:I

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mThumbBgWidth "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgWidth:I

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, ", mStillPreviewWidth = "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewWidth:I

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const v0, 0x7f0704ef

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    add-int/lit8 v0, v0, 0x2

    iput v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgHeight:I

    const v0, 0x7f0704f1

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    add-int/lit8 v0, v0, -0x1

    iput v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgTopMargin:I

    const v0, 0x7f0704f4

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    add-int/lit8 v0, v0, 0x2

    iput v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgWidthVertical:I

    const v0, 0x7f0704f0

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    add-int/lit8 v0, v0, 0x2

    iput v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgHeightVertical:I

    const v0, 0x7f0704f2

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    add-int/lit8 p1, p1, -0x1

    iput p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgTopMarginVertical:I

    iget p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgWidth:I

    iget v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewWidth:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewWidth:I

    iget p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgHeight:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewHeight:I

    iget p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgTopMargin:I

    add-int/lit8 p1, p1, 0x1

    div-int/lit8 v0, v0, 0x2

    sub-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewTopMargin:I

    iget p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgWidthVertical:I

    iget v0, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mStillPreviewHeight:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewWidthVertical:I

    iget p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgHeightVertical:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewHeightVertical:I

    iget p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbBgTopMarginVertical:I

    add-int/lit8 p1, p1, 0x1

    div-int/lit8 v0, v0, 0x2

    sub-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/wideselfie/WideSelfieConfig;->mThumbViewTopMarginVertical:I

    return-void
.end method
