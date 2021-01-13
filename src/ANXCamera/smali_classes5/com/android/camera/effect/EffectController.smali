.class public Lcom/android/camera/effect/EffectController;
.super Ljava/lang/Object;
.source "EffectController.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/effect/EffectController$EffectRectAttribute;,
        Lcom/android/camera/effect/EffectController$EffectChangedListener;,
        Lcom/android/camera/effect/EffectController$EffectChangeType;
    }
.end annotation


# static fields
.field private static final BLUR_ALPHA_FRAME_NUM:I = 0x8

.field public static final COLOR_RETENTION_FILTER_ID:I = 0xc8

.field public static final EFFECT_ALL_CHANGE_TYPES:[I

.field public static final EFFECT_CHANGE_BEAUTY:I = 0x3

.field public static final EFFECT_CHANGE_CINEMATIC:I = 0x9

.field public static final EFFECT_CHANGE_EXPOSURE_FEEDBACK:I = 0x7

.field public static final EFFECT_CHANGE_FILTER:I = 0x1

.field public static final EFFECT_CHANGE_FOCUS_PEAK:I = 0x4

.field public static final EFFECT_CHANGE_GRADIENTER:I = 0x6

.field public static final EFFECT_CHANGE_KALEIDOSCOPE:I = 0x8

.field public static final EFFECT_CHANGE_STICKER:I = 0x2

.field public static final EFFECT_CHANGE_TILT:I = 0x5

.field private static final MAX_BLUR_ALPHA:I = 0xd4

.field private static final TAG:Ljava/lang/String; = "EffectController"

.field private static sInstance:Lcom/android/camera/effect/EffectController;


# instance fields
.field private mAiColorCorrectionVersion:I

.field private mBeautyEnable:Z

.field private mBeautyFrameReady:Z

.field private mBlur:Z

.field private mBlurStep:I

.field private mChangedListeners:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/android/camera/effect/EffectController$EffectChangedListener;",
            ">;"
        }
    .end annotation
.end field

.field private mCurrentSticker:Ljava/lang/String;

.field private mDeviceRotation:F

.field private mDrawCinematic:Z

.field private mDrawExposure:Z

.field private mDrawGradienter:Z

.field private mDrawKaleidoscope:Ljava/lang/String;

.field private mDrawPeaking:Z

.field private mDrawTilt:Z

.field private mEffectId:I

.field private mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

.field private mFilterInfoMap:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;>;"
        }
    .end annotation
.end field

.field private mIsDrawMainFrame:Z

.field private mIsIndiaColorLookupTableAvailable:Z

.field private mLiveFilters:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;",
            ">;"
        }
    .end annotation
.end field

.field private mLock:Ljava/lang/Object;

.field private mNeedDestroyMakeup:Z

.field private mOrientation:I

.field private mOverrideAiEffectIndex:I

.field private mOverrideEffectIndex:I

