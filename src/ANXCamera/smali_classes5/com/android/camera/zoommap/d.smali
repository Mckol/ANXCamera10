.class public final synthetic Lcom/android/camera/zoommap/d;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/graphics/SurfaceTexture$OnFrameAvailableListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/zoommap/ZoomMapController;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/zoommap/ZoomMapController;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/zoommap/d;->tg:Lcom/android/camera/zoommap/ZoomMapController;

    return-void
.end method


# virtual methods
.method public final onFrameAvailable(Landroid/graphics/SurfaceTexture;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/zoommap/d;->tg:Lcom/android/camera/zoommap/ZoomMapController;

    invoke-virtual {p0, p1}, Lcom/android/camera/zoommap/ZoomMapController;->b(Landroid/graphics/SurfaceTexture;)V

    return-void
.end method
