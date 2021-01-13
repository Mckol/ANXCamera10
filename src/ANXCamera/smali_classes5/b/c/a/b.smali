.class public Lb/c/a/b;
.super Ljava/lang/Object;
.source "DataItemFeature.java"

# interfaces
.implements Lb/c/a/f;


# static fields
.field private static final Ag:Z = false

.field private static final PARALLEL_PERFORMANCE_SETTING:I = 0x0

.field private static final PARALLEL_QUALITY_SETTING:I = 0x1

.field private static final PARALLEL_QUEUE_SIZE:I = 0x2

.field private static final TAG:Ljava/lang/String; = "DataFeature"

.field private static final zg:Z


# instance fields
.field private mConfig:Lcom/mi/device/Common;

.field private yg:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    sget-boolean v0, Lcom/android/camera/Util;->DEBUG:Z

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    const-string v0, "camera.feature.clone"

    invoke-static {v0, v1}, Lcom/xiaomi/camera/util/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    sput-boolean v1, Lb/c/a/b;->zg:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lb/c/a/c;->Bg:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lb/c/a/c;->Bm()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lc/b;->aa(Ljava/lang/String;)Lcom/mi/device/Common;

    move-result-object v0

    iput-object v0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "DataItemFeature: init proxy = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "DataFeature"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic b(Lcom/android/camera2/CameraCapabilities;)Ljava/lang/Boolean;
    .locals 0

    invoke-virtual {p0}, Lcom/android/camera2/CameraCapabilities;->isMtkPipDevicesSupported()Z

    move-result p0

    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p0

    return-object p0
.end method

