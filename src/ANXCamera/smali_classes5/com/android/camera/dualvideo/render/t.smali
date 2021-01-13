.class public final synthetic Lcom/android/camera/dualvideo/render/t;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Predicate;


# instance fields
.field private final synthetic tg:Landroid/util/SparseArray;

.field private final synthetic ug:I


# direct methods
.method public synthetic constructor <init>(Landroid/util/SparseArray;I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/t;->tg:Landroid/util/SparseArray;

    iput p2, p0, Lcom/android/camera/dualvideo/render/t;->ug:I

    return-void
.end method


# virtual methods
.method public final test(Ljava/lang/Object;)Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/t;->tg:Landroid/util/SparseArray;

    iget p0, p0, Lcom/android/camera/dualvideo/render/t;->ug:I

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-static {v0, p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->a(Landroid/util/SparseArray;ILcom/android/camera/dualvideo/render/IRenderable;)Z

    move-result p0

    return p0
.end method
