.class public final synthetic Lcom/android/camera/features/mimoji2/fragment/edit/h;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

.field private final synthetic ug:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/features/mimoji2/fragment/edit/h;->tg:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iput p2, p0, Lcom/android/camera/features/mimoji2/fragment/edit/h;->ug:I

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/h;->tg:Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;

    iget p0, p0, Lcom/android/camera/features/mimoji2/fragment/edit/h;->ug:I

    invoke-virtual {v0, p0}, Lcom/android/camera/features/mimoji2/fragment/edit/FragmentMimojiEmoticon;->m(I)V

    return-void
.end method