.method private c(Lcom/android/camera2/CameraCapabilities;)Z
    .locals 4

    const/4 v0, 0x0

    if-nez p1, :cond_0

    return v0

    :cond_0
    invoke-virtual {p0}, Lb/c/a/b;->Zh()Landroid/util/Size;

    move-result-object p0

    if-nez p0, :cond_1

    return v0

    :cond_1
    const/16 v1, 0x20

    const v2, 0x80f3

    invoke-virtual {p1, v1, v2}, Lcom/android/camera2/CameraCapabilities;->getSupportedOutputSizeWithAssignedMode(II)Ljava/util/List;

    move-result-object p1

    if-eqz p1, :cond_3

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v1

    if-lez v1, :cond_3

    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :cond_2
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_3

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/CameraSize;

    iget v2, v1, Lcom/android/camera/CameraSize;->width:I

    invoke-virtual {p0}, Landroid/util/Size;->getWidth()I

    move-result v3

    if-lt v2, v3, :cond_2

    iget v2, v1, Lcom/android/camera/CameraSize;->height:I

    invoke-virtual {p0}, Landroid/util/Size;->getHeight()I

    move-result v3

    if-lt v2, v3, :cond_2

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p1, "isSupportUltraPixelRaw size:"

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Lcom/android/camera/CameraSize;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "DataFeature"

    invoke-static {p1, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x1

    return p0

    :cond_3
    return v0
.end method

.method private ga(Ljava/lang/String;)I
    .locals 0

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x0

    invoke-virtual {p1, p0}, Ljava/lang/String;->charAt(I)C

    move-result p0

    invoke-static {p0}, Ljava/lang/Character;->isDigit(C)Z

    move-result p1

    if-eqz p1, :cond_0

    invoke-static {p0}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p0

    return p0

    :cond_0
    const/4 p0, -0x1

    return p0
.end method

.method private ha(Ljava/lang/String;)Landroid/util/Size;
    .locals 2

    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p0

    if-nez p0, :cond_0

    const/16 p0, 0x3a

    invoke-virtual {p1, p0}, Ljava/lang/String;->indexOf(I)I

    move-result p0

    const/4 v0, 0x1

    add-int/2addr p0, v0

    invoke-virtual {p1, p0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-nez p1, :cond_0

    const-string p1, " "

    const-string v1, ""

    invoke-virtual {p0, p1, v1}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p0

    const-string p1, "x"

    invoke-virtual {p0, p1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length p1, p0

    const/4 v1, 0x2

    if-lt p1, v1, :cond_0

    const/4 p1, 0x0

    aget-object p1, p0, p1

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    aget-object p0, p0, v0

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    new-instance v0, Landroid/util/Size;

    invoke-direct {v0, p1, p0}, Landroid/util/Size;-><init>(II)V

    return-object v0

    :cond_0
    const/4 p0, 0x0

    return-object p0
.end method

.method private t(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    invoke-virtual {p0}, Lb/c/a/b;->Xh()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    return-object p2

    :cond_0
    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, ";"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_1
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    invoke-virtual {v0, p1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_1

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, ":"

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    sget-object p1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {v0, p1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    const-string p2, ""

    invoke-virtual {p1, p0, p2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p2

    :cond_2
    return-object p2
.end method

.method private uo()I
    .locals 0
    .annotation build Lc/a;
        key = "c_28041_0x0004"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->uo()I

    move-result p0

    return p0
.end method

.method private wo()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_s_a_u_q"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->wo()Z

    move-result p0

    return p0
.end method


# virtual methods
.method public Ah()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_0x47"
        value = "v0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ah()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Ai()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x5d"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ai()Z

    move-result p0

    return p0
.end method

.method public Aj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0009"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Aj()Z

    move-result p0

    return p0
.end method

.method public Ak()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35893_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ak()Z

    move-result p0

    return p0
.end method

.method public Al()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_17042_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Al()Z

    move-result p0

    if-eqz p0, :cond_0

    invoke-static {}, Lb/c/a/c;->isMTKPlatform()Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public Bh()I
    .locals 1
    .annotation build Lc/a;
        key = "s_f_s_2_s_t"
        value = "-1"
    .end annotation

    sget-boolean v0, Lb/c/a/c;->hh:Z

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/android/camera/CameraSettings;->isHighQualityPreferred()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Bh()I

    move-result p0

    return p0
.end method

.method public Bi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x45"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Bi()Z

    move-result p0

    return p0
.end method

.method public Bj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_28196_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Bj()Z

    move-result p0

    return p0
.end method

.method public Bk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x35"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Bk()Z

    move-result p0

    return p0
.end method

.method public Bl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0017"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Bl()Z

    move-result p0

    return p0
.end method

.method public Ch()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_0x29"
        value = "4.5"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ch()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Ci()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x24"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ci()Z

    move-result p0

    return p0
.end method

.method public Cj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_2974_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Cj()Z

    move-result p0

    return p0
.end method

.method public Ck()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_22367_0x0000"
        value = "false"
    .end annotation

    invoke-virtual {p0}, Lb/c/a/b;->yk()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->xk()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ck()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public Cl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0013"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Cl()Z

    move-result p0

    return p0
.end method

.method public Dh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_19040_0x0005"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Dh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Di()Z
    .locals 2

    iget-object v0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    if-nez v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Lcom/xiaomi/camera/util/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    :cond_0
    iget-object v0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    const-string v1, "india"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    return v1

    :cond_1
    iget-object v0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_2

    iget-object p0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "india_"

    invoke-virtual {p0, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result p0

    if-eqz p0, :cond_2

    return v1

    :cond_2
    const/4 p0, 0x0

    return p0
.end method

.method public Dj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27810_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Dj()Z

    move-result p0

    return p0
.end method

.method public Dk()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_0x41"
        value = "true"
    .end annotation

    invoke-static {}, Lb/c/a/c;->Om()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Dk()Z

    move-result p0

    return p0
.end method

.method public Dl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0007"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Dl()Z

    move-result p0

    return p0
.end method

.method public Eh()I
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->Dh()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lb/c/a/b;->ga(Ljava/lang/String;)I

    move-result p0

    return p0
.end method

.method public Ei()Z
    .locals 1

    invoke-static {}, Lmiui/os/Build;->getRegion()Ljava/lang/String;

    move-result-object p0

    const-string v0, "IN"

    invoke-virtual {p0, v0}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method public Ej()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x37"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ej()Z

    move-result p0

    if-eqz p0, :cond_0

    sget-boolean p0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public Ek()Z
    .locals 2
    .annotation build Lc/a;
        key = "s_s_n"
        value = "false"
    .end annotation

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1c

    if-lt v0, v1, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ek()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public El()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x000a"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->El()Z

    move-result p0

    return p0
.end method

.method public Fh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "h_d_v"
        value = "off"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Fh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Fi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x04_i_l_h_d"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Fi()Z

    move-result p0

    return p0
.end method

.method public Fj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x14"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Fj()Z

    move-result p0

    return p0
.end method

.method public Fk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_33066_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Fk()Z

    move-result p0

    return p0
.end method

.method public Fl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0012"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Fl()Z

    move-result p0

    return p0
.end method

.method public Gh()F
    .locals 0
    .annotation build Lc/a;
        key = "c_35905_0x0002"
        value = "12.0f"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Gh()F

    move-result p0

    return p0
.end method

.method public Gi()Z
    .locals 0
    .annotation build Lc/a;
        key = "i_l_m_d"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Gi()Z

    move-result p0

    return p0
.end method

.method public Gj()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_35893_0x0003"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->isNormalIntent()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Gj()Z

    move-result p0

    return p0

    :cond_1
    :goto_0
    const/4 p0, 0x0

    return p0
.end method

.method public Gk()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_v_f_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Gk()Z

    move-result p0

    return p0
.end method

.method public Gl()Z
    .locals 1
    .annotation build Lc/a;
        key = "s_i_a"
        value = "false"
    .end annotation

    iget-object v0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {v0}, Lcom/mi/device/Common;->Gl()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->Di()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public H()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_16001_0x0001"
        value = "false"
    .end annotation

    invoke-static {}, Lb/c/a/c;->_m()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lb/c/a/c;->an()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->H()Z

    move-result p0

    return p0

    :cond_1
    :goto_0
    const/4 p0, 0x0

    return p0
.end method

.method public Hh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_16818_0x0001"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Hh()I

    move-result p0

    return p0
.end method

.method public Hi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_190920"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Hi()Z

    move-result p0

    return p0
.end method

.method public Hj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_36658_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Hj()Z

    move-result p0

    return p0
.end method

.method public Hk()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->_g:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lb/c/a/c;->uh:Z

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0}, Lb/c/a/b;->ml()Ljava/lang/String;

    move-result-object p0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, ":"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    const-string v0, "ULTRA_WIDE"

    invoke-interface {p0, v0}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method public Hl()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_0x0c"
        value = "false"
    .end annotation

    iget-object v0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {v0}, Lcom/mi/device/Common;->Hl()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->Di()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public I()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_16001_0x0002"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->I()Z

    move-result p0

    return p0
.end method

.method public Ih()I
    .locals 0
    .annotation build Lc/a;
        key = "c_35893_0x0004"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ih()I

    move-result p0

    return p0
.end method

.method public Ii()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_m_c_t_f"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ii()Z

    move-result p0

    return p0
.end method

.method public Ij()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x08"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ij()Z

    move-result p0

    return p0
.end method

.method public Ik()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x11"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ik()Z

    move-result p0

    return p0
.end method

.method public Il()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Il()Z

    move-result p0

    return p0
.end method

.method public Jh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_27810_0x0004"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Jh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Ji()Z
    .locals 0
    .annotation build Lc/a;
        key = "e_m_a_u_q_s"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ji()Z

    move-result p0

    return p0
.end method

.method public Jj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x09"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Jj()Z

    move-result p0

    return p0
.end method

.method public Jk()Z
    .locals 0
    .annotation build Lc/a;
        key = "i_v_b_c_f_d"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Jk()Z

    move-result p0

    return p0
.end method

.method public Jl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x48"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Jl()Z

    move-result p0

    return p0
.end method

.method public K(Z)Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x43"
        value = "false"
    .end annotation

    if-eqz p1, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->oo()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public Kh()F
    .locals 0
    .annotation build Lc/a;
        key = "c_0x02_p_c_r_v"
        value = "0.8766"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Kh()F

    move-result p0

    return p0
.end method

.method public Ki()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_m_l"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ki()Z

    move-result p0

    return p0
.end method

.method public Kj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x03"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Kj()Z

    move-result p0

    return p0
.end method

.method public Kk()Ljava/lang/Boolean;
    .locals 0
    .annotation build Lc/a;
        key = "c_35893_0x0009"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Kk()Z

    move-result p0

    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p0

    return-object p0
.end method

.method public Kl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_28196_0x0003"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Kl()Z

    move-result p0

    return p0
.end method

.method public L(Z)Ljava/lang/String;
    .locals 0

    if-eqz p1, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->Ch()Ljava/lang/String;

    move-result-object p0

    return-object p0

    :cond_0
    invoke-virtual {p0}, Lb/c/a/b;->th()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Lh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_0x01_p_g_a_v"
        value = "280"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Lh()I

    move-result p0

    return p0
.end method

.method public Li()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27810_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Li()Z

    move-result p0

    return p0
.end method

.method public Lj()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_0x00_s_l_s"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Lj()Z

    move-result p0

    return p0
.end method

.method public Lk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_36211_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Lk()Z

    move-result p0

    return p0
.end method

.method public Ll()Z
    .locals 6

    invoke-virtual {p0}, Lb/c/a/b;->kh()Ljava/lang/String;

    move-result-object p0

    const/4 v0, 0x0

    if-nez p0, :cond_0

    return v0

    :cond_0
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "mfnr"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_1

    return v0

    :cond_1
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v1, ";"

    invoke-virtual {p0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v1, p0

    if-lez v1, :cond_3

    array-length v1, p0

    move v3, v0

    :goto_0
    if-ge v3, v1, :cond_3

    aget-object v4, p0, v3

    sget-object v5, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {v4, v5}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v4

    const-string v5, ":"

    invoke-virtual {v4, v5}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v4

    aget-object v5, v4, v0

    invoke-virtual {v5, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_2

    const/4 p0, 0x1

    aget-object p0, v4, p0

    const-string v0, "1"

    invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    return p0

    :cond_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_3
    return v0
.end method

.method public M(Z)Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_36211_0x0005"
        value = "4x3"
    .end annotation

    if-eqz p1, :cond_0

    const-string p0, "4x3"

    return-object p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->po()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Mh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_0x5a"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Mh()I

    move-result p0

    return p0
.end method

.method public Mi()Z
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->ml()Ljava/lang/String;

    move-result-object p0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, ":"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    const-string v0, "NO_PIXEL"

    invoke-interface {p0, v0}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method public Mj()Z
    .locals 2

    invoke-virtual {p0}, Lb/c/a/b;->H()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lb/c/a/b;->uo()I

    move-result p0

    if-ne p0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method public Mk()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "live_activation_id"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Mk()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Ml()Z
    .locals 6

    invoke-virtual {p0}, Lb/c/a/b;->kh()Ljava/lang/String;

    move-result-object p0

    const/4 v0, 0x0

    if-nez p0, :cond_0

    return v0

    :cond_0
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "notelemfnr"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_1

    return v0

    :cond_1
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v1, ";"

    invoke-virtual {p0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v1, p0

    if-lez v1, :cond_3

    array-length v1, p0

    move v3, v0

    :goto_0
    if-ge v3, v1, :cond_3

    aget-object v4, p0, v3

    sget-object v5, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {v4, v5}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v4

    const-string v5, ":"

    invoke-virtual {v4, v5}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v4

    aget-object v5, v4, v0

    invoke-virtual {v5, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_2

    const/4 p0, 0x1

    aget-object p0, v4, p0

    const-string v0, "1"

    invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    return p0

    :cond_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_3
    return v0
.end method

.method public Nh()Ljava/util/Map;
    .locals 13
    .annotation build Lc/a;
        key = "c_parallel_set"
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Nh()Ljava/lang/String;

    move-result-object p0

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-interface {v0, v2, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const/4 v3, 0x1

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-interface {v0, v4, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const/4 v5, 0x2

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/4 v6, 0x3

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-interface {v0, v5, v6}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v6

    if-nez v6, :cond_0

    return-object v0

    :cond_0
    sget-object v6, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v6}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v6, ";"

    invoke-virtual {p0, v6}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v6, p0

    move v7, v1

    :goto_0
    if-ge v7, v6, :cond_2

    aget-object v8, p0, v7

    sget-object v9, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {v8, v9}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v8

    const-string v9, ","

    invoke-virtual {v8, v9}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v8

    sget-wide v9, Lcom/android/camera/Util;->TOTAL_MEMORY:J

    aget-object v11, v8, v1

    invoke-static {v11}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v11

    cmp-long v9, v9, v11

    if-gez v9, :cond_1

    aget-object p0, v8, v3

    sget-object v6, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v6}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v6, "!"

    invoke-virtual {p0, v6}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    aget-object v6, p0, v1

    invoke-static {v6}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v6

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-interface {v0, v2, v6}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    aget-object p0, p0, v3

    sget-object v2, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v2, ":"

    invoke-virtual {p0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    aget-object v1, p0, v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-interface {v0, v4, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    aget-object p0, p0, v3

    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p0

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    invoke-interface {v0, v5, p0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_1

    :cond_1
    add-int/lit8 v7, v7, 0x1

    goto :goto_0

    :cond_2
    :goto_1
    return-object v0
.end method

.method public Ni()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->_g:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lb/c/a/c;->uh:Z

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0}, Lb/c/a/b;->ml()Ljava/lang/String;

    move-result-object p0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, ":"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    const-string v0, "PRO"

    invoke-interface {p0, v0}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method public Nj()Z
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->H()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lb/c/a/b;->uo()I

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public Nk()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "live_activation_license"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Nk()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Nl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0000"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Nl()Z

    move-result p0

    return p0
.end method

.method public Oh()I
    .locals 0
    .annotation build Lc/a;
        key = "s_p_r_n"
        value = "-1"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Oh()I

    move-result p0

    return p0
.end method

.method public Oi()Z
    .locals 0
    .annotation build Lc/a;
        key = "i_q_a_u_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Oi()Z

    move-result p0

    return p0
.end method

.method public Oj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_36211_0x0004"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Oj()Z

    move-result p0

    return p0
.end method

.method public Ok()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "module_name_pixel"
        value = "48M"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ok()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Ol()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0008"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ol()Z

    move-result p0

    return p0
.end method

.method public Ph()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0004"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ph()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Pi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x5c"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Pi()Z

    move-result p0

    return p0
.end method

.method public Pj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_006314_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Pj()Z

    move-result p0

    return p0
.end method

.method public Pk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_28196_0x0004"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Pk()Z

    move-result p0

    return p0
.end method

.method public Pl()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_0x1a"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Pl()Z

    move-result p0

    return p0
.end method

.method public Qh()I
    .locals 1

    invoke-static {}, Lb/c/a/c;->Pm()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, -0x1

    return p0

    :cond_0
    invoke-static {}, Lb/c/a/c;->Vm()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p0, 0x1

    return p0

    :cond_1
    sget-boolean v0, Lb/c/a/c;->mh:Z

    if-eqz v0, :cond_2

    invoke-static {}, Lb/c/a/c;->isGlobal()Z

    move-result v0

    if-eqz v0, :cond_2

    const/4 p0, 0x2

    return p0

    :cond_2
    invoke-virtual {p0}, Lb/c/a/b;->Ph()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lb/c/a/b;->ga(Ljava/lang/String;)I

    move-result p0

    return p0
.end method

.method public Qi()Z
    .locals 0
    .annotation build Lc/a;
        key = "rethrow_vendor_tag_not_found_exception"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Qi()Z

    move-result p0

    return p0
.end method

.method public Qj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0007"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Qj()Z

    move-result p0

    return p0
.end method

.method public Qk()Z
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->Di()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->Hj()Z

    move-result v0

    if-nez v0, :cond_1

    :cond_0
    invoke-virtual {p0}, Lb/c/a/b;->ij()Z

    move-result p0

    if-eqz p0, :cond_2

    :cond_1
    const/4 p0, 0x1

    goto :goto_0

    :cond_2
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public Ql()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27845_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ql()Z

    move-result p0

    return p0
.end method

.method public Rh()I
    .locals 1
    .annotation build Lc/a;
        key = "c_9006_0x0001"
        value = "5"
    .end annotation

    invoke-static {}, Lb/c/a/c;->mn()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x6

    return p0

    :cond_0
    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->hl()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p0, 0x5

    return p0

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->isHighQualityPreferred()Z

    move-result v0

    if-nez v0, :cond_2

    invoke-virtual {p0}, Lb/c/a/b;->Sh()I

    move-result p0

    return p0

    :cond_2
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Rh()I

    move-result p0

    return p0
.end method

.method public Ri()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_s_s"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ri()Z

    move-result p0

    return p0
.end method

.method public Rj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_36211_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Rj()Z

    move-result p0

    return p0
.end method

.method public Rk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x42_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Rk()Z

    move-result p0

    return p0
.end method

.method public Rl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_o_a_w"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Rl()Z

    move-result p0

    return p0
.end method

.method public Sh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_pm_9006_0x0001"
        value = "6"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Sh()I

    move-result p0

    return p0
.end method

.method public Si()Z
    .locals 0
    .annotation build Lc/a;
        key = "i_s_e_r"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Si()Z

    move-result p0

    return p0
.end method

.method public Sj()Z
    .locals 3
    .annotation build Lc/a;
        key = "c_33066_0x0004"
        value = "false"
    .end annotation

    const/4 v0, 0x0

    const-string v1, "miuicamera.mimoji.show"

    invoke-static {v1, v0}, Lcom/xiaomi/camera/util/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    const/4 v2, 0x1

    if-eqz v1, :cond_0

    return v2

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Sj()Z

    move-result p0

    if-eqz p0, :cond_1

    sget-boolean p0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-nez p0, :cond_1

    move v0, v2

    :cond_1
    return v0
.end method

.method public Sk()Z
    .locals 2

    invoke-virtual {p0}, Lb/c/a/b;->rm()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->vm()Z

    move-result p0

    xor-int/lit8 p0, p0, 0x1

    return p0

    :cond_0
    invoke-static {v0}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method public Sl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x4a_1"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Sl()Z

    move-result p0

    return p0
.end method

.method public Th()I
    .locals 0
    .annotation build Lc/a;
        key = "s_b_m_s_c"
        value = "-1"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Th()I

    move-result p0

    return p0
.end method

.method public Ti()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_s_v"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ti()Z

    move-result p0

    return p0
.end method

.method public Tj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x54"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Tj()Z

    move-result p0

    return p0
.end method

.method public Tk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x0e"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->cm()Z

    move-result p0

    return p0
.end method

.method public Tl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_p_a"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Tl()Z

    move-result p0

    return p0
.end method

.method public Uh()J
    .locals 2
    .annotation build Lc/a;
        key = "s_b_m_d_t"
        value = "-1"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Uh()J

    move-result-wide v0

    return-wide v0
.end method

.method public Ui()Z
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->ml()Ljava/lang/String;

    move-result-object p0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, ":"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    const-string v0, "MACRO"

    invoke-interface {p0, v0}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method public Uj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35910_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Uj()Z

    move-result p0

    return p0
.end method

.method public Uk()I
    .locals 0
    .annotation build Lc/a;
        key = "parallel_maxAcquireCount"
        value = "30"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Uk()I

    move-result p0

    return p0
.end method

.method public Ul()Z
    .locals 1
    .annotation build Lc/a;
        key = "s_p_l_i_e"
        value = "false"
    .end annotation

    invoke-virtual {p0}, Lb/c/a/b;->Di()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->Ei()Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Ul()Z

    move-result p0

    if-eqz p0, :cond_1

    const/4 p0, 0x1

    goto :goto_0

    :cond_1
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public Vh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_32889_0x0002"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Vh()I

    move-result p0

    return p0
.end method

.method public Vi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27810_0x0007"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Vi()Z

    move-result p0

    return p0
.end method

.method public Vj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35910_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Vj()Z

    move-result p0

    return p0
.end method

.method public Vk()I
    .locals 0
    .annotation build Lc/a;
        key = "parallel_maxAcquireCount_8G"
        value = "35"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Vk()I

    move-result p0

    return p0
.end method

.method public Vl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_p_l_b"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Vl()Z

    move-result p0

    return p0
.end method

.method public Wh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_32889_0x0004"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Wh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Wi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35893_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Wi()Z

    move-result p0

    return p0
.end method

.method public Wj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27845_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Wj()Z

    move-result p0

    return p0
.end method

.method public Wk()I
    .locals 0
    .annotation build Lc/a;
        key = "parallel_maxDequeueCount"
        value = "4"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Wk()I

    move-result p0

    return p0
.end method

.method public Wl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_p_l_f"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Wl()Z

    move-result p0

    return p0
.end method

.method public X(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .annotation build Lc/a;
        key = "c_22756_0x0001"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->qo()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-object p1

    :cond_0
    return-object p0
.end method

.method public Xh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x000c"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Xh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public Xi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0006"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Xi()Z

    move-result p0

    return p0
.end method

.method public Xj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x58"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Xj()Z

    move-result p0

    return p0
.end method

.method public Xk()I
    .locals 0
    .annotation build Lc/a;
        key = "parallel_maxDequeueCount_8G"
        value = "4"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Xk()I

    move-result p0

    return p0
.end method

.method public Xl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0015"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Xl()Z

    move-result p0

    return p0
.end method

.method public Y(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .annotation build Lc/a;
        key = "c_22367_0x000b"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ro()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-object p1

    :cond_0
    return-object p0
.end method

.method public Yh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_t_r"
        value = "20"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Yh()I

    move-result p0

    return p0
.end method

.method public Yi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x27"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Yi()Z

    move-result p0

    return p0
.end method

.method public Yj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35893_0x0008"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Yj()Z

    move-result p0

    return p0
.end method

.method public Yk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_28041_0x0007"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Yk()Z

    move-result p0

    return p0
.end method

.method public Yl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x5e"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Yl()Z

    move-result p0

    return p0
.end method

.method public Zh()Landroid/util/Size;
    .locals 2

    invoke-static {}, Lb/c/a/c;->Vm()Z

    move-result v0

    if-eqz v0, :cond_0

    new-instance p0, Landroid/util/Size;

    const/16 v0, 0x1f40

    const/16 v1, 0x1770

    invoke-direct {p0, v0, v1}, Landroid/util/Size;-><init>(II)V

    return-object p0

    :cond_0
    sget-boolean v0, Lb/c/a/c;->mh:Z

    if-eqz v0, :cond_1

    invoke-static {}, Lb/c/a/c;->isGlobal()Z

    move-result v0

    if-eqz v0, :cond_1

    new-instance p0, Landroid/util/Size;

    const/16 v0, 0x2420

    const/16 v1, 0x1b20

    invoke-direct {p0, v0, v1}, Landroid/util/Size;-><init>(II)V

    return-object p0

    :cond_1
    invoke-virtual {p0}, Lb/c/a/b;->Ph()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lb/c/a/b;->ha(Ljava/lang/String;)Landroid/util/Size;

    move-result-object p0

    return-object p0
.end method

.method public Zi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0016"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Zi()Z

    move-result p0

    return p0
.end method

.method public Zj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x10"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Zj()Z

    move-result p0

    return p0
.end method

.method public Zk()I
    .locals 0
    .annotation build Lc/a;
        key = "preferred_image_reprocessor_type"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Zk()I

    move-result p0

    return p0
.end method

.method public Zl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_28041_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->Zl()Z

    move-result p0

    return p0
.end method

.method public _h()I
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x000a"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->_h()I

    move-result p0

    return p0
.end method

.method public _i()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0010"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->_i()Z

    move-result p0

    return p0
.end method

.method public _j()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0008"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->_j()Z

    move-result p0

    return p0
.end method

.method public _k()I
    .locals 0
    .annotation build Lc/a;
        key = "c_0x0b"
        value = "180"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->_k()I

    move-result p0

    return p0
.end method

.method public _l()Z
    .locals 2
    .annotation build Lc/a;
        key = "c_17137_0x0001"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getIntentType()I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    invoke-static {}, Lb/c/a/c;->isMTKPlatform()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->_l()Z

    move-result p0

    return p0
.end method

.method public a(ZZLjava/lang/String;)Ljava/util/List;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(ZZ",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/Float;",
            ">;"
        }
    .end annotation

    const-string v0, "capture_inner"

    invoke-direct {p0, v0, p3}, Lb/c/a/b;->t(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p3

    if-eqz p2, :cond_0

    const-string p2, "video_inner"

    invoke-direct {p0, p2, p3}, Lb/c/a/b;->t(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p3

    :cond_0
    if-eqz p1, :cond_1

    const-string p3, "5:20:30:40:50:60"

    :cond_1
    const-string p0, ":"

    invoke-virtual {p3, p0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    const/4 p2, 0x0

    :goto_0
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result p3

    if-ge p2, p3, :cond_2

    invoke-interface {p0, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Ljava/lang/String;

    invoke-static {p3}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result p3

    invoke-static {p3}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p3

    invoke-interface {p1, p3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    add-int/lit8 p2, p2, 0x1

    goto :goto_0

    :cond_2
    return-object p1
.end method

.method public a(Lcom/android/camera2/CameraCapabilities;)Z
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->lh()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0, p1}, Lb/c/a/b;->c(Lcom/android/camera2/CameraCapabilities;)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public ai()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0006"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ai()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public aj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x31"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->aj()Z

    move-result p0

    return p0
.end method

.method public ak()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x000d"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ak()Z

    move-result p0

    return p0
.end method

.method public al()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_28041_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->al()Z

    move-result p0

    return p0
.end method

.method public am()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0019"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->am()Z

    move-result p0

    return p0
.end method

.method public b(ZZLjava/lang/String;)Ljava/util/List;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(ZZ",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation

    const-string v0, "capture_ruler"

    invoke-direct {p0, v0, p3}, Lb/c/a/b;->t(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p3

    if-eqz p2, :cond_0

    const-string p2, "video_ruler"

    invoke-direct {p0, p2, p3}, Lb/c/a/b;->t(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p3

    :cond_0
    if-eqz p1, :cond_1

    const-string p3, "3:10:10:10:10"

    :cond_1
    const-string p0, ":"

    invoke-virtual {p3, p0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    const/4 p2, 0x0

    :goto_0
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result p3

    if-ge p2, p3, :cond_2

    invoke-interface {p0, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Ljava/lang/String;

    invoke-static {p3}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p3

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p3

    invoke-interface {p1, p3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    add-int/lit8 p2, p2, 0x1

    goto :goto_0

    :cond_2
    return-object p1
.end method

.method public bh()Z
    .locals 0
    .annotation build Lc/a;
        key = "aftersales_enabled"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->bh()Z

    move-result p0

    return p0
.end method

.method public bi()Ljava/lang/String;
    .locals 1
    .annotation build Lc/a;
        key = "c_35910_0x0004"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->bi()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    :cond_0
    return-object p0
.end method

.method public bj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x59"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->bj()Z

    move-result p0

    return p0
.end method

.method public bk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27810_0x0006"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->bk()Z

    move-result p0

    return p0
.end method

.method public bl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x33"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->bl()Z

    move-result p0

    return p0
.end method

.method public bm()Z
    .locals 6

    invoke-virtual {p0}, Lb/c/a/b;->kh()Ljava/lang/String;

    move-result-object p0

    const/4 v0, 0x0

    if-nez p0, :cond_0

    return v0

    :cond_0
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "telesr"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_1

    return v0

    :cond_1
    sget-object v1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v1, ";"

    invoke-virtual {p0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v1, p0

    if-lez v1, :cond_3

    array-length v1, p0

    move v3, v0

    :goto_0
    if-ge v3, v1, :cond_3

    aget-object v4, p0, v3

    sget-object v5, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {v4, v5}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v4

    const-string v5, ":"

    invoke-virtual {v4, v5}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v4

    aget-object v5, v4, v0

    invoke-virtual {v5, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_2

    const/4 p0, 0x1

    aget-object p0, v4, p0

    const-string v0, "1"

    invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    return p0

    :cond_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_3
    return v0
.end method

.method public ci()I
    .locals 1
    .annotation build Lc/a;
        key = "c_35955_0x0002"
        value = "1"
    .end annotation

    invoke-virtual {p0}, Lb/c/a/b;->il()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lb/c/a/b;->li()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    invoke-virtual {p0}, Lb/c/a/b;->Di()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p0, 0x2

    return p0

    :cond_1
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ci()I

    move-result p0

    return p0
.end method

.method public cj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35893_0x0006"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->cj()Z

    move-result p0

    return p0
.end method

.method public ck()Z
    .locals 0
    .annotation build Lc/a;
        key = "i_s_q_c"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ck()Z

    move-result p0

    return p0
.end method

.method public cl()Z
    .locals 4
    .annotation build Lc/a;
        key = "s_18_7_5_9_s"
        value = "false"
    .end annotation

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x40055555

    sub-float/2addr v0, v1

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    float-to-double v0, v0

    const-wide v2, 0x3f947ae147ae147bL    # 0.02

    cmpg-double v0, v0, v2

    if-gez v0, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->cl()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public cm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x1c"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->cm()Z

    move-result p0

    return p0
.end method

.method public dh()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_28041_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->dh()Z

    move-result p0

    return p0
.end method

.method public di()I
    .locals 0
    .annotation build Lc/a;
        key = "c_0x57"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->di()I

    move-result p0

    return p0
.end method

.method public dj()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_b_a"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->dj()Z

    move-result p0

    return p0
.end method

.method public dk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0009"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->dk()Z

    move-result p0

    return p0
.end method

.method public dl()Z
    .locals 2
    .annotation build Lc/a;
        key = "s_f_s"
        value = "false"
    .end annotation

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x400aaaab

    cmpl-float v0, v0, v1

    if-ltz v0, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->dl()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public dm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0020"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->dm()Z

    move-result p0

    return p0
.end method

.method public eh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "pref_camera_antibanding_default"
        value = "1"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->eh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public ei()I
    .locals 0
    .annotation build Lc/a;
        key = "c_26813_0x0002"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ei()I

    move-result p0

    return p0
.end method

.method public ej()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_a"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ej()Z

    move-result p0

    return p0
.end method

.method public ek()Z
    .locals 0
    .annotation build Lc/a;
        key = "i_s_s_b"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ek()Z

    move-result p0

    return p0
.end method

.method public el()Z
    .locals 4
    .annotation build Lc/a;
        key = "s_19_9_s"
        value = "false"
    .end annotation

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x40071c72

    sub-float/2addr v0, v1

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    float-to-double v0, v0

    const-wide v2, 0x3f947ae147ae147bL    # 0.02

    cmpg-double v0, v0, v2

    if-gtz v0, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->el()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public em()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0018"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->em()Z

    move-result p0

    return p0
.end method

.method public fh()Z
    .locals 0
    .annotation build Lc/a;
        key = "pref_device_watermark_default"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->fh()Z

    move-result p0

    return p0
.end method

.method public fi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x18"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->fi()Z

    move-result p0

    return p0
.end method

.method public fj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x17"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->fj()Z

    move-result p0

    return p0
.end method

.method public fk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->fk()Z

    move-result p0

    return p0
.end method

.method public fl()Z
    .locals 2
    .annotation build Lc/a;
        key = "s_20_9_s"
        value = "false"
    .end annotation

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const v1, 0x400e38e4

    cmpl-float v0, v0, v1

    if-ltz v0, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->fl()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public fm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x50"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->fm()Z

    move-result p0

    return p0
.end method

.method public getConfig()Lcom/mi/device/Common;
    .locals 0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    return-object p0
.end method

.method public gh()Z
    .locals 0
    .annotation build Lc/a;
        key = "pref_lens_dirty_tip_default"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->gh()Z

    move-result p0

    return p0
.end method

.method public gi()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_0x0f"
        value = "false"
    .end annotation

    invoke-virtual {p0}, Lb/c/a/b;->Qh()I

    move-result v0

    if-ltz v0, :cond_1

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->gi()Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public gj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_006314_0x0002"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->gj()Z

    move-result p0

    return p0
.end method

.method public gk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->gk()Z

    move-result p0

    return p0
.end method

.method public gl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_26813_0x0004"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->gl()Z

    move-result p0

    return p0
.end method

.method public gm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x53"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->gm()Z

    move-result p0

    return p0
.end method

.method public hh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "pref_video_quality_default"
        value = "6"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->hh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public hi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x44"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->hi()Z

    move-result p0

    return p0
.end method

.method public hj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27845_0x0004"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->hj()Z

    move-result p0

    if-nez p0, :cond_1

    sget-boolean p0, Lb/c/a/b;->zg:Z

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public hk()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_35910_0x0001"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/Util;->isLocaleChinese()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->hk()Z

    move-result p0

    return p0

    :cond_1
    :goto_0
    const/4 p0, 0x0

    return p0
.end method

.method public hl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0009"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->hl()Z

    move-result p0

    return p0
.end method

.method public hm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_32974_0x0004"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->hm()Z

    move-result p0

    return p0
.end method

.method public ih()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x26"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ih()Z

    move-result p0

    return p0
.end method

.method public ii()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0011"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ii()Z

    move-result p0

    return p0
.end method

.method public ij()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_36658_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ij()Z

    move-result p0

    return p0
.end method

.method public ik()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x25"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ik()Z

    move-result p0

    return p0
.end method

.method public il()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_35955_0x0001"
        value = "false"
    .end annotation

    iget-object v0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {v0}, Lcom/mi/device/Common;->il()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lb/c/a/b;->Di()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->li()Z

    move-result p0

    if-eqz p0, :cond_1

    :cond_0
    const/4 p0, 0x1

    goto :goto_0

    :cond_1
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public im()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35893_0x0007"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->im()Z

    move-result p0

    return p0
.end method

.method public is4K30FpsEISSupported()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x07"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->is4K30FpsEISSupported()Z

    move-result p0

    return p0
.end method

.method public isCinematicPhotoSupported()Z
    .locals 2
    .annotation build Lc/a;
        key = "c_33066_0x0002"
        value = "false"
    .end annotation

    sget-boolean v0, Lb/c/a/c;->Vg:Z

    if-eqz v0, :cond_0

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1c

    if-ne v0, v1, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isCinematicPhotoSupported()Z

    move-result p0

    return p0
.end method

.method public isHighQualityPreferred()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x000c"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isHighQualityPreferred()Z

    move-result p0

    return p0
.end method

.method public isSRRequireReprocess()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0004"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSRRequireReprocess()Z

    move-result p0

    return p0
.end method

.method public isSupport960VideoEditor()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x5b"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupport960VideoEditor()Z

    move-result p0

    return p0
.end method

.method public isSupportBeautyBody()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_b_b"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupportBeautyBody()Z

    move-result p0

    return p0
.end method

.method public isSupportBokehAdjust()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x22"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupportBokehAdjust()Z

    move-result p0

    return p0
.end method

.method public isSupportMacroMode()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x32"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupportMacroMode()Z

    move-result p0

    return p0
.end method

.method public isSupportNormalWideLDC()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x05"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupportNormalWideLDC()Z

    move-result p0

    return p0
.end method

.method public isSupportShortVideoBeautyBody()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x28"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupportShortVideoBeautyBody()Z

    move-result p0

    return p0
.end method

.method public isSupportUltraWide()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_u_w"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupportUltraWide()Z

    move-result p0

    return p0
.end method

.method public isSupportUltraWideLDC()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x06"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->isSupportUltraWideLDC()Z

    move-result p0

    return p0
.end method

.method public jh()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0014"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->jh()Z

    move-result p0

    return p0
.end method

.method public ji()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ji()Z

    move-result p0

    return p0
.end method

.method public jj()Z
    .locals 0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->jj()Z

    move-result p0

    return p0
.end method

.method public jk()Z
    .locals 3
    .annotation build Lc/a;
        key = "c_0x60"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/Util;->isGlobalVersion()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    sget-boolean v0, Lb/c/a/c;->Vg:Z

    if-eqz v0, :cond_1

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1c

    if-ne v0, v2, :cond_1

    return v1

    :cond_1
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->jk()Z

    move-result p0

    return p0
.end method

.method public jl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_a_3"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->jl()Z

    move-result p0

    return p0
.end method

.method public jm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0021"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->jm()Z

    move-result p0

    return p0
.end method

.method public kh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_27845_0x0002"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->kh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public ki()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0006"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ki()Z

    move-result p0

    return p0
.end method

.method public kj()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_c_w_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->kj()Z

    move-result p0

    return p0
.end method

.method public kk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0007"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->kk()Z

    move-result p0

    return p0
.end method

.method public kl()Z
    .locals 6
    .annotation build Lc/a;
        key = "s_a_u"
        value = "false"
    .end annotation

    invoke-virtual {p0}, Lb/c/a/b;->ll()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->nl()Z

    move-result p0

    return p0

    :cond_0
    iget-object v0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {v0}, Lcom/mi/device/Common;->kl()Z

    move-result v0

    const-string v1, "DataFeature"

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Lb/c/a/b;->ll()Z

    move-result p0

    if-eqz p0, :cond_2

    const/16 p0, 0xa3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    if-eq p0, v0, :cond_1

    const/16 p0, 0xa5

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v0

    if-ne p0, v0, :cond_2

    :cond_1
    invoke-static {}, Lcom/android/camera/CameraSettings;->getCameraId()I

    move-result p0

    if-nez p0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->isUltraPixelOn()Z

    move-result p0

    if-nez p0, :cond_2

    invoke-static {}, Lcom/android/camera/CameraSettings;->readZoom()F

    move-result p0

    float-to-double v2, p0

    const-wide/high16 v4, 0x3ff0000000000000L    # 1.0

    cmpl-double p0, v2, v4

    if-ltz p0, :cond_2

    const-string p0, "Algo up enabled for mm-camera"

    invoke-static {v1, p0}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x1

    return p0

    :cond_2
    const-string p0, "Algo up disabled for mm-camera"

    invoke-static {v1, p0}, Lcom/android/camera/log/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x0

    return p0
.end method

.method public km()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_m_f"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->km()Z

    move-result p0

    return p0
.end method

.method public lh()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_r_i_m_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->lh()Z

    move-result p0

    return p0
.end method

.method public li()Z
    .locals 1

    iget-object v0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    if-nez v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Lcom/xiaomi/camera/util/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->yg:Ljava/lang/String;

    const-string v0, "cn"

    invoke-virtual {v0, p0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method public lj()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_c_w_m_g2"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->lj()Z

    move-result p0

    return p0
.end method

.method public lk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_17042_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->lk()Z

    move-result p0

    return p0
.end method

.method public ll()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_a_u_e_f_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ll()Z

    move-result p0

    return p0
.end method

.method public lm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_17042_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->lm()Z

    move-result p0

    return p0
.end method

.method public mh()Z
    .locals 0
    .annotation build Lc/a;
        key = "e_p_p_l_t"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->mh()Z

    move-result p0

    return p0
.end method

.method public mi()Z
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->ml()Ljava/lang/String;

    move-result-object p0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, ":"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p0

    const-string v0, "CAPTURE_INTENT"

    invoke-interface {p0, v0}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method public mj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x19"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->mj()Z

    move-result p0

    return p0
.end method

.method public mk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_39484_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->mk()Z

    move-result p0

    return p0
.end method

.method public ml()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_27810_0x0002"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ml()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public mm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0000"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->mm()Z

    move-result p0

    return p0
.end method

.method public nh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_28196_0x0005"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->nh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public nj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->nj()Z

    move-result p0

    return p0
.end method

.method public nk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->nk()Z

    move-result p0

    return p0
.end method

.method public nl()Z
    .locals 2

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1c

    if-le v0, v1, :cond_0

    invoke-direct {p0}, Lb/c/a/b;->wo()Z

    move-result p0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->kl()Z

    move-result p0

    return p0
.end method

.method public nm()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_0x49"
        value = "false"
    .end annotation

    sget-boolean v0, Lcom/android/camera/HybridZoomingSystem;->IS_3_OR_MORE_SAT:Z

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->nm()Z

    move-result p0

    return p0
.end method

.method public oh()I
    .locals 0
    .annotation build Lc/a;
        key = "a_l_h_l"
        value = "300"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->oh()I

    move-result p0

    return p0
.end method

.method public oi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_32889_0x0003"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->oi()Z

    move-result p0

    return p0
.end method

.method public oj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x21"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->oj()Z

    move-result p0

    return p0
.end method

.method public ol()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_26813_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ol()Z

    move-result p0

    const/4 v0, 0x0

    if-eqz p0, :cond_0

    const-string p0, "close.append.yuv"

    invoke-static {p0, v0}, Lcom/xiaomi/camera/util/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 v0, 0x1

    :cond_0
    return v0
.end method

.method public om()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x4a"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->om()Z

    move-result p0

    return p0
.end method

.method public ph()I
    .locals 0
    .annotation build Lc/a;
        key = "a_l_l_l"
        value = "350"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ph()I

    move-result p0

    return p0
.end method

.method public pi()Z
    .locals 2
    .annotation build Lc/a;
        key = "c_0x23"
        value = "false"
    .end annotation

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1c

    if-ge v0, v1, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->pi()Z

    move-result p0

    return p0
.end method

.method public pj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->pj()Z

    move-result p0

    return p0
.end method

.method public pk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->pk()Z

    move-result p0

    return p0
.end method

.method public pl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_32974_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->pl()Z

    move-result p0

    return p0
.end method

.method public pm()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_v_b"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->pm()Z

    move-result p0

    return p0
.end method

.method public qh()Z
    .locals 0
    .annotation build Lc/a;
        key = "a_e_d"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->qh()Z

    move-result p0

    return p0
.end method

.method public qi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_13254_0x01"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->qi()Z

    move-result p0

    return p0
.end method

.method public qj()Z
    .locals 2
    .annotation build Lc/a;
        key = "c_35893_0x0002"
        value = "false"
    .end annotation

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1c

    if-ne v0, v1, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->qj()Z

    move-result p0

    return p0
.end method

.method public qk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x0a"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->qk()Z

    move-result p0

    return p0
.end method

.method public ql()Z
    .locals 2
    .annotation build Lc/a;
        key = "c_17042_0x0005"
        value = "false"
    .end annotation

    sget-boolean v0, Lb/c/a/c;->eh:Z

    if-eqz v0, :cond_0

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1d

    if-le v0, v1, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ql()Z

    move-result p0

    return p0
.end method

.method public qm()Z
    .locals 2
    .annotation build Lc/a;
        key = "c_28041_0x0006"
        value = "false"
    .end annotation

    const/4 v0, 0x0

    const-string v1, "miuicamera.sat.video"

    invoke-static {v1, v0}, Lcom/xiaomi/camera/util/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    if-eqz v1, :cond_0

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "sat video debug prop:"

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "DataFeature"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v1

    :cond_0
    sget-boolean v1, Lb/c/a/c;->uh:Z

    if-eqz v1, :cond_1

    sget-boolean v1, Lb/c/a/c;->bh:Z

    if-nez v1, :cond_1

    return v0

    :cond_1
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->qm()Z

    move-result p0

    return p0
.end method

.method public rh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0004"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->rh()I

    move-result p0

    return p0
.end method

.method public ri()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x55"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ri()Z

    move-result p0

    return p0
.end method

.method public rj()Z
    .locals 3
    .annotation build Lc/a;
        key = "c_27810_0x0008"
        value = "false"
    .end annotation

    invoke-static {}, Lb/c/a/c;->Ym()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1c

    if-ne v0, v2, :cond_1

    return v1

    :cond_1
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->rj()Z

    move-result p0

    return p0
.end method

.method public rk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_22367_0x0002"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->rk()Z

    move-result p0

    return p0
.end method

.method public rl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_b_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->rl()Z

    move-result p0

    return p0
.end method

.method public rm()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_0x42"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->rm()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public sh()Landroid/util/Size;
    .locals 3

    invoke-virtual {p0}, Lb/c/a/b;->nh()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v0

    const-string v1, ":"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v0, p0

    const/4 v1, 0x1

    if-le v0, v1, :cond_0

    aget-object p0, p0, v1

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "x"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v0, p0

    const/4 v2, 0x2

    if-ne v0, v2, :cond_0

    new-instance v0, Landroid/util/Size;

    const/4 v2, 0x0

    aget-object v2, p0, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    aget-object p0, p0, v1

    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p0

    invoke-direct {v0, v2, p0}, Landroid/util/Size;-><init>(II)V

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return-object v0
.end method

.method public shouldCheckSatFallbackState()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_9006_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->shouldCheckSatFallbackState()Z

    move-result p0

    return p0
.end method

.method public si()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_e_f_a_l"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->si()Z

    move-result p0

    return p0
.end method

.method public sj()Z
    .locals 5
    .annotation build Lc/a;
        key = "c_35905_0x0001"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getInstance()Lcom/android/camera/module/loader/camera2/Camera2DataContainer;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getMainBackCameraId()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera/module/loader/camera2/Camera2DataContainer;->getCapabilities(I)Lcom/android/camera2/CameraCapabilities;

    move-result-object v0

    invoke-static {v0}, Ljava/util/Optional;->ofNullable(Ljava/lang/Object;)Ljava/util/Optional;

    move-result-object v0

    sget-object v1, Lb/c/a/a;->INSTANCE:Lb/c/a/a;

    invoke-virtual {v0, v1}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object v0

    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    const-string v2, "miuicamera.dualvideo.show"

    invoke-static {v2, v1}, Lcom/xiaomi/camera/util/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v2

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->sj()Z

    move-result p0

    invoke-static {}, Lb/c/a/c;->isMTKPlatform()Z

    move-result v3

    const/4 v4, 0x1

    if-eqz v3, :cond_2

    if-nez v2, :cond_0

    if-eqz v0, :cond_1

    if-eqz p0, :cond_1

    :cond_0
    move v1, v4

    :cond_1
    return v1

    :cond_2
    if-nez v2, :cond_3

    if-eqz p0, :cond_4

    :cond_3
    move v1, v4

    :cond_4
    return v1
.end method

.method public sk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_32889_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->sk()Z

    move-result p0

    return p0
.end method

.method public sl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_c_w_b"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->sl()Z

    move-result p0

    return p0
.end method

.method public sm()Z
    .locals 3

    invoke-virtual {p0}, Lb/c/a/b;->nh()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_1

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v0

    const-string v2, ":"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    invoke-virtual {p0, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v0, p0

    if-lez v0, :cond_1

    aget-object p0, p0, v1

    invoke-static {p0}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result p0

    move v1, p0

    goto :goto_0

    :cond_0
    invoke-static {p0}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v1

    :cond_1
    :goto_0
    return v1
.end method

.method public supportColorEnhance()Z
    .locals 1
    .annotation build Lc/a;
        key = "c_35955_0x0003"
        value = "false"
    .end annotation

    iget-object v0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {v0}, Lcom/mi/device/Common;->supportColorEnhance()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lb/c/a/b;->Di()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public th()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_0x30"
        value = "4"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->th()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public ti()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_f_s_c"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ti()Z

    move-result p0

    return p0
.end method

.method public tj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35905_0x0004"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->tj()Z

    move-result p0

    return p0
.end method

.method public tk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35910_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->tk()Z

    move-result p0

    return p0
.end method

.method public tl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_e_l"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->tl()Z

    move-result p0

    return p0
.end method

.method public tm()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_z_m"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->tm()Z

    move-result p0

    return p0
.end method

.method public uh()J
    .locals 2
    .annotation build Lc/a;
        key = "cas_c_33972_0x0001"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->uh()J

    move-result-wide v0

    return-wide v0
.end method

.method public ui()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x20"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ui()Z

    move-result p0

    return p0
.end method

.method public uj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_36658_0x0003"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->uj()Z

    move-result p0

    return p0
.end method

.method public uk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35910_0x0030"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->uk()Z

    move-result p0

    return p0
.end method

.method public ul()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19039_0x0022"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->ul()Z

    move-result p0

    return p0
.end method

.method public um()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x52"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->um()Z

    move-result p0

    return p0
.end method

.method public vh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_27845_0x0005"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->vh()I

    move-result p0

    return p0
.end method

.method public vi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_27810_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->vi()Z

    move-result p0

    return p0
.end method

.method public vj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x36"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->vj()Z

    move-result p0

    return p0
.end method

.method public vk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_d_e_f_w"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->vk()Z

    move-result p0

    return p0
.end method

.method public vl()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_f_a"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->vl()Z

    move-result p0

    return p0
.end method

.method public vm()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x56"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->vm()Z

    move-result p0

    return p0
.end method

.method public wh()F
    .locals 0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->wh()F

    move-result p0

    return p0
.end method

.method public wi()Z
    .locals 0
    .annotation build Lc/a;
        key = "f_a_u_u"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->wi()Z

    move-result p0

    return p0
.end method

.method public wj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_33066_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->wj()Z

    move-result p0

    return p0
.end method

.method public wk()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x34"
        value = "true"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->wk()Z

    move-result p0

    return p0
.end method

.method public wl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35955_0x0004"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->wl()Z

    move-result p0

    return p0
.end method

.method public wm()Z
    .locals 1

    invoke-virtual {p0}, Lb/c/a/b;->yk()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lb/c/a/b;->xk()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lb/c/a/b;->Ck()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lb/c/a/b;->Ak()Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public xh()Ljava/lang/String;
    .locals 0
    .annotation build Lc/a;
        key = "c_0x40"
        value = "common"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->xh()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public xi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_19086_0x0001"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->xi()Z

    move-result p0

    return p0
.end method

.method public xj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x16"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->xj()Z

    move-result p0

    return p0
.end method

.method public xk()Z
    .locals 1
    .annotation build Lc/a;
        key = "s_s_m_t"
        value = "false"
    .end annotation

    invoke-virtual {p0}, Lb/c/a/b;->yk()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->xk()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public xl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x46"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->xl()Z

    move-result p0

    return p0
.end method

.method public y(I)I
    .locals 0
    .annotation build Lc/a;
        key = "c_28041_0x0000"
        value = "-1"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->so()I

    move-result p0

    if-gez p0, :cond_0

    return p1

    :cond_0
    return p0
.end method

.method public yh()[I
    .locals 4
    .annotation build Lc/a;
        key = "c_36211_0x0003"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->yh()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    :cond_0
    :try_start_0
    const-string v0, ":"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    array-length v0, p0

    new-array v0, v0, [I

    const/4 v2, 0x0

    :goto_0
    array-length v3, p0

    if-ge v2, v3, :cond_1

    aget-object v3, p0, v2

    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v3

    aput v3, v0, v2
    :try_end_0
    .catch Ljava/util/regex/PatternSyntaxException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_1
    return-object v0

    :catch_0
    const-string p0, "DataFeature"

    const-string v0, "get default favorite modes fails."

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-object v1
.end method

.method public yi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x39"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->yi()Z

    move-result p0

    return p0
.end method

.method public yj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x38"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->yj()Z

    move-result p0

    return p0
.end method

.method public yk()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_f_9"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->yk()Z

    move-result p0

    return p0
.end method

.method public yl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_35955_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->yl()Z

    move-result p0

    return p0
.end method

.method public z(I)Z
    .locals 1
    .annotation build Lc/a;
        key = "c_26813_0x0003"
        value = "true"
    .end annotation

    const/16 v0, 0xb4

    if-eq p1, v0, :cond_0

    const/16 v0, 0xa2

    if-eq p1, v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->xo()Z

    move-result p0

    return p0
.end method

.method public zh()I
    .locals 0
    .annotation build Lc/a;
        key = "c_0x13"
        value = "0"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->zh()I

    move-result p0

    return p0
.end method

.method public zi()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_0x1b"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->zi()Z

    move-result p0

    return p0
.end method

.method public zj()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_32889_0x0005"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->zj()Z

    move-result p0

    return p0
.end method

.method public zk()Z
    .locals 0
    .annotation build Lc/a;
        key = "s_f_z_i"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->zk()Z

    move-result p0

    return p0
.end method

.method public zl()Z
    .locals 0
    .annotation build Lc/a;
        key = "c_32974_0x0002"
        value = "false"
    .end annotation

    iget-object p0, p0, Lb/c/a/b;->mConfig:Lcom/mi/device/Common;

    invoke-virtual {p0}, Lcom/mi/device/Common;->zl()Z

    move-result p0

    return p0
.end method
