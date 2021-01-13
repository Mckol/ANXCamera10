.class public final synthetic Lcom/android/camera/data/data/global/b;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/ToIntFunction;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/data/data/global/b;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/data/data/global/b;

    invoke-direct {v0}, Lcom/android/camera/data/data/global/b;-><init>()V

    sput-object v0, Lcom/android/camera/data/data/global/b;->INSTANCE:Lcom/android/camera/data/data/global/b;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final applyAsInt(Ljava/lang/Object;)I
    .locals 0

    check-cast p1, Ljava/lang/Integer;

    invoke-static {p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->d(Ljava/lang/Integer;)I

    move-result p0

    return p0
.end method
