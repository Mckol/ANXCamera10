.class public final synthetic Lcom/android/camera/fragment/ambilight/b;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic tg:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

.field private final synthetic ug:I


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/fragment/ambilight/FragmentAmbilight;I)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/ambilight/b;->tg:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

    iput p2, p0, Lcom/android/camera/fragment/ambilight/b;->ug:I

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/ambilight/b;->tg:Lcom/android/camera/fragment/ambilight/FragmentAmbilight;

    iget p0, p0, Lcom/android/camera/fragment/ambilight/b;->ug:I

    invoke-virtual {v0, p0}, Lcom/android/camera/fragment/ambilight/FragmentAmbilight;->n(I)V

    return-void
.end method
