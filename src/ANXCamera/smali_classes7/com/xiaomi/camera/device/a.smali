.class public final synthetic Lcom/xiaomi/camera/device/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/concurrent/Callable;


# instance fields
.field private final synthetic tg:Lcom/xiaomi/camera/device/callable/CameraCallable;


# direct methods
.method public synthetic constructor <init>(Lcom/xiaomi/camera/device/callable/CameraCallable;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/xiaomi/camera/device/a;->tg:Lcom/xiaomi/camera/device/callable/CameraCallable;

    return-void
.end method


# virtual methods
.method public final call()Ljava/lang/Object;
    .locals 0

    iget-object p0, p0, Lcom/xiaomi/camera/device/a;->tg:Lcom/xiaomi/camera/device/callable/CameraCallable;

    invoke-virtual {p0}, Lcom/xiaomi/camera/device/callable/CameraCallable;->call()Lcom/xiaomi/camera/device/callable/CallableReturn;

    move-result-object p0

    return-object p0
.end method
