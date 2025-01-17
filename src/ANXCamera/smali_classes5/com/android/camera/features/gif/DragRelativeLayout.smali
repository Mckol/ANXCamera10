.class public Lcom/android/camera/features/gif/DragRelativeLayout;
.super Landroid/widget/RelativeLayout;
.source "DragRelativeLayout.java"


# instance fields
.field private bottomParent:I

.field private bottomParentAfter:I

.field private endx:F

.field private endy:F

.field private horVector:I

.field private leftParent:I

.field private leftParentAfter:I

.field private rightParent:I

.field private rightParentAfter:I

.field private startx:F

.field private starty:F

.field private topParent:I

.field private topParentAfter:I

.field private verVector:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/camera/features/gif/DragRelativeLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/android/camera/features/gif/DragRelativeLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/widget/RelativeLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method


# virtual methods
.method public getBottomParentAfter()I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->bottomParentAfter:I

    return p0
.end method

.method public getLeftParentAfter()I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->leftParentAfter:I

    return p0
.end method

.method public getRightParentAfter()I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->rightParentAfter:I

    return p0
.end method

.method public getTopParentAfter()I
    .locals 0

    iget p0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->topParentAfter:I

    return p0
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 4

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_2

    if-eq v0, v1, :cond_3

    const/4 v2, 0x2

    if-eq v0, v2, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    iput v0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->endx:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->endy:F

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getLeft()I

    move-result p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->leftParent:I

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getTop()I

    move-result p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->topParent:I

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getRight()I

    move-result p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->rightParent:I

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->getBottom()I

    move-result p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->bottomParent:I

    iget p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->endx:F

    iget v0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->startx:F

    sub-float/2addr p1, v0

    float-to-int p1, p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->horVector:I

    iget p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->endy:F

    iget v0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->starty:F

    sub-float/2addr p1, v0

    float-to-int p1, p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->verVector:I

    iget p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->leftParent:I

    iget v0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->horVector:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->leftParentAfter:I

    iget p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->topParent:I

    iget v2, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->verVector:I

    add-int/2addr p1, v2

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->topParentAfter:I

    iget p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->rightParent:I

    add-int/2addr p1, v0

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->rightParentAfter:I

    iget p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->bottomParent:I

    add-int/2addr p1, v2

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->bottomParentAfter:I

    if-nez v0, :cond_1

    if-eqz v2, :cond_3

    :cond_1
    iget p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->leftParentAfter:I

    iget v0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->topParentAfter:I

    iget v2, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->rightParentAfter:I

    iget v3, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->bottomParentAfter:I

    invoke-virtual {p0, p1, v0, v2, v3}, Landroid/widget/RelativeLayout;->layout(IIII)V

    invoke-virtual {p0}, Landroid/widget/RelativeLayout;->invalidate()V

    goto :goto_0

    :cond_2
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v0

    iput v0, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->startx:F

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result p1

    iput p1, p0, Lcom/android/camera/features/gif/DragRelativeLayout;->starty:F

    :cond_3
    :goto_0
    return v1
.end method
