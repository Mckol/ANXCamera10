.class public Lcom/airbnb/lottie/a/b/j;
.super Lcom/airbnb/lottie/a/b/g;
.source "PathKeyframeAnimation.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/airbnb/lottie/a/b/g<",
        "Landroid/graphics/PointF;",
        ">;"
    }
.end annotation


# instance fields
.field private final hd:Landroid/graphics/PointF;

.field private jd:Lcom/airbnb/lottie/a/b/i;

.field private kd:Landroid/graphics/PathMeasure;

.field private final pos:[F


# direct methods
.method public constructor <init>(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "+",
            "Lcom/airbnb/lottie/e/a<",
            "Landroid/graphics/PointF;",
            ">;>;)V"
        }
    .end annotation

    invoke-direct {p0, p1}, Lcom/airbnb/lottie/a/b/g;-><init>(Ljava/util/List;)V

    new-instance p1, Landroid/graphics/PointF;

    invoke-direct {p1}, Landroid/graphics/PointF;-><init>()V

    iput-object p1, p0, Lcom/airbnb/lottie/a/b/j;->hd:Landroid/graphics/PointF;

    const/4 p1, 0x2

    new-array p1, p1, [F

    iput-object p1, p0, Lcom/airbnb/lottie/a/b/j;->pos:[F

    new-instance p1, Landroid/graphics/PathMeasure;

    invoke-direct {p1}, Landroid/graphics/PathMeasure;-><init>()V

    iput-object p1, p0, Lcom/airbnb/lottie/a/b/j;->kd:Landroid/graphics/PathMeasure;

    return-void
.end method


# virtual methods
.method public a(Lcom/airbnb/lottie/e/a;F)Landroid/graphics/PointF;
    .locals 10
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/airbnb/lottie/e/a<",
            "Landroid/graphics/PointF;",
            ">;F)",
            "Landroid/graphics/PointF;"
        }
    .end annotation

    move-object v0, p1

    check-cast v0, Lcom/airbnb/lottie/a/b/i;

    invoke-virtual {v0}, Lcom/airbnb/lottie/a/b/i;->getPath()Landroid/graphics/Path;

    move-result-object v1

    if-nez v1, :cond_0

    iget-object p0, p1, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    check-cast p0, Landroid/graphics/PointF;

    return-object p0

    :cond_0
    iget-object v2, p0, Lcom/airbnb/lottie/a/b/b;->bd:Lcom/airbnb/lottie/e/j;

    if-eqz v2, :cond_1

    iget v3, v0, Lcom/airbnb/lottie/e/a;->lb:F

    iget-object p1, v0, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result v4

    iget-object v5, v0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    iget-object v6, v0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    invoke-virtual {p0}, Lcom/airbnb/lottie/a/b/b;->sb()F

    move-result v7

    invoke-virtual {p0}, Lcom/airbnb/lottie/a/b/b;->getProgress()F

    move-result v9

    move v8, p2

    invoke-virtual/range {v2 .. v9}, Lcom/airbnb/lottie/e/j;->b(FFLjava/lang/Object;Ljava/lang/Object;FFF)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/graphics/PointF;

    if-eqz p1, :cond_1

    return-object p1

    :cond_1
    iget-object p1, p0, Lcom/airbnb/lottie/a/b/j;->jd:Lcom/airbnb/lottie/a/b/i;

    const/4 v2, 0x0

    if-eq p1, v0, :cond_2

    iget-object p1, p0, Lcom/airbnb/lottie/a/b/j;->kd:Landroid/graphics/PathMeasure;

    invoke-virtual {p1, v1, v2}, Landroid/graphics/PathMeasure;->setPath(Landroid/graphics/Path;Z)V

    iput-object v0, p0, Lcom/airbnb/lottie/a/b/j;->jd:Lcom/airbnb/lottie/a/b/i;

    :cond_2
    iget-object p1, p0, Lcom/airbnb/lottie/a/b/j;->kd:Landroid/graphics/PathMeasure;

    invoke-virtual {p1}, Landroid/graphics/PathMeasure;->getLength()F

    move-result v0

    mul-float/2addr p2, v0

    iget-object v0, p0, Lcom/airbnb/lottie/a/b/j;->pos:[F

    const/4 v1, 0x0

    invoke-virtual {p1, p2, v0, v1}, Landroid/graphics/PathMeasure;->getPosTan(F[F[F)Z

    iget-object p1, p0, Lcom/airbnb/lottie/a/b/j;->hd:Landroid/graphics/PointF;

    iget-object p2, p0, Lcom/airbnb/lottie/a/b/j;->pos:[F

    aget v0, p2, v2

    const/4 v1, 0x1

    aget p2, p2, v1

    invoke-virtual {p1, v0, p2}, Landroid/graphics/PointF;->set(FF)V

    iget-object p0, p0, Lcom/airbnb/lottie/a/b/j;->hd:Landroid/graphics/PointF;

    return-object p0
.end method

.method public bridge synthetic a(Lcom/airbnb/lottie/e/a;F)Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1, p2}, Lcom/airbnb/lottie/a/b/j;->a(Lcom/airbnb/lottie/e/a;F)Landroid/graphics/PointF;

    move-result-object p0

    return-object p0
.end method
