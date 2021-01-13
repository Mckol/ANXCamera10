.class public Lcom/android/camera/dualvideo/render/DualVideoRenderManager;
.super Ljava/lang/Object;
.source "DualVideoRenderManager.java"


# static fields
.field static final synthetic $assertionsDisabled:Z = false

.field private static final TAG:Ljava/lang/String; = "DualVideoRenderManager"


# instance fields
.field private mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

.field private mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

.field private mRenderLocker:Ljava/lang/Object;

.field private mRenderableList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/dualvideo/render/IRenderable;",
            ">;"
        }
    .end annotation
.end field

.field private mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

.field private mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Landroid/graphics/SurfaceTexture;Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;Lcom/android/gallery3d/ui/ExtTexture;Ljava/lang/Object;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    iput-object p4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderLocker:Ljava/lang/Object;

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    iput-object p2, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iput-object p3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    const/4 p1, 0x1

    invoke-static {p1}, Lcom/android/camera/Util;->getDisplayRect(I)Landroid/graphics/Rect;

    move-result-object p1

    new-instance p2, Lcom/android/camera/dualvideo/render/RegionHelper;

    iget p3, p1, Landroid/graphics/Rect;->left:I

    iget p4, p1, Landroid/graphics/Rect;->top:I

    invoke-virtual {p1}, Landroid/graphics/Rect;->height()I

    move-result v0

    invoke-virtual {p1}, Landroid/graphics/Rect;->width()I

    move-result p1

    invoke-direct {p2, p3, p4, v0, p1}, Lcom/android/camera/dualvideo/render/RegionHelper;-><init>(IIII)V

    iput-object p2, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->initRenderableList()V

    return-void
.end method

