.class public Lmiui/animation/listener/TransitionListener;
.super Ljava/lang/Object;
.source "TransitionListener.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public constructor <init>(J)V
    .locals 0
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onBegin(Ljava/lang/Object;)V
    .locals 0

    return-void
.end method

.method public onBegin(Ljava/lang/Object;Lmiui/animation/listener/UpdateInfo;)V
    .locals 0

    return-void
.end method

.method public onCancel(Ljava/lang/Object;)V
    .locals 0

    return-void
.end method

.method public onCancel(Ljava/lang/Object;Lmiui/animation/listener/UpdateInfo;)V
    .locals 0

    return-void
.end method

.method public onComplete(Ljava/lang/Object;)V
    .locals 0

    return-void
.end method

.method public onComplete(Ljava/lang/Object;Lmiui/animation/listener/UpdateInfo;)V
    .locals 0

    return-void
.end method

.method public onUpdate(Ljava/lang/Object;Ljava/util/Collection;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Object;",
            "Ljava/util/Collection<",
            "Lmiui/animation/listener/UpdateInfo;",
            ">;)V"
        }
    .end annotation

    return-void
.end method

.method public onUpdate(Ljava/lang/Object;Lmiui/animation/property/FloatProperty;FFZ)V
    .locals 0

    return-void
.end method

.method public onUpdate(Ljava/lang/Object;Lmiui/animation/property/FloatProperty;FZ)V
    .locals 0
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    return-void
.end method

.method public onUpdate(Ljava/lang/Object;Lmiui/animation/property/IIntValueProperty;IFZ)V
    .locals 0

    return-void
.end method
