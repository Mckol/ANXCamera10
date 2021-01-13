.class public Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;
.source "MimojiAvatarAdapter.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;",
        ">;"
    }
.end annotation


# instance fields
.field private mLastSelectPosition:I

.field private mResourceBg:I


# direct methods
.method public constructor <init>(Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;",
            ">;)V"
        }
    .end annotation

    invoke-direct {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;-><init>(Ljava/util/List;)V

    const/4 p1, 0x0

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mLastSelectPosition:I

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;)I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mResourceBg:I

    return p0
.end method


# virtual methods
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
            "Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;",
            ">;"
        }
    .end annotation

    invoke-virtual {p1}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p2

    const v0, 0x7f0c0078

    const/4 v1, 0x0

    invoke-virtual {p2, v0, p1, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    new-instance p2, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;

    invoke-direct {p2, p0, p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;-><init>(Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;Landroid/view/View;)V

    return-object p2
.end method

.method public setLastSelectPosition(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mLastSelectPosition:I

    return-void
.end method

.method public setResourceBg(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mResourceBg:I

    return-void
.end method

.method public setSelectState(I)V
    .locals 2

    iget v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mLastSelectPosition:I

    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    if-ne v0, p1, :cond_0

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->isAvailablePosion(I)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0, p1, v1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemChanged(ILjava/lang/Object;)V

    return-void

    :cond_0
    iget v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mLastSelectPosition:I

    invoke-virtual {p0, v0}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->isAvailablePosion(I)Z

    move-result v0

    if-eqz v0, :cond_1

    iget v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mLastSelectPosition:I

    invoke-virtual {p0, v0, v1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemChanged(ILjava/lang/Object;)V

    :cond_1
    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerAdapter;->isAvailablePosion(I)Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p0, p1, v1}, Landroidx/recyclerview/widget/RecyclerView$Adapter;->notifyItemChanged(ILjava/lang/Object;)V

    iput p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->mLastSelectPosition:I

    :cond_2
    return-void
.end method
