.class public final synthetic Lcom/android/camera/module/V;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Predicate;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/module/V;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/module/V;

    invoke-direct {v0}, Lcom/android/camera/module/V;-><init>()V

    sput-object v0, Lcom/android/camera/module/V;->INSTANCE:Lcom/android/camera/module/V;

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

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-static {p1}, Lcom/android/camera/module/DualVideoModule;->e(Lcom/android/camera/dualvideo/UserSelectData;)Z

    move-result p0

    return p0
.end method
