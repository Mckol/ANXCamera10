.class public Lcom/android/camera/module/loader/FunctionParseNearRangeTip;
.super Ljava/lang/Object;
.source "FunctionParseNearRangeTip.java"

# interfaces
.implements Lio/reactivex/functions/Function;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lio/reactivex/functions/Function<",
        "Landroid/hardware/camera2/CaptureResult;",
        "Landroid/hardware/camera2/CaptureResult;",
        ">;"
    }
.end annotation


# static fields
.field private static final DEBUG:Z

.field private static final TAG:Ljava/lang/String; = "FunctionParseNearRangeTip"


# instance fields
.field private mBottomPopupTips:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;",
            ">;"
        }
    .end annotation
.end field

.field private mDualController:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/camera/protocol/ModeProtocol$DualController;",
            ">;"
        }
    .end annotation
.end field

.field private mModule:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/camera/module/BaseModule;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 3

    const/4 v0, 0x0

    const-string v1, "near_range_dbg"

    invoke-static {v1, v0}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v1

    const/4 v2, 0x1

    if-ne v1, v2, :cond_0

    move v0, v2

    :cond_0
    sput-boolean v0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG:Z

    return-void
.end method

.method public constructor <init>(Lcom/android/camera/module/BaseModule;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mModule:Ljava/lang/ref/WeakReference;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xaf

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mBottomPopupTips:Ljava/lang/ref/WeakReference;

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xb6

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$DualController;

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mDualController:Ljava/lang/ref/WeakReference;

    return-void
.end method

.method private DEBUG(Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    sget-boolean p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG:Z

    if-eqz p0, :cond_0

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    return-void
.end method


# virtual methods
.method public synthetic Ud()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mBottomPopupTips:Ljava/lang/ref/WeakReference;

    invoke-virtual {p0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->showNearRangeTip()V

    return-void
.end method

.method public synthetic Vd()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mBottomPopupTips:Ljava/lang/ref/WeakReference;

    invoke-virtual {p0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->hideNearRangeTip()V

    return-void
.end method

.method public apply(Landroid/hardware/camera2/CaptureResult;)Landroid/hardware/camera2/CaptureResult;
    .locals 6
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_0

    return-object p1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    instance-of v0, v0, Lcom/android/camera/module/Camera2Module;

    if-eqz v0, :cond_f

    iget-object v0, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/module/Camera2Module;

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getCameraCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera2/CameraCapabilities;->supportNearRangeMode()Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_1

    invoke-virtual {v0, v2}, Lcom/android/camera/module/Camera2Module;->setNearRangeModeUIStatus(Z)V

    invoke-virtual {v0, v2}, Lcom/android/camera/module/Camera2Module;->setNearRangeMode(Z)V

    return-object p1

    :cond_1
    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getCameraCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v1

    invoke-static {v1, p1}, Lcom/android/camera2/CaptureResultParser;->getFallbackRoleId(Lcom/android/camera2/CameraCapabilities;Landroid/hardware/camera2/CaptureResult;)I

    move-result v1

    const/4 v3, 0x1

    if-eq v1, v3, :cond_2

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v3, "NearRangeMode:Not satisfied <fallback role id UW>!"

    invoke-direct {p0, v1, v3}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v2}, Lcom/android/camera/module/Camera2Module;->setNearRangeMode(Z)V

    goto/16 :goto_3

    :cond_2
    invoke-virtual {v0, v3}, Lcom/android/camera/module/Camera2Module;->setNearRangeMode(Z)V

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getBogusCameraId()I

    move-result v1

    if-eqz v1, :cond_3

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v4, "NearRangeMode:Not satisfed <back facing>!"

    invoke-direct {p0, v1, v4}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    move v1, v2

    goto :goto_0

    :cond_3
    move v1, v3

    :goto_0
    invoke-virtual {v0}, Lcom/android/camera/module/Camera2Module;->isNeedNearRangeTip()Z

    move-result v4

    if-nez v4, :cond_4

    move v1, v2

    :cond_4
    iget-object v4, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v4}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/camera/module/BaseModule;

    invoke-virtual {v4}, Lcom/android/camera/module/BaseModule;->getModuleIndex()I

    move-result v4

    const/16 v5, 0xa3

    if-eq v4, v5, :cond_5

    iget-object v4, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mModule:Ljava/lang/ref/WeakReference;

    invoke-virtual {v4}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/camera/module/BaseModule;

    invoke-virtual {v4}, Lcom/android/camera/module/BaseModule;->getModuleIndex()I

    move-result v4

    const/16 v5, 0xa5

    if-eq v4, v5, :cond_5

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v4, "NearRangeMode:Not satisfed <capture mode>!"

    invoke-direct {p0, v1, v4}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    move v1, v2

    :cond_5
    sget-boolean v4, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-nez v4, :cond_6

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v4, "NearRangeMode:Not satisfed <sat device>!"

    invoke-direct {p0, v1, v4}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    move v1, v2

    :cond_6
    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getModuleIndex()I

    move-result v4

    invoke-static {v4}, Lcom/android/camera/CameraSettings;->isDocumentModeOn(I)Z

    move-result v4

    if-eqz v4, :cond_7

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v4, "NearRangeMode:Not satisfed <document mode>!"

    invoke-direct {p0, v1, v4}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    move v1, v2

    :cond_7
    iget-object v4, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mDualController:Ljava/lang/ref/WeakReference;

    invoke-virtual {v4}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v4

    if-eqz v4, :cond_8

    iget-object v4, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mDualController:Ljava/lang/ref/WeakReference;

    invoke-virtual {v4}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/camera/protocol/ModeProtocol$DualController;

    invoke-interface {v4}, Lcom/android/camera/protocol/ModeProtocol$DualController;->isZoomPanelVisible()Z

    move-result v4

    if-eqz v4, :cond_8

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v4, "NearRangeMode:Not satisfed <zoom slide>!"

    invoke-direct {p0, v1, v4}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    move v1, v2

    :cond_8
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v4

    const/16 v5, 0xc2

    invoke-virtual {v4, v5}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v4

    check-cast v4, Lcom/android/camera/protocol/ModeProtocol$MiBeautyProtocol;

    if-eqz v4, :cond_9

    invoke-interface {v4}, Lcom/android/camera/protocol/ModeProtocol$MiBeautyProtocol;->isBeautyPanelShow()Z

    move-result v4

    if-eqz v4, :cond_9

    move v4, v3

    goto :goto_1

    :cond_9
    move v4, v2

    :goto_1
    if-eqz v4, :cond_a

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v4, "NearRangeMode:Not satisfed <beauty panel>!"

    invoke-direct {p0, v1, v4}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    move v1, v2

    :cond_a
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v4

    const/16 v5, 0xb4

    invoke-virtual {v4, v5}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v4

    check-cast v4, Lcom/android/camera/protocol/ModeProtocol$MakeupProtocol;

    if-eqz v4, :cond_b

    invoke-interface {v4}, Lcom/android/camera/protocol/ModeProtocol$MakeupProtocol;->isSeekBarVisible()Z

    move-result v4

    if-eqz v4, :cond_b

    goto :goto_2

    :cond_b
    move v3, v2

    :goto_2
    if-eqz v3, :cond_c

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v3, "NearRangeMode:Not satisfed <seek bar>!"

    invoke-direct {p0, v1, v3}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_3

    :cond_c
    move v2, v1

    :goto_3
    if-eqz v2, :cond_d

    iget-object v1, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mBottomPopupTips:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_e

    iget-object v1, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mBottomPopupTips:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->isNearRangeTipShowing()Z

    move-result v1

    if-nez v1, :cond_e

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v3, "NearRangeMode:Enter near range mode"

    invoke-direct {p0, v1, v3}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getHandler()Landroid/os/Handler;

    move-result-object v1

    new-instance v3, Lcom/android/camera/module/loader/a;

    invoke-direct {v3, p0}, Lcom/android/camera/module/loader/a;-><init>(Lcom/android/camera/module/loader/FunctionParseNearRangeTip;)V

    invoke-virtual {v1, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    goto :goto_4

    :cond_d
    iget-object v1, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mBottomPopupTips:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_e

    iget-object v1, p0, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->mBottomPopupTips:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;

    invoke-interface {v1}, Lcom/android/camera/protocol/ModeProtocol$BottomPopupTips;->isNearRangeTipShowing()Z

    move-result v1

    if-eqz v1, :cond_e

    sget-object v1, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->TAG:Ljava/lang/String;

    const-string v3, "NearRangeMode: hide near range mode tip"

    invoke-direct {p0, v1, v3}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->DEBUG(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getHandler()Landroid/os/Handler;

    move-result-object v1

    new-instance v3, Lcom/android/camera/module/loader/b;

    invoke-direct {v3, p0}, Lcom/android/camera/module/loader/b;-><init>(Lcom/android/camera/module/loader/FunctionParseNearRangeTip;)V

    invoke-virtual {v1, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_e
    :goto_4
    invoke-virtual {v0, v2}, Lcom/android/camera/module/Camera2Module;->setNearRangeModeUIStatus(Z)V

    :cond_f
    return-object p1
.end method

.method public bridge synthetic apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    check-cast p1, Landroid/hardware/camera2/CaptureResult;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/FunctionParseNearRangeTip;->apply(Landroid/hardware/camera2/CaptureResult;)Landroid/hardware/camera2/CaptureResult;

    move-result-object p0

    return-object p0
.end method
