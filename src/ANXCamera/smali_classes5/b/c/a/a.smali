.class public final synthetic Lb/c/a/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Function;


# static fields
.field public static final synthetic INSTANCE:Lb/c/a/a;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lb/c/a/a;

    invoke-direct {v0}, Lb/c/a/a;-><init>()V

    sput-object v0, Lb/c/a/a;->INSTANCE:Lb/c/a/a;

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

    check-cast p1, Lcom/android/camera2/CameraCapabilities;

    invoke-static {p1}, Lb/c/a/b;->b(Lcom/android/camera2/CameraCapabilities;)Ljava/lang/Boolean;

    move-result-object p0

    return-object p0
.end method