.method static synthetic A(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    const/4 v0, 0x1

    invoke-interface {p0, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->alphaIn6PatchTag(Z)V

    return-void
.end method

.method static synthetic B(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result p0

    xor-int/lit8 p0, p0, 0x1

    return p0
.end method

.method static synthetic C(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    const/4 v0, 0x1

    invoke-interface {p0, v0, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->setVisibilityWithAnim(ZZ)V

    return-void
.end method

.method static synthetic D(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic G(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic a(Lcom/android/camera/dualvideo/UserSelectData;)Ljava/lang/Integer;
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method

.method static synthetic a(Lcom/android/camera/dualvideo/render/IRenderable;Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/CameraIDManager;Lcom/android/camera/dualvideo/UserSelectData;)V
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result v0

    iput v0, p3, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    invoke-virtual {p3}, Lcom/android/camera/dualvideo/UserSelectData;->getIndex()I

    move-result p3

    const/4 v0, 0x1

    if-eq p3, v0, :cond_1

    const/4 v0, 0x2

    if-eq p3, v0, :cond_0

    goto :goto_0

    :cond_0
    const/16 p3, 0x65

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p3

    invoke-virtual {p2}, Lcom/android/camera/dualvideo/CameraIDManager;->get6Patch2CameraId()Landroid/util/SparseArray;

    move-result-object p2

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result p0

    invoke-virtual {p2, p0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p1, p3, p0}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_1
    const/16 p3, 0x64

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p3

    invoke-virtual {p2}, Lcom/android/camera/dualvideo/CameraIDManager;->get6Patch2CameraId()Landroid/util/SparseArray;

    move-result-object p2

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result p0

    invoke-virtual {p2, p0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p1, p3, p0}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :goto_0
    return-void
.end method

.method static synthetic a(Ljava/util/ArrayList;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method static synthetic a(Ljava/util/ArrayList;Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/CameraIDManager;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    new-instance v0, Lcom/android/camera/dualvideo/render/w;

    invoke-direct {v0, p3}, Lcom/android/camera/dualvideo/render/w;-><init>(Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p0

    new-instance v0, Lcom/android/camera/dualvideo/render/l;

    invoke-direct {v0, p3, p1, p2}, Lcom/android/camera/dualvideo/render/l;-><init>(Lcom/android/camera/dualvideo/render/IRenderable;Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/CameraIDManager;)V

    invoke-virtual {p0, v0}, Ljava/util/Optional;->ifPresent(Ljava/util/function/Consumer;)V

    return-void
.end method

.method static synthetic a(ILcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 0

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic a(Landroid/util/SparseArray;ILcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 2

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    if-ne p0, p1, :cond_0

    const/4 p0, 0x1

    invoke-interface {p2, v1, p0}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    return p0

    :cond_0
    return v1
.end method

.method static synthetic b(Lcom/android/camera/dualvideo/UserSelectData;)Ljava/lang/Integer;
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method

.method static synthetic b(Ljava/util/ArrayList;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    new-instance v0, Lcom/android/camera/dualvideo/render/v;

    invoke-direct {v0, p1}, Lcom/android/camera/dualvideo/render/v;-><init>(Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p0

    new-instance v0, Lcom/android/camera/dualvideo/render/a;

    invoke-direct {v0, p1}, Lcom/android/camera/dualvideo/render/a;-><init>(Lcom/android/camera/dualvideo/render/IRenderable;)V

    invoke-virtual {p0, v0}, Ljava/util/Optional;->ifPresent(Ljava/util/function/Consumer;)V

    return-void
.end method

.method static synthetic c(Lcom/android/camera/dualvideo/UserSelectData;)Ljava/lang/Integer;
    .locals 0

    iget p0, p0, Lcom/android/camera/dualvideo/UserSelectData;->mPreviewType:I

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method

.method static synthetic c(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 0

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->m6PatchType:I

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic c(Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 1

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->mCameraID:I

    const/16 v0, 0x64

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic d(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/UserSelectData;)V
    .locals 1

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/UserSelectData;->getIndex()I

    move-result p1

    const/4 v0, 0x1

    invoke-interface {p0, p1, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    return-void
.end method

.method static synthetic d(Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 1

    iget p1, p1, Lcom/android/camera/dualvideo/UserSelectData;->mCameraID:I

    const/16 v0, 0x65

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic e(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/UserSelectData;)Z
    .locals 0

    invoke-virtual {p1}, Lcom/android/camera/dualvideo/UserSelectData;->getIndex()I

    move-result p1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result p0

    if-ne p1, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private init6PatchTag()V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    sget-object v0, Lcom/android/camera/dualvideo/render/G;->INSTANCE:Lcom/android/camera/dualvideo/render/G;

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->forEach(Ljava/util/function/Consumer;)V

    return-void
.end method

.method private initPreviewAttris()V
    .locals 11

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/dualvideo/render/IRenderable;

    iget-object v2, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v3

    invoke-virtual {v2, v3}, Lcom/android/camera/dualvideo/render/RegionHelper;->getRenderAreaFor(I)Landroid/graphics/Rect;

    move-result-object v2

    const/16 v3, 0x10

    new-array v6, v3, [F

    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mSubSurfaceTexture:Landroid/graphics/SurfaceTexture;

    invoke-virtual {v3, v6}, Landroid/graphics/SurfaceTexture;->getTransformMatrix([F)V

    invoke-virtual {v6}, [F->clone()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, [F

    invoke-interface {v1, v3}, Lcom/android/camera/dualvideo/render/IRenderable;->setBasicPreviewTransMatrix([F)V

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->isFacingFront()Z

    move-result v3

    if-eqz v3, :cond_0

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v5, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v4

    invoke-static {v6, v4}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerScaleMatrix([FI)[F

    iget v7, v2, Landroid/graphics/Rect;->left:I

    iget v8, v2, Landroid/graphics/Rect;->top:I

    invoke-virtual {v2}, Landroid/graphics/Rect;->width()I

    move-result v9

    invoke-virtual {v2}, Landroid/graphics/Rect;->height()I

    move-result v10

    move-object v4, v3

    invoke-direct/range {v4 .. v10}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)V

    invoke-interface {v1, v3}, Lcom/android/camera/dualvideo/render/IRenderable;->setRenderAttri(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    goto :goto_0

    :cond_0
    const/4 v3, 0x0

    const/4 v4, 0x0

    const/high16 v5, 0x3f800000    # 1.0f

    invoke-static {v6, v4, v3, v5, v3}, Landroid/opengl/Matrix;->translateM([FIFFF)V

    const/high16 v3, -0x40800000    # -1.0f

    invoke-static {v6, v4, v5, v3, v5}, Landroid/opengl/Matrix;->scaleM([FIFFF)V

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v5, v4, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v4

    invoke-static {v6, v4}, Lcom/android/camera/dualvideo/render/DualVideoUtil;->centerScaleMatrix([FI)[F

    iget v7, v2, Landroid/graphics/Rect;->left:I

    iget v8, v2, Landroid/graphics/Rect;->top:I

    invoke-virtual {v2}, Landroid/graphics/Rect;->width()I

    move-result v9

    invoke-virtual {v2}, Landroid/graphics/Rect;->height()I

    move-result v10

    move-object v4, v3

    invoke-direct/range {v4 .. v10}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)V

    invoke-interface {v1, v3}, Lcom/android/camera/dualvideo/render/IRenderable;->setRenderAttri(Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;)V

    goto :goto_0

    :cond_1
    return-void
.end method

.method private initSelected()V
    .locals 3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getSelectedData()Ljava/util/ArrayList;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v1

    new-instance v2, Lcom/android/camera/dualvideo/render/H;

    invoke-direct {v2, v0}, Lcom/android/camera/dualvideo/render/H;-><init>(Ljava/util/ArrayList;)V

    invoke-interface {v1, v2}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    const-string v0, "DualVideoRenderManager"

    const-string v1, "initSelected: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->printRenderList()V

    return-void
.end method

.method static synthetic l(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result p0

    return p0
.end method

.method static synthetic m(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result p0

    return p0
.end method

.method static synthetic n(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p0

    const/16 v0, 0xa

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic o(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    const/4 v0, 0x1

    invoke-interface {p0, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->alphaIn6PatchTag(Z)V

    return-void
.end method

.method private onTouched(II)Z
    .locals 5

    new-instance v0, Lcom/android/camera/dualvideo/CameraIDManager;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/CameraIDManager;-><init>()V

    invoke-virtual {v0}, Lcom/android/camera/dualvideo/CameraIDManager;->get6Patch2CameraId()Landroid/util/SparseArray;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_4

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/dualvideo/render/IRenderable;

    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v4

    invoke-virtual {v3, v4}, Lcom/android/camera/dualvideo/render/RegionHelper;->getRenderAreaFor(I)Landroid/graphics/Rect;

    move-result-object v3

    invoke-virtual {v3, p1, p2}, Landroid/graphics/Rect;->contains(II)Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result p1

    const/4 p2, 0x2

    const/4 v1, 0x1

    if-ne p1, v1, :cond_1

    iget-object p1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p1

    sget-object v0, Lcom/android/camera/dualvideo/render/J;->INSTANCE:Lcom/android/camera/dualvideo/render/J;

    invoke-interface {p1, v0}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p1

    sget-object v0, Lcom/android/camera/dualvideo/render/u;->INSTANCE:Lcom/android/camera/dualvideo/render/u;

    invoke-interface {p1, v0}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    invoke-interface {v2, p2, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    goto :goto_1

    :cond_1
    invoke-interface {v2}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result p1

    if-nez p1, :cond_3

    invoke-interface {v2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result p1

    invoke-virtual {v0, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Integer;

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v3

    new-instance v4, Lcom/android/camera/dualvideo/render/t;

    invoke-direct {v4, v0, p1}, Lcom/android/camera/dualvideo/render/t;-><init>(Landroid/util/SparseArray;I)V

    invoke-interface {v3, v4}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p1

    sget-object v0, Lcom/android/camera/dualvideo/render/L;->INSTANCE:Lcom/android/camera/dualvideo/render/L;

    invoke-interface {p1, v0}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    goto :goto_0

    :cond_2
    iget-object p1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p1

    sget-object v0, Lcom/android/camera/dualvideo/render/j;->INSTANCE:Lcom/android/camera/dualvideo/render/j;

    invoke-interface {p1, v0}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    :goto_0
    invoke-interface {v2, p2, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    :cond_3
    :goto_1
    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->saveSelectedStatus()V

    return v1

    :cond_4
    const/4 p0, 0x0

    return p0
.end method

.method static synthetic p(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 2

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v0

    const/16 v1, 0x14

    if-ne v0, v1, :cond_0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic q(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isAnimating()Z

    move-result p0

    return p0
.end method

.method static synthetic r(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result p0

    const/4 v0, 0x2

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method static synthetic s(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    const/4 v0, 0x1

    invoke-interface {p0, v0, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    return-void
.end method

.method private saveSelectedStatus()V
    .locals 5

    new-instance v0, Lcom/android/camera/dualvideo/CameraIDManager;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/CameraIDManager;-><init>()V

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getSelectedData()Ljava/util/ArrayList;

    move-result-object v2

    new-instance v3, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v3}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    new-instance v4, Lcom/android/camera/dualvideo/render/i;

    invoke-direct {v4, v2, v3, v0}, Lcom/android/camera/dualvideo/render/i;-><init>(Ljava/util/ArrayList;Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/CameraIDManager;)V

    invoke-interface {p0, v4}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    invoke-virtual {v1, v2}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->setSelectedData(Ljava/util/ArrayList;)V

    invoke-virtual {v1, v3}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->setIds(Ljava/util/concurrent/ConcurrentHashMap;)V

    return-void
.end method

.method static synthetic t(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    invoke-interface {p0, v0, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    :cond_0
    return-void
.end method

.method static synthetic u(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 3

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    const/4 v0, 0x0

    invoke-interface {p0, v0, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    goto :goto_0

    :cond_0
    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v0

    const/4 v2, 0x2

    if-ne v0, v2, :cond_1

    invoke-interface {p0, v1, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setSelectedtypeWithAnim(IZ)V

    :cond_1
    :goto_0
    return-void
.end method

.method private updateRenderableList()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderLocker:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/dualvideo/render/IRenderable;

    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v2, v3}, Lcom/android/camera/dualvideo/render/IRenderable;->updateRenderAttri(Lcom/android/camera/dualvideo/render/RegionHelper;)V

    goto :goto_0

    :cond_0
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method static synthetic v(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "printRenderList: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "DualVideoRenderManager"

    invoke-static {v0, p0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic w(Lcom/android/camera/dualvideo/render/IRenderable;)Z
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->isPressedIn6Patch()Z

    move-result p0

    return p0
.end method

.method static synthetic x(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 0

    invoke-interface {p0}, Lcom/android/camera/dualvideo/render/IRenderable;->onKeyUp()V

    return-void
.end method

.method static synthetic z(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 1

    const/4 v0, 0x1

    invoke-interface {p0, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->alphaInSelectedFrame(Z)V

    return-void
.end method


# virtual methods
.method public synthetic E(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 2

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    const/4 v1, 0x0

    invoke-interface {p1, v0, p0, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    return-void
.end method

.method public synthetic Ec()V
    .locals 0

    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->updateRenderableList()V

    return-void
.end method

.method public synthetic F(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 3

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result v0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {p1, v0, p0, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :cond_0
    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v0

    const/4 v2, 0x2

    if-ne v0, v2, :cond_1

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result v0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {p1, v0, p0, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    :cond_1
    :goto_0
    return-void
.end method

.method public synthetic a(Landroid/util/SparseArray;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 4

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->ismShouldDraw6Patch()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result p1

    packed-switch p1, :pswitch_data_0

    goto/16 :goto_0

    :pswitch_0
    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iput-object p0, p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    goto/16 :goto_0

    :pswitch_1
    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object p0, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iput-object p0, p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    goto/16 :goto_0

    :cond_0
    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Integer;

    if-nez p1, :cond_1

    return-void

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v0

    const/16 v1, 0x64

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v1

    invoke-virtual {v1}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v1

    const/16 v2, 0x65

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "changeTexture: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v3, " main: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " sub "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "DualVideoRenderManager"

    invoke-static {v3, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result v2

    if-ne v2, v0, :cond_2

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mMainDrawAttribute:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object p0, p0, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iput-object p0, p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    goto :goto_0

    :cond_2
    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    if-ne p1, v1, :cond_3

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mSubExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iput-object p0, p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    :cond_3
    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x14
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public synthetic a(Ljava/util/List;Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 8

    invoke-interface {p2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderAttri()Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p2

    new-instance v7, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    iget-object v1, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mExtTexture:Lcom/android/gallery3d/ui/ExtTexture;

    iget-object v2, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mTextureTransform:[F

    iget v3, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mX:I

    iget v0, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mY:I

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    iget p0, p0, Lcom/android/camera/dualvideo/render/RegionHelper;->mY:I

    sub-int v4, v0, p0

    iget v5, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mWidth:I

    iget v6, p2, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->mHeight:I

    move-object v0, v7

    invoke-direct/range {v0 .. v6}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)V

    invoke-interface {p1, v7}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    return-void
.end method

.method public changeTexture(Landroid/util/SparseArray;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/util/SparseArray<",
            "Ljava/lang/Integer;",
            ">;)V"
        }
    .end annotation

    const-string v0, "DualVideoRenderManager"

    const-string v1, "changeTexture: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/render/g;->INSTANCE:Lcom/android/camera/dualvideo/render/g;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v0

    new-instance v1, Lcom/android/camera/dualvideo/render/o;

    invoke-direct {v1, p0, p1}, Lcom/android/camera/dualvideo/render/o;-><init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;Landroid/util/SparseArray;)V

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    return-void
.end method

.method public expandBottom()Z
    .locals 5

    const-string v0, "DualVideoRenderManager"

    const-string v1, "expandBottom: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->isAnimating()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    const/4 v2, 0x1

    if-eqz v1, :cond_4

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result v3

    if-nez v3, :cond_1

    goto :goto_0

    :cond_1
    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v3

    const/16 v4, 0xb

    if-eq v3, v4, :cond_3

    const/16 v4, 0xc

    if-eq v3, v4, :cond_2

    goto :goto_0

    :cond_2
    const/16 v3, 0xd

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v3, v4, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :cond_3
    const/16 v3, 0xa

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v3, v4, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :cond_4
    return v2
.end method

.method public expandOrShrinkTop()Z
    .locals 8

    const-string v0, "DualVideoRenderManager"

    const-string v1, "expandOrShrinkTop: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->isAnimating()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    const/4 v2, 0x1

    if-eqz v1, :cond_6

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result v3

    if-nez v3, :cond_1

    goto :goto_0

    :cond_1
    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getLastRenderComposeType()I

    move-result v3

    invoke-interface {v1}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v4

    const/16 v5, 0x14

    const/16 v6, 0xd

    const/16 v7, 0xa

    packed-switch v4, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    if-eq v3, v7, :cond_3

    if-eq v3, v6, :cond_3

    if-lt v3, v5, :cond_2

    goto :goto_1

    :cond_2
    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v3, v4, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :cond_3
    :goto_1
    const/16 v3, 0xc

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v3, v4, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :pswitch_1
    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v7, v3, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :pswitch_2
    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v6, v3, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :pswitch_3
    if-eq v3, v7, :cond_5

    if-eq v3, v6, :cond_5

    if-lt v3, v5, :cond_4

    goto :goto_2

    :cond_4
    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v3, v4, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :cond_5
    :goto_2
    const/16 v3, 0xb

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v1, v3, v4, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :cond_6
    return v2

    :pswitch_data_0
    .packed-switch 0xa
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public get6PatchNextComposeType(I)I
    .locals 1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getmSelectedData()Ljava/util/ArrayList;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    new-instance v0, Lcom/android/camera/dualvideo/render/F;

    invoke-direct {v0, p1}, Lcom/android/camera/dualvideo/render/F;-><init>(I)V

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object p0

    sget-object p1, Lcom/android/camera/dualvideo/render/e;->INSTANCE:Lcom/android/camera/dualvideo/render/e;

    invoke-virtual {p0, p1}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object p0

    const/4 p1, -0x1

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/util/Optional;->orElse(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    return p0
.end method

.method public getRenderableList()Ljava/util/ArrayList;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/dualvideo/render/IRenderable;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    return-object p0
.end method

.method public getRenderableListForRecord()Ljava/util/List;
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/effect/draw_mode/DrawAttribute;",
            ">;"
        }
    .end annotation

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->getRenderableList()Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v1

    sget-object v2, Lcom/android/camera/dualvideo/render/x;->INSTANCE:Lcom/android/camera/dualvideo/render/x;

    invoke-interface {v1, v2}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v1

    sget-object v2, Lcom/android/camera/dualvideo/render/U;->INSTANCE:Lcom/android/camera/dualvideo/render/U;

    invoke-interface {v1, v2}, Ljava/util/stream/Stream;->sorted(Ljava/util/Comparator;)Ljava/util/stream/Stream;

    move-result-object v1

    new-instance v2, Lcom/android/camera/dualvideo/render/q;

    invoke-direct {v2, p0, v0}, Lcom/android/camera/dualvideo/render/q;-><init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;Ljava/util/List;)V

    invoke-interface {v1, v2}, Ljava/util/stream/Stream;->forEachOrdered(Ljava/util/function/Consumer;)V

    return-object v0
.end method

.method public getVisibileRenderList()Ljava/util/ArrayList;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/dualvideo/render/IRenderable;",
            ">;"
        }
    .end annotation

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    new-instance v1, Lcom/android/camera/dualvideo/render/B;

    invoke-direct {v1, v0}, Lcom/android/camera/dualvideo/render/B;-><init>(Ljava/util/ArrayList;)V

    invoke-interface {p0, v1}, Ljava/util/stream/Stream;->forEachOrdered(Ljava/util/function/Consumer;)V

    return-object v0
.end method

.method public hasMiniPreview()Z
    .locals 1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object v0, Lcom/android/camera/dualvideo/render/I;->INSTANCE:Lcom/android/camera/dualvideo/render/I;

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result p0

    return p0
.end method

.method public initRenderableList()V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->tj()Z

    move-result v0

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v3, Lcom/android/camera/dualvideo/render/RenderItem;

    const/16 v4, 0x14

    invoke-direct {v3, v4, v2}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v3, Lcom/android/camera/dualvideo/render/RenderItem;

    const/16 v4, 0x15

    invoke-direct {v3, v4, v2}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v3, Lcom/android/camera/dualvideo/render/RenderItem;

    const/16 v4, 0x16

    invoke-direct {v3, v4, v2}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v3, Lcom/android/camera/dualvideo/render/RenderItem;

    const/16 v4, 0x17

    invoke-direct {v3, v4, v2}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v3, Lcom/android/camera/dualvideo/render/RenderItem;

    const/16 v4, 0x18

    invoke-direct {v3, v4, v2}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v2, Lcom/android/camera/dualvideo/render/RenderItem;

    const/16 v3, 0x19

    invoke-direct {v2, v3, v1}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningDualVideo()Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getIds()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v3

    invoke-virtual {v0}, Lcom/android/camera/data/data/runing/ComponentRunningDualVideo;->getmSelectedData()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v4

    new-instance v5, Lcom/android/camera/dualvideo/render/E;

    invoke-direct {v5, v3}, Lcom/android/camera/dualvideo/render/E;-><init>(Ljava/util/concurrent/ConcurrentHashMap;)V

    invoke-interface {v4, v5}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object v4

    sget-object v5, Lcom/android/camera/dualvideo/render/b;->INSTANCE:Lcom/android/camera/dualvideo/render/b;

    invoke-virtual {v4, v5}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object v4

    invoke-virtual {v4}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Integer;

    invoke-virtual {v4}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    new-instance v5, Lcom/android/camera/dualvideo/render/m;

    invoke-direct {v5, v3}, Lcom/android/camera/dualvideo/render/m;-><init>(Ljava/util/concurrent/ConcurrentHashMap;)V

    invoke-interface {v0, v5}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/stream/Stream;->findFirst()Ljava/util/Optional;

    move-result-object v0

    sget-object v3, Lcom/android/camera/dualvideo/render/A;->INSTANCE:Lcom/android/camera/dualvideo/render/A;

    invoke-virtual {v0, v3}, Ljava/util/Optional;->map(Ljava/util/function/Function;)Ljava/util/Optional;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Optional;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v5, Lcom/android/camera/dualvideo/render/RenderItem;

    invoke-direct {v5, v4, v2}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v3, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    iget-object v2, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    new-instance v3, Lcom/android/camera/dualvideo/render/RenderItem;

    invoke-direct {v3, v0, v1}, Lcom/android/camera/dualvideo/render/RenderItem;-><init>(IZ)V

    invoke-virtual {v2, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_1
    :goto_0
    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->initPreviewAttris()V

    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->initSelected()V

    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->init6PatchTag()V

    return-void
.end method

.method public is6PatchWindow()Z
    .locals 1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object v0, Lcom/android/camera/dualvideo/render/D;->INSTANCE:Lcom/android/camera/dualvideo/render/D;

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result p0

    return p0
.end method

.method public isAnimating()Z
    .locals 1

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    if-eqz p0, :cond_0

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object v0, Lcom/android/camera/dualvideo/render/r;->INSTANCE:Lcom/android/camera/dualvideo/render/r;

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->anyMatch(Ljava/util/function/Predicate;)Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method

.method public printRenderList()V
    .locals 2

    const-string v0, "DualVideoRenderManager"

    const-string v1, "printRenderList: start"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object v0, Lcom/android/camera/dualvideo/render/p;->INSTANCE:Lcom/android/camera/dualvideo/render/p;

    invoke-interface {p0, v0}, Ljava/util/stream/Stream;->forEachOrdered(Ljava/util/function/Consumer;)V

    :cond_0
    return-void
.end method

.method public selectItem(Landroid/view/MotionEvent;)Z
    .locals 6

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "selectItem: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "DualVideoRenderManager"

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    if-eq v0, v1, :cond_0

    const/4 v2, 0x3

    if-eq v0, v2, :cond_0

    goto :goto_1

    :cond_0
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    float-to-int v0, v0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    float-to-int p1, p1

    invoke-direct {p0, v0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->onTouched(II)Z

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {p0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object p0

    sget-object p1, Lcom/android/camera/dualvideo/render/d;->INSTANCE:Lcom/android/camera/dualvideo/render/d;

    invoke-interface {p0, p1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object p0

    sget-object p1, Lcom/android/camera/dualvideo/render/h;->INSTANCE:Lcom/android/camera/dualvideo/render/h;

    invoke-interface {p0, p1}, Ljava/util/stream/Stream;->forEachOrdered(Ljava/util/function/Consumer;)V

    goto :goto_1

    :cond_1
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    float-to-int v0, v0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    float-to-int p1, p1

    iget-object v2, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_2
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_3

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/android/camera/dualvideo/render/IRenderable;

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v3}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v5

    invoke-virtual {v4, v5}, Lcom/android/camera/dualvideo/render/RegionHelper;->getRenderAreaFor(I)Landroid/graphics/Rect;

    move-result-object v4

    invoke-virtual {v4, v0, p1}, Landroid/graphics/Rect;->contains(II)Z

    move-result v4

    if-eqz v4, :cond_2

    invoke-interface {v3}, Lcom/android/camera/dualvideo/render/IRenderable;->onKeyDown()V

    goto :goto_0

    :cond_3
    :goto_1
    return v1
.end method

.method public switch6patch2preview()V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->isAnimating()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    invoke-direct {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->saveSelectedStatus()V

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    new-instance v1, Lcom/android/camera/dualvideo/render/K;

    invoke-direct {v1, p0}, Lcom/android/camera/dualvideo/render/K;-><init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    return-void
.end method

.method public switchPreviewTo6Patch()V
    .locals 2

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->isAnimating()Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    :cond_0
    const-string v0, "DualVideoRenderManager"

    const-string v1, "switchPreviewTo6Patch: "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/render/C;->INSTANCE:Lcom/android/camera/dualvideo/render/C;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/render/c;->INSTANCE:Lcom/android/camera/dualvideo/render/c;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/render/y;->INSTANCE:Lcom/android/camera/dualvideo/render/y;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/render/z;->INSTANCE:Lcom/android/camera/dualvideo/render/z;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/render/n;->INSTANCE:Lcom/android/camera/dualvideo/render/n;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    sget-object v1, Lcom/android/camera/dualvideo/render/M;->INSTANCE:Lcom/android/camera/dualvideo/render/M;

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;

    move-result-object v0

    new-instance v1, Lcom/android/camera/dualvideo/render/s;

    invoke-direct {v1, p0}, Lcom/android/camera/dualvideo/render/s;-><init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->stream()Ljava/util/stream/Stream;

    move-result-object v0

    new-instance v1, Lcom/android/camera/dualvideo/render/f;

    invoke-direct {v1, p0}, Lcom/android/camera/dualvideo/render/f;-><init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V

    invoke-interface {v0, v1}, Ljava/util/stream/Stream;->forEach(Ljava/util/function/Consumer;)V

    return-void
.end method

.method public switchTopBottom()Z
    .locals 5

    const-string v0, "DualVideoRenderManager"

    const-string v1, "switchTopBottom "

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->isAnimating()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRenderableList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-interface {v2}, Lcom/android/camera/dualvideo/render/IRenderable;->isVisible()Z

    move-result v3

    if-nez v3, :cond_1

    goto :goto_0

    :cond_1
    invoke-interface {v2}, Lcom/android/camera/dualvideo/render/IRenderable;->getRenderComposeType()I

    move-result v3

    packed-switch v3, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    const/16 v3, 0xa

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v2, v3, v4, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :pswitch_1
    const/16 v3, 0xb

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v2, v3, v4, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :pswitch_2
    const/16 v3, 0xc

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v2, v3, v4, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :pswitch_3
    const/16 v3, 0xd

    iget-object v4, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {v2, v3, v4, v1}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    goto :goto_0

    :cond_2
    const/4 p0, 0x1

    return p0

    :pswitch_data_0
    .packed-switch 0xa
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public updateMiniWindowLocation(Landroid/view/MotionEvent;)Z
    .locals 7

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz v0, :cond_4

    if-eq v0, v2, :cond_2

    const/4 v3, 0x2

    if-eq v0, v3, :cond_0

    const/4 p1, 0x3

    if-eq v0, p1, :cond_2

    return v1

    :cond_0
    iget-object v0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    iget-boolean v0, v0, Lcom/android/camera/dualvideo/render/RegionHelper;->mIsHovering:Z

    if-eqz v0, :cond_1

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    iget-object v1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    iget v1, v1, Lcom/android/camera/dualvideo/render/RegionHelper;->mStartX:F

    sub-float/2addr v0, v1

    float-to-int v0, v0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v1

    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    iget v4, v3, Lcom/android/camera/dualvideo/render/RegionHelper;->mStartY:F

    sub-float/2addr v1, v4

    float-to-int v1, v1

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v4

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    invoke-virtual {v3, v4, p1}, Lcom/android/camera/dualvideo/render/RegionHelper;->setStartPosition(FF)V

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/dualvideo/render/RegionHelper;->updateMarginOffset(II)V

    return v2

    :cond_1
    return v1

    :cond_2
    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    iget-boolean p1, p0, Lcom/android/camera/dualvideo/render/RegionHelper;->mIsHovering:Z

    if-eqz p1, :cond_3

    iput-boolean v1, p0, Lcom/android/camera/dualvideo/render/RegionHelper;->mIsHovering:Z

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/RegionHelper;->moveToEdge()V

    return v2

    :cond_3
    return v1

    :cond_4
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    iget-object v3, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    const/16 v4, 0xa

    invoke-virtual {v3, v4}, Lcom/android/camera/dualvideo/render/RegionHelper;->getRenderAreaFor(I)Landroid/graphics/Rect;

    move-result-object v3

    iget v4, v3, Landroid/graphics/Rect;->left:I

    int-to-float v5, v4

    cmpl-float v5, v0, v5

    if-lez v5, :cond_5

    invoke-virtual {v3}, Landroid/graphics/Rect;->width()I

    move-result v5

    add-int/2addr v4, v5

    int-to-float v4, v4

    cmpg-float v4, v0, v4

    if-gez v4, :cond_5

    move v4, v2

    goto :goto_0

    :cond_5
    move v4, v1

    :goto_0
    iget v5, v3, Landroid/graphics/Rect;->top:I

    int-to-float v6, v5

    cmpl-float v6, p1, v6

    if-lez v6, :cond_6

    invoke-virtual {v3}, Landroid/graphics/Rect;->height()I

    move-result v3

    add-int/2addr v5, v3

    int-to-float v3, v5

    cmpg-float v3, p1, v3

    if-gez v3, :cond_6

    move v3, v2

    goto :goto_1

    :cond_6
    move v3, v1

    :goto_1
    if-eqz v4, :cond_7

    if-eqz v3, :cond_7

    iget-object v1, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    iput-boolean v2, v1, Lcom/android/camera/dualvideo/render/RegionHelper;->mIsHovering:Z

    new-instance v3, Lcom/android/camera/dualvideo/render/k;

    invoke-direct {v3, p0}, Lcom/android/camera/dualvideo/render/k;-><init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V

    invoke-virtual {v1, v3}, Lcom/android/camera/dualvideo/render/RegionHelper;->setListener(Lcom/android/camera/dualvideo/render/RegionHelper$UpdatedListener;)V

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-virtual {p0, v0, p1}, Lcom/android/camera/dualvideo/render/RegionHelper;->setStartPosition(FF)V

    return v2

    :cond_7
    return v1
.end method

.method public synthetic y(Lcom/android/camera/dualvideo/render/IRenderable;)V
    .locals 4

    const/4 v0, 0x0

    invoke-interface {p1, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->alphaIn6PatchTag(Z)V

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->getSelectedType()I

    move-result v1

    const/4 v2, 0x1

    if-eq v1, v2, :cond_0

    const/4 v3, 0x2

    if-eq v1, v3, :cond_0

    invoke-interface {p1, v0, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setVisibilityWithAnim(ZZ)V

    goto :goto_0

    :cond_0
    invoke-interface {p1, v0}, Lcom/android/camera/dualvideo/render/IRenderable;->alphaInSelectedFrame(Z)V

    invoke-interface {p1}, Lcom/android/camera/dualvideo/render/IRenderable;->get6patchComposeType()I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->get6PatchNextComposeType(I)I

    move-result v0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->mRegionHelper:Lcom/android/camera/dualvideo/render/RegionHelper;

    invoke-interface {p1, v0, p0, v2}, Lcom/android/camera/dualvideo/render/IRenderable;->setComposeTypeWithAnimation(ILcom/android/camera/dualvideo/render/RegionHelper;Z)V

    :goto_0
    return-void
.end method
