.class public Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;
.super Lcom/miui/internal/variable/api/AbstractExtension;
.source "Android_Widget_ListView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/variable/api/v29/Android_Widget_ListView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Extension"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/miui/internal/variable/api/AbstractExtension<",
        "Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Interface;",
        ">;"
    }
.end annotation


# static fields
.field private static final INSTANCE:Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;

    invoke-direct {v0}, Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;-><init>()V

    sput-object v0, Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;->INSTANCE:Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/miui/internal/variable/api/AbstractExtension;-><init>()V

    return-void
.end method

.method public static get()Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;
    .locals 1

    sget-object v0, Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;->INSTANCE:Lcom/miui/internal/variable/api/v29/Android_Widget_ListView$Extension;

    return-object v0
.end method
