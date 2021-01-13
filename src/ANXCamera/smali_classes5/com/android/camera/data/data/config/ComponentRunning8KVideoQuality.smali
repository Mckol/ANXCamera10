.class public Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;
.super Lcom/android/camera/data/data/ComponentData;
.source "ComponentRunning8KVideoQuality.java"


# instance fields
.field private mCacheFpsValues:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private mCacheValues:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation
.end field

.field private mCameraId:I

.field private mDefaultValue:I

.field private mIsSupportEis:Ljava/lang/Boolean;


# direct methods
.method public constructor <init>(Lcom/android/camera/data/data/runing/DataItemRunning;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/ComponentData;-><init>(Lcom/android/camera/data/data/DataItemBase;)V

    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCameraId:I

    const/16 p1, 0x1e

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mDefaultValue:I

    new-instance p1, Landroid/util/ArrayMap;

    invoke-direct {p1}, Landroid/util/ArrayMap;-><init>()V

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheValues:Ljava/util/Map;

    new-instance p1, Landroid/util/ArrayMap;

    invoke-direct {p1}, Landroid/util/ArrayMap;-><init>()V

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheFpsValues:Ljava/util/Map;

    return-void
.end method


# virtual methods
.method public clearArrayMap()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheValues:Ljava/util/Map;

    invoke-interface {v0}, Ljava/util/Map;->clear()V

    iget-object p0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheFpsValues:Ljava/util/Map;

    invoke-interface {p0}, Ljava/util/Map;->clear()V

    return-void
.end method

.method public get8KEisIsSupported(Lcom/android/camera2/CameraCapabilities;)Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mIsSupportEis:Ljava/lang/Boolean;

    if-nez v0, :cond_0

    const-string v0, "3001"

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    const/16 v1, 0x18

    invoke-static {v0, v1, p1}, Lcom/android/camera/CameraSettings;->isCurrentQualitySupportEis(IILcom/android/camera2/CameraCapabilities;)Z

    move-result p1

    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mIsSupportEis:Ljava/lang/Boolean;

    iget-object p0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mIsSupportEis:Ljava/lang/Boolean;

    invoke-virtual {p0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p0

    return p0

    :cond_0
    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p0

    return p0
.end method

.method public getDefaultValue(I)Ljava/lang/String;
    .locals 0
    .annotation build Landroidx/annotation/NonNull;
    .end annotation

    const/4 p0, 0x0

    return-object p0
.end method

.method public getDisplayTitleString()I
    .locals 0

    const p0, 0x7f1005df

    return p0
.end method

.method public getFps(I)I
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheFpsValues:Ljava/util/Map;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    iget p0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mDefaultValue:I

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    invoke-interface {v0, p1, p0}, Ljava/util/Map;->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    return p0
.end method

.method public getItems()Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/data/data/ComponentData;->mItems:Ljava/util/List;

    return-object p0
.end method

.method public getKey(I)Ljava/lang/String;
    .locals 2

    const/16 v0, 0xa2

    if-eq p1, v0, :cond_0

    const/16 v0, 0xb4

    if-eq p1, v0, :cond_0

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "pref_camera_video_8k_unsupported_"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCameraId:I

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "pref_camera_video_8k_"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, "_"

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCameraId:I

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public getQuality(I)Ljava/lang/String;
    .locals 0

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->getFps(I)I

    move-result p0

    const/16 p1, 0x18

    if-ne p0, p1, :cond_0

    const-string p0, "3001,24"

    return-object p0

    :cond_0
    const-string p0, "3001"

    return-object p0
.end method

.method public isEnabled(I)Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheValues:Ljava/util/Map;

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->getKey(I)Ljava/lang/String;

    move-result-object p0

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Boolean;

    if-nez p0, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p0

    :goto_0
    return p0
.end method

.method public reInit(IILcom/android/camera2/CameraCapabilities;)V
    .locals 4

    iput p2, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCameraId:I

    new-instance p2, Ljava/util/ArrayList;

    invoke-direct {p2}, Ljava/util/ArrayList;-><init>()V

    const/16 v0, 0xa2

    if-eq p1, v0, :cond_0

    const/16 v0, 0xb4

    if-eq p1, v0, :cond_0

    goto :goto_0

    :cond_0
    new-instance p1, Lcom/android/camera/data/data/ComponentDataItem;

    const v0, 0x7f0801f5

    const v1, 0x7f0801f6

    const v2, 0x7f1005df

    const-string v3, "on"

    invoke-direct {p1, v0, v1, v2, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p2, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-virtual {p3}, Lcom/android/camera2/CameraCapabilities;->get8KMaxFpsSupported()I

    move-result p1

    iput p1, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mDefaultValue:I

    :goto_0
    invoke-static {p2}, Ljava/util/Collections;->unmodifiableList(Ljava/util/List;)Ljava/util/List;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/data/data/ComponentData;->mItems:Ljava/util/List;

    return-void
.end method

.method public setEnabled(IZ)V
    .locals 3

    if-nez p2, :cond_0

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheFpsValues:Ljava/util/Map;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/16 v2, 0x1e

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_0
    iget v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mDefaultValue:I

    const/16 v1, 0x18

    if-ne v0, v1, :cond_1

    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheFpsValues:Ljava/util/Map;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    iget v2, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mDefaultValue:I

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_1
    :goto_0
    iget-object v0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheValues:Ljava/util/Map;

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->getKey(I)Ljava/lang/String;

    move-result-object p0

    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p1

    invoke-interface {v0, p0, p1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public setFps(II)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/data/data/config/ComponentRunning8KVideoQuality;->mCacheFpsValues:Ljava/util/Map;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    invoke-interface {p0, p1, p2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method
