.class public Lcom/airbnb/lottie/e/a;
.super Ljava/lang/Object;
.source "Keyframe.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Ljava/lang/Object;"
    }
.end annotation


# static fields
.field private static final ng:F = -3987645.8f

.field private static final og:I = 0x2ec8fb09


# instance fields
.field public endValue:Ljava/lang/Object;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "TT;"
        }
    .end annotation
.end field

.field private fg:F

.field private gg:F

.field private final ha:Lcom/airbnb/lottie/m;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation
.end field

.field private hg:I

.field private ig:I

.field public final interpolator:Landroid/view/animation/Interpolator;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation
.end field

.field private jg:F

.field private kg:F

.field public final lb:F

.field public lg:Landroid/graphics/PointF;

.field public mb:Ljava/lang/Float;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation
.end field

.field public mg:Landroid/graphics/PointF;

.field public final startValue:Ljava/lang/Object;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "TT;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/airbnb/lottie/m;Ljava/lang/Object;Ljava/lang/Object;Landroid/view/animation/Interpolator;FLjava/lang/Float;)V
    .locals 1
    .param p2    # Ljava/lang/Object;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param
    .param p3    # Ljava/lang/Object;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param
    .param p4    # Landroid/view/animation/Interpolator;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param
    .param p6    # Ljava/lang/Float;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/airbnb/lottie/m;",
            "TT;TT;",
            "Landroid/view/animation/Interpolator;",
            "F",
            "Ljava/lang/Float;",
            ")V"
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const v0, -0x358c9d09

    iput v0, p0, Lcom/airbnb/lottie/e/a;->fg:F

    iput v0, p0, Lcom/airbnb/lottie/e/a;->gg:F

    const v0, 0x2ec8fb09

    iput v0, p0, Lcom/airbnb/lottie/e/a;->hg:I

    iput v0, p0, Lcom/airbnb/lottie/e/a;->ig:I

    const/4 v0, 0x1

    iput v0, p0, Lcom/airbnb/lottie/e/a;->jg:F

    iput v0, p0, Lcom/airbnb/lottie/e/a;->kg:F

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/airbnb/lottie/e/a;->lg:Landroid/graphics/PointF;

    iput-object v0, p0, Lcom/airbnb/lottie/e/a;->mg:Landroid/graphics/PointF;

    iput-object p1, p0, Lcom/airbnb/lottie/e/a;->ha:Lcom/airbnb/lottie/m;

    iput-object p2, p0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    iput-object p3, p0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    iput-object p4, p0, Lcom/airbnb/lottie/e/a;->interpolator:Landroid/view/animation/Interpolator;

    iput p5, p0, Lcom/airbnb/lottie/e/a;->lb:F

    iput-object p6, p0, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    return-void
.end method

