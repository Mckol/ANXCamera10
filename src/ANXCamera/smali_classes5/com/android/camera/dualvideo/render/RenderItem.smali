.class public Lcom/android/camera/dualvideo/render/RenderItem;
.super Ljava/lang/Object;
.source "RenderItem.java"

# interfaces
.implements Lcom/android/camera/dualvideo/render/IRenderable;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/dualvideo/render/RenderItem$ComposeChangeAnimListener;,
        Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;
    }
.end annotation


# static fields
.field private static final ANIM_DURATION_100:I = 0x64

.field private static final ANIM_DURATION_200:I = 0xc8

.field private static final ANIM_DURATION_400:I = 0x190

.field private static final TAG:Ljava/lang/String; = "RenderItem"


# instance fields
.field private m6PatchTagAlpha:F

.field private m6PatchTagAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

.field private m6PatchTagDstAlpha:F

.field private m6patchComposeType:I

.field private mAlpha:F

.field private mBasicPreviewTransMatrix:[F

.field private mFacing:Z

.field private mIsAnimating:Z

.field private mIsPressedIn6Patch:Z

.field private mIsVisible:Z

.field private mLastPreviewComposeType:I

.field private mPreviewAttri:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

.field private mRenderComposeType:I

.field private mSelectedFrameAlpha:F

.field private mSelectedFrameAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

.field private mSelectedFrameDstAlpha:F

.field private mSelectedType:I

.field private mShrinkListener:Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;


# direct methods
.method public constructor <init>(IZ)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsVisible:Z

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsPressedIn6Patch:Z

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6patchComposeType:I

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mRenderComposeType:I

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mLastPreviewComposeType:I

    iput-boolean p2, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mFacing:Z

    const/high16 p1, 0x3f800000    # 1.0f

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mAlpha:F

    return-void
.end method

.method static synthetic access$002(Lcom/android/camera/dualvideo/render/RenderItem;F)F
    .locals 0

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mAlpha:F

    return p1
.end method

.method static synthetic access$102(Lcom/android/camera/dualvideo/render/RenderItem;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsVisible:Z

    return p1
.end method

.method static synthetic access$202(Lcom/android/camera/dualvideo/render/RenderItem;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    return p1
.end method

.method static synthetic access$302(Lcom/android/camera/dualvideo/render/RenderItem;Z)Z
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsPressedIn6Patch:Z

    return p1
.end method

.method static synthetic access$400(Lcom/android/camera/dualvideo/render/RenderItem;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mPreviewAttri:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    return-object p0
.end method

.method static synthetic access$500(Lcom/android/camera/dualvideo/render/RenderItem;Lcom/android/camera/dualvideo/render/RegionHelper;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/render/RenderItem;->genPreviewAttri(Lcom/android/camera/dualvideo/render/RegionHelper;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    return-object p0
.end method

.method private animShrink()V
    .locals 9

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "animShrink: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "RenderItem"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    const/high16 v1, 0x43480000    # 200.0f

    const/4 v2, 0x6

    const/16 v3, 0x3e8

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    const-string v4, "animShrink"

    const/4 v5, 0x0

    const/4 v6, 0x1

    if-eqz v0, :cond_0

    new-array v0, v6, [Ljava/lang/Object;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v4

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    aput-object v4, v0, v5

    invoke-static {v0}, Lmiuix/animation/Folme;->useValue([Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object v0

    new-array v4, v6, [Lmiuix/animation/base/AnimConfig;

    new-instance v7, Lmiuix/animation/base/AnimConfig;

    invoke-direct {v7}, Lmiuix/animation/base/AnimConfig;-><init>()V

    new-array v8, v6, [F

    aput v1, v8, v5

    invoke-virtual {v7, v2, v8}, Lmiuix/animation/base/AnimConfig;->setEase(I[F)Lmiuix/animation/base/AnimConfig;

    move-result-object v1

    new-array v2, v6, [Lmiuix/animation/listener/TransitionListener;

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mShrinkListener:Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;

    aput-object p0, v2, v5

    invoke-virtual {v1, v2}, Lmiuix/animation/base/AnimConfig;->addListeners([Lmiuix/animation/listener/TransitionListener;)Lmiuix/animation/base/AnimConfig;

    move-result-object p0

    aput-object p0, v4, v5

    invoke-interface {v0, v3, v4}, Lmiuix/animation/IStateStyle;->to(Ljava/lang/Object;[Lmiuix/animation/base/AnimConfig;)Lmiuix/animation/IStateStyle;

    goto :goto_0

    :cond_0
    iput-boolean v6, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    new-instance v0, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;

    invoke-direct {v0, p0}, Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;-><init>(Lcom/android/camera/dualvideo/render/RenderItem;)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mShrinkListener:Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;

    new-array v0, v6, [Ljava/lang/Object;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v4

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    aput-object v4, v0, v5

    invoke-static {v0}, Lmiuix/animation/Folme;->useValue([Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object v0

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-interface {v0, v4}, Lmiuix/animation/IStateStyle;->setTo(Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object v0

    new-array v4, v6, [Lmiuix/animation/base/AnimConfig;

    new-instance v7, Lmiuix/animation/base/AnimConfig;

    invoke-direct {v7}, Lmiuix/animation/base/AnimConfig;-><init>()V

    new-array v8, v6, [F

    aput v1, v8, v5

    invoke-virtual {v7, v2, v8}, Lmiuix/animation/base/AnimConfig;->setEase(I[F)Lmiuix/animation/base/AnimConfig;

    move-result-object v1

    new-array v2, v6, [Lmiuix/animation/listener/TransitionListener;

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mShrinkListener:Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;

    aput-object p0, v2, v5

    invoke-virtual {v1, v2}, Lmiuix/animation/base/AnimConfig;->addListeners([Lmiuix/animation/listener/TransitionListener;)Lmiuix/animation/base/AnimConfig;

    move-result-object p0

    aput-object p0, v4, v5

    invoke-interface {v0, v3, v4}, Lmiuix/animation/IStateStyle;->to(Ljava/lang/Object;[Lmiuix/animation/base/AnimConfig;)Lmiuix/animation/IStateStyle;

    :goto_0
    return-void
.end method

.method private genPreviewAttri(Lcom/android/camera/dualvideo/render/RegionHelper;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
    .locals 8

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderComposeType()I

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/dualvideo/render/RegionHelper;->getRenderAreaFor(I)Landroid/graphics/Rect;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mBasicPreviewTransMatrix:[F

    invoke-virtual {v0}, [F->clone()Ljava/lang/Object;

    move-result-object v0

    move-object v3, v0

    check-cast v3, [F

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->isFacingFront()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    const/4 v1, 0x0

    const/high16 v2, 0x3f800000    # 1.0f

    invoke-static {v3, v1, v0, v2, v0}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    const/high16 v0, -0x40800000    # -1.0f

    invoke-static {v3, v1, v2, v0, v2}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    :cond_0
    new-instance v0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mPreviewAttri:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v2, v1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderComposeType()I

    move-result p0

    invoke-static {v3, p0}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerScaleMatrix([FI)[F

    iget v4, p1, Landroid/graphics/Rect;->left:I

    iget v5, p1, Landroid/graphics/Rect;->top:I

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result v6

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result v7

    move-object v1, v0

    invoke-direct/range {v1 .. v7}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)V

    return-object v0
.end method

.method private getAnimationDuration()I
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderComposeType()I

    move-result v0

    const/16 v1, 0xa

    if-eq v0, v1, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->getRenderComposeType()I

    move-result v0

    const/16 v1, 0x14

    if-ge v0, v1, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->getLastRenderComposeType()I

    move-result p0

    if-lt p0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/16 p0, 0xc8

    goto :goto_1

    :cond_1
    :goto_0
    const/16 p0, 0x190

    :goto_1
    return p0
.end method

.method private restoreAnimShrink()V
    .locals 8

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "restoreAnimShrink: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "RenderItem"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x1

    new-array v1, v0, [Ljava/lang/Object;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "animShrink"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    aput-object v2, v1, v3

    invoke-static {v1}, Lmiuix/animation/Folme;->useValue([Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object v1

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-array v4, v0, [Lmiuix/animation/base/AnimConfig;

    new-instance v5, Lmiuix/animation/base/AnimConfig;

    invoke-direct {v5}, Lmiuix/animation/base/AnimConfig;-><init>()V

    new-array v6, v0, [F

    const/high16 v7, 0x43480000    # 200.0f

    aput v7, v6, v3

    const/4 v7, 0x6

    invoke-virtual {v5, v7, v6}, Lmiuix/animation/base/AnimConfig;->setEase(I[F)Lmiuix/animation/base/AnimConfig;

    move-result-object v5

    new-array v0, v0, [Lmiuix/animation/listener/TransitionListener;

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mShrinkListener:Lcom/android/camera/dualvideo/render/RenderItem$ShrinkAnimListener;

    aput-object p0, v0, v3

    invoke-virtual {v5, v0}, Lmiuix/animation/base/AnimConfig;->addListeners([Lmiuix/animation/listener/TransitionListener;)Lmiuix/animation/base/AnimConfig;

    move-result-object p0

    aput-object p0, v4, v3

    invoke-interface {v1, v2, v4}, Lmiuix/animation/IStateStyle;->to(Ljava/lang/Object;[Lmiuix/animation/base/AnimConfig;)Lmiuix/animation/IStateStyle;

    return-void
.end method


# virtual methods
.method public alphaIn6PatchTag(Z)V
    .locals 2

    new-instance v0, Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-eqz p1, :cond_0

    const/16 v1, 0xc8

    goto :goto_0

    :cond_0
    const/16 v1, 0x64

    :goto_0
    invoke-direct {v0, v1}, Lcom/android/camera/dualvideo/render/EaseOutAnim;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-eqz p1, :cond_1

    const/high16 p1, 0x3f800000    # 1.0f

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagDstAlpha:F

    goto :goto_1

    :cond_1
    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagDstAlpha:F

    :goto_1
    return-void
.end method

.method public alphaInSelectedFrame(Z)V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->getSelectedType()I

    move-result v0

    if-nez v0, :cond_0

    if-eqz p1, :cond_0

    return-void

    :cond_0
    new-instance v0, Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-eqz p1, :cond_1

    const/16 v1, 0xc8

    goto :goto_0

    :cond_1
    const/16 v1, 0x64

    :goto_0
    invoke-direct {v0, v1}, Lcom/android/camera/dualvideo/render/EaseOutAnim;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-eqz p1, :cond_2

    const/high16 p1, 0x3f800000    # 1.0f

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameDstAlpha:F

    goto :goto_1

    :cond_2
    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameDstAlpha:F

    :goto_1
    return-void
.end method

.method public get6PatchTagAlpha()F
    .locals 3

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-nez v0, :cond_0

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAlpha:F

    return p0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->isFinished()Z

    move-result v0

    if-eqz v0, :cond_1

    iget v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagDstAlpha:F

    iput v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAlpha:F

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAlpha:F

    return p0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->getRatio()F

    move-result v0

    iget v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagAlpha:F

    const/high16 v2, 0x3f800000    # 1.0f

    sub-float/2addr v2, v0

    mul-float/2addr v1, v2

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6PatchTagDstAlpha:F

    mul-float/2addr p0, v0

    add-float/2addr v1, p0

    return v1
.end method

.method public get6patchComposeType()I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6patchComposeType:I

    return p0
.end method

.method public getAlpha()F
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mAlpha:F

    return p0
.end method

.method public getLastRenderComposeType()I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mLastPreviewComposeType:I

    return p0
.end method

.method public getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mPreviewAttri:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    return-object p0
.end method

.method public getRenderComposeType()I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mRenderComposeType:I

    return p0
.end method

.method public getSelectFrameAlpha()F
    .locals 3

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    if-nez v0, :cond_0

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAlpha:F

    return p0

    :cond_0
    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->isFinished()Z

    move-result v0

    if-eqz v0, :cond_1

    iget v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameDstAlpha:F

    iput v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAlpha:F

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAlpha:F

    return p0

    :cond_1
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAnim:Lcom/android/camera/dualvideo/render/EaseOutAnim;

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/render/EaseOutAnim;->getRatio()F

    move-result v0

    iget v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameAlpha:F

    const/high16 v2, 0x3f800000    # 1.0f

    sub-float/2addr v2, v0

    mul-float/2addr v1, v2

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedFrameDstAlpha:F

    mul-float/2addr p0, v0

    add-float/2addr v1, p0

    return v1
.end method

.method public getSelectedType()I
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedType:I

    return p0
.end method

.method public isAnimating()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->isPressedIn6Patch()Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method public isFacingFront()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mFacing:Z

    return p0
.end method

.method public isPressedIn6Patch()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsPressedIn6Patch:Z

    return p0
.end method

.method public isVisible()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsVisible:Z

    return p0
.end method

.method public onKeyDown()V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsPressedIn6Patch:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsPressedIn6Patch:Z

    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->animShrink()V

    const-string p0, "RenderItem"

    const-string v0, "onKeyDown: "

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onKeyUp()V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsPressedIn6Patch:Z

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->restoreAnimShrink()V

    :cond_0
    const-string p0, "RenderItem"

    const-string v0, "onKeyUp: "

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public setBasicPreviewTransMatrix([F)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mBasicPreviewTransMatrix:[F

    return-void
.end method

.method public setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V
    .locals 5

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    if-eqz v0, :cond_0

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mRenderComposeType:I

    iput v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mLastPreviewComposeType:I

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mRenderComposeType:I

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "setComposeTypeWithAnimation: from: "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mLastPreviewComposeType:I

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v0, " to: "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mRenderComposeType:I

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "RenderItem"

    invoke-static {v0, p1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    new-instance p1, Lmiuix/animation/base/AnimConfig;

    invoke-direct {p1}, Lmiuix/animation/base/AnimConfig;-><init>()V

    const/4 v0, 0x6

    const/4 v1, 0x1

    new-array v2, v1, [F

    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/RenderItem;->getAnimationDuration()I

    move-result v3

    int-to-float v3, v3

    const/4 v4, 0x0

    aput v3, v2, v4

    invoke-virtual {p1, v0, v2}, Lmiuix/animation/base/AnimConfig;->setEase(I[F)Lmiuix/animation/base/AnimConfig;

    new-instance v0, Lcom/android/camera/dualvideo/render/RenderItem$ComposeChangeAnimListener;

    invoke-direct {v0, p0, p2}, Lcom/android/camera/dualvideo/render/RenderItem$ComposeChangeAnimListener;-><init>(Lcom/android/camera/dualvideo/render/RenderItem;Lcom/android/camera/dualvideo/render/RegionHelper;)V

    new-array p2, v1, [Lmiuix/animation/listener/TransitionListener;

    aput-object v0, p2, v4

    invoke-virtual {p1, p2}, Lmiuix/animation/base/AnimConfig;->addListeners([Lmiuix/animation/listener/TransitionListener;)Lmiuix/animation/base/AnimConfig;

    const/16 p2, 0x3e8

    if-eqz p3, :cond_1

    iput-boolean v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    new-array p0, v4, [Ljava/lang/Object;

    invoke-static {p0}, Lmiuix/animation/Folme;->useValue([Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object p0

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p3

    invoke-interface {p0, p3}, Lmiuix/animation/IStateStyle;->setTo(Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object p0

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    new-array p3, v1, [Lmiuix/animation/base/AnimConfig;

    aput-object p1, p3, v4

    invoke-interface {p0, p2, p3}, Lmiuix/animation/IStateStyle;->to(Ljava/lang/Object;[Lmiuix/animation/base/AnimConfig;)Lmiuix/animation/IStateStyle;

    goto :goto_0

    :cond_1
    invoke-virtual {v0, p2}, Lcom/android/camera/dualvideo/render/RenderItem$ComposeChangeAnimListener;->animatePreview(I)V

    :goto_0
    return-void
.end method

.method public setRenderAttri(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mPreviewAttri:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    return-void
.end method

.method public setSelectedtypeWithAnim(IZ)V
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setSelectedtypeWithAnim: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "RenderItem"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedType:I

    if-ne v0, p1, :cond_0

    return-void

    :cond_0
    if-eqz v0, :cond_1

    if-eqz p1, :cond_1

    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedType:I

    return-void

    :cond_1
    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedType:I

    if-eqz p2, :cond_3

    iget p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedType:I

    if-nez p1, :cond_2

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/render/RenderItem;->alphaInSelectedFrame(Z)V

    goto :goto_0

    :cond_2
    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/render/RenderItem;->alphaInSelectedFrame(Z)V

    :cond_3
    :goto_0
    return-void
.end method

.method public setVisibilityWithAnim(ZZ)V
    .locals 7

    iget-boolean v0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsVisible:Z

    if-ne v0, p1, :cond_0

    return-void

    :cond_0
    if-eqz p2, :cond_4

    const/4 p2, 0x1

    iput-boolean p2, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsVisible:Z

    if-eqz p1, :cond_1

    const/16 v0, 0xc8

    goto :goto_0

    :cond_1
    const/16 v0, 0x64

    :goto_0
    const/16 v1, 0x3e8

    const/4 v2, 0x0

    if-eqz p1, :cond_2

    move v3, v2

    goto :goto_1

    :cond_2
    move v3, v1

    :goto_1
    if-eqz p1, :cond_3

    goto :goto_2

    :cond_3
    move v1, v2

    :goto_2
    new-array p1, v2, [Ljava/lang/Object;

    invoke-static {p1}, Lmiuix/animation/Folme;->useValue([Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object p1

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-interface {p1, v3}, Lmiuix/animation/IStateStyle;->setTo(Ljava/lang/Object;)Lmiuix/animation/IStateStyle;

    move-result-object p1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    new-array v3, p2, [Lmiuix/animation/base/AnimConfig;

    new-instance v4, Lmiuix/animation/base/AnimConfig;

    invoke-direct {v4}, Lmiuix/animation/base/AnimConfig;-><init>()V

    const/4 v5, 0x6

    new-array v6, p2, [F

    int-to-float v0, v0

    aput v0, v6, v2

    invoke-virtual {v4, v5, v6}, Lmiuix/animation/base/AnimConfig;->setEase(I[F)Lmiuix/animation/base/AnimConfig;

    move-result-object v0

    new-array p2, p2, [Lmiuix/animation/listener/TransitionListener;

    new-instance v4, Lcom/android/camera/dualvideo/render/RenderItem$1;

    invoke-direct {v4, p0}, Lcom/android/camera/dualvideo/render/RenderItem$1;-><init>(Lcom/android/camera/dualvideo/render/RenderItem;)V

    aput-object v4, p2, v2

    invoke-virtual {v0, p2}, Lmiuix/animation/base/AnimConfig;->addListeners([Lmiuix/animation/listener/TransitionListener;)Lmiuix/animation/base/AnimConfig;

    move-result-object p0

    aput-object p0, v3, v2

    invoke-interface {p1, v1, v3}, Lmiuix/animation/IStateStyle;->to(Ljava/lang/Object;[Lmiuix/animation/base/AnimConfig;)Lmiuix/animation/IStateStyle;

    goto :goto_4

    :cond_4
    iput-boolean p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsVisible:Z

    if-eqz p1, :cond_5

    const/high16 p1, 0x3f800000    # 1.0f

    goto :goto_3

    :cond_5
    const/4 p1, 0x0

    :goto_3
    iput p1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mAlpha:F

    :goto_4
    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "RenderItem{mLastPreviewComposeType="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mLastPreviewComposeType:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", mRenderComposeType="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mRenderComposeType:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", m6patchComposeType="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->m6patchComposeType:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", mFacing="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mFacing:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", mIsAnimating="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsAnimating:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", mIsVisible="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mIsVisible:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", mSelectedType="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p0, p0, Lcom/android/camera/dualvideo/render/RenderItem;->mSelectedType:I

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const/16 p0, 0x7d

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public updateRenderAttri(Lcom/android/camera/dualvideo/render/RegionHelper;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/dualvideo/render/RenderItem;->genPreviewAttri(Lcom/android/camera/dualvideo/render/RegionHelper;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/camera/dualvideo/render/RenderItem;->setRenderAttri(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    return-void
.end method
