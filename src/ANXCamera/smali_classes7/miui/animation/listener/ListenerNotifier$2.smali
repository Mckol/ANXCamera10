.class Lmiui/animation/listener/ListenerNotifier$2;
.super Lmiui/animation/listener/ListenerNotifier$SingleNotifier;
.source "ListenerNotifier.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lmiui/animation/listener/ListenerNotifier;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lmiui/animation/listener/ListenerNotifier$SingleNotifier;-><init>(Lmiui/animation/listener/ListenerNotifier$1;)V

    return-void
.end method


# virtual methods
.method doNotify(Lmiui/animation/listener/ListenerNotifier$ListenerNode;Ljava/lang/Object;Lmiui/animation/listener/UpdateInfo;)V
    .locals 0

    iget-object p0, p1, Lmiui/animation/listener/ListenerNotifier$ListenerNode;->listener:Lmiui/animation/listener/TransitionListener;

    invoke-virtual {p0, p2, p3}, Lmiui/animation/listener/TransitionListener;->onBegin(Ljava/lang/Object;Lmiui/animation/listener/UpdateInfo;)V

    return-void
.end method
