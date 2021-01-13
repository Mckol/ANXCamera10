.class public final synthetic Lcom/android/camera/module/n;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

.field private final synthetic ug:Landroid/graphics/Bitmap;

.field private final synthetic vg:[F


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Landroid/graphics/Bitmap;[F)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/n;->tg:Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    iput-object p2, p0, Lcom/android/camera/module/n;->ug:Landroid/graphics/Bitmap;

    iput-object p3, p0, Lcom/android/camera/module/n;->vg:[F

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    iget-object v0, p0, Lcom/android/camera/module/n;->tg:Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;

    iget-object v1, p0, Lcom/android/camera/module/n;->ug:Landroid/graphics/Bitmap;

    iget-object p0, p0, Lcom/android/camera/module/n;->vg:[F

    invoke-static {v0, v1, p0}, Lcom/android/camera/module/Camera2Module;->a(Lcom/android/camera/protocol/ModeProtocol$ActionProcessing;Landroid/graphics/Bitmap;[F)V

    return-void
.end method
