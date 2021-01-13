.class Lcom/android/camera/module/WideSelfieModule$3;
.super Lcom/android/camera2/Camera2Proxy$PictureCallbackWrapper;
.source "WideSelfieModule.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/module/WideSelfieModule;->startWideSelfieShooting()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/WideSelfieModule;


# direct methods
.method constructor <init>(Lcom/android/camera/module/WideSelfieModule;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/WideSelfieModule$3;->this$0:Lcom/android/camera/module/WideSelfieModule;

    invoke-direct {p0}, Lcom/android/camera2/Camera2Proxy$PictureCallbackWrapper;-><init>()V

    return-void
.end method


# virtual methods
.method public onPictureTakenFinished(Z)V
    .locals 1

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onPictureBurstFinished success = "

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "WideSelfieModule"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onPictureTakenImageConsumed(Landroid/media/Image;Landroid/hardware/camera2/TotalCaptureResult;)Z
    .locals 1

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onPictureTaken>>image="

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    const-string v0, "WideSelfieModule"

    invoke-static {v0, p2}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz p1, :cond_1

    const/4 p2, 0x2

    invoke-static {p1, p2}, Lcom/arcsoft/camera/utils/d;->getDataFromImage(Landroid/media/Image;I)[B

    move-result-object p2

    iget-object v0, p0, Lcom/android/camera/module/WideSelfieModule$3;->this$0:Lcom/android/camera/module/WideSelfieModule;

    invoke-static {v0}, Lcom/android/camera/module/WideSelfieModule;->access$100(Lcom/android/camera/module/WideSelfieModule;)Lcom/android/camera/wideselfie/WideSelfieEngineWrapper;

    move-result-object v0

    invoke-virtual {v0, p2}, Lcom/android/camera/wideselfie/WideSelfieEngineWrapper;->onBurstCapture([B)V

    iget-object v0, p0, Lcom/android/camera/module/WideSelfieModule$3;->this$0:Lcom/android/camera/module/WideSelfieModule;

    invoke-static {v0}, Lcom/android/camera/module/WideSelfieModule;->access$400(Lcom/android/camera/module/WideSelfieModule;)[B

    move-result-object v0

    if-nez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/module/WideSelfieModule$3;->this$0:Lcom/android/camera/module/WideSelfieModule;

    invoke-static {p0, p2}, Lcom/android/camera/module/WideSelfieModule;->access$402(Lcom/android/camera/module/WideSelfieModule;[B)[B

    :cond_0
    invoke-virtual {p1}, Landroid/media/Image;->close()V

    :cond_1
    const/4 p0, 0x1

    return p0
.end method
