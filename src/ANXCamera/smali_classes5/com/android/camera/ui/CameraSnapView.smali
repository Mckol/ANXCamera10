.class public Lcom/android/camera/ui/CameraSnapView;
.super Landroid/view/View;
.source "CameraSnapView.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/ui/CameraSnapView$SnapListener;,
        Lcom/android/camera/ui/CameraSnapView$SnapEvent;
    }
.end annotation


# static fields
.field private static final LONG_PRESS_TIME:I = 0x320

.field private static final MSG_START_CLICK:I = 0x1

.field private static final MSG_START_LONG_PRESS:I = 0x2

.field private static final MSG_START_LONG_PRESS_CANCEL_IN:I = 0x4

.field private static final MSG_START_LONG_PRESS_CANCEL_OUT:I = 0x3

.field private static final RECT_WIDTH:F = 0.75f

.field private static final TAG:Ljava/lang/String; = "CameraSnapView"


# instance fields
.field private cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

.field private mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

.field private mCurrentMode:I

.field private mEnableSnapClick:Z

.field private mExtraBitmap:Landroid/graphics/Bitmap;

.field private mExtraBitmapMatrix:Landroid/graphics/Matrix;

.field private mExtraBitmapPaint:Landroid/graphics/Paint;

.field private mHandler:Landroid/os/Handler;

.field private mHasCancelByOutside:Z

.field private mHeight:I

.field private mLongPressTime:I

.field private mMissTaken:Z

.field private mPressDownTime:J

.field private mPressUpTime:J

.field private mSnapListener:Lcom/android/camera/ui/CameraSnapView$SnapListener;

.field private mWidth:I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mEnableSnapClick:Z

    const/16 p1, 0x320

    iput p1, p0, Lcom/android/camera/ui/CameraSnapView;->mLongPressTime:I

    new-instance p1, Lcom/android/camera/ui/CameraSnapView$1;

    invoke-direct {p1, p0}, Lcom/android/camera/ui/CameraSnapView$1;-><init>(Lcom/android/camera/ui/CameraSnapView;)V

    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHasCancelByOutside:Z

    invoke-direct {p0}, Lcom/android/camera/ui/CameraSnapView;->initView()V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mEnableSnapClick:Z

    const/16 p1, 0x320

    iput p1, p0, Lcom/android/camera/ui/CameraSnapView;->mLongPressTime:I

    new-instance p1, Lcom/android/camera/ui/CameraSnapView$1;

    invoke-direct {p1, p0}, Lcom/android/camera/ui/CameraSnapView$1;-><init>(Lcom/android/camera/ui/CameraSnapView;)V

    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHasCancelByOutside:Z

    invoke-direct {p0}, Lcom/android/camera/ui/CameraSnapView;->initView()V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mEnableSnapClick:Z

    const/16 p1, 0x320

    iput p1, p0, Lcom/android/camera/ui/CameraSnapView;->mLongPressTime:I

    new-instance p1, Lcom/android/camera/ui/CameraSnapView$1;

    invoke-direct {p1, p0}, Lcom/android/camera/ui/CameraSnapView$1;-><init>(Lcom/android/camera/ui/CameraSnapView;)V

    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHasCancelByOutside:Z

    invoke-direct {p0}, Lcom/android/camera/ui/CameraSnapView;->initView()V

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/ui/CameraSnapView;)Lcom/android/camera/ui/CameraSnapView$SnapListener;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->mSnapListener:Lcom/android/camera/ui/CameraSnapView$SnapListener;

    return-object p0
.end method

