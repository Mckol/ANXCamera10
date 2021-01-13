.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/edit/o;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

.field private final synthetic ug:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/o;->tg:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iput-boolean p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/o;->ug:Z

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/o;->tg:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iget-boolean p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/o;->ug:Z

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->u(Z)V

    return-void
.end method
