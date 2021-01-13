.class public final synthetic Lcom/android/camera/fragment/top/l;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/fragment/top/FragmentTopConfig;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/fragment/top/FragmentTopConfig;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/top/l;->tg:Lcom/android/camera/fragment/top/FragmentTopConfig;

    return-void
.end method


# virtual methods
.method public final onClick(Landroid/view/View;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/l;->tg:Lcom/android/camera/fragment/top/FragmentTopConfig;

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/top/FragmentTopConfig;->i(Landroid/view/View;)V

    return-void
.end method
