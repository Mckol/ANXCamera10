.class public final synthetic Lcom/xiaomi/camera/device/callable/g;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/xiaomi/camera/device/callable/OpenCameraCallable;


# direct methods
.method public synthetic constructor <init>(Lcom/xiaomi/camera/device/callable/OpenCameraCallable;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/xiaomi/camera/device/callable/g;->tg:Lcom/xiaomi/camera/device/callable/OpenCameraCallable;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/xiaomi/camera/device/callable/g;->tg:Lcom/xiaomi/camera/device/callable/OpenCameraCallable;

    invoke-virtual {p0}, Lcom/xiaomi/camera/device/callable/OpenCameraCallable;->yo()V

    return-void
.end method
