.class public Lcom/airbnb/lottie/model/content/ShapeTrimPath;
.super Ljava/lang/Object;
.source "ShapeTrimPath.java"

# interfaces
.implements Lcom/airbnb/lottie/model/content/b;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/airbnb/lottie/model/content/ShapeTrimPath$Type;
    }
.end annotation


# instance fields
.field private final end:Lcom/airbnb/lottie/model/a/b;

.field private final hidden:Z

.field private final name:Ljava/lang/String;

.field private final offset:Lcom/airbnb/lottie/model/a/b;

.field private final start:Lcom/airbnb/lottie/model/a/b;

.field private final type:Lcom/airbnb/lottie/model/content/ShapeTrimPath$Type;


# direct methods
.method public constructor <init>(Ljava/lang/String;Lcom/airbnb/lottie/model/content/ShapeTrimPath$Type;Lcom/airbnb/lottie/model/a/b;Lcom/airbnb/lottie/model/a/b;Lcom/airbnb/lottie/model/a/b;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->name:Ljava/lang/String;

    iput-object p2, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->type:Lcom/airbnb/lottie/model/content/ShapeTrimPath$Type;

    iput-object p3, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->start:Lcom/airbnb/lottie/model/a/b;

    iput-object p4, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->end:Lcom/airbnb/lottie/model/a/b;

    iput-object p5, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->offset:Lcom/airbnb/lottie/model/a/b;

    iput-boolean p6, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->hidden:Z

    return-void
.end method


# virtual methods
.method public a(Lcom/airbnb/lottie/Q;Lcom/airbnb/lottie/model/layer/c;)Lcom/airbnb/lottie/a/a/d;
    .locals 0

    new-instance p1, Lcom/airbnb/lottie/a/a/w;

    invoke-direct {p1, p2, p0}, Lcom/airbnb/lottie/a/a/w;-><init>(Lcom/airbnb/lottie/model/layer/c;Lcom/airbnb/lottie/model/content/ShapeTrimPath;)V

    return-object p1
.end method

.method public getEnd()Lcom/airbnb/lottie/model/a/b;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->end:Lcom/airbnb/lottie/model/a/b;

    return-object p0
.end method

.method public getName()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->name:Ljava/lang/String;

    return-object p0
.end method

.method public getOffset()Lcom/airbnb/lottie/model/a/b;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->offset:Lcom/airbnb/lottie/model/a/b;

    return-object p0
.end method

.method public getStart()Lcom/airbnb/lottie/model/a/b;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->start:Lcom/airbnb/lottie/model/a/b;

    return-object p0
.end method

.method public getType()Lcom/airbnb/lottie/model/content/ShapeTrimPath$Type;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->type:Lcom/airbnb/lottie/model/content/ShapeTrimPath$Type;

    return-object p0
.end method

.method public isHidden()Z
    .locals 0

    iget-boolean p0, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->hidden:Z

    return p0
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Trim Path: {start: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->start:Lcom/airbnb/lottie/model/a/b;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", end: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->end:Lcom/airbnb/lottie/model/a/b;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", offset: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeTrimPath;->offset:Lcom/airbnb/lottie/model/a/b;

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string p0, "}"

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
