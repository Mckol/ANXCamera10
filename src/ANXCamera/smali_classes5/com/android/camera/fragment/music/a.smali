.class public final synthetic Lcom/android/camera/fragment/music/a;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lio/reactivex/functions/Consumer;


# instance fields
.field private final synthetic tg:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/fragment/music/FragmentLiveMusicPager;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/fragment/music/a;->tg:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    iget-object p0, p0, Lcom/android/camera/fragment/music/a;->tg:Lcom/android/camera/fragment/music/FragmentLiveMusicPager;

    check-cast p1, Ljava/util/List;

    invoke-virtual {p0, p1}, Lcom/android/camera/fragment/music/FragmentLiveMusicPager;->c(Ljava/util/List;)V

    return-void
.end method
