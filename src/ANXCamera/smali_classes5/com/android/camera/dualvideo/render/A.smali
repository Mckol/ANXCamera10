.class public final synthetic Lcom/android/camera/dualvideo/render/A;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Function;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/dualvideo/render/A;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/dualvideo/render/A;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/render/A;-><init>()V

    sput-object v0, Lcom/android/camera/dualvideo/render/A;->INSTANCE:Lcom/android/camera/dualvideo/render/A;

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

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->c(Lcom/android/camera/dualvideo/UserSelectData;)Ljava/lang/Integer;

    move-result-object p0

    return-object p0
.end method
