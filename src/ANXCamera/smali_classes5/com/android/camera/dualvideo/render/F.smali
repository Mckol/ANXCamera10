.class public final synthetic Lcom/android/camera/dualvideo/render/F;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Predicate;


# instance fields
.field private final synthetic tg:I


# direct methods
.method public synthetic constructor <init>(I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lcom/android/camera/dualvideo/render/F;->tg:I

    return-void
.end method


# virtual methods
.method public final test(Ljava/lang/Object;)Z
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/render/F;->tg:I

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->a(ILcom/android/camera/dualvideo/UserSelectData;)Z

    move-result p0

    return p0
.end method
