.class public Lcom/airbnb/lottie/b/a;
.super Ljava/lang/Object;
.source "FontAssetManager.java"


# instance fields
.field private final Fd:Lcom/airbnb/lottie/model/h;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/airbnb/lottie/model/h<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private final Gd:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Lcom/airbnb/lottie/model/h<",
            "Ljava/lang/String;",
            ">;",
            "Landroid/graphics/Typeface;",
            ">;"
        }
    .end annotation
.end field

.field private final Hd:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Landroid/graphics/Typeface;",
            ">;"
        }
    .end annotation
.end field

.field private Jd:Ljava/lang/String;

.field private final assetManager:Landroid/content/res/AssetManager;

.field private delegate:Lcom/airbnb/lottie/c;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation
.end field


# direct methods
.method public constructor <init>(Landroid/graphics/drawable/Drawable$Callback;Lcom/airbnb/lottie/c;)V
    .locals 1
    .param p2    # Lcom/airbnb/lottie/c;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Lcom/airbnb/lottie/model/h;

    invoke-direct {v0}, Lcom/airbnb/lottie/model/h;-><init>()V

    iput-object v0, p0, Lcom/airbnb/lottie/b/a;->Fd:Lcom/airbnb/lottie/model/h;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/airbnb/lottie/b/a;->Gd:Ljava/util/Map;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/airbnb/lottie/b/a;->Hd:Ljava/util/Map;

    const-string v0, ".ttf"

    iput-object v0, p0, Lcom/airbnb/lottie/b/a;->Jd:Ljava/lang/String;

    iput-object p2, p0, Lcom/airbnb/lottie/b/a;->delegate:Lcom/airbnb/lottie/c;

    instance-of p2, p1, Landroid/view/View;

    if-nez p2, :cond_0

    const-string p1, "LottieDrawable must be inside of a view for images to work."

    invoke-static {p1}, Lcom/airbnb/lottie/d/d;->warning(Ljava/lang/String;)V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/airbnb/lottie/b/a;->assetManager:Landroid/content/res/AssetManager;

    return-void

    :cond_0
    check-cast p1, Landroid/view/View;

    invoke-virtual {p1}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object p1

    iput-object p1, p0, Lcom/airbnb/lottie/b/a;->assetManager:Landroid/content/res/AssetManager;

    return-void
.end method

.method private a(Landroid/graphics/Typeface;Ljava/lang/String;)Landroid/graphics/Typeface;
    .locals 1

    const-string p0, "Italic"

    invoke-virtual {p2, p0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result p0

    const-string v0, "Bold"

    invoke-virtual {p2, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result p2

    if-eqz p0, :cond_0

    if-eqz p2, :cond_0

    const/4 p0, 0x3

    goto :goto_0

    :cond_0
    if-eqz p0, :cond_1

    const/4 p0, 0x2

    goto :goto_0

    :cond_1
    if-eqz p2, :cond_2

    const/4 p0, 0x1

    goto :goto_0

    :cond_2
    const/4 p0, 0x0

    :goto_0
    invoke-virtual {p1}, Landroid/graphics/Typeface;->getStyle()I

    move-result p2

    if-ne p2, p0, :cond_3

    return-object p1

    :cond_3
    invoke-static {p1, p0}, Landroid/graphics/Typeface;->create(Landroid/graphics/Typeface;I)Landroid/graphics/Typeface;

    move-result-object p0

    return-object p0
.end method

.method private ca(Ljava/lang/String;)Landroid/graphics/Typeface;
    .locals 2

    iget-object v0, p0, Lcom/airbnb/lottie/b/a;->Hd:Ljava/util/Map;

    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/graphics/Typeface;

    if-eqz v0, :cond_0

    return-object v0

    :cond_0
    const/4 v0, 0x0

    iget-object v1, p0, Lcom/airbnb/lottie/b/a;->delegate:Lcom/airbnb/lottie/c;

    if-eqz v1, :cond_1

    invoke-virtual {v1, p1}, Lcom/airbnb/lottie/c;->B(Ljava/lang/String;)Landroid/graphics/Typeface;

    move-result-object v0

    :cond_1
    iget-object v1, p0, Lcom/airbnb/lottie/b/a;->delegate:Lcom/airbnb/lottie/c;

    if-eqz v1, :cond_2

    if-nez v0, :cond_2

    invoke-virtual {v1, p1}, Lcom/airbnb/lottie/c;->C(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    if-eqz v1, :cond_2

    iget-object v0, p0, Lcom/airbnb/lottie/b/a;->assetManager:Landroid/content/res/AssetManager;

    invoke-static {v0, v1}, Landroid/graphics/Typeface;->createFromAsset(Landroid/content/res/AssetManager;Ljava/lang/String;)Landroid/graphics/Typeface;

    move-result-object v0

    :cond_2
    if-nez v0, :cond_3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "fonts/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/airbnb/lottie/b/a;->Jd:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/airbnb/lottie/b/a;->assetManager:Landroid/content/res/AssetManager;

    invoke-static {v1, v0}, Landroid/graphics/Typeface;->createFromAsset(Landroid/content/res/AssetManager;Ljava/lang/String;)Landroid/graphics/Typeface;

    move-result-object v0

    :cond_3
    iget-object p0, p0, Lcom/airbnb/lottie/b/a;->Hd:Ljava/util/Map;

    invoke-interface {p0, p1, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-object v0
.end method


# virtual methods
.method public K(Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/airbnb/lottie/b/a;->Jd:Ljava/lang/String;

    return-void
.end method

.method public b(Lcom/airbnb/lottie/c;)V
    .locals 0
    .param p1    # Lcom/airbnb/lottie/c;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param

    iput-object p1, p0, Lcom/airbnb/lottie/b/a;->delegate:Lcom/airbnb/lottie/c;

    return-void
.end method

.method public l(Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Typeface;
    .locals 2

    iget-object v0, p0, Lcom/airbnb/lottie/b/a;->Fd:Lcom/airbnb/lottie/model/h;

    invoke-virtual {v0, p1, p2}, Lcom/airbnb/lottie/model/h;->set(Ljava/lang/Object;Ljava/lang/Object;)V

    iget-object v0, p0, Lcom/airbnb/lottie/b/a;->Gd:Ljava/util/Map;

    iget-object v1, p0, Lcom/airbnb/lottie/b/a;->Fd:Lcom/airbnb/lottie/model/h;

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/graphics/Typeface;

    if-eqz v0, :cond_0

    return-object v0

    :cond_0
    invoke-direct {p0, p1}, Lcom/airbnb/lottie/b/a;->ca(Ljava/lang/String;)Landroid/graphics/Typeface;

    move-result-object p1

    invoke-direct {p0, p1, p2}, Lcom/airbnb/lottie/b/a;->a(Landroid/graphics/Typeface;Ljava/lang/String;)Landroid/graphics/Typeface;

    move-result-object p1

    iget-object p2, p0, Lcom/airbnb/lottie/b/a;->Gd:Ljava/util/Map;

    iget-object p0, p0, Lcom/airbnb/lottie/b/a;->Fd:Lcom/airbnb/lottie/model/h;

    invoke-interface {p2, p0, p1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-object p1
.end method
