.class public final synthetic Lcom/android/camera/module/Y;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/DualVideoModule;

.field private final synthetic ug:Landroid/view/ViewGroup;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/DualVideoModule;Landroid/view/ViewGroup;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/Y;->tg:Lcom/android/camera/module/DualVideoModule;

    iput-object p2, p0, Lcom/android/camera/module/Y;->ug:Landroid/view/ViewGroup;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/Y;->tg:Lcom/android/camera/module/DualVideoModule;

    iget-object p0, p0, Lcom/android/camera/module/Y;->ug:Landroid/view/ViewGroup;

    invoke-virtual {v0, p0}, Lcom/android/camera/module/DualVideoModule;->a(Landroid/view/ViewGroup;)V

    return-void
.end method
