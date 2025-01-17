.class public Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;
.super Landroidx/recyclerview/widget/RecyclerView$Adapter;
.source "ColorListAdapter2.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroidx/recyclerview/widget/RecyclerView$Adapter<",
        "Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;",
        ">;"
    }
.end annotation


# static fields
.field public static final TAG:Ljava/lang/String; = "ColorListAdapter2"


# instance fields
.field private mAvatarConfigItemClick2:Lcom/android/camera/features/mimoji2/fragment/edit/AvatarConfigItemClick2;

.field private mContext:Landroid/content/Context;

.field private mDatas:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;",
            ">;"
        }
    .end annotation
.end field

.field public mLastPosion:I

.field private mLinearLayoutManagerWrapper:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

.field private mRootView:Landroidx/recyclerview/widget/RecyclerView;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Lcom/android/camera/features/mimoji2/fragment/edit/AvatarConfigItemClick2;Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;)V
    .locals 1

    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLastPosion:I

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mContext:Landroid/content/Context;

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mDatas:Ljava/util/List;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mAvatarConfigItemClick2:Lcom/android/camera/features/mimoji2/fragment/edit/AvatarConfigItemClick2;

    iput-object p3, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLinearLayoutManagerWrapper:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Landroidx/recyclerview/widget/RecyclerView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mRootView:Landroidx/recyclerview/widget/RecyclerView;

    return-object p0
.end method

.method static synthetic access$100(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLinearLayoutManagerWrapper:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    return-object p0
.end method

.method static synthetic access$200(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;)Lcom/android/camera/features/mimoji2/fragment/edit/AvatarConfigItemClick2;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mAvatarConfigItemClick2:Lcom/android/camera/features/mimoji2/fragment/edit/AvatarConfigItemClick2;

    return-object p0
.end method


# virtual methods
.method public getItemCount()I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mDatas:Ljava/util/List;

    if-nez p0, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    :cond_0
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result p0

    :goto_0
    return p0
.end method

.method public getSelectItem(I)F
    .locals 0

    invoke-static {}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInstance()Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;

    move-result-object p0

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->getInnerConfigSelectIndex(I)F

    move-result p0

    return p0
.end method

.method public getmLinearLayoutManagerWrapper()Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLinearLayoutManagerWrapper:Lcom/android/camera/fragment/beauty/LinearLayoutManagerWrapper;

    return-object p0
.end method

.method public onAttachedToRecyclerView(Landroidx/recyclerview/widget/RecyclerView;)V
    .locals 0
    .param p1    # Landroidx/recyclerview/widget/RecyclerView;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    invoke-super {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->onAttachedToRecyclerView(Landroidx/recyclerview/widget/RecyclerView;)V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mRootView:Landroidx/recyclerview/widget/RecyclerView;

    return-void
.end method

.method public bridge synthetic onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V
    .locals 0
    .param p1    # Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    check-cast p1, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->onBindViewHolder(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;I)V

    return-void
.end method

.method public onBindViewHolder(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;I)V
    .locals 7
    .param p1    # Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mDatas:Ljava/util/List;

    invoke-interface {v0, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    move-object v5, v0

    check-cast v5, Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;

    iget-object v6, p1, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;->ivColor:Lcom/android/camera/ui/CircleImageView;

    iget v0, v5, Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;->configType:I

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->getSelectItem(I)F

    move-result v0

    new-instance v4, Landroid/animation/ArgbEvaluator;

    invoke-direct {v4}, Landroid/animation/ArgbEvaluator;-><init>()V

    iget v1, v5, Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;->continuousValue:F

    iget v2, v5, Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;->startColorValue:I

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    iget v3, v5, Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;->endColorValue:I

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v4, v1, v2, v3}, Landroid/animation/ArgbEvaluator;->evaluate(FLjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    iget v2, v5, Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;->configID:I

    int-to-float v2, v2

    cmpl-float v0, v0, v2

    if-nez v0, :cond_0

    const/4 v0, 0x1

    invoke-virtual {v6, v0, v1}, Lcom/android/camera/ui/CircleImageView;->updateView(ZI)V

    iput p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mLastPosion:I

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    invoke-virtual {v6, v0, v1}, Lcom/android/camera/ui/CircleImageView;->updateView(ZI)V

    :goto_0
    iget-object p1, p1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    new-instance v0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;

    move-object v1, v0

    move-object v2, p0

    move v3, p2

    invoke-direct/range {v1 .. v6}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$1;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;ILandroid/animation/ArgbEvaluator;Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;Lcom/android/camera/ui/CircleImageView;)V

    invoke-virtual {p1, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    return-void
.end method

.method public bridge synthetic onCreateViewHolder(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
    .locals 0
    .param p1    # Landroid/view/ViewGroup;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroidx/annotation/NonNull;
    .end annotation

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;

    move-result-object p0

    return-object p0
.end method

.method public onCreateViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;
    .locals 2
    .param p1    # Landroid/view/ViewGroup;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param
    .annotation build Landroidx/annotation/NonNull;
    .end annotation

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mContext:Landroid/content/Context;

    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p2

    const v0, 0x7f0c00a0

    const/4 v1, 0x0

    invoke-virtual {p2, v0, p1, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    new-instance p2, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;

    invoke-direct {p2, p0, p1}, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2$ViewHolder;-><init>(Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;Landroid/view/View;)V

    return-object p2
.end method

.method public onDetachedFromRecyclerView(Landroidx/recyclerview/widget/RecyclerView;)V
    .locals 0
    .param p1    # Landroidx/recyclerview/widget/RecyclerView;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    invoke-super {p0, p1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->onDetachedFromRecyclerView(Landroidx/recyclerview/widget/RecyclerView;)V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mRootView:Landroidx/recyclerview/widget/RecyclerView;

    return-void
.end method

.method public setData(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/arcsoft/avatar2/AvatarConfig$ASAvatarConfigInfo;",
            ">;)V"
        }
    .end annotation

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/ColorListAdapter2;->mDatas:Ljava/util/List;

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    return-void
.end method
