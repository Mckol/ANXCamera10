.class Lmiuix/view/ExtendedVibrator;
.super Ljava/lang/Object;
.source "ExtendedVibrator.java"

# interfaces
.implements Lmiuix/view/HapticFeedbackProvider;


# annotations
.annotation build Landroidx/annotation/Keep;
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "ExtendedVibrator"


# direct methods
.method static constructor <clinit>()V
    .locals 0

    invoke-static {}, Lmiuix/view/ExtendedVibrator;->initialize()V

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static initialize()V
    .locals 2

    sget v0, Lmiuix/view/PlatformConstants;->VERSION:I

    const-string v1, "ExtendedVibrator"

    if-gez v0, :cond_0

    const-string v0, "MiuiHapticFeedbackConstants not found."

    invoke-static {v1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    new-instance v0, Lmiuix/view/ExtendedVibrator;

    invoke-direct {v0}, Lmiuix/view/ExtendedVibrator;-><init>()V

    invoke-static {v0}, Lmiuix/view/HapticCompat;->registerProvider(Lmiuix/view/HapticFeedbackProvider;)V

    const-string v0, "setup ExtendedVibrator success."

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method


# virtual methods
.method public performHapticFeedback(Landroid/view/View;I)Z
    .locals 0

    sget p0, Lmiuix/view/HapticFeedbackConstants;->MIUI_VIRTUAL_RELEASE:I

    if-ne p2, p0, :cond_0

    const/4 p0, 0x2

    invoke-virtual {p1, p0}, Landroid/view/View;->performHapticFeedback(I)Z

    move-result p0

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method
