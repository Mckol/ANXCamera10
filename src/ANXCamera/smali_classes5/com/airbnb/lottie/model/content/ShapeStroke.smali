.class public Lcom/airbnb/lottie/model/content/ShapeStroke;
.super Ljava/lang/Object;
.source "ShapeStroke.java"

# interfaces
.implements Lcom/airbnb/lottie/model/content/b;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/airbnb/lottie/model/content/ShapeStroke$LineJoinType;,
        Lcom/airbnb/lottie/model/content/ShapeStroke$LineCapType;
    }
.end annotation


# instance fields
.field private final color:Lcom/airbnb/lottie/model/a/a;

.field private final hidden:Z

.field private final ie:Lcom/airbnb/lottie/model/content/ShapeStroke$LineCapType;

.field private final je:Lcom/airbnb/lottie/model/content/ShapeStroke$LineJoinType;

.field private final ke:F

.field private final le:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/model/a/b;",
            ">;"
        }
    .end annotation
.end field

.field private final name:Ljava/lang/String;

.field private final offset:Lcom/airbnb/lottie/model/a/b;
    .annotation build Landroidx/annotation/Nullable;
    .end annotation
.end field

.field private final opacity:Lcom/airbnb/lottie/model/a/d;

.field private final width:Lcom/airbnb/lottie/model/a/b;


# direct methods
.method public constructor <init>(Ljava/lang/String;Lcom/airbnb/lottie/model/a/b;Ljava/util/List;Lcom/airbnb/lottie/model/a/a;Lcom/airbnb/lottie/model/a/d;Lcom/airbnb/lottie/model/a/b;Lcom/airbnb/lottie/model/content/ShapeStroke$LineCapType;Lcom/airbnb/lottie/model/content/ShapeStroke$LineJoinType;FZ)V
    .locals 0
    .param p2    # Lcom/airbnb/lottie/model/a/b;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Lcom/airbnb/lottie/model/a/b;",
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/model/a/b;",
            ">;",
            "Lcom/airbnb/lottie/model/a/a;",
            "Lcom/airbnb/lottie/model/a/d;",
            "Lcom/airbnb/lottie/model/a/b;",
            "Lcom/airbnb/lottie/model/content/ShapeStroke$LineCapType;",
            "Lcom/airbnb/lottie/model/content/ShapeStroke$LineJoinType;",
            "FZ)V"
        }
    .end annotation

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->name:Ljava/lang/String;

    iput-object p2, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->offset:Lcom/airbnb/lottie/model/a/b;

    iput-object p3, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->le:Ljava/util/List;

    iput-object p4, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->color:Lcom/airbnb/lottie/model/a/a;

    iput-object p5, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->opacity:Lcom/airbnb/lottie/model/a/d;

    iput-object p6, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->width:Lcom/airbnb/lottie/model/a/b;

    iput-object p7, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->ie:Lcom/airbnb/lottie/model/content/ShapeStroke$LineCapType;

    iput-object p8, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->je:Lcom/airbnb/lottie/model/content/ShapeStroke$LineJoinType;

    iput p9, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->ke:F

    iput-boolean p10, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->hidden:Z

    return-void
.end method


# virtual methods
.method public Pb()Lcom/airbnb/lottie/model/content/ShapeStroke$LineCapType;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->ie:Lcom/airbnb/lottie/model/content/ShapeStroke$LineCapType;

    return-object p0
.end method

.method public Qb()Lcom/airbnb/lottie/model/a/b;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->offset:Lcom/airbnb/lottie/model/a/b;

    return-object p0
.end method

.method public Rb()Lcom/airbnb/lottie/model/content/ShapeStroke$LineJoinType;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->je:Lcom/airbnb/lottie/model/content/ShapeStroke$LineJoinType;

    return-object p0
.end method

.method public Sb()Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/airbnb/lottie/model/a/b;",
            ">;"
        }
    .end annotation

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->le:Ljava/util/List;

    return-object p0
.end method

.method public Tb()F
    .locals 0

    iget p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->ke:F

    return p0
.end method

.method public a(Lcom/airbnb/lottie/Q;Lcom/airbnb/lottie/model/layer/c;)Lcom/airbnb/lottie/a/a/d;
    .locals 1

    new-instance v0, Lcom/airbnb/lottie/a/a/v;

    invoke-direct {v0, p1, p2, p0}, Lcom/airbnb/lottie/a/a/v;-><init>(Lcom/airbnb/lottie/Q;Lcom/airbnb/lottie/model/layer/c;Lcom/airbnb/lottie/model/content/ShapeStroke;)V

    return-object v0
.end method

.method public getColor()Lcom/airbnb/lottie/model/a/a;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->color:Lcom/airbnb/lottie/model/a/a;

    return-object p0
.end method

.method public getName()Ljava/lang/String;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->name:Ljava/lang/String;

    return-object p0
.end method

.method public getOpacity()Lcom/airbnb/lottie/model/a/d;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->opacity:Lcom/airbnb/lottie/model/a/d;

    return-object p0
.end method

.method public getWidth()Lcom/airbnb/lottie/model/a/b;
    .locals 0

    iget-object p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->width:Lcom/airbnb/lottie/model/a/b;

    return-object p0
.end method

.method public isHidden()Z
    .locals 0

    iget-boolean p0, p0, Lcom/airbnb/lottie/model/content/ShapeStroke;->hidden:Z

    return p0
.end method
