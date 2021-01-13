.class public final synthetic Lcom/android/camera/zoommap/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/zoommap/ZoomMapController;

.field private final synthetic ug:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/zoommap/ZoomMapController;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/zoommap/a;->tg:Lcom/android/camera/zoommap/ZoomMapController;

    iput-boolean p2, p0, Lcom/android/camera/zoommap/a;->ug:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/zoommap/a;->tg:Lcom/android/camera/zoommap/ZoomMapController;

    iget-boolean p0, p0, Lcom/android/camera/zoommap/a;->ug:Z

    invoke-virtual {v0, p0}, Lcom/android/camera/zoommap/ZoomMapController;->J(Z)V

    return-void
.end method
