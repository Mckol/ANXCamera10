.class final enum Lmiui/io/ResettableInputStream$Type;
.super Ljava/lang/Enum;
.source "ResettableInputStream.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/io/ResettableInputStream;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x401a
    name = "Type"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lmiui/io/ResettableInputStream$Type;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lmiui/io/ResettableInputStream$Type;

.field public static final enum Asset:Lmiui/io/ResettableInputStream$Type;

.field public static final enum ByteArray:Lmiui/io/ResettableInputStream$Type;

.field public static final enum File:Lmiui/io/ResettableInputStream$Type;

.field public static final enum Uri:Lmiui/io/ResettableInputStream$Type;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    new-instance v0, Lmiui/io/ResettableInputStream$Type;

    const/4 v1, 0x0

    const-string v2, "File"

    invoke-direct {v0, v2, v1}, Lmiui/io/ResettableInputStream$Type;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/io/ResettableInputStream$Type;->File:Lmiui/io/ResettableInputStream$Type;

    new-instance v0, Lmiui/io/ResettableInputStream$Type;

    const/4 v2, 0x1

    const-string v3, "Uri"

    invoke-direct {v0, v3, v2}, Lmiui/io/ResettableInputStream$Type;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/io/ResettableInputStream$Type;->Uri:Lmiui/io/ResettableInputStream$Type;

    new-instance v0, Lmiui/io/ResettableInputStream$Type;

    const/4 v3, 0x2

    const-string v4, "Asset"

    invoke-direct {v0, v4, v3}, Lmiui/io/ResettableInputStream$Type;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/io/ResettableInputStream$Type;->Asset:Lmiui/io/ResettableInputStream$Type;

    new-instance v0, Lmiui/io/ResettableInputStream$Type;

    const/4 v4, 0x3

    const-string v5, "ByteArray"

    invoke-direct {v0, v5, v4}, Lmiui/io/ResettableInputStream$Type;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lmiui/io/ResettableInputStream$Type;->ByteArray:Lmiui/io/ResettableInputStream$Type;

    const/4 v0, 0x4

    new-array v0, v0, [Lmiui/io/ResettableInputStream$Type;

    sget-object v5, Lmiui/io/ResettableInputStream$Type;->File:Lmiui/io/ResettableInputStream$Type;

    aput-object v5, v0, v1

    sget-object v1, Lmiui/io/ResettableInputStream$Type;->Uri:Lmiui/io/ResettableInputStream$Type;

    aput-object v1, v0, v2

    sget-object v1, Lmiui/io/ResettableInputStream$Type;->Asset:Lmiui/io/ResettableInputStream$Type;

    aput-object v1, v0, v3

    sget-object v1, Lmiui/io/ResettableInputStream$Type;->ByteArray:Lmiui/io/ResettableInputStream$Type;

    aput-object v1, v0, v4

    sput-object v0, Lmiui/io/ResettableInputStream$Type;->$VALUES:[Lmiui/io/ResettableInputStream$Type;

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

.method public static valueOf(Ljava/lang/String;)Lmiui/io/ResettableInputStream$Type;
    .locals 1

    const-class v0, Lmiui/io/ResettableInputStream$Type;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lmiui/io/ResettableInputStream$Type;

    return-object p0
.end method

.method public static values()[Lmiui/io/ResettableInputStream$Type;
    .locals 1

    sget-object v0, Lmiui/io/ResettableInputStream$Type;->$VALUES:[Lmiui/io/ResettableInputStream$Type;

    invoke-virtual {v0}, [Lmiui/io/ResettableInputStream$Type;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lmiui/io/ResettableInputStream$Type;

    return-object v0
.end method