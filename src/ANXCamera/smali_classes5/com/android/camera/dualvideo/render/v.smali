.class public final synthetic Lcom/android/camera/dualvideo/render/v;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Predicate;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/render/IRenderable;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/v;->tg:Lcom/android/camera/dualvideo/render/IRenderable;

    return-void
.end method


# virtual methods
.method public final test(Ljava/lang/Object;)Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/v;->tg:Lcom/android/camera/dualvideo/render/IRenderable;

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->c(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/UserSelectData;)Z

    move-result p0

    return p0
.end method
