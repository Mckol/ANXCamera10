.class Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;
.super Landroidx/recyclerview/widget/RecyclerView$OnScrollListener;
.source "FragmentCloneUseGuide.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->initAdapter()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$OnScrollListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onScrollStateChanged(Landroidx/recyclerview/widget/RecyclerView;I)V
    .locals 1

    iget-object p1, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p1, p2}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$302(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;I)I

    if-nez p2, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p1}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$400(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Ljava/util/List;

    move-result-object p1

    invoke-interface {p1}, Ljava/util/List;->isEmpty()Z

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p1}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$200(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Lcom/android/camera/visibilityutils/calculator/ListItemsVisibilityCalculator;

    move-result-object p1

    iget-object p2, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p2}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$000(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Lcom/android/camera/visibilityutils/scroll_utils/ItemsPositionGetter;

    move-result-object p2

    iget-object v0, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {v0}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$100(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Landroidx/recyclerview/widget/LinearLayoutManager;

    move-result-object v0

    invoke-virtual {v0}, Landroidx/recyclerview/widget/LinearLayoutManager;->findFirstVisibleItemPosition()I

    move-result v0

    iget-object p0, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p0}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$100(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Landroidx/recyclerview/widget/LinearLayoutManager;

    move-result-object p0

    invoke-virtual {p0}, Landroidx/recyclerview/widget/LinearLayoutManager;->findLastVisibleItemPosition()I

    move-result p0

    invoke-interface {p1, p2, v0, p0}, Lcom/android/camera/visibilityutils/calculator/ListItemsVisibilityCalculator;->onScrollStateIdle(Lcom/android/camera/visibilityutils/scroll_utils/ItemsPositionGetter;II)V

    :cond_0
    return-void
.end method

.method public onScrolled(Landroidx/recyclerview/widget/RecyclerView;II)V
    .locals 2

    iget-object p1, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p1}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$400(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Ljava/util/List;

    move-result-object p1

    invoke-interface {p1}, Ljava/util/List;->isEmpty()Z

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p1}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$200(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Lcom/android/camera/visibilityutils/calculator/ListItemsVisibilityCalculator;

    move-result-object p1

    iget-object p2, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p2}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$000(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Lcom/android/camera/visibilityutils/scroll_utils/ItemsPositionGetter;

    move-result-object p2

    iget-object p3, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p3}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$100(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Landroidx/recyclerview/widget/LinearLayoutManager;

    move-result-object p3

    invoke-virtual {p3}, Landroidx/recyclerview/widget/LinearLayoutManager;->findFirstVisibleItemPosition()I

    move-result p3

    iget-object v0, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {v0}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$100(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Landroidx/recyclerview/widget/LinearLayoutManager;

    move-result-object v0

    invoke-virtual {v0}, Landroidx/recyclerview/widget/LinearLayoutManager;->findLastVisibleItemPosition()I

    move-result v0

    iget-object v1, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {v1}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$100(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)Landroidx/recyclerview/widget/LinearLayoutManager;

    move-result-object v1

    invoke-virtual {v1}, Landroidx/recyclerview/widget/LinearLayoutManager;->findFirstVisibleItemPosition()I

    move-result v1

    sub-int/2addr v0, v1

    add-int/lit8 v0, v0, 0x1

    iget-object p0, p0, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide$3;->this$0:Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;

    invoke-static {p0}, Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;->access$300(Lcom/android/camera/fragment/clone/FragmentCloneUseGuide;)I

    move-result p0

    invoke-interface {p1, p2, p3, v0, p0}, Lcom/android/camera/visibilityutils/calculator/ListItemsVisibilityCalculator;->onScroll(Lcom/android/camera/visibilityutils/scroll_utils/ItemsPositionGetter;III)V

    :cond_0
    return-void
.end method
