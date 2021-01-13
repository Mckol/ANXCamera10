.class Lcom/miui/internal/analytics/SQLiteStore;
.super Ljava/lang/Object;
.source "SQLiteStore.java"

# interfaces
.implements Lcom/miui/internal/analytics/Storable;


# static fields
.field public static final TAG:Ljava/lang/String; = "ANALYTICS.SQLITESTORE"


# instance fields
.field private mDatabaseHelper:Lcom/miui/internal/analytics/DatabaseHelper;


# direct methods
.method constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public close()V
    .locals 0

    return-void
.end method

.method public create(Landroid/content/Context;)V
    .locals 0

    invoke-static {}, Lcom/miui/internal/analytics/DatabaseHelper;->getInstance()Lcom/miui/internal/analytics/DatabaseHelper;

    move-result-object p1

    iput-object p1, p0, Lcom/miui/internal/analytics/SQLiteStore;->mDatabaseHelper:Lcom/miui/internal/analytics/DatabaseHelper;

    return-void
.end method

.method public readDataset(Ljava/lang/String;)Landroid/database/Cursor;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/analytics/SQLiteStore;->mDatabaseHelper:Lcom/miui/internal/analytics/DatabaseHelper;

    if-eqz p0, :cond_0

    invoke-virtual {p0, p1}, Lcom/miui/internal/analytics/DatabaseHelper;->query(Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object p0

    return-object p0

    :cond_0
    const/4 p0, 0x0

    return-object p0
.end method

.method public writeData(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 7

    iget-object v0, p0, Lcom/miui/internal/analytics/SQLiteStore;->mDatabaseHelper:Lcom/miui/internal/analytics/DatabaseHelper;

    if-eqz v0, :cond_0

    move-object v1, p1

    move-object v2, p2

    move-object v3, p3

    move-object v4, p4

    move-object v5, p5

    move-object v6, p6

    invoke-virtual/range {v0 .. v6}, Lcom/miui/internal/analytics/DatabaseHelper;->insert(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    :cond_0
    return-void
.end method
