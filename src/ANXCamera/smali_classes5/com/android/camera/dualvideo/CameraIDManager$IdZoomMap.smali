.class Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;
.super Ljava/lang/Object;
.source "CameraIDManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/camera/dualvideo/CameraIDManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "IdZoomMap"
.end annotation


# instance fields
.field public mId:I

.field public mZoom:F

.field final synthetic this$0:Lcom/android/camera/dualvideo/CameraIDManager;


# direct methods
.method constructor <init>(Lcom/android/camera/dualvideo/CameraIDManager;IF)V
    .locals 0

    iput-object p1, p0, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;->this$0:Lcom/android/camera/dualvideo/CameraIDManager;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p2, p0, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;->mId:I

    iput p3, p0, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;->mZoom:F

    return-void
.end method