.field private mTiltShiftMaskAlpha:F


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const/16 v0, 0x9

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    sput-object v0, Lcom/android/camera/effect/EffectController;->EFFECT_ALL_CHANGE_TYPES:[I

    return-void

    :array_0
    .array-data 4
        0x1
        0x2
        0x3
        0x4
        0x5
        0x6
        0x7
        0x8
        0x9
    .end array-data
.end method

.method private constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/effect/EffectController;->mOverrideEffectIndex:I

    iput v0, p0, Lcom/android/camera/effect/EffectController;->mOverrideAiEffectIndex:I

    iput v0, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mBlur:Z

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/effect/EffectController;->mIsDrawMainFrame:Z

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    iput v1, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mBeautyEnable:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mNeedDestroyMakeup:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mBeautyFrameReady:Z

    const-string v0, "0"

    iput-object v0, p0, Lcom/android/camera/effect/EffectController;->mDrawKaleidoscope:Ljava/lang/String;

    new-instance v0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/android/camera/effect/EffectController$EffectRectAttribute;-><init>(Lcom/android/camera/effect/EffectController$1;)V

    iput-object v0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/android/camera/effect/EffectController;->mChangedListeners:Ljava/util/List;

    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/android/camera/effect/EffectController;->mLock:Ljava/lang/Object;

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initialize()V

    return-void
.end method

.method private convertToFilterCategory(I)Lcom/miui/filtersdk/filter/helper/FilterCategory;
    .locals 0

    const/4 p0, 0x1

    if-eq p1, p0, :cond_5

    const/4 p0, 0x2

    if-eq p1, p0, :cond_4

    const/4 p0, 0x3

    if-eq p1, p0, :cond_3

    const/4 p0, 0x5

    if-eq p1, p0, :cond_2

    const/4 p0, 0x6

    if-eq p1, p0, :cond_1

    const/16 p0, 0x8

    if-eq p1, p0, :cond_0

    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->UNKNOWN:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    return-object p0

    :cond_0
    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->MI_LIVE:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    return-object p0

    :cond_1
    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->LIGHTING:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    return-object p0

    :cond_2
    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->AI:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    return-object p0

    :cond_3
    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->STICKER:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    return-object p0

    :cond_4
    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->BEAUTY:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    return-object p0

    :cond_5
    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->NORMAL:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    return-object p0
.end method

.method public static createAiSceneEffectId(Lcom/miui/filtersdk/filter/helper/FilterType;)I
    .locals 1

    invoke-virtual {p0}, Ljava/lang/Enum;->ordinal()I

    move-result p0

    const/4 v0, 0x5

    invoke-static {v0, p0}, Lcom/android/camera/effect/FilterInfo;->getId(II)I

    move-result p0

    return p0
.end method

.method private getAiSceneRenderNew(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 0

    if-eqz p3, :cond_0

    const/4 p3, 0x5

    invoke-direct {p0, p1, p2, p3, p4}, Lcom/android/camera/effect/EffectController;->getRenderByCategory(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;IZ)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2

    :cond_0
    invoke-direct {p0, p1, p2, p4, p5}, Lcom/android/camera/effect/EffectController;->getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2
.end method

.method private getBeautyRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 1

    const/4 v0, 0x0

    if-eqz p3, :cond_0

    const/4 p3, 0x2

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/android/camera/effect/EffectController;->getRenderByCategory(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;IZ)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2

    :cond_0
    invoke-direct {p0, p1, p2, v0, p4}, Lcom/android/camera/effect/EffectController;->getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2
.end method

.method private getDegree(Lcom/miui/filtersdk/filter/helper/FilterType;)I
    .locals 0

    const/16 p0, 0x64

    return p0
.end method

.method public static declared-synchronized getInstance()Lcom/android/camera/effect/EffectController;
    .locals 2

    const-class v0, Lcom/android/camera/effect/EffectController;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/android/camera/effect/EffectController;->sInstance:Lcom/android/camera/effect/EffectController;

    if-nez v1, :cond_0

    new-instance v1, Lcom/android/camera/effect/EffectController;

    invoke-direct {v1}, Lcom/android/camera/effect/EffectController;-><init>()V

    sput-object v1, Lcom/android/camera/effect/EffectController;->sInstance:Lcom/android/camera/effect/EffectController;

    :cond_0
    sget-object v1, Lcom/android/camera/effect/EffectController;->sInstance:Lcom/android/camera/effect/EffectController;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method private getLightingRenderNew(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 0

    if-eqz p3, :cond_0

    const/4 p3, 0x6

    invoke-direct {p0, p1, p2, p3, p4}, Lcom/android/camera/effect/EffectController;->getRenderByCategory(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;IZ)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2

    :cond_0
    invoke-direct {p0, p1, p2, p4, p5}, Lcom/android/camera/effect/EffectController;->getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2
.end method

.method private getMakeupRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 3

    new-instance p0, Lcom/miui/filtersdk/filter/NewBeautificationFilter;

    invoke-direct {p0}, Lcom/miui/filtersdk/filter/NewBeautificationFilter;-><init>()V

    new-instance v0, Lcom/android/camera/effect/ArcsoftBeautyProcessor;

    invoke-direct {v0}, Lcom/android/camera/effect/ArcsoftBeautyProcessor;-><init>()V

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result v1

    if-eqz v1, :cond_0

    const/16 v1, 0x10e

    goto :goto_0

    :cond_0
    const/16 v1, 0x5a

    :goto_0
    invoke-virtual {v0, v1}, Lcom/miui/filtersdk/beauty/BeautyProcessor;->setRotation(I)V

    invoke-static {}, Lcom/android/camera/fragment/beauty/BeautyParameters;->getInstance()Lcom/android/camera/fragment/beauty/BeautyParameters;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/android/camera/fragment/beauty/BeautyParameters;->setMakeupProcessor(Lcom/miui/filtersdk/beauty/IntelligentBeautyProcessor;)V

    invoke-virtual {p0, v0}, Lcom/miui/filtersdk/filter/base/BaseBeautyFilter;->setBeautyProcessor(Lcom/miui/filtersdk/beauty/BeautyProcessor;)V

    sget v0, Lcom/android/camera/Util;->sWindowWidth:I

    sget v1, Lcom/android/camera/Util;->sWindowHeight:I

    invoke-virtual {p0, v0, v1}, Lcom/miui/filtersdk/filter/NewBeautificationFilter;->initBeautyProcessor(II)V

    new-instance v0, Lcom/android/camera/effect/renders/BeautificationWrapperRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->RENDER_ID_MAKEUP:I

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCamera()Z

    move-result v2

    invoke-direct {v0, p1, v1, p0, v2}, Lcom/android/camera/effect/renders/BeautificationWrapperRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILcom/miui/filtersdk/filter/base/GPUImageFilter;Z)V

    invoke-virtual {p2, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    return-object p2
.end method

.method private getMiLiveRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 1

    const/4 v0, 0x0

    if-eqz p3, :cond_0

    const/16 p3, 0x8

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/android/camera/effect/EffectController;->getRenderByCategory(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;IZ)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2

    :cond_0
    invoke-direct {p0, p1, p2, v0, p4}, Lcom/android/camera/effect/EffectController;->getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2
.end method

.method private getNormalRenderNew(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 0

    if-eqz p3, :cond_0

    const/4 p3, 0x1

    invoke-direct {p0, p1, p2, p3, p4}, Lcom/android/camera/effect/EffectController;->getRenderByCategory(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;IZ)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2

    :cond_0
    invoke-direct {p0, p1, p2, p4, p5}, Lcom/android/camera/effect/EffectController;->getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2
.end method

.method private getPrivateRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 15

    move-object/from16 v6, p1

    move-object/from16 v7, p2

    move/from16 v8, p5

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_BLUR:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    const/4 v9, 0x2

    const/4 v10, 0x0

    const/4 v11, 0x1

    if-nez v0, :cond_a

    if-nez p3, :cond_0

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_BLUR:I

    if-eq v8, v0, :cond_0

    if-gez v8, :cond_a

    :cond_0
    if-nez p3, :cond_3

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_BLUR:I

    if-eq v8, v0, :cond_3

    invoke-virtual {v7, v9}, Lcom/android/camera/effect/renders/RenderGroup;->isPartComplete(I)Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_2

    new-instance v0, Lcom/android/camera/effect/renders/XBlurEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/XBlurEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto/16 :goto_5

    :cond_2
    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_9

    new-instance v0, Lcom/android/camera/effect/renders/YBlurEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/YBlurEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto :goto_5

    :cond_3
    :goto_0
    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-eqz v0, :cond_4

    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    instance-of v0, v0, Lcom/android/camera/effect/renders/XBlurEffectRender;

    if-eqz v0, :cond_4

    move v12, v11

    goto :goto_1

    :cond_4
    move v12, v10

    :goto_1
    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-eqz v0, :cond_5

    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    instance-of v0, v0, Lcom/android/camera/effect/renders/YBlurEffectRender;

    if-eqz v0, :cond_5

    move v13, v11

    goto :goto_2

    :cond_5
    move v13, v10

    :goto_2
    new-instance v14, Lcom/android/camera/effect/renders/PipeRenderPair;

    sget v2, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_BLUR:I

    if-eqz v12, :cond_6

    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    goto :goto_3

    :cond_6
    new-instance v0, Lcom/android/camera/effect/renders/XBlurEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/XBlurEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_3
    move-object v3, v0

    if-eqz v13, :cond_7

    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    goto :goto_4

    :cond_7
    new-instance v0, Lcom/android/camera/effect/renders/YBlurEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/YBlurEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_4
    move-object v4, v0

    const/4 v5, 0x0

    move-object v0, v14

    move-object/from16 v1, p1

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/effect/renders/PipeRenderPair;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILcom/android/camera/effect/renders/Render;Lcom/android/camera/effect/renders/Render;Z)V

    invoke-virtual {v7, v14}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    if-nez v12, :cond_8

    if-eqz v13, :cond_9

    :cond_8
    invoke-virtual/range {p2 .. p2}, Lcom/android/camera/effect/renders/RenderGroup;->clearPartRenders()V

    :cond_9
    :goto_5
    move v0, v11

    goto :goto_6

    :cond_a
    move v0, v10

    :goto_6
    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GRADIENTER:I

    invoke-virtual {v7, v1}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    if-nez v1, :cond_d

    if-nez p3, :cond_b

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GRADIENTER:I

    if-eq v8, v1, :cond_b

    if-gez v8, :cond_d

    if-nez v0, :cond_d

    :cond_b
    if-eqz p4, :cond_c

    new-instance v0, Lcom/android/camera/effect/renders/GradienterSnapshotEffectRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GRADIENTER:I

    invoke-direct {v0, v6, v1}, Lcom/android/camera/effect/renders/GradienterSnapshotEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    goto :goto_7

    :cond_c
    new-instance v0, Lcom/android/camera/effect/renders/GradienterEffectRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GRADIENTER:I

    invoke-direct {v0, v6, v1}, Lcom/android/camera/effect/renders/GradienterEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    :goto_7
    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    move v0, v11

    :cond_d
    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_CINEMATIC:I

    invoke-virtual {v7, v1}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    if-nez v1, :cond_f

    if-nez p3, :cond_e

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_CINEMATIC:I

    if-eq v8, v1, :cond_e

    if-gez v8, :cond_f

    if-nez v0, :cond_f

    :cond_e
    new-instance v0, Lcom/android/camera/effect/renders/CinematicRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_CINEMATIC:I

    invoke-direct {v0, v6, v1}, Lcom/android/camera/effect/renders/CinematicRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    move v0, v11

    :cond_f
    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_TILTSHIFT:I

    invoke-virtual {v7, v1}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    const/4 v12, 0x3

    if-nez v1, :cond_19

    invoke-static {}, Lb/c/a/c;->Xn()Z

    move-result v1

    if-eqz v1, :cond_19

    if-nez p3, :cond_10

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_TILTSHIFT:I

    if-eq v8, v1, :cond_10

    if-gez v8, :cond_19

    if-nez v0, :cond_19

    :cond_10
    if-nez p3, :cond_14

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_TILTSHIFT:I

    if-eq v8, v0, :cond_14

    invoke-virtual {v7, v12}, Lcom/android/camera/effect/renders/RenderGroup;->isPartComplete(I)Z

    move-result v0

    if-eqz v0, :cond_11

    goto :goto_8

    :cond_11
    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_12

    new-instance v0, Lcom/android/camera/effect/renders/XTiltShiftEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/XTiltShiftEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto :goto_c

    :cond_12
    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_13

    new-instance v0, Lcom/android/camera/effect/renders/YTiltShiftEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/YTiltShiftEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto :goto_c

    :cond_13
    invoke-virtual {v7, v9}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_18

    new-instance v0, Lcom/android/camera/effect/renders/TiltShiftMaskEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/TiltShiftMaskEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto :goto_c

    :cond_14
    :goto_8
    new-instance v13, Lcom/android/camera/effect/renders/PipeRenderPair;

    sget v2, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_TILTSHIFT:I

    new-instance v3, Lcom/android/camera/effect/renders/PipeRenderPair;

    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-eqz v0, :cond_15

    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    goto :goto_9

    :cond_15
    new-instance v0, Lcom/android/camera/effect/renders/XTiltShiftEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/XTiltShiftEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_9
    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    if-eqz v1, :cond_16

    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    goto :goto_a

    :cond_16
    new-instance v1, Lcom/android/camera/effect/renders/YTiltShiftEffectRender;

    invoke-direct {v1, v6}, Lcom/android/camera/effect/renders/YTiltShiftEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_a
    invoke-direct {v3, v6, v0, v1, v10}, Lcom/android/camera/effect/renders/PipeRenderPair;-><init>(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/Render;Lcom/android/camera/effect/renders/Render;Z)V

    invoke-virtual {v7, v9}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-eqz v0, :cond_17

    invoke-virtual {v7, v9}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    goto :goto_b

    :cond_17
    new-instance v0, Lcom/android/camera/effect/renders/TiltShiftMaskEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/TiltShiftMaskEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_b
    move-object v4, v0

    const/4 v5, 0x0

    move-object v0, v13

    move-object/from16 v1, p1

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/effect/renders/PipeRenderPair;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILcom/android/camera/effect/renders/Render;Lcom/android/camera/effect/renders/Render;Z)V

    invoke-virtual {v7, v13}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    invoke-virtual/range {p2 .. p2}, Lcom/android/camera/effect/renders/RenderGroup;->clearPartRenders()V

    :cond_18
    :goto_c
    move v0, v11

    :cond_19
    invoke-static {}, Lb/c/a/c;->sn()Z

    move-result v1

    if-nez v1, :cond_22

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GAUSSIAN:I

    invoke-virtual {v7, v1}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    if-nez v1, :cond_22

    if-nez p3, :cond_1a

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GAUSSIAN:I

    if-eq v8, v1, :cond_1a

    if-gez v8, :cond_22

    if-nez v0, :cond_22

    :cond_1a
    if-nez p3, :cond_1e

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GAUSSIAN:I

    if-eq v8, v0, :cond_1e

    invoke-virtual {v7, v12}, Lcom/android/camera/effect/renders/RenderGroup;->isPartComplete(I)Z

    move-result v0

    if-eqz v0, :cond_1b

    goto :goto_d

    :cond_1b
    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_1c

    new-instance v0, Lcom/android/camera/effect/renders/XGaussianEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/XGaussianEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto/16 :goto_11

    :cond_1c
    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_1d

    new-instance v0, Lcom/android/camera/effect/renders/YGaussianEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/YGaussianEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto :goto_11

    :cond_1d
    invoke-virtual {v7, v9}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_23

    new-instance v0, Lcom/android/camera/effect/renders/GaussianMaskEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/GaussianMaskEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addPartRender(Lcom/android/camera/effect/renders/Render;)V

    goto :goto_11

    :cond_1e
    :goto_d
    new-instance v12, Lcom/android/camera/effect/renders/PipeRenderPair;

    sget v2, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GAUSSIAN:I

    new-instance v3, Lcom/android/camera/effect/renders/PipeRenderPair;

    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-eqz v0, :cond_1f

    invoke-virtual {v7, v10}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    goto :goto_e

    :cond_1f
    new-instance v0, Lcom/android/camera/effect/renders/XGaussianEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/XGaussianEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_e
    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    if-eqz v1, :cond_20

    invoke-virtual {v7, v11}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v1

    goto :goto_f

    :cond_20
    new-instance v1, Lcom/android/camera/effect/renders/YGaussianEffectRender;

    invoke-direct {v1, v6}, Lcom/android/camera/effect/renders/YGaussianEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_f
    invoke-direct {v3, v6, v0, v1, v10}, Lcom/android/camera/effect/renders/PipeRenderPair;-><init>(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/Render;Lcom/android/camera/effect/renders/Render;Z)V

    invoke-virtual {v7, v9}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-eqz v0, :cond_21

    invoke-virtual {v7, v9}, Lcom/android/camera/effect/renders/RenderGroup;->getPartRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    goto :goto_10

    :cond_21
    new-instance v0, Lcom/android/camera/effect/renders/GaussianMaskEffectRender;

    invoke-direct {v0, v6}, Lcom/android/camera/effect/renders/GaussianMaskEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    :goto_10
    move-object v4, v0

    const/4 v5, 0x0

    move-object v0, v12

    move-object/from16 v1, p1

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/effect/renders/PipeRenderPair;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILcom/android/camera/effect/renders/Render;Lcom/android/camera/effect/renders/Render;Z)V

    invoke-virtual {v7, v12}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    invoke-virtual/range {p2 .. p2}, Lcom/android/camera/effect/renders/RenderGroup;->clearPartRenders()V

    goto :goto_11

    :cond_22
    move v11, v0

    :cond_23
    :goto_11
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_PEAKINGMF:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_25

    invoke-static {}, Lb/c/a/c;->Rn()Z

    move-result v0

    if-eqz v0, :cond_25

    if-nez p4, :cond_25

    if-nez p3, :cond_24

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_PEAKINGMF:I

    if-eq v8, v0, :cond_24

    if-gez v8, :cond_25

    if-nez v11, :cond_25

    :cond_24
    new-instance v0, Lcom/android/camera/effect/renders/FocusPeakingRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_PEAKINGMF:I

    invoke-direct {v0, v6, v1}, Lcom/android/camera/effect/renders/FocusPeakingRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_25
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_KALEIDOSCOPE:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_27

    if-nez p3, :cond_26

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_KALEIDOSCOPE:I

    if-ne v8, v0, :cond_27

    :cond_26
    new-instance v0, Lcom/android/camera/effect/renders/KaleidoscopeRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_KALEIDOSCOPE:I

    invoke-virtual {p0}, Lcom/android/camera/effect/EffectController;->getCurrentKaleidoscope()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v0, v6, v1, v2}, Lcom/android/camera/effect/renders/KaleidoscopeRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILjava/lang/String;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_27
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_STICKER:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_29

    if-nez p3, :cond_28

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_STICKER:I

    if-eq v8, v0, :cond_28

    if-gez v8, :cond_29

    if-nez v11, :cond_29

    :cond_28
    new-instance v0, Lcom/android/camera/effect/renders/StickerRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_STICKER:I

    invoke-virtual {p0}, Lcom/android/camera/effect/EffectController;->getCurrentSticker()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v0, v6, v1, v2}, Lcom/android/camera/effect/renders/StickerRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILjava/lang/String;)V

    invoke-static {}, Lcom/android/camera/fragment/beauty/BeautyParameters;->getInstance()Lcom/android/camera/fragment/beauty/BeautyParameters;

    move-result-object v1

    invoke-virtual {v0}, Lcom/android/camera/effect/renders/StickerRender;->getMakeupProcessor()Lcom/miui/filtersdk/beauty/IntelligentBeautyProcessor;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/android/camera/fragment/beauty/BeautyParameters;->setStickerMakeupProcessor(Lcom/miui/filtersdk/beauty/IntelligentBeautyProcessor;)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_29
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_ZEBRA:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_2b

    if-nez p4, :cond_2b

    if-nez p3, :cond_2a

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_ZEBRA:I

    if-eq v8, v0, :cond_2a

    if-gez v8, :cond_2b

    if-nez v11, :cond_2b

    :cond_2a
    new-instance v0, Lcom/android/camera/effect/renders/ZebraRender;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_ZEBRA:I

    invoke-direct {v0, v6, v1}, Lcom/android/camera/effect/renders/ZebraRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_2b
    if-nez p3, :cond_2c

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_YUV2RGB:I

    if-eq v8, v0, :cond_2c

    if-gez v8, :cond_2d

    if-nez v11, :cond_2d

    :cond_2c
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_YUV2RGB:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_2d

    new-instance v0, Lcom/android/camera/effect/renders/YuvToRgbRender;

    invoke-direct {v0, v6, v8}, Lcom/android/camera/effect/renders/YuvToRgbRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_2d
    if-nez p3, :cond_2e

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_YUV4442RGB:I

    if-eq v8, v0, :cond_2e

    if-gez v8, :cond_2f

    if-nez v11, :cond_2f

    :cond_2e
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_YUV4442RGB:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_2f

    new-instance v0, Lcom/android/camera/effect/renders/Yuv444ToRgbRender;

    invoke-direct {v0, v6, v8}, Lcom/android/camera/effect/renders/Yuv444ToRgbRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_2f
    if-nez p3, :cond_30

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_RGB2YUV:I

    if-eq v8, v0, :cond_30

    if-gez v8, :cond_31

    if-nez v11, :cond_31

    :cond_30
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_RGB2YUV:I

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_31

    new-instance v0, Lcom/android/camera/effect/renders/RgbToYuvRender;

    invoke-direct {v0, v6, v8}, Lcom/android/camera/effect/renders/RgbToYuvRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    invoke-virtual {v7, v0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_31
    return-object v7
.end method

.method private getRenderByCategory(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;IZ)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 2

    invoke-direct {p0, p3}, Lcom/android/camera/effect/EffectController;->convertToFilterCategory(I)Lcom/miui/filtersdk/filter/helper/FilterCategory;

    move-result-object v0

    sget-object v1, Lcom/miui/filtersdk/filter/helper/FilterCategory;->UNKNOWN:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    if-ne v0, v1, :cond_0

    return-object p2

    :cond_0
    invoke-virtual {p0, p3}, Lcom/android/camera/effect/EffectController;->getFilterInfo(I)Ljava/util/ArrayList;

    move-result-object p3

    if-eqz p3, :cond_1

    invoke-virtual {p3}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p3

    :goto_0
    invoke-interface {p3}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-interface {p3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/effect/FilterInfo;

    invoke-virtual {v0}, Lcom/android/camera/effect/FilterInfo;->getId()I

    move-result v0

    invoke-direct {p0, p1, p2, p4, v0}, Lcom/android/camera/effect/EffectController;->getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :cond_1
    return-object p2
.end method

.method private getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 7

    if-gez p4, :cond_0

    return-object p2

    :cond_0
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v1, 0x0

    if-ne p4, v0, :cond_2

    invoke-virtual {p2, v0}, Lcom/android/camera/effect/renders/RenderGroup;->isNeedInit(I)Z

    move-result v0

    if-eqz v0, :cond_2

    if-eqz p3, :cond_1

    new-instance v1, Lcom/android/camera/effect/renders/NoneEffectRender;

    sget p0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    invoke-direct {v1, p1, p0}, Lcom/android/camera/effect/renders/NoneEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    :cond_1
    invoke-virtual {p2, v1}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    return-object p2

    :cond_2
    sget v0, Lcom/android/camera/effect/FilterInfo;->AI_SCENE_FILTER_ID_0_NONE:I

    if-ne p4, v0, :cond_4

    invoke-virtual {p2, v0}, Lcom/android/camera/effect/renders/RenderGroup;->isNeedInit(I)Z

    move-result v0

    if-eqz v0, :cond_4

    if-eqz p3, :cond_3

    new-instance v1, Lcom/android/camera/effect/renders/NoneEffectRender;

    sget p0, Lcom/android/camera/effect/FilterInfo;->AI_SCENE_FILTER_ID_0_NONE:I

    invoke-direct {v1, p1, p0}, Lcom/android/camera/effect/renders/NoneEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    :cond_3
    invoke-virtual {p2, v1}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    return-object p2

    :cond_4
    sget-object v0, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getRenderById: id = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p2, p4}, Lcom/android/camera/effect/renders/RenderGroup;->getRender(I)Lcom/android/camera/effect/renders/Render;

    move-result-object v0

    if-nez v0, :cond_a

    invoke-static {p4}, Lcom/android/camera/effect/FilterInfo;->getIndex(I)I

    move-result v0

    sget-object v2, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "getRenderById: index = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v2, -0x1

    if-le v0, v2, :cond_a

    invoke-static {}, Lcom/miui/filtersdk/filter/helper/FilterType;->values()[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v2

    array-length v2, v2

    if-ge v0, v2, :cond_a

    invoke-static {}, Lcom/miui/filtersdk/filter/helper/FilterType;->values()[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v2

    aget-object v2, v2, v0

    invoke-virtual {v2}, Lcom/miui/filtersdk/filter/helper/FilterType;->getFilterCategory()Lcom/miui/filtersdk/filter/helper/FilterCategory;

    move-result-object v3

    sget-object v4, Lcom/miui/filtersdk/filter/helper/FilterCategory;->AI:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    if-ne v3, v4, :cond_7

    iget v3, p0, Lcom/android/camera/effect/EffectController;->mAiColorCorrectionVersion:I

    const/4 v4, 0x1

    if-lt v3, v4, :cond_7

    invoke-virtual {v2, v3}, Lcom/miui/filtersdk/filter/helper/FilterType;->isAiCCLookupTableSupported(I)Z

    move-result v3

    if-nez v3, :cond_7

    sget-object p0, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getRenderById: "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v2, " does not support light color correction, reset to NONE"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p2, p4}, Lcom/android/camera/effect/renders/RenderGroup;->removeRender(I)V

    sget p0, Lcom/android/camera/effect/FilterInfo;->AI_SCENE_FILTER_ID_0_NONE:I

    invoke-virtual {p2, p0}, Lcom/android/camera/effect/renders/RenderGroup;->isNeedInit(I)Z

    move-result p0

    if-eqz p0, :cond_6

    if-eqz p3, :cond_5

    new-instance v1, Lcom/android/camera/effect/renders/NoneEffectRender;

    sget p0, Lcom/android/camera/effect/FilterInfo;->AI_SCENE_FILTER_ID_0_NONE:I

    invoke-direct {v1, p1, p0}, Lcom/android/camera/effect/renders/NoneEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    :cond_5
    invoke-virtual {p2, v1}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_6
    return-object p2

    :cond_7
    iget-boolean p3, p0, Lcom/android/camera/effect/EffectController;->mIsIndiaColorLookupTableAvailable:Z

    iget v1, p0, Lcom/android/camera/effect/EffectController;->mAiColorCorrectionVersion:I

    invoke-static {v2, p3, v1}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFilter(Lcom/miui/filtersdk/filter/helper/FilterType;ZI)Lcom/miui/filtersdk/filter/base/ColorLookupFilter;

    move-result-object p3

    if-eqz p3, :cond_8

    invoke-direct {p0, v2}, Lcom/android/camera/effect/EffectController;->getDegree(Lcom/miui/filtersdk/filter/helper/FilterType;)I

    move-result p0

    invoke-virtual {p3, p0}, Lcom/miui/filtersdk/filter/base/ColorLookupFilter;->setDegree(I)V

    :cond_8
    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterType;->L_2_NEON:Lcom/miui/filtersdk/filter/helper/FilterType;

    invoke-virtual {p0}, Ljava/lang/Enum;->ordinal()I

    move-result p0

    if-lt v0, p0, :cond_9

    sget-object p0, Lcom/miui/filtersdk/filter/helper/FilterType;->L_2_DREAMLAND:Lcom/miui/filtersdk/filter/helper/FilterType;

    invoke-virtual {p0}, Ljava/lang/Enum;->ordinal()I

    move-result p0

    if-gt v0, p0, :cond_9

    new-instance p0, Lcom/android/camera/effect/renders/PipeRenderPair;

    new-instance v4, Lcom/android/camera/effect/renders/WrapperRender;

    invoke-direct {v4, p1, p4, p3}, Lcom/android/camera/effect/renders/WrapperRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILcom/miui/filtersdk/filter/base/GPUImageFilter;)V

    new-instance v5, Lcom/android/camera/effect/renders/LightEffectRender;

    invoke-direct {v5, p1, v0}, Lcom/android/camera/effect/renders/LightEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    const/4 v6, 0x0

    move-object v1, p0

    move-object v2, p1

    move v3, p4

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/effect/renders/PipeRenderPair;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILcom/android/camera/effect/renders/Render;Lcom/android/camera/effect/renders/Render;Z)V

    goto :goto_0

    :cond_9
    new-instance p0, Lcom/android/camera/effect/renders/WrapperRender;

    invoke-direct {p0, p1, p4, p3}, Lcom/android/camera/effect/renders/WrapperRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;ILcom/miui/filtersdk/filter/base/GPUImageFilter;)V

    :goto_0
    invoke-virtual {p2, p0}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    :cond_a
    return-object p2
