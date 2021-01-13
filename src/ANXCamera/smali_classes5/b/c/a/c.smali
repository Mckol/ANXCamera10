.class public Lb/c/a/c;
.super Ljava/lang/Object;
.source "Device.java"


# static fields
.field private static Ah:Ljava/util/ArrayList; = null
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public static final Bg:Ljava/lang/String;

.field private static final Bh:[Ljava/lang/String;

.field public static final Cg:Ljava/lang/String; = "qcom"

.field private static final Ch:Ljava/lang/String; = "ro.boot.hwversion"

.field public static final Dg:Ljava/lang/String; = "mediatek"

.field private static final Eg:I = 0x64

.field public static final Fg:Ljava/lang/String;

.field public static final Gg:Z

.field public static final Hg:Z

.field public static final IS_HONGMI:Z

.field private static final IS_MTK_PLATFORM:Ljava/util/concurrent/atomic/AtomicReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/atomic/AtomicReference<",
            "Ljava/util/Optional<",
            "Ljava/lang/Boolean;",
            ">;>;"
        }
    .end annotation
.end field

.field public static final IS_XIAOMI:Z

.field public static final Ig:Z

.field public static final Jg:Z

.field public static final Kg:Z

.field public static final Lg:Z

.field public static final Mg:Z

.field public static final Ng:Z

.field public static final Og:Z

.field public static final Pg:Z

.field public static final Qg:Z

.field public static final Rg:Z

.field public static final Sg:Z

.field public static final Tg:Z

.field public static final Ug:Z

.field public static final Vg:Z

.field public static final Wg:Z

.field public static final Xg:Z

.field public static final Yg:Z

.field public static final Zg:Z

.field public static final _g:Z

.field public static final bh:Z

.field public static final dh:Z

.field public static final eh:Z

.field public static final fh:Z

.field public static final gh:Z

.field public static final hh:Z

.field public static final jh:Z

.field public static final kh:Z

.field public static final lh:Z

.field public static final mh:Z

.field public static final nh:Z

.field public static final oh:Z

.field public static final qh:Z

.field public static final rh:Z

.field public static final sh:Z

.field public static final th:Z

.field public static final uh:Z

.field public static final vh:Z

.field public static final wh:Z

.field private static final xh:I = 0x1

.field private static final yh:I = 0x4

.field private static final zh:I = 0x8


