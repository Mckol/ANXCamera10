.class public Lcom/airbnb/lottie/S;
.super Ljava/lang/Object;
.source "LottieImageAsset.java"


# instance fields
.field private final Lb:Ljava/lang/String;

.field private bitmap:Landroid/graphics/Bitmap;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation
.end field

.field private final fileName:Ljava/lang/String;

.field private final height:I

.field private final id:Ljava/lang/String;

.field private final width:I


# direct methods
.method public constructor <init>(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .annotation build Landroidx/annotation/RestrictTo;
        value = {
            .enum Landroidx/annotation/RestrictTo$Scope;->LIBRARY:Landroidx/annotation/RestrictTo$Scope;
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/airbnb/lottie/S;->width:I

    iput p2, p0, Lcom/airbnb/lottie/S;->height:I

    iput-object p3, p0, Lcom/airbnb/lottie/S;->id:Ljava/lang/String;

    iput-object p4, p0, Lcom/airbnb/lottie/S;->fileName:Ljava/lang/String;

    iput-object p5, p0, Lcom/airbnb/lottie/S;->Lb:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public getBitmap()Landroid/graphics/Bitmap;
    .locals 0
    .annotation build Landroidx/annotation/Nullable;
    .end annotation

    iget-object p0, p0, Lcom/airbnb/lottie/S;->bitmap:Landroid/graphics/Bitmap;

    return-object p0
.end method

.method public getFileName()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/S;->fileName:Ljava/lang/String;

    return-object p0
.end method

.method public getHeight()I
    .locals 0

    iget p0, p0, Lcom/airbnb/lottie/S;->height:I

    return p0
.end method

.method public getId()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/S;->id:Ljava/lang/String;

    return-object p0
.end method

.method public getWidth()I
    .locals 0

    iget p0, p0, Lcom/airbnb/lottie/S;->width:I

    return p0
.end method

.method public kb()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/S;->Lb:Ljava/lang/String;

    return-object p0
.end method

.method public setBitmap(Landroid/graphics/Bitmap;)V
    .locals 0
    .param p1    # Landroid/graphics/Bitmap;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param

    iput-object p1, p0, Lcom/airbnb/lottie/S;->bitmap:Landroid/graphics/Bitmap;

    return-void
.end method