.end method

.method private getStickerRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 1

    const/4 v0, 0x0

    if-eqz p3, :cond_0

    const/4 p3, 0x3

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/android/camera/effect/EffectController;->getRenderByCategory(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;IZ)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2

    :cond_0
    invoke-direct {p0, p1, p2, v0, p4}, Lcom/android/camera/effect/EffectController;->getRenderById(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    return-object p2
.end method

.method private initAiSceneFilterInfo()Ljava/util/ArrayList;
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    sget-object v0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->AI:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v0}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v0

    new-instance v1, Lcom/android/camera/effect/FilterInfo;

    sget v2, Lcom/android/camera/effect/FilterInfo;->AI_SCENE_FILTER_ID_0_NONE:I

    const/4 v3, 0x0

    invoke-direct {v1, v2, v3}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    array-length v1, v0

    const/4 v2, 0x1

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v4, v0, v3

    sget-object v5, Lcom/miui/filtersdk/filter/helper/FilterType;->A_COMMON:Lcom/miui/filtersdk/filter/helper/FilterType;

    if-ne v4, v5, :cond_0

    goto :goto_1

    :cond_0
    new-instance v5, Lcom/android/camera/effect/FilterInfo;

    invoke-static {v4}, Lcom/android/camera/effect/EffectController;->createAiSceneEffectId(Lcom/miui/filtersdk/filter/helper/FilterType;)I

    move-result v4

    add-int/lit8 v6, v2, 0x1

    invoke-direct {v5, v4, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move v2, v6

    :goto_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_1
    return-object p0
.end method

.method private initAppVideoFilterInfo()Ljava/util/ArrayList;
    .locals 14
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v2, 0x0

    const v3, 0x7f100360

    const v4, 0x7f080537

    invoke-direct {v0, v1, v3, v4, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(IIII)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->VIDEO:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v0}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v0

    array-length v1, v0

    move v3, v2

    move v4, v3

    move v5, v4

    move v6, v5

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v7, v0, v3

    sget-object v8, Lcom/android/camera/effect/EffectController$1;->$SwitchMap$com$miui$filtersdk$filter$helper$FilterType:[I

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v9

    aget v8, v8, v9

    packed-switch v8, :pswitch_data_0

    move v11, v4

    move v12, v5

    move v4, v6

    goto/16 :goto_2

    :pswitch_0
    const/16 v4, 0x50

    const v5, 0x7f1005d7

    const v6, 0x7f08053c

    goto :goto_1

    :pswitch_1
    const/16 v4, 0x48

    const v5, 0x7f100142

    const v6, 0x7f080532

    goto :goto_1

    :pswitch_2
    const/16 v4, 0x47

    const v5, 0x7f10014f

    const v6, 0x7f080535

    goto :goto_1

    :pswitch_3
    const/16 v4, 0x46

    const v5, 0x7f1005d5

    const v6, 0x7f08053a

    goto :goto_1

    :pswitch_4
    const/16 v4, 0x3c

    const v5, 0x7f1005cd

    const v6, 0x7f080533

    goto :goto_1

    :pswitch_5
    const/16 v4, 0x32

    const v5, 0x7f1005d3

    const v6, 0x7f080538

    goto :goto_1

    :pswitch_6
    const/16 v4, 0x28

    const v5, 0x7f1005d9

    const v6, 0x7f08053e

    goto :goto_1

    :pswitch_7
    const/16 v4, 0x1e

    const v5, 0x7f1005d4

    const v6, 0x7f080539

    goto :goto_1

    :pswitch_8
    const/16 v4, 0x14

    const v5, 0x7f1005cf

    const v6, 0x7f080536

    goto :goto_1

    :pswitch_9
    const/16 v4, 0xa

    const v5, 0x7f1005d8

    const v6, 0x7f08053d

    goto :goto_1

    :pswitch_a
    const/16 v4, 0x8

    const v5, 0x7f10014d

    const v6, 0x7f08053b

    goto :goto_1

    :pswitch_b
    const/4 v4, 0x7

    const v5, 0x7f100141

    const v6, 0x7f080531

    :goto_1
    move v11, v5

    move v12, v6

    :goto_2
    if-eqz v11, :cond_0

    if-eqz v12, :cond_0

    new-instance v5, Lcom/android/camera/effect/FilterInfo;

    const/16 v9, 0x8

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v10

    move-object v8, v5

    move v13, v4

    invoke-direct/range {v8 .. v13}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    invoke-virtual {p0, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move v5, v2

    move v11, v5

    goto :goto_3

    :cond_0
    move v5, v12

    :goto_3
    add-int/lit8 v3, v3, 0x1

    move v6, v4

    move v4, v11

    goto/16 :goto_0

    :cond_1
    invoke-static {p0}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    return-object p0

    nop

    :pswitch_data_0
    .packed-switch 0x4a
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private static initBeautyFilterInfo()Ljava/util/ArrayList;
    .locals 15
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    new-instance v1, Lcom/android/camera/effect/FilterInfo;

    sget v2, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v3, 0x0

    const v4, 0x7f100360

    const v5, 0x7f0804df

    invoke-direct {v1, v2, v4, v5, v3}, Lcom/android/camera/effect/FilterInfo;-><init>(IIII)V

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    sget-object v1, Lcom/miui/filtersdk/filter/helper/FilterCategory;->BEAUTY:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v1}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v1

    array-length v2, v1

    move v4, v3

    move v5, v4

    move v6, v5

    move v7, v6

    :goto_0
    if-ge v4, v2, :cond_1

    aget-object v8, v1, v4

    sget-object v9, Lcom/android/camera/effect/EffectController$1;->$SwitchMap$com$miui$filtersdk$filter$helper$FilterType:[I

    invoke-virtual {v8}, Ljava/lang/Enum;->ordinal()I

    move-result v10

    aget v9, v9, v10

    packed-switch v9, :pswitch_data_0

    move v12, v5

    move v13, v6

    move v5, v7

    goto/16 :goto_2

    :pswitch_0
    const/16 v5, 0x96

    const v6, 0x7f1002fe

    const v7, 0x7f0804d8

    goto :goto_1

    :pswitch_1
    const/16 v5, 0x8c

    const v6, 0x7f10030b

    const v7, 0x7f0804da

    goto :goto_1

    :pswitch_2
    const/16 v5, 0x82

    const v6, 0x7f100303

    const v7, 0x7f0804d9

    goto :goto_1

    :pswitch_3
    const/16 v5, 0x78

    const v6, 0x7f10030d

    const v7, 0x7f0804db

    goto :goto_1

    :pswitch_4
    const/16 v5, 0x6e

    const v6, 0x7f100306

    const v7, 0x7f0804dd

    goto :goto_1

    :pswitch_5
    const/16 v5, 0x64

    const v6, 0x7f100309

    const v7, 0x7f0804e1

    goto :goto_1

    :pswitch_6
    const/16 v5, 0x50

    const v6, 0x7f100304

    const v7, 0x7f0804dc

    goto :goto_1

    :pswitch_7
    const/16 v5, 0x32

    const v6, 0x7f100301

    const v7, 0x7f0804d6

    goto :goto_1

    :pswitch_8
    const/16 v5, 0x28

    const v6, 0x7f10030c

    const v7, 0x7f0804e2

    goto :goto_1

    :pswitch_9
    const/16 v5, 0x1e

    const v6, 0x7f100308

    const v7, 0x7f0804e0

    goto :goto_1

    :pswitch_a
    const/16 v5, 0x14

    const v6, 0x7f100302

    const v7, 0x7f0804d7

    goto :goto_1

    :pswitch_b
    const/16 v5, 0xa

    const v6, 0x7f100307

    const v7, 0x7f0804de

    :goto_1
    move v12, v6

    move v13, v7

    :goto_2
    if-eqz v12, :cond_0

    if-eqz v13, :cond_0

    new-instance v6, Lcom/android/camera/effect/FilterInfo;

    const/4 v10, 0x2

    invoke-virtual {v8}, Ljava/lang/Enum;->ordinal()I

    move-result v11

    move-object v9, v6

    move v14, v5

    invoke-direct/range {v9 .. v14}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    invoke-virtual {v0, v6}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move v6, v3

    move v12, v6

    goto :goto_3

    :cond_0
    move v6, v13

    :goto_3
    add-int/lit8 v4, v4, 0x1

    move v7, v5

    move v5, v12

    goto/16 :goto_0

    :cond_1
    invoke-static {v0}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    return-object v0

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private static initIndiaBeautyFilterInfo()Ljava/util/ArrayList;
    .locals 15
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    new-instance v1, Lcom/android/camera/effect/FilterInfo;

    sget v2, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v3, 0x0

    const v4, 0x7f100360

    const v5, 0x7f0804eb

    invoke-direct {v1, v2, v4, v5, v3}, Lcom/android/camera/effect/FilterInfo;-><init>(IIII)V

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    sget-object v1, Lcom/miui/filtersdk/filter/helper/FilterCategory;->BEAUTY_INDIA:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v1}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v1

    array-length v2, v1

    move v4, v3

    move v5, v4

    move v6, v5

    move v7, v6

    :goto_0
    if-ge v4, v2, :cond_1

    aget-object v8, v1, v4

    sget-object v9, Lcom/android/camera/effect/EffectController$1;->$SwitchMap$com$miui$filtersdk$filter$helper$FilterType:[I

    invoke-virtual {v8}, Ljava/lang/Enum;->ordinal()I

    move-result v10

    aget v9, v9, v10

    packed-switch v9, :pswitch_data_0

    move v12, v5

    move v13, v6

    move v5, v7

    goto/16 :goto_2

    :pswitch_0
    const/16 v5, 0xa0

    const v6, 0x7f1002fe

    const v7, 0x7f0804e4

    goto :goto_1

    :pswitch_1
    const/16 v5, 0x96

    const v6, 0x7f10030b

    const v7, 0x7f0804e7

    goto :goto_1

    :pswitch_2
    const/16 v5, 0x8c

    const v6, 0x7f10030d

    const v7, 0x7f0804e8

    goto :goto_1

    :pswitch_3
    const/16 v5, 0x82

    const v6, 0x7f100303

    const v7, 0x7f0804e6

    goto :goto_1

    :pswitch_4
    const/16 v5, 0x78

    const v6, 0x7f100300

    const v7, 0x7f0804e5

    goto :goto_1

    :pswitch_5
    const/16 v5, 0x64

    const v6, 0x7f100316

    const v7, 0x7f0804ef

    goto :goto_1

    :pswitch_6
    const/16 v5, 0x50

    const v6, 0x7f100315

    const v7, 0x7f0804ee

    goto :goto_1

    :pswitch_7
    const/16 v5, 0x32

    const v6, 0x7f10031a

    const v7, 0x7f0804f3

    goto :goto_1

    :pswitch_8
    const/16 v5, 0x28

    const v6, 0x7f100317

    const v7, 0x7f0804f0

    goto :goto_1

    :pswitch_9
    const/16 v5, 0x1e

    const v6, 0x7f100311

    const v7, 0x7f0804e9

    goto :goto_1

    :pswitch_a
    const/16 v5, 0x14

    const v6, 0x7f100313

    const v7, 0x7f0804ec

    goto :goto_1

    :pswitch_b
    const/16 v5, 0xa

    const v6, 0x7f100318

    const v7, 0x7f0804f1

    :goto_1
    move v12, v6

    move v13, v7

    :goto_2
    if-eqz v12, :cond_0

    if-eqz v13, :cond_0

    new-instance v6, Lcom/android/camera/effect/FilterInfo;

    const/4 v10, 0x2

    invoke-virtual {v8}, Ljava/lang/Enum;->ordinal()I

    move-result v11

    move-object v9, v6

    move v14, v5

    invoke-direct/range {v9 .. v14}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    invoke-virtual {v0, v6}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move v6, v3

    move v12, v6

    goto :goto_3

    :cond_0
    move v6, v13

    :goto_3
    add-int/lit8 v4, v4, 0x1

    move v7, v5

    move v5, v12

    goto/16 :goto_0

    :cond_1
    invoke-static {v0}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    return-object v0

    nop

    :pswitch_data_0
    .packed-switch 0xd
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private initLightingFilterInfo()Ljava/util/ArrayList;
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    sget-object v0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->LIGHTING:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v0}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v0

    new-instance v1, Lcom/android/camera/effect/FilterInfo;

    sget v2, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v3, 0x0

    invoke-direct {v1, v2, v3}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    array-length v1, v0

    const-string v2, "0"

    :goto_0
    if-ge v3, v1, :cond_0

    aget-object v4, v0, v3

    sget-object v5, Lcom/android/camera/effect/EffectController$1;->$SwitchMap$com$miui$filtersdk$filter$helper$FilterType:[I

    invoke-virtual {v4}, Ljava/lang/Enum;->ordinal()I

    move-result v6

    aget v5, v5, v6

    packed-switch v5, :pswitch_data_0

    goto :goto_1

    :pswitch_0
    const-string v2, "17"

    goto :goto_1

    :pswitch_1
    const-string v2, "16"

    goto :goto_1

    :pswitch_2
    const-string v2, "15"

    goto :goto_1

    :pswitch_3
    const-string v2, "14"

    goto :goto_1

    :pswitch_4
    const-string v2, "13"

    goto :goto_1

    :pswitch_5
    const-string v2, "12"

    goto :goto_1

    :pswitch_6
    const-string v2, "11"

    goto :goto_1

    :pswitch_7
    const-string v2, "10"

    goto :goto_1

    :pswitch_8
    const-string v2, "9"

    goto :goto_1

    :pswitch_9
    const-string v2, "8"

    goto :goto_1

    :pswitch_a
    const-string v2, "7"

    goto :goto_1

    :pswitch_b
    const-string v2, "6"

    goto :goto_1

    :pswitch_c
    const-string v2, "5"

    goto :goto_1

    :pswitch_d
    const-string v2, "4"

    goto :goto_1

    :pswitch_e
    const-string v2, "3"

    goto :goto_1

    :pswitch_f
    const-string v2, "2"

    goto :goto_1

    :pswitch_10
    const-string v2, "1"

    :goto_1
    new-instance v5, Lcom/android/camera/effect/FilterInfo;

    const/4 v6, 0x6

    invoke-virtual {v4}, Ljava/lang/Enum;->ordinal()I

    move-result v4

    invoke-static {v6, v4}, Lcom/android/camera/effect/FilterInfo;->getId(II)I

    move-result v4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v6

    invoke-direct {v5, v4, v6}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_0
    return-object p0

    :pswitch_data_0
    .packed-switch 0x19
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private initMiLiveFilterInfo()Ljava/util/ArrayList;
    .locals 14
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v2, 0x0

    const v3, 0x7f10015a

    const v4, 0x7f08010a

    invoke-direct {v0, v1, v3, v4, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(IIII)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->MI_LIVE:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v0}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v0

    array-length v1, v0

    move v3, v2

    move v4, v3

    move v5, v4

    move v6, v5

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v7, v0, v3

    sget-object v8, Lcom/android/camera/effect/EffectController$1;->$SwitchMap$com$miui$filtersdk$filter$helper$FilterType:[I

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v9

    aget v8, v8, v9

    packed-switch v8, :pswitch_data_0

    move v11, v4

    move v12, v5

    move v4, v6

    goto :goto_2

    :pswitch_0
    const/16 v4, 0x46

    const v5, 0x7f100156

    const v6, 0x7f080106

    goto :goto_1

    :pswitch_1
    const/16 v4, 0x3c

    const v5, 0x7f10015c

    const v6, 0x7f08010c

    goto :goto_1

    :pswitch_2
    const/16 v4, 0x32

    const v5, 0x7f100155

    const v6, 0x7f080105

    goto :goto_1

    :pswitch_3
    const/16 v4, 0x28

    const v5, 0x7f100154

    const v6, 0x7f080104

    goto :goto_1

    :pswitch_4
    const/16 v4, 0x1e

    const v5, 0x7f10015b

    const v6, 0x7f08010b

    goto :goto_1

    :pswitch_5
    const/16 v4, 0x14

    const v5, 0x7f100159

    const v6, 0x7f080109

    goto :goto_1

    :pswitch_6
    const/16 v4, 0xa

    const v5, 0x7f100157

    const v6, 0x7f080107

    :goto_1
    move v11, v5

    move v12, v6

    :goto_2
    if-eqz v11, :cond_0

    if-eqz v12, :cond_0

    new-instance v5, Lcom/android/camera/effect/FilterInfo;

    const/16 v9, 0x8

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v10

    move-object v8, v5

    move v13, v4

    invoke-direct/range {v8 .. v13}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    invoke-virtual {p0, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move v5, v2

    move v11, v5

    goto :goto_3

    :cond_0
    move v5, v12

    :goto_3
    add-int/lit8 v3, v3, 0x1

    move v6, v4

    move v4, v11

    goto :goto_0

    :cond_1
    invoke-static {p0}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    return-object p0

    nop

    :pswitch_data_0
    .packed-switch 0x43
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private initNormalFilterInfoNew()Ljava/util/ArrayList;
    .locals 14
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v2, 0x0

    const v3, 0x7f100360

    const v4, 0x7f0800fb

    invoke-direct {v0, v1, v3, v4, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(IIII)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->NORMAL:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v0}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v0

    array-length v1, v0

    move v3, v2

    move v4, v3

    move v5, v4

    move v6, v5

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v7, v0, v3

    sget-object v8, Lcom/android/camera/effect/EffectController$1;->$SwitchMap$com$miui$filtersdk$filter$helper$FilterType:[I

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v9

    aget v8, v8, v9

    packed-switch v8, :pswitch_data_0

    move v11, v4

    move v12, v5

    move v4, v6

    goto/16 :goto_2

    :pswitch_0
    const/16 v4, 0xbe

    const v5, 0x7f100144

    const v6, 0x7f0800ed

    goto/16 :goto_1

    :pswitch_1
    const/16 v4, 0xb4

    const v5, 0x7f100143

    const v6, 0x7f0800ec

    goto/16 :goto_1

    :pswitch_2
    const/16 v4, 0xaa

    const v5, 0x7f100142

    const v6, 0x7f0800eb

    goto/16 :goto_1

    :pswitch_3
    const/16 v4, 0xa0

    const v5, 0x7f10014f

    const v6, 0x7f0800ff

    goto/16 :goto_1

    :pswitch_4
    const/16 v4, 0x96

    const v5, 0x7f100149

    const v6, 0x7f0800f3

    goto/16 :goto_1

    :pswitch_5
    const/16 v4, 0x8c

    const v5, 0x7f100151

    const v6, 0x7f080101

    goto/16 :goto_1

    :pswitch_6
    const/16 v4, 0x82

    const v5, 0x7f100303

    const v6, 0x7f0800f8

    goto/16 :goto_1

    :pswitch_7
    const/16 v4, 0x78

    const v5, 0x7f100301

    const v6, 0x7f0800f1

    goto :goto_1

    :pswitch_8
    const/16 v4, 0x6e

    const v5, 0x7f100308

    const v6, 0x7f0800fd

    goto :goto_1

    :pswitch_9
    const/16 v4, 0x64

    const v5, 0x7f100307

    const v6, 0x7f0800fa

    goto :goto_1

    :pswitch_a
    const/16 v4, 0x5a

    const v5, 0x7f100302

    const v6, 0x7f0800f5

    goto :goto_1

    :pswitch_b
    const/16 v4, 0x50

    const v5, 0x7f100150

    const v6, 0x7f080100

    goto :goto_1

    :pswitch_c
    const/16 v4, 0x46

    const v5, 0x7f100306

    const v6, 0x7f0800f9

    goto :goto_1

    :pswitch_d
    const/16 v4, 0x3c

    const v5, 0x7f10014b

    const v6, 0x7f0800f6

    goto :goto_1

    :pswitch_e
    const/16 v4, 0x32

    const v5, 0x7f100148

    const v6, 0x7f0800f2

    goto :goto_1

    :pswitch_f
    const/16 v4, 0x28

    const v5, 0x7f100146

    const v6, 0x7f0800ef

    goto :goto_1

    :pswitch_10
    const/16 v4, 0x1e

    const v5, 0x7f10014d

    const v6, 0x7f0800fc

    goto :goto_1

    :pswitch_11
    const/16 v4, 0x14

    const v5, 0x7f100141

    const v6, 0x7f0800ea

    goto :goto_1

    :pswitch_12
    const/16 v4, 0xa

    const v5, 0x7f100152

    const v6, 0x7f080102

    :goto_1
    move v11, v5

    move v12, v6

    :goto_2
    if-eqz v11, :cond_0

    if-eqz v12, :cond_0

    new-instance v5, Lcom/android/camera/effect/FilterInfo;

    const/4 v9, 0x1

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v10

    move-object v8, v5

    move v13, v4

    invoke-direct/range {v8 .. v13}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    invoke-virtual {p0, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move v5, v2

    move v11, v5

    goto :goto_3

    :cond_0
    move v5, v12

    :goto_3
    add-int/lit8 v3, v3, 0x1

    move v6, v4

    move v4, v11

    goto/16 :goto_0

    :cond_1
    invoke-static {p0}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    return-object p0

    nop

    :pswitch_data_0
    .packed-switch 0x2a
        :pswitch_12
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private initPrivateFilterInfo()Ljava/util/ArrayList;
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_BLUR:I

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GRADIENTER:I

    const/4 v2, 0x1

    invoke-direct {v0, v1, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_TILTSHIFT:I

    const/4 v3, 0x2

    invoke-direct {v0, v1, v3}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {v0, v2}, Lcom/android/camera/effect/FilterInfo;->setNeedRect(Z)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_GAUSSIAN:I

    const/4 v3, 0x3

    invoke-direct {v0, v1, v3}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {v0, v2}, Lcom/android/camera/effect/FilterInfo;->setNeedRect(Z)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_PEAKINGMF:I

    const/4 v2, 0x4

    invoke-direct {v0, v1, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_STICKER:I

    const/4 v2, 0x5

    invoke-direct {v0, v1, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_ZEBRA:I

    const/4 v2, 0x6

    invoke-direct {v0, v1, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_CINEMATIC:I

    const/4 v2, 0x7

    invoke-direct {v0, v1, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(II)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    return-object p0
.end method

.method private initStickerFilterInfo()Ljava/util/ArrayList;
    .locals 14
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    const/4 v2, 0x0

    const v3, 0x7f100360

    const v4, 0x7f08052f

    invoke-direct {v0, v1, v3, v4, v2}, Lcom/android/camera/effect/FilterInfo;-><init>(IIII)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    sget-object v0, Lcom/miui/filtersdk/filter/helper/FilterCategory;->STICKER:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v0}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v0

    array-length v1, v0

    move v3, v2

    move v4, v3

    move v5, v4

    move v6, v5

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v7, v0, v3

    sget-object v8, Lcom/android/camera/effect/EffectController$1;->$SwitchMap$com$miui$filtersdk$filter$helper$FilterType:[I

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v9

    aget v8, v8, v9

    packed-switch v8, :pswitch_data_0

    move v11, v4

    move v12, v5

    move v4, v6

    goto :goto_2

    :pswitch_0
    const/16 v4, 0x32

    const v5, 0x7f1005cb

    const v6, 0x7f08052a

    goto :goto_1

    :pswitch_1
    const/16 v4, 0x2d

    const v5, 0x7f1005cc

    const v6, 0x7f08052b

    goto :goto_1

    :pswitch_2
    const/16 v4, 0x28

    const v5, 0x7f1005d1

    const v6, 0x7f08052d

    goto :goto_1

    :pswitch_3
    const/16 v4, 0x1e

    const v5, 0x7f1005d6

    const v6, 0x7f080530

    goto :goto_1

    :pswitch_4
    const/16 v4, 0x14

    const v5, 0x7f1005d2

    const v6, 0x7f08052e

    goto :goto_1

    :pswitch_5
    const/16 v4, 0xa

    const v5, 0x7f1005d0

    const v6, 0x7f08052c

    :goto_1
    move v11, v5

    move v12, v6

    :goto_2
    if-eqz v11, :cond_0

    if-eqz v12, :cond_0

    new-instance v5, Lcom/android/camera/effect/FilterInfo;

    const/4 v9, 0x3

    invoke-virtual {v7}, Ljava/lang/Enum;->ordinal()I

    move-result v10

    move-object v8, v5

    move v13, v4

    invoke-direct/range {v8 .. v13}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    invoke-virtual {p0, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    move v5, v2

    move v11, v5

    goto :goto_3

    :cond_0
    move v5, v12

    :goto_3
    add-int/lit8 v3, v3, 0x1

    move v6, v4

    move v4, v11

    goto :goto_0

    :cond_1
    invoke-static {p0}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    return-object p0

    nop

    :pswitch_data_0
    .packed-switch 0x3d
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private initVideoFilterInfo()Ljava/util/ArrayList;
    .locals 13
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v6, Lcom/android/camera/effect/FilterInfo;

    const/4 v5, 0x0

    const v3, 0x7f100360

    const v4, 0x7f080537

    const/4 v1, 0x7

    move-object v0, v6

    move v2, v5

    invoke-direct/range {v0 .. v5}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/4 v0, 0x0

    invoke-virtual {v6, v0}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v6}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/4 v12, 0x5

    const v10, 0x7f1005ce

    const v11, 0x7f080534

    const/4 v8, 0x7

    move-object v7, v0

    move v9, v12

    invoke-direct/range {v7 .. v12}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0xc8

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/4 v7, 0x6

    const v5, 0x7f100141

    const v6, 0x7f080531

    const/4 v3, 0x7

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x6e

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/4 v7, 0x7

    const v5, 0x7f10014d

    const v6, 0x7f08053b

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x6f

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0xa

    const v5, 0x7f1005d8

    const v6, 0x7f08053d

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x66

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x14

    const v5, 0x7f1005cf

    const v6, 0x7f080536

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x67

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x1e

    const v5, 0x7f1005d4

    const v6, 0x7f080539

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x68

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x28

    const v5, 0x7f1005d9

    const v6, 0x7f08053e

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x69

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x32

    const v5, 0x7f1005d3

    const v6, 0x7f080538

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x6a

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x3c

    const v5, 0x7f1005cd

    const v6, 0x7f080533

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x6b

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x46

    const v5, 0x7f1005d5

    const v6, 0x7f08053a

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x6c

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x47

    const v5, 0x7f10014f

    const v6, 0x7f080535

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x70

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x48

    const v5, 0x7f100142

    const v6, 0x7f080532

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x71

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/effect/FilterInfo;

    const/16 v7, 0x50

    const v5, 0x7f1005d7

    const v6, 0x7f08053c

    move-object v2, v0

    move v4, v7

    invoke-direct/range {v2 .. v7}, Lcom/android/camera/effect/FilterInfo;-><init>(IIIII)V

    const/16 v1, 0x6d

    invoke-virtual {v0, v1}, Lcom/android/camera/effect/FilterInfo;->setTagUniqueFilterId(I)V

    invoke-virtual {p0, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    invoke-static {p0}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    return-object p0
.end method

.method private initialize()V
    .locals 4

    invoke-static {}, Lcom/android/camera/effect/EffectController;->isIndiaColorLookupTableAvailable()Z

    move-result v0

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mIsIndiaColorLookupTableAvailable:Z

    new-instance v0, Landroid/util/SparseArray;

    const/4 v1, 0x6

    invoke-direct {v0, v1}, Landroid/util/SparseArray;-><init>(I)V

    iput-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initAiSceneFilterInfo()Ljava/util/ArrayList;

    move-result-object v2

    const/4 v3, 0x5

    invoke-virtual {v0, v3, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initPrivateFilterInfo()Ljava/util/ArrayList;

    move-result-object v2

    const/4 v3, 0x0

    invoke-virtual {v0, v3, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initNormalFilterInfoNew()Ljava/util/ArrayList;

    move-result-object v2

    const/4 v3, 0x1

    invoke-virtual {v0, v3, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initLightingFilterInfo()Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    iget-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mIsIndiaColorLookupTableAvailable:Z

    const/4 v1, 0x2

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-static {}, Lcom/android/camera/effect/EffectController;->initIndiaBeautyFilterInfo()Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initNormalFilterInfoNew()Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    :goto_0
    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    const/4 v1, 0x3

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initAppVideoFilterInfo()Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    const/4 v1, 0x7

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initVideoFilterInfo()Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    const/16 v1, 0x8

    invoke-direct {p0}, Lcom/android/camera/effect/EffectController;->initAppVideoFilterInfo()Ljava/util/ArrayList;

    move-result-object p0

    invoke-virtual {v0, v1, p0}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    return-void
.end method

.method private static isIndiaColorLookupTableAvailable()Z
    .locals 3

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v0

    invoke-virtual {v0}, Lb/c/a/b;->Hl()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    :try_start_0
    invoke-static {}, Lcom/android/camera/CameraAppImpl;->getAndroidContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    const-string v2, "filter/beauty_india"

    invoke-virtual {v0, v2}, Landroid/content/res/AssetManager;->list(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_1

    goto :goto_0

    :cond_1
    array-length v0, v0
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_1

    :catch_0
    sget-object v0, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    const-string v2, "Failed to list files in directory \'filter/beauty_india\'"

    invoke-static {v0, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    move v0, v1

    :goto_1
    sget-object v2, Lcom/miui/filtersdk/filter/helper/FilterCategory;->BEAUTY_INDIA:Lcom/miui/filtersdk/filter/helper/FilterCategory;

    invoke-static {v2}, Lcom/miui/filtersdk/filter/helper/FilterFactory;->getFiltersByCategory(Lcom/miui/filtersdk/filter/helper/FilterCategory;)[Lcom/miui/filtersdk/filter/helper/FilterType;

    move-result-object v2

    array-length v2, v2

    if-ne v0, v2, :cond_2

    const/4 v1, 0x1

    :cond_2
    return v1
.end method

.method private varargs postNotifyEffectChanged([I)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mChangedListeners:Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/effect/EffectController$EffectChangedListener;

    invoke-interface {v1, p1}, Lcom/android/camera/effect/EffectController$EffectChangedListener;->onEffectChanged([I)V

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

.method public static declared-synchronized releaseInstance()V
    .locals 2

    const-class v0, Lcom/android/camera/effect/EffectController;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/android/camera/effect/EffectController;->sInstance:Lcom/android/camera/effect/EffectController;

    if-eqz v1, :cond_0

    sget-object v1, Lcom/android/camera/effect/EffectController;->sInstance:Lcom/android/camera/effect/EffectController;

    iget-object v1, v1, Lcom/android/camera/effect/EffectController;->mChangedListeners:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    if-nez v1, :cond_0

    const/4 v1, 0x0

    sput-object v1, Lcom/android/camera/effect/EffectController;->sInstance:Lcom/android/camera/effect/EffectController;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    :cond_0
    monitor-exit v0

    return-void

    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method


# virtual methods
.method public addChangeListener(Lcom/android/camera/effect/EffectController$EffectChangedListener;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mChangedListeners:Ljava/util/List;

    invoke-interface {p0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-static {p1}, Lcom/android/camera/EffectChangedListenerController;->addEffectChangedListener(Lcom/android/camera/effect/EffectController$EffectChangedListener;)V

    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public clearEffectAttribute()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object v0, v0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mRectF:Landroid/graphics/RectF;

    const/4 v1, 0x0

    invoke-virtual {v0, v1, v1, v1, v1}, Landroid/graphics/RectF;->set(FFFF)V

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object v0, v0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mPoint1:Landroid/graphics/PointF;

    invoke-virtual {v0, v1, v1}, Landroid/graphics/PointF;->set(FF)V

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object v0, v0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mPoint2:Landroid/graphics/PointF;

    invoke-virtual {v0, v1, v1}, Landroid/graphics/PointF;->set(FF)V

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iput v1, p0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mRangeWidth:F

    return-void
.end method

.method public copyEffectRectAttribute()Lcom/android/camera/effect/EffectController$EffectRectAttribute;
    .locals 2

    new-instance v0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/android/camera/effect/EffectController$EffectRectAttribute;-><init>(Lcom/android/camera/effect/EffectController$EffectRectAttribute;Lcom/android/camera/effect/EffectController$1;)V

    return-object v0
.end method

.method public enableMakeup(Z)V
    .locals 2

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mBeautyEnable:Z

    const/4 v0, 0x1

    if-nez p1, :cond_0

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mNeedDestroyMakeup:Z

    :cond_0
    new-array p1, v0, [I

    const/4 v0, 0x0

    const/4 v1, 0x3

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public findLiveFilter(Landroid/content/Context;I)Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;
    .locals 2

    invoke-virtual {p0, p1}, Lcom/android/camera/effect/EffectController;->getLiveFilterList(Landroid/content/Context;)Ljava/util/List;

    move-result-object p0

    const/4 p1, 0x0

    if-nez p0, :cond_0

    return-object p1

    :cond_0
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_1
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;

    iget v1, v0, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;->id:I

    if-ne v1, p2, :cond_1

    return-object v0

    :cond_2
    return-object p1
.end method

.method public getAiColorCorrectionVersion()I
    .locals 3

    sget-object v0, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getAiColorCorrectionVersion: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/android/camera/effect/EffectController;->mAiColorCorrectionVersion:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget p0, p0, Lcom/android/camera/effect/EffectController;->mAiColorCorrectionVersion:I

    return p0
.end method

.method public getBlurAnimationValue()I
    .locals 4

    iget v0, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    const/4 v1, -0x1

    if-ltz v0, :cond_2

    const/16 v2, 0x8

    if-gt v0, v2, :cond_2

    iget-boolean v3, p0, Lcom/android/camera/effect/EffectController;->mBlur:Z

    if-eqz v3, :cond_0

    const/4 v3, 0x1

    goto :goto_0

    :cond_0
    move v3, v1

    :goto_0
    add-int/2addr v0, v3

    iput v0, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    iget v0, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    if-gt v2, v0, :cond_1

    iget-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mBlur:Z

    if-eqz v0, :cond_1

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_BLUR:I

    iput v0, p0, Lcom/android/camera/effect/EffectController;->mOverrideEffectIndex:I

    :cond_1
    iget p0, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    if-ltz p0, :cond_2

    if-gt p0, v2, :cond_2

    mul-int/lit16 p0, p0, 0xd4

    div-int/2addr p0, v2

    return p0

    :cond_2
    return v1
.end method

.method public getCurrentKaleidoscope()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mDrawKaleidoscope:Ljava/lang/String;

    return-object p0
.end method

.method public getCurrentSticker()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mCurrentSticker:Ljava/lang/String;

    return-object p0
.end method

.method public getDeviceRotation()F
    .locals 0

    iget p0, p0, Lcom/android/camera/effect/EffectController;->mDeviceRotation:F

    return p0
.end method

.method public getEffectAttribute()Lcom/android/camera/effect/EffectController$EffectRectAttribute;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    return-object p0
.end method

.method public getEffectCount(I)I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-virtual {p0, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/util/ArrayList;

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p0}, Ljava/util/ArrayList;->size()I

    move-result p0

    return p0
.end method

.method public getEffectForPreview(Z)I
    .locals 2

    monitor-enter p0

    const/4 v0, -0x1

    if-eqz p1, :cond_0

    :try_start_0
    iget p1, p0, Lcom/android/camera/effect/EffectController;->mOverrideEffectIndex:I

    if-eq p1, v0, :cond_0

    iget p1, p0, Lcom/android/camera/effect/EffectController;->mOverrideEffectIndex:I

    monitor-exit p0

    return p1

    :cond_0
    iget p1, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-ne p1, v1, :cond_1

    iget p1, p0, Lcom/android/camera/effect/EffectController;->mOverrideAiEffectIndex:I

    if-eq p1, v0, :cond_1

    iget p1, p0, Lcom/android/camera/effect/EffectController;->mOverrideAiEffectIndex:I

    monitor-exit p0

    return p1

    :cond_1
    iget p1, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    monitor-exit p0

    return p1

    :catchall_0
    move-exception p1

    monitor-exit p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public getEffectForSaving(Z)I
    .locals 2

    iget v0, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-eq v0, v1, :cond_0

    invoke-static {v0}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result v0

    const/4 v1, 0x6

    if-eq v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    if-nez v0, :cond_1

    sget p0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    return p0

    :cond_1
    iget v0, p0, Lcom/android/camera/effect/EffectController;->mAiColorCorrectionVersion:I

    const/4 v1, 0x2

    if-ne v0, v1, :cond_2

    iget v0, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-eq v0, v1, :cond_2

    invoke-static {v0}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result v0

    const/4 v1, 0x5

    if-ne v0, v1, :cond_2

    sget p0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    return p0

    :cond_2
    invoke-virtual {p0, p1}, Lcom/android/camera/effect/EffectController;->getEffectForPreview(Z)I

    move-result p0

    return p0
.end method

.method public getEffectGroup(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;
    .locals 4

    invoke-static {}, Lb/c/a/c;->Un()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    if-nez p3, :cond_1

    invoke-virtual {p2, p5}, Lcom/android/camera/effect/renders/RenderGroup;->isNeedInit(I)Z

    move-result v0

    if-nez v0, :cond_1

    return-object p2

    :cond_1
    const/4 v0, 0x1

    const/4 v1, -0x1

    if-le p5, v1, :cond_2

    invoke-static {p5}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result v0

    :cond_2
    sget-object v1, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getEffectGroup: renderId = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v1, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getEffectGroup: category = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    packed-switch v0, :pswitch_data_0

    :pswitch_0
    sget-object p0, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "invalid renderId "

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p5}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :pswitch_1
    invoke-direct {p0, p1, p2, p3, p5}, Lcom/android/camera/effect/EffectController;->getMiLiveRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :pswitch_2
    invoke-direct/range {p0 .. p5}, Lcom/android/camera/effect/EffectController;->getLightingRenderNew(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :pswitch_3
    invoke-direct/range {p0 .. p5}, Lcom/android/camera/effect/EffectController;->getAiSceneRenderNew(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :pswitch_4
    invoke-direct {p0, p1, p2}, Lcom/android/camera/effect/EffectController;->getMakeupRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :pswitch_5
    invoke-direct {p0, p1, p2, p3, p5}, Lcom/android/camera/effect/EffectController;->getStickerRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :pswitch_6
    invoke-direct {p0, p1, p2, p3, p5}, Lcom/android/camera/effect/EffectController;->getBeautyRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZI)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :pswitch_7
    invoke-direct/range {p0 .. p5}, Lcom/android/camera/effect/EffectController;->getNormalRenderNew(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;

    goto :goto_0

    :pswitch_8
    invoke-direct/range {p0 .. p5}, Lcom/android/camera/effect/EffectController;->getPrivateRender(Lcom/android/gallery3d/ui/GLCanvas;Lcom/android/camera/effect/renders/RenderGroup;ZZI)Lcom/android/camera/effect/renders/RenderGroup;

    :goto_0
    return-object p2

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method public getEffectRectF()Landroid/graphics/RectF;
    .locals 1

    new-instance v0, Landroid/graphics/RectF;

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object p0, p0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mRectF:Landroid/graphics/RectF;

    invoke-direct {v0, p0}, Landroid/graphics/RectF;-><init>(Landroid/graphics/RectF;)V

    return-object v0
.end method

.method public getFilterInfo(I)Ljava/util/ArrayList;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/ArrayList<",
            "Lcom/android/camera/effect/FilterInfo;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-virtual {p0, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/util/ArrayList;

    return-object p0
.end method

.method public getInvertFlag()I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget p0, p0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mInvertFlag:I

    return p0
.end method

.method public getLiveFilterList(Landroid/content/Context;)Ljava/util/List;
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            ")",
            "Ljava/util/List<",
            "Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;",
            ">;"
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mLiveFilters:Ljava/util/List;

    if-nez v0, :cond_1

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f03000b

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->obtainTypedArray(I)Landroid/content/res/TypedArray;

    move-result-object v0

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f03000c

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v2, 0x7f03000a

    invoke-virtual {p1, v2}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object p1

    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    iput-object v2, p0, Lcom/android/camera/effect/EffectController;->mLiveFilters:Ljava/util/List;

    const/4 v2, 0x0

    :goto_0
    invoke-virtual {v0}, Landroid/content/res/TypedArray;->length()I

    move-result v3

    if-ge v2, v3, :cond_0

    new-instance v3, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;

    invoke-direct {v3}, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;-><init>()V

    iput v2, v3, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;->id:I

    invoke-virtual {v0, v2}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    iput-object v4, v3, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;->imageViewRes:Landroid/graphics/drawable/Drawable;

    aget-object v4, v1, v2

    iput-object v4, v3, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;->name:Ljava/lang/String;

    aget-object v4, p1, v2

    iput-object v4, v3, Lcom/android/camera/fragment/beauty/LiveBeautyFilterFragment$LiveFilterItem;->directoryName:Ljava/lang/String;

    iget-object v4, p0, Lcom/android/camera/effect/EffectController;->mLiveFilters:Ljava/util/List;

    invoke-interface {v4, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Landroid/content/res/TypedArray;->recycle()V

    :cond_1
    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mLiveFilters:Ljava/util/List;

    return-object p0
.end method

.method public getOrientation()I
    .locals 0

    iget p0, p0, Lcom/android/camera/effect/EffectController;->mOrientation:I

    return p0
.end method

.method public getTiltShiftMaskAlpha()F
    .locals 0

    iget p0, p0, Lcom/android/camera/effect/EffectController;->mTiltShiftMaskAlpha:F

    return p0
.end method

.method public hasEffect()Z
    .locals 1

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/effect/EffectController;->hasEffect(Z)Z

    move-result p0

    return p0
.end method

.method public hasEffect(Z)Z
    .locals 12

    invoke-static {}, Lb/c/a/c;->Un()Z

    move-result v0

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isSquareModule()Z

    move-result v1

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTiltShiftOn()Z

    move-result v2

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object v3

    invoke-virtual {v3}, Lb/c/a/b;->Rl()Z

    move-result v3

    invoke-static {}, Lcom/android/camera/CameraSettings;->isDualCameraWaterMarkOpen()Z

    move-result v4

    invoke-static {}, Lcom/android/camera/CameraSettings;->isFrontCameraWaterMarkOpen()Z

    move-result v5

    invoke-static {}, Lcom/android/camera/CameraSettings;->isTimeWaterMarkOpen()Z

    move-result v6

    invoke-static {}, Lcom/android/camera/CameraSettings;->showGenderAge()Z

    move-result v7

    invoke-static {}, Lcom/android/camera/CameraSettings;->isMagicMirrorOn()Z

    move-result v8

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemGlobal()Lcom/android/camera/data/data/global/DataItemGlobal;

    move-result-object v9

    invoke-virtual {v9}, Lcom/android/camera/data/data/global/DataItemGlobal;->getCurrentMode()I

    move-result v9

    invoke-static {v9}, Lcom/android/camera/CameraSettings;->isCinematicAspectRatioEnabled(I)Z

    move-result v9

    const/4 v10, 0x1

    const/4 v11, 0x0

    if-nez v4, :cond_1

    if-nez v5, :cond_1

    if-nez v6, :cond_1

    if-nez v7, :cond_1

    if-eqz v8, :cond_0

    goto :goto_0

    :cond_0
    move v4, v11

    goto :goto_1

    :cond_1
    :goto_0
    move v4, v10

    :goto_1
    if-eqz v3, :cond_2

    if-eqz v4, :cond_2

    move v3, v10

    goto :goto_2

    :cond_2
    move v3, v11

    :goto_2
    if-eqz p1, :cond_4

    monitor-enter p0

    :try_start_0
    iget p1, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    sget v4, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-eq p1, v4, :cond_3

    iget p1, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    invoke-static {p1}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result p1

    const/4 v4, 0x6

    if-eq p1, v4, :cond_3

    move p1, v10

    goto :goto_3

    :cond_3
    move p1, v11

    :goto_3
    monitor-exit p0

    goto :goto_4

    :catchall_0
    move-exception p1

    monitor-exit p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1

    :cond_4
    move p1, v11

    :goto_4
    if-eqz v0, :cond_5

    if-nez p1, :cond_6

    if-nez v1, :cond_6

    if-nez v2, :cond_6

    if-nez v3, :cond_6

    if-eqz v9, :cond_5

    goto :goto_5

    :cond_5
    move v10, v11

    :cond_6
    :goto_5
    return v10
.end method

.method public isBackGroundBlur()Z
    .locals 2

    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/android/camera/effect/EffectController;->getEffectForPreview(Z)I

    move-result p0

    sget v1, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_BLUR:I

    if-ne p0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isBeautyFrameReady()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mBeautyFrameReady:Z

    return p0
.end method

.method public isBlurAnimationDone()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    const/16 v0, 0x8

    if-gt p0, v0, :cond_1

    if-gez p0, :cond_0

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

.method public isCinematicEnable()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mDrawCinematic:Z

    return p0
.end method

.method public isDrawGradienter()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mDrawGradienter:Z

    return p0
.end method

.method public isDrawTilt()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mDrawTilt:Z

    return p0
.end method

.method public isEffectPageSelected()Z
    .locals 1

    iget p0, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-eq p0, v0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isKaleidoscopeEnable()Z
    .locals 1

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mDrawKaleidoscope:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mDrawKaleidoscope:Ljava/lang/String;

    const-string v0, "0"

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    xor-int/lit8 p0, p0, 0x1

    return p0
.end method

.method public isMainFrameDisplay()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mIsDrawMainFrame:Z

    return p0
.end method

.method public isMakeupEnable()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mBeautyEnable:Z

    return p0
.end method

.method public isNeedDrawExposure()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mDrawExposure:Z

    return p0
.end method

.method public isNeedDrawPeaking()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mDrawPeaking:Z

    return p0
.end method

.method public isNeedRect(I)Z
    .locals 2

    invoke-static {p1}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result v0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mFilterInfoMap:Landroid/util/SparseArray;

    invoke-virtual {p0, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/util/ArrayList;

    if-eqz p0, :cond_1

    invoke-virtual {p0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/effect/FilterInfo;

    invoke-virtual {v0}, Lcom/android/camera/effect/FilterInfo;->getId()I

    move-result v1

    if-ne v1, p1, :cond_0

    invoke-virtual {v0}, Lcom/android/camera/effect/FilterInfo;->isNeedRect()Z

    move-result p0

    return p0

    :cond_1
    const/4 p0, 0x0

    return p0
.end method

.method public isStickerEnable()Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mCurrentSticker:Ljava/lang/String;

    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p0

    xor-int/lit8 p0, p0, 0x1

    return p0
.end method

.method public needDestroyMakeup()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/effect/EffectController;->mNeedDestroyMakeup:Z

    return p0
.end method

.method public notifyRealtimePreviewFilterChanged(I)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mChangedListeners:Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/effect/EffectController$EffectChangedListener;

    invoke-interface {v1, p1}, Lcom/android/camera/effect/EffectController$EffectChangedListener;->onRealtimePreviewFilterChanged(I)V

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

.method public removeChangeListener(Lcom/android/camera/effect/EffectController$EffectChangedListener;)Z
    .locals 2

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mLock:Ljava/lang/Object;

    monitor-enter v0

    :try_start_0
    iget-object v1, p0, Lcom/android/camera/effect/EffectController;->mChangedListeners:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    if-nez v1, :cond_0

    const/4 p0, 0x1

    monitor-exit v0

    return p0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mChangedListeners:Ljava/util/List;

    invoke-interface {p0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    move-result p0

    monitor-exit v0

    return p0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public reset()V
    .locals 2

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mBeautyEnable:Z

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/android/camera/effect/EffectController;->mNeedDestroyMakeup:Z

    const/4 v1, 0x0

    iput-object v1, p0, Lcom/android/camera/effect/EffectController;->mCurrentSticker:Ljava/lang/String;

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mDrawPeaking:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mDrawTilt:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mDrawGradienter:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mDrawExposure:Z

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mDrawCinematic:Z

    const-string v0, "0"

    iput-object v0, p0, Lcom/android/camera/effect/EffectController;->mDrawKaleidoscope:Ljava/lang/String;

    sget-object v0, Lcom/android/camera/effect/EffectController;->EFFECT_ALL_CHANGE_TYPES:[I

    invoke-direct {p0, v0}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setAiColorCorrectionVersion(I)V
    .locals 3

    sget-object v0, Lcom/android/camera/effect/EffectController;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setAiColorCorrectionVersion: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iput p1, p0, Lcom/android/camera/effect/EffectController;->mAiColorCorrectionVersion:I

    return-void
.end method

.method public setAiSceneEffect(IZ)V
    .locals 2

    invoke-static {p1}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    move-result v0

    const/4 v1, 0x5

    if-ne v0, v1, :cond_0

    iput p1, p0, Lcom/android/camera/effect/EffectController;->mOverrideAiEffectIndex:I

    invoke-virtual {p0, p1}, Lcom/android/camera/effect/EffectController;->setEffect(I)V

    goto :goto_0

    :cond_0
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-ne p1, v0, :cond_1

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/effect/EffectController;->mOverrideAiEffectIndex:I

    if-eqz p2, :cond_1

    invoke-virtual {p0, p1}, Lcom/android/camera/effect/EffectController;->setEffect(I)V

    :cond_1
    :goto_0
    return-void
.end method

.method public setBeautyFrameReady(Z)V
    .locals 2

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mBeautyFrameReady:Z

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 v0, 0x0

    const/4 v1, 0x3

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setBlurEffect(Z)V
    .locals 2

    iget-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mBlur:Z

    if-eq p1, v0, :cond_4

    if-nez p1, :cond_0

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/effect/EffectController;->mOverrideEffectIndex:I

    :cond_0
    iget v0, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    const/16 v1, 0x8

    if-ltz v0, :cond_1

    if-ge v1, v0, :cond_3

    :cond_1
    if-eqz p1, :cond_2

    const/4 v1, 0x0

    :cond_2
    iput v1, p0, Lcom/android/camera/effect/EffectController;->mBlurStep:I

    :cond_3
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/camera/effect/EffectController;->mIsDrawMainFrame:Z

    :cond_4
    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mBlur:Z

    return-void
.end method

.method public setCinematicEnable(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mDrawCinematic:Z

    return-void
.end method

.method public setCurrentSticker(Ljava/lang/String;)V
    .locals 2

    iput-object p1, p0, Lcom/android/camera/effect/EffectController;->mCurrentSticker:Ljava/lang/String;

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 v0, 0x0

    const/4 v1, 0x2

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setDestroyMakeup(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mNeedDestroyMakeup:Z

    return-void
.end method

.method public setDeviceRotation(ZF)V
    .locals 0

    iput p2, p0, Lcom/android/camera/effect/EffectController;->mDeviceRotation:F

    return-void
.end method

.method public setDrawExposure(Z)V
    .locals 2

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mDrawExposure:Z

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 v0, 0x0

    const/4 v1, 0x7

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setDrawGradienter(Z)V
    .locals 2

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mDrawGradienter:Z

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 v0, 0x0

    const/4 v1, 0x6

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setDrawPeaking(Z)V
    .locals 2

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mDrawPeaking:Z

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 v0, 0x0

    const/4 v1, 0x4

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setDrawTilt(Z)V
    .locals 2

    iput-boolean p1, p0, Lcom/android/camera/effect/EffectController;->mDrawTilt:Z

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 v0, 0x0

    const/4 v1, 0x5

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setEffect(I)V
    .locals 2

    monitor-enter p0

    :try_start_0
    sget v0, Lcom/android/camera/effect/FilterInfo;->FILTER_ID_NONE:I

    if-ne p1, v0, :cond_0

    iget v0, p0, Lcom/android/camera/effect/EffectController;->mOverrideAiEffectIndex:I

    const/4 v1, -0x1

    if-eq v0, v1, :cond_0

    iget p1, p0, Lcom/android/camera/effect/EffectController;->mOverrideAiEffectIndex:I

    iput p1, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    goto :goto_0

    :cond_0
    iput p1, p0, Lcom/android/camera/effect/EffectController;->mEffectId:I

    :goto_0
    const/4 p1, 0x1

    new-array v0, p1, [I

    const/4 v1, 0x0

    aput p1, v0, v1

    invoke-direct {p0, v0}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public setEffectAttribute(Landroid/graphics/RectF;Landroid/graphics/PointF;Landroid/graphics/PointF;F)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object v0, v0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mRectF:Landroid/graphics/RectF;

    invoke-virtual {v0, p1}, Landroid/graphics/RectF;->set(Landroid/graphics/RectF;)V

    iget-object p1, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object p1, p1, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mPoint1:Landroid/graphics/PointF;

    invoke-virtual {p1, p2}, Landroid/graphics/PointF;->set(Landroid/graphics/PointF;)V

    iget-object p1, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iget-object p1, p1, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mPoint2:Landroid/graphics/PointF;

    invoke-virtual {p1, p3}, Landroid/graphics/PointF;->set(Landroid/graphics/PointF;)V

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iput p4, p0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mRangeWidth:F

    return-void
.end method

.method public setInvertFlag(I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/effect/EffectController;->mEffectRectAttribute:Lcom/android/camera/effect/EffectController$EffectRectAttribute;

    iput p1, p0, Lcom/android/camera/effect/EffectController$EffectRectAttribute;->mInvertFlag:I

    return-void
.end method

.method public setKaleidoscope(Ljava/lang/String;)V
    .locals 2

    iput-object p1, p0, Lcom/android/camera/effect/EffectController;->mDrawKaleidoscope:Ljava/lang/String;

    const/4 p1, 0x1

    new-array p1, p1, [I

    const/4 v0, 0x0

    const/16 v1, 0x8

    aput v1, p1, v0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/EffectController;->postNotifyEffectChanged([I)V

    return-void
.end method

.method public setLightingEffect(I)V
    .locals 0

    invoke-static {p1}, Lcom/android/camera/effect/FilterInfo;->getCategory(I)I

    invoke-virtual {p0, p1}, Lcom/android/camera/effect/EffectController;->setEffect(I)V

    return-void
.end method

.method public setOrientation(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/effect/EffectController;->mOrientation:I

    return-void
.end method

.method public setTiltShiftMaskAlpha(F)V
    .locals 0

    iput p1, p0, Lcom/android/camera/effect/EffectController;->mTiltShiftMaskAlpha:F

    return-void
.end method
