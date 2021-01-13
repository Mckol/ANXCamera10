.class public Lcom/android/camera/dualvideo/DualVideoRenderProtocolImpl;
.super Ljava/lang/Object;
.source "DualVideoRenderProtocolImpl.java"

# interfaces
.implements Lcom/android/camera/protocol/ModeProtocol$DualVideoRenderProtocol;


# static fields
.field private static final TAG:Ljava/lang/String; = "DualVideoRenderProtocol"


# instance fields
.field private mControler:Lcom/android/camera/dualvideo/DualVideoControler;


# direct methods
.method public constructor <init>(Landroid/content/res/Resources;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/DualVideoControler;-><init>()V

    iput-object v0, p0, Lcom/android/camera/dualvideo/DualVideoRenderProtocolImpl;->mControler:Lcom/android/camera/dualvideo/DualVideoControler;

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoRenderProtocolImpl;->mControler:Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->setmResources(Landroid/content/res/Resources;)V

    return-void
.end method


# virtual methods
.method public getControler()Lcom/android/camera/dualvideo/DualVideoControler;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/DualVideoRenderProtocolImpl;->mControler:Lcom/android/camera/dualvideo/DualVideoControler;

    return-object p0
.end method

.method public registerProtocol()V
    .locals 2

    const-string v0, "DualVideoRenderProtocol"

    const-string v1, "registerProtocol: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0x1ae

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->attachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method

.method public unRegisterProtocol()V
    .locals 2

    const-string v0, "DualVideoRenderProtocol"

    const-string v1, "unRegisterProtocol: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->tj()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->setmShouldDraw6Patch(Z)V

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->resetIds()V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0x1ae

    invoke-virtual {v0, v1, p0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->detachProtocol(ILcom/android/camera/protocol/ModeProtocol$BaseProtocol;)V

    return-void
.end method
