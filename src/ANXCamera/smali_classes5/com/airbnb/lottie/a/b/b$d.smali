.class final Lcom/airbnb/lottie/a/b/b$d;
.super Ljava/lang/Object;
.source "BaseKeyframeAnimation.java"

# interfaces
.implements Lcom/airbnb/lottie/a/b/b$c;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/airbnb/lottie/a/b/b;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "d"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Ljava/lang/Object;",
        "Lcom/airbnb/lottie/a/b/b$c<",
        "TT;>;"
    }
.end annotation


# instance fields
.field private final Uc:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "+",
            "Lcom/airbnb/lottie/e/a<",
            "TT;>;>;"
        }
    .end annotation
.end field

.field private Vc:Lcom/airbnb/lottie/e/a;
    .annotation build Landroidx/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/airbnb/lottie/e/a<",
            "TT;>;"
        }
    .end annotation
.end field

.field private Wc:Lcom/airbnb/lottie/e/a;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/airbnb/lottie/e/a<",
            "TT;>;"
        }
    .end annotation
.end field

.field private Xc:F


# direct methods
.method constructor <init>(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "+",
            "Lcom/airbnb/lottie/e/a<",
            "TT;>;>;)V"
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Wc:Lcom/airbnb/lottie/e/a;

    const/high16 v0, -0x40800000    # -1.0f

    iput v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Xc:F

    iput-object p1, p0, Lcom/airbnb/lottie/a/b/b$d;->Uc:Ljava/util/List;

    const/4 p1, 0x0

    invoke-direct {p0, p1}, Lcom/airbnb/lottie/a/b/b$d;->l(F)Lcom/airbnb/lottie/e/a;

    move-result-object p1

    iput-object p1, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    return-void
.end method

.method private l(F)Lcom/airbnb/lottie/e/a;
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(F)",
            "Lcom/airbnb/lottie/e/a<",
            "TT;>;"
        }
    .end annotation

    iget-object v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Uc:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v1

    const/4 v2, 0x1

    sub-int/2addr v1, v2

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/airbnb/lottie/e/a;

    invoke-virtual {v0}, Lcom/airbnb/lottie/e/a;->nc()F

    move-result v1

    cmpl-float v1, p1, v1

    if-ltz v1, :cond_0

    return-object v0

    :cond_0
    iget-object v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Uc:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    add-int/lit8 v0, v0, -0x2

    :goto_0
    if-lt v0, v2, :cond_3

    iget-object v1, p0, Lcom/airbnb/lottie/a/b/b$d;->Uc:Ljava/util/List;

    invoke-interface {v1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/airbnb/lottie/e/a;

    iget-object v3, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    if-ne v3, v1, :cond_1

    goto :goto_1

    :cond_1
    invoke-virtual {v1, p1}, Lcom/airbnb/lottie/e/a;->k(F)Z

    move-result v3

    if-eqz v3, :cond_2

    return-object v1

    :cond_2
    :goto_1
    add-int/lit8 v0, v0, -0x1

    goto :goto_0

    :cond_3
    iget-object p0, p0, Lcom/airbnb/lottie/a/b/b$d;->Uc:Ljava/util/List;

    const/4 p1, 0x0

    invoke-interface {p0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/airbnb/lottie/e/a;

    return-object p0
.end method


# virtual methods
.method public C()Lcom/airbnb/lottie/e/a;
    .locals 0
    .annotation build Landroidx/annotation/NonNull;
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Lcom/airbnb/lottie/e/a<",
            "TT;>;"
        }
    .end annotation

    iget-object p0, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    return-object p0
.end method

.method public E()F
    .locals 1

    iget-object p0, p0, Lcom/airbnb/lottie/a/b/b$d;->Uc:Ljava/util/List;

    const/4 v0, 0x0

    invoke-interface {p0, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/airbnb/lottie/e/a;

    invoke-virtual {p0}, Lcom/airbnb/lottie/e/a;->nc()F

    move-result p0

    return p0
.end method

.method public K()F
    .locals 1

    iget-object p0, p0, Lcom/airbnb/lottie/a/b/b$d;->Uc:Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result v0

    add-int/lit8 v0, v0, -0x1

    invoke-interface {p0, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/airbnb/lottie/e/a;

    invoke-virtual {p0}, Lcom/airbnb/lottie/e/a;->K()F

    move-result p0

    return p0
.end method

.method public b(F)Z
    .locals 2

    iget-object v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Wc:Lcom/airbnb/lottie/e/a;

    iget-object v1, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    if-ne v0, v1, :cond_0

    iget v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Xc:F

    cmpl-float v0, v0, p1

    if-nez v0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    iget-object v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    iput-object v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Wc:Lcom/airbnb/lottie/e/a;

    iput p1, p0, Lcom/airbnb/lottie/a/b/b$d;->Xc:F

    const/4 p0, 0x0

    return p0
.end method

.method public c(F)Z
    .locals 2

    iget-object v0, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    invoke-virtual {v0, p1}, Lcom/airbnb/lottie/e/a;->k(F)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    invoke-virtual {p0}, Lcom/airbnb/lottie/e/a;->isStatic()Z

    move-result p0

    xor-int/2addr p0, v1

    return p0

    :cond_0
    invoke-direct {p0, p1}, Lcom/airbnb/lottie/a/b/b$d;->l(F)Lcom/airbnb/lottie/e/a;

    move-result-object p1

    iput-object p1, p0, Lcom/airbnb/lottie/a/b/b$d;->Vc:Lcom/airbnb/lottie/e/a;

    return v1
.end method

.method public isEmpty()Z
    .locals 0

    const/4 p0, 0x0

    return p0
.end method
