.class Lcom/android/camera/timerburst/CustomSeekBar$5;
.super Ljava/lang/Object;
.source "CustomSeekBar.java"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/timerburst/CustomSeekBar;->reverseEnlagerAnim()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/timerburst/CustomSeekBar;


# direct methods
.method constructor <init>(Lcom/android/camera/timerburst/CustomSeekBar;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar$5;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 2

    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->getAnimatedValue()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Float;

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result p1

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar$5;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v0}, Lcom/android/camera/timerburst/CustomSeekBar;->access$1900(Lcom/android/camera/timerburst/CustomSeekBar;)Lcom/android/camera/timerburst/CircleFullImageView;

    move-result-object v0

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar$5;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    const v1, 0x7f060058

    invoke-virtual {p0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result p0

    invoke-virtual {v0, p1, p0}, Lcom/android/camera/timerburst/CircleFullImageView;->updateRadiusView(FI)V

    return-void
.end method
