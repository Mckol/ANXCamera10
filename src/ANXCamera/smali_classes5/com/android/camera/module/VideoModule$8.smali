.class Lcom/android/camera/module/VideoModule$8;
.super Ljava/lang/Object;
.source "VideoModule.java"

# interfaces
.implements Lio/reactivex/SingleOnSubscribe;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/camera/module/VideoModule;->stopRecorder()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lio/reactivex/SingleOnSubscribe<",
        "Ljava/lang/Boolean;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/VideoModule;


# direct methods
.method constructor <init>(Lcom/android/camera/module/VideoModule;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public subscribe(Lio/reactivex/SingleEmitter;)V
    .locals 11
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lio/reactivex/SingleEmitter<",
            "Ljava/lang/Boolean;",
            ">;)V"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    iget-object v0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    new-instance v1, Ljava/util/concurrent/CountDownLatch;

    const/4 v2, 0x1

    invoke-direct {v1, v2}, Ljava/util/concurrent/CountDownLatch;-><init>(I)V

    invoke-static {v0, v1}, Lcom/android/camera/module/VideoModule;->access$402(Lcom/android/camera/module/VideoModule;Ljava/util/concurrent/CountDownLatch;)Ljava/util/concurrent/CountDownLatch;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-object v2, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v2}, Lcom/android/camera/module/VideoModule;->access$500(Lcom/android/camera/module/VideoModule;)Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-virtual {v3}, Lcom/android/camera/module/BaseModule;->isFrontCamera()Z

    move-result v3

    invoke-static {v2, v3}, Lcom/android/camera/statistic/ScenarioTrackUtil;->trackStopVideoRecordStart(Ljava/lang/String;Z)V

    const/4 v2, 0x0

    :try_start_0
    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$600(Lcom/android/camera/module/VideoModule;)Landroid/media/MediaRecorder;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/media/MediaRecorder;->setOnErrorListener(Landroid/media/MediaRecorder$OnErrorListener;)V

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$600(Lcom/android/camera/module/VideoModule;)Landroid/media/MediaRecorder;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/media/MediaRecorder;->setOnInfoListener(Landroid/media/MediaRecorder$OnInfoListener;)V

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$600(Lcom/android/camera/module/VideoModule;)Landroid/media/MediaRecorder;

    move-result-object v3

    invoke-virtual {v3}, Landroid/media/MediaRecorder;->stop()V
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v3

    sget-object v4, Lcom/android/camera/module/VideoBase;->TAG:Ljava/lang/String;

    const-string v5, "failed to stop media recorder"

    invoke-static {v4, v5, v3}, Lcom/android/camera/log/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v4, v3, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    if-eqz v4, :cond_0

    invoke-virtual {v3, v4}, Lcom/android/camera/module/VideoBase;->deleteVideoFile(Ljava/lang/String;)V

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iput-object v2, v3, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    :cond_0
    :goto_0
    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-boolean v3, v3, Lcom/android/camera/module/BaseModule;->mPaused:Z

    if-nez v3, :cond_1

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v3, v3, Lcom/android/camera/module/BaseModule;->mActivity:Lcom/android/camera/Camera;

    invoke-virtual {v3}, Lcom/android/camera/ActivityBase;->isActivityPaused()Z

    move-result v3

    if-nez v3, :cond_1

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    const/4 v4, 0x3

    invoke-virtual {v3, v4}, Lcom/android/camera/module/BaseModule;->playCameraSound(I)V

    :cond_1
    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$700(Lcom/android/camera/module/VideoModule;)V

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$400(Lcom/android/camera/module/VideoModule;)Ljava/util/concurrent/CountDownLatch;

    move-result-object v3

    invoke-virtual {v3}, Ljava/util/concurrent/CountDownLatch;->countDown()V

    sget-object v3, Lcom/android/camera/module/VideoBase;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "releaseTime="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v5

    sub-long/2addr v5, v0

    invoke-virtual {v4, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v0

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-wide v4, v3, Lcom/android/camera/module/VideoBase;->mRecordingStartTime:J

    sub-long/2addr v0, v4

    iget-object v3, v3, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v3, :cond_4

    invoke-static {}, Lcom/android/camera/module/ModuleManager;->isVideoNewSlowMotion()Z

    move-result v3

    if-eqz v3, :cond_4

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$800(Lcom/android/camera/module/VideoModule;)Z

    move-result v3

    if-nez v3, :cond_2

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$900(Lcom/android/camera/module/VideoModule;)Z

    move-result v3

    if-eqz v3, :cond_4

    :cond_2
    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$800(Lcom/android/camera/module/VideoModule;)Z

    move-result v3

    if-eqz v3, :cond_3

    const-string v3, "fps120"

    goto :goto_1

    :cond_3
    const-string v3, "fps240"

    :goto_1
    move-object v4, v3

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$1000(Lcom/android/camera/module/VideoModule;)I

    move-result v5

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v3, v3, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v3}, Lcom/android/camera2/Camera2Proxy;->getFlashMode()I

    move-result v6

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$1100(Lcom/android/camera/module/VideoModule;)I

    move-result v7

    const-wide/16 v8, 0x3e8

    div-long v8, v0, v8

    const/4 v10, 0x0

    invoke-static/range {v4 .. v10}, Lcom/android/camera/statistic/CameraStatUtils;->trackNewSlowMotionVideoRecorded(Ljava/lang/String;IIIJZ)V

    :cond_4
    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v4, v3, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    if-eqz v4, :cond_8

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$1200(Lcom/android/camera/module/VideoModule;)Z

    move-result v3

    if-eqz v3, :cond_8

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$1300(Lcom/android/camera/module/VideoModule;)Z

    move-result v3

    const-string v4, "attr_feature_name"

    const-string v5, "key_video_960"

    if-eqz v3, :cond_7

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-static {v3}, Lcom/android/camera/module/VideoModule;->access$1400(Lcom/android/camera/module/VideoModule;)Z

    move-result v3

    if-nez v3, :cond_5

    goto :goto_2

    :cond_5
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-object v3, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v6, v3, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    invoke-static {v3, v6}, Lcom/android/camera/module/VideoModule;->access$1500(Lcom/android/camera/module/VideoModule;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    sget-object v6, Lcom/android/camera/module/VideoBase;->TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "processTime="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v8

    sub-long/2addr v8, v0

    invoke-virtual {v7, v8, v9}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v6, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-nez v3, :cond_6

    iget-object p0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iput-object v2, p0, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    iput-object v2, p0, Lcom/android/camera/module/VideoBase;->mCurrentVideoValues:Landroid/content/ContentValues;

    const-string p0, "fps_960_process_failed"

    invoke-static {v5, v4, p0}, Lcom/android/camera/statistic/MistatsWrapper;->keyTriggerEvent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V

    goto :goto_3

    :cond_6
    iget-object v0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iput-object v3, v0, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    iget-object v0, v0, Lcom/android/camera/module/VideoBase;->mCurrentVideoValues:Landroid/content/ContentValues;

    const-string v1, "_data"

    invoke-virtual {v0, v1, v3}, Landroid/content/ContentValues;->put(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v1, v0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    if-eqz v1, :cond_8

    invoke-static {v0}, Lcom/android/camera/module/VideoModule;->access$1000(Lcom/android/camera/module/VideoModule;)I

    move-result v3

    iget-object v0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v0, v0, Lcom/android/camera/module/BaseModule;->mCamera2Device:Lcom/android/camera2/Camera2Proxy;

    invoke-virtual {v0}, Lcom/android/camera2/Camera2Proxy;->getFlashMode()I

    move-result v4

    const/16 v5, 0x3c0

    const-wide/16 v6, 0xa

    iget-object p0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-virtual {p0}, Lcom/android/camera/module/BaseModule;->getModuleIndex()I

    move-result p0

    invoke-static {p0}, Lcom/android/camera/CameraSettings;->is960WatermarkOn(I)Z

    move-result v8

    const-string v2, "fps960"

    invoke-static/range {v2 .. v8}, Lcom/android/camera/statistic/CameraStatUtils;->trackNewSlowMotionVideoRecorded(Ljava/lang/String;IIIJZ)V

    goto :goto_3

    :cond_7
    :goto_2
    sget-object v3, Lcom/android/camera/module/VideoBase;->TAG:Ljava/lang/String;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "uncomplete video.="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Lcom/android/camera/log/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iget-object v1, v0, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/android/camera/module/VideoBase;->deleteVideoFile(Ljava/lang/String;)V

    iget-object p0, p0, Lcom/android/camera/module/VideoModule$8;->this$0:Lcom/android/camera/module/VideoModule;

    iput-object v2, p0, Lcom/android/camera/module/VideoBase;->mCurrentVideoFilename:Ljava/lang/String;

    const-string p0, "fps_960_too_short"

    invoke-static {v5, v4, p0}, Lcom/android/camera/statistic/MistatsWrapper;->keyTriggerEvent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V

    :cond_8
    :goto_3
    sget-object p0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    invoke-interface {p1, p0}, Lio/reactivex/SingleEmitter;->onSuccess(Ljava/lang/Object;)V

    return-void
.end method
