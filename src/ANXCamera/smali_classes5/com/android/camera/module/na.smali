.class public final synthetic Lcom/android/camera/module/na;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/VideoBase;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/VideoBase;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/na;->tg:Lcom/android/camera/module/VideoBase;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/na;->tg:Lcom/android/camera/module/VideoBase;

    invoke-virtual {p0}, Lcom/android/camera/module/VideoBase;->Hd()V

    return-void
.end method
