.class public Lcom/airbnb/lottie/a/b/i;
.super Lcom/airbnb/lottie/e/a;
.source "PathKeyframe.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/airbnb/lottie/e/a<",
        "Landroid/graphics/PointF;",
        ">;"
    }
.end annotation


# instance fields
.field private path:Landroid/graphics/Path;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation
.end field

.field private final pg:Lcom/airbnb/lottie/e/a;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/airbnb/lottie/e/a<",
            "Landroid/graphics/PointF;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/airbnb/lottie/m;Lcom/airbnb/lottie/e/a;)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/airbnb/lottie/m;",
            "Lcom/airbnb/lottie/e/a<",
            "Landroid/graphics/PointF;",
            ">;)V"
        }
    .end annotation

    iget-object v2, p2, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    iget-object v3, p2, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    iget-object v4, p2, Lcom/airbnb/lottie/e/a;->interpolator:Landroid/view/animation/Interpolator;

    iget v5, p2, Lcom/airbnb/lottie/e/a;->lb:F

    iget-object v6, p2, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    move-object v0, p0

    move-object v1, p1

    invoke-direct/range {v0 .. v6}, Lcom/airbnb/lottie/e/a;-><init>(Lcom/airbnb/lottie/m;Ljava/lang/Object;Ljava/lang/Object;Landroid/view/animation/Interpolator;FLjava/lang/Float;)V

    iput-object p2, p0, Lcom/airbnb/lottie/a/b/i;->pg:Lcom/airbnb/lottie/e/a;

    invoke-virtual {p0}, Lcom/airbnb/lottie/a/b/i;->zc()V

    return-void
.end method


# virtual methods
.method getPath()Landroid/graphics/Path;
    .locals 0
    .annotation build Landroidx/annotation/Nullable;
    .end annotation

    iget-object p0, p0, Lcom/airbnb/lottie/a/b/i;->path:Landroid/graphics/Path;

    return-object p0
.end method

.method public zc()V
    .locals 4

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    if-eqz v0, :cond_0

    iget-object v1, p0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    if-eqz v1, :cond_0

    check-cast v1, Landroid/graphics/PointF;

    move-object v2, v0

    check-cast v2, Landroid/graphics/PointF;

    iget v2, v2, Landroid/graphics/PointF;->x:F

    check-cast v0, Landroid/graphics/PointF;

    iget v0, v0, Landroid/graphics/PointF;->y:F

    invoke-virtual {v1, v2, v0}, Landroid/graphics/PointF;->equals(FF)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    iget-object v1, p0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    if-eqz v1, :cond_1

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    check-cast v0, Landroid/graphics/PointF;

    check-cast v1, Landroid/graphics/PointF;

    iget-object v2, p0, Lcom/airbnb/lottie/a/b/i;->pg:Lcom/airbnb/lottie/e/a;

    iget-object v3, v2, Lcom/airbnb/lottie/e/a;->lg:Landroid/graphics/PointF;

    iget-object v2, v2, Lcom/airbnb/lottie/e/a;->mg:Landroid/graphics/PointF;

    invoke-static {v0, v1, v3, v2}, Lcom/airbnb/lottie/d/h;->a(Landroid/graphics/PointF;Landroid/graphics/PointF;Landroid/graphics/PointF;Landroid/graphics/PointF;)Landroid/graphics/Path;

    move-result-object v0

    iput-object v0, p0, Lcom/airbnb/lottie/a/b/i;->path:Landroid/graphics/Path;

    :cond_1
    return-void
.end method
