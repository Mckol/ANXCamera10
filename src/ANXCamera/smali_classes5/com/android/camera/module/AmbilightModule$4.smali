.class Lcom/android/camera/module/AmbilightModule$4;
.super Lcom/android/camera2/Camera2Proxy$PictureCallbackWrapper;
.source "AmbilightModule.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/module/AmbilightModule;->startAmbilightShooting()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/AmbilightModule;


# direct methods
.method constructor <init>(Lcom/android/camera/module/AmbilightModule;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/AmbilightModule$4;->this$0:Lcom/android/camera/module/AmbilightModule;

    invoke-direct {p0}, Lcom/android/camera2/Camera2Proxy$PictureCallbackWrapper;-><init>()V

    return-void
.end method


# virtual methods
.method public onCaptureStart(Lcom/xiaomi/camera/core/ParallelTaskData;Lcom/android/camera/CameraSize;ZZZZ)Lcom/xiaomi/camera/core/ParallelTaskData;
    .locals 0

    return-object p1
.end method

.method public onPictureTaken([BLandroid/hardware/camera2/CaptureResult;)V
    .locals 0

    invoke-super {p0, p1, p2}, Lcom/android/camera2/Camera2Proxy$PictureCallbackWrapper;->onPictureTaken([BLandroid/hardware/camera2/CaptureResult;)V

    return-void
.end method

.method public onPictureTakenImageConsumed(Landroid/media/Image;Landroid/hardware/camera2/TotalCaptureResult;)Z
    .locals 1

    if-eqz p1, :cond_0

    const/4 v0, 0x2

    invoke-static {p1, v0}, Lcom/arcsoft/camera/utils/d;->getDataFromImage(Landroid/media/Image;I)[B

    move-result-object v0

    invoke-virtual {p1}, Landroid/media/Image;->close()V

    iget-object p1, p0, Lcom/android/camera/module/AmbilightModule$4;->this$0:Lcom/android/camera/module/AmbilightModule;

    invoke-static {p1, p2}, Lcom/android/camera/module/AmbilightModule;->access$302(Lcom/android/camera/module/AmbilightModule;Landroid/hardware/camera2/TotalCaptureResult;)Landroid/hardware/camera2/TotalCaptureResult;

    iget-object p0, p0, Lcom/android/camera/module/AmbilightModule$4;->this$0:Lcom/android/camera/module/AmbilightModule;

    iget-object p0, p0, Lcom/android/camera/module/BaseModule;->mHandler:Landroid/os/Handler;

    const/16 p1, 0x66

    invoke-virtual {p0, p1, v0}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p0

    invoke-virtual {p0}, Landroid/os/Message;->sendToTarget()V

    :cond_0
    const/4 p0, 0x1

    return p0
.end method
