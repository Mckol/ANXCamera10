.class public final synthetic Lcom/android/camera/dualvideo/n;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Ljava/lang/StringBuilder;


# direct methods
.method public synthetic constructor <init>(Ljava/lang/StringBuilder;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/n;->tg:Ljava/lang/StringBuilder;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/n;->tg:Ljava/lang/StringBuilder;

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/DualVideoControler;->a(Ljava/lang/StringBuilder;Lcom/android/camera/dualvideo/render/IRenderable;)V

    return-void
.end method
