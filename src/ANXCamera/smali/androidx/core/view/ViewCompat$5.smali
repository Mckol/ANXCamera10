.class final Landroidx/core/view/ViewCompat$5;
.super Landroidx/core/view/ViewCompat$AccessibilityViewProperty;
.source "ViewCompat.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Landroidx/core/view/ViewCompat;->accessibilityHeadingProperty()Landroidx/core/view/ViewCompat$AccessibilityViewProperty;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroidx/core/view/ViewCompat$AccessibilityViewProperty<",
        "Ljava/lang/Boolean;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>(ILjava/lang/Class;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroidx/core/view/ViewCompat$AccessibilityViewProperty;-><init>(ILjava/lang/Class;I)V

    return-void
.end method


# virtual methods
.method frameworkGet(Landroid/view/View;)Ljava/lang/Boolean;
    .locals 0
    .annotation build Landroidx/annotation/RequiresApi;
        value = 0x1c
    .end annotation

    invoke-virtual {p1}, Landroid/view/View;->isAccessibilityHeading()Z

    move-result p0

    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p0

    return-object p0
.end method

.method bridge synthetic frameworkGet(Landroid/view/View;)Ljava/lang/Object;
    .locals 0
    .annotation build Landroidx/annotation/RequiresApi;
        value = 0x1c
    .end annotation

    invoke-virtual {p0, p1}, Landroidx/core/view/ViewCompat$5;->frameworkGet(Landroid/view/View;)Ljava/lang/Boolean;

    move-result-object p0

    return-object p0
.end method

.method frameworkSet(Landroid/view/View;Ljava/lang/Boolean;)V
    .locals 0
    .annotation build Landroidx/annotation/RequiresApi;
        value = 0x1c
    .end annotation

    invoke-virtual {p2}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p0

    invoke-virtual {p1, p0}, Landroid/view/View;->setAccessibilityHeading(Z)V

    return-void
.end method

.method bridge synthetic frameworkSet(Landroid/view/View;Ljava/lang/Object;)V
    .locals 0
    .annotation build Landroidx/annotation/RequiresApi;
        value = 0x1c
    .end annotation

    check-cast p2, Ljava/lang/Boolean;

    invoke-virtual {p0, p1, p2}, Landroidx/core/view/ViewCompat$5;->frameworkSet(Landroid/view/View;Ljava/lang/Boolean;)V

    return-void
.end method

.method shouldUpdate(Ljava/lang/Boolean;Ljava/lang/Boolean;)Z
    .locals 0

    invoke-virtual {p0, p1, p2}, Landroidx/core/view/ViewCompat$AccessibilityViewProperty;->booleanNullToFalseEquals(Ljava/lang/Boolean;Ljava/lang/Boolean;)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method bridge synthetic shouldUpdate(Ljava/lang/Object;Ljava/lang/Object;)Z
    .locals 0

    check-cast p1, Ljava/lang/Boolean;

    check-cast p2, Ljava/lang/Boolean;

    invoke-virtual {p0, p1, p2}, Landroidx/core/view/ViewCompat$5;->shouldUpdate(Ljava/lang/Boolean;Ljava/lang/Boolean;)Z

    move-result p0

    return p0
.end method
