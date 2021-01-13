.class public Lcom/android/camera/effect/renders/PipeRender;
.super Lcom/android/camera/effect/renders/RenderGroup;
.source "PipeRender.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;
    }
.end annotation


# static fields
.field private static final DUMP_TEXTURE:Z = false

.field private static final TAG:Ljava/lang/String; = "PipeRender"


# instance fields
.field private mBufferHeight:I

.field private mBufferMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;",
            ">;"
        }
    .end annotation
.end field

.field private mBufferWidth:I

.field private mFrameBufferTextures:[I

.field private mFrameBuffers:[I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 1

    invoke-direct {p0, p1}, Lcom/android/camera/effect/renders/RenderGroup;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    new-instance p1, Ljava/util/HashMap;

    const/4 v0, 0x1

    invoke-direct {p1, v0}, Ljava/util/HashMap;-><init>(I)V

    iput-object p1, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferMap:Ljava/util/HashMap;

    return-void
.end method

.method public constructor <init>(Lcom/android/gallery3d/ui/GLCanvas;I)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/effect/renders/RenderGroup;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    new-instance p1, Ljava/util/HashMap;

    const/4 p2, 0x1

    invoke-direct {p1, p2}, Ljava/util/HashMap;-><init>(I)V

    iput-object p1, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferMap:Ljava/util/HashMap;

    return-void
.end method

.method static synthetic access$400(Lcom/android/camera/effect/renders/PipeRender;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferWidth:I

    return p0
.end method

.method static synthetic access$500(Lcom/android/camera/effect/renders/PipeRender;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferHeight:I

    return p0
.end method

.method static synthetic access$600()Ljava/lang/String;
    .locals 1

    sget-object v0, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method private declared-synchronized destroyFrameBuffers()V
    .locals 10

    monitor-enter p0

    :try_start_0
    sget-object v0, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "destroyFrameBuffers: count="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBuffers:[I

    const/4 v3, 0x0

    if-nez v2, :cond_0

    move v2, v3

    goto :goto_0

    :cond_0
    iget-object v2, p0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBuffers:[I

    array-length v2, v2

    :goto_0
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_1
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_4

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;

    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$100(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v2

    if-eqz v2, :cond_1

    move v2, v3

    :goto_2
    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$100(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v4

    array-length v4, v4

    if-ge v2, v4, :cond_1

    sget-object v4, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    sget-object v5, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v6, "delete fbo thread=%d id=%d"

    const/4 v7, 0x2

    new-array v7, v7, [Ljava/lang/Object;

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/Thread;->getId()J

    move-result-wide v8

    invoke-static {v8, v9}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v8

    aput-object v8, v7, v3

    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$100(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v8

    aget v8, v8, v2

    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    const/4 v9, 0x1

    aput-object v8, v7, v9

    invoke-static {v5, v6, v7}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    add-int/lit8 v2, v2, 0x1

    goto :goto_2

    :cond_1
    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$200(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v2

    const/4 v4, 0x0

    if-eqz v2, :cond_2

    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$200(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v2

    array-length v2, v2

    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$200(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v5

    invoke-static {v2, v5, v3}, Landroid/opengl/GLES20;->glDeleteTextures(I[II)V

    invoke-static {v1, v4}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$202(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;[I)[I

    :cond_2
    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$100(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v2

    if-eqz v2, :cond_3

    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$100(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v2

    array-length v2, v2

    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$100(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object v5

    invoke-static {v2, v5, v3}, Landroid/opengl/GLES20;->glDeleteFramebuffers(I[II)V

    invoke-static {v1, v4}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$102(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;[I)[I

    :cond_3
    invoke-static {v1, v3}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$302(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;I)I

    goto :goto_1

    :cond_4
    iget-object v0, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->clear()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit p0

    return-void

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method


# virtual methods
.method public addRender(Lcom/android/camera/effect/renders/Render;)V
    .locals 1

    invoke-super {p0, p1}, Lcom/android/camera/effect/renders/RenderGroup;->addRender(Lcom/android/camera/effect/renders/Render;)V

    invoke-virtual {p0}, Lcom/android/camera/effect/renders/RenderGroup;->getRenderSize()I

    move-result p1

    add-int/lit8 p1, p1, -0x1

    iget-object v0, p0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBuffers:[I

    if-eqz v0, :cond_0

    array-length v0, v0

    if-le p1, v0, :cond_1

    :cond_0
    iget p1, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferWidth:I

    if-lez p1, :cond_1

    iget v0, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferHeight:I

    if-lez v0, :cond_1

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/effect/renders/PipeRender;->reInitFrameBuffers(II)V

    :cond_1
    return-void
.end method

.method public deleteBuffer()V
    .locals 0

    invoke-super {p0}, Lcom/android/camera/effect/renders/RenderGroup;->deleteBuffer()V

    invoke-direct {p0}, Lcom/android/camera/effect/renders/PipeRender;->destroyFrameBuffers()V

    return-void
.end method

.method public draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z
    .locals 25

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    iget-object v2, v0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBuffers:[I

    if-eqz v2, :cond_e

    iget-object v2, v0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBufferTextures:[I

    if-nez v2, :cond_0

    goto/16 :goto_9

    :cond_0
    invoke-virtual/range {p1 .. p1}, Lcom/android/camera/effect/draw_mode/DrawAttribute;->getTarget()I

    move-result v2

    const/4 v4, 0x5

    const/16 v5, 0xb

    if-eq v2, v4, :cond_4

    const/4 v4, 0x6

    if-eq v2, v4, :cond_3

    if-eq v2, v5, :cond_1

    sget-object v4, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "unsupported target "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v4, v7}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v4, 0x0

    const/4 v7, 0x0

    const/4 v8, 0x0

    const/4 v9, 0x0

    const/4 v10, 0x0

    const/16 v18, 0x0

    goto :goto_2

    :cond_1
    move-object v4, v1

    check-cast v4, Lcom/android/camera/effect/draw_mode/DrawYuvAttribute;

    iget v7, v4, Lcom/android/camera/effect/draw_mode/DrawYuvAttribute;->mBlockWidth:I

    if-nez v7, :cond_2

    iget v7, v4, Lcom/android/camera/effect/draw_mode/DrawYuvAttribute;->mBlockHeight:I

    if-nez v7, :cond_2

    iget-object v7, v4, Lcom/android/camera/effect/draw_mode/DrawYuvAttribute;->mPictureSize:Landroid/util/Size;

    invoke-virtual {v7}, Landroid/util/Size;->getWidth()I

    move-result v7

    iget-object v4, v4, Lcom/android/camera/effect/draw_mode/DrawYuvAttribute;->mPictureSize:Landroid/util/Size;

    invoke-virtual {v4}, Landroid/util/Size;->getHeight()I

    move-result v4

    goto :goto_0

    :cond_2
    iget v7, v4, Lcom/android/camera/effect/draw_mode/DrawYuvAttribute;->mBlockWidth:I

    iget v4, v4, Lcom/android/camera/effect/draw_mode/DrawYuvAttribute;->mBlockHeight:I

    :goto_0
    move v10, v4

    move v9, v7

    const/4 v4, 0x0

    const/4 v7, 0x0

    const/4 v8, 0x0

    const/16 v18, 0x1

    goto :goto_2

    :cond_3
    move-object v4, v1

    check-cast v4, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;

    iget v7, v4, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mX:I

    iget v8, v4, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mY:I

    iget v9, v4, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mWidth:I

    iget v10, v4, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mHeight:I

    iget v11, v4, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mTexId:I

    iget-boolean v4, v4, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mIsSnapshot:Z

    goto :goto_1

    :cond_4
    move-object v4, v1

    check-cast v4, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget v7, v4, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mX:I

    iget v8, v4, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mY:I

    iget v9, v4, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mWidth:I

    iget v10, v4, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mHeight:I

    iget-object v11, v4, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mBasicTexture:Lcom/android/gallery3d/ui/BasicTexture;

    invoke-virtual {v11}, Lcom/android/gallery3d/ui/BasicTexture;->getId()I

    move-result v11

    iget-boolean v4, v4, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mIsSnapshot:Z

    :goto_1
    move/from16 v18, v4

    move v4, v11

    :goto_2
    if-eqz v9, :cond_d

    if-nez v10, :cond_5

    goto/16 :goto_8

    :cond_5
    iget v15, v0, Lcom/android/camera/effect/renders/PipeRender;->mBufferWidth:I

    iget v14, v0, Lcom/android/camera/effect/renders/PipeRender;->mBufferHeight:I

    const/4 v11, 0x0

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/effect/renders/RenderGroup;->getRenders()Ljava/util/ArrayList;

    move-result-object v13

    if-eqz v13, :cond_c

    invoke-interface {v13}, Ljava/util/List;->size()I

    move-result v12

    move-object v3, v11

    const/4 v11, 0x0

    :goto_3
    if-ge v11, v12, :cond_c

    invoke-interface {v13, v11}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v16

    move-object/from16 v6, v16

    check-cast v6, Lcom/android/camera/effect/renders/Render;

    add-int/lit8 v5, v12, -0x1

    if-ge v11, v5, :cond_6

    const/4 v5, 0x1

    goto :goto_4

    :cond_6
    const/4 v5, 0x0

    :goto_4
    move/from16 v16, v12

    if-eqz v5, :cond_7

    iget-object v12, v0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBuffers:[I

    aget v12, v12, v11

    invoke-virtual {v0, v12, v15, v14}, Lcom/android/camera/effect/renders/RenderGroup;->beginBindFrameBuffer(III)V

    :cond_7
    if-nez v11, :cond_9

    const/16 v12, 0xb

    if-eq v12, v2, :cond_8

    if-eqz v5, :cond_8

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;

    const/16 v17, 0x0

    const/16 v19, 0x0

    move/from16 v20, v11

    move-object v11, v3

    move/from16 v22, v12

    move/from16 v21, v16

    move v12, v4

    move-object/from16 v23, v13

    move/from16 v13, v17

    move/from16 v24, v14

    move/from16 v14, v19

    move/from16 v19, v15

    move/from16 v16, v24

    move/from16 v17, v18

    invoke-direct/range {v11 .. v17}, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;-><init>(IIIIIZ)V

    invoke-virtual {v6, v3}, Lcom/android/camera/effect/renders/Render;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z

    :goto_5
    move/from16 v14, v24

    goto :goto_6

    :cond_8
    move/from16 v20, v11

    move/from16 v22, v12

    move-object/from16 v23, v13

    move/from16 v24, v14

    move/from16 v19, v15

    move/from16 v21, v16

    invoke-virtual {v6, v1}, Lcom/android/camera/effect/renders/Render;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z

    move/from16 v15, v19

    goto :goto_5

    :cond_9
    move/from16 v20, v11

    move-object/from16 v23, v13

    move/from16 v24, v14

    move/from16 v19, v15

    move/from16 v21, v16

    const/16 v22, 0xb

    iget-object v11, v0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBuffers:[I

    add-int/lit8 v12, v20, -0x1

    aget v11, v11, v12

    invoke-virtual {v6, v11, v15, v14}, Lcom/android/camera/effect/renders/Render;->setPreviousFrameBufferInfo(III)V

    if-nez v5, :cond_a

    iput v7, v3, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mX:I

    iput v8, v3, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mY:I

    iput v9, v3, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mWidth:I

    iput v10, v3, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mHeight:I

    :cond_a
    invoke-virtual {v6, v3}, Lcom/android/camera/effect/renders/Render;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z

    :goto_6
    if-eqz v5, :cond_b

    new-instance v3, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;

    iget-object v5, v0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBufferTextures:[I

    aget v12, v5, v20

    const/4 v13, 0x0

    const/4 v5, 0x0

    move-object v11, v3

    move v6, v14

    move v14, v5

    move v5, v15

    move/from16 v16, v6

    move/from16 v17, v18

    invoke-direct/range {v11 .. v17}, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;-><init>(IIIIIZ)V

    invoke-virtual/range {p0 .. p0}, Lcom/android/camera/effect/renders/RenderGroup;->endBindFrameBuffer()V

    goto :goto_7

    :cond_b
    move v6, v14

    move v5, v15

    :goto_7
    add-int/lit8 v11, v20, 0x1

    move v15, v5

    move v14, v6

    move/from16 v12, v21

    move/from16 v5, v22

    move-object/from16 v13, v23

    goto/16 :goto_3

    :cond_c
    const/4 v0, 0x1

    return v0

    :cond_d
    :goto_8
    const/4 v0, 0x1

    sget-object v1, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    sget-object v2, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    aput-object v4, v3, v0

    const-string v0, "invalid size: %dx%d"

    invoke-static {v2, v0, v3}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return v5

    :cond_e
    :goto_9
    const/4 v5, 0x0

    sget-object v0, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    const-string v1, "framebuffer hasn\'t been initialized"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return v5
.end method

.method public drawOnExtraFrameBufferOnce(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z
    .locals 5

    invoke-virtual {p1}, Lcom/android/camera/effect/draw_mode/DrawAttribute;->getTarget()I

    move-result v0

    const/4 v1, 0x5

    const/4 v2, 0x0

    if-eq v0, v1, :cond_1

    const/4 v1, 0x6

    if-eq v0, v1, :cond_0

    sget-object v1, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "unsupported target "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lcom/android/camera/log/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    move v0, v2

    move v1, v0

    goto :goto_0

    :cond_0
    move-object v0, p1

    check-cast v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mWidth:I

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawIntTexAttribute;->mHeight:I

    goto :goto_0

    :cond_1
    move-object v0, p1

    check-cast v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;

    iget v1, v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mWidth:I

    iget v0, v0, Lcom/android/camera/effect/draw_mode/DrawBasicTexAttribute;->mHeight:I

    :goto_0
    const/4 v3, 0x1

    if-eqz v1, :cond_5

    if-nez v0, :cond_2

    goto :goto_2

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/effect/renders/RenderGroup;->getRenders()Ljava/util/ArrayList;

    move-result-object p0

    if-eqz p0, :cond_4

    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result v0

    if-ne v0, v3, :cond_3

    invoke-interface {p0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lcom/android/camera/effect/renders/Render;

    invoke-virtual {p0, p1}, Lcom/android/camera/effect/renders/Render;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)Z

    goto :goto_1

    :cond_3
    sget-object p0, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    sget-object p1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    new-array v1, v3, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    aput-object v0, v1, v2

    const-string v0, "renders more than 1: %d"

    invoke-static {p1, v0, v1}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_4
    :goto_1
    return v3

    :cond_5
    :goto_2
    sget-object p0, Lcom/android/camera/effect/renders/PipeRender;->TAG:Ljava/lang/String;

    sget-object p1, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const/4 v4, 0x2

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    aput-object v1, v4, v2

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    aput-object v0, v4, v3

    const-string v0, "invalid size: %dx%d"

    invoke-static {p1, v0, v4}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return v2
.end method

.method public reInitFrameBuffers(II)V
    .locals 4

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "x"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;

    invoke-virtual {p0}, Lcom/android/camera/effect/renders/RenderGroup;->getRenderSize()I

    move-result v2

    add-int/lit8 v2, v2, -0x1

    const/4 v3, 0x2

    invoke-static {v3, v2}, Ljava/lang/Math;->max(II)I

    move-result v2

    if-nez v1, :cond_0

    new-instance v1, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;

    invoke-direct {v1, p0, v2, p1, p2}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;-><init>(Lcom/android/camera/effect/renders/PipeRender;III)V

    iget-object p1, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferMap:Ljava/util/HashMap;

    invoke-virtual {p1, v0, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_0
    invoke-static {v1, v2}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$000(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;I)V

    :goto_0
    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$100(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBuffers:[I

    invoke-static {v1}, Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;->access$200(Lcom/android/camera/effect/renders/PipeRender$FrameBufferPair;)[I

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/effect/renders/PipeRender;->mFrameBufferTextures:[I

    return-void
.end method

.method public setFrameBufferSize(II)V
    .locals 1

    iget v0, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferWidth:I

    if-ne v0, p1, :cond_0

    iget v0, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferHeight:I

    if-eq v0, p2, :cond_1

    :cond_0
    iput p1, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferWidth:I

    iput p2, p0, Lcom/android/camera/effect/renders/PipeRender;->mBufferHeight:I

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/effect/renders/PipeRender;->reInitFrameBuffers(II)V

    :cond_1
    return-void
.end method
