.class public final synthetic Lcom/android/camera/fragment/manually/e;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/data/data/ComponentData;

.field private final synthetic ug:Z


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/data/data/ComponentData;Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/manually/e;->tg:Lcom/android/camera/data/data/ComponentData;

    iput-boolean p2, p0, Lcom/android/camera/fragment/manually/e;->ug:Z

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/fragment/manually/e;->tg:Lcom/android/camera/data/data/ComponentData;

    iget-boolean p0, p0, Lcom/android/camera/fragment/manually/e;->ug:Z

    check-cast p1, Lcom/android/camera/fragment/manually/FragmentManuallyExtra;

    invoke-static {v0, p0, p1}, Lcom/android/camera/fragment/manually/FragmentManually;->a(Lcom/android/camera/data/data/ComponentData;ZLcom/android/camera/fragment/manually/FragmentManuallyExtra;)V

    return-void
.end method
