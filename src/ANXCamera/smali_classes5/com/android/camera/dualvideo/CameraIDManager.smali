.class public Lcom/android/camera/dualvideo/CameraIDManager;
.super Ljava/lang/Object;
.source "CameraIDManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;
    }
.end annotation


# static fields
.field public static final MAIN_ID_TAG:I = 0x64

.field public static final SUB_ID_TAG:I = 0x65

.field private static final TAG:Ljava/lang/String; = "CameraIDManager"


# instance fields
.field private m6Patch2CameraId:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private mIdZoomList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    invoke-direct {p0}, Lcom/android/camera/dualvideo/CameraIDManager;->init6PatchCameraIds()V

    return-void
.end method

.method static synthetic a(Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;)Ljava/lang/Float;
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;->mZoom:F

    invoke-static {p0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p0

    return-object p0
.end method

.method static synthetic a(ILcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;)Z
    .locals 0

    iget p1, p1, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;->mId:I

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private getIdByZoom(Ljava/util/ArrayList;F)I
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;",
            ">;F)I"
        }
    .end annotation

    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p0

    const/4 p1, -0x1

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;

    iget v1, v0, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;->mZoom:F

    const v2, 0x3c23d70a    # 0.01f

    sub-float/2addr v1, v2

    cmpl-float v1, p2, v1

    if-lez v1, :cond_0

    iget p1, v0, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;->mId:I

    goto :goto_0

    :cond_0
    return p1
.end method

.method public static getZoomDescription(I)Ljava/lang/String;
    .locals 0

    packed-switch p0, :pswitch_data_0

    :pswitch_0
    const-string p0, "1X"

    return-object p0

    :pswitch_1
    const-string p0, "Front"

    return-object p0

    :pswitch_2
    const-string p0, "10X"

    return-object p0

    :pswitch_3
    const-string p0, "5X"

    return-object p0

    :pswitch_4
    const-string p0, "2X"

    return-object p0

    :pswitch_5
    const-string p0, "UW"

    return-object p0

    nop

    :pswitch_data_0
    .packed-switch 0x14
        :pswitch_5
        :pswitch_0
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
    .end packed-switch
.end method

.method private init6PatchCameraIds()V
    .locals 3

    invoke-direct {p0}, Lcom/android/camera/dualvideo/CameraIDManager;->initIdWithZoomList()V

    iget-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/android/camera/dualvideo/CameraIDManager;->mIdZoomList:Ljava/util/ArrayList;

    const v2, 0x3f19999a    # 0.6f

    invoke-direct {p0, v1, v2}, Lcom/android/camera/dualvideo/CameraIDManager;->getIdByZoom(Ljava/util/ArrayList;F)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/16 v2, 0x14

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/android/camera/dualvideo/CameraIDManager;->mIdZoomList:Ljava/util/ArrayList;

    const/high16 v2, 0x3f800000    # 1.0f

    invoke-direct {p0, v1, v2}, Lcom/android/camera/dualvideo/CameraIDManager;->getIdByZoom(Ljava/util/ArrayList;F)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/16 v2, 0x15

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/android/camera/dualvideo/CameraIDManager;->mIdZoomList:Ljava/util/ArrayList;

    const/high16 v2, 0x40000000    # 2.0f

    invoke-direct {p0, v1, v2}, Lcom/android/camera/dualvideo/CameraIDManager;->getIdByZoom(Ljava/util/ArrayList;F)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/16 v2, 0x16

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/android/camera/dualvideo/CameraIDManager;->mIdZoomList:Ljava/util/ArrayList;

    const/high16 v2, 0x40a00000    # 5.0f

    invoke-direct {p0, v1, v2}, Lcom/android/camera/dualvideo/CameraIDManager;->getIdByZoom(Ljava/util/ArrayList;F)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/16 v2, 0x17

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/android/camera/dualvideo/CameraIDManager;->mIdZoomList:Ljava/util/ArrayList;

    const/high16 v2, 0x41200000    # 10.0f

    invoke-direct {p0, v1, v2}, Lcom/android/camera/dualvideo/CameraIDManager;->getIdByZoom(Ljava/util/ArrayList;F)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/16 v2, 0x18

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getFrontCameraId()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/16 v2, 0x19

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "init6PatchCameraIds: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    invoke-virtual {p0}, Landroid/util/SparseArray;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "CameraIDManager"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private initIdWithZoomList()V
    .locals 5

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraWideCameraId()I

    move-result v1

    const/4 v2, -0x1

    if-eq v1, v2, :cond_0

    new-instance v3, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;

    const v4, 0x3f19999a    # 0.6f

    invoke-direct {v3, p0, v1, v4}, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;-><init>(Lcom/android/camera/dualvideo/CameraIDManager;IF)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_0
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result v1

    if-eq v1, v2, :cond_1

    new-instance v3, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;

    const/high16 v4, 0x3f800000    # 1.0f

    invoke-direct {v3, p0, v1, v4}, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;-><init>(Lcom/android/camera/dualvideo/CameraIDManager;IF)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_1
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getAuxCameraId()I

    move-result v1

    if-eq v1, v2, :cond_2

    new-instance v3, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;

    const/high16 v4, 0x40000000    # 2.0f

    invoke-direct {v3, p0, v1, v4}, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;-><init>(Lcom/android/camera/dualvideo/CameraIDManager;IF)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_2
    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getUltraTeleCameraId()I

    move-result v1

    if-eq v1, v2, :cond_3

    new-instance v2, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;

    const/high16 v3, 0x40a00000    # 5.0f

    invoke-direct {v2, p0, v1, v3}, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;-><init>(Lcom/android/camera/dualvideo/CameraIDManager;IF)V

    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_3
    iput-object v0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->mIdZoomList:Ljava/util/ArrayList;

    return-void
.end method


# virtual methods
.method public get6Patch2CameraId()Landroid/util/SparseArray;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Landroid/util/SparseArray<",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->m6Patch2CameraId:Landroid/util/SparseArray;

    return-object p0
.end method

.method public getZoomById(I)F
    .locals 1

    iget-object p0, p0, Lcom/android/camera/dualvideo/CameraIDManager;->mIdZoomList:Ljava/util/ArrayList;

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    new-instance v0, Lcom/android/camera/dualvideo/b;

    invoke-direct {v0, p1}, Lcom/android/camera/dualvideo/b;-><init>(I)V

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p0

    sget-object p1, Lcom/android/camera/dualvideo/a;->INSTANCE:Lcom/android/camera/dualvideo/a;

    invoke-virtual {p0, p1}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p0

    const/4 p1, 0x0

    invoke-static {p1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Float;

    invoke-virtual {p0}, Ljava/lang/Float;->floatValue()F

    move-result p0

    return p0
.end method
