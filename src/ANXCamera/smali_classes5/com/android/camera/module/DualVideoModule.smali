.class public Lcom/android/camera/module/DualVideoModule;
.super Lcom/android/camera/module/VideoModule;
.source "DualVideoModule.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/module/DualVideoModule$MyDualVideoRenderListener;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "DualVideoModule"


# instance fields
.field private mBottomControlView:Landroid/widget/ImageView;

.field private mCameraSwitched:Z

.field private volatile mCurrentTapId:I

.field private mFocusCallback:Lcom/android/camera2/Camera2Proxy$FocusCallback;

.field private mMainFrameisAvailable:Z

.field private mRecordCaptureTimes:I

.field private mRecordPausedTimes:I

.field protected mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

.field protected mSubFocusAreaSupported:Z

.field private mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

.field protected mSubMeteringAreaSupported:Z

.field private mTopControlView:Landroid/widget/ImageView;

.field public mTouchEventView:Lcom/android/camera/dualvideo/TouchEventView;


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/VideoModule;-><init>()V

    new-instance v0, Lcom/android/camera/module/K;

    invoke-direct {v0, p0}, Lcom/android/camera/module/K;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    iput-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mFocusCallback:Lcom/android/camera2/Camera2Proxy$FocusCallback;

    return-void
.end method

