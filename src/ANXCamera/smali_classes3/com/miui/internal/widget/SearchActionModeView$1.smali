.class Lcom/miui/internal/widget/SearchActionModeView$1;
.super Ljava/lang/Object;
.source "SearchActionModeView.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/miui/internal/widget/SearchActionModeView;->onAnimationEnd(Landroid/animation/Animator;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/miui/internal/widget/SearchActionModeView;


# direct methods
.method constructor <init>(Lcom/miui/internal/widget/SearchActionModeView;)V
    .locals 0

    iput-object p1, p0, Lcom/miui/internal/widget/SearchActionModeView$1;->this$0:Lcom/miui/internal/widget/SearchActionModeView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    iget-object p0, p0, Lcom/miui/internal/widget/SearchActionModeView$1;->this$0:Lcom/miui/internal/widget/SearchActionModeView;

    invoke-static {p0}, Lcom/miui/internal/widget/SearchActionModeView;->access$000(Lcom/miui/internal/widget/SearchActionModeView;)Z

    move-result v0

    invoke-virtual {p0, v0}, Lcom/miui/internal/widget/SearchActionModeView;->setResultViewMargin(Z)V

    return-void
.end method
