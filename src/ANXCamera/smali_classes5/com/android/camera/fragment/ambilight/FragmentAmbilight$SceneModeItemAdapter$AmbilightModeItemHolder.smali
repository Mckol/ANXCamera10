.class Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;
.super Landroidx/recyclerview/widget/RecyclerView$ViewHolder;
.source "FragmentAmbilight.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "AmbilightModeItemHolder"
.end annotation


# instance fields
.field private mImageView:Landroid/widget/ImageView;

.field private mSelectedIndicator:Landroid/widget/ImageView;

.field protected mTextView:Lcom/android/camera/ui/ScrollTextview;

.field final synthetic this$1:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;


# direct methods
.method public constructor <init>(Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;Landroid/view/View;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->this$1:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;

    invoke-direct {p0, p2}, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;-><init>(Landroid/view/View;)V

    const p1, 0x7f0900db

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ui/ScrollTextview;

    iput-object p1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mTextView:Lcom/android/camera/ui/ScrollTextview;

    const p1, 0x7f0900d8

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mImageView:Landroid/widget/ImageView;

    const p1, 0x7f0900da

    invoke-virtual {p2, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    return-void
.end method

.method static synthetic access$000(Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;)Landroid/widget/ImageView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    return-object p0
.end method

.method static synthetic access$100(Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;)Landroid/widget/ImageView;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mImageView:Landroid/widget/ImageView;

    return-object p0
.end method


# virtual methods
.method public bindEffectIndex(ILcom/android/camera/data/data/ComponentDataItem;)V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mTextView:Lcom/android/camera/ui/ScrollTextview;

    iget v1, p2, Lcom/android/camera/data/data/ComponentDataItem;->mDisplayNameRes:I

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mImageView:Landroid/widget/ImageView;

    iget v1, p2, Lcom/android/camera/data/data/ComponentDataItem;->mIconRes:I

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageResource(I)V

    iget-object v0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mImageView:Landroid/widget/ImageView;

    iget-object v1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->this$1:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;

    iget-object v1, v1, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;->this$0:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

    invoke-static {v1}, Lcom/android/camera/fragment/ambilight/FragmentAmbilight;->access$200(Lcom/android/camera/fragment/ambilight/FragmentAmbilight;)I

    move-result v1

    const/4 v2, 0x1

    const/4 v3, 0x0

    if-ne p1, v1, :cond_0

    move v1, v2

    goto :goto_0

    :cond_0
    move v1, v3

    :goto_0
    invoke-static {v0, v1}, Lcom/android/camera/Util;->correctionSelectView(Landroid/view/View;Z)V

    iget-object v0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->this$1:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;

    iget-object v0, v0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;->this$0:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

    invoke-static {v0}, Lcom/android/camera/fragment/ambilight/FragmentAmbilight;->access$200(Lcom/android/camera/fragment/ambilight/FragmentAmbilight;)I

    move-result v0

    if-ne p1, v0, :cond_3

    iget-object p1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p1, v2}, Landroid/view/View;->setActivated(Z)V

    invoke-static {}, Lcom/android/camera/Util;->isAccessible()Z

    move-result p1

    if-nez p1, :cond_1

    invoke-static {}, Lcom/android/camera/Util;->isSetContentDesc()Z

    move-result p1

    if-eqz p1, :cond_2

    :cond_1
    iget-object p1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->this$1:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;

    iget-object v1, v1, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;->this$0:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

    invoke-virtual {v1}, Landroidx/fragment/app/Fragment;->getContext()Landroid/content/Context;

    move-result-object v1

    iget p2, p2, Lcom/android/camera/data/data/ComponentDataItem;->mDisplayNameRes:I

    invoke-virtual {v1, p2}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object p2, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->this$1:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;

    iget-object p2, p2, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;->this$0:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

    const v1, 0x7f100067

    invoke-virtual {p2, v1}, Landroidx/fragment/app/Fragment;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/view/View;->setContentDescription(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    new-instance p2, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder$1;

    invoke-direct {p2, p0}, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder$1;-><init>(Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;)V

    const-wide/16 v0, 0x64

    invoke-virtual {p1, p2, v0, v1}, Landroid/view/View;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_2
    iget-object p1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    invoke-virtual {p1, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    const/high16 p1, 0x3f800000    # 1.0f

    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setAlpha(F)V

    goto :goto_1

    :cond_3
    iget-object p1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p1, v3}, Landroid/view/View;->setActivated(Z)V

    iget-object p1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    const/16 p2, 0x8

    invoke-virtual {p1, p2}, Landroid/widget/ImageView;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->mSelectedIndicator:Landroid/widget/ImageView;

    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setAlpha(F)V

    :goto_1
    return-void
.end method
