.class public Lcom/airbnb/lottie/model/c;
.super Ljava/lang/Object;
.source "FontCharacter.java"


# annotations
.annotation build Landroidx/annotation/RestrictTo;
    value = {
        .enum Landroidx/annotation/RestrictTo$Scope;->LIBRARY:Landroidx/annotation/RestrictTo$Scope;
    }
.end annotation


# instance fields
.field private final Vd:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/model/content/j;",
            ">;"
        }
    .end annotation
.end field

.field private final Wd:C

.field private final fontFamily:Ljava/lang/String;

.field private final size:D

.field private final style:Ljava/lang/String;

.field private final width:D


# direct methods
.method public constructor <init>(Ljava/util/List;CDDLjava/lang/String;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/model/content/j;",
            ">;CDD",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/airbnb/lottie/model/c;->Vd:Ljava/util/List;

    iput-char p2, p0, Lcom/airbnb/lottie/model/c;->Wd:C

    iput-wide p3, p0, Lcom/airbnb/lottie/model/c;->size:D

    iput-wide p5, p0, Lcom/airbnb/lottie/model/c;->width:D

    iput-object p7, p0, Lcom/airbnb/lottie/model/c;->style:Ljava/lang/String;

    iput-object p8, p0, Lcom/airbnb/lottie/model/c;->fontFamily:Ljava/lang/String;

    return-void
.end method

.method public static a(CLjava/lang/String;Ljava/lang/String;)I
    .locals 1

    const/4 v0, 0x0

    add-int/2addr v0, p0

    mul-int/lit8 v0, v0, 0x1f

    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    move-result p0

    add-int/2addr v0, p0

    mul-int/lit8 v0, v0, 0x1f

    invoke-virtual {p2}, Ljava/lang/String;->hashCode()I

    move-result p0

    add-int/2addr v0, p0

    return v0
.end method


# virtual methods
.method public Eb()Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/model/content/j;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/airbnb/lottie/model/c;->Vd:Ljava/util/List;

    return-object p0
.end method

.method getSize()D
    .locals 2

    iget-wide v0, p0, Lcom/airbnb/lottie/model/c;->size:D

    return-wide v0
.end method

.method getStyle()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/c;->style:Ljava/lang/String;

    return-object p0
.end method

.method public getWidth()D
    .locals 2

    iget-wide v0, p0, Lcom/airbnb/lottie/model/c;->width:D

    return-wide v0
.end method

.method public hashCode()I
    .locals 2

    iget-char v0, p0, Lcom/airbnb/lottie/model/c;->Wd:C

    iget-object v1, p0, Lcom/airbnb/lottie/model/c;->fontFamily:Ljava/lang/String;

    iget-object p0, p0, Lcom/airbnb/lottie/model/c;->style:Ljava/lang/String;

    invoke-static {v0, v1, p0}, Lcom/airbnb/lottie/model/c;->a(CLjava/lang/String;Ljava/lang/String;)I

    move-result p0

    return p0
.end method
