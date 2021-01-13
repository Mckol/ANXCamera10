.class Lcom/android/camera/timerburst/CustomSeekBar$4;
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

    iput-object p1, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 6

    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->getAnimatedValue()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Float;

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result p1

    iget-object v0, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v0}, Lcom/android/camera/timerburst/CustomSeekBar;->access$800(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/widget/RelativeLayout;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/RelativeLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/RelativeLayout$LayoutParams;

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v1}, Lcom/android/camera/timerburst/CustomSeekBar;->access$1800(Lcom/android/camera/timerburst/CustomSeekBar;)F

    move-result v2

    sub-float/2addr v2, p1

    const/high16 v3, 0x40000000    # 2.0f

    div-float/2addr v2, v3

    invoke-static {v1, v2}, Lcom/android/camera/timerburst/CustomSeekBar;->access$1702(Lcom/android/camera/timerburst/CustomSeekBar;F)F

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v1}, Lcom/android/camera/timerburst/CustomSeekBar;->access$1700(Lcom/android/camera/timerburst/CustomSeekBar;)F

    move-result v1

    float-to-int v1, v1

    iget-object v2, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v2}, Lcom/android/camera/timerburst/CustomSeekBar;->access$1700(Lcom/android/camera/timerburst/CustomSeekBar;)F

    move-result v2

    float-to-int v2, v2

    const/4 v4, 0x0

    invoke-virtual {v0, v1, v4, v2, v4}, Landroid/widget/RelativeLayout$LayoutParams;->setMargins(IIII)V

    invoke-static {p1}, Ljava/lang/Math;->round(F)I

    move-result v1

    iput v1, v0, Landroid/widget/RelativeLayout$LayoutParams;->height:I

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v1}, Lcom/android/camera/timerburst/CustomSeekBar;->access$2000(Lcom/android/camera/timerburst/CustomSeekBar;)I

    move-result v1

    iget-object v2, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v2}, Lcom/android/camera/timerburst/CustomSeekBar;->access$2100(Lcom/android/camera/timerburst/CustomSeekBar;)[I

    move-result-object v2

    aget v2, v2, v4

    const/4 v5, 0x2

    if-ne v1, v2, :cond_0

    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v1, v5}, Lcom/android/camera/timerburst/CustomSeekBar;->access$200(Lcom/android/camera/timerburst/CustomSeekBar;I)F

    move-result v1

    invoke-static {v1}, Ljava/lang/Math;->round(F)I

    move-result v1

    iput v1, v0, Landroid/widget/RelativeLayout$LayoutParams;->width:I

    :cond_0
    iget-object v1, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {v1}, Lcom/android/camera/timerburst/CustomSeekBar;->access$800(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/widget/RelativeLayout;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/RelativeLayout;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    check-cast v1, Landroid/graphics/drawable/GradientDrawable;

    invoke-static {}, Lcom/android/camera/customization/TintColor;->tintColor()I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    const/16 v2, 0x8

    new-array v2, v2, [F

    div-float/2addr p1, v3

    aput p1, v2, v4

    const/4 v3, 0x1

    aput p1, v2, v3

    aput p1, v2, v5

    const/4 v3, 0x3

    aput p1, v2, v3

    const/4 v3, 0x4

    aput p1, v2, v3

    const/4 v3, 0x5

    aput p1, v2, v3

    const/4 v3, 0x6

    aput p1, v2, v3

    const/4 v3, 0x7

    aput p1, v2, v3

    invoke-virtual {v1, v2}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadii([F)V

    iget-object p0, p0, Lcom/android/camera/timerburst/CustomSeekBar$4;->this$0:Lcom/android/camera/timerburst/CustomSeekBar;

    invoke-static {p0}, Lcom/android/camera/timerburst/CustomSeekBar;->access$800(Lcom/android/camera/timerburst/CustomSeekBar;)Landroid/widget/RelativeLayout;

    move-result-object p0

    invoke-virtual {p0, v0}, Landroid/widget/RelativeLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    return-void
.end method
