.class public Lcom/miui/internal/variable/Android_View_View_class$Factory;
.super Lcom/miui/internal/variable/AbsClassFactory;
.source "Android_View_View_class.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/variable/Android_View_View_class;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Factory"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/miui/internal/variable/Android_View_View_class$Factory$Holder;
    }
.end annotation


# instance fields
.field private Android_View_View_class:Lcom/miui/internal/variable/Android_View_View_class;


# direct methods
.method private constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/miui/internal/variable/AbsClassFactory;-><init>()V

    const-string v0, "Android_View_View_class"

    invoke-virtual {p0, v0}, Lcom/miui/internal/variable/AbsClassFactory;->create(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/miui/internal/variable/Android_View_View_class;

    iput-object v0, p0, Lcom/miui/internal/variable/Android_View_View_class$Factory;->Android_View_View_class:Lcom/miui/internal/variable/Android_View_View_class;

    return-void
.end method

.method synthetic constructor <init>(Lcom/miui/internal/variable/Android_View_View_class$1;)V
    .locals 0

    invoke-direct {p0}, Lcom/miui/internal/variable/Android_View_View_class$Factory;-><init>()V

    return-void
.end method

.method public static getInstance()Lcom/miui/internal/variable/Android_View_View_class$Factory;
    .locals 1

    sget-object v0, Lcom/miui/internal/variable/Android_View_View_class$Factory$Holder;->INSTANCE:Lcom/miui/internal/variable/Android_View_View_class$Factory;

    return-object v0
.end method


# virtual methods
.method public get()Lcom/miui/internal/variable/Android_View_View_class;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/variable/Android_View_View_class$Factory;->Android_View_View_class:Lcom/miui/internal/variable/Android_View_View_class;

    return-object p0
.end method

.method public bridge synthetic get()Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0}, Lcom/miui/internal/variable/Android_View_View_class$Factory;->get()Lcom/miui/internal/variable/Android_View_View_class;

    move-result-object p0

    return-object p0
.end method
