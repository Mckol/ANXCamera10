.class public Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;
.source "MimojiBgAdapter.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter$BgViewViewHolder;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;",
        ">;"
    }
.end annotation


# instance fields
.field private mLastSelectedPosition:I


# direct methods
.method public constructor <init>(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;",
            ">;)V"
        }
    .end annotation

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;-><init>(Ljava/util/List;)V

    const/4 p1, -0x1

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter;->mLastSelectedPosition:I

    return-void
.end method


# virtual methods
.method public clearState()V
    .locals 4

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_1

    const/4 v0, 0x0

    move v1, v0

    :goto_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getItemCount()I

    move-result v2

    if-ge v1, v2, :cond_1

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v2

    invoke-interface {v2, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v2}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->isSelected()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-virtual {v2, v0}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->setSelected(Z)V

    invoke-virtual {p0, v1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemChanged(I)V

    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method

.method protected onCreateBaseRecyclerViewHolder(Landroid/view/ViewGroup;I)Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
    .locals 2
    .param p1    # Landroid/view/ViewGroup;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/view/ViewGroup;",
            "I)",
            "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder<",
            "Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;",
            ">;"
        }
    .end annotation

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p2

    const v0, 0x7f0c0079

    const/4 v1, 0x0

    invoke-virtual {p2, v0, p1, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    new-instance p2, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter$BgViewViewHolder;

    invoke-direct {p2, p0, p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter$BgViewViewHolder;-><init>(Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter;Landroid/view/View;)V

    return-object p2
.end method

.method public setSelectState(I)Z
    .locals 6

    iget v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter;->mLastSelectedPosition:I

    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v2

    if-ne v0, p1, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->isAvailablePosion(I)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0, p1, v2}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemChanged(ILjava/lang/Object;)V

    return v1

    :cond_0
    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter;->mLastSelectedPosition:I

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->isAvailablePosion(I)Z

    move-result v0

    const/4 v3, 0x1

    if-eqz v0, :cond_3

    move v0, v1

    :goto_0
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getItemCount()I

    move-result v4

    if-ge v0, v4, :cond_2

    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v4

    invoke-interface {v4, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v4}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->isSelected()Z

    move-result v5

    if-eqz v5, :cond_1

    invoke-virtual {v4, v1}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->setSelected(Z)V

    invoke-virtual {p0, v0}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemChanged(I)V

    :cond_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_2
    invoke-virtual {p0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->getDataList()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;

    invoke-virtual {v0, v3}, Lcom/android/camera/features/mimoji2/bean/MimojiBgInfo;->setSelected(Z)V

    invoke-virtual {p0, p1, v2}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemChanged(ILjava/lang/Object;)V

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiBgAdapter;->mLastSelectedPosition:I

    :cond_3
    return v3
.end method