.method public constructor <init>(Ljava/lang/Object;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;)V"
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const v0, -0x358c9d09

    iput v0, p0, Lcom/airbnb/lottie/e/a;->fg:F

    iput v0, p0, Lcom/airbnb/lottie/e/a;->gg:F

    const v0, 0x2ec8fb09

    iput v0, p0, Lcom/airbnb/lottie/e/a;->hg:I

    iput v0, p0, Lcom/airbnb/lottie/e/a;->ig:I

    const/4 v0, 0x1

    iput v0, p0, Lcom/airbnb/lottie/e/a;->jg:F

    iput v0, p0, Lcom/airbnb/lottie/e/a;->kg:F

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/airbnb/lottie/e/a;->lg:Landroid/graphics/PointF;

    iput-object v1, p0, Lcom/airbnb/lottie/e/a;->mg:Landroid/graphics/PointF;

    iput-object v1, p0, Lcom/airbnb/lottie/e/a;->ha:Lcom/airbnb/lottie/m;

    iput-object p1, p0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    iput-object p1, p0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    iput-object v1, p0, Lcom/airbnb/lottie/e/a;->interpolator:Landroid/view/animation/Interpolator;

    iput v0, p0, Lcom/airbnb/lottie/e/a;->lb:F

    const p1, 0x7f7fffff    # Float.MAX_VALUE

    invoke-static {p1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p1

    iput-object p1, p0, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    return-void
.end method


# virtual methods
.method public K()F
    .locals 3

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->ha:Lcom/airbnb/lottie/m;

    const/high16 v1, 0x3f800000    # 1.0f

    if-nez v0, :cond_0

    return v1

    :cond_0
    iget v0, p0, Lcom/airbnb/lottie/e/a;->kg:F

    const/4 v2, 0x1

    cmpl-float v0, v0, v2

    if-nez v0, :cond_2

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    if-nez v0, :cond_1

    iput v1, p0, Lcom/airbnb/lottie/e/a;->kg:F

    goto :goto_0

    :cond_1
    invoke-virtual {p0}, Lcom/airbnb/lottie/e/a;->nc()F

    move-result v0

    iget-object v1, p0, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    invoke-virtual {v1}, Ljava/lang/Float;->floatValue()F

    move-result v1

    iget v2, p0, Lcom/airbnb/lottie/e/a;->lb:F

    sub-float/2addr v1, v2

    iget-object v2, p0, Lcom/airbnb/lottie/e/a;->ha:Lcom/airbnb/lottie/m;

    invoke-virtual {v2}, Lcom/airbnb/lottie/m;->bb()F

    move-result v2

    div-float/2addr v1, v2

    add-float/2addr v0, v1

    iput v0, p0, Lcom/airbnb/lottie/e/a;->kg:F

    :cond_2
    :goto_0
    iget p0, p0, Lcom/airbnb/lottie/e/a;->kg:F

    return p0
.end method

.method public isStatic()Z
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/e/a;->interpolator:Landroid/view/animation/Interpolator;

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public k(F)Z
    .locals 1
    .param p1    # F
        .annotation build Landroidx/annotation/FloatRange;
            from = 0.0
            to = 1.0
        .end annotation
    .end param

    invoke-virtual {p0}, Lcom/airbnb/lottie/e/a;->nc()F

    move-result v0

    cmpl-float v0, p1, v0

    if-ltz v0, :cond_0

    invoke-virtual {p0}, Lcom/airbnb/lottie/e/a;->K()F

    move-result p0

    cmpg-float p0, p1, p0

    if-gez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public nc()F
    .locals 3

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->ha:Lcom/airbnb/lottie/m;

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget v1, p0, Lcom/airbnb/lottie/e/a;->jg:F

    const/4 v2, 0x1

    cmpl-float v1, v1, v2

    if-nez v1, :cond_1

    iget v1, p0, Lcom/airbnb/lottie/e/a;->lb:F

    invoke-virtual {v0}, Lcom/airbnb/lottie/m;->hb()F

    move-result v0

    sub-float/2addr v1, v0

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->ha:Lcom/airbnb/lottie/m;

    invoke-virtual {v0}, Lcom/airbnb/lottie/m;->bb()F

    move-result v0

    div-float/2addr v1, v0

    iput v1, p0, Lcom/airbnb/lottie/e/a;->jg:F

    :cond_1
    iget p0, p0, Lcom/airbnb/lottie/e/a;->jg:F

    return p0
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Keyframe{startValue="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", endValue="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", startFrame="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/airbnb/lottie/e/a;->lb:F

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, ", endFrame="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", interpolator="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p0, p0, Lcom/airbnb/lottie/e/a;->interpolator:Landroid/view/animation/Interpolator;

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const/16 p0, 0x7d

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public vc()F
    .locals 2

    iget v0, p0, Lcom/airbnb/lottie/e/a;->gg:F

    const v1, -0x358c9d09

    cmpl-float v0, v0, v1

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    check-cast v0, Ljava/lang/Float;

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v0

    iput v0, p0, Lcom/airbnb/lottie/e/a;->gg:F

    :cond_0
    iget p0, p0, Lcom/airbnb/lottie/e/a;->gg:F

    return p0
.end method

.method public wc()I
    .locals 2

    iget v0, p0, Lcom/airbnb/lottie/e/a;->ig:I

    const v1, 0x2ec8fb09

    if-ne v0, v1, :cond_0

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iput v0, p0, Lcom/airbnb/lottie/e/a;->ig:I

    :cond_0
    iget p0, p0, Lcom/airbnb/lottie/e/a;->ig:I

    return p0
.end method

.method public xc()F
    .locals 2

    iget v0, p0, Lcom/airbnb/lottie/e/a;->fg:F

    const v1, -0x358c9d09

    cmpl-float v0, v0, v1

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    check-cast v0, Ljava/lang/Float;

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v0

    iput v0, p0, Lcom/airbnb/lottie/e/a;->fg:F

    :cond_0
    iget p0, p0, Lcom/airbnb/lottie/e/a;->fg:F

    return p0
.end method

.method public yc()I
    .locals 2

    iget v0, p0, Lcom/airbnb/lottie/e/a;->hg:I

    const v1, 0x2ec8fb09

    if-ne v0, v1, :cond_0

    iget-object v0, p0, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iput v0, p0, Lcom/airbnb/lottie/e/a;->hg:I

    :cond_0
    iget p0, p0, Lcom/airbnb/lottie/e/a;->hg:I

    return p0
.end method
