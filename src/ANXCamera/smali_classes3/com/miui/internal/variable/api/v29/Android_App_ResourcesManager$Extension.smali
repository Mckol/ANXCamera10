.class public Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;
.super Lcom/miui/internal/variable/api/AbstractExtension;
.source "Android_App_ResourcesManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Extension"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/miui/internal/variable/api/AbstractExtension<",
        "Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Interface;",
        ">;"
    }
.end annotation


# static fields
.field private static final INSTANCE:Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;

    invoke-direct {v0}, Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;-><init>()V

    sput-object v0, Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;->INSTANCE:Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/miui/internal/variable/api/AbstractExtension;-><init>()V

    return-void
.end method

.method public static get()Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;
    .locals 1

    sget-object v0, Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;->INSTANCE:Lcom/miui/internal/variable/api/v29/Android_App_ResourcesManager$Extension;

    return-object v0
.end method
