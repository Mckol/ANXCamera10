.class public final synthetic Lcom/android/camera/dualvideo/render/l;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/render/IRenderable;

.field private final synthetic ug:Ljava/util/concurrent/ConcurrentHashMap;

.field private final synthetic vg:Lcom/android/camera/dualvideo/CameraIDManager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/render/IRenderable;Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/CameraIDManager;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/l;->tg:Lcom/android/camera/dualvideo/render/IRenderable;

    iput-object p2, p0, Lcom/android/camera/dualvideo/render/l;->ug:Ljava/util/concurrent/ConcurrentHashMap;

    iput-object p3, p0, Lcom/android/camera/dualvideo/render/l;->vg:Lcom/android/camera/dualvideo/CameraIDManager;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/l;->tg:Lcom/android/camera/dualvideo/render/IRenderable;

    iget-object v1, p0, Lcom/android/camera/dualvideo/render/l;->ug:Ljava/util/concurrent/ConcurrentHashMap;

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/l;->vg:Lcom/android/camera/dualvideo/CameraIDManager;

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-static {v0, v1, p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->a(Lcom/android/camera/dualvideo/render/IRenderable;Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/CameraIDManager;Lcom/android/camera/dualvideo/UserSelectData;)V

    return-void
.end method
