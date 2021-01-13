.class Lcom/android/camera/dualvideo/render/RenderItem$1;
.super Lmiuix/animation/listener/TransitionListener;
.source "RenderItem.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/dualvideo/render/RenderItem;->setVisibilityWithAnim(ZZ)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/dualvideo/render/RenderItem;


# direct methods
.method constructor <init>(Lcom/android/camera/dualvideo/render/RenderItem;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/RenderItem$1;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    invoke-direct {p0}, Lmiuix/animation/listener/TransitionListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onUpdate(Ljava/lang/Object;Lmiuix/animation/property/IIntValueProperty;IFZ)V
    .locals 0

    iget-object p1, p0, Lcom/android/camera/dualvideo/render/RenderItem$1;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    int-to-float p2, p3

    const/high16 p4, 0x447a0000    # 1000.0f

    div-float/2addr p2, p4

    invoke-static {p1, p2}, Lcom/android/camera/dualvideo/render/RenderItem;->access$002(Lcom/android/camera/dualvideo/render/RenderItem;F)F

    if-eqz p5, :cond_1

    const/16 p1, 0x3e8

    if-ne p3, p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem$1;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    const/4 p1, 0x1

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/RenderItem;->access$102(Lcom/android/camera/dualvideo/render/RenderItem;Z)Z

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem$1;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    const/4 p1, 0x0

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/RenderItem;->access$102(Lcom/android/camera/dualvideo/render/RenderItem;Z)Z

    :cond_1
    :goto_0
    return-void
.end method
