.class public final synthetic Lcom/android/camera/ui/f;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/ui/ModeSelectView;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/ModeSelectView;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/f;->tg:Lcom/android/camera/ui/ModeSelectView;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/f;->tg:Lcom/android/camera/ui/ModeSelectView;

    invoke-virtual {p0}, Lcom/android/camera/ui/ModeSelectView;->oa()V

    return-void
.end method
