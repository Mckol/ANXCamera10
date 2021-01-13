.class Lcom/android/camera/ui/DragLayout$1;
.super Landroid/animation/AnimatorListenerAdapter;
.source "DragLayout.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/ui/DragLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/ui/DragLayout;


# direct methods
.method constructor <init>(Lcom/android/camera/ui/DragLayout;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-direct {p0}, Landroid/animation/AnimatorListenerAdapter;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationEnd(Landroid/animation/Animator;)V
    .locals 1

    invoke-super {p0, p1}, Landroid/animation/AnimatorListenerAdapter;->onAnimationEnd(Landroid/animation/Animator;)V

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$100(Lcom/android/camera/ui/DragLayout;)I

    move-result p1

    const/4 v0, 0x2

    if-ne p1, v0, :cond_1

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$300(Lcom/android/camera/ui/DragLayout;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x4

    goto :goto_0

    :cond_0
    const/4 v0, 0x3

    :goto_0
    invoke-static {p1, v0}, Lcom/android/camera/ui/DragLayout;->access$500(Lcom/android/camera/ui/DragLayout;I)V

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$400(Lcom/android/camera/ui/DragLayout;)Lcom/android/camera/ui/DragLayout$Dependency;

    move-result-object p1

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p0}, Lcom/android/camera/ui/DragLayout;->access$300(Lcom/android/camera/ui/DragLayout;)Z

    move-result p0

    invoke-virtual {p1, p0}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragDone(Z)V

    :cond_1
    return-void
.end method

.method public onAnimationStart(Landroid/animation/Animator;)V
    .locals 2

    invoke-super {p0, p1}, Landroid/animation/AnimatorListenerAdapter;->onAnimationStart(Landroid/animation/Animator;)V

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$100(Lcom/android/camera/ui/DragLayout;)I

    move-result p1

    const/4 v0, 0x3

    const/4 v1, 0x4

    if-eq p1, v1, :cond_0

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$100(Lcom/android/camera/ui/DragLayout;)I

    move-result p1

    if-ne p1, v0, :cond_2

    :cond_0
    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$100(Lcom/android/camera/ui/DragLayout;)I

    move-result v1

    if-ne v1, v0, :cond_1

    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    invoke-static {p1, v0}, Lcom/android/camera/ui/DragLayout;->access$202(Lcom/android/camera/ui/DragLayout;Z)Z

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$200(Lcom/android/camera/ui/DragLayout;)Z

    move-result v0

    invoke-static {p1, v0}, Lcom/android/camera/ui/DragLayout;->access$302(Lcom/android/camera/ui/DragLayout;Z)Z

    iget-object p1, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {p1}, Lcom/android/camera/ui/DragLayout;->access$400(Lcom/android/camera/ui/DragLayout;)Lcom/android/camera/ui/DragLayout$Dependency;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    invoke-static {v0}, Lcom/android/camera/ui/DragLayout;->access$200(Lcom/android/camera/ui/DragLayout;)Z

    move-result v0

    invoke-virtual {p1, v0}, Lcom/android/camera/ui/DragLayout$Dependency;->onDragStart(Z)V

    iget-object p0, p0, Lcom/android/camera/ui/DragLayout$1;->this$0:Lcom/android/camera/ui/DragLayout;

    const/4 p1, 0x2

    invoke-static {p0, p1}, Lcom/android/camera/ui/DragLayout;->access$500(Lcom/android/camera/ui/DragLayout;I)V

    :cond_2
    return-void
.end method
