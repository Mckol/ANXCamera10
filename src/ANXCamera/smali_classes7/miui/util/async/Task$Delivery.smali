.class final enum Lmiui/util/async/Task$Delivery;
.super Ljava/lang/Enum;
.source "Task.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/util/async/Task;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4018
    name = "Delivery"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lmiui/util/async/Task$Delivery;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lmiui/util/async/Task$Delivery;

.field public static final enum Finalize:Lmiui/util/async/Task$Delivery;

.field public static final enum Prepare:Lmiui/util/async/Task$Delivery;

.field public static final enum Progress:Lmiui/util/async/Task$Delivery;

.field public static final enum Result:Lmiui/util/async/Task$Delivery;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    new-instance v0, Lmiui/util/async/Task$Delivery;

    const/4 v1, 0x0

    const-string v2, "Prepare"

    invoke-direct {v0, v2, v1}, Lmiui/util/async/Task$Delivery;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/util/async/Task$Delivery;->Prepare:Lmiui/util/async/Task$Delivery;

    new-instance v0, Lmiui/util/async/Task$Delivery;

    const/4 v2, 0x1

    const-string v3, "Result"

    invoke-direct {v0, v3, v2}, Lmiui/util/async/Task$Delivery;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/util/async/Task$Delivery;->Result:Lmiui/util/async/Task$Delivery;

    new-instance v0, Lmiui/util/async/Task$Delivery;

    const/4 v3, 0x2

    const-string v4, "Progress"

    invoke-direct {v0, v4, v3}, Lmiui/util/async/Task$Delivery;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/util/async/Task$Delivery;->Progress:Lmiui/util/async/Task$Delivery;

    new-instance v0, Lmiui/util/async/Task$Delivery;

    const/4 v4, 0x3

    const-string v5, "Finalize"

    invoke-direct {v0, v5, v4}, Lmiui/util/async/Task$Delivery;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/util/async/Task$Delivery;->Finalize:Lmiui/util/async/Task$Delivery;

    const/4 v0, 0x4

    new-array v0, v0, [Lmiui/util/async/Task$Delivery;

    sget-object v5, Lmiui/util/async/Task$Delivery;->Prepare:Lmiui/util/async/Task$Delivery;

    aput-object v5, v0, v1

    sget-object v1, Lmiui/util/async/Task$Delivery;->Result:Lmiui/util/async/Task$Delivery;

    aput-object v1, v0, v2

    sget-object v1, Lmiui/util/async/Task$Delivery;->Progress:Lmiui/util/async/Task$Delivery;

    aput-object v1, v0, v3

    sget-object v1, Lmiui/util/async/Task$Delivery;->Finalize:Lmiui/util/async/Task$Delivery;

    aput-object v1, v0, v4

    sput-object v0, Lmiui/util/async/Task$Delivery;->$VALUES:[Lmiui/util/async/Task$Delivery;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lmiui/util/async/Task$Delivery;
    .locals 1

    const-class v0, Lmiui/util/async/Task$Delivery;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lmiui/util/async/Task$Delivery;

    return-object p0
.end method

.method public static values()[Lmiui/util/async/Task$Delivery;
    .locals 1

    sget-object v0, Lmiui/util/async/Task$Delivery;->$VALUES:[Lmiui/util/async/Task$Delivery;

    invoke-virtual {v0}, [Lmiui/util/async/Task$Delivery;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lmiui/util/async/Task$Delivery;

    return-object v0
.end method
