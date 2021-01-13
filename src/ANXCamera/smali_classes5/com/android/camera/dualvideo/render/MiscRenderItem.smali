.class Lcom/android/camera/dualvideo/render/MiscRenderItem;
.super Ljava/lang/Object;
.source "MiscRenderItem.java"

# interfaces
.implements Lcom/android/camera/dualvideo/render/IMiscRenderable;


# instance fields
.field private mAlpha:F

.field private mAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

.field private mAnimDestAlpha:F

.field private mTex:Lcom/android/gallery3d/ui/BasicTexture;

.field private mType:I


# direct methods
.method public constructor <init>(IFLcom/android/gallery3d/ui/BasicTexture;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p3, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mTex:Lcom/android/gallery3d/ui/BasicTexture;

    iput p1, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mType:I

    iput p2, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAlpha:F

    return-void
.end method

.method private animateAlpha(F)V
    .locals 2

    iput p1, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAnimDestAlpha:F

    new-instance v0, Lcom/android/camera/dualvideo/render/EaseOutAnim;

    const/high16 v1, 0x3f000000    # 0.5f

    cmpl-float p1, p1, v1

    if-lez p1, :cond_0

    const/16 p1, 0xc8

    goto :goto_0

    :cond_0
    const/16 p1, 0x64

    :goto_0
    invoke-direct {v0, p1}, Lcom/android/camera/dualvideo/render/EaseOutAnim;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    return-void
.end method

.method private declared-synchronized setAlpha(F)V
    .locals 0

    monitor-enter p0

    :try_start_0
    iput p1, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAlpha:F
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method


# virtual methods
.method public getAlpha()F
    .locals 3

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-nez v0, :cond_0

    iget p0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAlpha:F

    return p0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->isFinished()Z

    move-result v0

    if-eqz v0, :cond_1

    iget v0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAnimDestAlpha:F

    invoke-direct {p0, v0}, Lcom/android/camera/dualvideo/render/MiscRenderItem;->setAlpha(F)V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    iget p0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAlpha:F

    return p0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->getRatio()F

    move-result v0

    iget v1, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAlpha:F

    const/high16 v2, 0x3f800000    # 1.0f

    sub-float/2addr v2, v0

    mul-float/2addr v1, v2

    iget p0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAnimDestAlpha:F

    mul-float/2addr p0, v0

    add-float/2addr v1, p0

    return v1
.end method

.method public declared-synchronized getBasicTexture()Lcom/android/gallery3d/ui/BasicTexture;
    .locals 1

    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mTex:Lcom/android/gallery3d/ui/BasicTexture;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit p0

    return-object v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public getTexType()I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mType:I

    return p0
.end method

.method public setAlphaWithAnim(FZ)V
    .locals 2

    iget v0, p0, Lcom/android/camera/dualvideo/render/MiscRenderItem;->mAlpha:F

    sub-float/2addr v0, p1

    invoke-static {v0}, Ljava/lang/Math;->abs(F)F

    move-result v0

    const v1, 0x3ca3d70a    # 0.02f

    cmpg-float v0, v0, v1

    if-gez v0, :cond_0

    return-void

    :cond_0
    if-eqz p2, :cond_1

    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/render/MiscRenderItem;->animateAlpha(F)V

    goto :goto_0

    :cond_1
    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/render/MiscRenderItem;->setAlpha(F)V

    :goto_0
    return-void
.end method
