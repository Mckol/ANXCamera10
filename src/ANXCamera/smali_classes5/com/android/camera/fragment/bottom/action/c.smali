.class public final synthetic Lcom/android/camera/fragment/bottom/action/c;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/ui/AdjustAnimationView;

.field private final synthetic ug:Landroid/view/View;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/AdjustAnimationView;Landroid/view/View;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/bottom/action/c;->tg:Lcom/android/camera/ui/AdjustAnimationView;

    iput-object p2, p0, Lcom/android/camera/fragment/bottom/action/c;->ug:Landroid/view/View;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/bottom/action/c;->tg:Lcom/android/camera/ui/AdjustAnimationView;

    iget-object p0, p0, Lcom/android/camera/fragment/bottom/action/c;->ug:Landroid/view/View;

    invoke-static {v0, p0}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->a(Lcom/android/camera/ui/AdjustAnimationView;Landroid/view/View;)V

    return-void
.end method
