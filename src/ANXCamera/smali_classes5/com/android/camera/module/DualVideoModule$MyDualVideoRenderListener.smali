.class Lcom/android/camera/module/DualVideoModule$MyDualVideoRenderListener;
.super Ljava/lang/Object;
.source "DualVideoModule.java"

# interfaces
.implements Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/module/DualVideoModule;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "MyDualVideoRenderListener"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/DualVideoModule;


# direct methods
.method constructor <init>(Lcom/android/camera/module/DualVideoModule;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/DualVideoModule$MyDualVideoRenderListener;->this$0:Lcom/android/camera/module/DualVideoModule;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onRenderRequestNeeded()V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule$MyDualVideoRenderListener;->this$0:Lcom/android/camera/module/DualVideoModule;

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->requestRender()V

    const-string p0, "DualVideoModule"

    const-string v0, "onRenderRequestNeeded: "

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onSubImageAvailable()V
    .locals 0

    return-void
.end method
