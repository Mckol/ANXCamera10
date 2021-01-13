.class public final synthetic Lcom/android/camera/module/da;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/module/da;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/module/da;

    invoke-direct {v0}, Lcom/android/camera/module/da;-><init>()V

    sput-object v0, Lcom/android/camera/module/da;->INSTANCE:Lcom/android/camera/module/da;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    invoke-static {}, Lcom/android/camera/module/LiveModuleSubVV;->ad()V

    return-void
.end method
