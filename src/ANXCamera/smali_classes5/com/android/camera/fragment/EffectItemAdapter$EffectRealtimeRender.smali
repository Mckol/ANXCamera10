.class public Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;
.super Ljava/lang/Object;
.source "EffectItemAdapter.java"

# interfaces
.implements Landroid/opengl/GLSurfaceView$Renderer;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/fragment/EffectItemAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4
    name = "EffectRealtimeRender"
.end annotation


# instance fields
.field private filterData:Lcom/android/camera/data/data/ComponentDataItem;

.field private height:I

.field private mContext:Landroid/content/Context;

.field private mExtTexture:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

.field mTransform:[F

.field final synthetic this$0:Lcom/android/camera/fragment/EffectItemAdapter;

.field private viewPosition:I

.field private width:I


# direct methods
.method public constructor <init>(Lcom/android/camera/fragment/EffectItemAdapter;Landroid/content/Context;)V
    .locals 1

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance p1, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    const/4 v0, 0x1

    invoke-direct {p1, v0}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;-><init>(Z)V

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mExtTexture:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    const/16 p1, 0x10

    new-array p1, p1, [F

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mTransform:[F

    iput-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mContext:Landroid/content/Context;

    return-void
.end method


# virtual methods
.method public bindEffectIndex(ILcom/android/camera/data/data/ComponentDataItem;)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->viewPosition:I

    iput-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->filterData:Lcom/android/camera/data/data/ComponentDataItem;

    return-void
.end method

.method public onDrawFrame(Ljavax/microedition/khronos/opengles/GL10;)V
    .locals 13

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mContext:Landroid/content/Context;

    check-cast p1, Lcom/android/camera/ActivityBase;

    invoke-virtual {p1}, Lcom/android/camera/ActivityBase;->getCameraScreenNail()Lcom/android/camera/CameraScreenNail;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mContext:Landroid/content/Context;

    check-cast v0, Lcom/android/camera/ActivityBase;

    invoke-virtual {v0}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->getGLCanvas()Lcom/android/gallery3d/ui/GLCanvasImpl;

    move-result-object v0

    if-eqz p1, :cond_2

    if-eqz v0, :cond_2

    invoke-virtual {p1}, Lcom/android/camera/SurfaceTextureScreenNail;->getSurfaceTexture()Landroid/graphics/SurfaceTexture;

    move-result-object v1

    if-eqz v1, :cond_2

    monitor-enter v0

    :try_start_0
    invoke-interface {v0}, Lcom/android/gallery3d/ui/GLCanvas;->clearBuffer()V

    invoke-interface {v0}, Lcom/android/gallery3d/ui/GLCanvas;->getWidth()I

    move-result v1

    invoke-interface {v0}, Lcom/android/gallery3d/ui/GLCanvas;->getHeight()I

    move-result v2

    invoke-interface {v0}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object v3

    invoke-virtual {v3}, Lcom/android/camera/effect/GLCanvasState;->pushState()V

    iget-object v3, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {v3}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object v3

    if-eqz v3, :cond_0

    iget-object v3, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {v3}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object v3

    invoke-interface {v3}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getHolderWidth()I

    move-result v3

    iget-object v4, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {v4}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object v4

    invoke-interface {v4}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getHolderHeight()I

    move-result v4

    invoke-interface {v0, v3, v4}, Lcom/android/gallery3d/ui/GLCanvas;->setSize(II)V

    :cond_0
    invoke-virtual {p1}, Lcom/android/camera/SurfaceTextureScreenNail;->getSurfaceTexture()Landroid/graphics/SurfaceTexture;

    move-result-object v3

    iget-object v4, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mTransform:[F

    invoke-virtual {v3, v4}, Landroid/graphics/SurfaceTexture;->getTransformMatrix([F)V

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v3

    invoke-static {}, Lcom/android/camera/effect/EffectController;->getInstance()Lcom/android/camera/effect/EffectController;

    move-result-object v4

    const/4 v5, 0x0

    invoke-virtual {v4, v5}, Lcom/android/camera/effect/EffectController;->getEffectForPreview(Z)I

    move-result v4

    iget-object v5, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->filterData:Lcom/android/camera/data/data/ComponentDataItem;

    iget-object v5, v5, Lcom/android/camera/data/data/ComponentDataItem;->mValue:Ljava/lang/String;

    invoke-static {v5}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    invoke-virtual {v3, v5}, Lcom/android/camera/effect/EffectController;->notifyRealtimePreviewFilterChanged(I)V

    iget-object v5, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {v5}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object v5

    if-eqz v5, :cond_1

    iget-object v6, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mExtTexture:Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    invoke-virtual {p1}, Lcom/android/camera/SurfaceTextureScreenNail;->getExtTexture()Lcom/android/gallery3d/ui/ExtTexture;

    move-result-object v7

    iget-object v8, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->mTransform:[F

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p1}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getTextureOffsetX()I

    move-result v9

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p1}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getTextureOffsetY()I

    move-result v10

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p1}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getTextureWidth()I

    move-result v11

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p0}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p0

    invoke-interface {p0}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getTextureHeight()I

    move-result v12

    invoke-virtual/range {v6 .. v12}, Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;->init(Lcom/android/gallery3d/ui/ExtTexture;[FIIII)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    invoke-interface {v0, p0}, Lcom/android/gallery3d/ui/GLCanvas;->draw(Lcom/android/camera/effect/draw_mode/DrawAttribute;)V

    :cond_1
    invoke-virtual {v3, v4}, Lcom/android/camera/effect/EffectController;->notifyRealtimePreviewFilterChanged(I)V

    invoke-interface {v0, v1, v2}, Lcom/android/gallery3d/ui/GLCanvas;->setSize(II)V

    invoke-interface {v0}, Lcom/android/gallery3d/ui/GLCanvas;->getState()Lcom/android/camera/effect/GLCanvasState;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/effect/GLCanvasState;->popState()V

    invoke-interface {v0}, Lcom/android/gallery3d/ui/GLCanvas;->recycledResources()V

    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0

    :cond_2
    :goto_0
    return-void
.end method

.method public onSurfaceChanged(Ljavax/microedition/khronos/opengles/GL10;II)V
    .locals 0

    iput p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->width:I

    iput p3, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->height:I

    return-void
.end method

.method public onSurfaceCreated(Ljavax/microedition/khronos/opengles/GL10;Ljavax/microedition/khronos/egl/EGLConfig;)V
    .locals 0

    return-void
.end method
