.class public Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;
.super Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter;
.source "StopsZoomSliderAdapter.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter<",
        "Ljava/lang/Float;",
        ">;"
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "StopsZoomSliderAdapter"


# instance fields
.field private mCurrentIndex:I

.field protected final mCurrentMode:I

.field private mEnable:Z

.field private mRulerLines:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private mZoomIndexs:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/Float;",
            ">;"
        }
    .end annotation
.end field

.field protected final mZoomRatioMax:F

.field protected final mZoomRatioMin:F


# direct methods
.method public constructor <init>(Landroid/content/Context;ILcom/android/camera/fragment/manually/ZoomValueListener;)V
    .locals 4

    invoke-direct {p0}, Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mCurrentIndex:I

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    iput p2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mCurrentMode:I

    iput-object p3, p0, Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter;->mZoomValueListener:Lcom/android/camera/fragment/manually/ZoomValueListener;

    invoke-static {}, Lcom/android/camera/CameraSettings;->readZoom()F

    move-result p3

    invoke-static {p3}, Ljava/lang/String;->valueOf(F)Ljava/lang/String;

    move-result-object p3

    iput-object p3, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    move-object p3, p1

    check-cast p3, Lcom/android/camera/ActivityBase;

    invoke-virtual {p3}, Lcom/android/camera/ActivityBase;->getCurrentModule()Lcom/android/camera/module/Module;

    move-result-object p3

    check-cast p3, Lcom/android/camera/module/BaseModule;

    invoke-virtual {p3}, Lcom/android/camera/module/BaseModule;->getMinZoomRatio()F

    move-result v0

    iput v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMin:F

    invoke-virtual {p3}, Lcom/android/camera/module/BaseModule;->getMaxZoomRatio()F

    move-result p3

    iput p3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMax:F

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "ZOOM RATIO RANGE ["

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMin:F

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v0, ", "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMax:F

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v0, "]"

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    const-string v0, "StopsZoomSliderAdapter"

    invoke-static {v0, p3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->initStyle(Landroid/content/Context;)V

    const/4 p1, 0x0

    const/4 p3, 0x1

    const/16 v0, 0xbc

    if-ne p2, v0, :cond_0

    move v0, p3

    goto :goto_0

    :cond_0
    move v0, p1

    :goto_0
    invoke-static {p2}, Lcom/android/camera/module/ModuleManager;->isVideoCategory(I)Z

    move-result p2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v1

    const-string v2, "0.6:1:2:5:10"

    invoke-virtual {v1, v0, p2, v2}, Lb/c/a/b;->a(ZZLjava/lang/String;)Ljava/util/List;

    move-result-object v1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v2

    const-string v3, "4:10:10:5"

    invoke-virtual {v2, v0, p2, v3}, Lb/c/a/b;->b(ZZLjava/lang/String;)Ljava/util/List;

    move-result-object p2

    :goto_1
    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v0

    if-ge p1, v0, :cond_2

    invoke-interface {v1, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Float;

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v0

    iget v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMin:F

    cmpl-float v2, v0, v2

    if-ltz v2, :cond_1

    iget v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMax:F

    cmpg-float v2, v0, v2

    if-gtz v2, :cond_1

    iget-object v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-static {v0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v0

    invoke-interface {v2, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    if-lt p1, p3, :cond_1

    invoke-interface {p2}, Ljava/util/List;->size()I

    move-result v0

    if-gt p1, v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    add-int/lit8 v2, p1, -0x1

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    invoke-interface {v0, v3}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {p2, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_1
    add-int/lit8 p1, p1, 0x1

    goto :goto_1

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result p1

    iget-object p2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {p2}, Ljava/util/List;->size()I

    move-result p2

    add-int/2addr p2, p3

    if-ne p1, p2, :cond_4

    iget-object p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    iget p2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMax:F

    invoke-static {p2}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p2

    invoke-interface {p1, p2}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_3

    iget-object p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    iget p2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMax:F

    invoke-static {p2}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p2

    invoke-interface {p1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object p0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    const/16 p1, 0xa

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-interface {p0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_3
    return-void

    :cond_4
    new-instance p1, Ljava/lang/IllegalStateException;

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "init zoom fail mZoomIndexs = "

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string p3, ",mRulerLines = "

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {p1, p0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p1
.end method


# virtual methods
.method public draw(ILandroid/graphics/Canvas;ZIF)V
    .locals 8

    invoke-super/range {p0 .. p5}, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->draw(ILandroid/graphics/Canvas;ZIF)V

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->measureWidth(I)F

    move-result p4

    neg-float p4, p4

    const/high16 p5, 0x40000000    # 2.0f

    div-float v1, p4, p5

    if-eqz p3, :cond_0

    iget v4, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentLineSelectHalfHeight:F

    neg-float v2, v4

    iget p1, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mLineStopPointWidth:I

    int-to-float p1, p1

    add-float v3, v1, p1

    const/high16 v5, 0x40000000    # 2.0f

    const/high16 v6, 0x40000000    # 2.0f

    iget-object v7, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mSelectPaint:Landroid/graphics/Paint;

    move-object v0, p2

    invoke-virtual/range {v0 .. v7}, Landroid/graphics/Canvas;->drawRoundRect(FFFFFFLandroid/graphics/Paint;)V

    goto :goto_0

    :cond_0
    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->isStopPoint(I)Z

    move-result p1

    if-eqz p1, :cond_1

    iget v4, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentLineHalfHeight:F

    neg-float v2, v4

    iget p1, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mLineStopPointWidth:I

    int-to-float p1, p1

    add-float v3, v1, p1

    const/high16 v5, 0x3f800000    # 1.0f

    const/high16 v6, 0x3f800000    # 1.0f

    iget-object v7, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mStopPointPaint:Landroid/graphics/Paint;

    move-object v0, p2

    invoke-virtual/range {v0 .. v7}, Landroid/graphics/Canvas;->drawRoundRect(FFFFFFLandroid/graphics/Paint;)V

    goto :goto_0

    :cond_1
    iget v4, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentLineHalfHeight:F

    neg-float v2, v4

    iget p1, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mLineWidth:I

    int-to-float p1, p1

    add-float v3, v1, p1

    const/high16 v5, 0x3f800000    # 1.0f

    const/high16 v6, 0x3f800000    # 1.0f

    iget-object v7, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mNormalPaint:Landroid/graphics/Paint;

    move-object v0, p2

    invoke-virtual/range {v0 .. v7}, Landroid/graphics/Canvas;->drawRoundRect(FFFFFFLandroid/graphics/Paint;)V

    :goto_0
    return-void
.end method

.method public getAlign(I)Landroid/graphics/Paint$Align;
    .locals 0

    const/4 p0, 0x0

    return-object p0
.end method

.method public getCount()I
    .locals 2

    iget-object p0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    const/4 v0, 0x1

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    add-int/2addr v0, v1

    goto :goto_0

    :cond_0
    return v0
.end method

.method public isEnable()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mEnable:Z

    return p0
.end method

.method public isFirstStopPoint(I)Z
    .locals 0

    if-nez p1, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isLastStopPoint(I)Z
    .locals 1

    invoke-virtual {p0}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->getCount()I

    move-result p0

    const/4 v0, 0x1

    sub-int/2addr p0, v0

    if-ne p1, p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isSingleValueLine(I)Z
    .locals 7

    const/4 v0, 0x0

    move v1, v0

    move v2, v1

    :goto_0
    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    const/4 v4, 0x1

    sub-int/2addr v3, v4

    if-ge v1, v3, :cond_1

    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {v3, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v3

    add-int/2addr v2, v3

    if-gt p1, v2, :cond_0

    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    add-int/lit8 v5, v1, 0x1

    invoke-interface {v3, v5}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Float;

    invoke-virtual {v3}, Ljava/lang/Float;->floatValue()F

    move-result v3

    const/high16 v5, 0x41200000    # 10.0f

    mul-float/2addr v3, v5

    iget-object v6, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v6, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Float;

    invoke-virtual {v6}, Ljava/lang/Float;->floatValue()F

    move-result v6

    mul-float/2addr v6, v5

    sub-float/2addr v3, v6

    iget-object v5, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {v5, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    int-to-float v5, v5

    div-float/2addr v3, v5

    float-to-int v3, v3

    int-to-float v3, v3

    const/high16 v5, 0x3f800000    # 1.0f

    cmpl-float v3, v3, v5

    if-nez v3, :cond_0

    return v4

    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_1
    return v0
.end method

.method public isStopPoint(I)Z
    .locals 4

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->isFirstStopPoint(I)Z

    move-result v0

    const/4 v1, 0x1

    if-nez v0, :cond_3

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->isLastStopPoint(I)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    const/4 v0, 0x0

    move v2, v0

    :cond_1
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_2

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v3

    add-int/2addr v2, v3

    if-ne v2, p1, :cond_1

    return v1

    :cond_2
    return v0

    :cond_3
    :goto_0
    return v1
.end method

.method public mapPositionToValue(F)Ljava/lang/Float;
    .locals 5

    iget v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomRatioMin:F

    const/4 v1, 0x0

    move v2, v0

    move v0, v1

    :goto_0
    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    add-int/lit8 v3, v3, -0x1

    if-ge v1, v3, :cond_1

    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {v3, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v3

    add-int/2addr v0, v3

    int-to-float v3, v0

    cmpg-float v4, p1, v3

    if-gtz v4, :cond_0

    sub-float/2addr p1, v3

    iget-object v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    int-to-float v0, v0

    add-float/2addr p1, v0

    iget-object v0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    add-int/lit8 v3, v1, 0x1

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Float;

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v0

    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v3, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Float;

    invoke-virtual {v3}, Ljava/lang/Float;->floatValue()F

    move-result v3

    sub-float/2addr v0, v3

    iget-object p0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {p0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    int-to-float p0, p0

    div-float/2addr v0, p0

    mul-float/2addr p1, v0

    add-float/2addr v2, p1

    goto :goto_1

    :cond_0
    iget-object v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    add-int/lit8 v1, v1, 0x1

    invoke-interface {v2, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Float;

    invoke-virtual {v2}, Ljava/lang/Float;->floatValue()F

    move-result v2

    goto :goto_0

    :cond_1
    :goto_1
    invoke-static {v2}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p0

    return-object p0
.end method

.method public bridge synthetic mapPositionToValue(F)Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mapPositionToValue(F)Ljava/lang/Float;

    move-result-object p0

    return-object p0
.end method

.method public mapValueToPosition(Ljava/lang/Float;)F
    .locals 5

    const/4 v0, 0x0

    move v1, v0

    :goto_0
    iget-object v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    add-int/lit8 v2, v2, -0x1

    if-ge v0, v2, :cond_1

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result v2

    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v3, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Float;

    invoke-virtual {v3}, Ljava/lang/Float;->floatValue()F

    move-result v3

    cmpl-float v2, v2, v3

    if-ltz v2, :cond_0

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result v2

    iget-object v3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    add-int/lit8 v4, v0, 0x1

    invoke-interface {v3, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Float;

    invoke-virtual {v3}, Ljava/lang/Float;->floatValue()F

    move-result v3

    cmpg-float v2, v2, v3

    if-gez v2, :cond_0

    int-to-float v1, v1

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result p1

    iget-object v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Float;

    invoke-virtual {v2}, Ljava/lang/Float;->floatValue()F

    move-result v2

    sub-float/2addr p1, v2

    iget-object v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v2

    int-to-float v2, v2

    mul-float/2addr p1, v2

    iget-object v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {v2, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Float;

    invoke-virtual {v2}, Ljava/lang/Float;->floatValue()F

    move-result v2

    iget-object p0, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mZoomIndexs:Ljava/util/List;

    invoke-interface {p0, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Float;

    invoke-virtual {p0}, Ljava/lang/Float;->floatValue()F

    move-result p0

    sub-float/2addr v2, p0

    div-float/2addr p1, v2

    add-float/2addr v1, p1

    float-to-int v1, v1

    goto :goto_1

    :cond_0
    iget-object v2, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mRulerLines:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v2

    add-int/2addr v1, v2

    add-int/lit8 v0, v0, 0x1

    goto/16 :goto_0

    :cond_1
    :goto_1
    int-to-float p0, v1

    return p0
.end method

.method public bridge synthetic mapValueToPosition(Ljava/lang/Object;)F
    .locals 0

    check-cast p1, Ljava/lang/Float;

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mapValueToPosition(Ljava/lang/Float;)F

    move-result p0

    return p0
.end method

.method public measureWidth(I)F
    .locals 0

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->isStopPoint(I)Z

    move-result p1

    if-eqz p1, :cond_0

    iget p0, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mLineStopPointWidth:I

    :goto_0
    int-to-float p0, p0

    return p0

    :cond_0
    iget p0, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mLineWidth:I

    goto :goto_0
.end method

.method public onChangeValue(Ljava/lang/String;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter;->mZoomValueListener:Lcom/android/camera/fragment/manually/ZoomValueListener;

    if-eqz v0, :cond_0

    invoke-interface {v0, p1}, Lcom/android/camera/fragment/manually/ZoomValueListener;->onManuallyDataChanged(Ljava/lang/String;)V

    iput-object p1, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    iget-object p1, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    invoke-static {p1}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result p1

    invoke-static {p1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mapValueToPosition(Ljava/lang/Float;)F

    move-result p1

    invoke-static {p1}, Ljava/lang/Math;->round(F)I

    move-result p1

    iput p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mCurrentIndex:I

    :cond_0
    return-void
.end method

.method public onPositionSelect(Landroid/view/View;IF)V
    .locals 5

    iget-boolean p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mEnable:Z

    if-nez p1, :cond_0

    return-void

    :cond_0
    const/high16 p1, -0x40800000    # -1.0f

    cmpl-float v0, p3, p1

    const/4 v1, -0x1

    const/4 v2, 0x1

    if-nez v0, :cond_2

    if-ne p2, v1, :cond_1

    return-void

    :cond_1
    int-to-float v0, p2

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mapPositionToValue(F)Ljava/lang/Float;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_2
    const/4 v0, 0x0

    const/high16 v3, 0x3f800000    # 1.0f

    invoke-static {p3, v0, v3}, Lcom/android/camera/Util;->clamp(FFF)F

    move-result p3

    invoke-virtual {p0}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->getCount()I

    move-result v0

    sub-int/2addr v0, v2

    int-to-float v0, v0

    mul-float/2addr v0, p3

    invoke-virtual {p0, v0}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mapPositionToValue(F)Ljava/lang/Float;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    :goto_0
    iget-object v3, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    xor-int/2addr v3, v2

    if-eqz v3, :cond_8

    const/4 v3, 0x0

    if-ne p2, v1, :cond_4

    invoke-virtual {p0}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->getCount()I

    move-result p1

    sub-int/2addr p1, v2

    int-to-float p1, p1

    mul-float/2addr p1, p3

    iget p3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mCurrentIndex:I

    int-to-float p3, p3

    sub-float/2addr p3, p1

    invoke-static {p3}, Ljava/lang/Math;->abs(F)F

    move-result p3

    const v4, 0x3f733333    # 0.95f

    cmpl-float p3, p3, v4

    if-lez p3, :cond_3

    goto :goto_1

    :cond_3
    move v2, v3

    goto :goto_1

    :cond_4
    iget p3, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mCurrentIndex:I

    if-eq p3, p2, :cond_3

    :goto_1
    iget-object p3, p0, Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter;->mZoomValueListener:Lcom/android/camera/fragment/manually/ZoomValueListener;

    if-eqz p3, :cond_6

    if-eqz v2, :cond_6

    if-ne p2, v1, :cond_5

    invoke-static {p1}, Ljava/lang/Math;->round(F)I

    move-result p1

    goto :goto_2

    :cond_5
    move p1, p2

    :goto_2
    iput p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mCurrentIndex:I

    iget-object p1, p0, Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter;->mZoomValueListener:Lcom/android/camera/fragment/manually/ZoomValueListener;

    invoke-virtual {p0, p2}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->isStopPoint(I)Z

    move-result p3

    invoke-interface {p1, p2, p3}, Lcom/android/camera/fragment/manually/ZoomValueListener;->onZoomItemSlideOn(IZ)V

    :cond_6
    iget-object p1, p0, Lcom/android/camera/fragment/manually/adapter/AbstractZoomSliderAdapter;->mZoomValueListener:Lcom/android/camera/fragment/manually/ZoomValueListener;

    if-eqz p1, :cond_7

    invoke-interface {p1, v0}, Lcom/android/camera/fragment/manually/ZoomValueListener;->onManuallyDataChanged(Ljava/lang/String;)V

    :cond_7
    iput-object v0, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    :cond_8
    return-void
.end method

.method public setCurrentValue(Ljava/lang/String;)V
    .locals 0

    invoke-static {}, Lcom/android/camera/CameraSettings;->readZoom()F

    move-result p1

    invoke-static {p1}, Ljava/lang/String;->valueOf(F)Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    iget-object p1, p0, Lcom/android/camera/ui/BaseHorizontalZoomView$HorizontalDrawAdapter;->mCurrentValue:Ljava/lang/String;

    invoke-static {p1}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result p1

    invoke-static {p1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mapValueToPosition(Ljava/lang/Float;)F

    move-result p1

    invoke-static {p1}, Ljava/lang/Math;->round(F)I

    move-result p1

    iput p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mCurrentIndex:I

    return-void
.end method

.method public setEnable(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/fragment/manually/adapter/sat/StopsZoomSliderAdapter;->mEnable:Z

    return-void
.end method
