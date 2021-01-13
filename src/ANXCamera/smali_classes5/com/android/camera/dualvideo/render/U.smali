.class public final synthetic Lcom/android/camera/dualvideo/render/U;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/Comparator;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/dualvideo/render/U;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/dualvideo/render/U;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/render/U;-><init>()V

    sput-object v0, Lcom/android/camera/dualvideo/render/U;->INSTANCE:Lcom/android/camera/dualvideo/render/U;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final compare(Ljava/lang/Object;Ljava/lang/Object;)I
    .locals 0

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    check-cast p2, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-static {p1, p2}, Lcom/android/camera/dualvideo/render/IRenderable;->compare(Lcom/android/camera/dualvideo/render/IRenderable;Lcom/android/camera/dualvideo/render/IRenderable;)I

    move-result p0

    return p0
.end method
