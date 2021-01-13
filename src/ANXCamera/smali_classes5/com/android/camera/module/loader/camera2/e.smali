.class public final synthetic Lcom/android/camera/module/loader/camera2/e;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lio/reactivex/functions/Consumer;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/module/loader/camera2/e;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/module/loader/camera2/e;

    invoke-direct {v0}, Lcom/android/camera/module/loader/camera2/e;-><init>()V

    sput-object v0, Lcom/android/camera/module/loader/camera2/e;->INSTANCE:Lcom/android/camera/module/loader/camera2/e;

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

    check-cast p1, Ljava/lang/Throwable;

    invoke-static {p1}, Lcom/android/camera/module/loader/camera2/CompletablePreFixCamera2Setup;->i(Ljava/lang/Throwable;)V

    return-void
.end method