.method static synthetic a(Lcom/android/camera/dualvideo/UserSelectData;Lcom/android/camera/dualvideo/UserSelectData;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    invoke-static {p0, p1}, Ljava/lang/Integer;->compare(II)I

    move-result p0

    return p0
.end method

.method static synthetic a(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Z)V
    .locals 0

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->switchModeOrExternalTipLayout(Z)V

    return-void
.end method

.method private addViewForGestureRecongnize(Landroid/view/ViewGroup;)V
    .locals 2

    new-instance v0, Lcom/android/camera/dualvideo/TouchEventView;

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/android/camera/dualvideo/TouchEventView;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mTouchEventView:Lcom/android/camera/dualvideo/TouchEventView;

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mTouchEventView:Lcom/android/camera/dualvideo/TouchEventView;

    new-instance v1, Lcom/android/camera/module/O;

    invoke-direct {v1, p0}, Lcom/android/camera/module/O;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/TouchEventView;->setListener(Lcom/android/camera/dualvideo/TouchEventView$TouchEventListener;)V

    new-instance v0, Landroid/widget/RelativeLayout$LayoutParams;

    const/4 v1, -0x1

    invoke-direct {v0, v1, v1}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mTouchEventView:Lcom/android/camera/dualvideo/TouchEventView;

    invoke-virtual {p1, p0, v0}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    return-void
.end method

.method private addViewForPreviewCompose(Landroid/view/ViewGroup;)V
    .locals 9

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isEqualPreview()Z

    move-result v0

    const/4 v1, 0x1

    invoke-static {v1}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object v2

    new-instance v3, Landroid/widget/ImageView;

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object v4

    invoke-direct {v3, v4}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const v3, 0x7f080203

    const v4, 0x7f080202

    if-eqz v0, :cond_0

    iget-object v5, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    invoke-virtual {v5, v4}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object v5, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    invoke-virtual {v5, v3}, Landroid/widget/ImageView;->setBackgroundResource(I)V

    goto :goto_0

    :cond_0
    iget-object v5, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const v6, 0x7f080204

    invoke-virtual {v5, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object v5, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const v6, 0x7f080205

    invoke-virtual {v5, v6}, Landroid/widget/ImageView;->setBackgroundResource(I)V

    :goto_0
    new-instance v5, Landroid/widget/RelativeLayout$LayoutParams;

    const/4 v6, -0x2

    invoke-direct {v5, v6, v6}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    const/16 v7, 0xa

    iput v7, v5, Landroid/widget/RelativeLayout$LayoutParams;->leftMargin:I

    iget v8, v2, Landroid/graphics/Rect;->top:I

    add-int/2addr v8, v7

    iput v8, v5, Landroid/widget/RelativeLayout$LayoutParams;->topMargin:I

    iget-object v8, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    invoke-virtual {p1, v8, v5}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    new-instance v5, Landroid/widget/ImageView;

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object v8

    invoke-direct {v5, v8}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    iput-object v5, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    iget-object v5, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    invoke-virtual {v5, v4}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object v4, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    invoke-virtual {v4, v3}, Landroid/widget/ImageView;->setBackgroundResource(I)V

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    const/4 v3, 0x4

    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    :cond_1
    new-instance v0, Landroid/widget/RelativeLayout$LayoutParams;

    invoke-direct {v0, v6, v6}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    iput v7, v0, Landroid/widget/RelativeLayout$LayoutParams;->leftMargin:I

    iget v3, v2, Landroid/graphics/Rect;->top:I

    iget v2, v2, Landroid/graphics/Rect;->bottom:I

    add-int/2addr v3, v2

    const/4 v2, 0x2

    div-int/2addr v3, v2

    add-int/2addr v3, v7

    iput v3, v0, Landroid/widget/RelativeLayout$LayoutParams;->topMargin:I

    iget-object v3, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    invoke-virtual {p1, v3, v0}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    new-array p1, v2, [Landroid/view/View;

    const/4 v0, 0x0

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    aput-object v2, p1, v0

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    aput-object v0, p1, v1

    invoke-static {p1}, Lcom/android/camera/animation/FolmeUtils;->touchTint([Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    new-instance v0, Lcom/android/camera/module/S;

    invoke-direct {v0, p0}, Lcom/android/camera/module/S;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    new-instance v0, Lcom/android/camera/module/M;

    invoke-direct {v0, p0}, Lcom/android/camera/module/M;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    return-void
.end method

.method private applyZooomForDevices(Lcom/android/camera2/Camera2Proxy;)V
    .locals 3

    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result p0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v0

    const/high16 v1, 0x3f800000    # 1.0f

    if-ne p0, v0, :cond_0

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setZoomRatio(F)V

    goto/16 :goto_0

    :cond_0
    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result p0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result v0

    if-ne p0, v0, :cond_1

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setZoomRatio(F)V

    goto :goto_0

    :cond_1
    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result p0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxCameraId()I

    move-result v0

    if-ne p0, v0, :cond_2

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setZoomRatio(F)V

    goto :goto_0

    :cond_2
    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result p0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraTeleCameraId()I

    move-result v0

    if-ne p0, v0, :cond_5

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getSelectedData()Ljava/util/ArrayList;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_3
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_4

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/UserSelectData;

    iget v0, v0, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    const/16 v2, 0x18

    if-ne v0, v2, :cond_3

    const/high16 p0, 0x40000000    # 2.0f

    invoke-virtual {p1, p0}, Lcom/android/camera2/Camera2Proxy;->setZoomRatio(F)V

    return-void

    :cond_4
    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setZoomRatio(F)V

    goto :goto_0

    :cond_5
    invoke-virtual {p1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result p0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getFrontCameraId()I

    move-result v0

    if-ne p0, v0, :cond_6

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setZoomRatio(F)V

    goto :goto_0

    :cond_6
    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->setZoomRatio(F)V

    :goto_0
    return-void
.end method

.method static synthetic b(Landroid/view/ViewGroup;)V
    .locals 0

    invoke-virtual {p0}, Landroid/view/ViewGroup;->removeAllViews()V

    return-void
.end method

.method static synthetic b(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Z)V
    .locals 0

    invoke-interface {p0, p1}, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;->switchThumbnailFunction(Z)V

    return-void
.end method

.method static synthetic d(Lcom/android/camera/protocol/ModeProtocol$DualVideoRenderProtocol;)Lcom/android/camera/dualvideo/DualVideoControler;
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$DualVideoRenderProtocol;->getControler()Lcom/android/camera/dualvideo/DualVideoControler;

    move-result-object p0

    return-object p0
.end method

.method static synthetic e(Lcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 1

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    const/16 v0, 0xb

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private getControler()Ljava/util/Optional;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Optional<",
            "Lcom/android/camera/dualvideo/DualVideoControler;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/module/VideoModule;->mProtolRender:Ljava/util/Optional;

    sget-object v0, Lcom/android/camera/module/L;->INSTANCE:Lcom/android/camera/module/L;

    invoke-virtual {p0, v0}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p0

    return-object p0
.end method

.method private getZoomGroupForTrack()Ljava/lang/String;
    .locals 2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getSelectedData()Ljava/util/ArrayList;

    move-result-object p0

    sget-object v0, Lcom/android/camera/module/T;->INSTANCE:Lcom/android/camera/module/T;

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->sort(Ljava/util/Comparator;)V

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/dualvideo/UserSelectData;

    iget v1, v1, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    invoke-static {v1}, Lcom/android/camera/dualvideo/CameraIDManager;->getZoomDescription(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, ":"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private handleBackStack()V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p0

    const/16 v0, 0xab

    invoke-virtual {p0, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p0

    check-cast p0, Lcom/android/camera/protocol/ModeProtocol$BackStack;

    if-eqz p0, :cond_0

    invoke-interface {p0}, Lcom/android/camera/protocol/ModeProtocol$BackStack;->handleBackStackFromKeyBack()Z

    move-result p0

    if-eqz p0, :cond_0

    nop

    :cond_0
    return-void
.end method

.method private initDualVideoControler()V
    .locals 3

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0x1af

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ModuleContent;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$ModuleContent;->getParent()Landroid/view/ViewGroup;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v1

    new-instance v2, Lcom/android/camera/module/Y;

    invoke-direct {v2, p0, v0}, Lcom/android/camera/module/Y;-><init>(Lcom/android/camera/module/DualVideoModule;Landroid/view/ViewGroup;)V

    invoke-virtual {v1, v2}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method private is6PatchWindow()Z
    .locals 0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->ismShouldDraw6Patch()Z

    move-result p0

    return p0
.end method

.method private isEqualPreview()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getmSelectedData()Ljava/util/ArrayList;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object v0, Lcom/android/camera/module/V;->INSTANCE:Lcom/android/camera/module/V;

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result p0

    return p0
.end method

.method private isMultiCameraMode()Z
    .locals 0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p0

    invoke-virtual {p0}, Lb/c/a/b;->tj()Z

    move-result p0

    return p0
.end method

.method private registDualVideoRender()V
    .locals 5

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0x1ae

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getImplFactory()Lcom/android/camera/module/impl/ImplFactory;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v2

    const/4 v3, 0x1

    new-array v3, v3, [I

    const/4 v4, 0x0

    aput v1, v3, v4

    invoke-virtual {v0, v2, v3}, Lcom/android/camera/module/impl/ImplFactory;->initModulePersistent(Lcom/android/camera/ActivityBase;[I)V

    :cond_0
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$DualVideoRenderProtocol;

    invoke-static {v0}, Ljava/util/Optional;->ofNullable(Ljava/lang/Object;)Ljava/util/Optional;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/module/VideoModule;->mProtolRender:Ljava/util/Optional;

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    new-instance v1, Lcom/android/camera/module/DualVideoModule$MyDualVideoRenderListener;

    invoke-direct {v1, p0}, Lcom/android/camera/module/DualVideoModule$MyDualVideoRenderListener;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->setListener(Lcom/android/camera/dualvideo/DualVideoControler$DualVideoRenderListener;)V

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/android/camera/SurfaceTextureScreenNail$ExternalFrameProcessor;

    invoke-virtual {v0, p0}, Lcom/android/camera/CameraScreenNail;->setExternalFrameProcessor(Lcom/android/camera/SurfaceTextureScreenNail$ExternalFrameProcessor;)V

    return-void
.end method

.method private showModeSwitchLayout(Z)V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa2

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    new-instance v1, Lcom/android/camera/module/U;

    invoke-direct {v1, v0, p1}, Lcom/android/camera/module/U;-><init>(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Z)V

    invoke-virtual {p0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_0
    return-void
.end method

.method private switch6PatchToPreview()Z
    .locals 3

    const-string v0, "DualVideoModule"

    const-string v1, "switch6PatchToPreview: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->isSwitching()Z

    move-result v0

    const/4 v1, 0x1

    if-nez v0, :cond_2

    iget-boolean v0, p0, Lcom/android/camera/module/DualVideoModule;->mMainFrameisAvailable:Z

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->is6PatchWindow()Z

    move-result v0

    const/4 v2, 0x0

    if-eqz v0, :cond_1

    invoke-direct {p0, v2}, Lcom/android/camera/module/DualVideoModule;->switchThumbnailFunction(Z)V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->setmShouldDraw6Patch(Z)V

    invoke-direct {p0, v2}, Lcom/android/camera/module/DualVideoModule;->showModeSwitchLayout(Z)V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->switch6patch2preview()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->enableContinuousRender(Z)V

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->requestRender()V

    invoke-virtual {p0, v2, v2, v2}, Lcom/android/camera/module/BaseModule;->switchCameraLens(ZZZ)V

    return v1

    :cond_1
    return v2

    :cond_2
    :goto_0
    return v1
.end method

.method private switchPreviewTo6Patch()V
    .locals 3

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->isSwitching()Z

    move-result v0

    if-nez v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/DualVideoModule;->mMainFrameisAvailable:Z

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->ismShouldDraw6Patch()Z

    move-result v1

    if-nez v1, :cond_1

    const/4 v1, 0x1

    invoke-direct {p0, v1}, Lcom/android/camera/module/DualVideoModule;->switchThumbnailFunction(Z)V

    invoke-virtual {v0, v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->setmShouldDraw6Patch(Z)V

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getDefaultDualVideoCameraIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->setIds(Ljava/util/concurrent/ConcurrentHashMap;)V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->switchPreviewTo6Patch()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->enableContinuousRender(Z)V

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->requestRender()V

    const/4 v0, 0x0

    invoke-virtual {p0, v0, v0, v0}, Lcom/android/camera/module/BaseModule;->switchCameraLens(ZZZ)V

    :cond_1
    :goto_0
    return-void
.end method

.method private switchThumbnailFunction(Z)V
    .locals 2

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0xa2

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    new-instance v1, Lcom/android/camera/module/W;

    invoke-direct {v1, v0, p1}, Lcom/android/camera/module/W;-><init>(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Z)V

    invoke-virtual {p0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_0
    return-void
.end method

.method private updateSubCameraFocusMode(I)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_2

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->getSupportedFocusModes()[I

    move-result-object v0

    invoke-static {p1, v0}, Lcom/android/camera/Util;->isSupported(I[I)Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0, p1}, Lcom/android/camera2/Camera2Proxy;->setFocusMode(I)V

    :cond_1
    return-void

    :cond_2
    :goto_0
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "updateSubCameraFocusMode: focusMode = "

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, ", but device is null..."

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "DualVideoModule"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method


# virtual methods
.method public synthetic a(Landroid/view/ViewGroup;)V
    .locals 2

    invoke-direct {p0, p1}, Lcom/android/camera/module/DualVideoModule;->addViewForGestureRecongnize(Landroid/view/ViewGroup;)V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->is6PatchWindow()Z

    move-result v0

    if-nez v0, :cond_0

    const-string v0, "DualVideoModule"

    const-string v1, "initDualVideoControler"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0, p1}, Lcom/android/camera/module/DualVideoModule;->addViewForPreviewCompose(Landroid/view/ViewGroup;)V

    :cond_0
    return-void
.end method

.method public synthetic a(Lcom/android/camera/dualvideo/DualVideoControler;)V
    .locals 0

    iget p0, p0, Lcom/android/camera/module/BaseModule;->mOrientation:I

    invoke-virtual {p1, p0}, Lcom/android/camera/dualvideo/DualVideoControler;->setOrientation(I)V

    return-void
.end method

.method public synthetic a(Lcom/android/camera/module/loader/camera2/FocusTask;)V
    .locals 4

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->getFocusTrigger()I

    move-result v0

    const/4 v1, 0x1

    if-ne v1, v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "focusTime="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->getElapsedTime()J

    move-result-wide v2

    invoke-virtual {v0, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v2, "ms focused="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->isSuccess()Z

    move-result v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " waitForRecording="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->isFocusingSnapOnFinish()Z

    move-result v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v2, "DualVideoModule"

    invoke-static {v2, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    invoke-interface {v0, v1}, Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;->setFocusViewType(Z)V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v0, p1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onFocusResult(Lcom/android/camera/module/loader/camera2/FocusTask;)V

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/SensorStateManager;->reset()V

    invoke-virtual {p1}, Lcom/android/camera/module/loader/camera2/FocusTask;->isSuccess()Z

    move-result p1

    if-eqz p1, :cond_0

    iget-boolean p1, p0, Lcom/android/camera/module/VideoBase;->m3ALocked:Z

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0, v1}, Lcom/android/camera2/Camera2Proxy;->lockExposure(Z)V

    :cond_0
    return-void
.end method

.method public synthetic a(Landroid/view/MotionEvent;)Z
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0
.end method

.method protected applyZoomRatio()V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->applyZoomRatio()V

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_2

    iget-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-nez v1, :cond_1

    goto :goto_0

    :cond_1
    invoke-direct {p0, v0}, Lcom/android/camera/module/DualVideoModule;->applyZooomForDevices(Lcom/android/camera2/Camera2Proxy;)V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-direct {p0, v0}, Lcom/android/camera/module/DualVideoModule;->applyZooomForDevices(Lcom/android/camera2/Camera2Proxy;)V

    :cond_2
    :goto_0
    return-void
.end method

.method public synthetic b(Lcom/android/camera/dualvideo/DualVideoControler;)V
    .locals 2

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/DualVideoControler;->isRecording()Z

    move-result p1

    if-nez p1, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->getEGLContext14()Landroid/opengl/EGLContext;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/VideoModule;->mRecorderSurface:Landroid/view/Surface;

    invoke-virtual {p1, v0, v1}, Lcom/android/camera/dualvideo/DualVideoControler;->startRecording(Landroid/opengl/EGLContext;Landroid/view/Surface;)V

    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->startVideoRecording()V

    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/module/DualVideoModule;->mRecordPausedTimes:I

    iput p1, p0, Lcom/android/camera/module/DualVideoModule;->mRecordCaptureTimes:I

    :cond_0
    return-void
.end method

.method public synthetic c(Lcom/android/camera/dualvideo/DualVideoControler;)V
    .locals 0

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->getGLCanvas()Lcom/android/gallery3d/ui/GLCanvasImpl;

    move-result-object p0

    invoke-virtual {p1, p0}, Lcom/android/camera/dualvideo/DualVideoControler;->release(Lcom/android/gallery3d/ui/GLCanvas;)V

    return-void
.end method

.method public cancelFocus(Z)V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isDeviceAlive()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isFrameAvailable()Z

    move-result v0

    const-string v1, "DualVideoModule"

    if-nez v0, :cond_1

    const-string p0, "cancelFocus: frame not available"

    invoke-static {v1, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    iget v0, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    iget-object v2, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v2

    if-ne v0, v2, :cond_2

    invoke-super {p0, p1}, Lcom/android/camera/module/VideoBase;->cancelFocus(Z)V

    goto :goto_0

    :cond_2
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "cancelFocus: sub camera resetFocusMode = "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x3

    if-eqz p1, :cond_3

    invoke-direct {p0, v0}, Lcom/android/camera/module/DualVideoModule;->updateSubCameraFocusMode(I)V

    :cond_3
    iget-object p1, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p1}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object p1

    const/4 v1, 0x0

    invoke-virtual {p1, v1}, Lcom/android/camera/SensorStateManager;->setFocusSensorEnabled(Z)V

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v1, p0, Lcom/android/camera/module/BaseModule;->mModuleIndex:I

    invoke-virtual {p1, v1}, Lcom/android/camera2/Camera2Proxy;->cancelFocus(I)V

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getCameraState()I

    move-result p1

    if-eq p1, v0, :cond_4

    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Lcom/android/camera/module/BaseModule;->setCameraState(I)V

    :cond_4
    :goto_0
    return-void
.end method

.method protected getOperatingMode()I
    .locals 0

    const p0, 0x8004

    return p0
.end method

.method protected initializeFocusManager()V
    .locals 5

    invoke-super {p0}, Lcom/android/camera/module/VideoBase;->initializeFocusManager()V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2}, Lcom/android/camera2/Camera2Proxy;->isFacingFront()Z

    move-result v2

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v3

    invoke-direct {v1, v0, p0, v2, v3}, Lcom/android/camera/module/loader/camera2/FocusManager2;-><init>(Lcom/android/camera2/CameraCapabilities;Lcom/android/camera/module/loader/camera2/FocusManager2$Listener;ZLandroid/os/Looper;)V

    iput-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    iget-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->isFacingFront()Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, Lcom/android/camera/dualvideo/render/DualVideoUtil;->FRONT_PREVIE:Landroid/util/Size;

    goto :goto_0

    :cond_0
    sget-object v1, Lcom/android/camera/dualvideo/render/DualVideoUtil;->BACK_PREVIE:Landroid/util/Size;

    :goto_0
    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v1}, Landroid/util/Size;->getHeight()I

    move-result v3

    invoke-virtual {v1}, Landroid/util/Size;->getWidth()I

    move-result v4

    invoke-virtual {v2, v3, v4}, Lcom/android/camera/FocusManagerAbstract;->setRenderSize(II)V

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v1}, Landroid/util/Size;->getHeight()I

    move-result v3

    invoke-virtual {v1}, Landroid/util/Size;->getWidth()I

    move-result v1

    invoke-virtual {v2, v3, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setPreviewSize(II)V

    iget-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mFocusCallback:Lcom/android/camera2/Camera2Proxy$FocusCallback;

    invoke-virtual {v1, v2}, Lcom/android/camera2/Camera2Proxy;->setFocusCallback(Lcom/android/camera2/Camera2Proxy$FocusCallback;)V

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isAFRegionSupported()Z

    move-result v1

    iput-boolean v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusAreaSupported:Z

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isAERegionSupported()Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubMeteringAreaSupported:Z

    return-void
.end method

.method public isZoomEnabled()Z
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/DualVideoControler;->isZoomEnabled()Z

    move-result p0

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public synthetic k(Landroid/view/View;)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->handleBackStack()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/DualVideoControler;->expandOrShrinkTop()Z

    move-result p1

    if-nez p1, :cond_0

    return-void

    :cond_0
    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    invoke-virtual {p1}, Landroid/widget/ImageView;->getVisibility()I

    move-result p1

    if-nez p1, :cond_1

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    const/16 v0, 0x8

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const v0, 0x7f080204

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const p1, 0x7f080205

    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setBackgroundResource(I)V

    goto :goto_0

    :cond_1
    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const v0, 0x7f080202

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const p1, 0x7f080203

    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setBackgroundResource(I)V

    :goto_0
    return-void
.end method

.method public synthetic l(Landroid/view/View;)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->handleBackStack()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/Optional;->isPresent()Z

    move-result p1

    if-eqz p1, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/DualVideoControler;->expandBottom()Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mBottomControlView:Landroid/widget/ImageView;

    const/16 v0, 0x8

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const v0, 0x7f080204

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mTopControlView:Landroid/widget/ImageView;

    const p1, 0x7f080205

    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setBackgroundResource(I)V

    :cond_0
    return-void
.end method

.method public notifyFocusAreaUpdate()V
    .locals 4

    iget v0, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    iget-object v1, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v1

    if-ne v0, v1, :cond_0

    invoke-super {p0}, Lcom/android/camera/module/VideoBase;->notifyFocusAreaUpdate()V

    goto :goto_1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    if-eqz v0, :cond_6

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->isActivityPaused()Z

    move-result v0

    if-nez v0, :cond_6

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isAlive()Z

    move-result v0

    if-nez v0, :cond_1

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-nez v0, :cond_2

    const-string p0, "DualVideoModule"

    const-string v0, "updateFocusArea: sub camera device is null!"

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_2
    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getCapabilities()Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->getActiveArraySize()Landroid/graphics/Rect;

    move-result-object v0

    iget v1, p0, Lcom/android/camera/module/BaseModule;->mZoomRatio:F

    invoke-static {v1, v0}, Lcom/android/camera/HybridZoomingSystem;->toCropRegion(FLandroid/graphics/Rect;)Landroid/graphics/Rect;

    move-result-object v1

    iget-object v2, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v2}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v2

    iget-object v3, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v3, v1, v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->getMeteringAreas(Landroid/graphics/Rect;Landroid/graphics/Rect;)[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v3

    if-eqz v3, :cond_3

    const/4 v3, 0x1

    goto :goto_0

    :cond_3
    const/4 v3, 0x0

    :goto_0
    invoke-virtual {v2, v3}, Lcom/android/camera/SensorStateManager;->setFocusSensorEnabled(Z)V

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v3, v1, v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->getMeteringAreas(Landroid/graphics/Rect;Landroid/graphics/Rect;)[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/android/camera2/Camera2Proxy;->setAERegions([Landroid/hardware/camera2/params/MeteringRectangle;)V

    iget-boolean v2, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusAreaSupported:Z

    if-eqz v2, :cond_4

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v3, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {v3, v1, v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->getFocusAreas(Landroid/graphics/Rect;Landroid/graphics/Rect;)[Landroid/hardware/camera2/params/MeteringRectangle;

    move-result-object v0

    invoke-virtual {v2, v0}, Lcom/android/camera2/Camera2Proxy;->setAFRegions([Landroid/hardware/camera2/params/MeteringRectangle;)V

    :cond_4
    invoke-static {}, Lcom/android/camera/CameraSettings;->getFocusMode()Ljava/lang/String;

    move-result-object v0

    iget-boolean v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusAreaSupported:Z

    if-eqz v1, :cond_5

    const-string v1, "manual"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_6

    :cond_5
    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->resumePreview()I

    :cond_6
    :goto_1
    return-void
.end method

.method public onBackPressed()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->ismShouldDraw6Patch()Z

    move-result v0

    if-nez v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/module/BaseModule;->mMediaRecorderRecording:Z

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->switchPreviewTo6Patch()V

    const/4 p0, 0x1

    return p0

    :cond_0
    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->onBackPressed()Z

    move-result p0

    return p0
.end method

.method protected onCameraOpened()V
    .locals 4

    invoke-static {}, Lb/c/a/c;->isMTKPlatform()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getDefaultDualVideoCameraIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v0

    const/4 v1, 0x2

    new-array v1, v1, [I

    const/4 v2, 0x0

    const/16 v3, 0x65

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v3

    aput v3, v1, v2

    const/4 v2, 0x1

    const/16 v3, 0x64

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    aput v0, v1, v2

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setMtkPipDevices([I)V

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setMtkPipDevices([I)V

    :cond_0
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->isFrontCameraId(I)Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->startFaceDetection()V

    :cond_1
    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->onCameraOpened()V

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v0

    iput v0, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    return-void
.end method

.method public onCameraPickerClicked(Landroid/view/View;)Z
    .locals 4

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->isPresent()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->switchTopBottom()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/module/DualVideoModule;->mCameraSwitched:Z

    const-wide/16 v2, 0x12c

    if-eqz v0, :cond_0

    invoke-static {p1}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    const/high16 v0, -0x3ccc0000    # -180.0f

    invoke-virtual {p1, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->rotationBy(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1, v2, v3}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_0

    :cond_0
    invoke-static {p1}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    const/high16 v0, 0x43340000    # 180.0f

    invoke-virtual {p1, v0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->rotationBy(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1, v2, v3}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    :goto_0
    iget-boolean p1, p0, Lcom/android/camera/module/DualVideoModule;->mCameraSwitched:Z

    xor-int/2addr p1, v1

    iput-boolean p1, p0, Lcom/android/camera/module/DualVideoModule;->mCameraSwitched:Z

    :cond_1
    return v1
.end method

.method public onCreate(II)V
    .locals 6

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getCookieStore()Lcom/xiaomi/camera/device/CameraHandlerThread$CookieStore;

    move-result-object v0

    invoke-virtual {v0}, Lcom/xiaomi/camera/device/CameraHandlerThread$CookieStore;->getCookies()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/xiaomi/camera/device/CameraHandlerThread$Cookie;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "enumerating: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, v1, Lcom/xiaomi/camera/device/CameraHandlerThread$Cookie;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "DualVideoModule"

    invoke-static {v3, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v2, v1, Lcom/xiaomi/camera/device/CameraHandlerThread$Cookie;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v2, :cond_0

    invoke-virtual {v2}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v4

    invoke-virtual {v4}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v4

    const/16 v5, 0x65

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Integer;

    invoke-virtual {v4}, Ljava/lang/Integer;->intValue()I

    move-result v4

    if-ne v2, v4, :cond_0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "setCameraDevice: "

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, v1, Lcom/xiaomi/camera/device/CameraHandlerThread$Cookie;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v3, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, v1, Lcom/xiaomi/camera/device/CameraHandlerThread$Cookie;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iput-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    goto :goto_0

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->registDualVideoRender()V

    invoke-super {p0, p1, p2}, Lcom/android/camera/module/VideoModule;->onCreate(II)V

    return-void
.end method

.method public onLongPress(FF)V
    .locals 0

    return-void
.end method

.method public onPause()V
    .locals 3

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v1, 0x1af

    invoke-virtual {v0, v1}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$ModuleContent;

    invoke-interface {v0}, Lcom/android/camera/protocol/ModeProtocol$ModuleContent;->getParent()Landroid/view/ViewGroup;

    move-result-object v0

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v1

    new-instance v2, Lcom/android/camera/module/Q;

    invoke-direct {v2, v0}, Lcom/android/camera/module/Q;-><init>(Landroid/view/ViewGroup;)V

    invoke-virtual {v1, v2}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->onPause()V

    return-void
.end method

.method public onPreviewSessionSuccess(Landroid/hardware/camera2/CameraCaptureSession;)V
    .locals 3

    invoke-virtual {p1}, Landroid/hardware/camera2/CameraCaptureSession;->getDevice()Landroid/hardware/camera2/CameraDevice;

    move-result-object v0

    invoke-virtual {v0}, Landroid/hardware/camera2/CameraDevice;->getId()Ljava/lang/String;

    move-result-object v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v1

    const/16 v2, 0x65

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p1, 0x3

    invoke-direct {p0, p1}, Lcom/android/camera/module/DualVideoModule;->updateSubCameraFocusMode(I)V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->resumePreview()I

    goto :goto_0

    :cond_0
    invoke-super {p0, p1}, Lcom/android/camera/module/VideoModule;->onPreviewSessionSuccess(Landroid/hardware/camera2/CameraCaptureSession;)V

    :goto_0
    return-void
.end method

.method public onResume()V
    .locals 1

    invoke-super {p0}, Lcom/android/camera/module/VideoBase;->onResume()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->registDualVideoRender()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->is6PatchWindow()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/android/camera/module/DualVideoModule;->showModeSwitchLayout(Z)V

    goto :goto_0

    :cond_0
    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/android/camera/module/DualVideoModule;->showModeSwitchLayout(Z)V

    :cond_1
    :goto_0
    return-void
.end method

.method public onReviewCancelClicked()V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->is6PatchWindow()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->switchPreviewTo6Patch()V

    return-void

    :cond_0
    invoke-super {p0}, Lcom/android/camera/module/VideoBase;->onReviewCancelClicked()V

    return-void
.end method

.method public onShutterButtonClick(I)V
    .locals 1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->switch6PatchToPreview()Z

    move-result v0

    if-nez v0, :cond_1

    :cond_0
    invoke-super {p0, p1}, Lcom/android/camera/module/VideoModule;->onShutterButtonClick(I)V

    :cond_1
    return-void
.end method

.method public onSingleTapUp(IIZ)V
    .locals 5

    iget-boolean v0, p0, Lcom/android/camera/module/BaseModule;->mPaused:Z

    const-string v1, "DualVideoModule"

    if-nez v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v0, :cond_6

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->hasCameraException()Z

    move-result v0

    if-nez v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isPreviewReady()Z

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->isPreviewReady()Z

    move-result v0

    if-eqz v0, :cond_6

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/module/BaseModule;->isInTapableRect(II)Z

    move-result v0

    if-nez v0, :cond_0

    goto/16 :goto_3

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->isPresent()Z

    move-result v0

    if-eqz v0, :cond_5

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->isSwitching()Z

    move-result v0

    if-nez v0, :cond_5

    invoke-static {}, Lcom/android/camera/CameraSettings;->getDualVideoConfig()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->ismShouldDraw6Patch()Z

    move-result v0

    if-eqz v0, :cond_1

    goto/16 :goto_2

    :cond_1
    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v0

    const/16 v2, 0xab

    invoke-virtual {v0, v2}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v0

    check-cast v0, Lcom/android/camera/protocol/ModeProtocol$BackStack;

    if-eqz v0, :cond_5

    invoke-interface {v0, p1, p2}, Lcom/android/camera/protocol/ModeProtocol$BackStack;->handleBackStackFromTapDown(II)Z

    move-result v0

    if-eqz v0, :cond_2

    goto/16 :goto_2

    :cond_2
    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    const/4 v2, 0x1

    invoke-interface {v0, v2}, Lcom/android/camera/protocol/ModeProtocol$IndicatorProtocol;->setFocusViewType(Z)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    iput-wide v3, p0, Lcom/android/camera/module/VideoBase;->mTouchFocusStartingTime:J

    new-instance v0, Landroid/graphics/Point;

    invoke-direct {v0, p1, p2}, Landroid/graphics/Point;-><init>(II)V

    invoke-virtual {p0, v0}, Lcom/android/camera/module/BaseModule;->mapTapCoordinate(Ljava/lang/Object;)V

    invoke-virtual {p0}, Lcom/android/camera/module/DualVideoModule;->unlockAEAF()V

    const/4 v3, 0x2

    invoke-virtual {p0, v3}, Lcom/android/camera/module/BaseModule;->setCameraState(I)V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v3

    invoke-virtual {v3}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/dualvideo/DualVideoControler;

    int-to-float p1, p1

    int-to-float p2, p2

    invoke-virtual {v3, p1, p2}, Lcom/android/camera/dualvideo/DualVideoControler;->getIdByPosition(FF)I

    move-result v3

    iput v3, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    iget v3, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    iget-object v4, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v4}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v4

    if-ne v3, v4, :cond_3

    iget-object v3, p0, Lcom/android/camera/module/VideoBase;->mFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    goto :goto_0

    :cond_3
    iget-object v3, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    :goto_0
    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v4

    invoke-virtual {v4}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v4, p1, p2}, Lcom/android/camera/dualvideo/DualVideoControler;->getRenderComposeTypeByPosition(FF)I

    move-result p1

    invoke-virtual {v3, p1}, Lcom/android/camera/FocusManagerAbstract;->setRenderComposeType(I)V

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onSingleTapUp: id = "

    invoke-virtual {p2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    invoke-virtual {p2, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ", is main camera = "

    invoke-virtual {p2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    iget-object p0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result p0

    if-ne v4, p0, :cond_4

    goto :goto_1

    :cond_4
    const/4 v2, 0x0

    :goto_1
    invoke-virtual {p2, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p0, ", type = "

    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget p0, v0, Landroid/graphics/Point;->x:I

    iget p1, v0, Landroid/graphics/Point;->y:I

    invoke-virtual {v3, p0, p1, p3}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onSingleTapUp(IIZ)V

    :cond_5
    :goto_2
    return-void

    :cond_6
    :goto_3
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "onSingleTapUp: preview ready: "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p2, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p2}, Lcom/android/camera2/Camera2Proxy;->isPreviewReady()Z

    move-result p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string p2, " "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->isPreviewReady()Z

    move-result p0

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onSurfaceTexturePending(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)Z
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method protected pauseVideoRecording()V
    .locals 1

    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->pauseVideoRecording()V

    iget v0, p0, Lcom/android/camera/module/DualVideoModule;->mRecordPausedTimes:I

    add-int/lit8 v0, v0, 0x1

    iput v0, p0, Lcom/android/camera/module/DualVideoModule;->mRecordPausedTimes:I

    return-void
.end method

.method public resetEvValue()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->resetEvValue()V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setExposureCompensation(I)V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setAWBLock(Z)V

    invoke-static {}, Lcom/android/camera/CameraSettings;->resetExposure()V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->resumePreview()I

    return-void
.end method

.method protected resetFocusState(D)V
    .locals 2

    invoke-super {p0, p1, p2}, Lcom/android/camera/module/VideoBase;->resetFocusState(D)V

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "resetFocusState: isRecording="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Lcom/android/camera/module/VideoBase;->isRecording()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", isNeedCancelAutoFocus = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v1, :cond_0

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->isNeedCancelAutoFocus()Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 v1, 0x1

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "DualVideoModule"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/FocusManager2;->isNeedCancelAutoFocus()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/module/VideoBase;->isRecording()Z

    move-result v0

    if-nez v0, :cond_1

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/module/loader/camera2/FocusManager2;->onDeviceKeepMoving(D)V

    :cond_1
    return-void
.end method

.method protected setEvValue()V
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isAlive()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    iget-object v1, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v1

    const/4 v2, 0x0

    if-ne v0, v1, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setAWBLock(Z)V

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->setEvValue()V

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setAWBLock(Z)V

    iget v0, p0, Lcom/android/camera/module/BaseModule;->mEvState:I

    const/4 v1, 0x2

    if-ne v0, v1, :cond_2

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setAWBLock(Z)V

    goto :goto_0

    :cond_2
    const/4 v1, 0x1

    if-eq v0, v1, :cond_3

    const/4 v3, 0x3

    if-ne v0, v3, :cond_5

    :cond_3
    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v3, p0, Lcom/android/camera/module/BaseModule;->mEvValue:I

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setExposureCompensation(I)V

    iget v0, p0, Lcom/android/camera/module/BaseModule;->mEvState:I

    if-ne v0, v1, :cond_5

    iget v0, p0, Lcom/android/camera/module/BaseModule;->mEvValue:I

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v1}, Lcom/android/camera2/Camera2Proxy;->setAWBLock(Z)V

    goto :goto_0

    :cond_4
    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setAWBLock(Z)V

    :cond_5
    :goto_0
    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->resumePreview()I

    :goto_1
    return-void
.end method

.method public setFrameAvailable(Z)V
    .locals 0

    invoke-super {p0, p1}, Lcom/android/camera/module/VideoBase;->setFrameAvailable(Z)V

    iput-boolean p1, p0, Lcom/android/camera/module/DualVideoModule;->mMainFrameisAvailable:Z

    return-void
.end method

.method protected setOrientationParameter()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/VideoBase;->setOrientationParameter()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/N;

    invoke-direct {v1, p0}, Lcom/android/camera/module/N;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    invoke-virtual {v0, v1}, Ljava/util/Optional;->ifPresent(Ljava/util/function/Consumer;)V

    return-void
.end method

.method public startFocus()V
    .locals 3

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isDeviceAlive()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isFrameAvailable()Z

    move-result v0

    const-string v1, "DualVideoModule"

    if-nez v0, :cond_1

    const-string p0, "cancelFocus: frame not available"

    invoke-static {v1, p0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    iget v0, p0, Lcom/android/camera/module/DualVideoModule;->mCurrentTapId:I

    iget-object v2, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v2}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v2

    if-ne v0, v2, :cond_2

    invoke-super {p0}, Lcom/android/camera/module/VideoBase;->startFocus()V

    goto :goto_0

    :cond_2
    const-string v0, "startFocus: sub camera"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->getSensorStateManager()Lcom/android/camera/SensorStateManager;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/camera/SensorStateManager;->setFocusSensorEnabled(Z)V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object v2, p0, Lcom/android/camera/module/DualVideoModule;->mFocusCallback:Lcom/android/camera2/Camera2Proxy$FocusCallback;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setFocusCallback(Lcom/android/camera2/Camera2Proxy$FocusCallback;)V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-static {v1}, Lcom/android/camera/module/loader/camera2/FocusTask;->create(I)Lcom/android/camera/module/loader/camera2/FocusTask;

    move-result-object v1

    iget p0, p0, Lcom/android/camera/module/BaseModule;->mModuleIndex:I

    invoke-virtual {v0, v1, p0}, Lcom/android/camera2/Camera2Proxy;->startFocus(Lcom/android/camera/module/loader/camera2/FocusTask;I)V

    :goto_0
    return-void
.end method

.method protected startVideoPreviewSession()V
    .locals 10

    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->startVideoPreviewSession()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->initDualVideoControler()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->createSubCameraSurfaceIfNeed()Landroid/view/Surface;

    move-result-object v2

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->resetFrameReady()V

    iget-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    iget-object v3, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v3

    invoke-virtual {v0, v3}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->isFrontCameraId(I)Z

    move-result v0

    if-eqz v0, :cond_0

    const v0, 0x8009

    goto :goto_0

    :cond_0
    const v0, 0x8004

    :goto_0
    move v7, v0

    const/4 v8, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    move-object v9, p0

    invoke-virtual/range {v1 .. v9}, Lcom/android/camera2/Camera2Proxy;->startVideoPreviewSession(Landroid/view/Surface;IZZLandroid/view/Surface;IZLcom/android/camera2/Camera2Proxy$CameraPreviewCallback;)V

    return-void
.end method

.method protected startVideoRecording()V
    .locals 2

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/X;

    invoke-direct {v1, p0}, Lcom/android/camera/module/X;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    invoke-virtual {v0, v1}, Ljava/util/Optional;->ifPresent(Ljava/util/function/Consumer;)V

    return-void
.end method

.method public stopVideoRecording(Z)V
    .locals 5

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p1

    const/4 v0, 0x1

    if-eqz p1, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/DualVideoControler;->isRecording()Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->isMultiCameraMode()Z

    move-result p1

    if-eqz p1, :cond_0

    invoke-direct {p0, v0}, Lcom/android/camera/module/DualVideoModule;->switchThumbnailFunction(Z)V

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/DualVideoControler;->stopRecording()V

    :cond_1
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "stopVideoRecording>>"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/android/camera/module/BaseModule;->mMediaRecorderRecording:Z

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, "|"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "DualVideoModule"

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean p1, p0, Lcom/android/camera/module/BaseModule;->mMediaRecorderRecording:Z

    if-nez p1, :cond_2

    return-void

    :cond_2
    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/module/BaseModule;->mMediaRecorderRecording:Z

    iput-boolean p1, p0, Lcom/android/camera/module/VideoBase;->mMediaRecorderRecordingPaused:Z

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    iget-object v3, p0, Lcom/android/camera/module/BaseModule;->mMainProtocol:Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;

    if-eqz v3, :cond_3

    invoke-interface {v3}, Lcom/android/camera/protocol/ModeProtocol$MainContentProtocol;->processingFinish()V

    :cond_3
    invoke-virtual {p0, p1}, Lcom/android/camera/module/BaseModule;->enableCameraControls(Z)V

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object v3

    const/16 v4, 0xd4

    invoke-virtual {v3, v4}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object v3

    check-cast v3, Lcom/android/camera/protocol/ModeProtocol$RecordState;

    if-eqz v3, :cond_4

    invoke-interface {v3}, Lcom/android/camera/protocol/ModeProtocol$RecordState;->onFinish()V

    :cond_4
    invoke-virtual {p0}, Lcom/android/camera/module/DualVideoModule;->trackDualVideo()V

    iput p1, p0, Lcom/android/camera/module/DualVideoModule;->mRecordPausedTimes:I

    iput p1, p0, Lcom/android/camera/module/DualVideoModule;->mRecordCaptureTimes:I

    const-wide/16 v3, 0x0

    iput-wide v3, p0, Lcom/android/camera/module/VideoModule;->mVideoRecordTime:J

    invoke-virtual {p0}, Lcom/android/camera/module/VideoModule;->stopRecorder()V

    invoke-virtual {p0}, Lcom/android/camera/module/VideoModule;->handleTempVideoFile()V

    iget-object p1, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-static {p1}, Lcom/android/camera/AutoLockManager;->getInstance(Landroid/content/Context;)Lcom/android/camera/AutoLockManager;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/AutoLockManager;->hibernateDelayed()V

    invoke-virtual {p0}, Lcom/android/camera/module/VideoBase;->exitSavePowerMode()V

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "stopVideoRecording<<time="

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    sub-long/2addr v3, v1

    invoke-virtual {p1, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->switchPreviewTo6Patch()V

    return-void
.end method

.method public takeVideoSnapShoot()Z
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->takeVideoSnapShoot()Z

    move-result v0

    if-eqz v0, :cond_0

    iget v1, p0, Lcom/android/camera/module/DualVideoModule;->mRecordCaptureTimes:I

    add-int/lit8 v1, v1, 0x1

    iput v1, p0, Lcom/android/camera/module/DualVideoModule;->mRecordCaptureTimes:I

    :cond_0
    return v0
.end method

.method protected trackDualVideo()V
    .locals 9

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    if-eqz v0, :cond_3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->tj()Z

    move-result v0

    const-string v1, "value_preview_mini"

    const-string v2, "value_preview_equal"

    if-eqz v0, :cond_1

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->hasMiniComposeType()Z

    move-result v0

    if-eqz v0, :cond_0

    move-object v3, v1

    goto :goto_0

    :cond_0
    move-object v3, v2

    :goto_0
    iget-wide v0, p0, Lcom/android/camera/module/VideoModule;->mVideoRecordTime:J

    invoke-static {v0, v1}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v4

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getZoomGroupForTrack()Ljava/lang/String;

    move-result-object v5

    iget v7, p0, Lcom/android/camera/module/DualVideoModule;->mRecordPausedTimes:I

    iget v8, p0, Lcom/android/camera/module/DualVideoModule;->mRecordCaptureTimes:I

    const-string v6, "value_record_merged"

    invoke-static/range {v3 .. v8}, Lcom/android/camera/statistic/CameraStatUtils;->trackMultiCameraDualVideo(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V

    goto :goto_2

    :cond_1
    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/DualVideoControler;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/DualVideoControler;->hasMiniComposeType()Z

    move-result v0

    if-eqz v0, :cond_2

    goto :goto_1

    :cond_2
    move-object v1, v2

    :goto_1
    iget-wide v2, p0, Lcom/android/camera/module/VideoModule;->mVideoRecordTime:J

    invoke-static {v2, v3}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object p0

    invoke-static {v1, p0}, Lcom/android/camera/statistic/CameraStatUtils;->trackDualVideo(Ljava/lang/String;Ljava/lang/String;)V

    :cond_3
    :goto_2
    return-void
.end method

.method public unRegisterModulePersistProtocol()V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/CameraScreenNail;->setExternalFrameProcessor(Lcom/android/camera/SurfaceTextureScreenNail$ExternalFrameProcessor;)V

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->unRegisterModulePersistProtocol()V

    invoke-direct {p0}, Lcom/android/camera/module/DualVideoModule;->getControler()Ljava/util/Optional;

    move-result-object v0

    new-instance v1, Lcom/android/camera/module/P;

    invoke-direct {v1, p0}, Lcom/android/camera/module/P;-><init>(Lcom/android/camera/module/DualVideoModule;)V

    invoke-virtual {v0, v1}, Ljava/util/Optional;->ifPresent(Ljava/util/function/Consumer;)V

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/Camera;->getImplFactory()Lcom/android/camera/module/impl/ImplFactory;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/module/impl/ImplFactory;->detachModulePersistent()V

    return-void
.end method

.method protected unlockAEAF()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/module/VideoBase;->unlockAEAF()V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubFocusManager:Lcom/android/camera/module/loader/camera2/FocusManager2;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/FocusManager2;->setAeAwbLock(Z)V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0, v1}, Lcom/android/camera2/Camera2Proxy;->setAELock(Z)V

    return-void
.end method

.method protected updateAntiBanding(Ljava/lang/String;)V
    .locals 0

    invoke-super {p0, p1}, Lcom/android/camera/module/BaseModule;->updateAntiBanding(Ljava/lang/String;)V

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isDeviceAlive()Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget-object p0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->getCameraConfigs()Lcom/android/camera2/CameraConfigs;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera2/CameraConfigs;->getAntiBanding()I

    move-result p0

    invoke-virtual {p1, p0}, Lcom/android/camera2/Camera2Proxy;->setAntiBanding(I)V

    :cond_0
    return-void
.end method

.method protected updateBeauty()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/VideoBase;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/fragment/beauty/BeautyValues;

    invoke-direct {v0}, Lcom/android/camera/fragment/beauty/BeautyValues;-><init>()V

    iput-object v0, p0, Lcom/android/camera/module/VideoBase;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    :cond_0
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->isFrontCameraId(I)Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/camera/module/VideoBase;->mBeautyValues:Lcom/android/camera/fragment/beauty/BeautyValues;

    const/16 v1, 0x28

    iput v1, v0, Lcom/android/camera/fragment/beauty/BeautyValues;->mBeautySkinSmooth:I

    iput v1, v0, Lcom/android/camera/fragment/beauty/BeautyValues;->mBeautySlimFace:I

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0, v0}, Lcom/android/camera2/Camera2Proxy;->setBeautyValues(Lcom/android/camera/fragment/beauty/BeautyValues;)V

    :cond_1
    return-void
.end method

.method protected updateFpsRange()V
    .locals 2

    sget-boolean v0, Lb/c/a/c;->nh:Z

    if-eqz v0, :cond_0

    const/16 v0, 0x18

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-static {v1, v0}, Landroid/util/Range;->create(Ljava/lang/Comparable;Ljava/lang/Comparable;)Landroid/util/Range;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v1, v0}, Lcom/android/camera2/Camera2Proxy;->setFpsRange(Landroid/util/Range;)V

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0, v0}, Lcom/android/camera2/Camera2Proxy;->setFpsRange(Landroid/util/Range;)V

    goto :goto_0

    :cond_0
    invoke-super {p0}, Lcom/android/camera/module/VideoModule;->updateFpsRange()V

    :goto_0
    return-void
.end method

.method protected updateModuleRelated()V
    .locals 3

    invoke-super {p0}, Lcom/android/camera/module/BaseModule;->updateModuleRelated()V

    iget-object v0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    iget v1, p0, Lcom/android/camera/module/BaseModule;->mModuleIndex:I

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v2

    iget-object p0, p0, Lcom/android/camera/module/DualVideoModule;->mSubCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {p0}, Lcom/android/camera2/Camera2Proxy;->getId()I

    move-result p0

    invoke-virtual {v2, p0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->isFrontCameraId(I)Z

    move-result p0

    invoke-virtual {v0, v1, p0}, Lcom/android/camera2/Camera2Proxy;->setModuleParameter(II)V

    return-void
.end method

.method protected updateVideoStabilization()V
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->isDeviceAlive()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isMovieSolidOn()Z

    move-result v0

    const-string v1, "DualVideoModule"

    const/4 v2, 0x1

    const/4 v3, 0x0

    if-eqz v0, :cond_1

    const-string v0, "videoStabilization: EIS"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setEnableOIS(Z)V

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setEnableEIS(Z)V

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCameraCapabilities:Lcom/android/camera2/CameraCapabilities;

    invoke-virtual {v0}, Lcom/android/camera2/CameraCapabilities;->isEISPreviewSupported()Z

    move-result v0

    if-nez v0, :cond_2

    iget-object p0, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object p0

    invoke-virtual {p0, v2}, Lcom/android/camera/SurfaceTextureScreenNail;->setVideoStabilizationCropped(Z)V

    goto :goto_0

    :cond_1
    const-string v0, "videoStabilization: OIS"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v3}, Lcom/android/camera2/Camera2Proxy;->setEnableEIS(Z)V

    iget-object v0, p0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0, v2}, Lcom/android/camera2/Camera2Proxy;->setEnableOIS(Z)V

    iget-object p0, p0, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object p0

    invoke-virtual {p0, v3}, Lcom/android/camera/SurfaceTextureScreenNail;->setVideoStabilizationCropped(Z)V

    :cond_2
    :goto_0
    return-void
.end method
