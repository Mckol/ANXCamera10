.class Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;
.super Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;
.source "MimojiAvatarAdapter.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "AvatarViewViewHolder"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder<",
        "Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;",
        ">;"
    }
.end annotation


# instance fields
.field private mAdapterSelectState:Ljava/lang/String;

.field private mCartoonSwitchView:Landroid/view/View;

.field private mItemImageView:Landroid/widget/ImageView;

.field private mLongSelectedView:Landroid/view/View;

.field private mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

.field private mSelectItemView:Landroid/view/View;

.field final synthetic this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;


# direct methods
.method public constructor <init>(Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;Landroid/view/View;)V
    .locals 1
    .param p1    # Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;
        .annotation build Landroidx/annotation/NonNull;
        .end annotation
    .end param

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->this$0:Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;

    invoke-direct {p0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;-><init>(Landroid/view/View;)V

    sget v0, Lcom/android/camera/features/mimoji2/widget/baseview/BaseItemAnimator;->DEFAULT_LIST_DURATION:I

    invoke-static {p2, v0}, Lcom/android/camera/animation/FolmeUtils;->handleListItemTouch(Landroid/view/View;I)V

    const v0, 0x7f0901bf

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mItemImageView:Landroid/widget/ImageView;

    const v0, 0x7f0901c0

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mSelectItemView:Landroid/view/View;

    const v0, 0x7f0901c3

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mLongSelectedView:Landroid/view/View;

    const v0, 0x7f0901be

    invoke-virtual {p2, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mCartoonSwitchView:Landroid/view/View;

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemLive()Lcom/android/camera/data/data/extra/DataItemLive;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/camera/data/data/extra/DataItemLive;->getMimojiStatusManager2()Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    move-result-object v0

    iput-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-static {p1}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;->access$000(Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter;)I

    move-result p0

    invoke-virtual {p2, p0}, Landroid/view/View;->setBackgroundResource(I)V

    return-void
.end method


# virtual methods
.method public getRotateViews()[Landroid/view/View;
    .locals 3

    const/4 v0, 0x3

    new-array v0, v0, [Landroid/view/View;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mCartoonSwitchView:Landroid/view/View;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mItemImageView:Landroid/widget/ImageView;

    const/4 v2, 0x1

    aput-object v1, v0, v2

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mLongSelectedView:Landroid/view/View;

    const/4 v1, 0x2

    aput-object p0, v0, v1

    return-object v0
.end method

.method public setData(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;I)V
    .locals 7

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p2}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiState()Ljava/lang/String;

    move-result-object p2

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mAdapterSelectState:Ljava/lang/String;

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mCartoonSwitchView:Landroid/view/View;

    const/16 v0, 0x8

    invoke-virtual {p2, v0}, Landroid/view/View;->setVisibility(I)V

    if-eqz p1, :cond_b

    iget-object p2, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-eqz p2, :cond_0

    goto/16 :goto_5

    :cond_0
    iget-object p2, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    const-string v1, "close_state"

    invoke-virtual {v1, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    const-string v2, "add_state"

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-eqz p2, :cond_1

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mItemImageView:Landroid/widget/ImageView;

    const v5, 0x7f080276

    invoke-virtual {p2, v5}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1

    :cond_1
    iget-object p2, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-virtual {v2, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_2

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mItemImageView:Landroid/widget/ImageView;

    const v5, 0x7f08026b

    invoke-virtual {p2, v5}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1

    :cond_2
    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->getFrame()I

    move-result p2

    const/16 v5, 0xa

    if-lez p2, :cond_3

    iget-object p2, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {p2}, Lcom/bumptech/glide/Glide;->with(Landroid/content/Context;)Lcom/bumptech/glide/RequestManager;

    move-result-object p2

    iget-object v6, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mThumbnailUrl2:Ljava/lang/String;

    invoke-virtual {p2, v6}, Lcom/bumptech/glide/RequestManager;->load(Ljava/lang/String;)Lcom/bumptech/glide/RequestBuilder;

    move-result-object p2

    invoke-virtual {p2, v4}, Lcom/bumptech/glide/request/BaseRequestOptions;->skipMemoryCache(Z)Lcom/bumptech/glide/request/BaseRequestOptions;

    move-result-object p2

    check-cast p2, Lcom/bumptech/glide/RequestBuilder;

    sget-object v6, Lcom/bumptech/glide/load/engine/DiskCacheStrategy;->ALL:Lcom/bumptech/glide/load/engine/DiskCacheStrategy;

    invoke-virtual {p2, v6}, Lcom/bumptech/glide/request/BaseRequestOptions;->diskCacheStrategy(Lcom/bumptech/glide/load/engine/DiskCacheStrategy;)Lcom/bumptech/glide/request/BaseRequestOptions;

    move-result-object p2

    check-cast p2, Lcom/bumptech/glide/RequestBuilder;

    new-instance v6, Lcom/android/camera/fragment/music/RoundedCornersTransformation;

    invoke-direct {v6, v5, v3}, Lcom/android/camera/fragment/music/RoundedCornersTransformation;-><init>(II)V

    invoke-static {v6}, Lcom/bumptech/glide/request/RequestOptions;->bitmapTransform(Lcom/bumptech/glide/load/Transformation;)Lcom/bumptech/glide/request/RequestOptions;

    move-result-object v5

    invoke-virtual {p2, v5}, Lcom/bumptech/glide/RequestBuilder;->apply(Lcom/bumptech/glide/request/BaseRequestOptions;)Lcom/bumptech/glide/RequestBuilder;

    move-result-object p2

    iget-object v5, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mItemImageView:Landroid/widget/ImageView;

    invoke-virtual {p2, v5}, Lcom/bumptech/glide/RequestBuilder;->into(Landroid/widget/ImageView;)Lcom/bumptech/glide/request/target/ViewTarget;

    goto :goto_0

    :cond_3
    iget-object p2, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {p2}, Lcom/bumptech/glide/Glide;->with(Landroid/content/Context;)Lcom/bumptech/glide/RequestManager;

    move-result-object p2

    iget-object v6, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mThumbnailUrl:Ljava/lang/String;

    invoke-virtual {p2, v6}, Lcom/bumptech/glide/RequestManager;->load(Ljava/lang/String;)Lcom/bumptech/glide/RequestBuilder;

    move-result-object p2

    invoke-virtual {p2, v4}, Lcom/bumptech/glide/request/BaseRequestOptions;->skipMemoryCache(Z)Lcom/bumptech/glide/request/BaseRequestOptions;

    move-result-object p2

    check-cast p2, Lcom/bumptech/glide/RequestBuilder;

    sget-object v6, Lcom/bumptech/glide/load/engine/DiskCacheStrategy;->ALL:Lcom/bumptech/glide/load/engine/DiskCacheStrategy;

    invoke-virtual {p2, v6}, Lcom/bumptech/glide/request/BaseRequestOptions;->diskCacheStrategy(Lcom/bumptech/glide/load/engine/DiskCacheStrategy;)Lcom/bumptech/glide/request/BaseRequestOptions;

    move-result-object p2

    check-cast p2, Lcom/bumptech/glide/RequestBuilder;

    new-instance v6, Lcom/android/camera/fragment/music/RoundedCornersTransformation;

    invoke-direct {v6, v5, v3}, Lcom/android/camera/fragment/music/RoundedCornersTransformation;-><init>(II)V

    invoke-static {v6}, Lcom/bumptech/glide/request/RequestOptions;->bitmapTransform(Lcom/bumptech/glide/load/Transformation;)Lcom/bumptech/glide/request/RequestOptions;

    move-result-object v5

    invoke-virtual {p2, v5}, Lcom/bumptech/glide/RequestBuilder;->apply(Lcom/bumptech/glide/request/BaseRequestOptions;)Lcom/bumptech/glide/RequestBuilder;

    move-result-object p2

    iget-object v5, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mItemImageView:Landroid/widget/ImageView;

    invoke-virtual {p2, v5}, Lcom/bumptech/glide/RequestBuilder;->into(Landroid/widget/ImageView;)Lcom/bumptech/glide/request/target/ViewTarget;

    :goto_0
    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->getDefaultFrame()I

    move-result p2

    if-lez p2, :cond_4

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mMimojiStatusManager2:Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;

    invoke-virtual {p2}, Lcom/android/camera/features/mimoji2/widget/helper/MimojiStatusManager2;->getCurrentMimojiInfo()Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    move-result-object p2

    if-eqz p2, :cond_4

    iget-object v5, p2, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mAvatarTemplatePath:Ljava/lang/String;

    invoke-static {v5}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v5

    if-nez v5, :cond_4

    iget-object p2, p2, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mAvatarTemplatePath:Ljava/lang/String;

    iget-object v5, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mAvatarTemplatePath:Ljava/lang/String;

    invoke-virtual {p2, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_4

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mCartoonSwitchView:Landroid/view/View;

    invoke-virtual {p2, v4}, Landroid/view/View;->setVisibility(I)V

    :cond_4
    :goto_1
    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mAdapterSelectState:Ljava/lang/String;

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-nez p2, :cond_a

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mAdapterSelectState:Ljava/lang/String;

    iget-object v5, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-virtual {p2, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_a

    iget-object p2, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-virtual {p2, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-nez p2, :cond_a

    iget-object p2, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-static {p2}, Lcom/android/camera/features/mimoji2/widget/helper/AvatarEngineManager2;->isPrefabModel(Ljava/lang/String;)Z

    move-result p2

    const v0, 0x7f080093

    if-nez p2, :cond_6

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mAdapterSelectState:Ljava/lang/String;

    invoke-virtual {p2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-nez p2, :cond_6

    iget-object p2, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-virtual {p2, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_5

    goto :goto_2

    :cond_5
    new-array p2, v3, [Landroid/view/View;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mLongSelectedView:Landroid/view/View;

    aput-object v1, p2, v4

    invoke-virtual {p0, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;->showView([Landroid/view/View;)V

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mSelectItemView:Landroid/view/View;

    iget-object v1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v1}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {p2, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_3

    :cond_6
    :goto_2
    sget p2, Lcom/android/camera/features/mimoji2/widget/baseview/BaseItemAnimator;->DEFAULT_LIST_DURATION:I

    new-array v1, v3, [Landroid/view/View;

    iget-object v2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mLongSelectedView:Landroid/view/View;

    aput-object v2, v1, v4

    invoke-virtual {p0, p2, v1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;->alphaGone(I[Landroid/view/View;)V

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mSelectItemView:Landroid/view/View;

    iget-object v1, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {v1}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v2, 0x7f080084

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {p2, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :goto_3
    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->getDefaultFrame()I

    move-result p2

    if-lez p2, :cond_9

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mAdapterSelectState:Ljava/lang/String;

    iget-object v1, p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->mConfigPath:Ljava/lang/String;

    invoke-virtual {p2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_9

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mCartoonSwitchView:Landroid/view/View;

    invoke-virtual {p2, v4}, Landroid/view/View;->setVisibility(I)V

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->isIsNeedAnim()Z

    move-result p2

    if-eqz p2, :cond_8

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mCartoonSwitchView:Landroid/view/View;

    invoke-static {p2}, Landroidx/core/view/ViewCompat;->animate(Landroid/view/View;)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p2

    invoke-virtual {p1}, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->getFrame()I

    move-result v1

    if-nez v1, :cond_7

    const/high16 v1, -0x3ccc0000    # -180.0f

    goto :goto_4

    :cond_7
    const/high16 v1, 0x43340000    # 180.0f

    :goto_4
    invoke-virtual {p2, v1}, Landroidx/core/view/ViewPropertyAnimatorCompat;->rotationBy(F)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p2

    sget v1, Lcom/android/camera/features/mimoji2/widget/baseview/BaseItemAnimator;->DEFAULT_LIST_DURATION:I

    int-to-long v1, v1

    invoke-virtual {p2, v1, v2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->setDuration(J)Landroidx/core/view/ViewPropertyAnimatorCompat;

    move-result-object p2

    invoke-virtual {p2}, Landroidx/core/view/ViewPropertyAnimatorCompat;->start()V

    invoke-virtual {p1, v4}, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;->setIsNeedAnim(Z)V

    :cond_8
    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mSelectItemView:Landroid/view/View;

    iget-object p2, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-virtual {p2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p2

    invoke-virtual {p2, v0}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_9
    new-array p1, v3, [Landroid/view/View;

    iget-object p2, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mSelectItemView:Landroid/view/View;

    aput-object p2, p1, v4

    invoke-virtual {p0, p1}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;->showView([Landroid/view/View;)V

    goto :goto_5

    :cond_a
    sget p1, Lcom/android/camera/features/mimoji2/widget/baseview/BaseItemAnimator;->DEFAULT_LIST_DURATION:I

    new-array p2, v3, [Landroid/view/View;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mSelectItemView:Landroid/view/View;

    aput-object v1, p2, v4

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/widget/baseview/BaseRecyclerViewHolder;->alphaGone(I[Landroid/view/View;)V

    iget-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mLongSelectedView:Landroid/view/View;

    invoke-virtual {p1, v0}, Landroid/view/View;->setVisibility(I)V

    iget-object p0, p0, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->mCartoonSwitchView:Landroid/view/View;

    invoke-virtual {p0, v0}, Landroid/view/View;->setVisibility(I)V

    :cond_b
    :goto_5
    return-void
.end method

.method public bridge synthetic setData(Ljava/lang/Object;I)V
    .locals 0

    check-cast p1, Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/features/mimoji2/fragment/bottomlist/MimojiAvatarAdapter$AvatarViewViewHolder;->setData(Lcom/android/camera/features/mimoji2/bean/MimojiInfo2;I)V

    return-void
.end method
