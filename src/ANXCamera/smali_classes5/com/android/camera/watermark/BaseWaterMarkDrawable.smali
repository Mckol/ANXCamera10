.class public abstract Lcom/android/camera/watermark/BaseWaterMarkDrawable;
.super Ljava/lang/Object;
.source "BaseWaterMarkDrawable.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "BaseWaterMarkDrawable"


# instance fields
.field protected final mContext:Landroid/content/Context;

.field private mCorrection:I

.field private mFaceInfoNumberPaint:Landroid/graphics/Paint;

.field private mFaceInfoTextPaint:Landroid/graphics/Paint;

.field protected mFacePopupBottom:I

.field private mGap:I

.field private mHeight:I

.field private mOrientation:I

.field protected mPopBottomMargin:I

.field private mSplitFaceInfoPattern:Ljava/util/regex/Pattern;

.field protected mVerPadding:I

.field private mWaterMarkBitmap:Landroid/graphics/Bitmap;

.field private mWaterMarkData:Lcom/android/camera/watermark/WaterMarkData;

.field protected mWaterMarkInfos:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/android/camera/watermark/WaterMarkData;",
            ">;"
        }
    .end annotation
.end field

.field private mWidth:I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Ljava/util/List;)V
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/watermark/WaterMarkData;",
            ">;)V"
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mContext:Landroid/content/Context;

    iput-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    if-eqz p1, :cond_0

    invoke-interface {p1}, Ljava/util/List;->isEmpty()Z

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    const/4 v0, 0x0

    invoke-interface {p1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/watermark/WaterMarkData;

    invoke-virtual {p1}, Lcom/android/camera/watermark/WaterMarkData;->getFaceViewWidth()I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWidth:I

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    invoke-interface {p1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/watermark/WaterMarkData;

    invoke-virtual {p1}, Lcom/android/camera/watermark/WaterMarkData;->getFaceViewHeight()I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mHeight:I

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    invoke-interface {p1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/watermark/WaterMarkData;

    invoke-virtual {p1}, Lcom/android/camera/watermark/WaterMarkData;->getOrientation()I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mOrientation:I

    :cond_0
    new-instance p1, Landroid/graphics/Paint;

    invoke-direct {p1}, Landroid/graphics/Paint;-><init>()V

    iput-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    const/4 v1, -0x1

    invoke-virtual {p1, v1}, Landroid/graphics/Paint;->setColor(I)V

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v1, 0x7f0701a0

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p1

    iget-object v1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v1, p1}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Align;->CENTER:Landroid/graphics/Paint$Align;

    invoke-virtual {p1, v1}, Landroid/graphics/Paint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0}, Landroid/graphics/Paint;->setFakeBoldText(Z)V

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f0701a4

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mGap:I

    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v1, 0x7f07019e

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getDimensionPixelOffset(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mCorrection:I

    new-instance p1, Landroid/graphics/Paint;

    iget-object v2, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    invoke-direct {p1, v2}, Landroid/graphics/Paint;-><init>(Landroid/graphics/Paint;)V

    iput-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoNumberPaint:Landroid/graphics/Paint;

    const-string p1, "(\\D+)|(\\d+\\.?\\d*)"

    invoke-static {p1}, Ljava/util/regex/Pattern;->compile(Ljava/lang/String;)Ljava/util/regex/Pattern;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mSplitFaceInfoPattern:Ljava/util/regex/Pattern;

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v2, 0x7f0701a5

    invoke-virtual {p1, v2}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mVerPadding:I

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mGap:I

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v1}, Landroid/content/res/Resources;->getDimensionPixelOffset(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mCorrection:I

    iget-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mContext:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v0, 0x7f0701a6

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result p1

    iput p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mPopBottomMargin:I

    invoke-virtual {p0}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->initBeforeDraw()V

    invoke-direct {p0}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->draw()V

    return-void
.end method

.method private draw()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    if-eqz v0, :cond_0

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/watermark/WaterMarkData;

    invoke-direct {v0}, Lcom/android/camera/watermark/WaterMarkData;-><init>()V

    iput-object v0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkData:Lcom/android/camera/watermark/WaterMarkData;

    iget-object v0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkData:Lcom/android/camera/watermark/WaterMarkData;

    iget-object v1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    const/4 v2, 0x0

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/watermark/WaterMarkData;

    invoke-virtual {v1}, Lcom/android/camera/watermark/WaterMarkData;->getWatermarkType()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera/watermark/WaterMarkData;->setWatermarkType(I)V

    iget v0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWidth:I

    iget v1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mHeight:I

    sget-object v2, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v0, v1, v2}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    new-instance v0, Landroid/graphics/Canvas;

    iget-object v1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    invoke-direct {v0, v1}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    invoke-virtual {p0, v0}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->onDraw(Landroid/graphics/Canvas;)V

    :cond_0
    return-void
.end method

.method private drawFaceInfoText(Landroid/graphics/Canvas;Ljava/lang/String;II)V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mSplitFaceInfoPattern:Ljava/util/regex/Pattern;

    invoke-virtual {v0, p2}, Ljava/util/regex/Pattern;->matcher(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;

    move-result-object p2

    int-to-float p3, p3

    int-to-float p4, p4

    :goto_0
    invoke-virtual {p2}, Ljava/util/regex/Matcher;->find()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p2}, Ljava/util/regex/Matcher;->group()Ljava/lang/String;

    move-result-object v0

    const-string v1, "\\d+\\.?\\d*"

    invoke-virtual {v0, v1}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result v1

    const/high16 v2, 0x40000000    # 2.0f

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoNumberPaint:Landroid/graphics/Paint;

    invoke-virtual {v1, v0}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v1

    div-float v2, v1, v2

    add-float/2addr v2, p3

    iget-object v3, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoNumberPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v2, p4, v3}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto :goto_1

    :cond_0
    iget-object v1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v1, v0}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v1

    div-float v2, v1, v2

    add-float/2addr v2, p3

    iget-object v3, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v2, p4, v3}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    :goto_1
    add-float/2addr p3, v1

    goto :goto_0

    :cond_1
    return-void
