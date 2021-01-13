.class Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder$1;
.super Ljava/lang/Object;
.source "FragmentAmbilight.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->bindEffectIndex(ILcom/android/camera/data/data/ComponentDataItem;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$2:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder$1;->this$2:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder$1;->this$2:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;

    iget-object v0, v0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;->this$1:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;

    iget-object v0, v0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter;->this$0:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

    invoke-virtual {v0}, Landroidx/fragment/app/Fragment;->isAdded()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder$1;->this$2:Lcom/android/camera/fragment/ambilight/FragmentAmbilight$SceneModeItemAdapter$AmbilightModeItemHolder;

    iget-object p0, p0, Landroidx/recyclerview/widget/RecyclerView$ViewHolder;->itemView:Landroid/view/View;

    const/16 v0, 0x80

    invoke-virtual {p0, v0}, Landroid/view/View;->sendAccessibilityEvent(I)V

    :cond_0
    return-void
.end method
