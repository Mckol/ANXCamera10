.class public final synthetic Lcom/android/camera/data/data/global/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/ToIntFunction;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/data/data/global/a;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/data/data/global/a;

    invoke-direct {v0}, Lcom/android/camera/data/data/global/a;-><init>()V

    sput-object v0, Lcom/android/camera/data/data/global/a;->INSTANCE:Lcom/android/camera/data/data/global/a;

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

    check-cast p1, Ljava/lang/String;

    invoke-static {p1}, Lcom/android/camera/data/data/global/DataItemGlobal;->P(Ljava/lang/String;)I

    move-result p0

    return p0
.end method
