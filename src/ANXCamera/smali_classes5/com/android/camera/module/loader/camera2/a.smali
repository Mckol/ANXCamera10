.class public final synthetic Lcom/android/camera/module/loader/camera2/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lio/reactivex/functions/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/loader/camera2/Camera2OpenManager;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/a;->tg:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/a;->tg:Lcom/android/camera/module/loader/camera2/Camera2OpenManager;

    check-cast p1, Lcom/android/camera/module/loader/camera2/Camera2Result;

    invoke-static {p0, p1}, Lcom/android/camera/module/loader/camera2/Camera2OpenManager;->a(Lcom/android/camera/module/loader/camera2/Camera2OpenManager;Lcom/android/camera/module/loader/camera2/Camera2Result;)V

    return-void
.end method