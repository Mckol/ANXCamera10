.class public Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;
.super Lcom/miui/internal/variable/AbsClassFactory;
.source "Android_Widget_ListView_class.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/variable/Android_Widget_ListView_class;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Factory"
.end annotation


# static fields
.field private static final INSTANCE:Lmiui/util/SoftReferenceSingleton;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lmiui/util/SoftReferenceSingleton<",
            "Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private Android_Widget_ListView_class:Lcom/miui/internal/variable/Android_Widget_ListView_class;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory$1;

    invoke-direct {v0}, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory$1;-><init>()V

    sput-object v0, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;->INSTANCE:Lmiui/util/SoftReferenceSingleton;

    return-void
.end method

.method private constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/miui/internal/variable/AbsClassFactory;-><init>()V

    const-string v0, "Android_Widget_ListView_class"

    invoke-virtual {p0, v0}, Lcom/miui/internal/variable/AbsClassFactory;->create(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/miui/internal/variable/Android_Widget_ListView_class;

    iput-object v0, p0, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;->Android_Widget_ListView_class:Lcom/miui/internal/variable/Android_Widget_ListView_class;

    return-void
.end method

.method synthetic constructor <init>(Lcom/miui/internal/variable/Android_Widget_ListView_class$1;)V
    .locals 0

    invoke-direct {p0}, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;-><init>()V

    return-void
.end method

.method public static getInstance()Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;
    .locals 1

    sget-object v0, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;->INSTANCE:Lmiui/util/SoftReferenceSingleton;

    invoke-virtual {v0}, Lmiui/util/SoftReferenceSingleton;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;

    return-object v0
.end method


# virtual methods
.method public get()Lcom/miui/internal/variable/Android_Widget_ListView_class;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;->Android_Widget_ListView_class:Lcom/miui/internal/variable/Android_Widget_ListView_class;

    return-object p0
.end method

.method public bridge synthetic get()Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0}, Lcom/miui/internal/variable/Android_Widget_ListView_class$Factory;->get()Lcom/miui/internal/variable/Android_Widget_ListView_class;

    move-result-object p0

    return-object p0
.end method
