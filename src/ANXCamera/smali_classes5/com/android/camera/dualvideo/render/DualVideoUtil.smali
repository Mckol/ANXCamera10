.class public Lcom/android/camera/dualvideo/render/DualVideoUtil;
.super Ljava/lang/Object;
.source "DualVideoUtil.java"


# static fields
.field public static BACK_PREVIE:Landroid/util/Size; = null

.field public static FRONT_PREVIE:Landroid/util/Size; = null

.field public static final LINE_COLOR:I = 0x6a6a6a6a

.field public static OUTPUT_SIZE:Landroid/util/Size;


# direct methods
.method static constructor <clinit>()V
    .locals 3

    new-instance v0, Landroid/util/Size;

    const/16 v1, 0x500

    const/16 v2, 0x2d0

    invoke-direct {v0, v1, v2}, Landroid/util/Size;-><init>(II)V

    sput-object v0, Lcom/android/camera/dualvideo/render/DualVideoUtil;->FRONT_PREVIE:Landroid/util/Size;

    new-instance v0, Landroid/util/Size;

    const/16 v1, 0x438

    const/16 v2, 0x780

    invoke-direct {v0, v2, v1}, Landroid/util/Size;-><init>(II)V

    sput-object v0, Lcom/android/camera/dualvideo/render/DualVideoUtil;->BACK_PREVIE:Landroid/util/Size;

    new-instance v0, Landroid/util/Size;

    invoke-direct {v0, v1, v2}, Landroid/util/Size;-><init>(II)V

    sput-object v0, Lcom/android/camera/dualvideo/render/DualVideoUtil;->OUTPUT_SIZE:Landroid/util/Size;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic b(ILcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 0

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public static blurTex(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;)V
    .locals 4

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    const/4 v2, 0x0

    :goto_0
    const/16 v3, 0x8

    if-ge v2, v3, :cond_0

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->renderBlurTexture(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;)V

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_0
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p1, "blur tex  cost time = "

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    sub-long/2addr v2, v0

    invoke-virtual {p0, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string p1, "ms"

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "DualVideoUtil"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public static centerCropHorizontal([FF)V
    .locals 4

    const/high16 v0, 0x3f800000    # 1.0f

    sub-float v1, v0, p1

    const/high16 v2, 0x40000000    # 2.0f

    div-float/2addr v1, v2

    const/4 v2, 0x0

    const/4 v3, 0x0

    invoke-static {p0, v3, v1, v2, v2}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    invoke-static {p0, v3, p1, v0, v0}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    return-void
.end method

.method public static centerCropVertical([FF)V
    .locals 4

    const/high16 v0, 0x3f800000    # 1.0f

    sub-float v1, v0, p1

    const/high16 v2, 0x40000000    # 2.0f

    div-float/2addr v1, v2

    const/4 v2, 0x0

    const/4 v3, 0x0

    invoke-static {p0, v3, v2, v1, v2}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    invoke-static {p0, v3, v0, p1, v0}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    return-void
.end method

.method public static centerScaleMatrix([FI)[F
    .locals 4

    const/16 v0, 0xb

    if-eq p1, v0, :cond_0

    const/16 v0, 0xc

    if-eq p1, v0, :cond_0

    const/high16 v0, 0x3f800000    # 1.0f

    const/4 v1, 0x0

    const/4 v2, 0x0

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    const v3, 0x3ef0a3d7    # 0.47f

    invoke-static {p0, v2, v3, v3, v1}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    const v1, 0x3d75c28f    # 0.06f

    invoke-static {p0, v2, v1, v1, v0}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getCropRatio(I)F

    move-result p1

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerCropVertical([FF)V

    goto :goto_0

    :pswitch_1
    const v3, 0x3ee147ae    # 0.44f

    invoke-static {p0, v2, v3, v3, v1}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    const v1, 0x3df5c28f    # 0.12f

    invoke-static {p0, v2, v1, v1, v0}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getCropRatio(I)F

    move-result p1

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerCropVertical([FF)V

    goto :goto_0

    :pswitch_2
    const v3, 0x3eb33333    # 0.35f

    invoke-static {p0, v2, v3, v3, v1}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    const v1, 0x3e99999a    # 0.3f

    invoke-static {p0, v2, v1, v1, v0}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getCropRatio(I)F

    move-result p1

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerCropVertical([FF)V

    goto :goto_0

    :pswitch_3
    const v3, 0x3e4ccccd    # 0.2f

    invoke-static {p0, v2, v3, v3, v1}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    const v1, 0x3f19999a    # 0.6f

    invoke-static {p0, v2, v1, v1, v0}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getCropRatio(I)F

    move-result p1

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerCropVertical([FF)V

    goto :goto_0

    :pswitch_4
    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getCropRatio(I)F

    move-result p1

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerCropVertical([FF)V

    goto :goto_0

    :cond_0
    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getCropRatio(I)F

    move-result p1

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerCropVertical([FF)V

    :goto_0
    return-object p0

    :pswitch_data_0
    .packed-switch 0x14
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
        :pswitch_4
    .end packed-switch
.end method

.method public static copyPreviewTexture(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V
    .locals 8

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v5

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v6

    new-instance v7, Lcom/android/camera/effect/FrameBuffer;

    const/4 v0, 0x0

    invoke-direct {v7, p0, p1, v0}, Lcom/android/camera/effect/FrameBuffer;-><init>(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;I)V

    invoke-interface {p0, v7}, Lcom/android/gallery3d/ui/GLCanvas;->beginBindFrameBuffer(Lcom/android/camera/effect/FrameBuffer;)V

    new-instance p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v1, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iget-object v2, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mTextureTransform:[F

    const/4 v3, 0x0

    const/4 v4, 0x0

    move-object v0, p1

    invoke-direct/range {v0 .. v6}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)V

    invoke-interface {p0, p1}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    invoke-static {}, Landroid/opengl/GLES20;->glFinish()V

    invoke-interface {p0}, Lcom/android/gallery3d/ui/GLCanvas;->endBindFrameBuffer()V

    invoke-virtual {v7}, Lcom/android/camera/effect/FrameBuffer;->delete()V

    return-void
.end method

.method static synthetic d(Lcom/android/camera/dualvideo/UserSelectData;)Ljava/lang/Integer;
    .locals 0

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/UserSelectData;->getIndex()I

    move-result p0

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method

.method public static getBitmap(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;
    .locals 2

    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    instance-of v1, v0, Landroid/graphics/drawable/BitmapDrawable;

    if-eqz v1, :cond_0

    invoke-static {p0, p1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0

    :cond_0
    instance-of p0, v0, Landroid/graphics/drawable/VectorDrawable;

    if-eqz p0, :cond_1

    check-cast v0, Landroid/graphics/drawable/VectorDrawable;

    invoke-static {v0}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getBitmap(Landroid/graphics/drawable/VectorDrawable;)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0

    :cond_1
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string p1, "unsupported drawable type"

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method private static getBitmap(Landroid/graphics/drawable/VectorDrawable;)Landroid/graphics/Bitmap;
    .locals 5

    invoke-virtual {p0}, Landroid/graphics/drawable/VectorDrawable;->getIntrinsicWidth()I

    move-result v0

    invoke-virtual {p0}, Landroid/graphics/drawable/VectorDrawable;->getIntrinsicHeight()I

    move-result v1

    sget-object v2, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v0, v1, v2}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v0

    new-instance v1, Landroid/graphics/Canvas;

    invoke-direct {v1, v0}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    invoke-virtual {v1}, Landroid/graphics/Canvas;->getWidth()I

    move-result v2

    invoke-virtual {v1}, Landroid/graphics/Canvas;->getHeight()I

    move-result v3

    const/4 v4, 0x0

    invoke-virtual {p0, v4, v4, v2, v3}, Landroid/graphics/drawable/VectorDrawable;->setBounds(IIII)V

    invoke-virtual {p0, v1}, Landroid/graphics/drawable/VectorDrawable;->draw(Landroid/graphics/Canvas;)V

    const/4 p0, 0x1

    invoke-virtual {v0, p0}, Landroid/graphics/Bitmap;->setPremultiplied(Z)V

    return-object v0
.end method

.method public static getCropRatio(I)F
    .locals 1

    const/16 v0, 0xb

    if-eq p0, v0, :cond_0

    const/16 v0, 0xc

    if-eq p0, v0, :cond_0

    packed-switch p0, :pswitch_data_0

    const/high16 p0, 0x3f800000    # 1.0f

    return p0

    :cond_0
    :pswitch_0
    const/high16 p0, 0x3f000000    # 0.5f

    return p0

    nop

    :pswitch_data_0
    .packed-switch 0x14
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public static getSelectedIndexBy6PathchType(I)I
    .locals 2

    invoke-static {}, Lcom/android/camera/CameraSettings;->getDualVideoConfig()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getSelectedData()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    new-instance v1, Lcom/android/camera/dualvideo/render/N;

    invoke-direct {v1, p0}, Lcom/android/camera/dualvideo/render/N;-><init>(I)V

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p0

    sget-object v0, Lcom/android/camera/dualvideo/render/O;->INSTANCE:Lcom/android/camera/dualvideo/render/O;

    invoke-virtual {p0, v0}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p0

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    return p0
.end method

.method private static getTextPaint(I)Landroid/text/TextPaint;
    .locals 3

    new-instance v0, Landroid/text/TextPaint;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Landroid/text/TextPaint;-><init>(I)V

    const/high16 v2, 0x42100000    # 36.0f

    invoke-virtual {v0, v2}, Landroid/text/TextPaint;->setTextSize(F)V

    invoke-virtual {v0, v1}, Landroid/text/TextPaint;->setAntiAlias(Z)V

    invoke-virtual {v0, p0}, Landroid/text/TextPaint;->setColor(I)V

    const-string p0, "mipro-regular"

    const/4 v1, 0x0

    invoke-static {p0, v1}, Landroid/graphics/Typeface;->create(Ljava/lang/String;I)Landroid/graphics/Typeface;

    move-result-object p0

    invoke-virtual {v0, p0}, Landroid/text/TextPaint;->setTypeface(Landroid/graphics/Typeface;)Landroid/graphics/Typeface;

    const/4 p0, 0x0

    const/high16 v1, 0x3f800000    # 1.0f

    const/high16 v2, -0x80000000

    invoke-virtual {v0, v1, p0, p0, v2}, Landroid/text/TextPaint;->setShadowLayer(FFFI)V

    invoke-virtual {v0, v1}, Landroid/text/TextPaint;->setStrokeWidth(F)V

    sget-object p0, Landroid/graphics/Paint$Style;->FILL_AND_STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, p0}, Landroid/text/TextPaint;->setStyle(Landroid/graphics/Paint$Style;)V

    return-object v0
.end method

.method public static mixArea(Landroid/graphics/Rect;Landroid/graphics/Rect;F)Landroid/graphics/Rect;
    .locals 6

    const/high16 v0, 0x3f800000    # 1.0f

    sub-float/2addr v0, p2

    new-instance v1, Landroid/graphics/Rect;

    iget v2, p0, Landroid/graphics/Rect;->left:I

    int-to-float v2, v2

    mul-float/2addr v2, v0

    iget v3, p1, Landroid/graphics/Rect;->left:I

    int-to-float v3, v3

    mul-float/2addr v3, p2

    add-float/2addr v2, v3

    float-to-int v2, v2

    iget v3, p0, Landroid/graphics/Rect;->top:I

    int-to-float v3, v3

    mul-float/2addr v3, v0

    iget v4, p1, Landroid/graphics/Rect;->top:I

    int-to-float v4, v4

    mul-float/2addr v4, p2

    add-float/2addr v3, v4

    float-to-int v3, v3

    iget v4, p0, Landroid/graphics/Rect;->right:I

    int-to-float v4, v4

    mul-float/2addr v4, v0

    iget v5, p1, Landroid/graphics/Rect;->right:I

    int-to-float v5, v5

    mul-float/2addr v5, p2

    add-float/2addr v4, v5

    float-to-int v4, v4

    iget p0, p0, Landroid/graphics/Rect;->bottom:I

    int-to-float p0, p0

    mul-float/2addr p0, v0

    iget p1, p1, Landroid/graphics/Rect;->bottom:I

    int-to-float p1, p1

    mul-float/2addr p1, p2

    add-float/2addr p0, p1

    float-to-int p0, p0

    invoke-direct {v1, v2, v3, v4, p0}, Landroid/graphics/Rect;-><init>(IIII)V

    return-object v1
.end method

.method public static mixMatrix([F[FF)[F
    .locals 5

    const/high16 v0, 0x3f800000    # 1.0f

    sub-float/2addr v0, p2

    invoke-virtual {p0}, [F->clone()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, [F

    const/4 v2, 0x0

    :goto_0
    array-length v3, p0

    if-ge v2, v3, :cond_0

    aget v3, p0, v2

    mul-float/2addr v3, v0

    aget v4, p1, v2

    mul-float/2addr v4, p2

    add-float/2addr v3, v4

    aput v3, v1, v2

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_0
    return-object v1
.end method

.method private static renderBlurTexture(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;)V
    .locals 8

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getWidth()I

    move-result v4

    invoke-virtual {p1}, Lcom/android/gallery3d/ui/BasicTexture;->getHeight()I

    move-result v5

    new-instance v6, Lcom/android/camera/effect/FrameBuffer;

    const/4 v0, 0x0

    invoke-direct {v6, p0, p1, v0}, Lcom/android/camera/effect/FrameBuffer;-><init>(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/gallery3d/ui/RawTexture;I)V

    invoke-interface {p0}, Lcom/android/gallery3d/ui/GLCanvas;->prepareBlurRenders()V

    invoke-interface {p0, v6}, Lcom/android/gallery3d/ui/GLCanvas;->beginBindFrameBuffer(Lcom/android/camera/effect/FrameBuffer;)V

    new-instance v7, Lcom/android/camera/effect/draw_mode/DrawBlurTexAttribute;

    const/4 v2, 0x0

    const/4 v3, 0x0

    move-object v0, v7

    move-object v1, p1

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/effect/draw_mode/DrawBlurTexAttribute;-><init>(Lcom/android/gallery3d/ui/BasicTexture;IIII)V

    invoke-interface {p0, v7}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    invoke-static {}, Landroid/opengl/GLES20;->glFinish()V

    invoke-interface {p0}, Lcom/android/gallery3d/ui/GLCanvas;->endBindFrameBuffer()V

    invoke-virtual {v6}, Lcom/android/camera/effect/FrameBuffer;->delete()V

    return-void
.end method

.method public static shrinkRect(Landroid/graphics/Rect;F)Landroid/graphics/Rect;
    .locals 5

    invoke-virtual {p0}, Landroid/graphics/Rect;->width()I

    move-result v0

    int-to-float v0, v0

    mul-float/2addr v0, p1

    const/high16 v1, 0x40000000    # 2.0f

    div-float/2addr v0, v1

    invoke-virtual {p0}, Landroid/graphics/Rect;->height()I

    move-result v2

    int-to-float v2, v2

    mul-float/2addr v2, p1

    div-float/2addr v2, v1

    invoke-virtual {p0}, Landroid/graphics/Rect;->exactCenterX()F

    move-result p1

    invoke-virtual {p0}, Landroid/graphics/Rect;->exactCenterY()F

    move-result p0

    new-instance v1, Landroid/graphics/Rect;

    sub-float v3, p1, v0

    float-to-int v3, v3

    sub-float v4, p0, v2

    float-to-int v4, v4

    add-float/2addr p1, v0

    float-to-int p1, p1

    add-float/2addr p0, v2

    float-to-int p0, p0

    invoke-direct {v1, v3, v4, p1, p0}, Landroid/graphics/Rect;-><init>(IIII)V

    return-object v1
.end method

.method public static textAsBitmap(Ljava/lang/String;I)Landroid/graphics/Bitmap;
    .locals 9

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->getTextPaint(I)Landroid/text/TextPaint;

    move-result-object p1

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/text/TextPaint;->setAntiAlias(Z)V

    new-instance v8, Landroid/text/StaticLayout;

    invoke-virtual {p1, p0}, Landroid/text/TextPaint;->measureText(Ljava/lang/String;)F

    move-result v0

    float-to-int v3, v0

    sget-object v4, Landroid/text/Layout$Alignment;->ALIGN_NORMAL:Landroid/text/Layout$Alignment;

    const/high16 v5, 0x3f800000    # 1.0f

    const/4 v6, 0x0

    const/4 v7, 0x1

    move-object v0, v8

    move-object v1, p0

    move-object v2, p1

    invoke-direct/range {v0 .. v7}, Landroid/text/StaticLayout;-><init>(Ljava/lang/CharSequence;Landroid/text/TextPaint;ILandroid/text/Layout$Alignment;FFZ)V

    invoke-virtual {p1, p0}, Landroid/text/TextPaint;->measureText(Ljava/lang/String;)F

    move-result p0

    float-to-int p0, p0

    const p1, 0x418b999a    # 17.45f

    invoke-static {p1}, Lcom/android/camera/Util;->dpToPixel(F)I

    move-result p1

    sget-object v0, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {p0, p1, v0}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object p0

    new-instance p1, Landroid/graphics/Canvas;

    invoke-direct {p1, p0}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    invoke-virtual {v8, p1}, Landroid/text/StaticLayout;->draw(Landroid/graphics/Canvas;)V

    return-object p0
.end method
