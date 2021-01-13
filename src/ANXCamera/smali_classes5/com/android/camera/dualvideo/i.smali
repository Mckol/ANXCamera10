.class public final synthetic Lcom/android/camera/dualvideo/i;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Predicate;


# instance fields
.field private final synthetic tg:F

.field private final synthetic ug:F


# direct methods
.method public synthetic constructor <init>(FF)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/android/camera/dualvideo/i;->tg:F

    iput p2, p0, Lcom/android/camera/dualvideo/i;->ug:F

    return-void
.end method


# virtual methods
.method public final test(Ljava/lang/Object;)Z
    .locals 1

    iget v0, p0, Lcom/android/camera/dualvideo/i;->tg:F

    iget p0, p0, Lcom/android/camera/dualvideo/i;->ug:F

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-static {v0, p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->b(FFLcom/android/camera/dualvideo/render/IRenderable;)Z

    move-result p0

    return p0
.end method
