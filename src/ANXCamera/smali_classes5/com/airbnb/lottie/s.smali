.class Lcom/airbnb/lottie/s;
.super Ljava/lang/Object;
.source "LottieCompositionFactory.java"

# interfaces
.implements Ljava/util/concurrent/Callable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/airbnb/lottie/y;->b(Ljava/io/InputStream;Ljava/lang/String;)Lcom/airbnb/lottie/Z;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljava/util/concurrent/Callable<",
        "Lcom/airbnb/lottie/X<",
        "Lcom/airbnb/lottie/m;",
        ">;>;"
    }
.end annotation


# instance fields
.field final synthetic tb:Ljava/io/InputStream;

.field final synthetic val$cacheKey:Ljava/lang/String;


# direct methods
.method constructor <init>(Ljava/io/InputStream;Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/airbnb/lottie/s;->tb:Ljava/io/InputStream;

    iput-object p2, p0, Lcom/airbnb/lottie/s;->val$cacheKey:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public call()Lcom/airbnb/lottie/X;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Lcom/airbnb/lottie/X<",
            "Lcom/airbnb/lottie/m;",
            ">;"
        }
    .end annotation

    iget-object v0, p0, Lcom/airbnb/lottie/s;->tb:Ljava/io/InputStream;

    iget-object p0, p0, Lcom/airbnb/lottie/s;->val$cacheKey:Ljava/lang/String;

    invoke-static {v0, p0}, Lcom/airbnb/lottie/y;->c(Ljava/io/InputStream;Ljava/lang/String;)Lcom/airbnb/lottie/X;

    move-result-object p0

    return-object p0
.end method

.method public bridge synthetic call()Ljava/lang/Object;
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    invoke-virtual {p0}, Lcom/airbnb/lottie/s;->call()Lcom/airbnb/lottie/X;

    move-result-object p0

    return-object p0
.end method
