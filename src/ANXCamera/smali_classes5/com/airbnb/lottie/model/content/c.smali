.class public Lcom/airbnb/lottie/model/content/c;
.super Ljava/lang/Object;
.source "GradientColor.java"


# instance fields
.field private final ce:[F

.field private final de:[I


# direct methods
.method public constructor <init>([F[I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/airbnb/lottie/model/content/c;->ce:[F

    iput-object p2, p0, Lcom/airbnb/lottie/model/content/c;->de:[I

    return-void
.end method


# virtual methods
.method public a(Lcom/airbnb/lottie/model/content/c;Lcom/airbnb/lottie/model/content/c;F)V
    .locals 4

    iget-object v0, p1, Lcom/airbnb/lottie/model/content/c;->de:[I

    array-length v0, v0

    iget-object v1, p2, Lcom/airbnb/lottie/model/content/c;->de:[I

    array-length v1, v1

    if-ne v0, v1, :cond_1

    const/4 v0, 0x0

    :goto_0
    iget-object v1, p1, Lcom/airbnb/lottie/model/content/c;->de:[I

    array-length v1, v1

    if-ge v0, v1, :cond_0

    iget-object v1, p0, Lcom/airbnb/lottie/model/content/c;->ce:[F

    iget-object v2, p1, Lcom/airbnb/lottie/model/content/c;->ce:[F

    aget v2, v2, v0

    iget-object v3, p2, Lcom/airbnb/lottie/model/content/c;->ce:[F

    aget v3, v3, v0

    invoke-static {v2, v3, p3}, Lcom/airbnb/lottie/d/g;->lerp(FFF)F

    move-result v2

    aput v2, v1, v0

    iget-object v1, p0, Lcom/airbnb/lottie/model/content/c;->de:[I

    iget-object v2, p1, Lcom/airbnb/lottie/model/content/c;->de:[I

    aget v2, v2, v0

    iget-object v3, p2, Lcom/airbnb/lottie/model/content/c;->de:[I

    aget v3, v3, v0

    invoke-static {p3, v2, v3}, Lcom/airbnb/lottie/d/b;->a(FII)I

    move-result v2

    aput v2, v1, v0

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_0
    return-void

    :cond_1
    new-instance p0, Ljava/lang/IllegalArgumentException;

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "Cannot interpolate between gradients. Lengths vary ("

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p1, p1, Lcom/airbnb/lottie/model/content/c;->de:[I

    array-length p1, p1

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, " vs "

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p1, p2, Lcom/airbnb/lottie/model/content/c;->de:[I

    array-length p1, p1

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, ")"

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public getColors()[I
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/c;->de:[I

    return-object p0
.end method

.method public getPositions()[F
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/c;->ce:[F

    return-object p0
.end method

.method public getSize()I
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/c;->de:[I

    array-length p0, p0

    return p0
.end method
