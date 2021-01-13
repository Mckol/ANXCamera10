.class public Lcom/android/camera/dualvideo/DualVideoControler;
.super Ljava/lang/Object;
.source "DualVideoControler.java"

# interfaces
.implements Lcom/android/camera/SurfaceTextureScreenNail$ExternalFrameProcessor;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;
    }
.end annotation


# static fields
.field private static final BLUR_DURATION:I = 0xc8

.field private static final BLUR_HEIGHT:I = 0x5a0

.field public static final LEFT_MARGIN:I

.field private static final SELECT_LINE_WIDTH:I

.field private static final SKIP_FRAMES:I = 0x2

.field private static final TAG:Ljava/lang/String; = "DualVideoControler"


# instance fields
.field private mAnimDstRect:Landroid/graphics/Rect;

.field private mAnimStartRect:Landroid/graphics/Rect;

.field private mCameraIdManager:Lcom/android/camera/dualvideo/CameraIDManager;

.field private mCaptureResult:Landroid/hardware/camera2/CaptureResult;

.field private mEaseOutAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

.field private mIsComposeSwitching:Z

.field mJpegCallback:Lcom/android/camera/module/VideoModule$JpegPictureCallback;

.field private mListener:Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;

.field private mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

.field private mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

.field private mNeedRecording:Z

.field private mNeedUpdateBlurTex:Z

.field private mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

.field private mRenderLock:Ljava/lang/Object;

.field private mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

.field private mResources:Landroid/content/res/Resources;

.field private mSkipFrame:I

.field private mSnapAnimTimer:Lcom/android/camera/dualvideo/render/Timer;

.field private mSnapOrientation:I

.field private mSnapPending:Ljava/util/concurrent/atomic/AtomicBoolean;

.field private mSnapReader:Landroid/media/ImageReader;

.field private mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

.field private mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

.field private mSubFrameReady:Z

.field private mSubPreviewSize:Landroid/util/Size;

.field private mSubSurface:Landroid/view/Surface;

.field private mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

.field private mUpdateBlurConditionVar:Landroid/os/ConditionVariable;

