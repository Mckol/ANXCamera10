.class Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;
.super Lmiuix/animation/listener/TransitionListener;
.source "RenderItem.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/dualvideo/render/RenderItem;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ShrinkAnimListener"
.end annotation


# instance fields
.field private srcRenderArea:Landroid/graphics/Rect;

.field final synthetic this$0:Lcom/android/camera/dualvideo/render/RenderItem;


# direct methods
.method constructor <init>(Lcom/android/camera/dualvideo/render/RenderItem;)V
    .locals 5

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    invoke-direct {p0}, Lmiuix/animation/listener/TransitionListener;-><init>()V

    new-instance v0, Landroid/graphics/Rect;

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v1

    iget v1, v1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v2

    iget v2, v2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v3

    iget v3, v3, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v4

    iget v4, v4, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    add-int/2addr v3, v4

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v4

    iget v4, v4, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    iget p1, p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    add-int/2addr v4, p1

    invoke-direct {v0, v1, v2, v3, v4}, Landroid/graphics/Rect;-><init>(IIII)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->srcRenderArea:Landroid/graphics/Rect;

    return-void
.end method


# virtual methods
.method public animatePreview(I)V
    .locals 9

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->srcRenderArea:Landroid/graphics/Rect;

    int-to-float p1, p1

    const/high16 v1, 0x447a0000    # 1000.0f

    div-float/2addr p1, v1

    const v1, 0x3d4ccccd    # 0.05f

    mul-float/2addr p1, v1

    const/high16 v1, 0x3f800000    # 1.0f

    sub-float/2addr v1, p1

    invoke-static {v0, v1}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->shrinkRect(Landroid/graphics/Rect;F)Landroid/graphics/Rect;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    new-instance v8, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    invoke-static {v0}, Lcom/android/camera/dualvideo/render/RenderItem;->access$400(Lcom/android/camera/dualvideo/render/RenderItem;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object v1

    iget-object v2, v1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    invoke-static {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->access$400(Lcom/android/camera/dualvideo/render/RenderItem;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    iget-object v3, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mTextureTransform:[F

    iget v4, p1, Landroid/graphics/Rect;->left:I

    iget v5, p1, Landroid/graphics/Rect;->top:I

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v6

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result v7

    move-object v1, v8

    invoke-direct/range {v1 .. v7}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)V

    invoke-virtual {v0, v8}, Lcom/android/camera/dualvideo/render/RenderItem;->setRenderAttri(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    return-void
.end method

.method public onUpdate(Ljava/lang/Object;Lmiuix/animation/property/IIntValueProperty;IFZ)V
    .locals 0

    invoke-virtual {p0, p3}, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->animatePreview(I)V

    if-eqz p5, :cond_0

    if-nez p3, :cond_0

    const-string p1, "RenderItem"

    const-string p2, "onUpdate: isCompleted"

    invoke-static {p1, p2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p1, p0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    const/4 p2, 0x0

    invoke-static {p1, p2}, Lcom/android/camera/dualvideo/render/RenderItem;->access$202(Lcom/android/camera/dualvideo/render/RenderItem;Z)Z

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;->this$0:Lcom/android/camera/dualvideo/render/RenderItem;

    invoke-static {p0, p2}, Lcom/android/camera/dualvideo/render/RenderItem;->access$302(Lcom/android/camera/dualvideo/render/RenderItem;Z)Z

    :cond_0
    return-void
.end method
