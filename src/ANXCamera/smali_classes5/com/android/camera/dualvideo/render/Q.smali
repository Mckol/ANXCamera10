.class public final synthetic Lcom/android/camera/dualvideo/render/Q;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/gallery3d/ui/GLCanvas;


# direct methods
.method public synthetic constructor <init>(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/Q;->tg:Lcom/android/gallery3d/ui/GLCanvas;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/Q;->tg:Lcom/android/gallery3d/ui/GLCanvas;

    check-cast p1, Lcom/android/camera/dualvideo/render/IMiscRenderable;

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->a(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/dualvideo/render/IMiscRenderable;)V

    return-void
.end method
