.class public Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;
.super Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
.source "EditLevelListAdapter2.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "ViewHolder"
.end annotation


# instance fields
.field mColorRecycleView:Landroidx/recyclerview/widget/RecyclerView;

.field mThumbnailGV:Landroidx/recyclerview/widget/RecyclerView;

.field mTvSubTitle:Landroid/widget/TextView;

.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;


# direct methods
.method public constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;Landroid/view/View;)V
    .locals 1

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;->this$0:Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2;

    invoke-direct {p0, p2}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;-><init>(Landroid/view/View;)V

    const p1, 0x7f0902ea

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/TextView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;->mTvSubTitle:Landroid/widget/TextView;

    const p1, 0x7f0900af

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroidx/recyclerview/widget/RecyclerView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;->mColorRecycleView:Landroidx/recyclerview/widget/RecyclerView;

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;->mColorRecycleView:Landroidx/recyclerview/widget/RecyclerView;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setFocusable(Z)V

    const p1, 0x7f0902ab

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroidx/recyclerview/widget/RecyclerView;

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;->mThumbnailGV:Landroidx/recyclerview/widget/RecyclerView;

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/EditLevelListAdapter2$ViewHolder;->mThumbnailGV:Landroidx/recyclerview/widget/RecyclerView;

    invoke-virtual {p0, v0}, Landroid/view/ViewGroup;->setFocusable(Z)V

    return-void
.end method
