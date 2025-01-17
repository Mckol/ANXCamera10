.class public final Lmiui/util/Pools;
.super Ljava/lang/Object;
.source "Pools.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lmiui/util/Pools$SoftReferencePool;,
        Lmiui/util/Pools$SimplePool;,
        Lmiui/util/Pools$BasePool;,
        Lmiui/util/Pools$SoftReferenceInstanceHolder;,
        Lmiui/util/Pools$InstanceHolder;,
        Lmiui/util/Pools$IInstanceHolder;,
        Lmiui/util/Pools$Manager;,
        Lmiui/util/Pools$Pool;
    }
.end annotation


# static fields
.field private static final mInstanceHolderMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/Class<",
            "*>;",
            "Lmiui/util/Pools$InstanceHolder<",
            "*>;>;"
        }
    .end annotation
.end field

.field private static final mSoftReferenceInstanceHolderMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/Class<",
            "*>;",
            "Lmiui/util/Pools$SoftReferenceInstanceHolder<",
            "*>;>;"
        }
    .end annotation
.end field

.field private static final mStringBuilderPool:Lmiui/util/Pools$Pool;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lmiui/util/Pools$Pool<",
            "Ljava/lang/StringBuilder;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 2

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lmiui/util/Pools;->mInstanceHolderMap:Ljava/util/HashMap;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lmiui/util/Pools;->mSoftReferenceInstanceHolderMap:Ljava/util/HashMap;

    new-instance v0, Lmiui/util/Pools$1;

    invoke-direct {v0}, Lmiui/util/Pools$1;-><init>()V

    const/4 v1, 0x4

    invoke-static {v0, v1}, Lmiui/util/Pools;->createSoftReferencePool(Lmiui/util/Pools$Manager;I)Lmiui/util/Pools$SoftReferencePool;

    move-result-object v0

    sput-object v0, Lmiui/util/Pools;->mStringBuilderPool:Lmiui/util/Pools$Pool;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()Ljava/util/HashMap;
    .locals 1

    sget-object v0, Lmiui/util/Pools;->mInstanceHolderMap:Ljava/util/HashMap;

    return-object v0
.end method

.method static synthetic access$100()Ljava/util/HashMap;
    .locals 1

    sget-object v0, Lmiui/util/Pools;->mSoftReferenceInstanceHolderMap:Ljava/util/HashMap;

    return-object v0
.end method

.method public static createSimplePool(Lmiui/util/Pools$Manager;I)Lmiui/util/Pools$SimplePool;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lmiui/util/Pools$Manager<",
            "TT;>;I)",
            "Lmiui/util/Pools$SimplePool<",
            "TT;>;"
        }
    .end annotation

    new-instance v0, Lmiui/util/Pools$SimplePool;

    invoke-direct {v0, p0, p1}, Lmiui/util/Pools$SimplePool;-><init>(Lmiui/util/Pools$Manager;I)V

    return-object v0
.end method

.method public static createSoftReferencePool(Lmiui/util/Pools$Manager;I)Lmiui/util/Pools$SoftReferencePool;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lmiui/util/Pools$Manager<",
            "TT;>;I)",
            "Lmiui/util/Pools$SoftReferencePool<",
            "TT;>;"
        }
    .end annotation

    new-instance v0, Lmiui/util/Pools$SoftReferencePool;

    invoke-direct {v0, p0, p1}, Lmiui/util/Pools$SoftReferencePool;-><init>(Lmiui/util/Pools$Manager;I)V

    return-object v0
.end method

.method public static getStringBuilderPool()Lmiui/util/Pools$Pool;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Lmiui/util/Pools$Pool<",
            "Ljava/lang/StringBuilder;",
            ">;"
        }
    .end annotation

    sget-object v0, Lmiui/util/Pools;->mStringBuilderPool:Lmiui/util/Pools$Pool;

    return-object v0
.end method

.method static onPoolClose(Lmiui/util/Pools$InstanceHolder;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lmiui/util/Pools$InstanceHolder<",
            "TT;>;I)V"
        }
    .end annotation

    sget-object v0, Lmiui/util/Pools;->mInstanceHolderMap:Ljava/util/HashMap;

    monitor-enter v0

    neg-int p1, p1

    :try_start_0
    invoke-virtual {p0, p1}, Lmiui/util/Pools$InstanceHolder;->resize(I)V

    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method static onPoolCreate(Ljava/lang/Class;I)Lmiui/util/Pools$InstanceHolder;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/lang/Class<",
            "TT;>;I)",
            "Lmiui/util/Pools$InstanceHolder<",
            "TT;>;"
        }
    .end annotation

    sget-object v0, Lmiui/util/Pools;->mInstanceHolderMap:Ljava/util/HashMap;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lmiui/util/Pools;->mInstanceHolderMap:Ljava/util/HashMap;

    invoke-virtual {v1, p0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lmiui/util/Pools$InstanceHolder;

    if-nez v1, :cond_0

    new-instance v1, Lmiui/util/Pools$InstanceHolder;

    invoke-direct {v1, p0, p1}, Lmiui/util/Pools$InstanceHolder;-><init>(Ljava/lang/Class;I)V

    sget-object p1, Lmiui/util/Pools;->mInstanceHolderMap:Ljava/util/HashMap;

    invoke-virtual {p1, p0, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_0
    invoke-virtual {v1, p1}, Lmiui/util/Pools$InstanceHolder;->resize(I)V

    :goto_0
    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method static onSoftReferencePoolClose(Lmiui/util/Pools$SoftReferenceInstanceHolder;I)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lmiui/util/Pools$SoftReferenceInstanceHolder<",
            "TT;>;I)V"
        }
    .end annotation

    sget-object v0, Lmiui/util/Pools;->mSoftReferenceInstanceHolderMap:Ljava/util/HashMap;

    monitor-enter v0

    neg-int p1, p1

    :try_start_0
    invoke-virtual {p0, p1}, Lmiui/util/Pools$SoftReferenceInstanceHolder;->resize(I)V

    monitor-exit v0

    return-void

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method static onSoftReferencePoolCreate(Ljava/lang/Class;I)Lmiui/util/Pools$SoftReferenceInstanceHolder;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Ljava/lang/Class<",
            "TT;>;I)",
            "Lmiui/util/Pools$SoftReferenceInstanceHolder<",
            "TT;>;"
        }
    .end annotation

    sget-object v0, Lmiui/util/Pools;->mSoftReferenceInstanceHolderMap:Ljava/util/HashMap;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lmiui/util/Pools;->mSoftReferenceInstanceHolderMap:Ljava/util/HashMap;

    invoke-virtual {v1, p0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lmiui/util/Pools$SoftReferenceInstanceHolder;

    if-nez v1, :cond_0

    new-instance v1, Lmiui/util/Pools$SoftReferenceInstanceHolder;

    invoke-direct {v1, p0, p1}, Lmiui/util/Pools$SoftReferenceInstanceHolder;-><init>(Ljava/lang/Class;I)V

    sget-object p1, Lmiui/util/Pools;->mSoftReferenceInstanceHolderMap:Ljava/util/HashMap;

    invoke-virtual {p1, p0, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_0
    invoke-virtual {v1, p1}, Lmiui/util/Pools$SoftReferenceInstanceHolder;->resize(I)V

    :goto_0
    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method
