.class public Lcom/android/camera/module/loader/FunctionParseAsdScene;
.super Ljava/lang/Object;
.source "FunctionParseAsdScene.java"

# interfaces
.implements Lio/reactivex/functions/Function;
.implements Lcom/android/camera/constant/AsdSceneConstant;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lio/reactivex/functions/Function<",
        "Landroid/hardware/camera2/CaptureResult;",
        "Ljava/lang/Integer;",
        ">;",
        "Lcom/android/camera/constant/AsdSceneConstant;"
    }
.end annotation


# static fields
.field private static final AEC_LUX_HEIGHT_LIGHT:F

.field private static final AEC_LUX_LAST_LIGHT:F

.field private static final AEC_LUX_LOW_LIGHT:F = 450.0f

.field private static final DEBUG:Z

.field private static final FRAME_BYPASS_NUMBER:I = 0x2

.field private static final LENS_FOCUS_DISTANCE_TOO_CLOSE:F = 2.5f

.field private static final LENS_FOCUS_DISTANCE_TOO_FAR:F = 0.5f

.field private static final TAG:Ljava/lang/String; = "FunctionParseAsdScene"

.field private static mFrameNumber:I

.field private static mIsFlashRetain:Z


# instance fields
.field private mModuleWeakReference:Ljava/lang/ref/WeakReference;
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
    .locals 2

    const-string v0, "FunctionParseAsdScene"

    const/4 v1, 0x3

    invoke-static {v0, v1}, Landroid/util/Log;->isLoggable(Ljava/lang/String;I)Z

    move-result v0

    sput-boolean v0, Lcom/android/camera/module/loader/FunctionParseAsdScene;->DEBUG:Z

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->ph()I

    move-result v0

    int-to-float v0, v0

    sput v0, Lcom/android/camera/module/loader/FunctionParseAsdScene;->AEC_LUX_LAST_LIGHT:F

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->oh()I

    move-result v0

    int-to-float v0, v0

    sput v0, Lcom/android/camera/module/loader/FunctionParseAsdScene;->AEC_LUX_HEIGHT_LIGHT:F

    return-void
.end method

