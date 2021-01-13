.class public final synthetic Lcom/android/camera/features/mimoji2/module/impl/g;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

.field private final synthetic ug:Ljava/nio/ByteBuffer;

.field private final synthetic vg:I

.field private final synthetic wg:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;Ljava/nio/ByteBuffer;II)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->tg:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iput-object p2, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->ug:Ljava/nio/ByteBuffer;

    iput p3, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->vg:I

    iput p4, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->wg:I

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->tg:Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;

    iget-object v1, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->ug:Ljava/nio/ByteBuffer;

    iget v2, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->vg:I

    iget p0, p0, Lcom/android/camera/features/mimoji2/module/impl/g;->wg:I

    invoke-virtual {v0, v1, v2, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->a(Ljava/nio/ByteBuffer;II)V

    return-void
.end method
