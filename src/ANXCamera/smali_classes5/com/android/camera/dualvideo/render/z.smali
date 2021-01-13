.class public final synthetic Lcom/android/camera/dualvideo/render/z;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Predicate;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/dualvideo/render/z;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/dualvideo/render/z;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/render/z;-><init>()V

    sput-object v0, Lcom/android/camera/dualvideo/render/z;->INSTANCE:Lcom/android/camera/dualvideo/render/z;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final test(Ljava/lang/Object;)Z
    .locals 0

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->B(Lcom/android/camera/dualvideo/render/IRenderable;)Z

    move-result p0

    return p0
.end method
