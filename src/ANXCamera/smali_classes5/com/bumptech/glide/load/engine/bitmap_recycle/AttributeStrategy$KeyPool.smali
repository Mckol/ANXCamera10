.class Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$KeyPool;
.super Lcom/bumptech/glide/load/engine/bitmap_recycle/BaseKeyPool;
.source "AttributeStrategy.java"


# annotations
.annotation build Landroidx/annotation/VisibleForTesting;
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "KeyPool"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/bumptech/glide/load/engine/bitmap_recycle/BaseKeyPool<",
        "Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lcom/bumptech/glide/load/engine/bitmap_recycle/BaseKeyPool;-><init>()V

    return-void
.end method


# virtual methods
.method protected create()Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;
    .locals 1

    new-instance v0, Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;

    invoke-direct {v0, p0}, Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;-><init>(Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$KeyPool;)V

    return-object v0
.end method

.method protected bridge synthetic create()Lcom/bumptech/glide/load/engine/bitmap_recycle/Poolable;
    .locals 0

    invoke-virtual {p0}, Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$KeyPool;->create()Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;

    move-result-object p0

    return-object p0
.end method

.method get(IILandroid/graphics/Bitmap$Config;)Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;
    .locals 0

    invoke-virtual {p0}, Lcom/bumptech/glide/load/engine/bitmap_recycle/BaseKeyPool;->get()Lcom/bumptech/glide/load/engine/bitmap_recycle/Poolable;

    move-result-object p0

    check-cast p0, Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;

    invoke-virtual {p0, p1, p2, p3}, Lcom/bumptech/glide/load/engine/bitmap_recycle/AttributeStrategy$Key;->init(IILandroid/graphics/Bitmap$Config;)V

    return-object p0
.end method
