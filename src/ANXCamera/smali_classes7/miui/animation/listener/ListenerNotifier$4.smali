.class Lmiui/animation/listener/ListenerNotifier$4;
.super Lmiui/animation/listener/ListenerNotifier$BaseNotifier;
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

    invoke-direct {p0, v0}, Lmiui/animation/listener/ListenerNotifier$BaseNotifier;-><init>(Lmiui/animation/listener/ListenerNotifier$1;)V

    return-void
.end method


# virtual methods
.method doNotify(Lmiui/animation/listener/ListenerNotifier$ListenerNode;Ljava/lang/Object;Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lmiui/animation/listener/ListenerNotifier$ListenerNode;",
            "Ljava/lang/Object;",
            "Ljava/util/List<",
            "Lmiui/animation/listener/UpdateInfo;",
            ">;)V"
        }
    .end annotation

    iget-object p0, p1, Lmiui/animation/listener/ListenerNotifier$ListenerNode;->listener:Lmiui/animation/listener/TransitionListener;

    invoke-virtual {p0, p2, p3}, Lmiui/animation/listener/TransitionListener;->onUpdate(Ljava/lang/Object;Ljava/util/Collection;)V

    return-void
.end method
