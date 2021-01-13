.class public Lcom/android/camera/fragment/EffectItemAdapter;
.super Landroidx/recyclerview/widget/RecyclerView$Adapter;
.source "EffectItemAdapter.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;,
        Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeRender;,
        Lcom/android/camera/fragment/EffectItemAdapter$EffectItemPadding;,
        Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;,
        Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;,
        Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;,
        Lcom/android/camera/fragment/EffectItemAdapter$EffectStillItemHolder;
    }
.end annotation


# instance fields
.field protected mComponentData:Lcom/android/camera/data/data/ComponentData;

.field protected final mContext:Landroid/content/Context;

.field private mDegree:I

.field private mDisplayRotation:I

.field private mEffectItemListener:Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

.field protected mLayoutInflater:Landroid/view/LayoutInflater;

.field private mOnClickListener:Landroid/view/View$OnClickListener;

.field private mSupportRealtimeEffect:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/android/camera/data/data/ComponentData;)V
    .locals 0

    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mContext:Landroid/content/Context;

    iput-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mComponentData:Lcom/android/camera/data/data/ComponentData;

    invoke-static {p1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mLayoutInflater:Landroid/view/LayoutInflater;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Lcom/android/camera/data/data/ComponentData;Z)V
    .locals 0

    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mContext:Landroid/content/Context;

    iput-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mComponentData:Lcom/android/camera/data/data/ComponentData;

    invoke-static {p1}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p1

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mLayoutInflater:Landroid/view/LayoutInflater;

    iput-boolean p3, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mSupportRealtimeEffect:Z

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/fragment/EffectItemAdapter;)Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mEffectItemListener:Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    return-object p0
.end method


# virtual methods
.method public getItemCount()I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mComponentData:Lcom/android/camera/data/data/ComponentData;

    invoke-virtual {p0}, Lcom/android/camera/data/data/ComponentData;->getItems()Ljava/util/List;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result p0

    return p0
.end method

.method public onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mComponentData:Lcom/android/camera/data/data/ComponentData;

    invoke-virtual {v0}, Lcom/android/camera/data/data/ComponentData;->getItems()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/data/data/ComponentDataItem;

    move-object v1, p1

    check-cast v1, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;

    iget-object v2, v1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    invoke-virtual {v1, p2, v0}, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->bindEffectIndex(ILcom/android/camera/data/data/ComponentDataItem;)V

    iget-boolean p2, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mSupportRealtimeEffect:Z

    if-eqz p2, :cond_0

    iget-object p2, p1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    const v0, 0x7f0900db

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p2

    iget v0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mDegree:I

    int-to-float v0, v0

    invoke-virtual {p2, v0}, Landroid/view/View;->setRotation(F)V

    iget-object p1, p1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    const p2, 0x7f0900d9

    invoke-virtual {p1, p2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    iget p0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mDisplayRotation:I

    int-to-float p0, p0

    invoke-virtual {p1, p0}, Landroid/view/View;->setRotation(F)V

    goto :goto_0

    :cond_0
    iget-object p1, p1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    iget p0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mDegree:I

    int-to-float p0, p0

    invoke-virtual {p1, p0}, Landroid/view/View;->setRotation(F)V

    :goto_0
    return-void
.end method

.method public onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;ILjava/util/List;)V
    .locals 1
    .param p1    # Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param
    .param p3    # Ljava/util/List;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    invoke-interface {p3}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/fragment/EffectItemAdapter;->onBindViewHolder(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V

    goto :goto_0

    :cond_0
    instance-of p2, p1, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;

    if-eqz p2, :cond_2

    check-cast p1, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;

    const/4 p2, 0x0

    invoke-interface {p3, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    instance-of v0, v0, Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;

    if-eqz v0, :cond_2

    invoke-interface {p3, p2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;

    iget-boolean v0, p3, Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;->select:Z

    if-eqz v0, :cond_1

    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mEffectItemListener:Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    if-eqz p0, :cond_1

    invoke-interface {p0}, Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;->getCurrentIndex()I

    move-result p0

    iget v0, p3, Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;->index:I

    if-eq p0, v0, :cond_1

    iput-boolean p2, p3, Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;->select:Z

    :cond_1
    iget-object p0, p1, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    iget-boolean p2, p3, Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;->select:Z

    invoke-virtual {p0, p2}, Landroid/view/View;->setActivated(Z)V

    iget-object p0, p1, Lcom/android/camera/fragment/EffectItemAdapter$EffectItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    if-eqz p0, :cond_2

    iget-boolean p1, p3, Lcom/android/camera/fragment/EffectItemAdapter$ItemChangeData;->select:Z

    const/4 p2, 0x1

    invoke-static {p0, p1, p2}, Lcom/android/camera/Util;->updateSelectIndicator(Landroid/view/View;ZZ)V

    :cond_2
    :goto_0
    return-void
.end method

.method public onCreateViewHolder(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
    .locals 2

    iget-boolean p2, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mSupportRealtimeEffect:Z

    const/4 v0, 0x0

    if-eqz p2, :cond_0

    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mLayoutInflater:Landroid/view/LayoutInflater;

    const v1, 0x7f0c002d

    invoke-virtual {p2, v1, p1, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    new-instance p2, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;

    invoke-direct {p2, p0, p1}, Lcom/android/camera/fragment/EffectItemAdapter$EffectRealtimeItemHolder;-><init>(Lcom/android/camera/fragment/EffectItemAdapter;Landroid/view/View;)V

    goto :goto_0

    :cond_0
    iget-object p2, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mLayoutInflater:Landroid/view/LayoutInflater;

    const v1, 0x7f0c002e

    invoke-virtual {p2, v1, p1, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    new-instance p2, Lcom/android/camera/fragment/EffectItemAdapter$EffectStillItemHolder;

    invoke-direct {p2, p0, p1}, Lcom/android/camera/fragment/EffectItemAdapter$EffectStillItemHolder;-><init>(Lcom/android/camera/fragment/EffectItemAdapter;Landroid/view/View;)V

    :goto_0
    iget-object p0, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    invoke-virtual {p1, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-static {p1}, Lcom/android/camera/animation/FolmeUtils;->handleListItemTouch(Landroid/view/View;)V

    return-object p2
.end method

.method public setDisplayRotation(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mDisplayRotation:I

    return-void
.end method

.method public setOnClickListener(Landroid/view/View$OnClickListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mOnClickListener:Landroid/view/View$OnClickListener;

    return-void
.end method

.method public setOnEffectItemListener(Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mEffectItemListener:Lcom/android/camera/fragment/EffectItemAdapter$IEffectItemListener;

    return-void
.end method

.method public setRotation(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mDegree:I

    return-void
.end method

.method public updateData(Lcom/android/camera/data/data/ComponentData;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/EffectItemAdapter;->mComponentData:Lcom/android/camera/data/data/ComponentData;

    invoke-virtual {p0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyDataSetChanged()V

    return-void
.end method
