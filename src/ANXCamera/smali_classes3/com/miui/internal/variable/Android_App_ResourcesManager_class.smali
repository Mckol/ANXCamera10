.class public abstract Lcom/miui/internal/variable/Android_App_ResourcesManager_class;
.super Lcom/miui/internal/util/ClassProxy;
.source "Android_App_ResourcesManager_class.java"

# interfaces
.implements Lcom/miui/internal/variable/IManagedClassProxy;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/miui/internal/variable/Android_App_ResourcesManager_class$Factory;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/miui/internal/util/ClassProxy<",
        "Ljava/lang/Object;",
        ">;",
        "Lcom/miui/internal/variable/IManagedClassProxy;"
    }
.end annotation


# static fields
.field protected static final ResourcsManager_Class:Ljava/lang/Class;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation
.end field

.field protected static mAppendedAssetPaths:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-string v0, "android.app.ResourcesManager"

    invoke-static {v0}, Lcom/miui/internal/variable/Android_App_ResourcesManager_class;->getClass(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    sput-object v0, Lcom/miui/internal/variable/Android_App_ResourcesManager_class;->ResourcsManager_Class:Ljava/lang/Class;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    sget-object v0, Lcom/miui/internal/variable/Android_App_ResourcesManager_class;->ResourcsManager_Class:Ljava/lang/Class;

    invoke-direct {p0, v0}, Lcom/miui/internal/util/ClassProxy;-><init>(Ljava/lang/Class;)V

    return-void
.end method

.method public static appendAssetPath(Ljava/lang/String;)V
    .locals 2

    sget-object v0, Lcom/miui/internal/variable/Android_App_ResourcesManager_class;->mAppendedAssetPaths:Ljava/util/List;

    if-nez v0, :cond_0

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lcom/miui/internal/variable/Android_App_ResourcesManager_class;->mAppendedAssetPaths:Ljava/util/List;

    :cond_0
    sget-object v0, Lcom/miui/internal/variable/Android_App_ResourcesManager_class;->mAppendedAssetPaths:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_1
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    return-void

    :cond_2
    sget-object v0, Lcom/miui/internal/variable/Android_App_ResourcesManager_class;->mAppendedAssetPaths:Ljava/util/List;

    invoke-interface {v0, p0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    return-void
.end method

.method protected static getClass(Ljava/lang/String;)Ljava/lang/Class;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation

    :try_start_0
    invoke-static {p0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p0
    :try_end_0
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    :catch_0
    move-exception p0

    invoke-static {}, Lcom/miui/internal/variable/VariableExceptionHandler;->getInstance()Lcom/miui/internal/variable/VariableExceptionHandler;

    move-result-object v0

    const-string v1, "android.app.ResourcesManager"

    invoke-virtual {v0, v1, p0}, Lcom/miui/internal/variable/VariableExceptionHandler;->onThrow(Ljava/lang/String;Ljava/lang/Throwable;)V

    const/4 p0, 0x0

    return-object p0
.end method
