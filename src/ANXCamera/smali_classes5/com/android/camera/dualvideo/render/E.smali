.class public final synthetic Lcom/android/camera/dualvideo/render/E;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Predicate;


# instance fields
.field private final synthetic tg:Ljava/util/concurrent/ConcurrentHashMap;


# direct methods
.method public synthetic constructor <init>(Ljava/util/concurrent/ConcurrentHashMap;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/E;->tg:Ljava/util/concurrent/ConcurrentHashMap;

    return-void
.end method


# virtual methods
.method public final test(Ljava/lang/Object;)Z
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/E;->tg:Ljava/util/concurrent/ConcurrentHashMap;

    check-cast p1, Lcom/android/camera/dualvideo/UserSelectData;

    invoke-static {p0, p1}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->c(Ljava/util/concurrent/ConcurrentHashMap;Lcom/android/camera/dualvideo/UserSelectData;)Z

    move-result p0

    return p0
.end method
