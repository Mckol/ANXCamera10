.class public Lcom/airbnb/lottie/model/a;
.super Ljava/lang/Object;
.source "CubicCurveData.java"


# annotations
.annotation build Landroidx/annotation/RestrictTo;
    value = {
        .enum Landroidx/annotation/RestrictTo$Scope;->LIBRARY:Landroidx/annotation/RestrictTo$Scope;
    }
.end annotation


# instance fields
.field private final Nd:Landroid/graphics/PointF;

.field private final Od:Landroid/graphics/PointF;

.field private final Pd:Landroid/graphics/PointF;


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Landroid/graphics/PointF;

    invoke-direct {v0}, Landroid/graphics/PointF;-><init>()V

    iput-object v0, p0, Lcom/airbnb/lottie/model/a;->Nd:Landroid/graphics/PointF;

    new-instance v0, Landroid/graphics/PointF;

    invoke-direct {v0}, Landroid/graphics/PointF;-><init>()V

    iput-object v0, p0, Lcom/airbnb/lottie/model/a;->Od:Landroid/graphics/PointF;

    new-instance v0, Landroid/graphics/PointF;

    invoke-direct {v0}, Landroid/graphics/PointF;-><init>()V

    iput-object v0, p0, Lcom/airbnb/lottie/model/a;->Pd:Landroid/graphics/PointF;

    return-void
.end method

.method public constructor <init>(Landroid/graphics/PointF;Landroid/graphics/PointF;Landroid/graphics/PointF;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/airbnb/lottie/model/a;->Nd:Landroid/graphics/PointF;

    iput-object p2, p0, Lcom/airbnb/lottie/model/a;->Od:Landroid/graphics/PointF;

    iput-object p3, p0, Lcom/airbnb/lottie/model/a;->Pd:Landroid/graphics/PointF;

    return-void
.end method


# virtual methods
.method public Ab()Landroid/graphics/PointF;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/a;->Nd:Landroid/graphics/PointF;

    return-object p0
.end method

.method public Bb()Landroid/graphics/PointF;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/a;->Od:Landroid/graphics/PointF;

    return-object p0
.end method

.method public Cb()Landroid/graphics/PointF;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/a;->Pd:Landroid/graphics/PointF;

    return-object p0
.end method

.method public c(FF)V
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/a;->Nd:Landroid/graphics/PointF;

    invoke-virtual {p0, p1, p2}, Landroid/graphics/PointF;->set(FF)V

    return-void
.end method

.method public d(FF)V
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/a;->Od:Landroid/graphics/PointF;

    invoke-virtual {p0, p1, p2}, Landroid/graphics/PointF;->set(FF)V

    return-void
.end method

.method public e(FF)V
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/a;->Pd:Landroid/graphics/PointF;

    invoke-virtual {p0, p1, p2}, Landroid/graphics/PointF;->set(FF)V

    return-void
.end method
