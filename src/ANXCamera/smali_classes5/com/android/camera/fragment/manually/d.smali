.class public final synthetic Lcom/android/camera/fragment/manually/d;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/camera/fragment/manually/d;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/camera/fragment/manually/d;

    invoke-direct {v0}, Lcom/android/camera/fragment/manually/d;-><init>()V

    sput-object v0, Lcom/android/camera/fragment/manually/d;->INSTANCE:Lcom/android/camera/fragment/manually/d;

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

    check-cast p1, Lcom/android/camera/fragment/manually/FragmentManuallyExtra;

    invoke-static {p1}, Lcom/android/camera/fragment/manually/FragmentManually;->c(Lcom/android/camera/fragment/manually/FragmentManuallyExtra;)V

    return-void
.end method
