.class public final enum Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;
.super Ljava/lang/Enum;
.source "EffectMediaPlayer.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "SurfaceGravity"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

.field public static final enum SurfaceGravityResizeAspect:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

.field public static final enum SurfaceGravityResizeAspectFill:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

.field public static final enum SurfaceGravityResizeAspectFit:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;


# instance fields
.field private code:I


# direct methods
.method static constructor <clinit>()V
    .locals 5

    new-instance v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    const/4 v1, 0x0

    const-string v2, "SurfaceGravityResizeAspect"

    invoke-direct {v0, v2, v1, v1}, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;-><init>(Ljava/lang/String;II)V

    sput-object v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->SurfaceGravityResizeAspect:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    new-instance v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    const/4 v2, 0x1

    const-string v3, "SurfaceGravityResizeAspectFit"

    invoke-direct {v0, v3, v2, v2}, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;-><init>(Ljava/lang/String;II)V

    sput-object v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->SurfaceGravityResizeAspectFit:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    new-instance v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    const/4 v3, 0x2

    const-string v4, "SurfaceGravityResizeAspectFill"

    invoke-direct {v0, v4, v3, v3}, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;-><init>(Ljava/lang/String;II)V

    sput-object v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->SurfaceGravityResizeAspectFill:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    const/4 v0, 0x3

    new-array v0, v0, [Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    sget-object v4, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->SurfaceGravityResizeAspect:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    aput-object v4, v0, v1

    sget-object v1, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->SurfaceGravityResizeAspectFit:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->SurfaceGravityResizeAspectFill:Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    aput-object v1, v0, v3

    sput-object v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->$VALUES:[Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

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

    iput p3, p0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->code:I

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;
    .locals 1

    const-class v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    return-object p0
.end method

.method public static values()[Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;
    .locals 1

    sget-object v0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->$VALUES:[Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    invoke-virtual {v0}, [Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;

    return-object v0
.end method


# virtual methods
.method public toInt()I
    .locals 0

    iget p0, p0, Lcom/xiaomi/Video2GifEditer/EffectMediaPlayer$SurfaceGravity;->code:I

    return p0
.end method
