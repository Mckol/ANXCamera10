.class public final synthetic Lcom/android/camera/module/F;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/CloneModule;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/CloneModule;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/F;->tg:Lcom/android/camera/module/CloneModule;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/module/F;->tg:Lcom/android/camera/module/CloneModule;

    invoke-virtual {p0}, Lcom/android/camera/module/CloneModule;->yd()V

    return-void
.end method