.class public final synthetic Lcom/android/camera/ui/p;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/ui/VideoTagView;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/VideoTagView;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/p;->tg:Lcom/android/camera/ui/VideoTagView;

    return-void
.end method


# virtual methods
.method public final onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/p;->tg:Lcom/android/camera/ui/VideoTagView;

    invoke-virtual {p0, p1}, Lcom/android/camera/ui/VideoTagView;->e(Landroid/animation/ValueAnimator;)V

    return-void
.end method