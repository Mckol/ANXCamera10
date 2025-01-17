.class public Lmiui/animation/internal/AnimRunner;
.super Ljava/lang/Object;
.source "AnimRunner.java"

# interfaces
.implements Lmiui/animation/physics/AnimationHandler$AnimationFrameCallback;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lmiui/animation/internal/AnimRunner$Holder;
    }
.end annotation


# static fields
.field public static final MAX_DELTA:J = 0x10L

.field private static final MAX_RECORD:I = 0x5

.field private static final MSG_START:I

.field private static final sMainHandler:Landroid/os/Handler;


# instance fields
.field private mDeltaRecord:[J

.field private mIsRunning:Z

.field private mLastFrameTime:J

.field private volatile mRatio:F

.field private mRecordCount:I

.field private mRunningTime:Ljava/util/concurrent/atomic/AtomicLong;

.field private mTargetList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lmiui/animation/IAnimTarget;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 2

    new-instance v0, Lmiui/animation/internal/AnimRunner$1;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Lmiui/animation/internal/AnimRunner$1;-><init>(Landroid/os/Looper;)V

    sput-object v0, Lmiui/animation/internal/AnimRunner;->sMainHandler:Landroid/os/Handler;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/util/concurrent/atomic/AtomicLong;

    invoke-direct {v0}, Ljava/util/concurrent/atomic/AtomicLong;-><init>()V

    iput-object v0, p0, Lmiui/animation/internal/AnimRunner;->mRunningTime:Ljava/util/concurrent/atomic/AtomicLong;

    const/high16 v0, 0x3f800000    # 1.0f

    iput v0, p0, Lmiui/animation/internal/AnimRunner;->mRatio:F

    const/4 v0, 0x5

    new-array v0, v0, [J

    fill-array-data v0, :array_0

    iput-object v0, p0, Lmiui/animation/internal/AnimRunner;->mDeltaRecord:[J

    const/4 v0, 0x0

    iput v0, p0, Lmiui/animation/internal/AnimRunner;->mRecordCount:I

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lmiui/animation/internal/AnimRunner;->mTargetList:Ljava/util/List;

    return-void

    nop

    :array_0
    .array-data 8
        0x0
        0x0
        0x0
        0x0
        0x0
    .end array-data
.end method

.method static synthetic access$000()V
    .locals 0

    invoke-static {}, Lmiui/animation/internal/AnimRunner;->startAnimRunner()V

    return-void
.end method

.method static synthetic access$100(Lmiui/animation/internal/AnimRunner;)Ljava/util/concurrent/atomic/AtomicLong;
    .locals 0

    iget-object p0, p0, Lmiui/animation/internal/AnimRunner;->mRunningTime:Ljava/util/concurrent/atomic/AtomicLong;

    return-object p0
.end method

.method static synthetic access$200(Lmiui/animation/internal/AnimRunner;)V
    .locals 0

    invoke-direct {p0}, Lmiui/animation/internal/AnimRunner;->start()V

    return-void
.end method

.method private average([J)J
    .locals 8

    array-length p0, p1

    const/4 v0, 0x0

    const-wide/16 v1, 0x0

    move v3, v0

    move-wide v4, v1

    :goto_0
    if-ge v0, p0, :cond_1

    aget-wide v6, p1, v0

    add-long/2addr v4, v6

    cmp-long v6, v6, v1

    if-lez v6, :cond_0

    add-int/lit8 v3, v3, 0x1

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    if-lez v3, :cond_2

    int-to-long p0, v3

    div-long v1, v4, p0

    :cond_2
    return-wide v1
.end method

.method private cleanOneshotTarget(Lmiui/animation/IAnimTarget;)V
    .locals 2

    invoke-virtual {p1}, Lmiui/animation/IAnimTarget;->getAnimTask()Lmiui/animation/internal/AnimTask;

    move-result-object p0

    const-wide/16 v0, 0x1

    invoke-virtual {p1, v0, v1}, Lmiui/animation/IAnimTarget;->hasFlags(J)Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p0}, Lmiui/animation/internal/AnimTask;->isValid()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Lmiui/animation/internal/AnimTask;->isFinished()Z

    move-result p0

    if-eqz p0, :cond_1

    :cond_0
    const/4 p0, 0x1

    new-array p0, p0, [Lmiui/animation/IAnimTarget;

    const/4 v0, 0x0

    aput-object p1, p0, v0

    invoke-static {p0}, Lmiui/animation/Folme;->clean([Ljava/lang/Object;)V

    :cond_1
    return-void
.end method

.method private endAnimation()Z
    .locals 3

    invoke-direct {p0}, Lmiui/animation/internal/AnimRunner;->isRunning()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_1

    invoke-static {}, Lmiui/animation/utils/LogUtils;->isLogEnabled()Z

    move-result v0

    if-eqz v0, :cond_0

    new-array v0, v1, [Ljava/lang/Object;

    const-string v2, "AnimRunner.endAnimation"

    invoke-static {v2, v0}, Lmiui/animation/utils/LogUtils;->debug(Ljava/lang/String;[Ljava/lang/Object;)V

    :cond_0
    iput-boolean v1, p0, Lmiui/animation/internal/AnimRunner;->mIsRunning:Z

    iget-object v0, p0, Lmiui/animation/internal/AnimRunner;->mRunningTime:Ljava/util/concurrent/atomic/AtomicLong;

    const-wide/16 v1, 0x0

    invoke-virtual {v0, v1, v2}, Ljava/util/concurrent/atomic/AtomicLong;->set(J)V

    iput-wide v1, p0, Lmiui/animation/internal/AnimRunner;->mLastFrameTime:J

    invoke-static {}, Lmiui/animation/physics/AnimationHandler;->getInstance()Lmiui/animation/physics/AnimationHandler;

    move-result-object v0

    invoke-virtual {v0, p0}, Lmiui/animation/physics/AnimationHandler;->removeCallback(Lmiui/animation/physics/AnimationHandler$AnimationFrameCallback;)V

    const/4 p0, 0x1

    return p0

    :cond_1
    return v1
.end method

.method public static getAnimTask(Lmiui/animation/IAnimTarget;JLmiui/animation/controller/AnimState;Lmiui/animation/controller/AnimState;Lmiui/animation/base/AnimConfigLink;)Lmiui/animation/internal/AnimTask;
    .locals 2

    invoke-virtual {p0}, Lmiui/animation/IAnimTarget;->getAnimTask()Lmiui/animation/internal/AnimTask;

    move-result-object v0

    new-instance v1, Lmiui/animation/internal/TransitionInfo;

    invoke-direct {v1, p0, p3, p4, p5}, Lmiui/animation/internal/TransitionInfo;-><init>(Lmiui/animation/IAnimTarget;Lmiui/animation/controller/AnimState;Lmiui/animation/controller/AnimState;Lmiui/animation/base/AnimConfigLink;)V

    invoke-virtual {v0, p1, p2, v1}, Lmiui/animation/internal/AnimTask;->addTransition(JLmiui/animation/internal/TransitionInfo;)V

    return-object v0
.end method

.method public static getInst()Lmiui/animation/internal/AnimRunner;
    .locals 1

    sget-object v0, Lmiui/animation/internal/AnimRunner$Holder;->inst:Lmiui/animation/internal/AnimRunner;

    return-object v0
.end method

.method private isAnimRunning(Lmiui/animation/IAnimTarget;)Z
    .locals 0

    invoke-virtual {p1}, Lmiui/animation/IAnimTarget;->getAnimTask()Lmiui/animation/internal/AnimTask;

    move-result-object p0

    invoke-virtual {p0}, Lmiui/animation/internal/AnimTask;->isFinished()Z

    move-result p0

    xor-int/lit8 p0, p0, 0x1

    return p0
.end method

.method private isRunning()Z
    .locals 2

    iget-object v0, p0, Lmiui/animation/internal/AnimRunner;->mTargetList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lmiui/animation/IAnimTarget;

    invoke-direct {p0, v1}, Lmiui/animation/internal/AnimRunner;->isAnimRunning(Lmiui/animation/IAnimTarget;)Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    :goto_0
    iget-object p0, p0, Lmiui/animation/internal/AnimRunner;->mTargetList:Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->clear()V

    return v0
.end method

.method private varargs runAnim(Lmiui/animation/internal/AnimTask;JJ[J)V
    .locals 0

    invoke-virtual/range {p1 .. p6}, Lmiui/animation/internal/AnimTask;->run(JJ[J)V

    return-void
.end method

.method private start()V
    .locals 1

    iget-boolean p0, p0, Lmiui/animation/internal/AnimRunner;->mIsRunning:Z

    if-eqz p0, :cond_0

    return-void

    :cond_0
    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object p0

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v0

    if-ne p0, v0, :cond_1

    invoke-static {}, Lmiui/animation/internal/AnimRunner;->startAnimRunner()V

    goto :goto_0

    :cond_1
    sget-object p0, Lmiui/animation/internal/AnimRunner;->sMainHandler:Landroid/os/Handler;

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    :goto_0
    return-void
.end method

.method private static startAnimRunner()V
    .locals 4

    invoke-static {}, Lmiui/animation/internal/AnimRunner;->getInst()Lmiui/animation/internal/AnimRunner;

    move-result-object v0

    iget-boolean v1, v0, Lmiui/animation/internal/AnimRunner;->mIsRunning:Z

    if-eqz v1, :cond_0

    return-void

    :cond_0
    invoke-static {}, Lmiui/animation/utils/LogUtils;->isLogEnabled()Z

    move-result v1

    if-eqz v1, :cond_1

    const/4 v1, 0x0

    new-array v1, v1, [Ljava/lang/Object;

    const-string v2, "AnimRunner.start"

    invoke-static {v2, v1}, Lmiui/animation/utils/LogUtils;->debug(Ljava/lang/String;[Ljava/lang/Object;)V

    :cond_1
    const/4 v1, 0x1

    iput-boolean v1, v0, Lmiui/animation/internal/AnimRunner;->mIsRunning:Z

    invoke-static {}, Lmiui/animation/physics/AnimationHandler;->getInstance()Lmiui/animation/physics/AnimationHandler;

    move-result-object v1

    const-wide/16 v2, 0x0

    invoke-virtual {v1, v0, v2, v3}, Lmiui/animation/physics/AnimationHandler;->addAnimationFrameCallback(Lmiui/animation/physics/AnimationHandler$AnimationFrameCallback;J)V

    return-void
.end method

.method private updateRunningTime(J)J
    .locals 5

    iget-wide v0, p0, Lmiui/animation/internal/AnimRunner;->mLastFrameTime:J

    const-wide/16 v2, 0x0

    cmp-long v4, v0, v2

    if-nez v4, :cond_0

    iput-wide p1, p0, Lmiui/animation/internal/AnimRunner;->mLastFrameTime:J

    goto :goto_0

    :cond_0
    sub-long v2, p1, v0

    iput-wide p1, p0, Lmiui/animation/internal/AnimRunner;->mLastFrameTime:J

    :goto_0
    iget p1, p0, Lmiui/animation/internal/AnimRunner;->mRecordCount:I

    rem-int/lit8 p2, p1, 0x5

    iget-object v0, p0, Lmiui/animation/internal/AnimRunner;->mDeltaRecord:[J

    aput-wide v2, v0, p2

    add-int/lit8 p1, p1, 0x1

    iput p1, p0, Lmiui/animation/internal/AnimRunner;->mRecordCount:I

    invoke-virtual {p0, v2, v3}, Lmiui/animation/internal/AnimRunner;->getAverageDelta(J)J

    move-result-wide p1

    iget-object p0, p0, Lmiui/animation/internal/AnimRunner;->mRunningTime:Ljava/util/concurrent/atomic/AtomicLong;

    invoke-virtual {p0, p1, p2}, Ljava/util/concurrent/atomic/AtomicLong;->addAndGet(J)J

    return-wide p1
.end method


# virtual methods
.method public varargs cancel(Lmiui/animation/IAnimTarget;[Lmiui/animation/property/FloatProperty;)V
    .locals 1

    new-instance v0, Lmiui/animation/internal/AnimRunner$2;

    invoke-direct {v0, p0, p1, p2}, Lmiui/animation/internal/AnimRunner$2;-><init>(Lmiui/animation/internal/AnimRunner;Lmiui/animation/IAnimTarget;[Lmiui/animation/property/FloatProperty;)V

    invoke-virtual {p1, v0}, Lmiui/animation/IAnimTarget;->post(Ljava/lang/Runnable;)V

    return-void
.end method

.method doAnimOnTarget(Lmiui/animation/IAnimTarget;JJ)V
    .locals 8

    invoke-virtual {p1}, Lmiui/animation/IAnimTarget;->getAnimTask()Lmiui/animation/internal/AnimTask;

    move-result-object v7

    const/4 v0, 0x0

    new-array v6, v0, [J

    move-object v0, p0

    move-object v1, v7

    move-wide v2, p2

    move-wide v4, p4

    invoke-direct/range {v0 .. v6}, Lmiui/animation/internal/AnimRunner;->runAnim(Lmiui/animation/internal/AnimTask;JJ[J)V

    invoke-virtual {v7}, Lmiui/animation/internal/AnimTask;->isFinished()Z

    move-result p2

    invoke-virtual {p1, p2}, Lmiui/animation/IAnimTarget;->onFrameEnd(Z)V

    invoke-direct {p0, p1}, Lmiui/animation/internal/AnimRunner;->cleanOneshotTarget(Lmiui/animation/IAnimTarget;)V

    return-void
.end method

.method public doAnimationFrame(J)Z
    .locals 5

    invoke-direct {p0, p1, p2}, Lmiui/animation/internal/AnimRunner;->updateRunningTime(J)J

    move-result-wide p1

    iget-object v0, p0, Lmiui/animation/internal/AnimRunner;->mRunningTime:Ljava/util/concurrent/atomic/AtomicLong;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v0

    iget-object v2, p0, Lmiui/animation/internal/AnimRunner;->mTargetList:Ljava/util/List;

    invoke-static {v2}, Lmiui/animation/Folme;->getTargets(Ljava/util/Collection;)V

    iget-object v2, p0, Lmiui/animation/internal/AnimRunner;->mTargetList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lmiui/animation/IAnimTarget;

    invoke-virtual {v3}, Lmiui/animation/IAnimTarget;->allowAnimRun()Z

    move-result v4

    if-eqz v4, :cond_0

    invoke-virtual {v3}, Lmiui/animation/IAnimTarget;->getAnimTask()Lmiui/animation/internal/AnimTask;

    move-result-object v4

    invoke-virtual {v4}, Lmiui/animation/internal/AnimTask;->isValid()Z

    move-result v4

    if-eqz v4, :cond_0

    invoke-virtual {v3, v0, v1, p1, p2}, Lmiui/animation/IAnimTarget;->executeAnim(JJ)V

    :cond_0
    invoke-direct {p0, v3}, Lmiui/animation/internal/AnimRunner;->cleanOneshotTarget(Lmiui/animation/IAnimTarget;)V

    goto :goto_0

    :cond_1
    invoke-direct {p0}, Lmiui/animation/internal/AnimRunner;->endAnimation()Z

    move-result p0

    return p0
.end method

.method public varargs end(Lmiui/animation/IAnimTarget;[Lmiui/animation/property/FloatProperty;)V
    .locals 1

    new-instance v0, Lmiui/animation/internal/AnimRunner$3;

    invoke-direct {v0, p0, p1, p2}, Lmiui/animation/internal/AnimRunner$3;-><init>(Lmiui/animation/internal/AnimRunner;Lmiui/animation/IAnimTarget;[Lmiui/animation/property/FloatProperty;)V

    invoke-virtual {p1, v0}, Lmiui/animation/IAnimTarget;->post(Ljava/lang/Runnable;)V

    return-void
.end method

.method public getAverageDelta(J)J
    .locals 4

    iget-object v0, p0, Lmiui/animation/internal/AnimRunner;->mDeltaRecord:[J

    invoke-direct {p0, v0}, Lmiui/animation/internal/AnimRunner;->average([J)J

    move-result-wide v0

    const-wide/16 v2, 0x0

    cmp-long v2, v0, v2

    if-lez v2, :cond_0

    move-wide p1, v0

    :cond_0
    const-wide/16 v0, 0x10

    cmp-long v2, p1, v0

    if-lez v2, :cond_1

    move-wide p1, v0

    :cond_1
    long-to-float p1, p1

    iget p0, p0, Lmiui/animation/internal/AnimRunner;->mRatio:F

    div-float/2addr p1, p0

    float-to-double p0, p1

    invoke-static {p0, p1}, Ljava/lang/Math;->ceil(D)D

    move-result-wide p0

    double-to-long p0, p0

    return-wide p0
.end method

.method public getRunningTime()J
    .locals 2

    iget-object p0, p0, Lmiui/animation/internal/AnimRunner;->mRunningTime:Ljava/util/concurrent/atomic/AtomicLong;

    invoke-virtual {p0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v0

    return-wide v0
.end method

.method public run(Lmiui/animation/IAnimTarget;Lmiui/animation/controller/AnimState;Lmiui/animation/controller/AnimState;Lmiui/animation/base/AnimConfigLink;)V
    .locals 7

    new-instance v6, Lmiui/animation/internal/AnimRunner$4;

    move-object v0, v6

    move-object v1, p0

    move-object v2, p1

    move-object v3, p2

    move-object v4, p3

    move-object v5, p4

    invoke-direct/range {v0 .. v5}, Lmiui/animation/internal/AnimRunner$4;-><init>(Lmiui/animation/internal/AnimRunner;Lmiui/animation/IAnimTarget;Lmiui/animation/controller/AnimState;Lmiui/animation/controller/AnimState;Lmiui/animation/base/AnimConfigLink;)V

    invoke-virtual {p1, v6}, Lmiui/animation/IAnimTarget;->executeOnInitialized(Ljava/lang/Runnable;)V

    return-void
.end method

.method public setTimeRatio(F)V
    .locals 0

    iput p1, p0, Lmiui/animation/internal/AnimRunner;->mRatio:F

    return-void
.end method