.method static synthetic access$102(Lcom/android/camera/ui/CameraSnapView;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHasCancelByOutside:Z

    return p1
.end method

.method private initView()V
    .locals 0

    return-void
.end method

.method private onTouchDownDefaultHandler(I)Z
    .locals 4

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->mSnapListener:Lcom/android/camera/ui/CameraSnapView$SnapListener;

    invoke-interface {v0}, Lcom/android/camera/ui/CameraSnapView$SnapListener;->canSnap()Z

    move-result v0

    const/4 v1, 0x1

    if-nez v0, :cond_1

    iget-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mMissTaken:Z

    if-nez p1, :cond_0

    iput-boolean v1, p0, Lcom/android/camera/ui/CameraSnapView;->mMissTaken:Z

    iget-wide v0, p0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    const-wide/16 v2, 0x0

    cmp-long p1, v0, v2

    if-lez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mSnapListener:Lcom/android/camera/ui/CameraSnapView$SnapListener;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-wide v2, p0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    sub-long/2addr v0, v2

    invoke-interface {p1, v0, v1}, Lcom/android/camera/ui/CameraSnapView$SnapListener;->onTrackSnapMissTaken(J)V

    :cond_0
    sget-object p0, Lcom/android/camera/ui/CameraSnapView;->TAG:Ljava/lang/String;

    const-string p1, "can not snap"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x0

    return p0

    :cond_1
    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;->getInstance(Landroid/content/Context;)Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;->performSnapClick()V

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleDownAnimation(I)V

    return v1
.end method

.method private recycleBitmap()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmap:Landroid/graphics/Bitmap;

    :cond_0
    return-void
.end method

.method private setImageBitmap(Landroid/graphics/Bitmap;)V
    .locals 5

    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    move-result v0

    int-to-float v0, v0

    const/high16 v1, 0x3f400000    # 0.75f

    mul-float/2addr v0, v1

    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    move-result v2

    int-to-float v2, v2

    mul-float/2addr v2, v1

    div-float v1, v0, v2

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v3

    int-to-float v3, v3

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v4

    int-to-float v4, v4

    div-float/2addr v3, v4

    cmpl-float v1, v1, v3

    if-lez v1, :cond_0

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getHeight()I

    move-result p1

    int-to-float p1, p1

    div-float p1, v2, p1

    goto :goto_0

    :cond_0
    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getWidth()I

    move-result p1

    int-to-float p1, p1

    div-float p1, v0, p1

    :goto_0
    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    iput-object v1, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapMatrix:Landroid/graphics/Matrix;

    iget-object v1, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapMatrix:Landroid/graphics/Matrix;

    invoke-virtual {v1, p1, p1}, Landroid/graphics/Matrix;->postScale(FF)Z

    iget-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapMatrix:Landroid/graphics/Matrix;

    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    move-result v1

    int-to-float v1, v1

    sub-float/2addr v1, v0

    const/high16 v0, 0x40000000    # 2.0f

    div-float/2addr v1, v0

    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    move-result v3

    int-to-float v3, v3

    sub-float/2addr v3, v2

    div-float/2addr v3, v0

    invoke-virtual {p1, v1, v3}, Landroid/graphics/Matrix;->postTranslate(FF)Z

    new-instance p1, Landroid/graphics/Paint;

    invoke-direct {p1}, Landroid/graphics/Paint;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapPaint:Landroid/graphics/Paint;

    iget-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapPaint:Landroid/graphics/Paint;

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapPaint:Landroid/graphics/Paint;

    invoke-virtual {p0, v0}, Landroid/graphics/Paint;->setFilterBitmap(Z)V

    return-void
.end method


# virtual methods
.method public addSegmentNow()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->addSegmentNow()V

    return-void
.end method

.method public directFinishRecord()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->directFinishRecord()V

    return-void
.end method

.method public hasSegments()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->hasSegments()Z

    move-result p0

    return p0
.end method

.method public hideCirclePaintItem()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->hideCirclePaintItem()V

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    return-void
.end method

.method public hidePaintCenterVVItem()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->hidePaintCenterVVItem()V

    return-void
.end method

.method public hideRoundPaintItem()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->hideRoundPaintItem()V

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    return-void
.end method

.method public inRegion(II)Z
    .locals 2

    sget-object v0, Landroid/os/Build;->DEVICE:Ljava/lang/String;

    const-string v1, "hercules"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    add-int/lit8 p2, p2, -0x60

    :cond_0
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    invoke-virtual {p0, v0}, Landroid/view/View;->getGlobalVisibleRect(Landroid/graphics/Rect;)Z

    invoke-virtual {v0, p1, p2}, Landroid/graphics/Rect;->contains(II)Z

    move-result p0

    return p0
.end method

.method public intoPattern(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz v0, :cond_0

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->intoPattern(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startModeChangeAnimation()V

    :cond_0
    return-void
.end method

.method public invalidateDrawable(Landroid/graphics/drawable/Drawable;)V
    .locals 0

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    return-void
.end method

.method public isBottomVisible()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->isBottomVisible()Z

    move-result p0

    return p0
.end method

.method public isFullScreen()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->isFullScreen()Z

    move-result p0

    return p0
.end method

.method public isSnapEnableClick()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/ui/CameraSnapView;->mEnableSnapClick:Z

    return p0
.end method

.method public longExposePrepare(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->prepareRecording(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V

    return-void
.end method

.method public longExposeStart(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startRecord(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V

    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 2

    invoke-super {p0}, Landroid/view/View;->onDetachedFromWindow()V

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->cancelAnimation()V

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/graphics/drawable/Drawable;->setCallback(Landroid/graphics/drawable/Drawable$Callback;)V

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/ui/CameraSnapView;->recycleBitmap()V

    return-void
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 2

    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz v0, :cond_0

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->draw(Landroid/graphics/Canvas;)V

    :cond_0
    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_1

    iget-object v1, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapMatrix:Landroid/graphics/Matrix;

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->mExtraBitmapPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v1, p0}, Landroid/graphics/Canvas;->drawBitmap(Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V

    :cond_1
    return-void
.end method

.method public onForceVideoStateChange(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    if-eqz v0, :cond_0

    iget-boolean v0, v0, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->forceVideoPattern:Z

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    iget-boolean v1, p1, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->forceVideoPattern:Z

    if-ne v1, v0, :cond_1

    return-void

    :cond_1
    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->intoPattern(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startModeChangeAnimation()V

    return-void
.end method

.method protected onMeasure(II)V
    .locals 2

    invoke-static {p1}, Landroid/view/View$MeasureSpec;->getMode(I)I

    move-result v0

    const/high16 v1, 0x40000000    # 2.0f

    if-eq v0, v1, :cond_0

    invoke-super {p0, p1, p2}, Landroid/view/View;->onMeasure(II)V

    goto :goto_0

    :cond_0
    invoke-static {p1}, Landroid/view/View$MeasureSpec;->getSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/CameraSnapView;->mWidth:I

    invoke-static {p2}, Landroid/view/View$MeasureSpec;->getSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/ui/CameraSnapView;->mHeight:I

    iget p1, p0, Lcom/android/camera/ui/CameraSnapView;->mWidth:I

    iget p2, p0, Lcom/android/camera/ui/CameraSnapView;->mHeight:I

    invoke-virtual {p0, p1, p2}, Landroid/view/View;->setMeasuredDimension(II)V

    iget-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz p1, :cond_1

    iget p2, p0, Lcom/android/camera/ui/CameraSnapView;->mWidth:I

    int-to-float p2, p2

    iget p0, p0, Lcom/android/camera/ui/CameraSnapView;->mHeight:I

    int-to-float p0, p0

    invoke-virtual {p1, p2, p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->setWidthHeight(FF)V

    :cond_1
    :goto_0
    return-void
.end method

.method public onTimeOut()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/ui/CameraSnapView;->showCirclePaintItem()V

    invoke-virtual {p0}, Lcom/android/camera/ui/CameraSnapView;->showRoundPaintItem()V

    :cond_0
    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 16

    move-object/from16 v0, p0

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/ui/CameraSnapView;->isSnapEnableClick()Z

    move-result v1

    if-nez v1, :cond_0

    sget-object v1, Lcom/android/camera/ui/CameraSnapView;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "this view is disabled. action="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual/range {p1 .. p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-super/range {p0 .. p1}, Landroid/view/View;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    return v0

    :cond_0
    invoke-virtual/range {p1 .. p1}, Landroid/view/MotionEvent;->getRawX()F

    move-result v1

    float-to-int v1, v1

    invoke-virtual/range {p1 .. p1}, Landroid/view/MotionEvent;->getRawY()F

    move-result v2

    float-to-int v2, v2

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/ui/CameraSnapView;->inRegion(II)Z

    move-result v1

    invoke-virtual/range {p1 .. p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v2

    const/16 v7, 0xb0

    const/16 v8, 0xa9

    const/16 v9, 0xa6

    const/16 v10, 0xa2

    const/16 v11, 0xa1

    const/4 v12, 0x2

    const/4 v13, 0x1

    const/4 v14, 0x0

    if-eqz v2, :cond_e

    const/4 v15, 0x3

    const/4 v3, 0x0

    if-eq v2, v13, :cond_4

    if-eq v2, v12, :cond_1

    if-eq v2, v15, :cond_4

    const/4 v12, 0x6

    if-eq v2, v12, :cond_6

    goto/16 :goto_4

    :cond_1
    if-eqz v1, :cond_2

    return v14

    :cond_2
    iget-object v2, v0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    invoke-virtual {v2, v15}, Landroid/os/Handler;->hasMessages(I)Z

    move-result v2

    if-nez v2, :cond_3

    iget-boolean v2, v0, Lcom/android/camera/ui/CameraSnapView;->mHasCancelByOutside:Z

    if-eqz v2, :cond_4

    :cond_3
    return v14

    :cond_4
    iget-object v2, v0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    invoke-virtual {v2, v3}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    iput-wide v3, v0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    iget-wide v3, v0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    iget-wide v5, v0, Lcom/android/camera/ui/CameraSnapView;->mPressDownTime:J

    sub-long/2addr v3, v5

    iget v5, v0, Lcom/android/camera/ui/CameraSnapView;->mLongPressTime:I

    int-to-long v5, v5

    cmp-long v3, v3, v5

    if-gez v3, :cond_6

    if-eqz v1, :cond_5

    sget-object v3, Lcom/android/camera/ui/CameraSnapView;->TAG:Ljava/lang/String;

    const-string v4, "snap click action_up"

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v3, v0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    invoke-virtual {v3, v13}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    goto :goto_0

    :cond_5
    sget-object v3, Lcom/android/camera/ui/CameraSnapView;->TAG:Ljava/lang/String;

    const-string v4, "out of shutter button when you touch up"

    invoke-static {v3, v4}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_6
    :goto_0
    iget-boolean v3, v0, Lcom/android/camera/ui/CameraSnapView;->mHasCancelByOutside:Z

    if-eqz v3, :cond_7

    return v13

    :cond_7
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    iput-wide v3, v0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    iget-wide v3, v0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    iget-wide v5, v0, Lcom/android/camera/ui/CameraSnapView;->mPressDownTime:J

    sub-long/2addr v3, v5

    sget-object v5, Lcom/android/camera/ui/CameraSnapView;->TAG:Ljava/lang/String;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "timeDiffer = "

    invoke-virtual {v6, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget v5, v0, Lcom/android/camera/ui/CameraSnapView;->mLongPressTime:I

    int-to-long v5, v5

    cmp-long v5, v3, v5

    if-ltz v5, :cond_9

    if-eqz v1, :cond_8

    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    const/4 v5, 0x4

    invoke-virtual {v1, v5}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    goto :goto_1

    :cond_8
    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    invoke-virtual {v1, v15}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :cond_9
    :goto_1
    const-wide/16 v5, 0x78

    cmp-long v1, v3, v5

    if-lez v1, :cond_a

    const-wide/16 v3, 0x0

    goto :goto_2

    :cond_a
    sub-long v3, v5, v3

    :goto_2
    iget v1, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    if-eq v1, v11, :cond_14

    if-eq v1, v10, :cond_14

    if-eq v1, v9, :cond_d

    if-eq v1, v8, :cond_14

    if-eq v1, v7, :cond_c

    const/16 v5, 0xbb

    if-eq v1, v5, :cond_14

    const/16 v5, 0xcc

    if-eq v1, v5, :cond_b

    const/16 v2, 0xb3

    if-eq v1, v2, :cond_14

    const/16 v2, 0xb4

    if-eq v1, v2, :cond_14

    packed-switch v1, :pswitch_data_0

    packed-switch v1, :pswitch_data_1

    invoke-virtual/range {p0 .. p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v1

    invoke-static {v1}, Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;->getInstance(Landroid/content/Context;)Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;->performSnapClick()V

    iget-object v0, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    const/4 v1, 0x0

    invoke-virtual {v0, v3, v4, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleUpAnimation(JLandroid/animation/Animator$AnimatorListener;)V

    goto/16 :goto_4

    :pswitch_0
    const/4 v1, 0x0

    iget-object v2, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    if-eqz v2, :cond_14

    iget-boolean v2, v2, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->forceVideoPattern:Z

    if-nez v2, :cond_14

    iget-object v0, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, v3, v4, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleUpAnimation(JLandroid/animation/Animator$AnimatorListener;)V

    goto/16 :goto_4

    :pswitch_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->isOnSuperNightAlgoUpAndQuickShot()Z

    move-result v1

    if-eqz v1, :cond_14

    invoke-virtual/range {p0 .. p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v1

    invoke-static {v1}, Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;->getInstance(Landroid/content/Context;)Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/lib/compatibility/related/vibrator/ViberatorContext;->performSnapClick()V

    iget-object v0, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    const/4 v1, 0x0

    invoke-virtual {v0, v3, v4, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleUpAnimation(JLandroid/animation/Animator$AnimatorListener;)V

    goto/16 :goto_4

    :cond_b
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->ismShouldDraw6Patch()Z

    move-result v1

    if-eqz v1, :cond_14

    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    if-eqz v1, :cond_14

    iget-boolean v1, v1, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->forceVideoPattern:Z

    if-nez v1, :cond_14

    iget-object v0, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    const/4 v1, 0x0

    invoke-virtual {v0, v3, v4, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleUpAnimation(JLandroid/animation/Animator$AnimatorListener;)V

    goto/16 :goto_4

    :cond_c
    const/4 v1, 0x0

    iget-object v0, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, v3, v4, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleUpAnimation(JLandroid/animation/Animator$AnimatorListener;)V

    goto/16 :goto_4

    :cond_d
    const/4 v1, 0x0

    iget-object v0, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, v3, v4, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleUpAnimation(JLandroid/animation/Animator$AnimatorListener;)V

    goto/16 :goto_4

    :cond_e
    iget v1, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    if-eq v1, v11, :cond_12

    if-eq v1, v10, :cond_12

    if-eq v1, v9, :cond_11

    if-eq v1, v8, :cond_12

    if-eq v1, v7, :cond_11

    const/16 v3, 0xbb

    if-eq v1, v3, :cond_12

    const/16 v3, 0xcc

    if-eq v1, v3, :cond_10

    const/16 v2, 0xb3

    if-eq v1, v2, :cond_12

    const/16 v2, 0xb4

    if-eq v1, v2, :cond_12

    packed-switch v1, :pswitch_data_2

    packed-switch v1, :pswitch_data_3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/extra/DataItemLive;->getTimerBurstController()Lcom/android/camera/timerburst/TimerBurstController;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting()Z

    move-result v1

    if-eqz v1, :cond_f

    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    iget v2, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    invoke-virtual {v1, v2}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleDownAnimation(I)V

    goto :goto_3

    :cond_f
    iget v1, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    invoke-direct {v0, v1}, Lcom/android/camera/ui/CameraSnapView;->onTouchDownDefaultHandler(I)Z

    move-result v1

    if-nez v1, :cond_12

    return v14

    :pswitch_2
    iget-object v2, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    if-eqz v2, :cond_12

    iget-boolean v2, v2, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->forceVideoPattern:Z

    if-nez v2, :cond_12

    iget-object v2, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v2, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleDownAnimation(I)V

    goto :goto_3

    :pswitch_3
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/global/DataItemGlobal;->isOnSuperNightAlgoUpAndQuickShot()Z

    move-result v1

    if-eqz v1, :cond_12

    iget v1, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    invoke-direct {v0, v1}, Lcom/android/camera/ui/CameraSnapView;->onTouchDownDefaultHandler(I)Z

    move-result v1

    if-nez v1, :cond_12

    return v14

    :cond_10
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->ismShouldDraw6Patch()Z

    move-result v1

    if-eqz v1, :cond_12

    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    if-eqz v1, :cond_12

    iget-boolean v1, v1, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->forceVideoPattern:Z

    if-nez v1, :cond_12

    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    iget v2, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    invoke-virtual {v1, v2}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleDownAnimation(I)V

    goto :goto_3

    :cond_11
    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    iget v2, v0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    invoke-virtual {v1, v2}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startScaleDownAnimation(I)V

    :cond_12
    :goto_3
    :pswitch_4
    sget-object v1, Lcom/android/camera/ui/CameraSnapView;->TAG:Ljava/lang/String;

    const-string v2, "snap click action_down"

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput-boolean v14, v0, Lcom/android/camera/ui/CameraSnapView;->mMissTaken:Z

    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->mSnapListener:Lcom/android/camera/ui/CameraSnapView$SnapListener;

    invoke-interface {v1}, Lcom/android/camera/ui/CameraSnapView$SnapListener;->onSnapPrepare()V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    iput-wide v1, v0, Lcom/android/camera/ui/CameraSnapView;->mPressDownTime:J

    iget-wide v1, v0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    const-wide/16 v3, 0x0

    cmp-long v3, v1, v3

    if-lez v3, :cond_13

    iget-object v3, v0, Lcom/android/camera/ui/CameraSnapView;->mSnapListener:Lcom/android/camera/ui/CameraSnapView$SnapListener;

    iget-wide v4, v0, Lcom/android/camera/ui/CameraSnapView;->mPressDownTime:J

    sub-long/2addr v4, v1

    invoke-interface {v3, v4, v5}, Lcom/android/camera/ui/CameraSnapView$SnapListener;->onTrackSnapTaken(J)V

    :cond_13
    iget-object v1, v0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    iget v2, v0, Lcom/android/camera/ui/CameraSnapView;->mLongPressTime:I

    int-to-long v2, v2

    invoke-virtual {v1, v12, v2, v3}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    iput-boolean v14, v0, Lcom/android/camera/ui/CameraSnapView;->mHasCancelByOutside:Z

    :cond_14
    :goto_4
    :pswitch_5
    return v13

    :pswitch_data_0
    .packed-switch 0xac
        :pswitch_5
        :pswitch_1
        :pswitch_5
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0xb7
        :pswitch_5
        :pswitch_0
        :pswitch_5
    .end packed-switch

    :pswitch_data_2
    .packed-switch 0xac
        :pswitch_4
        :pswitch_3
        :pswitch_4
    .end packed-switch

    :pswitch_data_3
    .packed-switch 0xb7
        :pswitch_4
        :pswitch_2
        :pswitch_4
    .end packed-switch
.end method

.method public pauseRecording()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->pauseRecording()V

    return-void
.end method

.method public performClick()Z
    .locals 2

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-super {p0}, Landroid/view/View;->performClick()Z

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return v0

    :cond_0
    invoke-super {p0}, Landroid/view/View;->performClick()Z

    move-result p0

    return p0
.end method

.method public prepareRecording(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->prepareRecording(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V

    return-void
.end method

.method public removeLastSegment()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->removeLastSegment()V

    return-void
.end method

.method public resumeRecording()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->resumeRecording()V

    return-void
.end method

.method public setDurationText(Ljava/lang/String;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->setDurationText(Ljava/lang/String;)V

    return-void
.end method

.method public setParameters(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V
    .locals 2

    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mCurrentCondition:Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/android/camera/ui/CameraSnapView;->mPressUpTime:J

    iget v0, p1, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->targetMode:I

    iput v0, p0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, p0}, Landroid/graphics/drawable/Drawable;->setCallback(Landroid/graphics/drawable/Drawable$Callback;)V

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->intoPatternFromParameters(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->resetRecordingState()V

    iget-boolean v0, p1, Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;->needSnapButtonAnimation:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->intoPattern(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V

    iget-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startModeChangeAnimation()V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->intoPatternFromParameters(Lcom/android/camera/ui/drawable/snap/PaintConditionReferred;)V

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    :goto_0
    const/16 p1, 0x226

    iput p1, p0, Lcom/android/camera/ui/CameraSnapView;->mLongPressTime:I

    return-void
.end method

.method public setSnapClickEnable(Z)V
    .locals 3

    sget-object v0, Lcom/android/camera/ui/CameraSnapView;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setClickEnable: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput-boolean p1, p0, Lcom/android/camera/ui/CameraSnapView;->mEnableSnapClick:Z

    if-nez p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->mHandler:Landroid/os/Handler;

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    :cond_0
    return-void
.end method

.method public setSnapListener(Lcom/android/camera/ui/CameraSnapView$SnapListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/ui/CameraSnapView;->mSnapListener:Lcom/android/camera/ui/CameraSnapView$SnapListener;

    return-void
.end method

.method public setStopButtonEnable(ZZ)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->setStopButtonEnable(ZZ)V

    return-void
.end method

.method public showCirclePaintItem()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->showCirclePaintItem()V

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    return-void
.end method

.method public showPaintCenterVVItem()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->showPaintCenterVVItem()V

    return-void
.end method

.method public showRoundPaintItem()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->showRoundPaintItem()V

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    return-void
.end method

.method public startRing()V
    .locals 2

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f080520

    invoke-static {v0, v1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/android/camera/ui/CameraSnapView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startRingAnimation()V

    return-void
.end method

.method public startSpeech()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->isFullScreen()Z

    move-result v0

    if-eqz v0, :cond_0

    const v0, 0x7f08030c

    goto :goto_0

    :cond_0
    const v0, 0x7f08030d

    :goto_0
    iget-object v1, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-virtual {v1, p0, v0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->showBitmap(Landroid/content/Context;I)V

    return-void
.end method

.method public stopRing()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/ui/CameraSnapView;->recycleBitmap()V

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->stopRingAnimation()V

    return-void
.end method

.method public stopSpeech()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->clearBitmap()V

    :cond_0
    return-void
.end method

.method public triggerAnimation(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V
    .locals 1

    iget v0, p0, Lcom/android/camera/ui/CameraSnapView;->mCurrentMode:I

    sparse-switch v0, :sswitch_data_0

    goto :goto_0

    :sswitch_0
    iget-boolean v0, p1, Lcom/android/camera/fragment/bottom/BottomAnimationConfig;->mIsVideoBokeh:Z

    if-nez v0, :cond_0

    iget-boolean v0, p1, Lcom/android/camera/fragment/bottom/BottomAnimationConfig;->mIsStart:Z

    if-nez v0, :cond_1

    :cond_0
    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz p0, :cond_1

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startRecordAnimation(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V

    goto :goto_0

    :sswitch_1
    iget-object p0, p0, Lcom/android/camera/ui/CameraSnapView;->cameraSnapAnimateDrawable:Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;

    if-eqz p0, :cond_1

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/drawable/snap/CameraSnapAnimateDrawable;->startRecordAnimation(Lcom/android/camera/fragment/bottom/BottomAnimationConfig;)V

    :cond_1
    :goto_0
    return-void

    :sswitch_data_0
    .sparse-switch
        0xa1 -> :sswitch_1
        0xa2 -> :sswitch_0
        0xa3 -> :sswitch_1
        0xa5 -> :sswitch_1
        0xa6 -> :sswitch_0
        0xa7 -> :sswitch_1
        0xa9 -> :sswitch_1
        0xac -> :sswitch_1
        0xad -> :sswitch_1
        0xae -> :sswitch_1
        0xb0 -> :sswitch_0
        0xb1 -> :sswitch_1
        0xb3 -> :sswitch_1
        0xb4 -> :sswitch_0
        0xb7 -> :sswitch_1
        0xb8 -> :sswitch_1
        0xb9 -> :sswitch_1
        0xbb -> :sswitch_1
        0xcc -> :sswitch_0
    .end sparse-switch
.end method
