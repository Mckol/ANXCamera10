.class public final synthetic Lcom/android/camera/dualvideo/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Function;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/dualvideo/a;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/dualvideo/a;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/a;-><init>()V

    sput-object v0, Lcom/android/camera/dualvideo/a;->INSTANCE:Lcom/android/camera/dualvideo/a;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    check-cast p1, Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;

    invoke-static {p1}, Lcom/android/camera/dualvideo/CameraIDManager;->a(Lcom/android/camera/dualvideo/CameraIDManager$IdZoomMap;)Ljava/lang/Float;

    move-result-object p0

    return-object p0
.end method
