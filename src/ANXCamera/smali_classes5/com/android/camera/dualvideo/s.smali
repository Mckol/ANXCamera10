.class public final synthetic Lcom/android/camera/dualvideo/s;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/DualVideoControler;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/DualVideoControler;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/s;->tg:Lcom/android/camera/dualvideo/DualVideoControler;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/s;->tg:Lcom/android/camera/dualvideo/DualVideoControler;

    check-cast p1, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->a(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V

    return-void
.end method
