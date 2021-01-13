.class public final synthetic Lcom/android/camera/dualvideo/render/K;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/K;->tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/K;->tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->y(Lcom/android/camera/dualvideo/render/IRenderable;)V

    return-void
.end method
