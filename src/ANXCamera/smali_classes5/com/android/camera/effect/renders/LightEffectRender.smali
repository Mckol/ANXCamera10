.class public Lcom/android/camera/effect/renders/LightEffectRender;
.super Lcom/android/camera/effect/renders/PixelEffectRender;
.source "LightEffectRender.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;,
        Lcom/android/camera/effect/renders/LightEffectRender$LightEffectId;
    }
.end annotation


# static fields
.field private static final FILTER_TYPE_BRIGHT_RED:I = 0x31

.field private static final FILTER_TYPE_DAZZLING:I = 0x2f

.field private static final FILTER_TYPE_DREAMLAND:I = 0x32

.field private static final FILTER_TYPE_GORGEOUS:I = 0x30

.field private static final FILTER_TYPE_LANSHAN:I = 0x2e

.field private static final FILTER_TYPE_NEON:I = 0x2a

.field private static final FILTER_TYPE_NOSTALGIA:I = 0x2c

.field private static final FILTER_TYPE_PHANTOM:I = 0x2b

.field private static final FILTER_TYPE_RAINBOW:I = 0x2d

.field private static final TAG:Ljava/lang/String; = "LightEffectRender"


# instance fields
.field private mFilterTexture:I

.field private mIsSnapshot:Z

.field private mLastRotation:I

.field private mLightBitmap:Landroid/graphics/Bitmap;

.field private mLightTexture:I

.field private mNeedBlur:Z

.field private mNeedColorSeparation:Z

.field private mNeedFilter:Z

.field private mNeedNoise:Z

.field private mNoiseBitmap:Landroid/graphics/Bitmap;

.field private mNoiseTexture:I

.field private mPreviewHeight:I

.field private mPreviewWidth:I

.field private mUniformFilterTexture:I

.field private mUniformLightTexture:I

.field private mUniformNeedBlur:I

.field private mUniformNeedColorSeparation:I

.field private mUniformNeedFilter:I

.field private mUniformNeedNoise:I

.field private mUniformNoiseTexture:I

.field private mUniformVerticalScreen:I

.field private mVerticalScreen:Z


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Lcom/android/gallery3d/ui/GLCanvas;I)V
    .locals 1
    .param p2    # I
        .annotation build Lcom/android/camera/effect/renders/LightEffectRender$LightEffectId;
        .end annotation
    .end param

    invoke-direct {p0, p1}, Lcom/android/camera/effect/renders/PixelEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedFilter:Z

    iput-boolean p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedNoise:Z

    iput-boolean p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedBlur:Z

    iput-boolean p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedColorSeparation:Z

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mVerticalScreen:Z

    iput p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mFilterTexture:I

    iput p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightTexture:I

    iput p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseTexture:I

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLastRotation:I

    iput-boolean p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mIsSnapshot:Z

    invoke-direct {p0, p2}, Lcom/android/camera/effect/renders/LightEffectRender;->prepareLightEffect(I)V

    return-void
.end method

