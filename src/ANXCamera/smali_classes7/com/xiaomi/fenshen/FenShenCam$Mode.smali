.class public final enum Lcom/xiaomi/fenshen/FenShenCam$Mode;
.super Ljava/lang/Enum;
.source "FenShenCam.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/xiaomi/fenshen/FenShenCam;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "Mode"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/xiaomi/fenshen/FenShenCam$Mode;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/xiaomi/fenshen/FenShenCam$Mode;

.field public static final enum PHOTO:Lcom/xiaomi/fenshen/FenShenCam$Mode;

.field public static final enum VIDEO:Lcom/xiaomi/fenshen/FenShenCam$Mode;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Mode;

    const/4 v1, 0x0

    const-string v2, "PHOTO"

    invoke-direct {v0, v2, v1}, Lcom/xiaomi/fenshen/FenShenCam$Mode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Mode;->PHOTO:Lcom/xiaomi/fenshen/FenShenCam$Mode;

    new-instance v0, Lcom/xiaomi/fenshen/FenShenCam$Mode;

    const/4 v2, 0x1

    const-string v3, "VIDEO"

    invoke-direct {v0, v3, v2}, Lcom/xiaomi/fenshen/FenShenCam$Mode;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Mode;->VIDEO:Lcom/xiaomi/fenshen/FenShenCam$Mode;

    const/4 v0, 0x2

    new-array v0, v0, [Lcom/xiaomi/fenshen/FenShenCam$Mode;

    sget-object v3, Lcom/xiaomi/fenshen/FenShenCam$Mode;->PHOTO:Lcom/xiaomi/fenshen/FenShenCam$Mode;

    aput-object v3, v0, v1

    sget-object v1, Lcom/xiaomi/fenshen/FenShenCam$Mode;->VIDEO:Lcom/xiaomi/fenshen/FenShenCam$Mode;

    aput-object v1, v0, v2

    sput-object v0, Lcom/xiaomi/fenshen/FenShenCam$Mode;->$VALUES:[Lcom/xiaomi/fenshen/FenShenCam$Mode;

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

.method public static valueOf(Ljava/lang/String;)Lcom/xiaomi/fenshen/FenShenCam$Mode;
    .locals 1

    const-class v0, Lcom/xiaomi/fenshen/FenShenCam$Mode;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/xiaomi/fenshen/FenShenCam$Mode;

    return-object p0
.end method

.method public static values()[Lcom/xiaomi/fenshen/FenShenCam$Mode;
    .locals 1

    sget-object v0, Lcom/xiaomi/fenshen/FenShenCam$Mode;->$VALUES:[Lcom/xiaomi/fenshen/FenShenCam$Mode;

    invoke-virtual {v0}, [Lcom/xiaomi/fenshen/FenShenCam$Mode;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/xiaomi/fenshen/FenShenCam$Mode;

    return-object v0
.end method
