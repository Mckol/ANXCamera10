.class public final synthetic Lcom/android/camera/features/gif/p;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/features/gif/GifMediaPlayer;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/gif/GifMediaPlayer;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/gif/p;->tg:Lcom/android/camera/features/gif/GifMediaPlayer;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/features/gif/p;->tg:Lcom/android/camera/features/gif/GifMediaPlayer;

    invoke-virtual {p0}, Lcom/android/camera/features/gif/GifMediaPlayer;->Jc()V

    return-void
.end method
