.class public Lcom/miui/internal/component/plugin/PluginContext;
.super Ljava/lang/Object;
.source "PluginContext.java"


# static fields
.field private static volatile instance:Lcom/miui/internal/component/plugin/PluginContext;


# instance fields
.field private applicationContext:Landroid/content/Context;

.field private pluginClassLoader:Lcom/miui/internal/component/plugin/PluginClassLoader;

.field private pluginLoader:Lcom/miui/internal/component/plugin/PluginLoader;

.field private pluginResourceLoader:Lcom/miui/internal/component/plugin/PluginResourceLoader;


# direct methods
.method private constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getInstance()Lcom/miui/internal/component/plugin/PluginContext;
    .locals 2

    sget-object v0, Lcom/miui/internal/component/plugin/PluginContext;->instance:Lcom/miui/internal/component/plugin/PluginContext;

    if-nez v0, :cond_1

    const-class v0, Lcom/miui/internal/component/plugin/PluginContext;

    monitor-enter v0

    :try_start_0
    sget-object v1, Lcom/miui/internal/component/plugin/PluginContext;->instance:Lcom/miui/internal/component/plugin/PluginContext;

    if-nez v1, :cond_0

    new-instance v1, Lcom/miui/internal/component/plugin/PluginContext;

    invoke-direct {v1}, Lcom/miui/internal/component/plugin/PluginContext;-><init>()V

    sput-object v1, Lcom/miui/internal/component/plugin/PluginContext;->instance:Lcom/miui/internal/component/plugin/PluginContext;

    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    :cond_1
    :goto_0
    sget-object v0, Lcom/miui/internal/component/plugin/PluginContext;->instance:Lcom/miui/internal/component/plugin/PluginContext;

    return-object v0
.end method


# virtual methods
.method public getApplicationContext()Landroid/content/Context;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/component/plugin/PluginContext;->applicationContext:Landroid/content/Context;

    return-object p0
.end method

.method public getPluginClassLoader()Lcom/miui/internal/component/plugin/PluginClassLoader;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/component/plugin/PluginContext;->pluginClassLoader:Lcom/miui/internal/component/plugin/PluginClassLoader;

    return-object p0
.end method

.method public getPluginLoader()Lcom/miui/internal/component/plugin/PluginLoader;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/component/plugin/PluginContext;->pluginLoader:Lcom/miui/internal/component/plugin/PluginLoader;

    return-object p0
.end method

.method public getPluginResourceLoader()Lcom/miui/internal/component/plugin/PluginResourceLoader;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/component/plugin/PluginContext;->pluginResourceLoader:Lcom/miui/internal/component/plugin/PluginResourceLoader;

    return-object p0
.end method

.method public setApplicationContext(Landroid/content/Context;)V
    .locals 0

    iput-object p1, p0, Lcom/miui/internal/component/plugin/PluginContext;->applicationContext:Landroid/content/Context;

    return-void
.end method

.method public setPluginClassLoader(Lcom/miui/internal/component/plugin/PluginClassLoader;)V
    .locals 0

    iput-object p1, p0, Lcom/miui/internal/component/plugin/PluginContext;->pluginClassLoader:Lcom/miui/internal/component/plugin/PluginClassLoader;

    return-void
.end method

.method public setPluginLoader(Lcom/miui/internal/component/plugin/PluginLoader;)V
    .locals 0

    iput-object p1, p0, Lcom/miui/internal/component/plugin/PluginContext;->pluginLoader:Lcom/miui/internal/component/plugin/PluginLoader;

    return-void
.end method

.method public setPluginResourceLoader(Lcom/miui/internal/component/plugin/PluginResourceLoader;)V
    .locals 0

    iput-object p1, p0, Lcom/miui/internal/component/plugin/PluginContext;->pluginResourceLoader:Lcom/miui/internal/component/plugin/PluginResourceLoader;

    return-void
.end method
