.class public Lcom/android/camera/ambilight/AmbilightEngine;
.super Ljava/lang/Object;
.source "AmbilightEngine.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/camera/ambilight/AmbilightEngine$AmbilightFlipMode;,
        Lcom/android/camera/ambilight/AmbilightEngine$AmbilightSceneMode;
    }
.end annotation


# static fields
.field public static final FLIP_HORIZONTAL:I = 0x1

.field public static final FLIP_VERTICAL:I = 0x2

.field public static final SCENE_MODE_CROWD_MOVING:I = 0x4

.field public static final SCENE_MODE_LIGHT_TRACK:I = 0x2

.field public static final SCENE_MODE_MAGIC_STAR:I = 0x5

.field public static final SCENE_MODE_SILKY_WATER:I = 0x1

.field public static final SCENE_MODE_STAR_TRACK:I = 0x3

.field public static final SCENE_MODE_TRAFFIC_LIGHT:I = 0x0

.field private static final TAG:Ljava/lang/String; = "AmbilightEngine"


# direct methods
.method static constructor <clinit>()V
    .locals 4

    :try_start_0
    const-string v0, "camera_ambilight_utils"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    const-string v0, "camera_ambilight_ambt"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    const-string v0, "camera_mialgo_ambilight"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/UnsatisfiedLinkError; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    sget-object v1, Lcom/android/camera/ambilight/AmbilightEngine;->TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "can\'t loadLibrary, "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/UnsatisfiedLinkError;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final native destroy()V
.end method

.method public final native frameProc()I
.end method

.method public final native getEngineStatus()I
.end method

.method public final native getPreviewHeight()I
.end method

.method public final native getPreviewWidth()I
.end method

.method public final native init(IIII)V
    .param p1    # I
        .annotation build Lcom/android/camera/ambilight/AmbilightEngine$AmbilightSceneMode;
        .end annotation
    .end param
.end method

.method public final native initData(I)V
.end method

.method public final native postProc()I
.end method

.method public final native prepare(Ljava/nio/ByteBuffer;I)V
.end method

.method public final native prepareData(Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
.end method

.method public final native setFlipMode(I)V
    .param p1    # I
        .annotation build Lcom/android/camera/ambilight/AmbilightEngine$AmbilightFlipMode;
        .end annotation
    .end param
.end method

.method public final native setZoomRoi(IIII)V
.end method

.method public final native updatePreview(Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Lcom/android/camera/ambilight/AmbilightRoi;)V
.end method
