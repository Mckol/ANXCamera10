.class public Lcom/android/camera/aiwatermark/data/WatermarkItem;
.super Ljava/lang/Object;
.source "WatermarkItem.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "WatermarkItem"


# instance fields
.field private mCaptureCoordinate:[I

.field private mCoordinate:[I

.field private mCountry:I

.field private mHasMove:Z

.field private mKey:Ljava/lang/String;

.field private mLimitArea:Landroid/graphics/Rect;

.field private mLocation:I

.field private mResId:I

.field private mResRvItem:I

.field private mText:Ljava/lang/String;

.field private mTimeBitmap:Landroid/graphics/Bitmap;

.field private mTimeWatermark:[Ljava/lang/String;

.field private mType:I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, ""

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const/4 v0, 0x1

    iput v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mType:I

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mText:Ljava/lang/String;

    const/4 v2, -0x1

    iput v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResId:I

    iput v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mLocation:I

    iput v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCountry:I

    iput v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResRvItem:I

    const/4 v0, 0x4

    new-array v2, v0, [I

    iput-object v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCoordinate:[I

    new-array v0, v0, [I

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCaptureCoordinate:[I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mHasMove:Z

    iput-object v1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeBitmap:Landroid/graphics/Bitmap;

    iput-object v1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;III)V
    .locals 6

    const/4 v3, 0x0

    move-object v0, p0

    move-object v1, p1

    move v2, p2

    move v4, p3

    move v5, p4

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;ILjava/lang/String;II)V

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;ILjava/lang/String;II)V
    .locals 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, ""

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const/4 v0, 0x1

    iput v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mType:I

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mText:Ljava/lang/String;

    const/4 v2, -0x1

    iput v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResId:I

    iput v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mLocation:I

    iput v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCountry:I

    iput v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResRvItem:I

    const/4 v0, 0x4

    new-array v2, v0, [I

    iput-object v2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCoordinate:[I

    new-array v0, v0, [I

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCaptureCoordinate:[I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mHasMove:Z

    iput-object v1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeBitmap:Landroid/graphics/Bitmap;

    iput-object v1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    iput-object p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    iput p2, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mType:I

    iput-object p3, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mText:Ljava/lang/String;

    iput p4, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResId:I

    iput p5, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mLocation:I

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;ILjava/lang/String;III)V
    .locals 0

    invoke-direct/range {p0 .. p5}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;ILjava/lang/String;II)V

    iput p6, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCountry:I

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;ILjava/lang/String;IIII)V
    .locals 0

    invoke-direct/range {p0 .. p6}, Lcom/android/camera/aiwatermark/data/WatermarkItem;-><init>(Ljava/lang/String;ILjava/lang/String;III)V

    iput p7, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResRvItem:I

    return-void
.end method


# virtual methods
.method public getCaptureCoordinate()[I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCaptureCoordinate:[I

    return-object p0
.end method

.method public getCoordinate()[I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCoordinate:[I

    return-object p0
.end method

.method public getCountry()I
    .locals 0

    iget p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCountry:I

    return p0
.end method

.method public getKey()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    return-object p0
.end method

.method public getLimitArea()Landroid/graphics/Rect;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mLimitArea:Landroid/graphics/Rect;

    return-object p0
.end method

.method public getLocation()I
    .locals 0

    iget p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mLocation:I

    return p0
.end method

.method public getResId()I
    .locals 0

    iget p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResId:I

    return p0
.end method

.method public getResRvItem()I
    .locals 0

    iget p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResRvItem:I

    return p0
.end method

.method public getText()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mText:Ljava/lang/String;

    return-object p0
.end method

.method public getTimeBitmap()Landroid/graphics/Bitmap;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeBitmap:Landroid/graphics/Bitmap;

    return-object p0
.end method

.method public getTimeWatermark()[Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->updateTimeWatermark()[Ljava/lang/String;

    :cond_0
    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    return-object p0
.end method

.method public getTimeWatermarkType()I
    .locals 2

    iget-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const-string v1, "time"

    invoke-static {v1, v0}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const-string v1, "super_moon_text_1"

    invoke-static {v1, v0}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p0, 0x2

    return p0

    :cond_1
    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const-string v0, "super_moon_text_4"

    invoke-static {v0, p0}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result p0

    if-eqz p0, :cond_2

    const/4 p0, 0x3

    return p0

    :cond_2
    const/4 p0, 0x0

    return p0
.end method

.method public getType()I
    .locals 0

    iget p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mType:I

    return p0
.end method

.method public hasMove()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mHasMove:Z

    return p0
.end method

.method public isTimeWatermark()Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const-string v1, "time"

    invoke-static {v1, v0}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const-string v1, "super_moon_text_1"

    invoke-static {v1, v0}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_1

    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    const-string v0, "super_moon_text_4"

    invoke-static {v0, p0}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result p0

    if-eqz p0, :cond_0

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

.method public setCaptureCoordinate([I)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCaptureCoordinate:[I

    return-void
.end method

.method public setCountry(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCountry:I

    return-void
.end method

.method public setHasMove(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mHasMove:Z

    return-void
.end method

.method public setKey(Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mKey:Ljava/lang/String;

    return-void
.end method

.method public setLimitArea(Landroid/graphics/Rect;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mLimitArea:Landroid/graphics/Rect;

    return-void
.end method

.method public setLocation(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mLocation:I

    return-void
.end method

.method public setResId(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResId:I

    return-void
.end method

.method public setResRvItem(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mResRvItem:I

    return-void
.end method

.method public setText(Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mText:Ljava/lang/String;

    return-void
.end method

.method public setType(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mType:I

    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "WatermarkItem: key is "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getKey()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "; type is "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getType()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "; text is "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getText()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "; res id is "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getResId()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "; location is "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getLocation()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "; country is "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getCountry()I

    move-result p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public updateCoordinate([I)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mCoordinate:[I

    return-void
.end method

.method public updateTimeBitmap(Landroid/view/View;)V
    .locals 3

    invoke-virtual {p1}, Landroid/view/View;->clearFocus()V

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/view/View;->setPressed(Z)V

    invoke-virtual {p1}, Landroid/view/View;->willNotCacheDrawing()Z

    move-result v1

    invoke-virtual {p1, v0}, Landroid/view/View;->setWillNotCacheDrawing(Z)V

    invoke-virtual {p1}, Landroid/view/View;->getDrawingCacheBackgroundColor()I

    move-result v2

    invoke-virtual {p1, v0}, Landroid/view/View;->setDrawingCacheBackgroundColor(I)V

    if-eqz v2, :cond_0

    invoke-virtual {p1}, Landroid/view/View;->destroyDrawingCache()V

    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->buildDrawingCache()V

    invoke-virtual {p1}, Landroid/view/View;->getDrawingCache()Landroid/graphics/Bitmap;

    move-result-object v0

    if-nez v0, :cond_1

    sget-object p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->TAG:Ljava/lang/String;

    const-string p1, "[updateTimeBitmap] cacheBitmap is null"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    invoke-static {v0}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object v0

    invoke-virtual {p1}, Landroid/view/View;->destroyDrawingCache()V

    invoke-virtual {p1, v1}, Landroid/view/View;->setWillNotCacheDrawing(Z)V

    invoke-virtual {p1, v2}, Landroid/view/View;->setDrawingCacheBackgroundColor(I)V

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeBitmap:Landroid/graphics/Bitmap;

    return-void
.end method

.method public updateTimeWatermark()[Ljava/lang/String;
    .locals 5

    invoke-virtual {p0}, Lcom/android/camera/aiwatermark/data/WatermarkItem;->getTimeWatermarkType()I

    move-result v0

    const/4 v1, 0x2

    const-string v2, "-"

    if-eq v0, v1, :cond_1

    const/4 v1, 0x3

    if-eq v0, v1, :cond_0

    new-instance v0, Ljava/text/SimpleDateFormat;

    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v3, "MMM-dd-hh:mm a"

    invoke-direct {v0, v3, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    invoke-static {v3, v4}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    goto :goto_0

    :cond_0
    new-instance v0, Ljava/text/SimpleDateFormat;

    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v3, "dd-MMM"

    invoke-direct {v0, v3, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    invoke-static {v3, v4}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    goto :goto_0

    :cond_1
    new-instance v0, Ljava/text/SimpleDateFormat;

    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v3, "HH:mm-MMM dd\'th\'.yyyy"

    invoke-direct {v0, v3, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    invoke-static {v3, v4}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    :goto_0
    iget-object p0, p0, Lcom/android/camera/aiwatermark/data/WatermarkItem;->mTimeWatermark:[Ljava/lang/String;

    return-object p0
.end method
