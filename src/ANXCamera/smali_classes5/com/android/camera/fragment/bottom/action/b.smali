.class public final synthetic Lcom/android/camera/fragment/bottom/action/b;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/ui/AdjustAnimationView;

.field private final synthetic ug:Lcom/android/camera/ui/AnimationView;

.field private final synthetic vg:Landroid/view/View;

.field private final synthetic wg:F

.field private final synthetic xg:F


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/AdjustAnimationView;Lcom/android/camera/ui/AnimationView;Landroid/view/View;FF)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/bottom/action/b;->tg:Lcom/android/camera/ui/AdjustAnimationView;

    iput-object p2, p0, Lcom/android/camera/fragment/bottom/action/b;->ug:Lcom/android/camera/ui/AnimationView;

    iput-object p3, p0, Lcom/android/camera/fragment/bottom/action/b;->vg:Landroid/view/View;

    iput p4, p0, Lcom/android/camera/fragment/bottom/action/b;->wg:F

    iput p5, p0, Lcom/android/camera/fragment/bottom/action/b;->xg:F

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 4

    iget-object v0, p0, Lcom/android/camera/fragment/bottom/action/b;->tg:Lcom/android/camera/ui/AdjustAnimationView;

    iget-object v1, p0, Lcom/android/camera/fragment/bottom/action/b;->ug:Lcom/android/camera/ui/AnimationView;

    iget-object v2, p0, Lcom/android/camera/fragment/bottom/action/b;->vg:Landroid/view/View;

    iget v3, p0, Lcom/android/camera/fragment/bottom/action/b;->wg:F

    iget p0, p0, Lcom/android/camera/fragment/bottom/action/b;->xg:F

    invoke-static {v0, v1, v2, v3, p0}, Lcom/android/camera/fragment/bottom/action/FragmentBottomAction;->a(Lcom/android/camera/ui/AdjustAnimationView;Lcom/android/camera/ui/AnimationView;Landroid/view/View;FF)V

    return-void
.end method
