.class public final enum Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;
.super Ljava/lang/Enum;
.source "DocumentProcess.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "RotateFlags"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

.field public static final enum ROTATE_0:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

.field public static final enum ROTATE_180:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

.field public static final enum ROTATE_270:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

.field public static final enum ROTATE_90:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    new-instance v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    const/4 v1, 0x0

    const-string v2, "ROTATE_0"

    invoke-direct {v0, v2, v1}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_0:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    new-instance v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    const/4 v2, 0x1

    const-string v3, "ROTATE_90"

    invoke-direct {v0, v3, v2}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_90:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    new-instance v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    const/4 v3, 0x2

    const-string v4, "ROTATE_180"

    invoke-direct {v0, v4, v3}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_180:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    new-instance v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    const/4 v4, 0x3

    const-string v5, "ROTATE_270"

    invoke-direct {v0, v5, v4}, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_270:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    const/4 v0, 0x4

    new-array v0, v0, [Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    sget-object v5, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_0:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    aput-object v5, v0, v1

    sget-object v1, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_90:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_180:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    aput-object v1, v0, v3

    sget-object v1, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->ROTATE_270:Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    aput-object v1, v0, v4

    sput-object v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->$VALUES:[Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

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

.method public static valueOf(Ljava/lang/String;)Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;
    .locals 1

    const-class v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    return-object p0
.end method

.method public static values()[Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;
    .locals 1

    sget-object v0, Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->$VALUES:[Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    invoke-virtual {v0}, [Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/xiaomi/ocr/sdk/imgprocess/DocumentProcess$RotateFlags;

    return-object v0
.end method
