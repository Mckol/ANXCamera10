.class public Ljava/miui/autoinstall/config/pm/MarketInstallObserver;
.super Landroid/os/ResultReceiver;
.source "MarketInstallObserver.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Ljava/miui/autoinstall/config/pm/MarketInstallObserver$a;
    }
.end annotation


# static fields
.field private static final Aa:I = 0x0

.field private static final Ba:I = 0x1

.field private static final Ca:I = 0x2

.field private static final Da:Ljava/lang/String; = "returnCode"

.field private static final KEY_PACKAGE_NAME:Ljava/lang/String; = "packageName"


# instance fields
.field private final mListener:Ljava/miui/autoinstall/config/pm/a;


# direct methods
.method public constructor <init>(Ljava/miui/autoinstall/config/pm/a;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, v0}, Landroid/os/ResultReceiver;-><init>(Landroid/os/Handler;)V

    iput-object p1, p0, Ljava/miui/autoinstall/config/pm/MarketInstallObserver;->mListener:Ljava/miui/autoinstall/config/pm/a;

    return-void
.end method

.method private static a(Landroid/os/Bundle;)I
    .locals 1

    const-string v0, "returnCode"

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result p0

    return p0
.end method

.method static synthetic access$000(Ljava/lang/String;I)Landroid/os/Bundle;
    .locals 0

    invoke-static {p0, p1}, Ljava/miui/autoinstall/config/pm/MarketInstallObserver;->h(Ljava/lang/String;I)Landroid/os/Bundle;

    move-result-object p0

    return-object p0
.end method

.method private static b(Landroid/os/Bundle;)Ljava/lang/String;
    .locals 1

    const-string v0, "packageName"

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static h(Ljava/lang/String;I)Landroid/os/Bundle;
    .locals 2

    new-instance v0, Landroid/os/Bundle;

    const/4 v1, 0x2

    invoke-direct {v0, v1}, Landroid/os/Bundle;-><init>(I)V

    const-string v1, "packageName"

    invoke-virtual {v0, v1, p0}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    const-string p0, "returnCode"

    invoke-virtual {v0, p0, p1}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    return-object v0
.end method


# virtual methods
.method protected onReceiveResult(ILandroid/os/Bundle;)V
    .locals 1

    invoke-super {p0, p1, p2}, Landroid/os/ResultReceiver;->onReceiveResult(ILandroid/os/Bundle;)V

    iget-object p0, p0, Ljava/miui/autoinstall/config/pm/MarketInstallObserver;->mListener:Ljava/miui/autoinstall/config/pm/a;

    if-eqz p0, :cond_3

    if-eqz p1, :cond_2

    const/4 v0, 0x1

    if-eq p1, v0, :cond_1

    const/4 p2, 0x2

    if-eq p1, p2, :cond_0

    goto :goto_0

    :cond_0
    invoke-interface {p0}, Ljava/miui/autoinstall/config/pm/a;->onServiceDead()V

    goto :goto_0

    :cond_1
    invoke-static {p2}, Ljava/miui/autoinstall/config/pm/MarketInstallObserver;->b(Landroid/os/Bundle;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p2}, Ljava/miui/autoinstall/config/pm/MarketInstallObserver;->a(Landroid/os/Bundle;)I

    move-result p2

    invoke-interface {p0, p1, p2}, Ljava/miui/autoinstall/config/pm/a;->onRefuseInstall(Ljava/lang/String;I)V

    goto :goto_0

    :cond_2
    invoke-static {p2}, Ljava/miui/autoinstall/config/pm/MarketInstallObserver;->b(Landroid/os/Bundle;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p2}, Ljava/miui/autoinstall/config/pm/MarketInstallObserver;->a(Landroid/os/Bundle;)I

    move-result p2

    invoke-interface {p0, p1, p2}, Ljava/miui/autoinstall/config/pm/a;->packageInstalled(Ljava/lang/String;I)V

    :cond_3
    :goto_0
    return-void
.end method
