.class public final synthetic Lcom/android/camera/module/impl/component/x;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/module/Camera2Module;

.field private final synthetic ug:Z

.field private final synthetic vg:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/Camera2Module;ZI)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/impl/component/x;->tg:Lcom/android/camera/module/Camera2Module;

    iput-boolean p2, p0, Lcom/android/camera/module/impl/component/x;->ug:Z

    iput p3, p0, Lcom/android/camera/module/impl/component/x;->vg:I

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/impl/component/x;->tg:Lcom/android/camera/module/Camera2Module;

    iget-boolean v1, p0, Lcom/android/camera/module/impl/component/x;->ug:Z

    iget p0, p0, Lcom/android/camera/module/impl/component/x;->vg:I

    invoke-static {v0, v1, p0}, Lcom/android/camera/module/impl/component/MiAsdDetectImpl;->a(Lcom/android/camera/module/Camera2Module;ZI)V

    return-void
.end method