.field private mWeNeedBlurAnim:Z


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const/high16 v0, 0x41680000    # 14.5f

    invoke-static {v0}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result v0

    sput v0, Lcom/android/camera/dualvideo/DualVideoControler;->LEFT_MARGIN:I

    const v0, 0x400b851f    # 2.18f

    invoke-static {v0}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result v0

    sput v0, Lcom/android/camera/dualvideo/DualVideoControler;->SELECT_LINE_WIDTH:I

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Lcom/android/camera/dualvideo/render/Timer;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/render/Timer;-><init>()V

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapAnimTimer:Lcom/android/camera/dualvideo/render/Timer;

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedRecording:Z

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubFrameReady:Z

    const/4 v1, 0x2

    iput v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSkipFrame:I

    new-instance v1, Ljava/lang/Object;

    invoke-direct {v1}, Ljava/lang/Object;-><init>()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedUpdateBlurTex:Z

    new-instance v1, Landroid/os/ConditionVariable;

    invoke-direct {v1}, Landroid/os/ConditionVariable;-><init>()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mUpdateBlurConditionVar:Landroid/os/ConditionVariable;

    new-instance v1, Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-direct {v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;-><init>()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mWeNeedBlurAnim:Z

    invoke-static {v0}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mAnimDstRect:Landroid/graphics/Rect;

    new-instance v1, Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-direct {v1, v0}, Ljava/util/concurrent/atomic/AtomicBoolean;-><init>(Z)V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapPending:Ljava/util/concurrent/atomic/AtomicBoolean;

    iput v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapOrientation:I

    new-instance v0, Lcom/android/camera/dualvideo/CameraIDManager;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/CameraIDManager;-><init>()V

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mCameraIdManager:Lcom/android/camera/dualvideo/CameraIDManager;

    return-void
.end method

.method private RectIterpolate(Landroid/graphics/Rect;Landroid/graphics/Rect;F)Landroid/graphics/Rect;
    .locals 5

    const/high16 p0, 0x3f800000    # 1.0f

    sub-float/2addr p0, p3

    new-instance v0, Landroid/graphics/Rect;

    iget v1, p1, Landroid/graphics/Rect;->left:I

    int-to-float v1, v1

    mul-float/2addr v1, p0

    iget v2, p2, Landroid/graphics/Rect;->left:I

    int-to-float v2, v2

    mul-float/2addr v2, p3

    add-float/2addr v1, v2

    float-to-int v1, v1

    iget v2, p1, Landroid/graphics/Rect;->top:I

    int-to-float v2, v2

    mul-float/2addr v2, p0

    iget v3, p2, Landroid/graphics/Rect;->top:I

    int-to-float v3, v3

    mul-float/2addr v3, p3

    add-float/2addr v2, v3

    float-to-int v2, v2

    iget v3, p1, Landroid/graphics/Rect;->right:I

    int-to-float v3, v3

    mul-float/2addr v3, p0

    iget v4, p2, Landroid/graphics/Rect;->right:I

    int-to-float v4, v4

    mul-float/2addr v4, p3

    add-float/2addr v3, v4

    float-to-int v3, v3

    iget p1, p1, Landroid/graphics/Rect;->bottom:I

    int-to-float p1, p1

    mul-float/2addr p1, p0

    iget p0, p2, Landroid/graphics/Rect;->bottom:I

    int-to-float p0, p0

    mul-float/2addr p0, p3

    add-float/2addr p1, p0

    float-to-int p0, p1

    invoke-direct {v0, v1, v2, v3, p0}, Landroid/graphics/Rect;-><init>(IIII)V

    return-object v0
.end method

.method static synthetic a(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/UserSelectData;)V
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    iput p0, p1, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    return-void
.end method

.method static synthetic a(Ljava/lang/StringBuilder;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, " id: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    iget-object p1, p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    return-void
.end method

.method static synthetic a(FFLcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 5

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v0

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    int-to-float v2, v1

    cmpl-float v2, p0, v2

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-ltz v2, :cond_0

    iget v2, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    add-int/2addr v1, v2

    int-to-float v1, v1

    cmpg-float p0, p0, v1

    if-gtz p0, :cond_0

    move p0, v3

    goto :goto_0

    :cond_0
    move p0, v4

    :goto_0
    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    int-to-float v2, v1

    cmpl-float v2, p1, v2

    if-ltz v2, :cond_1

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v1, v0

    int-to-float v0, v1

    cmpg-float p1, p1, v0

    if-gtz p1, :cond_1

    move p1, v3

    goto :goto_1

    :cond_1
    move p1, v4

    :goto_1
    if-eqz p0, :cond_2

    if-eqz p1, :cond_2

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    const/16 p1, 0xa

    if-eq p0, p1, :cond_2

    goto :goto_2

    :cond_2
    move v3, v4

    :goto_2
    return v3
.end method

.method static synthetic a(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result p0

    const/4 v0, 0x1

    if-ne p0, v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method static synthetic a(Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 1

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->mCameraID:I

    const/16 v0, 0x65

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic b(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/UserSelectData;)V
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    iput p0, p1, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    return-void
.end method

.method static synthetic b(FFLcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 5

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v0

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    int-to-float v2, v1

    cmpl-float v2, p0, v2

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-ltz v2, :cond_0

    iget v2, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    add-int/2addr v1, v2

    int-to-float v1, v1

    cmpg-float p0, p0, v1

    if-gtz p0, :cond_0

    move p0, v3

    goto :goto_0

    :cond_0
    move p0, v4

    :goto_0
    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    int-to-float v2, v1

    cmpl-float v2, p1, v2

    if-ltz v2, :cond_1

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v1, v0

    int-to-float v0, v1

    cmpg-float p1, p1, v0

    if-gtz p1, :cond_1

    move p1, v3

    goto :goto_1

    :cond_1
    move p1, v4

    :goto_1
    if-eqz p0, :cond_2

    if-eqz p1, :cond_2

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    const/16 p1, 0xa

    if-eq p0, p1, :cond_2

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result p0

    if-eqz p0, :cond_2

    goto :goto_2

    :cond_2
    move v3, v4

    :goto_2
    return v3
.end method

.method static synthetic b(Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 1

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->mCameraID:I

    const/16 v0, 0x64

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic c(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    const/16 v0, 0xa

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic d(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isFacingFront()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    const/16 v0, 0xd

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private drawBlur(Lcom/android/gallery3d/ui/GLCanvasImpl;Lcom/android/camera/CameraScreenNail;Landroid/graphics/Rect;)V
    .locals 6

    iget v2, p3, Landroid/graphics/Rect;->left:I

    iget v3, p3, Landroid/graphics/Rect;->top:I

    invoke-virtual {p3}, Landroid/graphics/Rect;->width()I

    move-result v4

    invoke-virtual {p3}, Landroid/graphics/Rect;->height()I

    move-result v5

    move-object v0, p2

    move-object v1, p1

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/CameraScreenNail;->drawBlurTexture(Lcom/android/gallery3d/ui/GLCanvas;IIII)V

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mListener:Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;

    invoke-interface {p0}, Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;->onRenderRequestNeeded()V

    return-void
.end method

.method private drawBlurCover(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 7

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v0

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->isFacingFront()Z

    move-result p2

    if-eqz p2, :cond_0

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 p2, 0xc

    invoke-virtual {p0, p2}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object p0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 p2, 0xb

    invoke-virtual {p0, p2}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object p0

    :goto_0
    move-object v2, p0

    new-instance p0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget v3, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v4, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget v5, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    iget v6, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    move-object v1, p0

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {p1, p0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    return-void
.end method

.method private drawBottomMask(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 7

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->hasMiniPreview()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->isRecording()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    invoke-static {p0}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object p0

    new-instance v6, Lcom/android/camera/effect/draw_mode/FillRectAttribute;

    iget v0, p0, Landroid/graphics/Rect;->left:I

    int-to-float v1, v0

    iget v0, p0, Landroid/graphics/Rect;->bottom:I

    int-to-float v2, v0

    invoke-virtual {p0}, Landroid/graphics/Rect;->width()I

    move-result p0

    int-to-float v3, p0

    invoke-interface {p1}, Lcom/android/gallery3d/ui/GLCanvas;->getHeight()I

    move-result p0

    int-to-float v4, p0

    const/high16 v5, -0x1000000

    move-object v0, v6

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/effect/draw_mode/FillRectAttribute;-><init>(FFFFI)V

    invoke-interface {p1, v6}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    :cond_0
    return-void
.end method

.method private drawDarkCornerFrame(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 10

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v0

    const/16 v1, 0x14

    if-ge v0, v1, :cond_0

    return-void

    :cond_0
    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 v1, 0x9

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v3

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 v0, 0xa

    invoke-virtual {p0, v0}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object p0

    new-instance v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget v4, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v5, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget v6, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    invoke-virtual {v3}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v7

    move-object v2, v0

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {p1, v0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    new-instance v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget v6, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v1, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget v2, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v1, v2

    invoke-virtual {p0}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v2

    sub-int v7, v1, v2

    iget v8, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    invoke-virtual {p0}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v9

    move-object v4, v0

    move-object v5, p0

    invoke-direct/range {v4 .. v9}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {p1, v0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    return-void
.end method

.method private drawDualVideo(Lcom/android/gallery3d/ui/GLCanvas;)Z
    .locals 5

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    invoke-virtual {v0}, Landroid/graphics/SurfaceTexture;->updateTexImage()V

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubFrameReady:Z

    if-nez v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mIsComposeSwitching:Z

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    return v1

    :cond_2
    :goto_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-nez v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mResources:Landroid/content/res/Resources;

    invoke-virtual {v0, p1, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->init(Lcom/android/gallery3d/ui/GLCanvas;Landroid/content/res/Resources;)V

    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->initSubSurfaceTexture(Lcom/android/gallery3d/ui/GLCanvas;)V

    new-instance v0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    iget-object v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v3, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iget-object v4, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    invoke-direct {v0, v1, v2, v3, v4}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;-><init>(Landroid/graphics/SurfaceTexture;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;Lcom/android/gallery3d/ui/ExtTexture;Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    :cond_3
    invoke-direct {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawForRecording()V

    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawForPreview(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-direct {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->waitVideoDrawFinish()V

    const/4 p0, 0x1

    return p0
.end method

.method private drawForPreview(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 5

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableList()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/h;->INSTANCE:Lcom/android/camera/dualvideo/h;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/y;->INSTANCE:Lcom/android/camera/dualvideo/y;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->sorted(Ljava/util/Comparator;)Ljava/util/stream/Stream;

    move-result-object v0

    invoke-static {}, Ljava/util/stream/Collectors;->toList()Ljava/util/stream/Collector;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->collect(Ljava/util/stream/Collector;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/List;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapAnimTimer:Lcom/android/camera/dualvideo/render/Timer;

    invoke-virtual {v1}, Lcom/android/camera/dualvideo/render/Timer;->isValid()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v2

    invoke-interface {p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v3

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getAlpha()F

    move-result v4

    invoke-virtual {v3, v4}, Lcom/android/camera/effect/GLCanvasState;->setAlpha(F)V

    invoke-direct {p0, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->shouldDrawBlur(Lcom/android/camera/dualvideo/render/IRenderable;)Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-direct {p0, p1, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawBlurCover(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V

    goto :goto_1

    :cond_0
    invoke-interface {p1, v2}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    :goto_1
    invoke-direct {p0, p1, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawGridLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-interface {p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v2

    const/high16 v3, 0x3f800000    # 1.0f

    invoke-virtual {v2, v3}, Lcom/android/camera/effect/GLCanvasState;->setAlpha(F)V

    invoke-direct {p0, p1, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawSelectedFrame(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-direct {p0, p1, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawSixPatchTag(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-direct {p0, p1, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawDarkCornerFrame(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V

    goto :goto_0

    :cond_1
    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawBottomMask(Lcom/android/gallery3d/ui/GLCanvas;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableList()Ljava/util/ArrayList;

    move-result-object v0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->updateBlurTex(Lcom/android/gallery3d/ui/GLCanvas;Ljava/util/List;)V

    return-void
.end method

.method private drawForRecording()V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedRecording:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableListForRecord()Ljava/util/List;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    invoke-virtual {v1, v0}, Lcom/android/camera/module/encoder/RenderHandler;->draw(Ljava/util/List;)V

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapPending:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z

    move-result v1

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    invoke-virtual {v1, v0}, Lcom/android/camera/module/encoder/RenderHandler;->draw(Ljava/util/List;)V

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapPending:Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    :cond_1
    return-void
.end method

.method private drawGridLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    const-string v1, "pref_camera_referenceline_key"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/data/data/DataItemBase;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->isAnimating()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->isPressedIn6Patch()Z

    move-result v1

    if-eqz v1, :cond_1

    :cond_0
    if-nez v0, :cond_2

    :cond_1
    return-void

    :cond_2
    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v0

    const/16 v1, 0xa

    if-eq v0, v1, :cond_4

    const/16 v1, 0xd

    if-eq v0, v1, :cond_3

    const/high16 v0, 0x3f000000    # 0.5f

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawHorizontalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawVerticalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V

    goto :goto_0

    :cond_3
    const v0, 0x3eaaaaab

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawHorizontalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawVerticalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V

    const v0, 0x3f2aaaab

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawHorizontalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawVerticalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V

    :cond_4
    :goto_0
    return-void
.end method

.method private drawHorizontalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V
    .locals 8

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    const/16 p2, 0xbe2

    invoke-static {p2}, Landroid/opengl/GLES20;->glEnable(I)V

    const/16 v0, 0x302

    const/16 v1, 0x303

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glBlendFunc(II)V

    new-instance v0, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;

    iget v1, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    int-to-float v3, v1

    iget v2, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    int-to-float v4, v2

    iget v5, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    int-to-float v6, v5

    mul-float/2addr v6, p3

    add-float/2addr v4, v6

    iget p0, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    add-int/2addr v1, p0

    int-to-float p0, v1

    int-to-float v1, v2

    int-to-float v2, v5

    mul-float/2addr v2, p3

    add-float v6, v1, v2

    new-instance v7, Lcom/android/gallery3d/ui/GLPaint;

    const p3, 0x3f3ae148    # 0.73f

    invoke-static {p3}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result p3

    int-to-float p3, p3

    const v1, 0x6a6a6a6a

    invoke-direct {v7, p3, v1}, Lcom/android/gallery3d/ui/GLPaint;-><init>(FI)V

    move-object v2, v0

    move v5, p0

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;-><init>(FFFFLcom/android/gallery3d/ui/GLPaint;)V

    invoke-interface {p1, v0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    invoke-static {p2}, Landroid/opengl/GLES20;->glDisable(I)V

    return-void
.end method

.method private drawSelectedFrame(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 22

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    invoke-interface/range {p2 .. p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectFrameAlpha()F

    move-result v2

    const v3, 0x3c23d70a    # 0.01f

    cmpg-float v2, v2, v3

    if-gez v2, :cond_0

    return-void

    :cond_0
    invoke-interface/range {p1 .. p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v2

    invoke-interface/range {p2 .. p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectFrameAlpha()F

    move-result v3

    invoke-virtual {v2, v3}, Lcom/android/camera/effect/GLCanvasState;->setAlpha(F)V

    invoke-interface/range {p2 .. p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v2

    new-instance v9, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;

    iget v3, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    sget v4, Lcom/android/camera/dualvideo/DualVideoControler;->SELECT_LINE_WIDTH:I

    div-int/lit8 v5, v4, 0x2

    add-int/2addr v5, v3

    int-to-float v5, v5

    iget v6, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    int-to-float v7, v6

    div-int/lit8 v8, v4, 0x2

    add-int/2addr v3, v8

    int-to-float v8, v3

    iget v3, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v6, v3

    int-to-float v10, v6

    new-instance v11, Lcom/android/gallery3d/ui/GLPaint;

    int-to-float v3, v4

    const/16 v12, -0x31ea

    invoke-direct {v11, v3, v12}, Lcom/android/gallery3d/ui/GLPaint;-><init>(FI)V

    move-object v3, v9

    move v4, v5

    move v5, v7

    move v6, v8

    move v7, v10

    move-object v8, v11

    invoke-direct/range {v3 .. v8}, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;-><init>(FFFFLcom/android/gallery3d/ui/GLPaint;)V

    invoke-interface {v1, v9}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;

    iget v4, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v5, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    add-int v6, v4, v5

    sget v7, Lcom/android/camera/dualvideo/DualVideoControler;->SELECT_LINE_WIDTH:I

    div-int/lit8 v8, v7, 0x2

    sub-int/2addr v6, v8

    int-to-float v14, v6

    iget v6, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    int-to-float v15, v6

    add-int/2addr v4, v5

    div-int/lit8 v5, v7, 0x2

    sub-int/2addr v4, v5

    int-to-float v4, v4

    iget v5, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v6, v5

    int-to-float v5, v6

    new-instance v6, Lcom/android/gallery3d/ui/GLPaint;

    int-to-float v7, v7

    invoke-direct {v6, v7, v12}, Lcom/android/gallery3d/ui/GLPaint;-><init>(FI)V

    move-object v13, v3

    move/from16 v16, v4

    move/from16 v17, v5

    move-object/from16 v18, v6

    invoke-direct/range {v13 .. v18}, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;-><init>(FFFFLcom/android/gallery3d/ui/GLPaint;)V

    invoke-interface {v1, v3}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget-object v4, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 v5, 0xe

    invoke-virtual {v4, v5}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v17

    iget v4, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v6, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget v7, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    iget-object v8, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {v8, v5}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v5

    invoke-virtual {v5}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v21

    move-object/from16 v16, v3

    move/from16 v18, v4

    move/from16 v19, v6

    move/from16 v20, v7

    invoke-direct/range {v16 .. v21}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {v1, v3}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget-object v4, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 v5, 0xf

    invoke-virtual {v4, v5}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v9

    iget v10, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v4, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget v6, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v4, v6

    iget-object v6, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {v6, v5}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v6

    invoke-virtual {v6}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v6

    sub-int v11, v4, v6

    iget v12, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    iget-object v4, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {v4, v5}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v13

    move-object v8, v3

    invoke-direct/range {v8 .. v13}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {v1, v3}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    iget-object v3, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 v4, 0xd

    invoke-virtual {v3, v4}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v6

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget v4, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v5, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    add-int/2addr v4, v5

    invoke-virtual {v6}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v5

    sub-int v7, v4, v5

    iget v4, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget v5, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v4, v5

    invoke-virtual {v6}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v5

    sub-int v8, v4, v5

    invoke-virtual {v6}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v9

    invoke-virtual {v6}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v10

    move-object v5, v3

    invoke-direct/range {v5 .. v10}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {v1, v3}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    invoke-interface/range {p2 .. p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v3

    const/4 v4, 0x1

    if-eq v3, v4, :cond_2

    const/4 v4, 0x2

    if-eq v3, v4, :cond_1

    return-void

    :cond_1
    iget-object v3, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 v4, 0x8

    invoke-virtual {v3, v4}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v3

    goto :goto_0

    :cond_2
    iget-object v3, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v4, 0x7

    invoke-virtual {v3, v4}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v3

    :goto_0
    move-object v5, v3

    invoke-interface/range {p1 .. p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/effect/GLCanvasState;->pushState()V

    invoke-interface/range {p1 .. p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v3

    iget-object v0, v0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getmAnimTexTransMatrix()[F

    move-result-object v0

    invoke-virtual {v3, v0}, Lcom/android/camera/effect/GLCanvasState;->setTexMatrix([F)V

    new-instance v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget v3, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v4, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    add-int/2addr v3, v4

    invoke-virtual {v5}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v4

    sub-int v6, v3, v4

    iget v3, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget v2, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v3, v2

    invoke-virtual {v5}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v2

    sub-int v7, v3, v2

    invoke-virtual {v5}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v8

    invoke-virtual {v5}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v9

    move-object v4, v0

    invoke-direct/range {v4 .. v9}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {v1, v0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    invoke-interface/range {p1 .. p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/effect/GLCanvasState;->popState()V

    invoke-interface/range {p1 .. p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v0

    const/high16 v1, 0x3f800000    # 1.0f

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/GLCanvasState;->setAlpha(F)V

    return-void
.end method

.method private drawSixPatchTag(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 7

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result v0

    packed-switch v0, :pswitch_data_0

    return-void

    :pswitch_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    goto :goto_0

    :pswitch_1
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v1, 0x5

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    goto :goto_0

    :pswitch_2
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    goto :goto_0

    :pswitch_3
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    goto :goto_0

    :pswitch_4
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    goto :goto_0

    :pswitch_5
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    :goto_0
    move-object v2, v0

    invoke-interface {p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/effect/GLCanvasState;->pushState()V

    invoke-interface {p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v0

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->get6PatchTagAlpha()F

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/GLCanvasState;->setAlpha(F)V

    invoke-interface {p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {v1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getmTexTransMatrix()[F

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/GLCanvasState;->setTexMatrix([F)V

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexOrientation()I

    move-result p0

    if-eqz p0, :cond_1

    const/16 v0, 0x5a

    if-eq p0, v0, :cond_0

    const/16 v0, 0xb4

    if-eq p0, v0, :cond_1

    const/16 v0, 0x10e

    if-eq p0, v0, :cond_0

    goto :goto_1

    :cond_0
    new-instance p0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v0

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    const v1, 0x40e8a3d7    # 7.27f

    invoke-static {v1}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result v3

    add-int/2addr v3, v0

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v0

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p2

    iget p2, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v0, p2

    invoke-virtual {v2}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result p2

    sub-int/2addr v0, p2

    invoke-static {v1}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result p2

    sub-int v4, v0, p2

    invoke-virtual {v2}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v5

    invoke-virtual {v2}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v6

    move-object v1, p0

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {p1, p0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    goto :goto_1

    :cond_1
    new-instance p0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v0

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    const v1, 0x412e8f5c    # 10.91f

    invoke-static {v1}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result v1

    add-int v3, v0, v1

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v0

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p2

    iget p2, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v0, p2

    invoke-virtual {v2}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result p2

    sub-int/2addr v0, p2

    const p2, 0x408b851f    # 4.36f

    invoke-static {p2}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result p2

    sub-int v4, v0, p2

    invoke-virtual {v2}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v5

    invoke-virtual {v2}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v6

    move-object v1, p0

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {p1, p0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    :goto_1
    invoke-interface {p1}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/effect/GLCanvasState;->popState()V

    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x14
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private drawVerticalLine(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IRenderable;F)V
    .locals 8

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    const/16 p2, 0xbe2

    invoke-static {p2}, Landroid/opengl/GLES20;->glEnable(I)V

    const/16 v0, 0x302

    const/16 v1, 0x303

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glBlendFunc(II)V

    new-instance v0, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;

    iget v1, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    int-to-float v2, v1

    iget v3, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    int-to-float v4, v3

    mul-float/2addr v4, p3

    add-float/2addr v4, v2

    iget v2, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    int-to-float v5, v2

    int-to-float v1, v1

    int-to-float v3, v3

    mul-float/2addr v3, p3

    add-float p3, v1, v3

    iget p0, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    add-int/2addr v2, p0

    int-to-float v6, v2

    new-instance v7, Lcom/android/gallery3d/ui/GLPaint;

    const p0, 0x3f3ae148    # 0.73f

    invoke-static {p0}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result p0

    int-to-float p0, p0

    const v1, 0x6a6a6a6a

    invoke-direct {v7, p0, v1}, Lcom/android/gallery3d/ui/GLPaint;-><init>(FI)V

    move-object v2, v0

    move v3, v4

    move v4, v5

    move v5, p3

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/draw_mode/DrawLineAttribute;-><init>(FFFFLcom/android/gallery3d/ui/GLPaint;)V

    invoke-interface {p1, v0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    invoke-static {p2}, Landroid/opengl/GLES20;->glDisable(I)V

    return-void
.end method

.method static synthetic e(Lcom/android/camera/dualvideo/render/IRenderable;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    return-object p0
.end method

.method static synthetic f(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isFacingFront()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private findSubPreviewSize()Landroid/util/Size;
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object p0

    const/16 v0, 0x65

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->isFrontCameraId(I)Z

    move-result p0

    if-eqz p0, :cond_0

    sget-object p0, Lcom/android/camera/dualvideo/render/DualVideoUtil;->FRONT_PREVIE:Landroid/util/Size;

    return-object p0

    :cond_0
    sget-object p0, Lcom/android/camera/dualvideo/render/DualVideoUtil;->BACK_PREVIE:Landroid/util/Size;

    return-object p0
.end method

.method static synthetic g(Lcom/android/camera/dualvideo/render/IRenderable;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    return-object p0
.end method

.method static synthetic h(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isFacingFront()Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic i(Lcom/android/camera/dualvideo/render/IRenderable;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    return-object p0
.end method

.method private initSubSurfaceTexture(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    if-eqz v0, :cond_0

    return-void

    :cond_0
    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "initSubSurfaceTexture: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->createSubCameraSurfaceIfNeed()Landroid/view/Surface;

    new-instance v0, Lcom/android/gallery3d/ui/ExtTexture;

    invoke-direct {v0}, Lcom/android/gallery3d/ui/ExtTexture;-><init>()V

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {v0, p1}, Lcom/android/gallery3d/ui/ExtTexture;->onBind(Lcom/android/gallery3d/ui/GLCanvas;)Z

    iget-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    invoke-virtual {v0}, Landroid/util/Size;->getWidth()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    invoke-virtual {v1}, Landroid/util/Size;->getHeight()I

    move-result v1

    invoke-virtual {p1, v0, v1}, Lcom/android/gallery3d/ui/BasicTexture;->setSize(II)V

    iget-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    invoke-virtual {p1}, Landroid/graphics/SurfaceTexture;->detachFromGLContext()V

    iget-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {p0}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result p0

    invoke-virtual {p1, p0}, Landroid/graphics/SurfaceTexture;->attachToGLContext(I)V

    return-void
.end method

.method static synthetic j(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isFacingFront()Z

    move-result p0

    return p0
.end method

.method static synthetic k(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isFacingFront()Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private printTexId(Ljava/util/List;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/dualvideo/render/IRenderable;",
            ">;)V"
        }
    .end annotation

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, " tex id:  "

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-interface {p1}, Ljava/util/List;->stream()Ljava/util/stream/Stream;

    move-result-object p1

    new-instance v0, Lcom/android/camera/dualvideo/n;

    invoke-direct {v0, p0}, Lcom/android/camera/dualvideo/n;-><init>(Ljava/lang/StringBuilder;)V

    invoke-interface {p1, v0}, Ljava/util/stream/Stream;->forEachOrdered(Ljava/util/function/Consumer;)V

    sget-object p1, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "printTexId: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private releaseRecordingRelated()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/module/encoder/RenderHandler;->release()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapReader:Landroid/media/ImageReader;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroid/media/ImageReader;->close()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapReader:Landroid/media/ImageReader;

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    invoke-virtual {v0}, Lcom/android/camera/module/encoder/RenderHandler;->release()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    :cond_1
    return-void
.end method

.method private releaseSurfaceTexture(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "releaseSurfaceTexture: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-interface {p1, v0}, Lcom/android/gallery3d/ui/GLCanvas;->deleteSurfaceTexture(Landroid/graphics/SurfaceTexture;)V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    :cond_0
    iget-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurface:Landroid/view/Surface;

    if-eqz p1, :cond_1

    invoke-virtual {p1}, Landroid/view/Surface;->release()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurface:Landroid/view/Surface;

    :cond_1
    iget-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    if-eqz p1, :cond_2

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->recycle()V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    :cond_2
    return-void
.end method

.method private saveJpeg(Landroid/media/Image;)V
    .locals 7

    if-eqz p1, :cond_2

    invoke-virtual {p1}, Landroid/media/Image;->getWidth()I

    move-result v0

    invoke-virtual {p1}, Landroid/media/Image;->getHeight()I

    move-result v1

    invoke-virtual {p1}, Landroid/media/Image;->getPlanes()[Landroid/media/Image$Plane;

    move-result-object v2

    const/4 v3, 0x0

    aget-object v4, v2, v3

    invoke-virtual {v4}, Landroid/media/Image$Plane;->getBuffer()Ljava/nio/ByteBuffer;

    move-result-object v4

    aget-object v5, v2, v3

    invoke-virtual {v5}, Landroid/media/Image$Plane;->getPixelStride()I

    move-result v5

    aget-object v2, v2, v3

    invoke-virtual {v2}, Landroid/media/Image$Plane;->getRowStride()I

    move-result v2

    mul-int v6, v5, v0

    sub-int/2addr v2, v6

    div-int v5, v2, v5

    add-int/2addr v5, v0

    sget-object v6, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v5, v1, v6}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-virtual {v1, v4}, Landroid/graphics/Bitmap;->copyPixelsFromBuffer(Ljava/nio/Buffer;)V

    if-eqz v2, :cond_0

    invoke-virtual {v1}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v2

    invoke-static {v1, v3, v3, v0, v2}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIII)Landroid/graphics/Bitmap;

    move-result-object v0

    invoke-virtual {v1}, Landroid/graphics/Bitmap;->recycle()V

    goto :goto_0

    :cond_0
    move-object v0, v1

    :goto_0
    invoke-virtual {p1}, Landroid/media/Image;->close()V

    new-instance p1, Ljava/io/ByteArrayOutputStream;

    invoke-direct {p1}, Ljava/io/ByteArrayOutputStream;-><init>()V

    iget v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapOrientation:I

    iget-object v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mCaptureResult:Landroid/hardware/camera2/CaptureResult;

    invoke-static {v0, p1, v1, v2}, Lcom/android/camera/Util;->appendExifToBitmap(Landroid/graphics/Bitmap;Ljava/io/OutputStream;ILandroid/hardware/camera2/CaptureResult;)V

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mJpegCallback:Lcom/android/camera/module/VideoModule$JpegPictureCallback;

    if-eqz v0, :cond_1

    invoke-virtual {p1}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Lcom/android/camera/module/VideoModule$JpegPictureCallback;->onPictureTaken([BLandroid/hardware/camera2/CaptureResult;)V

    iput-object v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mJpegCallback:Lcom/android/camera/module/VideoModule$JpegPictureCallback;

    :cond_1
    :try_start_0
    invoke-virtual {p1}, Ljava/io/ByteArrayOutputStream;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_1

    :catch_0
    move-exception p0

    invoke-virtual {p0}, Ljava/io/IOException;->printStackTrace()V

    :cond_2
    :goto_1
    return-void
.end method

.method private shouldDrawBlur(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    iget-boolean p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mIsComposeSwitching:Z

    if-eqz p0, :cond_1

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    const/16 v0, 0x14

    if-ge p0, v0, :cond_0

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getLastRenderComposeType()I

    move-result p0

    if-lt p0, v0, :cond_1

    :cond_0
    const/4 p0, 0x1

    goto :goto_0

    :cond_1
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private updateBlurTex(Lcom/android/gallery3d/ui/GLCanvas;Ljava/util/List;)V
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/gallery3d/ui/GLCanvas;",
            "Ljava/util/List<",
            "Lcom/android/camera/dualvideo/render/IRenderable;",
            ">;)V"
        }
    .end annotation

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedUpdateBlurTex:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getVisibileRenderList()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/d;->INSTANCE:Lcom/android/camera/dualvideo/d;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result v0

    sget-object v1, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "updateBlurTex: contain front: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v1, 0x0

    const/16 v2, 0xb

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {v0, v2}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    check-cast v0, Lcom/android/gallery3d/ui/RawTexture;

    invoke-interface {p2}, Ljava/util/List;->stream()Ljava/util/stream/Stream;

    move-result-object v3

    sget-object v4, Lcom/android/camera/dualvideo/g;->INSTANCE:Lcom/android/camera/dualvideo/g;

    invoke-interface {v3, v4}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v3

    invoke-interface {v3}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object v3

    sget-object v4, Lcom/android/camera/dualvideo/c;->INSTANCE:Lcom/android/camera/dualvideo/c;

    invoke-virtual {v3, v4}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    invoke-static {p1, v0, v3}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->copyPreviewTexture(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    const/16 v3, 0xc

    invoke-virtual {v0, v3}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    check-cast v0, Lcom/android/gallery3d/ui/RawTexture;

    invoke-interface {p2}, Ljava/util/List;->stream()Ljava/util/stream/Stream;

    move-result-object p2

    sget-object v4, Lcom/android/camera/dualvideo/w;->INSTANCE:Lcom/android/camera/dualvideo/w;

    invoke-interface {p2, v4}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p2

    invoke-interface {p2}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p2

    sget-object v4, Lcom/android/camera/dualvideo/k;->INSTANCE:Lcom/android/camera/dualvideo/k;

    invoke-virtual {p2, v4}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p2

    invoke-virtual {p2, v1}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p2

    check-cast p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    invoke-static {p1, v0, p2}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->copyPreviewTexture(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {p2, v2}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object p2

    check-cast p2, Lcom/android/gallery3d/ui/RawTexture;

    invoke-static {p1, p2}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->blurTex(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;)V

    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {p2, v3}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object p2

    check-cast p2, Lcom/android/gallery3d/ui/RawTexture;

    invoke-static {p1, p2}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->blurTex(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;)V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {v0, v2}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object v0

    check-cast v0, Lcom/android/gallery3d/ui/RawTexture;

    invoke-interface {p2}, Ljava/util/List;->stream()Ljava/util/stream/Stream;

    move-result-object p2

    sget-object v3, Lcom/android/camera/dualvideo/e;->INSTANCE:Lcom/android/camera/dualvideo/e;

    invoke-interface {p2, v3}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p2

    invoke-interface {p2}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p2

    sget-object v3, Lcom/android/camera/dualvideo/p;->INSTANCE:Lcom/android/camera/dualvideo/p;

    invoke-virtual {p2, v3}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p2

    invoke-virtual {p2, v1}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p2

    check-cast p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    invoke-static {p1, v0, p2}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->copyPreviewTexture(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {p2, v2}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->getTexture(I)Lcom/android/gallery3d/ui/BasicTexture;

    move-result-object p2

    check-cast p2, Lcom/android/gallery3d/ui/RawTexture;

    invoke-static {p1, p2}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->blurTex(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;)V

    :goto_0
    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedUpdateBlurTex:Z

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mUpdateBlurConditionVar:Landroid/os/ConditionVariable;

    invoke-virtual {p0}, Landroid/os/ConditionVariable;->open()V

    return-void
.end method

.method private waitVideoDrawFinish()V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedRecording:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    invoke-virtual {v0}, Lcom/android/camera/module/encoder/RenderHandler;->waitDrawFinish()V

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    invoke-virtual {p0}, Lcom/android/camera/module/encoder/RenderHandler;->waitDrawFinish()V

    return-void
.end method


# virtual methods
.method public synthetic a(Landroid/graphics/SurfaceTexture;)V
    .locals 2

    iget-boolean p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubFrameReady:Z

    if-nez p1, :cond_1

    iget p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSkipFrame:I

    const/4 v0, 0x1

    if-lez p1, :cond_0

    sget-object p1, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "sub frame skipped: "

    invoke-static {p1, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSkipFrame:I

    sub-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSkipFrame:I

    goto :goto_0

    :cond_0
    sget-object p1, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "subFrameReady"

    invoke-static {p1, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubFrameReady:Z

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->enableContinuousRender(Z)V

    :cond_1
    :goto_0
    return-void
.end method

.method public synthetic a(Landroid/media/ImageReader;)V
    .locals 0

    invoke-virtual {p1}, Landroid/media/ImageReader;->acquireNextImage()Landroid/media/Image;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->saveJpeg(Landroid/media/Image;)V

    return-void
.end method

.method public synthetic a(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mCameraIdManager:Lcom/android/camera/dualvideo/CameraIDManager;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/CameraIDManager;->get6Patch2CameraId()Landroid/util/SparseArray;

    move-result-object p0

    invoke-virtual {p1, p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->changeTexture(Landroid/util/SparseArray;)V

    return-void
.end method

.method public synthetic b(Lcom/android/camera/dualvideo/render/IRenderable;)Ljava/lang/Integer;
    .locals 0

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    iget-object p1, p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result p1

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {p0}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result p0

    if-ne p1, p0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object p0

    const/16 p1, 0x65

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    return-object p0

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object p0

    const/16 p1, 0x64

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    return-object p0
.end method

.method public captureVideoSnapshot(Lcom/android/camera/module/VideoModule$JpegPictureCallback;ILandroid/hardware/camera2/CaptureResult;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapPending:Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapPending:Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    iput-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mJpegCallback:Lcom/android/camera/module/VideoModule$JpegPictureCallback;

    iput-object p3, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mCaptureResult:Landroid/hardware/camera2/CaptureResult;

    iget-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapAnimTimer:Lcom/android/camera/dualvideo/render/Timer;

    const-wide/16 v0, 0x3c

    invoke-virtual {p1, v0, v1}, Lcom/android/camera/dualvideo/render/Timer;->init(J)V

    const/4 p1, -0x1

    if-ne p2, p1, :cond_1

    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapOrientation:I

    goto :goto_0

    :cond_1
    iput p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapOrientation:I

    :goto_0
    return-void
.end method

.method public createSubCameraSurfaceIfNeed()Landroid/view/Surface;
    .locals 3

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurface:Landroid/view/Surface;

    if-nez v0, :cond_0

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "createSubCameraSurfaceIfNeed: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->findSubPreviewSize()Landroid/util/Size;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    new-instance v0, Landroid/graphics/SurfaceTexture;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Landroid/graphics/SurfaceTexture;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    invoke-virtual {v1}, Landroid/util/Size;->getWidth()I

    move-result v1

    iget-object v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    invoke-virtual {v2}, Landroid/util/Size;->getHeight()I

    move-result v2

    invoke-virtual {v0, v1, v2}, Landroid/graphics/SurfaceTexture;->setDefaultBufferSize(II)V

    new-instance v0, Landroid/view/Surface;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    invoke-direct {v0, v1}, Landroid/view/Surface;-><init>(Landroid/graphics/SurfaceTexture;)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurface:Landroid/view/Surface;

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    new-instance v1, Lcom/android/camera/dualvideo/q;

    invoke-direct {v1, p0}, Lcom/android/camera/dualvideo/q;-><init>(Lcom/android/camera/dualvideo/DualVideoControler;)V

    invoke-virtual {v0, v1}, Landroid/graphics/SurfaceTexture;->setOnFrameAvailableListener(Landroid/graphics/SurfaceTexture$OnFrameAvailableListener;)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    invoke-direct {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->findSubPreviewSize()Landroid/util/Size;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/util/Size;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->findSubPreviewSize()Landroid/util/Size;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    invoke-virtual {v1}, Landroid/util/Size;->getWidth()I

    move-result v1

    iget-object v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubPreviewSize:Landroid/util/Size;

    invoke-virtual {v2}, Landroid/util/Size;->getHeight()I

    move-result v2

    invoke-virtual {v0, v1, v2}, Landroid/graphics/SurfaceTexture;->setDefaultBufferSize(II)V

    :cond_1
    :goto_0
    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubSurface:Landroid/view/Surface;

    return-object p0
.end method

.method public dispatchTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubFrameReady:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->hasMiniPreview()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->updateMiniWindowLocation(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->is6PatchWindow()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->selectItem(Landroid/view/MotionEvent;)Z

    const/4 p0, 0x1

    return p0

    :cond_1
    const/4 p0, 0x0

    return p0
.end method

.method public enableContinuousRender(Z)V
    .locals 3

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "enableContinuousRender: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput-boolean p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mIsComposeSwitching:Z

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->switchTexture()V

    return-void
.end method

.method public expandBottom()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->expandBottom()Z

    move-result v1

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->saveSettingsForComposeTypeChanged()V

    monitor-exit v0

    return v1

    :cond_0
    monitor-exit v0

    const/4 p0, 0x0

    return p0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public expandOrShrinkTop()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->expandOrShrinkTop()Z

    move-result v1

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->saveSettingsForComposeTypeChanged()V

    monitor-exit v0

    return v1

    :cond_0
    monitor-exit v0

    const/4 p0, 0x0

    return p0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public getIdByPosition(FF)I
    .locals 3

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    const/4 v1, -0x1

    if-nez v0, :cond_0

    return v1

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getVisibileRenderList()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    new-instance v2, Lcom/android/camera/dualvideo/l;

    invoke-direct {v2, p1, p2}, Lcom/android/camera/dualvideo/l;-><init>(FF)V

    invoke-interface {v0, v2}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p1

    invoke-interface {p1}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p1

    new-instance p2, Lcom/android/camera/dualvideo/m;

    invoke-direct {p2, p0}, Lcom/android/camera/dualvideo/m;-><init>(Lcom/android/camera/dualvideo/DualVideoControler;)V

    invoke-virtual {p1, p2}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    return p0
.end method

.method public getProcessorType()I
    .locals 0

    const/4 p0, 0x1

    return p0
.end method

.method public getRenderComposeTypeByPosition(FF)I
    .locals 2

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    const/16 v0, 0xd

    if-nez p0, :cond_0

    return v0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableList()Ljava/util/ArrayList;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    new-instance v1, Lcom/android/camera/dualvideo/i;

    invoke-direct {v1, p1, p2}, Lcom/android/camera/dualvideo/i;-><init>(FF)V

    invoke-interface {p0, v1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p0

    sget-object p1, Lcom/android/camera/dualvideo/x;->INSTANCE:Lcom/android/camera/dualvideo/x;

    invoke-virtual {p0, p1}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    return p0
.end method

.method public hasMiniComposeType()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableList()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1

    return v1

    :cond_1
    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableList()Ljava/util/ArrayList;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object v0, Lcom/android/camera/dualvideo/v;->INSTANCE:Lcom/android/camera/dualvideo/v;

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result p0

    if-eqz p0, :cond_2

    const/4 p0, 0x1

    return p0

    :cond_2
    return v1
.end method

.method public isProcessorReady()Z
    .locals 0

    const/4 p0, 0x1

    return p0
.end method

.method public isRecording()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedRecording:Z

    return p0
.end method

.method public isSwitching()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget-boolean p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mIsComposeSwitching:Z

    if-nez p0, :cond_1

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->isAnimating()Z

    move-result p0

    if-eqz p0, :cond_2

    :cond_1
    const/4 v1, 0x1

    :cond_2
    return v1
.end method

.method public isZoomEnabled()Z
    .locals 1

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableList()Ljava/util/ArrayList;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object v0, Lcom/android/camera/dualvideo/r;->INSTANCE:Lcom/android/camera/dualvideo/r;

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result p0

    xor-int/lit8 p0, p0, 0x1

    return p0
.end method

.method public onDrawFrame(Lcom/android/gallery3d/ui/GLCanvasImpl;Lcom/android/camera/CameraScreenNail;)V
    .locals 9

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BaseGLCanvas;->clearBuffer()V

    const/16 v0, 0x10

    new-array v3, v0, [F

    invoke-virtual {p2}, Lcom/android/camera/SurfaceTextureScreenNail;->getSurfaceTexture()Landroid/graphics/SurfaceTexture;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/graphics/SurfaceTexture;->getTransformMatrix([F)V

    invoke-virtual {p2}, Lcom/android/camera/CameraScreenNail;->getDisplayRect()Landroid/graphics/Rect;

    move-result-object v0

    if-nez v0, :cond_0

    sget-object p0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string p1, "onDrawFrame: disaplay rect is null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v1

    invoke-virtual {v1}, Lb/c/a/b;->tj()Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_1

    iput-boolean v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mWeNeedBlurAnim:Z

    :cond_1
    iget-boolean v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mWeNeedBlurAnim:Z

    if-eqz v1, :cond_5

    invoke-virtual {v0}, Landroid/graphics/Rect;->height()I

    move-result v1

    const/16 v4, 0x5a0

    if-ne v1, v4, :cond_2

    iput-boolean v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mWeNeedBlurAnim:Z

    goto :goto_0

    :cond_2
    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mEaseOutAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-nez v1, :cond_3

    new-instance v1, Lcom/android/camera/dualvideo/render/EaseOutAnim;

    const/16 v4, 0xc8

    invoke-direct {v1, v4}, Lcom/android/camera/dualvideo/render/EaseOutAnim;-><init>(I)V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mEaseOutAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    new-instance v1, Landroid/graphics/Rect;

    invoke-direct {v1, v0}, Landroid/graphics/Rect;-><init>(Landroid/graphics/Rect;)V

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mAnimStartRect:Landroid/graphics/Rect;

    :cond_3
    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mEaseOutAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    invoke-virtual {v1}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->isFinished()Z

    move-result v1

    if-eqz v1, :cond_4

    iput-boolean v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mWeNeedBlurAnim:Z

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mEaseOutAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    goto :goto_0

    :cond_4
    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mEaseOutAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    invoke-virtual {v1}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->getRatio()F

    move-result v1

    iget-object v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mAnimStartRect:Landroid/graphics/Rect;

    iget-object v4, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mAnimDstRect:Landroid/graphics/Rect;

    invoke-direct {p0, v2, v4, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->RectIterpolate(Landroid/graphics/Rect;Landroid/graphics/Rect;F)Landroid/graphics/Rect;

    move-result-object v1

    invoke-direct {p0, p1, p2, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawBlur(Lcom/android/gallery3d/ui/GLCanvasImpl;Lcom/android/camera/CameraScreenNail;Landroid/graphics/Rect;)V

    :cond_5
    :goto_0
    iget-boolean v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mWeNeedBlurAnim:Z

    if-nez v1, :cond_7

    new-instance v8, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    invoke-virtual {p2}, Lcom/android/camera/SurfaceTextureScreenNail;->getExtTexture()Lcom/android/gallery3d/ui/ExtTexture;

    move-result-object v2

    iget v4, v0, Landroid/graphics/Rect;->left:I

    iget v5, v0, Landroid/graphics/Rect;->top:I

    invoke-virtual {v0}, Landroid/graphics/Rect;->width()I

    move-result v6

    invoke-virtual {v0}, Landroid/graphics/Rect;->height()I

    move-result v7

    move-object v1, v8

    invoke-direct/range {v1 .. v7}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)V

    invoke-virtual {p0, p1, v8}, Lcom/android/camera/dualvideo/DualVideoControler;->onDrawFrame(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)Z

    move-result v1

    if-nez v1, :cond_7

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v1

    invoke-virtual {v1}, Lb/c/a/b;->tj()Z

    move-result v1

    if-eqz v1, :cond_6

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mAnimDstRect:Landroid/graphics/Rect;

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawBlur(Lcom/android/gallery3d/ui/GLCanvasImpl;Lcom/android/camera/CameraScreenNail;Landroid/graphics/Rect;)V

    goto :goto_1

    :cond_6
    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/dualvideo/DualVideoControler;->drawBlur(Lcom/android/gallery3d/ui/GLCanvasImpl;Lcom/android/camera/CameraScreenNail;Landroid/graphics/Rect;)V

    :cond_7
    :goto_1
    return-void
.end method

.method public onDrawFrame(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)Z
    .locals 0

    iput-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    monitor-enter p2

    :try_start_0
    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->drawDualVideo(Lcom/android/gallery3d/ui/GLCanvas;)Z

    move-result p1

    monitor-exit p2
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    iget-boolean p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mIsComposeSwitching:Z

    if-eqz p2, :cond_0

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mListener:Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;

    invoke-interface {p0}, Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;->onRenderRequestNeeded()V

    :cond_0
    return p1

    :catchall_0
    move-exception p0

    :try_start_1
    monitor-exit p2
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0
.end method

.method public release(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "release: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    monitor-enter v0

    const/4 v1, 0x0

    :try_start_0
    iput-boolean v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubFrameReady:Z

    const/4 v1, 0x2

    iput v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSkipFrame:I

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mJpegCallback:Lcom/android/camera/module/VideoModule$JpegPictureCallback;

    invoke-direct {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->releaseRecordingRelated()V

    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->releaseSurfaceTexture(Lcom/android/gallery3d/ui/GLCanvas;)V

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->release()V

    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public resetFrameReady()V
    .locals 1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubFrameReady:Z

    const/4 v0, 0x2

    iput v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSkipFrame:I

    return-void
.end method

.method public saveSettingsForComposeTypeChanged()V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->printRenderList()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getmSelectedData()Ljava/util/ArrayList;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v1

    iget-object v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v2}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getVisibileRenderList()Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-interface {v3}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v4

    iget-object v4, v4, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {v4}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result v4

    iget-object v5, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {v5}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result v5

    if-ne v4, v5, :cond_0

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v4

    new-instance v5, Lcom/android/camera/dualvideo/j;

    invoke-direct {v5, v1}, Lcom/android/camera/dualvideo/j;-><init>(Ljava/util/concurrent/ConcurrentHashMap;)V

    invoke-interface {v4, v5}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v4

    new-instance v5, Lcom/android/camera/dualvideo/o;

    invoke-direct {v5, v3}, Lcom/android/camera/dualvideo/o;-><init>(Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-interface {v4, v5}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v4

    new-instance v5, Lcom/android/camera/dualvideo/f;

    invoke-direct {v5, v1}, Lcom/android/camera/dualvideo/f;-><init>(Ljava/util/concurrent/ConcurrentHashMap;)V

    invoke-interface {v4, v5}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v4

    new-instance v5, Lcom/android/camera/dualvideo/t;

    invoke-direct {v5, v3}, Lcom/android/camera/dualvideo/t;-><init>(Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-interface {v4, v5}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    goto :goto_0

    :cond_1
    return-void
.end method

.method public setListener(Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mListener:Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;

    return-void
.end method

.method public setOrientation(I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mMiscTexManager:Lcom/android/camera/dualvideo/render/MiscTextureManager;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->setTexTransDegree(I)V

    return-void
.end method

.method public setmResources(Landroid/content/res/Resources;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mResources:Landroid/content/res/Resources;

    return-void
.end method

.method public startRecording(Landroid/opengl/EGLContext;Landroid/view/Surface;)V
    .locals 5

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "startRecording: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    const/4 v2, 0x1

    if-nez v1, :cond_0

    sget-object v1, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    sget-object v3, Lcom/android/camera/dualvideo/render/DualVideoUtil;->OUTPUT_SIZE:Landroid/util/Size;

    invoke-virtual {v3}, Landroid/util/Size;->getWidth()I

    move-result v3

    sget-object v4, Lcom/android/camera/dualvideo/render/DualVideoUtil;->OUTPUT_SIZE:Landroid/util/Size;

    invoke-virtual {v4}, Landroid/util/Size;->getHeight()I

    move-result v4

    invoke-static {v1, v3, v4}, Lcom/android/camera/module/encoder/RenderHandler;->createHandler(Ljava/lang/String;II)Lcom/android/camera/module/encoder/RenderHandler;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRecordRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    invoke-virtual {v1, p1, p2, v2}, Lcom/android/camera/module/encoder/RenderHandler;->setEglContext(Landroid/opengl/EGLContext;Ljava/lang/Object;Z)V

    :cond_0
    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapReader:Landroid/media/ImageReader;

    if-nez p2, :cond_1

    sget-object p2, Lcom/android/camera/dualvideo/render/DualVideoUtil;->OUTPUT_SIZE:Landroid/util/Size;

    invoke-virtual {p2}, Landroid/util/Size;->getWidth()I

    move-result p2

    sget-object v1, Lcom/android/camera/dualvideo/render/DualVideoUtil;->OUTPUT_SIZE:Landroid/util/Size;

    invoke-virtual {v1}, Landroid/util/Size;->getHeight()I

    move-result v1

    invoke-static {p2, v1, v2, v2}, Landroid/media/ImageReader;->newInstance(IIII)Landroid/media/ImageReader;

    move-result-object p2

    iput-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapReader:Landroid/media/ImageReader;

    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapReader:Landroid/media/ImageReader;

    new-instance v1, Lcom/android/camera/dualvideo/u;

    invoke-direct {v1, p0}, Lcom/android/camera/dualvideo/u;-><init>(Lcom/android/camera/dualvideo/DualVideoControler;)V

    const/4 v3, 0x0

    invoke-virtual {p2, v1, v3}, Landroid/media/ImageReader;->setOnImageAvailableListener(Landroid/media/ImageReader$OnImageAvailableListener;Landroid/os/Handler;)V

    :cond_1
    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    if-nez p2, :cond_2

    sget-object p2, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    sget-object v1, Lcom/android/camera/dualvideo/render/DualVideoUtil;->OUTPUT_SIZE:Landroid/util/Size;

    invoke-virtual {v1}, Landroid/util/Size;->getWidth()I

    move-result v1

    sget-object v3, Lcom/android/camera/dualvideo/render/DualVideoUtil;->OUTPUT_SIZE:Landroid/util/Size;

    invoke-virtual {v3}, Landroid/util/Size;->getHeight()I

    move-result v3

    invoke-static {p2, v1, v3}, Lcom/android/camera/module/encoder/RenderHandler;->createHandler(Ljava/lang/String;II)Lcom/android/camera/module/encoder/RenderHandler;

    move-result-object p2

    iput-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    iget-object p2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapRenderHandler:Lcom/android/camera/module/encoder/RenderHandler;

    iget-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mSnapReader:Landroid/media/ImageReader;

    invoke-virtual {v1}, Landroid/media/ImageReader;->getSurface()Landroid/view/Surface;

    move-result-object v1

    invoke-virtual {p2, p1, v1, v2}, Lcom/android/camera/module/encoder/RenderHandler;->setEglContext(Landroid/opengl/EGLContext;Ljava/lang/Object;Z)V

    :cond_2
    iput-boolean v2, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedRecording:Z

    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public stopRecording()V
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "stopRecording: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderLock:Ljava/lang/Object;

    monitor-enter v0

    const/4 v1, 0x0

    :try_start_0
    iput-boolean v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedRecording:Z

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mJpegCallback:Lcom/android/camera/module/VideoModule$JpegPictureCallback;

    invoke-direct {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->releaseRecordingRelated()V

    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public switch6patch2preview()V
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "switch6patch2preview: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mIsComposeSwitching:Z

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->triggerUpdateBlurTex()V

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->switch6patch2preview()V

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->printRenderList()V

    :cond_0
    return-void
.end method

.method public switchPreviewTo6Patch()V
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "switchPreviewTo6Patch: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-eqz v0, :cond_0

    iget-boolean v1, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mIsComposeSwitching:Z

    if-nez v1, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->switchPreviewTo6Patch()V

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->printRenderList()V

    :cond_0
    return-void
.end method

.method public switchTexture()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-static {v0}, Ljava/util/Optional;->ofNullable(Ljava/lang/Object;)Ljava/util/Optional;

    move-result-object v0

    new-instance v1, Lcom/android/camera/dualvideo/s;

    invoke-direct {v1, p0}, Lcom/android/camera/dualvideo/s;-><init>(Lcom/android/camera/dualvideo/DualVideoControler;)V

    invoke-virtual {v0, v1}, Ljava/util/Optional;->ifPresent(Ljava/util/function/Consumer;)V

    return-void
.end method

.method public switchTopBottom()Z
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "switchTopBottom: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mRenderManager:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->switchTopBottom()Z

    move-result v0

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->saveSettingsForComposeTypeChanged()V

    return v0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public triggerUpdateBlurTex()V
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/DualVideoControler;->TAG:Ljava/lang/String;

    const-string v1, "triggerUpdateBlurTex: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedUpdateBlurTex:Z

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mUpdateBlurConditionVar:Landroid/os/ConditionVariable;

    invoke-virtual {v0}, Landroid/os/ConditionVariable;->close()V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mNeedUpdateBlurTex:Z

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoControler;->mUpdateBlurConditionVar:Landroid/os/ConditionVariable;

    const-wide/16 v0, 0x1f4

    invoke-virtual {p0, v0, v1}, Landroid/os/ConditionVariable;->block(J)Z

    return-void
.end method
