.class public Lcom/android/camera/ui/RotateTextToast;
.super Ljava/lang/Object;
.source "RotateTextToast.java"


# static fields
.field private static final TOAST_DURATION:I = 0x1388

.field private static sRotateTextToast:Lcom/android/camera/ui/RotateTextToast;


# instance fields
.field mHandler:Landroid/os/Handler;

.field mLayoutRoot:Landroid/view/ViewGroup;

.field private final mRunnable:Ljava/lang/Runnable;

.field mToast:Lcom/android/camera/ui/RotateLayout;


# direct methods
.method private constructor <init>(Landroid/app/Activity;)V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Lcom/android/camera/ui/RotateTextToast$1;

    invoke-direct {v0, p0}, Lcom/android/camera/ui/RotateTextToast$1;-><init>(Lcom/android/camera/ui/RotateTextToast;)V

    iput-object v0, p0, Lcom/android/camera/ui/RotateTextToast;->mRunnable:Ljava/lang/Runnable;

    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/android/camera/ui/RotateTextToast;->mHandler:Landroid/os/Handler;

    invoke-virtual {p1}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/android/camera/ui/RotateTextToast;->mLayoutRoot:Landroid/view/ViewGroup;

    invoke-virtual {p1}, Landroid/app/Activity;->getLayoutInflater()Landroid/view/LayoutInflater;

    move-result-object p1

    iget-object v0, p0, Lcom/android/camera/ui/RotateTextToast;->mLayoutRoot:Landroid/view/ViewGroup;

    const v1, 0x7f0c00f3

    invoke-virtual {p1, v1, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p1

    const v0, 0x7f090257

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/android/camera/ui/RotateLayout;

    iput-object p1, p0, Lcom/android/camera/ui/RotateTextToast;->mToast:Lcom/android/camera/ui/RotateLayout;

    return-void
.end method

.method static synthetic access$002(Lcom/android/camera/ui/RotateTextToast;)Lcom/android/camera/ui/RotateTextToast;
    .locals 0

    sput-object p0, Lcom/android/camera/ui/RotateTextToast;->sRotateTextToast:Lcom/android/camera/ui/RotateTextToast;

    return-object p0
.end method

.method public static getInstance()Lcom/android/camera/ui/RotateTextToast;
    .locals 1

    sget-object v0, Lcom/android/camera/ui/RotateTextToast;->sRotateTextToast:Lcom/android/camera/ui/RotateTextToast;

    return-object v0
.end method

.method public static getInstance(Landroid/app/Activity;)Lcom/android/camera/ui/RotateTextToast;
    .locals 1

    sget-object v0, Lcom/android/camera/ui/RotateTextToast;->sRotateTextToast:Lcom/android/camera/ui/RotateTextToast;

    if-nez v0, :cond_0

    new-instance v0, Lcom/android/camera/ui/RotateTextToast;

    invoke-direct {v0, p0}, Lcom/android/camera/ui/RotateTextToast;-><init>(Landroid/app/Activity;)V

    sput-object v0, Lcom/android/camera/ui/RotateTextToast;->sRotateTextToast:Lcom/android/camera/ui/RotateTextToast;

    :cond_0
    sget-object p0, Lcom/android/camera/ui/RotateTextToast;->sRotateTextToast:Lcom/android/camera/ui/RotateTextToast;

    return-object p0
.end method


# virtual methods
.method public show(II)V
    .locals 2

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/android/camera/ui/RotateTextToast;->mHandler:Landroid/os/Handler;

    iget-object p2, p0, Lcom/android/camera/ui/RotateTextToast;->mRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    iget-object p1, p0, Lcom/android/camera/ui/RotateTextToast;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/ui/RotateTextToast;->mRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/android/camera/ui/RotateTextToast;->mToast:Lcom/android/camera/ui/RotateLayout;

    const v1, 0x7f0901b6

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(I)V

    iget-object p1, p0, Lcom/android/camera/ui/RotateTextToast;->mToast:Lcom/android/camera/ui/RotateLayout;

    const/4 v0, 0x0

    invoke-virtual {p1, p2, v0}, Lcom/android/camera/ui/RotateLayout;->setOrientation(IZ)V

    iget-object p1, p0, Lcom/android/camera/ui/RotateTextToast;->mToast:Lcom/android/camera/ui/RotateLayout;

    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setVisibility(I)V

    iget-object p1, p0, Lcom/android/camera/ui/RotateTextToast;->mHandler:Landroid/os/Handler;

    iget-object p2, p0, Lcom/android/camera/ui/RotateTextToast;->mRunnable:Ljava/lang/Runnable;

    invoke-virtual {p1, p2}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    iget-object p1, p0, Lcom/android/camera/ui/RotateTextToast;->mHandler:Landroid/os/Handler;

    iget-object p0, p0, Lcom/android/camera/ui/RotateTextToast;->mRunnable:Ljava/lang/Runnable;

    const-wide/16 v0, 0x1388

    invoke-virtual {p1, p0, v0, v1}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    :goto_0
    return-void
.end method
