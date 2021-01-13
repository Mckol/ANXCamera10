.class Lcom/android/camera/module/VideoModule$AudioManagerAudioDeviceCallback;
.super Landroid/media/AudioDeviceCallback;
.source "VideoModule.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/module/VideoModule;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "AudioManagerAudioDeviceCallback"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/camera/module/VideoModule;


# direct methods
.method private constructor <init>(Lcom/android/camera/module/VideoModule;)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/module/VideoModule$AudioManagerAudioDeviceCallback;->this$0:Lcom/android/camera/module/VideoModule;

    invoke-direct {p0}, Landroid/media/AudioDeviceCallback;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/android/camera/module/VideoModule;Lcom/android/camera/module/VideoModule$1;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/module/VideoModule$AudioManagerAudioDeviceCallback;-><init>(Lcom/android/camera/module/VideoModule;)V

    return-void
.end method


# virtual methods
.method public onAudioDevicesAdded([Landroid/media/AudioDeviceInfo;)V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/module/VideoModule$AudioManagerAudioDeviceCallback;->this$0:Lcom/android/camera/module/VideoModule;

    const/4 v0, 0x1

    invoke-static {p0, p1, v0}, Lcom/android/camera/module/VideoModule;->access$1900(Lcom/android/camera/module/VideoModule;[Landroid/media/AudioDeviceInfo;Z)V

    return-void
.end method

.method public onAudioDevicesRemoved([Landroid/media/AudioDeviceInfo;)V
    .locals 1

    iget-object p0, p0, Lcom/android/camera/module/VideoModule$AudioManagerAudioDeviceCallback;->this$0:Lcom/android/camera/module/VideoModule;

    const/4 v0, 0x0

    invoke-static {p0, p1, v0}, Lcom/android/camera/module/VideoModule;->access$1900(Lcom/android/camera/module/VideoModule;[Landroid/media/AudioDeviceInfo;Z)V

    return-void
.end method
