.class public Lcom/android/camera/MutexModeManager;
.super Ljava/lang/Object;
.source "MutexModeManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/MutexModeManager$MutexCallBack;,
        Lcom/android/camera/MutexModeManager$MutexMode;
    }
.end annotation


# static fields
.field public static final MUTEX_AOHDR:I = 0x2

.field public static final MUTEX_BURST_SHOOT:I = 0x6

.field public static final MUTEX_GROUP:I = 0x8

.field public static final MUTEX_HAND_NIGHT:I = 0x3

.field public static final MUTEX_MORPHO_HDR:I = 0x1

.field public static final MUTEX_NONE:I = 0x0

.field public static final MUTEX_RAW:I = 0x4

.field public static final MUTEX_SCENE_HDR:I = 0x5

.field public static final MUTEX_STEREO:I = 0x7

.field public static final MUTEX_SUPER_RESOLUTION:I = 0x9


# instance fields
.field private mCurrentMutexMode:I

.field private mIsMandatory:Z

.field private mMutexCallBack:Lcom/android/camera/MutexModeManager$MutexCallBack;


# direct methods
.method public constructor <init>(Lcom/android/camera/MutexModeManager$MutexCallBack;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput v0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    iput-object p1, p0, Lcom/android/camera/MutexModeManager;->mMutexCallBack:Lcom/android/camera/MutexModeManager$MutexCallBack;

    return-void
.end method

.method private enter(I)V
    .locals 2

    if-nez p1, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/MutexModeManager;->mMutexCallBack:Lcom/android/camera/MutexModeManager$MutexCallBack;

    if-nez v0, :cond_1

    return-void

    :cond_1
    iget v1, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    if-ne v1, p1, :cond_2

    return-void

    :cond_2
    iput p1, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    invoke-interface {v0, p1}, Lcom/android/camera/MutexModeManager$MutexCallBack;->enterMutexMode(I)V

    return-void
.end method

.method private exit(I)V
    .locals 2

    if-nez p1, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/android/camera/MutexModeManager;->mMutexCallBack:Lcom/android/camera/MutexModeManager$MutexCallBack;

    if-nez v0, :cond_1

    return-void

    :cond_1
    const/4 v1, 0x0

    iput v1, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    invoke-interface {v0, p1}, Lcom/android/camera/MutexModeManager$MutexCallBack;->exitMutexMode(I)V

    return-void
.end method

.method private switchMutexMode(II)V
    .locals 0

    if-eq p1, p2, :cond_0

    invoke-direct {p0, p1}, Lcom/android/camera/MutexModeManager;->exit(I)V

    invoke-direct {p0, p2}, Lcom/android/camera/MutexModeManager;->enter(I)V

    :cond_0
    return-void
.end method


# virtual methods
.method public clearMandatoryFlag()V
    .locals 1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/MutexModeManager;->mIsMandatory:Z

    return-void
.end method

.method public getAlgorithmName()Ljava/lang/String;
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x1

    if-eq p0, v0, :cond_2

    const/4 v0, 0x2

    if-eq p0, v0, :cond_1

    const/4 v0, 0x3

    if-eq p0, v0, :cond_0

    const/4 v0, 0x5

    if-eq p0, v0, :cond_2

    const-string p0, ""

    return-object p0

    :cond_0
    const-string p0, "HHT"

    return-object p0

    :cond_1
    const-string p0, "AO_HDR"

    return-object p0

    :cond_2
    const-string p0, "HDR"

    return-object p0
.end method

.method public getMutexMode()I
    .locals 0

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    return p0
.end method

.method public getSuffix()Ljava/lang/String;
    .locals 2

    iget v0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v1, 0x4

    if-ne v0, v1, :cond_0

    const-string p0, "_RAW"

    return-object p0

    :cond_0
    invoke-static {}, Lb/c/a/c;->ym()Z

    move-result v0

    const-string v1, ""

    if-nez v0, :cond_2

    invoke-static {}, Lcom/android/camera/Util;->isForceNameSuffix()Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    return-object v1

    :cond_2
    :goto_0
    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x1

    if-eq p0, v0, :cond_5

    const/4 v0, 0x2

    if-eq p0, v0, :cond_4

    const/4 v0, 0x3

    if-eq p0, v0, :cond_3

    const/4 v0, 0x5

    if-eq p0, v0, :cond_5

    return-object v1

    :cond_3
    const-string p0, "_HHT"

    return-object p0

    :cond_4
    const-string p0, "_AO_HDR"

    return-object p0

    :cond_5
    const-string p0, "_HDR"

    return-object p0
.end method

.method public inMandatoryMode()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/MutexModeManager;->mIsMandatory:Z

    return p0
.end method

.method public isAoHdr()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x2

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isBurstShoot()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x6

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isGroupShot()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/16 v0, 0x8

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isHandNight()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x3

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isHdr()Z
    .locals 2

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x1

    const/4 v1, 0x2

    if-eq p0, v1, :cond_1

    if-eq p0, v0, :cond_1

    const/4 v1, 0x5

    if-ne p0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :cond_1
    :goto_0
    return v0
.end method

.method public isHdrSupportTorch(Z)Z
    .locals 2

    invoke-static {}, Lb/c/a/c;->Zn()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    if-eqz p1, :cond_0

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    if-ne p0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method public isMorphoHdr()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x1

    if-ne p0, v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isNeedComposed()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    if-eqz p0, :cond_0

    const/4 v0, 0x2

    if-eq p0, v0, :cond_0

    const/4 v0, 0x6

    if-eq p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isNormal()Z
    .locals 0

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isRAW()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x4

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isSceneHdr()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/4 v0, 0x5

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isSuperResolution()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    const/16 v0, 0x9

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isSupportedFlashOn()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    if-eqz p0, :cond_1

    const/4 v0, 0x4

    if-eq p0, v0, :cond_1

    const/4 v0, 0x3

    if-ne p0, v0, :cond_0

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

.method public isSupportedTorch()Z
    .locals 1

    invoke-static {}, Lb/c/a/c;->Zn()Z

    move-result v0

    if-eqz v0, :cond_1

    iget p0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    if-eqz p0, :cond_0

    const/4 v0, 0x2

    if-eq p0, v0, :cond_0

    const/4 v0, 0x6

    if-eq p0, v0, :cond_0

    const/16 v0, 0x8

    if-eq p0, v0, :cond_0

    const/16 v0, 0x9

    if-ne p0, v0, :cond_1

    :cond_0
    const/4 p0, 0x1

    goto :goto_0

    :cond_1
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public resetMutexMode()V
    .locals 2
    .annotation build Landroidx/annotation/MainThread;
    .end annotation

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/MutexModeManager;->mIsMandatory:Z

    iget v1, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    invoke-direct {p0, v1, v0}, Lcom/android/camera/MutexModeManager;->switchMutexMode(II)V

    return-void
.end method

.method public setMutexMode(I)V
    .locals 1
    .annotation build Landroidx/annotation/MainThread;
    .end annotation

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/MutexModeManager;->mIsMandatory:Z

    iget v0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    invoke-direct {p0, v0, p1}, Lcom/android/camera/MutexModeManager;->switchMutexMode(II)V

    return-void
.end method

.method public setMutexModeMandatory(I)V
    .locals 1

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/MutexModeManager;->mIsMandatory:Z

    iget v0, p0, Lcom/android/camera/MutexModeManager;->mCurrentMutexMode:I

    invoke-direct {p0, v0, p1}, Lcom/android/camera/MutexModeManager;->switchMutexMode(II)V

    return-void
.end method
