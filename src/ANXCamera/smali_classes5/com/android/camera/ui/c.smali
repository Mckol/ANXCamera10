.class public final synthetic Lcom/android/camera/ui/c;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/ui/FastmotionTextureVideoView;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/FastmotionTextureVideoView;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/c;->tg:Lcom/android/camera/ui/FastmotionTextureVideoView;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/c;->tg:Lcom/android/camera/ui/FastmotionTextureVideoView;

    invoke-virtual {p0}, Lcom/android/camera/ui/FastmotionTextureVideoView;->ma()V

    return-void
.end method