.method private getBitmapFromAssert(Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 3

    const/4 p0, 0x0

    :try_start_0
    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/InputStream;->available()I

    move-result v1

    if-eqz v1, :cond_0

    new-instance v1, Landroid/graphics/BitmapFactory$Options;

    invoke-direct {v1}, Landroid/graphics/BitmapFactory$Options;-><init>()V

    sget-object v2, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    iput-object v2, v1, Landroid/graphics/BitmapFactory$Options;->inPreferredConfig:Landroid/graphics/Bitmap$Config;

    invoke-static {v0, p0, v1}, Landroid/graphics/BitmapFactory;->decodeStream(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;

    move-result-object p0

    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    sget-object v0, Lcom/android/camera/effect/renders/LightEffectRender;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "get assert failed, path:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    :goto_0
    return-object p0
.end method

.method private getCropOperationForCapture(I)Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;
    .locals 7

    new-instance v0, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;-><init>(Lcom/android/camera/effect/renders/LightEffectRender;Lcom/android/camera/effect/renders/LightEffectRender$1;)V

    iget-object v2, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v2

    iget-object v3, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v3}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v3

    const/16 v4, 0x5a

    add-int/2addr p1, v4

    rem-int/lit16 p1, p1, 0x168

    const/4 v5, 0x0

    if-eqz p1, :cond_3

    if-eq p1, v4, :cond_2

    const/16 v4, 0xb4

    if-eq p1, v4, :cond_1

    const/16 v4, 0x10e

    if-eq p1, v4, :cond_0

    sget-object p0, Lcom/android/camera/effect/renders/LightEffectRender;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "unknown degree:"

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :cond_0
    iget p0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewHeight:I

    sub-int/2addr v2, p0

    div-int/lit8 p1, v2, 0x2

    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    const/high16 v2, 0x42b40000    # 90.0f

    invoke-virtual {v1, v2}, Landroid/graphics/Matrix;->setRotate(F)V

    goto :goto_1

    :cond_1
    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    const/high16 p0, 0x43340000    # 180.0f

    invoke-virtual {v1, p0}, Landroid/graphics/Matrix;->setRotate(F)V

    goto :goto_0

    :cond_2
    iget p0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewHeight:I

    sub-int/2addr v2, p0

    div-int/lit8 p1, v2, 0x2

    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    const/high16 v2, 0x43870000    # 270.0f

    invoke-virtual {v1, v2}, Landroid/graphics/Matrix;->setRotate(F)V

    goto :goto_1

    :cond_3
    :goto_0
    move p0, v2

    move p1, v5

    :goto_1
    new-instance v2, Landroid/graphics/Rect;

    invoke-direct {v2, p1, v5, p0, v3}, Landroid/graphics/Rect;-><init>(IIII)V

    iput-object v2, v0, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iput-object v1, v0, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->matrix:Landroid/graphics/Matrix;

    return-object v0
.end method

.method private getCropOperationForPreview(I)Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;
    .locals 7

    new-instance v0, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;-><init>(Lcom/android/camera/effect/renders/LightEffectRender;Lcom/android/camera/effect/renders/LightEffectRender$1;)V

    iget-object v2, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v2

    iget-object v3, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v3}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v3

    const/4 v4, 0x0

    if-eqz p1, :cond_3

    const/16 v5, 0x5a

    if-eq p1, v5, :cond_2

    const/16 v5, 0xb4

    if-eq p1, v5, :cond_1

    const/16 p0, 0x10e

    if-eq p1, p0, :cond_0

    sget-object p0, Lcom/android/camera/effect/renders/LightEffectRender;->TAG:Ljava/lang/String;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "unknown degree:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    move p1, v4

    goto :goto_2

    :cond_0
    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    const/high16 p0, 0x42b40000    # 90.0f

    invoke-virtual {v1, p0}, Landroid/graphics/Matrix;->setRotate(F)V

    goto :goto_0

    :cond_1
    iget p0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewWidth:I

    sub-int/2addr v2, p0

    div-int/lit8 p1, v2, 0x2

    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    const/high16 v2, 0x43340000    # 180.0f

    invoke-virtual {v1, v2}, Landroid/graphics/Matrix;->setRotate(F)V

    goto :goto_1

    :cond_2
    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    const/high16 p0, 0x43870000    # 270.0f

    invoke-virtual {v1, p0}, Landroid/graphics/Matrix;->setRotate(F)V

    goto :goto_0

    :cond_3
    iget p0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewWidth:I

    sub-int/2addr v2, p0

    div-int/lit8 p1, v2, 0x2

    :goto_1
    move v2, p0

    :goto_2
    new-instance p0, Landroid/graphics/Rect;

    invoke-direct {p0, p1, v4, v2, v3}, Landroid/graphics/Rect;-><init>(IIII)V

    iput-object p0, v0, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iput-object v1, v0, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->matrix:Landroid/graphics/Matrix;

    return-object v0
.end method

