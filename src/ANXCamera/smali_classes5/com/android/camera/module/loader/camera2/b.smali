.class public final synthetic Lcom/android/camera/module/loader/camera2/b;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lio/reactivex/functions/Function;


# instance fields
.field private final synthetic tg:Ljava/util/concurrent/ConcurrentHashMap;


# direct methods
.method public synthetic constructor <init>(Ljava/util/concurrent/ConcurrentHashMap;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/loader/camera2/b;->tg:Ljava/util/concurrent/ConcurrentHashMap;

    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/loader/camera2/b;->tg:Ljava/util/concurrent/ConcurrentHashMap;

    check-cast p1, Lcom/android/camera/module/loader/camera2/Camera2Result;

    invoke-static {p0, p1}, Lcom/android/camera/module/loader/camera2/Camera2OpenManager;->a(Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/module/loader/camera2/Camera2Result;)Lio/reactivex/SingleSource;

    move-result-object p0

    return-object p0
.end method
