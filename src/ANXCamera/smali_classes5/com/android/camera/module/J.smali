.class public final synthetic Lcom/android/camera/module/J;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/protocol/ModeProtocol$CloneProcess;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/protocol/ModeProtocol$CloneProcess;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/J;->tg:Lcom/android/camera/protocol/ModeProtocol$CloneProcess;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/J;->tg:Lcom/android/camera/protocol/ModeProtocol$CloneProcess;

    invoke-static {p0}, Lcom/android/camera/module/CloneModule;->a(Lcom/android/camera/protocol/ModeProtocol$CloneProcess;)V

    return-void
.end method
