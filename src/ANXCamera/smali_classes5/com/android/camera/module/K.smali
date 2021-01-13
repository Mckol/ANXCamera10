.class public final synthetic Lcom/android/camera/module/K;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera2/Camera2Proxy$FocusCallback;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/DualVideoModule;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/DualVideoModule;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/K;->tg:Lcom/android/camera/module/DualVideoModule;

    return-void
.end method


# virtual methods
.method public final onFocusStateChanged(Lcom/android/camera/module/loader/camera2/FocusTask;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/K;->tg:Lcom/android/camera/module/DualVideoModule;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/DualVideoModule;->a(Lcom/android/camera/module/loader/camera2/FocusTask;)V

    return-void
.end method
