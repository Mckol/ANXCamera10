.class public final synthetic Lcom/android/camera/fragment/fullscreen/h;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/fullscreen/h;->tg:Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/fullscreen/h;->tg:Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;

    invoke-static {p0}, Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;->d(Lcom/android/camera/fragment/fullscreen/FragmentFullScreen;)V

    return-void
.end method
