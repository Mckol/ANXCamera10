.class public final synthetic Lcom/android/camera/fragment/dialog/k;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/fragment/dialog/LongPressLiveFragment;

.field private final synthetic ug:Landroid/view/View;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/fragment/dialog/LongPressLiveFragment;Landroid/view/View;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/dialog/k;->tg:Lcom/android/camera/fragment/dialog/LongPressLiveFragment;

    iput-object p2, p0, Lcom/android/camera/fragment/dialog/k;->ug:Landroid/view/View;

    return-void
.end method


# virtual methods
.method public final onClick(Landroid/view/View;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/dialog/k;->tg:Lcom/android/camera/fragment/dialog/LongPressLiveFragment;

    iget-object p0, p0, Lcom/android/camera/fragment/dialog/k;->ug:Landroid/view/View;

    invoke-virtual {v0, p0, p1}, Lcom/android/camera/fragment/dialog/LongPressLiveFragment;->a(Landroid/view/View;Landroid/view/View;)V

    return-void
.end method
