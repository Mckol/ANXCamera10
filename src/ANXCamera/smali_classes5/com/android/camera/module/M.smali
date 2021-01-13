.class public final synthetic Lcom/android/camera/module/M;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/DualVideoModule;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/DualVideoModule;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/M;->tg:Lcom/android/camera/module/DualVideoModule;

    return-void
.end method


# virtual methods
.method public final onClick(Landroid/view/View;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/M;->tg:Lcom/android/camera/module/DualVideoModule;

    invoke-virtual {p0, p1}, Lcom/android/camera/module/DualVideoModule;->l(Landroid/view/View;)V

    return-void
.end method
