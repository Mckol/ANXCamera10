.class final Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;
.super Ljava/lang/Object;
.source "LinkedHashTreeMap.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "a"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "<K:",
        "Ljava/lang/Object;",
        "V:",
        "Ljava/lang/Object;",
        ">",
        "Ljava/lang/Object;"
    }
.end annotation


# instance fields
.field private Wf:I

.field private Xf:I

.field private size:I

.field private stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f<",
            "TK;TV;>;"
        }
    .end annotation
.end field


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method a(Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;)V
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f<",
            "TK;TV;>;)V"
        }
    .end annotation

    const/4 v0, 0x0

    iput-object v0, p1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->right:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v0, p1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v0, p1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->left:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    const/4 v0, 0x1

    iput v0, p1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->height:I

    iget v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Wf:I

    if-lez v1, :cond_0

    iget v2, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    and-int/lit8 v3, v2, 0x1

    if-nez v3, :cond_0

    add-int/2addr v2, v0

    iput v2, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    sub-int/2addr v1, v0

    iput v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Wf:I

    iget v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    add-int/2addr v1, v0

    iput v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    :cond_0
    iget-object v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v1, p1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    iget p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Wf:I

    if-lez p1, :cond_1

    iget v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    and-int/lit8 v2, v1, 0x1

    if-nez v2, :cond_1

    add-int/2addr v1, v0

    iput v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    sub-int/2addr p1, v0

    iput p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Wf:I

    iget p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    :cond_1
    const/4 p1, 0x4

    :goto_0
    iget v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    add-int/lit8 v2, p1, -0x1

    and-int/2addr v1, v2

    if-ne v1, v2, :cond_5

    iget v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    if-nez v1, :cond_2

    iget-object v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget-object v2, v1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget-object v3, v2, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget-object v4, v3, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v4, v2, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v2, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v3, v2, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->left:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v1, v2, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->right:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget v4, v1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->height:I

    add-int/2addr v4, v0

    iput v4, v2, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->height:I

    iput-object v2, v3, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v2, v1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    goto :goto_1

    :cond_2
    const/4 v2, 0x0

    if-ne v1, v0, :cond_3

    iget-object v1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget-object v3, v1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v3, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput-object v1, v3, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->right:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget v4, v1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->height:I

    add-int/2addr v4, v0

    iput v4, v3, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->height:I

    iput-object v3, v1, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iput v2, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    goto :goto_1

    :cond_3
    const/4 v3, 0x2

    if-ne v1, v3, :cond_4

    iput v2, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    :cond_4
    :goto_1
    mul-int/lit8 p1, p1, 0x2

    goto :goto_0

    :cond_5
    return-void
.end method

.method reset(I)V
    .locals 1

    invoke-static {p1}, Ljava/lang/Integer;->highestOneBit(I)I

    move-result v0

    mul-int/lit8 v0, v0, 0x2

    add-int/lit8 v0, v0, -0x1

    sub-int/2addr v0, p1

    iput v0, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Wf:I

    const/4 p1, 0x0

    iput p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->size:I

    iput p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->Xf:I

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    return-void
.end method

.method tc()Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f<",
            "TK;TV;>;"
        }
    .end annotation

    iget-object p0, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$a;->stack:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    iget-object v0, p0, Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;->parent:Lcom/airbnb/lottie/parser/moshi/LinkedHashTreeMap$f;

    if-nez v0, :cond_0

    return-object p0

    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    invoke-direct {p0}, Ljava/lang/IllegalStateException;-><init>()V

    throw p0
.end method
