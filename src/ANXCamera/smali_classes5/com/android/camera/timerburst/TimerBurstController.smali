.class public Lcom/android/camera/timerburst/TimerBurstController;
.super Ljava/lang/Object;
.source "TimerBurstController.java"

# interfaces
.implements Lcom/android/camera/timerburst/CustomSeekBar$AddSeekBarValueListener;


# static fields
.field public static final SHOT_COUNT_ENLARGE_TIMES:I = 0xa

.field public static final TIME_INTERVAL_ENLARGE_TIMES:I = 0x1

.field public static final shotCount:[I

.field public static final timeInterval:[I


# instance fields
.field private volatile isDecreasedCount:Z

.field private volatile isInTimerBurstShotting:Z

.field private mJpegRotation:I

.field private mOrientation:I

.field private mPictureName:Ljava/lang/String;

.field mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

.field private volatile pendingStopTimerBurst:Z


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const/4 v0, 0x2

    new-array v1, v0, [I

    fill-array-data v1, :array_0

    sput-object v1, Lcom/android/camera/timerburst/TimerBurstController;->shotCount:[I

    new-array v0, v0, [I

    fill-array-data v0, :array_1

    sput-object v0, Lcom/android/camera/timerburst/TimerBurstController;->timeInterval:[I

    return-void

    :array_0
    .array-data 4
        0x1
        0x3c
    .end array-data

    :array_1
    .array-data 4
        0x1
        0x3c
    .end array-data
.end method

.method public constructor <init>()V
    .locals 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->isDecreasedCount:Z

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mJpegRotation:I

    iput v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mOrientation:I

    new-instance v0, Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-direct {v0}, Lcom/android/camera/timerburst/TimerBurstBean;-><init>()V

    iput-object v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v0

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstInterval()I

    move-result v1

    iget-object v2, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-virtual {v2, v0}, Lcom/android/camera/timerburst/TimerBurstBean;->setTotalCount(I)V

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    int-to-long v0, v1

    invoke-virtual {p0, v0, v1}, Lcom/android/camera/timerburst/TimerBurstBean;->setIntervalTimer(J)V

    return-void
.end method

.method public static isSupportTimerBurst(I)Z
    .locals 1

    const/16 v0, 0xa3

    if-eq p0, v0, :cond_0

    const/16 v0, 0xa7

    if-eq p0, v0, :cond_0

    const/16 v0, 0xa5

    if-eq p0, v0, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    const/4 p0, 0x1

    return p0
.end method

.method private resetTimerConfig()V
    .locals 2

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->isDecreasedCount:Z

    iput-boolean v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting:Z

    iget-object v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/android/camera/timerburst/TimerBurstBean;->setTotalCount(I)V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mPictureName:Ljava/lang/String;

    const/4 v0, -0x1

    iput v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mJpegRotation:I

    iput v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mOrientation:I

    return-void
.end method


# virtual methods
.method public currentSeekBarValue(Landroid/view/View;II)V
    .locals 0

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    const/4 p2, 0x0

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    sget-object p1, Lcom/android/camera/timerburst/TimerBurstController;->timeInterval:[I

    aget p2, p1, p2

    if-gt p2, p3, :cond_0

    array-length p2, p1

    add-int/lit8 p2, p2, -0x1

    aget p1, p1, p2

    if-gt p3, p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    int-to-long p1, p3

    invoke-virtual {p0, p1, p2}, Lcom/android/camera/timerburst/TimerBurstBean;->setIntervalTimer(J)V

    invoke-static {p3}, Lcom/android/camera/CameraSettings;->setTimerBurstInterval(I)V

    goto :goto_0

    :pswitch_1
    sget-object p1, Lcom/android/camera/timerburst/TimerBurstController;->shotCount:[I

    aget p2, p1, p2

    if-gt p2, p3, :cond_0

    array-length p2, p1

    add-int/lit8 p2, p2, -0x1

    aget p1, p1, p2

    if-gt p3, p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    mul-int/lit8 p1, p3, 0xa

    invoke-virtual {p0, p1}, Lcom/android/camera/timerburst/TimerBurstBean;->setTotalCount(I)V

    invoke-static {p3}, Lcom/android/camera/CameraSettings;->setTimerBurstTotalCount(I)V

    :cond_0
    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x7f0900b7
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public decreaseCount(I)V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    const/4 v1, 0x1

    sub-int/2addr p1, v1

    invoke-virtual {v0, p1}, Lcom/android/camera/timerburst/TimerBurstBean;->setTotalCount(I)V

    iput-boolean v1, p0, Lcom/android/camera/timerburst/TimerBurstController;->isDecreasedCount:Z

    return-void
.end method

.method public getCaptureIndex()I
    .locals 1

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v0

    invoke-virtual {p0}, Lcom/android/camera/timerburst/TimerBurstController;->getTotalCount()I

    move-result p0

    sub-int/2addr v0, p0

    add-int/lit8 v0, v0, 0x1

    return v0
.end method

.method public getIntervalTimer()J
    .locals 2

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-virtual {p0}, Lcom/android/camera/timerburst/TimerBurstBean;->getIntervalTimer()J

    move-result-wide v0

    return-wide v0
.end method

.method public getIsDecreasedCount()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->isDecreasedCount:Z

    return p0
.end method

.method public getJpegRotation(I)I
    .locals 2

    iget v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mJpegRotation:I

    const/4 v1, -0x1

    if-ne v0, v1, :cond_0

    iput p1, p0, Lcom/android/camera/timerburst/TimerBurstController;->mJpegRotation:I

    :cond_0
    iget p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mJpegRotation:I

    return p0
.end method

.method public getOrientation(ZI)I
    .locals 1

    if-nez p1, :cond_0

    return p2

    :cond_0
    iget p1, p0, Lcom/android/camera/timerburst/TimerBurstController;->mOrientation:I

    const/4 v0, -0x1

    if-ne p1, v0, :cond_1

    iput p2, p0, Lcom/android/camera/timerburst/TimerBurstController;->mOrientation:I

    :cond_1
    iget p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mOrientation:I

    return p0
.end method

.method public getPictureTitle(Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mPictureName:Ljava/lang/String;

    if-nez v0, :cond_0

    iput-object p1, p0, Lcom/android/camera/timerburst/TimerBurstController;->mPictureName:Ljava/lang/String;

    :cond_0
    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mPictureName:Ljava/lang/String;

    return-object p0
.end method

.method public getTimerBurstBean()Lcom/android/camera/timerburst/TimerBurstBean;
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    return-object p0
.end method

.method public getTotalCount()I
    .locals 0

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-virtual {p0}, Lcom/android/camera/timerburst/TimerBurstBean;->getTotalCount()I

    move-result p0

    return p0
.end method

.method public isInTimerBurstShotting()Z
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting:Z

    if-eqz v0, :cond_0

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-virtual {p0}, Lcom/android/camera/timerburst/TimerBurstBean;->getTotalCount()I

    move-result p0

    if-lez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public isPendingStopTimerBurst()Z
    .locals 0

    iget-boolean p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->pendingStopTimerBurst:Z

    return p0
.end method

.method public muteTimerConfig()V
    .locals 1

    const/4 v0, 0x0

    invoke-static {v0}, Lcom/android/camera/CameraSettings;->setTimerBurstEnable(Z)V

    invoke-virtual {p0}, Lcom/android/camera/timerburst/TimerBurstController;->resetTimerRunningData()V

    const/4 p0, 0x3

    invoke-static {p0}, Lcom/android/camera/CameraSettings;->setTimerBurstTotalCount(I)V

    const/4 p0, 0x5

    invoke-static {p0}, Lcom/android/camera/CameraSettings;->setTimerBurstInterval(I)V

    return-void
.end method

.method public resetTimerRunningData()V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/timerburst/TimerBurstController;->mTimerBurstBeanSetting:Lcom/android/camera/timerburst/TimerBurstBean;

    invoke-static {}, Lcom/android/camera/CameraSettings;->getTimerBurstTotalCount()I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/android/camera/timerburst/TimerBurstBean;->setTotalCount(I)V

    return-void
.end method

.method public setInTimerBurstShotting(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting:Z

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/android/camera/timerburst/TimerBurstController;->pendingStopTimerBurst:Z

    iget-boolean p1, p0, Lcom/android/camera/timerburst/TimerBurstController;->isInTimerBurstShotting:Z

    if-nez p1, :cond_0

    invoke-direct {p0}, Lcom/android/camera/timerburst/TimerBurstController;->resetTimerConfig()V

    :cond_0
    return-void
.end method

.method public setPendingStopTimerBurst(Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/android/camera/timerburst/TimerBurstController;->pendingStopTimerBurst:Z

    return-void
.end method
