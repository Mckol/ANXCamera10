.class public Lcom/miui/internal/variable/v21/Android_View_View_class;
.super Lcom/miui/internal/variable/v19/Android_View_View_class;
.source "Android_View_View_class.java"

# interfaces
.implements Lcom/miui/internal/variable/api/Overridable;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/miui/internal/variable/v19/Android_View_View_class;",
        "Lcom/miui/internal/variable/api/Overridable<",
        "Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;",
        ">;"
    }
.end annotation


# instance fields
.field private mImpl:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

.field private mOriginal:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;


# direct methods
.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Lcom/miui/internal/variable/v19/Android_View_View_class;-><init>()V

    new-instance v0, Lcom/miui/internal/variable/v21/Android_View_View_class$1;

    invoke-direct {v0, p0}, Lcom/miui/internal/variable/v21/Android_View_View_class$1;-><init>(Lcom/miui/internal/variable/v21/Android_View_View_class;)V

    iput-object v0, p0, Lcom/miui/internal/variable/v21/Android_View_View_class;->mImpl:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    return-void
.end method

.method static synthetic access$000(Lcom/miui/internal/variable/v21/Android_View_View_class;JLandroid/view/View;)V
    .locals 0

    invoke-virtual {p0, p1, p2, p3}, Lcom/miui/internal/variable/v16/Android_View_View_class_common;->handleRefreshDrawableState(JLandroid/view/View;)V

    return-void
.end method

.method static synthetic access$100(Lcom/miui/internal/variable/v21/Android_View_View_class;JLandroid/view/View;I)[I
    .locals 0

    invoke-virtual {p0, p1, p2, p3, p4}, Lcom/miui/internal/variable/v16/Android_View_View_class_common;->handleOnCreateDrawableState(JLandroid/view/View;I)[I

    move-result-object p0

    return-object p0
.end method


# virtual methods
.method public asInterface()Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;
    .locals 0

    iget-object p0, p0, Lcom/miui/internal/variable/v21/Android_View_View_class;->mImpl:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    return-object p0
.end method

.method public bridge synthetic asInterface()Ljava/lang/Object;
    .locals 0

    invoke-virtual {p0}, Lcom/miui/internal/variable/v21/Android_View_View_class;->asInterface()Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    move-result-object p0

    return-object p0
.end method

.method public bind(Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;)V
    .locals 0

    iput-object p1, p0, Lcom/miui/internal/variable/v21/Android_View_View_class;->mOriginal:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    return-void
.end method

.method public bridge synthetic bind(Ljava/lang/Object;)V
    .locals 0

    check-cast p1, Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    invoke-virtual {p0, p1}, Lcom/miui/internal/variable/v21/Android_View_View_class;->bind(Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;)V

    return-void
.end method

.method public buildProxy()V
    .locals 1

    invoke-super {p0}, Lcom/miui/internal/variable/v16/Android_View_View_class_common;->buildProxy()V

    const-string v0, "(Landroid/content/Context;Landroid/util/AttributeSet;II)V"

    invoke-virtual {p0, v0}, Lcom/miui/internal/util/ClassProxy;->attachConstructor(Ljava/lang/String;)J

    return-void
.end method

.method protected callOriginalOnCreateDrawableState(JLandroid/view/View;I)[I
    .locals 1

    iget-object v0, p0, Lcom/miui/internal/variable/v21/Android_View_View_class;->mOriginal:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    if-eqz v0, :cond_0

    invoke-interface {v0, p3, p4}, Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;->onCreateDrawableState(Landroid/view/View;I)[I

    move-result-object p0

    return-object p0

    :cond_0
    invoke-super {p0, p1, p2, p3, p4}, Lcom/miui/internal/variable/v16/Android_View_View_class_common;->callOriginalOnCreateDrawableState(JLandroid/view/View;I)[I

    move-result-object p0

    return-object p0
.end method

.method protected callOriginalRefreshDrawableState(JLandroid/view/View;)V
    .locals 1

    iget-object v0, p0, Lcom/miui/internal/variable/v21/Android_View_View_class;->mOriginal:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    if-eqz v0, :cond_0

    invoke-interface {v0, p3}, Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;->refreshDrawableState(Landroid/view/View;)V

    return-void

    :cond_0
    invoke-super {p0, p1, p2, p3}, Lcom/miui/internal/variable/v16/Android_View_View_class_common;->callOriginalRefreshDrawableState(JLandroid/view/View;)V

    return-void
.end method

.method protected callOriginal_init_(JLandroid/view/View;Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 6

    iget-object v0, p0, Lcom/miui/internal/variable/v21/Android_View_View_class;->mOriginal:Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;

    if-eqz v0, :cond_0

    move-object v1, p3

    move-object v2, p4

    move-object v3, p5

    move v4, p6

    move v5, p7

    invoke-interface/range {v0 .. v5}, Lcom/miui/internal/variable/api/v29/Android_View_View$Interface;->init(Landroid/view/View;Landroid/content/Context;Landroid/util/AttributeSet;II)V

    return-void

    :cond_0
    invoke-virtual/range {p0 .. p7}, Lcom/miui/internal/variable/v21/Android_View_View_class;->original_init_(JLandroid/view/View;Landroid/content/Context;Landroid/util/AttributeSet;II)V

    return-void
.end method

.method protected handle()V
    .locals 8

    const-wide/16 v1, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    const/4 v7, 0x0

    move-object v0, p0

    invoke-virtual/range {v0 .. v7}, Lcom/miui/internal/variable/v21/Android_View_View_class;->handle_init_(JLandroid/view/View;Landroid/content/Context;Landroid/util/AttributeSet;II)V

    invoke-super {p0}, Lcom/miui/internal/variable/v16/Android_View_View_class_common;->handle()V

    return-void
.end method

.method protected handle_init_(JLandroid/view/View;Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 0

    invoke-virtual/range {p0 .. p7}, Lcom/miui/internal/variable/v21/Android_View_View_class;->callOriginal_init_(JLandroid/view/View;Landroid/content/Context;Landroid/util/AttributeSet;II)V

    invoke-static {p3, p5}, Lcom/miui/internal/util/TaggingDrawableHelper;->initViewSequenceStates(Landroid/view/View;Landroid/util/AttributeSet;)V

    return-void
.end method

.method protected onClassProxyDisabled()V
    .locals 1

    invoke-static {}, Lcom/miui/internal/variable/api/v29/Android_View_View$Extension;->get()Lcom/miui/internal/variable/api/v29/Android_View_View$Extension;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/miui/internal/variable/api/AbstractExtension;->setExtension(Lcom/miui/internal/variable/api/Overridable;)V

    return-void
.end method

.method protected original_init_(JLandroid/view/View;Landroid/content/Context;Landroid/util/AttributeSet;II)V
    .locals 0

    new-instance p0, Ljava/lang/IllegalStateException;

    const-string p1, "com.miui.internal.variable.v21.Android_View_View_class.original_init_(long, View, Context, AttributeSet, int, int)"

    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0
.end method
