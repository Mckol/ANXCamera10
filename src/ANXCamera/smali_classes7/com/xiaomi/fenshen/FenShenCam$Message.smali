.class public final enum Lcom/xiaomi/fenshen/FenShenCam$Message;
.super Ljava/lang/Enum;
.source "FenShenCam.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/xiaomi/fenshen/FenShenCam;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "Message"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/xiaomi/fenshen/FenShenCam$Message;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum ALIGN_OK:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum ALIGN_TOO_LARGE_OR_FAILED:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum ALIGN_WARNING:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum DYNAMIC_SCENE:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum ERROR_INIT:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum ERROR_RUNTIME:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum MOVE_OUTSIDE:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum NO_PERSON:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum PREVIEW_NO_PERSON:Lcom/xiaomi/fenshen/FenShenCam$Message;

.field public static final enum START:Lcom/xiaomi/fenshen/FenShenCam$Message;


# direct methods
.method static constructor <clinit>()V
    .locals 12

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v1, 0x0

    const-string v2, "START"

    invoke-direct {v0, v2, v1}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->START:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v2, 0x1

    const-string v3, "PREVIEW_NO_PERSON"

    invoke-direct {v0, v3, v2}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->PREVIEW_NO_PERSON:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v3, 0x2

    const-string v4, "ALIGN_OK"

    invoke-direct {v0, v4, v3}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->ALIGN_OK:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v4, 0x3

    const-string v5, "ALIGN_WARNING"

    invoke-direct {v0, v5, v4}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->ALIGN_WARNING:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v5, 0x4

    const-string v6, "ALIGN_TOO_LARGE_OR_FAILED"

    invoke-direct {v0, v6, v5}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->ALIGN_TOO_LARGE_OR_FAILED:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v6, 0x5

    const-string v7, "NO_PERSON"

    invoke-direct {v0, v7, v6}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->NO_PERSON:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v7, 0x6

    const-string v8, "MOVE_OUTSIDE"

    invoke-direct {v0, v8, v7}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->MOVE_OUTSIDE:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/4 v8, 0x7

    const-string v9, "DYNAMIC_SCENE"

    invoke-direct {v0, v9, v8}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->DYNAMIC_SCENE:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/16 v9, 0x8

    const-string v10, "ERROR_INIT"

    invoke-direct {v0, v10, v9}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->ERROR_INIT:Lcom/xiaomi/fenshen/FenShenCam$Message;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/16 v10, 0x9

    const-string v11, "ERROR_RUNTIME"

    invoke-direct {v0, v11, v10}, Lcom/xiaomi/fenshen/FenShenCam$Message;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->ERROR_RUNTIME:Lcom/xiaomi/fenshen/FenShenCam$Message;

    const/16 v0, 0xa

    new-array v0, v0, [Lcom/xiaomi/fenshen/FenShenCam$Message;

    sget-object v11, Lcom/xiaomi/fenshen/FenShenCam$Message;->START:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v11, v0, v1

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->PREVIEW_NO_PERSON:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v2

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->ALIGN_OK:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v3

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->ALIGN_WARNING:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v4

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->ALIGN_TOO_LARGE_OR_FAILED:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v5

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->NO_PERSON:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v6

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->MOVE_OUTSIDE:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v7

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->DYNAMIC_SCENE:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v8

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->ERROR_INIT:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v9

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Message;->ERROR_RUNTIME:Lcom/xiaomi/fenshen/FenShenCam$Message;

    aput-object v1, v0, v10

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->$VALUES:[Lcom/xiaomi/fenshen/FenShenCam$Message;

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

.method public static valueOf(Ljava/lang/String;)Lcom/xiaomi/fenshen/FenShenCam$Message;
    .locals 1

    const-class v0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/xiaomi/fenshen/FenShenCam$Message;

    return-object p0
.end method

.method public static values()[Lcom/xiaomi/fenshen/FenShenCam$Message;
    .locals 1

    sget-object v0, Lcom/xiaomi/fenshen/FenShenCam$Message;->$VALUES:[Lcom/xiaomi/fenshen/FenShenCam$Message;

    invoke-virtual {v0}, [Lcom/xiaomi/fenshen/FenShenCam$Message;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/xiaomi/fenshen/FenShenCam$Message;

    return-object v0
.end method
