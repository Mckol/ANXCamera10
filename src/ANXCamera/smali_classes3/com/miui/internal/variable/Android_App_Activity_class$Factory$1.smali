.class Lcom/miui/internal/variable/Android_App_Activity_class$Factory$1;
.super Lmiui/util/SoftReferenceSingleton;
.source "Android_App_Activity_class.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/miui/internal/variable/Android_App_Activity_class$Factory;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lmiui/util/SoftReferenceSingleton<",
        "Lcom/miui/internal/variable/Android_App_Activity_class$Factory;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, Lmiui/util/SoftReferenceSingleton;-><init>()V

    return-void
.end method


# virtual methods
.method protected createInstance()Lcom/miui/internal/variable/Android_App_Activity_class$Factory;
    .locals 1

    new-instance p0, Lcom/miui/internal/variable/Android_App_Activity_class$Factory;

    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/miui/internal/variable/Android_App_Activity_class$Factory;-><init>(Lcom/miui/internal/variable/Android_App_Activity_class$1;)V

    return-object p0
.end method

.method protected bridge synthetic createInstance()Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0}, Lcom/miui/internal/variable/Android_App_Activity_class$Factory$1;->createInstance()Lcom/miui/internal/variable/Android_App_Activity_class$Factory;

    move-result-object p0

    return-object p0
.end method
