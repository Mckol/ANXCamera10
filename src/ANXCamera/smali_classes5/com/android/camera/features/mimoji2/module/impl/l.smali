.class public final synthetic Lcom/android/camera/features/mimoji2/module/impl/l;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;

.field private final synthetic ug:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/module/impl/l;->tg:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;

    iput-boolean p2, p0, Lcom/android/camera/features/mimoji2/module/impl/l;->ug:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/module/impl/l;->tg:Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/module/impl/l;->ug:Z

    invoke-static {v0, p0}, Lcom/android/camera/features/mimoji2/module/impl/MimojiAvatarEngine2Impl;->a(Lcom/android/camera/features/mimoji2/module/protocol/MimojiModeProtocol$MimojiBottomList;Z)V

    return-void
.end method
