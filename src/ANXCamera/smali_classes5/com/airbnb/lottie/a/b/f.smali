.class public Lcom/airbnb/lottie/a/b/f;
.super Lcom/airbnb/lottie/a/b/g;
.source "IntegerKeyframeAnimation.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/airbnb/lottie/a/b/g<",
        "Ljava/lang/Integer;",
        ">;"
    }
.end annotation


# direct methods
.method public constructor <init>(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/e/a<",
            "Ljava/lang/Integer;",
            ">;>;)V"
        }
    .end annotation

    invoke-direct {p0, p1}, Lcom/airbnb/lottie/a/b/g;-><init>(Ljava/util/List;)V

    return-void
.end method


# virtual methods
.method a(Lcom/airbnb/lottie/e/a;F)Ljava/lang/Integer;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/airbnb/lottie/e/a<",
            "Ljava/lang/Integer;",
            ">;F)",
            "Ljava/lang/Integer;"
        }
    .end annotation

    invoke-virtual {p0, p1, p2}, Lcom/airbnb/lottie/a/b/f;->b(Lcom/airbnb/lottie/e/a;F)I

    move-result p0

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method

.method bridge synthetic a(Lcom/airbnb/lottie/e/a;F)Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0, p1, p2}, Lcom/airbnb/lottie/a/b/f;->a(Lcom/airbnb/lottie/e/a;F)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method

.method b(Lcom/airbnb/lottie/e/a;F)I
    .locals 9
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/airbnb/lottie/e/a<",
            "Ljava/lang/Integer;",
            ">;F)I"
        }
    .end annotation

    iget-object v0, p1, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    if-eqz v0, :cond_1

    iget-object v0, p1, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    if-eqz v0, :cond_1

    iget-object v1, p0, Lcom/airbnb/lottie/a/b/b;->bd:Lcom/airbnb/lottie/e/j;

    if-eqz v1, :cond_0

    iget v2, p1, Lcom/airbnb/lottie/e/a;->lb:F

    iget-object v0, p1, Lcom/airbnb/lottie/e/a;->mb:Ljava/lang/Float;

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v3

    iget-object v4, p1, Lcom/airbnb/lottie/e/a;->startValue:Ljava/lang/Object;

    iget-object v5, p1, Lcom/airbnb/lottie/e/a;->endValue:Ljava/lang/Object;

    invoke-virtual {p0}, Lcom/airbnb/lottie/a/b/b;->sb()F

    move-result v7

    invoke-virtual {p0}, Lcom/airbnb/lottie/a/b/b;->getProgress()F

    move-result v8

    move v6, p2

    invoke-virtual/range {v1 .. v8}, Lcom/airbnb/lottie/e/j;->b(FFLjava/lang/Object;Ljava/lang/Object;FFF)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    return p0

    :cond_0
    invoke-virtual {p1}, Lcom/airbnb/lottie/e/a;->yc()I

    move-result p0

    invoke-virtual {p1}, Lcom/airbnb/lottie/e/a;->wc()I

    move-result p1

    invoke-static {p0, p1, p2}, Lcom/airbnb/lottie/d/g;->a(IIF)I

    move-result p0

    return p0

    :cond_1
    new-instance p0, Ljava/lang/IllegalStateException;

    const-string p1, "Missing values for keyframe."

    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public getIntValue()I
    .locals 2

    invoke-virtual {p0}, Lcom/airbnb/lottie/a/b/b;->C()Lcom/airbnb/lottie/e/a;

    move-result-object v0

    invoke-virtual {p0}, Lcom/airbnb/lottie/a/b/b;->rb()F

    move-result v1

    invoke-virtual {p0, v0, v1}, Lcom/airbnb/lottie/a/b/f;->b(Lcom/airbnb/lottie/e/a;F)I

    move-result p0

    return p0
.end method
