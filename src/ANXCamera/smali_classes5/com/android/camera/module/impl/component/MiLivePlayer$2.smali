.class Lcom/android/camera/module/impl/component/MiLivePlayer$2;
.super Ljava/lang/Object;
.source "MiLivePlayer.java"

# interfaces
.implements Lcom/xiaomi/recordmediaprocess/EffectNotifier;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/module/impl/component/MiLivePlayer;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/impl/component/MiLivePlayer;


# direct methods
.method constructor <init>(Lcom/android/camera/module/impl/component/MiLivePlayer;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/impl/component/MiLivePlayer$2;->this$0:Lcom/android/camera/module/impl/component/MiLivePlayer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public OnReceiveFailed()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/impl/component/MiLivePlayer$2;->this$0:Lcom/android/camera/module/impl/component/MiLivePlayer;

    invoke-static {v0}, Lcom/android/camera/module/impl/component/MiLivePlayer;->access$000(Lcom/android/camera/module/impl/component/MiLivePlayer;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "Compose notifier OnReceiveFailed"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/impl/component/MiLivePlayer$2;->this$0:Lcom/android/camera/module/impl/component/MiLivePlayer;

    const/4 v1, -0x1

    invoke-static {v0, v1}, Lcom/android/camera/module/impl/component/MiLivePlayer;->access$200(Lcom/android/camera/module/impl/component/MiLivePlayer;I)V

    iget-object p0, p0, Lcom/android/camera/module/impl/component/MiLivePlayer$2;->this$0:Lcom/android/camera/module/impl/component/MiLivePlayer;

    const/4 v0, 0x1

    invoke-static {p0, v0}, Lcom/android/camera/module/impl/component/MiLivePlayer;->access$200(Lcom/android/camera/module/impl/component/MiLivePlayer;I)V

    return-void
.end method

.method public OnReceiveFinish()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/impl/component/MiLivePlayer$2;->this$0:Lcom/android/camera/module/impl/component/MiLivePlayer;

    invoke-static {v0}, Lcom/android/camera/module/impl/component/MiLivePlayer;->access$000(Lcom/android/camera/module/impl/component/MiLivePlayer;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "Compose notifier OnReceiveFinish"

    invoke-static {v0, v1}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object p0, p0, Lcom/android/camera/module/impl/component/MiLivePlayer$2;->this$0:Lcom/android/camera/module/impl/component/MiLivePlayer;

    const/4 v0, 0x3

    invoke-static {p0, v0}, Lcom/android/camera/module/impl/component/MiLivePlayer;->access$200(Lcom/android/camera/module/impl/component/MiLivePlayer;I)V

    return-void
.end method
