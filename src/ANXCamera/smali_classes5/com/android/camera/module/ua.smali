.class public final synthetic Lcom/android/camera/module/ua;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Function;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/module/ua;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/module/ua;

    invoke-direct {v0}, Lcom/android/camera/module/ua;-><init>()V

    sput-object v0, Lcom/android/camera/module/ua;->INSTANCE:Lcom/android/camera/module/ua;

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

    check-cast p1, Lcom/android/camera/protocol/ModeProtocol$DualVideoRenderProtocol;

    invoke-static {p1}, Lcom/android/camera/module/VideoModule;->a(Lcom/android/camera/protocol/ModeProtocol$DualVideoRenderProtocol;)Ljava/lang/Boolean;

    move-result-object p0

    return-object p0
.end method
