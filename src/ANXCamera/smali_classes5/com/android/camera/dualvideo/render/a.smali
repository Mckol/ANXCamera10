.class public final synthetic Lcom/android/camera/dualvideo/render/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/render/IRenderable;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/a;->tg:Lcom/android/camera/dualvideo/render/IRenderable;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/a;->tg:Lcom/android/camera/dualvideo/render/IRenderable;

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->d(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/UserSelectData;)V

    return-void
.end method
