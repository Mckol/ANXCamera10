.class public final synthetic Lcom/android/camera/dualvideo/render/k;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/android/camera/dualvideo/render/RegionHelper$UpdatedListener;


# instance fields
.field private final synthetic tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/dualvideo/render/DualVideoRenderManager;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/dualvideo/render/k;->tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    return-void
.end method


# virtual methods
.method public final onUpdated()V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/dualvideo/render/k;->tg:Lcom/android/camera/dualvideo/render/DualVideoRenderManager;

    invoke-virtual {p0}, Lcom/android/camera/dualvideo/render/DualVideoRenderManager;->Ec()V

    return-void
.end method