.end method


# virtual methods
.method public getBitmap()Landroid/graphics/Bitmap;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    return-object p0
.end method

.method protected abstract getFaceRectPaint(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/Paint;
.end method

.method protected abstract getHonPadding(Lcom/android/camera/watermark/WaterMarkData;)I
.end method

.method protected abstract getTopBackgroundDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;
.end method

.method protected abstract getTopIndicatorDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;
.end method

.method public getWaterMarkData()Lcom/android/camera/watermark/WaterMarkData;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkData:Lcom/android/camera/watermark/WaterMarkData;

    return-object p0
.end method

.method protected abstract initBeforeDraw()V
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 17

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    sget-object v4, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->TAG:Ljava/lang/String;

    const-string v5, "start make water mark."

    invoke-static {v4, v5}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->save()I

    iget v4, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mOrientation:I

    neg-int v4, v4

    int-to-float v4, v4

    invoke-virtual {v1, v4}, Landroid/graphics/Canvas;->rotate(F)V

    new-instance v4, Ljava/util/ArrayList;

    iget-object v5, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    invoke-direct {v4, v5}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    invoke-interface {v4}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v4

    :goto_0
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_5

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/android/camera/watermark/WaterMarkData;

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v7

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v8

    invoke-virtual {v8}, Landroid/graphics/RectF;->width()F

    move-result v8

    const v9, 0x3c75c28f    # 0.015f

    mul-float/2addr v8, v9

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v10

    invoke-virtual {v10}, Landroid/graphics/RectF;->height()F

    move-result v10

    mul-float/2addr v10, v9

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getFaceRectPaint(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/Paint;

    move-result-object v9

    invoke-virtual {v1, v7, v8, v10, v9}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    iget-object v7, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mSplitFaceInfoPattern:Ljava/util/regex/Pattern;

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getInfo()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/util/regex/Pattern;->matcher(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;

    move-result-object v7

    const/4 v9, 0x0

    :goto_1
    invoke-virtual {v7}, Ljava/util/regex/Matcher;->find()Z

    move-result v10

    if-eqz v10, :cond_1

    invoke-virtual {v7}, Ljava/util/regex/Matcher;->group()Ljava/lang/String;

    move-result-object v10

    const-string v11, "\\d+\\.?\\d*"

    invoke-virtual {v10, v11}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result v11

    if-eqz v11, :cond_0

    iget-object v11, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoNumberPaint:Landroid/graphics/Paint;

    invoke-virtual {v11, v10}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v10

    goto :goto_2

    :cond_0
    iget-object v11, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v11, v10}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v10

    :goto_2
    add-float/2addr v9, v10

    goto :goto_1

    :cond_1
    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getHonPadding(Lcom/android/camera/watermark/WaterMarkData;)I

    move-result v7

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopIndicatorDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v10

    invoke-virtual {v10}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v10

    add-int/2addr v10, v7

    iget v11, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mGap:I

    add-int/2addr v10, v11

    int-to-float v10, v10

    add-float/2addr v10, v9

    int-to-float v11, v7

    add-float/2addr v10, v11

    float-to-int v10, v10

    iget v11, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mVerPadding:I

    int-to-float v11, v11

    const v12, 0x40666666    # 3.6f

    mul-float/2addr v11, v12

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopIndicatorDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v12

    invoke-virtual {v12}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v12

    int-to-float v12, v12

    add-float/2addr v11, v12

    float-to-int v11, v11

    new-instance v12, Landroid/graphics/Rect;

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v13

    invoke-virtual {v13}, Landroid/graphics/RectF;->centerX()F

    move-result v13

    float-to-int v13, v13

    div-int/lit8 v10, v10, 0x2

    sub-int/2addr v13, v10

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v14

    iget v14, v14, Landroid/graphics/RectF;->top:F

    float-to-int v14, v14

    sub-int/2addr v14, v11

    iget v15, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mPopBottomMargin:I

    sub-int/2addr v14, v15

    iget v15, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFacePopupBottom:I

    sub-int/2addr v14, v15

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v15

    invoke-virtual {v15}, Landroid/graphics/RectF;->centerX()F

    move-result v15

    float-to-int v15, v15

    add-int/2addr v15, v10

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v6

    iget v6, v6, Landroid/graphics/RectF;->top:F

    float-to-int v6, v6

    iget v8, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mPopBottomMargin:I

    sub-int/2addr v6, v8

    invoke-direct {v12, v13, v14, v15, v6}, Landroid/graphics/Rect;-><init>(IIII)V

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopBackgroundDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v6

    if-eqz v6, :cond_2

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopBackgroundDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v6

    invoke-virtual {v6, v12}, Landroid/graphics/drawable/Drawable;->setBounds(Landroid/graphics/Rect;)V

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopBackgroundDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v6

    invoke-virtual {v6, v1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    :cond_2
    invoke-virtual {v12}, Landroid/graphics/Rect;->width()I

    move-result v6

    invoke-virtual {v12}, Landroid/graphics/Rect;->height()I

    move-result v8

    sget-object v12, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v6, v8, v12}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v6

    new-instance v8, Landroid/graphics/Canvas;

    invoke-direct {v8, v6}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    new-instance v12, Landroid/graphics/Rect;

    invoke-direct {v12}, Landroid/graphics/Rect;-><init>()V

    iget v13, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mVerPadding:I

    int-to-float v13, v13

    const v14, 0x3fe66666    # 1.8f

    mul-float/2addr v13, v14

    iget v15, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mCorrection:I

    int-to-float v15, v15

    sub-float/2addr v13, v15

    float-to-int v13, v13

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopIndicatorDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v15

    invoke-virtual {v15}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v15

    add-int/2addr v15, v7

    move-object/from16 v16, v4

    iget v4, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mVerPadding:I

    int-to-float v4, v4

    mul-float/2addr v4, v14

    iget v14, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mCorrection:I

    int-to-float v14, v14

    sub-float/2addr v4, v14

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopIndicatorDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v14

    invoke-virtual {v14}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v14

    int-to-float v14, v14

    add-float/2addr v4, v14

    float-to-int v4, v4

    invoke-virtual {v12, v7, v13, v15, v4}, Landroid/graphics/Rect;->set(IIII)V

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopIndicatorDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    invoke-virtual {v4, v12}, Landroid/graphics/drawable/Drawable;->setBounds(Landroid/graphics/Rect;)V

    invoke-virtual {v0, v5}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->getTopIndicatorDrawable(Lcom/android/camera/watermark/WaterMarkData;)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    invoke-virtual {v4, v8}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    const/4 v4, 0x0

    cmpl-float v4, v9, v4

    if-eqz v4, :cond_3

    iget-object v4, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFaceInfoTextPaint:Landroid/graphics/Paint;

    invoke-virtual {v4}, Landroid/graphics/Paint;->getFontMetricsInt()Landroid/graphics/Paint$FontMetricsInt;

    move-result-object v4

    iget v7, v12, Landroid/graphics/Rect;->right:I

    iget v9, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mGap:I

    add-int/2addr v7, v9

    iget v9, v12, Landroid/graphics/Rect;->bottom:I

    iget v12, v12, Landroid/graphics/Rect;->top:I

    add-int/2addr v9, v12

    iget v12, v4, Landroid/graphics/Paint$FontMetricsInt;->bottom:I

    sub-int/2addr v9, v12

    iget v4, v4, Landroid/graphics/Paint$FontMetricsInt;->top:I

    sub-int/2addr v9, v4

    div-int/lit8 v9, v9, 0x2

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getInfo()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v0, v8, v4, v7, v9}, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->drawFaceInfoText(Landroid/graphics/Canvas;Ljava/lang/String;II)V

    :cond_3
    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontMirror()Z

    move-result v4

    if-nez v4, :cond_4

    const/4 v4, 0x0

    invoke-static {v6, v4}, Lcom/android/camera/Util;->flipBitmap(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object v6

    :cond_4
    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v4

    invoke-virtual {v4}, Landroid/graphics/RectF;->centerX()F

    move-result v4

    float-to-int v4, v4

    sub-int/2addr v4, v10

    int-to-float v4, v4

    invoke-virtual {v5}, Lcom/android/camera/watermark/WaterMarkData;->getFaceRectF()Landroid/graphics/RectF;

    move-result-object v5

    iget v5, v5, Landroid/graphics/RectF;->top:F

    float-to-int v5, v5

    sub-int/2addr v5, v11

    iget v7, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mPopBottomMargin:I

    sub-int/2addr v5, v7

    iget v7, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mFacePopupBottom:I

    sub-int/2addr v5, v7

    int-to-float v5, v5

    const/4 v7, 0x0

    invoke-virtual {v1, v6, v4, v5, v7}, Landroid/graphics/Canvas;->drawBitmap(Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V

    move-object/from16 v4, v16

    goto/16 :goto_0

    :cond_5
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->restore()V

    iget-object v1, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkData:Lcom/android/camera/watermark/WaterMarkData;

    iget v4, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mOrientation:I

    invoke-virtual {v1, v4}, Lcom/android/camera/watermark/WaterMarkData;->setOrientation(I)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontMirror()Z

    move-result v1

    if-nez v1, :cond_8

    iget v1, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mOrientation:I

    const/16 v4, 0x5a

    if-eq v1, v4, :cond_7

    const/16 v4, 0x10e

    if-ne v1, v4, :cond_6

    goto :goto_3

    :cond_6
    iget-object v1, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    const/4 v4, 0x0

    invoke-static {v1, v4}, Lcom/android/camera/Util;->flipBitmap(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object v1

    iput-object v1, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    goto :goto_4

    :cond_7
    :goto_3
    iget-object v1, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    const/4 v4, 0x1

    invoke-static {v1, v4}, Lcom/android/camera/Util;->flipBitmap(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object v1

    iput-object v1, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    :cond_8
    :goto_4
    iget-object v1, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkData:Lcom/android/camera/watermark/WaterMarkData;

    iget-object v0, v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v1, v0}, Lcom/android/camera/watermark/WaterMarkData;->setImage(Landroid/graphics/Bitmap;)V

    sget-object v0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "end make water mark...time consuming\uff1a"

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    sub-long/2addr v4, v2

    invoke-virtual {v1, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public setWaterMarkInfos(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/watermark/WaterMarkData;",
            ">;)V"
        }
    .end annotation

    iput-object p1, p0, Lcom/android/camera/watermark/BaseWaterMarkDrawable;->mWaterMarkInfos:Ljava/util/List;

    return-void
.end method
