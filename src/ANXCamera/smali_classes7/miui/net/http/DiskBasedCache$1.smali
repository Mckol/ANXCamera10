.class Lmiui/net/http/DiskBasedCache$1;
.super Lmiui/util/SoftReferenceSingleton;
.source "DiskBasedCache.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/net/http/DiskBasedCache;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lmiui/util/SoftReferenceSingleton<",
        "Lmiui/net/http/DiskBasedCache;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lmiui/util/SoftReferenceSingleton;-><init>()V

    return-void
.end method


# virtual methods
.method protected bridge synthetic createInstance()Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0}, Lmiui/net/http/DiskBasedCache$1;->createInstance()Lmiui/net/http/DiskBasedCache;

    move-result-object p0

    return-object p0
.end method

.method protected createInstance()Lmiui/net/http/DiskBasedCache;
    .locals 0

    new-instance p0, Lmiui/net/http/DiskBasedCache;

    invoke-direct {p0}, Lmiui/net/http/DiskBasedCache;-><init>()V

    invoke-virtual {p0}, Lmiui/net/http/DiskBasedCache;->initialize()V

    return-object p0
.end method