.method private prepareLightEffect(I)V
    .locals 4
    .param p1    # I
        .annotation build Lcom/android/camera/effect/renders/LightEffectRender$LightEffectId;
        .end annotation
    .end param

    const-string v0, ""

    packed-switch p1, :pswitch_data_0

    move-object v1, v0

    goto :goto_0

    :pswitch_0
    const-string v1, "effect/light_effect/DREAMLAND.png"

    goto :goto_0

    :pswitch_1
    const-string v1, "effect/light_effect/BRIGHT_RED.png"

    goto :goto_0

    :pswitch_2
    const-string v1, "effect/light_effect/GORGEOUS.png"

    goto :goto_0

    :pswitch_3
    const-string v1, "effect/light_effect/DAZZLING.png"

    goto :goto_0

    :pswitch_4
    const-string v1, "effect/light_effect/LANSHAN.png"

    goto :goto_0

    :pswitch_5
    const-string v1, "effect/light_effect/RAINBOW.png"

    goto :goto_0

    :pswitch_6
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedNoise:Z

    const-string v0, "effect/light_effect/NOSTALGIA.png"

    const-string v1, "effect/light_effect/NOSTALGIA_NOISE.png"

    move-object v3, v1

    move-object v1, v0

    move-object v0, v3

    goto :goto_0

    :pswitch_7
    const-string v1, "effect/light_effect/PHANTOM.png"

    goto :goto_0

    :pswitch_8
    const-string v1, "effect/light_effect/NEON.png"

    :goto_0
    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_0

    invoke-direct {p0, v1}, Lcom/android/camera/effect/renders/LightEffectRender;->getBitmapFromAssert(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v1

    iput-object v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    :cond_0
    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-direct {p0, v0}, Lcom/android/camera/effect/renders/LightEffectRender;->getBitmapFromAssert(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseBitmap:Landroid/graphics/Bitmap;

    :cond_1
    sget-object p0, Lcom/android/camera/effect/renders/LightEffectRender;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "prepareLightEffect: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :pswitch_data_0
    .packed-switch 0x2a
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private safeDeleteTexture(I)V
    .locals 2

    const/4 p0, 0x1

    new-array v0, p0, [I

    const/4 v1, 0x0

    aput p1, v0, v1

    invoke-static {p0, v0, v1}, Landroid/opengl/GLES20;->glDeleteTextures(I[II)V

    return-void
.end method

.method private updateLightTexture()V
    .locals 18

    move-object/from16 v0, p0

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/effect/EffectController;->getOrientation()I

    move-result v1

    iget v2, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mLastRotation:I

    if-eq v2, v1, :cond_5

    iget-boolean v2, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mIsSnapshot:Z

    if-eqz v2, :cond_0

    invoke-direct {v0, v1}, Lcom/android/camera/effect/renders/LightEffectRender;->getCropOperationForCapture(I)Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;

    move-result-object v2

    goto :goto_0

    :cond_0
    invoke-direct {v0, v1}, Lcom/android/camera/effect/renders/LightEffectRender;->getCropOperationForPreview(I)Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;

    move-result-object v2

    :goto_0
    iget-object v3, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    const/16 v10, 0xde1

    if-eqz v3, :cond_2

    iget-object v4, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iget v5, v4, Landroid/graphics/Rect;->left:I

    iget v6, v4, Landroid/graphics/Rect;->top:I

    iget v7, v4, Landroid/graphics/Rect;->right:I

    iget v8, v4, Landroid/graphics/Rect;->bottom:I

    iget-object v9, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->matrix:Landroid/graphics/Matrix;

    const/4 v11, 0x0

    move v4, v5

    move v5, v6

    move v6, v7

    move v7, v8

    move-object v8, v9

    move v9, v11

    invoke-static/range {v3 .. v9}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;

    move-result-object v3

    iget v4, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightTexture:I

    if-eqz v4, :cond_1

    invoke-direct {v0, v4}, Lcom/android/camera/effect/renders/LightEffectRender;->safeDeleteTexture(I)V

    :cond_1
    invoke-static {v10, v3}, Lcom/android/camera/sticker/glutils/GlUtil;->createTexture(ILandroid/graphics/Bitmap;)I

    move-result v3

    iput v3, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightTexture:I

    :cond_2
    iget-boolean v3, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedNoise:Z

    if-eqz v3, :cond_4

    iget-object v11, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseBitmap:Landroid/graphics/Bitmap;

    if-eqz v11, :cond_4

    iget-object v3, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iget v12, v3, Landroid/graphics/Rect;->left:I

    iget v13, v3, Landroid/graphics/Rect;->top:I

    iget v14, v3, Landroid/graphics/Rect;->right:I

    iget v15, v3, Landroid/graphics/Rect;->bottom:I

    iget-object v3, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->matrix:Landroid/graphics/Matrix;

    const/16 v17, 0x0

    move-object/from16 v16, v3

    invoke-static/range {v11 .. v17}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;

    move-result-object v3

    iget v4, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseTexture:I

    if-eqz v4, :cond_3

    invoke-direct {v0, v4}, Lcom/android/camera/effect/renders/LightEffectRender;->safeDeleteTexture(I)V

    :cond_3
    invoke-static {v10, v3}, Lcom/android/camera/sticker/glutils/GlUtil;->createTexture(ILandroid/graphics/Bitmap;)I

    move-result v3

    iput v3, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseTexture:I

    :cond_4
    iput v1, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mLastRotation:I

    sget-object v3, Lcom/android/camera/effect/renders/LightEffectRender;->TAG:Ljava/lang/String;

    const/16 v4, 0x8

    new-array v4, v4, [Ljava/lang/Object;

    const/4 v5, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    aput-object v1, v4, v5

    const/4 v1, 0x1

    iget-object v5, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iget v5, v5, Landroid/graphics/Rect;->left:I

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v4, v1

    const/4 v1, 0x2

    iget-object v5, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iget v5, v5, Landroid/graphics/Rect;->top:I

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v4, v1

    const/4 v1, 0x3

    iget-object v5, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iget v5, v5, Landroid/graphics/Rect;->right:I

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v4, v1

    const/4 v1, 0x4

    iget-object v2, v2, Lcom/android/camera/effect/renders/LightEffectRender$CropOperation;->rect:Landroid/graphics/Rect;

    iget v2, v2, Landroid/graphics/Rect;->bottom:I

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    aput-object v2, v4, v1

    const/4 v1, 0x5

    iget v2, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewWidth:I

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    aput-object v2, v4, v1

    const/4 v1, 0x6

    iget v2, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewHeight:I

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    aput-object v2, v4, v1

    const/4 v1, 0x7

    iget-boolean v0, v0, Lcom/android/camera/effect/renders/LightEffectRender;->mIsSnapshot:Z

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    aput-object v0, v4, v1

    const-string v0, "updateLightTexture rotation(%d) bitmap(%d,%d,%d,%d) preview(%d,%d) mIsSnapshot(%b)"

    invoke-static {v0, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_5
    return-void
.end method


# virtual methods
.method public destroy()V
    .locals 2

    iget v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightTexture:I

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-direct {p0, v0}, Lcom/android/camera/effect/renders/LightEffectRender;->safeDeleteTexture(I)V

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightTexture:I

    :cond_0
    iget v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseTexture:I

    if-eqz v0, :cond_1

    invoke-direct {p0, v0}, Lcom/android/camera/effect/renders/LightEffectRender;->safeDeleteTexture(I)V

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseTexture:I

    :cond_1
    iget-object v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    const/4 v1, 0x0

    if-eqz v0, :cond_2

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    iput-object v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightBitmap:Landroid/graphics/Bitmap;

    :cond_2
    iget-object v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_3

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    iput-object v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseBitmap:Landroid/graphics/Bitmap;

    :cond_3
    invoke-super {p0}, Lcom/android/camera/effect/renders/ShaderRender;->destroy()V

    return-void
.end method

.method public draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z
    .locals 3

    invoke-virtual {p1}, Lcom/android/camera/effect/draw_mode/DrawAttribute;->getTarget()I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/android/camera/effect/renders/ShaderRender;->isAttriSupported(I)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p1}, Lcom/android/camera/effect/draw_mode/DrawAttribute;->getTarget()I

    move-result v0

    const/4 v1, 0x5

    if-eq v0, v1, :cond_2

    const/4 v1, 0x6

    if-eq v0, v1, :cond_1

    sget-object v0, Lcom/android/camera/effect/renders/LightEffectRender;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "attr unsupported, target:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/android/camera/effect/draw_mode/DrawAttribute;->getTarget()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :cond_1
    move-object v0, p1

    check-cast v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mTexId:I

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mFilterTexture:I

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mWidth:I

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewWidth:I

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mHeight:I

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewHeight:I

    iget-boolean v0, v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mIsSnapshot:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mIsSnapshot:Z

    goto :goto_0

    :cond_2
    move-object v0, p1

    check-cast v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget-object v1, v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mBasicTexture:Lcom/android/gallery3d/ui/BasicTexture;

    invoke-virtual {v1}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result v1

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mFilterTexture:I

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mWidth:I

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewWidth:I

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mHeight:I

    iput v1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mPreviewHeight:I

    iget-boolean v0, v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mIsSnapshot:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mIsSnapshot:Z

    :goto_0
    invoke-direct {p0}, Lcom/android/camera/effect/renders/LightEffectRender;->updateLightTexture()V

    invoke-super {p0, p1}, Lcom/android/camera/effect/renders/PixelEffectRender;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z

    move-result p0

    return p0
.end method

.method public getFragShaderString()Ljava/lang/String;
    .locals 0

    const-string p0, "frag_light.c"

    invoke-static {p0}, Lcom/android/camera/effect/ShaderUtil;->loadFromAssetsFile(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method protected initShader()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/effect/renders/PixelEffectRender;->initShader()V

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "text_filter"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformFilterTexture:I

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "text_light"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformLightTexture:I

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "text_noise"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNoiseTexture:I

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "needFilter"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedFilter:I

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "needNoise"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedNoise:I

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "needBlur"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedBlur:I

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "needColorSeparation"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedColorSeparation:I

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "verticalScreen"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformVerticalScreen:I

    return-void
.end method

.method protected initShaderValue(Z)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/effect/renders/PixelEffectRender;->initShaderValue(Z)V

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mFilterTexture:I

    if-eqz p1, :cond_0

    const v0, 0x84c1

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/effect/renders/ShaderRender;->bindTexture(II)Z

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformFilterTexture:I

    const/4 v0, 0x1

    invoke-static {p1, v0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    :cond_0
    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mLightTexture:I

    if-eqz p1, :cond_1

    const v0, 0x84c2

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/effect/renders/ShaderRender;->bindTexture(II)Z

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformLightTexture:I

    const/4 v0, 0x2

    invoke-static {p1, v0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    :cond_1
    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNoiseTexture:I

    if-eqz p1, :cond_2

    const v0, 0x84c3

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/effect/renders/ShaderRender;->bindTexture(II)Z

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNoiseTexture:I

    const/4 v0, 0x3

    invoke-static {p1, v0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    :cond_2
    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedFilter:I

    iget-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedFilter:Z

    invoke-static {p1, v0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedNoise:I

    iget-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedNoise:Z

    invoke-static {p1, v0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedBlur:I

    iget-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedBlur:Z

    invoke-static {p1, v0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformNeedColorSeparation:I

    iget-boolean v0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mNeedColorSeparation:Z

    invoke-static {p1, v0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    iget p1, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mUniformVerticalScreen:I

    iget-boolean p0, p0, Lcom/android/camera/effect/renders/LightEffectRender;->mVerticalScreen:Z

    invoke-static {p1, p0}, Landroid/opengl/GLES20;->glUniform1i(II)V

    return-void
.end method
