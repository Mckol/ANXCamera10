.class public final synthetic Lcom/android/camera/dualvideo/q;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/graphics/SurfaceTexture$OnFrameAvailableListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/DualVideoControler;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/DualVideoControler;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/q;->tg:Lcom/android/camera/dualvideo/DualVideoControler;

    return-void
.end method


# virtual methods
.method public final onFrameAvailable(Landroid/graphics/SurfaceTexture;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/q;->tg:Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->a(Landroid/graphics/SurfaceTexture;)V

    return-void
.end method
