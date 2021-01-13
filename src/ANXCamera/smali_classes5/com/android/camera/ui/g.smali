.class public final synthetic Lcom/android/camera/ui/g;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/ui/MutiStateButton;

.field private final synthetic ug:F

.field private final synthetic vg:F

.field private final synthetic wg:F

.field private final synthetic xg:F


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/MutiStateButton;FFFF)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/g;->tg:Lcom/android/camera/ui/MutiStateButton;

    iput p2, p0, Lcom/android/camera/ui/g;->ug:F

    iput p3, p0, Lcom/android/camera/ui/g;->vg:F

    iput p4, p0, Lcom/android/camera/ui/g;->wg:F

    iput p5, p0, Lcom/android/camera/ui/g;->xg:F

    return-void
.end method


# virtual methods
.method public final onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 6

    iget-object v0, p0, Lcom/android/camera/ui/g;->tg:Lcom/android/camera/ui/MutiStateButton;

    iget v1, p0, Lcom/android/camera/ui/g;->ug:F

    iget v2, p0, Lcom/android/camera/ui/g;->vg:F

    iget v3, p0, Lcom/android/camera/ui/g;->wg:F

    iget v4, p0, Lcom/android/camera/ui/g;->xg:F

    move-object v5, p1

    invoke-virtual/range {v0 .. v5}, Lcom/android/camera/ui/MutiStateButton;->a(FFFFLandroid/animation/ValueAnimator;)V

    return-void
.end method
