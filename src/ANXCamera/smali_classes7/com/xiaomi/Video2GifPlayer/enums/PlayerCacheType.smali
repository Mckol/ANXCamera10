.class public final enum Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;
.super Ljava/lang/Enum;
.source "PlayerCacheType.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

.field public static final enum PlayerCacheAll:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

.field public static final enum PlayerCacheFile:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

.field public static final enum PlayerCacheMemory:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

.field public static final enum PlayerCacheNo:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;


# instance fields
.field private nCode:I


# direct methods
.method static constructor <clinit>()V
    .locals 6

    new-instance v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    const/4 v1, 0x0

    const-string v2, "PlayerCacheNo"

    invoke-direct {v0, v2, v1, v1}, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;-><init>(Ljava/lang/String;II)V

    sput-object v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheNo:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    new-instance v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    const/4 v2, 0x1

    const-string v3, "PlayerCacheFile"

    invoke-direct {v0, v3, v2, v2}, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;-><init>(Ljava/lang/String;II)V

    sput-object v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheFile:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    new-instance v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    const/4 v3, 0x2

    const-string v4, "PlayerCacheMemory"

    invoke-direct {v0, v4, v3, v3}, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;-><init>(Ljava/lang/String;II)V

    sput-object v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheMemory:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    new-instance v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    const/4 v4, 0x3

    const-string v5, "PlayerCacheAll"

    invoke-direct {v0, v5, v4, v4}, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;-><init>(Ljava/lang/String;II)V

    sput-object v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheAll:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    const/4 v0, 0x4

    new-array v0, v0, [Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    sget-object v5, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheNo:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    aput-object v5, v0, v1

    sget-object v1, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheFile:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheMemory:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    aput-object v1, v0, v3

    sget-object v1, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheAll:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    aput-object v1, v0, v4

    sput-object v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->$VALUES:[Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;II)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)V"
        }
    .end annotation

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    iput p3, p0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->nCode:I

    return-void
.end method

.method public static int2enum(I)Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;
    .locals 6

    sget-object v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->PlayerCacheNo:Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    invoke-static {}, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->values()[Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    move-result-object v1

    array-length v2, v1

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v2, :cond_1

    aget-object v4, v1, v3

    invoke-virtual {v4}, Ljava/lang/Enum;->ordinal()I

    move-result v5

    if-ne v5, p0, :cond_0

    move-object v0, v4

    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_1
    return-object v0
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;
    .locals 1

    const-class v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    return-object p0
.end method

.method public static values()[Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;
    .locals 1

    sget-object v0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->$VALUES:[Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    invoke-virtual {v0}, [Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;

    return-object v0
.end method


# virtual methods
.method public toString()Ljava/lang/String;
    .locals 0

    iget p0, p0, Lcom/xiaomi/Video2GifPlayer/enums/PlayerCacheType;->nCode:I

    invoke-static {p0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
