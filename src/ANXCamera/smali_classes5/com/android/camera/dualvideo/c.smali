.class public final synthetic Lcom/android/camera/dualvideo/c;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Function;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/dualvideo/c;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/dualvideo/c;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/c;-><init>()V

    sput-object v0, Lcom/android/camera/dualvideo/c;->INSTANCE:Lcom/android/camera/dualvideo/c;

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

    check-cast p1, Lcom/android/camera/dualvideo/render/IRenderable;

    invoke-static {p1}, Lcom/android/camera/dualvideo/DualVideoControler;->e(Lcom/android/camera/dualvideo/render/IRenderable;)Lcom/android/camera/effect/draw_mode/DrawExtTexAttribute;

    move-result-object p0

    return-object p0
.end method
