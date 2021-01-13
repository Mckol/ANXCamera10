.class Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment$2;
.super Ljava/lang/Object;
.source "BeautySmoothLevelFragment.java"

# interfaces
.implements Lcom/android/camera/ui/SeekBarCompat$OnSeekBarCompatTouchListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;->initView(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment$2;->this$0:Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 3

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result p1

    const/4 v0, 0x1

    if-eqz p1, :cond_0

    if-eq p1, v0, :cond_1

    const/4 v1, 0x2

    if-eq p1, v1, :cond_1

    const/4 p0, 0x0

    return p0

    :cond_0
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getX()F

    move-result p1

    float-to-int p1, p1

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getY()F

    move-result v1

    float-to-int v1, v1

    iget-object v2, p0, Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment$2;->this$0:Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;

    invoke-static {v2}, Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;->access$100(Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;)Lcom/android/camera/ui/SeekBarCompat;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/camera/ui/SeekBarCompat;->getTouchRect()Landroid/graphics/Rect;

    move-result-object v2

    invoke-virtual {v2, p1, v1}, Landroid/graphics/Rect;->contains(II)Z

    move-result p1

    if-nez p1, :cond_1

    return v0

    :cond_1
    iget-object p1, p0, Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment$2;->this$0:Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;

    invoke-static {p1}, Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;->access$100(Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;)Lcom/android/camera/ui/SeekBarCompat;

    move-result-object p1

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getX()F

    move-result p2

    invoke-virtual {p1, p2}, Lcom/android/camera/ui/SeekBarCompat;->getNextProgress(F)I

    move-result p1

    iget-object p0, p0, Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment$2;->this$0:Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;

    invoke-static {p0}, Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;->access$100(Lcom/android/camera/fragment/beauty/BeautySmoothLevelFragment;)Lcom/android/camera/ui/SeekBarCompat;

    move-result-object p0

    invoke-virtual {p0, p1, v0}, Lcom/android/camera/ui/SeekBarCompat;->setProgress(IZ)V

    return v0
.end method
