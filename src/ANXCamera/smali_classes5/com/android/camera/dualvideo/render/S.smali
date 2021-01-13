.class public final synthetic Lcom/android/camera/dualvideo/render/S;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/dualvideo/render/S;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/dualvideo/render/S;

    invoke-direct {v0}, Lcom/android/camera/dualvideo/render/S;-><init>()V

    sput-object v0, Lcom/android/camera/dualvideo/render/S;->INSTANCE:Lcom/android/camera/dualvideo/render/S;

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

    check-cast p1, Lcom/android/camera/dualvideo/render/IMiscRenderable;

    invoke-static {p1}, Lcom/android/camera/dualvideo/render/MiscTextureManager;->a(Lcom/android/camera/dualvideo/render/IMiscRenderable;)V

    return-void
.end method
