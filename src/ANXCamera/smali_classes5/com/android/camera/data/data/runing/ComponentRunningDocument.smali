.class public Lcom/android/camera/data/data/runing/ComponentRunningDocument;
.super Lcom/android/camera/data/data/ComponentData;
.source "ComponentRunningDocument.java"


# static fields
.field public static final DOCUMENT_BLACK_WHITE:Ljava/lang/String; = "bin"

.field public static final DOCUMENT_ORIGIN:Ljava/lang/String; = "raw"

.field public static final DOCUMENT_STRENGTHEN:Ljava/lang/String; = "color"


# instance fields
.field private mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

.field private mSupported:Z


# direct methods
.method public constructor <init>(Lcom/android/camera/data/data/runing/DataItemRunning;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/data/data/ComponentData;-><init>(Lcom/android/camera/data/data/DataItemBase;)V

    iput-object p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    return-void
.end method


# virtual methods
.method public getDefaultValue(I)Ljava/lang/String;
    .locals 0
    .annotation build Landroidx/annotation/NonNull;
    .end annotation

    const-string p0, "raw"

    return-object p0
.end method

.method public getDisplayTitleString()I
    .locals 0

    const/4 p0, 0x0

    return p0
.end method

.method public getItems()Ljava/util/List;
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/android/camera/data/data/ComponentDataItem;",
            ">;"
        }
    .end annotation

    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    new-instance v0, Lcom/android/camera/data/data/ComponentDataItem;

    const/4 v1, -0x1

    const v2, 0x7f100190

    const-string v3, "raw"

    invoke-direct {v0, v1, v1, v2, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p0, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/data/data/ComponentDataItem;

    const v2, 0x7f10018c

    const-string v3, "bin"

    invoke-direct {v0, v1, v1, v2, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p0, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    new-instance v0, Lcom/android/camera/data/data/ComponentDataItem;

    const v2, 0x7f100191

    const-string v3, "color"

    invoke-direct {v0, v1, v1, v2, v3}, Lcom/android/camera/data/data/ComponentDataItem;-><init>(IIILjava/lang/String;)V

    invoke-interface {p0, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-static {p0}, Ljava/util/Collections;->unmodifiableList(Ljava/util/List;)Ljava/util/List;

    move-result-object p0

    return-object p0
.end method

.method public getKey(I)Ljava/lang/String;
    .locals 0

    const-string p0, "pref_document_mode_value_key"

    return-object p0
.end method

.method public isSwitchOn(I)Z
    .locals 0

    iget-boolean p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->mSupported:Z

    if-nez p1, :cond_0

    const/4 p0, 0x0

    return p0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    const-string p1, "pref_document_mode_key"

    invoke-virtual {p0, p1}, Lcom/android/camera/data/data/runing/DataItemRunning;->isSwitchOn(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method public reInit(IIZ)V
    .locals 1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->mSupported:Z

    if-eqz p3, :cond_1

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p3

    invoke-virtual {p3}, Lb/c/a/b;->qj()Z

    move-result p3

    if-nez p3, :cond_0

    invoke-static {}, Lcom/android/camera/data/DataRepository;->dataItemFeature()Lb/c/a/b;

    move-result-object p3

    invoke-virtual {p3}, Lb/c/a/b;->rj()Z

    move-result p3

    if-eqz p3, :cond_1

    :cond_0
    const/16 p3, 0xba

    if-ne p1, p3, :cond_1

    if-nez p2, :cond_1

    const/4 p1, 0x1

    iput-boolean p1, p0, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->mSupported:Z

    :cond_1
    return-void
.end method

.method public setEnabled(Z)V
    .locals 1

    const-string v0, "pref_document_mode_key"

    if-eqz p1, :cond_0

    iget-object p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {p0, v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->switchOn(Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    iget-object p0, p0, Lcom/android/camera/data/data/runing/ComponentRunningDocument;->mDataItemRunning:Lcom/android/camera/data/data/runing/DataItemRunning;

    invoke-virtual {p0, v0}, Lcom/android/camera/data/data/runing/DataItemRunning;->switchOff(Ljava/lang/String;)V

    :goto_0
    return-void
.end method
