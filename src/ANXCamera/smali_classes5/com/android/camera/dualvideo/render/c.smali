.class public final synthetic Lcom/android/camera/dualvideo/render/c;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/dualvideo/render/c;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/dualvideo/render/c;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/render/c;-><init>()V

    sput-object v0, Lcom/android/camera/dualvideo/render/c;->INSTANCE:Lcom/android/camera/dualvideo/render/c;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->z(Lcom/android/camera/dualvideo/render/IRenderable;)V

    return-void
.end method
