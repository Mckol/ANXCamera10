.class Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16$1;
.super Ljava/lang/Object;
.source "AnimationHandler.java"

# interfaces
.implements Landroid/view/Choreographer$FrameCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16;-><init>(Lmiui/animation/physics/AnimationHandler$AnimationCallbackDispatcher;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16;


# direct methods
.method constructor <init>(Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16;)V
    .locals 0

    iput-object p1, p0, Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16$1;->this$0:Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public doFrame(J)V
    .locals 0

    iget-object p0, p0, Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16$1;->this$0:Lmiui/animation/physics/AnimationHandler$FrameCallbackProvider16;

    iget-object p0, p0, Lmiui/animation/physics/AnimationHandler$AnimationFrameCallbackProvider;->mDispatcher:Lmiui/animation/physics/AnimationHandler$AnimationCallbackDispatcher;

    invoke-virtual {p0}, Lmiui/animation/physics/AnimationHandler$AnimationCallbackDispatcher;->dispatchAnimationFrame()V

    return-void
.end method