# direct methods
.method static constructor <clinit>()V
    .locals 5

    sget-object v0, Lmiui/os/Build;->DEVICE:Ljava/lang/String;

    sput-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    sget-object v0, Lmiui/os/Build;->MODEL:Ljava/lang/String;

    sput-object v0, Lb/c/a/c;->Fg:Ljava/lang/String;

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "beryllium"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Gg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "lavender"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Hg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "violet"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Ig:Z

    invoke-static {}, Lb/c/a/c;->hn()Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->IS_HONGMI:Z

    invoke-static {}, Lb/c/a/c;->eo()Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->IS_XIAOMI:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "polaris"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Jg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "sirius"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Kg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "dipper"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Lg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "andromeda"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Mg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "perseus"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Ng:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "cepheus"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Og:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "raphael"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Pg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "grus"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Qg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "begonia"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Rg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "phoenix"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-nez v0, :cond_1

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "phoenixin"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    move v0, v1

    goto :goto_1

    :cond_1
    :goto_0
    move v0, v2

    :goto_1
    sput-boolean v0, Lb/c/a/c;->Sg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "begoniain"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Tg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "ginkgo"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Ug:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "pyxis"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Vg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "vela"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Wg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "laurus"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Xg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "laurel_sprout"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Yg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "tucana"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->Zg:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "umi"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->_g:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "cmi"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->bh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "cas"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->dh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "apollo"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_3

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "apolloin"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    goto :goto_2

    :cond_2
    move v0, v1

    goto :goto_3

    :cond_3
    :goto_2
    move v0, v2

    :goto_3
    sput-boolean v0, Lb/c/a/c;->eh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "bomb"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_5

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "banana"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    goto :goto_4

    :cond_4
    move v0, v1

    goto :goto_5

    :cond_5
    :goto_4
    move v0, v2

    :goto_5
    sput-boolean v0, Lb/c/a/c;->fh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "lmi"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_7

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "lmiin"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_6

    goto :goto_6

    :cond_6
    move v0, v1

    goto :goto_7

    :cond_7
    :goto_6
    move v0, v2

    :goto_7
    sput-boolean v0, Lb/c/a/c;->gh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "lmipro"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_9

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "lmiinpro"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_8

    goto :goto_8

    :cond_8
    move v0, v1

    goto :goto_9

    :cond_9
    :goto_8
    move v0, v2

    :goto_9
    sput-boolean v0, Lb/c/a/c;->hh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "draco"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->jh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "picasso"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_b

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v4, "picassoin"

    invoke-virtual {v4, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_a

    goto :goto_a

    :cond_a
    move v0, v1

    goto :goto_b

    :cond_b
    :goto_a
    move v0, v2

    :goto_b
    sput-boolean v0, Lb/c/a/c;->kh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->lh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "gauguin"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_d

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "gauguinpro"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_c

    goto :goto_c

    :cond_c
    move v0, v1

    goto :goto_d

    :cond_d
    :goto_c
    move v0, v2

    :goto_d
    sput-boolean v0, Lb/c/a/c;->mh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "cezanne"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->nh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "crux"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->oh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "curtana"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_f

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "durandal"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_f

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "excalibur"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_f

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "joyeuse"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_f

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "gram"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_e

    goto :goto_e

    :cond_e
    move v0, v1

    goto :goto_f

    :cond_f
    :goto_e
    move v0, v2

    :goto_f
    sput-boolean v0, Lb/c/a/c;->qh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "dandelion"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lb/c/a/c;->rh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "angelica"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_11

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "angelican"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_11

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "angelicain"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_11

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "cattail"

    invoke-virtual {v3, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_10

    goto :goto_10

    :cond_10
    move v0, v1

    goto :goto_11

    :cond_11
    :goto_10
    move v0, v2

    :goto_11
    sput-boolean v0, Lb/c/a/c;->sh:Z

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "merlin"

    invoke-static {v0, v3}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_12

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v3, "merlinnfc"

    invoke-static {v0, v3}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_13

    :cond_12
    move v1, v2

    :cond_13
    sput-boolean v1, Lb/c/a/c;->th:Z

    sget-boolean v0, Lmiui/os/Build;->IS_STABLE_VERSION:Z

    sput-boolean v0, Lb/c/a/c;->uh:Z

    sget-boolean v0, Lmiui/os/Build;->IS_CM_CUSTOMIZATION_TEST:Z

    sput-boolean v0, Lb/c/a/c;->vh:Z

    sget-boolean v0, Lmiui/os/Build;->IS_CM_CUSTOMIZATION:Z

    sput-boolean v0, Lb/c/a/c;->wh:Z

    const-string v0, "KR"

    const-string v1, "JP"

    filled-new-array {v0, v1}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lb/c/a/c;->Bh:[Ljava/lang/String;

    new-instance v0, Ljava/util/concurrent/atomic/AtomicReference;

    invoke-static {}, Ljava/util/Optional;->empty()Ljava/util/Optional;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicReference;-><init>(Ljava/lang/Object;)V

    sput-object v0, Lb/c/a/c;->IS_MTK_PLATFORM:Ljava/util/concurrent/atomic/AtomicReference;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static Am()I
    .locals 1
    .annotation build Lc/a;
        key = "burst_shoot_count"
        value = "100"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Am()I

    move-result v0

    return v0
.end method

.method public static An()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_mi_face_beauty"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->An()Z

    move-result v0

    return v0
.end method

.method public static Bm()Ljava/lang/String;
    .locals 3

    const/4 v0, 0x1

    const-string v1, "ro.boot.camera.config"

    invoke-static {v1, v0}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v1

    if-eqz v1, :cond_1

    const-string v2, ""

    if-eq v1, v0, :cond_0

    :cond_0
    return-object v2

    :cond_1
    const-string v0, "_pro"

    return-object v0
.end method

.method public static Bn()Z
    .locals 1

    invoke-static {}, Lb/c/a/c;->un()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lb/c/a/c;->An()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static Cm()Ljava/util/ArrayList;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    sget-object v0, Lb/c/a/c;->Ah:Ljava/util/ArrayList;

    if-nez v0, :cond_0

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lb/c/a/c;->Ah:Ljava/util/ArrayList;

    invoke-static {}, Lb/c/a/c;->Dm()[Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_0

    sget-object v1, Lb/c/a/c;->Ah:Ljava/util/ArrayList;

    invoke-static {v1, v0}, Ljava/util/Collections;->addAll(Ljava/util/Collection;[Ljava/lang/Object;)Z

    :cond_0
    sget-object v0, Lb/c/a/c;->Ah:Ljava/util/ArrayList;

    return-object v0
.end method

.method public static Cn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_psensor_pocket_mode"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Cn()Z

    move-result v0

    return v0
.end method

.method public static Dm()[Ljava/lang/String;
    .locals 2
    .annotation build Lc/a;
        key = "fp_nav_event_name_list"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Dm()Ljava/lang/String;

    move-result-object v0

    const-string v1, ","

    invoke-virtual {v0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static Dn()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->dj()Z

    move-result v0

    if-nez v0, :cond_0

    sget-boolean v0, Lb/c/a/c;->IS_HONGMI:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Em()Ljava/lang/String;
    .locals 3

    sget-boolean v0, Lb/c/a/c;->lh:Z

    const-string v1, ""

    if-eqz v0, :cond_2

    invoke-static {}, Lb/c/a/c;->Vm()Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "_48m"

    return-object v0

    :cond_0
    invoke-static {}, Lb/c/a/c;->Wm()Z

    move-result v0

    if-eqz v0, :cond_1

    const-string v0, "_speed"

    return-object v0

    :cond_1
    return-object v1

    :cond_2
    invoke-static {}, Lb/c/a/c;->Om()Z

    move-result v0

    if-eqz v0, :cond_3

    const-string v0, "_l"

    return-object v0

    :cond_3
    invoke-static {}, Lb/c/a/c;->in()Z

    move-result v0

    if-nez v0, :cond_f

    invoke-static {}, Lb/c/a/c;->kn()Z

    move-result v0

    if-eqz v0, :cond_4

    goto/16 :goto_2

    :cond_4
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->fi()Z

    move-result v0

    if-nez v0, :cond_5

    return-object v1

    :cond_5
    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v2, "BROWN EDITION"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_e

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v2, "Explorer"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_6

    goto :goto_1

    :cond_6
    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v2, "ROY"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_7

    const-string v0, "_b"

    return-object v0

    :cond_7
    invoke-static {}, Lb/c/a/c;->Qm()Z

    move-result v0

    if-eqz v0, :cond_8

    const-string v0, "_s"

    return-object v0

    :cond_8
    invoke-static {}, Lb/c/a/c;->en()Z

    move-result v0

    const-string v2, "_global"

    if-nez v0, :cond_d

    invoke-static {}, Lb/c/a/c;->dn()Z

    move-result v0

    if-nez v0, :cond_d

    invoke-static {}, Lb/c/a/c;->_m()Z

    move-result v0

    if-nez v0, :cond_d

    invoke-static {}, Lb/c/a/c;->Zm()Z

    move-result v0

    if-nez v0, :cond_d

    invoke-static {}, Lb/c/a/c;->an()Z

    move-result v0

    if-eqz v0, :cond_9

    goto :goto_0

    :cond_9
    invoke-static {}, Lb/c/a/c;->on()Z

    move-result v0

    if-eqz v0, :cond_a

    const-string v0, "_premium"

    return-object v0

    :cond_a
    invoke-static {}, Lb/c/a/c;->bn()Z

    move-result v0

    if-eqz v0, :cond_c

    invoke-static {}, Lb/c/a/c;->pn()Z

    move-result v0

    if-eqz v0, :cond_b

    const-string v0, "_global_pro"

    return-object v0

    :cond_b
    return-object v2

    :cond_c
    return-object v1

    :cond_d
    :goto_0
    return-object v2

    :cond_e
    :goto_1
    const-string v0, "_a"

    return-object v0

    :cond_f
    :goto_2
    const-string v0, "_in"

    return-object v0
.end method

.method public static En()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_screen_light"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->En()Z

    move-result v0

    return v0
.end method

.method public static Fj()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->Fj()Z

    move-result v0

    return v0
.end method

.method public static Fm()I
    .locals 1
    .annotation build Lc/a;
        key = "camera_hibernation_timeout_in_minutes"
        value = "3"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Fm()I

    move-result v0

    return v0
.end method

.method public static Fn()Z
    .locals 1

    invoke-static {}, Lb/c/a/c;->ho()I

    move-result v0

    and-int/lit8 v0, v0, 0xd

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Gm()[I
    .locals 4
    .annotation build Lc/a;
        key = "fpsList"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Gm()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    const/4 v2, 0x0

    if-eqz v1, :cond_0

    new-array v0, v2, [I

    return-object v0

    :cond_0
    const-string v1, ","

    invoke-virtual {v0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    array-length v1, v0

    new-array v1, v1, [I

    :goto_0
    array-length v3, v0

    if-ge v2, v3, :cond_1

    aget-object v3, v0, v2

    invoke-static {v3}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v3

    aput v3, v1, v2

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_1
    return-object v1
.end method

.method public static Gn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_aohdr"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Gn()Z

    move-result v0

    return v0
.end method

.method public static Hm()Z
    .locals 1
    .annotation build Lc/a;
        key = "sensor_has_latency"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Hm()Z

    move-result v0

    return v0
.end method

.method public static Hn()Z
    .locals 2

    invoke-static {}, Lb/c/a/c;->ho()I

    move-result v0

    const/4 v1, 0x1

    and-int/2addr v0, v1

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method public static Im()Z
    .locals 2
    .annotation build Lc/a;
        key = "is_18x9_ratio_screen"
        value = "false"
    .end annotation

    sget v0, Lcom/android/camera/Util;->sWindowHeight:I

    int-to-float v0, v0

    sget v1, Lcom/android/camera/Util;->sWindowWidth:I

    int-to-float v1, v1

    div-float/2addr v0, v1

    const/high16 v1, 0x40000000    # 2.0f

    cmpl-float v0, v0, v1

    if-ltz v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Im()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static In()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_chroma_flash"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->In()Z

    move-result v0

    return v0
.end method

.method public static Jm()Z
    .locals 2

    invoke-static {}, Lb/c/a/c;->vo()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->vj()Z

    move-result v0

    if-nez v0, :cond_1

    return v1

    :cond_1
    invoke-static {}, Lb/c/a/c;->Cm()Ljava/util/ArrayList;

    move-result-object v0

    if-eqz v0, :cond_2

    invoke-static {}, Lb/c/a/c;->Cm()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_2

    const/4 v0, 0x1

    return v0

    :cond_2
    return v1
.end method

.method public static Jn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_edge_handgrip"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Jn()Z

    move-result v0

    return v0
.end method

.method public static Km()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->ll()Z

    move-result v0

    if-nez v0, :cond_1

    sget-boolean v0, Lb/c/a/c;->Sg:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static Kn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_record_location"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Kn()Z

    move-result v0

    return v0
.end method

.method public static Lm()Z
    .locals 1

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    invoke-static {}, Lb/c/a/c;->getCountry()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lb/c/a/c;->ia(Ljava/lang/String;)Z

    move-result v0

    return v0

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public static Ln()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_burst_shoot"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Ln()Z

    move-result v0

    return v0
.end method

.method public static Mm()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_camera_app_water_mark"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Mm()Z

    move-result v0

    return v0
.end method

.method public static Mn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_manual_function"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Mn()Z

    move-result v0

    return v0
.end method

.method public static N(Z)Z
    .locals 3

    const-string v0, "ro.miui.customized.region"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "fr_sfr"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_2

    const-string v1, "fr_orange"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    const-string v1, "es_vodafone"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lb/c/a/c;->getCountry()Ljava/lang/String;

    move-result-object v0

    const-string v1, "NL"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    return v2

    :cond_1
    return p0

    :cond_2
    :goto_0
    return v2
.end method

.method public static Nm()Z
    .locals 2

    const-string v0, "ro.boot.hwversion"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sget-boolean v1, Lb/c/a/c;->oh:Z

    if-eqz v1, :cond_1

    const-string v1, "7.1.7"

    invoke-static {v0, v1}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    const-string v1, "7.2.0"

    invoke-static {v0, v1}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Nn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_movie_solid"
        value = "false"
    .end annotation

    invoke-static {}, Lb/c/a/c;->Om()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Nn()Z

    move-result v0

    return v0
.end method

.method public static Om()Z
    .locals 3

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "onc"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwversion"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_0

    const/16 v2, 0x32

    invoke-virtual {v0, v1}, Ljava/lang/String;->charAt(I)C

    move-result v0

    if-ne v2, v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method public static On()Z
    .locals 1

    invoke-static {}, Lb/c/a/c;->Lm()Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    return v0
.end method

.method public static Pl()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->Pl()Z

    move-result v0

    return v0
.end method

.method public static Pm()Z
    .locals 2

    sget-boolean v0, Lb/c/a/c;->Hg:Z

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "India"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Pn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_new_style_time_water_mark"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Pn()Z

    move-result v0

    return v0
.end method

.method public static Qm()Z
    .locals 2

    sget-boolean v0, Lb/c/a/c;->Hg:Z

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "India_48_5"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Qn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_object_track"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Qn()Z

    move-result v0

    return v0
.end method

.method public static Rm()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->Jg:Z

    return v0
.end method

.method public static Rn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_peaking_mf"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Rn()Z

    move-result v0

    return v0
.end method

.method public static Sm()Z
    .locals 1

    invoke-static {}, Lb/c/a/c;->Xm()Z

    move-result v0

    if-nez v0, :cond_1

    sget-boolean v0, Lb/c/a/c;->Vg:Z

    if-nez v0, :cond_1

    sget-boolean v0, Lb/c/a/c;->Qg:Z

    if-nez v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->ll()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static Sn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_quick_snap"
        value = "false"
    .end annotation

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Sn()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Tm()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_front_video_quality_1080p"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Tm()Z

    move-result v0

    return v0
.end method

.method public static Tn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_dual_sd_card"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Tn()Z

    move-result v0

    return v0
.end method

.method public static Um()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->Rg:Z

    if-nez v0, :cond_1

    sget-boolean v0, Lb/c/a/c;->Tg:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static Un()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_shader_effect"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Un()Z

    move-result v0

    return v0
.end method

.method public static Vm()Z
    .locals 2

    sget-boolean v0, Lb/c/a/c;->lh:Z

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const-string v0, "ro.product.name"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "picasso_48m"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public static Vn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_skin_beauty"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Vn()Z

    move-result v0

    return v0
.end method

.method public static Wm()Z
    .locals 6

    sget-boolean v0, Lb/c/a/c;->lh:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    const-string v0, "3.9.3"

    const-string v2, "3.9.5"

    filled-new-array {v0, v2}, [Ljava/lang/String;

    move-result-object v0

    const-string v2, "ro.boot.hwversion"

    invoke-static {v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    array-length v3, v0

    move v4, v1

    :goto_0
    if-ge v4, v3, :cond_2

    aget-object v5, v0, v4

    invoke-virtual {v5, v2}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_1

    const/4 v0, 0x1

    return v0

    :cond_1
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    :cond_2
    return v1
.end method

.method public static Wn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_support_stereo"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Wn()Z

    move-result v0

    return v0
.end method

.method public static Xm()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->Ng:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Xn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_tilt_shift"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Xn()Z

    move-result v0

    return v0
.end method

.method public static Ym()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->Og:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Yn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_water_mark"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Yn()Z

    move-result v0

    return v0
.end method

.method public static Zm()Z
    .locals 2

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "davinci"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static Zn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_torch_capture"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->Zn()Z

    move-result v0

    return v0
.end method

.method public static _m()Z
    .locals 2

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "raphael"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static _n()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_video_pause"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->_n()Z

    move-result v0

    return v0
.end method

.method public static an()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->Vg:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static ao()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_4k_quality"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->ao()Z

    move-result v0

    return v0
.end method

.method public static bn()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->Zg:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static bo()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_surface_size_limit"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->bo()Z

    move-result v0

    return v0
.end method

.method public static cn()Z
    .locals 2

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "toco"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static co()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->IS_XIAOMI:Z

    if-nez v0, :cond_0

    sget-boolean v0, Lb/c/a/c;->IS_HONGMI:Z

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static dn()Z
    .locals 2

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "lmi"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static do()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_video_snapshot_size_limit"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->do()Z

    move-result v0

    return v0
.end method

.method private static dp()Z
    .locals 2

    const-string v0, "ro.hardware.fp.fod"

    const/4 v1, 0x0

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    return v0
.end method

.method public static en()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->th:Z

    if-eqz v0, :cond_0

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static eo()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_xiaomi"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->eo()Z

    move-result v0

    return v0
.end method

.method public static fn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_hal_does_caf_when_flash_on"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->fn()Z

    move-result v0

    return v0
.end method

.method public static fo()I
    .locals 1
    .annotation build Lc/a;
        key = "camera_reduce_preview_flag"
        value = "0"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->fo()I

    move-result v0

    return v0
.end method

.method public static getCountry()Ljava/lang/String;
    .locals 2

    sget-object v0, Lcom/android/camera/Util;->sRegion:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    return-object v0

    :cond_0
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Locale;->getCountry()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static gn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_camera_hold_blur_background"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->gn()Z

    move-result v0

    return v0
.end method

.method public static hk()Z
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->hk()Z

    move-result v0

    return v0
.end method

.method public static hn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_hongmi"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->hn()Z

    move-result v0

    return v0
.end method

.method public static ho()I
    .locals 1
    .annotation build Lc/a;
        key = "camera_supported_asd"
        value = "0"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->ho()I

    move-result v0

    return v0
.end method

.method private static ia(Ljava/lang/String;)Z
    .locals 5

    sget-object v0, Lb/c/a/c;->Bh:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    move v3, v2

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v4, v0, v3

    invoke-static {p0, v4}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v4

    if-eqz v4, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_1
    return v2
.end method

.method public static in()Z
    .locals 2

    sget-boolean v0, Lb/c/a/c;->Gg:Z

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "India"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isGlobal()Z
    .locals 1

    sget-boolean v0, Lmiui/os/Build;->IS_INTERNATIONAL_BUILD:Z

    return v0
.end method

.method public static isMTKPlatform()Z
    .locals 4

    sget-object v0, Lb/c/a/c;->IS_MTK_PLATFORM:Ljava/util/concurrent/atomic/AtomicReference;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Optional;

    invoke-virtual {v0}, Ljava/util/Optional;->isPresent()Z

    move-result v0

    if-nez v0, :cond_1

    sget-object v0, Lb/c/a/c;->IS_MTK_PLATFORM:Ljava/util/concurrent/atomic/AtomicReference;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lb/c/a/c;->IS_MTK_PLATFORM:Ljava/util/concurrent/atomic/AtomicReference;

    invoke-virtual {v1}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Optional;

    invoke-virtual {v1}, Ljava/util/Optional;->isPresent()Z

    move-result v1

    if-nez v1, :cond_0

    invoke-static {}, Lb/c/a/c;->no()Ljava/lang/String;

    move-result-object v1

    sget-object v2, Lb/c/a/c;->IS_MTK_PLATFORM:Ljava/util/concurrent/atomic/AtomicReference;

    const-string v3, "mediatek"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    invoke-static {v1}, Ljava/util/Optional;->of(Ljava/lang/Object;)Ljava/util/Optional;

    move-result-object v1

    invoke-virtual {v2, v1}, Ljava/util/concurrent/atomic/AtomicReference;->set(Ljava/lang/Object;)V

    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    :cond_1
    :goto_0
    sget-object v0, Lb/c/a/c;->IS_MTK_PLATFORM:Ljava/util/concurrent/atomic/AtomicReference;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Optional;

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    return v0
.end method

.method public static isPad()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_pad"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->isPad()Z

    move-result v0

    return v0
.end method

.method public static isSupportSuperResolution()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_super_resolution"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->isSupportSuperResolution()Z

    move-result v0

    return v0
.end method

.method public static isSupportedOpticalZoom()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_support_optical_zoom"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->isSupportedOpticalZoom()Z

    move-result v0

    return v0
.end method

.method public static jn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_camera_isp_rotated"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->jn()Z

    move-result v0

    return v0
.end method

.method public static jo()Z
    .locals 2

    const/4 v0, -0x1

    const-string v1, "ro.boot.camera.config"

    invoke-static {v1, v0}, Landroid/os/SystemProperties;->getInt(Ljava/lang/String;I)I

    move-result v1

    if-eq v1, v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static kn()Z
    .locals 2

    sget-boolean v0, Lb/c/a/c;->qh:Z

    if-eqz v0, :cond_0

    const-string v0, "ro.boot.hwc"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "India"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static ko()Z
    .locals 1

    invoke-static {}, Lb/c/a/c;->Gm()[I

    move-result-object v0

    if-eqz v0, :cond_0

    array-length v0, v0

    if-lez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static ln()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->gh:Z

    if-nez v0, :cond_1

    sget-boolean v0, Lb/c/a/c;->hh:Z

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static lo()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_picture_watermark"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->lo()Z

    move-result v0

    return v0
.end method

.method public static mn()Z
    .locals 3

    sget-boolean v0, Lb/c/a/c;->Zg:Z

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1d

    const-string v2, "03"

    if-lt v0, v1, :cond_1

    const-string v0, "persist.vendor.camera.rearMain.vendorID"

    invoke-static {v0, v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0

    :cond_1
    const-string v0, "persist.camera.rearMain.vendorID"

    invoke-static {v0, v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static mo()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_realtime_manual_exposure_time"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->mo()Z

    move-result v0

    return v0
.end method

.method public static nn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_camera_lower_qrscan_frequency"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->nn()Z

    move-result v0

    return v0
.end method

.method public static no()Ljava/lang/String;
    .locals 1
    .annotation build Lc/a;
        key = "vendor"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->no()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static on()Z
    .locals 2

    sget-object v0, Lb/c/a/c;->Bg:Ljava/lang/String;

    const-string v1, "raphael"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Lmiui/os/Build;->MODEL:Ljava/lang/String;

    const-string v1, "Premium Edition"

    invoke-virtual {v0, v1}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static pn()Z
    .locals 1

    sget-boolean v0, Lb/c/a/c;->Zg:Z

    if-eqz v0, :cond_0

    invoke-static {}, Lb/c/a/c;->mn()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static qn()Z
    .locals 2

    invoke-static {}, Lb/c/a/c;->no()Ljava/lang/String;

    move-result-object v0

    const-string v1, "qcom"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public static rn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_rgb888_egl_prefer"
        value = "true"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->rn()Z

    move-result v0

    return v0
.end method

.method public static sn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_camera_replace_higher_cost_effect"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->sn()Z

    move-result v0

    return v0
.end method

.method public static tn()Z
    .locals 1
    .annotation build Lc/a;
        key = "is_camera_preview_with_subthread_looper"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->tn()Z

    move-result v0

    return v0
.end method

.method public static un()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_3d_face_beauty"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->un()Z

    move-result v0

    return v0
.end method

.method public static vn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_role"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->vn()Z

    move-result v0

    return v0
.end method

.method private static vo()Z
    .locals 1
    .annotation build Lc/a;
        key = "front_fingerprint_sensor"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->vo()Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {}, Lb/c/a/c;->dp()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static wn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_dynamic_light_spot"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->wn()Z

    move-result v0

    return v0
.end method

.method public static xm()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_boost_brightness"
        value = "false"
    .end annotation

    sget-boolean v0, Lb/c/a/c;->vh:Z

    if-nez v0, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->xm()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static xn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_front_beauty_mfnr"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->xn()Z

    move-result v0

    return v0
.end method

.method public static ym()Z
    .locals 1
    .annotation build Lc/a;
        key = "enable_algorithm_in_file_suffix"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->ym()Z

    move-result v0

    return v0
.end method

.method public static yn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_front_flash"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->yn()Z

    move-result v0

    return v0
.end method

.method public static zm()F
    .locals 1
    .annotation build Lc/a;
        key = "camera_exposure_compensation_steps_num"
        value = "12"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->zm()F

    move-result v0

    return v0
.end method

.method public static zn()Z
    .locals 1
    .annotation build Lc/a;
        key = "support_camera_gradienter"
        value = "false"
    .end annotation

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->getConfig()Lcom/mi/device/Common;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mi/device/Common;->zn()Z

    move-result v0

    return v0
.end method
