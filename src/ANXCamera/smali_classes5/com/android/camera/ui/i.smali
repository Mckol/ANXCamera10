.class public final synthetic Lcom/android/camera/ui/i;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/ui/ScreenHint;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/ui/ScreenHint;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/ui/i;->tg:Lcom/android/camera/ui/ScreenHint;

    return-void
.end method


# virtual methods
.method public final onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/ui/i;->tg:Lcom/android/camera/ui/ScreenHint;

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/ui/ScreenHint;->i(Landroid/content/DialogInterface;I)V

    return-void
.end method
