.class Lcom/android/camera/fragment/FragmentBeauty$3;
.super Ljava/lang/Object;
.source "FragmentBeauty.java"

# interfaces
.implements Lcom/android/camera/ui/SeekBarCompat$OnSeekBarCompatChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/fragment/FragmentBeauty;->initAdjustSeekBar()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/fragment/FragmentBeauty;


# direct methods
.method constructor <init>(Lcom/android/camera/fragment/FragmentBeauty;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/fragment/FragmentBeauty$3;->this$0:Lcom/android/camera/fragment/FragmentBeauty;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onProgressChanged(Landroid/widget/SeekBar;IZ)V
    .locals 1

    invoke-static {}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getInstance()Lcom/android/camera/protocol/ModeCoordinatorImpl;

    move-result-object p1

    const/16 v0, 0xac

    invoke-virtual {p1, v0}, Lcom/android/camera/protocol/ModeCoordinatorImpl;->getAttachProtocol(I)Lcom/android/camera/protocol/ModeProtocol$BaseProtocol;

    move-result-object p1

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    const/4 v0, 0x1

    if-eqz p1, :cond_1

    if-eqz p3, :cond_1

    iget-object p3, p0, Lcom/android/camera/fragment/FragmentBeauty$3;->this$0:Lcom/android/camera/fragment/FragmentBeauty;

    invoke-static {p3}, Lcom/android/camera/fragment/FragmentBeauty;->access$100(Lcom/android/camera/fragment/FragmentBeauty;)Lcom/android/camera/ui/SeekBarCompat;

    move-result-object p3

    invoke-virtual {p3}, Lcom/android/camera/ui/SeekBarCompat;->isCenterTwoWayMode()Z

    move-result p3

    if-eqz p3, :cond_0

    div-int/lit8 p3, p2, 0x2

    goto :goto_0

    :cond_0
    move p3, p2

    :goto_0
    div-int/2addr p3, v0

    invoke-static {p3}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object p3

    invoke-interface {p1, v0, p3}, Lcom/android/camera/protocol/ModeProtocol$TopAlert;->alertUpdateValue(ILjava/lang/String;)V

    :cond_1
    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemRunning()Lcom/android/camera/data/data/runing/DataItemRunning;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/DataItemRunning;->getComponentRunningShine()Lcom/android/camera/data/data/runing/ComponentRunningShine;

    move-result-object p1

    invoke-virtual {p1}, Lcom/android/camera/data/data/runing/ComponentRunningShine;->getCurrentType()Ljava/lang/String;

    move-result-object p1

    const-string p3, "14"

    invoke-virtual {p3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_2

    const/4 p1, 0x0

    goto :goto_1

    :cond_2
    const/4 p1, 0x5

    :goto_1
    if-eqz p2, :cond_3

    iget-object p3, p0, Lcom/android/camera/fragment/FragmentBeauty$3;->this$0:Lcom/android/camera/fragment/FragmentBeauty;

    invoke-static {p3}, Lcom/android/camera/fragment/FragmentBeauty;->access$200(Lcom/android/camera/fragment/FragmentBeauty;)I

    move-result p3

    if-eq p2, p3, :cond_3

    iget-object p3, p0, Lcom/android/camera/fragment/FragmentBeauty$3;->this$0:Lcom/android/camera/fragment/FragmentBeauty;

    invoke-static {p3}, Lcom/android/camera/fragment/FragmentBeauty;->access$300(Lcom/android/camera/fragment/FragmentBeauty;)I

    move-result p3

    sub-int p3, p2, p3

    invoke-static {p3}, Ljava/lang/Math;->abs(I)I

    move-result p3

    mul-int/2addr p1, v0

    if-le p3, p1, :cond_4

    :cond_3
    iget-object p1, p0, Lcom/android/camera/fragment/FragmentBeauty$3;->this$0:Lcom/android/camera/fragment/FragmentBeauty;

    invoke-static {p1, p2}, Lcom/android/camera/fragment/FragmentBeauty;->access$302(Lcom/android/camera/fragment/FragmentBeauty;I)I

    iget-object p0, p0, Lcom/android/camera/fragment/FragmentBeauty$3;->this$0:Lcom/android/camera/fragment/FragmentBeauty;

    invoke-static {p0}, Lcom/android/camera/fragment/FragmentBeauty;->access$000(Lcom/android/camera/fragment/FragmentBeauty;)Lio/reactivex/FlowableEmitter;

    move-result-object p0

    div-int/2addr p2, v0

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-interface {p0, p1}, Lio/reactivex/Emitter;->onNext(Ljava/lang/Object;)V

    :cond_4
    return-void
.end method

.method public onStartTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 0

    return-void
.end method

.method public onStopTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 0

    return-void
.end method
