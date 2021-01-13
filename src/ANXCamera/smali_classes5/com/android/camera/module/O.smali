.class public final synthetic Lcom/android/camera/module/O;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera/dualvideo/TouchEventView$TouchEventListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/DualVideoModule;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/DualVideoModule;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/O;->tg:Lcom/android/camera/module/DualVideoModule;

    return-void
.end method


# virtual methods
.method public final onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/O;->tg:Lcom/android/camera/module/DualVideoModule;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/DualVideoModule;->a(Landroid/view/MotionEvent;)Z

    move-result p0

    return p0
.end method
