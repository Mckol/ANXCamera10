.class public final synthetic Lcom/android/camera/module/W;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

.field private final synthetic ug:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/W;->tg:Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    iput-boolean p2, p0, Lcom/android/camera/module/W;->ug:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/W;->tg:Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    iget-boolean p0, p0, Lcom/android/camera/module/W;->ug:Z

    invoke-static {v0, p0}, Lcom/android/camera/module/DualVideoModule;->b(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Z)V

    return-void
.end method
