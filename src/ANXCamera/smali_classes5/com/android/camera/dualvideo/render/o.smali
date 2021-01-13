.class public final synthetic Lcom/android/camera/dualvideo/render/o;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

.field private final synthetic ug:Landroid/util/SparseArray;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;Landroid/util/SparseArray;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/o;->tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    iput-object p2, p0, Lcom/android/camera/dualvideo/render/o;->ug:Landroid/util/SparseArray;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/o;->tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/o;->ug:Landroid/util/SparseArray;

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-virtual {v0, p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->a(Landroid/util/SparseArray;Lcom/android/camera/dualvideo/render/IRenderable;)V

    return-void
.end method
