.class Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
.source "MimojiTimbreAdapter.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "TimbreViewViewHolder"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;",
        ">;"
    }
.end annotation


# instance fields
.field private imageView:Landroid/widget/ImageView;

.field private mProgressTimbreView:Landroid/widget/ProgressBar;

.field private mSelectItemView:Landroid/widget/ImageView;

.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter;


# direct methods
.method public constructor <init>(Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter;Landroid/view/View;)V
    .locals 0
    .param p1    # Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter;

    invoke-direct {p0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;-><init>(Landroid/view/View;)V

    sget p1, Lcom/android/camera/features/mimoji2/widget/baseview/BaseItemAnimator;->DEFAULT_LIST_DURATION:I

    invoke-static {p2, p1}, Lcom/android/camera/animation/FolmeUtils;->handleListItemTouch(Landroid/view/View;I)V

    const p1, 0x7f0901bf

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->imageView:Landroid/widget/ImageView;

    const p1, 0x7f0901c0

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    const p1, 0x7f0901c6

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ProgressBar;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->mProgressTimbreView:Landroid/widget/ProgressBar;

    return-void
.end method


# virtual methods
.method public getRotateViews()[Landroid/view/View;
    .locals 2

    const/4 v0, 0x1

    new-array v0, v0, [Landroid/view/View;

    iget-object p0, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    const/4 v1, 0x0

    aput-object p0, v0, v1

    return-object v0
.end method

.method public setData(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;I)V
    .locals 2

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->getResourceId()I

    move-result p2

    if-lez p2, :cond_0

    iget-object p2, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    const v0, 0x7f060162

    invoke-virtual {p2, v0}, Landroid/view/View;->setBackgroundResource(I)V

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->imageView:Landroid/widget/ImageView;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->getResourceId()I

    move-result v0

    invoke-virtual {p2, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    goto :goto_0

    :cond_0
    iget-object p2, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f080092

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p2, v0}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->imageView:Landroid/widget/ImageView;

    const v0, 0x7f080276

    invoke-virtual {p2, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    :goto_0
    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->imageView:Landroid/widget/ImageView;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->isSelected()Z

    move-result v0

    invoke-static {p2, v0}, Lcom/android/camera/Util;->correctionSelectView(Landroid/view/View;Z)V

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->isSelected()Z

    move-result p2

    const/16 v0, 0x8

    if-eqz p2, :cond_2

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->mProgressTimbreView:Landroid/widget/ProgressBar;

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;->isCompsing()Z

    move-result p1

    const/4 v1, 0x0

    if-eqz p1, :cond_1

    move v0, v1

    :cond_1
    invoke-virtual {p2, v0}, Landroid/widget/ProgressBar;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    iget-object p2, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-virtual {p2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p2

    const v0, 0x7f080084

    invoke-virtual {p2, v0}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    invoke-virtual {p0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    goto :goto_1

    :cond_2
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->mSelectItemView:Landroid/widget/ImageView;

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->mProgressTimbreView:Landroid/widget/ProgressBar;

    invoke-virtual {p0, v0}, Landroid/widget/ProgressBar;->setVisibility(I)V

    :goto_1
    return-void
.end method

.method public bridge synthetic setData(Ljava/lang/Object;I)V
    .locals 0

    check-cast p1, Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiTimbreAdapter$TimbreViewViewHolder;->setData(Lcom/android/camera/features/mimoji2/bean/MimojiTimbreInfo;I)V

    return-void
.end method
