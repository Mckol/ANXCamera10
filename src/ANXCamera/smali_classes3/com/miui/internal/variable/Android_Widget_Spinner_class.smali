.class public abstract Lcom/miui/internal/variable/Android_Widget_Spinner_class;
.super Lcom/miui/internal/util/ClassProxy;
.source "Android_Widget_Spinner_class.java"

# interfaces
.implements Lcom/miui/internal/variable/IManagedClassProxy;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/miui/internal/variable/Android_Widget_Spinner_class$Factory;
    }
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/miui/internal/util/ClassProxy<",
        "Landroid/widget/Spinner;",
        ">;",
        "Lcom/miui/internal/variable/IManagedClassProxy;"
    }
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 1

    const-class v0, Landroid/widget/Spinner;

    invoke-direct {p0, v0}, Lcom/miui/internal/util/ClassProxy;-><init>(Ljava/lang/Class;)V

    return-void
.end method


# virtual methods
.method public buildProxy()V
    .locals 2

    const-string v0, "setPrompt"

    const-string v1, "(Ljava/lang/CharSequence;)V"

    invoke-virtual {p0, v0, v1}, Lcom/miui/internal/util/ClassProxy;->attachMethod(Ljava/lang/String;Ljava/lang/String;)J

    return-void
.end method

.method protected callOriginalSetPrompt(JLandroid/widget/Spinner;Ljava/lang/CharSequence;)V
    .locals 0

    invoke-virtual {p0, p1, p2, p3, p4}, Lcom/miui/internal/variable/Android_Widget_Spinner_class;->originalSetPrompt(JLandroid/widget/Spinner;Ljava/lang/CharSequence;)V

    return-void
.end method

.method protected abstract getArrowPopupWindow(Landroid/widget/Spinner;)Lmiui/widget/ArrowPopupWindow;
.end method

.method protected handle()V
    .locals 3

    const/4 v0, 0x0

    const-wide/16 v1, 0x0

    invoke-virtual {p0, v1, v2, v0, v0}, Lcom/miui/internal/variable/Android_Widget_Spinner_class;->handleSetPrompt(JLandroid/widget/Spinner;Ljava/lang/CharSequence;)V

    return-void
.end method

.method protected handleSetPrompt(JLandroid/widget/Spinner;Ljava/lang/CharSequence;)V
    .locals 1

    invoke-virtual {p0, p3}, Lcom/miui/internal/variable/Android_Widget_Spinner_class;->getArrowPopupWindow(Landroid/widget/Spinner;)Lmiui/widget/ArrowPopupWindow;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {v0, p4}, Lmiui/widget/ArrowPopupWindow;->setTitle(Ljava/lang/CharSequence;)V

    goto :goto_0

    :cond_0
    invoke-virtual {p0, p1, p2, p3, p4}, Lcom/miui/internal/variable/Android_Widget_Spinner_class;->callOriginalSetPrompt(JLandroid/widget/Spinner;Ljava/lang/CharSequence;)V

    :goto_0
    return-void
.end method

.method protected originalSetPrompt(JLandroid/widget/Spinner;Ljava/lang/CharSequence;)V
    .locals 0

    new-instance p0, Ljava/lang/IllegalStateException;

    const-string p1, "com.miui.internal.variable.Android_Widget_Spinner_class.originalSetPrompt(long, Spinner, CharSequence)"

    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0
.end method