.method public constructor <init>(Lcom/android/camera/module/BaseModule;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mModuleWeakReference:Ljava/lang/ref/WeakReference;

    return-void
.end method

.method private static getLowLightValue()F
    .locals 1

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isFunARModule()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->J()I

    move-result v0

    int-to-float v0, v0

    return v0

    :cond_0
    const/high16 v0, 0x43e10000    # 450.0f

    return v0
.end method


# virtual methods
.method public apply(Landroid/hardware/camera2/CaptureResult;)Ljava/lang/Integer;
    .locals 7

    iget-object v0, p0, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mModuleWeakReference:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/module/BaseModule;

    const/4 v1, -0x1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    if-eqz v0, :cond_5

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getCameraDevice()Lcom/android/camera2/Camera2Proxy;

    move-result-object v2

    sget-object v3, Landroid/hardware/camera2/CaptureResult;->CONTROL_AE_STATE:Landroid/hardware/camera2/CaptureResult$Key;

    invoke-virtual {p1, v3}, Landroid/hardware/camera2/CaptureResult;->get(Landroid/hardware/camera2/CaptureResult$Key;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    const/4 v4, 0x0

    if-eqz v2, :cond_0

    invoke-virtual {v2}, Lcom/android/camera2/Camera2Proxy;->getFlashMode()I

    move-result v5

    invoke-virtual {v2, v3, v5}, Lcom/android/camera2/Camera2Proxy;->isNeedFlashForAuto(Ljava/lang/Integer;I)Z

    move-result v2

    goto :goto_0

    :cond_0
    move v2, v4

    :goto_0
    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->isFrontCamera()Z

    move-result v3

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v5

    if-eqz v5, :cond_1

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->getActivity()Lcom/android/camera/Camera;

    move-result-object v5

    invoke-virtual {v5}, Lcom/android/camera/Camera;->isScreenSlideOff()Z

    move-result v5

    goto :goto_1

    :cond_1
    move v5, v4

    :goto_1
    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->isPortraitMode()Z

    move-result v6

    if-nez v6, :cond_4

    if-nez v3, :cond_4

    invoke-virtual {v0}, Lcom/android/camera/module/BaseModule;->isMimojiMode()Z

    move-result v6

    if-eqz v6, :cond_2

    goto :goto_2

    :cond_2
    if-eqz v2, :cond_5

    instance-of p0, v0, Lcom/android/camera2/Camera2Proxy$ASDSceneCallback;

    if-eqz p0, :cond_3

    check-cast v0, Lcom/android/camera2/Camera2Proxy$ASDSceneCallback;

    invoke-interface {v0}, Lcom/android/camera2/Camera2Proxy$ASDSceneCallback;->isAutoFlashOff()Z

    move-result p0

    if-eqz p0, :cond_3

    return-object v1

    :cond_3
    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    return-object p0

    :cond_4
    :goto_2
    invoke-virtual {p0, p1, v3, v5}, Lcom/android/camera/module/loader/FunctionParseAsdScene;->parseRtbSceneResult(Landroid/hardware/camera2/CaptureResult;ZZ)I

    move-result p0

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    return-object p0

    :cond_5
    return-object v1
.end method

.method public bridge synthetic apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    check-cast p1, Landroid/hardware/camera2/CaptureResult;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/loader/FunctionParseAsdScene;->apply(Landroid/hardware/camera2/CaptureResult;)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method

.method public parseRtbSceneResult(Landroid/hardware/camera2/CaptureResult;ZZ)I
    .locals 4

    invoke-static {p1}, Lcom/android/camera2/CaptureResultParser;->getAecLux(Landroid/hardware/camera2/CaptureResult;)F

    move-result p0

    const-string v0, "FunctionParseAsdScene"

    const/4 v1, 0x0

    const/4 v2, -0x1

    if-nez p2, :cond_5

    sput-boolean v1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mIsFlashRetain:Z

    invoke-static {}, Lcom/android/camera/module/loader/FunctionParseAsdScene;->getLowLightValue()F

    move-result p2

    sget-boolean p3, Lcom/android/camera/module/loader/FunctionParseAsdScene;->DEBUG:Z

    if-eqz p3, :cond_0

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "<back facing>aecLux:"

    invoke-virtual {p3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3, p0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, ",low_light_value:"

    invoke-virtual {p3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    invoke-static {v0, p3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    cmpl-float p0, p0, p2

    if-lez p0, :cond_1

    const/4 p0, 0x6

    return p0

    :cond_1
    sget-object p0, Landroid/hardware/camera2/CaptureResult;->LENS_FOCUS_DISTANCE:Landroid/hardware/camera2/CaptureResult$Key;

    invoke-virtual {p1, p0}, Landroid/hardware/camera2/CaptureResult;->get(Landroid/hardware/camera2/CaptureResult$Key;)Ljava/lang/Object;

    move-result-object p0

    if-eqz p0, :cond_4

    sget-object p0, Landroid/hardware/camera2/CaptureResult;->LENS_FOCUS_DISTANCE:Landroid/hardware/camera2/CaptureResult$Key;

    invoke-virtual {p1, p0}, Landroid/hardware/camera2/CaptureResult;->get(Landroid/hardware/camera2/CaptureResult$Key;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Float;

    invoke-virtual {p0}, Ljava/lang/Float;->floatValue()F

    move-result p0

    const/high16 p1, 0x40200000    # 2.5f

    cmpl-float p1, p0, p1

    if-ltz p1, :cond_2

    const/4 p0, 0x4

    return p0

    :cond_2
    const/high16 p1, 0x3f000000    # 0.5f

    cmpg-float p0, p0, p1

    if-gtz p0, :cond_3

    const/4 p0, 0x5

    return p0

    :cond_3
    const/4 p0, 0x7

    return p0

    :cond_4
    return v2

    :cond_5
    if-nez p3, :cond_b

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p1

    invoke-virtual {p1}, Lb/c/a/b;->Ri()Z

    move-result p1

    const/4 p2, 0x1

    if-eqz p1, :cond_6

    sget p1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mFrameNumber:I

    const/4 p3, 0x2

    if-ge p1, p3, :cond_6

    add-int/2addr p1, p2

    sput p1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mFrameNumber:I

    return v2

    :cond_6
    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isFunARModule()Z

    move-result p1

    if-eqz p1, :cond_7

    sget p1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->AEC_LUX_HEIGHT_LIGHT:F

    const p3, 0x3f5eb852    # 0.87f

    mul-float/2addr p1, p3

    goto :goto_0

    :cond_7
    sget p1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->AEC_LUX_HEIGHT_LIGHT:F

    :goto_0
    sget-boolean p3, Lcom/android/camera/module/loader/FunctionParseAsdScene;->DEBUG:Z

    if-eqz p3, :cond_8

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "<front facing>aecLux:"

    invoke-virtual {p3, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3, p0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v3, ",AEC_LUX_LAST_LIGHT:"

    invoke-virtual {p3, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v3, Lcom/android/camera/module/loader/FunctionParseAsdScene;->AEC_LUX_LAST_LIGHT:F

    invoke-virtual {p3, v3}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v3, ",,low_light_value:"

    invoke-virtual {p3, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v3, ",mIsFlashRetain:"

    invoke-virtual {p3, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-boolean v3, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mIsFlashRetain:Z

    invoke-virtual {p3, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    invoke-static {v0, p3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_8
    sget-boolean p3, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mIsFlashRetain:Z

    const/16 v0, 0x9

    if-eqz p3, :cond_9

    cmpl-float p1, p0, p1

    if-lez p1, :cond_9

    return v0

    :cond_9
    sget p1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->AEC_LUX_LAST_LIGHT:F

    cmpl-float p0, p0, p1

    if-lez p0, :cond_a

    sput-boolean p2, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mIsFlashRetain:Z

    return v0

    :cond_a
    sput-boolean v1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mIsFlashRetain:Z

    return v2

    :cond_b
    sput v1, Lcom/android/camera/module/loader/FunctionParseAsdScene;->mFrameNumber:I

    return v2
.end method
