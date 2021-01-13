.class public final synthetic Lcom/android/camera/fragment/top/n;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/fragment/top/TopBarAnimationComponent;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/fragment/top/TopBarAnimationComponent;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/top/n;->tg:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/top/n;->tg:Lcom/android/camera/fragment/top/TopBarAnimationComponent;

    invoke-virtual {p0}, Lcom/android/camera/fragment/top/TopBarAnimationComponent;->Zc()V

    return-void
.end method
