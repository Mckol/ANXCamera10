.class Lcom/android/camera/module/AmbilightModule$MainHandler;
.super Landroid/os/Handler;
.source "AmbilightModule.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/module/AmbilightModule;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "MainHandler"
.end annotation


# static fields
.field public static final MSG_FRAME_PROC_DONE:I = 0x64

.field public static final MSG_MAGIC_STAR_DONE:I = 0x66

.field public static final MSG_ON_PAUSE:I = 0x69

.field public static final MSG_POST_PROC_DONE:I = 0x65

.field public static final MSG_POST_PROC_START:I = 0x6a

.field public static final MSG_TAKE_SHOT:I = 0x68

.field public static final MSG_UPDATE_DEBUG_INFO:I = 0x67


# instance fields
.field private mModule:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/camera/module/AmbilightModule;",
            ">;"
        }
    .end annotation
.end field

.field private showPreview:Z


# direct methods
.method public constructor <init>(Landroid/os/Looper;Lcom/android/camera/module/AmbilightModule;)V
    .locals 0

    invoke-direct {p0, p1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->showPreview:Z

    new-instance p1, Ljava/lang/ref/WeakReference;

    invoke-direct {p1, p2}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object p1, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->mModule:Ljava/lang/ref/WeakReference;

    return-void
.end method

.method private handleDoneMsg(Landroid/os/Message;)V
    .locals 4

    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, [B

    iget-object v0, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/module/AmbilightModule;

    if-nez v0, :cond_0

    sget-object p0, Lcom/android/camera/module/AmbilightModule;->TAG:Ljava/lang/String;

    const-string p1, "handleDoneMsg module is null!"

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    if-eqz p1, :cond_1

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$1500(Lcom/android/camera/module/AmbilightModule;)I

    move-result v1

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$1600(Lcom/android/camera/module/AmbilightModule;)I

    move-result v2

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$300(Lcom/android/camera/module/AmbilightModule;)Landroid/hardware/camera2/TotalCaptureResult;

    move-result-object v3

    invoke-static {v0, p1, v1, v2, v3}, Lcom/android/camera/module/AmbilightModule;->access$1700(Lcom/android/camera/module/AmbilightModule;[BIILandroid/hardware/camera2/TotalCaptureResult;)V

    goto :goto_0

    :cond_1
    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$1800(Lcom/android/camera/module/AmbilightModule;)V

    :goto_0
    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->showPreview:Z

    iget-object p0, v0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object p0

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/CameraScreenNail;->setExternalFrameProcessor(Lcom/android/camera/SurfaceTextureScreenNail$ExternalFrameProcessor;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/module/AmbilightModule;

    iget v1, p1, Landroid/os/Message;->what:I

    const/16 v2, 0x9

    if-eq v1, v2, :cond_2

    const/16 v2, 0x2a

    if-eq v1, v2, :cond_1

    const/4 v2, 0x0

    packed-switch v1, :pswitch_data_0

    goto/16 :goto_0

    :pswitch_0
    if-eqz v0, :cond_3

    invoke-virtual {p0}, Lcom/android/camera/module/AmbilightModule$MainHandler;->isInRendering()Z

    move-result p0

    if-eqz p0, :cond_3

    invoke-virtual {v0, v2}, Lcom/android/camera/module/BaseModule;->playCameraSound(I)V

    goto/16 :goto_0

    :pswitch_1
    if-eqz v0, :cond_3

    invoke-virtual {v0}, Lcom/android/camera/module/AmbilightModule;->closeCamera()V

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$1400(Lcom/android/camera/module/AmbilightModule;)V

    goto :goto_0

    :pswitch_2
    if-eqz v0, :cond_3

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$500(Lcom/android/camera/module/AmbilightModule;)Z

    move-result p0

    if-nez p0, :cond_3

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$1300(Lcom/android/camera/module/AmbilightModule;)V

    goto :goto_0

    :pswitch_3
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0x1a5

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$AmbilightSelector;

    if-eqz p0, :cond_3

    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Ljava/lang/String;

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$AmbilightSelector;->updateDebugInfo(Ljava/lang/String;)V

    goto :goto_0

    :pswitch_4
    if-eqz v0, :cond_0

    invoke-virtual {v0, v2}, Lcom/android/camera/module/BaseModule;->playCameraSound(I)V

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$1200(Lcom/android/camera/module/AmbilightModule;)V

    :cond_0
    invoke-direct {p0, p1}, Lcom/android/camera/module/AmbilightModule$MainHandler;->handleDoneMsg(Landroid/os/Message;)V

    goto :goto_0

    :pswitch_5
    invoke-direct {p0, p1}, Lcom/android/camera/module/AmbilightModule$MainHandler;->handleDoneMsg(Landroid/os/Message;)V

    goto :goto_0

    :pswitch_6
    if-eqz v0, :cond_3

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$500(Lcom/android/camera/module/AmbilightModule;)Z

    move-result p1

    if-eqz p1, :cond_3

    invoke-virtual {v0}, Lcom/android/camera/module/AmbilightModule;->handleFrameProcessDone()V

    iget-boolean p1, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->showPreview:Z

    if-nez p1, :cond_3

    iget-object p1, v0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object p1

    invoke-virtual {p1, v0}, Lcom/android/camera/CameraScreenNail;->setExternalFrameProcessor(Lcom/android/camera/SurfaceTextureScreenNail$ExternalFrameProcessor;)V

    invoke-static {v0}, Lcom/android/camera/module/AmbilightModule;->access$1100(Lcom/android/camera/module/AmbilightModule;)V

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->showPreview:Z

    goto :goto_0

    :cond_1
    if-eqz v0, :cond_3

    invoke-virtual {v0}, Lcom/android/camera/module/AmbilightModule;->updateRecordingTime()V

    goto :goto_0

    :cond_2
    if-eqz v0, :cond_3

    iget-object p0, v0, Lcom/android/camera/module/BaseModule;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {p0, v0}, Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;->initializeFocusView(Lcom/android/camera/ui/FocusView$ExposureViewListener;)V

    :cond_3
    :goto_0
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x64
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public isInRendering()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/module/AmbilightModule$MainHandler;->showPreview:Z

    return p0
.end method
