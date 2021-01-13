.class Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;
.super Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;
.source "EffectItemAdapter.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/fragment/EffectItemAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "EffectRealtimeItemHolder"
.end annotation


# instance fields
.field private effectRenderer:Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;

.field private mTextureView:Lcom/android/camera/ui/GLTextureView;

.field final synthetic this$0:Lcom/android/camera/fragment/EffectItemAdapter;


# direct methods
.method public constructor <init>(Lcom/android/camera/fragment/EffectItemAdapter;Landroid/view/View;)V
    .locals 1

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-direct {p0, p2}, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;-><init>(Landroid/view/View;)V

    const v0, 0x7f0900d9

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/android/camera/ui/GLTextureView;

    iput-object v0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->mTextureView:Lcom/android/camera/ui/GLTextureView;

    const v0, 0x7f0900da

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p2

    check-cast p2, Landroid/widget/ImageView;

    iput-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    new-instance p2, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;

    iget-object v0, p1, Lcom/android/camera/fragment/EffectItemAdapter;->mContext:Landroid/content/Context;

    invoke-direct {p2, p1, v0}, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;-><init>(Lcom/android/camera/fragment/EffectItemAdapter;Landroid/content/Context;)V

    iput-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->effectRenderer:Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->mTextureView:Lcom/android/camera/ui/GLTextureView;

    const/4 p2, 0x2

    invoke-virtual {p1, p2}, Lcom/android/camera/ui/GLTextureView;->setEGLContextClientVersion(I)V

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->mTextureView:Lcom/android/camera/ui/GLTextureView;

    new-instance p2, Lcom/android/camera/fragment/d;

    invoke-direct {p2, p0}, Lcom/android/camera/fragment/d;-><init>(Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;)V

    invoke-virtual {p1, p2}, Lcom/android/camera/ui/GLTextureView;->setEGLShareContextGetter(Lcom/android/camera/ui/GLTextureView$EGLShareContextGetter;)V

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->mTextureView:Lcom/android/camera/ui/GLTextureView;

    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->effectRenderer:Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;

    invoke-virtual {p1, p2}, Lcom/android/camera/ui/GLTextureView;->setRenderer(Landroid/opengl/GLSurfaceView$Renderer;)V

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->mTextureView:Lcom/android/camera/ui/GLTextureView;

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/GLTextureView;->setRenderMode(I)V

    return-void
.end method


# virtual methods
.method public synthetic ab()Ljavax/microedition/khronos/egl/EGLContext;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mContext:Landroid/content/Context;

    check-cast p0, Lcom/android/camera/ActivityBase;

    invoke-virtual {p0}, Lcom/android/camera/ActivityBase;->getGLView()Lcom/android/camera/ui/V6CameraGLSurfaceView;

    move-result-object p0

    invoke-virtual {p0}, Lcom/android/camera/ui/V6CameraGLSurfaceView;->getEGLContext()Ljavax/microedition/khronos/egl/EGLContext;

    move-result-object p0

    return-object p0
.end method

.method public bindEffectIndex(ILcom/android/camera/data/data/ComponentDataItem;)V
    .locals 8

    invoke-super {p0, p1, p2}, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->bindEffectIndex(ILcom/android/camera/data/data/ComponentDataItem;)V

    iget-object v0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->effectRenderer:Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;

    invoke-virtual {v0, p1, p2}, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;->bindEffectIndex(ILcom/android/camera/data/data/ComponentDataItem;)V

    iget-object v0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {v0}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object v0

    const-wide/16 v1, 0x1f4

    const/4 v3, 0x0

    const/high16 v4, 0x3f800000    # 1.0f

    const/4 v5, 0x0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {v0}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object v0

    invoke-interface {v0}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getCurrentIndex()I

    move-result v0

    if-ne v0, p1, :cond_3

    iget-object p1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/view/View;->setActivated(Z)V

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result p1

    if-nez p1, :cond_0

    invoke-static {}, Lcom/android/camera/Util;->isSetContentDesc()Z

    move-result p1

    if-eqz p1, :cond_1

    :cond_0
    iget-object p1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v6, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    iget-object v6, v6, Lcom/android/camera/fragment/EffectItemAdapter;->mContext:Landroid/content/Context;

    iget p2, p2, Lcom/android/camera/data/data/ComponentDataItem;->mDisplayNameRes:I

    invoke-virtual {v6, p2}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    iget-object p2, p2, Lcom/android/camera/fragment/EffectItemAdapter;->mContext:Landroid/content/Context;

    const v6, 0x7f100067

    invoke-virtual {p2, v6}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/view/View;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    new-instance p2, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder$1;

    invoke-direct {p2, p0}, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder$1;-><init>(Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;)V

    const-wide/16 v6, 0x64

    invoke-virtual {p1, p2, v6, v7}, Landroid/view/View;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p1}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p1}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p1

    invoke-interface {p1}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->isAnimation()Z

    move-result p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-static {p1, v5}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-static {p1}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1, v1, v2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1, v4}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    new-instance p2, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {p2}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {p1, p2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setInterpolator(Landroid/view/animation/Interpolator;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    new-instance p2, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder$2;

    invoke-direct {p2, p0}, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder$2;-><init>(Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;)V

    invoke-virtual {p1, p2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setListener(Landroidx/core/view/ViewPropertyAnimatorListener;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_0

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-virtual {p1, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-virtual {p0, v4}, Landroid/widget/ImageView;->setAlpha(F)V

    goto :goto_0

    :cond_3
    iget-object p2, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p2, v3}, Landroid/view/View;->setActivated(Z)V

    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p2}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p2

    if-eqz p2, :cond_4

    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p2}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p2

    invoke-interface {p2}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->isAnimation()Z

    move-result p2

    if-eqz p2, :cond_4

    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p2}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p2

    if-eqz p2, :cond_4

    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->this$0:Lcom/android/camera/fragment/EffectItemAdapter;

    invoke-static {p2}, Lcom/android/camera/fragment/EffectItemAdapter;->access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    move-result-object p2

    invoke-interface {p2}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getLastIndex()I

    move-result p2

    if-ne p2, p1, :cond_4

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-static {p1, v4}, Landroidx/core/view/ViewCompat;->setAlpha(Landroid/view/View;F)V

    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-static {p1}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1, v1, v2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    invoke-virtual {p1, v5}, Landroidx/core/view/ViewPropertyAnimatorCompat;->alpha(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    new-instance p2, Lmiui/view/animation/CubicEaseOutInterpolator;

    invoke-direct {p2}, Lmiui/view/animation/CubicEaseOutInterpolator;-><init>()V

    invoke-virtual {p1, p2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setInterpolator(Landroid/view/animation/Interpolator;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p1

    new-instance p2, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder$3;

    invoke-direct {p2, p0}, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder$3;-><init>(Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;)V

    invoke-virtual {p1, p2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setListener(Landroidx/core/view/ViewPropertyAnimatorListener;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    goto :goto_0

    :cond_4
    iget-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    const/16 p2, 0x8

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-virtual {p0, v5}, Landroid/widget/ImageView;->setAlpha(F)V

    :goto_0
    return-void
.end method

.method public requestRender()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;->mTextureView:Lcom/android/camera/ui/GLTextureView;

    invoke-virtual {p0}, Lcom/android/camera/ui/GLTextureView;->requestRender()V

    return-void
.end method
