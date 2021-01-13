.class public interface abstract Lcom/android/camera/dualvideo/render/IRenderable;
.super Ljava/lang/Object;
.source "IRenderable.java"


# direct methods
.method public static compare(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/render/IRenderable;)I
    .locals 2

    sget-object v0, Lcom/android/camera/dualvideo/render/Compose;->PRIORITYS:[[I

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v1

    invoke-static {v1}, Lcom/android/camera/dualvideo/render/Compose;->getIndex(I)I

    move-result v1

    aget-object v0, v0, v1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getLastRenderComposeType()I

    move-result p0

    invoke-static {p0}, Lcom/android/camera/dualvideo/render/Compose;->getIndex(I)I

    move-result p0

    aget p0, v0, p0

    sget-object v0, Lcom/android/camera/dualvideo/render/Compose;->PRIORITYS:[[I

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v1

    invoke-static {v1}, Lcom/android/camera/dualvideo/render/Compose;->getIndex(I)I

    move-result v1

    aget-object v0, v0, v1

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getLastRenderComposeType()I

    move-result p1

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/Compose;->getIndex(I)I

    move-result p1

    aget p1, v0, p1

    if-le p0, p1, :cond_0

    const/4 p0, -0x1

    return p0

    :cond_0
    if-ge p0, p1, :cond_1

    const/4 p0, 0x1

    return p0

    :cond_1
    const/4 p0, 0x0

    return p0
.end method


# virtual methods
.method public abstract alphaIn6PatchTag(Z)V
.end method

.method public abstract alphaInSelectedFrame(Z)V
.end method

.method public abstract get6PatchTagAlpha()F
.end method

.method public abstract get6patchComposeType()I
.end method

.method public abstract getAlpha()F
.end method

.method public abstract getLastRenderComposeType()I
.end method

.method public abstract getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
.end method

.method public abstract getRenderComposeType()I
.end method

.method public abstract getSelectFrameAlpha()F
.end method

.method public abstract getSelectedType()I
.end method

.method public abstract isAnimating()Z
.end method

.method public abstract isFacingFront()Z
.end method

.method public abstract isPressedIn6Patch()Z
.end method

.method public abstract isVisible()Z
.end method

.method public abstract onKeyDown()V
.end method

.method public abstract onKeyUp()V
.end method

.method public abstract setBasicPreviewTransMatrix([F)V
.end method

.method public abstract setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V
.end method

.method public abstract setRenderAttri(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V
.end method

.method public abstract setSelectedtypeWithAnim(IZ)V
.end method

.method public abstract setVisibilityWithAnim(ZZ)V
.end method

.method public abstract updateRenderAttri(Lcom/android/camera/dualvideo/render/RegionHelper;)V
.end method
