.class public final synthetic Lcom/android/camera/dualvideo/m;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Function;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/DualVideoControler;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/DualVideoControler;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/m;->tg:Lcom/android/camera/dualvideo/DualVideoControler;

    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/m;->tg:Lcom/android/camera/dualvideo/DualVideoControler;

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->b(Lcom/android/camera/dualvideo/render/IRenderable;)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method
