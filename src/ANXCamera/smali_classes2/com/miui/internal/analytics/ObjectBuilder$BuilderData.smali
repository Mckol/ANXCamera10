.class Lcom/miui/internal/analytics/ObjectBuilder$BuilderData;
.super Ljava/lang/Object;
.source "ObjectBuilder.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/analytics/ObjectBuilder;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "BuilderData"
.end annotation


# instance fields
.field mTag:Ljava/lang/String;

.field mType:Ljava/lang/Class;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/Class<",
            "+TT;>;"
        }
    .end annotation
.end field

.field final synthetic this$0:Lcom/miui/internal/analytics/ObjectBuilder;


# direct methods
.method constructor <init>(Lcom/miui/internal/analytics/ObjectBuilder;Ljava/lang/Class;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class<",
            "+TT;>;",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    iput-object p1, p0, Lcom/miui/internal/analytics/ObjectBuilder$BuilderData;->this$0:Lcom/miui/internal/analytics/ObjectBuilder;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p2, p0, Lcom/miui/internal/analytics/ObjectBuilder$BuilderData;->mType:Ljava/lang/Class;

    iput-object p3, p0, Lcom/miui/internal/analytics/ObjectBuilder$BuilderData;->mTag:Ljava/lang/String;

    return-void
.end method
